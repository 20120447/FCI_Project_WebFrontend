<%@ page language="java" contentType="text/html; charset=windows-1256"
	pageEncoding="windows-1256"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1256">
<title>Welcome Home </title>
</head>
<body>
    <div style="width:500px;height:0px;">
	<img src="E:\FCI\SW2\project\item453602.gif" align="middle" style="display:block;"/></div>
	
	<p>Welcome b2a ya ${it.name} :D :D </p>
	<p>The user home page</p>
	<p> You can show your current position on map and update your position on our database from <a href="/FCISquareApp/app/showLocation"> here</a>

    <h2> you can updatePosition  <a href = "/frontend/app/updateMyLocation">here</a> </h2>
    <h2> you can follow user  <a href = "/frontend/app/followUser">here</a> </h2>
	<h2> you can get follower <a href = "/frontend/app/getFollower">here</a> </h2>
	<h2> you can un-follow other user <a href = "/frontend/app/unfollowUser">here</a> </h2>
	<h2> you can get the last position  <a href = "/frontend/app/GetLastPosition">here</a> </h2>
	
  
</body>
</html>