package com.town.common.config.exception;

import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration;
import org.springframework.boot.autoconfigure.web.reactive.error.DefaultErrorWebExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.result.view.ViewResolver;

@Configuration
@ConditionalOnClass(WebFluxConfigurer.class)
@AutoConfigureBefore(WebFluxAutoConfiguration.class)
@EnableConfigurationProperties({ServerProperties.class, WebProperties.class})
@EnableWebFlux
public class CoreWebFluxConfig {

  private final ServerProperties serverProperties;
  private final ApplicationContext applicationContext;
  private final WebProperties webProperties;
  private final List<ViewResolver> viewResolvers;
  private final ServerCodecConfigurer serverCodecConfigurer;

  @Value("${webflux.worker.threads:100}")
  private String webfluxWorkerThreads;

  public CoreWebFluxConfig(
      ServerProperties serverProperties,
      WebProperties webProperties,
      ObjectProvider<ViewResolver> viewResolversProvider,
      ServerCodecConfigurer serverCodecConfigurer,
      ApplicationContext applicationContext) {
    this.serverProperties = serverProperties;
    this.applicationContext = applicationContext;
    this.webProperties = webProperties;
    this.viewResolvers = viewResolversProvider.orderedStream().collect(Collectors.toList());
    this.serverCodecConfigurer = serverCodecConfigurer;
  }

  @PostConstruct
  public void initNettyThreads() {
    // Sets the number of worker threads for the underlying Netty server
    System.setProperty("reactor.netty.ioWorkerCount", webfluxWorkerThreads);
  }

  @Bean
  @Order(-10) // High priority to override the default handler
  public ErrorWebExceptionHandler errorWebExceptionHandler(ErrorAttributes errorAttributes) {
    DefaultErrorWebExceptionHandler exceptionHandler =
        new CoreDefaultErrorWebExceptionHandler(
            errorAttributes,
            this.webProperties,
            this.serverProperties.getError(),
            this.applicationContext);
    exceptionHandler.setViewResolvers(this.viewResolvers);
    exceptionHandler.setMessageWriters(this.serverCodecConfigurer.getWriters());
    exceptionHandler.setMessageReaders(this.serverCodecConfigurer.getReaders());
    return exceptionHandler;
  }

  @Bean
  @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
  public DefaultErrorAttributes errorAttributes() {
    return new DefaultErrorAttributes();
  }
}
