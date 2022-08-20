/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.dao;

import otc.framework.generic.dao.BaseDao;

// TODO: Auto-generated Javadoc
/**
 * The Interface UidDao.
 */
public interface UidDao extends BaseDao {
	
	/** The table bank names. */
	String TABLE_BANK_NAMES = "UID_BANK_NAMES";
	
	/** The column bank name. */
	String COLUMN_BANK_NAME = "BANK_NAME";
	
	/**
	 * Read bank name for update.
	 *
	 * @param bankName the bank name
	 * @return the string
	 */
	public String readBankNameForUpdate(String bankName);

	/**
	 * Creates the bank name.
	 *
	 * @param bankName the bank name
	 * @return true, if successful
	 */
	public boolean createBankName(String bankName);
	
}
