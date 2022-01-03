package com.history.api.disney.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CharacterDTO extends BasicCharacterDTO{
    private String img;
    private Integer weight;
    private Integer age;
    private String history;
}
