package com.example.leasing.service.application;

import com.example.leasing.entity.application.Application;
import com.example.leasing.entity.application.ApplicationStatus;
import com.example.leasing.repository.application.ApplicationRepository;
import com.example.leasing.service.application.acceptance.ApplicationAcceptanceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/***
 *
 * @author Ignas Ivoska
 *
 */
@ExtendWith(MockitoExtension.class)
class DefaultApplicationServiceTest {
    private static final String REFERENCE = "REFERENCE";
    private static final String VIN = "VIN";
    private static final BigDecimal FUNDING = BigDecimal.ZERO;
    private static final String APPLICANT_PERSON_CODE = "123456";
    private static final BigDecimal APPLICANT_INCOME = BigDecimal.ONE;
    private static final String CO_APPLICANT_CODE = "654321";
    private static final BigDecimal CO_APPLICANT_INCOME = BigDecimal.TEN;

    @Mock
    private ApplicationRepository applicationRepository;
    @Mock
    private ApplicationAcceptanceService acceptanceService;

    @Mock
    private Application application;

    @Captor
    private ArgumentCaptor<Application> applicationArgumentCaptor;

    @InjectMocks
    private DefaultApplicationService defaultApplicationService;

    @Test
    void findsApplication() {
        given(applicationRepository.findOneByReference(REFERENCE))
                .willReturn(Optional.of(application));

        assertThat(defaultApplicationService.find(REFERENCE))
                .isEqualTo(application);
    }

    @Test
    void throwsIfApplicationIsNotPresent() {
        assertThatExceptionOfType(ApplicationNotFound.class)
                .isThrownBy(() -> defaultApplicationService.find(REFERENCE));
    }

    @Test
    void savesApplicationWithoutCoApplicant() {
        ApplicationParameters parameters = parameters(null, null);
        mockSavingAndCalculation();

        defaultApplicationService.apply(parameters);

        assertCalls();

        Application captured = applicationArgumentCaptor.getValue();

        assertThat(captured.getVehicleData().getVin()).isEqualTo(VIN);
        assertThat(captured.getApplicants().size()).isEqualTo(1);
        assertThat(captured.getStatus()).isEqualTo(ApplicationStatus.ACCEPTED);
    }

    @Test
    void savesApplicationWithCoApplicant() {
        ApplicationParameters parameters = parameters(CO_APPLICANT_CODE, CO_APPLICANT_INCOME);
        mockSavingAndCalculation();

        defaultApplicationService.apply(parameters);

        assertCalls();

        Application captured = applicationArgumentCaptor.getValue();

        assertThat(captured.getVehicleData().getVin()).isEqualTo(VIN);
        assertThat(captured.getApplicants().size()).isEqualTo(2);
        assertThat(captured.getStatus()).isEqualTo(ApplicationStatus.ACCEPTED);
    }

    private ApplicationParameters parameters(String coApplicantCode, BigDecimal coApplicantIncome) {
        return com.example.leasing.service.application.ImmutableApplicationParameters.builder()
                .vin(VIN)
                .requestedFunding(FUNDING)
                .applicantPersonCode(APPLICANT_PERSON_CODE)
                .applicantIncome(APPLICANT_INCOME)
                .coApplicantPersonCode(ofNullable(coApplicantCode))
                .coApplicantIncome(ofNullable(coApplicantIncome))
                .build();
    }

    private void mockSavingAndCalculation() {
        given(applicationRepository.save(any(Application.class))).willReturn(application);
        given(acceptanceService.calculateStatus(any(Application.class))).willReturn(ApplicationStatus.ACCEPTED);
    }

    private void assertCalls() {
        then(acceptanceService).should().calculateStatus(any(Application.class));
        then(applicationRepository).should().save(applicationArgumentCaptor.capture());
    }

}