package com.example.demo.domain.token.service;

import com.example.demo.domain.token.dto.RefreshTokenDto;

public interface RefreshTokenService {
	void updateRefreshToken(String id, String uuid);
	RefreshTokenDto refreshJwtToken(String accessToken, String refreshToken);
	void logoutToken(String accessToken);
}
