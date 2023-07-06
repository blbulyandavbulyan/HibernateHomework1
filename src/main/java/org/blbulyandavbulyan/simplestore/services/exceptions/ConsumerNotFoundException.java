package org.blbulyandavbulyan.simplestore.services.exceptions;

public class ConsumerNotFoundException extends ResourceNotFoundException{
    public ConsumerNotFoundException(String message, Long id) {
        super(message, id);
    }
    public ConsumerNotFoundException(String message, String name) {
        super(message, name);
    }
}
