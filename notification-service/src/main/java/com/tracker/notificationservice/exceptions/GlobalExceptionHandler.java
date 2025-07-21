package com.tracker.notificationservice.exceptions;

import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private ErrorDetails getErrorDetails(String message, WebRequest webRequest,
                                         HttpStatus status, Map<String, Object> details) {

        return new ErrorDetails(LocalDateTime.now(), message,
                webRequest.getDescription(false), status.name(), details);
    }

    private ResponseEntity<ErrorDetails> handleException(Exception e,
                                                         WebRequest webRequest,
                                                         HttpStatus status,
                                                         Map<String, Object> details) {

        return new ResponseEntity<>(getErrorDetails(e.getMessage(),
                webRequest, status, details), status);
    }

    @ExceptionHandler(FirebaseMessagingException.class)
    public ResponseEntity<ErrorDetails> handleFirebaseMessagingException(Exception e,
                                                                         WebRequest webRequest) {

        return handleException(e, webRequest, HttpStatus.EXPECTATION_FAILED, Map.of());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<ObjectError> errors = ex.getBindingResult().getAllErrors();

        Map<String, Object> details = new HashMap<>();

        errors.forEach(error -> {
            details.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(getErrorDetails("Invalid argument error", request,
                HttpStatus.BAD_REQUEST, details), status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleOtherExceptions(Exception e, WebRequest webRequest) {
        return handleException(e, webRequest, HttpStatus.INTERNAL_SERVER_ERROR, Map.of());
    }
}
