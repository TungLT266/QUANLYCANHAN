<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>


<% String ctyId = (String)session.getAttribute("congtyId");%>

<%
	try
	{  
		dbutils db = new dbutils();
		
		String sohieutk = "";
		String tentk = "";		
		
		String command = " SELECT * from ERP_TAIKHOANKT where TRANGTHAI = 1 AND CONGTY_FK ="+ctyId;
		  
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc tai khoan: " + command + "\n");
	 	
	 	ResultSet kt = db.get(command);
	 	
		if(kt != null)
		{
			while(kt.next())
			{   
				sohieutk = kt.getString("sohieutaikhoan");
				tentk = kt.getString("tentaikhoan");
				
				
				if(tentk.toUpperCase().startsWith(query.toUpperCase()) || tentk.toUpperCase().indexOf(query.toUpperCase()) >= 0   || sohieutk.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					out.println(sohieutk + " -- " + tentk +"|");
					//System.out.println("Lay duoc tai khoan: "+ sohieutk + " -- " + tentk );
				}
			}
			kt.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("1.Exception: " + e.getMessage());
	}
		
%>