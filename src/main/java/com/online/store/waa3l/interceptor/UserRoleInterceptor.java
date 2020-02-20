package com.online.store.waa3l.interceptor;

import com.online.store.waa3l.domain.Advertisement;
import com.online.store.waa3l.domain.RoleType;
import com.online.store.waa3l.repository.UserRepository;
import com.online.store.waa3l.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Component
public class UserRoleInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AdvertisementService advertisementService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        Principal principal = request.getUserPrincipal();
        String userRole = RoleType.ROLE_BUYER.toString();

        boolean isAuthenticated = false;
        if(principal != null){
            if(request.isUserInRole("ADMIN")) {
                userRole = RoleType.ROLE_ADMIN.toString();
            }
            else if (request.isUserInRole("SELLER")){
                userRole = RoleType.ROLE_SELLER.toString();
            }

            isAuthenticated = true;

        }

        if(modelAndView != null) {

            Advertisement advertisement = advertisementService.getActiveAdvertisement();
            modelAndView.getModelMap().addAttribute("ads", advertisement);
            modelAndView.getModelMap().addAttribute("userRole", userRole);
            modelAndView.getModelMap().addAttribute("isAuthenticated", isAuthenticated);

        }
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
