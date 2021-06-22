package com.v1ok.commons.exception;

public class OperationException extends Error {

  public OperationException() {
    super();
  }

  public OperationException(String message) {
    super(message);
  }

  public OperationException(String message, Throwable cause) {
    super(message, cause);
  }

  public OperationException(Throwable cause) {
    super(cause);
  }
}
