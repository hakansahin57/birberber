package com.birberber.services.user;

import com.birberber.domain.user.User;
import com.birberber.forms.RegisterForm;
import com.birberber.forms.UpdatePasswordForm;
import com.birberber.forms.UpdateProfileForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BirBerberUserService extends UserDetailsService {

    void register(final RegisterForm registerForm);

    void updateUser(UpdateProfileForm updateProfileForm);

    void updatePassword(User user, UpdatePasswordForm updatePasswordForm);
}
