/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.bank.biz;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import otc.framework.uid.common.dto.UidBankDto;

// TODO: Auto-generated Javadoc
/**
 * The Interface UidBankService.
 */
public interface UidBankService {

	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	String getBankName();

	/**
	 * Gets the lower water mark.
	 *
	 * @return the lower water mark
	 */
	Integer getLowerWaterMark();

	/**
	 * Sets the lower water mark.
	 *
	 * @param threshold the new lower water mark
	 */
	void setLowerWaterMark(Integer threshold);

	/**
	 * Gets the refill size.
	 *
	 * @return the refill size
	 */
	Integer getRefillSize();

	/**
	 * Sets the refill size.
	 *
	 * @param refillSize the new refill size
	 */
	void setRefillSize(Integer refillSize);

	/**
	 * Modify uid.
	 *
	 * @param uid the uid
	 * @param status the status
	 * @param endOfLifetimestamp the end of lifetimestamp
	 * @return true, if successful
	 */
	boolean modifyUid(String uid, String status, Timestamp endOfLifetimestamp);

	/**
	 * Load next uid.
	 *
	 * @return the string
	 */
	String loadNextUid();

	/**
	 * Load one available uid with lock.
	 *
	 * @return the uid bank dto
	 */
	UidBankDto loadOneAvailableUidWithLock();

	/**
	 * Load all uids.
	 *
	 * @return the sets the
	 */
	Set<String> loadAllUids();

	/**
	 * Save uids.
	 *
	 * @param uids the uids
	 * @return true, if successful
	 */
	boolean saveUids(List<String> uids);

	/**
	 * Gets the count of unsed uids.
	 *
	 * @return the count of unsed uids
	 */
	int getCountOfUnsedUids();

	/**
	 * Generate uids and fill bank.
	 *
	 * @return true, if successful
	 */
	boolean generateUidsAndFillBank();

	/**
	 * Inits the.
	 */
	void init();
}
