package com.relaxiTaxi.onboarding.rest;

import com.relaxiTaxi.onboarding.service.AdminService;
import com.relaxiTaxi.onboarding.service.ServiceManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = {"/admin"})
public class AdminloginController {

    @Autowired
    AdminService adminService;
    @GetMapping("/init/{email}")
    ResponseEntity firstContact(@PathVariable String email) {
        return adminService.init(email);
    }

    @PostMapping("/auth/{email}/{otp}")
    ResponseEntity otpValidation(@PathVariable String otp, @PathVariable String email){
        Map<String, String> vars = new HashMap<>();
        vars.put("mailId", email);
        vars.put("otp", otp);
        return adminService.validatedOtp(vars);
    }

    @GetMapping("/login")
    ResponseEntity login(){
        HttpHeaders responseHeaders = new HttpHeaders();
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body("logged in as a admin, Welcome Admin");
    }
}
