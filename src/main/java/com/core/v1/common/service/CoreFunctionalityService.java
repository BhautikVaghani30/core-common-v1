package com.core.v1.common.service;

import com.core.v1.common.config.CorePaymentProperties;
import com.core.v1.common.enums.CoreCounty;
import com.core.v1.common.handler.CorePaymentMethodHandler;
import com.core.v1.common.model.CoreAvailablePayments;
import com.core.v1.common.model.CoreRequestContext;
import com.core.v1.common.providers.CoreHandlersProvider;
import com.core.v1.common.providers.CorePropertiesProvider;
import com.core.v1.common.utils.CoreJsonParserUtils;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * A service class responsible for orchestrating core business functionalities, such as processing
 * payments and retrieving configurations.
 *
 * <p>This service acts as a high-level coordinator that uses various providers to fetch
 * configurations and payment handlers, and then executes tasks based on them.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CoreFunctionalityService {

  /** Provides access to business-specific payment configurations based on site context. */
  private final CorePropertiesProvider corePropertiesProvider;

  /** Provides access to registered payment method handlers (e.g., Credit Card, PayPal). */
  private final CoreHandlersProvider coreHandlersProvider;

  /**
   * Fetches and logs the business payment configuration for a given request context. This method is
   * intended for demonstrating or debugging configuration retrieval.
   */
  public void executeBusinessPaymentTask() {
    final CoreRequestContext requestContext =
        CoreRequestContext.builder().country(CoreCounty.US).build();

    final CorePaymentProperties config = corePropertiesProvider.getCoreProperties(requestContext);

    // CoreSitePaymentProperties is a data-centric @ConfigurationProperties class,
    // which is generally safe to serialize.
    log.debug("Business Config : {}", CoreJsonParserUtils.getJsonFromObject(config));
  }

  /**
   * Fetches the configured payment handlers and logs their class names.
   *
   * <p>This method demonstrates how to safely log information about Spring beans without causing
   * serialization errors. Instead of logging the entire handler objects (which are Spring AOP
   * proxies), it extracts and logs only their simple class names.
   */
  public Mono<CoreAvailablePayments> executePaymentHandlerTask() {
    final List<String> paymentMethodNames = List.of("creditCard", "venmo", "paypal");

    final List<CorePaymentMethodHandler> handlers =
        paymentMethodNames.stream()
            .map(coreHandlersProvider::getHandler)
            .filter(Objects::nonNull)
            .toList();

    return Flux.fromIterable(handlers)
        .flatMap(CorePaymentMethodHandler::isEligiblePayment)
        .collectList()
        .map(result -> CoreAvailablePayments.builder().eligiblePayments(result).build());
  }
}
