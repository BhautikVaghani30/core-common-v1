package com.town.common.config.exception;

import com.town.common.enums.CoreApiStatus;
import com.town.common.model.response.CoreMessage;
import com.town.common.model.response.CoreMeta;
import com.town.common.model.response.CoreResponse;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class CoreDefaultErrorWebExceptionHandler extends DefaultErrorWebExceptionHandler {

  public static final String TOWN_INVALID_CONTEXT_CODE = "E_TOWN_1000_INVALID-CONTEXT";
  public static final String TOWN_GLOBAL_ERROR_CODE = "E_TOWN_GLOBAL_500";

  public CoreDefaultErrorWebExceptionHandler(
      ErrorAttributes errorAttributes,
      WebProperties resourceProperties,
      ErrorProperties errorProperties,
      ApplicationContext applicationContext) {
    super(errorAttributes, resourceProperties.getResources(), errorProperties, applicationContext);
  }

  @Override
  protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
    // Define which error details to extract (message, stacktrace, etc.)
    ErrorAttributeOptions options =
        ErrorAttributeOptions.defaults()
            .including(ErrorAttributeOptions.Include.MESSAGE)
            .including(ErrorAttributeOptions.Include.EXCEPTION);

    return ServerResponse.status(getHttpStatus(getErrorAttributes(request, options)))
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(resolveCustomErrorResponse(request)));
  }

  private Object resolveCustomErrorResponse(ServerRequest request) {
    return Optional.ofNullable(getError(request))
        .map(this::processResponse)
        .orElseGet(this::fallbackResponse);
  }

  private CoreResponse<Object> processResponse(Throwable error) {
    String code =
        (error instanceof CoreRequestContextException)
            ? TOWN_INVALID_CONTEXT_CODE
            : TOWN_GLOBAL_ERROR_CODE;

    return buildResponse(code);
  }

  private CoreResponse<Object> fallbackResponse() {
    return buildResponse(TOWN_GLOBAL_ERROR_CODE);
  }

  private CoreResponse<Object> buildResponse(String errorCode) {
    return CoreResponse.create(
        coreResponse ->
            coreResponse
                .withMeta(
                    CoreMeta.builder()
                        .timestamp(Instant.now().toString())
                        .status(CoreApiStatus.FAIL.getValue())
                        .build())
                .withMessages(
                    Collections.singletonList(
                        CoreMessage.builder()
                            .code(errorCode)
                            .type(CoreMessage.CoreMessageType.ERROR.toString())
                            .build())));
  }
}
