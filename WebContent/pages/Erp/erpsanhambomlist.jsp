<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.traphaco.erp.db.sql.dbutils"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
 
<%@ page import = "java.net.URLDecoder" %>

<%-- <% String lspId = (String) session.getAttribute("lspId"); %>
<% String lhhId = (String) session.getAttribute("lhhId"); %> --%>
<% String nccId = (String) session.getAttribute("nccId"); %>
<%-- <% String nccLoai = (String) session.getAttribute("nccLoai"); %> --%>

<%
	dbutils db = new dbutils();
	try
	{
	/* 	if(lhhId == null)
			lhhId = "0";  //SP nhap kho
		if(lspId == null)
			lspId = "";
		if(nccLoai == null)
			nccLoai = ""; */
		
		String command = "";
		
			request.setCharacterEncoding("UTF-8");	
			response.setHeader("Content-Type", "text/html; charset=UTF-8");			
		 	
			String query = (String)request.getQueryString(); 
		   	
		   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
		   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		   	
		   	Utility Ult = new Utility();
		   	query = Ult.replaceAEIOU(query);
			
	 
		 			 
	 	command =   " SELECT TOP 50 A.PK_SEQ, A.MA AS MA, A.TEN + ' - ' + ISNULL(A.QUYCACH,'') AS TEN , "+
			 		" A.LOAISANPHAM_FK, B.DONVI, 'NA' AS NHOMHANG  "+
			 		" FROM ERP_SANPHAM A  LEFT JOIN DONVIDOLUONG B ON A.DVDL_FK = B.PK_SEQ "+ 
			 		" WHERE A.TRANGTHAI = '1'  AND A.TIMKIEM LIKE '%"+query+"%'  ";
 	  
		//System.out.println("[erpsanhambomlist.jsp]  coocococo :  " + command);
 
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				String maSP = Ult.replaceAEIOU(sp.getString("ma"));
				String tenSP = Ult.replaceAEIOU(sp.getString("ten"));
				
				if( maSP.toUpperCase().contains(query.toUpperCase()) || tenSP.toUpperCase().contains(query.toUpperCase()) )
				{
					String tensp = sp.getString("ten");
					out.print("###" + sp.getString("ma") + " -- " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}	
			}	
			sp.close();
		}
		
		db.shutDown();
		
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

