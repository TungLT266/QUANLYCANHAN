<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.distributor.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	dbutils db = new dbutils();
	try
	{
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		String search_tdv = request.getParameter("tdv");
		if( search_tdv == null )
			search_tdv = "";
		
	   	String query = (String)request.getQueryString(); 
	   	
	   	System.out.println("SEARCH TDV: " + search_tdv + " -- QUERY: " + query );
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		String command = "" ;
		
		if( !search_tdv.equals("1") )
		{
			command = " SELECT top 50  SP.PK_SEQ, isnull(SP.MA, '') as MA, isnull(SP.TEN, '')  AS TEN, DV.DIENGIAI as DVT " +
		 			  " FROM SANPHAM SP "+
		 			  " INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.DVDL_FK "+
		 			  " WHERE sp.trangthai='1' and sp.timkiem like N'%"+query+"%' "; 
		}
		else
		{
			command = " select maFAST + ' -- ' + TEN + ' [' + CAST( PK_SEQ as varchar(10) ) + ']' as ten " +
					  "	from DAIDIENKINHDOANH where trangthai = '1' and ( maFAST + ' -- ' + TEN + ' [' + CAST( PK_SEQ as varchar(10) ) + ']' ) like N'%" + query + "%' order by TEN asc "; 
		}
		
		System.out.println("SP KH CK - "+command);
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				if( !search_tdv.equals("1") )
				{
					out.print("###" + sp.getString("MA") + " - " + sp.getString("TEN")+ " - " + sp.getString("DVT")  + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
				else
				{
					out.print("###" + sp.getString("TEN") + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}
			}	
			sp.close();
		}
		
		db.shutDown();
		
	}
	catch(Exception ex)
	{ 
		System.out.println("Xay ra exception roi ban..." + ex.getMessage()); 
	}
%>

