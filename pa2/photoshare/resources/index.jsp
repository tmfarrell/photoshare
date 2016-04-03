<%--
	index.jsp: main page for Photoshare web application. 
	
  	Original author: Giorgos Zervas <cs460tf@cs.bu.edu>
	Adaption author: Tim Farrell, tmf@bu.edu

	CS660, Programming Assignment #2
	150422
--%>

<!--Imports-->
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.User" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.FriendDao" %>
<%@ page import="photoshare.Album" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.Tag" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.Comment" %>
<%@ page import="photoshare.CommentDao" %>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<!--Page-->
<!DOCTYPE HTML>
<html>
	<head>
		<title>PhotoShare</title>
		
		<!--Scripts--> 
		<script language="javascript" type="text/javascript" src="./resources/js/jquery.min.js"></script>
		<script language="javascript" type="text/javascript" src="./resources/js/jquery.poptrox.min.js"></script>
		<script language="javascript" type="text/javascript" src="./resources/js/skel.min.js"></script>
		<script language="javascript" type="text/javascript" src="./resources/js/init.js"></script>
		
		<!--Stylesheets-->
		<link rel="stylesheet" href="./resources/css/skel-noscript.css" type="text/css"/>
		<link rel="stylesheet" href="./resources/css/style.css" type="text/css"/>
		
	</head>
	
	
	<!--Main-->
	<body>
		<header id="header">
			<!-- Logo -->
			<h1 id="logo"><a href="#">PhotoShare</a></h1>
			
			<nav id="nav">
				<ul>
					<li><a href="/photoshare/userdisplay.jsp">View Users</a></li>
					<li><a href="/photoshare/logout.jsp">Logout</a></li>
				</ul>
			</nav>
		</header>
		
		<% 
			UserDao userDao = new UserDao(); 
			User user = userDao.getUser(request.getUserPrincipal().getName()); 
			session.setAttribute("user_id", user.getId()); 
			session.setAttribute("email", user.getEmail()); 
		%> 

		<!-- Intro -->
		<section id="intro" class="main dark">
			<div class="content container small">
				<header>
					<h2>Hi <code><%= user.getFirst() %></code>.</h2>
				</header>
				<p>Welcome to <b>PhotoShare</b>.</p>
			</div>
		</section>
		
		<!--Profile-->
		<section id="one" class="main left fullscreen"> 
			<div class="content box">
				<header>
					<h2>Basics.</h2>
				</header>
				<dl>
					<dt>Name: <b><%= (user.getFirst() + " " + user.getLast()) %></b></dt>
					<dt>Email: <b><%= user.getEmail() %></b></dt>  
					<dt>Location: <b><%= user.getLocation() %></b></dt>
					<dt>Title: <b><%= user.getTitle() %></b></dt>
					<dt>Rating: <b><%= user.getRating() %></b></dt>
				</dl>
			</div>
		</section>
		
		<!--Script for browsing through images-->
		<script type="text/javascript">
			function show_image(src, pid, albumName, caption) {
				//add chosen image
				var img = document.createElement("img");
				img.src = src;
				img.style.minWidth = "320px"; 
				img.style.minHeight = "250px";
				img.style.maxWidth = "800px"; 
				img.style.maxHeight = "750px";  
				img.style.border = "1px solid #99ADD6"; 
				var item = document.getElementById("photo_browser");
				item.replaceChild(img, item.childNodes[1]); 
				
				//build form
				var f = document.createElement("form");
				f.setAttribute('method',"post");
				f.setAttribute('action',"addtophoto.jsp");
				
				//hidden input
				var hi = document.createElement("input"); 
				hi.setAttribute('type', "hidden"); 
				hi.setAttribute('name', "picture_id"); 
				hi.setAttribute('value', pid); 
				
				//caption
				var p1 = document.createElement("p"); 
				p1.innerHTML = "Edit Caption: "; 
				p1.style.margin = "0px"; 
				var i1 = document.createElement("input"); 
				i1.setAttribute('type',"text");
				i1.setAttribute('name',"caption1");
				i1.setAttribute('value', caption); 	
				i1.style.paddingTop = "10px"; 
				i1.style.paddingBottom = "10px"; 
				i1.style.margin = "0px"; 	
				
				//tag
				var p3 = document.createElement("p"); 
				p3.innerHTML = "Add Tag: "; 
				p3.style.margin = "0px"; 
				var i4 = document.createElement("input"); 
				i4.setAttribute('type',"text");
				i4.setAttribute('name',"tag");
				i4.setAttribute('value', ""); 	
				i4.style.paddingTop = "10px"; 
				i4.style.paddingBottom = "10px"; 
				i4.style.margin = "0px";		
				
				//album name
				var p2 = document.createElement("p"); 
				p2.innerHTML = "Edit Album: "; 
				p2.style.margin = "0px";
				var i2 = document.createElement("input");
				i2.setAttribute('type',"text");
				i2.setAttribute('name',"album_name");
				i2.setAttribute('value', albumName); 
				i2.style.paddingTop = "10px"; 
				i2.style.paddingBottom = "10px"; 
				i2.style.margin = "0px"; 
				
				//delete checkbox
				var l1 = document.createElement("label"); 
				l1.htmlFor = "delete_checkbox"; 
				l1.appendChild(document.createTextNode('Delete:  '));
				l1.style.display = "inline-block"; 
				l1.style.margin = "2px";
				var i3 = document.createElement("input");
				i3.setAttribute('id', "delete_checkbox"); 
				i3.setAttribute('type',"checkbox");
				i3.setAttribute('name',"delete");
				i3.setAttribute('value', "to_delete"); 
				i3.style.margin = "2px";

				//submit button
				var b = document.createElement("br"); 
				var s = document.createElement("input");
				s.setAttribute('type',"submit");
				s.setAttribute('value',"Update");
				s.style.margin = "0px"; 
				s.style.height = "5em"; 
				s.style.width = "7.5em";
				s.style.fontSize = "medium"; 
				s.style.maxHeight = "5em"; 

				f.appendChild(p1);
				f.appendChild(i1);
				f.appendChild(p2);
				f.appendChild(i2);
				f.appendChild(p3); 
				f.appendChild(i4);
				f.appendChild(hi);
				f.appendChild(l1); 
				f.appendChild(i3);
				f.appendChild(b); 
				f.appendChild(s);


				item.replaceChild(f, item.childNodes[3]);
		    }
		</script>
		
		<!--Browse photos-->
		<section id="browse" class="main left fullscreen">
			<div class="content container">
				<div class="content box">
					<header><h2>Your Photos.</h2></header>
					<p>Click to view photos in full below.</p>
					<table>
					    <tr><% 
							TagDao tagDao = new TagDao(); 
							PictureDao pictureDao = new PictureDao();
							CommentDao commentDao = new CommentDao(); 
							
							List<Tag> tags = new ArrayList<Tag>(); 
							List<Comment> comments = new ArrayList<Comment>(); 
				            List<Picture> pictures = pictureDao.allPicturesUser(user.getId());	
							
				            for (Picture pic : pictures) {				
								int pictureId = pic.getId(); 
								String albumName = pic.getAlbumName(); 
								String caption = pic.getCaption(); 
								out.println("<figure>"); 
								out.println("<img src=\"/photoshare/img?picture_id=" + pictureId + "\"");  
								out.println(" onclick=\"show_image(this.src, \'" + pictureId + "\', \'" + albumName + "\', \'" + caption + "\')\""); 
								out.println(" width=320 height=250 style=\"padding:0px;margin:8px;border:5px #FFFFFF solid;float:left;\"/>"); 
								out.println("<figcaption style=\"padding-left:2em\"><textarea class=\"textarea\">" + caption + "."); 
								out.println("Likes: " + pic.getLikes() + "."); 
								out.println("Album: " + albumName + "."); 
								out.println("Tags: "); 
								tags = tagDao.loadTags(pictureId); 
								for(Tag t : tags) { 
									out.println(t.getTag()); 
								}
								out.println("Comments:"); 
								comments = commentDao.load(pictureId); 
								for(Comment c : comments) {
									out.println(c.getComment() + "(" + c.getUserName() + " on " + c.getDate().toString() + ")");
								}
								out.println("</textarea></figcaption></figure>"); 
				            }
						%></tr>
					</table>
				</div>
				
				<div class="content box" id="photo_browser">
					<img src="" />
					<form></form>
				</div>
				
				<div class="content box">
					<header>
						<h2>Upload a new photo.</h2>
					</header>
					<div class="box container small">
						<form method="post" action="addtophoto.jsp" enctype="multipart/form-data">
				    		<!--choose image file-->
							Filename: <input type="file" name="filename" style="font-size:medium"/><br />
							Caption: <input type="text" name="caption2" placeholder="Enter Caption" size="20"/>
							Enter Tag: <input type="text" name="tag" placeholder="Enter Tag" size="20"/>	
							Album: <input type="text" name="album_name" placeholder="Enter Album Name" size="20"/>
				    		<!--submit-->
							<input type="submit" value="Upload" style="height:5em;width:7.5em;font-size:medium"/>
						</form>
					</div>
				</div>
				
			</div>
		</section>
		
		<!--Tag and album management-->
		<section id="browse" class="main left fullscreen">
			<div class="content container">
				<div class="content box">
					<header><h2>Your Albums and Tags.</h2></header>
					<h1>Manage Albums.</h1>
					<table>
					    <tr><%
							AlbumDao albumDao = new AlbumDao();
				            List<Album> albums = albumDao.allAlbums(user.getId());	//get all albums
				            for (Album album : albums) {					
								int albumId = album.getAlbumId(); 
								out.println("<div style=\"float:left;margin-left:30px;margin-right:30px\">"); 
								out.println("Album: <b>" + album.getName() + ".</b><br/>"); 
								out.println("Date: <b>" + album.getDate().toString() + ".</b><br/>");  
								out.println("Photos (likes):<dl>"); 
								for (Picture p : pictureDao.allPicturesAlbum(albumId)) 
									out.println("<dt><b>" + p.getCaption() + "(" + p.getLikes() + ")</b></dt>"); 
								out.println("</dl><form method=\"post\" action=\"deletealbum.jsp\">"); 
								out.println("<input type=\"hidden\" name=\"album_id\" value=\'" + albumId + "\'/>"); 
								out.println("<input type=\"submit\" value=\"Delete\" style=\"height:5em;width:7.5em;font-size:medium\"/>");
								out.println("</form></div>"); 
				            }
						%></tr>
					</table> 
					<h1>Manage Tags.</h1>
					<table>
					    <tr><%
							tags = tagDao.loadTagsUser(user.getId()); 
				            for (Tag tag : tags) {	
								String _tag = tag.getTag(); 				
								out.println("<div style=\"float:left;margin-left:30px;margin-right:30px\">"); 
								out.println("Tag: <b>" + _tag + ".</b><br/>"); 
								out.println("Photos (likes):<dl>");    
								for (Picture p : tagDao.loadPictures(_tag)) 
									out.println("<dt><b>" + p.getCaption() + "(" + p.getLikes() + ")</b></dt>"); 
								out.println("</dl><form method=\"post\" action=\"deletetag.jsp\">"); 
								out.println("<input type=\"hidden\" name=\"tag\" value=\'" + _tag + "\'/>"); 
								out.println("<input type=\"submit\" value=\"Delete\" style=\"height:5em;width:7.5em;font-size:medium\"/>");
								out.println("</form></div>"); 
				            }
						%></tr>
					</table>
				</div>
				
				<div class="content box" id="photo_browser">
					<img src="" />
					<form></form>
				</div>
				
				<div class="content box">
					<header>
						<h2>Upload a new photo.</h2>
					</header>
					<div class="box container small">
						<form method="post" action="addtophoto.jsp" enctype="multipart/form-data">
				    		<!--choose image file-->
							Filename: <input type="file" name="filename" style="font-size:medium"/><br />
							Caption: <input type="text" name="caption2" placeholder="Enter Caption" size="20"/>
							Album: <input type="text" name="album_name" placeholder="Enter Album Name" size="20"/>
				    		<!--submit-->
							<input type="submit" value="Upload" style="height:5em;width:7.5em;font-size:medium"/>
						</form>
					</div>
				</div>
				
			</div>
		</section>

		<!--Friends-->
		<section id="two" class="main left fullscreen"> 
			<div class="content box">
				<header><h2>Friends.</h2></header>
			</div>
		</section>
		
		<section id="browse" class="main left fullscreen">
			<div class="content container">
				<div class="content box">
					<header><h2>Their Basics.</h2></header>
					
					<p>Click to browse their profiles.</p>
					<%
						FriendDao friendDao = new FriendDao();
			            List<User> friends = friendDao.getFriends(request.getUserPrincipal().getName()); 
		
			            for (User friend : friends) {
							out.println("<div style=\"float:left;margin-left:30px;margin-right:30px\">"); 	
							out.println("<form method=\"post\" action=\"viewfriendprofile.jsp\" >"); 		
							out.println("<dl>"); 
							out.println("<dt>Name: <b>" + friend.getFirst() + " " + friend.getLast() + "</b></dt>"); 
							out.println("<dt>Email: <b>" + friend.getEmail() + "</b></dt>"); 
							out.println("<dt>Location: <b>" + friend.getLocation() + "</b></dt>"); 
							out.println("<dt>Title: <b>" + friend.getTitle() + "</b></dt>"); 
							out.println("<input type=\'hidden\' name=\'first_name\' value=\'" + friend.getFirst() + "\' />");  
							out.println("<input type=\'hidden\' name=\'last_name\' value=\'" + friend.getLast() + "\' />"); 
							out.println("<input type=\'hidden\' name=\'email\' value=\'" + friend.getEmail() + "\' />"); 
							out.println("<input type=\'hidden\' name=\'location\' value=\'" + friend.getLocation() + "\' />"); 
							out.println("<input type=\'hidden\' name=\'title\' value=\'" + friend.getTitle() + "\' />"); 
							out.println("<input type=\'hidden\' name=\'friend_id\' value=\'" + friend.getId() + "\' />"); 
							out.println("<input type=\'submit\' value=\'View Profile\' style=\"height:5em;width:7.5em;font-size:medium\"/>");
							out.println("</form></div>"); 
			            }
					%>
				</div>
			
				<div class="content box" style="clear:both">
					<header>
						<h2>Search for more friends.</h2>
					</header>
					<div class="box container small">
						<form method="post" action="viewfriendsearch.jsp">
				    		<!--choose image file-->
							Email: <input type="text" name="email" /><br />
				    		<!--submit-->
							<input type="submit" value="Search" style="height:5em;width:7.5em;font-size:medium"/><br/>
						</form>
					</div>
				</div>
			</div>
		</section>

		
		<!-- Footer -->
		<footer id="footer">
			<!--Social media icons-->
			<ul class="actions"><li>Share: </li>
				<li><a href="http://www.twitter.com" class="fa solo fa-twitter"><span>Twitter</span></a></li>
				<li><a href="http://www.facebook.com" class="fa solo fa-facebook"><span>Facebook</span></a></li>
				<li><a href="http://plus.google.com" class="fa solo fa-google-plus"><span>Google+</span></a></li>
				<li><a href="http://www.instagram.com" class="fa solo fa-instagram"><span>Instagram</span></a></li>
			</ul>
			<!--Attribution-->
			<ul class="menu">
				<li>Design: <a href="http://html5up.net/">HTML5 UP</a></li>
			</ul>
		</footer>
		
	</body>
	<!--End main-->
</html>
<!--End page-->
