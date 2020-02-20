package com.online.store.waa3l.domain;


import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private boolean show = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Transient
    private MultipartFile fileImage;


}
