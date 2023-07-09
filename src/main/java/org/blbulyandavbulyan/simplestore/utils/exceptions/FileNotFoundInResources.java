package org.blbulyandavbulyan.simplestore.utils.exceptions;

/**
 * Данное исключение бросается если не был найден файл в ресурсах
 */
public class FileNotFoundInResources extends RuntimeException{
    public FileNotFoundInResources(String message) {
        super(message);
    }
}
