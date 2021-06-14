package com.v1ok.commons.exception;

/**
 * Created by liubinduo on 2017/5/17.
 */
public class AuthorityException extends RuntimeException {

  public AuthorityException() {
  }

  public AuthorityException(String message) {
    super(message);
  }

  public AuthorityException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthorityException(Throwable cause) {
    super(cause);
  }

  public AuthorityException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
