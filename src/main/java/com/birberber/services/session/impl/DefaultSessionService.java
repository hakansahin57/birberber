package com.birberber.services.session.impl;


import com.birberber.domain.user.User;
import com.birberber.security.BirBerberUserDetails;
import com.birberber.services.session.SessionService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component("sessionService")
public class DefaultSessionService implements SessionService {

    @Override
    public User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

    @Override
    public void setSessionUser(HttpServletRequest request, Authentication authentication) {
        BirBerberUserDetails birBerberUserDetails = (BirBerberUserDetails) authentication.getPrincipal();
        User currentUser = birBerberUserDetails.getUser();
        request.getSession().setAttribute("user", currentUser);
    }
}