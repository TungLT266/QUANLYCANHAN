<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<%
	try
	{  
		System.out.println("Da  chay vao day");
		dbutils db = new dbutils();
		
		String nppTen = "";
		
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		

		String command = "select cast(pk_seq as nvarchar(10)) + '--'+ ma +', '+ ten as nppTen from ERP_NHANVIEN where trangthai = '1'"
				+" AND (ma like N'%"+query+"%' OR TEN like N'%"+query+"%')";
		String ctyId = (String)session.getAttribute("congtyId");
		if (ctyId != null) command = command + " and CONGTY_FK = " + ctyId + "";
	 	System.out.println("[ErpKhachhangList.jsp] npp command = " + command);
	 	System.out.println("[ErpKhachhangList.jsp] query = " + query);
	 	ResultSet npp = db.get(command);
		if(npp != null)
		{
			while(npp.next())
			{   
				nppTen = npp.getString("nppTen");
				if(nppTen.toUpperCase().startsWith(query.toUpperCase()) || nppTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					out.println(nppTen + "\n");
				}
			}
			npp.close();
		}
			
		db.shutDown();
	}
	catch(SQLException e){
		System.out.println("[ErpKhachhangList.jsp] Exception Message = " + e.getMessage());
	}
		
%>