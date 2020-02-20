package com.online.store.waa3l.service.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.*;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.repository.OrderRepository;
import com.online.store.waa3l.repository.OrderStatusRepository;
import com.online.store.waa3l.repository.PaymentRepository;
import com.online.store.waa3l.service.EmailService;
import com.online.store.waa3l.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private EmailService emailService;


    @Override
    public Order save(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public OrderStatus getStatusByName(OrderStatusType status) {
        return orderStatusRepository.getStatusByName(status);
    }

    @Override
    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Payment getPaymentById(int id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public List<Order> getAllOrdersByUser(Long userId){
        return orderRepository.getAllOrdersByUser(userId);
    }

    @Override
    public Order getOrderById(Long id){
        return orderRepository.findById(id).get();
    }

    @Override
    public int getPointsOfUser(Long userId){
        int points = 0;
        List<Order> orders = this.getAllOrdersByUser(userId);

        for(Order o: orders)
            points += o.getPoint();

        return points;
    }

    @Override
    public int exchangeToAccumulatedPoints(double subTotal){
        return (int)subTotal/ ApplicationConstants.ACCUMULATED_POINT_EXCHANGE_RATE;
    }

    @Override
    public int exchangeToEqualPoints(double price){
        return (int)price/ ApplicationConstants.EQUAL_POINT_EXCHANGE_RATE;
    }

    @Override
    public Order cancelOrder(Long orderId){
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if(!optionalOrder.isPresent())
            throw new RuntimeException("Order does not exist");

        Order order = optionalOrder.get();
        order.setStatus(getStatusByName(OrderStatusType.Cancelled));

        if(order.getPayment().getPayment() == PaymentMethod.POINT)
            order.setPoint(0);

        orderRepository.save(order);

        User user = order.getUser();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("<br/><br/>");
        stringBuilder.append("Your order #" + orderId.toString() + " on 3L* Store (%s) has been cancelled.<br/><br/>");
        stringBuilder.append("Thank you so much<br/><br/>Waa3L Team.");

        emailService.sendEmail(stringBuilder.toString(), "Order Canceled", Arrays.asList(user.getEmail()));
        return order;
    }

    @Override
    public Response updateOrderStatus(Long orderId, String status) {

        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (!orderOptional.isPresent())
            return new Response("404", "Order not found", null);

        Order order = orderOptional.get();

        OrderStatusType orderStatusType = OrderStatusType.valueOf(status);
        OrderStatus orderStatus = orderStatusRepository.getStatusByName(orderStatusType);
        order.setStatus(orderStatus);

        if(order.getPayment().getPayment() != PaymentMethod.POINT){
            if(orderStatusType == OrderStatusType.Successful){
                Double totalPrice = order.getOrderLines().stream().map(e->e.getQuantity()*e.getProduct().getPrice()).mapToDouble(Double::doubleValue).sum();
//            order.setPoint(totalPrice.intValue() / ApplicationConstants.EXCHANGE_POINT_RATE);
                order.setPoint(this.exchangeToAccumulatedPoints(totalPrice));
            }
            else
                order.setPoint(0);
        }
        else{
            if(orderStatusType == OrderStatusType.Returned||orderStatusType == OrderStatusType.Cancelled)
                order.setPoint(0);
            else
                order.setPoint(-1 * this.exchangeToEqualPoints(order.getSubTotal()));
        }

//        if(orderStatusType == OrderStatusType.Successful && order.getPayment().getPayment() != PaymentMethod.POINT){
//            Double totalPrice = order.getOrderLines().stream().map(e->e.getQuantity()*e.getProduct().getPrice()).mapToDouble(Double::doubleValue).sum();
////            order.setPoint(totalPrice.intValue() / ApplicationConstants.EXCHANGE_POINT_RATE);
//            order.setPoint(this.exchangeToAccumulatedPoints(totalPrice));
//        }
////        else{ // reset in case of return
////            order.setPoint(0);
////        }
//        if(orderStatusType == OrderStatusType.Returned)
//            order.setPoint(0);

        orderRepository.save(order);

        User user = order.getUser();

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("<br/><br/>");
        stringBuilder.append(String.format("Your ORDER #%s on 3L* Store Store has been update status to %s by Admin.<br/><br/>", order.getId(), status.toUpperCase()));
        stringBuilder.append("We will keep you updated about the order. <br/><br/> Happy Shopping at %s. <br/><br/>Thank you so much<br/><br/>Waa3L Team.");

        emailService.sendEmail(stringBuilder.toString(), "Order Status has been updated", Arrays.asList(user.getEmail()));
        return new Response(order.getPoint());

    }

    public void checkout(Order order, Cart cart, User user){

        order.setOrderDate(new Date());
        order.setStatus(this.getStatusByName(OrderStatusType.Processing));
        order.setPayment(this.getPaymentById(order.getPayment().getId()));

        List<OrderLine> orderlines = new ArrayList<>();
        for(CartItem cartItem: cart.getCartItemList()){
            OrderLine orderItem = new OrderLine();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderlines.add(orderItem);
        }

        order.setOrderLines(orderlines);
        order.setSubTotal(cart.getGrandTotal());
        if(order.getPayment().getPayment() == PaymentMethod.POINT)
            order.setPoint(-1 * this.exchangeToEqualPoints(order.getSubTotal()));

        order.setUser(user);

        orderRepository.save(order);

        //send email
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append("<br/><br/>");
        stringBuilder.append("Your order #" + order.getId().toString() + " on 3L* Store(%s) has been created.<br/><br/>");
        stringBuilder.append("Thank you so much<br/><br/>Waa3L Team.");

        emailService.sendEmail(stringBuilder.toString(), "Order Created", Arrays.asList(user.getEmail()));
    }

    public ByteArrayInputStream createReport(Order order){

        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {

            PdfWriter.getInstance(document, out);
            document.open();

            document.addTitle(String.format("ORDER #%d", order.getId()));


            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            Paragraph p;
            p = new Paragraph(String.format("ORDER #%d", order.getId()), headFont);
            document.add(p);

            document.add(new Paragraph(" "));

            p = new Paragraph(String.format("Customer: %s %s", order.getFirstName(), order.getLastName()));
            document.add(p);

            p = new Paragraph(String.format("Date: %s", order.getOrderDate().toString()));
            document.add(p);

            p = new Paragraph(String.format("Shipping address: %s, %s, %s, %s",
                    order.getAddress().getStreet(), order.getAddress().getCity(), order.getAddress().getState(), order.getAddress().getZip()));
            document.add(p);

            p = new Paragraph(String.format("Phone: %s", order.getPhone()));
            document.add(p);

            p = new Paragraph(String.format("Status: %s", order.getStatus().getStatus()));
            document.add(p);

            p = new Paragraph(String.format("Points: %s", order.getPoint()));
            document.add(p);

            document.add(new Paragraph(" "));

            p = new Paragraph("Products:");
            document.add(p);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(90);
            table.setWidths(new int[]{2, 3, 2, 2});

            PdfPCell hcell;
            hcell = new PdfPCell(new Phrase("ID", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            hcell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Name", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
            hcell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Price", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            hcell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(hcell);

            hcell = new PdfPCell(new Phrase("Quantity", headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            hcell.setBorder(PdfPCell.NO_BORDER);
            table.addCell(hcell);

            for(OrderLine orderLine: order.getOrderLines()){
                PdfPCell cell;

                cell = new PdfPCell(new Phrase(orderLine.getProduct().getId().toString()));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);


                cell = new PdfPCell(new Phrase(orderLine.getProduct().getName()));
                cell.setPaddingLeft(5);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);


                cell = new PdfPCell(new Phrase(String.format("$%.2f", orderLine.getProduct().getPrice())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);

                cell = new PdfPCell(new Phrase(String.format("%d", orderLine.getQuantity())));
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                cell.setPaddingRight(5);
                cell.setBorder(PdfPCell.NO_BORDER);
                table.addCell(cell);
            }

            document.add(table);

            document.add(new Paragraph(" "));

            p = new Paragraph(String.format("Sub Total: $%.2f", order.getSubTotal()));
            document.add(p);

            p = new Paragraph(String.format("Payment Method: %s", order.getPayment().getPayment()));
            document.add(p);

            document.add(new Paragraph(" "));

            p = new Paragraph(String.format("Thank you %s %s for your order!", order.getFirstName(), order.getLastName()));
            document.add(p);


            document.close();

        } catch (DocumentException ex) {

            int x = 1;
//            logger.error("Error occurred: {0}", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());


    }


}
