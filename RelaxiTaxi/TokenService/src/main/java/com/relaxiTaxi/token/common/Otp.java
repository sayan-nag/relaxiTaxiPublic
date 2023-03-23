package com.relaxiTaxi.token.common;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class Otp {

    private String mailID;
    private String otp;

    public String getMailID() {
        return mailID;
    }

    public void setMailID(String mailID) {
        this.mailID = mailID;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String generateRandomSessionKey() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

}
