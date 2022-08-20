/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.exception;

// TODO: Auto-generated Javadoc
/**
 * The Class UidException.
 */
public class UidException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1010966906463374079L;

	/**
	 * Instantiates a new uid exception.
	 *
	 * @param errorCode the error code
	 */
	public UidException(String errorCode) {
		super(errorCode);
	}

	/**
	 * Instantiates a new uid exception.
	 *
	 * @param cause the cause
	 */
	public UidException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new uid exception.
	 *
	 * @param errorCode the error code
	 * @param cause the cause
	 */
	public UidException(String errorCode, Throwable cause) {
		super(errorCode, cause);
	}

}
