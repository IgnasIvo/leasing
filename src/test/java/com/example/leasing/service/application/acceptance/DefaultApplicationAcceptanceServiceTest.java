package com.example.leasing.service.application.acceptance;

import com.example.leasing.entity.application.Applicant;
import com.example.leasing.entity.application.Application;
import com.example.leasing.entity.application.ApplicationStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Set;

import static java.util.Collections.singleton;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/***
 *
 * @author Ignas Ivoska
 *
 */
@ExtendWith(MockitoExtension.class)
class DefaultApplicationAcceptanceServiceTest {

    @Mock
    private Application application;
    @Mock
    private Applicant applicant;
    @Mock
    private Applicant coApplicant;

    private final DefaultApplicationAcceptanceService acceptanceService = new DefaultApplicationAcceptanceService();

    @Test
    void rejectsApplicationIfApplicantHasLessIncomeThanThreshold() {
        given(application.getApplicants()).willReturn(singleton(applicant));
        given(applicant.getIncome()).willReturn(BigDecimal.ZERO);

        assertThat(acceptanceService.calculateStatus(application))
                .isEqualTo(ApplicationStatus.REJECTED);
    }

    @Test
    void rejectsApplicationIfApplicantsHaveLessIncomeThanThreshold() {
        given(application.getApplicants()).willReturn(Set.of(applicant, coApplicant));
        given(applicant.getIncome()).willReturn(BigDecimal.ZERO);
        given(coApplicant.getIncome()).willReturn(BigDecimal.TEN);

        assertThat(acceptanceService.calculateStatus(application))
                .isEqualTo(ApplicationStatus.REJECTED);
    }

    @Test
    void acceptsApplicationIfApplicantHasMoreIncomeThanThreshold() {
        given(application.getApplicants()).willReturn(singleton(applicant));
        given(applicant.getIncome()).willReturn(BigDecimal.valueOf(600));

        assertThat(acceptanceService.calculateStatus(application))
                .isEqualTo(ApplicationStatus.ACCEPTED);
    }

    @Test
    void rejectsApplicationIfApplicantsHaveMoreIncomeThanThreshold() {
        given(application.getApplicants()).willReturn(Set.of(applicant, coApplicant));
        given(applicant.getIncome()).willReturn(BigDecimal.valueOf(600));
        given(coApplicant.getIncome()).willReturn(BigDecimal.valueOf(600));

        assertThat(acceptanceService.calculateStatus(application))
                .isEqualTo(ApplicationStatus.ACCEPTED);
    }

}