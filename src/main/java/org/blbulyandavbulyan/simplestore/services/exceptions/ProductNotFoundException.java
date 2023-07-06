package org.blbulyandavbulyan.simplestore.services.exceptions;

public class ProductNotFoundException extends ResourceNotFoundException{
    public ProductNotFoundException(String message, Long id) {
        super(message, id);
    }
    public ProductNotFoundException(String message, String title) {
        super(message, title);
    }
}
