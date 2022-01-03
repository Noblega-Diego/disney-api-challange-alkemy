package com.history.api.disney.controllers;


import com.history.api.disney.dto.CharacterDTO;
import com.history.api.disney.dto.CompleteCharacterDTO;
import com.history.api.disney.services.CharacterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.history.api.disney.models.CharacterModel;



@RestController
@CrossOrigin(origins = "*")
@RequestMapping("characters")
public class CharacterController  extends BaseControllerImp<CharacterModel, CompleteCharacterDTO, CharacterService>{

    public CharacterController(){
        super(CharacterModel.class);
    }

    @GetMapping(value = "", params = {"name"})
    public ResponseEntity<?> getByName(@RequestParam(name = "name") String name){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.findByName(name));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }

    @GetMapping(value = "", params = {"age"})
    public ResponseEntity<?> getByAge(@RequestParam(name = "age") Integer age){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.findByAge(age));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }
    @GetMapping(value = "", params = {"weight"})
    public ResponseEntity<?> getByWeight(@RequestParam(name = "weight") Integer weight){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.findByWeight(weight));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }

    @GetMapping(value = "", params = {"film_title"})
    public ResponseEntity<?> getByFilmTitle(@RequestParam(name = "filmTitle") String title){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.findByFilmTitle(title));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }



    @GetMapping(value = "", params = {"films"})
    public ResponseEntity<?> getByFilmId(@RequestParam(name = "films") Long filmId){
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(this.service.findByFilm(filmId));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body("{\"error\":\"Error. Por favor intente nuevamente mas tarde\"}");
        }
    }
}
