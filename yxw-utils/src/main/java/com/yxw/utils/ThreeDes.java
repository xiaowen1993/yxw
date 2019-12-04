package com.yxw.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class ThreeDes {

	//用于将返回字段的值进行3DES加密
	public static String getEncryptedValue(String value, String key) throws Exception {
		if (null == value || "".equals(value)) {
			return "";
		}
		byte[] valueByte = value.getBytes();
		byte[] sl = encrypt3DES(valueByte, hexToBytes(key));
		String result = Base64.encodeBase64String(sl);
		//        String result = BytesUtil.bytesToHex(sl);
		return result;
	}

	public static byte[] encrypt3DES(byte[] input, byte[] key) throws Exception {
		Cipher c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		c.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, "DESede"));
		return c.doFinal(input);
	}

	//用于将返回字段的值进行3DES解密
	public static String getDecryptedValue(String value, String key) throws Exception {
		if (null == value || "".equals(value)) {
			return "";
		}
		//         byte[] valueByte = BytesUtil.hexToBytes(value);
		byte[] valueByte = Base64.decodeBase64(value);
		byte[] sl = decrypt3DES(valueByte, hexToBytes(key));
		String result = new String(sl);
		return result;
	}

	public static byte[] decrypt3DES(byte[] input, byte[] key) throws Exception {
		Cipher c = Cipher.getInstance("DESede/ECB/PKCS5Padding");
		c.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "DESede"));
		return c.doFinal(input);
	}

	/**
	 * 将16进制的字符串转换成bytes
	 *
	 * @param hex
	 * @return 转化后的byte数组
	 */
	public static byte[] hexToBytes(String hex) {
		return hexToBytes(hex.toCharArray());
	}

	/**
	 * 将16进制的字符数组转换成byte数组
	 *
	 * @param hex
	 * @return 转换后的byte数组
	 */
	public static byte[] hexToBytes(char[] hex) {
		int length = hex.length / 2;
		byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127) {
				value -= 256;
			}
			raw[i] = (byte) value;
		}
		return raw;
	}

	/**
	 * 将byte数组转换成16进制字符串
	 *
	 * @param bytes
	 * @return 16进制字符串
	 */
	public static String bytesToHex(byte[] bytes) {
		String hexArray = "0123456789abcdef";
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		for (byte b : bytes) {
			int bi = b & 0xff;
			sb.append(hexArray.charAt(bi >> 4));
			sb.append(hexArray.charAt(bi & 0xf));
		}
		return sb.toString();
	}

	public static byte[] readAll(InputStream in) throws IOException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		for (int i = in.read(); i != -1; i = in.read()) {
			bout.write(i);
		}
		return bout.toByteArray();
	}

	public static void main(String[] args) throws Exception {
		String key = "54915889b3b50e9b6298df293198ecf854915889b3b50e9b";
		System.out.println(key);

		String s = "13760690645";
		System.out.println("加密前的字符串:" + s);
		String encoded = getEncryptedValue(s, key);
		System.out.println("加密后的字符串:" + encoded);

		String value = getDecryptedValue(encoded, key);
		System.out.println("解密后的字符串:" + value);
		
		//01：身份证，03：护照，04：回乡证，05：台胞证
		System.out.println("证件类型(certTp): " + getDecryptedValue("jrW4q8WmLSE=", key));
		System.out.println("证件号(certId): " + getDecryptedValue("cQhd8wIyaMrS26mWrlxF+iJm1Zr8q+/9", key));
		System.out.println("是否实名(isAuth): " + getDecryptedValue("ToBJ9nUivIA=", key));
		System.out.println("登录手机号(mobile): " + getDecryptedValue("ogVx6QqE5YIA/XippKJCYA==", key));
		System.out.println("用户姓名(realName): " + getDecryptedValue("dFNPYO7ub04LfhXlq/tdQg==", key));
		System.out.println("设备vid(openVid): " + getDecryptedValue("s5UCoO2hAaixNEmR5tVF7dpzSPUuLoAXkqedMVASpwUiOhGeaqnBJNZxvhhDP20g", key));
	}
}