<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>

<% String dvkd = (String) session.getAttribute("dvkd"); %>
<% String nhanhangId = (String) session.getAttribute("nhanhangId"); %>
<% String chungloaiId = (String) session.getAttribute("chungloaiId"); %>

<%
	dbutils db = new dbutils();
	try
	{
		
		String command ="select a.pk_seq, a.ma, a.ten,b.donvi" +
						" from SanPham a inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
						" where a.TRANGTHAI = '1' " ;
						
	
		System.out.println(dvkd);
		System.out.println("Lay san pham: " + command);
		
		response.setHeader("Content-Type", "text/html");
		String query = (String)request.getParameter("letters");
		
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				
				if(sp.getString("ma").toUpperCase().startsWith(query.toUpperCase()) ||sp.getString("ma").toUpperCase().indexOf(query.toUpperCase()) >= 0 
						|| sp.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 || sp.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					String tensp = sp.getString("ten");
					out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}	
			}	
			sp.close();
		}
		db.shutDown();
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

