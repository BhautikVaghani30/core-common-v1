/*
 * Copyright 2025 com.core
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.core.v1.common.webclient.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

/**
 * An {@link ExchangeFilterFunction} that logs outgoing {@link ClientRequest} details and the
 * corresponding {@link ClientResponse}.
 *
 * <p>This filter provides visibility into the HTTP requests being made by the WebClient. Logging
 * can be enabled or disabled via a constructor flag, allowing for flexible configuration in
 * different environments.
 *
 * <p>When enabled, it logs:
 *
 * <ul>
 *   <li>The method, URL, and headers of the outgoing request.
 *   <li>The status code and headers of the successful response.
 *   <li>The error message and stack trace if the request fails.
 * </ul>
 */
public final class CoreWebClientRequestResponseLogger implements ExchangeFilterFunction {

  private static final Logger log =
      LoggerFactory.getLogger(CoreWebClientRequestResponseLogger.class);

  private final boolean loggingEnabled;

  /**
   * Constructs the logging filter.
   *
   * @param loggingEnabled A flag to enable or disable logging. If {@code false}, this filter will
   *     have no effect.
   */
  public CoreWebClientRequestResponseLogger(final boolean loggingEnabled) {
    this.loggingEnabled = loggingEnabled;
  }

  /**
   * Applies the logging filter to the WebClient request chain.
   *
   * <p>If logging is enabled, this method logs the request before execution and attaches hooks to
   * log the response upon success or error. If logging is disabled, it immediately passes the
   * request to the next function in the chain.
   *
   * @param clientRequest The current, immutable client request.
   * @param exchangeFunction The function that executes the request, allowing the chain to proceed.
   * @return A {@link Mono} containing the {@link ClientResponse} from the downstream service.
   */
  @Override
  public Mono<ClientResponse> filter(
      final ClientRequest clientRequest, final ExchangeFunction exchangeFunction) {
    // Bypass all logging logic if disabled for better performance.
    if (!loggingEnabled) {
      return exchangeFunction.exchange(clientRequest);
    }

    log.info(
        "Request: method={}, url={}, headers={}",
        clientRequest.method(),
        clientRequest.url(),
        clientRequest.headers());

    return exchangeFunction
        .exchange(clientRequest)
        .doOnSuccess(
            clientResponse -> {
              if (log.isInfoEnabled()) {
                log.info(
                    "Response: url={}, status={}, headers={}",
                    clientRequest.url(),
                    clientResponse.statusCode(),
                    clientResponse.headers().asHttpHeaders());
              }
            })
        .doOnError(
            throwable -> {
              if (log.isErrorEnabled()) {
                log.error(
                    "Error: url={}, message='{}'",
                    clientRequest.url(),
                    throwable.getMessage(),
                    throwable); // Pass throwable for full stack trace
              }
            });
  }
}
