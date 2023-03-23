package com.relaxiTaxi.token.service;

import com.relaxiTaxi.token.common.Memcache;
import com.relaxiTaxi.token.common.Otp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OtpService {

    @Autowired
    Otp otpToken;
    @Autowired
    Memcache memcache;

    public ResponseEntity createOTP(String mailId) throws IOException {
        //mailUtilities.sendMail(email);
        otpToken.setMailID(mailId);
        String value = otpToken.generateRandomSessionKey();
        memcache.add(otpToken.getMailID(), value);
        //System.out.println("key is" + sessionToken.getGeneratedSession());
        return new ResponseEntity<>(value, HttpStatus.OK);
    }

    public ResponseEntity validateOTP(String mailId, String otp)  {
        try{
            boolean valid = memcache.validate(mailId, otp);
            if (valid)
                return new ResponseEntity<>("true", HttpStatus.OK);
            else
                return new ResponseEntity<>("false", HttpStatus.CONFLICT);
        }catch (IOException ex){
            return new ResponseEntity<>("false", HttpStatus.SERVICE_UNAVAILABLE);
        }


    }
}
