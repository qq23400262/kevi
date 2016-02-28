package com.fh.util;

import javax.servlet.http.HttpServletRequest;

public class PageUtil {
	public static String getPreBasePath(HttpServletRequest request) {
		String serverName = request.getServerName();
		String port = "";
		if(!"testyun.cn".equals(serverName.toLowerCase())) {
			port = ":"+request.getServerPort();
		}
		return request.getScheme()+"://"+serverName+port;
	}
}
