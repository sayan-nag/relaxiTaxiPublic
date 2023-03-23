package com.relaxiTaxi.onboarding.common;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class CustomResponseBuilder {

    private static Map<ErrorCode, String> errorCodes = new HashMap<>();
    private static Map<ErrorCode, HttpStatus> errorCodesHttp = new HashMap<>();

    static {
        errorCodes.put(ErrorCode.RT0001, "Not a valid email");
        errorCodes.put(ErrorCode.RT0002, "Driver email already registered, OTP sent to registered email");
        errorCodes.put(ErrorCode.RT0003, "Not a valid driverDocumentId");
        errorCodes.put(ErrorCode.RT0004, "Not a valid vehicleId");
        errorCodes.put(ErrorCode.RT0005, "Authentication failed, wrong OTP");
        errorCodes.put(ErrorCode.RT0006, "Invalid document type");
        errorCodes.put(ErrorCode.RT0007, "File upload failure, contact administrator");
        errorCodes.put(ErrorCode.RT0008, "your, current stage is less than 5, trying to access stage 6 not allowed");
        errorCodes.put(ErrorCode.RT0009, "your, current stage is less than 6, trying to access stage 7 not allowed");
        errorCodes.put(ErrorCode.RT0010, "your, current stage is 8 or above, trying to access stage 4,5,6,7 not allowed");
        errorCodes.put(ErrorCode.RT0011, "user, current stage is less than 7 try to complete pending state");
        errorCodes.put(ErrorCode.RT0012, "user, current stage is less than 8 wait driver to upload all the document and get verified");
        errorCodes.put(ErrorCode.RT0013, "user, current stage is less than 9 wait driver to upload all the document and get verified");
        errorCodes.put(ErrorCode.RT0014, "Wrong device entered, for a driver");
        errorCodes.put(ErrorCode.RT0015, "Not an Admin");
        errorCodesHttp.put(ErrorCode.RT0001, HttpStatus.BAD_REQUEST);
        errorCodesHttp.put(ErrorCode.RT0002, HttpStatus.ALREADY_REPORTED);
        errorCodesHttp.put(ErrorCode.RT0003, HttpStatus.BAD_REQUEST);
        errorCodesHttp.put(ErrorCode.RT0004, HttpStatus.BAD_REQUEST);
        errorCodesHttp.put(ErrorCode.RT0005, HttpStatus.UNAUTHORIZED);
        errorCodesHttp.put(ErrorCode.RT0006, HttpStatus.BAD_REQUEST);
        errorCodesHttp.put(ErrorCode.RT0007, HttpStatus.INTERNAL_SERVER_ERROR);
        errorCodesHttp.put(ErrorCode.RT0008, HttpStatus.UNAUTHORIZED);
        errorCodesHttp.put(ErrorCode.RT0009, HttpStatus.UNAUTHORIZED);
        errorCodesHttp.put(ErrorCode.RT0010, HttpStatus.UNAUTHORIZED);
        errorCodesHttp.put(ErrorCode.RT0011, HttpStatus.UNAUTHORIZED);
        errorCodesHttp.put(ErrorCode.RT0012, HttpStatus.UNAUTHORIZED);
        errorCodesHttp.put(ErrorCode.RT0013, HttpStatus.UNAUTHORIZED);
        errorCodesHttp.put(ErrorCode.RT0013, HttpStatus.BAD_REQUEST);
        errorCodesHttp.put(ErrorCode.RT0015, HttpStatus.UNAUTHORIZED);
    }

    public static String getErrorCodes(ErrorCode errorCode) {
        return errorCodes.get(errorCode);
    }

    public static HttpStatus getErrorCodesHttp(ErrorCode errorCode) {
        return errorCodesHttp.get(errorCode);
    }

    public static enum ErrorCode {
        RT0001,
        RT0002,
        RT0003,
        RT0004,
        RT0005,
        RT0006,
        RT0007, RT0008, RT0009, RT0010, RT0011, RT0012, RT0013, RT0015, RT0014

    }
}
