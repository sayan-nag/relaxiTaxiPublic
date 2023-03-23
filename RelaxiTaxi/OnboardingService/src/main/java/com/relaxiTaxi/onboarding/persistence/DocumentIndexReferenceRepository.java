package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.DocumentIndexReference;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentIndexReferenceRepository extends JpaRepository<DocumentIndexReference, String> {

    boolean existsById(String documentReferenceId);
}

