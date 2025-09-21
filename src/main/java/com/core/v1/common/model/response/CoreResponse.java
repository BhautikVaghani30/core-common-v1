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
import java.util.List;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a generic and standardized response object for all core API operations.
 *
 * <p>This class is designed to be a consistent container for API responses, including metadata, the
 * actual data payload, and any associated messages (such as errors or warnings).
 *
 * <p>It is constructed using a custom builder pattern.
 *
 * <p><b>Example Usage:</b>
 *
 * <pre>{@code
 * CoreResponse<UserDTO> response = CoreResponse.create(builder -> builder
 *     .withMeta(new CoreMeta("SUCCESS", "2024-01-01T12:00:00Z"))
 *     .withData(userDto)
 *     .withMessages(Collections.emptyList())
 * );
 * }</pre>
 *
 * @param <T> The type of the data payload contained in the response.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"meta", "data", "messages"})
public class CoreResponse<T> {

  /** Metadata associated with the response, such as timestamp and status. */
  private CoreMeta meta;

  /** The main data payload of the response. */
  private T data;

  /** A list of messages, such as errors, warnings, or informational messages. */
  private List<CoreMessage> messages;

  /**
   * Creates a new {@link CoreResponse} instance using a builder pattern.
   *
   * @param builderConsumer A consumer that receives a builder instance to configure the response.
   * @param <T> The type of the data payload.
   * @return A new, configured {@link CoreResponse} instance.
   */
  public static <T> CoreResponse<T> create(final Consumer<CoreResponseBuilder<T>> builderConsumer) {
    return new CoreResponse<T>().build(builderConsumer);
  }

  /**
   * Private helper method to apply the builder logic to this instance.
   *
   * @param builderConsumer The consumer that configures the instance.
   * @return The configured {@link CoreResponse} instance.
   */
  private CoreResponse<T> build(final Consumer<CoreResponseBuilder<T>> builderConsumer) {
    builderConsumer.accept(
        new CoreResponseBuilder<T>() {
          @Override
          public CoreResponseBuilder<T> withMeta(final CoreMeta meta) {
            CoreResponse.this.meta = meta;
            return this;
          }

          @Override
          public CoreResponseBuilder<T> withData(final T data) {
            CoreResponse.this.data = data;
            return this;
          }

          @Override
          public CoreResponseBuilder<T> withMessages(final List<CoreMessage> messageList) {
            // Corrected bug: Use the parameter 'messageList' instead of the field 'messages'.
            CoreResponse.this.messages = messageList;
            return this;
          }
        });

    return this;
  }

  /**
   * A builder interface for constructing a {@link CoreResponse} in a fluent manner.
   *
   * @param <T> The type of the data payload.
   */
  public interface CoreResponseBuilder<T> {
    /**
     * Sets the metadata for the response.
     *
     * @param meta The {@link CoreMeta} object.
     * @return This builder instance for chaining.
     */
    CoreResponseBuilder<T> withMeta(CoreMeta meta);

    /**
     * Sets the data payload for the response.
     *
     * @param data The data payload.
     * @return This builder instance for chaining.
     */
    CoreResponseBuilder<T> withData(T data);

    /**
     * Sets the list of messages for the response.
     *
     * @param messages The list of {@link CoreMessage} objects.
     * @return This builder instance for chaining.
     */
    CoreResponseBuilder<T> withMessages(List<CoreMessage> messages);
  }
}
