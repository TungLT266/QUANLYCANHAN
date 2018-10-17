<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>

<%
	dbutils db = new dbutils();
	try
	{		
		String khoXUAT = (String)session.getAttribute("khoxuatID");
		if(khoXUAT == null)
			khoXUAT = "";
		
		String kenhXUAT = (String)session.getAttribute("kenhxuatID");
		if(kenhXUAT == null)
			kenhXUAT = "";
		
		String nppId = (String)session.getAttribute("nppID");
		if(nppId == null)
			nppId = "";
		System.out.println("----NPP ID: " + nppId);
		
		if(khoXUAT.trim().length() > 0 && kenhXUAT.trim().length() > 0 && nppId.trim().length() > 0 )
		{
			String command = " select a.ma, a.ten, b.donvi, isnull(a.hansudung, 0) as hansudung, isnull(c.thuexuat, 0) as thuexuat, ISNULL( ( select sum(available) from NHAPP_KHO_CHITIET where KHO_FK = '" + khoXUAT + "' and KBH_FK = '" + kenhXUAT + "' and NPP_FK = '" + nppId + "' and sanpham_fk = a.pk_seq ), 0 ) as avai " +
						 	 "	from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq ";
			
			System.out.println("Lay san pham: " + command);
			
			response.setHeader("Content-Type", "text/html");
			String query = (String)request.getParameter("letters");
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) || sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					{
						String tensp = sp.getString("ten");
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("hansudung") + "] [" + sp.getString("avai") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}	
				}	
				sp.close();
			}
			
			db.shutDown();
		}
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

