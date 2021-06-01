package com.example.leasing.controller.application;

import lombok.Data;

import java.math.BigDecimal;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Data
class ApplicationRequest {
    private final String vin;
    private final String applicantCode;
    private final BigDecimal applicantIncome;
    private final BigDecimal requestedAmount;
    private final String coApplicantCode;
    private final BigDecimal coApplicantIncome;
}
