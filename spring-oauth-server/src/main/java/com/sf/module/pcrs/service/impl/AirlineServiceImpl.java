package com.sf.module.pcrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.module.pcrs.domain.Airline;
import com.sf.module.pcrs.repository.AirlineRepository;
import com.sf.module.pcrs.service.AirlineService;

/**
 * 类说明：AirlineService实现
 * 
 * @author 作者: kevi
 * @version 创建时间：2015-07-24 16:30
 */
@Service
@Transactional
public class AirlineServiceImpl implements AirlineService {

	@Autowired
	private AirlineRepository airlineRepository;

	@Override
	public List<Airline> findAirlines(Airline airline) {
		// TODO Auto-generated method stub
		return airlineRepository.findAirlines(airline);
	}

	@Override
	public Airline findAirlineById(int airlineId) {
		// TODO Auto-generated method stub
		return airlineRepository.findAirlineById(airlineId);
	}

	
}
