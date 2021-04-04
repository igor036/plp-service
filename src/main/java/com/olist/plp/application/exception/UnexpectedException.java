package com.olist.plp.application.exception;

import org.springframework.http.HttpStatus;

public class UnexpectedException extends AbstractException {

    private static final long serialVersionUID = 1570788277689977748L;
    
    public UnexpectedException(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
