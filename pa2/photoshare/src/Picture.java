/**
* A simple Picture class.
*
* @original_author G. Zervas <cs460tf@bu.edu>
* @adaption_author Tim Farrell <tmf@bu.edu>
*
* PA2, CS660, BU
* 150422  
*/
package photoshare;

import org.apache.commons.io.output.ByteArrayOutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;


public class Picture {
	private long size; 
	private int id = 0;
	private byte[] data;
	private int likes = 0;
	private byte[] thumbdata;
	private int user_id = 0; 
	private int album_id = 0; 
	private String contentType;
	private String caption = "";
	private String comment = ""; 
	private String album_name = ""; 
	private String tag = "";
	private static final int THUMBNAIL_WIDTH = 80;

	public Picture() {
	}
	
	public Picture(int id, String album_name, String caption) { 
		this.id = id;
		this.album_name = album_name; 
		this.caption = caption; 
	}
	
	public Picture(int id, String album_name, String caption, int likes) { 
		this.id = id;
		this.album_name = album_name; 
		this.caption = caption; 
		this.likes = likes; 
	}
	
	public Picture(int user_id, int id, String album_name, String caption) { 
		this(id, album_name, caption); 
		this.user_id = user_id; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getUserId() {
		return user_id;
	}

	public void setUserId(int user_id) {
		this.user_id = user_id;
	}
  
	public int getAlbumId() {
		return album_id;
	}

	public void setAlbumId(int album_id) {
		this.album_id = album_id;
	}
	
	public String getAlbumName() {
		return album_name;
	}

	public void setAlbumName(String album_name) {
		this.album_name = album_name;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getThumbdata() {
		return thumbdata;
	}

	public void setThumbdata(byte[] thumbdata) {
		this.thumbdata = thumbdata;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}
	
	public void addLike() { 
		this.likes++; 
	}
	
	public void setLikes(int likes) { 
		this.likes = likes; 
	}
	
	public int getLikes() { 
		return likes; 
	}
	
	public void setTag(String tag) { 
		this.tag = tag; 
	}
	
	public String getTag() { 
		return tag; 
	}

	public String getFormat() {
		if (getContentType() != null) {
			return getContentType().substring(getContentType().lastIndexOf('/') + 1).toLowerCase();
		} else {
			return "";
		}
	}

	public void createThumbnail() {
		if (getData() != null && getContentType() != null) {
			try {
				BufferedImage source = ImageIO.read(new ByteArrayInputStream(getData()));
				int width = THUMBNAIL_WIDTH;
				int height = THUMBNAIL_WIDTH;// * (source.getWidth() / source.getHeight());
				BufferedImage thumb = new BufferedImage(width, height, source.getType());
				thumb.createGraphics().drawImage(source, 0, 0, width, height, null);
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(thumb, getFormat(), bos);
				setThumbdata(bos.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
