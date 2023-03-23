package com.relaxiTaxi.onboarding.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driver_address")
public class DriverAddress {

    @Id
    @Column(updatable = false, nullable = false)
    private String driverId;
    @Column(nullable = false)
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;

    @Column(nullable = false)
    private String state;
    @Column(nullable = false)
    private String district;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String pincode;

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public DriverAddress(String driverId, String addressLine1, String addressLine2, String addressLine3, String state, String district, String city, String pincode) {
        this.driverId = driverId;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.state = state;
        this.district = district;
        this.city = city;
        this.pincode = pincode;
    }

    public DriverAddress(){}
}
