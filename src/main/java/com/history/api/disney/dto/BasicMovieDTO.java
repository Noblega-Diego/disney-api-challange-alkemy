package com.history.api.disney.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class BasicMovieDTO extends BaseDTO<Long>{
	private String img;
	private String title;
}
