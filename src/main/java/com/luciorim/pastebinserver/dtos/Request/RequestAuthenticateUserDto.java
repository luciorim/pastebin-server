package com.luciorim.pastebinserver.dtos.Request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestAuthenticateUserDto {

    @JsonProperty("email")
    @NotNull(message = "Email should not be empty")
    @Email
    private String email;

    @JsonProperty("password")
    @NotNull(message = "Password should not be empty")
    private String password;

}
