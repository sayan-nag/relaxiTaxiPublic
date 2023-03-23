package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.DocumentIndexReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DocumentIndexDao {

    @Autowired
    private DocumentIndexReferenceRepository documentIndexReferenceRepository;

    public DocumentIndexReference fetchDocumentIndexReferenceById(String documentReferenceId) {
        return documentIndexReferenceRepository.findById(documentReferenceId).get();
    }


    public DocumentIndexReference saveDocumentIndexReference(DocumentIndexReference documentIndexReference) {
        return documentIndexReferenceRepository.save(documentIndexReference);
    }

    public boolean isDocumentIndexReferenceExist(String documentReferenceId) {
        return documentIndexReferenceRepository.existsById(documentReferenceId);
    }
}
