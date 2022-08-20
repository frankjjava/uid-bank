/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.core.bank.biz.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.text.StrBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import otc.framework.uid.common.bank.biz.AbstractUidBankService;
import otc.framework.uid.common.dto.UidBankDto;

// TODO: Auto-generated Javadoc
/**
 * The Class UidBankServiceImpl.
 */
public class UidBankServiceImpl extends AbstractUidBankService {

	/** The logger. */
	private Logger LOGGER = LoggerFactory.getLogger(UidBankServiceImpl.class);

	/**
	 * Load next uid.
	 *
	 * @return the string
	 */
	@Override
	public String loadNextUid() {
		LOGGER.debug("Loading next UID from bank : " + uidBankDao.getBankName());
		UidBankDto uidBankDto = uidBankDao.readOneAvailableUid();
		if (uidBankDto != null) {
			return uidBankDto.getUid();
		}
		return null;
	}

	/**
	 * Modify uid.
	 *
	 * @param uid the uid
	 * @param status the status
	 * @param endOfLifetimestamp the end of lifetimestamp
	 * @return true, if successful
	 */
	@Override
	public boolean modifyUid(String uid, String status, Timestamp endOfLifetimestamp) {
		LOGGER.debug("Updating UID from bank : " + uidBankDao.getBankName());
		uidBankDao.updateUid(uid, status, endOfLifetimestamp);
		return true;
	}

	/**
	 * Load one available uid with lock.
	 *
	 * @return the uid bank dto
	 */
	@Override
	public UidBankDto loadOneAvailableUidWithLock() {
		LOGGER.debug("Attempting row-level lock for any available UID from bank : " + uidBankDao.getBankName());
		UidBankDto uidBankDto = uidBankDao.readOneAvailableUidForUpdate();
		LOGGER.debug(new StringBuilder("Locked UID : ").append(uidBankDto.getUid()).append(" in UID Bank : ").append(uidBankDao.getBankName())
				.append(". Lock will be released automatically when Tx completes : ").toString());
		return uidBankDto; 
		
	}

	/**
	 * Load all uids.
	 *
	 * @return the sets the
	 */
	@Override
	public Set<String> loadAllUids() {
		LOGGER.debug("Loading UIDs for bank : " + uidBankDao.getBankName());
		return uidBankDao.readAllUids();
	}

	/**
	 * Save uids.
	 *
	 * @param uids the uids
	 * @return true, if successful
	 */
	@Override
	public boolean saveUids(List<String> uids) {
		LOGGER.debug(new StringBuilder("Saving ").append(uids.size()).append(" UIDs for bank : ").append(uidBankDao.getBankName()).toString());
		return uidBankDao.createUids(uids);
	}

	/**
	 * Gets the count of unsed uids.
	 *
	 * @return the count of unsed uids
	 */
	@Override
	public int getCountOfUnsedUids() {
		LOGGER.debug("Reading unused UID count.");
		return uidBankDao.readUnsedUidCount();
	}

	/**
	 * Generate uids and fill bank.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean generateUidsAndFillBank() {
		int collisionCount = 0;
		int maxCollisionCount = (int) ((int) refillSize * collisionCountFactor);
		if (refillSize * collisionCountFactor > maxCollisionCount) {
			maxCollisionCount++;
		}
		float batchesFloat = refillSize / batchSize;
		int batches = (int) batchesFloat;
		if (batches == 0) {
			batches++;
		}
		Set<String> currentUniqueIds = loadAllUids();
		boolean archived = false;
		for (int batchCount = 0; batchCount < batches; batchCount++) {
			if (currentUniqueIds == null) {
				currentUniqueIds = new HashSet<String>();
			}
			BatchInsertDto batchInsertDto = createAndSave(currentUniqueIds, batchSize, collisionCount, maxCollisionCount, batchCount, archived);
			Set<String> newUids = batchInsertDto.getNewUids();
			currentUniqueIds.addAll(newUids);
			collisionCount = batchInsertDto.getCollisionCount();
			archived = batchInsertDto.isArchived();
			if (batchCount == (batches - 1) && batchesFloat > batches) {
				int lastBatchSize = refillSize - (batchCount * batchSize);
				batchInsertDto = createAndSave(currentUniqueIds, lastBatchSize, collisionCount, maxCollisionCount, batchCount, archived);
				currentUniqueIds.addAll(batchInsertDto.getNewUids());
				collisionCount = batchInsertDto.getCollisionCount();
				archived = batchInsertDto.isArchived();
			}
		}
		return true;
	}
	
	/**
	 * Creates the and save.
	 *
	 * @param currentUniqueIds the current unique ids
	 * @param batchSize the batch size
	 * @param collisionCount the collision count
	 * @param maxCollisionCount the max collision count
	 * @param batchCount the batch count
	 * @param archived the archived
	 * @return the batch insert dto
	 */
	private BatchInsertDto createAndSave(Set<String> currentUniqueIds, int batchSize, int collisionCount, int maxCollisionCount, int batchCount, boolean archived) {
		Set<String> newUids = new HashSet<>();
		for (int index = 0; index < batchSize; index++) {
			String uniqueId = RandomStringUtils.randomAlphanumeric(uidLength).toUpperCase();
			if (currentUniqueIds != null && currentUniqueIds.contains(uniqueId)) {
				if (!archived) {
					archived = true;
//					uidBankArchivalService.archive();
				}
				collisionCount++;
				if (collisionCount > maxCollisionCount) {
					String msg = new StrBuilder("Collisions exceeded ").append(maxCollisionCount).append(" in ").append(batchCount)
							.append(" attempts!").toString();
					LOGGER.warn(msg);
					break;
				}
				continue;
			}
			if (newUids.contains(uniqueId)) {
				continue;
			}
			newUids.add(uniqueId);
		}
		saveUids(new ArrayList<>(newUids));
		BatchInsertDto batchInsertDto = new BatchInsertDto(newUids, collisionCount, archived);
		return batchInsertDto;
	}
	
	/**
	 * The Class BatchInsertDto.
	 */
	private static class BatchInsertDto {
		
		/** The new uids. */
		Set<String> newUids = null;
		
		/** The collision count. */
		int collisionCount;
		
		/** The archived. */
		boolean archived;
		
		/**
		 * Instantiates a new batch insert dto.
		 *
		 * @param newUids the new uids
		 * @param collisionCount the collision count
		 * @param archived the archived
		 */
		BatchInsertDto(Set<String> newUids, int collisionCount, boolean archived) {
			this.newUids = newUids;
			this.collisionCount = collisionCount;
			this.archived = archived;
		}
		
		/**
		 * Gets the new uids.
		 *
		 * @return the new uids
		 */
		public Set<String> getNewUids() {
			return newUids;
		}
		
		/**
		 * Sets the new uids.
		 *
		 * @param newUids the new new uids
		 */
		public void setNewUids(Set<String> newUids) {
			this.newUids = newUids;
		}
		
		/**
		 * Gets the collision count.
		 *
		 * @return the collision count
		 */
		public int getCollisionCount() {
			return collisionCount;
		}
		
		/**
		 * Sets the collision count.
		 *
		 * @param collisionCount the new collision count
		 */
		public void setCollisionCount(int collisionCount) {
			this.collisionCount = collisionCount;
		}
		
		/**
		 * Checks if is archived.
		 *
		 * @return true, if is archived
		 */
		public boolean isArchived() {
			return archived;
		}
		
		/**
		 * Sets the archived.
		 *
		 * @param archived the new archived
		 */
		public void setArchived(boolean archived) {
			this.archived = archived;
		}
	}
}
