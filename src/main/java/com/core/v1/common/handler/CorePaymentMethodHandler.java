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

package com.core.v1.common.handler;

import com.core.v1.common.model.response.EligiblePayment;
import reactor.core.publisher.Mono;

/**
 * Defines the contract for payment method handlers. Each implementation of this interface is
 * responsible for the specific logic of a single payment method, such as Credit Card, PayPal, or
 * Venmo. This design promotes a consistent interaction model across all payment types.
 */
public interface CorePaymentMethodHandler {

  /**
   * Determines if the payment method is eligible for the current context. The implementation should
   * contain the business logic to check for eligibility, such as feature flags, user entitlements,
   * or regional availability.
   *
   * @return A {@link Mono} emitting an {@link EligiblePayment} object that indicates whether the
   *     payment method is eligible and provides any relevant messages.
   */
  Mono<EligiblePayment> isEligiblePayment();

  /**
   * Generates payment-specific metadata required for initiating a transaction. This may include
   * generating a client token, setting up a payment session, or other preparatory steps.
   *
   * @return A {@link Mono} emitting a string that contains the generated metadata, often in JSON
   *     format.
   */
  Mono<String> generateMetadata();

  /**
   * Authorizes a payment with the payment provider. This method should handle the complete
   * authorization flow, including sending the request to the provider and processing the response.
   *
   * @return A {@link Mono} emitting a string that represents the result of the authorization, such
   *     as a transaction ID or status.
   */
  Mono<String> authorizePayment();

  /**
   * Voids a previously authorized payment. This is used to cancel a transaction that has not yet
   * been settled.
   *
   * @return A {@link Mono} emitting a string that confirms the void operation, such as a
   *     confirmation number or status.
   */
  Mono<String> voidPayment();

  /**
   * Retrieves payment details for a given order.
   *
   * @return A {@link Mono} emitting a string containing the payment information for the order,
   *     typically in JSON format.
   */
  Mono<String> getPaymentsInOrder();

  /**
   * Retrieves payment details for a split-tender order. This is used when an order is paid for with
   * multiple payment methods.
   *
   * @return A {@link Mono} emitting a string with the payment details for the split order.
   */
  Mono<String> getPaymentsInSplitOrder();

  /**
   * Retrieves payment details for a void operation in a split-tender order.
   *
   * @return A {@link Mono} emitting a string with the relevant payment information for the void.
   */
  Mono<String> getPaymentForVoidInSplitOrder();
}
