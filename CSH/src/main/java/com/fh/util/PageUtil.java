package com.fh.util;

import javax.servlet.http.HttpServletRequest;

public class PageUtil {
	public static String getBasePath(HttpServletRequest request, String path) {
		String serverName = request.getServerName();
		String port = "";
		if(!"testyun.cn".equals(serverName.toLowerCase())) {
			port = ":"+request.getServerPort();
		}
		return request.getScheme()+"://"+serverName+port+path+"/";
	}
}
