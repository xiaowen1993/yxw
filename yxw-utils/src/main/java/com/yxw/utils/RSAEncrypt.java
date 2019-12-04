package com.yxw.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * Supplies RSA arithmetic operation. Such as encrypt, decrypt, sign, verify and so on...
 * 
 * @author Mingz Fan
 * @version 1.0
 */
public class RSAEncrypt {

	public static final String ALGORITHM_NAME = "RSA";

	public static final String SIGN_ALGORITHM = "SHA1WithRSA";

	public static final int ENCRYPT_MODE = Cipher.ENCRYPT_MODE;

	public static final int DECRYPT_MODE = Cipher.DECRYPT_MODE;

	public static final int PUBLIC_KEY = Cipher.PUBLIC_KEY;

	public static final int PRIVATE_KEY = Cipher.PRIVATE_KEY;

	private static final int KEY_COUNT = 2;

	private static final int KEY_SIZE = 1024;

	private static final int MAX_ENCRYPT_BLOCK = 117;

	private static final int MAX_DECRYPT_BLOCK = 128;

	private Map<Integer, Key> keyMap;

	private Map<Integer, String> keySpecMap;

	private static Signature SIGNATURE;

	/**
	 * Constructs a <tt>RSAEncrypt</tt> with generated public key and private key. <br/>
	 * 构造一个自动生成公钥及私钥的 <tt>RSAEncrypt</tt>
	 * 
	 * @throws NoSuchAlgorithmException
	 *             if no Provider supports a KeyFactorySpi implementation for the specified algorithm.
	 */
	public RSAEncrypt() throws NoSuchAlgorithmException {
		super();
		this.keyMap = generateKeys();
		// 得到公钥字符串
		String publicKeySpec = Base64.encodeBase64String(getPublicKey().getEncoded());
		// 得到私钥字符串
		String privateKeySpec = Base64.encodeBase64String(getPrivateKey().getEncoded());

		this.keySpecMap = new HashMap<Integer, String>(KEY_COUNT);
		this.keySpecMap.put(PUBLIC_KEY, publicKeySpec);
		this.keySpecMap.put(PRIVATE_KEY, privateKeySpec);
	}

	/**
	 * Constructs a <tt>RSAEncrypt</tt> with the specified public key and private key. <br/>
	 * 构造一个含有指定公钥及私钥的 <tt>RSAEncrypt</tt>
	 * 
	 * @param publicKeySpec
	 *            the public key, which is assumed to be encoded according to the X.509 standard, followed by Base64
	 *            encoding.
	 * @param privateKeySpec
	 *            the private key, which is assumed to be encoded according to the PKCS #8 standard, followed by Base64
	 *            encoding.
	 * @throws NoSuchAlgorithmException
	 *             if no Provider supports a KeyFactorySpi implementation for the specified algorithm.
	 * @throws InvalidKeySpecException
	 *             if the specified public (or private) key is not encoded according to the X.509 (or PKCS #8) standard.
	 */
	public RSAEncrypt(String publicKeySpec, String privateKeySpec) throws NoSuchAlgorithmException, InvalidKeySpecException {
		super();
		update(publicKeySpec, privateKeySpec);
	}

