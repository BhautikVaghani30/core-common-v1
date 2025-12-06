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

package com.core.v1.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a data transfer object for a single message.
 *
 * <p>This class is used to encapsulate message details, such as its type and a unique code. It is
 * designed to be a simple and flexible container for message information.
 */
@Setter
@Getter
@ToString
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreMessage {
  /** The type of the message, indicating its nature (e.g., info, error, warning). */
  private String type;

  /** A unique code identifying the specific message. */
  private String code;

  /** Enumerates the possible types of a {@link CoreMessage}. */
  public enum CoreMessageType {
    /** Informational message. */
    INFO("info"),
    /** Error message. */
    ERROR("error"),
    /** Warning message. */
    WARNING("warning");

    private static final Map<String, CoreMessageType> MESSAGE_TYPE_MAP = new HashMap<>();

    static {
      for (CoreMessageType c : values()) {
        MESSAGE_TYPE_MAP.put(c.value, c);
      }
    }

    /** The string representation of the message type. */
    private final String value;

    CoreMessageType(String value) {
      this.value = value;
    }
  }

  /**
   * Represents a generic response object for core operations. This class is designed to be a
   * standardized container for API responses, including metadata, the actual data payload, and any
   * associated messages.
   *
   * @param <T> the type of the data payload
   */
  @Getter
  @Setter
  @ToString
  @NoArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  @JsonPropertyOrder({"meta", "data", "messages"})
  public static class CoreResponse<T> {
    /** Metadata associated with the response, such as timestamp and status. */
    private CoreMeta meta;

    /** The main data payload of the response. */
    private T data;

    /** A list of messages, such as errors, warnings, or informational messages. */
    private List<CoreMessage> messages;
  }
}
