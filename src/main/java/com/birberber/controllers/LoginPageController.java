package com.birberber.controllers;

import com.birberber.constants.BirBerberConstants;
import com.birberber.forms.LoginForm;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginPageController {

    private static final Logger LOG = Logger.getLogger(LoginPageController.class);

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        model.addAttribute(new LoginForm());
        return BirBerberConstants.BIRBERBER_LOGIN_PAGE;
    }

    @PostMapping("/login")
    public void login(final LoginForm loginForm) {
        System.out.println("Login methodu");
    }
}
