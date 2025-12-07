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

package com.core.v1.common.utils;

import com.core.v1.common.constant.CoreCommonConstants;
import com.core.v1.common.constant.CoreErrorMessageConstants;
import com.core.v1.common.exception.CoreServiceException;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class CoreJsonParserUtils {

  private static final ObjectMapper MAPPER;
  private static final Logger LOG = LoggerFactory.getLogger(CoreJsonParserUtils.class);

  static {
    MAPPER = new ObjectMapper();
    MAPPER.registerModule(new JavaTimeModule());
    MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  private CoreJsonParserUtils() {}

  /**
   * Takes an object as parameter and converts in json string format like
   *
   * <p>
   *
   * @return string
   */
  public static String getJsonFromObject(final Object object) {
    if (null == object) {
      return null;
    }
    try {
      return MAPPER.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOG.error("getJsonFromObject, object :: {}, error :: {}", object, e);
      throw new CoreServiceException(CoreErrorMessageConstants.TECHNICAL_ERROR);
    }
  }

  /**
   * Takes Json String and valueType as parameter and converts the Json string as object of
   * valueType.
   *
   * @return T
   */
  public static <T> T getObjectFromJson(final String jsonString, final Class<T> valueType) {
    if (ObjectUtils.isEmpty(jsonString)) {
      return null;
    }
    try {
      return MAPPER.readValue(jsonString, valueType);
    } catch (JsonProcessingException e) {
      LOG.error("getObjectFromJson, jsonString :: {}, error :: {}", jsonString, e);
      throw new CoreServiceException(e.getMessage());
    }
  }

  /**
   * Takes Json String and valueType as parameter and converts the Json string as object of
   * valueType.
   *
   * @return T
   */
  public static <T> T getObjectFromJson(final String jsonString, final TypeReference<T> valueType) {
    if (ObjectUtils.isEmpty(jsonString)) {
      return null;
    }
    try {
      return MAPPER.readValue(jsonString, valueType);
    } catch (JsonProcessingException e) {
      LOG.error("getObjectFromJson, jsonString :: {}, error :: {}", jsonString, e);
      throw new CoreServiceException(e.getMessage());
    }
  }

  /**
   * Takes Json String and valueType as parameter and converts the Json string as object of
   * valueType.
   *
   * @return T
   */
  public static <T> T readObjectFromResource(final String resourcePath, final Class<T> valueType) {
    final InputStream resourceAsStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
    try {
      return MAPPER.readValue(resourceAsStream, valueType);
    } catch (IOException e) {
      LOG.error("readObjectFromResource, resourcePath :: {}, error :: {}", resourcePath, e);
      return null;
    }
  }

  /**
   * Takes Json String and valueType as parameter and converts the Json string as object of
   * valueType.
   *
   * @return T
   */
  public static <T> T readObjectFromResource(
      final String resourcePath, final TypeReference<T> valueType) {
    final InputStream resourceAsStream =
        Thread.currentThread().getContextClassLoader().getResourceAsStream(resourcePath);
    try {
      return MAPPER.readValue(
          new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8), valueType);
    } catch (IOException e) {
      LOG.error("readObjectFromResource, resourcePath :: {}, error :: {}", resourcePath, e);
      return null;
    }
  }

  public static String convertAndTruncateString(
      final String stringToTruncate, final int truncationLength) {
    if (stringToTruncate != null && stringToTruncate.length() > truncationLength) {
      return stringToTruncate
          .substring(0, truncationLength - 1)
          .concat(CoreCommonConstants.STRING_MASKER);
    }
    return stringToTruncate;
  }

  public static Optional<String> convertAndTruncateRequestAndResponse(
      final Object objectToConvert, int truncationLength) {
    return Optional.ofNullable(objectToConvert)
        .map(
            obj -> {
              String objectAsJson = null;
              try {
                objectAsJson = CoreJsonParserUtils.getJsonFromObject(obj);
              } catch (CoreServiceException e) {
                LOG.error(
                    "convertAndTruncateRequestAndResponse, Failed to convert object to json: {}",
                    e);
              }
              return objectAsJson;
            })
        .map(
            jsonString -> {
              if (jsonString.length() > truncationLength) {
                return jsonString
                    .substring(0, truncationLength - 1)
                    .concat(CoreCommonConstants.STRING_MASKER);
              }
              return jsonString;
            });
  }
}
