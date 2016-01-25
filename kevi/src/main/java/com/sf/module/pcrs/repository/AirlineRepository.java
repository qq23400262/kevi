package com.sf.module.pcrs.repository;

import java.util.List;

import cc.wdcy.domain.shared.Repository;

import com.sf.module.pcrs.domain.Airline;

/**
 * 类说明：
 * 
 * @author 作者: LiuJunGuang
 * @version 创建时间：2012-3-25 下午03:03:19
 */
public interface AirlineRepository  extends Repository{
	public List<Airline> findAirlines(Airline airline);
	public Airline findAirlineById(int airlineId);
}