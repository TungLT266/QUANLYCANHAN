<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	try
	{  
		dbutils db = new dbutils();
		
		String khTen = "";
		
		String command = "select cast(pk_seq as nvarchar(10)) + ' - ' + ma + ',  ' + ten as khTen \n" +
						 "from NhaPhanPhoi where trangthai = '1' and isKHACHHANG = 1\n";
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc khach hang: " + command + "\n");
	 	System.out.println("Query la: " + query);
	 	
	 	ResultSet kh = db.get(command);
	 	
		if(kh != null)
		{
			while(kh.next())
			{   
				khTen = kh.getString("khTen");
				if(khTen.toUpperCase().startsWith(query.toUpperCase()) || khTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					out.println(khTen + "\n");
				}
			}
			kh.close();
		}
			
		db.shutDown();
	}
	catch(SQLException e){
		e.printStackTrace();
	}
		
%>