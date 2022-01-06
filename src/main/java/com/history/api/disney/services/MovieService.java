package com.history.api.disney.services;

import com.history.api.disney.dao.MovieDao;
import com.history.api.disney.dto.BasicMovieDTO;
import com.history.api.disney.dto.CompleteMovieDTO;
import com.history.api.disney.dto.MovieDTO;
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
    public List<BasicMovieDTO> findAll() throws Exception {
        return movieRepository.listAll().stream()
                .map((entity)-> maped.map(entity, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    public List<BasicMovieDTO> findAll(String order) throws Exception {
        return movieRepository.listAll(order).stream()
                .map((entity)-> maped.map(entity, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CompleteMovieDTO findById(Long id) throws Exception {
        return maped.map(movieRepository.findById(id), CompleteMovieDTO.class);
    }


    @Override
    public MovieDTO update(Long id, Movie entity) throws Exception {
        entity.setId(id);
        if(isValidRating(entity))
            throw new Exception("rating not accepted");
        return maped.map(movieRepository.update(entity), MovieDTO.class);
    }


    @Override
    public MovieDTO save(Movie entity) throws Exception {
        if(isValidRating(entity))
            throw new Exception("rating not accepted");
        return maped.map(movieRepository.insert(entity), MovieDTO.class);
    }

    @Override
    public boolean delete(Long id) throws Exception {
        return movieRepository.remove(id);
    }

    public List<BasicMovieDTO> findByGenereId(Long genere) throws Exception {
        return movieRepository.findByGenere(genere).stream()
                .map(movie -> maped.map(movie, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    public List<BasicMovieDTO> findByTitle(String title, String order) throws Exception{
        return movieRepository.findByTitle(title, order).stream()
                .map(movie -> maped.map(movie, BasicMovieDTO.class))
                .collect(Collectors.toList());
    }

    private boolean isValidRating(Movie entity) {
        return entity.getRating() < 0 || entity.getRating() > 5;
    }
}
