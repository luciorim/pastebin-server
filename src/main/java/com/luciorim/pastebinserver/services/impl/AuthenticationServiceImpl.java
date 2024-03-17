package com.luciorim.pastebinserver.services.impl;

import com.luciorim.pastebinserver.Exceptions.DbEntityNotFoundException;
import com.luciorim.pastebinserver.dtos.Request.RequestAuthenticateUserDto;
import com.luciorim.pastebinserver.dtos.Request.RequestRegisterUserDto;
import com.luciorim.pastebinserver.dtos.Response.ResponseAuthenticationDto;
import com.luciorim.pastebinserver.entities.Role;
import com.luciorim.pastebinserver.entities.User;
import com.luciorim.pastebinserver.repositories.UserRepository;
import com.luciorim.pastebinserver.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.luciorim.pastebinserver.constants.ValueConstants.ZONE_ID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public ResponseAuthenticationDto registerUser(RequestRegisterUserDto requestRegisterUserDto) {

        userRepository.findUserByEmail(requestRegisterUserDto.getEmail())
                .ifPresent(user -> {throw new IllegalArgumentException("This email already taken!");});

        var user = User.builder()
                .username(requestRegisterUserDto.getUsername())
                .email(requestRegisterUserDto.getEmail())
                .password(passwordEncoder.encode(requestRegisterUserDto.getPassword()))
                .role(Role.USER)
                .createdAt(LocalDateTime.now(ZONE_ID))
                .build();

        log.info("Registered new user: {}", user);

        userRepository.save(user);

        var jwtAccessToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);

        return ResponseAuthenticationDto.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();
    }

    @Override
    public ResponseAuthenticationDto authenticate(RequestAuthenticateUserDto requestAuthenticateUserDto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestAuthenticateUserDto.getEmail(),
                        requestAuthenticateUserDto.getPassword()
                )
        );

        var user = userRepository.findUserByEmail(requestAuthenticateUserDto.getEmail())
                .orElseThrow(() -> new DbEntityNotFoundException(HttpStatus.BAD_REQUEST.getReasonPhrase(), "User doesn't exist"));

        var jwtAccessToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);

        return ResponseAuthenticationDto.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken)
                .build();

    }
}
