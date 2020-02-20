package com.online.store.waa3l.dto;

import com.online.store.waa3l.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private int active;
    private String firstName;
    private String lastName;
    private String email;

    public UserDTO(User user){
        this.id = user.getId();
        this.active = user.getActive();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
    }
}
