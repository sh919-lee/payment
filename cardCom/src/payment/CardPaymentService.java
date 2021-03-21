package payment;

/**
 * 카드결재 인터페이스
 * @author 이상훈
 * @since 2021.03.17
 */
public interface CardPaymentService {

	/**
	 * <pre>
	 * 결제 승인 요청
	 * </pre>
	 * @param PaymentDto
	 * @return ApprovalDtoDTO
	 * @throws Exception
	 */
	public ApprovalDto createPayment(PaymentDto reqDto) throws Exception;
	
	/**
	 * <pre>
	 * 결제 취소 요청
	 * </pre>
	 * @param PaymentDto
	 * @return ApprovalDtoDTO
	 * @throws Exception
	 */
	public ApprovalDto cancelPayment(PaymentDto cancelDto) throws Exception;
	
	/**
	 * <pre>
	 * 결제 데이타 조회
	 * </pre>
	 * @param  PaymentDto
	 * @return PaymentDto
	 * @throws Exception
	 */
	public PaymentDto getPaymentInfo(PaymentDto reqDto) throws Exception;
	
}
