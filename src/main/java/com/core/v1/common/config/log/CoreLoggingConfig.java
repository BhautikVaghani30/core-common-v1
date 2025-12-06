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

package com.core.v1.common.config.log;

import ch.qos.logback.classic.pattern.TargetLengthBasedClassNameAbbreviator;
import com.core.v1.common.constant.CoreCommonConstants;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Operators;

/**
 * Global logging configuration for the application. This class configures Reactor's context
 * propagation for MDC and provides AOP-based method entry/exit logging for all methods within the
 * `com.core.v1` package.
 */
@Aspect
@Slf4j
@Configuration
public class CoreLoggingConfig {

  /**
   * Abbreviator for class names to keep log messages concise. Configured to abbreviate class names
   * to a maximum length of 36 characters.
   */
  private final TargetLengthBasedClassNameAbbreviator targetLengthBasedClassNameAbbreviator =
      new TargetLengthBasedClassNameAbbreviator(36);

  /**
   * Initializes a Reactor hook to propagate MDC (Mapped Diagnostic Context) across reactive
   * streams. This ensures that logging context (e.g., trace IDs, user IDs) is maintained throughout
   * asynchronous operations.
   */
  @PostConstruct
  private void contextOperatorHook() {
    Hooks.onEachOperator(
        CoreCommonConstants.MDC_CONTEXT_REACTOR_KEY,
        Operators.lift((scannable, coreSubscriber) -> new CoreMdcContextLifter<>(coreSubscriber)));
  }

  /**
   * Cleans up the Reactor hook for MDC propagation when the application context is destroyed. This
   * prevents potential resource leaks or unexpected behavior.
   */
  @PreDestroy
  private void cleanupHook() {
    Hooks.resetOnEachOperator(CoreCommonConstants.MDC_CONTEXT_REACTOR_KEY);
  }

  /**
   * Logs the entry into any method within the `com.core.v1` package. The log message includes the
   * abbreviated class name and the method name.
   *
   * @param joinPoint The join point representing the method execution.
   */
  @Before("execution(* com.core.v1..*.*(..))")
  public void beforeMethod(JoinPoint joinPoint) {
    log.debug(
        "Entered {}-{}",
        targetLengthBasedClassNameAbbreviator.abbreviate(
            joinPoint.getTarget().getClass().getName()),
        joinPoint.getSignature().getName());
  }

  /**
   * Logs the exit from any method within the `com.core.v1` package. The log message includes the
   * abbreviated class name and the method name.
   *
   * @param joinPoint The join point representing the method execution.
   */
  @After("execution(* com.core.v1..*.*(..))")
  public void afterMethod(JoinPoint joinPoint) {
    log.debug(
        "Exited {}-{}",
        targetLengthBasedClassNameAbbreviator.abbreviate(
            joinPoint.getTarget().getClass().getName()),
        joinPoint.getSignature().getName());
  }
}
