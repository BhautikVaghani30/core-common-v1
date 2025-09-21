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
import com.core.v1.common.enums.CoreHeaderKeys;
import com.core.v1.common.enums.CoreQueryParamKeys;
import com.core.v1.common.model.CoreRequestContext;
import java.net.URI;
import java.util.EnumSet;
import java.util.Optional;
import org.springframework.lang.Nullable;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/**
 * An {@link ExchangeFilterFunction} that transforms a {@link CoreRequestContext} from the reactive
 * context into query parameters for an outgoing {@link ClientRequest}.
 *
 * <p>This filter is designed to propagate request-specific context (like channel, locale, and store
 * ID) to downstream services via URL query parameters. It retrieves the {@code CoreRequestContext}
 * object stored under the key {@link CoreCommonConstants#REQUEST_CONTEXT_NAME}.
 *
 * <p>If the context is found, this filter will:
 *
 * <ol>
 *   <li>Build a new URI, adding the context's properties as query parameters.
 *   <li>Remove the corresponding original headers (e.g., {@code X-ULTA-CHANNEL}) from the request
 *       to prevent sending redundant information.
 *   <li>Create a new {@code ClientRequest} with the updated URI and headers.
 * </ol>
 *
 * If no context is found, the original request is passed along unmodified.
 */
public final class CoreRequestContextToQueryParamExchange implements ExchangeFilterFunction {

  /**
   * A set of headers that should be removed from the outgoing request because their values are
   * being converted to query parameters.
   */
  private static final EnumSet<CoreHeaderKeys> HEADERS_TO_REMOVE =
      EnumSet.of(
          CoreHeaderKeys.X_ULTA_COUNTRY,
          CoreHeaderKeys.X_ULTA_LOCALE,
          CoreHeaderKeys.X_ULTA_CHANNEL,
          CoreHeaderKeys.X_ULTA_DEVICE,
          CoreHeaderKeys.X_ULTA_ASSOCIATE_ID,
          CoreHeaderKeys.X_ULTA_STORE_ID,
          CoreHeaderKeys.X_ULTA_TRUE_CLIENT_IP,
          CoreHeaderKeys.X_AKAMAI_DEVICE_CHARACTERISTICS);

  /**
   * Applies the filter to the WebClient request chain.
   *
   * <p>This method accesses the reactive context to find a {@link CoreRequestContext}. If found, it
   * transforms the request by adding query parameters and removing original headers before passing
   * it to the next function in the exchange chain.
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
                      .<CoreRequestContext>getOrEmpty(CoreCommonConstants.REQUEST_CONTEXT_NAME)
                      .map(reqContext -> buildRequestWithQueryParams(clientRequest, reqContext))
                      .orElse(clientRequest);

              return exchangeFunction.exchange(requestToExecute);
            });
  }

  /**
   * Builds a new {@link ClientRequest} by adding query parameters from the {@link
   * CoreRequestContext} and removing the original corresponding headers.
   *
   * @param originalRequest The original client request.
   * @param context The core request context containing the values to add as query parameters.
   * @return A new, modified {@link ClientRequest}.
   */
  private ClientRequest buildRequestWithQueryParams(
      final ClientRequest originalRequest, final CoreRequestContext context) {

    final URI newUri =
        UriComponentsBuilder.fromUri(originalRequest.url())
            .queryParam(CoreQueryParamKeys.X_ULTA_COUNTRY.getValue(), context.getCountry())
            .queryParam(
                CoreQueryParamKeys.X_ULTA_LOCALE.getValue(), formatLocale(context.getLocale()))
            .queryParamIfPresent(
                CoreQueryParamKeys.X_ULTA_CHANNEL.getValue(),
                Optional.ofNullable(context.getChannel()))
            .queryParamIfPresent(
                CoreQueryParamKeys.X_ULTA_DEVICE.getValue(),
                Optional.ofNullable(context.getDevice()))
            .queryParamIfPresent(
                CoreQueryParamKeys.X_ULTA_STORE_ID.getValue(),
                Optional.ofNullable(context.getStoreId()))
            .queryParamIfPresent(
                CoreQueryParamKeys.X_ULTA_ASSOCIATE_ID.getValue(),
                Optional.ofNullable(context.getAssociateId()))
            .queryParamIfPresent(
                CoreQueryParamKeys.X_ULTA_TRUE_CLIENT_IP.getValue(),
                Optional.ofNullable(context.getTrueClientIp()))
            .queryParamIfPresent(
                CoreQueryParamKeys.X_AKAMAI_DEVICE_CHARACTERISTICS.getValue(),
                Optional.ofNullable(context.getDeviceCharacteristics()))
            .build()
            .toUri();

    return ClientRequest.from(originalRequest)
        .url(newUri)
        .headers(
            httpHeaders -> HEADERS_TO_REMOVE.forEach(key -> httpHeaders.remove(key.getValue())))
        .build();
  }

  /**
   * Formats a locale string into a standardized format (lowercase, with hyphens as separators).
   *
   * @param locale The raw locale string (e.g., "en_US" or "en US").
   * @return The formatted locale string (e.g., "en-us"), or {@code null} if the input is null.
   */
  @Nullable
  private String formatLocale(@Nullable final String locale) {
    if (locale == null) {
      return null;
    }
    return locale.toLowerCase().replace(" ", "-").replace("_", "-");
  }
}
