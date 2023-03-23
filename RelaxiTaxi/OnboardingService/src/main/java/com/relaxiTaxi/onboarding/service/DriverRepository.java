package com.relaxiTaxi.onboarding.service;

import com.relaxiTaxi.onboarding.datamodel.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DriverRepository extends JpaRepository<Driver, String> {
}
