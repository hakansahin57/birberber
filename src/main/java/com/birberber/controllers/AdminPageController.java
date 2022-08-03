package com.birberber.controllers;

import com.birberber.constants.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    // user tablosunda ROLE_ADMIN tanımlı olması gerekli

    @GetMapping
    public String home(Model model) {
        return Constants.ADMIN_PAGE;
    }

    @GetMapping("/import")
    public String getDataImport(Model model) {
        return Constants.ADMIN_DATA_IMPEX;
    }


}
