package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.DriverDocuments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class DriverDocumentDao {


    @Autowired
    private DriverDocumentsRepository driverDocumentsRepository;

    public DriverDocuments fetchDriverDocumentsById(String driverDocumentId) throws NoSuchElementException {
        if (driverDocumentsRepository.existsById(driverDocumentId))
            return driverDocumentsRepository.findById(driverDocumentId).get();
        else
            throw new NoSuchElementException("NO driverDocumentId PRESENT WITH ID = " + driverDocumentId);
    }


    public DriverDocuments saveDriverDocument(DriverDocuments driverDocuments) {
        return driverDocumentsRepository.save(driverDocuments);
    }

    public boolean isDriverDocumentExist(String driverDocumentId) {
        return driverDocumentsRepository.existsById(driverDocumentId);
    }

}
