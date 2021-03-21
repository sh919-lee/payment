package payment.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import payment.ApprovalDto;
import payment.CardPaymentService;
import payment.PaymentDto;
import payment.SendBodyDto;
import payment.SendHeadDto;
import payment.util.CardUtils;
import payment.util.CryptoUtil;
import payment.util.LogicalException;

/**
 *<pre>
 * 카드결재 인터페이스 구현체
 *  DB 미연결
 *  쿼리 정보 : CardPayment.xml
 * </pre>
 */
public class CardPaymentServiceImpl implements CardPaymentService {
	
	final int INT_100 = 100;
	final double D10M = 1000000000;

	
	/**
	 * <pre>
	 * 결제 승인 요청
	 * </pre>
	 * @param PaymentDto
	 * @return ApprovalDtoDTO
	 * @throws Exception
	 */
	public ApprovalDto createPayment(PaymentDto reqDto) throws Exception{
		
		checkValue(reqDto);//정합성 체크
		
		//관리번호 채번
		String mngNo = generateNextVal();
		reqDto.setMngNo(mngNo);
		reqDto.setDataType("PAYMENT");
		reqDto.setEncCardInfo(CryptoUtil.aes_encode(reqDto.toCardString()));
		
		ApprovalDto ad = sendCardInfo(reqDto);
		return ad;
	}
	
	/**
	 * <pre>
	 * 결제 취소 요청
	 * </pre>
	 * @param PaymentDto
	 * @return ApprovalDtoDTO
	 * @throws Exception
	 */
	public ApprovalDto cancelPayment(PaymentDto cancelDto) throws Exception{
		
		if (null == cancelDto) {
			throw new LogicalException("입력 정보가 없습니다.");
		}
		
		if (CardUtils.isBlank(cancelDto.getMngNo())) {
			throw new LogicalException("관리번호가 없습니다.");
		}
		
		cancelDto.setBefMngNo(cancelDto.getMngNo()); //채번전에 원 관리번호 셋팅.
		
		PaymentDto payDto = this.getPaymentInfo(cancelDto); //원결제 조회
		cancelDto.setCardNo(payDto.getCardNo());
		cancelDto.setValidDate(payDto.getValidDate());
		cancelDto.setCvc(payDto.getCvc());
		cancelDto.setEncCardInfo(payDto.getEncCardInfo());
		
		System.out.println(cancelDto.getEncCardInfo());
		
		List<PaymentDto> cancelDtoList = cancelList(cancelDto);
		checkCal(cancelDto, payDto, cancelDtoList);
		
		//관리번호 채번
		String mngNo = generateNextVal();
		cancelDto.setMngNo(mngNo);
		cancelDto.setDataType("CANCEL");

		cancelDto.setMonthlyInstallmentPlan("00");
		
		ApprovalDto ad = sendCardInfo(cancelDto);
		return ad;
		
	}
	
