/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.core.biz.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.transaction.UnexpectedRollbackException;

import otc.framework.uid.common.bank.biz.UidBankService;
import otc.framework.uid.common.biz.AbstractUidService;
import otc.framework.uid.common.dto.UidBankDto;

// TODO: Auto-generated Javadoc
/**
 * The Class UidServiceImpl.
 */
public class UidServiceImpl extends AbstractUidService {

	/** The logger. */
	private Logger LOGGER = LoggerFactory.getLogger(UidServiceImpl.class);

	/**
	 * Modify uid.
	 *
	 * @param bankName the bank name
	 * @param uid the uid
	 * @param status the status
	 * @return true, if successful
	 */
	@Override
	public boolean modifyUid(String bankName, String uid, String status) {
		UidBankService uidBankService = mapUidBankServices.get(bankName);
		uidBankService.modifyUid(uid, status, null);
		return true;
	}

	/**
	 * Load bank name for update.
	 *
	 * @param bankName the bank name
	 * @return the string
	 */
	@Override
	public String loadBankNameForUpdate(String bankName) {
		LOGGER.debug("Attempting row-level Lock on bank : " + bankName + " in DB table. Lock will be released automatically when Tx completes.");
		bankName = uidDao.readBankNameForUpdate(bankName);
		return bankName;
	}

	/**
	 * Load next uid.
	 *
	 * @param bankName the bank name
	 * @return the string
	 */
	@Override
	public String loadNextUid(String bankName) {
        UidBankDto uidBankDto = attemptLoad(bankName);
		try {
			boolean isSynchronous = true;
			if (uidBankDto != null) {
				isSynchronous = false;
			}
			fillIfBankrupt(bankName, isSynchronous);
		} catch (UnexpectedRollbackException e1) {
			LOGGER.warn(e1.getMessage());
		}
		if (uidBankDto == null) {
			int attempts = 0;
			while (true) {
				uidBankDto = attemptLoad(bankName);
				attempts++;
				if (uidBankDto != null) {
					break;
				}
				if (attempts == attemptsCount) {
					LOGGER.error(new StringBuilder("Could not load UID even after attempting ").append(attempts)
							.append(" times! Giving up. If you wish to make more attempts, please configure value accordingly.").toString());
				}
			}
		}
		String uid = uidBankDto.getUid();
		modifyUid(bankName, uid, "ACTIVE");
		return uid;
	}

	/**
	 * Attempt load.
	 *
	 * @param bankName the bank name
	 * @return the uid bank dto
	 */
	private UidBankDto attemptLoad(String bankName) {
		if (mapUidBankServices == null) {
			init();
		}
		UidBankService uidBankService = mapUidBankServices.get(bankName);
        UidBankDto uidBankDto = null;
        try {
        	uidBankDto = uidBankService.loadOneAvailableUidWithLock();
        } catch (CannotAcquireLockException e) {
            LOGGER.error(e.getMessage());
            if (sleepTime == null) {
            	init();
            }
            LOGGER.error(new StringBuilder("Will attempt after sleeping for '").append(sleepTime /1000).append("' seconds").toString());
			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e1) {
				LOGGER.warn(e1.getMessage());
			}
        }
        return uidBankDto;
	}
	
	/**
	 * Fill if bankrupt.
	 *
	 * @param bankName the bank name
	 * @param isSynchronous the is synchronous
	 * @return the future
	 */
	private Future<?> fillIfBankrupt(String bankName, boolean isSynchronous) {
		UidBankService uidBankService = mapUidBankServices.get(bankName);
		int threshold = uidBankService.getLowerWaterMark();
		int refillSize = uidBankService.getRefillSize();
		if (isSynchronous) {
			LOGGER.debug("Filling UID bank Synchronously for bank : " + bankName);
			boolean filled = uidService.fillBank(bankName);
			Future<Boolean> future = CompletableFuture.completedFuture(filled);
			return future;
		}
		int count = uidBankService.getCountOfUnsedUids();
		int remaining = refillSize - threshold;
		if (remaining < count) {
			return CompletableFuture.completedFuture(false);
		}
		Future<Object> future = threadPoolExecutor.submit(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				LOGGER.debug("Filling UID bank Asynchronously for bank : " + bankName);
				return uidService.fillBank(bankName);
			}
		});
		return future;
	}

	/**
	 * Fill bank.
	 *
	 * @param bankName the bank name
	 * @return true, if successful
	 */
	@Override
	public boolean fillBank(String bankName) {
		try {
			loadBankNameForUpdate(bankName);
		} catch (CannotAcquireLockException exception) {
			LOGGER.warn(exception.getMessage());
			try {
				Thread.sleep(sleepTime);
				return false;
			} catch (InterruptedException e1) {
				LOGGER.warn(e1.getMessage());
			}
		}
		UidBankService uidBankService = mapUidBankServices.get(bankName);
		boolean generateUidsForFillBankResult = uidBankService.generateUidsAndFillBank();
		return generateUidsForFillBankResult;
	}
}
