package com.relaxiTaxi.onboarding.rest;

import com.relaxiTaxi.onboarding.datamodel.DeviceShipping;
import com.relaxiTaxi.onboarding.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/admin/shipment"})
public class ShipmentController {

    @Autowired
    AdminService adminService;
    @PostMapping("/{deviceId}")
    ResponseEntity shipDevice(@Valid @RequestBody DeviceShipping deviceShipping, @PathVariable("deviceId") String deviceId,
                              @RequestHeader("driver_id") String driverId) {
        return adminService.initiateShipment(deviceShipping, driverId ,deviceId);
    }

    @PostMapping("/activate/device/{deviceId}")
    ResponseEntity shipDevice( @PathVariable("deviceId") String deviceId,
                               @RequestHeader("driver_email") String driverEmail) {
        return adminService.activateDevice( deviceId ,driverEmail);
    }

    /*@GetMapping("/admin/{adminEmail}")
    ResponseEntity getAdminToken( @PathVariable("adminEmail") String adminEmail) {
        return serviceManager.getAdminToken(adminEmail);
    }*/
}
