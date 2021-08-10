package eu.acme.demo.domain.exceptions;

import java.util.UUID;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(UUID id){
        super("Order with id: " + id + " doesn't exist.");
    }
}
