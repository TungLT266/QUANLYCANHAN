<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<% String congtyId = (String) session.getAttribute("congtyId"); %>
<%
	try
	{  
		dbutils db = new dbutils();
		
		String command = "";

		command= 	"SELECT NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, NH.TEN + ' - ' + CN.TEN + ', ' + N'Mã số thuế: ' + ISNULL(NH.MASOTHUE, N'N/A') AS NHTEN " +
					"FROM ERP_NGANHANG_CONGTY NHCTY " +
					"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK " +
					"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NHCTY.CHINHANH_FK " +
					"WHERE NHCTY.TIENTE_FK = 100000 AND NH.TRANGTHAI = 1";
		
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc nha cung cap: " + command + "\n");
	 	
	 	ResultSet nganhang = db.get(command);
	 	
	 	String nhId = "";
	 	String nhTen = "";
		if(nganhang != null)
		{
			while(nganhang.next())
			{   
				nhId = nganhang.getString("NHID") + ":" + nganhang.getString("CNID");
				nhTen = nganhang.getString("NHTEN");
				
				
				if(nhTen.toUpperCase().startsWith(query.toUpperCase()) || nhTen.toUpperCase().indexOf(query.toUpperCase()) >= 0   || nhId.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					out.println(nhTen + " [ " + nhId +" ]|");
					System.out.println("Lay duoc ngan hang: "+ nhId + " -- " + nhTen );
				}
			}
			nganhang.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("1.Exception: " + e.getMessage());
	}
		
%>



<%-- <% String congtyId = (String) session.getAttribute("congtyId"); %>
<% String kbhId = (String) session.getAttribute("kenhid"); %>
<% String doituong = (String) session.getAttribute("doituong"); %>
<%
	try
	{  
		dbutils db = new dbutils();
		
		String khId = "";
		String khTen = "";
		String type="";
		
		if(doituong==null){
			doituong="1";
		}
		
		String command = "";
		if(doituong.equals("0")){
			command ="SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHANVIEN WHERE TRANGTHAI=1  ";			
		}else{
			command ="SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHACUNGCAP WHERE TRANGTHAI=1";
		}
		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc nha cung cap: " + command + "\n");
	 	
	 	ResultSet ncc = db.get(command);
	 	
		if(ncc != null)
		{
			while(ncc.next())
			{   
				khId = ncc.getString("pk_seq");
				khTen = ncc.getString("Ten");
				
				
				if(khTen.toUpperCase().startsWith(query.toUpperCase()) || khTen.toUpperCase().indexOf(query.toUpperCase()) >= 0   || khId.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					out.println(khId + " -- " + khTen +"|");
					System.out.println("Lay duoc nha cung cap: "+ khId + " -- " + khTen );
				}
			}
			ncc.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("1.Exception: " + e.getMessage());
	}
		
%> --%>