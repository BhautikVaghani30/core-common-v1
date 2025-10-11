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

import static com.core.v1.common.constant.CoreErrorMessageConstants.TECHNICAL_ERROR;

import com.core.v1.common.exception.CoreServiceException;
import com.core.v1.common.factory.CoreHandlerFactory;
import com.core.v1.common.handler.CorePaymentMethodHandler;
import com.core.v1.common.utils.CoreBeanUtils;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * A provider service that retrieves specific payment method handlers from the application context.
 * This class simplifies obtaining handler instances by using a {@link CoreHandlerFactory} to
 * dynamically resolve them based on a logical payment name.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class CoreHandlersProvider {

  private final CoreHandlerFactory coreHandlerFactory;

  /**
   * Retrieves a {@link CorePaymentMethodHandler} for the given payment method name. This method
   * uses a functional approach to validate the input and handle exceptions, ensuring that a valid
   * handler is returned or a {@link CoreServiceException} is thrown.
   *
   * @param paymentBeanName The logical name of the payment method (e.g., "creditCard").
   * @return The corresponding {@link CorePaymentMethodHandler} instance.
   * @throws CoreServiceException if the paymentBeanName is empty or if no handler is found.
   */
  public CorePaymentMethodHandler getHandler(String paymentBeanName) {
    return Optional.ofNullable(paymentBeanName)
        .filter(name -> !ObjectUtils.isEmpty(name))
        .map(CoreBeanUtils::getHandlerBeanName)
        .map(this::getHandlerSafely)
        .orElseThrow(
            () -> {
              log.error(
                  "CoreHandlersProvider :: getHandler : Payment bean name cannot be null or empty.");
              return new CoreServiceException(TECHNICAL_ERROR);
            });
  }

  /**
   * Safely retrieves a payment handler by its bean name, handling the case where the bean is not
   * found.
   *
   * @param handlerBeanName The name of the handler bean to retrieve.
   * @return The {@link CorePaymentMethodHandler} instance.
   * @throws CoreServiceException if no handler bean is found with the given name.
   */
  private CorePaymentMethodHandler getHandlerSafely(String handlerBeanName) {
    try {
      return coreHandlerFactory.getHandler(handlerBeanName);
    } catch (NoSuchBeanDefinitionException e) {
      log.error(
          "CoreHandlersProvider :: getHandlerSafely : No handler bean found with name: {}, exception: {}",
          handlerBeanName,
          e.getMessage());
      throw new CoreServiceException(TECHNICAL_ERROR);
    }
  }
}
