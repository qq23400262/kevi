package org.caco.common.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author Shengzhao Li
 */
public abstract class WebUtils {
	static JsonConfig jsonConfig;  
	  

    //private
    private WebUtils() {
    }


    public static void writeJson(HttpServletResponse response, Object o) {
    	if(jsonConfig == null) {
    		jsonConfig = new JsonConfig();
    		jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor());
    	}
    	JSON json = JSONObject.fromObject(o,jsonConfig);
        response.setContentType("application/json;charset=UTF-8");
        try {
            PrintWriter writer = response.getWriter();
            json.write(writer);
            writer.flush();
        } catch (IOException e) {
            throw new IllegalStateException("Write json to response error", e);
        }

    }

}