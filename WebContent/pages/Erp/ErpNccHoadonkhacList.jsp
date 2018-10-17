<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page  import = "java.sql.ResultSet" %>
<%@page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@page import = "java.net.URLDecoder" %>
<%@page  import = "geso.traphaco.center.util.*" %>
<% String noibo = (String) session.getAttribute("noibo"); %>
<%
	try 
	{  
		System.out.println("Noi bo la " + noibo);
		if(noibo==null)
		{
			noibo = "";
		}
		
		Utility uti = new Utility();
		String userId = (String)request.getSession().getAttribute("userId");
		String congtyId = (String)request.getSession().getAttribute("congtyId");
		
		dbutils db = new dbutils();
		
		String nccTen = "";
		String command = "";
		   
		 command =  " select cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten     as nccTen " +
 				" ,  isnull(a.masothue,'') as masothue  ,isnull( a.diachi,'') as diachi   from ERP_NHACUNGCAP a where trangthai = '1' "+
 				" and CONGTY_FK = " + congtyId + "";
		 
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	query = uti.replaceAEIOU(query);
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc nha cung cap: " + command + "\n");
	 	System.out.println("Query la: " + query);
	 	
	 	ResultSet ncc = db.get(command);
	 	
		if(ncc != null)
		{
			while(ncc.next())
			{   
				nccTen = uti.replaceAEIOU(ncc.getString("nccTen"));
				
				if(nccTen.toUpperCase().contains(query.toUpperCase()) )
				{
					out.println(ncc.getString("nccTen") +" [ "+ncc.getString("masothue")+" ] "+" [ "+ncc.getString("diachi")+" ]"+ "\n");
				}
			}
			ncc.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("___LOI LOAD NCC: " + e.getMessage());
	}
		
%>