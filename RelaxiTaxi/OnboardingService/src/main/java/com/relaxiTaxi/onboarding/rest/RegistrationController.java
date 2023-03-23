package com.relaxiTaxi.onboarding.rest;


import com.relaxiTaxi.onboarding.datamodel.Driver;
import com.relaxiTaxi.onboarding.datamodel.DriverAddress;
import com.relaxiTaxi.onboarding.persistence.TestRepository;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/register"})
public class RegistrationController {

    @Autowired
    ServiceManager serviceManager;

    @GetMapping("/init/{email}")
    ResponseEntity firstContact(@PathVariable String email) {
        return serviceManager.init("1", 1, email);
    }

    @PostMapping("/auth/{email}/{otp}")
    ResponseEntity otpValidation(@PathVariable String otp, @PathVariable String email){
        Map<String, String> vars = new HashMap<>();
        vars.put("mailId", email);
        vars.put("otp", otp);
        return serviceManager.validatedOtp(vars);
    }
    @GetMapping("/login")
    ResponseEntity login(@RequestHeader("driver_id") String driverId ){
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("logged in as a user, Welcome driver"+ driverId);
    }

    /*
    @PostMapping("/driver/demographics/information")
    ResponseEntity basicInformation( @Valid @RequestBody Driver driver, @RequestHeader("driver_id") String driverId) {
        driver.setDriverId(driverId);
        return serviceManager.uploadDriverInformation(driver);
    }

    @PostMapping("/driver/demographics/address")
    ResponseEntity addressInformation( @Valid @RequestBody DriverAddress driverAddress, @RequestHeader("driver_id") String driverId) {
        return serviceManager.uploadDriverDemographicsInformation(driverAddress, driverId);

    }

     */

}
