package com.core.v1.common.config;

import com.core.v1.common.providers.CoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Binds to the properties defined in application.properties with the prefix "core.messages". This
 * provides a type-safe and centralized way to access message code configurations.
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({"advisors", "declaredPointcut", "classFilter", "methodMatcher"})
public class CoreErrorProperties implements CoreProperties {

  /** Maps to 'core.messages.technical-error'. */
  private String technicalError;

  /** Maps to 'core.messages.get-available-payment-success'. */
  private String getAvailablePaymentSuccess;

  /** Maps to 'core.messages.get-available-payment-error'. */
  private String getAvailablePaymentError;

  /** Maps to 'core.messages.venmo-payment-not-eligible'. */
  private String venmoPaymentNotEligible;
}
