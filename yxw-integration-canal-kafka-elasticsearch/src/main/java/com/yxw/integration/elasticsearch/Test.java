/**
 * Copyright© 2014-2018 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2018年3月16日
 * @version 1.0
 */
package com.yxw.integration.elasticsearch;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.yxw.integration.common.PropertiesUtil;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2018年3月16日  
 */
public class Test {

	public static void main(String[] args) {
		PropertiesUtil.getProperties();

		String text =
				"{\"id\":\"43cb86a4ef0c4ea7882fe0725bc52845\",\"user_id\":\"\",\"branch_id\":\"f84b7e903da14c4a84d4e0e6bf127725\",\"branch_code\":\"122\",\"hospital_id\":\"6d5c740eef52432fa6701191e9303fa3\",\"hospital_code\":\"szsdermyy\",\"platform\":2,\"name\":\"郑大钱\",\"sex\":1,\"age\":27,\"birth\":\"1990-12-29\",\"mobile\":\"15231624631\",\"id_type\":1,\"id_no\":\"460004199012290239\",\"address\":\"\",\"open_id\":\"20881045159084463609694201117675\",\"ownership\":1,\"card_type\":1,\"card_no\":\"3666733\",\"guard_name\":\"\",\"guard_id_type\":\"\",\"guard_id_no\":\"\",\"guard_mobile\":\"\",\"is_medicare\":\"\",\"medicare_no\":\"\",\"mark\":\"\",\"bind_way\":0,\"patient_id\":\"\",\"state\":1,\"create_time\":1521080253318,\"update_time\":1521080253318,\"hospital_name\":\"\",\"admission_no\":\"\",\"admission_id_no\":\"\",\"medicalcard_no\":\"\",\"computer_no\":\"\",\"medical_insurance_type\":\"\",\"id_term_of_validity_begin\":\"\",\"id_term_of_validity_end\":\"xx\"}";

		Map<String, Object> fieldMap = JSON.parseObject(text);

		ElasticsearchController.insertById("47.yx129.biz_medical_card", "biz_medical_card", "43cb86a4ef0c4ea7882fe0725bc52845", fieldMap);
	}

}
