package com.relaxiTaxi.onboarding.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Table(name = "driver_documents")
public class DriverDocuments {

    @Id
    /*@GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )*/
    @Column(updatable = false, nullable = false)
    private String driverDocumentId;
    @Column(nullable = false)
    private String aadhaar;
    @Column(nullable = false)
    private String pan;
    @Column(nullable = false)
    private String drivingLicenseNumber;

    @Column(nullable = false)
    private Date dlValidUpTo;
    @Column(nullable = false)
    private Date uploadedDate;
    private Date verifiedDate;
    //@Column(nullable = false)
    private String driverId;
    private boolean archived;

    public DriverDocuments() {

    }

    public DriverDocuments(String driverDocumentId, String aadhaar, String pan, String drivingLicenseNumber, Date dlValidUpTo, Date uploadedDate, Date verifiedDate, String driverId, boolean archived) {
        this.driverDocumentId = driverDocumentId;
        this.aadhaar = aadhaar;
        this.pan = pan;
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.dlValidUpTo = dlValidUpTo;
        this.uploadedDate = uploadedDate;
        this.verifiedDate = verifiedDate;
        this.driverId = driverId;
        this.archived = archived;
    }

    public String getDriverDocumentId() {
        return driverDocumentId;
    }

    public void setDriverDocumentId(String driverDocumentId) {
        this.driverDocumentId = driverDocumentId;
    }

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public Date getDlValidUpTo() {
        return dlValidUpTo;
    }

    public void setDlValidUpTo(Date dlValidUpTo) {
        this.dlValidUpTo = dlValidUpTo;
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
