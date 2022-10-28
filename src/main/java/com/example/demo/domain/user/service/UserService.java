package com.example.demo.domain.user.service;

import com.example.demo.domain.user.entity.User;

import java.util.Optional;

public interface UserService {
	void signUp(User user);
	Optional<User> getUserInfo(String userId);
}
