package com.birberber.services.user.impl;


import com.birberber.domain.User;
import com.birberber.forms.RegisterForm;
import com.birberber.repositories.UserRepository;
import com.birberber.security.BirBerberUserDetails;
import com.birberber.services.user.BirBerberUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component("birBerberUserService")
public class DefaultBirBerberUserService implements BirBerberUserService {

    @Resource
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return new BirBerberUserDetails(user);
    }

    @Override
    public void register(RegisterForm registerForm) {
        User user = new User();
        setUserFields(user, registerForm);
        userRepo.save(user);
    }

    private void setUserFields(User user, RegisterForm registerForm) {
        user.setFirstName(registerForm.getFirstName());
        user.setLastName(registerForm.getLastName());
        user.setEmail(registerForm.getEmail());
        user.setPassword(registerForm.getPassword());
    }
}