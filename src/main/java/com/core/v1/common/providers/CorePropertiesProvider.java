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

package com.core.v1.common.providers;

import static com.core.v1.common.constant.CoreCommonConstants.CORE_MESSAGE_PROPERTIES_BEAN_NAME;

import com.core.v1.common.config.CoreErrorProperties;
import com.core.v1.common.config.CorePaymentProperties;
import com.core.v1.common.factory.CorePropertiesFactory;
import com.core.v1.common.model.CoreRequestContext;
import com.core.v1.common.utils.CoreBeanUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Component;

/**
 * Provides access to country-specific {@link CorePaymentProperties}. This service acts as a bridge
 * between the application logic and the configuration properties, allowing for dynamic retrieval of
 * properties based on the current request context.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class CorePropertiesProvider {

  private final CorePropertiesFactory corePropertiesFactory;

  /**
   * Retrieves the {@link CorePaymentProperties} for the country specified in the {@link
   * CoreRequestContext}. This method uses a functional approach to safely navigate the request
   * context and retrieve the corresponding configuration bean. If no context or country is
   * provided, or if no configuration bean is found for the country, it returns {@code null}.
   *
   * @param coreRequestContext The context of the current request, which includes country
   *     information.
   * @return The {@link CorePaymentProperties} for the given context, or {@code null} if not found.
   */
  public CorePaymentProperties getCoreProperties(CoreRequestContext coreRequestContext) {
    return Optional.ofNullable(coreRequestContext)
        .map(CoreRequestContext::getCountry)
        .map(CoreBeanUtils::getCountryPropertiesBeanName)
        .flatMap(this::getPaymentConfigSafely)
        .map(CorePaymentProperties.class::cast)
        .orElse(null);
  }

  public CoreErrorProperties getCoreErrorProperties() {
    return Optional.of(CORE_MESSAGE_PROPERTIES_BEAN_NAME)
        .flatMap(this::getPaymentConfigSafely)
        .map(CoreErrorProperties.class::cast)
        .orElse(null);
  }

  /**
   * Safely retrieves a {@link CorePaymentProperties} bean using the {@link CorePropertiesFactory}.
   * It handles the {@link NoSuchBeanDefinitionException} by logging an error and returning an empty
   * {@link Optional}, preventing the exception from propagating.
   *
   * @param beanName The name of the properties bean to retrieve.
   * @return An {@link Optional} containing the {@link CorePaymentProperties} if the bean exists;
   *     otherwise, an empty {@link Optional}.
   */
  private Optional<? extends CoreProperties> getPaymentConfigSafely(String beanName) {
    try {
      return Optional.ofNullable(corePropertiesFactory.getPaymentConfig(beanName));
    } catch (NoSuchBeanDefinitionException e) {
      log.error(
          "CorePropertiesProvider :: getPaymentConfigSafely : No configuration defined for bean name: {}, exception: {}",
          beanName,
          e.getMessage());
      return Optional.empty();
    }
  }
}
