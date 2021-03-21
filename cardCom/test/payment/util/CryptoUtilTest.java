package payment.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class CryptoUtilTest {

	@Test
	public void testCryptoUtil() {
		try {
			String enStr = CryptoUtil.aes_encode("1234567890123456|1125|777");
			System.out.println("암호화=" + enStr + "|");
			
			String deStr = CryptoUtil.aes_decode(enStr);
			System.out.println("복호화=" + deStr + "|");
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

}
