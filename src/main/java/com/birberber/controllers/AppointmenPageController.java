package com.birberber.controllers;

import com.birberber.constants.BirBerberConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/appointments")
public class AppointmenPageController {

    @GetMapping
    public String getAllAppointments(Model model) {
        return BirBerberConstants.BIRBERBER_APPOINTMENTS_PAGE;
    }

}
