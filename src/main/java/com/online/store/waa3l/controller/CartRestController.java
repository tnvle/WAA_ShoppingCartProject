package com.online.store.waa3l.controller;

import com.online.store.waa3l.constant.ApplicationConstants;
import com.online.store.waa3l.domain.Cart;
import com.online.store.waa3l.domain.CartItem;
import com.online.store.waa3l.domain.Product;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.dto.CartDTO;
import com.online.store.waa3l.exception.ProductNotFoundException;
import com.online.store.waa3l.helper.SessionAttributeHelper;
import com.online.store.waa3l.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "rest/cart")
@SessionAttributes(ApplicationConstants.SESSION_ATTRIBUTES.SHOPPING_CART)
public class CartRestController {
    @Autowired
    private ProductService productService;
//
//    @RequestMapping(value = "/add/{productId}", method = RequestMethod.PUT)
////    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public @ResponseBody
//    Response addItem(@PathVariable Long productId, HttpServletRequest request, Model model) {
//        Cart cart = SessionAttributeHelper.getCartFromModel(model);
//        Product product = productService.getProductById(productId);
//        if (product == null) {
//            throw new IllegalArgumentException(new ProductNotFoundException(productId));
//        }
//        cart.addCartItem(new CartItem(product, 1));
//        SessionAttributeHelper.setCartToModel(cart, model);
//        return new Response(new CartDTO(cart.getCartItemDTOList(), cart.getGrandTotal()));
//    }
    @RequestMapping(value = "/add/{productId}/{qty}", method = RequestMethod.PUT)
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody
    Response addItemWithQty(@PathVariable("productId") Long productId, @PathVariable("qty") Integer qty,
                               HttpServletRequest request, Model model) {
        Cart cart = SessionAttributeHelper.getCartFromModel(model);
        if (cart == null) {
            cart = new Cart();
            cart.setCartId(request.getSession(true).getId());
            SessionAttributeHelper.setCartToModel(cart, model);
        }
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException(new ProductNotFoundException(productId));
        }
        if(qty == null) qty = 1;
        cart.addCartItem(new CartItem(product, qty));
        CartDTO cartDTO = new CartDTO(cart.getCartItemDTOList(), cart.getGrandTotal());
        SessionAttributeHelper.setCartToModel(cart, model);
        return new Response(cartDTO);
    }

    @RequestMapping(value = "/remove/{productId}", method = RequestMethod.PUT)
    public @ResponseBody
    Response removeItem(@PathVariable Long productId, HttpServletRequest request, Model model) {
        Cart cart = SessionAttributeHelper.getCartFromModel(model);
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException(new ProductNotFoundException(productId));
        }
        cart.removeCartItem(new CartItem(product));
        SessionAttributeHelper.setCartToModel(cart, model);
        return new Response(new CartDTO(cart.getCartItemDTOList(), cart.getGrandTotal()));
    }
    @RequestMapping(value = "/update/{productId}/{qty}", method = RequestMethod.PUT)
    public @ResponseBody
    Response updateQty(@PathVariable("productId") Long productId, @PathVariable("qty") Integer qty, HttpServletRequest request, Model model) {
        Cart cart = SessionAttributeHelper.getCartFromModel(model);
        Product product = productService.getProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException(new ProductNotFoundException(productId));
        }
        if(cart.updateCartItem(new CartItem(product, qty))) {
            SessionAttributeHelper.setCartToModel(cart, model);
            return new Response(new CartDTO(cart.getCartItemDTOList(), cart.getGrandTotal()));
        } else {
            return new Response("500", "Product was not found in shopping cart.", null);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Illegal request, please verify your payload")
    public void handleClientErrors(Exception ex) {
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
    public void handleServerErrors(Exception ex) {
    }
}
