<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<% String congtyId = (String) session.getAttribute("congtyId"); %>
<% String userId = (String) session.getAttribute("userId"); %>
<% String kbhId = (String) session.getAttribute("kenhid"); %>

<% String ndxId = (String) session.getAttribute("ndxId"); %>

<%
	try
	{  
		Utility util = new Utility();
		
		dbutils db = new dbutils();
		
		String khId = "";
		String khTen = "";
		
		//String command = "select cast(pk_seq as nvarchar(10)) + ' -- ' + sitecode + ', ' + ten as nccTen from NHAPHANPHOI where trangthai='1'";
		
		String command = "";
		
		if(ndxId != null && ndxId.equals("100007") )
		{
			command = "select pk_seq, ma + ', ' + ten as nccTen from Erp_NhaCungCap where trangthai = '1' and pk_seq in " + util.quyen_nhacungcap(userId) + " ";
			
			if(congtyId != null)
			{
				if(congtyId.trim().length() > 0)
				{
					command += " and congty_fk = '" + congtyId.trim() + "' ";
				}
			}
		}
		else
		{
			command = "select pk_seq, ma + ', ' + ten as nccTen from Erp_KhachHang where trangthai = '1' and pk_seq in " + util.quyen_npp(userId) + " ";
			
			if(congtyId != null)
			{
				if(congtyId.trim().length() > 0)
				{
					command += " and congty_fk = '" + congtyId.trim() + "' ";
				}
			}
			
			if(kbhId != null)
			{
				if(kbhId.trim().length() > 0)
				{
					command += " and KenhBanHang_fk = '" + kbhId + "' ";
				}
			}
		}
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc nha cung cap / khach hang: " + command + "\n");
	 	
	 	ResultSet ncc = db.get(command);
	 	
		if(ncc != null)
		{
			while(ncc.next())
			{   
				khId = ncc.getString("pk_seq");
				khTen = ncc.getString("nccTen");
				
				if(khId.toUpperCase().contains(query.toUpperCase()) || khTen.toUpperCase().contains(query.toUpperCase()) )
				{
					out.println(khId + " -- " + khTen + "\n");
				}
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