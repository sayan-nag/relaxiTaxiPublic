package com.relaxiTaxi.onboarding.service;

import com.relaxiTaxi.onboarding.datamodel.Driver;

public interface DriverService {
    //read by ID
    Driver fetchDriverById(String driverId);

    // Save operation
    Driver saveDriver(Driver driver);

    // Update operation
    Driver updateDriverDocumentID(String driverId, String driverDocumentId);

    // Delete operation
    void deleteDriverById(String driverId);

    boolean existsDriverById(String driverId);

    Driver updateVehicleDocumentID(String driverId, String vehicleDocumentId);
}
