package com.sf.module.pcrs.repository;

import java.util.List;

import cc.wdcy.domain.shared.Repository;

import com.sf.module.pcrs.domain.CheckRecord;

/**
 * 类说明：
 * 
 * @author 作者: kevi
 * @version 创建时间：2015-07-28 10:31
 */
public interface CheckRecordRepository  extends Repository{
	public List<CheckRecord> findCheckRecords(CheckRecord checkRecord);
	public void insertCheckRecord(CheckRecord checkRecord);
	public void updateCheckRecord(CheckRecord checkRecord);
	public CheckRecord findCheckRecordById(int checkRecordId);
}