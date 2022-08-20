/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.bank.biz;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface UidBankArchivalService.
 */
public interface UidBankArchivalService {

	/**
	 * Gets the dormant days.
	 *
	 * @return the dormant days
	 */
	public Integer getDormantDays();
	
	/**
	 * Archive.
	 *
	 * @return the list
	 */
	public List<String> archive();

}
