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

package com.town.common.enums;

import lombok.Getter;

/** Represents the status of an API operation. */
@Getter
public enum CoreApiStatus {
  /** Indicates that the API operation was completely successful. */
  SUCCESS("success"),
  /** Indicates that the API operation failed. */
  FAIL("fail"),
  /** Indicates that the API operation was partially successful. */
  PARTIAL("partial");
  private final String value;

  CoreApiStatus(String value) {
    this.value = value;
  }
}
