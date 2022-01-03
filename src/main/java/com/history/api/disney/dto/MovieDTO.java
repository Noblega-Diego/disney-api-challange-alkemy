package com.history.api.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Date;

@Getter
@Setter
public class MovieDTO extends BasicMovieDTO {
    private Date creationDate;
    private int rating;
}
