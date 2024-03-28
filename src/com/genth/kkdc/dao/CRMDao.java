/**
 * 
 */
package com.genth.kkdc.dao;

import java.util.List;
import java.util.Map;

import com.genth.kkdc.domain.CRMCleanup;


/**
 * @author Thanompong.W
 *
 */
public interface CRMDao {
	
	/**
	* Retrieves All pending case
	* 
	* @return list of CRMCleanup
	*/
	public List<CRMCleanup> getCleanupList();
	
	/**
	* Clean assign case as pending by communication id.
	* 
	* @param fileName String
	*/
	public void cleanup(String fileName);
	
	/**
	* Clean assign case as pending by list of communication id.
	* 
	* @param list of String
	*/
	public void cleanup(List<String> list);
	
	/**
	* Populate unsuccess data and insert into TB_T_IMPORT.
	* 
	* @param bankKeyLenght Integer 16 : credit card.
	* @param unsuccessDate String 
	* @param list of filingDetail 
	* 
	* @return list of SummaryDetail
	*/
	 
	/**
	* Get unsuccess file from DD_REPORT.
	* 
	* @param bankKeyLenght Integer 16 : credit card.
	* @param unsuccessDate String 
	* 
	* @return list of FilingDetail
	*/
	 

	public void populateUnsuccessData();
	 
	
	/**
	* Get Agent code data.
	* 
	* @return list of String
	*/
	public List<String> getAgenCode();
	
	public Map<String, String> getPolicyInfo(String policyNo);

}
