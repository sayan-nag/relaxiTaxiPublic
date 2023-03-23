package com.relaxiTaxi.onboarding.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.relaxiTaxi.onboarding.datamodel.*;
import org.springframework.boot.jackson.JsonComponent;

@JsonComponent
public class ApiResponse {

    private String email;
    private String driverId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int currentStage;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int nextStage;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Driver driver;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DriverAddress driverAddress;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DriverDocuments driverDocuments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VehicleDocuments vehicleDocuments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Device device;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DeviceShipping deviceShipping;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private boolean deviceShipped;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Admin admin;

    public boolean isDeviceShipped() {
        return deviceShipped;
    }

    public void setDeviceShipped(boolean deviceShipped) {
        this.deviceShipped = deviceShipped;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public int getCurrentStage() {
        return currentStage;
    }

    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public int getNextStage() {
        return nextStage;
    }

    public void setNextStage(int nextStage) {
        this.nextStage = nextStage;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public DriverAddress getDriverAddress() {
        return driverAddress;
    }

    public void setDriverAddress(DriverAddress driverAddress) {
        this.driverAddress = driverAddress;
    }

    public DriverDocuments getDriverDocuments() {
        return driverDocuments;
    }

    public void setDriverDocuments(DriverDocuments driverDocuments) {
        this.driverDocuments = driverDocuments;
    }

    public VehicleDocuments getVehicleDocuments() {
        return vehicleDocuments;
    }

    public void setVehicleDocuments(VehicleDocuments vehicleDocuments) {
        this.vehicleDocuments = vehicleDocuments;
    }

    public DeviceShipping getDeviceShipping() {
        return deviceShipping;
    }

    public void setDeviceShipping(DeviceShipping deviceShipping) {
        this.deviceShipping = deviceShipping;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }
}
