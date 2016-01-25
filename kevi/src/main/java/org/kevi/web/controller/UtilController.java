package org.kevi.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Shengzhao Li
 */
@Controller
@RequestMapping("/util/")
public class UtilController {
    /**
     * Just forward to page
     *
     * @return View page
     */
    @RequestMapping("/mouse")
    public String overview() {
        return "/util/mouse";
    }

}