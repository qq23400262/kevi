package com.sf.module.pcrs.service;

import java.util.List;

import com.sf.module.pcrs.domain.CheckRecord;

/**
 * 类说明：
 * 
 * @author 作者: kevi
 * @version 创建时间：2015-07-28 10:40
 */
public interface CheckRecordService {
	public abstract List<CheckRecord> findCheckRecords(CheckRecord checkRecord);
	public abstract CheckRecord findCheckRecordById(int checkRecordId);
	public abstract void updateCheckRecord(CheckRecord checkRecord);
	public abstract void addCheckRecord(CheckRecord checkRecord);
}