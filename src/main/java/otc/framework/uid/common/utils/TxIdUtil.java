/**
* Copyright (c) eTree Technologies
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the etree-commons.
* 
*  The etree-commons is free library: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The etree-commons is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with etree-commons project.  If not, see <https://www.gnu.org/licenses/>.
*
*/
package otc.framework.uid.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;

import otc.framework.uid.common.exception.UidException;

// TODO: Auto-generated Javadoc
/**
 * The Class TxIdUtil.
 */
public class TxIdUtil {
	
	/** The zones config. */
	private static Map<String, TxIdDto> zonesConfig;
	
	/** The zones counters. */
	private static Map<String, Integer> zonesCounters;
	
	/** The default uid token full length. */
	private static int defaultUidTokenFullLength = 10;
	
	/** The default uid token salt length. */
	private static int defaultUidTokenSaltLength = 4;
	
	/** The default max salt. */
	private static int defaultMaxSalt = 1000;

	/**
	 * Register zone.
	 *
	 * @param transcientUidDto the transcient uid dto
	 */
	public static void registerZone(TxIdDto transcientUidDto) {
		createOrResetZone(transcientUidDto, true);
	}

	/**
	 * Register zone.
	 *
	 * @param zoneName the zone name
	 * @param uidTokenFullLen the uid token full len
	 * @param uidTokenSaltLen the uid token salt len
	 * @param maxSalt the max salt
	 */
	public static void registerZone(String zoneName, int uidTokenFullLen, int uidTokenSaltLen, int maxSalt) {
		TxIdDto transcientUidDto = new TxIdDto();
		transcientUidDto.setZoneName(zoneName);
		transcientUidDto.setUidTokenFullLength(uidTokenFullLen);
		transcientUidDto.setUidTokenSaltLength(uidTokenSaltLen);
		transcientUidDto.setMaxSalt(maxSalt);
		registerZone(transcientUidDto);
	}

	/**
	 * De register zone.
	 *
	 * @param zoneName the zone name
	 */
	public static void deRegisterZone(String zoneName) {
		if (zonesConfig == null || !zonesConfig.containsKey(zoneName)) {
			return;
		}
		zonesConfig.remove(zoneName.toUpperCase());
	}

	/**
	 * Refresh zone.
	 *
	 * @param transcientUidDto the transcient uid dto
	 */
	public static void refreshZone(TxIdDto transcientUidDto) {
		createOrResetZone(transcientUidDto, false);
	}

	/**
	 * Creates the or reset zone.
	 *
	 * @param transcientUidDto the transcient uid dto
	 * @param isRegister the is register
	 */
	private static void createOrResetZone(TxIdDto transcientUidDto, boolean isRegister) {
		if (transcientUidDto == null) {
			throw new UidException("Zone config cannot be null!");
		}
		String zoneName = transcientUidDto.getZoneName();
		if (zoneName == null) {
			throw new UidException("Zone-name cannot be null!");
		}
		if (zonesConfig == null) {
			zonesConfig = new HashMap<>();
		} else if (isRegister && zonesConfig.containsKey(zoneName)) {
			throw new UidException("Transicent Uid Zone already registered!");
		}
		zonesConfig.put(zoneName.toUpperCase(), transcientUidDto);
	}

	/**
	 * Checks for zone.
	 *
	 * @param zoneName the zone name
	 * @return true, if successful
	 */
	public static boolean hasZone(String zoneName) {
		if (zonesConfig == null) {
			return false;
		}
		zoneName = zoneName.toUpperCase();
		return zonesConfig.containsKey(zoneName);
	}

