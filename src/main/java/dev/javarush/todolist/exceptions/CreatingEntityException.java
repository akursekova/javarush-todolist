package dev.javarush.todolist.exceptions;

public class CreatingEntityException extends RuntimeException {
    public CreatingEntityException(String message, Class clazz) {
        super(message + clazz.getSimpleName());
    }

}
