package com.relaxiTaxi.onboarding.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "device")
public class Device {

    //driverId?
    @Id
    /*@GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )*/
    private String deviceId;
    @Column(nullable = false)
    private String deviceModel;

    @Column(nullable = false)
    private Date manufacturedDate;
    private boolean activated;

    public Device(String deviceId, String deviceModel, Date manufacturedDate, boolean activated) {
        this.deviceId = deviceId;
        this.deviceModel = deviceModel;
        this.manufacturedDate = manufacturedDate;
        this.activated = activated;
    }

    public Device() {
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public Date getManufacturedDate() {
        return manufacturedDate;
    }

    public void setManufacturedDate(Date manufacturedDate) {
        this.manufacturedDate = manufacturedDate;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}
