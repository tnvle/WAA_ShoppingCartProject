package com.online.store.waa3l.controller;

import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.Cart;
import com.online.store.waa3l.domain.CartItem;
import com.online.store.waa3l.domain.Product;
import com.online.store.waa3l.domain.User;
import com.online.store.waa3l.dto.CartDTO;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.exception.ProductNotFoundException;
import com.online.store.waa3l.helper.SessionAttributeHelper;
import com.online.store.waa3l.service.ProductService;
import com.online.store.waa3l.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@SessionAttributes(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART)
public class ProductDetailsController {
    @Autowired
    private UserService userService;

    @Autowired
    private  ProductService productService;

    @GetMapping("/product-details/{pid}")
    public  String details(@PathVariable Long pid, Model model){

        //For HanTruong
        model.addAttribute("productId", pid);

        //For current user logged in
        model.addAttribute("currentUser", userService.getCurrentUser());

        //TODO (Thu Truong):
        Product product = productService.getProductById(pid);
        model.addAttribute(product);
        return "product-details";
    }

    @RequestMapping(value = "/rest/follow/{seller_id}", method = RequestMethod.PUT)
    public @ResponseBody
    Response followSeller(@PathVariable("seller_id") Long sellerId, HttpServletRequest request, Model model) {
        User user = userService.findUserById(sellerId);
        if (user == null) {
            throw new IllegalArgumentException(new ProductNotFoundException(sellerId));
        }
        user.addFollower(userService.getCurrentUser());
        userService.save(user);
        return new Response(true);
    }
    @RequestMapping(value = "/rest/unfollow/{seller_id}", method = RequestMethod.PUT)
    public @ResponseBody
    Response unfollowSeller(@PathVariable("seller_id") Long sellerId, HttpServletRequest request, Model model) {
        User user = userService.findUserById(sellerId);
        if (user == null) {
            throw new IllegalArgumentException(new ProductNotFoundException(sellerId));
        }
        user.removeFollower(userService.getCurrentUser());
        userService.save(user);
        return new Response(true);
    }
}
