package com.relaxiTaxi.onboarding.service;

import com.relaxiTaxi.onboarding.common.*;
import com.relaxiTaxi.onboarding.datamodel.*;
import com.relaxiTaxi.onboarding.persistence.*;
import com.relaxiTaxi.onboarding.stage.firstContact.AuthManager;
import com.relaxiTaxi.onboarding.stage.firstContact.ContactManager;
import com.relaxiTaxi.onboarding.utils.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class ServiceManager {

    private static final String warehouseLocation = "D:\\intuit\\document_warehouse\\";
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




    public ResponseEntity init(String version, int stageId, String email) {
        // StageManager<String> contactStage = StageVersionFactory.getStageManager(version, stageId);
        return contactManager.performStageActions(0, email).getResponse();
    }

    public String getJwtToken(String driverId){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("driver_id", driverId);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        String token = restTemplate.exchange("http://localhost:8084/token/create", HttpMethod.GET, entity, String.class).getBody();
        return token;
    }

    public String getsessionToken(String driverId){

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("driver_id", driverId);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate = new RestTemplate();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("driver_id", driverId);
        restTemplate = new RestTemplate();

        String sessionId = restTemplate.exchange("http://localhost:8083/session/create", HttpMethod.GET, entity, String.class).getBody();

        return sessionId;
    }

    public void updateStage(Driver driver){
        driverDao.saveDriver(driver);
    }

    public ResponseEntity validatedOtp(Map<String, String> vars) {
        HttpHeaders responseHeaders = new HttpHeaders();
        boolean val = authManager.performStageActionsTest(vars);

        if (val) {
            //create an entry if not exists
            String driverId = GenerateId.getDriverId(vars.get("mailId"));
            Driver driver = new Driver();
            boolean existingDriver = driverDao.existsDriverById(driverId);
            if (!existingDriver) {
                driver.setDriverId(driverId);
                driver.setDriverEmail(vars.get("mailId"));

                driverDao.saveDriver(driver);
            }else{
                driver = driverDao.fetchDriverById(driverId);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("driver_id", driverId);

            HttpEntity<?> entity = new HttpEntity<>(headers);
            restTemplate = new RestTemplate();

            String sessionId = restTemplate.exchange("http://localhost:8083/session/create", HttpMethod.GET, entity, String.class).getBody();
            String token = restTemplate.exchange("http://localhost:8084/token//create", HttpMethod.GET, entity, String.class).getBody();
            //get session id and jwt token
            responseHeaders.set("token", token);
            responseHeaders.set("rt_d_session", sessionId);
            responseHeaders.set("driver_id", driverId);
            if(driver.getStageNumber() <= 2){
                driver.setStageNumber(2);
                updateStage(driver);
            }

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDriver(driver);
            apiResponse.setDriverId(driverId);
            apiResponse.setEmail(vars.get("mailId"));
            apiResponse.setCurrentStage(driver.getStageNumber());
            if(apiResponse.getCurrentStage() > 2){
                setStage(apiResponse);
            }

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(apiResponse);
        } else {
            return new ResponseEntity<>("Not a valid OTP please recheck and verify", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity uploadDriverInformation(Driver driver) {
        Driver driverDb = driverDao.fetchDriverById(driver.getDriverId());
        driverDb.setDriverName(driver.getDriverName());
        driverDb.setDriverSurname(driver.getDriverSurname());
        driverDb.setDriverMobile(driver.getDriverMobile());
        //driver.setDriverEmail(driverDao.fetchDriverById(driver.getDriverId()).getDriverEmail());
        driverDao.saveDriver(driverDb);
        HttpHeaders responseHeaders = new HttpHeaders();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("driver_id", driver.getDriverId());

        HttpEntity<?> entity = new HttpEntity<>(headers);
        restTemplate = new RestTemplate();
        if(driver.getStageNumber() < 2)
            driver.setStageNumber(2);
        String sessionId = restTemplate.exchange("http://localhost:8083/session/create", HttpMethod.GET, entity, String.class).getBody();
        String token = restTemplate.exchange("http://localhost:8084/token//create", HttpMethod.GET, entity, String.class).getBody();
        responseHeaders.set("token", token);
        responseHeaders.set("rt_d_session", sessionId);
        responseHeaders.set("driver_id", driver.getDriverId());

        //HttpHeaders responseHeaders = new HttpHeaders();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setDriver(driver);
        apiResponse.setDriverId(driver.getDriverId());
        apiResponse.setEmail(driver.getDriverEmail());
        apiResponse.setCurrentStage(driver.getStageNumber());
        if(apiResponse.getCurrentStage() > 2){
            setStage(apiResponse);
        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(apiResponse);
    }



    public ResponseEntity uploadDriverDemographicsInformation(DriverAddress driverAddress, String driverId) {
        driverAddress.setDriverId(driverId);

        driverAddressDao.saveDriverDemographic(driverAddress);
        HttpHeaders responseHeaders = new HttpHeaders();

        String sessionId = getsessionToken(driverId);
        String token = getJwtToken(driverId);
        responseHeaders.set("token", token);
        responseHeaders.set("rt_d_session", sessionId);
        responseHeaders.set("driver_id", driverId);

        //HttpHeaders responseHeaders = new HttpHeaders();
        ApiResponse apiResponse = new ApiResponse();
        Driver driver = driverDao.fetchDriverById(driverId);
        apiResponse.setDriver(driver);
        apiResponse.setDriverId(driverId);
        apiResponse.setEmail(driver.getDriverEmail());
        if(driver.getStageNumber() <= 2)
            driver.setStageNumber(3);
        apiResponse.setCurrentStage(driver.getStageNumber());
        updateStage(driver);
        if(apiResponse.getCurrentStage() > 2){
            setStage(apiResponse);
        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(apiResponse);
    }

    public ResponseEntity uploadDriverPersonalDocumentInformation(DriverDocuments driverDocuments, String driverId){
        DriverDocuments driverDocumentsDb = driverDocumentDao.saveDriverDocument(driverDocuments);
        Driver driver = driverDao.fetchDriverById(driverId);
        driver.setDriverDocumentId(driverDocumentsDb.getDriverDocumentId());
        if (driver.getStageNumber() >= 8 )
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0010);
        if(driver.getStageNumber() <= 3)
            driver.setStageNumber(4);
        //driver.setStageNumber(4);
        HttpHeaders responseHeaders = new HttpHeaders();
        String sessionId = getsessionToken(driverId);
        String token = getJwtToken(driverId);
        responseHeaders.set("token", token);
        responseHeaders.set("rt_d_session", sessionId);
        responseHeaders.set("driver_id", driverId);

        //HttpHeaders responseHeaders = new HttpHeaders();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setDriver(driver);
        apiResponse.setDriverId(driverId);
        apiResponse.setEmail(driver.getDriverEmail());
        apiResponse.setCurrentStage(driver.getStageNumber());
        apiResponse.setDriverDocuments(driverDocuments);
        updateStage(driver);
        if(apiResponse.getCurrentStage() > 2){
            setStage(apiResponse);
        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(apiResponse);
    }

    public ResponseEntity uploadDriverDocument(MultipartFile file, String driverId, String documentId, String documentType){
        Driver driver = driverDao.fetchDriverById(driverId);
        HttpHeaders responseHeaders = new HttpHeaders();
        String documentFolder = warehouseLocation + documentId;
        if (driver.getStageNumber() >= 8 )
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0010);
        File documentDirectory = new File(documentFolder);
        try {
            String id = driver.getDriverDocumentId();
            if(!id.toLowerCase().equals(documentId))
                throw new FileNotFoundException();

            if (!documentDirectory.exists()) {
                Files.createDirectory(Paths.get(documentFolder));
            }
            String extension[] = file.getOriginalFilename().split("\\.");
            String fileLocation = documentFolder + "\\" + documentType + "."+extension[1];
            File documentFile = new File(fileLocation);
            if (documentFile.exists()) {
                Files.delete(Paths.get(documentFile.toURI()));
            }
            Files.write(Paths.get(documentFile.toURI()), file.getBytes());
            DocumentIndexReference documentIndexReference = null;
            if(documentIndexDao.isDocumentIndexReferenceExist(documentId))
                documentIndexReference = documentIndexDao.fetchDocumentIndexReferenceById(documentId);
            else
                documentIndexReference = new DocumentIndexReference();
            documentIndexReference.setDocumentReferenceId(documentId);
            uploadDriverIndex(documentIndexReference, documentType, fileLocation);
            boolean stageCompleted = isDriverDocumentUploadStageCompleted(documentId);



            if(driver.getStageNumber() <= 4)
                driver.setStageNumber(4);
            //driver.setStageNumber(4);
            updateStage(driver);
            if(stageCompleted && driver.getStageNumber() <= 5){
                driver.setStageNumber(5);
                updateStage(driver);
            }


            String sessionId = getsessionToken(driverId);
            String token = getJwtToken(driverId);
            responseHeaders.set("token", token);
            responseHeaders.set("rt_d_session", sessionId);
            responseHeaders.set("driver_id", driverId);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDriver(driver);
            apiResponse.setDriverId(driverId);
            apiResponse.setEmail(driver.getDriverEmail());
            apiResponse.setCurrentStage(driver.getStageNumber());

            apiResponse.setDriverDocuments(driverDocumentDao.fetchDriverDocumentsById(documentId));

            if(apiResponse.getCurrentStage() > 2){
                setStage(apiResponse);
            }

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(apiResponse);

        }catch (FileNotFoundException e){
            return new ResponseEntity<>("Document Id is not associated with this driver id = "+driverId, responseHeaders, HttpStatus.NOT_FOUND);
        }
        catch (Exception e){
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0007);
        }

    }


    public ResponseEntity uploadVehicleDocumentInformation(VehicleDocuments vehicleDocuments, String driverId){
        try
        {
            Driver driver = driverDao.fetchDriverById(driverId);
            if (driver.getStageNumber() < 5)
                return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0008);
            if (driver.getStageNumber() >= 8 )
                return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0010);
            VehicleDocuments vehicleDocumentsDb = vehicleDocumentDao.saveDriversVehicleDocument(vehicleDocuments);

            driver.setVehicleDocumentId(vehicleDocumentsDb.getVehicleDocumentId());
            driverDao.saveDriver(driver);
            if (driver.getStageNumber() <= 5)
                driver.setStageNumber(6);
            //driver.setStageNumber(4);
            HttpHeaders responseHeaders = new HttpHeaders();
            //String sessionId = getsessionToken(driverId);
            String token = getJwtToken(driverId);
            responseHeaders.set("token", token);
            //responseHeaders.set("rt_d_session", sessionId);
            responseHeaders.set("driver_id", driverId);

            //HttpHeaders responseHeaders = new HttpHeaders();
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDriver(driver);
            apiResponse.setDriverId(driverId);
            apiResponse.setEmail(driver.getDriverEmail());
            apiResponse.setCurrentStage(driver.getStageNumber());
            apiResponse.setVehicleDocuments(vehicleDocumentsDb);
            updateStage(driver);
            if (apiResponse.getCurrentStage() > 2) {
                setStage(apiResponse);
            }

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(apiResponse);
        }catch (Exception e){
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0006);
        }
    }


    public ResponseEntity uploadVehicleDocument(MultipartFile file, String driverId, String documentId, String documentType){
        Driver driver = driverDao.fetchDriverById(driverId);
        HttpHeaders responseHeaders = new HttpHeaders();
        if (driver.getStageNumber() < 6)
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0009);
        if (driver.getStageNumber() >= 8 )
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0010);
        String documentFolder = warehouseLocation + documentId;
        File documentDirectory = new File(documentFolder);
        try {
            String id = driver.getVehicleDocumentId();
            if(!id.toLowerCase().equals(documentId))
                throw new DocumentMissMatchException();

            if (!documentDirectory.exists()) {
                Files.createDirectory(Paths.get(documentFolder));
            }
            String extension[] = file.getOriginalFilename().split("\\.");
            String fileLocation = documentFolder + "\\" + documentType + "."+extension[1];
            File documentFile = new File(fileLocation);
            if (documentFile.exists()) {
                Files.delete(Paths.get(documentFile.toURI()));
            }
            Files.write(Paths.get(documentFile.toURI()), file.getBytes());
            DocumentIndexReference documentIndexReference = null;
            if(documentIndexDao.isDocumentIndexReferenceExist(documentId))
                documentIndexReference = documentIndexDao.fetchDocumentIndexReferenceById(documentId);
            else
                documentIndexReference = new DocumentIndexReference();
            documentIndexReference.setDocumentReferenceId(documentId);
            uploadDriverIndex(documentIndexReference, documentType, fileLocation);
            boolean stageCompleted = isVehicleDocumentUploadStageCompleted(documentId);

            if(driver.getStageNumber() <= 6 && stageCompleted){
                driver.setStageNumber(7);
                updateStage(driver);
            }


            String sessionId = getsessionToken(driverId);
            String token = getJwtToken(driverId);
            responseHeaders.set("token", token);
            responseHeaders.set("rt_d_session", sessionId);
            responseHeaders.set("driver_id", driverId);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setDriver(driver);
            apiResponse.setDriverId(driverId);
            apiResponse.setEmail(driver.getDriverEmail());
            apiResponse.setCurrentStage(driver.getStageNumber());
            apiResponse.setVehicleDocuments(vehicleDocumentDao.fetchVehicleDocumentsById(documentId));
            //apiResponse.setDriverDocuments(driverDocumentDao.fetchDriverDocumentsById(documentId));

            if(apiResponse.getCurrentStage() > 2){
                setStage(apiResponse);
            }

            return ResponseEntity.ok()
                    .headers(responseHeaders)
                    .body(apiResponse);

        }catch (DocumentMissMatchException e){
            return new ResponseEntity<>("Vehicle Document Id is not associated with this driver id = "+driverId, responseHeaders, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0007);
        }

    }



    public ResponseEntity getAdminToken(String email){
        String Id = GenerateId.getDriverId(email);
        if(!adminDao.existAdmin(Id))
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0014);
        Admin admin = adminDao.fetchByID(Id);
        HttpHeaders responseHeaders = new HttpHeaders();
        String token = getJwtToken(Id);
        responseHeaders.set("token", token);
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("session sent");
    }

    private ApiResponse setStage(ApiResponse apiResponse){
        switch (apiResponse.getCurrentStage()){
            case 3:
                apiResponse.setDriverAddress(driverAddressDao.getDriverAddressById(apiResponse.getDriverId()));
                break;
            case 4:
                apiResponse.setDriverDocuments(driverDocumentDao.fetchDriverDocumentsById(apiResponse.getDriver().getDriverDocumentId()));
                break;
            case 5:
                if(Objects.isNull(apiResponse.getDriverDocuments())){
                    apiResponse.setDriverDocuments(driverDocumentDao.fetchDriverDocumentsById(apiResponse.getDriver().getVehicleDocumentId()));
                }
                break;
            case 6:
                apiResponse.setVehicleDocuments(vehicleDocumentDao.fetchVehicleDocumentsById(apiResponse.getDriver().getVehicleDocumentId()));
                break;
            case 7:
                if(Objects.isNull(apiResponse.getDriverDocuments())){
                    apiResponse.setVehicleDocuments(vehicleDocumentDao.fetchVehicleDocumentsById(apiResponse.getDriver().getVehicleDocumentId()));
                }
                break;
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

    private void uploadDriverIndex(DocumentIndexReference documentIndexReference, String documentType, String filePath){
        //documentIndexDao.fetchDocumentIndexReferenceById()
        switch (documentType){
            case "PAN":
                documentIndexReference.setUploadedPanPathReference(filePath);
                break;
            case "AADHAAR":
                documentIndexReference.setUploadedAadhaarPathReference(filePath);
                break;
            case "DRIVING_LICENSE":
                documentIndexReference.setUploadedDLPathReference(filePath);
                break;
            case "REGISTRATION":
                documentIndexReference.setUploadedRegPathReference(filePath);
                break;
            case "INSURANCE":
                documentIndexReference.setUploadedInsurancePathReference(filePath);
                break;

        }
        documentIndexDao.saveDocumentIndexReference(documentIndexReference);
    }

    private boolean isDriverDocumentUploadStageCompleted(String documentId){
        DocumentIndexReference documentIndexReference = documentIndexDao.fetchDocumentIndexReferenceById(documentId);
        if(documentIndexReference.getUploadedAadhaarPathReference() == null || documentIndexReference.getUploadedPanPathReference() == null)
            return false;
        if(documentIndexReference.getUploadedAadhaarPathReference().isEmpty() || documentIndexReference.getUploadedPanPathReference().isEmpty())
            return false;
        return true;
    }

    private boolean isVehicleDocumentUploadStageCompleted(String documentId){
        DocumentIndexReference documentIndexReference = documentIndexDao.fetchDocumentIndexReferenceById(documentId);
        //
        if(documentIndexReference.getUploadedDLPathReference() == null || documentIndexReference.getUploadedRegPathReference() == null ||
                documentIndexReference.getUploadedInsurancePathReference() == null){
            return false;
        }

        if(documentIndexReference.getUploadedDLPathReference().isEmpty() || documentIndexReference.getUploadedRegPathReference().isEmpty()
                || documentIndexReference.getUploadedInsurancePathReference().isEmpty())
            return false;
        return true;
    }


    public Driver testDriverInformation(Driver driver) {
        Driver driverDb = driverDao.fetchDriverById(driver.getDriverId());
        driverDb.setDriverName(driver.getDriverName());
        driverDb.setDriverSurname(driver.getDriverSurname());
        driverDb.setDriverMobile(driver.getDriverMobile());
        return driverDb;
    }

}




