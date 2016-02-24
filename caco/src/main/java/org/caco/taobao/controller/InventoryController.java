package org.caco.taobao.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.caco.common.util.Result;
import org.caco.common.util.WebUtils;
import org.caco.common.util.excel.ExcelHelperBiz;
import org.caco.common.util.excel.IExcelHelperBiz;
import org.caco.taobao.domain.Inventory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author caco
 */
@Controller
@RequestMapping("/taobao/")
public class InventoryController {
	private static List<Inventory> inventorys = new ArrayList<Inventory>();
	public 	static List<Inventory> getInventorys() {
		return inventorys;
	}
	
	@RequestMapping("inventory")
    public String inventory() {
        return "taobao/inventory";
    }
	
	@RequestMapping(value = "inventory/update", method = RequestMethod.POST)  
	public void upload(HttpServletResponse response, HttpServletRequest request, @RequestParam String fileName) {
		System.out.println("fileName=="+fileName);
		IExcelHelperBiz excelHelper = new ExcelHelperBiz();
		
		String tplFileName = request.getServletContext().getRealPath("/")
				+ "upload/tpl.xlsx";
		excelHelper.readExcelTemplateByPath(tplFileName);
		inventorys = excelHelper.parseExcel(new File(fileName), Inventory.class);
		Result ret = new Result(Result.SUCCESS, "库存更新", "库存更新成功!");
		WebUtils.writeJson(response, ret);
	}
}