<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	dbutils db = new dbutils();
	try
	{
		
		String command = "";
		String ltsId = (String)session.getAttribute("ltsId");
		if(ltsId == null) ltsId = "";
			
		command = " select top(30) cast(a.pk_seq as nvarchar(50)) as id, a.ma, a.diengiai as ten , isnull(a.dvt, 'NA') as donvi, isnull(b.ma, 'NA') as nhomhang " + 
				  "	from erp_taisancodinh a " + 
				  " left join erp_nhomtaisan b on a.NhomTaiSan_fk = b.pk_seq " +
				  "	where a.trangthai =  '1' ";
		if(ltsId.length() > 0){
			command = command + " and a.LOAITAISAN_FK = " + ltsId + " ";
		}
		
		System.out.println("Lay tai san: " + command);
		
		
	   	System.out.println(command);
		request.setCharacterEncoding("UTF-8");		
	   	String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	command += " and (a.ma like N'%"+query +"%' or  a.diengiai like N'%"+query +"%' )";
	   	command = command + " order by a.ma asc";
		
	   	System.out.println("Lay TS:   " + command);
		
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		
		System.out.println(query);
		
		ResultSet ts = db.get(command);
		int j = 0;
		if(ts != null)
		{
			while(ts.next())
			{
				if(ts.getString("id").startsWith(query.toUpperCase()) || ts.getString("ma").toUpperCase().startsWith(query.toUpperCase()) ||ts.getString("ma").toUpperCase().indexOf(query.toUpperCase()) >= 0 
				   || ts.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 || ts.getString("ten").toUpperCase().indexOf(query.toUpperCase()) >= 0 )
				{
					String tensp = ts.getString("ten");
					out.print( ts.getString("id") + "-" + ts.getString("ma") + "-" + tensp  + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				}	
			}	
			if(ts != null) ts.close();
		}
		
		db.shutDown();
		
	}
	catch(java.sql.SQLException ex){ System.out.println("Xay ra exception roi ban..." + ex.toString()); }
%>

