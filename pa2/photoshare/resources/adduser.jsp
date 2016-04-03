<%--
	adduser.jsp: redirect-get page for adding users for PhotoShare web app. 
	
	Tim Farrell, tmf@bu.edu
	PA2, CS660, BU
	150422
--%>

<!--Imports-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page import="photoshare.UserDao" %>

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
			<h1 id="logo"><a href="#">PhotoShare</a></h1>
		</header>
		
		<!--Main JSP script-->
		<% 
		   String err = null;
		   String email  = request.getParameter("email");
		   String password1 = request.getParameter("password1");
		   String password2 = request.getParameter("password2");
		   String first_name = request.getParameter("first_name"); 
		   String last_name = request.getParameter("last_name"); 
		   String title = request.getParameter("title"); 
		   String location = request.getParameter("location");
		   String hometown = request.getParameter("hometown");  


		   if (!email.equals("")) {
		     if (!password1.equals(password2)) 
		       err = "Both password strings must match.";
			 
		     else if (password1.length() < 4) 
		       err = "Your password must be at least four characters long.";
			 
		     else {
		       // We have valid inputs, try to create the user
		       UserDao userDao = new UserDao();
		       boolean success = userDao.create(email, password1, first_name, last_name, title, location, hometown);
		       if (!success)  err = "Couldn't create user (that email may already be in use).";
		     }
		   } else
			 err = "You have to provide an email.";
		   
		   if (err != null) {
			   out.println("<section id=\"intro\" class=\"main style1 dark fullscreen\">"); 
			   out.println("<div class=\"content container small\">"); 
			   out.println("<header><h2>Error.</h2></header>"); 
			   out.println("<p>" + err + "</p>"); 
			   out.println("</div>"); 
			   out.println("</section>"); 
		   } else { 
			   out.println("<section id=\"intro\" class=\"main style1 dark fullscreen\">"); 
			   out.println("<div class=\"content container small\">"); 
			   out.println("<header><h2>Congratulations.</h2></header>"); 
			   out.println("<p>You've successfully created an account.</p>"); 
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
