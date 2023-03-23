package com.relaxiTaxi.onboarding.stage.firstContact;

import com.relaxiTaxi.onboarding.common.CustomResponseBuilder;
import com.relaxiTaxi.onboarding.common.OnboardingStages;
import com.relaxiTaxi.onboarding.common.ResponseCreator;
import com.relaxiTaxi.onboarding.common.StageResponse;
import com.relaxiTaxi.onboarding.persistence.DriverDao;
import com.relaxiTaxi.onboarding.stage.StageManager;
import com.relaxiTaxi.onboarding.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Component
public class ContactManager implements StageManager<String> {


    @Autowired
    private DriverDao driverDao;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OnboardingStages.Stage getStage() {
        return OnboardingStages.Stage.INITIAL_CONTACT;
    }

    @Override
    public StageResponse performStageActions(int previousStageId, String email) {
        StageResponse response = new StageResponse();
        if (Utilities.isValidEmail(email)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            Map<String, String> vars = new HashMap<>();
            vars.put("mailId", email);
            String otpToken = restTemplate.exchange("http://localhost:8084/auth/create/{mailId}", HttpMethod.GET, entity, String.class, vars).getBody();
            sendEmail(email, otpToken);
            if (!driverDao.isDriverEmailExists(email)) {
                //action :: send otp to mail
                //restTemplate = new RestTemplate();
                response.setResponse(ResponseCreator.createSuccessResponse("Registration OTP sent to " + email));
            } else {
                response.setResponse(ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0002));
            }
        } else {
            response.setResponse(ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0001));
        }
        return response;
    }



    public void sendEmail(String emailId, String otp) throws RuntimeException{
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<?> entity = new HttpEntity<>(headers);
        Map<String, String> vars = new HashMap<>();
        vars.put("emailId", emailId);
        vars.put("otp", otp);

        restTemplate.exchange("http://localhost:8082/notification/send/email/{emailId}/{otp}", HttpMethod.POST, entity, String.class, vars).getBody();

    }

    public void sendActivationEmail(String emailId, String link) throws RuntimeException{

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        requestHeaders.add("activationLink", link);
        HttpEntity<?> entity = new HttpEntity<>(requestHeaders);
        Map<String, String> vars = new HashMap<>();
        vars.put("emailId", emailId);
        //send/activation/email/{emailID}
        vars.put("otp", "activate");

        restTemplate.exchange("http://localhost:8082/notification/send/activation/{emailId}", HttpMethod.POST, entity, String.class, vars).getBody();
        //restTemplate.exchange("http://localhost:8082/notification/send/activation/email/{emailID}", HttpMethod.POST, entity, String.class, vars).getBody();

    }

    @Override
    public String getVersion() {
        return null;
    }
}
