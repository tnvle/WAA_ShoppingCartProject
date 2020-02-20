package com.online.store.waa3l.domain;


import com.online.store.waa3l.dto.CartItemDTO;
import com.online.store.waa3l.dto.ProductDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Cart {
    private String cartId;
    private Map<Long, CartItem> cartItems;
    private double grandTotal;

    public Cart() {
        cartItems = new HashMap<Long, CartItem>();
        grandTotal = 0.0;
    }

    public Cart(String cartId) {
        this();
        this.cartId = cartId;
    }


    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public Map<Long, CartItem> getCartItems() {
        return cartItems;
    }

    public List<CartItem> getCartItemList() {

        return new ArrayList<CartItem>(cartItems.values());
    }
    public List<CartItemDTO> getCartItemDTOList() {
        List<CartItemDTO> items = new ArrayList<CartItemDTO>();
        for(CartItem i : cartItems.values()){
            items.add(new CartItemDTO(new ProductDTO(i.getProduct()), i.getQuantity(), i.getTotalPrice()));
        }
        return items;
    }

    public void setCartItems(Map<Long, CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public double getGrandTotal() {
        return grandTotal;
    }

    public void addCartItem(CartItem item) {
        Long productId = item.getProduct().getId();
        if (cartItems.containsKey(productId)) {
            CartItem existingCartItem = cartItems.get(productId);
            existingCartItem.setQuantity(existingCartItem.getQuantity() + item.getQuantity());
            cartItems.put(productId, existingCartItem);
        } else {
            cartItems.put(productId, item);
        }
        updateGrandTotal();
    }

    public boolean updateCartItem(CartItem item) {
        Long productId = item.getProduct().getId();
        if (cartItems.containsKey(productId)) {
            CartItem existingCartItem = cartItems.get(productId);
            existingCartItem.setQuantity(item.getQuantity());
            cartItems.replace(productId, existingCartItem);
            updateGrandTotal();
            return true;
        }
        return false;
    }

    public void removeCartItem(CartItem item) {
        Long productId = item.getProduct().getId();
        cartItems.remove(productId);
        updateGrandTotal();
    }

    public void updateGrandTotal() {
        grandTotal = 0.0;
        for (CartItem item : cartItems.values()) {
            grandTotal += item.getTotalPrice();
        }
    }

    @Override
    public int hashCode() {
        final int prime = 71;
        int result = 1;
        result = prime * result + ((cartId == null) ? 0 : cartId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Cart other = (Cart) obj;
        if (cartId == null) {
            if (other.cartId != null)
                return false;
        } else if (!cartId.equals(other.cartId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Cart [cartId=" + cartId + ", cartItems=" + cartItems + ", grandTotal=" + grandTotal + "]";
    }
}
