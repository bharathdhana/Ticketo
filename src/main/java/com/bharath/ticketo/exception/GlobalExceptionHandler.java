package com.bharath.ticketo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler extends RuntimeException {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request Error")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .error("Resource Not Found Error")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnAuthorizedException(UnAuthorizedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error("UnAuthorized User Error")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Illegal Argument Error")
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("An UnExcepted Situation Occurred" + e.getMessage())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error("UnAuthorized")
                .message("Invalid Email or Password")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.UNAUTHORIZED.value())
                .error("Authorized Denied")
                .message("Not Authorized for Accessing this Endpoints")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SeatAlreadyBookedException.class)
    public ResponseEntity<ErrorResponse> handleSeatAlreadyBookedException(SeatAlreadyBookedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.CONFLICT.value())
                .error("Seat Already Booked")
                .message("Conflicting! Seats Is Already Booked")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidBookingException.class)
    public ResponseEntity<ErrorResponse> handleInvalidBookingException(InvalidBookingException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .time(LocalDateTime.now().toString())
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .error("Invalid Booking")
                .message("Invalid Booking Exception Occurred")
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
