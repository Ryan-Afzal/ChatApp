package core.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public abstract class Encryption {
	
	public static String encrypt(String input, byte[] key) throws EncryptionException {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
			
	    	byte[] encrypted = cipher.doFinal((input).getBytes());
	    	
	    	return new String(encrypted);
	    	
		} catch (Exception e) {
			throw new EncryptionException(e.getMessage());
		}
	}
	
	public static String decrypt(String input, byte[] key) throws EncryptionException {
		try {
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
			
	    	byte[] decrypted = cipher.doFinal((input).getBytes());
	    	
	    	return new String(decrypted);
	    	
		} catch (Exception e) {
			throw new EncryptionException(e.getMessage());
		}
	}

}
