package com.example.leasing.repository.application;

import com.example.leasing.entity.application.Application;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/***
 *
 * @author Ignas Ivoska
 *
 */
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    Optional<Application> findOneByReference(String reference);

}
