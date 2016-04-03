/**
* A data access object (DAO) to handle album objects
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
import java.sql.Date;  

import photoshare.Album; 


public class AlbumDao {
  
	/* SQL Statements */ 
	private static final String LOAD_ALBUM_STMT = "SELECT * FROM Albums "
		+ "WHERE user_id = ? AND album_name = ?;";
	
	private static final String LOAD_ALL_ALBUMS = "SELECT * FROM Albums "
		+ "WHERE user_id = ?;"; 
	
	private static final String CHECK_NAME_STMT = "SELECT COUNT(*) "
		+ "FROM Albums WHERE album_name = ?;"; 
	
	private static final String INSERT_ALBUM_STMT = "INSERT INTO Albums "
		+ "(user_id, album_name, date_created) VALUES (?, ?, ?);";
	
	private static final String DELETE_ALBUM = "DELETE FROM Albums "
		+ "WHERE album_id = ?;"; 

	/* Methods */ 
	//
	//Get Album with album_name from db
	//
	public Album load(int user_id, String album_name) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null; 
		Album album = null;
		ResultSet rs = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_ALBUM_STMT);	//prepare statement
			stmt.setInt(1, user_id); 						//set parameters
			stmt.setString(2, album_name);					
			
			rs = stmt.executeQuery();						//execute
			
			if (rs.next())
				album = new Album(rs.getInt(1), user_id, album_name);			

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

		return album;
	} 
	
	//
	//Save album
	//
	public boolean save(Album album) {
		//declare all db access helpers
		PreparedStatement stmt = null; 
		Connection conn = null;
		ResultSet rs = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			
			//check if name already in use
			stmt = conn.prepareStatement(CHECK_NAME_STMT);	
			stmt.setString(1, album.getName());						
			rs = stmt.executeQuery();	
			
	        if (!rs.next()) return false;				
			
			int result = rs.getInt(1);		//if there are matching names
			if (result > 0)	return false; 	//return false
			
			stmt = conn.prepareStatement(INSERT_ALBUM_STMT);	//otherwise prep insert stmt
			stmt.setInt(1, album.getUserId());					//set parameters
			stmt.setString(2, album.getName());
			
			//get sql.Date
			java.sql.Date date = new java.sql.Date(album.getDate().getTime()); 
			stmt.setDate(3, date);

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
	//Delete album
	//
	public void delete(int album_id) {
		//declare all db access helpers
		PreparedStatement stmt = null; 
		Connection conn = null;
		ResultSet rs = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			
			//check if name already in use
			stmt = conn.prepareStatement(DELETE_ALBUM);	
			stmt.setInt(1, album_id);						
			
			stmt.executeUpdate();	
			
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
	//Get all albums
	//
	public List<Album> allAlbums(int user_id) {
		//declare db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		//declare list to store pids
		List<Album> albums = new ArrayList<Album>();
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_ALL_ALBUMS);	//prepare statement
			stmt.setInt(1, user_id); 
			
			rs = stmt.executeQuery();							//execute
			
			//loop thru result set
			while (rs.next()) {
				java.util.Date date_created = new Date(rs.getDate(4).getTime());
				albums.add(new Album(rs.getInt(1), user_id, rs.getString(3), date_created)); 
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

		return albums;
	}
}
