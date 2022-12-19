package ru.avdeev.market.service.product.exceptions;

import ru.avdeev.market.exceptions.ApiException;

public class FileCloudException extends ApiException {

    public FileCloudException(String message) {
        super(message);
    }
}
