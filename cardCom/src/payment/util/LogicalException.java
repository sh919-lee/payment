package payment.util;

/**
 * 
 * <pre>
 * 예외 메시지
 * </pre>
 *
 */
public class LogicalException extends Exception{
	
	private static final long serialVersionUID = 2482643654689373480L;

	public LogicalException(String errMsg) {
		super(errMsg);
	}

}
