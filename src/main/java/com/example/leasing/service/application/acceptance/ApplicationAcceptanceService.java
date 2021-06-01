package com.example.leasing.service.application.acceptance;

import com.example.leasing.entity.application.Application;
import com.example.leasing.entity.application.ApplicationStatus;

/***
 *
 * @author Ignas Ivoska
 *
 */
public interface ApplicationAcceptanceService {

    ApplicationStatus calculateStatus(Application application);

}
