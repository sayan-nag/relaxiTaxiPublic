package com.relaxiTaxi.notification.service;

import com.relaxiTaxi.notification.common.MailUtilities;
import com.relaxiTaxi.notification.datamodel.Emails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class EmailService {
    @Autowired
    MailUtilities mailUtilities;

    public ResponseEntity sendMail(Emails email, String otp) throws MessagingException {
        mailUtilities.sendMail(email, otp);

        return new ResponseEntity<>("mail send", HttpStatus.OK);
    }

    public ResponseEntity sendDriverActivationMail(Emails email, String activationLink) throws MessagingException {
        mailUtilities.sendDriverActivationMail(email, activationLink);

        return new ResponseEntity<>("mail send", HttpStatus.OK);
    }
}
