package com.online.store.waa3l.dto;

import com.online.store.waa3l.domain.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CartDTO {
    private List<CartItemDTO> cartItems = new ArrayList<>();
    private double grandTotal;
    public CartDTO(List<CartItemDTO> items, double total){
        this.cartItems = items;
        this.grandTotal = total;
    }
}
