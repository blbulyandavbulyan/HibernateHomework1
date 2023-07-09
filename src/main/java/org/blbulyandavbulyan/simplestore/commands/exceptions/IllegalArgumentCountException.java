package org.blbulyandavbulyan.simplestore.commands.exceptions;

/**
 * Данное исключение бросается обработчиком команд если у команды неверное количество аргументов
 */
public class IllegalArgumentCountException extends CommandProcessorException{
    public IllegalArgumentCountException(String msg) {
        super(msg);
    }
}
