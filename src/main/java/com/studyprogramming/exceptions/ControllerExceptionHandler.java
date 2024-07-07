package com.studyprogramming.exceptions;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public String accessDeniedException(AccessDeniedException ex) {
        return "redirect:/admin/403";
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String noResourceFoundException(NoResourceFoundException ex) {
        return "redirect:/admin/404";
    }

    @ExceptionHandler(Exception.class)
    public String exception(Exception ex) {
        return "redirect:/admin/500";
    }

}
