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
	
	
		String marqId = "";
		String marqTen = "";
		String marqMa = "";
		
		String command="";
		request.setCharacterEncoding("UTF-8");
	   
	 	Utility Ult = new Utility();
		
	   	String query = (String)request.getQueryString(); 
	   	System.out.println("QUERYString: " + query);
	   	String dieukien = new String(query.substring(query.indexOf("sanphamId=") + 10, query.indexOf("&q=")).getBytes("UTF-8"), "UTF-8");
	   	System.out.println("Dieu Kien: " + dieukien);
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
		query = Ult.replaceAEIOU(query);
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
	 	
		command = 	"select ma from MARQUETTE where 1=1 " ;
		command += 	
					"and " + 
				//	" (upper(cast(PK_SEQ as nvarchar(10))) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
				//	" or upper(MA) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) " +
					"  upper(ma) like upper((N'%" + Ult.replaceAEIOU(query).toUpperCase() + "%')) and sanpham_fk="+dieukien+" ";
	
	 	
	 	System.out.println("_____marrquet JSP: " + command);
	  	
	 	ResultSet kh = db.get(command);
	 	
		if(kh != null)
		{
			int m = 0;
				while(kh.next())
				{   
				//	marqId =  kh.getString("PK_SEQ");
					marqTen = kh.getString("ma");
				//	marqMa = kh.getString("MA");
	               
	           //     String marq = marqId + "-->[" + marqMa + "][" + marqTen + "]" ;
	           
	            	String marq = marqTen;
					
					out.println(marq + "\n");
						
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