package gift.global.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
/*
@Component
@RequiredArgsConstructor
public class JwtProvider implements InitializingBean {

	private final JwtProperties jwtProperties;
	private Key secretKey;
	private static final String ROLE = "role";
	private static final String IS_ACCESS_TOKEN = "isAccessToken";
	private static final String HEADER_PREFIX = "Bearer ";

	public String parseHeader(String header) {
		if (header == null || header.isEmpty()) {
			throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
		} else if (!header.startsWith(HEADER_PREFIX)) {
			throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
		} else if (header.split(" ").length != 2) {
			throw new IllegalArgumentException("Authorization 올바르지 않습니다.");
		}

		return header.split(" ")[1];
	}

	public JwtToken createToken(JwtUser jwtUser) {
		String accessToken = generateToken(jwtUser, true);
		String refreshToken = generateToken(jwtUser, false);
		return JwtToken.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	/*
	토큰 검증시 rawToken을 Claims로 변환하고, 해당 토큰이 accessToken이면서 만료되어있지 않다면 True를 반환한다.
	 */

/*
	public boolean validateToken(String rawToken, boolean isAccessToken) {
		try {
			Claims claims = extractClaims(rawToken);
			if (claims.get(IS_ACCESS_TOKEN, Boolean.class) != isAccessToken) {
				return false;
			}
			return !claims.getExpiration().before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * refreshToken을 통해, accessToken을 재발급하는 메서드.
	 * refreshToken의 유효성을 검사하고, isAccessToken이 true일때만 accessToken을 재발급한다.
	 * TODO: refreshToken을 저장하고, 저장된 refreshToken과 비교하는 로직 필요 redis 추가 후 구현
	 */
/*
	public String reissueAccessToken(String refreshToken) {
		Claims claims = extractClaims(refreshToken);
		if (claims.get(IS_ACCESS_TOKEN, Boolean.class)) {
			throw new IllegalArgumentException("리프레시 토큰이 아닙니다.");
		}

		Date expiration = claims.getExpiration();
		if (expiration.before(new Date())) {
			throw new IllegalArgumentException("리프레시 토큰이 만료되었습니다.");
		}

		JwtUser jwtUser = claimsToJwtUser(claims);
		return generateToken(jwtUser, true);
	}


	public JwtUser getJwtUser(String rawToken) {
		Claims claims = extractClaims(rawToken);
		return claimsToJwtUser(claims);
	}

	private JwtUser claimsToJwtUser(Claims claims) {
		String userId = claims.getSubject();
		return JwtUser.of(Long.parseLong(userId));
	}

	private String generateToken(JwtUser jwtUser, boolean isAccessToken) {
		long expireTime = isAccessToken ? jwtProperties.getAccessTokenExpireTime() : jwtProperties.getRefreshTokenExpireTime();
		Date expireDate = new Date(System.currentTimeMillis() + expireTime);
		return Jwts.builder()
			.signWith(secretKey)
			.claim(IS_ACCESS_TOKEN, isAccessToken)
			.setSubject(jwtUser.getId().toString())
			.setExpiration(expireDate)
			.compact();
	}

	private Claims extractClaims(String rawToken) {
		return Jwts.parserBuilder()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(rawToken)
			.getBody();
	}

	@Override
	public void afterPropertiesSet() {
		secretKey = new SecretKeySpec(jwtProperties.getSecret().getBytes(), SignatureAlgorithm.HS256.getJcaName());
	}
}
*/
