package ru.avdeev.market.cart.exceptions;

import org.springframework.http.HttpStatus;
import ru.avdeev.market.exceptions.ApiException;

public class UnauthorizedException extends ApiException {
    public UnauthorizedException() {
        super("Отказано в доступе");
        status = HttpStatus.UNAUTHORIZED.value();
    }
}
