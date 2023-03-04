package learn.springframework.section7recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * To use it in MockMVC Test cases, see the setControllerAdvice() method
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleBadRequest(Exception ex) {
        log.error("Handler: NumberFormatException");
        log.error(ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("400error");
        modelAndView.addObject("exception", ex.getMessage());

        return modelAndView;
    }

}
