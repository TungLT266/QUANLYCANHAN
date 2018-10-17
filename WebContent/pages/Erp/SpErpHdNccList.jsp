<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.nhapkhonhamay.*" %>
<%@ page  import = "geso.traphaco.erp.beans.nhapkhonhamay.imp.*" %> %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<% String ctyId = (String) session.getAttribute("congtyId"); %>
<% String lspId = (String) session.getAttribute("lspId"); %>
<% String lhhId = (String) session.getAttribute("lhhId"); %>
<% String nccId = (String) session.getAttribute("nccId"); %>
<% String nccLoai = (String) session.getAttribute("nccLoai"); %>
<% String loaimh = (String) session.getAttribute("loaimh"); %>
<% String loaikho = (String) session.getAttribute("loai_kho"); %>
<% String loaihanghoa = (String) session.getAttribute("loaihanghoa"); %>

<%
	dbutils db = new dbutils();
	try
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.#####"); 
		if(lhhId == null)
			lhhId = "0";  //SP nhap kho
		if(lspId == null)
			lspId = "";
		if(nccLoai == null)
			nccLoai = "";
		
		String command = "";
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		String query = (String)request.getQueryString(); 
	 
		String view = query;
		String taiKhoanId = view.substring(view.indexOf('_')+1,view.indexOf('='));
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
	   	
	 	System.out.println("NCC" + nccId);
	 	System.out.println("lhhId" + lhhId);
	 	
	    if(view.indexOf("donmuahang") >= 0){
	    	if(loaihanghoa.equals("0"))
	    	{
	    		command = " select top 30 a.pk_seq, case when ( len(ltrim(rtrim(a.ma))) <= 0 or ( a.ma is null ) ) then a.ma else a.ma end as ma, isnull(a.ma, a.ma) as ma, \n"+
				          " isnull(a.ten, '')   as ten, \n"+
				          " a.loaisanpham_fk, a.thuexuat, isnull(b.pk_seq, 0) as donvi, 'NA' as nhomhang \n" +
						  " from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ \n" +
						  " where a.TRANGTHAI = '1'  and a.timkiem like '%"+query+"%' and A.CONGTY_FK="+ctyId;
		 

				System.out.println("Lay san pham / vat tu / tai san: " + command);
				
				ResultSet sp = db.get(command);
				int j = 0;
				if(sp != null)
				{
					while(sp.next())
					{
						String maSP = Ult.replaceAEIOU(sp.getString("ma"));
						String tenSP = Ult.replaceAEIOU(sp.getString("ten"));
						String ma = Ult.replaceAEIOU(sp.getString("ma")); 
						
						if( maSP.toUpperCase().contains(query.toUpperCase()) || tenSP.toUpperCase().contains(query.toUpperCase()) || ma.toUpperCase().contains(query.toUpperCase()) )
						{
							String tensp = sp.getString("ten");
							out.print("###" + sp.getString("ma") + " -- " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						}	
					}	
					sp.close();
				}
				
	    	}
	    	else if(loaihanghoa.equals("1"))
	    	{
	    		command="select pk_seq,ma,ten \n"+ 
	    				"from erp_masclon \n"+
	    				"where trangthai=1 and congty_fk="+ctyId+" and (ma like '%"+query+"%' or ten like '%"+query+"%' )";
				System.out.println("Lay san MaSclon: " + command);
				
				ResultSet sp = db.get(command);
				int j = 0;
				if(sp != null)
				{
					while(sp.next())
					{
						String ten = Ult.replaceAEIOU(sp.getString("ten"));
						String ma = Ult.replaceAEIOU(sp.getString("ma")); 
						
							String tensp = sp.getString("ten");
							out.print("###" + sp.getString("ma") + " -- " + ten + " [ ] [ ] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
					}	
					sp.close();
				}
	    		
	    	}
	    	else if(loaihanghoa.equals("2"))
	    	{
	    		command="select pk_seq,ma,diengiai as ten \n"+ 
	    				"from ERP_CONGCUDUNGCU \n"+
	    				"where congty_fk="+ctyId+" and trangthai=1 and (ma like '%"+query+"%' or diengiai like N'%"+query+"%' )";
				System.out.println("Lay san cong cu dung cu: " + command);
				
				ResultSet sp = db.get(command);
				int j = 0;
				if(sp != null)
				{
					while(sp.next())
					{
						String ten = Ult.replaceAEIOU(sp.getString("ten"));
						String ma = Ult.replaceAEIOU(sp.getString("ma")); 
						
							String tensp = sp.getString("ten");
							out.print("###" + sp.getString("ma") + " -- " + ten + " [ ] [ ] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
					}	
					sp.close();
				}
	    		
	    	}
	    	
	    	else if(loaihanghoa.equals("3"))
	    	{
	    		
// 	    		String taiKhoanId = view.substring(view.indexOf('_')+1,view.indexOf('='));
	    		
	    		command="select pk_seq, ten as ma,diengiai as ten \n"+ 
	    				"from ERP_NHOMCHIPHI \n"+
	    				"where congty_fk="+ctyId+" and trangthai=1 and (ten like '%"+query+"%' or diengiai like N'%"+query+"%' ) and taikhoan_fk=(SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_sEQ="+taiKhoanId+")  ";
				System.out.println("Lay chi phi tra truoc: " + command);
				
				ResultSet sp = db.get(command);
				int j = 0;
				if(sp != null)
				{
					while(sp.next())
					{
						String ten = Ult.replaceAEIOU(sp.getString("ten"));
						String ma = Ult.replaceAEIOU(sp.getString("ma")); 
						
							String tensp = sp.getString("ten");
							out.print("###" + sp.getString("ma") + " -- " + ten + " [ ] [ ] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						
					}	
					sp.close();
				}
	    		
	    	}
	    }else{
	    	
	      	command="SELECT top 30  PK_SEQ,SOHIEUTAIKHOAN,TENTAIKHOAN  FROM ERP_TAIKHOANKT  WHERE sohieutaikhoan like '%"+query+"%' and  CONGTY_FK="+ctyId;
	    	System.out.println("query " + command);
	    	
	    	ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{ 
						 
						out.print("###" + sp.getString("pk_seq") + " -- " + sp.getString("sohieutaikhoan") + " [" + sp.getString("TENTAIKHOAN") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				 }	
				sp.close();
			}
	    	
	    }
		db.shutDown();
		
	}
	catch(Exception ex){ 
		System.out.println("Xay ra exception roi ban...");
		ex.printStackTrace();						
	}
%>
