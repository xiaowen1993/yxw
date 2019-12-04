package com.yxw.easyhealth.biz.datastorage.clinic.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.yxw.easyhealth.biz.datastorage.clinic.dao.DataMzFeeDetailDao;
import com.yxw.easyhealth.biz.datastorage.clinic.entity.DataMzFeeDetail;
import com.yxw.framework.exception.SystemException;
import com.yxw.framework.mvc.dao.impl.BaseDaoImpl;

/**
 * 门诊待缴费记录明细Dao实现
 * @Package: com.yxw.mobileapp.biz.datastorage.dao.impl
 * @ClassName: DataMZFeeDetailImpl
 * @Statement: <p></p>
 * @JDK version used: 1.6
 * @Author: dfw
 * @Create Date: 2015-7-17
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
@Repository
public class DataMzFeeDetailDaoImpl extends BaseDaoImpl<DataMzFeeDetail, String> implements DataMzFeeDetailDao {

	private Logger logger = LoggerFactory.getLogger(DataMzFeeDetailDaoImpl.class);

	private final String SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID_AND_ITEM_ID = "findByBranchHospitalCodeAndMzFeeIdAndItemId";

	@Override
	public List<DataMzFeeDetail> findByBranchHospitalCodeAndFeeIdAndItemId(DataMzFeeDetail dataMZFeeDetail) {
		List<DataMzFeeDetail> list = null;
		try {
			Assert.notNull(dataMZFeeDetail.getBranchHospitalCode());
			Assert.notNull(dataMZFeeDetail.getMzFeeId());
			// ItemId为空可能无法入库（接口必须给我保证ItemID不为空）
			Assert.notNull(dataMZFeeDetail.getItemId());

			Map<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("branchHospitalCode", dataMZFeeDetail.getBranchHospitalCode());
			paramsMap.put("mzFeeId", dataMZFeeDetail.getMzFeeId());
			paramsMap.put("itemId", dataMZFeeDetail.getItemId());

			list = sqlSession.selectList(getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID_AND_ITEM_ID), paramsMap);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(
					String.format("根据分院CODE和检查ID查询待缴费明细入库记录出错!语句：%s", getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID_AND_ITEM_ID)), e);
			throw new SystemException(String.format("根据分院CODE和检查ID查询待缴费明细入库记录出错!语句：%s",
					getSqlName(SQLNAME_FIND_BY_BRANCH_HOSPITAL_CODE_AND_MZFEE_ID_AND_ITEM_ID)), e);
		}
		return list;
	}

}
