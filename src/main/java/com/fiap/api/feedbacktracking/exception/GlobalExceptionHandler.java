package com.fiap.api.feedbacktracking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FeedbackNotFoundException.class)
    public ResponseEntity<String> handleFeedbackNotFoundException(FeedbackNotFoundException feedbackNotFoundException) {
        return new ResponseEntity<>(feedbackNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeedbackServiceException.class)
    public ResponseEntity<String> handleFeedbackNotFoundException(FeedbackServiceException feedbackServiceException) {
        return new ResponseEntity<>(feedbackServiceException.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(FeedbackCategoryNotFoundException.class)
    public ResponseEntity<String> handleFeedbackNotFoundException(FeedbackCategoryNotFoundException feedbackCategoryNotFoundException) {
        return new ResponseEntity<>(feedbackCategoryNotFoundException.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
