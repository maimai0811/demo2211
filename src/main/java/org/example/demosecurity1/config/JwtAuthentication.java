package org.example.demosecurity1.config;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

public class JwtAuthentication extends UsernamePasswordAuthenticationToken {

    public JwtAuthentication(String username) {
        super(username, null, AuthorityUtils.NO_AUTHORITIES);
    }
}

