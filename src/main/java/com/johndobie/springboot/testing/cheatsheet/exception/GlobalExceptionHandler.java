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
    
    @ExceptionHandler(value = {Exception.class, Throwable.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseModel handleException(Exception e) {
        ErrorModel errorModel = new ErrorModel("server_error", e.getMessage(), e.getClass().getSimpleName());
        return new ErrorResponseModel(ErrorType.SERVER.toString(), List.of(errorModel));
    }
    
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseModel handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ErrorModel> errorModels = processFieldErrors(e);
        return new ErrorResponseModel(ErrorType.VALIDATION.toString(), errorModels);
    }
    
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponseModel handleException(ConstraintViolationException e) {
        List<ErrorModel> validationErrorModels = processConstraintViolations(e);
        return new ErrorResponseModel(ErrorType.VALIDATION.toString(), validationErrorModels);
    }
    
    private List<ErrorModel> processFieldErrors(MethodArgumentNotValidException e) {
        List<ErrorModel> validationErrorModels = new ArrayList<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            ErrorModel validationErrorModel = getErrorModelFromFieldError(fieldError);
            validationErrorModels.add(validationErrorModel);
        }
        return validationErrorModels;
    }
    
    private List<ErrorModel> processConstraintViolations(ConstraintViolationException e) {
        List<ErrorModel> errorModels = new ArrayList<>();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            ErrorModel errorModel = getErrorModelFromConstraintViolation(violation);
            errorModels.add(errorModel);
        }
        return errorModels;
    }
    
    private static ErrorModel getErrorModelFromFieldError(final FieldError fieldError) {
        String code = fieldError.getCode();
        String source = fieldError.getObjectName() + "/" + fieldError.getField();
        String detail = fieldError.getField() + " " + fieldError.getDefaultMessage();
        
        return new ErrorModel(code, detail, source);
    }
    
    private ErrorModel getErrorModelFromConstraintViolation(final ConstraintViolation<?> violation) {
        String code = getCode(violation);
        String source = violation.getPropertyPath().toString();
        String detail = violation.getMessage();
        
        return new ErrorModel(code, detail, source);
    }
    
    private static String getCode(final ConstraintViolation<?> violation) {
        return violation.getConstraintDescriptor()
                        .getAnnotation()
                        .annotationType()
                        .getSimpleName();
    }
    
}
