package com.birberber.controllers;

import com.birberber.constants.BirBerberConstants;
import com.birberber.forms.LoginForm;
import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginPageController {

    private static final Logger LOG = Logger.getLogger(LoginPageController.class);

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute(new LoginForm());
        return BirBerberConstants.BIRBERBER_LOGIN_PAGE;
    }

    @PostMapping("/login/process")
    public void login(final LoginForm loginForm) {
        System.out.println("Login methodu");
    }

    @GetMapping(value="/logout")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; //You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }
}
