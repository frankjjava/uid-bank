/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.biz;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import otc.framework.uid.common.UidConstants;
import otc.framework.uid.common.bank.biz.UidBankService;
import otc.framework.uid.common.dao.UidDao;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUidService.
 */
public abstract class AbstractUidService implements UidService {

	/** The logger. */
	private Logger LOGGER = LoggerFactory.getLogger(AbstractUidService.class);
	
	/** The map uid bank services. */
	protected static Map<String, UidBankService> mapUidBankServices = null;
	
	/** The default attempts count. */
	protected int defaultAttemptsCount = 5;
	
	/** The default sleep time. */
	protected int defaultSleepTime = 1000;
	
	/** The sleep time. */
	protected Integer sleepTime;
	
	/** The attempts count. */
	protected Integer attemptsCount;
	
	/** The uid service. */
	protected UidService uidService;
	
	/** The uid dao. */
	protected UidDao uidDao;
	
	/** The thread pool executor. */
	protected ThreadPoolTaskExecutor threadPoolExecutor;

	/**
	 * Gets the uid service.
	 *
	 * @return the uid service
	 */
	public UidService getUidService() {
		return uidService;
	}

	/**
	 * Sets the uid service.
	 *
	 * @param uidService the new uid service
	 */
	public void setUidService(UidService uidService) {
		this.uidService = uidService;
	}

	/**
	 * Gets the uid dao.
	 *
	 * @return the uid dao
	 */
	public UidDao getUidDao() {
		return uidDao;
	}

	/**
	 * Sets the uid dao.
	 *
	 * @param uidDao the new uid dao
	 */
	public void setUidDao(UidDao uidDao) {
		this.uidDao = uidDao;
	}

	/**
	 * Gets the thread pool executor.
	 *
	 * @return the thread pool executor
	 */
	public ThreadPoolTaskExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	/**
	 * Sets the thread pool executor.
	 *
	 * @param threadPoolExecutor the new thread pool executor
	 */
	public void setThreadPoolExecutor(ThreadPoolTaskExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}
	
	/**
	 * Inits the.
	 */
	protected void init() {
		if (sleepTime == null || attemptsCount == null) {
			ConfigurationDto configurationDto = null;
//			configuratorServiceClient.fetchConfiguration("THIRDPARTY_PNR_ARCHIVAL", null, null, null, "SUPER_PNRS", 
//					UidConstants.KEY_DORMANT_DAYS, null, null);
			if (configurationDto != null) {
				sleepTime = configurationDto.getInteger(UidConstants.KEY_DORMANT_DAYS);
				attemptsCount = configurationDto.getInteger(UidConstants.KEY_ATTEMPTS_COUNT);
			}
			if (sleepTime == null) {
				sleepTime = defaultSleepTime;
				LOGGER.info("SLEEP_TIME not configured! Using default value : " + defaultSleepTime);
			}
			if (attemptsCount == null) {
				attemptsCount = defaultAttemptsCount;
				LOGGER.info("ATTEMPTS_COUNT not configured! Using default value : " + defaultAttemptsCount);
			}
		}
		if (mapUidBankServices == null) {
			mapUidBankServices = new HashMap<>();
		}
		for (Entry<Object, Object> entry : configParams.entrySet()) {
			Object value = entry.getValue();
			if (value instanceof UidBankService) {
				UidBankService uidBankService = (UidBankService) value;
				mapUidBankServices.put(uidBankService.getBankName(), uidBankService);
			}	
		}
	}
}
