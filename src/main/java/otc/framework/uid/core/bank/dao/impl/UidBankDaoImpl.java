/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.core.bank.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import otc.framework.generic.dao.dto.TableMetaDataDto;
import otc.framework.generic.dao.dto.TableMetaDataDto.ColumnMetaDataDto;
import otc.framework.generic.dao.dto.TableMetaDataDto.ColumnMetaDataDto.CONSTRAINTS;
import otc.framework.uid.common.bank.dao.AbstractUidBankDao;
import otc.framework.uid.common.bank.dao.UidBankDao;
import otc.framework.uid.common.dto.UidBankDto;

// TODO: Auto-generated Javadoc
/**
 * The Class UidBankDaoImpl.
 */
public class UidBankDaoImpl extends AbstractUidBankDao implements UidBankDao {

	/**
	 * Read unsed uid count.
	 *
	 * @return the int
	 */
	@Override
	public int readUnsedUidCount() {
		StringBuilder whereClause = buildWhereClause(null, COLUMN_NAME_STATUS, COLUMN_VALUE_STATUS_NEW, null, true);
		String sql = new StringBuilder(SQL_SELECT_COUNT_UID).append(bankName).append(whereClause).toString();
		Integer count = executeQuery(sql, Integer.class);
		return count;
	}

	/**
	 * Read all uids.
	 *
	 * @return the sets the
	 */
	@Override
	public Set<String> readAllUids() {
		String query = new StringBuilder(SQL_SELECT_UID).append(bankName).toString();
		Set<String> uniqueIdDtos = executeQuery(query, createResultSetExtractorForUidList());
		return uniqueIdDtos;
	}

	/**
	 * Read one available uid.
	 *
	 * @return the uid bank dto
	 */
	@Override
	public UidBankDto readOneAvailableUid() {
		StringBuilder whereClause = buildWhereClause(null, COLUMN_NAME_STATUS, COLUMN_VALUE_STATUS_NEW, null, true);
		String query = new StringBuilder(SQL_SELECT_FROM).append(bankName).append(whereClause).append(LIMIT_ONE).toString();
		UidBankDto uidDto = executeQuery(query, createResultSetExtractor());
		return uidDto;
	}

	/**
	 * Read one available uid for update.
	 *
	 * @return the uid bank dto
	 */
	@Override
	public UidBankDto readOneAvailableUidForUpdate() {
		StringBuilder whereClause = buildWhereClause(null, COLUMN_NAME_STATUS, COLUMN_VALUE_STATUS_NEW, null, true);
		String query = new StringBuilder(SQL_SELECT_FROM).append(bankName).append(whereClause).append(LOCK_FOR_UPDATE_NOWAIT_LIMIT_ONE).toString();
//		String query = new StringBuilder(SQL_SELECT_FROM).append(bankName).append(whereClause).append(LIMIT_ONE).toString();
		UidBankDto uidDto = executeQuery(query, createResultSetExtractor());
		return uidDto;
	}

	/**
	 * Read all older than.
	 *
	 * @param dormantDays the dormant days
	 * @return the list
	 */
	@Override
	public List<UidBankDto> readAllOlderThan(int dormantDays) {
		String sql = new StringBuilder(SQL_SELECT_FROM).append(bankName).append(" where ")
			.append(UidBankDao.COLUMN_NAME_CONSUMED_DATE).append(" < ")
			.append("current_date - interval '").append(dormantDays).append("' day").toString();
		List<UidBankDto> uniqueIdDtos = executeQuery(sql, createResultSetExtractorForList());
		return uniqueIdDtos;
	}
	
