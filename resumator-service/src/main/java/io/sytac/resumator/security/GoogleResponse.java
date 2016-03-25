package io.sytac.resumator.security;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/*
 * Class to hold google api invocation response
 */
@Getter
@Setter
@ToString
public class GoogleResponse {
	
	private String accessToken;
	
	private String email;
	
	private String name;
	
	private String surname;
	
	private String hostedDomain;

	@Builder
	public GoogleResponse(String accessToken, String email,String name,String surname,String hostedDomain) {
		super();
		this.accessToken = accessToken;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.hostedDomain=hostedDomain;
	}

}
