package com.v1ok.commons.exception;

/**
 * Created by xinli on 10/04/2017.
 */
public class MetadataRepositoryException extends RuntimeException {

  public MetadataRepositoryException() {
    super();
  }

  public MetadataRepositoryException(String message) {
    super(message);
  }

  public MetadataRepositoryException(String message, Throwable cause) {
    super(message, cause);
  }

  public MetadataRepositoryException(Throwable cause) {
    super(cause);
  }

  protected MetadataRepositoryException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
