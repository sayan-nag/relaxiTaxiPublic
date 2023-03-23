package com.relaxiTaxi.onboarding.rest;

import com.relaxiTaxi.onboarding.common.CustomResponseBuilder;
import com.relaxiTaxi.onboarding.common.DocumentType;
import com.relaxiTaxi.onboarding.common.GenerateId;
import com.relaxiTaxi.onboarding.common.ResponseCreator;
import com.relaxiTaxi.onboarding.datamodel.DeviceShipping;
import com.relaxiTaxi.onboarding.datamodel.DriverDocuments;
import com.relaxiTaxi.onboarding.datamodel.VehicleDocuments;
import com.relaxiTaxi.onboarding.persistence.VehicleDocumentDao;
import com.relaxiTaxi.onboarding.service.DriverService;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/vehicle/document"})
public class VehicleDocumentRegistrationController {

    @Autowired
    ServiceManager serviceManager;

    @PostMapping("/information")
    ResponseEntity collectDriversDocument(@Valid @RequestBody VehicleDocuments vehicleDocuments, @RequestHeader("driver_id") String driverId) {
        String vehicleDocumentId = GenerateId.getDriverId("vehicle"+driverId);
        vehicleDocuments.setVehicleDocumentId(vehicleDocumentId);
        vehicleDocuments.setDriverId(driverId);
        return serviceManager.uploadVehicleDocumentInformation(vehicleDocuments, driverId);
    }

    @PostMapping("upload/{vehicleDocumentId}/{type}")
    ResponseEntity getDriverByID(@RequestParam("file") MultipartFile file, @PathVariable("vehicleDocumentId") String vehicleDocumentId,
                                 @PathVariable("type") String type, @RequestHeader("driver_id") String driverId) {
        if(!DocumentType.documentSet.contains(type)){
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0006);
        }
        return serviceManager.uploadVehicleDocument(file, driverId, vehicleDocumentId, type);

    }

}
