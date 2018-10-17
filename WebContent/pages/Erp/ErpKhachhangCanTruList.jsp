<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "geso.traphaco.center.util.Utility" %>
<%
	dbutils db = new dbutils();
	try
	{  
		System.out.println("Da  chay vao day");
		Utility util = new Utility();
		
		String nppTen = "";
		String ctyId = (String)session.getAttribute("congtyId");
		String kbhId = (String)session.getAttribute("kbhId");
		String userId = (String)session.getAttribute("userId");
		String npp_fk = util.getIdNhapp(userId);
		
		String command = 
/* 		"select cast(pk_seq as nvarchar(10)) + ' - ' + ten + ' -- 0' as nppTen \n" +
		"from TraphacoDMS..KHACHHANG \n" +
		"where trangthai = '1' \n" +
		"and PK_SEQ in (select ps.MADOITUONG\n" +
		"from TraphacoDMS..ERP_PHATSINHKETOAN ps \n" +
		"inner join ERP_TAIKHOANKT tk on tk.PK_SEQ = ps.TAIKHOAN_FK and tk.npp_fk = 1\n" +
		"where ps.DOITUONG = N'Khách hàng' and (ps.isNPP is null or ps.isNPP = 0)\n" +
		")\n" +

		" UNION ALL \n"+ */
		"select cast(pk_seq as nvarchar(10)) + ' - ' + ten + ' -- 1' as nppTen \n" + 
		"from NHAPHANPHOI WHERE TRANGTHAI = 1 and isKHACHHANG = 1 "+
		
		" UNION ALL "+
		"select cast(pk_seq as nvarchar(10)) + ' - ' + ten + ' -- 2' as nppTen from KHACHHANG WHERE TRANGTHAI = 1 and CONGTY_FK = "+ctyId;
		//if (ctyId != null) command = command + " and CONGTY_FK = " + ctyId + "";
		if (kbhId != null && kbhId.trim().length() > 0) command = command + " and KBH_FK = " + kbhId + "";
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("[ErpKhachhangList.jsp] npp command = \n" + command + "\n");
	 	System.out.println("[ErpKhachhangList.jsp] query = " + query);
	 	
	 	System.out.print("command:\n" + command + "\n------------------------------");
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
	}
	catch(SQLException e){
		e.printStackTrace();
	}
	finally{
		db.shutDown();
	}
%>