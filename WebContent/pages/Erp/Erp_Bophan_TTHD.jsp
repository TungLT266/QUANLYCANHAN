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
		Utility uti = new Utility();
		String userId = (String)request.getSession().getAttribute("userId");
		String ctyId = (String)request.getSession().getAttribute("congtyId");
		dbutils db = new dbutils();
		
		String bpTen = "";
		
		String command = "select  cast(pk_seq as nvarchar(10)) + ' -- ' + ma + ', ' + ten   as bpTen " +
		 				 "from ERP_DONVITHUCHIEN "+
		 				 "where trangthai = 1";
		
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("[Erp_Bophan_TTHD.jsp] bp command = " + command );
	 	
	 	ResultSet bp = db.get(command);
	 	
		if(bp != null)
		{
			while(bp.next())
			{   
				bpTen = bp.getString("bpTen");
				if(bpTen.toUpperCase().startsWith(query.toUpperCase()) || bpTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					out.println(bpTen + "\n");
				}
			}
			bp.close();
		}
			
		db.shutDown();
	}
	catch(SQLException e){}
		
%>