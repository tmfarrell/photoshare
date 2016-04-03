<%--
	login.jsp: login page for PhotoShare web app. 

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
		<header id="header">
			<!-- Logo -->
			<h1 id="logo"><a href="#">PhotoShare</a></h1>
		</header>
			
		<!-- Intro -->
		<section id="intro" class="main style1 inactive dark fullscreen">
			<div class="content container small">
				<header>
					<h2>Hey.</h2>
				</header>
				<p>Welcome to <strong>PhotoShare</strong>, a simple application for sharing photos with friends.</p>
			</div>
		</section>
	
		
		<!-- Create Acct -->
		<section id="create_acct" class="main style3 secondary">
			<header>
				<h2>Create an Account.</h2>
			</header>
			
			<div class="box container small">
				<!--Create acct form-->
				<form method="post" action="adduser.jsp">
					<div class="row">
						<div class="12u"><input type="text" name="email" placeholder="Email"/></div></div>
					<div class="row">
						<div class="12u"><input type="password" name="password1" placeholder="Enter Password" /></div></div>
					<div class="row">
						<div class="12u"><input type="password" name="password2" placeholder="Reenter Password" s/></div></div>
					<div class="row">
						<div class="12u"><input type="text" name="first_name" placeholder="First Name" /></div></div>
					<div class="row">
						<div class="12u"><input type="text" name="last_name" placeholder="Last Name" /></div></div>
					<div class="row">
						<div class="12u"><input type="text" name="title" placeholder="Title" /></div></div>
					<div class="row">
						<div class="12u"><input type="text" name="location" placeholder="Location" /></div></div>
					<div class="row">
						<div class="12u"><input type="text" name="hometown" placeholder="Hometown" /></div></div>
					<div class="row">
						<div class="12u"><ul class="actions"><li><input type="submit" class="button" value="Create Account" /></li></ul></div>
					</div>
				</form>
			</div>
		</section>
		
		<!-- Login -->
		<section id="login" class="main left dark secondary">
			<div class="content box">
				<header>
					<h2>Or Login.</h2>
				</header>
				<!--Login form-->
				<form method="post" action="j_security_check">
					<div class="row half">
						<div class="6u"><input type="text" name="j_username" placeholder="Email" /></div>
						<div class="6u"><input type="password" name="j_password" placeholder="Password" size="30"/></div>
					</div>
					<div class="row">
						<div class="12u"><ul class="actions"><li><input type="submit" class="button" value="Login" /></li></ul></div>
					</div>
				</form>
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
			
	</body>
	<!--End main-->
</html>
<!--End page-->