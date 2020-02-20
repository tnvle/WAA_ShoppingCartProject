package com.online.store.waa3l.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    private String message;

    private boolean show = false;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

}
