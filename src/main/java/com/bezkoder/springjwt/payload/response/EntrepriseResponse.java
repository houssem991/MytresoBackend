package com.bezkoder.springjwt.payload.response;


import com.bezkoder.springjwt.models.Role;
import com.bezkoder.springjwt.models.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntrepriseResponse {

	private Integer id;
	private String name;
	private List<UserResponse> users;
}
