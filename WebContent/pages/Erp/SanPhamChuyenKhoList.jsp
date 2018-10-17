<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
 
<%@ page import = "java.net.URLDecoder" %>

<% String khochuyenIds = (String) session.getAttribute("khochuyenIds"); %>

<%

		String nccchuyenId = "";
		try{
			nccchuyenId =(String) session.getAttribute("nccchuyenId");
		}catch(Exception er){
			
		}
%>

<% String trangthaisp = (String) session.getAttribute("trangthaisp"); %>
<% String congtyId = (String) session.getAttribute("congtyId"); %>


<%
	dbutils db = new dbutils();
	try
	{
		
		response.setHeader("Content-Type", "text/html");
		//String query = (String)request.getParameter("letters");
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		String query = (String)request.getQueryString(); 
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
	   	
	   	
		if(khochuyenIds == null)
			khochuyenIds = "0";  //SP nhap kho
			
		if(trangthaisp == null)
			trangthaisp = "1";  //Da qua kiem dinh
			
		if(nccchuyenId == null)
			nccchuyenId = "";
		
		String command = "";
		
		command	=	" SELECT TOP(50) SP.MA , "+
		" sp.ten, "+ 
		" KHO.GIATON AS DONGIA, SP.PK_SEQ as spId, " + 
		" ISNULL(DVDL.DONVI, 'Chua xac dinh') AS DONVI, KHO.AVAILABLE AS HIENHUU " +
		" FROM ERP_KHOTT_SANPHAM KHO " +
		" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHO.SANPHAM_FK " +
		" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK  WHERE SP.Trangthai='1'  and  1=1 AND SP.TIMKIEM LIKE N'%"+query+"%' " ;

		if(khochuyenIds.length() > 0) { 
			command += "  AND  KHO.KHOTT_FK = " + khochuyenIds + "  ";
		}
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
		while(sp.next())
		{
			int hienhuu = sp.getInt("hienhuu");
			if(hienhuu <= 0)
				hienhuu = 0;
				String tensp = sp.getString("ten");
				out.print("###" + sp.getString("ma") + " -- " + tensp +" [" + sp.getString("donvi") + "][" + sp.getString("HIENHUU") + "][" + sp.getString("spId") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				
		}	
		}
		sp.close();
		db.shutDown();
		
	}
	catch(Exception ex){ 
		ex.printStackTrace();
		System.out.println("Xay ra exception roi ban..."); 
	}
%>