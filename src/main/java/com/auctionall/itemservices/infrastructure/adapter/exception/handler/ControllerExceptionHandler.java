package com.auctionall.itemservices.infrastructure.adapter.exception.handler;

import com.auctionall.itemservices.application.out.ItemNotFound;
import com.auctionall.itemservices.infrastructure.adapter.exception.BusinessException;
import com.auctionall.itemservices.infrastructure.adapter.exception.ExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ItemNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ResponseEntity<ExceptionDetails> notFoundHandler(ItemNotFound ex) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .code(Integer.toString(404))
                .message(Objects.isNull(ex.getMessage()) ? "Not Found" : ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {HttpMessageConversionException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleJsonParseException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(ExceptionDetails.builder()
                .code(Integer.toString(400))
                .message(Objects.isNull(ex.getMessage()) ? "Not Found" : ex.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public final ResponseEntity<Object> handleValidationException(BusinessException businessException) {
       return new ResponseEntity<>(ExceptionDetails.builder()
                .code(Integer.toString(400))
                .message(Objects.isNull(businessException.getMessage()) ? "" : businessException.getMessage())
                .timestamp(LocalDateTime.now())
                .build(), HttpStatus.BAD_REQUEST);
    }

}




