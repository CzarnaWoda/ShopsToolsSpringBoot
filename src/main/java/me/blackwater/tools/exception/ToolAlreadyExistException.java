package me.blackwater.tools.exception;

public class ToolAlreadyExistException extends RuntimeException {
  public ToolAlreadyExistException(String message) {
    super(message);
  }
}
