package com.online.store.waa3l.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.online.store.waa3l.domain.User;
import lombok.*;

import java.util.Date;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrder {

    private User user;

    private Long id;

    private String product;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private Date date;

    private String phone;

    private Long total;

    private String payment;

    private String status;

    private String comment;

    private Integer points;

    private double subTotal;
}
