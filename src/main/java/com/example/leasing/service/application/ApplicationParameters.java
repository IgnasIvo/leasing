package com.example.leasing.service.application;

import org.immutables.value.Value;

import java.math.BigDecimal;
import java.util.Optional;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Value.Immutable
public interface ApplicationParameters {

    String vin();

    String applicantPersonCode();

    BigDecimal applicantIncome();

    BigDecimal requestedFunding();

    Optional<String> coApplicantPersonCode();

    Optional<BigDecimal> coApplicantIncome();

}
