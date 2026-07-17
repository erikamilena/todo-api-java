package com.projects.ToDo.infrastructure.rest.controller;

import com.projects.ToDo.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    // A simple public endpoint to get a token without needing a username or password
    @GetMapping("/get-token")
    public String getMyLearningToken() {
        // We will generate a token for a placeholder user profile named "learningUser"
        String token = jwtUtils.generateToken("learningUser");

        return "SUCCESS! Copy your token below (do not copy the word Bearer):\n\n" + token;
    }
}
