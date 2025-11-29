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

package com.core.v1.common.service;

import com.core.v1.common.constant.CoreCommonConstants;
import com.core.v1.common.exception.model.CoreAuditData;
import java.util.List;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class CoreAuditService {

  @Value("${core.audit.error.messages}")
  private List<String> errorMessages;

  /**
   * Method is used to record audit trails of payments logs and to identify payment troubleshooting.
   */
  public void printErrorLog(CoreAuditData auditData) {
    MDC.put(CoreCommonConstants.AUDIT_LOG_TYPE, auditData.getAuditLog());
    log.info(auditData.toString());
  }

}
