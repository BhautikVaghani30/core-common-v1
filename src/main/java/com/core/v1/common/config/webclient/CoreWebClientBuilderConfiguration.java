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

package com.core.v1.common.config.webclient;

import com.core.v1.common.constant.CoreCommonConstants;
import com.core.v1.common.constant.CoreWebClientBuilderTypeConstants;
import com.core.v1.common.model.CoreWebClientConnectionProperties;
import com.core.v1.common.model.CoreWebClientProperties;
import com.core.v1.common.webclient.filters.CoreHeaderExchange;
import com.core.v1.common.webclient.filters.CoreRequestContextToQueryParamExchange;
import com.core.v1.common.webclient.filters.CoreWebClientRequestResponseLogger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import io.netty.util.CharsetUtil;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

/**
 * Configures and provides prototype-scoped {@link WebClient.Builder} beans for creating {@link
 * WebClient} instances throughout the application.
 *
 * <p>This class defines several pre-configured builders for different use cases (e.g., internal
 * orchestration, external service calls) and sets up default HTTP client settings like timeouts,
 * connection pooling, and logging. Configurations can be customized via application properties.
 */
@Configuration
public class CoreWebClientBuilderConfiguration {

  private final CoreWebClientConnectionProperties coreWebClientConnectionProperties;

  @Value("${core.webclient.config.writetimeout:10000}")
  private Integer writeTimeoutInMillis;

  @Value("${core.webclient.config.readtimeout:10000}")
  private Integer readTimeoutInMillis;

  @Value("${core.webclient.config.loggingresponse.enabled:false}")
  private boolean loggingEnabled;

  @Value("${core.webclient.maxConnections:-1}")
  private Integer maxConnections;

  /**
   * Constructs the configuration with necessary dependencies.
   *
   * @param coreWebClientConnectionProperties The externalized properties for different {@code
   *     WebClient} configurations.
   */
  public CoreWebClientBuilderConfiguration(
      final CoreWebClientConnectionProperties coreWebClientConnectionProperties) {
    this.coreWebClientConnectionProperties = coreWebClientConnectionProperties;
  }

  /**
   * Creates a {@link ConnectionProvider} for the {@link HttpClient}.
   *
   * @param coreWebClientProperties The properties containing connection pool settings.
   * @return A configured {@link ConnectionProvider}.
   */
  protected static ConnectionProvider createConnectionProvider(
      final CoreWebClientProperties coreWebClientProperties) {
    final Integer maxConnectionsLocal =
        Optional.ofNullable(coreWebClientProperties.getMaxConnectionPoolSize())
            .filter(i -> i > -1)
            .orElse(ConnectionProvider.DEFAULT_POOL_MAX_CONNECTIONS);
    return ConnectionProvider.builder(coreWebClientProperties.getConnectionPoolName())
        .maxConnections(maxConnectionsLocal)
        .metrics(true)
        .build();
  }

  /**
   * Creates a default {@link CoreWebClientProperties} instance. This serves as a fallback when no
   * specific configuration is found for a given client name.
   *
   * @return A {@link CoreWebClientProperties} object with default settings.
   */
  public CoreWebClientProperties createDefaultWebClientConfig() {
    final CoreWebClientProperties coreWebClientProperties = new CoreWebClientProperties();
    coreWebClientProperties.setCompressionEnabled(false);
    coreWebClientProperties.setConnectionPoolName(CoreCommonConstants.DEFAULT_CONNECTION_POOL_NAME);
    coreWebClientProperties.setMaxConnectionPoolSize(this.maxConnections);
    coreWebClientProperties.setReadTimeoutMilli(this.readTimeoutInMillis);
    coreWebClientProperties.setWriteTimeoutMilli(this.writeTimeoutInMillis);
    coreWebClientProperties.setLoggingEnabled(this.loggingEnabled);
    return coreWebClientProperties;
  }

  /**
   * Defines a bean that provides the name for the default {@code WebClient} configuration. This
   * name is used as a qualifier to inject the configuration name into builder beans.
   *
   * @return The string "defaultWebClientConfig".
   */
  @Bean(name = CoreCommonConstants.DEFAULT_WEB_CLIENT_CONFIG)
  public String defaultWebClientConfig() {
    return CoreCommonConstants.DEFAULT_WEB_CLIENT_CONFIG;
  }

  /**
   * Provides a prototype-scoped {@link WebClient.Builder} for core services. It includes filters
   * for header propagation and request context-to-query parameter conversion.
   *
   * @param webClientConfigName The name of the configuration to use, typically
   *     "defaultWebClientConfig".
   * @return A new {@link WebClient.Builder} instance.
   */
  @Bean(CoreWebClientBuilderTypeConstants.CORE_WEB_CLIENT_BUILDER)
  @Scope("prototype")
  public WebClient.Builder createCoreBuilder(
      @Qualifier(CoreCommonConstants.DEFAULT_WEB_CLIENT_CONFIG) final String webClientConfigName) {
    return createDefaultBuilder(getWebClientConfigProperties(webClientConfigName))
        .filter(new CoreHeaderExchange())
        .filter(new CoreRequestContextToQueryParamExchange());
  }

