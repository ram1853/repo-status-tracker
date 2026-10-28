package com.repo.registration.common.exception;

import com.repo.registration.common.dto.response.RegistrationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.logging.Logger;

@RestControllerAdvice
public class GlobalExceptionAspect {

    private final Logger LOGGER = Logger.getLogger(GlobalExceptionAspect.class.getName());

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<RegistrationResponse> exception(HttpClientErrorException ex){
        LOGGER.info("GlobalExceptionAspect:exception, ex: "+ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT)
                .body(new RegistrationResponse("Repo registration failed: "+ex.getMessage()));
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<RegistrationResponse> exception(MissingRequestHeaderException ex){
        LOGGER.info("GlobalExceptionAspect:exception, ex: "+ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new RegistrationResponse("Repo registration failed: "+ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RegistrationResponse> exception(Exception ex){
        LOGGER.info("GlobalExceptionAspect:exception, ex: "+ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new RegistrationResponse(ex.getMessage()));
    }
}
