package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
    boolean existsById(String driverId);

    boolean existsByDriverEmail(String driverEmail);

    @Query("Select u from Driver u where u.driverEmail = ?1")
    Driver findByEmail(String driverEmail);


}
