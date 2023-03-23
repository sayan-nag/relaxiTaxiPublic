package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.DeviceShipping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceShippingDao {

    @Autowired
    DeviceShippingRepository deviceShippingRepository;
    public DeviceShipping fetchShippingById(String shipmentId){
        return deviceShippingRepository.findById(shipmentId).get();

    }

    public DeviceShipping saveShipment(DeviceShipping deviceShipping){
        return deviceShippingRepository.save(deviceShipping);
    }

    public DeviceShipping findByDeviceId(String deviceId){
        return deviceShippingRepository.findByDeviceId(deviceId);
    }

}
