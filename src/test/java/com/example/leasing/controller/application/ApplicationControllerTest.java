package com.example.leasing.controller.application;

import com.example.leasing.entity.application.Application;
import com.example.leasing.service.application.ApplicationParameters;
import com.example.leasing.service.application.ApplicationService;
import com.example.leasing.service.application.ImmutableApplicationParameters;
import com.example.leasing.service.audit.AuditService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/***
 *
 * @author Ignas Ivoska
 *
 */
@ExtendWith(MockitoExtension.class)
class ApplicationControllerTest {
    private static final String APPLICATION_FIND_ENDPOINT = "/application/find";
    private static final String REFERENCE = "REFERENCE";
    private static final String VIN = "VIN";
    private static final String APPLICANT_PERSON_CODE = "123456";
    private static final String CO_APPLICANT_PERSON_CODE = "654321";
    private static final BigDecimal APPLICANT_INCOME = BigDecimal.ONE;
    private static final BigDecimal CO_APPLICANT_INCOME = BigDecimal.TEN;
    private static final BigDecimal REQUESTED_FUNDING = BigDecimal.ZERO;
    private static final String APPLICATION_APPLY_ENDPOINT = "/application/apply";

    @Mock
    private AuditService auditService;
    @Mock
    private ApplicationService applicationService;

    @Mock
    private Application application;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ApplicationController applicationController;

    @Test
    void findsApplication() {
        given(request.getServletPath())
                .willReturn(APPLICATION_FIND_ENDPOINT);
        given(applicationService.find(REFERENCE))
                .willReturn(application);

        assertThat(applicationController.findApplication(REFERENCE, request))
                .isEqualTo(application);
        then(auditService).should().save(REFERENCE, APPLICATION_FIND_ENDPOINT);
    }

    @Test
    void servesApplicationWithCoApplicant() {
        given(request.getServletPath())
                .willReturn(APPLICATION_APPLY_ENDPOINT);
        given(applicationService.apply(parameters(null, null)))
                .willReturn(application);

        ApplicationRequest requestData = request(null, null);

        assertThat(applicationController.applyForLeasing(requestData, request))
                .isEqualTo(application);
        then(auditService).should().save(requestData, APPLICATION_APPLY_ENDPOINT);
        then(applicationService).should().apply(parameters(null, null));
    }

    @Test
    void servesApplicationWithoutCoApplicant() {
        given(request.getServletPath())
                .willReturn(APPLICATION_APPLY_ENDPOINT);
        given(applicationService.apply(parameters(CO_APPLICANT_PERSON_CODE, CO_APPLICANT_INCOME)))
                .willReturn(application);

        ApplicationRequest requestData = request(CO_APPLICANT_PERSON_CODE, CO_APPLICANT_INCOME);

        assertThat(applicationController.applyForLeasing(requestData, request))
                .isEqualTo(application);
        then(auditService).should().save(requestData, APPLICATION_APPLY_ENDPOINT);
        then(applicationService).should().apply(parameters(CO_APPLICANT_PERSON_CODE, CO_APPLICANT_INCOME));
    }

    private ApplicationParameters parameters(String coApplicantCode, BigDecimal coApplicantIncome) {
        return ImmutableApplicationParameters.builder()
                .vin(VIN)
                .requestedFunding(REQUESTED_FUNDING)
                .applicantPersonCode(APPLICANT_PERSON_CODE)
                .applicantIncome(APPLICANT_INCOME)
                .coApplicantPersonCode(ofNullable(coApplicantCode))
                .coApplicantIncome(ofNullable(coApplicantIncome))
                .build();
    }

    private ApplicationRequest request(String coApplicantCode, BigDecimal coApplicantIncome) {
        return new ApplicationRequest(VIN, APPLICANT_PERSON_CODE,
                APPLICANT_INCOME, REQUESTED_FUNDING,
                coApplicantCode, coApplicantIncome);
    }

}