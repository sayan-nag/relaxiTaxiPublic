package com.relaxiTaxi.session.service;

import com.relaxiTaxi.session.common.Memcache;
import com.relaxiTaxi.session.datamodel.SessionToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SessionService {

    @Autowired
    SessionToken sessionToken;
    @Autowired
    Memcache memcache;

    public ResponseEntity createSession(String driverId) throws IOException {
        //mailUtilities.sendMail(email);
        sessionToken.setDrivierID(driverId);
        sessionToken.setGeneratedSession(sessionToken.generateRandomSessionKey());
        memcache.add(sessionToken.getDrivierID(), sessionToken.getGeneratedSession());
        System.out.println("key is" + sessionToken.getGeneratedSession());
        return new ResponseEntity<>(sessionToken.getGeneratedSession(), HttpStatus.OK);
    }

    public ResponseEntity validateSession(String driverId, String sessionID) throws IOException {
        //mailUtilities.sendMail(email);
        boolean valid = memcache.validate(driverId, sessionID);
        if (valid)
            return new ResponseEntity<>("your session is valid", HttpStatus.OK);
        else
            return new ResponseEntity<>("invalid session", HttpStatus.CONFLICT);
    }
}
