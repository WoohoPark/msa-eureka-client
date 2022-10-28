package com.example.demo.domain.token.repository;

import com.example.demo.domain.token.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {
}