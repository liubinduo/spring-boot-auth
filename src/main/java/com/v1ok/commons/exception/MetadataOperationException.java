package com.v1ok.commons.exception;

/**
 * Created by xinli on 05/04/2017.
 */
public class MetadataOperationException extends RuntimeException {

  public MetadataOperationException() {
    super();
  }

  public MetadataOperationException(String message) {
    super(message);
  }

  public MetadataOperationException(String message, Throwable cause) {
    super(message, cause);
  }

  public MetadataOperationException(Throwable cause) {
    super(cause);
  }

  protected MetadataOperationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
