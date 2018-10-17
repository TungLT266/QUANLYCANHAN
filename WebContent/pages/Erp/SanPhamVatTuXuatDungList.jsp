<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.dms.db.sql.dbutils" %>
<%@ page  import = "geso.dms.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<%
	dbutils db = new dbutils();
	try
	{
		String command = "";
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		String query = (String)request.getQueryString(); 
	  	
	  	String khoId = "";
	  	System.out.println("cau query: " + query);
	  	String[] arr1 = query.split("&");
	  	String[] arr2 = arr1[0].split("_");
	  	if (arr2.length > 1)
	  		khoId = arr2[1].split("%3E")[0];
		String view = query;
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
	   	
	 	System.out.println("query tim san pham: \n" + query + "\n=============");
    	if (khoId.trim().length() > 0)
		{
			command = 
				"select top 30 a.pk_seq \n" + 
				"	, a.ma \n" + 
				"	, a.ma as machitiet \n" + 
				"	, ( isnull(a.ten, '') + '('  \n" + 
				"		+ case when substring(LTRIM(isnull(a.QUYCACH,'')), 1, 1 ) = 'x'  \n" + 
				"			then STUFF(LTRIM(isnull(a.QUYCACH,'')),1,1,'')  \n" + 
				"			else isnull(a.QUYCACH,'') end   \n" + 
				"		+ ')') as ten \n" + 
				"	, a.loaisanpham_fk, b.DONVI, 'NA' as nhomhang   \n" + 
				"	, (select AVAILABLE from ERP_KHOTT_SANPHAM where SANPHAM_FK = a.PK_SEQ and KHOTT_FK = " + khoId + " ) as soLuong \n" +
				"from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ  \n" + 
				"where a.timkiem like '%" + query + "%' and  a.TRANGTHAI = '1'  \n" + 
				"	and a.pk_seq in ( select sanpham_fk  \n" + 
				"					from ERP_KHOTT_SANPHAM  \n" + 
				"					where KHOTT_FK = " + khoId + " )";
		}
				
		System.out.println("Lay san pham / vat tu / tai san: " + command);
				
		ResultSet sp = null;
		if (command.trim().length() > 0)
			sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			
// 			while(sp.next())
// 			{
// 				String maSP = Ult.replaceAEIOU(sp.getString("ma"));
// 				String tenSP = Ult.replaceAEIOU(sp.getString("ten"));
// 				String maChiTiet = Ult.replaceAEIOU(sp.getString("machitiet")); 
				
// 				if( maSP.toUpperCase().contains(query.toUpperCase()) || tenSP.toUpperCase().contains(query.toUpperCase()) || maChiTiet.toUpperCase().contains(query.toUpperCase()) )
// 				{
// 					String tensp = sp.getString("ten");
// 					out.print("###" + sp.getString("ma") + " -- " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
// 				}	
// 			}	
// 			sp.close();
			while(sp.next())
			{
				String maSP = Ult.replaceAEIOU(sp.getString("ma"));
				String tenSP = Ult.replaceAEIOU(sp.getString("ten"));
				String maChiTiet = Ult.replaceAEIOU(sp.getString("machitiet")); 
				String soLuong = Ult.replaceAEIOU(sp.getString("soLuong"));
					String tensp = sp.getString("ten");
					out.print("###" + sp.getString("ma") + " -- " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "] [" + soLuong + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				 
			}	
			sp.close();
		}
		db.shutDown();
	}
	catch(Exception ex){ 
		ex.printStackTrace(); 
	}
%>

