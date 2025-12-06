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

package com.core.v1.common.factory;

import com.core.v1.common.config.CorePaymentProperties;
import com.core.v1.common.providers.CoreProperties;

/**
 * A functional interface for a factory that provides {@link CorePaymentProperties} beans. This
 * factory is designed to be used with a {@link
 * org.springframework.beans.factory.config.ServiceLocatorFactoryBean} to dynamically retrieve
 * payment configuration beans based on their names.
 */
@FunctionalInterface
public interface CorePropertiesFactory {

  /**
   * Retrieves a {@link CorePaymentProperties} bean by its name.
   *
   * @param configBeanName The name of the {@link CorePaymentProperties} bean to retrieve.
   * @return The {@link CorePaymentProperties} instance corresponding to the given bean name.
   */
  CoreProperties getPaymentConfig(String configBeanName);
}
