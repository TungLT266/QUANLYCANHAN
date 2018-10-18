<%@ page language="java" contentType="text/html; charset=UTF8" pageEncoding="UTF-8"%>
<%@page import="login.beans.ILogin"%>

<%
ILogin obj = (ILogin)session.getAttribute("obj");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>

<!-- <script language="javascript" type="text/javascript">
	function submitform() {
		
	}
</script> -->
</head>
<body>
	<form name="login" method="post" action="/QUANLYCANHAN/LoginSvl">
		<input type="hidden" name="action" value="">
		
		Username <input type="text" name="username" id="username" value="admin" required="" placeholder="Vui lòng nhập tên đăng nhập.">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		Password <input type="password" name="password" id="password" value="tunggeso" required="" placeholder="Vui lòng nhập mật khẩu.">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="submit" value="Login"><input type="text" readonly="readonly" value="<%=obj.getMsg() %>">
	</form>
</body>
</html>