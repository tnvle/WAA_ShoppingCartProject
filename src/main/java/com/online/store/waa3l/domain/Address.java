package com.online.store.waa3l.domain;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @NotEmpty
    //@Size(min = 4, max = 50, message = "{size.street.validation}")
    @Size(min = 4, max = 50, message = "Size of Street must be between 4 and 50")
    private String street;

    @NotEmpty
    private String city;

    //@Size(min = 2, max = 2, message = "{size.state.validation}")
    @Size(min = 2, max = 2, message = "Size of State must be 2")
    @NotEmpty
    private String state;

    @NotEmpty
    private String zip;

    @Override
    public String toString() {
        return street + ", " + city + ", " + state + ", " + zip;
    }
}
