<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<% String ctyId = (String) session.getAttribute("ctyId"); %>
<% String lspId = (String) session.getAttribute("lspId"); %>
<% String lhhId = (String) session.getAttribute("lhhId"); %>
<% String nccId = (String) session.getAttribute("nccId"); %>
<% String nccLoai = (String) session.getAttribute("nccLoai"); %>

<%
	dbutils db = new dbutils();
	try
	{
		if(lhhId == null)
			lhhId = "0";  //SP nhap kho
		if(lspId == null)
			lspId = "";
		if(nccLoai == null)
			nccLoai = "";
		
		System.out.println("nccLoai:"+nccLoai);
		System.out.println("lhhId:"+lhhId);
		System.out.println("lspId:"+lspId);
		
		String command = "";
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");
		String query = (String)request.getQueryString(); 
	 
		String view = query;
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
	   	
	 
	    if(view.indexOf("tenkh") >= 0)
	    { 
    	   command="select pk_seq, ma +  '  ,  ' + ten as khten  from Erp_KhachHang where trangthai = '1'";
  		    //System.out.println("du lieu  : "+command);
  			ResultSet vt = db.get(command);
			while(vt.next())
			{   
				String	khId=vt.getString("pk_seq");
				String khTen = vt.getString("khten");
				 
				if( khId.toUpperCase().contains(query.toUpperCase()) || khTen.toUpperCase().contains(query.toUpperCase()) )
				{
					 out.println(khId + " -- " +  khTen +" |" + "\n");
				}
			}
			vt.close();		
	    }
	    else if(view.contains("hoadonkhac"))
	    {
	    	command = "select top 30 a.pk_seq, case when ( len(ltrim(rtrim(a.ma))) <= 0 or ( a.ma is null ) ) then a.ma else a.ma end as ma, a.ma as ma,  "+
			          " ( isnull(a.ten, '')  as ten , "+
			          " a.loaisanpham_fk, b.DONVI, 'NA' as nhomhang " +
					  "from SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
					  "where a.timkiem like '%"+query+"%' ) ";
	    }
	    else if(view.contains("donmuahang"))
	    {
			if(nccLoai != null)
			{
				// nếu là nhà gia công và  chọn loại hàng hóa là sản phẩm 
				if(nccLoai.equals("100003") && lhhId.equals("0")) //nha cung cap nhan gia cong
				{
					
					command = "select top 30 a.pk_seq, case when ( len(ltrim(rtrim(a.ma))) <= 0 or ( a.ma is null ) ) then a.ma else a.ma end as ma, a.ma as ma,  "+
					          " ( isnull(a.ten, '')  as ten , "+
					          " a.loaisanpham_fk, b.DONVI, 'NA' as nhomhang " +
							  "from SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
							  "where a.timkiem like '%"+query+"%' and  a.TRANGTHAI = '1' "+
							  "and a.pk_seq in ( select sanpham_fk from ERP_NHACUNGCAP_SPNHANGIACONG where ncc_fk = '" + nccId + "' ) ";
					 
				}
				else
				{
					if(lhhId.equals("0")) //Sáº£n pháº©m
					{
						 
							command = " select top 30 a.pk_seq, case when ( len(ltrim(rtrim(a.ma))) <= 0 or ( a.ma is null ) ) then a.ma else a.ma end as ma, isnull(a.ma, a.ma) as ma, "+
							          " isnull(a.ten, '')   as ten , "+
							          " a.loaisanpham_fk, isnull(b.DONVI, 'NA') as donvi, 'NA' as nhomhang " +
									  " from Erp_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
									  " where a.TRANGTHAI = '1' and (a.timkiem like '%"+query+"%' or a.ten like '%"+query+"%' or a.ma like '%"+query+"%') and a.loaisanpham_fk in (100001, 100010)";
					}
					else
					{
						if(lhhId.equals("1"))  //Tai san co dinh
						{
							command = " select a.pk_seq, a.ma, a.ma as ma, a.ten, 'NA' as donvi, 'NA' as nhomhang " + 
									  "	from ERP_MASCLON a "+
									  "	where a.trangthai = '1' and (a.ma like '%"+query+"%' or a.ten like N'%"+query+"%') order by a.ma asc";
						}
						else if(lhhId.equals("2"))
						{
							/* command = "select a.pk_seq, a.ten as ma, a.diengiai as ten, '' as donvi, isnull(b.ten, 'NA') as nhomhang " +
									" from erp_nhomchiphi a left join  erp_trungtamchiphi b on a.ttchiphi_fk = b.pk_seq where a.trangthai = '1' and a.loai = '2' order by a.ten asc"; */
							
							command = "select a.pk_seq, a.ten as ma, a.ten as ma, a.diengiai as ten, '' as donvi, isnull(b.diengiai, 'NA') as nhomhang " +
									  " from erp_nhomchiphi a left join  erp_trungtamchiphi b on a.ttchiphi_fk = b.pk_seq where a.trangthai = '1'  and a.loai = '2' order by a.ten asc";		
						}
						else if(lhhId.equals("3"))
						{
							command = " select a.pk_seq, a.MA as ma, a.MA as ma, a.diengiai as ten, isnull(a.DONVI, 'NA') as donvi, isnull(b.diengiai, 'NA') as nhomhang"+ 
									" from ERP_CONGCUDUNGCU a" +
									" left join  erp_trungtamchiphi b on a.TTCP_FK = b.pk_seq where a.trangthai = '0' and a.congty_fk = "+ ctyId +" order by a.MA asc";
						}
					}
				}
			}
			
			System.out.println("Lay san pham / vat tu / tai san: " + command);
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				
				if(!lhhId.equals("0")){
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
				}else{
					while(sp.next())
					{
						String maSP = Ult.replaceAEIOU(sp.getString("ma"));
						String tenSP = Ult.replaceAEIOU(sp.getString("ten"));
						String ma = Ult.replaceAEIOU(sp.getString("ma")); 
							String tensp = sp.getString("ten");
							out.print("###" + sp.getString("ma") + " -- " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						 
					}	
					sp.close();
				}
			}
    	}
	    else if(view.contains("masothue"))
	    {
	    	command= 
	    	"  select mst + ' [ ' + NCC +' ] ' as nccTen " +
	        " from MST_NCC ";
  		    //System.out.println("du lieu  : "+command);
  			ResultSet ncc = db.get(command);
			while(ncc.next())
			{   
				String nccTen = "";
				nccTen = ncc.getString("nccTen");
			    if(nccTen.toUpperCase().startsWith(query.toUpperCase()) || nccTen.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
			    {
			     out.println(nccTen + "|");
			    }
			}
			ncc.close();
	    }
	    else{
				if(nccLoai != null)
				{
							// nếu là nhà gia công và  chọn loại hàng hóa là sản phẩm 
					if(nccLoai.equals("100003") && lhhId.equals("0")) //nha cung cap nhan gia cong
					{
						command = "select top 30 a.pk_seq, case when ( len(ltrim(rtrim(a.ma))) <= 0 or ( a.ma is null ) ) then a.ma else a.ma end as ma, a.ma as ma,  "+
						          " ( isnull(a.ten, '')  as ten , "+
						          " a.loaisanpham_fk, b.DONVI, 'NA' as nhomhang " +
								  "from SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
								  "where a.timkiem like '%"+query+"%' and  a.TRANGTHAI = '1' and a.pk_seq in ( select sanpham_fk from ERP_NHACUNGCAP_SPNHANGIACONG where ncc_fk = '" + nccId + "' ) ";
					}
					else
					{
						if(lhhId.equals("0")) //Sáº£n pháº©m
						{
							command = " select top 30 a.pk_seq, case when ( len(ltrim(rtrim(a.ma))) <= 0 or ( a.ma is null ) ) then a.ma else a.ma end as ma, isnull(a.ma, a.ma) as ma, "+
							          " isnull(a.ten, '')   as ten , "+
							          " a.loaisanpham_fk, isnull(b.DONVI, 'NA') as donvi, 'NA' as nhomhang " +
									  " from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
									  " where a.TRANGTHAI = '1' and (a.timkiem like '%"+query+"%' or a.ten like '%"+query+"%') and a.loaisanpham_fk in (100001, 100010)";
						}
						else
						{
							if(lhhId.equals("1"))  //Tai san co dinh
							{
								command = " select a.pk_seq, a.ma, a.ma as ma, a.ten, 'NA' as donvi, 'NA' as nhomhang " + 
										  "	from ERP_MASCLON a "+
										  "	where a.trangthai = '1' and (a.ma like '%"+query+"%' or a.ten like N'%"+query+"%') order by a.ma asc";
							}
							else if(lhhId.equals("2"))
							{
								/* command = "select a.pk_seq, a.ten as ma, a.diengiai as ten, '' as donvi, isnull(b.ten, 'NA') as nhomhang " +
										" from erp_nhomchiphi a left join  erp_trungtamchiphi b on a.ttchiphi_fk = b.pk_seq where a.trangthai = '1' and a.loai = '2' order by a.ten asc"; */
								
								command = "select a.pk_seq, a.ten as ma, a.ten as ma, a.diengiai as ten, '' as donvi, isnull(b.diengiai, 'NA') as nhomhang " +
										  " from erp_nhomchiphi a left join  erp_trungtamchiphi b on a.ttchiphi_fk = b.pk_seq where a.trangthai = '1' and a.loai = '2' order by a.ten asc";		
							}
							else if(lhhId.equals("3"))
							{
								command = " select a.pk_seq, a.MA as ma, a.MA as ma, a.diengiai as ten, isnull(a.DONVI, 'NA') as donvi, isnull(b.diengiai, 'NA') as nhomhang"+ 
										" from ERP_CONGCUDUNGCU a" +
										" left join  erp_trungtamchiphi b on a.TTCP_FK = b.pk_seq where a.trangthai = '0' order by a.MA asc";
							}
						}
					}
				}
				
				System.out.println("Lay san pham / vat tu / tai san: " + command);
				
				ResultSet sp = db.get(command);
				int j = 0;
				if(sp != null)
				{
					
					if(!lhhId.equals("0")){
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
					}else{
						while(sp.next())
						{
							String maSP = Ult.replaceAEIOU(sp.getString("ma"));
							String tenSP = Ult.replaceAEIOU(sp.getString("ten"));
							String ma = Ult.replaceAEIOU(sp.getString("ma")); 
								String tensp = sp.getString("ten");
								out.print("###" + sp.getString("ma") + " -- " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
							 
						}	
						sp.close();
					}
				}
	    }
		db.shutDown();
		
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

