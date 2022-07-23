package com.birberber.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        System.out.println("ccc");
//        http
//                .antMatcher("/**")
//                .authorizeRequests()
//                .antMatchers("/", "/login**")
//                .permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .formLogin().loginPage("/login").loginProcessingUrl("/login")
//                .defaultSuccessUrl("/home").failureUrl("/login?message=error").usernameParameter("username")
//                .passwordParameter("password").and().logout().logoutUrl("/perform_logout")
//                .logoutSuccessUrl("/login?message=logout");
        System.out.println("ddd");

    }

//    @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("root")
//                .password("root")
//                .roles("ADM")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
}

