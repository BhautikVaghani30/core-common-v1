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

package com.core.v1.common.webclient;

import com.core.v1.common.config.webclient.CoreWebClientBuilderConfiguration;
import com.core.v1.common.enums.CoreWebClientBuilderType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * A service that acts as a factory for creating pre-configured {@link WebClient.Builder} instances.
 *
 * <p>This service provides a centralized and simplified way to obtain {@code WebClient.Builder}
 * objects for different use cases. It delegates the creation logic to the {@link
 * CoreWebClientBuilderConfiguration}, which is responsible for defining and configuring the
 * prototype-scoped builder beans.
 *
 * <p><b>Note:</b> The {@code clientName} parameter in the creation methods is used to look up
 * specific connection properties (like timeouts and pool sizes) from the application configuration.
 * This allows for per-client customization of the underlying HTTP client.
 */
@Service
public class CoreWebClientBuilderService implements CoreWebClientBuilderFactory {

  private final CoreWebClientBuilderConfiguration coreWebClientBuilderConfiguration;

  /**
   * Constructs the service with the necessary configuration dependency.
   *
   * @param coreWebClientBuilderConfiguration The Spring-managed configuration class that provides
   *     the builder creation logic. It is injected here to be used as a factory.
   */
  public CoreWebClientBuilderService(
      final CoreWebClientBuilderConfiguration coreWebClientBuilderConfiguration) {
    this.coreWebClientBuilderConfiguration = coreWebClientBuilderConfiguration;
  }

  /**
   * Retrieves a new, prototype-scoped {@link WebClient.Builder} instance based on a specific type.
   *
   * <p>This method is a convenience wrapper that maps an enum type to the corresponding builder
   * creation method.
   *
   * @param webClientType The enum constant representing the desired builder type (e.g., {@code
   *     CORE}, {@code EXTERNAL}).
   * @return A new, pre-configured {@link WebClient.Builder} instance with default properties.
   */
  @Override
  public WebClient.Builder getWebClientBuilder(final CoreWebClientBuilderType webClientType) {
    switch (webClientType) {
      case CORE:
        return createCoreWebClient(coreWebClientBuilderConfiguration.defaultWebClientConfig());
      case ORCH:
        return createOrchWebClient(coreWebClientBuilderConfiguration.defaultWebClientConfig());
      case ORCH_WITH_COMPRESSION:
        return createOrchWithCompressionWebClient(
            coreWebClientBuilderConfiguration.defaultWebClientConfig());
      case EXTERNAL:
        return createExternalWebClient(coreWebClientBuilderConfiguration.defaultWebClientConfig());
      default:
        // This case should be unreachable if the enum is exhaustive.
        throw new IllegalArgumentException("Unsupported WebClientBuilderType: " + webClientType);
    }
  }

  /**
   * Creates a new {@link WebClient.Builder} for core services, customized by client name.
   *
   * @param clientName The name of the client configuration to use for properties like timeouts and
   *     connection pooling.
   * @return A new {@link WebClient.Builder} instance for core services.
   */
  public WebClient.Builder createCoreWebClient(final String clientName) {
    return coreWebClientBuilderConfiguration.createCoreBuilder(clientName);
  }

  /**
   * Creates a new {@link WebClient.Builder} for orchestration services, customized by client name.
   *
   * @param clientName The name of the client configuration to use for properties like timeouts and
   *     connection pooling.
   * @return A new {@link WebClient.Builder} instance for orchestration services.
   */
  public WebClient.Builder createOrchWebClient(final String clientName) {
    return coreWebClientBuilderConfiguration.createOrchestrationBuilder(clientName);
  }

  /**
   * Creates a new {@link WebClient.Builder} for external services, customized by client name.
   *
   * @param clientName The name of the client configuration to use for properties like timeouts and
   *     connection pooling.
   * @return A new {@link WebClient.Builder} instance for external services.
   */
  public WebClient.Builder createExternalWebClient(final String clientName) {
    return coreWebClientBuilderConfiguration.createExternalBuilder(clientName);
  }

  /**
   * Creates a new {@link WebClient.Builder} for orchestration with compression, customized by
   * client name.
   *
   * @param clientName The name of the client configuration to use for properties like timeouts and
   *     connection pooling.
   * @return A new {@link WebClient.Builder} instance for compressed orchestration services.
   */
  public WebClient.Builder createOrchWithCompressionWebClient(final String clientName) {
    return coreWebClientBuilderConfiguration.createOrchestrationWithCompressionBuilder(clientName);
  }
}
