package org.blbulyandavbulyan.simplestore.commands.exceptions;

/**
 * Корневое исключение бросаемое обработчиком команд {@link org.blbulyandavbulyan.simplestore.commands.Processor}
 */
public abstract class CommandProcessorException extends RuntimeException{

    public CommandProcessorException(String message) {
        super(message);
    }

}