	/**
	 * Creates the uids.
	 *
	 * @param uids the uids
	 * @return true, if successful
	 */
	@Override
	public boolean createUids(List<String> uids) {
		Map<String, Object>[] paramsArray = new HashMap[uids.size()];
		for (int idx = 0; idx < uids.size(); idx++) {
			Map<String, Object> params = new HashMap<>();
			paramsArray[idx] = params;
			params.put(COLUMN_NAME_UID, uids.get(idx));
			params.put(COLUMN_NAME_STATUS, "NEW");
			params.put(COLUMN_CREATED_TIMESTAMP, new Date(System.currentTimeMillis()));
		}
		int[] count = executeBatchInsert(bankName, paramsArray);
		if (count != null && count.length > 0 && count[0] > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Update uid.
	 *
	 * @param uid the uid
	 * @param status the status
	 * @param endOfLifetimestamp the end of lifetimestamp
	 * @return true, if successful
	 */
	@Override
	public boolean updateUid(String uid, String status, Timestamp endOfLifetimestamp) {
		StringBuilder setClause = buildSetClause(null, COLUMN_NAME_STATUS);
		setClause = buildSetClause(setClause, COLUMN_NAME_CONSUMED_DATE);
        if (endOfLifetimestamp != null) {
            setClause = buildSetClause(setClause, COLUMN_NAME_END_OF_LIFE_DATE);
        }
		StringBuilder whereClause = buildWhereClause(null, COLUMN_NAME_UID, null, null, true);
		String sqlClause = new StringBuilder(SQL_UPDATE)
					.append(bankName)
					.append(" ")
					.append(setClause)
					.append(whereClause)
					.toString();
		Map<String, Object> params = new HashMap<>();
		params.put(COLUMN_NAME_UID, uid);
		params.put(COLUMN_NAME_STATUS, COLUMN_VALUE_STATUS_ACTIVE);
		params.put(COLUMN_NAME_CONSUMED_DATE, new Date(System.currentTimeMillis()));
		if (endOfLifetimestamp != null) {
			params.put(COLUMN_NAME_END_OF_LIFE_DATE, endOfLifetimestamp);
		}
		int count = executeNamedUpdate(sqlClause, params);
		if (count > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Delete uid.
	 *
	 * @param uids the uids
	 * @return the int
	 */
	@Override
	public int deleteUid(List<String> uids) {
		String uidCsv = uids.toString();
		uidCsv = uidCsv.replace("[", "").replace("]", "");
		String sql = new StringBuilder(SQL_DELETE_FROM).append(bankName).append(" where ")
			.append(COLUMN_NAME_UID).append(" in (").append(uidCsv).append(")").toString();
		int count = executeUpdate(sql);
		return count;
	}
	
	/**
	 * Creates the bank.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean createBank() {
		TableMetaDataDto tableMetaDataDto = new TableMetaDataDto();
		tableMetaDataDto.setTableName(bankName);
		tableMetaDataDto.setColumns(new HashSet<>());

		ColumnMetaDataDto columnMetaDataDto = new ColumnMetaDataDto();
		tableMetaDataDto.getColumns().add(columnMetaDataDto);
		columnMetaDataDto.setColumnName(COLUMN_NAME_UID);
		columnMetaDataDto.setType(ColumnMetaDataDto.TYPE.VARCHAR);
		columnMetaDataDto.setLength(128);
		Set<CONSTRAINTS> constraints = new HashSet<>();
		columnMetaDataDto.setConstraints(constraints);
		constraints.add(CONSTRAINTS.PRIMARY_KEY);

		columnMetaDataDto = new ColumnMetaDataDto();
		tableMetaDataDto.getColumns().add(columnMetaDataDto);
		columnMetaDataDto.setColumnName(COLUMN_NAME_STATUS);
		columnMetaDataDto.setType(ColumnMetaDataDto.TYPE.VARCHAR);
		columnMetaDataDto.setLength(50);

		columnMetaDataDto = new ColumnMetaDataDto();
		tableMetaDataDto.getColumns().add(columnMetaDataDto);
		columnMetaDataDto.setColumnName(COLUMN_CREATED_TIMESTAMP);
		columnMetaDataDto.setType(ColumnMetaDataDto.TYPE.TIMESTAMP);
		columnMetaDataDto.setLength(50);

		columnMetaDataDto = new ColumnMetaDataDto();
		tableMetaDataDto.getColumns().add(columnMetaDataDto);
		columnMetaDataDto.setColumnName(COLUMN_NAME_CONSUMED_DATE);
		columnMetaDataDto.setType(ColumnMetaDataDto.TYPE.TIMESTAMP);
		columnMetaDataDto.setLength(50);

		return createTable(tableMetaDataDto);
	}
}
