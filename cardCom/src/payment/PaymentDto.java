package payment;

import java.io.Serializable;

/**
 * <pre>
 * 결제 정보 DTO
 *</pre>
 */
public class PaymentDto implements Serializable {
	
	private static final long serialVersionUID = -934281496698712340L;

	/** 카드번호 숫자: 10~16 */
	private String cardNo;
	
	/** 할부개붤수 0(일시불, 1~12 */
	private String monthlyInstallmentPlan;
	
	/** 유효기간 - mmyy : 4자리 숫자 */
	private String validDate;
	
	/** cvc : 3 숫자 */
	private String cvc;
	
	/** 금액 :  */
	private String amt;
	
	/** 부가가치세 :  */
	private String VAT;
	
	/** 관리번호 : 20 */
	private String mngNo;
	
	/** 원 결재 관리번호 : 20 */
	private String befMngNo;
	
	/** 데이타 구분 : 10 
	 *  기능 구분값, 승인(PAYMENT), 취소(CANCEL) */
	private String dataType;
	
	/** 암호화된 카드정보 :300 */
	private String encCardInfo;
	
	/** 자동계산 부가가치세 */
	private String autoVAT;
	
	/** 암호화 할 카드 정보 */
	public String toCardString() {
		StringBuilder bulider = new StringBuilder();
		bulider.append(cardNo);
		bulider.append("|");
		bulider.append(validDate);
		bulider.append("|");
		bulider.append(cvc);
		return bulider.toString();
	}
	

	/**
	 * @return the cardNo
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * @param cardNo the cardNo to set
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * @return the monthlyInstallmentPlan
	 */
	public String getMonthlyInstallmentPlan() {
		return monthlyInstallmentPlan;
	}

	/**
	 * @param monthlyInstallmentPlan the monthlyInstallmentPlan to set
	 */
	public void setMonthlyInstallmentPlan(String monthlyInstallmentPlan) {
		this.monthlyInstallmentPlan = monthlyInstallmentPlan;
	}

	/**
	 * @return the validDate
	 */
	public String getValidDate() {
		return validDate;
	}

	/**
	 * @param validDate the validDate to set
	 */
	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	/**
	 * @return the cvc
	 */
	public String getCvc() {
		return cvc;
	}

	/**
	 * @param cvc the cvc to set
	 */
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	/**
	 * @return the amt
	 */
	public String getAmt() {
		return amt;
	}

	/**
	 * @param amt the amt to set
	 */
	public void setAmt(String amt) {
		this.amt = amt;
	}

	/**
	 * @return the vAT
	 */
	public String getVAT() {
		return VAT;
	}

	/**
	 * @param vAT the vAT to set
	 */
	public void setVAT(String vAT) {
		VAT = vAT;
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

	/**
	 * @return the befMngNo
	 */
	public String getBefMngNo() {
		return befMngNo;
	}

	/**
	 * @param befMngNo the befMngNo to set
	 */
	public void setBefMngNo(String befMngNo) {
		this.befMngNo = befMngNo;
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
	 * @return the encCardInfo
	 */
	public String getEncCardInfo() {
		return encCardInfo;
	}

	/**
	 * @param encCardInfo the encCardInfo to set
	 */
	public void setEncCardInfo(String encCardInfo) {
		this.encCardInfo = encCardInfo;
	}

	/**
	 * @return the autoVAT
	 */
	public String getAutoVAT() {
		return autoVAT;
	}

	/**
	 * @param autoVAT the autoVAT to set
	 */
	public void setAutoVAT(String autoVAT) {
		this.autoVAT = autoVAT;
	}

	
	
	
	

}
