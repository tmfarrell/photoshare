/**
* A bean that handles file uploads
*
* @original_author G. Zervas <cs460tf@bu.edu>
* @adaption_author Tim Farrell <tmf@bu.edu>
* 
* PA2, CS660, BU
* 150422
*/

package photoshare;

import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import photoshare.Album; 
import photoshare.AlbumDao; 


public class ImageUploadBean {
	//
	//Upload new image from request
	//
	public Picture upload(HttpServletRequest request, int user_id) throws FileUploadException {
    
		//get context
		RequestContext requestContext = new ServletRequestContext(request);
    
		//if request has multiple parts
		if (FileUpload.isMultipartContent(requestContext)) {
			
			//init album to picture and album data
			Picture picture = new Picture(); 
			Album album = new Album(user_id); 
			String tag = ""; 								
			
			//get file fields
			FileItemFactory fileItemFactory = new DiskFileItemFactory();	
			ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
			List<FileItem> fileItems = servletFileUpload.parseRequest(request); 

			//add all fields to image
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					if (fileItem.getFieldName().equals("caption2")) 
						picture.setCaption(fileItem.getString());
					
					else if (fileItem.getFieldName().equals("album_name")) 
						album.setName(fileItem.getString());
					
					else if (fileItem.getFieldName().equals("tag"))
						tag = fileItem.getString(); 
					
				} else {
					if (fileItem.getFieldName().equals("filename")) {
						picture.setContentType(fileItem.getContentType());
						picture.setSize(fileItem.getSize());
						picture.setData(fileItem.get());
					} 
				}
			}
			
			//save album to db, even if there
			AlbumDao albumDao = new AlbumDao(); 
			albumDao.save(album); 
			
			//load album_id 
			int album_id = albumDao.load(user_id, album.getName()).getAlbumId(); 
			
			//set picture album_id
			picture.setAlbumId(album_id);
			
			//set tag
			picture.setTag(tag); 

			//then create thumbnail 
			picture.createThumbnail();
			
			//and return 
			return picture;
		
		//if not multipart, return null
		} else {
			return null;
		}
	}
}
