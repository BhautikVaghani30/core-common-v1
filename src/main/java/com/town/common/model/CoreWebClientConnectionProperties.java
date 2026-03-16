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

package com.town.common.model;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Use below pattern to set the webclient config properties for "promotion" client
 *
 * <p>core.webclient.properties.promotion.maxConnectionPoolSize=100
 *
 * <p>for "price" client
 *
 * <p>core.webclient.properties.price.maxConnectionPoolSize=200
 */
@ConfigurationProperties(prefix = "core.webclient")
@Getter
@Setter
@Component
public class CoreWebClientConnectionProperties {
  private Map<String, CoreWebClientProperties> properties;
}
