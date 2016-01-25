package com.sf.module.pcrs.controller;

import static cc.wdcy.web.WebUtils.writeJson;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sf.module.pcrs.domain.Airline;
import com.sf.module.pcrs.domain.Result;
import com.sf.module.pcrs.service.AirlineService;
@Controller
@RequestMapping("/rest/airline")
public class AirlineController {
	@Autowired
	private AirlineService airlineService;
	/**
	 * 按名称查找航空公司
	 * @param airlineStr
	 * @return
	 */
	@RequestMapping(value = "/search/{name}", method = RequestMethod.GET)
	public void findAirlines(HttpServletResponse response, @PathVariable String name) {
		System.out.println(name);
		Airline airline = new Airline();
		airline.setAirline(name);
		Result ret = new Result(Result.SUCCESS, "成功", airlineService.findAirlines(airline));
		writeJson(response, ret);
	}
	/**
	 * 查找所有航空公司
	 * @return
	 */
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public void findAirlines(HttpServletResponse response) {
		Airline airline = new Airline();
		Result ret = new Result(Result.SUCCESS, "成功", airlineService.findAirlines(airline));
		writeJson(response, ret);
	}
	
	/**
	 * 查找所有航空公司
	 * @return
	 */
	@RequestMapping(value = "/{airlineId}", method = RequestMethod.GET)
	public void findAirlineById(HttpServletResponse response, @PathVariable int airlineId) {
		Result ret = new Result(Result.SUCCESS, "成功", airlineService.findAirlineById(airlineId));
		writeJson(response, ret);
	}
}