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
public class AuthManager implements StageManager<Map<String, String>> {


    @Autowired
    private DriverDao driverDao;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public OnboardingStages.Stage getStage() {
        return OnboardingStages.Stage.INITIAL_CONTACT;
    }

    @Override
    public StageResponse performStageActions(int previousStageId, Map<String, String> payload) {
        return null;
    }



    public boolean performStageActionsTest(Map<String, String> payload) {
        StageResponse response = new StageResponse();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String otpToken = "false";
        try {
            otpToken = restTemplate.exchange("http://localhost:8084/auth/validate/{mailId}/{otp}", HttpMethod.GET, entity, String.class, payload).getBody();

        }catch (Exception exception){
            return false;
        }
        return Boolean.parseBoolean(otpToken);
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

    @Override
    public String getVersion() {
        return null;
    }
}
