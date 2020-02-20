package com.online.store.waa3l.service.impl;

import com.online.store.waa3l.domain.Review;
import com.online.store.waa3l.domain.Role;
import com.online.store.waa3l.domain.RoleType;
import com.online.store.waa3l.domain.User;
import com.online.store.waa3l.dto.Response;
import com.online.store.waa3l.repository.UserRepository;
import com.online.store.waa3l.service.EmailService;
import com.online.store.waa3l.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private EmailService emailService;

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }

        if (user.getRoles() != null && !user.getRoles().isEmpty()) {
            Role role = user.getRoles().iterator().next();
            if (role != null && role.getRole() == RoleType.ROLE_BUYER)
                user.setActive(1);

        }

        return userRepository.save(user);
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findByUsername(userName).get();
    }

    public List<User> getAllUsersByRole(Integer role) {
        return userRepository.getAllUsersByRole(role);
    }

    @Override
    public User getCurrentUser() {
        Principal principal = request.getUserPrincipal();
        if (principal == null)
            return null;
        return userRepository.findByUsername(request.getUserPrincipal().getName()).get();
    }

    @Override
    public void sendEmailRegisterUser(Long userId) {
        User user = userRepository.findById(userId).get();
        Role role = user.getRoles().iterator().next();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",").append("<br/><br/>");
        if (role != null && role.getRole() == RoleType.ROLE_BUYER) {
            stringBuilder.append("Register Buyer successfully.<br/><br/>");
            stringBuilder.append("Please login at %s <br/><br/> Thank you so much. <br/><br/>Waa3L Team");
            emailService.sendEmail(stringBuilder.toString(), "Register Buyer", Arrays.asList(user.getEmail()));
        } else if (role != null && role.getRole() == RoleType.ROLE_SELLER) {
            stringBuilder.append("Register Seller successfully. <br/><br/>");
            stringBuilder.append("Please wait for Admin's approval then login at %s. <br/><br/> Thank you so much. <br/><br/>Waa3L Team");
            emailService.sendEmail(stringBuilder.toString(), "Register Seller", Arrays.asList(user.getEmail()));
        }
    }

    @Override
    public void sendEmailApprovalSeller(Long userId) {
        User user = userRepository.findById(userId).get();
        Role role = user.getRoles().iterator().next();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dear ").append(user.getFirstName()).append(" ").append(user.getLastName()).append(",").append("<br/><br/>");
        if (role != null && role.getRole() == RoleType.ROLE_SELLER) {
            stringBuilder.append("Your registration to be a seller has just approved by Waa3L Team. <br/><br/>");
            stringBuilder.append("Now you can login at %s and sell your products. <br/><br/> Thank you so much.<br/><br/>Waa3L Team");
            emailService.sendEmail(stringBuilder.toString(), "Approve Seller's Registration", Arrays.asList(user.getEmail()));
        }
    }
}
