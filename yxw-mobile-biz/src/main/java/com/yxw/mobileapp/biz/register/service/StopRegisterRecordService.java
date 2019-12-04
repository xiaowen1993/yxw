package com.yxw.mobileapp.biz.register.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.yxw.mobileapp.biz.register.entity.StopRegisterRecord;

public interface StopRegisterRecordService {
	public void batchInsert(List<StopRegisterRecord> records);

	public void update(StopRegisterRecord record);

	public PageInfo<StopRegisterRecord> findListByPage(String hospitalId, String branchId, String minScheduleDate, String maxScheduleDate,
			String deptName, String doctorName, String orderNo, Byte finishState, Byte handleState, Byte pushState, Byte ackRefundState,
			Byte refundState, Byte hisRefundState, int pageNum, int pageSize);

	public void deleteByLaunchTime(String min, String max);
}
