package com.online.store.waa3l.controller.admin;

import com.online.store.waa3l.domain.*;
import com.online.store.waa3l.dto.ProductOrder;
import com.online.store.waa3l.dto.ProductReview;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;

    @Autowired
    EmailService emailService;

    @Autowired
    OrderService orderService;

    @Autowired
    ReviewService reviewService;

    @GetMapping("")
    public String home() {
        return "admin/index";
    }

    @GetMapping("/sellers")
    public String getAllSellers(Model model) {
        List<User> sellers = userService.getAllUsersByRole(RoleType.ROLE_SELLER.getValue());
        model.addAttribute("users", sellers);
        return "admin/sellers";
    }

    @GetMapping("/sellers/seller-details/{id}")
    public String getApproval(@PathVariable("id") Long id, @ModelAttribute("seller") User user, Model model) {
        User seller = userService.findUserById(id);
        model.addAttribute("seller", seller);
        return "admin/seller-details";
    }

    @PostMapping("/sellers/seller-details/{id}")
    public String submitApproval(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        User seller = userService.findUserById(id);
        seller.setActive(1);
        userService.save(seller);
        userService.sendEmailApprovalSeller(seller.getId());
        List<User> sellers = userService.getAllUsersByRole(RoleType.ROLE_BUYER.getValue());
        redirectAttributes.addFlashAttribute("users", sellers);
        return "redirect:/admin/sellers";
    }

    @GetMapping("/product/reviews")
    public String reviews(Model model) {


        List<Product> products = productService.findTopProduct();

        List<ProductReview> productReviews = new ArrayList<>();

        for (Product product : products) {
            for (Review review : product.getReviews()) {
                productReviews.add(new ProductReview(product, review.getUser(), review));
            }
        }
        model.addAttribute("productReviews", productReviews);

        return "admin/review-list";
    }

    @PostMapping("/product/reviews/approve/{reviewId}")
    @ResponseBody
    public Response approveReview(@PathVariable Long reviewId) {
        return reviewService.approveReview(reviewId);
    }

    @GetMapping("/product/orders")
    public String orders(Model model) {

        List<String> orderStatuses = Stream.of(OrderStatusType.values()).map(Enum::name).collect(Collectors.toList());

        List<Order> orders = orderService.getAllOrders();

        List<ProductOrder> productOrders = new ArrayList<>();

        for (Order order : orders) {
            ProductOrder productOrder = new ProductOrder();
            productOrder.setUser(order.getUser());
            productOrder.setComment(order.getComment());
            productOrder.setPayment(order.getPayment().getPayment().name());
            productOrder.setStatus(order.getStatus().getStatus().name());
            productOrder.setDate(order.getOrderDate());
            productOrder.setPhone(order.getPhone());
            productOrder.setPoints(order.getPoint());

            productOrder.setSubTotal(order.getSubTotal());
            productOrder.setId(order.getId());
            Double total = 0.0;

            List<String> products = new ArrayList<>();


            for (OrderLine orderLine : order.getOrderLines()) {
                total += Double.valueOf(orderLine.getProduct().getPrice() * orderLine.getQuantity());
                products.add(orderLine.getProduct().getName());
            }

            productOrder.setProduct(products.toString());

            productOrder.setTotal(total.longValue());

            productOrders.add(productOrder);
        }

        model.addAttribute("productOrders", productOrders);
        model.addAttribute("orderStatuses", orderStatuses);

        return "admin/order-list";
    }

    @ResponseBody
    @PutMapping("/product/orders/{orderId}/{status}")
    public Response updateOrderStatus(@PathVariable Long orderId, @PathVariable String status) {
        return orderService.updateOrderStatus(orderId, status);
    }


}
