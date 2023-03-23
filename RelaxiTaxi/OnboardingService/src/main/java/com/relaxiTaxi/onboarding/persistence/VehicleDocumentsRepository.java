package com.relaxiTaxi.onboarding.persistence;


import com.relaxiTaxi.onboarding.datamodel.VehicleDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleDocumentsRepository extends JpaRepository<VehicleDocuments, String> {

    boolean existsById(String vehicleDocumentId);
}
