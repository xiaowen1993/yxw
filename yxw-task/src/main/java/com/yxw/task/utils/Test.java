/**
 * Copyright© 2014-2017 医享网, All Rights Reserved <br/>
 * 描述: TODO <br/>
 * @author Caiwq
 * @date 2017年7月25日
 * @version 1.0
 */
package com.yxw.task.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/** 
 * 描述: TODO<br>
 * @author Caiwq
 * @date 2017年7月25日  
 */
public class Test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		/*Set<String> s1 =
				Sets.newHashSet("ID", "USER_ID", "BRANCH_ID", "BRANCH_CODE", "HOSPITAL_ID", "HOSPITAL_CODE", "PLATFORM", "NAME", "SEX",
						"AGE", "BIRTH", "MOBILE", "ID_TYPE", "ID_NO", "ADDRESS", "OPEN_ID", "OWNERSHIP", "CARD_TYPE", "CARD_NO",
						"GUARD_NAME", "GUARD_ID_TYPE", "GUARD_ID_NO", "GUARD_MOBILE", "IS_MEDICARE", "MEDICARE_NO", "MARK", "BIND_WAY",
						"PATIENT_ID", "STATE", "CREATE_TIME", "UPDATE_TIME", "HOSPITAL_NAME", "ADMISSION_NO", "ADMISSION_ID_NO",
						"MEDICALCARD_NO", "COMPUTER_NO", "MEDICAL_INSURANCE_TYPE");
		Set<String> s2 =
				Sets.newHashSet("ID", "USER_ID", "BRANCH_ID", "BRANCH_NAME", "BRANCH_CODE", "HOSPITAL_ID", "HOSPITAL_NAME",
						"HOSPITAL_CODE", "PLATFORM_MODE", "NAME", "SEX", "AGE", "BIRTH", "MOBILE", "ID_TYPE", "ID_NO", "ADDRESS",
						"OPEN_ID", "OWNERSHIP", "CARD_TYPE", "CARD_NO", "ADMISSION_NO", "GUARD_NAME", "GUARD_ID_TYPE", "GUARD_ID_NO",
						"GUARD_MOBILE", "IS_MEDICARE", "MEDICARE_NO", "MARK", "BIND_WAY", "PATIENT_ID", "STATE", "CREATE_TIME",
						"UPDATE_TIME", "FAMILY_ID", "APP_ID", "APP_CODE", "AREA_CODE", "AREA_NAME");
		System.out.println(s1.size());
		System.out.println(s2.size());
		SetView<String> intersection = Sets.difference(s1, s2);

		for (String string : intersection) {
			System.out.println(string);
		}*/

		/*String a = "http://hw23.yx129.net/registration/index?appId=wx95a6e0981f0f096e&cityService=wechat";
		System.out.println(StringUtils.contains(a, "registration/index"));*/
		//		String a = "aHR0cCUzQSUyRiUyRmh3Mi55eDEyOS5uZXQlMkZyZWdpc3RyYXRpb24lMkZhdXRoJTNGYXBwSWQlM0R3eDRjMDFkNjY1Y2NjZTdkYTAlMjZvcGVuSWQlM0RvcFV0ZXVHRmNpZ1pibml6QzViaXg2aDNWOUZJJTI2aWRObyUzRDQ0MDgyMzE5OTIwNDA4NDMxNiUyNm5hbWUlM0QlRTYlOUUlOTclRTYlQjUlQjclRTYlQjYlOUI";
		//		System.out.println(URLDecoder.decode(new String(org.apache.commons.codec.binary.Base64.decodeBase64(a)), "UTF-8"));

		/*for (int i = 0; i < 100; i++) {
			System.out.println(i);
		}*/

		String a = "aHR0cCUzQSUyRiUyRmh3MTAueXgxMjkubmV0JTJGcmVnaXN0cmF0aW9uJTJGaW5kZXglM0ZhcHBJZCUzRHd4YjY5MDE4MTNkMDFiOTY0Yw";
		byte[] b = org.apache.commons.codec.binary.Base64.decodeBase64(a);
		System.out.println(URLDecoder.decode(new String(b), "UTF-8"));

		/*		String a = "http://hw10.yx129.net/registration/index?appId=wxb6901813d01b964c";
				String b = URLEncoder.encode(a, "UTF-8");
				String c = org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString(b.getBytes());
				System.out.println(c);*/
	}
}
