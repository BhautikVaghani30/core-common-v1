package com.town.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.town.common.model.response.CoreMessage;
import com.town.common.model.response.EligiblePayment;
import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Represents the response payload containing a list of available and eligible payment methods.
 *
 * <p>This class is typically used as a return type for endpoints that determine which payment
 * options are available to a user based on their context (e.g., country, cart contents).
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CoreAvailablePayments {
  /** A list of payment methods that have been determined to be eligible for the current context. */
  private List<EligiblePayment> eligiblePayments;

  /** An optional message providing additional information, such as warnings or errors. */
  private CoreMessage message;
}
