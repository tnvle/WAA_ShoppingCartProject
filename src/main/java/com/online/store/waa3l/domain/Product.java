package com.online.store.waa3l.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private String description;
    @OneToMany(cascade = CascadeType.ALL)
    @Fetch(FetchMode.JOIN)
    @JoinTable(name = "product_image", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "image_id"))
    private List<Image> images = new ArrayList<>();

    @Transient
    private List<MultipartFile> fileImages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "product_review", joinColumns = @JoinColumn(name = "product_id"), inverseJoinColumns = @JoinColumn(name = "review_id"))
    private List<Review> reviews = new ArrayList<>();

    private boolean status = true;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    public int getAverageRating() {
        int sum = 0, count = 0;
        for (Review review : this.reviews) {
            if(review.isShow()) {
                count++;
                sum += review.getRating();
            }
        }
        if(count == 0) return 0;
        return sum / count;
    }

}
