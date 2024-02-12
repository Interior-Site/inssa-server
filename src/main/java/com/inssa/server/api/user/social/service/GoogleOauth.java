package com.inssa.server.api.user.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inssa.server.api.user.social.dto.SocialOauthToken;
import com.inssa.server.api.user.social.dto.SocialUser;
import com.inssa.server.common.exception.InssaException;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GoogleOauth implements SocialOauth {
	@Value("${sns.google.url}")
	private String GOOGLE_SNS_BASE_URL;
	@Value("${sns.google.client.id}")
	private String GOOGLE_SNS_CLIENT_ID;
	@Value("${sns.google.callback.url}")
	private String GOOGLE_SNS_CALLBACK_URL;
	@Value("${sns.google.client.secret}")
	private String GOOGLE_SNS_CLIENT_SECRET;
	@Value("${sns.google.token.url}")
	private String GOOGLE_SNS_TOKEN_BASE_URL;

	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;

	private static final Logger log = LoggerFactory.getLogger(GoogleOauth.class);

	// 1. redirect Url 생성
	@Override
	public String getOauthRedirectURL() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.set("scope", "email profile");
		queryParams.set("response_type", "code");
		queryParams.set("client_id", GOOGLE_SNS_CLIENT_ID);
		queryParams.set("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

		return UriComponentsBuilder
			.fromUriString(GOOGLE_SNS_BASE_URL)
			.queryParams(queryParams)
			.encode().build().toString();
	}
	// 2. 코드 추가한 url 생성
	@Override
	public SocialOauthToken getAccessToken(String code) throws JsonProcessingException {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.set("code", code);
		queryParams.set("client_id", GOOGLE_SNS_CLIENT_ID);
		queryParams.set("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
		queryParams.set("client_secret", GOOGLE_SNS_CLIENT_SECRET);
		queryParams.set("grant_type", "authorization_code");

		ResponseEntity<String> response = restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, queryParams, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			log.info("response.getBody() : {}", response.getBody());
			// 3. responseEntity에 담긴 JSON String을 역직렬화해 SocialOAuthToken 객체에 담고 반환
			return objectMapper.readValue(response.getBody(), SocialOauthToken.class);
		} else {
			throw new InssaException("구글 로그인에 실패하였습니다.");
		}
	}

	// 4. 다시 구글로 3에서 받아온 액세스 토큰을 보내 구글 사용자 정보를 받아온다.
	@Override
	public SocialUser getUserInfo(SocialOauthToken oAuthToken)
		throws JsonProcessingException {
		//header에 accessToken을 담는다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+ oAuthToken.getAccess_token());

		URI uri = UriComponentsBuilder
			.fromUriString("https://www.googleapis.com/oauth2/v1/userinfo")
			.build().toUri();

		//HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			// 5. 구글 유저 정보가 담긴 JSON 문자열을 파싱하여 SocailUser 객체에 담기
			return objectMapper.readValue(response.getBody(), SocialUser.class);
		} else {
			throw new InssaException("사용자 정보를 가져오지 못했습니다.");
		}
	}
}
