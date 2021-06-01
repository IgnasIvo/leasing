package com.example.leasing.service.application;

import com.example.leasing.entity.application.Application;

/***
 *
 * @author Ignas Ivoska
 *
 */
public interface ApplicationService {

    Application apply(ApplicationParameters parameters);

    Application find(String reference);

}
