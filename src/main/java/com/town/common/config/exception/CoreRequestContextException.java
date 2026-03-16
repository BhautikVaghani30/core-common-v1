package com.town.common.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CoreRequestContextException extends CoreException {
  private static final long serialVersionUID = 1L;

  public CoreRequestContextException(String message) {
    super(message);
  }
}
