<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<% String ctyId = (String) session.getAttribute("ctyId"); %>
<% String lspId = (String) session.getAttribute("lspId"); %>
<% String lhhId = (String) session.getAttribute("lhhId"); %>
<% String nccId = (String) session.getAttribute("nccId"); %>
<% String nccLoai = (String) session.getAttribute("nccLoai"); %>
<% DecimalFormat formatter = new DecimalFormat("###,###,###.####"); %>

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
		
		String command = "";
		
		request.setCharacterEncoding("UTF-8");	
		response.setHeader("Content-Type", "text/html; charset=UTF-8");			
	 	
		String query = (String)request.getQueryString(); 
	   	
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
		System.out.println("loahdid"+lhhId);
		System.out.println("ncc_loai : "+nccLoai);
		
		
	
		if(nccLoai != null)
		{
				if(lhhId.equals("0"))  
				{
				 
						/* command = "select a.pk_seq, a.ma  as ma,   "+
						          "   isnull(a.ten, '') as ten , "+
						          " a.loaisanpham_fk, isnull(b.DONVI, 'NA') as donvi, 'NA' as nhomhang " +
								  "from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
								  "where a.TRANGTHAI = '1'  and a.timkiem like N'%"+query+"%'"; */
								  
								  
					command = " select a.pk_seq, a.ma  as ma,   "+
					          " isnull(a.ten, '') as ten , "+
					          " a.loaisanpham_fk, isnull(b.PK_SEQ,0) as donvi, a.thuexuat as thuexuat, 'NA' as nhomhang, isnull( c.giamua,0) as giamua " +
							  " from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
							  " left join erp_banggiamuancc_sanpham c on a.PK_SEQ = C.sanpham_fk \n"+
							  " where a.TRANGTHAI = '1'  and a.timkiem like N'%"+query+"%'" +
							  " and C.trangthai = '1' \n" +
							  " and ( c.BANGGIAMUA_FK is null or c.BANGGIAMUA_FK in (select top(1) pk_seq from erp_banggiamuancc d inner join ERP_BANGGIAMUANCC_NCC e on d.PK_SEQ = e.BANGGIA_FK \n"+
							  " where D.trangthai = '1'  and D.daduyet = '1' and e.ncc_fk = " + nccId +
							  " order by d.tungay desc)) \n" ;
								  
					// 2018-05-09 : Được yêu cầu: hiện tại các sản phẩm có trong bảng giá mới load lên đơn, bỏ rang chỗ này,
					command = " select DISTINCT a.pk_seq, a.ma  as ma,   "+
					          " isnull(a.ten, '') as ten , "+
					          " a.loaisanpham_fk, isnull(b.PK_SEQ,0) as donvi, a.thuexuat as thuexuat, 'NA' as nhomhang,0 as giamua " +
							  " from ERP_SanPham a left join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " + 
							  " where a.TRANGTHAI = '1'  and a.timkiem like N'%"+query+"%'" ;
				 
				}
				else
				{
					if(lhhId.equals("1"))  //Tai san co dinh
					{
						 
						 command="	 select a.pk_seq, a.ma, a.ma as machitiet, a.diengiai as ten ,d.DONVI ,'' as thuexuat, isnull(b.ma, 'NA') as nhomhang , 0 as giamua  \n" +
								 "	 from erp_taisancodinh a left join erp_nhomtaisan b on a.NhomTaiSan_fk = b.pk_seq  						 \n" +
								 "							 left join ERP_NHANHANG_SANPHAM d on d.TAISAN_FK=a.pk_seq  \n" +
								 "							 left join ERP_NHANHANG e on e.PK_SEQ=d.NHANHANG_FK  \n" +
								 "	 where a.congty_fk = "+ ctyId +" and e.trangthai = '2' and a.pk_seq not in (select TAISAN_FK from ERP_KHAUHAOTAISAN) \n" +
								 "	 order by a.ma asc ";
					}
					else if(lhhId.equals("2"))
					{
						command = " select a.pk_seq, a.ten as ma, a.ten as machitiet, a.diengiai as ten, '' as donvi, '' as thuexuat,   isnull(b.diengiai, 'NA') as nhomhang  , 0 as giamua" +
								  " from erp_nhomchiphi a left join  erp_trungtamchiphi b on a.ttchiphi_fk = b.pk_seq "+
								  " where a.congty_fk = "+ ctyId +" and a.trangthai = '1' and a.loai = '2' order by a.ten asc";		
					}
					else if(lhhId.equals("3"))
					{
						command = " select a.pk_seq, a.MA as ma, a.MA as machitiet, a.diengiai as ten, isnull(a.DONVI, 'NA') as donvi, '' as thuexuat, isnull(b.diengiai, 'NA') as nhomhang  , 0 as giamua"+ 
								  " from ERP_CONGCUDUNGCU a left join  erp_trungtamchiphi b on a.TTCP_FK = b.pk_seq " +
								  " where a.congty_fk = "+ ctyId +" and a.trangthai = '0' order by a.MA asc";
					}
				}
			
		}
		
		System.out.println("Lay san pham / vat tu / tai san: " + command);
		
		/* response.setHeader("Content-Type", "text/html");
		String query = (String)request.getParameter("letters"); */
	
		
		ResultSet sp = db.get(command);
		int j = 0;
		if(sp != null)
		{
			while(sp.next())
			{
				String maSP = Ult.replaceAEIOU(sp.getString("ma"));
				String tenSP = Ult.replaceAEIOU(sp.getString("ten"));
 
				 
					String tensp = sp.getString("ten");
					out.print("###" + sp.getString("ma") + " -- " + tensp + " [" + sp.getString("donvi") + "] ["  + formatter.format(sp.getDouble("giamua")) + "] ["  +sp.getString("thuexuat") + "] ["  + sp.getString("nhomhang") + "] [" + sp.getString("pk_seq") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
				 
					
					 
			}	
			sp.close();
		}
		
		db.shutDown();
		
	}
	catch(Exception ex){ System.out.println("Xay ra exception roi ban..."); }
%>

