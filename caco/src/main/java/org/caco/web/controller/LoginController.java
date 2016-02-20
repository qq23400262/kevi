package org.caco.web.controller;

import static org.caco.web.controller.WebUtils.writeJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.caco.web.dao.UserDAO;
import org.caco.web.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caco
 */
@Controller
@RequestMapping("/web/")
public class LoginController {
	@Autowired
	UserDAO userDao;
    /**
     * Just forward to page
     *
     * @return View page
     */
    @RequestMapping("/login")
    public void overview(HttpServletResponse response, HttpServletRequest request) {
    	User user1 = new User();
        user1.setId(1);
        user1.setName("obama");
        userDao.saveUser(user1);
        User user2 = userDao.getUser(1);
        System.out.println(user2.getName());
        writeJson(response, new Result(Result.SUCCESS, "UserInfo", user2));
    }

}