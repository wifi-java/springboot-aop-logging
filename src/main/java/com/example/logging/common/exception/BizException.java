package com.example.logging.common.exception;

import com.example.logging.common.response.Status;

public class BizException extends RuntimeException {
  private final Status status;
  private Exception exception;

  public BizException(Status status) {
    super(status.getMessage());
    this.status = status;
  }

  public BizException(Status status, String message) {
    super(message);
    this.status = status;
  }

  public void setException(Exception exception) {
    this.exception = exception;
  }

  public Exception getException() {
    return exception;
  }

  public Status getStatus() {
    return status;
  }
}
