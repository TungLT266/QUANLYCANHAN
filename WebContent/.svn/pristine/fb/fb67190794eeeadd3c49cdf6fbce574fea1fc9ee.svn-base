<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>

<%
String userId = (String)session.getAttribute("userId");
if(userId == null)
	userId = "";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Lê Thanh Tùng - CV</title>
</head>
<body>
	<%if(userId.trim().length() > 0){ %>
		<a href="/QUANLYCANHAN/QuanLyCaNhanSvl">Quản lý cá nhân</a>
		<a href="/QUANLYCANHAN/LoginSvl?userId=&logout=">Đăng xuất</a>
	<%} else { %>
		<a href="/QUANLYCANHAN/LoginSvl">Đăng nhập</a>
	<%} %>
</body>
</html>