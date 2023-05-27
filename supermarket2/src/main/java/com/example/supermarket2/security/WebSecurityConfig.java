package com.example.supermarket2.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.supermarket2.services.UserService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserService userservice;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
   


public SecurityFilterChain filterchain(HttpSecurity http) throws Exception {
    http.userDetailsService(userservice)
    .authorizeRequests()
    .antMatchers("/supermarket", "/supermarket/saveUser", "/supermarket/reset-pass").permitAll()
            .antMatchers("/supermarket/adminHomePage", "/supermarket/addProduct-form", "/supermarket/viewProducts-form", "/admin/viewAllOrders")
            .hasAuthority("Admin")
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/supermarket/login")
            .usernameParameter("email")
            .loginProcessingUrl("/supermarket/check-user")
            .defaultSuccessUrl("/supermarket/homepage")
            .successHandler(new MyAuthenticationSuccessHandler())
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .logoutRequestMatcher(new AntPathRequestMatcher("/supermarket/logout"))
            .logoutSuccessUrl("/supermarket/login");
    return http.build();
}
}