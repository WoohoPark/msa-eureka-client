package com.example.demo.domain.token.dto;


import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
public class RefreshTokenDto {
	private String accessToken;
	private Date accessTokenExpiredDate;
	private String refreshToken;

	@Builder
	public RefreshTokenDto(String accessToken, String refreshToken, Date accessTokenExpiredDate) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.accessTokenExpiredDate = accessTokenExpiredDate;
	}
}