/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年9月11日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.privilege.utils;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

import com.yxw.framework.exception.SystemException;
import com.yxw.utils.MD5Utils;

/**
 * @Package: com.yxw.platform.privilege.utils
 * @ClassName: PasswdUtil
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月11日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class PasswdUtil {
	/**
	 * 原始密码、盐值生成 md5或sha值
	 * @param passwd
	 * @param salt
	 * @return
	 */
	public static String getPwd(String passwd, String salt) {
		try {
			return MD5Utils.getMd5String32(salt + passwd + salt);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new SystemException(e);
		}
		//return DigestUtils.sha256Hex(salt + passwd + salt);
	}

	/**
	 * 检查密码是否正确
	 * @param md			数据库中保存的密码值
	 * @param checkPasswd	前端输入的明文密码
	 * @param salt
	 * @return
	 */
	public static boolean checkPasswd(String md, String checkPasswd, String salt) {
		if (StringUtils.isBlank(md) || StringUtils.isBlank(checkPasswd)) {
			throw new SystemException("需要校验的密码不能为空");
		}

		String checkMd = getPwd(checkPasswd, salt);

		return StringUtils.equals(md, checkMd);
	}
}
