package com.online.store.waa3l.controller;

import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.Cart;
import com.online.store.waa3l.helper.SessionAttributeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/cart")
@SessionAttributes(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART)
public class CartController {

    @RequestMapping
    public String getCart(Model model) {
        return "cart";
    }
}