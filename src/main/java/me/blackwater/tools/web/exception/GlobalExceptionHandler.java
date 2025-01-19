package me.blackwater.tools.web.exception;


import me.blackwater.tools.exception.ShopAlreadyExistException;
import me.blackwater.tools.exception.ShopNotFoundException;
import me.blackwater.tools.exception.ToolAlreadyExistException;
import me.blackwater.tools.exception.ToolNotFoundException;
import me.blackwater.tools.util.HttpResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ShopNotFoundException.class)
    public ResponseEntity<HttpResponse> handleShopNotFoundException(ShopNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND)
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                        .status(NOT_FOUND)
                        .statusCode(NOT_FOUND.value())
                        .reason("Shop not found")
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ShopAlreadyExistException.class)
    public ResponseEntity<HttpResponse> handleShopAlreadyExistException(ShopAlreadyExistException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .reason("Shop already exist")
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(ToolNotFoundException.class)
    public ResponseEntity<HttpResponse> handleToolNotFoundException(ToolNotFoundException e) {
        return ResponseEntity.status(NOT_FOUND)
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                        .status(NOT_FOUND)
                        .statusCode(NOT_FOUND.value())
                        .reason("Tool not found")
                        .message(e.getMessage())
                        .build());
    }
    @ExceptionHandler(ToolAlreadyExistException.class)
    public ResponseEntity<HttpResponse> handleToolAlreadyExistException(ToolAlreadyExistException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(HttpResponse.builder()
                        .timeStamp(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")))
                        .status(BAD_REQUEST)
                        .statusCode(BAD_REQUEST.value())
                        .reason("Tool already exist")
                        .message(e.getMessage())
                        .build());
    }
}
