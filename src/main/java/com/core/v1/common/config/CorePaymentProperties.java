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

package com.core.v1.common.config;

import com.core.v1.common.providers.CoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Represents a container for payment-related configuration properties. This class is designed to be
 * populated from external configuration sources (e.g., application.yml) and provides a type-safe
 * way to access these properties. The properties are typically specific to a country or region.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"advisors", "declaredPointcut", "classFilter", "methodMatcher"})
public class CorePaymentProperties implements CoreProperties {

  /**
   * A list of all supported payment method identifiers. This property holds the various payment
   * options available (e.g., "CreditCard", "PayPal").
   */
  private List<String> allPaymentMethods;

  /**
   * A list of supported credit card subtypes. This property specifies the different types of credit
   * cards accepted (e.g., "VISA", "MASTERCARD").
   */
  private List<String> creditCardSubType;
}
