package com.inssa.server.api.user.social.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inssa.server.api.user.social.dto.SocialOauthToken;
import com.inssa.server.api.user.social.dto.SocialUser;
import com.inssa.server.api.user.social.model.SocialType;

public interface SocialOauth {
	String getOauthRedirectURL();
	SocialOauthToken getAccessToken(String code) throws JsonProcessingException;
	SocialUser getUserInfo(SocialOauthToken oauthToken) throws JsonProcessingException;

	default SocialType type() {
		if (this instanceof GoogleOauth) {
			return SocialType.google;
		} else if (this instanceof KakaoOauth) {
			return SocialType.kakao;
		} else {
			return null;
		}
	}
}
