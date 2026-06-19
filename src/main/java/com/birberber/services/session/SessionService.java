package com.birberber.services.session;

import com.birberber.domain.user.User;
import org.springframework.security.core.Authentication;

import jakarta.servlet.http.HttpServletRequest;

public interface SessionService {
    User getCurrentUser(HttpServletRequest request);

    void setSessionUser(HttpServletRequest request, Authentication authentication);

}
