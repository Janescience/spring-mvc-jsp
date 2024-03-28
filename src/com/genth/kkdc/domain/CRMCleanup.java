package com.genth.kkdc.domain;

import java.io.Serializable;

/**
 * Entity implementation class for Entity: CRMCleanup
 *
 */
public class CRMCleanup implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String fileName;
	private String lastUploadDate;
	private String uploadedBy;
	private String status;

	public CRMCleanup() {
		super();
	}  
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the lastUploadDate
	 */
	public String getLastUploadDate() {
		return lastUploadDate;
	}

	/**
	 * @param lastUploadDate the lastUploadDate to set
	 */
	public void setLastUploadDate(String lastUploadDate) {
		this.lastUploadDate = lastUploadDate;
	}

	/**
	 * @return the uploadedBy
	 */
	public String getUploadedBy() {
		return uploadedBy;
	}

	/**
	 * @param uploadedBy the uploadedBy to set
	 */
	public void setUploadedBy(String uploadedBy) {
		this.uploadedBy = uploadedBy;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
   
}
