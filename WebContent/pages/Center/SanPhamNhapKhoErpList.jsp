<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	dbutils db = new dbutils();
	try
	{		
		response.setHeader("Content-Type", "text/html");
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
		
		String loadvitriNHAN_KHONHAN = "";
		String query = (String)request.getQueryString(); 
	   	if( query.contains("layvitriNHANHANG") )
	   		loadvitriNHAN_KHONHAN = "1";
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	String command = "";
	   	if( loadvitriNHAN_KHONHAN.equals("1") )
		{
			String khonhanIds = "";  
			if( session.getAttribute("khonhanIds") != null )
				khonhanIds = session.getAttribute("khonhanIds").toString();
			
			command = "select pk_seq, MA + ', ' + TEN as TEN from ERP_BIN " + 
					  " where KHOTT_FK = '" + khonhanIds + "' and trangthai = '1' and timkiem like  '%"+query+"%' ";
		}
	   	else
	   	{
			command = " select a.ma, a.ten, b.donvi, isnull(a.hansudung, 0) as hansudung, isnull(c.thuexuat, 0) as thuexuat, ISNULL( ( select sum(available) from ERP_KHOTT_SANPHAM where KHOTT_FK = '100001' and sanpham_fk = a.pk_seq ), 0 ) as avai " +
					  "	from ERP_SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq LEFT join NGANHHANG c on a.nganhhang_fk = c.pk_seq where a.timkiem like  '%"+query+"%' ";
	   	}
	   	
		System.out.println("::: AJAX: " + command);
		
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				if( loadvitriNHAN_KHONHAN.equals("1") )
				{
					out.print("###" + sp.getString("TEN") +  " [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
				else
				{
					String tensp = sp.getString("ten");
					out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("thuexuat") + "] [" + sp.getString("avai") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					System.out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("hansudung") + "] [" + sp.getString("avai") + "]|");
				}
			}	
			sp.close();
		}
		
		db.shutDown();
	}
	catch(Exception ex)
	{ 
		db.shutDown();
		System.out.println("Xay ra exception roi ban..."); 
	}
%>

