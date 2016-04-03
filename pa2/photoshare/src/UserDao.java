/**
* A data access object (DAO) to handle the Users table
*
* @original_author G. Zervas <cs460tf@bu.edu>
* @adaption_author Tim Farrell <tmf@bu.edu>
* 
* PA2, CS660, BU
* 150422
*/
package photoshare;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class UserDao {
	
	/* SQL Statements */ 
	private static final String CHECK_EMAIL_STMT = "SELECT "
		+ "COUNT(*) FROM Users WHERE email = ?;";

	private static final String NEW_USER_STMT = "INSERT INTO " 
		+ "Users (email, password, first_name, last_name, title, location, hometown) "
		+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
	
	private static final String GET_USER_STMT = "SELECT * FROM Users WHERE email = ?;"; 
	
	private static final String RATING_UPDATE_STMT = "UPDATE Users U SET rating = "
		+ "(SELECT COUNT(*) FROM Comments C WHERE C.user_id = U.user_id) + "
		+ "(SELECT COUNT(*) FROM Pictures P, Albums A"
		+ " WHERE P.album_id = A.album_id AND A.user_id = U.user_id) " 
		+ "WHERE user_id = ?;"; 
	
	private static final String GET_RATING_STMT = "SELECT rating FROM Users WHERE user_id = ?;"; 
	
	private static final String GET_BY_RATING = "SELECT * FROM Users ORDER BY rating DESC;";


	/* Methods */ 
	//
	//Adds new user to Users table in db with email, password, first_name and location fields
	//
	public boolean create(String email, String password, String first_name, String last_name, String title, 
						String location, String hometown) {
		//declare db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			
			//check if email already in use
			stmt = conn.prepareStatement(CHECK_EMAIL_STMT);	
			stmt.setString(1, email);						
			rs = stmt.executeQuery();	
			
	        if (!rs.next()) return false;				
			
			int result = rs.getInt(1);		//if there are matching emails
			if (result > 0)	return false; 	//return false
			
			
			try { stmt.close(); }			//if not, reset statement
			catch (Exception e) { }
			stmt = conn.prepareStatement(NEW_USER_STMT);	//prep new user stmt
			stmt.setString(1, email);						//set parameters
			stmt.setString(2, password);
			stmt.setString(3, first_name); 
			stmt.setString(4, last_name); 
			stmt.setString(5, title); 
			stmt.setString(6, location);
			stmt.setString(7, hometown);  
			
			stmt.executeUpdate();							//execute
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		} finally {
			if (rs != null) {
				try { rs.close(); }
				catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); }
				catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); }
				catch (SQLException e) { ; }
				conn = null;
			}
		}
	}
	
	//
	//Returns User with email
	//
	public User getUser(String email) {
		//declare db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		User u = null; 
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			
			//prep stmt and execute
			stmt = conn.prepareStatement(GET_USER_STMT);	
			stmt.setString(1, email);						
			rs = stmt.executeQuery();	
			
	        if (rs.next()) {
				u = new User(rs.getInt(1), email, rs.getString(5), rs.getString(8), rs.getString(9)); 
				u.setLast(rs.getString(6)); 
				u.setHometown(rs.getString(7)); 
			}  	
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		} finally {
			if (rs != null) {
				try { rs.close(); }
				catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); }
				catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); }
				catch (SQLException e) { ; }
				conn = null;
			}
		}
		u.getRating(); 
		return u; 
	} 
	
	//
	//Updates user's rating and then returns it
	//
	public int getRating(int user_id) {
		//declare db access helpers
		PreparedStatement stmt = null; 
		Connection conn = null;
		ResultSet rs = null;
		int rating = 0; 
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			
			//prep update stmt and execute
			stmt = conn.prepareStatement(RATING_UPDATE_STMT);	
			stmt.setInt(1, user_id);						
			stmt.executeUpdate();	
			
			//prep get stmt and execute
			stmt = conn.prepareStatement(GET_RATING_STMT);	
			stmt.setInt(1, user_id);						
			rs = stmt.executeQuery();	
			
			if(rs.next())				//return result
				rating = rs.getInt(1); 
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		} finally {
			if (rs != null) {
				try { rs.close(); }
				catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); }
				catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); }
				catch (SQLException e) { ; }
				conn = null;
			}
		} 
		
		return rating; 
	} 
	
	//
	//Updates user's rating and then returns it
	//
	public List<User> getByRating() {
		//declare db access helpers
		PreparedStatement stmt = null; 
		Connection conn = null;
		List<User> users = new ArrayList<User>(); 
		ResultSet rs = null;
		int rating = 0; 
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			
			//prep update stmt and execute
			stmt = conn.prepareStatement(GET_BY_RATING);	
						
			rs = stmt.executeQuery();	
			
			while(rs.next())	 { 			//return result
				users.add(new User(rs.getInt(1), rs.getString(2), rs.getString(5), 
					rs.getString(6), rs.getString(8), rs.getString(9))); 
			} 
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		} finally {
			if (rs != null) {
				try { rs.close(); }
				catch (SQLException e) { ; }
				rs = null;
			}
			if (stmt != null) {
				try { stmt.close(); }
				catch (SQLException e) { ; }
				stmt = null;
			}
			if (conn != null) {
				try { conn.close(); }
				catch (SQLException e) { ; }
				conn = null;
			}
		} 
		
		return users; 
	}
	
}
