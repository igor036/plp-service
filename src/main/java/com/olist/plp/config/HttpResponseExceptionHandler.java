package com.olist.plp.config;

import com.olist.plp.exception.AbstractException;
import com.olist.plp.exception.UnexpectedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HttpResponseExceptionHandler extends ResponseEntityExceptionHandler {
    
    private static final String UNEXPECTED_ERROR_MESSAGE = "Unexpected internal error.";

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> unexpectedException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(UNEXPECTED_ERROR_MESSAGE);
    }


    @ExceptionHandler(value = UnexpectedException.class)
    public ResponseEntity<String> unexpectedException(UnexpectedException ex) {
        ex.printStackTrace();
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(UNEXPECTED_ERROR_MESSAGE);
    }

    @ExceptionHandler(value = AbstractException.class)
    public ResponseEntity<String> abstractException(AbstractException ex) {
        ex.printStackTrace();
        return ResponseEntity
            .status(ex.getHttpStatus())
            .body(ex.getMessage());
    }
}
