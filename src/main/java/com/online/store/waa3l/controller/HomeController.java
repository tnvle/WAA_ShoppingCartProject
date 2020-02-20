package com.online.store.waa3l.controller;

import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.Cart;
import com.online.store.waa3l.helper.SessionAttributeHelper;
import com.online.store.waa3l.service.EmailService;
import com.online.store.waa3l.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Arrays;

@Controller
@SessionAttributes(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART)
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    ResourceLoader resourceLoader;


    @Autowired
    EmailService emailService;


    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("products", productService.findTopProduct());
        return "index";
    }

}
