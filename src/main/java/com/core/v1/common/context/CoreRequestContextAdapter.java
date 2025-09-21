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

public class CoreRequestContextAdapter {
  /**
   * Adapter class for managing the {@link CoreRequestContext} within a {@link ThreadLocal}. This
   * ensures that each thread has its own independent context, preventing concurrency issues when
   * multiple requests are processed simultaneously.
   */
  private static final ThreadLocal<CoreRequestContext> copyOnThreadLocal = new ThreadLocal<>();

  /**
   * Retrieves the current {@link CoreRequestContext} associated with the calling thread.
   *
   * @return The {@link CoreRequestContext} for the current thread, or {@code null} if not set.
   */
  public static CoreRequestContext getCurrentContext() {
    return copyOnThreadLocal.get();
  }

  /**
   * Sets the {@link CoreRequestContext} for the current thread. This method should be called at the
   * beginning of a request or a logical unit of work to establish the context for subsequent
   * operations within that thread.
   *
   * @param coreRequestContext The {@link CoreRequestContext} to be set for the current thread.
   */
  public static void setContext(CoreRequestContext coreRequestContext) {
    copyOnThreadLocal.set(coreRequestContext);
  }

  /**
   * Clears the {@link CoreRequestContext} from the current thread. It is crucial to call this
   * method at the end of a request or a logical unit of work to prevent memory leaks and ensure
   * that context from previous operations does not inadvertently affect subsequent operations on
   * the same thread (e.g., in a thread pool).
   */
  public static void clear() {
    copyOnThreadLocal.remove();
  }
}
