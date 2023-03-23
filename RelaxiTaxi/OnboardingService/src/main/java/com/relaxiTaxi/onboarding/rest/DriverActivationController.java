package com.relaxiTaxi.onboarding.rest;

import com.relaxiTaxi.onboarding.service.AdminService;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/activate"})
public class DriverActivationController {

    @Autowired
    ServiceManager serviceManager;
    @Autowired
    AdminService adminService;

    @PutMapping("/driver/{driverId}")
    ResponseEntity activateDriver(@PathVariable("driverId") String driverId) {
        return adminService.activateDriver(driverId);
    }
}
