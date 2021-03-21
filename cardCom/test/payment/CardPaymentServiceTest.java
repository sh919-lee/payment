package payment;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import payment.impl.CardPaymentServiceImpl;

public class CardPaymentServiceTest {

	
	/** 결제 테스트 */
	@Test
	public void createPaymentTest() {
		try {
			CardPaymentService cps = new CardPaymentServiceImpl();
			
			PaymentDto reqDto = new PaymentDto();
			reqDto.setCardNo("1234567890123456");
			reqDto.setValidDate("1125");
			reqDto.setCvc("777");
			reqDto.setMonthlyInstallmentPlan("0");
			reqDto.setAmt("20000");
			reqDto.setVAT("10000");
			
			ApprovalDto ad = cps.createPayment(reqDto);
			
			System.out.println("MngNo=" + ad.getMngNo());
			System.out.println("endMsg=" + ad.getSendMsg()+"X");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 결제취소 테스트 */
	@Test
	public void cancelPaymentTest() {
		try {
			CardPaymentService cps = new CardPaymentServiceImpl();
			
			PaymentDto reqDto = new PaymentDto();
			
			reqDto.setMngNo("20210320000000000001");
			reqDto.setAmt("20000");
			//reqDto.setVAT("10000");
			
			ApprovalDto ad = cps.cancelPayment(reqDto);
			
			System.out.println("MngNo=" + ad.getMngNo());
			System.out.println("endMsg=" + ad.getSendMsg()+"X");
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 조회 테스트 */
	@Test
	public void getPaymentInfoTest() {
		try {
			CardPaymentService cps = new CardPaymentServiceImpl();
			
			PaymentDto reqDto = new PaymentDto();
			
			reqDto.setMngNo("20210320000000000001");
		
			
			PaymentDto pd = cps.getPaymentInfo(reqDto);
			
			System.out.println("getCardNo=" + pd.getCardNo());
			System.out.println("getValidDate=" + pd.getValidDate());
			System.out.println("getCvc=" + pd.getCvc());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 부분취소 테스트 1 */
	@Test
	public void checkCalCase1() {
		try {
			
			CardPaymentServiceImpl cps = new CardPaymentServiceImpl();
			
			PaymentDto payDto = new PaymentDto(); //결제된금액
			payDto.setAmt("11000");
			payDto.setVAT("1000");
			
			List<PaymentDto> cancelDtoList = new ArrayList<PaymentDto>(3); 
			
			PaymentDto reqDto = setCan(100); //취소요청금액
			
			//기존에 취소된 금액액
			cancelDtoList.add(setCan(1100, 100));
			cancelDtoList.add(setCan(3300, 300));
			//cancelDtoList.add(setCan(6600, 600));
			
			cps.checkCal(reqDto, payDto, cancelDtoList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 부분취소 테스트 2 */
	@Test
	public void checkCalCase2() {
		try {
			
			CardPaymentServiceImpl cps = new CardPaymentServiceImpl();
			
			PaymentDto payDto = new PaymentDto(); //결제된금액
			payDto.setAmt("20000");
			payDto.setVAT("909");
			
			List<PaymentDto> cancelDtoList = new ArrayList<PaymentDto>(3); 
			
			PaymentDto reqDto = setCan(10000, 909); //취소요청금액
			
			//기존에 취소된 금액액
			cancelDtoList.add(setCan(10000, 0));
		
			
			cps.checkCal(reqDto, payDto, cancelDtoList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 부분취소 테스트 3 */
	@Test
	public void checkCalCase3() {
		try {
			
			CardPaymentServiceImpl cps = new CardPaymentServiceImpl();
			
			PaymentDto payDto = new PaymentDto(); //결제된금액
			payDto.setAmt("20000");
			payDto.setVAT("1818");//자동계산된 값임
			
			List<PaymentDto> cancelDtoList = new ArrayList<PaymentDto>(3); 
			
			PaymentDto reqDto = setCan(10000); //취소요청금액
			
			//기존에 취소된 금액액
			cancelDtoList.add(setCan(10000, 1000));
			
			cps.checkCal(reqDto, payDto, cancelDtoList);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 금액 셋팅
	 * @param amt
	 * @param vat
	 * @return
	 */
	private PaymentDto setCan(int amt, int vat) {
		PaymentDto pd = new PaymentDto();
		pd.setAmt(""+amt);
		pd.setVAT(""+vat);
		return pd;
	}
	private PaymentDto setCan(int amt) {
		PaymentDto pd = new PaymentDto();
		pd.setAmt(""+amt);
		return pd;
	}
	
	

}
