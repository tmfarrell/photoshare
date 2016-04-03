<%--
	addtofriends.jsp: redirect-get page adding photos for PhotoShare web app. 
	
	Tim Farrell, tmf@bu.edu
	PA2, CS660, BU
	150422
--%>

<!--Imports-->
<%@ page import="photoshare.FriendDao" %>
<%@ page import="photoshare.User" %>

<%@ page import="java.lang.Integer" %>

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
		</header>
		
		<!--Main JSP script-->
		<% 
			String err = null; 
			try {
	 		   String friend_email = request.getParameter("email"); 
			   String user_email = session.getAttribute("email").toString(); 
			   
			   FriendDao friendDao = new FriendDao(); 
			   friendDao.makeFriends(user_email, friend_email); 
				
			} catch (Exception e) {
				e.printStackTrace();
				err = e.toString(); 
			}
		   
			if (err != null) {
				out.println("<section id=\"intro\" class=\"main style1 dark fullscreen\">"); 
				out.println("<div class=\"content container small\">"); 
				out.println("<header><h2>Error.</h2></header>"); 
				out.println("<p>" + err + " <a href=\"/photoshare/index.jsp\">Go back</a> to retry.</p>"); 
				out.println("</div>"); 
				out.println("</section>"); 
			} else { 
				out.println("<section id=\"intro\" class=\"main style1 dark fullscreen\">"); 
				out.println("<div class=\"content container small\">"); 
				out.println("<header><h2>Congrats.</h2></header>"); 
				out.println("<p>You've successfully updated your friends.</p>"); 
				out.println("</div>"); 
				out.println("</section>"); 
			}
			
			response.setHeader("Refresh", "5;url=index.jsp");
		%>	
		
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
