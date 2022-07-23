package com.birberber.services.user;

import com.birberber.forms.RegisterForm;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface BirBerberUserService extends UserDetailsService {

    void register(final RegisterForm registerForm);
}
