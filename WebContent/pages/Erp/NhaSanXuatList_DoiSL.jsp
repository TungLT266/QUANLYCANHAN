<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Date" %>

<%
	String usedId = (String) session.getAttribute("userId");
	String nppId = (String) session.getAttribute("nppId");
	
	
	
	
	try
	{  
	
	dbutils db = new dbutils();
	
	
		String nsxId = "";
		String nsxTen = "";
		String nsxMa = "";
		
		String command="";
		request.setCharacterEncoding("UTF-8");
	   
	 	Utility Ult = new Utility();
		
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		query = Ult.replaceAEIOU(query);
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	
		command = 	"select PK_SEQ, MA, ten from erp_nhasanxuat where 1=1 " ;
		command += 	
					"and (upper(cast(PK_SEQ as nvarchar(10))) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
					" or upper(MA) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
					" or upper(TEN) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%'))) ";
	
	 	
	 	System.out.println("_____nhan san xuat JSP: " + command);
	  	
	 	ResultSet kh = db.get(command);
	 	
		if(kh != null)
		{
			int m = 0;
				while(kh.next())
				{   
					nsxId =  kh.getString("PK_SEQ");
					nsxTen = kh.getString("TEN");
					nsxMa = kh.getString("MA");
	               
	                String nsx = nsxTen + " - " + nsxId + "" ;
					
					out.println(nsx + "\n");
						
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