package com.birberber.services.session.impl;


import com.birberber.domain.user.User;
import com.birberber.security.BirBerberUserDetails;
import com.birberber.services.session.SessionService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;

@Component("sessionService")
public class DefaultSessionService implements SessionService {

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            return user;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || !(authentication.getPrincipal() instanceof BirBerberUserDetails details)) {
            return null;
        }

        user = details.getUser();
        request.getSession().setAttribute("user", user);
        return user;
    }

    @Override
    public void setSessionUser(HttpServletRequest request, Authentication authentication) {
        BirBerberUserDetails birBerberUserDetails = (BirBerberUserDetails) authentication.getPrincipal();
        User currentUser = birBerberUserDetails.getUser();
        request.getSession().setAttribute("user", currentUser);
    }
}