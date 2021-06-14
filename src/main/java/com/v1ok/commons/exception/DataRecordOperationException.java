package com.v1ok.commons.exception;

/**
 * Created by xinli on 12/05/2017.
 */
public class DataRecordOperationException extends RuntimeException {

  public DataRecordOperationException() {
    super();
  }

  public DataRecordOperationException(String message) {
    super(message);
  }

  public DataRecordOperationException(String message, Throwable cause) {
    super(message, cause);
  }

  public DataRecordOperationException(Throwable cause) {
    super(cause);
  }

  protected DataRecordOperationException(String message, Throwable cause, boolean enableSuppression,
      boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

}
