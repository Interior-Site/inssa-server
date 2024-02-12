package com.inssa.server.api.user.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inssa.server.api.user.social.dto.SocialOauthToken;
import com.inssa.server.api.user.social.dto.SocialUser;
import com.inssa.server.common.exception.InssaException;
import java.math.BigInteger;
import java.net.URI;
import java.security.SecureRandom;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class NaverOauth implements SocialOauth {
	@Value("${sns.naver.client.id}")
	private String NAVER_SNS_CLIENT_ID;
	@Value("${sns.naver.client.secret}")
	private String NAVER_SNS_CLIENT_SECRET;
	@Value("${sns.naver.redirect.uri}")
	private String NAVER_SNS_REDIRECT_URI;
	@Value("${sns.naver.authorization.grantType}")
	private String NAVER_SNS_AUTH_TYPE;
	@Value("${sns.naver.authorization.uri}")
	private String NAVER_SNS_AUTHORIZATION_URI;
	@Value("${sns.naver.token.uri}")
	private String NAVER_SNS_TOKEN_URI;
	@Value("${sns.naver.user.info.uri}")
	private String NAVER_SNS_USER_INFO_URI;

	private final ObjectMapper objectMapper;
	private final RestTemplate restTemplate;

	private static final Logger log = LoggerFactory.getLogger(NaverOauth.class);

	@Override
	public String getOauthRedirectURL() {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		queryParams.set("response_type", "code");
		queryParams.set("client_id", NAVER_SNS_CLIENT_ID);
		queryParams.set("redirect_uri", NAVER_SNS_REDIRECT_URI);
		queryParams.set("state", new BigInteger(130, new SecureRandom()).toString());

		return UriComponentsBuilder
			.fromUriString(NAVER_SNS_AUTHORIZATION_URI)
			.queryParams(queryParams)
			.encode().build().toString();
	}

	@Override
	public SocialOauthToken getAccessToken(String code) {
		MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
		try {
			queryParams.set("grant_type", NAVER_SNS_AUTH_TYPE);
			queryParams.set("client_id", NAVER_SNS_CLIENT_ID);
			queryParams.set("client_secret", NAVER_SNS_CLIENT_SECRET);
			queryParams.set("code", code);
			queryParams.set("state", new BigInteger(130, new SecureRandom()).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<String> response =
			restTemplate.postForEntity(NAVER_SNS_TOKEN_URI, queryParams, String.class);

		if (response.getStatusCode() == HttpStatus.OK) {
			log.info("response.getBody() : {}", response.getBody());
			try {
				return objectMapper.readValue(response.getBody(), SocialOauthToken.class);
			} catch (JsonProcessingException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new InssaException("네이버 로그인에 실패하였습니다.");
		}
	}

	@Override
	public SocialUser getUserInfo(SocialOauthToken oAuthToken) {
		//header에 accessToken을 담는다.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization","Bearer "+ oAuthToken.getAccess_token());

		URI uri = UriComponentsBuilder
			.fromUriString(NAVER_SNS_USER_INFO_URI)
			.build().toUri();

		//HttpEntity를 하나 생성해 헤더를 담아서 restTemplate으로 구글과 통신하게 된다.
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET,
			new HttpEntity<>(headers), String.class);

		JSONObject jsonObject = (JSONObject) JSONValue.parse(Objects.requireNonNull(response.getBody()));

		try {
			return objectMapper.readValue(jsonObject.get("response").toString(), SocialUser.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
