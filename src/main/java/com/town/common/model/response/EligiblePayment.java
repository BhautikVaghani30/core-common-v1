package com.town.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EligiblePayment {
  private String payment;
  private boolean isEligible;
  private CoreMessage message;
}
