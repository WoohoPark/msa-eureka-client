package com.example.demo.domain.token.service;

import com.example.demo.domain.token.dto.RefreshTokenDto;
import com.example.demo.domain.token.entity.RefreshToken;
import com.example.demo.domain.token.repository.RefreshTokenRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.utils.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

	private final UserDetailsService userDetailsService;
	private final JwtTokenProvider jwtTokenProvider;
	private final UserRepository userRepository;
	private final RefreshTokenRepository refreshTokenRepository;

	@Override
	public void updateRefreshToken(String email, String uuid) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("사용자 고유번호 : " + email + "는 없는 사용자입니다."));
		refreshTokenRepository.save(RefreshToken.of(email, uuid));
	}

	@Override
	public RefreshTokenDto refreshJwtToken(String accessToken, String refreshToken) {
		String userId = jwtTokenProvider.getUserId(accessToken);

		RefreshToken findRefreshToken = refreshTokenRepository.findById(userId)
				.orElseThrow(()
						-> new IllegalArgumentException("사용자 고유번호 : " + userId + "는 등록된 리프레쉬 토큰이 없습니다.")
				);

		// refresh token 검증
		String findRefreshTokenId = findRefreshToken.getRefreshTokenId();
		if (!jwtTokenProvider.validateJwtToken(refreshToken)) {
			refreshTokenRepository.delete(findRefreshToken);
			throw new IllegalArgumentException("Not validate jwt token = " + refreshToken);
		}

		if (!jwtTokenProvider.equalRefreshTokenId(findRefreshTokenId, refreshToken)) {
			throw new IllegalArgumentException("redis 의 값과 일치하지 않습니다. = " + refreshToken);
		}

		User findUser = userRepository.findById(Long.valueOf(userId))
				.orElseThrow(() -> new IllegalArgumentException("유저 고유 번호 : " + userId + "는 없는 유저입니다."));

		// access token 생성
		Authentication authentication = getAuthentication(findUser.getEmail());
		List<String> roles = authentication.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		String newAccessToken = jwtTokenProvider.createJwtAccessToken(userId, "/reissu", roles);
		Date expiredTime = jwtTokenProvider.getExpiredTime(newAccessToken);

		return RefreshTokenDto.builder()
				.accessToken(newAccessToken)
				.accessTokenExpiredDate(expiredTime)
				.refreshToken(refreshToken)
				.build();
	}

	@Override
	public void logoutToken(String accessToken) {
		if (!jwtTokenProvider.validateJwtToken(accessToken)) {
			// 예외 발생
			throw new IllegalArgumentException("access token is not valid");
		}

		RefreshToken refreshToken = refreshTokenRepository.findById(jwtTokenProvider.getUserId(accessToken))
				.orElseThrow(() -> new IllegalArgumentException("refresh Token is not exist"));

		refreshTokenRepository.delete(refreshToken);
	}

	public Authentication getAuthentication(String email) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
	}
}
