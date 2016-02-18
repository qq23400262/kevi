package org.kevi.web.controller;

import static cc.wdcy.web.WebUtils.writeJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sf.module.pcrs.domain.Result;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/util/")
public class UtilController {
	TokenService tokenService = new TokenService();
    /**
     * Just forward to page
     *
     * @return View page
     */
    @RequestMapping("/mouse")
    public String overview() {
        return "/util/mouse";
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
	public void getUserInfo(HttpServletResponse response, HttpServletRequest request,String username, String password) {
    	UserInfo user = tokenService.getUserInfo(username, password);
    	writeJson(response, new Result(user==null?Result.FAILD:Result.SUCCESS, "UserInfo", user));
	}
    
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public void getUserInfo1(HttpServletResponse response, HttpServletRequest request,String username, String password) {
    	UserInfo user = tokenService.getUserInfo(username, password);
    	writeJson(response, new Result(user==null?Result.FAILD:Result.SUCCESS, "UserInfo", user));
	}
    
    @RequestMapping(value = "/searchWeather/{city}", method = RequestMethod.GET)
	public void searchWeather(HttpServletResponse response, @PathVariable String city) {
		writeJson(response, new Result(Result.SUCCESS, city+":天气很好"));
	}
    
    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
	public void checkToken(HttpServletResponse response, HttpServletRequest request,String token) {
    	Result ret = null;
    	boolean isOK = tokenService.checkToken(token);
    	if(isOK) {
    		ret = new Result(Result.SUCCESS);
		} else {
			ret = new Result(Result.FAILD);
		}
    	writeJson(response, ret);
	}
}