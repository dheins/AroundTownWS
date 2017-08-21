package utils;

import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Properties;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Crypto {
	private static KeyStore ks;
	private static Properties prop;
	// private final static Logger log = Logger.getLogger(WSImpl.class);

	private Crypto() {
	};

	private static void init() {

		prop = new Properties();
		try {
			prop.load(Crypto.class.getResourceAsStream("/etc/serviceKeystore.properties"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String path = prop.getProperty("org.apache.ws.security.crypto.merlin.keystore.file");
		String type = prop.getProperty("org.apache.ws.security.crypto.merlin.keystore.type");
		String pass = prop.getProperty("org.apache.ws.security.crypto.merlin.keystore.password");

		try {

			ks = KeyStore.getInstance(type);
			ks.load(Crypto.class.getResourceAsStream("/" + path), pass.toCharArray());

		} catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static byte[] encrypt(String s) {
		if (ks == null)
			init();
		Certificate cer;
		Cipher cipher;
		try {
			String alias = prop.getProperty("org.apache.ws.security.crypto.merlin.cert.alias");
			cer = ks.getCertificate(alias);
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, cer.getPublicKey());

			byte[] encrypted = cipher.doFinal(s.getBytes("UTF8"));
			byte[] base64 = org.apache.commons.codec.binary.Base64.encodeBase64(encrypted);

			return base64;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			cer = null;
			cipher = null;

		}
		return null;

	}

	public static String decrypt(byte[] b) {
		if (ks == null)
			init();
		PrivateKey priv;
		Cipher cipher;
		try {
			String alias = prop.getProperty("org.apache.ws.security.crypto.merlin.keystore.alias");

			priv = (PrivateKey) ks.getKey(alias, "skpass".toCharArray());
			cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, priv);

			byte[] decoded = org.apache.commons.codec.binary.Base64.decodeBase64(b);
			byte[] decrypted = cipher.doFinal(decoded);

			return new String(decrypted, "UTF8");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			priv = null;
			cipher = null;
		}
		return null;
	}

	public static SecretKey generateKey() {

		KeyGenerator keyGen;
		try {
			keyGen = KeyGenerator.getInstance("HmacSHA256");
			keyGen.init(256);
			SecretKey secretKey = keyGen.generateKey();
			return secretKey;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
