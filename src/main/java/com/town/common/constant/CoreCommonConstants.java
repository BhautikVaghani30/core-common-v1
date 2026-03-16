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

package com.town.common.constant;

/**
 * A final utility class that holds application-wide constants. This class is designed to be a
 * central repository for constants to avoid magic strings, improve maintainability, and ensure
 * consistency across the application.
 *
 * <p>This class is final and cannot be instantiated.
 */
public final class CoreCommonConstants {

  // --- Context and Logging Constants ---
  /** The key used to store HTTP headers in the Reactor context. */
  public static final String HEADERS_CONTEXT_NAME = "httpHeaders";

  /** The key used to store the core request context in the Reactor context. */
  public static final String REQUEST_CONTEXT_NAME = "core-request-context";
  /** The key used to identify the Reactor context operator hook for MDC propagation. */
  public static final String MDC_CONTEXT_REACTOR_KEY =
      "com.core.v1.common.config.log.LoggingConfig";

  /** The audit log type name. */
  public static final String AUDIT_LOG_TYPE = "auditLogType";

  // --- WebClient Constants ---
  /** The name for the default WebClient configuration bean. */
  public static final String DEFAULT_WEB_CLIENT_CONFIG = "defaultWebClientConfig";

  /** The default name for the WebClient connection pool. */
  public static final String DEFAULT_CONNECTION_POOL_NAME = "core-webclient-conn-pool";

  // --- Masking Constants ---
  /** A constant used for masking sensitive data in logs or responses. */
  public static final String STRING_MASKER = "...";

  // --- Payment Properties Constants ---
  /** The base prefix for all payment properties. */
  public static final String PAYMENT_PROPERTIES = "paymentproperties";

  /** The prefix for Canadian payment properties. */
  public static final String CA_PAYMENT_PROPERTIES_PREFIX = PAYMENT_PROPERTIES + ".ca";

  /** The bean name for Canadian payment properties. */
  public static final String CA_PAYMENT_PROPERTIES_BEAN_NAME = PAYMENT_PROPERTIES + "-ca";

  /** The prefix for US payment properties. */
  public static final String US_PAYMENT_PROPERTIES_PREFIX = PAYMENT_PROPERTIES + ".us";

  /** The bean name for US payment properties. */
  public static final String US_PAYMENT_PROPERTIES_BEAN_NAME = PAYMENT_PROPERTIES + "-us";

  // --- Bean Naming Suffixes and Separators ---
  /** A separator used in constructing bean names. */
  public static final String HYPHEN = "-";

  /** The suffix for payment configuration beans. */
  public static final String PAYMENT_CONFIG_BEAN_SUFFIX = "Config";

  /** The suffix for payment handler beans. */
  public static final String PAYMENT_HANDLER_BEAN_SUFFIX = "Handler";

  // --- Factory Bean Names ---
  /** The bean name for the {@code CorePropertiesFactory}. */
  public static final String CORE_PROPERTIES_FACTORY_BEAN_NAME = "corePropertiesFactory";

  /** The bean name for the {@code CoreHandlerFactory}. */
  public static final String CORE_HANDLER_FACTORY_BEAN_NAME = "coreHandlerFactory";

  // --- Payment Method Names ---
  /** The payment method name for Venmo. */
  public static final String VENMO = "venmo";

  /** The payment method name for PayPal. */
  public static final String PAYPAL = "paypal";

  /** The payment method name for Credit Card. */
  public static final String CREDIT_CARD = "creditCard";

  // --- Payment Handler Bean Names ---
  /** The bean name for the Venmo payment handler. */
  public static final String VENMO_HANDLER = VENMO + PAYMENT_HANDLER_BEAN_SUFFIX;

  /** The bean name for the PayPal payment handler. */
  public static final String PAYPAL_HANDLER = PAYPAL + PAYMENT_HANDLER_BEAN_SUFFIX;

  /** The bean name for the Credit Card payment handler. */
  public static final String CREDIT_CARD_HANDLER = CREDIT_CARD + PAYMENT_HANDLER_BEAN_SUFFIX;

  /** The bean name for the Credit Card payment handler. */
  public static final String CORE_MESSAGE_PROPERTIES_PREFIX = "core.messages";

  /** The bean name for the Credit Card payment handler. */
  public static final String CORE_MESSAGE_PROPERTIES_BEAN_NAME = "coreMessageProperties";

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws UnsupportedOperationException always, as this class is not meant to be instantiated.
   */
  private CoreCommonConstants() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }
}
