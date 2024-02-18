package com.nikonenko.rideservice.utils;

import com.nikonenko.rideservice.dto.ExceptionResponse;
import com.nikonenko.rideservice.exceptions.BadRequestByRideException;
import com.nikonenko.rideservice.exceptions.ChargeIsNotSuccessException;
import com.nikonenko.rideservice.exceptions.RideIsAlreadyStartedException;
import com.nikonenko.rideservice.exceptions.RideIsNotAcceptedException;
import com.nikonenko.rideservice.exceptions.RideIsNotOpenedException;
import com.nikonenko.rideservice.exceptions.RideIsNotStartedException;
import com.nikonenko.rideservice.exceptions.RideNotFoundException;
import com.nikonenko.rideservice.exceptions.UnknownDriverException;
import com.nikonenko.rideservice.exceptions.WrongPageableParameterException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(RideNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleNotFoundException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(ex.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.add(fieldError.getDefaultMessage());
        }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessages);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, PropertyReferenceException.class,
            MethodArgumentTypeMismatchException.class, RideIsAlreadyStartedException.class,
            RideIsNotOpenedException.class, RideIsNotStartedException.class,
            RideIsNotAcceptedException.class, UnknownDriverException.class,
            ChargeIsNotSuccessException.class, WrongPageableParameterException.class,
            BadRequestByRideException.class})
    public ResponseEntity<ExceptionResponse> handleBadRequestsExceptions(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
