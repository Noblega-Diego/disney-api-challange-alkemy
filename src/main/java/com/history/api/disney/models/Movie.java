package com.history.api.disney.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Movies")
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends Base{

	@Column(name = "title", nullable = false) @Getter @Setter
	private String title;
	@Column(name = "creation_date") @Getter @Setter
	private Date creationDate;
	@Column(name = "rating") @Getter @Setter
	private Integer rating = 0;
	@Lob @Column(name = "image") @Getter @Setter
	private byte[] image;


	@ManyToMany(mappedBy = "movies")
	@Getter @Setter
	private List<CharacterModel> characters = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="genere_id")
	private Genere genere;
}
