package org.caco.taobao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author caco
 */
@Controller
@RequestMapping("/taobao/")
public class InventoryController {
	@RequestMapping("inventory")
    public String dashboard() {
        return "taobao/inventory";
    }
}