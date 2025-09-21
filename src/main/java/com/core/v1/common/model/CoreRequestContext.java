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

package com.core.v1.common.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoreRequestContext {

  private String country;
  private String locale;
  private String userId;
  private String sessionId;
  private String authStatus;
  private String channel;
  private String device;
  private String storeId;
  private String deliveryMethod;
  private String associateId;
  private String deviceCharacteristics;
  private String loyaltyMemberId;
  private String loyaltyMemberStatus;
  private String trueClientIp;

  @Builder.Default private Map<String, Object> additionalProperties = new ConcurrentHashMap<>();
}
