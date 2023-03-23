package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.DriverDocuments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverDocumentsRepository extends JpaRepository<DriverDocuments, String> {

    boolean existsById(String driverDocumentId);
}
