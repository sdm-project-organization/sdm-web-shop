package com.example.springbootshop.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT) /*409*/
public class UserExistException extends RuntimeException {

    public UserExistException() {
        super();
    }
}
