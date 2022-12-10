package ru.avdeev.market.exceptions;

import lombok.Getter;

public class ApiException extends RuntimeException{

    @Getter
    protected Integer status = 500;

    public ApiException(String message) {
        super(message);
    }
}
