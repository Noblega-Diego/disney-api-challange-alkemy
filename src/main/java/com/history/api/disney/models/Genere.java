package com.history.api.disney.models;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "genders")
@NoArgsConstructor
@AllArgsConstructor
public class Genere extends Base{
    @Column(name = "name")
    private String name;
    @Column(name = "image")
    private byte[] image;
    @OneToMany(mappedBy = "genere")
    private List<Movie> movies;
}
