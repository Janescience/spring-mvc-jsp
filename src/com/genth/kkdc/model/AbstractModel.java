/**
 * 
 */
package com.genth.kkdc.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Thanompong.W
 * @param <E>
 *
 */
public abstract class AbstractModel<E> {
	/**
	* Current page of the query
	*/
	protected Integer page;
	 
	/**
	* Total pages for the query
	*/
	protected Integer total;
	 
	/**
	* Total number of records for the query
	*/
	protected Integer records;
	 
	/**
	* An array that contains the actual objects
	*/
	protected List<E> rows;
	
	/**
	* Maximum records per pages for the query
	*/
	private Integer max;
	
	/**
	* UserData contains the current page and row inside.
	*/
	private UserData userdata;

	/**
	 * @return the page
	 */
	public Integer getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}

	/**
	 * @return the records
	 */
	public Integer getRecords() {
		return records;
	}

	/**
	 * @param records the records to set
	 */
	public void setRecords(Integer records) {
		this.records = records;
	}

	/**
	 * @return the max
	 */
	public Integer getMax() {
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(Integer max) {
		this.max = max;
	}

	/**
	 * @return the rows
	 */
	public List<E> getRows() {
		return rows;
	}

	/**
	 * @param rows the rows to set
	 */
	public void setRows(List<E> rows) {
		this.rows = rows;
	}
	
	/**
	 * @return the userData
	 */
	public UserData getUserdata() {
		return userdata;
	}

	/**
	 * @param userData the userData to set
	 */
	public void setUserdata(UserData userdata) {
		this.userdata = userdata;
	}

	public class UserData implements Serializable {
		
		private static final long serialVersionUID = 1L;
		private Integer page;
		private String selectedId;
		
		public UserData(Integer page, String selectedId) {
			this.page = page;
			this.selectedId = selectedId;
		}

		/**
		 * @return the page
		 */
		public Integer getPage() {
			return page;
		}

		/**
		 * @param page the page to set
		 */
		public void setPage(Integer page) {
			this.page = page;
		}

		/**
		 * @return the selectedId
		 */
		public String getSelectedId() {
			return selectedId;
		}

		/**
		 * @param selectedId the selectedId to set
		 */
		public void setSelectedId(String selectedId) {
			this.selectedId = selectedId;
		}
	}
}
