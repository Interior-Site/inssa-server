package com.inssa.server.api.user.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inssa.server.api.user.social.dto.SocialOauthToken;
import com.inssa.server.api.user.social.dto.SocialUser;
import com.inssa.server.api.user.social.model.SocialType;
import com.inssa.server.api.user.user.data.UserRepository;
import com.inssa.server.api.user.user.model.User;
import com.inssa.server.common.exception.InssaException;
import com.inssa.server.config.security.JwtTokenProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OauthService {

	private final List<SocialOauth> socialOauthList;
	private final UserRepository userRepository;
	private final JwtTokenProvider jwtTokenProvider;

	// redirectURL 만들기
	public String getRedirectUrl(SocialType socialType) {
		SocialOauth socialOauth = this.findSocialOauthByType(socialType);
		return socialOauth.getOauthRedirectURL();
	}

	// 소셜 타입 찾기
	private SocialOauth findSocialOauthByType(SocialType socialType) {
		return socialOauthList.stream()
			.filter(x -> x.type() == socialType)
			.findFirst()
			.orElseThrow(() -> new InssaException("알 수 없는 SocialLoginType 입니다."));
	}

	// 클라이언트로 보낼 GetSocialOAuthRes 객체 만들기
	public Map<String, Object> oAuthLogin(SocialType socialType, String code) {

		SocialOauth socialOauth = findSocialOauthByType(socialType);
		try {
			// 소셜서버로 일회성 코드를 보내 액세스 토큰이 담긴 응답객체를 받아온 후 응답 객체 deserialization
			SocialOauthToken oAuthToken = socialOauth.getAccessToken(code);
			// 액세스 토큰을 다시 소셜로 보내 소셜에 저장된 사용자 정보가 담긴 응답 객체를 받아온 후 역직렬화
			SocialUser socialUser = socialOauth.getUserInfo(oAuthToken);

			String email = socialUser.getEmail();
			String name = socialUser.getName();

			Optional<User> optionalUser = userRepository.findByEmail(email);

			Map<String, Object> userResponse = new HashMap<>();

			// 사용자가 존재하지 않으면 회원가입 진행을 위해 정보 리턴
			if (optionalUser.isEmpty()) {
				userResponse.put("email", email);
				userResponse.put("name", name);
				userResponse.put("isExists", false);
			} else {
				// 사용자가 존재하고, 해당 소셜 로그인을 통해 가입한 이력이 있는 경우 JWT 토큰을 발급
				User user = optionalUser.get();

				if (user.getSocialType() != socialType) {
					throw new InssaException("다른 로그인을 통해 등록된 이메일입니다.");
				}

				ArrayList<GrantedAuthority> authorities = new ArrayList<>();
				authorities.add(
					new SimpleGrantedAuthority(user.getRoleKey().replaceFirst("ROLE_", "")));
				String token = jwtTokenProvider.createToken(
					new UsernamePasswordAuthenticationToken(user.getNo(), user.getPassword(),
						authorities), user.getEmail());

				userResponse.put("isExists", true);
				userResponse.put("token", token);
			}

			return userResponse;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}

	}
}
