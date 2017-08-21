package utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.xml.security.utils.Base64;

public class HashPass {
	
	public static String hash(String pass, String salt) {
		byte[] digest = DigestUtils.getSha256Digest().digest((pass+salt).getBytes());
		for(int i=0; i< 10; i++) {
			digest = DigestUtils.getSha256Digest().digest(digest);
		}
		return Base64.encode(digest);
	}

}
