package otc.framework.uid.core.bank.biz.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import otc.framework.uid.common.bank.biz.AbstractUidBankArchivalService;
import otc.framework.uid.common.dto.UidBankDto;

// TODO: Auto-generated Javadoc
/**
 * The Class UidBankArchivalServiceImpl.
 */
public class UidBankArchivalServiceImpl extends AbstractUidBankArchivalService {

	/** The logger. */
	private Logger LOGGER = LoggerFactory.getLogger(UidBankArchivalServiceImpl.class);
	
	/**
	 * Archive.
	 *
	 * @return the list
	 */
	@Override
	public List<String> archive() {
		LOGGER.warn("Dormant days for UidBankArchival not set!");
		if (dormantDays == null) {
			return null;
		}
		List<UidBankDto> uniqueIdDtos = uidBankDao.readAllOlderThan(dormantDays);
		if (uniqueIdDtos == null || uniqueIdDtos.isEmpty()) {
			return null;
		}
		Timestamp nowMinusDormantDays = new Timestamp(System.currentTimeMillis() - CommonsSupportUtils.convertDaysToMilis(dormantDays));
		List<String> uidsForArchive = null;
		for (UidBankDto uidBankDto : uniqueIdDtos) {
			if (uidsForArchive == null) {
				uidsForArchive = new ArrayList<>();
			}
			Timestamp endOfLifeDate = uidBankDto.getEndOfLifeDate();
			if (endOfLifeDate == null) {
				uidsForArchive.add(uidBankDto.getUid());
			} else if (endOfLifeDate.before(nowMinusDormantDays)) {
				uidsForArchive.add(uidBankDto.getUid());
			}
		}
		List<String> uids = uidBankArchivalDao.createArchive(uidsForArchive);
		return uids;
	}
}