  /**
   * Provides a prototype-scoped {@link WebClient.Builder} for orchestration services. It includes a
   * filter for header propagation.
   *
   * @param webClientConfigName The name of the configuration to use, typically
   *     "defaultWebClientConfig".
   * @return A new {@link WebClient.Builder} instance.
   */
  @Bean(CoreWebClientBuilderTypeConstants.ORCH_WEB_CLIENT_BUILDER)
  @Scope("prototype")
  public WebClient.Builder createOrchestrationBuilder(
      @Qualifier(CoreCommonConstants.DEFAULT_WEB_CLIENT_CONFIG) final String webClientConfigName) {
    return createDefaultBuilder(getWebClientConfigProperties(webClientConfigName))
        .filter(new CoreHeaderExchange());
  }

  /**
   * Provides a prototype-scoped {@link WebClient.Builder} for orchestration services with GZIP
   * compression enabled. It includes a filter for header propagation.
   *
   * @param webClientConfigName The name of the configuration to use, typically
   *     "defaultWebClientConfig".
   * @return A new {@link WebClient.Builder} instance with compression enabled.
   */
  @Bean(CoreWebClientBuilderTypeConstants.ORCH_WITH_COMPRESSION_WEB_CLIENT_BUILDER)
  @Scope("prototype")
  public WebClient.Builder createOrchestrationWithCompressionBuilder(
      @Qualifier(CoreCommonConstants.DEFAULT_WEB_CLIENT_CONFIG) final String webClientConfigName) {
    final CoreWebClientProperties webClientProperties =
        getWebClientConfigProperties(webClientConfigName);
    webClientProperties.setCompressionEnabled(true);
    return createDefaultBuilder(webClientProperties).filter(new CoreHeaderExchange());
  }

  /**
   * Provides a prototype-scoped {@link WebClient.Builder} for calling external services. It does
   * not include any custom filters for header or context propagation by default.
   *
   * @param webClientConfigName The name of the configuration to use, typically
   *     "defaultWebClientConfig".
   * @return A new {@link WebClient.Builder} instance.
   */
  @Bean(CoreWebClientBuilderTypeConstants.EXTERNAL_WEB_CLIENT_BUILDER)
  @Scope("prototype")
  public WebClient.Builder createExternalBuilder(
      @Qualifier(CoreCommonConstants.DEFAULT_WEB_CLIENT_CONFIG) final String webClientConfigName) {
    return createDefaultBuilder(getWebClientConfigProperties(webClientConfigName));
  }

  /**
   * Retrieves the {@link CoreWebClientProperties} for a given configuration name. If no specific
   * configuration is found, it returns the default properties.
   *
   * @param webClientConfigName The name of the web client configuration.
   * @return The resolved {@link CoreWebClientProperties}.
   */
  private CoreWebClientProperties getWebClientConfigProperties(final String webClientConfigName) {
    return Optional.ofNullable(coreWebClientConnectionProperties.getProperties())
        .map(
            properties ->
                properties.getOrDefault(webClientConfigName, createDefaultWebClientConfig()))
        .orElseGet(this::createDefaultWebClientConfig);
  }

  /**
   * Creates a base {@link WebClient.Builder} with a configured {@link HttpClient}. The client is
   * set up with connection pooling, timeouts, and logging based on the provided properties.
   *
   * @param properties The properties to configure the underlying {@link HttpClient}.
   * @return A {@link WebClient.Builder} with a configured client connector and logging filter.
   */
  private WebClient.Builder createDefaultBuilder(final CoreWebClientProperties properties) {
    final HttpClient httpClient =
        HttpClient.create(createConnectionProvider(properties))
            .doOnConnected(
                connection ->
                    connection
                        .addHandlerLast(
                            new WriteTimeoutHandler(
                                properties.getWriteTimeoutMilli(), TimeUnit.MILLISECONDS))
                        .addHandlerLast(
                            new ReadTimeoutHandler(
                                properties.getReadTimeoutMilli(), TimeUnit.MILLISECONDS))
                        .addHandlerFirst(new CustomLogger(HttpClient.class)))
            .wiretap(properties.isLoggingEnabled())
            .compress(properties.isCompressionEnabled());

    return WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .filter(new CoreWebClientRequestResponseLogger(properties.isLoggingEnabled()));
  }

  /**
   * A custom Netty {@link LoggingHandler} to format byte buffers as UTF-8 strings for more readable
   * wire logs.
   */
  public static class CustomLogger extends LoggingHandler {
    /**
     * Constructs a new CustomLogger.
     *
     * @param clazz The class to use for the logger name.
     */
    public CustomLogger(final Class<?> clazz) {
      super(clazz);
    }

    /**
     * Formats the log message. If the argument is a {@link ByteBuf}, it is converted to a UTF-8
     * string. Otherwise, the default formatting is used.
     *
     * @param ctx The channel handler context.
     * @param event The logging event name.
     * @param arg The object to log.
     * @return The formatted log string.
     */
    @Override
    protected String format(final ChannelHandlerContext ctx, final String event, final Object arg) {
      if (arg instanceof ByteBuf msg) {
          return msg.toString(CharsetUtil.UTF_8);
      }
      return super.format(ctx, event, arg);
    }
  }
}
