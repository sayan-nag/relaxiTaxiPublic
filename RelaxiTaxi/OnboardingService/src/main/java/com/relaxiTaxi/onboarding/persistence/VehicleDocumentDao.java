package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.VehicleDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleDocumentDao {

    @Autowired
    private VehicleDocumentsRepository vehicleDocumentsRepository;

    public VehicleDocuments fetchVehicleDocumentsById(String vehicleDocumentId) {
        return vehicleDocumentsRepository.findById(vehicleDocumentId).get();
    }


    public VehicleDocuments saveDriversVehicleDocument(VehicleDocuments vehicleDocuments) {
        return vehicleDocumentsRepository.save(vehicleDocuments);
    }

    public boolean isVehicleDocumentExist(String vehicleDocumentId) {
        return vehicleDocumentsRepository.existsById(vehicleDocumentId);
    }
}
