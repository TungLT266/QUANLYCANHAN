
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
		String commandSoHieu= "SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " +loaiTaiKhoan ;
		ResultSet sohieuRs= db.get(commandSoHieu);
		if(sohieuRs.next())
		{
			sohieutaikhoan=sohieuRs.getString("SOHIEUTAIKHOAN").trim();
		}
		
		System.out.print("loaidoituong :" +loaidoituong);
		
		
		String command = "SELECT * \n " +
		"FROM (	\n " +
		"	SELECT 1 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[SP]' + ' - ' + MA AS MA, TEN, 0 AS SOHIEU \n " +
		", trangThai\n" +
		"	FROM ERP_SANPHAM  \n " +

		"	UNION ALL				 \n " +
		"	SELECT 2 AS LOAI, CONVERT(VARCHAR, a.PK_SEQ) AS PK_SEQ, '[NCC]' + ' - ' + a.MA AS MA, a.TEN, b.sohieutaikhoan as SOHIEU \n" +
		", a.trangThai\n" +
		" FROM ERP_NHACUNGCAP a left join erp_taikhoankt  b on a.taikhoan_fk = b.pk_seq  \n " +
		"	WHERE a.CONGTY_FK = " + congtyId;// + " AND TAIKHOAN_FK  = "+ sohieuTaiKhoan +" \n" ;
		if(loaidoituong.indexOf("2") >=0 && (sohieutaikhoan.indexOf("136")>= 0 || sohieutaikhoan.indexOf("336")>=0|| sohieutaikhoan.indexOf("338")>=0)){
			command = command + " AND ISNULL(b.SOHIEUTAIKHOAN,'') LIKE '"+sohieutaikhoan+"%'";
		}
		
		command = command + "	UNION ALL \n " +
		"	SELECT DISTINCT 3 AS LOAI,  CONVERT(VARCHAR, NH.PK_SEQ) AS PK_SEQ, '[NH]' + ' - ' + MA AS MA, TEN, 0 as SOHIEU  \n " +
		"   , NH.trangThai\n" +
		"	FROM ERP_NGANHANG NH  \n " +
		"	INNER JOIN ERP_NGANHANG_CONGTY NH_CTY ON NH_CTY.NGANHANG_FK = NH.PK_SEQ  \n " +
		"	WHERE NH_CTY.CONGTY_FK  = " + congtyId + "  \n " +

		"	UNION ALL \n " +
		"	SELECT 4 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[TS]' + ' - ' + MA AS MA, DIENGIAI AS TEN, 0 as SOHIEU \n" +
		"   , trangThai\n" +
		"   FROM ERP_TAISANCODINH  \n " +
		"	WHERE CONGTY_FK  = " + congtyId + " \n " +
	
		"	UNION ALL \n " +
		
		"	SELECT 5 AS LOAI, PK_SEQ,ISNULL( MA, '') AS MA, TEN, ISNULL(SOHIEU,0) AS SOHIEU  \n " +
		"   , trangThai\n" +
		"	FROM gopKHACHHANG  WHERE 1 = 1 \n " ;
		if(loaidoituong.indexOf("5") >=0 && (sohieutaikhoan.indexOf("136")>= 0 || sohieutaikhoan.indexOf("336")>=0)){
			command = command + " AND SOHIEU LIKE '"+sohieutaikhoan+"%'";
		}	

//		"	UNION ALL \n " +
//		"	SELECT 6 AS LOAI, PK_SEQ, MA, TEN FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " \n " +
		
		command = command +	"	UNION ALL  \n " +
		"	SELECT 7 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[NV]' + ' - ' + MA AS MA, TEN, 0 AS SOHIEU \n" +
		"   , trangThai\n" +
		"   FROM ERP_NHANVIEN WHERE CONGTY_FK = " + congtyId + "  \n " +

		"	UNION ALL  \n " +
		"	SELECT 8 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[DTK]' + ' - ' + MADOITUONG AS MA, TENDOITUONG AS TEN, 0 AS SOHIEU \n" +
		"   , trangThai\n" +
		"   FROM ERP_DOITUONGKHAC WHERE TRANGTHAI = 1  \n " + //WHERE CONGTY_FK = " + congtyId + "
//		"	AND SOHIEUTAIKHOAN LIKE '" + sohieutaikhoan + "%' \n " +
		



		"	UNION ALL  \n " +
		"	SELECT 9 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[MSCL]' + ' - ' + MA AS MA, TEN, 0 AS SOHIEU \n" +
		"   , trangThai \n" +
		"   FROM ERP_MASCLON WHERE TRANGTHAI = 1  \n " + //WHERE CONGTY_FK = " + congtyId + "
//		"	AND SOHIEUTAIKHOAN LIKE '" + sohieutaikhoan + "%' \n " +


	"	UNION ALL  \n " +
		"	SELECT 10 AS LOAI, CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, '[CPTT]' + ' - ' + MA AS MA, DIENGIAI AS TEN, 0 AS SOHIEU \n" +
		"   , trangThai\n" +
		"   FROM ERP_CONGCUDUNGCU WHERE TRANGTHAI = 0  \n " + //WHERE CONGTY_FK = " + congtyId + "
//		"	AND SOHIEUTAIKHOAN LIKE '" + sohieutaikhoan + "%' \n " +



		")RS WHERE RS.LOAI IN (" + loaidoituong + ")";
		if(query.trim().length()>0)
		{
			command = command +	"	AND (RS.MA like N'%"+query+"%' OR RS.TEN LIKE N'%"+query+"%' ) ";
		}
		
		
	   	System.out.println("cau lay doi tuong: "+command);
		request.setCharacterEncoding("UTF-8");		
	   

		
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		ResultSet doiTuongNoRs=db.get(command);

		int j = 0;
		if(doiTuongNoRs != null)
		{
			while(doiTuongNoRs.next())
			{
// 				if( doiTuongNoRs.getString("ma").toUpperCase().startsWith(query.toUpperCase()) ||doiTuongNoRs.getString("ma").toUpperCase().indexOf(query.toUpperCase()) >= 0 
// 				   || doiTuongNoRs.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 || doiTuongNoRs.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					String tenDoiTuong = doiTuongNoRs.getString("ten");
					out.println(doiTuongNoRs.getString("loai") +"," +doiTuongNoRs.getString("PK_SEQ") + "--" + doiTuongNoRs.getString("ma") + "-" + tenDoiTuong   + "\n");
				}	
			}	
			if(doiTuongNoRs != null) doiTuongNoRs.close();
		}
		
		db.shutDown();
		
	}
	catch(java.sql.SQLException ex){ System.out.println("Xay ra exception roi ban..." + ex.toString()); }
%>

