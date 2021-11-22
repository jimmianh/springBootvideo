package com.medlink.api.medlinkapi.controller;


import com.sun.istack.NotNull;
import lombok.Data;

@Data
public class RegistrantionRequest {
    @NotNull
    private String login;

    @NotNull
    private String password;
}
