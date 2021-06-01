package com.example.leasing.service.application;

import com.example.leasing.entity.application.Applicant;
import com.example.leasing.entity.application.Application;
import com.example.leasing.entity.application.VehicleData;
import com.example.leasing.repository.application.ApplicationRepository;
import com.example.leasing.service.application.acceptance.ApplicationAcceptanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Service
class DefaultApplicationService implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationAcceptanceService acceptanceService;

    @Autowired
    DefaultApplicationService(ApplicationRepository applicationRepository,
                              ApplicationAcceptanceService acceptanceService) {
        this.applicationRepository = applicationRepository;
        this.acceptanceService = acceptanceService;
    }

    @Override
    public Application apply(ApplicationParameters parameters) {
        Application application = applicationFrom(parameters);
        application.setStatus(acceptanceService.calculateStatus(application));
        applicationRepository.save(application);
        return application;
    }

    @Override
    public Application find(String reference) {
        return applicationRepository.findOneByReference(reference)
                .orElseThrow(() -> new ApplicationNotFound(reference));
    }

    private Application applicationFrom(ApplicationParameters parameters) {
        VehicleData vehicleData = vehicleDataFrom(parameters);
        Set<Applicant> applicants = applicantsFrom(parameters);
        return Application.builder()
                .requestedAmount(parameters.requestedFunding())
                .vehicleData(vehicleData)
                .applicants(applicants)
                .build();
    }

    private VehicleData vehicleDataFrom(ApplicationParameters parameters) {
        return VehicleData.builder()
                .vin(parameters.vin())
                .build();
    }

    private Set<Applicant> applicantsFrom(ApplicationParameters parameters) {
        Applicant applicant = mainApplicantFrom(parameters);
        Optional<Applicant> optionalCoApplicant = optionalCoApplicantFrom(parameters);
        return optionalCoApplicant.map(coApplicant -> Set.of(applicant, coApplicant))
                .orElse(Collections.singleton(applicant));
    }

    private Applicant mainApplicantFrom(ApplicationParameters parameters) {
        return applicantFrom(parameters.applicantPersonCode(), parameters.applicantIncome());
    }

    private Applicant applicantFrom(String personCode, BigDecimal income) {
        return Applicant.builder()
                .personCode(personCode)
                .income(income)
                .build();
    }

    private Optional<Applicant> optionalCoApplicantFrom(ApplicationParameters parameters) {
        return parameters.coApplicantPersonCode()
                .map(coApplicantExists -> coApplicantFrom(parameters));
    }

    private Applicant coApplicantFrom(ApplicationParameters parameters) {
        String coApplicantCode = parameters.coApplicantPersonCode()
                .orElseThrow(PersonCodeMissing::new);
        BigDecimal coApplicantIncome = parameters.coApplicantIncome()
                .orElse(BigDecimal.ZERO);
        return applicantFrom(coApplicantCode, coApplicantIncome);
    }

}
