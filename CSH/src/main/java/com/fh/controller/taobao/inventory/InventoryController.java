package com.fh.controller.taobao.inventory;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fh.controller.base.BaseController;
import com.fh.entity.Page;
import com.fh.service.taobao.inventory.InventoryManager;
import com.fh.util.AppUtil;
import com.fh.util.Const;
import com.fh.util.FileDownload;
import com.fh.util.FileUpload;
import com.fh.util.Jurisdiction;
import com.fh.util.ObjectExcelRead;
import com.fh.util.ObjectExcelView;
import com.fh.util.PageData;
import com.fh.util.PathUtil;

/** 
 * 说明：淘宝库存查询
 * 创建人：FH Q313596790
 * 创建时间：2016-02-27
 */
@Controller
@RequestMapping(value="/inventory")
public class InventoryController extends BaseController {
	
	String menuUrl = "inventory/list.do"; //菜单地址(权限用)
	@Resource(name="inventoryService")
	private InventoryManager inventoryService;
	
	/**保存
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/save")
	public ModelAndView save() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"新增Inventory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd.put("INVENTORY_ID", this.get32UUID());	//主键
		inventoryService.save(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**删除
	 * @param out
	 * @throws Exception
	 */
	@RequestMapping(value="/delete")
	public void delete(PrintWriter out) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"删除Inventory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return;} //校验权限
		PageData pd = new PageData();
		pd = this.getPageData();
		inventoryService.delete(pd);
		out.write("success");
		out.close();
	}
	
	/**修改
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/edit")
	public ModelAndView edit() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"修改Inventory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "edit")){return null;} //校验权限
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		inventoryService.edit(pd);
		mv.addObject("msg","success");
		mv.setViewName("save_result");
		return mv;
	}
	
	/**列表
	 * @param page
	 * @throws Exception
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(Page page) throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"列表Inventory");
		//if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;} //校验权限(无权查看时页面会有提示,如果不注释掉这句代码就无法进入列表页面,所以根据情况是否加入本句代码)
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		String keywords = pd.getString("keywords");				//关键词检索条件
		if(null != keywords && !"".equals(keywords)){
			pd.put("keywords", keywords.trim());
		}
		page.setPd(pd);
		List<PageData>	varList = inventoryService.list(page);	//列出Inventory列表
		mv.setViewName("taobao/inventory/inventory_list");
		mv.addObject("varList", varList);
		mv.addObject("pd", pd);
		mv.addObject("QX",Jurisdiction.getHC());	//按钮权限
		return mv;
	}
	
	/**去新增页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goAdd")
	public ModelAndView goAdd()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		mv.setViewName("taobao/inventory/inventory_edit");
		mv.addObject("msg", "save");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**去修改页面
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/goEdit")
	public ModelAndView goEdit()throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		pd = inventoryService.findById(pd);	//根据ID读取
		mv.setViewName("taobao/inventory/inventory_edit");
		mv.addObject("msg", "edit");
		mv.addObject("pd", pd);
		return mv;
	}	
	
	 /**批量删除
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/deleteAll")
	@ResponseBody
	public Object deleteAll() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"批量删除Inventory");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "del")){return null;} //校验权限
		PageData pd = new PageData();		
		Map<String,Object> map = new HashMap<String,Object>();
		pd = this.getPageData();
		List<PageData> pdList = new ArrayList<PageData>();
		String DATA_IDS = pd.getString("DATA_IDS");
		if(null != DATA_IDS && !"".equals(DATA_IDS)){
			String ArrayDATA_IDS[] = DATA_IDS.split(",");
			inventoryService.deleteAll(ArrayDATA_IDS);
			pd.put("msg", "ok");
		}else{
			pd.put("msg", "no");
		}
		pdList.add(pd);
		map.put("list", pdList);
		return AppUtil.returnObject(pd, map);
	}
	
	 /**导出到excel
	 * @param
	 * @throws Exception
	 */
	@RequestMapping(value="/excel")
	public ModelAndView exportExcel() throws Exception{
		logBefore(logger, Jurisdiction.getUsername()+"导出Inventory到excel");
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "cha")){return null;}
		ModelAndView mv = new ModelAndView();
		PageData pd = new PageData();
		pd = this.getPageData();
		Map<String,Object> dataMap = new HashMap<String,Object>();
		List<String> titles = new ArrayList<String>();
		titles.add("款式年份");	//1
		titles.add("款式编号");	//2
		titles.add("款式季节");	//3
		titles.add("款式名称");	//4
		titles.add("款式现价");	//5
		titles.add("颜色");	//6
		titles.add("S");	//7
		titles.add("M");	//8
		titles.add("L");	//9
		titles.add("XL");	//10
		titles.add("XXL");	//11
		titles.add("总库存");	//12
		titles.add("日期");	//13
		dataMap.put("titles", titles);
		List<PageData> varOList = inventoryService.listAll(pd);
		List<PageData> varList = new ArrayList<PageData>();
		for(int i=0;i<varOList.size();i++){
			PageData vpd = new PageData();
			vpd.put("var1", varOList.get(i).get("YEARS").toString());	//1
			vpd.put("var2", varOList.get(i).getString("CODE"));	//2
			vpd.put("var3", varOList.get(i).getString("SEASON"));	//3
			vpd.put("var4", varOList.get(i).getString("NAME"));	//4
			vpd.put("var5", varOList.get(i).get("PRICE").toString());	//5
			vpd.put("var6", varOList.get(i).getString("COLOR"));	//6
			vpd.put("var7", varOList.get(i).get("SS").toString());	//7
			vpd.put("var8", varOList.get(i).get("SM").toString());	//8
			vpd.put("var9", varOList.get(i).get("SL").toString());	//9
			vpd.put("var10", varOList.get(i).get("SXL").toString());	//10
			vpd.put("var11", varOList.get(i).get("SXXL").toString());	//11
			vpd.put("var12", varOList.get(i).get("TOTAL").toString());	//12
			vpd.put("var13", varOList.get(i).getString("DATE"));	//13
			varList.add(vpd);
		}
		dataMap.put("varList", varList);
		ObjectExcelView erv = new ObjectExcelView();
		mv = new ModelAndView(erv,dataMap);
		return mv;
	}
	
	/**打开上传EXCEL页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goUploadExcel")
	public ModelAndView goUploadExcel()throws Exception{
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("taobao/inventory/uploadexcel");
		return mv;
	}
	
	/**下载模版
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/downExcel")
	public void downExcel(HttpServletResponse response)throws Exception{
		FileDownload.fileDownload(response, PathUtil.getClasspath() + Const.FILEPATHFILE + "Inventory.xls", "Inventory.xls");
	}
	
	/**从EXCEL导入到数据库
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/readExcel")
	public ModelAndView readExcel(
			@RequestParam(value="excel",required=false) MultipartFile file
			) throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = new PageData();
		if(!Jurisdiction.buttonJurisdiction(menuUrl, "add")){return null;}
		if (null != file && !file.isEmpty()) {
			String filePath = PathUtil.getClasspath() + Const.FILEPATHFILE;								//文件上传路径
			String fileName =  FileUpload.fileUp(file, filePath, "userexcel");							//执行上传
			@SuppressWarnings({ "unchecked", "rawtypes" })
			List<PageData> listPd = (List)ObjectExcelRead.readExcel(filePath, fileName, 2, 0, 0);		//执行读EXCEL操作,读出的数据导入List 2:从第3行开始；0:从第A列开始；0:第0个sheet
			/**
			 * var0 :款式年份
			 * var1 :款式编号
			 * var2 :款式季节
			 * var3 :款式名称
			 * var4 :款式现价
			 * var5 :颜色
			 * var6 :S
			 * var7 :M
			 * var8 :L
			 * var9 :XL
			 * var10:XXL
			 * var11:总库存
			 * var12:日期
			 */
			String var=null;
			for(int i=0;i<listPd.size();i++){
				pd.put("INVENTORY_ID", this.get32UUID());										//ID
				pd.put("YEARS", listPd.get(i).getString("var0"));								//款式年份
				pd.put("CODE", listPd.get(i).getString("var1"));								//款式编号
				pd.put("SEASON", listPd.get(i).getString("var2"));								//款式季节
				pd.put("NAME", listPd.get(i).getString("var3"));								//款式名称
				pd.put("PRICE", listPd.get(i).getString("var4"));								//款式现价
				pd.put("COLOR", listPd.get(i).getString("var5"));								//颜色
				pd.put("SS", (var=listPd.get(i).getString("var6"))==null||var.trim().equals("")?0:var);			//S
				pd.put("SM", (var=listPd.get(i).getString("var7"))==null||var.trim().equals("")?0:var);			//M
				pd.put("SL", (var=listPd.get(i).getString("var8"))==null||var.trim().equals("")?0:var);			//L
				pd.put("SXL", (var=listPd.get(i).getString("var9"))==null||var.trim().equals("")?0:var);		//XL
				pd.put("SXXL", (var=listPd.get(i).getString("var10"))==null||var.trim().equals("")?0:var);		//XXL
				pd.put("TOTAL", (var=listPd.get(i).getString("var11"))==null||var.trim().equals("")?0:var);		//总库存
				pd.put("DATE", listPd.get(i).getString("var12"));							//日期
				
				inventoryService.save(pd);
			}
			/*存入数据库操作======================================*/
			mv.addObject("msg","success");
		}
		mv.setViewName("save_result");
		return mv;
	}
	
	@InitBinder
	public void initBinder(WebDataBinder binder){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(format,true));
	}
}
