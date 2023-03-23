package com.relaxiTaxi.onboarding.datamodel;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "documents_index")
public class DocumentIndexReference {
    //vechileID??
    //documents/vehicle/documentReferenceId <<v_doc_id>>
    //documents/driver/documentReferenceId <<d_doc_id>>

    @Id
    private String documentReferenceId;

    private String uploadedPanPathReference;
    private String uploadedAadhaarPathReference;
    private String uploadedDLPathReference;
    private String uploadedRegPathReference;
    private String uploadedInsurancePathReference;

    public DocumentIndexReference(String documentReferenceId, String uploadedPanPathReference, String uploadedAadhaarPathReference, String uploadedDLPathReference, String uploadedRegPathReference, String uploadedInsurancePathReference) {
        this.documentReferenceId = documentReferenceId;
        this.uploadedPanPathReference = uploadedPanPathReference;
        this.uploadedAadhaarPathReference = uploadedAadhaarPathReference;
        this.uploadedDLPathReference = uploadedDLPathReference;
        this.uploadedRegPathReference = uploadedRegPathReference;
        this.uploadedInsurancePathReference = uploadedInsurancePathReference;
    }

    public DocumentIndexReference() {
    }

    public String getDocumentReferenceId() {
        return documentReferenceId;
    }

    public void setDocumentReferenceId(String documentReferenceId) {
        this.documentReferenceId = documentReferenceId;
    }

    public String getUploadedPanPathReference() {
        return uploadedPanPathReference;
    }

    public void setUploadedPanPathReference(String uploadedPanPathReference) {
        this.uploadedPanPathReference = uploadedPanPathReference;
    }

    public String getUploadedAadhaarPathReference() {
        return uploadedAadhaarPathReference;
    }

    public void setUploadedAadhaarPathReference(String uploadedAadhaarPathReference) {
        this.uploadedAadhaarPathReference = uploadedAadhaarPathReference;
    }

    public String getUploadedDLPathReference() {
        return uploadedDLPathReference;
    }

    public void setUploadedDLPathReference(String uploadedDLPathReference) {
        this.uploadedDLPathReference = uploadedDLPathReference;
    }

    public String getUploadedRegPathReference() {
        return uploadedRegPathReference;
    }

    public void setUploadedRegPathReference(String uploadedRegPathReference) {
        this.uploadedRegPathReference = uploadedRegPathReference;
    }

    public String getUploadedInsurancePathReference() {
        return uploadedInsurancePathReference;
    }

    public void setUploadedInsurancePathReference(String uploadedInsurancePathReference) {
        this.uploadedInsurancePathReference = uploadedInsurancePathReference;
    }
}
