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
 
<% String congtyId = (String) session.getAttribute("congtyId");  
String nccchuyenId = "";
		try{
			nccchuyenId =(String) session.getAttribute("nccchuyenId");
		}catch(Exception er){
			
		}
		String nppid="";
		try{
			nppid =(String) session.getAttribute("nppid");
		}catch(Exception er){
			
		}
		
		String khid_xuat = "";
		try{
			khid_xuat =(String) session.getAttribute("khid_xuat");
		}catch(Exception er){
			
		}
		String loaikhoxuat="";
		try{
			loaikhoxuat =(String) session.getAttribute("loaikhoxuat");
		}catch(Exception er){
			
		}
		
		String nvid_xuat="";
		try{
			nvid_xuat =(String) session.getAttribute("nvid_xuat");
		}catch(Exception er){
			
		}
		String khochuyenIds = "";
		try{
			khochuyenIds = (String) session.getAttribute("khochuyenIds"); 
		}catch(Exception er){
			
		}
		
		String khonhapId=(String) session.getAttribute("khonhanid"); 
		String nhomkenh_fk="100000";	
 
	dbutils db = new dbutils();
	try
	{
		   
		  //String nccchuyenId = (String) session.getAttribute("nccchuyenId");  
		
		
		response.setHeader("Content-Type", "text/html");
		//String query = (String)request.getParameter("letters");
		
		NumberFormat formatter3 = new DecimalFormat("#######.######");
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		String query = (String)request.getQueryString(); 
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	
	  
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
	   	
		String command = "";
 
					       
			
			command	=	" SELECT TOP(50) SP.MA ,  sp.ten,  0 AS DONGIA, SP.PK_SEQ as spId, "+
						" ISNULL(DVDL.DONVI, 'Chua xac dinh') AS DONVI,0 AS HIENHUU  FROM "+  
						" ERP_SANPHAM SP   "+
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = SP.DVDL_FK  "+ 
						" WHERE SP.Trangthai='1' and SP.CONGTY_FK="+congtyId+" and  1=1 and  SP.PK_SEQ in (select SANPHAM_FK from ERP_KHOTT_SANPHAM where KHOTT_FK='"+khonhapId+"') AND SP.TIMKIEM LIKE N'%"+query+"%'  " ;
			
			 
			System.out.println("Danh sách sản phẩm "+command);
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					 
					String ten =  sp.getString("ten");
			 
					String dvdl =  sp.getString("DONVI") ;
					 
					String	maSP = sp.getString("ma");
					out.print("###" + maSP + " - " +ten +  " [" + sp.getString("spId") + "] [" + dvdl + "] [" +formatter3.format(sp.getDouble("HIENHUU")) + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					 
				}	
				sp.close();
			}		  
 
		db.shutDown();
		
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

