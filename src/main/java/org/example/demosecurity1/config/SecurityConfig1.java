package org.example.demosecurity1.config;

import org.example.demosecurity1.service.TwoFactorAuthenticationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig1 extends WebSecurityConfigurerAdapter {

    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    public SecurityConfig1(TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/authenticate", "/api/register", "/api/auth/register-two-factor", "/api/auth/**").permitAll() // Allow unauthenticated access to register and login
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new TwoFactorAuthenticationFilter(twoFactorAuthenticationService), UsernamePasswordAuthenticationFilter.class);
    }
}

