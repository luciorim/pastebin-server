package com.luciorim.pastebinserver.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class DbEntityNotFoundException extends RuntimeException{

    private final String error;

    private final String message;

}
