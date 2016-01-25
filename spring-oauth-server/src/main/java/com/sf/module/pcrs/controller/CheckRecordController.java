package com.sf.module.pcrs.controller;

import static cc.wdcy.web.WebUtils.writeJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sf.module.pcrs.domain.CheckRecord;
import com.sf.module.pcrs.domain.Result;
import com.sf.module.pcrs.service.CheckRecordService;

@Controller
@RequestMapping("/rest")
public class CheckRecordController {
	@Autowired
	private CheckRecordService checkRecordService;
	
	/**
	 * 查找某飞行员的考核记录
	 * @param pilotId 飞行员id
	 * @return
	 */
	@RequestMapping(value = "/polit/{pilotId}/checkRecords", method = RequestMethod.GET)
	public void findPolitCheckRecords(HttpServletResponse response, @PathVariable int pilotId) {
		CheckRecord checkRecord = new CheckRecord();
		checkRecord.setPilotId(pilotId);
		Result ret = new Result(Result.SUCCESS, "成功", checkRecordService.findCheckRecords(checkRecord));
		writeJson(response, ret);
	}
	
	/**
	 * 按找某个考核记录
	 * @param checkRecordId 考核记录id
	 * @return
	 */
	@RequestMapping(value = "/checkRecord/{checkRecordId}", method = RequestMethod.GET)
	public void findCheckRecordById(HttpServletResponse response, @PathVariable int checkRecordId) {
		Result ret = new Result(Result.SUCCESS, "成功", checkRecordService.findCheckRecordById(checkRecordId));
		writeJson(response, ret);
	}
	
	/**
	 * 添加考核记录
	 * @param checkRecord
	 * @return
	 */
	@RequestMapping(value = "/checkRecord/add", method = RequestMethod.POST)
	public void addCheckRecord(@RequestBody String jsonStr, HttpServletRequest request, HttpServletResponse response) {
		CheckRecord checkRecord = (CheckRecord)JSONObject.toBean(JSONObject.fromObject(jsonStr), CheckRecord.class);
		checkRecordService.addCheckRecord(checkRecord);
		Result ret = new Result(Result.SUCCESS, "添加考核记录成功", checkRecord);
		writeJson(response, ret);
	}
}