package com.relaxiTaxi.onboarding.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseCreator {

    public static ResponseEntity createErrorResponse(CustomResponseBuilder.ErrorCode errorCode) {
        return ResponseEntity.status(CustomResponseBuilder.getErrorCodesHttp(errorCode)).body(CustomResponseBuilder.getErrorCodes(errorCode));
    }

    public static ResponseEntity createSuccessResponse(String message) {
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
}
