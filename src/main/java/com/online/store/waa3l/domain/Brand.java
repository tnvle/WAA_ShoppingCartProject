package com.online.store.waa3l.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "brand")
    private List<Product> products = new ArrayList<>();
}
