package com.example.springbootshop.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN) /*403*/
public class UserNotLoginException extends RuntimeException {

    public UserNotLoginException() {
        super("Only login users can access");
    }
}
