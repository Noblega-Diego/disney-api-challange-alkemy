package com.history.api.disney.controllers;

import com.history.api.disney.dto.CompleteMovieDTO;
import com.history.api.disney.models.Movie;
import com.history.api.disney.services.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("movies")
public class MovieController extends BaseControllerImp<Movie, CompleteMovieDTO, MovieService>{

    public MovieController(){
        super(Movie.class, CompleteMovieDTO.class);
    }

    @GetMapping(name = "", params = {"order"})
    public ResponseEntity<?> getAllapplyOrder(
            @RequestParam(name = "order") String order){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(order));
    }

    @GetMapping(name = "", params = {"genre"})
    public ResponseEntity<?> filterByGenere(
            @RequestParam(name = "genre") Long genre){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByGenereId(genre));
    }

    @GetMapping(name = "", params = {"title"})
    public ResponseEntity<?> filterByTitle(@RequestParam(name = "title") String title){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByTitle(title, "DESC"));
    }

    @GetMapping(name = "", params = {"title","order"})
    public ResponseEntity<?> filterByTitle(
            @RequestParam(name = "title") String title,
            @RequestParam(name = "order") String order){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByTitle(title, order));
    }

}
