package com.example.leasing.exception.handler;

import com.example.leasing.exception.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

/***
 *
 * @author Ignas Ivoska
 *
 */
@ExtendWith(MockitoExtension.class)
class RestExceptionHandlerTest {
    private static final String DETAILS = "Details";
    private static final String MESSAGE = "Message";
    private static final StackTraceElement[] STACK_TRACE_ELEMENTS = new StackTraceElement[0];

    @Mock
    private RestException restException;
    @Mock
    private RuntimeException runtimeException;

    private final RestExceptionHandler restExceptionHandler = new RestExceptionHandler();

    @Test
    void handlesRestException() {
        given(restException.getStatus()).willReturn(HttpStatus.BAD_REQUEST);
        given(restException.getMessage()).willReturn(MESSAGE);
        given(restException.getDetails()).willReturn(singletonList(DETAILS));

        assertThat(restExceptionHandler.handleApplicationExceptions(restException))
                .isEqualTo(new ResponseEntity<>(expectedResponseWithDetails(), HttpStatus.BAD_REQUEST));
    }

    @Test
    void handlesRuntimeException() {
        given(runtimeException.getMessage()).willReturn(MESSAGE);
        given(runtimeException.getStackTrace()).willReturn(STACK_TRACE_ELEMENTS);

        assertThat(restExceptionHandler.handleAnyOtherExceptions(runtimeException))
                .isEqualTo(new ResponseEntity<>(expectedResponseWithStackTrace(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    private ErrorResponse expectedResponseWithDetails() {
        return new ErrorResponse(MESSAGE, singletonList(DETAILS));
    }

    private ErrorResponse expectedResponseWithStackTrace() {
        return new ErrorResponse(MESSAGE, STACK_TRACE_ELEMENTS);
    }

}