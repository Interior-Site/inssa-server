package com.inssa.server.api.user.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inssa.server.api.user.social.dto.SocialOauthToken;
import com.inssa.server.api.user.social.dto.SocialUser;
import java.net.URI;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class KakaoOauth implements SocialOauth {
	@Value("${sns.kakao.client.id}")
	private String KAKAO_SNS_CLIENT_ID;
	@Value("${sns.kakao.redirect.uri}")
	private String KAKAO_SNS_CALLBACK_URL;
	@Value("${sns.kakao.authorization.grantType}")
	private String KAKAO_SNS_AUTH_TYPE;
	@Value("${sns.kakao.authorization.uri}")
	private String KAKAO_SNS_AUTHORIZATION_URI;
	@Value("${sns.kakao.token.uri}")
	private String KAKAO_SNS_TOKEN_URI;
	@Value("${sns.kakao.user.info.uri}")
	private String KAKAO_SNS_USER_INFO_URI;

	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;
	private static final Logger log = LoggerFactory.getLogger(KakaoOauth.class);

	// 1. redirect Url 생성
	@Override
	public String getOauthRedirectURL() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.set("response_type", "code");
		queryParams.set("client_id", KAKAO_SNS_CLIENT_ID);
		queryParams.set("redirect_uri", KAKAO_SNS_CALLBACK_URL);
		queryParams.set("scope", "account_email profile_nickname");

		return UriComponentsBuilder
			.fromUriString(KAKAO_SNS_AUTHORIZATION_URI)
			.queryParams(queryParams)
			.encode().build().toString();
	}

	@Override
	public SocialOauthToken getAccessToken(String code) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.set("code", code);
		queryParams.set("client_id", KAKAO_SNS_CLIENT_ID);
		queryParams.set("redirect_uri", KAKAO_SNS_CALLBACK_URL);
		queryParams.set("grant_type", KAKAO_SNS_AUTH_TYPE);

		URI uri = UriComponentsBuilder
			.fromUriString(KAKAO_SNS_TOKEN_URI)
			.queryParams(queryParams)
			.encode().build().toUri();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, new HttpEntity<>(headers), String.class);

		log.info("response.getBody() : {}", response.getBody());

		try {
			return objectMapper.readValue(response.getBody(), SocialOauthToken.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public SocialUser getUserInfo(SocialOauthToken oAuthToken) {
		//header에 accessToken을 담는다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Bearer "+ oAuthToken.getAccess_token());

		headers.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));

		URI uri = UriComponentsBuilder
			.fromUriString(KAKAO_SNS_USER_INFO_URI)
			.encode().build().toUri();

		//HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,
			new HttpEntity<>(headers), String.class);

		// 카카오 로그인은 objectMapper를 이용한 json변환이 어려워 JSONObject를 이용해 변환
		JSONObject jsonObject = (JSONObject) JSONValue.parse(Objects.requireNonNull(response.getBody()));
		JSONObject accountObject = (JSONObject) jsonObject.get("kakao_account");
		JSONObject profileObject = (JSONObject) accountObject.get("profile");
		String id = String.valueOf((Long) jsonObject.get("id"));

		return SocialUser.kakaoBuilder()
							.id(id)
							.name((String) profileObject.get("nickname"))
							.email((String) accountObject.get("email"))
							.build();

	}
}
