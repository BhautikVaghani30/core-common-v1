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

import java.util.Map;
import java.util.stream.Collectors;
import org.reactivestreams.Subscription;
import org.slf4j.MDC;
import reactor.core.CoreSubscriber;
import reactor.util.context.Context;

/**
 * A {@link CoreSubscriber} implementation that lifts Reactor {@link Context} values into the SLF4J
 * {@link MDC} (Mapped Diagnostic Context) for logging purposes. This allows context-specific
 * information to be available in logs throughout a reactive stream processing.
 *
 * @param <T> the type of elements signaled to this subscriber.
 */
class CoreMdcContextLifter<T> implements CoreSubscriber<T> {

  private final CoreSubscriber<T> coreSubscriber;

  /**
   * Constructs a new {@code MdcContextLifter}.
   *
   * @param coreSubscriber the delegate {@link CoreSubscriber} to which signals will be forwarded.
   */
  public CoreMdcContextLifter(CoreSubscriber<T> coreSubscriber) {
    this.coreSubscriber = coreSubscriber;
  }

  /**
   * Delegates the subscription to the underlying {@link CoreSubscriber}.
   *
   * @param subscription the {@link Subscription} to delegate.
   */
  @Override
  public void onSubscribe(Subscription subscription) {
    coreSubscriber.onSubscribe(subscription);
  }

  /**
   * Copies the current Reactor {@link Context} to the {@link MDC} before delegating the {@code
   * onNext} signal to the underlying {@link CoreSubscriber}.
   *
   * @param obj the element to be processed.
   */
  @Override
  public void onNext(T obj) {
    copyToMdc(coreSubscriber.currentContext());
    coreSubscriber.onNext(obj);
  }

  /**
   * Delegates the error signal to the underlying {@link CoreSubscriber}.
   *
   * @param t the {@link Throwable} that caused the error.
   */
  @Override
  public void onError(Throwable t) {
    coreSubscriber.onError(t);
  }

  /** Delegates the completion signal to the underlying {@link CoreSubscriber}. */
  @Override
  public void onComplete() {
    coreSubscriber.onComplete();
  }

  /**
   * Returns the current Reactor {@link Context} from the underlying {@link CoreSubscriber}.
   *
   * @return the current {@link Context}.
   */
  @Override
  public Context currentContext() {
    return coreSubscriber.currentContext();
  }

  /**
   * Extension function for the Reactor [Context]. Copies the current context to the MDC, if context
   * is empty clears the MDC. The state of the MDC after calling this method should be the same as
   * the Reactor [Context] state. This operation relies on thread-local access for MDC.
   *
   * @param context the Reactor {@link Context} to copy to the MDC. Keys and values are converted to
   *     {@link String} for MDC storage.
   */
  private void copyToMdc(Context context) {
    if (!context.isEmpty()) {
      Map<String, String> map =
          context.stream()
              .collect(Collectors.toMap(e -> e.getKey().toString(), e -> e.getValue().toString()));
      MDC.setContextMap(map);
    } else {
      MDC.clear();
    }
  }
}
