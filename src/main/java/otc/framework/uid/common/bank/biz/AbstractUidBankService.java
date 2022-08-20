/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.bank.biz;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import otc.framework.uid.common.UidConstants;
import otc.framework.uid.common.bank.dao.UidBankDao;

// TODO: Auto-generated Javadoc
/**
 * Instantiates a new abstract uid bank service.
 */
@Data
public abstract class AbstractUidBankService implements UidBankService {

	/** The logger. */
	private Logger LOGGER = LoggerFactory.getLogger(AbstractUidBankService.class);

	/** The default collision count factor. */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected float defaultCollisionCountFactor = 0.1F;
	
	/** The default uid length. */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected int defaultUidLength = 10;
	
	/** The default refill size. */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected int defaultRefillSize = 100000;
	
	/** The default lower water mark. */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected int defaultLowerWaterMark = 99000;
	
	/** The default batch size. */
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	protected int defaultBatchSize = 100000;
	
	/** The collision count factor. */
	protected Float collisionCountFactor;
	
	/** The uid length. */
	@Getter(AccessLevel.NONE)
	protected Integer uidLength;
	
	/** The refill size. */
	@Getter(AccessLevel.NONE)
	protected Integer refillSize;
	
	/** The lower water mark. */
	@Getter(AccessLevel.NONE)
	protected Integer lowerWaterMark;
	
	/** The batch size. */
	@Getter(AccessLevel.NONE)
	protected Integer batchSize;
	
	/** The uid bank dao. */
	protected UidBankDao uidBankDao;
	
	/** The uid bank archival service. */
	protected UidBankArchivalService uidBankArchivalService;
	
	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	public String getBankName() {
		return uidBankDao.getBankName();
	}
	
	/**
	 * Gets the uid length.
	 *
	 * @return the uid length
	 */
	public Integer getUidLength() {
		if (lowerWaterMark == null) {
			init();
		}
		return uidLength;
	}
	
	/**
	 * Gets the refill size.
	 *
	 * @return the refill size
	 */
	public Integer getRefillSize() {
		if (lowerWaterMark == null) {
			init();
		}
		return refillSize;
	}
	
	/**
	 * Gets the lower water mark.
	 *
	 * @return the lower water mark
	 */
	public Integer getLowerWaterMark() {
		if (lowerWaterMark == null) {
			init();
		}
		return lowerWaterMark;
	}
	
	/**
	 * Gets the batch size.
	 *
	 * @return the batch size
	 */
	public Integer getBatchSize() {
		if (lowerWaterMark == null) {
			init();
		}
		return batchSize;
	}
	
	/**
	 * Inits the.
	 */
	@Override
	public void init() {
		if (collisionCountFactor == null || uidLength == null || refillSize == null || lowerWaterMark == null || batchSize == null) {
			List<String> ignoreCriteriaFields = new ArrayList<>();
			ignoreCriteriaFields.add(ConfiguratorConfigIdDto.QUERYPARAM_NAME);
			VtConfigurationDto vtConfigurationDto = configuratorServiceClient.fetchConfiguration("UID_BANK", null, null, null, "SUPER_PNRS", 
					UidConstants.KEY_DORMANT_DAYS, null, ignoreCriteriaFields);
			if (vtConfigurationDto != null) {
				collisionCountFactor = vtConfigurationDto.getFloat(UidConstants.KEY_BANK_COLLISION_COUNT_FACTOR);
				uidLength = vtConfigurationDto.getInteger(UidConstants.KEY_BANK_UID_LENGTH);
				refillSize = vtConfigurationDto.getInteger(UidConstants.KEY_BANK_REFILL_SIZE);
				lowerWaterMark = vtConfigurationDto.getInteger(UidConstants.KEY_BANK_LOWER_WATERMARK);
				batchSize = vtConfigurationDto.getInteger(UidConstants.KEY_BANK_BATCH_SIZE);
			}
			if (collisionCountFactor == null) {
				collisionCountFactor = defaultCollisionCountFactor;
				LOGGER.info("COLLISION_COUNT_FACTOR not configured! Using default value : " + defaultCollisionCountFactor);
			}
			if (uidLength == null) {
				uidLength = defaultUidLength;
				LOGGER.info("UID_LENGTH not configured! Using default value : " + defaultUidLength);
			}
			if (refillSize == null) {
				refillSize = defaultRefillSize;
				LOGGER.info("REFILL_SIZE not configured! Using default value : " + defaultRefillSize);
			}
			if (lowerWaterMark == null) {
				lowerWaterMark = defaultLowerWaterMark;
				LOGGER.info("LOWER_WATERMARK not configured! Using default value : " + defaultLowerWaterMark);
			}
			if (batchSize == null) {
				batchSize = defaultBatchSize;
				LOGGER.info("BATCH_SIZE not configured! Using default value : " + defaultBatchSize);
			}
		}
	}
}
