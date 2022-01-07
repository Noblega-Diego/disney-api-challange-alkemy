package com.history.api.disney.services;

import com.history.api.disney.dao.MovieDao;
import com.history.api.disney.dto.BasicMovieDTO;
import com.history.api.disney.exceptions.BadRequestException;
import com.history.api.disney.models.Movie;
import com.history.api.disney.utils.Mapeador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService implements BaseService<Movie>{

    @Autowired
    private MovieDao movieRepository;

    @Autowired
    protected Mapeador maped;

    @Override
    public List<BasicMovieDTO> findAll(){
        return movieRepository.listAll().stream()
                .map((entity)-> maped.map(entity, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    public List<BasicMovieDTO> findAll(String order){
        return movieRepository.listAll(order).stream()
                .map((entity)-> maped.map(entity, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Movie findById(Long id){
        return movieRepository.findById(id);
    }


    @Override
    public Movie update(Long id, Movie entity){
        entity.setId(id);
        if(isInvalidRating(entity))
            throw new BadRequestException("rating not valid: "+ entity.getRating());
        if(isInvalidDate(entity))
            throw new BadRequestException("creationDate not valid, use format: yyyy-MM-dd");
        return movieRepository.update(entity);
    }

    private boolean isInvalidDate(Movie entity) {
        return entity.getCreationDate() == null;
    }

    @Override
    public Movie save(Movie entity){
        if(isInvalidRating(entity))
            throw new BadRequestException("rating not valid: "+ entity.getRating());
        if(isInvalidDate(entity))
            throw new BadRequestException("creationDate not valid, use format: yyyy-MM-dd");
        return movieRepository.insert(entity);
    }

    @Override
    public boolean delete(Long id){
        return movieRepository.remove(id);
    }

    public List<BasicMovieDTO> findByGenereId(Long genere){
        return movieRepository.findByGenere(genere).stream()
                .map(movie -> maped.map(movie, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    public List<BasicMovieDTO> findByTitle(String title, String order){
        return movieRepository.findByTitle(title, order).stream()
                .map(movie -> maped.map(movie, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    private boolean isInvalidRating(Movie entity) {
        return entity.getRating() == null ||
                entity.getRating() < 0 || entity.getRating() > 5;
    }
}
