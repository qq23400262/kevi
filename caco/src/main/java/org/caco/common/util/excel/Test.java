package org.caco.common.util.excel;

import java.io.File;
import java.util.List;

import org.caco.taobao.domain.Inventory;

public class Test {
	public static void main(String[] args) {
		IExcelHelperBiz excelHelper = new ExcelHelperBiz();
		excelHelper.readExcelTemplateByPath("C:\\Users\\Administrator\\Desktop\\taobao\\tpl.xlsx");
		List<Inventory> list = excelHelper.parseExcel(new File("C:\\Users\\Administrator\\Desktop\\taobao\\test.xlsx"), Inventory.class);
		for (Inventory excelData : list) {
			System.out.print(excelData+",");
		}
	}
}