	/**
	 * Updates the public key and private key, using the specified public key and private key. <br/>
	 * 更新指定的公钥及私钥
	 * 
	 * @param publicKeySpec
	 *            the public key, which is assumed to be encoded according to the X.509 standard, followed by Base64
	 *            encoding.
	 * @param privateKeySpec
	 *            the private key, which is assumed to be encoded according to the PKCS #8 standard, followed by Base64
	 *            encoding.
	 * @throws NoSuchAlgorithmException
	 *             if no Provider supports a KeyFactorySpi implementation for the specified algorithm.
	 * @throws InvalidKeySpecException
	 *             if the specified public (or private) key is not encoded according to the X.509 (or PKCS #8) standard.
	 */
	public void update(String publicKeySpec, String privateKeySpec) throws NoSuchAlgorithmException, InvalidKeySpecException {
		if (this.keyMap == null) {
			this.keyMap = new HashMap<Integer, Key>(KEY_COUNT);
			this.keySpecMap = new HashMap<Integer, String>(KEY_COUNT);
		}

		try {
			if (privateKeySpec != null) {
				this.keyMap.put(PRIVATE_KEY, generateKey(PRIVATE_KEY, privateKeySpec.getBytes()));
				this.keySpecMap.put(PRIVATE_KEY, privateKeySpec);
			}

			if (publicKeySpec != null) {
				this.keyMap.put(PUBLIC_KEY, generateKey(PUBLIC_KEY, publicKeySpec.getBytes()));
				this.keySpecMap.put(PUBLIC_KEY, publicKeySpec);

			}

		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Returns a reference to the public key component of this <tt>REAEncrypt</tt>. <br/>
	 * 返回此 <tt>REAEncrypt</tt> 实例的公钥组件的引用
	 * 
	 * @return a reference to the public key
	 */
	public Key getPublicKey() {
		return keyMap.get(PUBLIC_KEY);
	}

	/**
	 * Returns a reference to the private key component of this <tt>REAEncrypt</tt>. <br/>
	 * 返回此 <tt>REAEncrypt</tt> 实例的私钥组件的引用
	 * 
	 * @return a reference to the private key
	 */
	public Key getPrivateKey() {
		return keyMap.get(PRIVATE_KEY);
	}

	/**
	 * Returns a reference to the string-formatted public key of this <tt>RSAEncrypt</tt>. <br/>
	 * 返回此 <tt>RSAEncrypt</tt> 实例的公钥字符串的引用
	 * 
	 * @return a reference to the string-formatted public key
	 */
	public String getPublicKeySpec() {
		return keySpecMap.get(PUBLIC_KEY);
	}

	/**
	 * Returns a reference to the string-formatted private key of this <tt>RSAEncrypt</tt>. <br/>
	 * 返回此 <tt>RSAEncrypt</tt> 实例的私钥字符串的引用
	 * 
	 * @return
	 */
	public String getPrivateKeySpec() {
		return keySpecMap.get(PRIVATE_KEY);
	}

	protected static Map<Integer, Key> generateKeys() throws NoSuchAlgorithmException {
		Map<Integer, Key> keyMap = new HashMap<Integer, Key>(KEY_COUNT);

		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_NAME);
		keyPairGen.initialize(KEY_SIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		keyMap.put(PUBLIC_KEY, keyPair.getPublic());
		keyMap.put(PRIVATE_KEY, keyPair.getPrivate());

		return keyMap;
	}

	public String encryptHexByPublicKey(String content, boolean toLowerCase) throws NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		return encryptHexByPublicKey(content.getBytes(), toLowerCase);
	}

	public String encryptHexByPublicKey(byte[] data, boolean toLowerCase) throws NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			return encryptHex(PUBLIC_KEY, data, toLowerCase);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public String encryptHexByPrivateKey(String content, boolean toLowerCase) throws NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		return encryptHexByPrivateKey(content.getBytes(), toLowerCase);
	}

