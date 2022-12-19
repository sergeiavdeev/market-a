package ru.avdeev.market.service.product.exceptions;

import org.springframework.http.HttpStatus;
import ru.avdeev.market.exceptions.ApiException;

import java.util.UUID;

public class EntityNotFondException extends ApiException {

    public EntityNotFondException(UUID id, String entityName) {
        super(String.format("Entity %s with id: %s not found.", entityName, id));
        status = HttpStatus.NOT_FOUND.value();
    }
}
