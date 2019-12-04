package com.yxw.easyhealth.biz.special.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
	private byte[] desKey = null;

	// private byte[] vi={ 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19 };

	public DES(String skey) {

		try {

			desKey = skey.substring(0, 8).getBytes("UTF-8");
		} catch (Exception ex) {

		}
	}

	// �������
	public String decode(String message) throws Exception {
		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(desKey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		// IvParameterSpec iv = new IvParameterSpec(vi);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] retByte = cipher.doFinal(bytesrc);
		return new String(retByte);
	}

	public String encode(String message) throws Exception {
		Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(desKey);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		// IvParameterSpec iv = new IvParameterSpec(vi);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		return toHexString(cipher.doFinal(message.getBytes("UTF-8")));
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}
		return digest;
	}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}
		return hexString.toString();
	}

}
