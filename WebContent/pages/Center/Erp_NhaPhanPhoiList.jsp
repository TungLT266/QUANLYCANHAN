<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>

<%@ page import = "java.net.URLDecoder" %>

<% String congtyId = (String) session.getAttribute("congtyId"); %>
<% String kbhId = (String) session.getAttribute("kenhid"); %>

<%
	try
	{  
		dbutils db = new dbutils();
		
		String khId = "";
		String khTen = "";
	
		//String command = "select cast(pk_seq as nvarchar(10)) + ' -- ' + sitecode + ', ' + ten as nccTen from NHAPHANPHOI where trangthai='1'";
		
		String command = "select pk_seq, ma + ', ' + ten as nccTen from nhaphanphoi where trangthai = '1' ";
		
		
	
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc khach hang: " + command + "\n");
	 	
	 	ResultSet ncc = db.get(command);
	 	
		if(ncc != null)
		{
			while(ncc.next())
			{   
				khId = ncc.getString("pk_seq");
				khTen = ncc.getString("nccTen");
				
				
				if( khId.toUpperCase().contains(query.toUpperCase()) || khTen.toUpperCase().contains(query.toUpperCase()) )
				{
					out.println(khId + " -- " + khTen);
				}
			}
			ncc.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("1.Exception: " + e.getMessage());
	}
		
%>