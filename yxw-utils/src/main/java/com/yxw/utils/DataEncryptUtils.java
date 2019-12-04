/**
 * 
 */
package com.yxw.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;

/**
 * @author Yuce
 * 信息的加密与解密工具类(3DES加密与解密)
 */
public class DataEncryptUtils {
	private static final Logger logger = Logger.getLogger(DataEncryptUtils.class);
	//定义加密算法，有DES、DESede(即3DES)、Blowfish
	private static final String Algorithm = "DESEDE";
	private static final String PASSWORD_CRYPT_KEY = "www.yx129.com";

	/**
	 * 3DES加密方法
	 * @param message 源数据
	 * @return String 加密后的16进制字符串
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static String encryptMode(String message) throws Exception {
		Cipher cipher = null;
		SecretKey secretKey = null;
		try {
			byte[] src = message.getBytes();
			secretKey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm); //生成密钥
			cipher = Cipher.getInstance(Algorithm); //实例化负责加密/解密的Cipher工具类
			cipher.init(Cipher.ENCRYPT_MODE, secretKey); //初始化为加密模式
			return parseByteYToHexStr(cipher.doFinal(src));
		} catch (Exception e) {
			logger.error("加密失败!");
			throw new Exception("加密失败!");
		}
	}

	/**
	 * 3DES解密函数
	 * @param message 密文的16进制字符串
	 * @return
	 */
	public static String decryptMode(String message) throws Exception {
		SecretKey secretKey = null;
		Cipher cipher = null;
		try {
			byte[] src = parseHexStrToByte(message);
			secretKey = new SecretKeySpec(build3DesKey(PASSWORD_CRYPT_KEY), Algorithm);
			cipher = Cipher.getInstance(Algorithm);
			cipher.init(Cipher.DECRYPT_MODE, secretKey); //初始化为解密模式
			return new String(cipher.doFinal(src));
		} catch (Exception e) {
			logger.error("解密失败!");
			e.printStackTrace();
			throw new Exception("解密失败!");
		}
	}

	/*
	 * 根据字符串生成密钥字节数组 
	 * @param keyStr 密钥字符串
	 * @return 
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] build3DesKey(String desKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		//声明一个24位的字节数组，默认里面都是0
		byte[] key = new byte[24];

		//对字符串进行MD5加密并将其转成字节数组
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		byte[] temp = messageDigest.digest(desKey.getBytes());

		/*
		 * 执行数组拷贝
		 * System.arraycopy(源数组，从源数组哪里开始拷贝，目标数组，拷贝多少位)
		 */
		if (key.length > temp.length) {
			//如果temp不够24位，则拷贝temp数组整个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			//如果temp大于24位，则拷贝temp数组24个长度的内容到key数组中
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/**将二进制转换成16进制 
	 * @param buf  
	 * @return  
	 */
	private static String parseByteYToHexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**将16进制转换为二进制 
	 * @param hexStr 
	 * @return 
	 */
	private static byte[] parseHexStrToByte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length() / 2];
		for (int i = 0; i < hexStr.length() / 2; i++) {
			int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
			result[i] = (byte) ( high * 16 + low );
		}
		return result;
	}

	public static void main(String[] args) {
		String message = "妇产科妇产科";
		try {
			String s = encryptMode(message);
			System.out.println("加密后的数据:" + s + "数据长度：" + s.length());
			String hexStr = parseByteYToHexStr(message.getBytes("UTF-8"));
			System.out.println("转16进制数据:" + hexStr + "  数据长度:" + hexStr.length());
			System.out.println("16进制转Strin数据:" + new String(parseHexStrToByte(hexStr), "UTF-8"));
			System.out.println("解密后的数据:" + decryptMode(s));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
