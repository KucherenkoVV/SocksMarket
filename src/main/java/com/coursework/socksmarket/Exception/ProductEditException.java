package com.coursework.socksmarket.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductEditException extends RuntimeException{
    public ProductEditException(String message) {
        super(message);
    }

    public ProductEditException(String message, Throwable cause) {
        super(message, cause);
    }
}
