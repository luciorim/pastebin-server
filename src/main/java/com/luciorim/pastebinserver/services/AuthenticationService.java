package com.luciorim.pastebinserver.services;

import com.luciorim.pastebinserver.dtos.Request.RequestAuthenticateUserDto;
import com.luciorim.pastebinserver.dtos.Request.RequestRegisterUserDto;
import com.luciorim.pastebinserver.dtos.Response.ResponseAuthenticationDto;

public interface AuthenticationService {

    ResponseAuthenticationDto registerUser(RequestRegisterUserDto requestRegisterUserDto);

    ResponseAuthenticationDto authenticate(RequestAuthenticateUserDto requestAuthenticateUserDto);

}
