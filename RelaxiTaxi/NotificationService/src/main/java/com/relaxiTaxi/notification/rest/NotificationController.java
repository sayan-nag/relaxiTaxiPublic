package com.relaxiTaxi.notification.rest;

import com.relaxiTaxi.notification.datamodel.Emails;
import com.relaxiTaxi.notification.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController

@RequestMapping(value = {"/notification"})
public class NotificationController {

    @Autowired
    EmailService emailService;

    @Autowired
    Emails driverEmail;

    @PostMapping("/send/email")
    ResponseEntity sendEmail(@Valid @RequestBody Emails email) {

        try {
            return emailService.sendMail(email,"987");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/send/email/{emailID}/{otp}")
    ResponseEntity sendEmailById(@PathVariable String emailID, @PathVariable String otp) {
        driverEmail.setRecipient(emailID);
        try {
            return emailService.sendMail(driverEmail, otp);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/send/activation/{emailID}")
    ResponseEntity sendDriverActivationMail(@PathVariable String emailID , @RequestHeader("activationLink") String activationLink) {
        driverEmail.setRecipient(emailID);
        try {
            return emailService.sendDriverActivationMail(driverEmail, activationLink);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

}
