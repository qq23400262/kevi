package org.caco.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenService implements  ITokenService{
	Map<String, UserInfo> userMap;
	Map<String, String> userTokenMap;
	public TokenService() {
		userMap = new HashMap<String, UserInfo>();
		userTokenMap = new HashMap<String, String>();
		UserInfo user = new UserInfo();
		user.setUsername("admin");
		userMap.put(user.getUsername()+"-admin123", user);
		user.setUsername("422575");
		userMap.put(user.getUsername()+"-123", user);
	}
	@Override
	public UserInfo getUserInfo(String username, String password) {
		UserInfo user = userMap.get(username+"-"+password);
		if(user != null) {
			String token = userTokenMap.get(username);
			if(token==null) {
				token = UUID.randomUUID().toString();
				userTokenMap.put(username, token);
			}
			user.setAccessToken(token);
		}
		return user;
	}

	@Override
	public boolean checkToken(String token) {
		for (String t : userTokenMap.values()) {
			if(t.equals(token))
				return true;
		}
		return false;
	}

}
