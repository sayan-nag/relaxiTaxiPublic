package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.Driver;
import com.relaxiTaxi.onboarding.datamodel.DriverAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DriverAddressRepository extends JpaRepository<DriverAddress, String> {
    boolean existsById(String driverId);


}
