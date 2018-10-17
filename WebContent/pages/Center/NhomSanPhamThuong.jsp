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
   	System.out.println("Query String: " + query);
   	
   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
   	query = Ult.replaceAEIOU(query);
    
   	System.out.println("11.VIEEEEEEEEEEEELA: " + view);
    
   	if(view.indexOf("nhomthuong") >= 0)
   	{
		command = "select pk_seq, ten from nhomsanpham where trangthai = '1' and type = '4' " 
				+  " and pk_seq not in ( select NHOMSP_FK from TIEUCHITHUONGSKU_NHOMSP where TIEUCHITHUONGSKU_FK in (select pk_seq from TIEUCHITHUONGSKU where TRANGTHAI in (0, 1)) ) order by pk_seq desc";
   	}
   	else
   	{
   		command = "select ma as pk_seq, ten from sanpham where trangthai = '1' ";
   	}
   	
	System.out.println("___Lay SanPham: " + command);
	ResultSet sp = db.get(command);
	
	if(sp != null)
	{
		try
		{
			int m = 0;
			while(sp.next())
			{
				if(sp.getString("pk_seq") != null)
				{
					String maSP =  sp.getString("pk_seq");
					String tenSP = sp.getString("ten");
					
					String masp =  Ult.replaceAEIOU(sp.getString("pk_seq"));
					String tensp = Ult.replaceAEIOU(sp.getString("ten"));
					
					if(masp.trim().toUpperCase().startsWith(query.trim().toUpperCase()) ||
							masp.trim().toUpperCase().indexOf(query.trim().toUpperCase()) >= 0 || tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0)
					{
						if(tenSP.length() > 50)
							tenSP = tenSP.substring(0, 50);
						out.print("###" + maSP + " - " + tenSP + "|"); 
						
						m++;
						if(m > 40)
							break;
					}
				}
			}
			sp.close();
		}
		catch(SQLException e){}
	} 
    	
	db.shutDown();
%>