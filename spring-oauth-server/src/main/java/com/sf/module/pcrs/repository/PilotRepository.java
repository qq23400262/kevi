package com.sf.module.pcrs.repository;

import java.util.List;

import cc.wdcy.domain.shared.Repository;

import com.sf.module.pcrs.domain.MyPilot;
import com.sf.module.pcrs.domain.Pilot;

/**
 * 类说明：
 * 
 * @author 作者: kevi
 * @version 创建时间：2015-07-27 15:47
 */
public interface PilotRepository  extends Repository{
	public List<Pilot> findPilots(Pilot pilot);
	public Pilot findPilotById(int pilotId);
	public List<Pilot> findMyPilots(MyPilot myPilot);
	public void insertMyPilot(MyPilot myPilot);
	public void deleteMyPilot(MyPilot myPilot);
	public void updatePilot(Pilot pilot);
	public void insertPilot(Pilot pilot);
}