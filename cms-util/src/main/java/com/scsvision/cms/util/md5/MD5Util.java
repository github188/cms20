/**
 * 
 */
package com.scsvision.cms.util.md5;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author wangbinyu
 *
 */
public class MD5Util {
	public static String MD5(String plaintext) {
		MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.reset();
			m.update(plaintext.getBytes());
			byte[] digest = m.digest();
			BigInteger bigInt = new BigInteger(1, digest);
			String hashtext = bigInt.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return "";
		}
	}
}
