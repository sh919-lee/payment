package payment.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CardUtils {
	
	/** 빈문자열 상수 <code>""</code>.	 */
	public static final String EMPTY = "";
	public static final String SPACE = " ";
	
	/**
	 * 주어진 원본 문자열의 길이가 size 보다 작은경우 부족한 길이만큼 문자열의 원쪽에 공백 채움
	 * @param str 원본 문자열
	 * @param size 채울 문자열이 길이
	 * @return 공백으로 채운 문자열, 원본문자열이 size 보다 크거나 음수이면 그대로 반환
	 */
	public static String lpad(String str, int size) {
		return lpad(str, size, SPACE);
	}
	
	/**
	 * 주어진 원본 문자열의 길이가 size 보다 작은경우 부족한 길이만큼 문자열의 원쪽에 특정문자 채움
	 * @param str 원본 문자열
	 * @param size 채울 문자열이 길이
	 * @param padStr 채울때 쓰일 문자
	 * @return 특정문자로 채운 문자열, 원본문자열이 size 보다 크거나 음수이면 그대로 반환
	 */
	public static String lpad(String str, int size, String padStr ) {
		if (null == str) {
			return null;
		}
		
		if (null == padStr || 0 == padStr.length()){
			padStr = SPACE;
		}
		
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		
		if (pads == padLen) {
			return padStr.concat(str);
		}else if (pads < padLen) {
			return padStr.substring(0, pads).concat(str);
		}else {
			return padding(pads, padStr).concat(str);
		}
	}
	
	/**
	 * 트정문자열을 일정 길이만큼 반복하여 채운다.
	 * 
	 * @param size     반복하여 채울 무자열길이
	 * @param padChar  채울때 쓰일 문자열
	 * @return         주어진 길이만큼 채운 문자열
	 */
	public static String padding(int size, String padChar) {
		if (null == padChar ||  0 == padChar.length()){
			return EMPTY;
		}
		
		char[] padding = new char[size];
		char[] padChars = padChar.toCharArray();
		
		for (int i=0; i<size; i++) {
			padding[i] = padChars[i % padChars.length];
		}
		return new String(padding);
	}
	
	/**
	 * 주어진 원본 문자열의 길이가 size 보다 작은경우 부족한 길이만큼 문자열의 오른쪽에 공백 채움
	 * @param str 원본 문자열
	 * @param size 채울 문자열이 길이
	 * @return 공백으로 채운 문자열, 원본문자열이 size 보다 크거나 음수이면 그대로 반환
	 */
	public static String rpad(String str, int size) {
		return rpad(str, size, SPACE);
	}
	
	/**
	 * 주어진 원본 문자열의 길이가 size 보다 작은경우 부족한 길이만큼 문자열의 오른쪽에 특정문자 채움
	 * @param str 원본 문자열
	 * @param size 채울 문자열이 길이
	 * @param padStr 채울때 쓰일 문자
	 * @return 특정문자로 채운 문자열, 원본문자열이 size 보다 크거나 음수이면 그대로 반환
	 */
	public static String rpad(String str, int size, String padStr ) {
		if (null == str) {
			return null;
		}
		
		if (null == padStr || 0 == padStr.length()){
			padStr = SPACE;
		}
		
		int padLen = padStr.length();
		int strLen = str.length();
		int pads = size - strLen;
		if (pads <= 0) {
			return str;
		}
		
		if (pads == padLen) {
			return str.concat(padStr);
		}else if (pads < padLen) {
			return str.concat(padStr).substring(0, pads);
		}else {
			return str.concat(padding(pads, padStr)); 
		}
	}
	
	/**
	 * 주어진 문자열이 숫자만을 포함하고 있는지 확인.
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(String str) {
		if (null == str) {
			return false;
		}
		
		int sz = str.length();
		for (int i=0; i<sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
			
	}
	/**
	 * 문자열이 공백, empty "" or null 인지 확인
	 * @param str
	 * @return 만약 주어진 문자열이 공백, empty "" or null 이면 true 반환
	 */
	public static boolean isBlank(String str) {
		int strLen;
		if (null == str || (0 == (strLen = str.length()))){
			return true;
		}
		for (int i=0; i<strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
	/**
	 * null 이면 "" 반환
	 * @param str
	 * @return
	 */
	public static String nvl(String str) {
		return (null == str ? EMPTY : str);
	}
	/**
	 * null 이면 지정된 문자 반환
	 * @param str
	 * @return
	 */
	public static String nvl(String str, String defStr) {
		return (null == str ? defStr : str);
	}
	/**
	 * 현재일자
	 * @return
	 */
	public static String getCurrentDateTime() {
		Calendar calender = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(calender.getTime());
	}
	

}
