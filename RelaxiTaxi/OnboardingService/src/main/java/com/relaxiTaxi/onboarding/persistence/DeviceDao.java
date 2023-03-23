package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeviceDao {

    @Autowired
    private DeviceRepository deviceRepository;

    public Device fetchDeviceById(String deviceId) {
        return deviceRepository.findById(deviceId).get();
    }


    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    public boolean isDeviceExist(String deviceId) {
        return deviceRepository.existsById(deviceId);
    }
}
