<%--
viewfriendsearch.jsp: redirect-get page adding photos for PhotoShare web app. 
	
Tim Farrell, tmf@bu.edu
PA2, CS660, BU
150422
--%>

<!--Imports-->
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<!--Page-->
<!DOCTYPE HTML>
<html>
	<head>
		<title>PhotoShare</title>
		
		<!--Scripts and stylesheets--> 
		<script language="javascript" type="text/javascript" src="./resources/js/jquery.min.js"></script>
		<script language="javascript" type="text/javascript" src="./resources/js/jquery.poptrox.min.js"></script>
		<script language="javascript" type="text/javascript" src="./resources/js/skel.min.js"></script>
		<script language="javascript" type="text/javascript" src="./resources/js/init.js"></script>
		
		<link rel="stylesheet" href="./resources/css/skel-noscript.css" type="text/css"/>
		<link rel="stylesheet" href="./resources/css/style.css" type="text/css"/>
		
	</head>
	
	<!--Main-->
	<body>
		
		<!--Header-->
		<header id="header">
			<h1 id="logo"><a href="/photoshare/index.jsp">PhotoShare</a></h1>
			
			<nav id="nav">
				<ul>
					<li><a href="/photoshare/index.jsp">Home</a></li>
					<li><a href="/photoshare/logout.jsp">Logout</a></li>
				</ul>
			</nav>
		</header>
		
		<!--Main JSP script-->
		<section id="browse" class="main left fullscreen">
			<div class="content container">
				<div class="content box">
					<header><h2>Their Basics.</h2></header>
					<p>Click to make friend or view their profile.</p>
					<%
						UserDao userDao = new UserDao();
			            User u = userDao.getUser(request.getParameter("email")); 
						out.println("<div style=\"float:left;margin-left:30px;margin-right:30px\">"); 	
						out.println("<form method=\"post\" action=\"addtofriends.jsp\" >"); 		
						out.println("<dl>"); 
						out.println("<dt>Name: <b>" + u.getFirst() + " " + u.getLast() + "</b></dt>"); 
						out.println("<dt>Location: <b>" + u.getLocation() + "</b></dt>"); 
						out.println("<dt>Title: <b>" + u.getTitle() + "</b></dt>"); 
						out.println("<input type=\'hidden\' name=\'email\' value=\'" + u.getEmail() + "\' />");
						out.println("<input type=\'submit\' value=\'Make Friend\' "); 
						out.println(" style=\"height:5em;width:7.5em;font-size:medium\"/>");
						out.println("</form>"); 
						
						out.println("<form method=\"post\" action=\"viewfriendsearchprofile.jsp\" >"); 
						out.println("<input type=\'hidden\' name=\'first_name\' value=\'" + u.getFirst() + "\' />");  
						out.println("<input type=\'hidden\' name=\'last_name\' value=\'" + u.getLast() + "\' />"); 
						out.println("<input type=\'hidden\' name=\'email\' value=\'" + u.getEmail() + "\' />"); 
						out.println("<input type=\'hidden\' name=\'location\' value=\'" + u.getLocation() + "\' />"); 
						out.println("<input type=\'hidden\' name=\'title\' value=\'" + u.getTitle() + "\' />"); 
						out.println("<input type=\'hidden\' name=\'friend_id\' value=\'" + u.getId() + "\' />"); 
						out.println("<input type=\'submit\' value=\'View Profile\' style=\"height:5em;width:7.5em;font-size:medium\"/>");
						out.println("</form></div>"); 
					%> 
				</div>
			</div>
		</section>
		
		<!-- Footer -->
		<footer id="footer">
			<!--Social media icons-->
			<ul class="actions">Share: </li>
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
		
		<!--End main-->
	</body>
</html>
