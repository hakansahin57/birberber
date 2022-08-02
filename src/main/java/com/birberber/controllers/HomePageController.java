package com.birberber.controllers;

import com.birberber.constants.BirBerberConstants;
import com.birberber.domain.user.User;
import com.birberber.services.session.SessionService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/")
public class HomePageController {

    private static final Logger LOG = Logger.getLogger(HomePageController.class);

    @Resource
    private SessionService sessionService;

    @GetMapping
    public String getHomePage(Model model, HttpServletRequest request) {
        User user = sessionService.getSessionUser(request);
//        model.addAttribute("userName", user.getName());
        return BirBerberConstants.BIRBERBER_HOME_PAGE;
    }
}
