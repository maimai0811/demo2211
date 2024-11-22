package org.example.demosecurity1.controller;

import org.example.demosecurity1.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    // Simulate a login endpoint where users provide username and password
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // In a real scenario, you'd authenticate the user here.
        // Assume the user is authenticated and generate the JWT token.

        String token = jwtTokenUtil.generateToken(username);
        return token; // In a real application, you'd return a response object containing token and refresh token.
    }

    // Endpoint for refreshing the access token using a refresh token
    @PostMapping("/refresh")
    public String refresh(@RequestParam String refreshToken) {
        // Validate the refresh token and generate a new access token
        String username = jwtTokenUtil.getUsernameFromToken(refreshToken);

        if (jwtTokenUtil.isTokenExpired(refreshToken)) {
            throw new RuntimeException("Refresh Token Expired");
        }

        return jwtTokenUtil.generateToken(username);
    }
}

