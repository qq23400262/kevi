package cc.wdcy.web.controller.unity;

import static cc.wdcy.web.WebUtils.writeJson;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cc.wdcy.domain.dto.UserJsonDto;
import cc.wdcy.service.UserService;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/unity/")
public class UnityController {


    @Autowired
    private UserService userService;


    @RequestMapping("dashboard")
    public String dashboard() {
        return "unity/dashboard";
    }

    @RequestMapping("user_info")
    public void userInfo(HttpServletResponse response) throws Exception {
        final UserJsonDto jsonDto = userService.loadCurrentUserJsonDto();
        writeJson(response, jsonDto);
    }

}