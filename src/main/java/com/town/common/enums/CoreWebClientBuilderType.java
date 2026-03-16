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

package com.town.common.enums;

import com.town.common.constant.CoreWebClientBuilderTypeConstants;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Represents the different types of pre-configured {@link
 * org.springframework.web.reactive.function.client.WebClient.Builder} beans available in the
 * application context.
 *
 * <p>This enum provides a type-safe way to specify which builder to use when creating a {@code
 * WebClient} instance. Each type corresponds to a bean name defined in {@link
 * CoreWebClientBuilderTypeConstants}.
 */
public enum CoreWebClientBuilderType {
  /**
   * Builder for internal core service-to-service communication. Includes filters for header and
   * context propagation.
   */
  CORE(CoreWebClientBuilderTypeConstants.CORE_WEB_CLIENT_BUILDER),
  /** Builder for orchestration services. Includes a filter for header propagation. */
  ORCH(CoreWebClientBuilderTypeConstants.ORCH_WEB_CLIENT_BUILDER),
  /**
   * Builder for orchestration services that require GZIP compression. Includes a filter for header
   * propagation.
   */
  ORCH_WITH_COMPRESSION(CoreWebClientBuilderTypeConstants.ORCH_WITH_COMPRESSION_WEB_CLIENT_BUILDER),
  /**
   * A general-purpose builder for communicating with external, third-party services. It does not
   * include any internal context propagation filters by default.
   */
  EXTERNAL(CoreWebClientBuilderTypeConstants.EXTERNAL_WEB_CLIENT_BUILDER);

  /**
   * A static map for efficient lookup of enum constants by their bean name. This is initialized on
   * class loading.
   */
  private static final Map<String, CoreWebClientBuilderType> NAME_MAP =
      Arrays.stream(values())
          .collect(
              Collectors.toMap(
                  CoreWebClientBuilderType::getWebClientBuilderName, Function.identity()));

  /** The name of the Spring bean for the WebClient.Builder. */
  @Getter private final String webClientBuilderName;

  /**
   * Constructs a CoreWebClientBuilderType enum constant.
   *
   * @param webClientBuilderName The name of the corresponding Spring bean.
   */
  CoreWebClientBuilderType(final String webClientBuilderName) {
    this.webClientBuilderName = webClientBuilderName;
  }

  /**
   * Retrieves a {@code CoreWebClientBuilderType} enum constant from its bean name.
   *
   * @param name The bean name of the builder (e.g., "coreWebClientBuilder").
   * @return An {@link Optional} containing the matching {@code CoreWebClientBuilderType} constant,
   *     or {@link Optional#empty()} if no match is found.
   */
  public static Optional<CoreWebClientBuilderType> fromName(final String name) {
    return Optional.ofNullable(NAME_MAP.get(name));
  }

  /**
   * Returns the string representation of the enum, which is its associated bean name.
   *
   * @return The WebClient Builder bean name.
   */
  @Override
  public String toString() {
    return webClientBuilderName;
  }
}
