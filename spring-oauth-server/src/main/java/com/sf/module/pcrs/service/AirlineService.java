package com.sf.module.pcrs.service;

import java.util.List;

import com.sf.module.pcrs.domain.Airline;

/**
 * 类说明：
 * 
 * @author 作者: LiuJunGuang
 * @version 创建时间：2012-3-25 下午03:03:19
 */
public interface AirlineService {
	public abstract List<Airline> findAirlines(Airline airline);
	public abstract Airline findAirlineById(int airlineId);
}