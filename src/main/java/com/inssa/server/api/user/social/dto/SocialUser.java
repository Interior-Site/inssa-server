package com.inssa.server.api.user.social.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SocialUser {
	public String id;
	public String email;
	public Boolean verifiedEmail;
	public String name;
	public String givenName;
	public String familyName;
	public String picture;
	public String locale;

	@Builder(builderMethodName = "kakaoBuilder", builderClassName = "kakaoBuilder")
	public SocialUser(String id, String email, String name) {
		this.id = id;
		this.email = email;
		this.name = name;
	}
}
