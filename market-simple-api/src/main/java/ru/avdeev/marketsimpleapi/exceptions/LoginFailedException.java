package ru.avdeev.marketsimpleapi.exceptions;

import org.springframework.http.HttpStatus;

public class LoginFailedException extends ApiException {

    public LoginFailedException(String username) {
        super(String.format("Login filed for user '%s'", username));
        status = HttpStatus.UNAUTHORIZED;
    }
}
