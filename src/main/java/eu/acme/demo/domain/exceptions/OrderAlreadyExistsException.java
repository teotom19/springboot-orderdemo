package eu.acme.demo.domain.exceptions;

public class OrderAlreadyExistsException extends RuntimeException{

    public OrderAlreadyExistsException (String clientRefCode){
        super("Order with client reference code " + clientRefCode + " already exists.");
    }
}
