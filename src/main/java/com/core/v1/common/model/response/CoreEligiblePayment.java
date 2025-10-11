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

package com.core.v1.common.model.response;

import com.core.v1.common.exception.CorePayment;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Represents the result of an eligibility check for a specific payment method.
 *
 * <p>This DTO is used to convey whether a payment method is available for use, and can optionally
 * include a message providing more context (e.g., why it is ineligible). It implements the {@link
 * CorePayment} interface to provide a consistent way of identifying the payment method.
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreEligiblePayment implements CorePayment {
  /** The name of the payment method (e.g., "creditCard", "payPal"). */
  private String payment;

  /** A boolean flag indicating if the payment method is eligible for use. */
  private boolean isEligible;

  /** An optional message providing additional details, such as the reason for ineligibility. */
  private CoreMessage message;

  @Override
  public String getPaymentMethod() {
    return this.payment;
  }
}
