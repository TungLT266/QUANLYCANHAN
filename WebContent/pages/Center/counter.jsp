<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "geso.traphaco.center.servlets.count.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Refresh" content="60" >
<title>Insert title here</title>
</head>
<%
	dbutils db =new dbutils();
	String songuoi="30";
	ResultSet rs = db.get("select COUNT(PK_SEQ) as soluong from NHANVIEN where ISLOGIN='1'");		
	try{
	rs.next();
	songuoi = rs.getString("soluong");
	//System.out.println("So nguoi truy cap: " + songuoi + "\n");
	}
	catch(Exception er)
	{
		er.printStackTrace();
		System.out.println(er.toString());	
	}
%>
<body>
  <P style="margin-top: -1px;font-size: 11px;margin-left: 5px"> Hiện có <%= songuoi %> người đang dùng hệ thống  </P> 
</body>
</html>