	/**
	 * Creates the uid token.
	 *
	 * @param zoneName the zone name
	 * @return the string
	 */
	public static String createUidToken(String zoneName) {
		if (zoneName == null) {
			throw new UidException("Zone-name cannot be null!");
		}
		zoneName = zoneName.toUpperCase();
		if (zonesConfig == null || !zonesConfig.containsKey(zoneName)) {
			return null;
		}
		TxIdDto transcientUidDto = zonesConfig.get(zoneName);
		int uidTokenFullLength = transcientUidDto.getUidTokenFullLength();
		if (uidTokenFullLength == 0) {
			uidTokenFullLength = defaultUidTokenFullLength;
		}
		int uidTokenSaltLength = transcientUidDto.getUidTokenSaltLength();
		if (uidTokenSaltLength == 0) {
			uidTokenSaltLength = defaultUidTokenSaltLength;
		}
		int maxSalt = transcientUidDto.getMaxSalt();
		if (maxSalt == 0) {
			maxSalt = defaultMaxSalt;
		}
		if (zonesCounters == null) {
			synchronized (TxIdUtil.class) {
				if (zonesCounters == null) {
					zonesCounters = new HashMap<>();
				}
			}
		}
		Integer nextValue = 0;
		synchronized (zonesCounters) {
			nextValue = zonesCounters.get(zoneName);
			if (nextValue == null || nextValue == maxSalt) {
				nextValue = 0;
			} else {
				nextValue++;
			}
			zonesCounters.put(zoneName, nextValue);
		}
		int padLen = String.valueOf(nextValue).length();
		int uidLen = uidTokenFullLength - padLen;
		String uid = RandomStringUtils.randomAlphanumeric(uidLen);
		String uidToken = uid + nextValue;
		return uidToken;
	}

	/**
	 * Creates the uid token with millis.
	 *
	 * @param len the len
	 * @return the string
	 */
	public static String createUidTokenWithMillis(int len) {
		if (len < 14) {
			throw new UidException("Cannot create UidToken! Length should be greater than 14.");
		}
		long nano = Math.abs(System.currentTimeMillis());
		String transactionIdentifier = "-" + nano;
		transactionIdentifier = RandomStringUtils.randomAlphanumeric(len - transactionIdentifier.length())
				+ transactionIdentifier;
		return transactionIdentifier;
	}

	/**
	 * The Class TxIdDto.
	 */
	public static class TxIdDto {
		
		/** The zone name. */
		private String zoneName;
		
		/** The uid token full length. */
		private int uidTokenFullLength;
		
		/** The uid token salt length. */
		private int uidTokenSaltLength;
		
		/** The max salt. */
		private int maxSalt;

		/**
		 * Gets the zone name.
		 *
		 * @return the zone name
		 */
		public String getZoneName() {
			return zoneName;
		}

		/**
		 * Sets the zone name.
		 *
		 * @param zoneName the new zone name
		 */
		public void setZoneName(String zoneName) {
			this.zoneName = zoneName;
		}

		/**
		 * Gets the uid token full length.
		 *
		 * @return the uid token full length
		 */
		public int getUidTokenFullLength() {
			return uidTokenFullLength;
		}

		/**
		 * Sets the uid token full length.
		 *
		 * @param uidTokenFullLength the new uid token full length
		 */
		public void setUidTokenFullLength(int uidTokenFullLength) {
			this.uidTokenFullLength = uidTokenFullLength;
		}

		/**
		 * Gets the uid token salt length.
		 *
		 * @return the uid token salt length
		 */
		public int getUidTokenSaltLength() {
			return uidTokenSaltLength;
		}

		/**
		 * Sets the uid token salt length.
		 *
		 * @param uidTokenSaltLength the new uid token salt length
		 */
		public void setUidTokenSaltLength(int uidTokenSaltLength) {
			this.uidTokenSaltLength = uidTokenSaltLength;
		}

		/**
		 * Gets the max salt.
		 *
		 * @return the max salt
		 */
		public int getMaxSalt() {
			return maxSalt;
		}

		/**
		 * Sets the max salt.
		 *
		 * @param maxSalt the new max salt
		 */
		public void setMaxSalt(int maxSalt) {
			this.maxSalt = maxSalt;
		}
	}
}
