package org.blbulyandavbulyan.simplestore.commands.exceptions;

public class IllegalCommandException extends CommandProcessorException{
    public IllegalCommandException(String message) {
        super(message);
    }
}
