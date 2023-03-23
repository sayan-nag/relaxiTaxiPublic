package com.relaxiTaxi.onboarding.service;

import com.relaxiTaxi.onboarding.datamodel.Driver;
import com.relaxiTaxi.onboarding.persistence.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class DriverServiceImpl implements DriverService {
    @Autowired
    private DriverRepository driverRepository;

    @Override
    public Driver fetchDriverById(String driverId) throws NoSuchElementException {
        //return driverRepository.findById(driverId).orElseThrow(() -> new ResourceNotFoundException(String.format("%s not found with %s : '%s'", "driver", "driverID", driverId)));
        return driverRepository.findById(driverId).orElseThrow(
                ()
                        -> new NoSuchElementException(
                        "NO CUSTOMER PRESENT WITH ID = " + driverId));
    }

    @Override
    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public Driver updateDriverDocumentID(String driverId, String driverDocumentId) {
        Driver driverDb = driverRepository.findById(driverId).get();

        if (driverDb != null) {
            driverDb.setDriverDocumentId(driverDocumentId);
            driverRepository.save(driverDb);
        }
        return driverDb;
    }

    @Override
    public Driver updateVehicleDocumentID(String driverId, String vehicleDocumentId) {
        Driver driverDb = driverRepository.findById(driverId).get();
        driverDb.setVehicleDocumentId(vehicleDocumentId);
        driverRepository.save(driverDb);
        return driverDb;
    }

    @Override
    public void deleteDriverById(String departmentId) {

        driverRepository.deleteById(departmentId);
    }

    @Override
    public boolean existsDriverById(String driverId) {
        return driverRepository.existsById(driverId);
    }

}
