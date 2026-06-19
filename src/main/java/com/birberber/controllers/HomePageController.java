package com.birberber.controllers;

import com.birberber.constants.Constants;
import com.birberber.domain.user.User;
import com.birberber.services.session.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/")
public class HomePageController {

    private static final Logger LOG = LoggerFactory.getLogger(HomePageController.class);

    @Resource
    private SessionService sessionService;

    @GetMapping
    public String getHomePage(Model model, HttpServletRequest request) {
        User user = sessionService.getCurrentUser(request);
        model.addAttribute("user", user);
        return Constants.HOME_PAGE;
    }
}
