<%@ page import="geso.traphaco.center.util.Utility"%>
<%@ page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page  import = "geso.traphaco.distributor.db.sql.dbutils" %>

<% dbutils db = new dbutils(); %>
<%	
	String command;
	String userId = (String) session.getAttribute("userId");
	Utility Ult = new Utility();
	
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
	
   	String query = (String)request.getQueryString(); 
   	String view = query;
   	//System.out.println("Query String: " + query);
   	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	query = Ult.replaceAEIOU(query);
    
   	command = "select SCHEME, DIENGIAI from CTTRUNGBAY order by NGAYKETTHUCTB desc ";
   	
	//System.out.println("___Lay SanPham: " + command);
	ResultSet sp = db.get(command);
	
	if(sp != null)
	{
		try
		{
			int m = 0;
			while(sp.next())
			{
				if(sp.getString("SCHEME") != null)
				{
					String maSP =  sp.getString("SCHEME");
					String tenSP = sp.getString("DIENGIAI");
					
					String masp =  Ult.replaceAEIOU(sp.getString("SCHEME"));
					String tensp = Ult.replaceAEIOU(sp.getString("DIENGIAI"));
					
					if( masp.trim().toUpperCase().contains(query.toUpperCase()) || tensp.toUpperCase().contains(query.toUpperCase()) )
					{
						out.print("###" + maSP + " --> " + tenSP + "|"); 					
						m++;
					}
				}
			}
			sp.close();
		}
		catch(Exception e){ System.out.println("EXCEPTION: " + e.getMessage()); }
	} 
    	
	db.shutDown();
%>