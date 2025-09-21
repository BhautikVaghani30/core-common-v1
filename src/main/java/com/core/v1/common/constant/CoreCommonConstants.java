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

package com.core.v1.common.constant;

/**
 * A utility class that holds application-wide constants. This class is designed to be a central
 * repository for constants to avoid magic strings and improve maintainability.
 *
 * <p>This class is final and cannot be instantiated.
 */
public final class CoreCommonConstants {

  /** The key used to store HTTP headers in the Reactor context. */
  public static final String HEADERS_CONTEXT_NAME = "httpHeaders";
  /** The key used to store the core request context in the Reactor context. */
  public static final String REQUEST_CONTEXT_NAME = "core-request-context";
  /** The key used to identify the Reactor context operator hook for MDC propagation. */
  public static final String MDC_CONTEXT_REACTOR_KEY =
      "com.core.v1.common.config.log.LoggingConfig";
  /** The name for the default WebClient configuration bean. */
  public static final String DEFAULT_WEB_CLIENT_CONFIG = "defaultWebClientConfig";
  /** The default name for the WebClient connection pool. */
  public static final String DEFAULT_CONNECTION_POOL_NAME = "core-webclient-conn-pool";
  /** The audit log type name. */
  public static final String AUDIT_LOG_TYPE = "auditLogType";
  /** to convert json as string to object. */
  public static final String STRING_MASKER = "...";

  /** Private constructor to prevent instantiation of this utility class. */
  private CoreCommonConstants() {}
}
