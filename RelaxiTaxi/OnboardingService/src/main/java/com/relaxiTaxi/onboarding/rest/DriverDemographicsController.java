package com.relaxiTaxi.onboarding.rest;

import com.relaxiTaxi.onboarding.datamodel.Driver;
import com.relaxiTaxi.onboarding.datamodel.DriverAddress;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/driver"})
public class DriverDemographicsController {

    @Autowired
    ServiceManager serviceManager;
    @PostMapping("/demographics/information")
    ResponseEntity basicInformation(@Valid @RequestBody Driver driver, @RequestHeader("driver_id") String driverId) {
        driver.setDriverId(driverId);
        return serviceManager.uploadDriverInformation(driver);
    }

    @PostMapping("/demographics/address")
    ResponseEntity addressInformation(@Valid @RequestBody DriverAddress driverAddress, @RequestHeader("driver_id") String driverId) {
        return serviceManager.uploadDriverDemographicsInformation(driverAddress, driverId);

    }
}
