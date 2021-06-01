package com.example.leasing.service.audit;

import com.example.leasing.entity.audit.AuditEntry;
import com.example.leasing.repository.audit.AuditRepository;
import com.example.leasing.service.mapper.MapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/***
 *
 * @author Ignas Ivoska
 *
 */
@Service
class DefaultAuditService implements AuditService {

    private final AuditRepository auditRepository;
    private final MapperService mapperService;

    @Autowired
    DefaultAuditService(AuditRepository auditRepository,
                        MapperService mapperService) {
        this.auditRepository = auditRepository;
        this.mapperService = mapperService;
    }

    @Override
    public AuditEntry save(Object request, String endpoint) {
        return auditRepository.save(auditEntryOf(request, endpoint));
    }

    private AuditEntry auditEntryOf(Object request, String endpoint) {
        return AuditEntry.builder()
                .request(mapperService.toString(request))
                .endpoint(endpoint)
                .build();
    }

}
