package com.example.demo.domain.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "tb_user")
//@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User{

	@Id @GeneratedValue
	@Column(name = "user_id")
	private Long id;
	private String email;
	private String password;
	private String name;
	private Integer phoneNumber;

	@Column(insertable = false,updatable = false)
	protected String dtype;

	public User(String email, String password, String name, Integer phoneNumber) {
		this.email = email;
		this.password = new BCryptPasswordEncoder().encode(password);
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

}