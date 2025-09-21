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

import com.core.v1.common.constant.CoreCommonConstants;
import java.util.Map;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

/**
 * An {@link ExchangeFilterFunction} that propagates custom headers from the reactive context to
 * outgoing WebClient requests.
 *
 * <p>This filter inspects the Reactor {@code ContextView} for a map of headers stored under the key
 * defined by {@link CoreCommonConstants#HEADERS_CONTEXT_NAME}. If found, these headers are copied
 * to the {@link ClientRequest} before it is sent. This is useful for ensuring that cross-service
 * communication includes necessary context, such as correlation IDs or authentication tokens,
 * without requiring manual header injection at every call site.
 */
public final class CoreHeaderExchange implements ExchangeFilterFunction {

  /**
   * Applies the filter to the WebClient request chain.
   *
   * <p>This method accesses the reactive context to find a map of headers. If the headers are
   * present, it creates a new {@link ClientRequest} with these headers added and passes it to the
   * next function in the exchange chain. If no headers are found in the context, the original
   * request is passed along unmodified.
   *
   * @param clientRequest The current, immutable client request.
   * @param exchangeFunction The function that executes the request, allowing the chain to proceed.
   * @return A {@link Mono} containing the {@link ClientResponse} from the downstream service.
   */
  @Override
  public Mono<ClientResponse> filter(
      final ClientRequest clientRequest, final ExchangeFunction exchangeFunction) {
    return Mono.deferContextual(Mono::just)
        .flatMap(
            contextView -> {
              final ClientRequest requestToExecute =
                  contextView
                      .<Map<String, String>>getOrEmpty(CoreCommonConstants.HEADERS_CONTEXT_NAME)
                      .map(
                          headers ->
                              ClientRequest.from(clientRequest)
                                  .headers(httpHeaders -> headers.forEach(httpHeaders::add))
                                  .build())
                      .orElse(clientRequest);

              return exchangeFunction.exchange(requestToExecute);
            });
  }
}
