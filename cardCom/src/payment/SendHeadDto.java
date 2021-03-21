package payment;

import java.io.Serializable;

/**
 * <pre>
 * 카드사 전송 해더 DTO
 *</pre>
 */
public class SendHeadDto implements Serializable {
	
	
	private static final long serialVersionUID = -6689296015960574242L;

	/** 데이타 길이 :  4 */
	private String headLength;
	
	/** 데이타 구분 : 10 
	 *  기능 구분값, 승인(PAYMENT), 취소(CANCEL) */
	private String dataType;
	
	/** 관리번호 : 20 */
	private String mngNo;
	
	@Override
	public String toString() {
		
		StringBuilder bulider = new StringBuilder();
		bulider.append(headLength);
		bulider.append(dataType);
		bulider.append(mngNo);
		return bulider.toString();
	}

	/**
	 * @return the headLength
	 */
	public String getHeadLength() {
		return headLength;
	}

	/**
	 * @param headLength the headLength to set
	 */
	public void setHeadLength(String headLength) {
		this.headLength = headLength;
	}

	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

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

	
	

}
