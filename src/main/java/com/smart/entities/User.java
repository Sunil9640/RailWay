package com.smart.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
@Data

@Table(name="USER")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotBlank(message = "name should not be blank!!")
	@Size(max = 30 ,min = 3 ,message = "should contain min 3 and max 30 characters")
	private String name;
	@Column(unique = true)
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="email Invalid ! please enter the correct email id")
	private String email;
	@Pattern(regexp ="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Minimum eight characters, at least one letter, one number and one special character" )
	private String password;
	private String role;
	private boolean enable;
	private String imageUrl;
	@Column(length = 500)
	private String about;
	
	
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "user")
	private List<Contact> contacts =new ArrayList<Contact>();
	

	
	
}
