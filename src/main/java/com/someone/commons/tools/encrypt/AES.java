package com.someone.commons.tools.encrypt;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import com.someone.commons.tools.utils.HexUtil;
import com.someone.commons.tools.utils.StrUtil;

/**
 * @Description 
 * @author liuzhao
 * @date 2017年2月15日 下午5:34:43
 *
 */
public class AES {
	private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
	private static final String ALGORITHM = "AES";
	private static final String ENCODE = "utf-8";
	
	/**
	 * 使用AES对字符串加密
	 * 
	 * @param str
	 *            utf8编码的字符串
	 * @param key
	 *            密钥（16字节）
	 * @return 加密结果
	 * @throws Exception
	 */
	public static String encrypt(String str, String key) throws Exception {
		if (StrUtil.isBlank(str) || StrUtil.isBlank(key))
			return null;
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes(ENCODE), ALGORITHM));
		return HexUtil.byteToHexString(cipher.doFinal(str.getBytes(ENCODE)));
	}

	/**
	 * 使用AES对数据解密
	 * 
	 * @param str
	 *            字节码转换16进制后的字符串
	 * @param key
	 *            密钥（16字节）
	 * @return 解密结果
	 * @throws Exception
	 */
	public static String decrypt(String str, String key) throws Exception {
		if (StrUtil.isBlank(str) || StrUtil.isBlank(key))
			return null;
		byte[] bytes = HexUtil.HexStringToBytes(str);
		Cipher cipher = Cipher.getInstance(TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes(ENCODE), ALGORITHM));
		bytes = cipher.doFinal(bytes);
		return new String(bytes, ENCODE);
	}
	
	public static void main(String[] args) throws Exception {
		System.err.println(System.currentTimeMillis()/1000);
		String pass = "123456";
		String encryptKey = "0123456789012345";// 必须为16位
		String encryptVal = AES.encrypt(pass, encryptKey);
		System.err.println(pass+"加密值后为："+encryptVal);
		String decryptVal = AES.decrypt(encryptVal, encryptKey);
		System.err.println(encryptVal+"解密值后为："+decryptVal);
	}
}
