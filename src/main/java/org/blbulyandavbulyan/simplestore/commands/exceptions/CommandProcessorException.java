package org.blbulyandavbulyan.simplestore.commands.exceptions;

public abstract class CommandProcessorException extends RuntimeException{

    public CommandProcessorException(String message) {
        super(message);
    }

}
