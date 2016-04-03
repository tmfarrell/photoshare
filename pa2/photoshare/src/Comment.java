/**
* Comment class. 
*
* @author Tim Farrell <tmf@bu.edu>
* 
* PA2, CS660, BU 
* 150422
*/

package photoshare;

import java.util.Date; 


public class Comment { 
	private int user_id  = 0; 			//user whom commented
	private String user_name  = ""; 
	private int comment_id = 0;
	private String comment = "";
	private int picture_id = 0;
	private Date date_commented = null; 
	
	public Comment(int user_id, int picture_id, String comment) { 
		this.user_id = user_id;
		this.picture_id = picture_id; 
		this.comment = comment;  
		this.date_commented = new Date();  
	}
	
	public int getPid() {
		return picture_id; 
	}
  
	public int getUserId() {
		return user_id; 
	}
	
	public String getComment() { 
		return comment; 
	}
  
	public Date getDate() {
		return date_commented; 
	}
	
	public void setId() { 
		this.comment_id = comment_id; 
	}
	
	public void setDate(Date date_commented) {
		this.date_commented = date_commented; 
	}
	
	public void setUserName(String user_name) { 
		this.user_name = user_name; 
	}
	
	public String getUserName() { 
		return this.user_name; 
	}
}
