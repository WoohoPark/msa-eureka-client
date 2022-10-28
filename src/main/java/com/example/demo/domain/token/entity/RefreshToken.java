package com.example.demo.domain.token.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;


@Entity
@Table(name = "tb_token")
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "DTYPE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
//	@javax.persistence.Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "id", nullable = false)
//	private Long id;

	@Id
	@Column(name = "user_id")
	private String userId;
	private String refreshTokenId;

	public static RefreshToken of(String userId, String refreshTokenId) {
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.userId = userId;
		refreshToken.refreshTokenId = refreshTokenId;
		return refreshToken;
	}
}