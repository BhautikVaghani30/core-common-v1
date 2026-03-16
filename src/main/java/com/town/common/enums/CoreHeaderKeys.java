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

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Getter;

/**
 * Defines the standard HTTP header keys used within the core ecosystem. This enum provides a
 * centralized, type-safe way to reference header names, preventing typos and promoting consistency
 * across microservices.
 */
public enum CoreHeaderKeys {
  /** The unique identifier for the user. */
  X_ULTA_USER_ID("X-ULTA-USER-ID"),
  /** The user's unique identifier from the Auth0 identity provider. */
  X_ULTA_AUTH0_ID("X-ULTA-AUTH0-ID"),
  /** The user's Customer Profile ID. */
  X_ULTA_CP_ID("X-ULTA-CP-ID"),
  /** The identifier for the user's current session. */
  X_ULTA_SESSION_ID("X-ULTA-SESSION-ID"),
  /** The authentication status of the user (e.g., "authenticated", "anonymous"). */
  X_ULTA_AUTH_STATUS("X-ULTA-AUTH-STATUS"),
  /** The country context for the request (e.g., "US"). */
  X_ULTA_COUNTRY("X-ULTA-COUNTRY"),
  /** The locale for the request, specifying language and region (e.g., "en_US"). */
  X_ULTA_LOCALE("X-ULTA-LOCALE"),
  /** The unique identifier for a store associate, used in assisted selling scenarios. */
  X_ULTA_ASSOCIATE_ID("X-ULTA-ASSOCIATE-ID"),
  /** The unique identifier for a physical store, relevant for in-store or BOPIS operations. */
  X_ULTA_STORE_ID("X-ULTA-STORE-ID"),
  /** The channel from which the request originated (e.g., "web", "mobile-app", "store-pos"). */
  X_ULTA_CHANNEL("X-ULTA-CHANNEL"),
  /** The type of device making the request (e.g., "mobile", "desktop", "tablet"). */
  X_ULTA_DEVICE("X-ULTA-DEVICE"),
  /** Akamai-specific header containing device characteristics for security and analytics. */
  X_AKAMAI_DEVICE_CHARACTERISTICS("X-AKAMAI-DEVICE-CHARACTERISTICS"),
  /** The original IP address of the client, typically passed by a load balancer or proxy. */
  X_ULTA_TRUE_CLIENT_IP("X-ULTA-TRUE-CLIENT-IP"),
  /** W3C Trace Context header for distributed tracing, identifying a specific trace and request. */
  TRACE_PARENT("traceparent"),
  /** W3C Trace Context header for vendor-specific trace data. */
  TRACE_STATE("tracestate"),
  /** The member ID for the user's loyalty program account. */
  X_ULTA_LOYALTY_MEMBER_ID("X-ULTA-LOYALTY-MEMBER-ID"),
  /** The status of the user within the loyalty program (e.g., "Platinum", "Diamond"). */
  X_ULTA_LOYALTY_MEMBER_STATUS("X-ULTA-LOYALTY-MEMBER-STATUS");

  /**
   * A static, case-insensitive map for efficient lookup of enum constants by their string value.
   * This is initialized on class loading.
   */
  private static final Map<String, CoreHeaderKeys> VALUE_MAP =
      Arrays.stream(values())
          .collect(Collectors.toMap(key -> key.value.toLowerCase(), Function.identity()));

  /** The string representation of the HTTP header key. */
  @Getter private final String value;

  /**
   * Constructs a CoreHeaderKeys enum constant.
   *
   * @param value The string representation of the header key.
   */
  CoreHeaderKeys(String value) {
    this.value = value;
  }

  /**
   * Retrieves a {@code CoreHeaderKeys} enum constant from its string value. The lookup is
   * case-insensitive.
   *
   * @param value The string value of the header key (e.g., "X-ULTA-USER-ID").
   * @return An {@link Optional} containing the matching {@code CoreHeaderKeys} constant, or {@link
   *     Optional#empty()} if no match is found.
   */
  public static Optional<CoreHeaderKeys> fromValue(String value) {
    if (value == null) {
      return Optional.empty();
    }
    return Optional.ofNullable(VALUE_MAP.get(value.toLowerCase()));
  }
}
