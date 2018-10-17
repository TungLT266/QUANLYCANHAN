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
		String command="";
		System.out.print("VÀO AJAX");
		dbutils db = new dbutils();
		if(command==null||command.length()==0)
		{
			command="select Pk_SEQ,TEN from erp_nganhang where trangthai=1" ;
		}
		
		command= " SELECT  top (30)* FROM ( "+command+" )Data ";
		String khId = "";
		String khTen = "";
				
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		command += " WHERE (TEN LIKE N'%"+query+"%' OR PK_SEQ LIKE N'%"+query+"%' ) ";
		command += " ORDER BY TEN" ;
	 	
		
		System.out.print("command :"+command);
	 	ResultSet ncc = db.get(command);
	 
		if(ncc != null)
		{
			while(ncc.next())
			{   
				khId = ncc.getString("PK_SEQ");
				khTen = ncc.getString("TEN");
				out.println( khId +" - [ " + khTen + " ] \n");
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