package com.town.common.config.exception;

public class CoreException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public CoreException(String exception) {
    super(exception);
  }
}
