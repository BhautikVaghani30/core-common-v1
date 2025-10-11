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

package com.core.v1.common.handler.venmo;

import static com.core.v1.common.constant.CoreCommonConstants.VENMO;
import static com.core.v1.common.constant.CoreCommonConstants.VENMO_HANDLER;
import static com.core.v1.common.constant.CoreErrorMessageConstants.ERROR;

import com.core.v1.common.handler.CorePaymentMethodHandler;
import com.core.v1.common.i18n.CoreResourceManager;
import com.core.v1.common.model.response.CoreMessage;
import com.core.v1.common.model.response.EligiblePayment;
import com.core.v1.common.providers.CorePropertiesProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * A payment method handler for Venmo transactions. This class implements the {@link
 * CorePaymentMethodHandler} interface to provide specific logic for Venmo payments. It is
 * registered as a Spring bean with the name "venmoHandler".
 */
@Component(VENMO_HANDLER)
@Slf4j
@RequiredArgsConstructor
public class CoreVenmoHandler implements CorePaymentMethodHandler {
  private final CorePropertiesProvider corePropertiesProvider;

  /**
   * {@inheritDoc}
   *
   * <p>For Venmo, this implementation currently returns a static response indicating it is not
   * eligible. This can be extended to include more complex eligibility logic.
   */
  @Override
  public Mono<EligiblePayment> isEligiblePayment() {
    log.debug("CoreVenmoHandler :: isEligiblePayment : called successfully");
    return Mono.just(
        EligiblePayment.builder()
            .payment(VENMO)
            .isEligible(false)
            .message(
                CoreMessage.builder()
                    .code(
                        CoreResourceManager.getCode(
                            CoreResourceManager.getMessageKey(
                                corePropertiesProvider
                                    .getCoreErrorProperties()
                                    .getVenmoPaymentNotEligible())))
                    .type(ERROR)
                    .build())
            .build());
  }

  /**
   * {@inheritDoc}
   *
   * <p>This method is not yet implemented and returns an empty {@link Mono}. It should be
   * implemented to generate any metadata required for Venmo payments.
   */
  @Override
  public Mono<String> generateMetadata() {
    return Mono.empty();
  }

  /**
   * {@inheritDoc}
   *
   * <p>This method is not yet implemented and returns an empty {@link Mono}. It should be
   * implemented to handle the authorization of Venmo payments.
   */
  @Override
  public Mono<String> authorizePayment() {
    return Mono.empty();
  }

  /**
   * {@inheritDoc}
   *
   * <p>This method is not yet implemented and returns an empty {@link Mono}. It should be
   * implemented to handle the voiding of Venmo payments.
   */
  @Override
  public Mono<String> voidPayment() {
    return Mono.empty();
  }

  /**
   * {@inheritDoc}
   *
   * <p>This method is not yet implemented and returns an empty {@link Mono}. It should be
   * implemented to retrieve payment details for an order.
   */
  @Override
  public Mono<String> getPaymentsInOrder() {
    return Mono.empty();
  }

  /**
   * {@inheritDoc}
   *
   * <p>This method is not yet implemented and returns an empty {@link Mono}. It should be
   * implemented to retrieve payment details for a split-tender order.
   */
  @Override
  public Mono<String> getPaymentsInSplitOrder() {
    return Mono.empty();
  }

  /**
   * {@inheritDoc}
   *
   * <p>This method is not yet implemented and returns an empty {@link Mono}. It should be
   * implemented to retrieve payment details for a void in a split-tender order.
   */
  @Override
  public Mono<String> getPaymentForVoidInSplitOrder() {
    return Mono.empty();
  }
}
