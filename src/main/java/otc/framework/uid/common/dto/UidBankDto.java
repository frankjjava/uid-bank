/**
* Copyright © 2022 OTC Framework.
*
* @author  Franklin J Abel (frank.a.otc@gmail.com)
* @version 1.0
* @since   2016-01-15 
*/
package otc.framework.uid.common.dto;

import java.sql.Timestamp;

// TODO: Auto-generated Javadoc
/**
 * The Class UidBankDto.
 */
public class UidBankDto {

	/** The bank name. */
	private UidDto bankName;
	
	/** The uid. */
	private String uid;
	
	/** The status. */
	private String status;
	
	/** The created date. */
	private Timestamp createdDate;
	
	/** The consumed date. */
	private Timestamp consumedDate;
	
	/** The end of life date. */
	private Timestamp endOfLifeDate;
	
	/**
	 * Gets the bank name.
	 *
	 * @return the bank name
	 */
	public UidDto getBankName() {
		return bankName;
	}
	
	/**
	 * Sets the bank name.
	 *
	 * @param bankName the new bank name
	 */
	public void setBankName(UidDto bankName) {
		this.bankName = bankName;
	}
	
	/**
	 * Gets the uid.
	 *
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}
	
	/**
	 * Sets the uid.
	 *
	 * @param uid the new uid
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * Gets the created date.
	 *
	 * @return the created date
	 */
	public Timestamp getCreatedDate() {
		return createdDate;
	}
	
	/**
	 * Sets the created date.
	 *
	 * @param createdDate the new created date
	 */
	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
	
	/**
	 * Gets the consumed date.
	 *
	 * @return the consumed date
	 */
	public Timestamp getConsumedDate() {
		return consumedDate;
	}
	
	/**
	 * Sets the consumed date.
	 *
	 * @param consumedDate the new consumed date
	 */
	public void setConsumedDate(Timestamp consumedDate) {
		this.consumedDate = consumedDate;
	}
	
	/**
	 * Gets the end of life date.
	 *
	 * @return the end of life date
	 */
	public Timestamp getEndOfLifeDate() {
		return endOfLifeDate;
	}
	
	/**
	 * Sets the end of life date.
	 *
	 * @param endOfLifeDate the new end of life date
	 */
	public void setEndOfLifeDate(Timestamp endOfLifeDate) {
		this.endOfLifeDate = endOfLifeDate;
	}
}
