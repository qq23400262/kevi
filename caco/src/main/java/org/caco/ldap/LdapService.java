package org.caco.ldap;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

public class LdapService {

	/**
	 * check user in LDAP
	 * 
	 * @param user_code
	 * @param user_pwd
	 * @return
	 */
	@SuppressWarnings({ "unused", "rawtypes", "unchecked" })
	public static boolean checkUser(String user_code, String user_pwd) {
		String domainType = "ALL";
		boolean flag = true;
		String URL = "ldap://HQDC02.SF.COM:389/";
		String BASEDN = "OU=SF-Express,DC=sf,DC=com";
		String FACTORY = "com.sun.jndi.ldap.LdapCtxFactory";
		LdapContext ctx = null;
		Control[] connCtls = null;
		String userDN = "";
		SearchResult si = null;
		Hashtable env = new Hashtable();
		env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY);
		// LDAP server
		env.put(Context.PROVIDER_URL, URL + BASEDN);
		// 注意用户名的写法：domain\User 或User@domain.com
		String domainUserName = "sf\\" + user_code;
		env.put(Context.SECURITY_PRINCIPAL, domainUserName);
		env.put(Context.SECURITY_CREDENTIALS, user_pwd);

		try {
			ctx = new InitialLdapContext(env, connCtls);

		} catch (javax.naming.AuthenticationException e) {
			System.out.println("Authentication faild: " + e.toString());
			flag = false;
		} catch (Exception e) {
			System.out.println("Something wrong while authenticating: " + e.toString());
			flag = false;
		}

		return flag;
	}
}
