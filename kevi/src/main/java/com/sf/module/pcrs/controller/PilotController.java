package com.sf.module.pcrs.controller;

import static cc.wdcy.web.WebUtils.writeJson;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cc.wdcy.service.UserService;

import com.sf.module.pcrs.domain.CheckRecord;
import com.sf.module.pcrs.domain.MyPilot;
import com.sf.module.pcrs.domain.Pilot;
import com.sf.module.pcrs.domain.Result;
import com.sf.module.pcrs.service.CheckRecordService;
import com.sf.module.pcrs.service.PilotService;

@Controller
@RequestMapping("/rest")
public class PilotController {
	@Autowired
	private PilotService pilotService;
	@Autowired
	private CheckRecordService checkRecordService;
	@Autowired
    private UserService userService;
	
	/**
	 * 查找某航空公司下的所有飞行员
	 * @param airlineId 航空公司id
	 * @return
	 */
	@RequestMapping(value = "/airline/{airlineId}/pilots", method = RequestMethod.GET)
	public void findPolitsByAirlineId(HttpServletResponse response, @PathVariable int airlineId) {
		Pilot pilot = new Pilot();
		pilot.setAirlineId(airlineId);
		Result ret = new Result(Result.SUCCESS, "成功", pilotService.findPilots(pilot));
		writeJson(response, ret);
	}
	
	/**
	 * 查找所有的飞行员
	 * @param request
	 * @param response
	 * @param pilot
	 * @return
	 */
	@RequestMapping(value = "/pilot/all", method = RequestMethod.GET)
	public void findAllPolits(HttpServletResponse response) {
		Result ret = new Result(Result.SUCCESS, "成功", pilotService.findPilots(null));
		writeJson(response, ret);
	}
	
	/**
	 * 查找指定飞行员
	 * @param pilotId 飞行员id
	 * @return
	 */
	@RequestMapping(value = "/pilot/{id}", method = RequestMethod.GET)
	public void findPolitById(HttpServletResponse response, @PathVariable int id) {
//		final UserJsonDto jsonDto = userService.loadCurrentUserJsonDto();
//	    writeJson(response, JSONObject.fromObject(jsonDto));
		
	    Result ret = new Result(Result.SUCCESS, "成功", pilotService.findPilotById(id));
		writeJson(response, ret);
	}
	
	/**
	 * 按照飞行员名称查找飞行员
	 * @param name 飞行员名称
	 * @return
	 */
	@RequestMapping(value = "/pilot/search/{name}", method = RequestMethod.GET)
	public void findPolitByName(HttpServletResponse response, @PathVariable String name) {
		Pilot pilot = new Pilot();
		pilot.setPilot(name);
		Result ret = new Result(Result.SUCCESS, "成功", pilotService.findPilots(pilot));
		writeJson(response, ret);
	}
	
	/**
	 * 按照飞行员名称查找飞行员
	 * @param name 飞行员名称
	 * @return
	 */
	@RequestMapping(value = "/pilot/add", method = RequestMethod.POST)
	public void addPilot(@RequestBody String jsonStr, HttpServletRequest request, HttpServletResponse response) {
		Pilot pilot = (Pilot)JSONObject.toBean(JSONObject.fromObject(jsonStr), Pilot.class);
		Pilot pilot1 = new Pilot();
		pilot1.setLicence(pilot.getLicence());
		List<Pilot> pilots = pilotService.findPilots(pilot1);
		if(pilots != null && pilots.size() > 0) {
			Result ret = new Result(Result.FAILD, "执照重复", pilots.get(0));
			writeJson(response, ret);
		} else  {
			pilotService.addPilot(pilot);
			System.out.println(pilot.getId());
			Result ret = new Result(Result.SUCCESS, "添加成功", pilot);
			writeJson(response, ret);
		}
	}
	
	/**
	 * 查找我收藏的飞行员
	 * @return
	 */
	@RequestMapping(value = "/myPilot/all", method = RequestMethod.GET)
	public void findAllMyPolits(HttpServletResponse response) {
		MyPilot myPilot = new MyPilot();
		myPilot.setUserId(userService.loadCurrentUser().id());
		Result ret = new Result(Result.SUCCESS, "成功", pilotService.findMyPilots(myPilot));
		writeJson(response, ret);
	}
	
	/**
	 * 收藏飞行员
	 * @param jsonStr
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/myPilot/add", method = RequestMethod.POST)
	public void addMyPilot(@RequestBody String jsonStr, HttpServletRequest request, HttpServletResponse response) {
		MyPilot myPilot = (MyPilot)JSONObject.toBean(JSONObject.fromObject(jsonStr), MyPilot.class);
		myPilot.setUserId(userService.loadCurrentUser().id());
		pilotService.addMyPilot(myPilot);
		Result ret = new Result(Result.SUCCESS);
		writeJson(response, ret);
	}
	
	/**
	 * 取消收藏飞行员
	 * @param jsonStr
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/myPilot/delete", method = RequestMethod.POST)
	public void deleteMyPilot(@RequestBody String jsonStr, HttpServletRequest request, HttpServletResponse response) {
		MyPilot myPilot = (MyPilot)JSONObject.toBean(JSONObject.fromObject(jsonStr), MyPilot.class);
		myPilot.setUserId(userService.loadCurrentUser().id());
		pilotService.deleteMyPilot(myPilot);
		Result ret = new Result(Result.SUCCESS);
		writeJson(response, ret);
	}
	
	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)  
	public void upload(HttpServletResponse response, HttpServletRequest request,String type, int id, @RequestParam MultipartFile[] files) {
		String imageNames="";
		String imageName="";
		
		String port = request.getLocalPort()==80?"":":"+request.getLocalPort();
		String url = "http://"+request.getLocalAddr() + port + "/PCRS/upload/";
		try {
			// 文件个数至少1个以上
			if (files != null && files.length > 0) {
				// 循环获取file数组中得文件
				for (int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					if(!file.isEmpty()) {
						// 文件保存路径
//						String filePath = request.getServletContext().getRealPath("/")
//								+ "upload/" + file.getOriginalFilename();
						imageName = UUID.randomUUID()+(file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")));
						if("".equals(imageNames)) {
							imageNames = url + imageName;
						} else {
							imageNames += "," + url + imageName;
						}
						String filePath = request.getServletContext().getRealPath("/")
								+ "upload/" + imageName;
						// 转存文件
						file.transferTo(new File(filePath));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if("avatar".equals(type)) {
			Pilot pilot = pilotService.findPilotById(id);
			pilot.setAvatar(imageNames);
			pilotService.updatePilot(pilot);
		} else {
			CheckRecord checkRecord = checkRecordService.findCheckRecordById(id);
			checkRecord.setCheckImages(imageNames);
			checkRecordService.updateCheckRecord(checkRecord);
		}
		Result ret = new Result(Result.SUCCESS, "上传成功", imageNames);
		writeJson(response, ret);
	}  
}