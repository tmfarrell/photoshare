/**
* A data access object (DAO) to handle comment objects
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
import java.sql.Date; 

import java.util.ArrayList;
import java.util.List;

import photoshare.Comment; 


public class CommentDao {
  
	/* SQL Statements */ 
	private static final String SAVE_COMMENT_STMT = "INSERT INTO "
		+ "Comments (user_id, picture_id, comment, date_commented) " 
		+ "VALUES (?, ?, ?, ?);";

	private static final String ALL_COMMENTS_STMT = "SELECT "
		+ "C.user_id, C.comment, C.date_commented, U.first_name, U.last_name "
		+ "FROM Comments C, Users U WHERE C.picture_id = ? AND U.user_id = C.user_id;";
	
	private static final String DELETE_STMT = "DELETE FROM "
		+ "Comments WHERE picture_id = ?;";


	/* Methods */ 
	//
	//Get all comments of picture 
	//
	public List<Comment> load(int picture_id) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Comment> comments = new ArrayList<Comment>(); 
		Comment comment; 
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_COMMENTS_STMT);	//prepare statement
			stmt.setInt(1, picture_id);							//set parameter
			rs = stmt.executeQuery();							//execute
			
			if (rs.next()) {
				comment = new Comment(rs.getInt(1), picture_id, rs.getString(2)); 
				comment.setDate(rs.getDate(3)); 
				comment.setUserName(rs.getString(4) + rs.getString(5)); 
				comments.add(comment); 
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

		return comments; 
	}
	
	//
	//Save comment
	//
	public void save(Comment comment) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		java.sql.Date date = new java.sql.Date(comment.getDate().getTime()); //prep date
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_COMMENT_STMT);	//prepare statement
			stmt.setInt(1, comment.getUserId());				//set parameters
			stmt.setInt(2, comment.getPid()); 
			stmt.setString(3, comment.getComment());
			stmt.setDate(4, date);
			
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
	//Delete comment
	//
	public void delete(int picture_id) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_STMT);	//prepare stmt
			stmt.setInt(1, picture_id);					//set parameters 
			
			stmt.executeUpdate();						//execute
			
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

}
