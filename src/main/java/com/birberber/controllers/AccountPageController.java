package com.birberber.controllers;

import com.birberber.constants.Constants;
import com.birberber.domain.user.User;
import com.birberber.forms.UpdatePasswordForm;
import com.birberber.forms.UpdateProfileForm;
import com.birberber.services.session.SessionService;
import com.birberber.services.user.BirBerberUserService;
import org.apache.log4j.Logger;
import org.hibernate.sql.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/my-account")
public class AccountPageController {

    private static final Logger LOG = Logger.getLogger(AccountPageController.class);

    @Resource
    private SessionService sessionService;

    @Resource
    private BirBerberUserService birBerberUserService;

    @GetMapping("/profile")
    public String getProfilePage(Model model, HttpServletRequest request) {
        User user = sessionService.getCurrentUser(request);
        UpdateProfileForm updateProfileForm = new UpdateProfileForm();
        setUserFieldsToProfileForm(user, updateProfileForm);
        model.addAttribute("updateProfileForm", updateProfileForm);
        return Constants.PROFILE_PAGE;
    }

    @PostMapping("/update-profile")
    public String updateProfile(final UpdateProfileForm updateProfileForm) {
        birBerberUserService.updateUser(updateProfileForm);
        return Constants.PROFILE_PAGE;
    }

    @GetMapping("/password")
    public String getPasswordPage(Model model) {
        model.addAttribute(new UpdatePasswordForm());
        return Constants.PASSWORD_PAGE;
    }

    @PostMapping("/update-password")
    public String updatePassword(final UpdatePasswordForm updatePasswordForm, HttpServletRequest request) {
        User currentUser = sessionService.getCurrentUser(request);
        birBerberUserService.updatePassword(currentUser, updatePasswordForm);
        return Constants.PASSWORD_PAGE;
    }

    private void setUserFieldsToProfileForm(User user, UpdateProfileForm updateProfileForm) {
        updateProfileForm.setFirstName(user.getName());
        updateProfileForm.setLastName(user.getLastName());
        updateProfileForm.setEmail(user.getEmail());
        updateProfileForm.setPhoneNumber(user.getPhoneNumber());
        updateProfileForm.setBirthDate(user.getBirthDate());
    }
}
