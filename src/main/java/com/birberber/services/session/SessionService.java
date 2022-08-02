package com.birberber.services.session;

import com.birberber.domain.user.User;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface SessionService {
    User getSessionUser(HttpServletRequest request);

    void setSessionUser(HttpServletRequest request, Authentication authentication);

}
