<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	Utility util = new Utility();
	String query = (String)request.getQueryString(); 
	System.out.println("query :" +query);
	String congTyId = (String)session.getAttribute("congtyId");
	dbutils db = new dbutils();
	
	String sohieutaikhoan="";
	String content = query.split("&")[0].split("=")[1];

	try
	{
		String command= 
			"SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, SOHIEUTAIKHOAN  + ' - ' + TENTAIKHOAN as ten \n" +
			"FROM ERP_TAIKHOANKT WHERE trangThai = 1 and DUNGCHODOITUONGKHAC = 1 and congTy_FK = " + congTyId;
		if(content.length() > 0){
			command +=  " and (SOHIEUTAIKHOAN like '%"+content+"%' or PK_SEQ like '%"+content+"%')";
		}
		
	   	System.out.println("cau lay tai khoan doi tuong khac: \n" + command + "\n----------------------------------------------------");
		request.setCharacterEncoding("UTF-8");		
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		ResultSet rs = db.get(command);

		if(rs != null)
		{
			while(rs.next())
			{
				out.println(rs.getString("PK_SEQ") + "--" + rs.getString("ten") + "\n");
			}	
			rs.close();
		}
	}
	catch(java.sql.SQLException ex){
		ex.printStackTrace();
	}finally{
		db.shutDown();
	}
%>