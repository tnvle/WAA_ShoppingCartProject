package com.online.store.waa3l.domain;


import java.math.BigDecimal;

public class CartItem {
    private Product product;
    private int quantity;
    private double totalPrice;

    public CartItem() {
        // TODO Auto-generated constructor stub
    }

    public CartItem(Product product, int qty) {
        super();
        this.product = product;
        this.quantity = qty;
        this.totalPrice = product.getPrice()*qty;
    }
    public CartItem(Product product) {
        super();
        this.product = product;
        this.quantity = 1;
        this.totalPrice = product.getPrice();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        this.updateTotalPrice();
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.updateTotalPrice();
    }

    public double getTotalPrice() {

        return this.product.getPrice()*this.quantity;
    }

    public void updateTotalPrice() {
        totalPrice = this.product.getPrice()*this.quantity;
    }

    @Override
    public int hashCode() {
        final int prime = 311;
        int result = 1;
        result = prime * result + ((product == null) ? 0 : product.hashCode());
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
        CartItem other = (CartItem) obj;
        if (product == null) {
            if (other.product != null)
                return false;
        } else if (!product.equals(other.product))
            return false;
        return true;
    }
}
