package eu.acme.demo.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class OrderAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(OrderAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String OrderAlreadyExistsHandler( OrderAlreadyExistsException e){
        return e.getMessage();
    }
}
