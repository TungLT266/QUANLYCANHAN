<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<% String ctyId = (String) session.getAttribute("congtyId"); %>

<%
	dbutils db = new dbutils();
	try
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.#####"); 
		
		String command = "";
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		String query = (String)request.getQueryString(); 
	 
		String view = query;
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	
	  
	 	command = "select a.pk_seq, a.ten as ma,  a.diengiai as ten, '' as donvi, isnull(b.diengiai, 'NA') as nhomhang " +
				  "from erp_nhomchiphi a left join erp_trungtamchiphi b on a.ttchiphi_fk = b.pk_seq "+ 
				  "where a.trangthai = '1' and a.loai = '2' and ( a.diengiai like N'%"+query+"%' or a.ten like N'%"+query+"%' ) "+
				  "order by a.ten asc";	
	 	
	 	System.out.println(command);
	 	ResultSet sp = db.get(command);
	 	while(sp.next())
		{
			String maSP = sp.getString("ma");
			String tenSP = sp.getString("ten");
			String ma = sp.getString("ma"); 
			String tensp = sp.getString("ten");
			out.print("###" + sp.getString("pk_seq") + " -- " + maSP + " [" + tenSP + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "] [0] [ 0 ]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
			System.out.println("###" + sp.getString("pk_seq") + " -- " + maSP + " [" + tenSP + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "] [0] [ 0 ]|");
		}	
		sp.close();
		
		db.shutDown();
		
	}
	catch(Exception ex){
		ex.printStackTrace();
		System.out.println("Xay ra exception roi ban..."); }
%>

