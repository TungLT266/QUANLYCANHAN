<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page  import = "java.sql.ResultSet" %>
<%@page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@page import = "java.net.URLDecoder" %>
<%@page  import = "geso.traphaco.center.util.*" %>
<% String noibo = (String) session.getAttribute("noibo"); %>
<%
	try
	{  
		System.out.println("Noi bo la " + noibo);
		if(noibo==null)
		{
			noibo = "";
		}
		
		Utility uti = new Utility();
		String userId = (String)request.getSession().getAttribute("userId");
		String congtyId = (String)request.getSession().getAttribute("congtyId");
		
		dbutils db = new dbutils();
		
		String nccTen = "";
		String command = "";
		if(noibo.equalsIgnoreCase("1")){
			
			 /* command =  " select cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten + ' [ ' + cast(isnull(loainhacungcap_fk,0) as varchar(10)) + ' ]' as nccTen " +
				 		" from ERP_NHACUNGCAP where trangthai = '1' and noibo = '1' and ISNULL(TAIKHOAN_FK,0)!='0'" +
				 		" and CONGTY_FK =  " + congtyId + " "; */
			 
			//sua ngay 11_11_2016 Thu: gang ncc co tai khoan ke toan trong nuoc
			 command =  " select cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten + ' [ ' + cast(isnull(loainhacungcap_fk,0) as varchar(10)) + ' ]' as nccTen " +
		 		"\n from ERP_NHACUNGCAP where trangthai = '1' and noibo = '1' and ISNULL(TAIKHOAN_FK,0) in   (select PK_SEQ from ERP_TAIKHOANKT where npp_fk =1 and SOHIEUTAIKHOAN = 33111000)  " +
		 		"\n and CONGTY_FK =  " + congtyId + " ";
		}
		else{
			
			
			 /* command =   " select cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten + ' [ ' + cast(isnull(loainhacungcap_fk,0) as varchar(10)) + ' ]' as nccTen " +
		 				 " from ERP_NHACUNGCAP where trangthai = '1' and ISNULL(TAIKHOAN_FK,0)!='0'"+
		 				 "and CONGTY_FK = " + congtyId + " "; */
		 				 
		 	//sua ngay 11_11_2016 Thu: gang ncc co tai khoan ke toan trong nuoc			 
			command =   " select cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten + ' [ ' + cast(isnull(loainhacungcap_fk,0) as varchar(10)) + ' ]' as nccTen " +
			 "\n from ERP_NHACUNGCAP where trangthai = '1' and ISNULL(TAIKHOAN_FK,0) in   (select PK_SEQ from ERP_TAIKHOANKT where npp_fk =1 and SOHIEUTAIKHOAN = 33111000) "+
			 "\n and CONGTY_FK = " + congtyId + " ";
		}
		
		
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	query = uti.replaceAEIOU(query);
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc nha cung cap: " + command + "\n");
	 	System.out.println("Query la: " + query);
	 	
	 	ResultSet ncc = db.get(command);
	 	
		if(ncc != null)
		{
			while(ncc.next())
			{   
				nccTen = uti.replaceAEIOU(ncc.getString("nccTen"));
				
				if(nccTen.toUpperCase().contains(query.toUpperCase()) )
				{
					out.println(ncc.getString("nccTen") + "\n");
				}
			}
			ncc.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("___LOI LOAD NCC: " + e.getMessage());
	}
		
%>