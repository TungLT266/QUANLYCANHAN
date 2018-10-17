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
    String nppId = (String) session.getAttribute("nppId");
	String ddkdId = (String) session.getAttribute("ddkdId");
	String usedId = (String) session.getAttribute("userId");
	try
	{  
	
	dbutils db = new dbutils();
	
	String spId = "";
	String spMa = "";
	String spTen = "";
	
	String command="";
	request.setCharacterEncoding("UTF-8");
   
 	Utility Ult = new Utility();
	
   	String query = (String)request.getQueryString(); 
   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	query = Ult.replaceAEIOU(query);
	
	response.setHeader("Content-Type", "text/html; charset=UTF-8");
	
 	
	command = 	"select top(50) PK_SEQ, MA, TEN from SANPHAM where PK_SEQ not in (select SANPHAM_FK from BANGGIABANLEKH_SANPHAM) ";
	command += 	"and trangthai = '1' " +
				"and upper(timkiem) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) ";

 	
 	System.out.println("_____Lay sp JSP: " + command);
  	
 	ResultSet kh = db.get(command);
 	
	if(kh != null)
	{
		int m = 0;
			while(kh.next())
			{   
				spId =  kh.getString("PK_SEQ");
				spMa = kh.getString("MA") + "-" + spId;
				spTen = kh.getString("TEN");
               
                String sp = spMa + "-->[" + spTen + "]";
				//System.out.println("khachhang : "+khachhang);
				out.println(sp + "\n");
					
				m++;
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