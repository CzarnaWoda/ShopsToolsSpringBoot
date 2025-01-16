package me.blackwater.tools.exception;

public class ShopNotFoundException extends RuntimeException {
  public ShopNotFoundException(String message) {
    super(message);
  }
}
