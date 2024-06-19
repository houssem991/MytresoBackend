package com.bezkoder.springjwt.payload.response;


import com.bezkoder.springjwt.models.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

	private Long id;


	private String username;
	private String firstname;
	private String lastname;
	//private String cin;
	//private LocalDate datenaissance;

	private String image;
	private String email;


	private String password;
	//private String image;


	private Set<Role> roles = new HashSet<>();
	private int identreprise;
	private String nameEntreprise;
	private String logo;

}