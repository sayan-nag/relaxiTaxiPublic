package com.relaxiTaxi.onboarding.common;

import org.springframework.http.ResponseEntity;

import java.util.Properties;

public class StageResponse {

    private ResponseEntity response;
    private DocumentMissMatchException exception;
    private Properties properties;


    public ResponseEntity getResponse() {
        return response;
    }

    public void setResponse(ResponseEntity response) {
        this.response = response;
    }

    public DocumentMissMatchException getException() {
        return exception;
    }

    public void setException(DocumentMissMatchException exception) {
        this.exception = exception;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
