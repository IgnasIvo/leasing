package com.example.leasing.service.audit;

import com.example.leasing.entity.audit.AuditEntry;
import com.example.leasing.repository.audit.AuditRepository;
import com.example.leasing.service.mapper.MapperService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

/***
 *
 * @author Ignas Ivoska
 *
 */
@ExtendWith(MockitoExtension.class)
class DefaultAuditServiceTest {
    private static final String REQUEST_DATA = "requestData";
    private static final String CONVERTED_REQUEST_DATA = "convertedRequestData";
    private static final String ENDPOINT = "endpoint";

    @Mock
    private AuditRepository auditRepository;
    @Mock
    private MapperService mapperService;

    @Captor
    private ArgumentCaptor<AuditEntry> entryArgumentCaptor;

    @InjectMocks
    private DefaultAuditService defaultAuditService;

    @Test
    void savesAuditEntry() {
        given(mapperService.toString(REQUEST_DATA)).willReturn(CONVERTED_REQUEST_DATA);

        defaultAuditService.save(REQUEST_DATA, ENDPOINT);

        then(auditRepository).should().save(entryArgumentCaptor.capture());

        AuditEntry entry = entryArgumentCaptor.getValue();
        assertThat(entry.getEndpoint()).isEqualTo(ENDPOINT);
        assertThat(entry.getRequest()).isEqualTo(CONVERTED_REQUEST_DATA);
    }

}