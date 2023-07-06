package org.blbulyandavbulyan.simplestore.commands.exceptions;

public class IllegalArgumentForCommandException extends CommandProcessorException{
    public IllegalArgumentForCommandException(String message) {
        super(message);
    }
}
