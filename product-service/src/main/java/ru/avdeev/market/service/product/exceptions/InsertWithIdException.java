package ru.avdeev.market.service.product.exceptions;

import ru.avdeev.market.exceptions.ApiException;

public class InsertWithIdException extends ApiException {

    public InsertWithIdException() {
        super("Can not insert entity with selected id.");
    }
}
