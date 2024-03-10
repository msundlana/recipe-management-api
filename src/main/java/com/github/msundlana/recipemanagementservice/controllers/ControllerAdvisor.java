package com.github.msundlana.recipemanagementservice.controllers;

import com.github.msundlana.recipemanagementservice.exception.RecipeNotFoundException;
import com.github.msundlana.recipemanagementservice.models.ErrorResponseDto;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.webjars.NotFoundException;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ControllerAdvisor.class);

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDto> handleValidationException(ConstraintViolationException ex) {
        logger.error("Constraint violation error occurred: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(ErrorResponseDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(RecipeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDto> handleNotFoundException(Exception ex) {
        logger.error("Error occurred while search for recipe with ID: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDto.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex) {
        logger.error("Error occurred while managing recipes: {}", ex.getMessage());
        return ResponseEntity.internalServerError()
                .body(ErrorResponseDto.builder().message(ex.getMessage()).build());
    }
}
