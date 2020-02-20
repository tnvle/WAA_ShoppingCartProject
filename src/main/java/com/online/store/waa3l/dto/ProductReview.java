package com.online.store.waa3l.dto;

import com.online.store.waa3l.domain.Product;
import com.online.store.waa3l.domain.Review;
import com.online.store.waa3l.domain.User;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductReview {
    private Product product;
    private User user;
    private Review review;

}
