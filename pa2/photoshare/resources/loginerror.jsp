<%--
	logout.jsp: logout page for PhotoShare web app. 
	
	Tim Farrell, tmf@bu.edu
	PA2, CS660, BU
	150422
--%>

<!--Imports-->
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
			<h1 id="logo"><a href="#">PhotoShare</a></h1>
		</header>
		
		<!--Failed-->
		<section id="intro" class="main style1 dark fullscreen">
			<div class="content container small">
				<header>
					<h2>Login failed.</h2>
				</header>
				
				<% response.setHeader("Refresh", "3;url=index.jsp"); %> 
			</div>
		</section>		
		
		<!-- Footer -->
		<footer id="footer">
			<!--Social media icons-->
			<ul class="actions">
				<li><a href="#" class="fa solo fa-twitter"><span>Twitter</span></a></li>
				<li><a href="#" class="fa solo fa-facebook"><span>Facebook</span></a></li>
				<li><a href="#" class="fa solo fa-google-plus"><span>Google+</span></a></li>
				<li><a href="#" class="fa solo fa-instagram"><span>Instagram</span></a></li>
			</ul>
			<!--Attribution-->
			<ul class="menu">
				<li>Design: <a href="http://html5up.net/">HTML5 UP</a></li>
			</ul>
		</footer>
		
	<!--End main-->
	</body>
</html>