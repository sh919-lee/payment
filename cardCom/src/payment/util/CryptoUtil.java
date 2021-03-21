package payment.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 암/복호화 유틸 
 */
public class CryptoUtil {
	
	public static final String key = "fakecodingsecret";
	public static final byte[] ivBytes = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	/**
	 * 암호화
	 * @param str
	 * @return 암호화 값
	 */
	public static String aes_encode(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{
		byte[] textBytes = str.getBytes();
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.ENCRYPT_MODE, newKey, ivSpec);
		return asHex(cipher.doFinal(textBytes));
	}
	/**
	 * 복호화
	 * @param str
	 * @return
	 */
	public static String aes_decode(String str) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException{

		AlgorithmParameterSpec ivSpec = new IvParameterSpec(ivBytes);
		SecretKeySpec newKey = new SecretKeySpec(key.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(Cipher.DECRYPT_MODE, newKey, ivSpec);
		
		return new String(cipher.doFinal(fromHex( str)));
	}
	
	/** hex 변환 */
	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;
		
		for (i=0; i<buf.length; i++) {
			if (((int)buf[i] & 0xff) < 0x10) {
				strbuf.append(0);
			}
			strbuf.append(Long.toString((int)buf[i] & 0xff, 16));
		}
		
		return strbuf.toString();
	}
	
	/** hex 변환 */
	public static byte[] fromHex(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		
		for (int i=0; i<len; i +=2) {
			data[i/2] = (byte)((Character.digit(s.charAt(i), 16) << 4) +(Character.digit(s.charAt(i+1), 16 ) ) );
		}
		
		return data;
	}
	
	

}
