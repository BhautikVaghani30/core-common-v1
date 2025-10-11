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

package com.core.v1.common.config.handler;

import static com.core.v1.common.constant.CoreCommonConstants.CORE_HANDLER_FACTORY_BEAN_NAME;

import com.core.v1.common.factory.CoreHandlerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Configuration class that sets up a factory for creating payment method handlers. This class uses
 * a {@link ServiceLocatorFactoryBean} to create a factory that can dynamically provide instances of
 * {@link CoreHandlerFactory}, allowing for loose coupling and easy extension of payment handling
 * logic.
 */
@Component
public class CorePaymentInterfaceHandlerFactoryConfig {

  /**
   * Creates a {@link ServiceLocatorFactoryBean} for the {@link CoreHandlerFactory}. This factory
   * enables the dynamic retrieval of payment handler beans by their names, which is essential for
   * routing payment requests to the correct handler at runtime.
   *
   * @return A configured {@link FactoryBean} for the {@link CoreHandlerFactory}.
   */
  @Bean(CORE_HANDLER_FACTORY_BEAN_NAME)
  public FactoryBean<?> serviceLocatorFactoryBean() {
    ServiceLocatorFactoryBean serviceLocatorFactoryBean = new ServiceLocatorFactoryBean();
    serviceLocatorFactoryBean.setServiceLocatorInterface(CoreHandlerFactory.class);
    return serviceLocatorFactoryBean;
  }
}
