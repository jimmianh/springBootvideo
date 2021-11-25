package com.medlink.api.medlinkapi.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class RegistrationRequest {

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String role;
}