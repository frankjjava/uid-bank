/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.biz;

// TODO: Auto-generated Javadoc
/**
 * The Interface UidService.
 */
public interface UidService {

	/**
	 * Modify uid.
	 *
	 * @param bankName the bank name
	 * @param uid the uid
	 * @param status the status
	 * @return true, if successful
	 */
	public boolean modifyUid(String bankName, String uid, String status);

	/**
	 * Load next uid.
	 *
	 * @param bankName the bank name
	 * @return the string
	 */
	public String loadNextUid(String bankName);
	
	/**
	 * Load bank name for update.
	 *
	 * @param bankName the bank name
	 * @return the string
	 */
	public String loadBankNameForUpdate(String bankName);
		
	/**
	 * Fill bank.
	 *
	 * @param bankName the bank name
	 * @return true, if successful
	 */
	public boolean fillBank(String bankName);
}
