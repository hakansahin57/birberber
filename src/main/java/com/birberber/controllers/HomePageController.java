package com.birberber.controllers;

import com.birberber.constants.BirBerberConstants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class HomePageController {

    private static final Logger LOG = Logger.getLogger(HomePageController.class);

    @GetMapping
    public String login(Model model) {
        return BirBerberConstants.BIRBERBER_HOME_PAGE;
    }

}