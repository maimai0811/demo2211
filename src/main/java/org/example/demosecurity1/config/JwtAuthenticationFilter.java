package org.example.demosecurity1.config;


import org.example.demosecurity1.util.JwtTokenUtil;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7); // Remove "Bearer " from the start of the token
            String username = jwtTokenUtil.getUsernameFromToken(token);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // If token is valid, set authentication
                if (jwtTokenUtil.validateToken(token, username)) {
                    // Set user authentication
                    SecurityContextHolder.getContext().setAuthentication(new JwtAuthentication(username));
                }
            }
        }
        filterChain.doFilter(request, response);
    }

//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//    }

    @Override
    public void destroy() {
    }
}

