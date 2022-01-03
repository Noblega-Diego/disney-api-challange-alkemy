package com.history.api.disney.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "characters")
@NoArgsConstructor
@AllArgsConstructor
public class CharacterModel extends Base{

	@Column(name = "name") @Getter @Setter
	private String name;
	@Column(name = "age") @Getter @Setter
	private Integer age = 0;
	@Column(name = "weight") @Getter @Setter
	private Integer weight = 0;
	@Column(name = "history") @Getter @Setter
	private String history = "";
	@ManyToMany
	@JoinTable(name = "movies_characters",
	joinColumns = @JoinColumn(name = "id_character"),
	inverseJoinColumns = @JoinColumn(name = "id_movie"))
	@Getter @Setter
	private List<Movie> movies = new ArrayList<>();
	@Lob
	@Column(name = "image") @Getter @Setter
	private byte[] image;
	
}
