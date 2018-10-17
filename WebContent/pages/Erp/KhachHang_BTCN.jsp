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
	String congtyId= (String)session.getAttribute("congtyId");

	String query = (String)request.getQueryString(); 
	System.out.println("query :" +query);
	String loaidoituong =(String)Utility.getParameter(query,"loaidoituong");
	String loaiTaiKhoan =(String)Utility.getParameter(query,"taikhoan"); 
	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	System.out.println("query :" +query);
	dbutils db = new dbutils();
	
	String sohieutaikhoan="";

	try
	{
		String command = 
			"select top 30 * from (\n" +
			"select '0,' + convert(nvarchar, kh.pk_seq) pk_seq, ('') ma, isNull(kh.MaFAST, '') mafast, isNull(kh.ten, '') ten\n" +
			"from TraphacoDMS..KHACHHANG kh\n" +
			"where kh.TRANGTHAI = 1 and (kh.mafast like N'%" + query +"%' or kh.ten like N'%" + query +"%')\n" +
			"and kh.PK_SEQ in\n" +
			"(\n" +
			"select ps.maDoiTuong from TraphacoDMS..ERP_PHATSINHKETOAN ps \n" +
			"inner join ERP_TAIKHOANKT tk on tk.pk_seq = ps.TAIKHOAN_FK and tk.npp_fk = 1\n" +
			"where ps.doiTuong = N'Khách hàng' and (ps.isNPP is null or ps.isNPP = 0) and ps.MADOITUONG = kh.PK_SEQ\n" +
			")\n"+ 
			"union all\n" +
			"select '1,' + convert(nvarchar, npp.pk_seq) pk_seq, isNull(npp.MA, '') ma, isNull(npp.MaFAST, '') mafast, isNull(npp.ten, '') ten\n" +
			"from NHAPHANPHOI npp\n" +
			"where npp.TRANGTHAI = 1 and npp.PK_SEQ != 1 and (npp.ma like N'%" + query +"%' or npp.mafast like N'%" + query +"%' or npp.ten like N'%" + query +"%')\n" +
			") a";
		
	   	System.out.println("cau lay doi tuong: \n" + command + "\n----------------------------------------------------------");
		request.setCharacterEncoding("UTF-8");		
	   
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		ResultSet doiTuongNoRs=db.get(command);

		int j = 0;
		if(doiTuongNoRs != null)
		{
			while(doiTuongNoRs.next())
			{
				out.println(doiTuongNoRs.getString("pk_seq") + "--" + j + " - " + doiTuongNoRs.getString("ma") + " - " + doiTuongNoRs.getString("maFast")  + " - " + doiTuongNoRs.getString("ten") + "\n");
				j++;
			}	
			if(doiTuongNoRs != null) doiTuongNoRs.close();
		}
	}
	catch(java.sql.SQLException ex){ 
		ex.printStackTrace(); 
	}
	finally
	{
		db.shutDown();
	}
%>