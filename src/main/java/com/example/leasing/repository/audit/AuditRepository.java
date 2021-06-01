package com.example.leasing.repository.audit;

import com.example.leasing.entity.audit.AuditEntry;
import org.springframework.data.jpa.repository.JpaRepository;

/***
 *
 * @author Ignas Ivoska
 *
 */
public interface AuditRepository extends JpaRepository<AuditEntry, Long> {
}
