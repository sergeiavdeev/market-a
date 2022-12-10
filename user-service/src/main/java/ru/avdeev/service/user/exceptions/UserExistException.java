package ru.avdeev.service.user.exceptions;

import ru.avdeev.market.exceptions.ApiException;

public class UserExistException extends ApiException {

    public UserExistException(String username) {
        super(String.format("Username %s is already taken", username));
    }
}
