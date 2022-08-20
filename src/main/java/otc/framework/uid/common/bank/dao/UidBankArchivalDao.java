/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.bank.dao;

import java.util.List;

import otc.framework.generic.dao.BaseDao;

// TODO: Auto-generated Javadoc
/**
 * The Interface UidBankArchivalDao.
 */
public interface UidBankArchivalDao extends BaseDao {
	
	/**
	 * Creates the archive.
	 *
	 * @param uids the uids
	 * @return the list
	 */
	public List<String> createArchive(List<String> uids); 
}
