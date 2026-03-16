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
 * Defines constants for different types of WebClient.Builder beans. These constants are used to
 * qualify which WebClient.Builder bean to inject.
 */
public final class CoreWebClientBuilderTypeConstants {

  /** Represents the standard core WebClient.Builder. */
  public static final String CORE_WEB_CLIENT_BUILDER = "coreWebClientBuilder";

  /** Represents the orchestration WebClient.Builder. */
  public static final String ORCH_WEB_CLIENT_BUILDER = "orchWebClientBuilder";

  /** Represents the orchestration WebClient.Builder with compression enabled. */
  public static final String ORCH_WITH_COMPRESSION_WEB_CLIENT_BUILDER =
      "orchWithCompressionWebClientBuilder";

  /** Represents the WebClient.Builder for external communication. */
  public static final String EXTERNAL_WEB_CLIENT_BUILDER = "externalWebClientBuilder";

  private CoreWebClientBuilderTypeConstants() {}
}
