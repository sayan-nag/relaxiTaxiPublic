import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.relaxiTaxi.onboarding.common.ApiResponse;
import com.relaxiTaxi.onboarding.common.CustomResponseBuilder;
import com.relaxiTaxi.onboarding.datamodel.*;
import com.relaxiTaxi.onboarding.persistence.DeviceDao;
import com.relaxiTaxi.onboarding.persistence.DeviceShippingDao;
import com.relaxiTaxi.onboarding.persistence.DriverDao;
import com.relaxiTaxi.onboarding.persistence.DriverDocumentDao;
import com.relaxiTaxi.onboarding.service.AdminService;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.*;

import org.mockito.runners.MockitoJUnitRunner;

//import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.URISyntaxException;

import java.sql.Date;
import java.util.Collections;

import java.util.Map;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;





@RunWith(MockitoJUnitRunner.class)
public class ServiceManagerTest {

    @Mock
    DriverDao dao;
    @Mock
    DeviceDao deviceDao;

    @Mock
    DriverDocumentDao driverDocumentDao;
    @InjectMocks
    ServiceManager serviceManager;


    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    RestTemplate restTemplate;
    @InjectMocks
    AdminService adminService;
    Driver driver = new Driver();

    @Mock
    MultipartFile multipartFile;
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        driver.setDriverId("e23456");
        driver.setStageNumber(4);
        driver.setDriverMobile("987654321");
        driver.setDriverName("lokesh");
        driver.setDriverSurname("shaw");
        driver.setDeviceId("6b4f531a9a28e8e4bb50e2d324bb694b");
    }


    @Test
    public void uploadDriverPersonalDocumentInformationTest(){
        driver.setStageNumber(8);
        DriverDocuments driverDocuments = new DriverDocuments();
        driverDocuments.setDriverDocumentId("d23456");
        when(dao.fetchDriverById("e23456")).thenReturn(driver);
        when(driverDocumentDao.saveDriverDocument(driverDocuments)).thenReturn(driverDocuments);

        ResponseEntity responseEntity = serviceManager.uploadDriverPersonalDocumentInformation(driverDocuments, "e23456");

        assertEquals(CustomResponseBuilder.getErrorCodes(CustomResponseBuilder.ErrorCode.RT0010), responseEntity.getBody());
    }

    @Test
    public void uploadDriverDocumentTest() throws FileNotFoundException {

        driver.setStageNumber(7);
        driver.setDriverDocumentId("d23456");
        when(dao.fetchDriverById("e23456")).thenReturn(driver);
        ResponseEntity responseEntity = serviceManager.uploadDriverDocument(multipartFile, "e23456","d23457", "AADHAAR");
        String result = "Document Id is not associated with this driver id = "+"e23456";
        assertEquals(result, responseEntity.getBody());
        driver.setStageNumber(8);
        responseEntity = serviceManager.uploadDriverDocument(multipartFile, "e23456","d23456", "AADHAAR");
        assertEquals(CustomResponseBuilder.getErrorCodes(CustomResponseBuilder.ErrorCode.RT0010), responseEntity.getBody());
    }

    @Test
    public void uploadVehicleDocumentInformation(){
        when(dao.fetchDriverById("e23456")).thenReturn(driver);
        VehicleDocuments vehicleDocuments = new VehicleDocuments();
        ResponseEntity responseEntity = serviceManager.uploadVehicleDocumentInformation(vehicleDocuments,"e23456");
        assertEquals(CustomResponseBuilder.getErrorCodes(CustomResponseBuilder.ErrorCode.RT0008), responseEntity.getBody());
        driver.setStageNumber(8);
        responseEntity = serviceManager.uploadDriverDocument(multipartFile, "e23456","d23456", "AADHAAR");
        assertEquals(CustomResponseBuilder.getErrorCodes(CustomResponseBuilder.ErrorCode.RT0010), responseEntity.getBody());
    }

    @Test
    public void uploadVehicleDocument(){
        driver.setStageNumber(7);
        driver.setVehicleDocumentId("d23456");
        when(dao.fetchDriverById("e23456")).thenReturn(driver);
        ResponseEntity responseEntity = serviceManager.uploadVehicleDocument(multipartFile, "e23456","d23457", "AADHAAR");
        String result = "Vehicle Document Id is not associated with this driver id = "+"e23456";
        assertEquals(result, responseEntity.getBody());
    }

}
