package com.unabl4;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.io.IOException;

/*
    This somewhat fixes http 500 errors thrown at the request param validation step,
    caused by 'ConstraintViolationException'
*/

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public void constraintViolationExceptionHandler(HttpServletResponse response) throws IOException {
        response.sendError(400);    // and not 500
    }

}
