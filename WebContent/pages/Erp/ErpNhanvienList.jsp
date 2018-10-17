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
		
		String nvTen = "";
		
		String command = "select cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten + ' [ ' + cast(ISNULL(DVTH_FK, 0) as varchar(10)) + ' ]' as nvTen " +
		 				 "from ERP_NHANVIEN where TRANGTHAI = '1' AND CONGTY_FK = " + ctyId;
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("[Erp_NhanvienList.jsp] nv command = " + command );
	 	System.out.println("[Erp_NhanvienList.jsp] query = " + query );
	 	
	 	ResultSet nv = db.get(command);
	 	
		if(nv != null)
		{
			while(nv.next())
			{   
				nvTen = nv.getString("nvTen");
				if(nvTen.toUpperCase().startsWith(query.toUpperCase()) || nvTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					out.println(nvTen + "\n");
				}
			}
			nv.close();
		}
			
		db.shutDown();
	}
	catch(SQLException e){}
		
%>