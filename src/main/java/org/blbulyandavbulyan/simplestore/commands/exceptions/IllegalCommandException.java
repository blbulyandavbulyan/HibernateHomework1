package org.blbulyandavbulyan.simplestore.commands.exceptions;

/**
 * Данное исключение бросается если передана неверная команда
 */
public class IllegalCommandException extends CommandProcessorException{
    public IllegalCommandException(String message) {
        super(message);
    }
}
