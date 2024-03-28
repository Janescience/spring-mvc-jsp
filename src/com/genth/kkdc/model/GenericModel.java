/**
 * 
 */
package com.genth.kkdc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A custom POJO that will be automatically converted to JSON format. 
 * <p>
 * We can use this to send generic messages to our JqGrid, whether a request is successful or not.
 * Of course, you will use plain JavaScript to parse the JSON response. 
 * </p>
 * @author Thanompong.W
*/
public class GenericModel {
	 
	/**
	* true if successful. 
	*/
	private Boolean success;
	
	private List<Object[]> obj; 
	 
	/**
	* Any custom message, i.e, 'Your request has been processed successfully!'
	*/
	private List<String> message;
	 
	public GenericModel() {
		this.message = new ArrayList<String>();
	}

	/**
	 * @return the success
	 */
	public Boolean getSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(Boolean success) {
		this.success = success;
	}

	/**
	 * @return the message
	 */
	public List<String> getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(List<String> message) {
		this.message = message;
	}

	public List<Object[]> getObj() {
		return obj;
	}

	public void setObj(List<Object[]> obj) {
		this.obj = obj;
	}

}
