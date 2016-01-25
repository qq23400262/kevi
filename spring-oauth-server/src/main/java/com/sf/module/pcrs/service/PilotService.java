package com.sf.module.pcrs.service;

import java.util.List;

import com.sf.module.pcrs.domain.MyPilot;
import com.sf.module.pcrs.domain.Pilot;

/**
 * 类说明：
 * 
 * @author 作者: kevi
 * @version 创建时间：2015-07-27 15:47
 */
public interface PilotService {
	public abstract List<Pilot>  findPilots(Pilot pilot);
	public abstract Pilot  findPilotById(int pilotId);
	public abstract List<Pilot>  findMyPilots(MyPilot myPilot);
	public abstract void addPilot(Pilot pilot);
	public abstract void addMyPilot(MyPilot myPilot);
	public abstract void deleteMyPilot(MyPilot myPilot);
	public abstract void updatePilot(Pilot pilot);
}