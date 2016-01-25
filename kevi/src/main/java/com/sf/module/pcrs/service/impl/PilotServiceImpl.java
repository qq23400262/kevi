package com.sf.module.pcrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.module.pcrs.domain.MyPilot;
import com.sf.module.pcrs.domain.Pilot;
import com.sf.module.pcrs.repository.PilotRepository;
import com.sf.module.pcrs.service.PilotService;

/**
 * 类说明：用户dao实现
 * 
 * @author 作者: LiuJunGuang
 * @version 创建时间：2012-3-25 下午02:24:15
 */
@Service
@Transactional
public class PilotServiceImpl implements PilotService {
	@Autowired
	private PilotRepository pilotRepository;

	@Override
	public List<Pilot> findPilots(Pilot pilot) {
		return pilotRepository.findPilots(pilot);
	}

	@Override
	public List<Pilot> findMyPilots(MyPilot myPilot) {
		// TODO Auto-generated method stub
		return pilotRepository.findMyPilots(myPilot);
	}
	
	@Override
	public void addMyPilot(MyPilot myPilot) {
		// TODO Auto-generated method stub
		pilotRepository.insertMyPilot(myPilot);
	}

	@Override
	public void deleteMyPilot(MyPilot myPilot) {
		// TODO Auto-generated method stub
		pilotRepository.deleteMyPilot(myPilot);
		
	}

	@Override
	public Pilot findPilotById(int pilotId) {
		return pilotRepository.findPilotById(pilotId);
	}

	@Override
	public void updatePilot(Pilot pilot) {
		pilotRepository.updatePilot(pilot);
	}

	@Override
	public void addPilot(Pilot pilot) {
		pilotRepository.insertPilot(pilot);
	}
}
