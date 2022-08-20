/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.core.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import otc.framework.uid.common.dao.AbstractUidDao;
import otc.framework.uid.common.dto.UidDto;

// TODO: Auto-generated Javadoc
/**
 * The Class UidDaoImpl.
 */
public class UidDaoImpl extends AbstractUidDao {

	/** The logger. */
	private Logger LOGGER = LoggerFactory.getLogger(UidDaoImpl.class);

	/**
	 * Read bank name for update.
	 *
	 * @param bankName the bank name
	 * @return the string
	 */
	@Override
	public String readBankNameForUpdate(String bankName) {
		StringBuilder whereClause = buildWhereClause(null, COLUMN_BANK_NAME, bankName, null, true);
		String sql = new StringBuilder(SQL_SELECT_FROM)
					.append(TABLE_BANK_NAMES)
					.append(whereClause)
					.append(LOCK_FOR_UPDATE_NOWAIT)
					.toString();
		UidDto uidDto = executeQuery(sql, createResultSetExtractor());
		if (uidDto == null) {
			createBankName(bankName);
		}
		uidDto = executeQuery(sql, createResultSetExtractor());
		return uidDto.getBankName();
	}

	/**
	 * Creates the bank name.
	 *
	 * @param bankName the bank name
	 * @return true, if successful
	 */
	@Override
	public boolean createBankName(String bankName) {
		Map<String, Object> params = new HashMap<>();
		params.put(COLUMN_BANK_NAME, bankName);
		executeInsert(TABLE_BANK_NAMES, params);
		return true;
	}
}
