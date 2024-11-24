package gift.global.security;
/*
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;

@EnableMethodSecurity
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Controller
public class WebSecurityConfig {
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	@Bean
	public WebSecurityCustomizer configure() {
		return (web) -> web.ignoring()
			.requestMatchers("/swagger-ui")
			.requestMatchers("/static/**");
	}

	@Bean
	public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.securityMatcher("/oauth2/**")
			.csrf(AbstractHttpConfigurer::disable)
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/oauth2/authorization/**",
					"/oauth2/code/kakao/**"
				).permitAll()
				.anyRequest().authenticated()
			)
			.oauth2Login((oauth2) -> oauth2
				.redirectionEndpoint(redirection -> redirection
					.baseUri("/oauth2/code/*"))
				.userInfoEndpoint((userInfo) -> userInfo
					.userService(new Oauth2CustomUserService())
				)
				// 추후 로그인 방식이 다양해지면, Handler의 세부 내용을 변경.
				.successHandler((request, response, authentication) -> {
					response.sendRedirect("/oauth2/login/kakao");
				})
				.failureHandler((request, response, exception) -> {
					System.out.println(exception.getMessage());
				})
			);
		return http.build();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.sessionManagement((session) -> session
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.cors(AbstractHttpConfigurer::disable);
		http.authorizeHttpRequests((authorize) ->
			authorize
				.requestMatchers(
					"/swagger-ui/**",
					"/swagger-resources",
					"/v3/api-docs/**",
					"/actuator/**",
					"/test/signup",
					"/v1/products/**",
					"/v1/reviews/**",
					"/ws/**",
					"/v1/artists/**"
				).permitAll()
				.anyRequest().authenticated()
		).exceptionHandling((exception) -> exception
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)
			.accessDeniedHandler(jwtAccessDeniedHandler)
		);

		http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}

 */