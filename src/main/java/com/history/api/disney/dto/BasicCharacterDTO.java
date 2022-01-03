package com.history.api.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasicCharacterDTO extends BaseDTO<Long>{
    private String name;
    private String img;
}
