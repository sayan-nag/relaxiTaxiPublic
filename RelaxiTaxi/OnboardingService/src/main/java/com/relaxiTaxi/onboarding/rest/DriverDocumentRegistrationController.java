package com.relaxiTaxi.onboarding.rest;

import com.relaxiTaxi.onboarding.common.CustomResponseBuilder;
import com.relaxiTaxi.onboarding.common.DocumentType;
import com.relaxiTaxi.onboarding.common.GenerateId;
import com.relaxiTaxi.onboarding.common.ResponseCreator;
import com.relaxiTaxi.onboarding.datamodel.DocumentIndexReference;
import com.relaxiTaxi.onboarding.datamodel.DriverDocuments;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/driver/document"})
public class DriverDocumentRegistrationController {

    @Autowired
    ServiceManager serviceManager;

    @PostMapping("/information")
    ResponseEntity collectDriversDocument(@Valid @RequestBody DriverDocuments driverDocuments, @RequestHeader("driver_id") String driverId) {
        String documentId = GenerateId.getDriverId("driver"+driverId);
        driverDocuments.setDriverId(driverId);
        driverDocuments.setDriverDocumentId(documentId);
        return serviceManager.uploadDriverPersonalDocumentInformation(driverDocuments, driverId);
        //return new ResponseEntity<>("",HttpStatus.OK);
    }

    @PostMapping("upload/{driverDocumentId}/{type}")
    ResponseEntity getDriverByID(@RequestParam("file") MultipartFile file,@PathVariable("driverDocumentId") String driverDocumentId,
                                 @PathVariable("type") String type, @RequestHeader("driver_id") String driverId) {
        if(!DocumentType.documentSet.contains(type)){
            return ResponseCreator.createErrorResponse(CustomResponseBuilder.ErrorCode.RT0006);
        }
        return serviceManager.uploadDriverDocument(file, driverId, driverDocumentId, type);

        //return new ResponseEntity<>("",HttpStatus.OK);

    }



    //test not clear



}