	/**
	 * <pre>
	 * 결제 데이타 조회
	 * </pre>
	 * @param  PaymentDto
	 * @return PaymentDto
	 * @throws Exception
	 */
	public PaymentDto getPaymentInfo(PaymentDto reqDto) throws Exception{
		if (null == reqDto) {
			throw new LogicalException("입력 정보가 없습니다.");
		}
		if (CardUtils.isBlank(reqDto.getMngNo())) {
			throw new LogicalException("관리번호가 없습니다.");
		}
		
		//TODO 주석풀것
		//PaymentDto rDto = (PaymentDto)getSqlMapClientTemplate.queryForObject("CardPayment.selectCardInfo",reqDto.getMngNo()); 
		
		PaymentDto rDto = new PaymentDto();
		//디비 조회한 걸로 친다.
		{
			rDto.setMonthlyInstallmentPlan("0");
			rDto.setAmt("110000");
			rDto.setVAT("10000");
			rDto.setEncCardInfo("e9ae59b052accf40ad31eff813ea31c484f8b2dddbdb148e2e8ca6eec6c70052");
		}
		//암호화 정보 복호화
		
		PaymentDto decDto = deCarInfo(rDto.getEncCardInfo());
		rDto.setCardNo(decDto.getCardNo());
		rDto.setValidDate(decDto.getValidDate());
		rDto.setCvc(decDto.getCvc());
		
		return rDto;
	}
	
	
	//내부 사용 메소드
	/**
	 * 정합성 체크
	 * @param reqDto
	 * @throws Exception
	 */
	private void checkValue(PaymentDto reqDto)throws Exception{
		
		if (null == reqDto) {
			throw new LogicalException("입력 정보가 없습니다.");
		}
		if (CardUtils.isBlank(reqDto.getCardNo())) {
			throw new LogicalException("카드번호가 없습니다.");
		}
		if (!CardUtils.isNumeric(reqDto.getCardNo())) {
			throw new LogicalException("카드번호가 숫자가 아닙니다.");
		}
		if (!(10<= reqDto.getCardNo().length() && 16 >= reqDto.getCardNo().length())) {
			throw new LogicalException("카드번호 자리수가 틀립니다.");
		}
		
		if (CardUtils.isBlank(reqDto.getValidDate())) {
			throw new LogicalException("유효기간이 없습니다.");
		}
		if (!CardUtils.isNumeric(reqDto.getValidDate())) {
			throw new LogicalException("유효기간이 숫자가 아닙니다.");
		}
		if (4 != reqDto.getValidDate().length()) {
			throw new LogicalException("유효기간이 자리수가 틀립니다.");
		}
		
		if (CardUtils.isBlank(reqDto.getCvc())) {
			throw new LogicalException("cvc 없습니다.");
		}
		if (!CardUtils.isNumeric(reqDto.getCvc())) {
			throw new LogicalException("cvc 숫자가 아닙니다.");
		}
		if (3 != reqDto.getCvc().length()) {
			throw new LogicalException("cvc 자리수가 틀립니다.");
		}
		
		if (CardUtils.isBlank(reqDto.getMonthlyInstallmentPlan())) {
			throw new LogicalException("할부개월이 없습니다.");
		}
		if (!CardUtils.isNumeric(reqDto.getMonthlyInstallmentPlan())) {
			throw new LogicalException("할부개월이 숫자가 아닙니다.");
		}
		int mPlan = Integer.parseInt(reqDto.getMonthlyInstallmentPlan());
		if (0 > mPlan) {
			throw new LogicalException("할부개월 오류입니다.");
		}
		if (12 < mPlan) {
			throw new LogicalException("할부개월은 12개월을 초과 할수 없습니다.");
		}
		
		checkAmt(reqDto);
	}
	
