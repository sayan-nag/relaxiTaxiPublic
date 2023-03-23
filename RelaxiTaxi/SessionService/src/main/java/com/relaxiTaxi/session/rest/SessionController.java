package com.relaxiTaxi.session.rest;

import com.relaxiTaxi.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController

@RequestMapping(value = {"/session"})
public class SessionController {

    @Autowired
    SessionService sessionService;

    @GetMapping("/create")
    public ResponseEntity<String> createSession(@RequestHeader("driver_id") String driverId) throws IOException {
        // code that uses the language variable
        return sessionService.createSession(driverId);
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateSession(@RequestHeader("driver_id") String driverId, @RequestHeader("rt_d_session") String sessionID) throws IOException {
        // code that uses the language variable
        return sessionService.validateSession(driverId, sessionID);
    }

}
