package com.someone.commons.tools.encrypt;

import java.security.SecureRandom;
import javax.crypto.spec.DESKeySpec;

import com.someone.commons.tools.utils.HexUtil;
import com.someone.commons.tools.utils.StrUtil;

import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

/**
 * DES加密介绍 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 * 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 * 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现 。
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DES {
	private static final String ENCODE = "utf-8";
	private static final String ALGORITHM = "DES";

	// 测试
	public static void main(String args[]) {
		// 待加密内容
		String str = "123456";
		// 密码，长度要是8的倍数
		String password = "1111111|2222222|2222222|";

		String result = DES.encrypt(str, password);
		System.out.println("加密后：" + result);
		// 直接将如上内容解密
		try {
			System.out.println("解密后：" + DES.decrypt(result, password));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 加密字符串，返回值为字节转16进制字符串
	 * @param str	需加密字符串
	 * @param password	密钥，长度必须大于等于8位，多于8位和8位密码一样
	 * @return
	 */
	public static String encrypt(String str, String password) {
		if(StrUtil.isBlank(str) || StrUtil.isBlank(password)) {
			return null;
		}
		try {
			SecureRandom random = new SecureRandom();
			DESKeySpec desKey = new DESKeySpec(password.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
			SecretKey securekey = keyFactory.generateSecret(desKey);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
			// 现在，获取数据并加密
			// 正式执行加密操作
			return HexUtil.byteToHexString((cipher.doFinal(str.getBytes(ENCODE))));
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param str	需解密字符串
	 * @param password	密钥，长度必须大于等于8位，多于8位和8位密码一样
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String str, String password) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom random = new SecureRandom();
		// 创建一个DESKeySpec对象
		DESKeySpec desKey = new DESKeySpec(password.getBytes());
		// 创建一个密匙工厂
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		// 将DESKeySpec对象转换成SecretKey对象
		SecretKey securekey = keyFactory.generateSecret(desKey);
		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, random);
		// 真正开始解密操作
		return new String(cipher.doFinal(HexUtil.HexStringToBytes(str)), ENCODE);
	}
}