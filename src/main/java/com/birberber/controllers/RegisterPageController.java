package com.birberber.controllers;

import com.birberber.constants.Constants;
import com.birberber.forms.RegisterForm;
import com.birberber.services.user.BirBerberUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.annotation.Resource;

@Controller
public class RegisterPageController {

    @Resource(name = "birBerberUserService")
    private BirBerberUserService birBerberUserService;

    private static final Logger LOG = LoggerFactory.getLogger(RegisterPageController.class);

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        RegisterForm registerForm = new RegisterForm();
        model.addAttribute(registerForm);
        return Constants.REGISTER_PAGE;
    }

    @PostMapping("/register")
    public void registerUser(final RegisterForm registerForm) {
        birBerberUserService.register(registerForm);
        System.out.println("Register oldu");
    }
}
