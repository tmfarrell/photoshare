/**
* A data access object (DAO) to handle tags
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

import java.lang.Integer; 

import photoshare.Tag; 
import photoshare.Picture; 


public class TagDao {
  
	/* SQL Statements */ 
	private static final String CHECK_TAG_STMT = "SELECT "
		+ "COUNT(*) FROM Tags WHERE tag = ?;";
	
	private static final String SAVE_TAG_STMT = "INSERT INTO "
		+ "Tags (tag, picture_id) VALUES (?, ?);";

	private static final String ALL_TAGS_PIC = "SELECT tag FROM Tags WHERE picture_id = ?;";
	
	private static final String ALL_TAGS_USER = "SELECT T.tag FROM Tags T, Pictures P, Albums A "
		+ "WHERE A.user_id = ? AND P.album_id = A.album_id AND P.picture_id = T.picture_id;"; 
	
	private static final String ALL_TAGGED_STMT = "SELECT "
		+ "P.picture_id, A.album_name, P.caption, P.likes FROM Tags T, Pictures P, Albums A "
		+ "WHERE T.tag = ? AND T.picture_id = P.picture_id AND P.album_id = A.album_id;";
	
	private static final String DELETE_STMT = "DELETE FROM Tags WHERE tag = ?;";
	
	private static final String GET_MOST_POP = "SELECT tag FROM Tags "
		+ "WHERE COUNT(picture_id) = (SELECT MAX(COUNT(picture_id)) From Tags GROUPBY () )"; 


	/* Methods */ 
	//
	//Get all tags of picture 
	//
	public List<Tag> loadTagsUser(int user_id) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Tag> tags = new ArrayList<Tag>(); 
		Tag tag; 
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_TAGS_USER);	//prepare statement
			stmt.setInt(1, user_id);							//set parameter
			rs = stmt.executeQuery();							//execute
			
			while (rs.next()) {
				tag = new Tag(rs.getString(1)); 
				tags.add(tag); 
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

		return tags; 
	}
	
	//
	//Get all tags of picture 
	//
	public List<Tag> loadTags(int picture_id) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Tag> tags = new ArrayList<Tag>(); 
		Tag tag; 
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_TAGS_PIC);	//prepare statement
			stmt.setInt(1, picture_id);							//set parameter
			rs = stmt.executeQuery();							//execute
			
			while (rs.next()) {
				tag = new Tag(picture_id, rs.getString(1)); 
				tags.add(tag); 
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

		return tags; 
	}
	
	//
	//Get all tags of picture 
	//
	public List<Picture> loadPictures(String tag) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Picture> pics = new ArrayList<Picture>(); 
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_TAGGED_STMT);	//prepare statement
			stmt.setString(1, tag);							//set parameter
			rs = stmt.executeQuery();						//execute
			
			while (rs.next()) 
				pics.add(new Picture(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4))); 

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

		return pics; 
	}
	
	//
	//Get most popular tag 
	//
	/* public Tag getMostPopular() {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		List<Picture> pics = new ArrayList<Picture>(); 
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_TAGGED_STMT);	//prepare statement
			stmt.setString(1, tag);							//set parameter
			rs = stmt.executeQuery();						//execute
			
			while (rs.next()) 
				pics.add(new Picture(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4))); 

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

		return pics; 
	}*/ 
	
	//
	//Save tag
	//
	public boolean save(Tag tag) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null; 
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			//check if email already in use
			stmt = conn.prepareStatement(CHECK_TAG_STMT);	
			stmt.setString(1, tag.getTag());		 				
			rs = stmt.executeQuery();	
			
	        if (!rs.next()) return false;				
			
			int result = rs.getInt(1);		//if there are matching emails
			if (result > 0)	return false; 	//return false
			
			
			stmt = conn.prepareStatement(SAVE_TAG_STMT);	//prepare statement
			stmt.setString(1, tag.getTag());			//set parameters
			stmt.setInt(2, tag.getPid()); 
			
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
		return true; 
	}
	
	//
	//Delete tag
	//
	public void delete(String tag) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_STMT);	//prepare caption update
			stmt.setString(1, tag);					//set parameters 
			
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
}
