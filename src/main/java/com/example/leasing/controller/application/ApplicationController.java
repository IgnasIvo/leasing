package com.example.leasing.controller.application;

import com.example.leasing.entity.application.Application;
import com.example.leasing.service.application.ApplicationParameters;
import com.example.leasing.service.application.ApplicationService;
import com.example.leasing.service.application.ImmutableApplicationParameters;
import com.example.leasing.service.audit.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/***
 *
 * @author Ignas Ivoska
 *
 */
@RestController
@RequestMapping("application")
class ApplicationController {

    private final AuditService auditService;
    private final ApplicationService applicationService;

    @Autowired
    ApplicationController(AuditService auditService,
                          ApplicationService applicationService) {
        this.auditService = auditService;
        this.applicationService = applicationService;
    }

    @PostMapping("apply")
    public Application applyForLeasing(@RequestBody ApplicationRequest request, HttpServletRequest servletRequest) {
        auditService.save(request, servletRequest.getServletPath());
        return applicationService.apply(parameters(request));
    }

    @GetMapping("find")
    public Application findApplication(@RequestParam String reference, HttpServletRequest request) {
        auditService.save(reference, request.getServletPath());
        return applicationService.find(reference);
    }

    private ApplicationParameters parameters(ApplicationRequest request) {
        return ImmutableApplicationParameters.builder()
                .requestedFunding(request.getRequestedAmount())
                .applicantIncome(request.getApplicantIncome())
                .applicantPersonCode(request.getApplicantCode())
                .vin(request.getVin())
                .coApplicantIncome(Optional.ofNullable(request.getCoApplicantIncome()))
                .coApplicantPersonCode(Optional.ofNullable(request.getCoApplicantCode()))
                .build();
    }

}
