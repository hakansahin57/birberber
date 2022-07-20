package com.birberber.controllers;

import com.birberber.constants.BirBerberConstants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegisterPageController {

    private static final Logger LOG = Logger.getLogger(RegisterPageController.class);

    @GetMapping
    @RequestMapping(value = "/register")
    public String getHomePage(Model model) {
        return BirBerberConstants.BIRBERBER_REGISTER_PAGE;
    }
}
