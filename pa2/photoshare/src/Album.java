/**
* Album class. 
*
* @author Tim Farrell <tmf@bu.edu>
* 
* PA2, CS660, BU 
* 150422
*/

package photoshare;

import java.util.Date; 


public class Album {
	private int album_id = 0; 
	private int user_id  = 0; 
	private String album_name = ""; 
	private Date date_created = null; 
	
	public Album(int user_id) { 
		this.user_id = user_id;
		this.date_created = new Date();  
	}
	
	public Album(int user_id, String album_name) { 
		this(user_id);  
		this.album_name = album_name; 
	}
	
	public Album(int album_id, int user_id, String album_name) { 
		this(user_id, album_name); 
		this.album_id = album_id; 
	}
	
	public Album(int album_id, int user_id, String album_name, Date date_created) { 
		this.user_id = user_id; 
		this.album_name = album_name;  
		this.album_id = album_id; 
		this.date_created = date_created; 
	}
	
	public int getAlbumId() {
		return album_id; 
	}
  
	public int getUserId() {
		return user_id; 
	}
  
	public String getName() {
		return album_name;
	}
  
	public Date getDate() {
		return date_created; 
	}
	
	public void setAlbumId(int album_id) { 
		this.album_id = album_id; 
	}

	public void setName(String name) {
		this.album_name = name;
	}
}
