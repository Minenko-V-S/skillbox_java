package main.services.exception;

public class EnumValueNotFoundException extends Exception {

    public EnumValueNotFoundException(String message) {
        super(message);
    }
}