/**
*  Tag class. 
*
* @author Tim Farrell <tmf@bu.edu>
* 
* PA2, CS660, BU 
* 150422
*/

package photoshare;


public class Tag { 
	private int picture_id = 0;
	private String tag = ""; 
	
	public Tag(String tag) { 
		this.tag = tag; 
	}
	
	public Tag(int picture_id, String tag) { 
		this.picture_id = picture_id; 
		this.tag = tag;   
	}
	
	public int getPid() {
		return picture_id; 
	}
  
	public String getTag() {
		return tag; 
	}
}
