package com.luciorim.pastebinserver.controllers;

import com.luciorim.pastebinserver.dtos.Request.RequestAuthenticateUserDto;
import com.luciorim.pastebinserver.dtos.Request.RequestRegisterUserDto;
import com.luciorim.pastebinserver.dtos.Response.ResponseAuthenticationDto;
import com.luciorim.pastebinserver.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ResponseAuthenticationDto> registerUser(
            @RequestBody @Valid RequestRegisterUserDto requestRegisterUserDto){

        return ResponseEntity
                .ok(authenticationService.registerUser(requestRegisterUserDto));

    }


    @PostMapping("/authenticate")
    public ResponseEntity<ResponseAuthenticationDto> authenticate(
            @RequestBody @Valid RequestAuthenticateUserDto requestAuthenticateUserDto){

        return ResponseEntity
                .ok(authenticationService.authenticate(requestAuthenticateUserDto));

    }

}
