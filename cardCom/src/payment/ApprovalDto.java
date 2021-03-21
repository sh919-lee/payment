package payment;

import java.io.Serializable;

/**
 * <pre>
 * 카드 승인 DTO
 *</pre>
 */
public class ApprovalDto implements Serializable {
	
	private static final long serialVersionUID = 7951987633976332521L;

	/** 관리번호 : 20 */
	private String mngNo;
	
	/** 카드사에 전달한 String 데이타 : 450 
	 * 공통 헤더부분 + 데이타 부분 */
	private String sendMsg;
	

	/**
	 * @return the mngNo
	 */
	public String getMngNo() {
		return mngNo;
	}

	/**
	 * @param mngNo the mngNo to set
	 */
	public void setMngNo(String mngNo) {
		this.mngNo = mngNo;
	}

	/**
	 * @return the sendMsg
	 */
	public String getSendMsg() {
		return sendMsg;
	}

	/**
	 * @param sendMsg the sendMsg to set
	 */
	public void setSendMsg(String sendMsg) {
		this.sendMsg = sendMsg;
	}
	
	
	
	

}
