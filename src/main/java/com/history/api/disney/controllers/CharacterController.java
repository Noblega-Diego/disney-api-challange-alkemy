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
        super(CharacterModel.class, CompleteCharacterDTO.class);
    }

    @GetMapping(value = "", params = {"name"})
    public ResponseEntity<?> getByName(@RequestParam(name = "name") String name){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.findByName(name));
    }

    @GetMapping(value = "", params = {"age"})
    public ResponseEntity<?> getByAge(@RequestParam(name = "age") Integer age){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.findByAge(age));

    }
    @GetMapping(value = "", params = {"weight"})
    public ResponseEntity<?> getByWeight(@RequestParam(name = "weight") Integer weight){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.findByWeight(weight));
    }

    @GetMapping(value = "", params = {"movie_title"})
    public ResponseEntity<?> getByFilmTitle(@RequestParam(name = "filmTitle") String title){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.findByFilmTitle(title));
    }



    @GetMapping(value = "", params = {"movie"})
    public ResponseEntity<?> getByFilmId(@RequestParam(name = "movie") Long filmId){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.service.findByFilm(filmId));

    }
}
