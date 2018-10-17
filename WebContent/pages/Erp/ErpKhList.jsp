<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	try
	{  
	
		dbutils db = new dbutils();
		
		String nccTen = "";
 
	   	Utility Ult = new Utility();
		String congtyId = (String)request.getSession().getAttribute("congtyId");
		
		String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		query = Ult.replaceAEIOU(query);
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		 
	    
	   	String command = " select cast(pk_seq as nvarchar(10)) + ' -- ' + MA + ', ' + ten as nccTen  from ERP_KHACHHANG where trangthai='1' and CONGTY_FK = "+ congtyId +" ";
	   
	   	
	 	System.out.println("[Erp_KhList.jsp] 111.Lay duoc nha cung cap: " + command + "\n");
	 	
	 	ResultSet ncc = db.get(command);
	 	
		if(ncc != null)
		{
			while(ncc.next())
			{   
				
					nccTen = ncc.getString("nccTen");
					if(nccTen.toUpperCase().startsWith(query.toUpperCase()) || nccTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						out.println(nccTen + "\n");
					}
				 
			}
			ncc.close();
		}
			
		db.shutDown();
	}
	catch(SQLException e){}
		
%>