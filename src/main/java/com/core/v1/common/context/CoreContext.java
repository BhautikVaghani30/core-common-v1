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

package com.core.v1.common.context;

import com.core.v1.common.model.CoreRequestContext;

public final class CoreContext {
  /**
   * The adapter responsible for managing the current CoreRequestContext. This is initialized once
   * and used throughout the application to access and manipulate the context.
   */
  private static final CoreRequestContextAdapter CORE_REQUEST_CONTEXT_ADAPTER =
      new CoreRequestContextAdapter();

  /**
   * Error message for when the adapter is unexpectedly null. While the current implementation
   * initializes it statically, this provides a safeguard for potential future refactorings or
   * unexpected class loading issues.
   */
  private static final String ADAPTER_NOT_NULL = "UltaRequestContextAdapter cannot be null.";

  /**
   * Private constructor to prevent instantiation of this utility class. All methods are static and
   * operate on a shared context.
   */
  public CoreContext() {}

  /**
   * Retrieves the current CoreRequestContext associated with the current thread of execution.
   *
   * @return The current CoreRequestContext.
   * @throws IllegalStateException if the CORE_REQUEST_CONTEXT_ADAPTER is null, indicating a
   *     critical initialization failure.
   */
  public static CoreRequestContext getContext() {
    if (CORE_REQUEST_CONTEXT_ADAPTER == null) {
      throw new IllegalStateException(ADAPTER_NOT_NULL);
    }
    return CoreRequestContextAdapter.getCurrentContext();
  }

  /**
   * Sets the CoreRequestContext for the current thread of execution. This method is used to
   * establish the context at the beginning of a request or operation.
   *
   * @param coreRequestContext The CoreRequestContext to be set.
   * @throws IllegalStateException if the CORE_REQUEST_CONTEXT_ADAPTER is null, indicating a
   *     critical initialization failure.
   */
  public static void setContext(CoreRequestContext coreRequestContext) {
    if (CORE_REQUEST_CONTEXT_ADAPTER == null) {
      throw new IllegalStateException(ADAPTER_NOT_NULL);
    }
    CoreRequestContextAdapter.setContext(coreRequestContext);
  }

  /**
   * Clears the current CoreRequestContext, effectively removing any context associated with the
   * current thread. This is typically called at the end of a request or operation to prevent
   * context leakage.
   *
   * @throws IllegalStateException if the CORE_REQUEST_CONTEXT_ADAPTER is null, indicating a
   *     critical initialization failure.
   */
  public static void clear() {
    if (CORE_REQUEST_CONTEXT_ADAPTER == null) {
      throw new IllegalStateException(ADAPTER_NOT_NULL);
    }
    CoreRequestContextAdapter.clear();
  }

  /**
   * Returns the underlying CoreRequestContextAdapter. This method is primarily for internal use or
   * for advanced scenarios where direct interaction with the adapter is necessary.
   *
   * @return The CoreRequestContextAdapter instance.
   */
  public static CoreRequestContextAdapter getRequestContextAdapter() {
    return CORE_REQUEST_CONTEXT_ADAPTER;
  }
}
