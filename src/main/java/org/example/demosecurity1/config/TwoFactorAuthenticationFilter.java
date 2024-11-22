package org.example.demosecurity1.config;

import org.example.demosecurity1.service.TwoFactorAuthenticationService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class TwoFactorAuthenticationFilter extends OncePerRequestFilter {

    private final TwoFactorAuthenticationService twoFactorAuthenticationService;

    public TwoFactorAuthenticationFilter(TwoFactorAuthenticationService twoFactorAuthenticationService) {
        this.twoFactorAuthenticationService = twoFactorAuthenticationService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String otp = request.getHeader("X-OTP"); // Get OTP from the request header
            String secretKey = "user-secret-key"; // Fetch the secret key from your database or session for this user

            if (otp == null || !twoFactorAuthenticationService.verifyCode(secretKey, Integer.parseInt(otp))) {
                response.setStatus(401);
                response.getWriter().write("Invalid OTP");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}

