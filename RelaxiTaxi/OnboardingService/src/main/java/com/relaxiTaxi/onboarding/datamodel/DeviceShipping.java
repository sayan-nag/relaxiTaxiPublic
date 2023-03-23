package com.relaxiTaxi.onboarding.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "device_shipment")
public class DeviceShipping {
    @Column(nullable = false)
    private String deviceId;
    @Column(nullable = false)
    private String driverId;
    @Column(nullable = false)
    private Date shippedDate;
    private Date expectedDeliveryDate;
    private Date deliveredDate;
    @Id
    @Column(nullable = false)

    private String shipmentId;
    @Column(nullable = false)
    private String courierName;
    @Column(nullable = false)
    private String externalTrackerReference;

    public DeviceShipping() {
    }

    public DeviceShipping(String deviceId, String driverId, Date shippedDate, Date expectedDeliveryDate, Date deliveredDate, String shipmentId, String courierName, String externalTrackerReference) {
        this.deviceId = deviceId;
        this.driverId = driverId;
        this.shippedDate = shippedDate;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.deliveredDate = deliveredDate;
        this.shipmentId = shipmentId;
        this.courierName = courierName;
        this.externalTrackerReference = externalTrackerReference;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public Date getDeliveredDate() {
        return deliveredDate;
    }

    public void setDeliveredDate(Date deliveredDate) {
        this.deliveredDate = deliveredDate;
    }

    public String getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getExternalTrackerReference() {
        return externalTrackerReference;
    }

    public void setExternalTrackerReference(String externalTrackerReference) {
        this.externalTrackerReference = externalTrackerReference;
    }
}
