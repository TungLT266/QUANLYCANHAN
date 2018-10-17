<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<% String doituongdinhkhoan = (String) session.getAttribute("doituongdinhkhoan"); %>
<% String congtyId = (String) session.getAttribute("congtyId"); %>

<%
	try
	{  
		dbutils db = new dbutils();
		
		String pk_seq = "";
		String ma = "";
		String ten = "";		
		
		String command = "";
		
		if(doituongdinhkhoan.equals("1")) // dung cho kho
		{
			command = "select PK_SEQ, MA , TEN from SANPHAM";
		}
		else if(doituongdinhkhoan.equals("2")) // dung cho ngan hang
		{
			command = "select PK_SEQ, MA , TEN from ERP_NGANHANG";
		}
		else if(doituongdinhkhoan.equals("3")) // dung cho ncc
		{
			command = "select PK_SEQ, MA , TEN from ERP_NHACUNGCAP WHERE CONGTY_FK = "+congtyId+"";
		}
		else if(doituongdinhkhoan.equals("4")) // dung cho tai san
		{
			command = "select PK_SEQ, MA , TEN from ERP_TAISANCODINH";
		}
		else if(doituongdinhkhoan.equals("5")) // dung cho khach hang
		{
			command = "select PK_SEQ, MA , TEN from NHAPHANPHOI";
		}
		else if(doituongdinhkhoan.equals("6")) // dung cho nhan vien
		{
			command = "select PK_SEQ, MA , TEN from ERP_NHANVIEN WHERE CONGTY_FK  = "+congtyId;
		}
		

		request.setCharacterEncoding("UTF-8");
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc tai khoan: " + command + "\n");
	 	
	 	ResultSet kt = db.get(command);
	 	
		if(kt != null)
		{
			while(kt.next())
			{   
				pk_seq = kt.getString("pk_seq");
				ma = kt.getString("ma");
				ten = kt.getString("ten");
				
				
				if(ten.toUpperCase().startsWith(query.toUpperCase()) || ten.toUpperCase().indexOf(query.toUpperCase()) >= 0   || pk_seq.toUpperCase().indexOf(query.toUpperCase()) >=0)
				{
					out.println(pk_seq + " -- " +ma+ "," + ten +"|");
					System.out.println("Lay duoc doi tuong tai khoan: "+ pk_seq + " -- " + ma +","+ ten );
				}
			}
			kt.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("1.Exception: " + e.getMessage());
	}
		
%>