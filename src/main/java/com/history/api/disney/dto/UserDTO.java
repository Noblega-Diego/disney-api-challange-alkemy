package com.history.api.disney.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDTO extends BaseDTO<Long>{
    private String email;
    private String password;
    private String name;
    private String lastname;
}
