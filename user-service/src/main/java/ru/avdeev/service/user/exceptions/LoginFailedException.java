package ru.avdeev.service.user.exceptions;

import org.springframework.http.HttpStatus;
import ru.avdeev.market.exceptions.ApiException;

public class LoginFailedException extends ApiException {

    public LoginFailedException(String username) {
        super(String.format("Login filed for user '%s'", username));
        status = 404;
    }
}
