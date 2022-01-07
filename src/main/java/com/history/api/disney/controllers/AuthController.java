package com.history.api.disney.controllers;

import com.history.api.disney.dto.AuthenticationRequest;
import com.history.api.disney.dto.ErrorResponse;
import com.history.api.disney.dto.UserDTO;
import com.history.api.disney.exceptions.BadRequestException;
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
        service.registerUser(user);
        return ResponseEntity.status(HttpStatus.OK).body("OK");
    }

    @GetMapping("confirm_email/{key}")
    public ResponseEntity<?> confirmEmail(@PathVariable String key){
        if(service.confirmEmail(key))
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        else
            throw new BadRequestException("email invalid");
    }


    @PostMapping("login")
    public ResponseEntity<?> createToken(@RequestBody AuthenticationRequest authenticationRequest){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(service.createToken(authenticationRequest));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e,""));
        }
    }

}
