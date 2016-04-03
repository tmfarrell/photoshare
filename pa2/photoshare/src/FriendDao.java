/**
* A data access object (DAO) to handle Friends table
*
* @author Tim Farrell <tmf@bu.edu>
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

import photoshare.User; 


public class FriendDao {
	
	/* SQL Statements */ 
	private static final String GET_FRIENDS_STMT = "SELECT U.user_id, U.email, U.first_name, "
		+ "U.last_name, U.title, U.location FROM Friends F, Users U "
		+ "WHERE F.user_id =(SELECT user_id FROM Users WHERE email = ?) "
		+ "AND F.friend_id = U.user_id;";

	private static final String INSERT_FRIEND_STMT = "INSERT INTO Friends (user_id, friend_id) " 
		+ "VALUES ( (SELECT user_id FROM Users WHERE email = ?), "
		+ "(SELECT user_id FROM Users WHERE email = ?) );";

	/* Methods */ 
	//
	//Make friends
	//
	public void makeFriends(String user_email, String friend_email) { 
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(INSERT_FRIEND_STMT);	//prepare statement
			stmt.setString(1, user_email);						//set parameters
			stmt.setString(2, friend_email);
			stmt.executeUpdate();								//execute
			
			//close all 
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		
		} finally {
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
	//Creates new user with email and password
	//
	public List<User> getFriends(String user_email) {
		//declare db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		//declare list to store pids
		List<User> users = new ArrayList<User>();
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(GET_FRIENDS_STMT);	//prepare statement
			stmt.setString(1, user_email); 
			rs = stmt.executeQuery();						//execute
			
			User u; 
			//loop thru result set
			while (rs.next()) {
				u = new User(rs.getInt(1), rs.getString(2), rs.getString(3)); 
				u.setLast(rs.getString(4)); 
				u.setTitle(rs.getString(5)); 
				u.setLocation(rs.getString(6)); 
				users.add(u);	//add each to list
			}

			//close all
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;
			conn.close();
			conn = null;
			
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
