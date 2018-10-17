<%@page import="geso.dms.center.util.Utility"%>
<%@page import="geso.traphaco.erp.db.sql.dbutils"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.net.URLDecoder" %>

<%
dbutils db =new dbutils();
try {
	String command ="";
	command = "select PK_SEQ, MA, TEN from ERP_BIN";
	System.out.println("Vi tri:"+ command);
	
	response.setHeader("Content-Type", "text/html");
	request.setCharacterEncoding("UTF-8");
	
	String query = (String)request.getQueryString();
	System.out.println("Vi tri tim kiem:"+ query);
	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	
	Utility Ult = new Utility();
	query = Ult.replaceAEIOU(query);
	ResultSet vitri = db.get(command);
	System.out.println("Tim kiem:"+ command);
	int j = 0;
	if(vitri != null){
		while(vitri.next()){
			String id = vitri.getString("PK_SEQ");
			String ma = vitri.getString("MA");
			String ten = vitri.getString("TEN");
			if(ma.toUpperCase().startsWith(query.toUpperCase()) || ma.toUpperCase().indexOf(query.toUpperCase()) >= 0
			|| ten.toUpperCase().indexOf(query.toUpperCase()) >= 0 || ten.toUpperCase().indexOf(query.toUpperCase()) >= 0 ) {
				out.print("###[" + id+ "] " + ma + " - " + ten + "|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
			}
		}
	}
	vitri.close();
	db.shutDown();
	db=null;
} catch(Exception ex){
	if(db!=null){
		db.shutDown();
		db=null;
	}
}
%>