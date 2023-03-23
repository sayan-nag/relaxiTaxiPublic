package com.relaxiTaxi.onboarding.persistence;

import com.relaxiTaxi.onboarding.datamodel.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdminDao {
    @Autowired
    AdminRepository adminRepository;

    public Admin fetchByID(String Id){
        return adminRepository.findById(Id).get();
    }

    public boolean existAdmin(String Id){
        return adminRepository.existsById(Id);
    }
}
