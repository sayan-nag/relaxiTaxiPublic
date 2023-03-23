package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.DeviceShipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceShippingRepository extends JpaRepository<DeviceShipping, String> {
    boolean existsById(String documentReferenceId);
    DeviceShipping findByDeviceId(String deviceId);
}
