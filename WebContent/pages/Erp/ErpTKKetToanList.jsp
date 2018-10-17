<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<% String congtyId = (String) session.getAttribute("congtyId"); %>

<%
	try
	{  
		dbutils db = new dbutils();
		
		String tkId = "";
		String tkTen = "";
		String type="";
		
	
		
		String	command ="SELECT SOHIEUTAIKHOAN, TENTAIKHOAN AS TEN   FROM ERP_TAIKHOANKT WHERE CONGTY_FK="+congtyId+" AND TRANGTHAI=1 ";
		
		System.out.println(command);
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc tai khoan: " + command + "\n");
	 	
	 	ResultSet tk = db.get(command);
	 	
		if(tk != null)
		{
			while(tk.next())
			{   
				tkId = tk.getString("SOHIEUTAIKHOAN");
				tkTen = tk.getString("Ten");
				
				
				if(tkTen.toUpperCase().startsWith(query.toUpperCase()) || tkTen.toUpperCase().indexOf(query.toUpperCase()) >= 0   || tkId.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					out.println(tkId + " -- " + tkTen +"|");
					System.out.println("Lay duoc tai khoan: "+ tkId + " -- " + tkTen );
				}
			}
			tk.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("1.Exception: " + e.getMessage());
	}
		
%>