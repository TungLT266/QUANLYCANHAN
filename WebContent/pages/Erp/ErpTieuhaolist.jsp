
<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.erp.db.sql.dbutils"%>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>


<% String khotieuhao = (String) session.getAttribute("khoTieuhao_fk"); %>
 


<%
	String loaichuyenkygui = "";
	String nccchuyenId ="";
	try{
	  loaichuyenkygui = (String) session.getAttribute("loaichuyenkygui");
	  nccchuyenId = (String) session.getAttribute("nccchuyenId");
	}
	catch(Exception er){
	 	
	}
	 dbutils db = new dbutils();
	try
	{
	 
		String command = "";
		response.setHeader("Content-Type", "text/html");
		String query = (String)request.getParameter("letters");
	 	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		
		if(khotieuhao.length() > 0)
		{
				command = " select  top 50 a.pk_seq, a.MA, a.TEN as spTen  ,b.AVAILABLE ,dvdl.donvi " +
						  "	from SANPHAM a inner join ERP_KHOTT_SANPHAM b on a.PK_SEQ = b.SANPHAM_FK "+  
						  " inner join donvidoluong dvdl on dvdl.pk_seq=a.dvdl_fk  " +
						  "	where timkiem like '%"+query+"%' and  b.KHOTT_FK = '" + khotieuhao + "' and b.AVAILABLE > 0  ";
  
						 System.out.println(command); 
						  
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
				 
						String tensp = sp.getString("spTen");
						if(tensp.length() > 30)
							tensp = tensp.substring(0, 30);
						out.print("###" + sp.getString("ma") + " - " + sp.getString("spTen") + " [" + sp.getString("donvi") + "] [" + sp.getString("AVAILABLE") + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				 
				}	
				sp.close();
			}		  
					  
		}
		
		
		db.shutDown();
		
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

