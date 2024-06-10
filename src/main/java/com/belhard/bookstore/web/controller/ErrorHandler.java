package com.belhard.bookstore.web.controller;

import com.belhard.bookstore.service.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String appError(Exception e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("message", "Something went wrong....");
        return "errorPage";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String appError(AppException e, Model model) {
        log.error(e.getMessage());
        model.addAttribute("message", "Something went wrong....");
        return "errorPage";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String appError(NotFoundException e, Model model) {
        log.debug(e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "errorPage";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public String appError(NoValidException e, Model model) {
        log.debug(e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "errorPage";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public String appError(NoEditException e, Model model) {
        log.debug(e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "errorPage";
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public String appError(WrongLogginException e, Model model) {
        log.debug(e.getMessage());
        model.addAttribute("message", e.getMessage());
        return "loginFormError";
    }

}
