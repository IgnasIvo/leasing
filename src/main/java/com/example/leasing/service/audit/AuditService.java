package com.example.leasing.service.audit;

import com.example.leasing.entity.audit.AuditEntry;

/***
 *
 * @author Ignas Ivoska
 *
 */
public interface AuditService {

    AuditEntry save(Object request, String endpoint);

}
