package com.online.store.waa3l.config;

import com.online.store.waa3l.domain.RoleType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class CustomRedirectLoginSucessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();

        if (roles != null && !roles.isEmpty()) {

            String userRole = roles.get(0).getAuthority();

            if (RoleType.ROLE_ADMIN.toString().equals(userRole))
                response.sendRedirect("/admin");
            else if (RoleType.ROLE_SELLER.toString().equals(userRole))
                response.sendRedirect("/seller");
            else if (RoleType.ROLE_BUYER.toString().equals(userRole))
                response.sendRedirect("/");
        }
    }
}
