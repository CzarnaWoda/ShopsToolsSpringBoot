package me.blackwater.tools.exception;

public class ShopAlreadyExistException extends RuntimeException {
    public ShopAlreadyExistException(String message) {
        super(message);
    }
}
