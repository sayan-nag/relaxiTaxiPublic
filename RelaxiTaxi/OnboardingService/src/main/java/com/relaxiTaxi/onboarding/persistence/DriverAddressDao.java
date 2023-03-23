package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.DriverAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DriverAddressDao {

    @Autowired
    DriverAddressRepository driverAddressRepository;

    public DriverAddress getDriverAddressById(String driverId){
        return driverAddressRepository.findById(driverId).get();
    }

    public DriverAddress saveDriverDemographic(DriverAddress driverAddress){
        return driverAddressRepository.save(driverAddress);
    }
}
