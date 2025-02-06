package com.johndobie.springboot.testing.cheatsheet.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
@Configuration
@Slf4j
public class GlobalExceptionHandler {
    
    private static String getCode(final ConstraintViolation<?> violation) {
        return violation.getConstraintDescriptor()
                        .getAnnotation()
                        .annotationType()
                        .getSimpleName();
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseModel handleException(MethodArgumentNotValidException e) {
        List<ErrorModel> errorModels = processFieldErrors(e);
        return new ErrorResponseModel(ErrorType.VALIDATION.toString(), errorModels);
    }
    
    private List<ErrorModel> processFieldErrors(MethodArgumentNotValidException e) {
        List<ErrorModel> validationErrorModels = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult()
                                      .getFieldErrors()) {
            String code = fieldError.getCode();
            String source = fieldError.getObjectName() + "/" + fieldError.getField();
            String detail = fieldError.getField() + " " + fieldError.getDefaultMessage();
            
            ErrorModel validationErrorModel = new ErrorModel(code, detail, source);
            validationErrorModels.add(validationErrorModel);
        }
        return validationErrorModels;
    }
    
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseModel handleException(ConstraintViolationException e) {
        List<ErrorModel> validationErrorModels = processConstraintViolations(e);
        return new ErrorResponseModel(ErrorType.VALIDATION.toString(), validationErrorModels);
    }
    
    private List<ErrorModel> processConstraintViolations(ConstraintViolationException e) {
        List<ErrorModel> errorModels = new ArrayList<ErrorModel>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            
            String code = getCode(violation);
            String source = violation.getPropertyPath().toString();
            String detail = violation.getMessage();
            
            ErrorModel errorModel = new ErrorModel(code, detail, source);
            errorModels.add(errorModel);
        }
        return errorModels;
    }
    
}
