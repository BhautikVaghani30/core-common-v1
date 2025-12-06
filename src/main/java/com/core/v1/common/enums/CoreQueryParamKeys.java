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

package com.core.v1.common.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Defines the standard query parameter keys used for downstream service calls.
 *
 * <p>This enum provides a centralized, type-safe way to reference query parameter names, ensuring
 * consistency. Many of these correspond to values propagated from {@link CoreHeaderKeys}.
 */
public enum CoreQueryParamKeys {
  /** The query parameter for the country context (e.g., "US"). */
  X_ULTA_COUNTRY("country"),
  /** The query parameter for the locale (e.g., "en_US"). */
  X_ULTA_LOCALE("locale"),
  /** The query parameter for the selected delivery method. */
  X_ULTA_DELIVERY_METHOD("deliveryMethod"),
  /** The query parameter for the originating channel (e.g., "web", "mobile-app"). */
  X_ULTA_CHANNEL("channel"),
  /** The query parameter for the store associate's ID. */
  X_ULTA_ASSOCIATE_ID("associateId"),
  /** The query parameter for the physical store's ID. */
  X_ULTA_STORE_ID("storeId"),
  /** The query parameter for the client device type (e.g., "mobile", "desktop"). */
  X_ULTA_DEVICE("device"),
  /** The query parameter for the original client IP address. */
  X_ULTA_TRUE_CLIENT_IP("trueClientIp"),
  /** The query parameter for Akamai device characteristics. */
  X_AKAMAI_DEVICE_CHARACTERISTICS("deviceCharacteristics");

  /**
   * A static, case-insensitive map for efficient lookup of enum constants by their string value.
   * This is initialized on class loading.
   */
  private static final Map<String, CoreQueryParamKeys> VALUE_MAP =
      Arrays.stream(values())
          .collect(Collectors.toMap(key -> key.value.toLowerCase(), Function.identity()));

  /** The string representation of the query parameter key. */
  @Getter private final String value;

  /**
   * Constructs a CoreQueryParamKeys enum constant.
   *
   * @param value The string representation of the query parameter key.
   */
  CoreQueryParamKeys(final String value) {
    this.value = value;
  }

  /**
   * Retrieves a {@code CoreQueryParamKeys} enum constant from its string value. The lookup is
   * case-insensitive.
   *
   * @param value The string value of the query parameter key (e.g., "country").
   * @return An {@link Optional} containing the matching {@code CoreQueryParamKeys} constant, or
   *     {@link Optional#empty()} if no match is found.
   */
  public static Optional<CoreQueryParamKeys> fromValue(final String value) {
    if (value == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(VALUE_MAP.get(value.toLowerCase()));
  }
}
