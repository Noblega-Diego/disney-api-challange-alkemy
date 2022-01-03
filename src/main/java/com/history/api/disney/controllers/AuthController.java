package com.history.api.disney.controllers;

import com.history.api.disney.dto.AuthenticationRequest;
import com.history.api.disney.dto.UserDTO;
import com.history.api.disney.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO user){
        try {
            service.registerUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\""+ e.getMessage() +"\"}");
        }
    }

    @GetMapping("confirm_email/{key}")
    public ResponseEntity<?> confirmEmail(@PathVariable String key){
        try {
            if(service.confirmEmail(key))
                return ResponseEntity.status(HttpStatus.OK).body("OK");
            else
                throw new Exception();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }


    @PostMapping("login")
    public ResponseEntity<?> createToken(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.createToken(authenticationRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\""+e.getMessage()+"\"}");
        }
    }

}
