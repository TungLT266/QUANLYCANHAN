<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<% String nppId = (String) session.getAttribute("nppId"); %>
<% String kbhId = (String) session.getAttribute("kenhId"); %>
<% String khoId = (String) session.getAttribute("khoxuat"); %>
<%
	dbutils db = new dbutils();
	try
	{	
		
		String nhomkenhId = "100000"; //HOP DONG THI LUC NAO CUNG LAY NHOM KENH THAU
		String command = " select a.ma, a.ten, b.donvi, isnull(a.hansudung, 0) as hansudung, isnull( a.thuexuat, c.thuexuat) as thuexuat, " + 
						 " ISNULL( ( select sum(available) from NHAPP_KHO where KHO_FK = '" + khoId + "' and sanpham_fk = a.pk_seq  and npp_fk = '" + nppId + "' and nhomkenh_FK= '" + nhomkenhId + "' ), 0 ) as avai " +
						 " from SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq ";
		
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
					out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("thuexuat") + "] [" + sp.getString("avai") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}	
			}	
			sp.close();
		}
		
		db.shutDown();
	}
	catch(Exception ex)
	{
		db.shutDown();
		ex.printStackTrace();
		System.out.println("Xay ra exception roi ban..."); 
	}
%>

