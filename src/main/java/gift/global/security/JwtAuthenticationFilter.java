package gift.global.security;
/*
import com.helpmeCookies.global.jwt.JwtProvider;
import com.helpmeCookies.global.jwt.JwtUser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtProvider jwtProvider;
	private static final String AUTHORIZATION_HEADER = "Authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		log.info("JwtAuthenticationFilter");
		String rawToken;

		// 토큰 추출
		try {
			rawToken = jwtProvider.parseHeader(request.getHeader(AUTHORIZATION_HEADER));
		} catch (Exception e) {
			filterChain.doFilter(request, response);
			return;
		}

		if (jwtProvider.validateToken(rawToken, true)) {
			JwtUser jwtUser = jwtProvider.getJwtUser(rawToken);
			Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUser, null,
				jwtUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		} else {
			log.info("유효하지 않은 토큰 발생");
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰이 유효하지 않습니다.");
		}

		filterChain.doFilter(request, response);
	}
}

 */