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

package com.town.common.model;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Configuration properties for the core WebClient. This class holds settings for connection
 * pooling, timeouts, and other WebClient features.
 */
@Getter
@Setter
public class CoreWebClientProperties {

  /** The name of the connection pool. Defaults to a randomly generated name. */
  private String connectionPoolName =
      "core-webclient-conn-pool-" + RandomStringUtils.randomAlphanumeric(10);

  /** The maximum size of the connection pool. A value of -1 means no limit. */
  private Integer maxConnectionPoolSize = -1;

  /** The write timeout in milliseconds. */
  private Integer writeTimeoutMilli = 10000;

  /** The read timeout in milliseconds. */
  private Integer readTimeoutMilli = 10000;

  /** Whether response compression is enabled. */
  private boolean compressionEnabled = false;

  /** Whether logging of requests and responses is enabled. */
  private boolean loggingEnabled = false;
}
