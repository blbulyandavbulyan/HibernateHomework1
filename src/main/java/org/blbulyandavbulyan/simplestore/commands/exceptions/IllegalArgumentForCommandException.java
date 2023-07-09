package org.blbulyandavbulyan.simplestore.commands.exceptions;

/**
 * Данное исключение бросается в случае если команда написана с неправильным аргументом
 */
public class IllegalArgumentForCommandException extends CommandProcessorException{
    public IllegalArgumentForCommandException(String message) {
        super(message);
    }
}
