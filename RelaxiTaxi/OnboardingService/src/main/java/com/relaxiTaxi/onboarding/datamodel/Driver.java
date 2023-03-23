package com.relaxiTaxi.onboarding.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;


@Entity
@Table(name = "driver_details")

public class Driver implements Serializable {

    private String driverName;

    private String driverSurname;
    @Column(nullable = false)
    @Email
    private String driverEmail;

    private String driverMobile;

    @Id
    /*
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )

     */
    @Column(updatable = false, nullable = false)
    private String driverId;

    //when driver receives device and checks into the platform
    //@Column(columnDefinition = "default false")
    private boolean activated;

    //post document verification;
    private boolean verified;

    //if driver is offline
    private boolean suspended;
    private String driverDocumentId;
    private String vehicleDocumentId;
    private String deviceId;



    private int stageNumber;

    public Driver(String driverName, String driverSurname, String driverEmail, String driverMobile, boolean activated, boolean verified, boolean suspended, int stageNumber) {
        this.driverName = driverName;
        this.driverSurname = driverSurname;
        this.driverEmail = driverEmail;
        this.driverMobile = driverMobile;
        //this.driverId = driverId;
        this.activated = activated;
        this.verified = verified;
        this.suspended = suspended;
        this.stageNumber = stageNumber;
    }

    public Driver() {
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverSurname() {
        return driverSurname;
    }

    public void setDriverSurname(String driverSurname) {
        this.driverSurname = driverSurname;
    }

    public String getDriverEmail() {
        return driverEmail;
    }

    public void setDriverEmail(String driverEmail) {
        this.driverEmail = driverEmail;
    }

    public String getDriverMobile() {
        return driverMobile;
    }

    public void setDriverMobile(String driverMobile) {
        this.driverMobile = driverMobile;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isSuspended() {
        return suspended;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    public String getDriverDocumentId() {
        return driverDocumentId;
    }

    public void setDriverDocumentId(String driverDocumentId) {
        this.driverDocumentId = driverDocumentId;
    }

    public String getVehicleDocumentId() {
        return vehicleDocumentId;
    }

    public void setVehicleDocumentId(String vehicleDocumentId) {
        this.vehicleDocumentId = vehicleDocumentId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    public int getStageNumber() {
        return stageNumber;
    }

    public void setStageNumber(int stageNumber) {
        this.stageNumber = stageNumber;
    }


}
