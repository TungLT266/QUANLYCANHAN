<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>
<%
System.out.println("Vao sp list 2");
	dbutils db =new dbutils();
	String khoId = (String)session.getAttribute("khoId");
	 
	if(khoId == null) khoId = "";
	 
	try
	{
		if(khoId.trim().length() > 0)
		{
			String command ="";
			request.setCharacterEncoding("UTF-8");
			   
		   	String query = (String)request.getQueryString(); 
		   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
		   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
			Utility Ult = new Utility();
		   	query = Ult.replaceAEIOU(query);
		   	 
			command	=	" SELECT  TOP 50 SP.MA, "+ 
						"  sp.ten    as ten , KHO.GIATON AS DONGIA, SP.PK_SEQ as spId, " + 
						" ISNULL(DVDL.DONVI, 'Chua xac dinh') AS DONVI, KHO.AVAILABLE AS HIENHUU " +
						" FROM ERP_SANPHAM SP " +
						" left JOIN ERP_KHOTT_SANPHAM KHO  ON SP.PK_SEQ = KHO.SANPHAM_FK " +
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK " +
						" WHERE 1=1  and SP.trangthai='1' and sp.timkiem like N'%"+query+"%' ";
			
		 
			if(khoId.length() > 0) 
				command += " AND KHO.KHOTT_FK = " + khoId + " ";
 
			
			response.setHeader("Content-Type", "text/html; charset=UTF-8");
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					int hienhuu = sp.getInt("hienhuu");
					if(hienhuu <= 0)
						hienhuu = 0;
					
			 
						String tensp = sp.getString("ten");
						out.print("" + sp.getString("MA") + " -- " + tensp +" [" + sp.getString("donvi") + "][" +  sp.getString("DONGIA")+ "][" +  sp.getString("spId")+ "]\n"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				 
				}	
			}
			sp.close();
			db.shutDown();
		}
	}
	catch(Exception ex)
	{
		ex.printStackTrace();
		System.out.println("EXCEPTION lay SP2: " + ex.getMessage());
	}
%>