	/**
	 * 금액 체크
	 * @param reqDto
	 * @throws Exception
	 */
	private void checkAmt(PaymentDto reqDto)throws Exception{
		if (CardUtils.isBlank(reqDto.getAmt())) {
			throw new LogicalException("금액 없습니다.");
		}
		if (!CardUtils.isNumeric(reqDto.getAmt())) {
			throw new LogicalException("금액이 숫자가 아닙니다.");
		}
		
		BigDecimal b100 = new BigDecimal(INT_100);
		BigDecimal b10m = new BigDecimal(D10M);
		BigDecimal nowAmt = new BigDecimal(reqDto.getAmt());
		
		if (nowAmt.compareTo(b100) < 0) {
			throw new LogicalException("최소금액은" + INT_100 + "원 입니다.");
		}
		if (nowAmt.compareTo(b10m) > 0) {
			throw new LogicalException("최대 금액은 10억 입니다.");
		}
		
		//부가세 자동계산
		if (CardUtils.isBlank(reqDto.getVAT())) {
			reqDto.setAutoVAT(nowAmt.divide(new BigDecimal(11), 0 ,BigDecimal.ROUND_HALF_UP).toString());
		}else {
			if (!CardUtils.isNumeric(reqDto.getVAT())) {
				throw new LogicalException("부가세가 숫자가 아닙니다.");
			}
			if (nowAmt.compareTo(new BigDecimal(reqDto.getVAT())) <=0 ) {
				throw new LogicalException("부가세가는 금액보다 클 수 없습니다.");
			}
		}
	}
	
	
	
	
	/**
	 * 관리번호를 채번한다.
	 * @return
	 */
	public String generateNextVal() {
		
		String result = "";
		String nowDate = CardUtils.getCurrentDateTime().substring(0,8);
		Map<String, Object> paramsMap = new HashMap<String, Object>(3);
		
		paramsMap.put("typeCls", "CARD000001");
		paramsMap.put("inptYmd", nowDate);
		//int udateCount = getSqlMapClientTemplate().update("CardPayment.updateSeq",paramsMap); //TODO 주석풀것
		int udateCount = 1;
		if (0 == udateCount) { //업데이트 건이 없는경우 insert
			//getSqlMapClientTemplate().insert("CardPayment.insertSeq",paramsMap); //TODO 주석풀것
		}
		//Integer seq = (Integer)getSqlMapClientTemplate.queryForObject("CardPayment.selectSeq",paramsMap); //TODO 주석풀것
		Integer seq = 1;
		result = nowDate + CardUtils.lpad(seq.toString(), 12,"0");
		
		return result;
	}
	/**
	 * 결제 정보 저장 및 카드사 전송
	 * @param reqDto
	 * @return
	 */
	public ApprovalDto sendCardInfo(PaymentDto reqDto) {
		ApprovalDto ad = new ApprovalDto();
		SendHeadDto shd = new SendHeadDto();
		SendBodyDto sbd = new SendBodyDto();
		
		//헤더
		shd.setHeadLength(CardUtils.lpad("446", 4));
		shd.setDataType(CardUtils.rpad(reqDto.getDataType(), 10));
		shd.setMngNo(reqDto.getMngNo());
		
		//바디
		sbd.setCardNo(CardUtils.rpad(reqDto.getCardNo(), 20));
		sbd.setMonthlyInstallmentPlan(CardUtils.lpad(reqDto.getMonthlyInstallmentPlan(), 2,"0"));
		sbd.setValidDate(CardUtils.lpad(reqDto.getValidDate(), 4,"0"));
		sbd.setCvc(CardUtils.rpad(reqDto.getCvc(), 3));
		sbd.setAmt(CardUtils.lpad(reqDto.getAmt(), 10));
		
		if (CardUtils.isBlank(reqDto.getVAT())) {//부가세 없으면 자동계산값으로
			sbd.setVAT(CardUtils.lpad(reqDto.getAutoVAT(), 10, "0"));
		}else {
			sbd.setVAT(CardUtils.lpad(reqDto.getVAT(), 10, "0"));
		}
		sbd.setBefMngNo(CardUtils.rpad(CardUtils.nvl(reqDto.getBefMngNo()), 20));//취소시에만
		sbd.setEncCardInfo(CardUtils.rpad(reqDto.getEncCardInfo(), 300));
		sbd.setEtc1(CardUtils.rpad("", 47));
		
		ad.setMngNo(reqDto.getMngNo());
		ad.setSendMsg(shd.toString() + sbd.toString());
		
		//디비에 결재정보저장
		//getSqlMapClientTemplate().insert("CardPayment.insertCardInfo", reqDto); //TODO 디비연결시 주석풀것
		//카드사에 전송데이타 저장
		//getSqlMapClientTemplate().insert("CardPayment.insertSendCard", sbd); //TODO 디비연결시 주석풀것
		return ad;
	}
	/**
	 * 취소 결제 리스트 조회
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	private List<PaymentDto> cancelList(PaymentDto dto) throws Exception{
		
		//TODO 디비연결시 주석풀것
		//List<PaymentDto> cancelDtoList = (List<PaymentDto>)getSqlMapClientTemplate.queryForList("CardPayment.selectCancelList", dto.getBefMngNo());
		List<PaymentDto> cancelDtoList = null;
		return cancelDtoList;
	}
	
	/**
	 * 취소시 금액 정합성
	 * @param reqDto
	 * @param payDto
	 * @param cancelDtoList
	 * @throws Exception
	 */
	public void checkCal(PaymentDto reqDto, PaymentDto payDto, List<PaymentDto> cancelDtoList) throws Exception {
		
		checkAmt(reqDto);
		
		BigDecimal payAmt = new BigDecimal(payDto.getAmt());
		BigDecimal payVAT = new BigDecimal(payDto.getVAT());
		
		BigDecimal canSumAmt = new BigDecimal(0D);
		BigDecimal canSumVAT = new BigDecimal(0D);
		
		BigDecimal totAmt = new BigDecimal(0D);
		BigDecimal totVAT = new BigDecimal(0D);
		
		BigDecimal nowAmt = new BigDecimal(reqDto.getAmt());
		BigDecimal nowVAT = null;
		
		if (null != cancelDtoList && !cancelDtoList.isEmpty()) {
			for (PaymentDto pDto : cancelDtoList) {
				canSumAmt = canSumAmt.add( new BigDecimal(pDto.getAmt()));
				canSumVAT = canSumVAT.add( new BigDecimal(pDto.getVAT()));
			}
		}
		
		//남은 잔액
		totAmt = payAmt.subtract(canSumAmt);
		totVAT = payVAT.subtract(canSumVAT);
		
		
		//현재금액 빼기
		totAmt = totAmt.subtract(nowAmt);
		int flagAmt = totAmt.compareTo(BigDecimal.ZERO);
		if (0 > flagAmt) {
			throw new LogicalException("결제 금액보다 취소 금액이 큽니다.");
		}
		
		int flagVAT = 0;
		if (!CardUtils.isBlank(reqDto.getVAT())) { //부가세 존재
			nowVAT = new BigDecimal(reqDto.getVAT());
			
			totVAT = totVAT.subtract(nowVAT); //부가세 빼기
			flagVAT = totVAT.compareTo(BigDecimal.ZERO);
			
			if (0 > flagAmt) {
				throw new LogicalException("결제 부가세보다 취소 부가세가 큽니다.");
			}
			
			//결제 금액 전부취소
			if (0 == flagAmt) {
				if (0 < flagVAT) {
					throw new LogicalException("남은 부가세가 있습니다.");
				}
			}
		}else {
			nowVAT = new BigDecimal(reqDto.getAutoVAT());
			
			if (0 == flagAmt) { //결제잔액이 0이면 부가세 전액처리
				reqDto.setVAT(totVAT.toString()); //디비저장을 위해 셋팅해준다.
			
			}else { //결제금액이 남았으면 자동계산
				totVAT = totVAT.subtract(nowVAT); //부가세 빼기
				flagVAT = totVAT.compareTo(BigDecimal.ZERO);
				
				if (0 > flagVAT) {
					throw new LogicalException("남은 부가세 보다 취소 부가세가 큽니다.");
				}
			}
		}
		
		System.out.println("잔액="+totAmt.toString() + ",잔액 부가세=" + totVAT.toString());
	}
	
	/**
	 * 카드정보 복호화
	 * @param enData
	 * @return
	 * @throws Exception
	 */
	private PaymentDto deCarInfo(String enData) throws Exception{
		
		PaymentDto pd = new PaymentDto();
		String deData = CryptoUtil.aes_decode(enData);
		
		System.out.println("deData = " + deData);
		StringTokenizer st = new StringTokenizer(deData, "|");
		int i = 0;
		while (st.hasMoreTokens()) {
			if ( i == 0) {
				pd.setCardNo(st.nextToken());
			}else if (i == 1) {
				pd.setValidDate(st.nextToken());
			}else if (i == 2) {
				pd.setCvc(st.nextToken());
			}
			i++;
		}
		
		return pd;
	}
	

}
