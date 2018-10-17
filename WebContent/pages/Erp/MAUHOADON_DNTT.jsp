<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	dbutils db = new dbutils();

	try
	{System.out.print("aaaaaaaaaaaaaaaaaaa");
	
	String query = (String) request.getQueryString();

//	String view = query;

query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
//	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
//	String queryU = query;

System.out.println("cau query: "+ query);
	
		String command = "  select  + isNull(MAUHOADON, '') as mauhoadon from MAUHOADON_DNTT where MAUHOADON like N'%" + query + "%'";
		
	   	System.out.println("cau lay mau hoa don DNTT: "+ command);
		request.setCharacterEncoding("UTF-8");		
	   
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		ResultSet rs = db.get(command);

		int j = 0;
		if(rs != null)
		{
			while(rs.next())
			{
				out.println(rs.getString(1) + "\n");
			}	
			if(rs != null) 
				rs.close();
		}
		
		db.shutDown();
	}
	catch(java.sql.SQLException ex){ 
		ex.printStackTrace();
	}
%>