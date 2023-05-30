package com.library.book.exception;

import com.library.book.model.dto.response.JSONResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<JSONResponse> notFoundException(NotFoundException ex) {
        JSONResponse response = new JSONResponse("Not Found", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StorageException.class)
    private ResponseEntity<JSONResponse> storageException(StorageException ex) {
        JSONResponse response = new JSONResponse("Bad Request", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<JSONResponse> badRequestException(BadRequestException ex) {
        JSONResponse response = new JSONResponse("Bad Request", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<JSONResponse> exception(Exception ex) {
        JSONResponse response = new JSONResponse("error", ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
