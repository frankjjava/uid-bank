package otc.framework.uid.core.bank.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import otc.framework.uid.common.bank.dao.AbstractUidBankDao;
import otc.framework.uid.common.bank.dao.UidBankArchivalDao;
import otc.framework.uid.common.bank.dao.UidBankDao;

// TODO: Auto-generated Javadoc
/**
 * The Class UidBankArchivalDaoImpl.
 */
public class UidBankArchivalDaoImpl extends AbstractUidBankDao implements UidBankArchivalDao {

	/**
	 * Creates the archive.
	 *
	 * @param uidBankDtosForArchive the uid bank dtos for archive
	 * @return the list
	 */
	@Override
	public List<String> createArchive(List<String> uidBankDtosForArchive) {
		Map<String, Object>[] paramsArray = new HashMap[uidBankDtosForArchive.size()];
		List<String> uids = null;
		for (int idx = 0; idx < uidBankDtosForArchive.size(); idx++) {
			if (uids == null) {
				uids = new ArrayList<>();
			}
			String uid = uidBankDtosForArchive.get(idx);
			Map<String, Object> params = new HashMap<>();
			paramsArray[idx] = params;
			uids.add(uid);
			params.put(UidBankDao.COLUMN_NAME_UID, uid);
			params.put(COLUMN_NAME_STATUS, uid);
			params.put(COLUMN_CREATED_TIMESTAMP, uid);
			params.put(UidBankDao.COLUMN_NAME_CONSUMED_DATE, uid);
			params.put(UidBankDao.COLUMN_NAME_END_OF_LIFE_DATE, uid);
		}
		int[] count = executeBatchInsert(bankName, paramsArray);	
		return uids;
	}
}
