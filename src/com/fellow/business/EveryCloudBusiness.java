package com.fellow.business;

import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConfig;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.user.UserAPI;

public interface EveryCloudBusiness {
	
	public static final String SESSION_LOGIN_ACCESS_TOKEN = "SESSION_LOGIN_ACCESS_TOKEN";
	
	public static final String SESSION_LOGIN_USER_INFO = "SESSION_LOGIN_USER_INFO";
	
	public static final String SESSION_LOGIN_PROVIDER = "SESSION_LOGIN_PROVIDER";
	
	OAuthConfig getOAuthConfig(String provider);
	
	Api getOAuthAPI(String provider);
	
	AccessToken createAccessToken(String provider);
	
	UserAPI getUserAPI(String provider);
}
