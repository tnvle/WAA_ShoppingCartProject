package com.online.store.waa3l.advice;


import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.Cart;
import com.online.store.waa3l.helper.SessionAttributeHelper;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@ControllerAdvice
public class ControllerExceptionAdvice {
    @ModelAttribute(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART)
    public Cart shoppingCart() {
        return new Cart();
    }
}
