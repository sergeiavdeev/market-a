package ru.avdeev.marketsimpleapi.exceptions;

public class UserExistException extends ApiException {

    public UserExistException(String username) {
        super(String.format("Username %s is already taken", username));
    }
}
