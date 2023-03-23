import com.relaxiTaxi.onboarding.common.ApiResponse;
import com.relaxiTaxi.onboarding.common.CustomResponseBuilder;
import com.relaxiTaxi.onboarding.datamodel.Device;
import com.relaxiTaxi.onboarding.datamodel.DeviceShipping;
import com.relaxiTaxi.onboarding.datamodel.Driver;
import com.relaxiTaxi.onboarding.persistence.DeviceShippingDao;
import com.relaxiTaxi.onboarding.persistence.DriverDao;
import com.relaxiTaxi.onboarding.service.AdminService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServiceTest {

    @InjectMocks
    AdminService adminService;

    @Mock
    DriverDao driverDao;
    @Mock
    DeviceShippingDao deviceShippingDao;

    Driver driver = new Driver();

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
    public void initiateShipmentTest() throws IOException {

        driver.setStageNumber(8);
        when(driverDao.fetchDriverById("e23456")).thenReturn(driver);

        String deviceId = "6b4f531a9a28e8e4bb50e2d324bb694b";
        String driverId = "e23456";
        DeviceShipping deviceShipping = new DeviceShipping();
        deviceShipping.setShippedDate(new Date(System.currentTimeMillis()));
        deviceShipping.setCourierName("dtdc");
        when(driverDao.fetchDriverById("e23456")).thenReturn(driver);
        when(deviceShippingDao.saveShipment(deviceShipping)).thenReturn(deviceShipping);
        ResponseEntity initiateShipment = adminService.initiateShipment(deviceShipping,  driverId, deviceId);
        assertEquals(initiateShipment.getStatusCode(), HttpStatus.OK);
        Object body = initiateShipment.getBody();
        ApiResponse apiResponse = (ApiResponse) initiateShipment.getBody();
        assertEquals("e23456", apiResponse.getDriver().getDriverId());
        assertEquals(new Date(System.currentTimeMillis()).toString().compareTo(apiResponse.getDeviceShipping().getShippedDate().toString()), 0);

        //negative use case for stage
        //CustomResponseBuilder.ErrorCode.RT0012
        driver.setStageNumber(7);
        driver.setDeviceId("6b4f531a9a28e8e4bb50e2d324bb694b");
        //when(dao.fetchDriverById("e23457")).thenReturn(driver);
        ResponseEntity initiateShipmentTest = adminService.initiateShipment(deviceShipping,  driverId, deviceId);
        String response = (String) initiateShipmentTest.getBody();
        assertEquals(CustomResponseBuilder.getErrorCodes(CustomResponseBuilder.ErrorCode.RT0012), response);
    }

    @Test
    public void verifyDocumentTest() throws URISyntaxException, InstantiationException, IllegalAccessException {
        driver.setStageNumber(6);
        Device device = new Device();
        device.setDeviceId("D@345");
        device.setDeviceModel("####987");
        when(driverDao.fetchDriverById("e23456")).thenReturn(driver);
        ResponseEntity stageErrorResponse = adminService.verifyDocument("6b4f531a9a28e8e4bb50e2d324bb694b",  "e23456", "6b4f531a9a28e8e4bb50e2d324bb694b");

        assertEquals(CustomResponseBuilder.getErrorCodes(CustomResponseBuilder.ErrorCode.RT0011), stageErrorResponse.getBody());

    }
}
