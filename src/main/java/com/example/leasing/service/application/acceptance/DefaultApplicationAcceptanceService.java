package com.example.leasing.service.application.acceptance;

import com.example.leasing.entity.application.Applicant;
import com.example.leasing.entity.application.Application;
import com.example.leasing.entity.application.ApplicationStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Service
class DefaultApplicationAcceptanceService implements ApplicationAcceptanceService {
    private static final BigDecimal ACCEPTANCE_THRESHOLD = BigDecimal.valueOf(600);

    @Override
    public ApplicationStatus calculateStatus(Application application) {
        return totalIncomeOf(application.getApplicants())
                .filter(exceedsThreshold())
                .map(exceedsThreshold -> ApplicationStatus.ACCEPTED)
                .orElse(ApplicationStatus.REJECTED);
    }

    private Optional<BigDecimal> totalIncomeOf(Set<Applicant> applicants) {
        return applicants.stream()
                .map(Applicant::getIncome)
                .reduce(BigDecimal::add);
    }

    private Predicate<BigDecimal> exceedsThreshold() {
        return income -> income.compareTo(ACCEPTANCE_THRESHOLD) >= 0;
    }

}
