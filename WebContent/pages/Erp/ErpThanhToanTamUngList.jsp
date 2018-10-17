<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<% String congtyId = (String) session.getAttribute("congtyId"); %>
<% String kbhId = (String) session.getAttribute("kenhid"); %>
<% String doituong = (String) session.getAttribute("doituong"); %>
<% String doituongkhac = (String) session.getAttribute("doituongkhac"); %>
<% String loaithanhtoan = (String) session.getAttribute("loaithanhtoan"); %>
<% String nhomncccn = (String) session.getAttribute("nhomncccn"); %>
<%
	try
	{  
		dbutils db = new dbutils();
		
		String khId = "";
		String khTen = "";
		String type="";
		
		if(doituong==null||doituong.equals("")){
			doituong="1";
		}
		
		if(doituongkhac==null||doituongkhac.equals("")){
			doituongkhac="1";
		}
		
		if(loaithanhtoan==null||loaithanhtoan.equals("")){
			loaithanhtoan="1";
		}
		
		if(nhomncccn==null||nhomncccn.equals("")){
			nhomncccn="";
		}
		
		System.out.println("loai thanh toan " + loaithanhtoan );
		System.out.println("loai doi tuong " + doituong );
		
		String command = "";
		if(doituong.equals("1") && doituongkhac.equals("1")){
			//if(!loaithanhtoan.equals("2")){
				if(nhomncccn.equals("1"))
				{
					command= "SELECT PK_SEQ, DIENGIAI AS TEN FROM NHOMNHACUNGCAPCN where TRANGTHAI = 1 AND congty_fk = "+congtyId+"";
				}
				else
				{
					command ="SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHACUNGCAP WHERE TRANGTHAI=1 AND CONGTY_FK = "+congtyId+";// and NOIBO!=1";
				}
			/* }
			else{
				command ="SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHACUNGCAP WHERE TRANGTHAI=1 and NOIBO=1";
			} */
		}else{
			command ="SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHANVIEN WHERE TRANGTHAI = 1 AND CONGTY_FK = "+congtyId+" "; 
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