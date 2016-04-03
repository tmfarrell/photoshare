/**
* A data access object (DAO) to handle picture objects
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

import photoshare.AlbumDao; 
import photoshare.Album; 


public class PictureDao {
  
	/* SQL Statements */ 
	private static final String LOAD_PICTURE_STMT = "SELECT "
		+ "caption, album_id, imgdata, thumbdata, size, content_type "
		+ "FROM Pictures WHERE picture_id = ?;";
	
	private static final String LOAD_LAST_SAVED = "SELECT MAX(picture_id) FROM Pictures;";

	private static final String SAVE_PICTURE_STMT = "INSERT INTO "
		+ "Pictures (caption, album_id, imgdata, thumbdata, size, content_type) " 
		+ "VALUES (?, ?, ?, ?, ?, ?);";

	private static final String ALL_PICTURES_USER = "SELECT "
		+ "P.picture_id, A.album_name, P.caption, P.likes FROM Pictures P, Albums A "
		+ "WHERE A.user_id = ? AND A.album_id = P.album_id;";
	
	private static final String ALL_PICTURES_ALBUM = "SELECT caption, likes "
		+ "FROM Pictures WHERE album_id = ?;";
	
	private static final String UPDATE_CAPTION_STMT = "UPDATE Pictures "
		+ "SET caption = ? WHERE picture_id = ?;";
	
	private static final String UPDATE_ALBUM_STMT = "UPDATE Pictures "
		+ "SET album_id = ? WHERE picture_id = ?;";
	
	private static final String ADD_LIKE_STMT = "UPDATE Pictures "
		+ "SET likes = likes + 1 WHERE picture_id = ?;";
	
	private static final String DELETE_STMT = "DELETE FROM "
		+ "Pictures WHERE picture_id = ?;";


	/* Methods */ 
	//
	//Get picture with pid id from db
	//
	public Picture load(int id) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		Picture picture = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_PICTURE_STMT);	//prepare statement
			stmt.setInt(1, id);									//set parameter
			rs = stmt.executeQuery();							//execute
			
			if (rs.next()) {
				picture = new Picture();			//build picture from result
				picture.setId(id);
				picture.setCaption(rs.getString(1));
				picture.setAlbumId(rs.getInt(2)); 
				picture.setData(rs.getBytes(3));
				picture.setThumbdata(rs.getBytes(4));
				picture.setSize(rs.getLong(5));
				picture.setContentType(rs.getString(6));
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

		return picture;
	}
	
	//
	//loads last picture saved
	//
	public int loadLastSaved() {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		int pid = -1;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(LOAD_LAST_SAVED);	//prepare statement

			rs = stmt.executeQuery();							//execute
			
			if (rs.next())
				pid = rs.getInt(1); 

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

		return pid;
	}
	
	//
	//Save picture
	//
	public void save(Picture picture) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(SAVE_PICTURE_STMT);	//prepare statement
			stmt.setString(1, picture.getCaption());			//set parameters
			stmt.setInt(2, picture.getAlbumId()); 
			stmt.setBytes(3, picture.getData());
			stmt.setBytes(4, picture.getThumbdata());
			stmt.setLong(5, picture.getSize());
			stmt.setString(6, picture.getContentType());
			
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
	//Update caption and album_info 
	//
	public void update(Picture picture) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(UPDATE_CAPTION_STMT);	//prepare caption update
			stmt.setString(1, picture.getCaption());			//set parameters
			stmt.setInt(2, picture.getId()); 
			
			stmt.executeUpdate();								//execute
			
			AlbumDao albumDao = new AlbumDao(); 
			Album album = new Album(picture.getUserId(), picture.getAlbumName()); 
			albumDao.save(album);
			album = albumDao.load(album.getUserId(), album.getName()); 
			
			stmt = conn.prepareStatement(UPDATE_ALBUM_STMT);	//prepare album update
			stmt.setInt(1, album.getAlbumId());					//set parameters
			stmt.setInt(2, picture.getId()); 
			
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
	//Add like to picture
	//
	public void addLike(int picture_id) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ADD_LIKE_STMT);//prepare statement
			stmt.setInt(1, picture_id);					//set parameters
			
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
	//Delete picture
	//
	public void delete(int picture_id) {
		//declare all db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		
		//try to connect to db
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(DELETE_STMT);	//prepare caption update
			stmt.setInt(1, picture_id);					//set parameters 
			
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
	//Get all pids from db for this user
	//
	public List<Picture> allPicturesUser(int user_id) {
		//declare db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		//declare list to store pids
		List<Picture> pictures = new ArrayList<Picture>();
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURES_USER);	//prepare statement
			stmt.setInt(1, user_id); 
			rs = stmt.executeQuery();							//execute
			
			Picture pic; 
			//loop thru result set
			while (rs.next()) {
				pic = new Picture(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4));  
				pictures.add(pic);	//add each to list
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

		return pictures;
	}
	
	//
	//Get all pids from db for this album
	//
	public List<Picture> allPicturesAlbum(int album_id) {
		//declare db access helpers
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		
		//declare list to store pids
		List<Picture> pictures = new ArrayList<Picture>();
		
		//try to connect
		try {
			conn = DbConnection.getConnection();
			stmt = conn.prepareStatement(ALL_PICTURES_ALBUM);	//prepare statement
			stmt.setInt(1, album_id); 
			rs = stmt.executeQuery();							//execute
			
			Picture pic; 
			//loop thru result set
			while (rs.next()) {
				pic = new Picture();  
				pic.setCaption(rs.getString(1)); 
				pic.setLikes(rs.getInt(2)); 
				pictures.add(pic);	//add each to list
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

		return pictures;
	}
}
