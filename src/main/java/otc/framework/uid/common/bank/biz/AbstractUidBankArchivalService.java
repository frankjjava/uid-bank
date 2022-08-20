/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.bank.biz;

import otc.framework.uid.common.bank.dao.UidBankArchivalDao;
import otc.framework.uid.common.bank.dao.UidBankDao;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUidBankArchivalService.
 */
public abstract class AbstractUidBankArchivalService implements UidBankArchivalService {

	/** The dormant days. */
	protected Integer dormantDays;
	
	/** The default dormant days. */
	protected int defaultDormantDays = 120;
	
	/** The uid bank dao. */
	protected UidBankDao uidBankDao;
	
	/** The uid bank archival dao. */
	protected UidBankArchivalDao uidBankArchivalDao;

	/**
	 * Gets the dormant days.
	 *
	 * @return the dormant days
	 */
	public Integer getDormantDays() {
		return dormantDays;
	}

	/**
	 * Gets the uid bank dao.
	 *
	 * @return the uid bank dao
	 */
	public UidBankDao getUidBankDao() {
		return uidBankDao;
	}

	/**
	 * Sets the uid bank dao.
	 *
	 * @param uidBankDao the new uid bank dao
	 */
	public void setUidBankDao(UidBankDao uidBankDao) {
		this.uidBankDao = uidBankDao;
	}

	/**
	 * Gets the uid bank archival dao.
	 *
	 * @return the uid bank archival dao
	 */
	public UidBankArchivalDao getUidBankArchivalDao() {
		return uidBankArchivalDao;
	}

	/**
	 * Sets the uid bank archival dao.
	 *
	 * @param uidBankArchivalDao the new uid bank archival dao
	 */
	public void setUidBankArchivalDao(UidBankArchivalDao uidBankArchivalDao) {
		this.uidBankArchivalDao = uidBankArchivalDao;
	}
	
}
