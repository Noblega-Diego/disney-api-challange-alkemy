package com.history.api.disney.models;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User extends Base{

	@Column(name = "name", nullable = false) @Getter @Setter
	private String name;
	@Column(name = "lastname", nullable = false) @Getter @Setter
	private String lastname;
	@Column(name = "email", nullable = false, unique = true) @Getter @Setter
	private String email;
	@Column(name = "password", nullable = false) @Getter @Setter
	private String password;
	@Column(name = "active", nullable = false) @Getter @Setter
	private boolean active = false;

}
