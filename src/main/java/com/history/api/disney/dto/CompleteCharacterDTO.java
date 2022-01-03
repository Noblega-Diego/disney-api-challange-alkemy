package com.history.api.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CompleteCharacterDTO extends CharacterDTO{
    private List<BaseDTO<Long>> movies;
}
