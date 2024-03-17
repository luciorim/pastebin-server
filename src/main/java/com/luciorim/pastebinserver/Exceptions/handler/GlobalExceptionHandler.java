package com.luciorim.pastebinserver.Exceptions.handler;

import com.luciorim.pastebinserver.Exceptions.DbEntityNotFoundException;
import com.luciorim.pastebinserver.dtos.Response.ResponseErrorDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(DbEntityNotFoundException.class)
    public ResponseEntity<ResponseErrorDto> handlePositionNotFoundException(DbEntityNotFoundException ex) {

        log.error("DbObjectNotFoundException exception: ", ex);

        ResponseErrorDto errorResponse = new ResponseErrorDto(ex.getError(), ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseErrorDto> argumentExceptionHandler(IllegalArgumentException e) {

        log.error("Argument exception: ", e);

        var errorResponse = new ResponseErrorDto(HttpStatus.BAD_REQUEST.toString(), e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorDto> handleValidationErrors(MethodArgumentNotValidException ex) {

        log.error("MethodArgumentNotValidException exception: ", ex);

        String error = ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage();
        ResponseErrorDto errorResponse = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), error, null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ResponseErrorDto> handleIOException(IOException ex) {

        log.error("IOException exception: ", ex);

        ResponseErrorDto errorResponse = new ResponseErrorDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), "Something went wrong...", null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
