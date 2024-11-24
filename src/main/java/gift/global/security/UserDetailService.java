package gift.global.security;
/*
import com.helpmeCookies.global.jwt.JwtUser;
import com.helpmeCookies.user.entity.User;
import com.helpmeCookies.user.entity.UserInfo;
import com.helpmeCookies.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService {
	private final UserRepository userRepository;


	public JwtUser loadUserByEmail(String email,String nickname) throws UsernameNotFoundException {
		// 만약 유저가 존재하지 않는다면 저장
		User user = userRepository.findByUserInfoEmail(email)
			.orElseGet(() -> {
				User newUser = User.builder()
					.userInfo(UserInfo.builder()
						.email(email)
						.build())
					.nickname(nickname)
					.build();
				return userRepository.save(newUser);
			});
		return JwtUser.of(user.getId());
	}
}


 */