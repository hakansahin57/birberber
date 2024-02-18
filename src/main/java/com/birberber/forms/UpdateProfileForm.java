package com.birberber.forms;

import java.util.Date;

public class UpdateProfileForm extends RegisterForm {

    private Date birthDate;

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
