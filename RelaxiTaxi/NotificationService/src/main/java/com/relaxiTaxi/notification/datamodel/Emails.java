package com.relaxiTaxi.notification.datamodel;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class Emails {

    //Recipient, subject, body
    @NonNull
    private String recipient;
    private String subject;
    private String body;

    public Emails(String recipient, String subject, String body) {
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }

    public Emails() {

    }


    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
