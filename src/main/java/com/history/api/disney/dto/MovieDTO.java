package com.history.api.disney.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieDTO extends BasicMovieDTO {
    private String creationDate;
    private int rating;
}
