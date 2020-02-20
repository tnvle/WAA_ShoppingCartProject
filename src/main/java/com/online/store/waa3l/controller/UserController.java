package com.online.store.waa3l.controller;

import com.online.store.waa3l.domain.Role;
import com.online.store.waa3l.domain.RoleType;
import com.online.store.waa3l.domain.User;
import com.online.store.waa3l.service.RoleService;
import com.online.store.waa3l.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;


    @ModelAttribute("roles")
    public List<Role> populateRoles() {
        return roleService.getAllRolesNotAdmin(RoleType.ROLE_ADMIN.getValue());
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
    public String getAddUserForm(@ModelAttribute("user") User user) {
        return "user-form";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String processAddUserForm(@Valid @ModelAttribute("user") User user, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) return "user-form";
        User savedUser = userService.save(user);
        userService.sendEmailRegisterUser(savedUser.getId());
        redirectAttributes.addFlashAttribute("savedUser", savedUser);
        return "redirect:/user-detail";
    }

    @RequestMapping("/user-detail")
    public String detail() {
        return "user-detail";
    }
}
