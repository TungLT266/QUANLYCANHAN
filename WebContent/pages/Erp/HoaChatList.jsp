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
		System.out.println("query trong HoaChatList: " + query);
		if (view.equals("hoaChat")) {
			command = "SELECT SP.PK_SEQ AS PK_SEQSP, SP.MA, SP.TEN, DVDL.DIENGIAI FROM ERP_SANPHAM SP"
					+" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK"
					+" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ = SP.LOAISANPHAM_FK"
					+" WHERE LSP.PK_SEQ = 100000 AND ((SP.TEN LIKE N'%"+query+"%') or (SP.MA like  '%"+query+"%'))";

			System.out.println("Lay hoa chat "+ command);
			ResultSet hc = db.get(command);
			int j = 0;
			if (hc != null) {
				while (hc.next()) {
					out.print("###" + hc.getString("MA") + " -- " +hc.getString("TEN")+ " [" + hc.getString("DIENGIAI") + "] [" + hc.getString("PK_SEQSP") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
				hc.close();

			}
		} else if (view.equals("mayMoc")){
			command = "SELECT PK_SEQ, MA, TEN FROM ERP_TAISANCODINH WHERE (TEN LIKE '%"+query+"%') or (MA LIKE '%"+query+"%') ";
			System.out.println("Lay san pham / vat tu / tai san: "
					+ command);
			ResultSet vt = db.get(command);
			int j = 0;
			if (vt != null) {
				while (vt.next()) {

					out.print("###" + vt.getString("MA") + " -- " +vt.getString("TEN")+ " [" + vt.getString("PK_SEQ") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
				vt.close();

			}
		}
		

		db.shutDown();
	} catch (Exception ex) {
		System.out.println("Xay ra exception roi ban...");
	}
%>
