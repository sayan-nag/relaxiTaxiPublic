package com.relaxiTaxi.onboarding.rest;

import com.relaxiTaxi.onboarding.datamodel.DeviceShipping;
import com.relaxiTaxi.onboarding.service.AdminService;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/admin/device"})
public class DeviceController {

    @Autowired
    ServiceManager serviceManager;
    @Autowired
    AdminService adminService;


    @PostMapping("/{driverdocumentId}/{vehicleDocumentID}")
    ResponseEntity showUploadedDocument(@PathVariable("driverdocumentId") String driverdocumentId, @RequestHeader("driver_id") String driverId, @PathVariable("vehicleDocumentID") String vehicleDocumentID) {
        return adminService.verifyDocument(driverdocumentId, driverId, vehicleDocumentID);
    }

}
