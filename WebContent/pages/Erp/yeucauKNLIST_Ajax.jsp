<%@page import="geso.traphaco.erp.beans.tieuchuankiemnghiem.IErpTieuChuanKiemNghiem"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.erp.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import="geso.traphaco.center.util.Utility"%>

<%
	IErpTieuChuanKiemNghiem tcknBean = (IErpTieuChuanKiemNghiem)session.getAttribute("tcknBean"); 
	String usedId = (String) session.getAttribute("userId");
	try
	{  
		String yeucau= "0";
		if(tcknBean!=null){
			yeucau=tcknBean.getYeucauIDSS();
		}
	
	dbutils db = new dbutils();
	
	String spId = "";
	String spMa = "";
	String spTen = "";
	
	String command="";
	request.setCharacterEncoding("UTF-8");
   
 	Utility Ult = new Utility();
	
   	String query = (String)request.getQueryString(); 
   	System.out.println("string: " + query);
   	query = new String(query.substring(query.indexOf("q=") + 2, query.indexOf("&limit=")).getBytes("UTF-8"), "UTF-8");
   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	//query = Ult.replaceAEIOU(query);
	//System.out.println("====================="+query);
	
	response.setHeader("Content-Type", "text/html; charset=UTF-8");
	
 	
	command = 	"select top 10 PK_SEQ, MA, TEN from erp_yeucaukythuat where 1=1 ";
	command += 	"and trangthai = '1' " +
				"and (upper(PK_SEQ) like upper((N'%" + query.toUpperCase() + "%')) " +
					" or upper(MA) like upper((N'%" + query.toUpperCase() + "%')) " +
				" or upper(TEN) like upper((N'%" +query.toUpperCase() + "%'))) and PK_SEQ not in ("+yeucau+") ";

 	
 	System.out.println("_____Lay san pham chiet khau JSP: " + command);
  	
 	ResultSet kh = db.get(command);
 	
	if(kh != null)
	{
		int m = 0;
			while(kh.next())
			{   
				spId =  kh.getString("PK_SEQ");
				spMa = spId + " - " + kh.getString("MA");
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