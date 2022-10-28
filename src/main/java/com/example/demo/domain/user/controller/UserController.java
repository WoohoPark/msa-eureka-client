package com.example.demo.domain.user.controller;

import com.example.demo.domain.user.dto.UserDto;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.global.dto.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	@GetMapping("/getPort")
	public String getPort(@Value("${server.port}") String port) {
		return "User 서비스의 기본 동작 Port: {" + port + "}";
	}

	@PostMapping("/signUp")
	public ResponseEntity signUp(@RequestBody UserDto userDto){
		User data = new User(userDto.getEmail(),userDto.getPassword(),userDto.getName(),userDto.getPhoneNumber());
		userService.signUp(data);
		return ResponseEntity.status(HttpStatus.OK)
				.body(Result.createSuccessResult(userDto));
	}

	@GetMapping("/userInfo/{userId}")
	public ResponseEntity getUserInfo(@PathVariable String userId){
		Optional<User> userDto = userService.getUserInfo(userId);
		userDto.orElseThrow(() -> new IllegalAccessError("gg"));
		return ResponseEntity.status(HttpStatus.OK)
				.body(Result.createSuccessResult(userDto));
	}
}
