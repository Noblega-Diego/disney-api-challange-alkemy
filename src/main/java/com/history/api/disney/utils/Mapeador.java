package com.history.api.disney.utils;

import com.history.api.disney.dto.*;
import com.history.api.disney.exceptions.BadRequestException;
import com.history.api.disney.models.CharacterModel;
import com.history.api.disney.models.Genere;
import com.history.api.disney.models.Movie;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Mapeador extends ModelMapper {

    public Mapeador(){
        super();
        super.addConverter(completeCharacterDTOtoCharacter());
        super.addConverter(completeMovieDTOtoMovie());
        super.addConverter(movieToBasicMovieDTO());
        super.addConverter(characterToBasicCharacterDTO());
        super.addConverter(characterToCompleteCharacterDTO());
        super.addConverter(movieToCompleteMovieDTO());
        super.addConverter(movieToMovieDTO());
        super.addConverter(characterToCharacterDTO());
    }

    private Converter<CompleteCharacterDTO, CharacterModel> completeCharacterDTOtoCharacter(){
        return new Converter<CompleteCharacterDTO, CharacterModel>() {
            @Override
            public CharacterModel convert(MappingContext<CompleteCharacterDTO, CharacterModel> mappingContext) {
                CompleteCharacterDTO completeCharacterDTO = mappingContext.getSource();
                CharacterModel characterModel = mappingContext.getDestination();
                if(characterModel == null);
                    characterModel = new CharacterModel();
                characterModel.setId(completeCharacterDTO.getId());
                characterModel.setAge(completeCharacterDTO.getAge());
                characterModel.setName(completeCharacterDTO.getName());
                characterModel.setWeight(completeCharacterDTO.getWeight());
                characterModel.setHistory((completeCharacterDTO.getHistory() != null)?
                        completeCharacterDTO.getHistory() : "");
                if(completeCharacterDTO.getImg() != null)
                    characterModel.setImage(Base64.getDecoder()
                        .decode(completeCharacterDTO.getImg().getBytes(StandardCharsets.UTF_8)));
                if(completeCharacterDTO.getMovies() != null)
                characterModel.setMovies(completeCharacterDTO.getMovies().stream()
                        .map(basicMovieDTO -> {
                            Movie m = new Movie();
                            m.setId(basicMovieDTO.getId());
                            return m;
                        })
                        .collect(Collectors.toList()));
                return characterModel;
            }
        };
    }

    private Converter<CompleteMovieDTO, Movie> completeMovieDTOtoMovie(){
        return new Converter<CompleteMovieDTO, Movie>() {
            public Movie convert(MappingContext<CompleteMovieDTO, Movie> mappingContext) {
                    CompleteMovieDTO completeMovieDTO = mappingContext.getSource();
                    Movie movie = mappingContext.getDestination();
                    if (movie == null)
                        movie = new Movie();
                    movie.setId(completeMovieDTO.getId());
                    movie.setTitle(completeMovieDTO.getTitle());
                    try {
                        if (completeMovieDTO.getCreationDate() != null) {
                            Integer[] date = Arrays.stream(completeMovieDTO.getCreationDate().split("-"))
                                    .map(Integer::valueOf).toArray(Integer[]::new);
                            if (date.length != 3)
                                movie.setCreationDate(null);
                            else
                                movie.setCreationDate(new Date(date[0] - 1900, date[1] - 1, date[2]));
                        }
                    }catch (NumberFormatException e) {
                        movie.setCreationDate(null);
                    }
                    movie.setRating(completeMovieDTO.getRating());
                    if (completeMovieDTO.getGenereId() != null) {
                        Genere genere = new Genere();
                        genere.setId(completeMovieDTO.getGenereId());
                        movie.setGenere(genere);
                    }
                    if (completeMovieDTO.getImg() != null || !completeMovieDTO.getImg().equals(""))
                        movie.setImage(Base64.getDecoder()
                                .decode(completeMovieDTO.getImg().getBytes(StandardCharsets.UTF_8)));
                    if (completeMovieDTO.getCharacters() != null)
                        movie.setCharacters(completeMovieDTO.getCharacters().stream()
                                .map(basicCharacterDTO -> map(basicCharacterDTO, CharacterModel.class))
                                .collect(Collectors.toList()));
                    else
                        movie.setCharacters(new ArrayList<>());
                    return movie;
            }
        };
    }

    private Converter<Movie, BasicMovieDTO> movieToBasicMovieDTO(){
        return new Converter<Movie, BasicMovieDTO>() {
            @Override
            public BasicMovieDTO convert(MappingContext<Movie, BasicMovieDTO> mappingContext) {
                Movie origin = mappingContext.getSource();
                BasicMovieDTO basicMovieDTO = mappingContext.getDestination();
                if(basicMovieDTO == null)
                    basicMovieDTO = new BasicMovieDTO();
                basicMovieDTO.setTitle(origin.getTitle());
                basicMovieDTO.setId(origin.getId());
                basicMovieDTO.setImg((origin.getImage() != null)?
                        Base64.getEncoder().encodeToString(origin.getImage()) :
                        null);
                return basicMovieDTO;
            }
        };
    }

    private Converter<CharacterModel, BasicCharacterDTO> characterToBasicCharacterDTO(){
        return new Converter<CharacterModel, BasicCharacterDTO>() {
            @Override
            public BasicCharacterDTO convert(MappingContext<CharacterModel, BasicCharacterDTO> mappingContext) {
                CharacterModel origin = mappingContext.getSource();
                BasicCharacterDTO basicCharacterDTO = mappingContext.getDestination();
                if(basicCharacterDTO == null)
                    basicCharacterDTO = new BasicCharacterDTO();
                basicCharacterDTO.setName(origin.getName());
                basicCharacterDTO.setId(origin.getId());
                if (origin.getImage() != null)
                    basicCharacterDTO.setImg(Base64.getEncoder().encodeToString(origin.getImage()));
                return basicCharacterDTO;
            }
        };
    }
    private Converter<CharacterModel, CharacterDTO> characterToCharacterDTO(){
        return new Converter<CharacterModel, CharacterDTO>() {
            @Override
            public CharacterDTO convert(MappingContext<CharacterModel, CharacterDTO> mappingContext) {
                CharacterModel origin = mappingContext.getSource();
                CharacterDTO characterDTO = mappingContext.getDestination();
                if(characterDTO == null)
                    characterDTO = new CharacterDTO();
                characterDTO.setName(origin.getName());
                characterDTO.setId(origin.getId());
                characterDTO.setAge(origin.getAge());
                characterDTO.setHistory((origin.getHistory() != null)? origin.getHistory():"");
                characterDTO.setWeight(origin.getWeight());
                if (origin.getImage() != null)
                    characterDTO.setImg(Base64.getEncoder().encodeToString(origin.getImage()));
                return characterDTO;
            }
        };
    }

    private Converter<CharacterModel, CompleteCharacterDTO> characterToCompleteCharacterDTO(){
        return new Converter<CharacterModel, CompleteCharacterDTO>() {
            @Override
            public CompleteCharacterDTO convert(MappingContext<CharacterModel, CompleteCharacterDTO> mappingContext) {
                CharacterModel origin = mappingContext.getSource();
                CompleteCharacterDTO completeCharacterDTO = mappingContext.getDestination();
                if(completeCharacterDTO == null)
                    completeCharacterDTO = new CompleteCharacterDTO();
                completeCharacterDTO.setName(origin.getName());
                completeCharacterDTO.setId(origin.getId());
                completeCharacterDTO.setAge(origin.getAge());
                completeCharacterDTO.setHistory((origin.getHistory() != null)? origin.getHistory():"");
                completeCharacterDTO.setWeight(origin.getWeight());
                completeCharacterDTO.setMovies(origin.getMovies().stream()
                        .map(movie -> map(movie, BasicMovieDTO.class))
                        .collect(Collectors.toList()));
                if (origin.getImage() != null)
                    completeCharacterDTO.setImg(Base64.getEncoder().encodeToString(origin.getImage()));
                return completeCharacterDTO;
            }
        };
    }
    private Converter<Movie, CompleteMovieDTO> movieToCompleteMovieDTO(){
        return new Converter<Movie, CompleteMovieDTO>() {
            @Override
            public CompleteMovieDTO convert(MappingContext<Movie, CompleteMovieDTO> mappingContext) {
                Movie origin = mappingContext.getSource();
                CompleteMovieDTO completeMovieDTO = mappingContext.getDestination();
                if(completeMovieDTO == null)
                    completeMovieDTO = new CompleteMovieDTO();
                completeMovieDTO.setTitle(origin.getTitle());
                completeMovieDTO.setId(origin.getId());
                completeMovieDTO.setRating(origin.getRating());
                completeMovieDTO.setCharacters(origin.getCharacters().stream()
                        .map(characterModel -> map(characterModel, CharacterDTO.class))
                        .collect(Collectors.toList()));
                if(origin.getCreationDate() != null)
                completeMovieDTO.setCreationDate(new SimpleDateFormat("yyyy-MM-dd")
                        .format(origin.getCreationDate()));
                if(origin.getGenere() != null)
                    completeMovieDTO.setGenereId(origin.getGenere().getId());
                if (origin.getImage() != null)
                    completeMovieDTO.setImg(Base64.getEncoder().encodeToString(origin.getImage()));
                return completeMovieDTO;
            }
        };
    }

    private Converter<Movie, MovieDTO> movieToMovieDTO(){
        return new Converter<Movie, MovieDTO>() {
            @Override
            public MovieDTO convert(MappingContext<Movie, MovieDTO> mappingContext) {
                Movie origin = mappingContext.getSource();
                MovieDTO completeMovieDTO = mappingContext.getDestination();
                if(completeMovieDTO == null)
                    completeMovieDTO = new MovieDTO();
                completeMovieDTO.setTitle(origin.getTitle());
                completeMovieDTO.setId(origin.getId());
                completeMovieDTO.setRating(origin.getRating());
                if(origin.getCreationDate() != null)
                    completeMovieDTO.setCreationDate(new SimpleDateFormat("yyyy-MM-dd")
                            .format(origin.getCreationDate()));
                if (origin.getImage() != null)
                    completeMovieDTO.setImg(Base64.getEncoder().encodeToString(origin.getImage()));
                return completeMovieDTO;
            }
        };
    }
}
