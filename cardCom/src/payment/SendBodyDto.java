package payment;

import java.io.Serializable;

/**
 * <pre>
 * 카드사 바디 DTO
 *</pre>
 */
public class SendBodyDto implements Serializable {
	
	private static final long serialVersionUID = 2482423160637250043L;

	/** 카드번호 숫자: 10~16 */
	private String cardNo;
	
	/** 할부개붤수 0(일시불, 1~12 */
	private String monthlyInstallmentPlan;
	
	/** 유효기간 - mmyy : 4자리 숫자 */
	private String validDate;
	
	/** cvc : 3 숫자 */
	private String cvc;
	
	/** 금액 :  10*/
	private String amt;
	
	/** 부가가치세 : 10 */
	private String VAT;
	
	/** 원 결재 관리번호 : 20 */
	private String befMngNo;
	
	/** 암호화된 카드정보 :300 */
	private String encCardInfo;
	
	/** 예비필드 :47 */
	private String etc1;
	
	@Override
	public String toString() {
		StringBuilder bulider = new StringBuilder();
		bulider.append(cardNo);
		bulider.append(monthlyInstallmentPlan);
		bulider.append(validDate);
		bulider.append(cvc);
		bulider.append(amt);
		bulider.append(VAT);
		bulider.append(befMngNo);
		bulider.append(encCardInfo);
		bulider.append(etc1);
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
	 * @return the etc1
	 */
	public String getEtc1() {
		return etc1;
	}

	/**
	 * @param etc1 the etc1 to set
	 */
	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	
	
	

}
