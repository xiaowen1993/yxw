/**
 * <html>
 * <body>
 *  <P>  Copyright 2014-2015 www.yx129.com Group.</p>
 *  <p>  All rights reserved.</p>
 *  <p> Created on 2015年9月1日</p>
 *  <p> Created by 黄忠英</p>
 *  </body>
 * </html>
 */
package com.yxw.platform.privilege.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.util.Base64Utils;

/**
 * @Package: com.yxw.platform.privilege.utils
 * @ClassName: AESUtil
 * @Statement: <p></p>
 * @JDK version used: 
 * @Author: 黄忠英
 * @Create Date: 2015年9月1日
 * @modify By:
 * @modify Date:
 * @Why&What is modify:
 * @Version: 1.0
 */
public class AESUtil {
	private static final String key = "AqUcfgLTg7waWKaR";
	private static final String iv = "PdvUEY6xAba32NL8";

	private static final String algorithm = "AES";
	private static final String transformation = "AES/CBC/PKCS5Padding";

	public static String encrypt(String data) throws Exception {
		byte[] dataBytes = data.getBytes();

		byte[] encrypted = initCipher(Cipher.ENCRYPT_MODE).doFinal(dataBytes);

		return Base64Utils.encodeToString(encrypted);
	}

	public static String desEncrypt(String data) throws Exception {
		byte[] encrypted = Base64Utils.decodeFromString(data);

		byte[] original = initCipher(Cipher.DECRYPT_MODE).doFinal(encrypted);
		String originalString = new String(original);

		return originalString;
	}

	private static Cipher initCipher(int mode) throws Exception {
		SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), algorithm);
		IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(mode, keyspec, ivspec);

		return cipher;
	}

	public static void main(String[] args) throws Exception {
		String enStr = encrypt("yx129");
		System.out.println(enStr);

		String deStr = desEncrypt(enStr);
		System.out.println(deStr);
	}
}
