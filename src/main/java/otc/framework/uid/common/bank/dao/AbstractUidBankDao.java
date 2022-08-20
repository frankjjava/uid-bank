/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.bank.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import otc.framework.generic.dao.AbstractDaoImpl;
import otc.framework.uid.common.dto.UidBankDto;
import otc.framework.uid.common.exception.UidException;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUidBankDao.
 */
public abstract class AbstractUidBankDao extends AbstractDaoImpl { /* -- do not implement UidBankDao here -- */ 

	/** The bank name. */
 protected String bankName;

	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName the new bank name
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	/**
	 * Creates the result set extractor for list.
	 *
	 * @return the result set extractor
	 */
	protected ResultSetExtractor<List<UidBankDto>> createResultSetExtractorForList() {
		return new ResultSetExtractor<List<UidBankDto>>() {
			@Override
			public List<UidBankDto> extractData(ResultSet rs) {
				List<UidBankDto> uniqueIdDtos = null;
				try {
					while (rs.next()) {
						if (uniqueIdDtos == null) {
							uniqueIdDtos = new ArrayList<>();
						}
						UidBankDto uniqueIdDto = createUidBankDto(rs);
						uniqueIdDtos.add(uniqueIdDto);
					}
				} catch (SQLException e) {
					throw new UidException(e);
				}
				return uniqueIdDtos;
			}
		};
	}

	/**
	 * Creates the result set extractor.
	 *
	 * @return the result set extractor
	 */
	protected ResultSetExtractor<UidBankDto> createResultSetExtractor() {
		return new ResultSetExtractor<UidBankDto>() {
			@Override
			public UidBankDto extractData(ResultSet rs) {
				try {
					if (rs.next()) {
						return createUidBankDto(rs);
					}
				} catch (SQLException e) {
					throw new UidException(e);
				}
				return null;
			}
		};
	}

	/**
	 * Creates the result set extractor for uid list.
	 *
	 * @return the result set extractor
	 */
	protected ResultSetExtractor<Set<String>> createResultSetExtractorForUidList() {
		return new ResultSetExtractor<Set<String>>() {
			@Override
			public Set<String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Set<String> uniqueIds = null;
				try {
					while (rs.next()) {
						if (uniqueIds == null) {
							uniqueIds = new HashSet<String>();
						}
						uniqueIds.add(rs.getString(UidBankDao.COLUMN_NAME_UID));
					}
				} catch (SQLException e) {
					throw new UidException(e);
				}
				return uniqueIds;
			}

		};
	}
	
	/**
	 * Creates the uid bank dto.
	 *
	 * @param rs the rs
	 * @return the uid bank dto
	 */
	private UidBankDto createUidBankDto(ResultSet rs) {
		try {
			UidBankDto uniqueIdDto = new UidBankDto();
			uniqueIdDto.setUid(rs.getString(UidBankDao.COLUMN_NAME_UID));
			uniqueIdDto.setCreatedDate(rs.getTimestamp(COLUMN_CREATED_TIMESTAMP));
			uniqueIdDto.setConsumedDate(rs.getTimestamp(UidBankDao.COLUMN_NAME_CONSUMED_DATE));
			uniqueIdDto.setEndOfLifeDate(rs.getTimestamp(UidBankDao.COLUMN_NAME_END_OF_LIFE_DATE));
			uniqueIdDto.setStatus(rs.getString(COLUMN_NAME_STATUS));
			return uniqueIdDto;
		} catch (SQLException e) {
			throw new UidException(e);
		}
	}
}
