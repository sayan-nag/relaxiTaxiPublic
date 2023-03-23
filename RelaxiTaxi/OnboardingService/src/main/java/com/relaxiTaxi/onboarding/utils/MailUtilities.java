package com.relaxiTaxi.onboarding.utils;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.Security;
import java.util.Properties;


public class MailUtilities {

    private static final String SMTP_HOST_NAME = "smtp.gmail.com";
    private static final String SMTP_PORT = "465";
    private static final String emailMsgTxt = "Test Message Contents";
    private static final String emailSubjectTxt = "A test from gmail";
    private static final String emailFromAddress = "onboardrelaxidrivers@gmail.com";
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    public static void main(String[] args) throws MessagingException {
        //MailUtilities.sendMail("onboardrelaxidrivers@gmail.com", "nagsayan@gmail.com", "0nboard@123");

        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
        String[] sendTo = new String[1];
        sendTo[0] = "nagsayan@gmail.com";
        new MailUtilities().sendSSLMessage(sendTo, emailSubjectTxt,
                emailMsgTxt, emailFromAddress);
        System.out.println("Sucessfully Sent mail to All Users");

    }

    public void sendSSLMessage(String recipients[], String subject,
                               String message, String from) throws MessagingException {
        boolean debug = true;


        System.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\java\\cacerts");
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST_NAME);


        props.put("mail.smtp.auth", "true");

        props.put("mail.debug", "true");
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.port", SMTP_PORT);
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("javax.net.ssl.trustStore", "C:\\Program Files\\java\\cacerts");

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("onboardrelaxidrivers@gmail.com", "ulngsffnjtvhypov");
            }
        };

        Session session = Session.getDefaultInstance(props, auth);


        session.setDebug(debug);

        Message msg = new MimeMessage(session);
        InternetAddress addressFrom = new InternetAddress(from);
        msg.setFrom(addressFrom);

        InternetAddress[] addressTo = new InternetAddress[recipients.length];
        for (int i = 0; i < recipients.length; i++) {
            addressTo[i] = new InternetAddress(recipients[i]);
        }
        msg.setRecipients(Message.RecipientType.TO, addressTo);

// Setting the Subject and Content Type
        msg.setSubject(subject);
        msg.setContent(message, "text/plain");
        Transport.send(msg);
    }

}
