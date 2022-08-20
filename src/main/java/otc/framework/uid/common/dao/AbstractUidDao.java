/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import otc.framework.generic.dao.AbstractDaoImpl;
import otc.framework.uid.common.dto.UidDto;
import otc.framework.uid.common.exception.UidException;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUidDao.
 */
public abstract class AbstractUidDao extends AbstractDaoImpl implements UidDao {

	/**
	 * Creates the result set extractor for list.
	 *
	 * @return the result set extractor
	 */
	protected ResultSetExtractor<List<UidDto>> createResultSetExtractorForList() {
		return new ResultSetExtractor<List<UidDto>>() {
			@Override
			public List<UidDto> extractData(ResultSet rs) {
				List<UidDto> bankNameDtos = null;
				try {
					while (rs.next()) {
						if (bankNameDtos == null) {
							bankNameDtos = new ArrayList<>();
						}
						UidDto bankNameDto = new UidDto();
						bankNameDtos.add(bankNameDto);
						bankNameDto.setBankName(rs.getString(COLUMN_BANK_NAME));
					}
				} catch (SQLException e) {
					throw new UidException(e);
				}
				return bankNameDtos;
			}
		};
	}

	/**
	 * Creates the result set extractor.
	 *
	 * @return the result set extractor
	 */
	protected ResultSetExtractor<UidDto> createResultSetExtractor() {
		return new ResultSetExtractor<UidDto>() {
			@Override
			public UidDto extractData(ResultSet rs) {
				try {
					if (rs.next()) {
						UidDto bankNameDto = new UidDto();
						bankNameDto.setBankName(rs.getString(COLUMN_BANK_NAME));
						return bankNameDto;
					}
				} catch (SQLException e) {
					throw new UidException(e);
				}
				return null;
			}
		};
	}	
}
