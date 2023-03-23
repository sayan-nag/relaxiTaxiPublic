package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class DriverDao {
    @Autowired
    private DriverRepository driverRepository;

    public Driver fetchDriverById(String driverId) throws NoSuchElementException {
        //return driverRepository.findById(driverId).orElseThrow(() -> new ResourceNotFoundException(String.format("%s not found with %s : '%s'", "driver", "driverID", driverId)));
        return driverRepository.findById(driverId).orElseThrow(
                ()
                        -> new NoSuchElementException(
                        "NO CUSTOMER PRESENT WITH ID = " + driverId));
    }

    public Driver saveDriver(Driver driver) {
        return driverRepository.save(driver);
    }

    public Driver updateDriverDocumentID(String driverId, String driverDocumentId) {
        Driver driverDb = driverRepository.findById(driverId).get();

        if (driverDb != null) {
            driverDb.setDriverDocumentId(driverDocumentId);
            driverRepository.save(driverDb);
        }
        return driverDb;
    }

    public Driver updateVehicleDocumentID(String driverId, String vehicleDocumentId) {
        Driver driverDb = driverRepository.findById(driverId).get();
        driverDb.setVehicleDocumentId(vehicleDocumentId);
        driverRepository.save(driverDb);
        return driverDb;
    }

    public boolean isDriverEmailExists(String driverEmail) {
        return driverRepository.existsByDriverEmail(driverEmail);
    }

    public void deleteDriverById(String departmentId) {

        driverRepository.deleteById(departmentId);
    }

    public boolean existsDriverById(String driverId) {
        return driverRepository.existsById(driverId);
    }




}
