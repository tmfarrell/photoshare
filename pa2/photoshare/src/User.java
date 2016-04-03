/**
* User class. 
*
* @original_author G. Zervas <cs460tf@bu.edu>
* @adaption_author Tim Farrell <tmf@bu.edu>
* 
* PA2, CS660, BU 
* 150422
*/

package photoshare;

import javax.servlet.http.HttpServletRequest;

import photoshare.UserDao; 

import java.util.List;


public class User {
	private int user_id = 0; 
	private String email = "";
	private String password1 = "";
	private String password2 = "";
	private String first = ""; 
	private String last = ""; 
	private String hometown = ""; 
	private String location = ""; 
	private String title = ""; 
	private int rating = 0; 
	
	public User(int user_id, String email, String first) { 
		this.user_id = user_id; 
		this.email = email; 
		this.first = first; 
		this.updateRating(); 
	}
	
	public User(int user_id, String email, String first, String location) { 
		this(user_id, email, first); 
		this.location = location; 
	}
	
	public User(int user_id, String email, String first, String location, String title) {
		this(user_id, email, first, location); 
		this.title = title; 
	}
	
	public User(int user_id, String email, String first, String last, String location, String title) {
		this(user_id, email, first, location, title); 
		this.last = last; 
	}

	public String saySomething() {
		System.out.println("Hello!");
		return "Test";
	}
	
	public int getId() { 
		return user_id; 
	}
  
	public String getEmail() {
		return email;
	}

	public String getPassword1() {
		return password1;
	}

	public String getPassword2() {
		return password2;
	}
  
	public String getFirst() {
		return first;
	}
  
	public String getLast() {
		return last;
	}
  
	public String getHometown() {
		return hometown;
	}
  
	public String getLocation() {
		return location;
	}
  
	public String getTitle() {
		return title;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
  
	public void setFirst(String first) {
		this.first = first;
	}
  
	public void setLast(String last) {
		this.last = last;
	}
  
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
  
	public void setLocation(String location) {
		this.location = location;
	}
  
	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getRating() {  
		return this.rating; 
	}
	
	public void updateRating() { 
		UserDao userDao = new UserDao(); 
		this.rating = userDao.getRating(this.user_id);
	}
  
}
