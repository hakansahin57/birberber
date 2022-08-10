package com.birberber.controllers;

import com.birberber.constants.Constants;
import com.birberber.domain.address.Country;
import com.birberber.forms.ImportForm;
import com.birberber.repositories.CountryRepository;
import com.birberber.services.imports.ExcelImportService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping(value = "/admin")
public class AdminPageController {

    @Resource(name = "excelImportService")
    private ExcelImportService excelImportService;

    @Resource
    private CountryRepository countryRepository;

    // user tablosunda ROLE_ADMIN tanımlı olması gerekli

    @GetMapping
    public String home(Model model) {
        return Constants.ADMIN_PAGE;
    }

    @GetMapping("/import")
    public String getImportPage(Model model) {
        model.addAttribute(new ImportForm());
        return Constants.ADMIN_DATA_IMPEX;
    }

    @PostMapping("/import")
    public String importFile(ImportForm importForm) {
        List<Country> countryList = excelImportService.excelImport(importForm.getFile());
        countryRepository.saveAll(countryList);
        return Constants.ADMIN_DATA_IMPEX;
    }


}
