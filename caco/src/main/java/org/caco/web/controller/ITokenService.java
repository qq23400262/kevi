package org.caco.web.controller;

public interface ITokenService {
	UserInfo getUserInfo(String username, String password);
	boolean checkToken(String token);
}
