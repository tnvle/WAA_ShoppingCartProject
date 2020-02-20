package com.online.store.waa3l.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 20, message = "Size of Username must be from 4 to 20")
    @Column(name = "username")
    private String username;

    @NotEmpty
    @Column(name = "password")
    private String password;

    @Column(name = "active")
    private int active;

    @NotEmpty
    @Column(name = "firstname")
    private String firstName;

    @NotEmpty
    @Column(name = "lastname")
    private String lastName;

    @NotEmpty
    @Email
    @Column(name = "email")
    private String email;

    @NotEmpty
    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Valid
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Product> products;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="user")
    private List<Order> orders;


    @Column(columnDefinition = "int default 0")
    private int point = 0;

    @ManyToMany(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(name = "seller_follower", joinColumns = @JoinColumn(name = "seller_id"), inverseJoinColumns = @JoinColumn(name = "follower_id"))
    private List<User> followers = new ArrayList<>();

    public boolean checkFollower(User user){
        return (this.followers.indexOf(user) > -1);
    }

    public void addFollower(User user){
        this.followers.add(user);
    }
    public void removeFollower(User user){
        this.followers.remove(user);
    }

}
