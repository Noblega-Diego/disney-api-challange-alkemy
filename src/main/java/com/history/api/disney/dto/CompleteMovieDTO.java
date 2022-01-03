package com.history.api.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class CompleteMovieDTO extends MovieDTO {
    private List<BaseDTO<Long>> characters;
    private String genere;
}
