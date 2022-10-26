package com.example.demo.domain.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@GetMapping("/getPort")
	public String getPort(@Value("${server.port}") String port) {
		return "User 서비스의 기본 동작 Port: {" + port + "}";
	}

}
