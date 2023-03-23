package com.relaxiTaxi.onboarding.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "vehicle_documents")
public class VehicleDocuments {

    @Id
    /*@GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )*/
    @Column(updatable = false, nullable = false)
    private String vehicleDocumentId;
    @Column(nullable = false)
    private String vehicleRegistrationNumber;
    @Column(nullable = false)
    private Date vehicleRegisteredUpTo;
    @Column(nullable = false)
    private String vehicleChassisNumber;
    @Column(nullable = false)
    private String insuranceReferenceNumber;
    @Column(nullable = false)
    private String insuranceIssuingCompany;
    @Column(nullable = false)
    private Date insuranceValidUpTo;
    @Column(nullable = false)
    private Date uploadedDate;
    private Date verifiedDate;

    @Column(nullable = false)
    private String driverId;
    private boolean archived;

    public VehicleDocuments(String vehicleDocumentId, String vehicleRegistrationNumber, Date vehicleRegisteredUpTo, String vehicleChassisNumber, String insuranceReferenceNumber, String insuranceIssuingCompany, Date insuranceValidUpTo, Date uploadedDate, Date verifiedDate, String driverId, boolean archived) {
        this.vehicleDocumentId = vehicleDocumentId;
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
        this.vehicleRegisteredUpTo = vehicleRegisteredUpTo;
        this.vehicleChassisNumber = vehicleChassisNumber;
        this.insuranceReferenceNumber = insuranceReferenceNumber;
        this.insuranceIssuingCompany = insuranceIssuingCompany;
        this.insuranceValidUpTo = insuranceValidUpTo;
        this.uploadedDate = uploadedDate;
        this.verifiedDate = verifiedDate;
        this.driverId = driverId;
        this.archived = archived;
    }

    public VehicleDocuments() {

    }

    public String getVehicleDocumentId() {
        return vehicleDocumentId;
    }

    public void setVehicleDocumentId(String vehicleDocumentId) {
        this.vehicleDocumentId = vehicleDocumentId;
    }

    public String getVehicleRegistrationNumber() {
        return vehicleRegistrationNumber;
    }

    public void setVehicleRegistrationNumber(String vehicleRegistrationNumber) {
        this.vehicleRegistrationNumber = vehicleRegistrationNumber;
    }

    public Date getVehicleRegisteredUpTo() {
        return vehicleRegisteredUpTo;
    }

    public void setVehicleRegisteredUpTo(Date vehicleRegisteredUpTo) {
        this.vehicleRegisteredUpTo = vehicleRegisteredUpTo;
    }

    public String getVehicleChassisNumber() {
        return vehicleChassisNumber;
    }

    public void setVehicleChassisNumber(String vehicleChassisNumber) {
        this.vehicleChassisNumber = vehicleChassisNumber;
    }

    public String getInsuranceReferenceNumber() {
        return insuranceReferenceNumber;
    }

    public void setInsuranceReferenceNumber(String insuranceReferenceNumber) {
        this.insuranceReferenceNumber = insuranceReferenceNumber;
    }

    public String getInsuranceIssuingCompany() {
        return insuranceIssuingCompany;
    }

    public void setInsuranceIssuingCompany(String insuranceIssuingCompany) {
        this.insuranceIssuingCompany = insuranceIssuingCompany;
    }

    public Date getInsuranceValidUpTo() {
        return insuranceValidUpTo;
    }

    public void setInsuranceValidUpTo(Date insuranceValidUpTo) {
        this.insuranceValidUpTo = insuranceValidUpTo;
    }

    public Date getUploadedDate() {
        return uploadedDate;
    }

    public void setUploadedDate(Date uploadedDate) {
        this.uploadedDate = uploadedDate;
    }

    public Date getVerifiedDate() {
        return verifiedDate;
    }

    public void setVerifiedDate(Date verifiedDate) {
        this.verifiedDate = verifiedDate;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }
}
