package com.birberber.controllers;

import com.birberber.constants.Constants;
import com.birberber.forms.LoginForm;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    private final MessageSource messageSource;

    public LoginPageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, String error, String logout) {
        model.addAttribute(new LoginForm());
        if (error != null) {
            model.addAttribute("errorMsg",
                    messageSource.getMessage("login.error.invalid", null, LocaleContextHolder.getLocale()));
        }
        if (logout != null) {
            model.addAttribute("msg",
                    messageSource.getMessage("login.success.logout", null, LocaleContextHolder.getLocale()));
        }
        return Constants.LOGIN_PAGE;
    }
}
