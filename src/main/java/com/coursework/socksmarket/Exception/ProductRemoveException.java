package com.coursework.socksmarket.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductRemoveException extends RuntimeException{
    public ProductRemoveException(String message) {
        super(message);
    }

    public ProductRemoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
