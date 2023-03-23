package com.relaxiTaxi.onboarding.service;

import com.relaxiTaxi.onboarding.common.*;
import com.relaxiTaxi.onboarding.datamodel.Admin;
import com.relaxiTaxi.onboarding.datamodel.Device;
import com.relaxiTaxi.onboarding.datamodel.DeviceShipping;
import com.relaxiTaxi.onboarding.datamodel.Driver;
import com.relaxiTaxi.onboarding.persistence.*;
import com.relaxiTaxi.onboarding.stage.firstContact.AuthManager;
import com.relaxiTaxi.onboarding.stage.firstContact.ContactManager;
import com.relaxiTaxi.onboarding.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class AdminService {

    private static final String baseUrl = "http://localhost:8081/onboarding";
    @Autowired
    ContactManager contactManager;
    @Autowired
    AuthManager authManager;
    @Autowired
    public DriverDao driverDao;

    @Autowired
    DriverAddressDao driverAddressDao;

    @Autowired
    DriverDocumentDao driverDocumentDao;

    @Autowired
    VehicleDocumentDao vehicleDocumentDao;

    @Autowired
    DeviceDao deviceDao;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DeviceShippingDao deviceShippingDao;

    @Autowired
    DocumentIndexDao documentIndexDao;

    @Autowired
    AdminDao adminDao;


    public void updateStage(Driver driver){
        driverDao.saveDriver(driver);
    }

    public String getJwtToken(String driverId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("driver_id", driverId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String token = restTemplate.exchange("http://localhost:8084/token/create", HttpMethod.GET, entity, String.class).getBody();
        return token;
    }


    public ResponseEntity init( String email) {

        StageResponse response = new StageResponse();
        if (Utilities.isValidEmail(email)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            Map<String, String> vars = new HashMap<>();
            vars.put("mailId", email);
            String otpToken = restTemplate.exchange("http://localhost:8084/auth/create/{mailId}", HttpMethod.GET, entity, String.class, vars).getBody();
            contactManager.sendEmail(email, otpToken);
            if (!driverDao.isDriverEmailExists(email)) {
                //action :: send otp to mail
                response.setResponse(ResponseCreator.createSuccessResponse("Registration OTP sent to " + email));
            } else {
                response.setResponse(ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0002));
            }
        } else {
            response.setResponse(ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0001));
        }
        return response.getResponse();
    }

    public ResponseEntity validatedOtp(Map<String, String> vars) {
        HttpHeaders responseHeaders = new HttpHeaders();
        boolean val = authManager.performStageActionsTest(vars);
        StageResponse response = new StageResponse();
        Admin admin = null;
        if (val) {
            //create an entry if not exists
            String adminId = GenerateId.getDriverId(vars.get("mailId"));

            boolean isAdmin = adminDao.existAdmin(adminId);
            if (!isAdmin) {
                response.setResponse(ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0001));
            }else{
               admin = adminDao.fetchByID(adminId);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("driver_id", admin.getAdminId());

            HttpEntity<?> entity = new HttpEntity<>(headers);
            restTemplate = new RestTemplate();

            String sessionId = restTemplate.exchange("http://localhost:8083/session/create", HttpMethod.GET, entity, String.class).getBody();
            String token = restTemplate.exchange("http://localhost:8084/token/create", HttpMethod.GET, entity, String.class).getBody();
            //get session id and jwt token
            responseHeaders.set("token", token);
            responseHeaders.set("rt_d_session", sessionId);


            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setAdmin(admin);

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(apiResponse);
        } else {
            return new ResponseEntity<>("Not a valid OTP please recheck and verify", HttpStatus.NOT_FOUND);
        }
    }


    public ResponseEntity verifyDocument(String driverdocumentId, String driverId, String vehicleDocumentID){
        boolean stageCompleted = false;
        HttpHeaders responseHeaders = new HttpHeaders();
        Driver driver = driverDao.fetchDriverById(driverId);
        if (driver.getStageNumber() < 7)
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0011);
        if(driver.getStageNumber() > 8){
            return new ResponseEntity<>("driver documents already verified = "+ " and  driver Id = "+driverId, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
        String deviceGeneratedId = GenerateId.getDriverId("Device"+driverId+driver.getDriverEmail());
        Device device = new Device();
        device.setDeviceId(deviceGeneratedId);
        device.setDeviceModel("####987");
        String vehicleId = driver.getVehicleDocumentId();
        String documentId = driver.getDriverDocumentId();

        if(!vehicleId.toLowerCase().equals(vehicleDocumentID.toLowerCase()) || !documentId.toLowerCase().equals(driverdocumentId.toLowerCase())) {
            return new ResponseEntity<>("driverdocumentId or  vehicleDocumentID is not associated with this driver id = "+driverId, responseHeaders, HttpStatus.NOT_FOUND);
        }else{
            driver.setVerified(true);
            stageCompleted = true;
            device.setManufacturedDate(new Date(System.currentTimeMillis()));

            deviceDao.saveDevice(device);
            driver.setDeviceId(deviceGeneratedId);
        }
        if(driver.getStageNumber() <= 7 && stageCompleted){
            driver.setStageNumber(8);
            updateStage(driver);
        }
        //driverDao.saveDriver(driver);

        //String sessionId = getsessionToken(driverId);
        String token = getJwtToken(driverId);
        responseHeaders.set("token", token);
        //responseHeaders.set("rt_d_session", sessionId);
        //responseHeaders.set("driver_id", driverId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setDriver(driver);
        apiResponse.setDriverId(driverId);
        apiResponse.setEmail(driver.getDriverEmail());
        apiResponse.setCurrentStage(driver.getStageNumber());
        apiResponse.setDevice(device);
        if(apiResponse.getCurrentStage() > 2){
            setStage(apiResponse);
        }
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(apiResponse);
    }

    public ResponseEntity initiateShipment(DeviceShipping deviceShipping, String driverId, String deviceId){
        HttpHeaders responseHeaders = new HttpHeaders();
        Driver driver = driverDao.fetchDriverById(driverId);
        if (driver.getStageNumber() < 8)
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0012);
        if(driver.getStageNumber() > 9){
            return new ResponseEntity<>("device already shipped for device id  = "+deviceId+ " and associated with driver Id"+driverId, responseHeaders, HttpStatus.UNAUTHORIZED);
        }
        String shipmentId = GenerateId.getDriverId("shipment"+driverId+deviceId);
        deviceShipping.setShipmentId(shipmentId);
        String externalTrackerReference = GenerateId.getDriverId("shipment"+driverId+deviceId+shipmentId);
        deviceShipping.setExternalTrackerReference(externalTrackerReference);
        String device = driver.getDeviceId();
        if(device.toLowerCase().equals(deviceId.toLowerCase())){
            deviceShipping.setDeviceId(deviceId);
            deviceShipping.setDriverId(driverId);
            deviceShippingDao.saveShipment(deviceShipping);
            if(driver.getStageNumber() <= 8){
                driver.setStageNumber(9);
                updateStage(driver);
            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDriver(driver);
            apiResponse.setDriverId(driverId);
            apiResponse.setEmail(driver.getDriverEmail());
            apiResponse.setCurrentStage(driver.getStageNumber());

            apiResponse.setDeviceShipping(deviceShipping);
            apiResponse.setDeviceShipped(true);
            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(apiResponse);
        }else{
            return new ResponseEntity<>("device is not associated with this driver id = "+driverId, responseHeaders, HttpStatus.NOT_FOUND);

        }

    }

    public ResponseEntity activateDevice(String deviceId , String driverEmail){
        try {
            HttpHeaders responseHeaders = new HttpHeaders();
            String driverId = GenerateId.getDriverId(driverEmail);
            Driver driver = driverDao.fetchDriverById(driverId);
            if (driver.getStageNumber() < 9)
                return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0013);
            if(!deviceDao.isDeviceExist(deviceId))
                return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0014);
            if(driver.getStageNumber() >= 10){
                return new ResponseEntity<>("device already activated for device id  = "+deviceId+ " and associated with driver Id"+driverId, responseHeaders, HttpStatus.UNAUTHORIZED);
            }
            Device device = deviceDao.fetchDeviceById(deviceId);
            device.setActivated(true);
            DeviceShipping deviceShipping = deviceShippingDao.findByDeviceId(deviceId);
            deviceShipping.setDeliveredDate(new Date(System.currentTimeMillis()));
            deviceShippingDao.saveShipment(deviceShipping);
            deviceDao.saveDevice(device);
            String activationLink = baseUrl+"/activate/driver/"+driverId;
            sendActivationLink(driverEmail,activationLink);
            if(driver.getStageNumber() <= 9){
                driver.setStageNumber(10);
                updateStage(driver);
            }
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDriver(driver);
            apiResponse.setDriverId(driverId);
            apiResponse.setEmail(driver.getDriverEmail());
            apiResponse.setDeviceShipping(deviceShipping);
            apiResponse.setDevice(device);
            apiResponse.setCurrentStage(driver.getStageNumber());

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(apiResponse);
        }catch (Exception e){
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0014);
        }
    }

    public StageResponse sendActivationLink(String driverEmail, String link){
        StageResponse response = new StageResponse();
        if (Utilities.isValidEmail(driverEmail)) {

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            HttpEntity<?> entity = new HttpEntity<>(headers);
            Map<String, String> vars = new HashMap<>();
            vars.put("mailId", driverEmail);

            contactManager.sendActivationEmail(driverEmail, link);
            if (driverDao.isDriverEmailExists(driverEmail)) {
                //action :: send otp to mail

                response.setResponse(ResponseCreator.createSuccessResponse("Registration link sent to " + driverEmail));
            } else {
                response.setResponse(ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0002));
            }
        } else {
            response.setResponse(ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0001));
        }
        return response;
    }

    public ResponseEntity activateDriver(String driverId){
        boolean existingDriver = driverDao.existsDriverById(driverId);
        HttpHeaders responseHeaders = new HttpHeaders();
        Driver driver = driverDao.fetchDriverById(driverId);
        driver.setActivated(true);
        if(driver.getStageNumber() <= 10){
            driver.setStageNumber(11);
            updateStage(driver);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setDriver(driver);
        apiResponse.setDeviceShipped(true);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(apiResponse);

    }

    private ApiResponse setStage(ApiResponse apiResponse){
        switch (apiResponse.getCurrentStage()){
            case 8:
                apiResponse.setDevice(deviceDao.fetchDeviceById(apiResponse.getDriver().getDeviceId()));
                break;
            case 9:
                apiResponse.setDeviceShipping(deviceShippingDao.findByDeviceId(apiResponse.getDriver().getDeviceId()));
                break;

            case 10:
                apiResponse.setDeviceShipping(apiResponse.getDeviceShipping());
                break;
        }
        return apiResponse;
    }


}
