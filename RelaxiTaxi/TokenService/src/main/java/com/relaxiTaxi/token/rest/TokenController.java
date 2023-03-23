package com.relaxiTaxi.token.rest;

import com.relaxiTaxi.token.security.JWTTokenUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;

@RestController

@RequestMapping(value = {"/token"})
public class TokenController {

    @GetMapping("/create")
    public String createToken() throws SignatureException, NoSuchAlgorithmException, InvalidKeyException {
        return JWTTokenUtils.createToken();
    }

    @GetMapping("/validate")
    public boolean validateUser(@RequestHeader("token") String token) {
        return JWTTokenUtils.validateToken(token);
    }
}
