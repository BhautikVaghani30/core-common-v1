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

package com.town.common.utils;

import com.town.common.constant.CoreCommonConstants;
import com.town.common.enums.CoreCounty;

/**
 * A final utility class for generating conventional bean names used throughout the application.
 * This class provides static methods to construct consistent bean names for payment properties,
 * configurations, and handlers. Centralizing this logic reduces the risk of typos and ensures
 * naming consistency across the application.
 */
public final class CoreBeanUtils {

  /**
   * Private constructor to prevent instantiation of this utility class.
   *
   * @throws UnsupportedOperationException always, as this class is not meant to be instantiated.
   */
  private CoreBeanUtils() {
    throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
  }

  /**
   * Generates the bean name for country-specific payment properties. The name is constructed by
   * combining a base name, a hyphen, and the country code.
   *
   * @param country The {@link CoreCounty} for which to get the bean name.
   * @return The bean name, e.g., "paymentproperties-us" for the US.
   */
  public static String getCountryPropertiesBeanName(CoreCounty country) {
    return CoreCommonConstants.PAYMENT_PROPERTIES
        .concat(CoreCommonConstants.HYPHEN)
        .concat(country.getValues());
  }

  /**
   * Generates the bean name for a payment method's configuration. The name is created by appending
   * a standard suffix to the payment method name.
   *
   * @param paymentMethod The name of the payment method (e.g., "creditCard").
   * @return The configuration bean name, e.g., "creditCardConfig".
   */
  public static String getPaymentConfigBeanName(String paymentMethod) {
    return paymentMethod.concat(CoreCommonConstants.PAYMENT_CONFIG_BEAN_SUFFIX);
  }

  /**
   * Generates the bean name for a payment method's handler. This convention assumes the handler
   * bean is named after the payment method, followed by a standard suffix.
   *
   * @param paymentBeanName The base name of the payment method (e.g., "creditCard").
   * @return The handler bean name, e.g., "creditCardHandler".
   */
  public static String getHandlerBeanName(String paymentBeanName) {
    return paymentBeanName.concat(CoreCommonConstants.PAYMENT_HANDLER_BEAN_SUFFIX);
  }
}
