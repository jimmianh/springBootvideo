package com.medlink.api.medlinkapi.controller;

import com.medlink.api.medlinkapi.config.jwt.JwtProvider;
import com.medlink.api.medlinkapi.entity.UserEntity;
import com.medlink.api.medlinkapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
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
    public String registerUser(@RequestBody @Validated RegistrantionRequest registrantionRequest){
        UserEntity u = new UserEntity();
        u.setPassword(registrantionRequest.getPassword());
        u.setLogin(registrantionRequest.getLogin());
        userService.saveUser(u);
        return "oke";
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request){
        UserEntity userEntity = userService.findByLoginAndPassword(request.getLogin(), request.getPassword());
        String token = jwtProvider.generateToken(userEntity.getLogin());
        return new AuthResponse(token);
    }
}
