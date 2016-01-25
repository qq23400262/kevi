package cc.wdcy.web.controller;

import static cc.wdcy.web.WebUtils.writeJson;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cc.wdcy.domain.dto.UserDto;
import cc.wdcy.domain.user.User;
import cc.wdcy.service.UserService;

import com.sf.module.pcrs.domain.Result;

/**
 * @author Shengzhao Li
 */
@Controller
//@RequestMapping("/user/")
public class UserController {

	@Autowired
    private UserService userService;

	@Autowired
    private UserValidator userValidator;
	
	@Autowired
    private Md5PasswordEncoder md5PasswordEncoder; 
	
    /**
     * Just forward to page
     *
     * @return View page
     */
    @RequestMapping("/user/overview")
    public String overview() {
        return "user_overview";
    }
    
    /**
     * Just forward to page
     *
     * @return View page
     */
    @RequestMapping(value = "/user/signup", method = RequestMethod.GET)
    public String registerClient(Model model) {
    	model.addAttribute("formDto", new UserDto());
        return "user/register_user";
    }
    
    /**
     * Just forward to page
     *
     * @return View page
     */
    @RequestMapping(value = "/user/signup", method = RequestMethod.POST)
    public String submitRegisterUser(@ModelAttribute("formDto") UserDto formDto, BindingResult result) {
    	try{
    		UserDetails userDetails  = userService.loadUserByUsername(formDto.getUsername());
    		if(userDetails != null) {
    			//用户已经存在
    			result.rejectValue("username", null, "用户已经存在");
    			return "user/register_user";
    		}
    	} catch (UsernameNotFoundException e) {
    		
    	}
    	String encodePassword = md5PasswordEncoder.encodePassword(formDto.getPassword(), null);
//    	System.out.println("form Passowrd:"+formDto.getPassword());
//    	System.out.println("encodePassword:"+encodePassword);
    	
    	formDto.setPassword(encodePassword);
    	User user = formDto.createDomain();
        //userService.addUser(user);
        
		//目前暂时都默认MOBILE权限
        //userService.addPrivilege(user.id(), Privilege.MOBILE.name());
        return "user/register_success";
    }
    
    /**
	 * 修改密码
	 * @param 请求参数
	 * @return
	 */
	@RequestMapping(value = "/rest/user/updatePassword", method = RequestMethod.POST)
	public void updatePassword(@RequestBody String jsonStr, HttpServletRequest request, HttpServletResponse response) {
        @SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>)JSONObject.toBean(JSONObject.fromObject(jsonStr), Map.class);
        String newPassword = map.get("newPassword");
        String oldPassword = map.get("oldPassword");
        newPassword = md5PasswordEncoder.encodePassword(newPassword, null);
        oldPassword = md5PasswordEncoder.encodePassword(oldPassword, null);
//        System.out.println("oldPassword=="+oldPassword);
        User user = userService.loadCurrentUser();
        if(!user.password().equals(oldPassword)) {
        	Result ret = new Result(Result.FAILD, "旧的密码不正确!");
    		writeJson(response, ret);
        } else {
        	user.password(newPassword);
            userService.updateUser(user);
    		Result ret = new Result(Result.SUCCESS, "修改密码成功!");
    		writeJson(response, ret);
        }
        
	}

}