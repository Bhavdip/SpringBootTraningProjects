package trainging.spring.boot.exception;

/**
 * Customer Exception Type Class
 * @author ac518
 *
 */
public class UserServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -288338612005182356L;
	
	public UserServiceException(String localizedMessage) {
		super(localizedMessage);
	}
}
