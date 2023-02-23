package com.coursework.socksmarket.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CleanFileException extends RuntimeException{
    public CleanFileException(String message) {
        super(message);
    }

    public CleanFileException(String message, Throwable cause) {
        super(message, cause);
    }

}
