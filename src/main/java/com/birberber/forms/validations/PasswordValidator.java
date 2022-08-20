package com.birberber.forms.validations;

import com.birberber.forms.UpdatePasswordForm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component("passwordValidator")
public class PasswordValidator implements Validator {
    private static final String UPDATE_PWD_INVALID = "updatePwd.pwd.invalid";
    private static final String FIELD_CANNOT_BE_EMPTY = "field.cannot.be.empty";
    private static final String NEW_PWD_CHECK_MUST_BE_SAME = "updatePwd.checkPwd.must.equal.newPwd";


    @Override
    public boolean supports(final Class<?> aClass) {
        return UpdatePasswordForm.class.equals(aClass);
    }

    @Override
    public void validate(final Object object, final Errors errors) {
        final UpdatePasswordForm updatePasswordForm = (UpdatePasswordForm) object;
        final String currPasswd = updatePasswordForm.getCurrentPassword();
        final String newPasswd = updatePasswordForm.getNewPassword();
        final String checkPasswd = updatePasswordForm.getNewPasswordCheck();

        if (StringUtils.isEmpty(currPasswd)) {
            errors.rejectValue("currentPassword", FIELD_CANNOT_BE_EMPTY);
        }

        if (StringUtils.isEmpty(newPasswd)) {
            errors.rejectValue("newPassword", FIELD_CANNOT_BE_EMPTY);
        } else if (StringUtils.length(newPasswd) < 6 || StringUtils.length(newPasswd) > 255) {
            errors.rejectValue("newPassword", UPDATE_PWD_INVALID);
        }

        if (StringUtils.isEmpty(checkPasswd)) {
            errors.rejectValue("newPasswordCheck", FIELD_CANNOT_BE_EMPTY);
        } else if (StringUtils.length(checkPasswd) < 6 || StringUtils.length(checkPasswd) > 255) {
            errors.rejectValue("newPasswordCheck", UPDATE_PWD_INVALID);
        }

        if (StringUtils.equals(checkPasswd, newPasswd)) {
            errors.rejectValue("newPasswordCheck", NEW_PWD_CHECK_MUST_BE_SAME);
        }
    }

}
