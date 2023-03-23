package com.relaxiTaxi.session.datamodel;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SessionToken {
    private String drivierID;
    private String generatedSession;

    public SessionToken(String drivierID) {
        this.drivierID = drivierID;
    }

    public SessionToken() {
    }

    public String getDrivierID() {
        return drivierID;
    }

    public void setDrivierID(String drivierID) {
        this.drivierID = drivierID;
    }

    public String getGeneratedSession() {
        return generatedSession;
    }

    public void setGeneratedSession(String generatedSession) {

        this.generatedSession = generatedSession;
    }

    public String generateRandomSessionKey() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

}
