package com.relaxiTaxi.token.rest;

import com.relaxiTaxi.token.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@RestController

@RequestMapping(value = {"/auth"})
public class OTPController {

    @Autowired
    OtpService otpService;

    @GetMapping("/create/{mailId}")
    public ResponseEntity createOTP(@PathVariable("mailId") String mailId) throws SignatureException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        //generate otp
        //persist to cache with 5 min expiry
        //return otp
        return otpService.createOTP(mailId);
    }

    @GetMapping("/validate/{mailId}/{otp}")
    public ResponseEntity validateUser(@PathVariable("mailId") String mailId, @PathVariable("otp") String otp) throws IOException {
        return otpService.validateOTP(mailId, otp);
    }
}
