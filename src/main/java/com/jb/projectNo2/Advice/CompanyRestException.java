package com.jb.projectNo2.Advice;

import com.jb.projectNo2.Exceptions.CompanyUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class CompanyRestException {
    @ExceptionHandler(value = {CompanyUserException.class})
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public ErrorDetail handleException(Exception error){
        return new ErrorDetail("Something went wrong --- COMPANY EDITION", error.getMessage());
    }
}
