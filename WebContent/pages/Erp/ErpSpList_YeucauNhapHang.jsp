<%@page import="java.sql.SQLException"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %> 
<%@ page import = "java.net.URLDecoder" %>
 
<% 
	String congtyId = (String) session.getAttribute("congtyId");

	String maloaisp=(String)session.getAttribute("loaisanpham");
	String khottid = (String)session.getAttribute("khonhapId");
	String ndnhapId =  (String)session.getAttribute("noidungxuat");
	System.out.println("=============nd nhap============== "+ndnhapId);
	System.out.println("=============khott Id============== "+khottid);
	System.out.println("==========================="+maloaisp);
%>
<%

	dbutils db = new dbutils();
	try
	{

		
		response.setHeader("Content-Type", "text/html");
		
		NumberFormat formatter3 = new DecimalFormat("#######.######");
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		String query = (String)request.getQueryString(); 

	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	System.out.println(":::QUERY SAU: " + query);
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		String command = "";
 		
		if(ndnhapId.equals("100051")){
			maloaisp = null;
		}
		
		command = " select  top 50 a.pk_seq, a.MA,   a.TEN  as spTen,   dvdl.donvi as dvdl \n" +
			      " from  ERP_SANPHAM a "+
			      " left join donvidoluong dvdl on dvdl.pk_seq = a.dvdl_fk \n"+
			      " left join ERP_KHOTT_SANPHAM KHOSP on a.pk_seq=KHOSP.SANPHAM_FK \n" +
			      "	where  a.trangthai='1' and  a.timkiem like  '%"+query+"%'  and  a.congty_fk = '" + congtyId + "' \n";
				  
		
		if(maloaisp != null && maloaisp.length() > 0){
			command += 	"and a.loaisanpham_fk='"+maloaisp+"' \n"; 
		}
		
		if(khottid != null && khottid.length() >0 ){
			command += 	"and KHOSP.KHOTT_FK='"+khottid+"' \n";
		}
		command += " group by a.pk_seq, a.MA, a.TEN, dvdl.donvi ";
		
		System.out.println("command:\n" + command + "\n=============================");
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				
					String ma = Ult.replaceAEIOU(sp.getString("ma"));
					String ten = Ult.replaceAEIOU(sp.getString("spTen"));
					String tensp = sp.getString("spTen");
					 
					String dvdl = Ult.replaceAEIOU(sp.getString("dvdl"));
					 
					String	maSP = sp.getString("ma");
					out.print("###" + maSP + " - " + sp.getString("spTen") +  " [" + sp.getString("pk_seq") + "] [" + dvdl + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				 
			}	
			sp.close();
		}		  
 
		db.shutDown();
	}
	catch(Exception ex)
	{ 
		db.shutDown();
		ex.printStackTrace();
		System.out.println("Xay ra exception roi ban..."); 
	} 
%>