	public String encryptHexByPrivateKey(byte[] data, boolean toLowerCase) throws NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			return encryptHex(PRIVATE_KEY, data, toLowerCase);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public String encryptHex(int keyType, String content, boolean toLowerCase) throws InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		return encryptHex(keyType, content.getBytes(), toLowerCase);
	}

	public String encryptHex(int keyType, byte[] data, boolean toLowerCase) throws InvalidKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException {
		return new String(Hex.encodeHex(encrypt(keyType, data), toLowerCase));
	}

	public byte[] encryptByPublicKey(String content) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return encryptByPublicKey(content.getBytes());
	}

	public byte[] encryptByPublicKey(byte[] data) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
			return encrypt(PUBLIC_KEY, data);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public byte[] encryptByPrivateKey(String content) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return encryptByPrivateKey(content.getBytes());
	}

	public byte[] encryptByPrivateKey(byte[] data) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
			return encrypt(PRIVATE_KEY, data);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public byte[] encrypt(int keyType, String content) throws InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		return encrypt(keyType, content.getBytes());
	}

	public byte[] encrypt(int keyType, byte[] data) throws InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			return digest(ENCRYPT_MODE, keyMap.get(keyType), data);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public String decryptHexByPublicKey(String hexContent) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decryptHexByPublicKey(hexContent.getBytes());
	}

	public String decryptHexByPublicKey(byte[] hexData) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
			return decryptHex(PUBLIC_KEY, hexData);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public String decryptHexByPrivateKey(String hexContent) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decryptHexByPrivateKey(hexContent.getBytes());
	}

	public String decryptHexByPrivateKey(byte[] hexData) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
			return decryptHex(PRIVATE_KEY, hexData);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public String decryptHex(int keyType, String hexContent) throws InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		return decryptHex(keyType, hexContent.getBytes());
	}

	public String decryptHex(int keyType, byte[] hexData) throws InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			return new String(decrypt(keyType, Hex.decodeHex(new String(hexData).toCharArray())));
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public byte[] decryptByPublicKey(String content) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decryptByPublicKey(content.getBytes());
	}

	public byte[] decryptByPublicKey(byte[] data) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
			return decrypt(PUBLIC_KEY, data);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public byte[] decryptByPrivateKey(String content) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return decryptByPrivateKey(content.getBytes());
	}

	public byte[] decryptByPrivateKey(byte[] data) throws NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		try {
			return decrypt(PRIVATE_KEY, data);
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public byte[] decrypt(int keyType, String content) throws InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		return decrypt(keyType, content.getBytes());
	}

	public byte[] decrypt(int keyType, byte[] data) throws InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		try {
			return digest(DECRYPT_MODE, keyMap.get(keyType), data);
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}

	public String sign(String content) throws SignatureException {
		return new String(sign(content.getBytes()));
	}

	public byte[] sign(byte[] data) throws SignatureException {
		try {
			return sign(data, (PrivateKey) getPrivateKey());
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (InvalidKeyException e) {
			return null;
		}
	}

	public boolean verify(String content, String sign) throws SignatureException {
		return verify(content.getBytes(), sign.getBytes());
	}

	public boolean verify(byte[] data, byte[] sign) throws SignatureException {
		try {
			return verify(data, (PublicKey) getPublicKey(), sign);
		} catch (NoSuchAlgorithmException e) {
			return false;
		} catch (InvalidKeyException e) {
			return false;
		}
	}

	public static String encryptHexByPublicKey(String publicKey, String content, boolean toLowerCase) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encryptHexByPublicKey(publicKey, content, toLowerCase);
	}

	public static byte[] encryptHexByPublicKey(byte[] publicKey, byte[] data, boolean toLowerCase) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encryptHex(PUBLIC_KEY, publicKey, data, toLowerCase);
	}

	public static String encryptHexByPrivateKey(String privateKey, String content, boolean toLowerCase) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encryptHex(PRIVATE_KEY, privateKey, content, toLowerCase);
	}

	public static byte[] encryptHexByPrivateKey(byte[] privateKey, byte[] data, boolean toLowerCase) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encryptHex(PRIVATE_KEY, privateKey, data, toLowerCase);
	}

	public static String encryptHex(int keyType, String key, String content, boolean toLowerCase) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(Hex.encodeHex(encrypt(keyType, key, content).getBytes(), toLowerCase));
	}

	public static byte[] encryptHex(int keyType, byte[] key, byte[] data, boolean toLowerCase) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encryptHex(keyType, new String(key), new String(data), toLowerCase).getBytes();
	}

	public static String encryptByPublicKey(String publicKey, String content) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encrypt(PUBLIC_KEY, publicKey, content);
	}

	public static byte[] encryptByPublicKey(byte[] publicKey, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encrypt(PUBLIC_KEY, publicKey, data);
	}

	public static String encryptByPrivateKey(String privateKey, String content) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encrypt(PRIVATE_KEY, privateKey, content);
	}

	public static byte[] encryptByPrivateKey(byte[] privateKey, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return encrypt(PRIVATE_KEY, privateKey, data);
	}

	public static String encrypt(int keyType, String key, String content) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(encrypt(keyType, key.getBytes(), content.getBytes()));
	}

	public static byte[] encrypt(int keyType, byte[] key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return digest(ENCRYPT_MODE, keyType, key, data);
	}

	public static String decryptHexByPublicKey(String publicKey, String hexContent) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decryptHex(PUBLIC_KEY, publicKey, hexContent);
	}

	public static byte[] decryptHexByPublicKey(byte[] publicKey, byte[] hexData) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decryptHex(PUBLIC_KEY, publicKey, hexData);
	}

	public static String decryptHexByPrivateKey(String privateKey, String hexContent) throws NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decryptHex(PRIVATE_KEY, privateKey, hexContent);
	}

	public static byte[] decryptHexByPrivateKey(byte[] privateKey, byte[] hexData) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decryptHex(PRIVATE_KEY, privateKey, hexData);
	}

	public static String decryptHex(int keyType, String key, String hexContent) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(decryptHex(keyType, key.getBytes(), hexContent.getBytes()));
	}

	public static byte[] decryptHex(int keyType, byte[] key, byte[] hexData) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		String string = new String(hexData);
		try {
			return decrypt(keyType, key, Hex.decodeHex(string.toCharArray()));
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static String decryptByPublicKey(String publicKey, String content) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decrypt(PUBLIC_KEY, publicKey, content);
	}

	public static byte[] decryptByPublicKey(byte[] publicKey, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decrypt(PUBLIC_KEY, publicKey, data);
	}

	public static String decryptByPrivateKey(String privateKey, String content) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decrypt(PRIVATE_KEY, privateKey, content);
	}

	public static byte[] decryptByPrivateKey(byte[] privateKey, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return decrypt(PRIVATE_KEY, privateKey, data);
	}

	public static String decrypt(int keyType, String key, String content) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return new String(decrypt(keyType, key.getBytes(), content.getBytes()));
	}

	public static byte[] decrypt(int keyType, byte[] key, byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		return digest(DECRYPT_MODE, keyType, key, data);
	}

	public String decryptBase64(String encryptKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
		

		byte[] source =  Base64.decodeBase64(encryptKey);
		
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE,  getPrivateKey());
	
		
		byte[] bytes = cipher.doFinal(source);

		return new String(bytes,"utf-8");

}
	
	protected static byte[] digest(int opmode, int keyType, byte[] key, byte[] data) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException {
		Key pk = generateKey(keyType, key);
		return digest(opmode, pk, data);
	}

	protected static Key generateKey(int keyType, byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
		KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_NAME);

		byte[] buffer = Base64.decodeBase64(key);
		Key pk = null;
		switch (keyType) {
		case PUBLIC_KEY:
			X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(buffer);
			pk = keyFactory.generatePublic(x509KeySpec);
			break;

		case PRIVATE_KEY:
			PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(buffer);
			pk = keyFactory.generatePrivate(pkcs8KeySpec);
			break;

		default:
			throw new InvalidKeyException("Unknown key type: " + keyType);
		}

		return pk;
	}

	protected static byte[] digest(int opmode, Key key, byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
		cipher.init(opmode, key);

		int leftLen = data.length;
		int blockSize;
		int transSize;
		switch (opmode) {
		case ENCRYPT_MODE:
			blockSize = MAX_ENCRYPT_BLOCK;
			transSize = MAX_DECRYPT_BLOCK;
			break;

		case DECRYPT_MODE:
			blockSize = MAX_DECRYPT_BLOCK;
			transSize = MAX_ENCRYPT_BLOCK;
			break;

		default:
			throw new InvalidKeyException("Unknown mode: " + opmode);
		}
		int bufLen = leftLen / blockSize;
		if (leftLen % blockSize != 0) {
			bufLen++;
		}
		bufLen *= transSize;
		byte[] buffer = (byte[]) Array.newInstance(byte.class, bufLen);

		// 分段加(解)密
		byte[] cache;
		bufLen = 0;
		while (leftLen > 0) {
			int digestLen = leftLen < blockSize ? leftLen : blockSize;
			cache = cipher.doFinal(data, data.length - leftLen, digestLen);
			System.arraycopy(cache, 0, buffer, bufLen, cache.length);

			bufLen += cache.length;
			leftLen -= blockSize;
		}

		return buffer.length == bufLen ? buffer : Arrays.copyOf(buffer, bufLen);
	}

	
	public static String sign(String content, String privateKeySpec) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, SignatureException {
		return new String(sign(content.getBytes(), privateKeySpec));
	}

	public static byte[] sign(byte[] data, String privateKeySpec) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, SignatureException {
		PrivateKey privateKey = (PrivateKey) generateKey(PRIVATE_KEY, privateKeySpec.getBytes());
		return sign(data, privateKey);
	}

	protected static byte[] sign(byte[] data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException,
			SignatureException {
		Signature signature = getSignature();
		signature.initSign(privateKey);
		signature.update(data);

		return Base64.encodeBase64(signature.sign());
	}

	public static boolean verify(String content, String publicKeySpec, String sign) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, SignatureException {
		return verify(content.getBytes(), publicKeySpec, sign.getBytes());
	}

	public static boolean verify(byte[] data, String publicKeySpec, byte[] sign) throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, SignatureException {
		PublicKey publicKey = (PublicKey) generateKey(PUBLIC_KEY, publicKeySpec.getBytes());
		return verify(data, publicKey, sign);
	}

	protected static boolean verify(byte[] data, PublicKey publicKey, byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException,
			SignatureException {
		Signature signature = getSignature();
		signature.initVerify(publicKey);
		signature.update(data);

		return signature.verify(Base64.decodeBase64(sign));
	}

	protected static Signature getSignature() throws NoSuchAlgorithmException {
		if (SIGNATURE == null) {
			SIGNATURE = Signature.getInstance(SIGN_ALGORITHM);
		}
		return SIGNATURE;
	}

	public static void main(String[] args) throws Exception {
		/*String publicKeySpec =
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCqjAR2C5fykCdxiQS7VFMFFEwDiiPH1HZmJg5rMMjpzV0eWDbDHdZ5FkKjuJjYSnSVY5p8u8LNC9KqJXxsvOTapwvs1jh41uF4lkPf33QFcUMyF9wea2TEv7YcUxBdOlFHFW6XYRwEgA14408sxU5Dmx0yaS2lpfQIjC0P9KETYwIDAQAB";
		String privateKeySpec =
				"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKqMBHYLl/KQJ3GJBLtUUwUUTAOKI8fUdmYmDmswyOnNXR5YNsMd1nkWQqO4mNhKdJVjmny7ws0L0qolfGy85NqnC+zWOHjW4XiWQ9/fdAVxQzIX3B5rZMS/thxTEF06UUcVbpdhHASADXjjTyzFTkObHTJpLaWl9AiMLQ/0oRNjAgMBAAECgYBYOHeyTIr8OZ99toARGyyaywYLKrEZlIjujC8XObQkZUwO/1jMk6m3F1G/eSTvdYxbHpvye/nnsX727s8vPIMw092t8/cVfOy/YoYuugpH+v+ORtsprmMaeqIgBpgR/qkIh1NDC3oxC1DAqDPZcizweDdVDL+LizsIF+OS2wIoEQJBANFSGV3OugPwSxDXgjdMnbvAghFteE1Pl/+45Gobe8SyM75INNMNFEnqHuEFaJzt8CRRhReuo8ChPllrRseduwkCQQDQlF1sCTpAZdom1IcoXI08xGH4gv0B30/KWkbhBPIVBg9jaj0zOnP+owfnJQQn2EKSBRiKQWiPC1f2F2+NKjoLAkEAkAOuVC5BKSiQXPwJKUbbVyvx0U/B718NsvFgpehW1VPN6eMABR0AkoIz0JnKCf5itHop2ctb+tJ1dUwrnHdXEQJAJdnWvkVKaedR2FwXDu8EvnNQ6B01NsLEow8Q78LK/5+y6TKdWo+P/zSsuXiRMX3gTslX87b894ByfPkxyCdSkwJAaOXuEcYeFHemN82k+yNmwEQ8lqt+jesPQ3SkxjeJKqN1bCEdjPv+YnxlRQPXDjconKLpQeQ/YWIrSVHFEttYjA==";
		RSAEncrypt rsaEncrypt = new RSAEncrypt(publicKeySpec, privateKeySpec);
		// RSAEncrypt rsaEncrypt = new RSAEncrypt();
		System.out.println(String.format("公钥\n%s\n", rsaEncrypt.getPublicKeySpec()));
		System.out.println(String.format("私钥\n%s\n", rsaEncrypt.getPrivateKeySpec()));

		String content = "“问：这是一行没有任何意义的文字，你看完了等于没看，不是吗？ 答：不是，我觉得这是一行非常有内涵的文字，只是没多少人能看出来。”";

		// String sign = rsaEncrypt.sign(content);
		// System.out.println(String.format("签名\n%s\n", sign));
		// if (rsaEncrypt.verify(content, sign)) {
		// System.out.println("校验成功");
		// } else {
		// System.out.println("校验失败");
		// }

		String ciphertext = rsaEncrypt.encryptHexByPrivateKey(content.getBytes(), false);
		// String plaintext = rsaEncrypt.decryptHexByPublicKey(ciphertext);
		System.out.println(String.format("原文: %s", content));
		System.out.println(String.format("密文: %s", ciphertext));
		// System.out.println(String.format("明文: %s", plaintext));

		byte[] encryptData = rsaEncrypt.encryptByPublicKey(content);
		byte[] decryptData = rsaEncrypt.decryptByPrivateKey(encryptData);
		System.out.println(String.format("原文: %s", content));
		System.out.println(String.format("密文: %s", new String(encryptData)));
		System.out.println(String.format("明文: %s", new String(decryptData)));*/

		String publicKeySpec =
				"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCv55auOSAL8MoGNCDLKzXAJa9oSB7/MKG/3+kl0P4Z67/2sx77ihRTcsDItwnHIjG/4I7ZAhrqyCYJ1AcWYFBy7dqgd39TBcHdxn7O3Qt7DYIaanqWqIBNvNj/m9/a1CH2IYeeeKmB9BtdeuPkUCsIIt3NAYZ2rYoRpCETmRvwtwIDAQAB";

		RSAEncrypt rsaEncrypt = new RSAEncrypt(publicKeySpec, null);

		String encryptData =
				"gL28SQ5Sc-IOGg5KHh1-YwNEWhbVpOUHR6nDcvvCvvO_HcLz9dVrIdSZ8kTWNBNW1PKRADEakGPMJNe9s3wm_8n2STco6RcsGnnsNqaceQ1xqxsFRfk2Qp7x06ad6hTkQ4TyzC2d5wuSUZBGqrE5DGFe5kgsaGP8eyOkwVe06pk&encryptData=m1VZa-DzoCiMTV8rsGtu-zd70aA4ELnYZrvsBvL3RIOc-p4SyIyOLyhkmVLgctcvp-km2gvIWvYwGHl9EcWlqtzmdq9NZI3jE7JQaMCW1cerKJDr3lFsD2yURIslztTSewEb_OKm2WFTgS1OSJrGewivc2q4sGvoNmlxdRvRrQbw-PBvZW0J8Ca09lnqfcPHafT7wrRBo-BPKI1FBH3kibOs9zjsnyEIRciJl3rjQX8";
		String decryptData = rsaEncrypt.decryptHexByPublicKey(encryptData);

		System.out.println(decryptData);
	}

}
