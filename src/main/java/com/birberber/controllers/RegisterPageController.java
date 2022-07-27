package com.birberber.controllers;

import com.birberber.constants.BirBerberConstants;
import com.birberber.forms.RegisterForm;
import com.birberber.services.user.BirBerberUserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

@Controller
public class RegisterPageController {

    @Resource(name = "birBerberUserService")
    private BirBerberUserService birBerberUserService;

    private static final Logger LOG = Logger.getLogger(RegisterPageController.class);

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        RegisterForm registerForm = new RegisterForm();
//        registerForm.setFirstName("Hakan");
//        registerForm.setLastName("Sahin");
//        registerForm.setEmail("hakan@sahin.com");
//        registerForm.setPassword("1");
        model.addAttribute(registerForm);
        return BirBerberConstants.BIRBERBER_REGISTER_PAGE;
    }

    @PostMapping("/register")
    public void registerUser(final RegisterForm registerForm) {
        birBerberUserService.register(registerForm);
        System.out.println("Register oldu");
    }
}
