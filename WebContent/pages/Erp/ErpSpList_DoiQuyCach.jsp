<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %> 
<%@ page import = "java.net.URLDecoder" %>
 
<% String congtyId = (String) session.getAttribute("congtyId"); %>
<%
	dbutils db = new dbutils();
	try
	{
		String khochuyenIds = (String) session.getAttribute("khochuyenIds");  
		
		String loadvitriNHAN = "";
		String laytaikhoan = "";
		String loadsanphamNHAN = "";
		
		response.setHeader("Content-Type", "text/html");
		
		NumberFormat formatter3 = new DecimalFormat("#######.######");
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		String query = (String)request.getQueryString(); 
	   	if( query.contains("loadvitriNHAN") )
	   		loadvitriNHAN = "1";
	   	else if( query.contains("laytaikhoan") )
	   		laytaikhoan = "1";
	   	else if( query.contains("loadsanphamNHAN") )
	   		loadsanphamNHAN = "1";
		
	   	System.out.println(":::QUERY TRUOC: " + query);
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	System.out.println(":::QUERY SAU: " + query);
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		String command = "";
		
		if( loadvitriNHAN.equals("1") )
		{
			String khonhanIds = "";  
			if( session.getAttribute("khonhanIds") != null )
				khonhanIds = session.getAttribute("khonhanIds").toString();
			
			command = "select pk_seq, MA + ', ' + TEN as TEN from ERP_BIN " + 
					  " where KHOTT_FK = '" + khonhanIds + "' and trangthai = '1' and timkiem like  '%"+query+"%' ";
		}
		else if( laytaikhoan.equals("1") )
		{
			command = " select pk_seq, SOHIEUTAIKHOAN + ' - ' + TENTAIKHOAN as ten " +
					  " from ERP_TAIKHOANKT " + 
					  " where npp_fk = '1' and ( sohieutaikhoan like N'%621%' or sohieutaikhoan like N'%622%' or sohieutaikhoan like N'%627%' ) " +
					  "		and sohieutaikhoan like  '%" + query + "%' ";
		}
		else if( loadsanphamNHAN.equals("1") )
		{
			command = " select top 50 a.pk_seq, a.MA, a.TEN as spTen, dvdl.donvi as dvdl, 0 as tonkho, 0 as giaton \n" +
				      " from ERP_SANPHAM a " +
				      " 	left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
				      "	where  a.trangthai='1' and  a.timkiem like  '%"+query+"%'   \n";
		}
		else
		{
			command = " select  top 50 a.pk_seq, a.MA ,   a.TEN    as spTen,   dvdl.donvi as dvdl, isnull(kho.available,0) as tonkho, 0 as giaton \n" +
				      " from dbo.ufn_tonhientai_full( )  kho inner join ERP_SANPHAM a on    kho.sanpham_fk=a.pk_seq and khott_fk="+ khochuyenIds + "\n" +
				      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
				      "	where  a.trangthai='1' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "' and isnull(kho.available,0) > 0  \n";
		}
		
		System.out.println("command:\n" + command + "\n=============================");
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				if( loadvitriNHAN.equals("1")  )
				{
					out.print("###" + sp.getString("TEN") +  " [" + sp.getString("pk_seq") + "]|"); 
				}
				else if( laytaikhoan.equals("1") )
				{
					out.print("###" + sp.getString("TEN") +  "|"); 
				}
				else
				{
					String	maSP = sp.getString("ma");
					out.print("###" + maSP + " - " + sp.getString("spTen") +  " [" + sp.getString("pk_seq") + "] [" + sp.getString("dvdl") + "] [" +formatter3.format(sp.getDouble("giaton")) + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
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

