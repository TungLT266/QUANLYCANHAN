<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<% String ctyId = (String) session.getAttribute("ctyId"); %>
<% String lspId = (String) session.getAttribute("lspId"); %>
<% String lhhId = (String) session.getAttribute("lhhId"); %>
<% String nccId = (String) session.getAttribute("nccId"); %>
<% String nccLoai = (String) session.getAttribute("nccLoai"); %>

<%
	dbutils db = new dbutils();
	try
	{
		if(lhhId == null)
			lhhId = "0";  //SP nhap kho
		if(lspId == null)
			lspId = "";
		if(nccLoai == null)
			nccLoai = "";
		
		System.out.println("nccLoai:"+nccLoai);
		System.out.println("lhhId:"+lhhId);
		System.out.println("lspId:"+lspId);
		
		String command = "";
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		String query = (String)request.getQueryString(); 
	 
		String view = query;
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
	   	
	 
	   if(view.contains("donmuahang"))
	    {
	    	command = "select top 30 a.pk_seq, case when ( len(ltrim(rtrim(a.MA_FAST))) <= 0 or ( a.MA_FAST is null ) ) then a.MA_FAST else a.MA_FAST end as ma,  "+
	          		  " isnull(a.ten, '')  as ten , "+
	          		  " a.loaisanpham_fk, b.DONVI, 'NA' as nhomhang , a.DVDL_FK " +
			  		  "from SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
			  		  "where a.timkiem like '%"+query+"%'  ";
			
			System.out.println("Lay san pham / vat tu / tai san: " + command);
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
					while(sp.next())
					{
						out.print("###" + sp.getString("ma") + " -- " + sp.getString("ten") + " [" + sp.getString("donvi") + "] [" + sp.getString("pk_seq") + "] [" + sp.getString("DVDL_FK") + "] [" + sp.getString("nhomhang") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						System.out.print("###" + sp.getString("ma") + " -- " + sp.getString("ten") + " [" + sp.getString("donvi") + "] [" + sp.getString("pk_seq") + "] [" + sp.getString("DVDL_FK") + "] [" + sp.getString("nhomhang") + "]|");
					}	
					sp.close();
				
			}
    	}
	    
		db.shutDown();
		
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

