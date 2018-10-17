<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.distributor.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import="geso.traphaco.center.util.Utility"%>

<%
    String nppId = (String) session.getAttribute("nppSearchId");
	try
	{  
	
	dbutils db = new dbutils();
	
	String smartId = "";
	String khId = "";
	String khTen = "";
	
	String command="";
	request.setCharacterEncoding("UTF-8");
   
 	Utility Ult = new Utility();
	
   	String query = (String)request.getQueryString(); 
   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	query = Ult.replaceAEIOU(query);
	
	response.setHeader("Content-Type", "text/html; charset=UTF-8");
	
	command =   "	select distinct b.maFAST, b.pk_seq as khId, b.ten as khTen, b.diachi  "+
				"	from khachhang b where b.trangthai = '1' and b.npp_fk = '" + nppId + "'  "+ 
				" 			and upper( timkiem_ten ) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
				"order by maFAST, khTen ";
 	System.out.println("_____Lay khach hang JSP: " + command);
  	
 	ResultSet kh = db.get(command);
 	
	if(kh != null)
	{
		while(kh.next())
		{   
			khId = kh.getString("maFAST");
			//khTen = khId + " - " + kh.getString("khTen") + " - " + kh.getString("diachi") + " -- " + kh.getString("khId");
			khTen = khId + " - " + kh.getString("khTen") + " -- " + kh.getString("khId");

			String khachhang = khTen + "|";
			
			out.println(khachhang + "\n");
		}
		kh.close();
	}
	db.shutDown();
	db=null;

	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
		
%>