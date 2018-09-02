package com.example.springbootshop.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST) /*400*/
public class UserDataIntegrityViolationException extends RuntimeException {

    public UserDataIntegrityViolationException() {
        super("User data integration occured.");
    }
}
