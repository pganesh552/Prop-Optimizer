package com.personal.controller;

import com.personal.entity.UserResponse;
import com.personal.entity.AuthRequest;
import com.personal.model.UserInfo;
import com.personal.service.AuthService;
import com.personal.service.UserInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserInfoService userInfoService;
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public UserController(UserInfoService userInfoService,
                          AuthService authService,
                          AuthenticationManager authenticationManager) {
        this.userInfoService = userInfoService;
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/addNewUser")
    public ResponseEntity<String> addNewUser(@RequestBody UserInfo userInfo) {
        return ResponseEntity.ok(userInfoService.addUser(userInfo));
    }

    @PostMapping("/generate-token")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok(
                    authService.generateToken(authRequest.getUsername(), authRequest.getPassword())
            );
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("/user/profile")
    public ResponseEntity<UserResponse> getUserProfile(Authentication authentication) {
        UserInfo user = userInfoService.getUserByEmail(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(new UserResponse(user));
    }
}
