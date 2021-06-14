package com.v1ok.commons.exception;

/**
 * Created by liubinduo on 2017/5/17.
 */
public class RestTemplateException extends RuntimeException {

  public RestTemplateException() {
  }

  public RestTemplateException(String message) {
    super(message);
  }

  public RestTemplateException(String message, Throwable cause) {
    super(message, cause);
  }

  public RestTemplateException(Throwable cause) {
    super(cause);
  }

  public RestTemplateException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
