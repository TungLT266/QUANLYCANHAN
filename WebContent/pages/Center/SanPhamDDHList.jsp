<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>

<%
	String dvkdId = "";
	if(session.getAttribute("dvkdId") != null )
		dvkdId = (String) session.getAttribute("dvkdId");
	
	String kbhId = "";
	if(session.getAttribute("kbhId") != null )
		kbhId = (String) session.getAttribute("kbhId");
	
	String nppId = "";
	if(session.getAttribute("khachhangId") != null )
		nppId = (String) session.getAttribute("khachhangId");
	
	String doitacId = "-1";
	if(session.getAttribute("doitacId") != null )
		doitacId = (String) session.getAttribute("doitacId");
	System.out.println("vo day : ");
	if( dvkdId.trim().length() > 0 &&   nppId.trim().length() > 0 )
	{
		dbutils db = new dbutils();
		System.out.println("vo day 2: ");
		try
		{		
			String command = "select a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, " +
					"	cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 4) ) as qc,	" +
					" 	CAST(  isnull( ( select GIAMUANPP * ( 1 - isnull( ( select chietkhau from BANGGIAMUANPP_NPP where banggiamuaNPP_FK = bg_sp.bgmuaNPP_FK and NPP_FK = '" + nppId + "' ), 0) / 100 ) " +
					" 				from BGMUANPP_SANPHAM bg_sp " +
					"			    where SANPHAM_FK = a.pk_seq  " +
					"					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = '" + dvkdId + "'  order by bg.TUNGAY desc ) ), 0) as numeric(18,4) )  as giamua, isnull(a.thuexuat,0) as  thuexuat,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE " +
					"from ERP_SANPHAM a inner join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq " +
					" left join NGANHHANG c on a.nganhhang_fk = c.pk_seq " +
					"where a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "'  ";		 
			System.out.println("Lay san pham_command : " + command);
			
			response.setHeader("Content-Type", "text/html");
			String query = (String)request.getParameter("letters");
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					//double quycach = rs.get
					if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) || sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					{
						String tensp = sp.getString("ten");
						//out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("trongluong") + "] [" + sp.getString("thetich") + "] [" + sp.getString("qc") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "] [" + sp.getString("IS_KHONGTHUE") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}	
				}	
				sp.close();
			}
			
			db.shutDown();
		}
		catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
	}
%>

