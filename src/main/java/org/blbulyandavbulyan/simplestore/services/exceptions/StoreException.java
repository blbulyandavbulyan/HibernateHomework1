package org.blbulyandavbulyan.simplestore.services.exceptions;

/**
 * Данный класс является началом иерархии исключений для сервиса магазина
 */
public abstract class StoreException extends RuntimeException{
    public StoreException() {
    }

    public StoreException(String message) {
        super(message);
    }

    public StoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
