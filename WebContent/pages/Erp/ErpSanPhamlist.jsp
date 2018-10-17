<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page  import = "java.sql.ResultSet" %>
<%@page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@page import = "java.net.URLDecoder" %>
<%@page  import = "geso.traphaco.center.util.*" %>

<%
	try
	{  
		//String timTen= (String)request.getSession().getAttribute("tensanpham");
		//System.out.println("Noi bo la " + noibo);
				
		Utility uti = new Utility();
		String userId = (String)request.getSession().getAttribute("userId");
		String congtyId = (String)request.getSession().getAttribute("congtyId");
		
		dbutils db = new dbutils();
		String command ="";
		
	   
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	query =query;

		System.out.println("timTem : "+query);
		
			command = "select pk_seq, ma ,ten  \n"
			+" from erp_sanpham where congty_fk='"+congtyId+ "' "
						+"and ma like '%"+query.trim()+"%' or ten like N'%"+query.trim()+"%' ";
			
			System.out.println("command "+command);
			request.setCharacterEncoding("UTF-8");
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	System.out.println("Lay duoc nha cung cap: " + command + "\n");
	 	System.out.println("Query la: " + query);
	 	
	 	ResultSet rsSp = db.get(command);
	 	String pk_seq="",tensp="",masp="";
		if(rsSp != null)
		{
			while(rsSp.next())
			{   
				
				pk_seq = rsSp.getString("pk_Seq");
				tensp = rsSp.getString("ten");
				masp = rsSp.getString("ma");
				
				
				out.print("" +pk_seq +" -- "+masp+ " [ "+tensp + " ]\n"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				System.out.println(pk_seq +" -- "+masp+ " [ "+tensp + " ]\n");
			}
			rsSp.close();
		}
			
		db.shutDown();
	}
	catch(Exception e)
	{
		System.out.println("___LOI LOAD NCC: " + e.getMessage());
	}
		
%>