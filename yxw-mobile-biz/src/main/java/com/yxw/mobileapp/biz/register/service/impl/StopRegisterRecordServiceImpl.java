package com.yxw.mobileapp.biz.register.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.yxw.framework.common.spring.ext.SpringContextHolder;
import com.yxw.mobileapp.biz.register.dao.StopRegisterRecordDao;
import com.yxw.mobileapp.biz.register.entity.StopRegisterRecord;
import com.yxw.mobileapp.biz.register.service.StopRegisterRecordService;

@Service(value = "stopRegisterRecordService")
public class StopRegisterRecordServiceImpl implements StopRegisterRecordService {
	private StopRegisterRecordDao dao = SpringContextHolder.getBean(StopRegisterRecordDao.class);

	@Override
	public void batchInsert(List<StopRegisterRecord> records) {
		dao.batchInsert(records);
	}

	@Override
	public void update(StopRegisterRecord record) {
		dao.update(record);
	}

	@Override
	public PageInfo<StopRegisterRecord> findListByPage(String hospitalId, String branchId, String minScheduleDate, String maxScheduleDate,
			String deptName, String doctorName, String orderNo, Byte finishState, Byte handleState, Byte pushState, Byte ackRefundState,
			Byte refundState, Byte hisRefundState, int pageNum, int pageSize) {
		Map<String, Object> params = new HashMap<>(9);
		params.put("hospitalId", hospitalId);
		params.put("branchId", branchId);
		params.put("minScheduleDate", minScheduleDate);
		params.put("maxScheduleDate", maxScheduleDate);
		params.put("deptName", deptName);
		params.put("doctorName", doctorName);
		params.put("orderNo", orderNo);
		setStateRange(params, finishState, handleState, pushState, ackRefundState, refundState, hisRefundState);
		setStateMatcher(params, finishState, handleState, pushState, ackRefundState, refundState, hisRefundState);

		return dao.findListByPage(params, new Page<StopRegisterRecord>(pageNum, pageSize));
	}

	private void setStateRange(Map<String, Object> params, Byte finishState, Byte handleState, Byte pushState, Byte ackRefundState,
			Byte refundState, Byte hisRefundState) {
		params.put("minState", getMinState(finishState, handleState, pushState, ackRefundState, refundState, hisRefundState));
		params.put("maxState", getMaxState(finishState, handleState, pushState, ackRefundState, refundState, hisRefundState));
	}

	private int getMinState(Byte finishState, Byte handleState, Byte pushState, Byte ackRefundState, Byte refundState, Byte hisRefundState) {
		int minState = 0;
		if (finishState != null && finishState == 1) {
			minState = minState | StopRegisterRecord.FINISHED;
		}

		if (handleState != null && handleState == 1) {
			minState = minState | StopRegisterRecord.HANDLED;
		}

		if (pushState != null && pushState == 1) {
			minState = minState | StopRegisterRecord.PUSHED;
		}

		if (ackRefundState != null && ackRefundState == 1) {
			minState = minState | StopRegisterRecord.ACKED_REFUND;
		}

		if (refundState != null && refundState == 1) {
			minState = minState | StopRegisterRecord.REFUNDED;
		}

		if (hisRefundState != null && hisRefundState == 1) {
			minState = minState | StopRegisterRecord.HIS_REFUNDED;
		}

		return minState;
	}

	private int getMaxState(Byte finishState, Byte handleState, Byte pushState, Byte ackRefundState, Byte refundState, Byte hisRefundState) {
		int maxState = 0;
		if (finishState == null || finishState == 1) {
			maxState = maxState | StopRegisterRecord.FINISHED;
		}

		if (handleState == null || handleState == 1) {
			maxState = maxState | StopRegisterRecord.HANDLED;
		}

		if (pushState == null || pushState == 1) {
			maxState = maxState | StopRegisterRecord.PUSHED;
		}

		if (ackRefundState == null || ackRefundState == 1) {
			maxState = maxState | StopRegisterRecord.ACKED_REFUND;
		}

		if (refundState == null || refundState == 1) {
			maxState = maxState | StopRegisterRecord.REFUNDED;
		}

		if (hisRefundState == null || hisRefundState == 1) {
			maxState = maxState | StopRegisterRecord.HIS_REFUNDED;
		}

		return maxState;
	}

	private void setStateMatcher(Map<String, Object> params, Byte finishState, Byte handleState, Byte pushState, Byte ackRefundState,
			Byte refundState, Byte hisRefundState) {
		List<Byte> oneStates = new ArrayList<>();
		List<Byte> zeroStates = new ArrayList<>();
		if (finishState != null) {
			if (finishState == 1) {
				oneStates.add(StopRegisterRecord.FINISHED);
			} else {
				zeroStates.add(StopRegisterRecord.FINISHED);
			}
		}

		if (handleState != null) {
			if (handleState == 1) {
				oneStates.add(StopRegisterRecord.HANDLED);
			} else {
				zeroStates.add(StopRegisterRecord.HANDLED);
			}
		}

		if (pushState != null) {
			if (pushState == 1) {
				oneStates.add(StopRegisterRecord.PUSHED);
			} else {
				zeroStates.add(StopRegisterRecord.PUSHED);
			}
		}

		if (ackRefundState != null) {
			if (ackRefundState == 1) {
				oneStates.add(StopRegisterRecord.ACKED_REFUND);
			} else {
				zeroStates.add(StopRegisterRecord.ACKED_REFUND);
			}
		}

		if (refundState != null) {
			if (refundState == 1) {
				oneStates.add(StopRegisterRecord.REFUNDED);
			} else {
				zeroStates.add(StopRegisterRecord.REFUNDED);
			}
		}

		if (hisRefundState != null) {
			if (hisRefundState == 1) {
				oneStates.add(StopRegisterRecord.HIS_REFUNDED);
			} else {
				zeroStates.add(StopRegisterRecord.HIS_REFUNDED);
			}
		}

		if (oneStates.size() > 0) {
			int oneState = 0;
			for (Byte temp : oneStates) {
				oneState = oneState | temp;
			}
			params.put("oneState", oneState);
		}

		if (zeroStates.size() > 0) {
			int zeroState = 0;
			for (Byte temp : zeroStates) {
				zeroState = zeroState | temp;
			}
			params.put("zeroState", zeroState);
		}
	}

	@Override
	public void deleteByLaunchTime(String min, String max) {
		dao.deleteByLaunchTime(min, max);
	}
}
