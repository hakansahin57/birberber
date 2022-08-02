package com.birberber.controllers;

import com.birberber.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/stores")
public class StorePageController {

    @GetMapping
    public String getStoreById(Model model) {
        return Constants.APPOINTMENTS_PAGE;
    }

}
