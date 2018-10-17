<%@page import="geso.traphaco.erp.db.sql.dbutils"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.dms.center.util.*"%>
<%@ page import="java.net.URLDecoder"%>


<%
	dbutils db = new dbutils();
	try {

		String userId = (String) session.getAttribute("userId");

		String command = "";

		request.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		String query = (String) request.getQueryString();
		System.out.println(query);
		String view = query.substring(0, query.indexOf("="));
		System.out.println("View: "+view);
		query = new String(query.substring(
				query.indexOf("&letters=") + 9, query.length())
				.getBytes("UTF-8"), "UTF-8");
		query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		// query se la cai ky tu ma nguoi dung danh vao o tim kiem
		Utility Ult = new Utility();
		query = Ult.replaceAEIOU(query);
		//Lay cai chu ma minh danh trong input
		System.out.println("query trong BOM_VATTU: " + query);
			command = "SELECT SP.[PK_SEQ]\r\n" + 
					"      ,[MA]\r\n" + 
					"      ,[TEN]\r\n" + 
					"	  ,[DIENGIAI]\r\n" + 
					"  FROM [dbo].[ERP_SANPHAM] SP\r\n" + 
					"  LEFT JOIN [dbo].[DONVIDOLUONG] DVT ON SP.DVDL_FK = DVT.PK_SEQ \r\n" + 
					"  WHERE SP.[LOAISANPHAM_FK] IN( 100000, 100013, 100008) AND (SP.TEN LIKE '%"+query+"%' OR SP.MA LIKE '%"+query+"%')";
			
			System.out.println("Lay BOM "+ command);
			ResultSet bom = db.get(command);
			int j = 0;
			if (bom != null) {
				while (bom.next()) {
					out.print("###" + bom.getString("MA") + " -- " +bom.getString("TEN")+ " [" + bom.getString("DIENGIAI") + "] [" + bom.getString("PK_SEQ") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
				bom.close();

			}
		

		db.shutDown();
	} catch (Exception ex) {
		System.out.println("Xay ra exception roi ban...");
	}
%>
