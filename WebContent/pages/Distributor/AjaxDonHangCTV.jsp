<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>

<%
		String khid = (String)session.getAttribute("nguoiban");
		String ctvid = (String)session.getAttribute("kbhId");
		
		dbutils db = new dbutils();
		
		try
		{				
			
			 String command = "";
			 command =  "select distinct a.pk_seq, a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, "+ 
					" cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ "+
					 " and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc "	+
					 "    "+
					 "from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq "+ 
					 " inner join ERP_DONDATHANGNPP_SANPHAM d on a.PK_SEQ = d.sanpham_fk inner join "+
					 " ERP_DonDatHangNPP e on d.dondathang_fk = e.PK_SEQ  "+  
					 "where a.pk_seq > 0  and e.TRANGTHAI in (2,4) ";

			 
							 
			System.out.println("Lay san pham: " + command);
			System.out.print("khid "+khid);
			response.setHeader("Content-Type", "text/html");
			String query = (String)request.getParameter("letters");
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(khid != null )
			if(sp != null)
			{
				while(sp.next())
				{
					//double quycach = rs.get
					if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) || sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					{
					String sql =	"select top(1) isnull(dongia,0) as dongia from ERP_HOPDONGNPP a inner join  ERP_HOPDONGNPP_sanpham b on a.PK_SEQ = b.hopdong_fk "+
									" where a.TRANGTHAI in (1,3) and sanpham_fk = '"+sp.getString("pk_seq") +"' and a.KHACHHANG_FK = '"+khid+"' "+
									" order by a.PK_SEQ asc ";
					System.out.println("Gia theo hop dong cu "+sql);
					ResultSet rs = db.get(sql);
					double dongia = 0;
					if(rs.next())
					{
						dongia = rs.getDouble("dongia");
					}
					String tensp = sp.getString("ten");
						
						//out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("trongluong") + "] [" + sp.getString("thetich") + "] [" + sp.getString("qc") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" +""+dongia+ "] [" + "0" + "] [" + "" + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}	
					
				}	
				sp.close();
			}
			
			db.shutDown();
		}
		catch(Exception ex)
		{ 
			ex.printStackTrace();
		}
	
	
	 
// 	 session.setAttribute("nguoiban", null);
// 	session.setAttribute("kbhId", null);
/*	session.setAttribute("nppId",null);
	session.setAttribute("lsxBean", null);
	session.setAttribute("khoId", null); */
	
%>

