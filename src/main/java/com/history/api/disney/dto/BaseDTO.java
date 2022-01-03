package com.history.api.disney.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BaseDTO<ID extends Serializable> {
    private ID id;
}
