package com.olist.plp.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class AbstractException extends RuntimeException {
    
    private static final long serialVersionUID = 8330456383615771098L;
    
    private HttpStatus httpStatus;
    private String message;

    public AbstractException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
