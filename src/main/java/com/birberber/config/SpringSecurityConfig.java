package com.birberber.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Resource
//    private LoginSuccessHandler authenticationSuccessHandler;

//    @Bean
//    AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(new BCryptPasswordEncoder());
//        return provider;
//    }

//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/","/ui/**","/login", "/register")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin().loginProcessingUrl("/login")
//                .loginPage("/login")
//                .successHandler(authenticationSuccessHandler);
//    }
}

