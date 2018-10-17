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
		
		String nccTen = "";
		
		String command = "select  masothue + ' - ' + ten  + ' [ ' + diachi + ' ] ' + ' [ ' + cast(pk_seq as nvarchar(50)) + ' ] ' as nccTen " +
		 				 "from ERP_NHACUNGCAPTHAYTHE ";
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("[Erp_NhaCungCapList.jsp] ncc command = " + command );
	 	
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