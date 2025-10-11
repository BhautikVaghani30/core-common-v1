package com.core.v1.common.controller;

import com.core.v1.common.enums.CoreApiStatus;
import com.core.v1.common.i18n.CoreResourceManager;
import com.core.v1.common.model.CoreAvailablePayments;
import com.core.v1.common.model.response.CoreMessage;
import com.core.v1.common.model.response.CoreMeta;
import com.core.v1.common.model.response.CoreResponse;
import com.core.v1.common.providers.CorePropertiesProvider;
import com.core.v1.common.service.CoreFunctionalityService;
import com.core.v1.common.utils.CoreJsonParserUtils;
import java.util.Collections;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/** Controller for handling payment eligibility checks. */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TestController {

  private final CoreFunctionalityService coreFunctionalityService;
  private final CorePropertiesProvider corePropertiesProvider;

  /**
   * Retrieves the available and eligible payment methods based on the current context.
   *
   * @return A Mono emitting a CoreResponse with the eligibility details.
   */
  @GetMapping("/payments/eligibility")
  public Mono<CoreResponse<CoreAvailablePayments>> getPaymentEligibility() {
    return coreFunctionalityService
        .executePaymentHandlerTask()
        .map(this::buildAvailablePaymentsResponse)
        .doOnSuccess(
            result -> {
              if (log.isDebugEnabled()) {
                log.debug(
                    "TestController : getPaymentEligibility :: Response successfully built: {}",
                    CoreJsonParserUtils.getJsonFromObject(result));
              }
            });
  }

  /**
   * Transforms the service layer result into a final, user-facing CoreResponse in a functional
   * style.
   *
   * @param result The CoreAvailablePayments object from the service.
   * @return A fully constructed CoreResponse.
   */
  private CoreResponse<CoreAvailablePayments> buildAvailablePaymentsResponse(
      CoreAvailablePayments result) {
    boolean hasError =
        result.getEligiblePayments().stream()
            .anyMatch(payment -> Objects.nonNull(payment.getMessage()));

    CoreApiStatus status = hasError ? CoreApiStatus.FAIL : CoreApiStatus.SUCCESS;
    String messageCode =
        hasError
            ? CoreResourceManager.getCode(
                corePropertiesProvider.getCoreErrorProperties().getGetAvailablePaymentError())
            : CoreResourceManager.getCode(
                corePropertiesProvider.getCoreErrorProperties().getGetAvailablePaymentSuccess());

    return CoreResponse.create(
        baseResponse ->
            baseResponse
                .withMeta(CoreMeta.builder().status(status.getValue()).build())
                .withData(result)
                .withMessages(
                    Collections.singletonList(
                        CoreMessage.builder().code(messageCode).type(status.getValue()).build())));
  }
}
