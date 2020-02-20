package com.online.store.waa3l.dto;

import com.online.store.waa3l.domain.Brand;
import com.online.store.waa3l.domain.Category;
import com.online.store.waa3l.domain.Image;
import com.online.store.waa3l.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProductDTO {
    private Long id;

    private String name;

    private BrandDTO brand;

    private CategoryDTO category;

    private double price;
    private List<Image> images = new ArrayList<>();
    private String description;
    private int averageRating;
    private UserDTO seller;


    public ProductDTO(Product product){
        this.id = product.getId();
        this.brand = new BrandDTO(product.getBrand());
        this.category = new CategoryDTO(product.getCategory());
        this.price = product.getPrice();
        this.description = product.getDescription();
        this.name = product.getName();
        this.images = product.getImages();
        this.averageRating = product.getAverageRating();
        this.seller = new UserDTO(product.getUser());
    }
}
