package com.online.store.waa3l.controller;

import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.*;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.service.EmailService;
import com.online.store.waa3l.service.OrderService;
import com.online.store.waa3l.service.ProductService;
import com.online.store.waa3l.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/buyer")
@SessionAttributes(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART)
public class CheckoutController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;//just for test

    @GetMapping("")
    public String buyerHome(){
        return "redirect:/buyer/orders";
    }

    @GetMapping("/checkout")
    public  String getCheckout(@ModelAttribute("order") Order order, Model model, HttpSession session){

        User user = userService.getCurrentUser();
        Cart cart = (Cart)session.getAttribute(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART);

//        //for test
//        Cart cart = new Cart();
//        CartItem item1 = new CartItem();
//        Product product1 = new Product();
//        product1.setName("Product1");
//        product1.setPrice(10);
//        item1.setProduct(product1);
//        item1.setQuantity(2);
//        cart.addCartItem(item1);

        if(cart == null||cart.getCartItems().size() == 0)
            return "redirect:/cart";

        double subTotal = cart.getGrandTotal();
        int accumulatedPoints = orderService.getPointsOfUser(user.getId());
        int equalPoints = orderService.exchangeToEqualPoints(subTotal);
        boolean canPayByPoint = accumulatedPoints >= equalPoints;


        List<Payment> payments = orderService.getAllPayments();
//        model.addAttribute("cart", cart);
        model.addAttribute("user", user);
        model.addAttribute("payments", payments);
        model.addAttribute("canPayByPoint", canPayByPoint);
        model.addAttribute("accumulatedPoints", accumulatedPoints);
        model.addAttribute("equalPoints", equalPoints);

        return "checkout/checkout";
    }

    @PostMapping("/checkout")
    public String checkout(@Valid @ModelAttribute("order") Order order, BindingResult bindingResult, HttpSession session, SessionStatus status){

        User user = userService.getCurrentUser();

        Cart cart = (Cart)session.getAttribute(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART);
//        //for test
//        Cart cart = new Cart();
//        CartItem item1 = new CartItem();
//        Product product1 = productService.getProductById(1L);
//        item1.setProduct(product1);
//        item1.setQuantity(2);
//        cart.addCartItem(item1);

        orderService.checkout(order, cart, user);
//        session.removeAttribute(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART);
        status.setComplete();
        return "redirect:/buyer/orders";
    }

    @GetMapping("/orders")
    public  String getOrderList(Model model){

        User user = userService.getCurrentUser();
        List<Order> orders = orderService.getAllOrdersByUser(user.getId());
        int idShipped = orderService.getStatusByName(OrderStatusType.Shipped).getId();
        int points = orderService.getPointsOfUser(user.getId());

        model.addAttribute("orders", orders);
        model.addAttribute("idShipped", idShipped);
        model.addAttribute("points", points);

        return "checkout/order_list";
    }

    @PostMapping("/order/{orderId}/canceled")
    @ResponseBody
    public Response cancelOrder(@PathVariable Long orderId) {
        Order order = orderService.cancelOrder(orderId);
        return new Response(order.getPoint());
    }

    @GetMapping("/order-details/{id}")
    public  String getOrderDetails(Model model, @PathVariable("id") Long orderId){

        Order order = orderService.getOrderById(orderId);

        model.addAttribute("order", order);

        return "checkout/order_details";
    }

    @RequestMapping(value = "/order/{id}/downloadPDF", method = RequestMethod.GET, produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> orderPDFReport(@PathVariable("id") Long orderId) {

        Order order = orderService.getOrderById(orderId);

        ByteArrayInputStream bis = orderService.createReport(order);

        String fileName = String.format("Order#%d.pdf", order.getId());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", String.format("inline; filename=%s", fileName));

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }

}
