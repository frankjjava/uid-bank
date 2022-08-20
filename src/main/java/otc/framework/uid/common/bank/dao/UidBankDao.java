/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.bank.dao;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import otc.framework.generic.dao.BaseDao;
import otc.framework.uid.common.dto.UidBankDto;

// TODO: Auto-generated Javadoc
/**
 * The Interface UidBankDao.
 */
public interface UidBankDao extends BaseDao {
	
	/** The column name uid. */
	String COLUMN_NAME_UID = "uid";
	
	/** The column name consumed date. */
	String COLUMN_NAME_CONSUMED_DATE = "CONSUMED_DATE";
	
	/** The column name end of life date. */
	String COLUMN_NAME_END_OF_LIFE_DATE = "END_OF_LIFE_DATE";
	
	/** The column value status new. */
	String COLUMN_VALUE_STATUS_NEW = "NEW";
	
	/** The column value status active. */
	String COLUMN_VALUE_STATUS_ACTIVE = "ACTIVE";
	
	/** The sql select count. */
	String SQL_SELECT_COUNT = "select count(";
	
	/** The sql select count uid. */
	String SQL_SELECT_COUNT_UID = new StringBuilder(SQL_SELECT_COUNT)
			.append(COLUMN_NAME_UID)
			.append(") FROM ").toString();
	
	/** The sql select uid. */
	String SQL_SELECT_UID = new StringBuilder("select ")
			.append(COLUMN_NAME_UID)
			.append(" FROM ").toString();
	
	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	String getBankName();
	
	/**
	 * Read one available uid.
	 *
	 * @return the uid bank dto
	 */
	UidBankDto readOneAvailableUid();

	/**
	 * Read one available uid for update.
	 *
	 * @return the uid bank dto
	 */
	UidBankDto readOneAvailableUidForUpdate();

	/**
	 * Read unsed uid count.
	 *
	 * @return the int
	 */
	int readUnsedUidCount();

	/**
	 * Read all uids.
	 *
	 * @return the sets the
	 */
	Set<String> readAllUids();

	/**
	 * Creates the uids.
	 *
	 * @param uids the uids
	 * @return true, if successful
	 */
	boolean createUids(List<String> uids);

	/**
	 * Update uid.
	 *
	 * @param uniqueId the unique id
	 * @param status the status
	 * @param endOfLifetimestamp the end of lifetimestamp
	 * @return true, if successful
	 */
	boolean updateUid(String uniqueId, String status, Timestamp endOfLifetimestamp);

	/**
	 * Creates the bank.
	 *
	 * @return true, if successful
	 */
	boolean createBank();
	
	/**
	 * Read all older than.
	 *
	 * @param dormantDays the dormant days
	 * @return the list
	 */
	List<UidBankDto> readAllOlderThan(int dormantDays);
	
	/**
	 * Delete uid.
	 *
	 * @param uids the uids
	 * @return the int
	 */
	int deleteUid(List<String> uids);
}
