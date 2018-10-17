<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	dbutils db = new dbutils();
	try
	{		
		String command = "select top(100) a.ma, a.ten   " +
						 "from Khachhang a "+
						 "where a.pk_seq > 0 and a.trangthai = '1'  ";		 
		
		response.setHeader("Content-Type", "text/html");
		//String query = (String)request.getParameter("letters");
		
		String query = (String)request.getQueryString(); 
			   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		
	   	command += " and a.timkiem like N'%" + query + "%' ";
	   	System.out.println("Lay KH_command : " + command);
	   	
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				String tensp = sp.getString("ten");
				//out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("trongluong") + "] [" + sp.getString("thetich") + "] [" + sp.getString("qc") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				out.print("###" + sp.getString("ma") + " - " + tensp +"|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
			}	
			sp.close();
		}
		
		db.shutDown();
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		System.out.println("Xay ra exception roi ban..."); 
	}
	
%>

