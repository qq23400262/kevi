package com.sf.module.pcrs.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sf.module.pcrs.domain.CheckRecord;
import com.sf.module.pcrs.repository.CheckRecordRepository;
import com.sf.module.pcrs.service.CheckRecordService;

/**
 * 类说明：用户dao实现
 * 
 * @author 作者: LiuJunGuang
 * @version 创建时间：2012-3-25 下午02:24:15
 */
@Service
@Transactional
public class CheckRecordServiceImpl implements CheckRecordService {
	@Autowired
	private CheckRecordRepository checkRecordRepository;

	@Override
	public List<CheckRecord> findCheckRecords(CheckRecord checkRecord) {
		// TODO Auto-generated method stub
		return checkRecordRepository.findCheckRecords(checkRecord);
	}

	@Override
	public CheckRecord findCheckRecordById(int checkRecordId) {
		// TODO Auto-generated method stub
		return checkRecordRepository.findCheckRecordById(checkRecordId);
	}

	@Override
	public void updateCheckRecord(CheckRecord checkRecord) {
		// TODO Auto-generated method stub
		checkRecordRepository.updateCheckRecord(checkRecord);
	}

	@Override
	public void addCheckRecord(CheckRecord checkRecord) {
		// TODO Auto-generated method stub
		checkRecordRepository.insertCheckRecord(checkRecord);
	}
	
}
