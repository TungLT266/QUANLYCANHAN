<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<% String congtyId = (String) session.getAttribute("congtyId"); %>
<% String nppQuery =  (String) session.getAttribute("queryNpp"); %>

<%
	try
	{  
		String command="";

		dbutils db = new dbutils();
		if(nppQuery==null||nppQuery.length()==0)
		{
			command=
					"select CONVERT(VARCHAR, PK_SEQ) + ' -- ' + '0' AS PK_SEQ, MAFAST , maFAST + ' -- ' + TEN  as nppTen \n" +
					"from TRAPHACODMS..KHACHHANG where trangthai = '1' \n";
		}
		else
		{
			command=nppQuery;
		}
		
		command= " SELECT  top (30)* FROM ( "+command+" )Data ";
		String khId = "";
		String khTen = "";
				
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		command += " WHERE (nppTen LIKE N'%"+query+"%' OR PK_SEQ LIKE N'%"+query+"%') ";
		command += " ORDER BY nppTen" ;
	 	
		
		System.out.print("command :"+command);
	 	ResultSet ncc = db.get(command);
	 
		if(ncc != null)
		{
			while(ncc.next())
			{   
				khId = ncc.getString("PK_SEQ");
				khTen = ncc.getString("nppTen");
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