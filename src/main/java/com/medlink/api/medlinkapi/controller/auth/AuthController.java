package com.medlink.api.medlinkapi.controller.auth;

import com.medlink.api.medlinkapi.config.jwt.JwtProvider;
import com.medlink.api.medlinkapi.controller.request.AuthRequest;
import com.medlink.api.medlinkapi.controller.response.AuthResponse;
import com.medlink.api.medlinkapi.controller.request.RegistrationRequest;
import com.medlink.api.medlinkapi.model.UserEntity;
import com.medlink.api.medlinkapi.service.auth.UserService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;

    @PostMapping("/register")
    public String registerUser(@RequestBody @Valid RegistrationRequest registrationRequest) {
        userService.saveUser(registrationRequest);
        return "OK";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) {
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new AuthResponse(token);
    }
}