<%--
	viewfriendsearchprofile.jsp: friend profile view page for Photoshare web application. 
	
  	Original author: Giorgos Zervas <cs460tf@cs.bu.edu>
	Adaption author: Tim Farrell, tmf@bu.edu

	CS660, Programming Assignment #2
	150422
--%>

<!--Imports-->
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.Tag" %>
<%@ page import="photoshare.TagDao" %>
<%@ page import="photoshare.Comment" %>
<%@ page import="photoshare.CommentDao" %>
<%@ page import="photoshare.User" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.FriendDao" %>
<%@ page import="photoshare.Album" %>
<%@ page import="photoshare.AlbumDao" %>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="java.lang.Integer" %>

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
	
	<!--Go back script-->
	<script> 
		function goBack() { 
			window.history.back(); 
		}
	</script>
	
	<!--Main-->
	<body>
		<header id="header">
			<!-- Logo -->
			<h1 id="logo"><a href="/photoshare/index.jsp">PhotoShare</a></h1>
			
			<nav id="nav">
				<ul>
					<li><a onclick="goBack()">Back</a></li>
					<li><a href="/photoshare/index.jsp">Home</a></li>
					<li><a href="/photoshare/logout.jsp">Logout</a></li>
				</ul>
			</nav>
		</header>
		
		<!--Get friend's info from request-->
		<% 
			User friend = new User(Integer.parseInt(request.getParameter("friend_id")), 
						request.getParameter("email"), request.getParameter("first_name"),
						request.getParameter("last_name"), request.getParameter("location"), 
						request.getParameter("title")); 
		%> 

		<!-- Intro-->
		<section id="intro" class="main dark">
			<div class="content container small">
				<header>
					<h2><%= (friend.getFirst() + " " + friend.getLast()) %></h2>
				</header>
			</div>
		</section>
		
		<!--Profile-->
		<section id="one" class="main left fullscreen"> 
			<div class="content box">
				<header>
					<h2>Profile.</h2>
				</header>
				<dl>
					<dt>Name: <b><%= (friend.getFirst() + " " + friend.getLast()) %></b></dt>
					<dt>Email: <b><%= friend.getEmail() %></b></dt>  
					<dt>Location: <b><%= friend.getLocation() %></b></dt>
					<dt>Title: <b><%= friend.getTitle() %></b></dt>
					<dt>Rating: <b><%= friend.getRating() %></b></dt>
				</dl>
			</div>
		</section>
		
		<!--Script for browsing through images-->
		<script type="text/javascript">
			function show_image(src, pid) {
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
				f.setAttribute('action',"addtophotofriend.jsp");
				
				//hidden input
				var hi = document.createElement("input"); 
				hi.setAttribute('type', "hidden"); 
				hi.setAttribute('name', "picture_id"); 
				hi.setAttribute('value', pid); 
				
				//caption
				var p1 = document.createElement("p"); 
				p1.innerHTML = "Comment: "; 
				p1.style.margin = "0px"; 
				var i1 = document.createElement("input"); 
				i1.setAttribute('type',"text");
				i1.setAttribute('name',"comment");
				i1.setAttribute('placeholder', "Enter Comment"); 	
				i1.style.paddingTop = "10px"; 
				i1.style.paddingBottom = "10px"; 
				i1.style.margin = "0px"; 			 
				
				//like checkbox
				var l1 = document.createElement("label"); 
				l1.htmlFor = "like_checkbox"; 
				l1.appendChild(document.createTextNode('Like: '));
				l1.style.display = "inline-block"; 
				l1.style.margin = "0px";
				var i3 = document.createElement("input");
				i3.setAttribute('id', "like_checkbox"); 
				i3.setAttribute('type',"checkbox");
				i3.setAttribute('name',"like");
				i3.setAttribute('value', "liked"); 
				i3.setAttribute('class', "check"); 
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
				f.appendChild(l1); 
				f.appendChild(i3);
				f.appendChild(hi); 
				f.appendChild(b); 
				f.appendChild(s);

				item.replaceChild(f, item.childNodes[3]);
		    }
		</script>
		
		<!--Browse images and friends-->
		<section id="browse" class="main left fullscreen">
			<div class="content container">
				<div class="content box">
					<header><h2>Photos.</h2></header>
					<p>Click to view photos in full below.</p>
					<table>
					    <tr><%
							TagDao tagDao = new TagDao(); 
							PictureDao pictureDao = new PictureDao();
							CommentDao commentDao = new CommentDao(); 
							
							List<Tag> tags = new ArrayList<Tag>(); 
							List<Comment> comments = new ArrayList<Comment>(); 
				            List<Picture> pictures = pictureDao.allPicturesUser(friend.getId());
							
				            for (Picture pic : pictures) {				
								int pictureId = pic.getId(); 
								String albumName = pic.getAlbumName(); 
								String caption = pic.getCaption(); 
								out.println("<figure>"); 
								out.println("<img src=\"/photoshare/img?picture_id=" + pictureId + "\"");  
								out.println(" onclick=\"show_image(this.src, \'" + pictureId + "\')\""); 
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
			</div>
		</section>

		<!--Image upload-->
		<section id="friends" class="main left dark secondary">
			<div class="content box">
				<header><h2>Friends.</h2></header>
				<%
					FriendDao friendDao = new FriendDao();
		            List<User> friends = friendDao.getFriends(friend.getEmail()); 
		
		            for (User f : friends) {
						out.println("<div style=\"float:left;margin-left:30px;margin-right:30px\">"); 			
						out.println("<dl style=\"float:left\">");
						out.println("<dt>Name: <b>" + f.getFirst() + " " + f.getLast() + "</b></dt>"); 
						out.println("<dt>Email: <b>" + f.getEmail() + "</b></dt>"); 
						out.println("<dt>Location: <b>" + f.getLocation() + "</b></dt>"); 
						out.println("<dt>Title: <b>" + f.getTitle() + "</b></dt></dl></div>"); 
		            }
				%>
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
