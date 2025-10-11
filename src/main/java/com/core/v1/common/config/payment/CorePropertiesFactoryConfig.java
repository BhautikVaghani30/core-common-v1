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

package com.core.v1.common.config.payment;

import static com.core.v1.common.constant.CoreCommonConstants.CA_PAYMENT_PROPERTIES_BEAN_NAME;
import static com.core.v1.common.constant.CoreCommonConstants.CA_PAYMENT_PROPERTIES_PREFIX;
import static com.core.v1.common.constant.CoreCommonConstants.CORE_MESSAGE_PROPERTIES_BEAN_NAME;
import static com.core.v1.common.constant.CoreCommonConstants.CORE_MESSAGE_PROPERTIES_PREFIX;
import static com.core.v1.common.constant.CoreCommonConstants.CORE_PROPERTIES_FACTORY_BEAN_NAME;

import com.core.v1.common.config.CoreErrorProperties;
import com.core.v1.common.config.CorePaymentProperties;
import com.core.v1.common.constant.CoreCommonConstants;
import com.core.v1.common.factory.CorePropertiesFactory;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for defining and exposing payment-related properties as Spring Beans. This
 * class uses Spring's {@link ConfigurationProperties} to bind external properties to {@link
 * CorePaymentProperties} objects, making them available for injection throughout the application.
 * This setup allows for a flexible and type-safe way to manage configuration for different
 * countries or environments.
 */
@Configuration
@PropertySource("classpath:i18n/messages.properties")
public class CorePropertiesFactoryConfig {

  /**
   * Creates a {@link CorePaymentProperties} bean for Canadian payment configurations. This bean is
   * populated with properties prefixed by {@code paymentproperties.ca}.
   *
   * @return A {@link CorePaymentProperties} object containing Canadian payment properties.
   */
  @Bean(name = CA_PAYMENT_PROPERTIES_BEAN_NAME)
  @ConfigurationProperties(prefix = CA_PAYMENT_PROPERTIES_PREFIX)
  public CorePaymentProperties caPaymentProperties() {
    return new CorePaymentProperties();
  }

  /**
   * Creates a {@link CorePaymentProperties} bean for US payment configurations. This bean is
   * populated with properties prefixed by {@code paymentproperties.us}.
   *
   * @return A {@link CorePaymentProperties} object containing US payment properties.
   */
  @Bean(name = CoreCommonConstants.US_PAYMENT_PROPERTIES_BEAN_NAME)
  @ConfigurationProperties(prefix = CoreCommonConstants.US_PAYMENT_PROPERTIES_PREFIX)
  public CorePaymentProperties usPaymentProperties() {
    return new CorePaymentProperties();
  }

  /**
   * Creates a {@link CoreErrorProperties} bean for core error message configurations. This bean is
   * populated with properties prefixed by {@code core.messages}.
   *
   * @return A {@link CoreErrorProperties} object containing core error message properties.
   */
  @Bean(name = CORE_MESSAGE_PROPERTIES_BEAN_NAME)
  @ConfigurationProperties(prefix = CORE_MESSAGE_PROPERTIES_PREFIX)
  public CoreErrorProperties coreErrorProperties() { // RENAMED for consistency
    return new CoreErrorProperties();
  }

  /**
   * Creates a {@link ServiceLocatorFactoryBean} for the {@link CorePropertiesFactory}. This factory
   * allows for dynamic retrieval of payment property beans based on a given name, which is useful
   * for accessing country-specific configurations at runtime.
   *
   * @return A configured {@link ServiceLocatorFactoryBean} for the {@link CorePropertiesFactory}.
   */
  @Bean(CORE_PROPERTIES_FACTORY_BEAN_NAME)
  public ServiceLocatorFactoryBean serviceLocatorFactoryBean() {
    ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
    serviceLocatorFactoryBean.setServiceLocatorInterface(CorePropertiesFactory.class);
    return serviceLocatorFactoryBean;
  }
}
