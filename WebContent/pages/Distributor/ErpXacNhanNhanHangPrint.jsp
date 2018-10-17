<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="geso.traphaco.distributor.db.sql.dbutils"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IErpXacnhannhanhang lsxBean = (IErpXacnhannhanhang)session.getAttribute("lsxBean"); %>

<%
	//LẤY THÔNG TIN
	dbutils db = new dbutils();
	String id = lsxBean.getId();	

	String tencongty = "";
	String diachi = "", dienthoainpp = "", fax="";
	String query = "select ten, diachi, dienthoai, fax from nhaphanphoi where pk_seq = ( select npp_fk from ERP_XACNHANNHANHANG where pk_seq = '" + id + "' ) ";
	ResultSet rs = db.get(query);
	try 
	{
		rs.next();
		tencongty = rs.getString("ten");
		diachi = rs.getString("diachi");
		dienthoainpp = rs.getString("dienthoai");
		fax = rs.getString("fax");
		rs.close();
	} 
	catch (Exception e) 
	{
		e.printStackTrace();
	}
	
	query = " select b.ten as nguoitao, a.machungtu, CONVERT(VARCHAR(20), a.Created_Date, 120) as ngaylap " + 
			" from ERP_XACNHANNHANHANG a inner join NHANVIEN b on a.nguoitao = b.pk_seq " +
			"	inner join NHAPHANPHOI c on a.npp_fk = c.pk_seq	" +
			" where a.PK_SEQ = "+id;
	
	ResultSet khors = db.get(query);
	
	String khohanghoa = "";
	String nguoilap = "";
	String machungtu = "";
	String ngaylap = "";
	try 
	{
		if(khors.next())
		{
			khohanghoa = "Kho hàng bán";
			nguoilap = khors.getString("nguoitao");
			machungtu = khors.getString("machungtu");
			ngaylap = khors.getString("ngaylap");
		}
		khors.close();
	} 
	catch (Exception e1)
	{
		e1.printStackTrace();
	}
	
	String makh = "", tenkh = "", diachiKH = "", sodonhang = "", sohoadon = "";
	query = " select isnull(  isnull(b.ma, c.maFAST), isnull(d.maFAST, e.maFAST) ) as maFAST, isnull( isnull(b.TEN, c.TEN), isnull(d.TEN, e.TEN) ) as TEN, " + 
			 " 	case a.xuatcho when 0 then e.diachi else  isnull( isnull(b.diachiGiaohang, b.diachi), isnull(d.diachiGiaohang, d.diachi) ) end as diachi,  "+
			 " 			 (	Select hd.sohoadon + ', ' AS [text()]   "+
			 " 				From ERP_XACNHANNHANHANG_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq   "+
			 " 				Where YCXK1.xacnhannhanhang_fk = a.pk_seq   "+
			 " 				For XML PATH ('') )  as sohoadon, "+
			 " 			(	select distinct dh.machungtu + ', ' as [text()]   "+
			 " 				from ERP_DONDATHANGNPP dh inner join ERP_HOADONNPP_DDH hd_dh on dh.PK_SEQ = hd_dh.DDH_FK "+
			 " 					inner join  ERP_XACNHANNHANHANG_DDH YCXK1 on hd_dh.HOADONNPP_FK = YCXK1.hoadon_fk "+
			 " 				where YCXK1.xacnhannhanhang_fk = a.pk_seq     "+
			 " 				For XML PATH ('') )  as sodonhang      "+
			 " from ERP_XACNHANNHANHANG a left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ "+
			 " 							  left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ "+
			 " 		left join "+
			 " 		("+
			 " 			select top(1) xn.xacnhannhanhang_fk, hd.KHACHHANG_FK  "+
			 " 			from ERP_XACNHANNHANHANG_DDH xn inner join ERP_HOADONNPP hd on xn.hoadon_fk = hd.PK_SEQ"+
			 " 			where xn.xacnhannhanhang_fk = '" + id + "'"+
			 " 			order by STT asc"+
			 " 		 ) "+
			 " 		 DH on a.PK_SEQ = DH.xacnhannhanhang_fk " + 
			 " 				left join KHACHHANG d on DH.KHACHHANG_FK = d.PK_SEQ"+
			 " 				left join NHAPHANPHOI e on a.NPP_DAT_FK = e.PK_SEQ"+
			 " where a.PK_SEQ = '" + id + "'" ;
	
	System.out.println("::: LAY KHACH HANG: " + query);
	ResultSet ttkhrs = db.get(query);
	try 
	{
		if(ttkhrs.next())
		{
			makh = ttkhrs.getString("maFAST");
			tenkh = ttkhrs.getString("TEN");
			diachiKH = ttkhrs.getString("diachi");
			sodonhang = ttkhrs.getString("sodonhang");
			sohoadon = ttkhrs.getString("sohoadon");
		}
		ttkhrs.close();
	} 
	catch (Exception e1)
	{
		e1.printStackTrace();
	}
	
%>

<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">

    <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
   
    <style type="text/css" >
        @media print {  
          @page {
                size: A4; 
            }
        }
        
        .bottom
        {
        	border-bottom: dashed; border-bottom-width: 1px; border-bottom-color: #EEEEEE;
        }
        
        .bottomRight
        {
        	border-bottom: dashed; border-bottom-width: 1px; border-bottom-color: #EEEEEE;
        	border-right: dashed; border-right-width: 1px; border-right-color: #EEEEEE;
        }
        
        .bottomRightLast
        {
        	border-right: dashed; border-right-width: 1px; border-right-color: #EEEEEE;
        }
            
    </style>
    
    
</head>
<body onload="window.print();" > 
<!-- <body > -->

<div >
	
	<div style="line-height: 1.1em; font-size: 0.9em; margin-top: 0px; " >
		<b>CTY Cổ Phần Dược Pha Nam (HCM) </b><br />
		Kho hàng bán <br />
		436 Cao Thắng Phường 12 Quận 10 TPHCM <br />
		Tel: <%= dienthoainpp %> - Fax: <%= fax %> <br />
	</div>
	
	<div align="center" style="margin-top: 5px;" >
		<span style="font-size: 1.3em; font-weight: bold; " >BIÊN BẢN NHẬN HÀNG</span>
		<br />
		<span style="font-size: 1.0em;" >( Vui lòng fax về công ty theo số fax sau: <%= fax %> )</span>
	</div>
	
	<table style='width:100%; border: dashed; border-width: 1px; border-color: #EEEEEE;' cellspacing="1" cellpadding="1" >

	  	<tr >
			<td colspan="2" style="font-weight: bold; font-size: 12pt "  class="bottom" >THÔNG TIN CHỨNG TỪ</td>
		</tr>
		<tr>
			<td style="width: 50%; font-size: 0.8em;" class="bottomRight" >
				<b>Mã chứng từ: <%= machungtu %></b> 
			</td>
			<td style="width: 50%; font-size: 0.8em;" class="bottom"  >
				<b>Người lập: <%= nguoilap %></b> - <b>Ngày lập: <%= ngaylap %></b> 
			</td>
		</tr>
		
		<tr>
			<td colspan="2" style="font-weight: bold; font-size: 1.0em " class="bottom" >CÔNG TY CỔ PHẦN DƯỢC PHANAM XÁC NHẬN VIỆC XUẤT HÀNG CHO:</td>
		</tr>
		<tr>
			<td class="bottomRight" >
				<b>Mã khách hàng: <%= makh %></b> 
			</td>
			<td class="bottomRight" >
				<b>Khách hàng: <%= tenkh %></b>
			</td>
		</tr>
		<tr>
			<td class="bottomRight" >
				<b>Địa chỉ: <%= diachi %></b> 
			</td>
			<td class="bottomRight" >
				<b>Đơn đặt hàng: <%= sodonhang %></b> 
			</td>
		</tr>
		
		<tr>
			<td colspan="2" style="font-weight: bold; font-size: 12pt "  class="bottom" >CHÚNG TÔI GỒM CÓ:</td>
		</tr>
		<tr>
			<td colspan="2" align="center" >
				<table style='width:100%; border: dashed; border-width: 1px; border-color: #EEEEEE;'  >
					<tr>
						<th align="center" class="bottomRight" >Họ và tên</th>
						<th align="center" class="bottomRight" >Chức vụ</th>
						<th align="center" class="bottomRight" style="border-right: 0;" >Ký tên</th>
					</tr>
					
					<%
						//LẤY DANH SÁCH NHÂN VIÊN
						query = "select b.TEN, '' as chucvu, stt  "+
								 "from ERP_XACNHANNHANHANG_NHANVIEN a inner join NHANVIEN b on a.nhanvien_fk = b.PK_SEQ where xacnhannhanhang_fk = '" + id + "' and isNVGN = '0' "+
								 /* "union ALL		 "+
								 "select b.TEN, N'NV Cung ứng' as chucvu, stt "+
								 "from ERP_XACNHANNHANHANG_NHANVIEN a inner join NHANVIENGIAONHAN b on a.nhanvien_fk = b.PK_SEQ where xacnhannhanhang_fk = '" + id + "' and isNVGN = '1' "+ */
								 "order by stt asc ";
						
						System.out.println("::: LAY NHAN VIEN: " + query );
						rs = db.get(query);
						if( rs != null )
						{
							while(rs.next())
							{
								%>
								
								<tr>
									<td align="center" class="bottomRightLast" ><%= rs.getString("TEN") %></td>
									<td align="center" class="bottomRightLast" ><%= rs.getString("chucvu") %></td>
									<td align="center" class="bottomRightLast" style="border-right: 0;" > </td>
								</tr>
								
							<% }
							rs.close();
						}
					%>
					
				</table>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" style="font-weight: bold; font-size: 12pt; "  class="bottom" >VỚI CÁC SẢN PHẨM KÈM THEO NHƯ SAU:</td>
		</tr>
		<tr>
			<td colspan="2" align="center" >
				<table style='width:100%; border: dashed; border-width: 1px; border-color: #EEEEEE;'  >
					<tr>
						<th align="center" class="bottomRight" style="width: 5%" >STT</th>
						<th align="center" class="bottomRight" style="width: 30%" >Tên sản phẩm</th>
						<th align="center" class="bottomRight" style="width: 8%" >ĐVT</th>
						<th align="center" class="bottomRight" style="width: 12%" >Số lượng</th>
						<th align="center" class="bottomRight" style="width: 15%" >Ghi chú</th>
						<th align="center" class="bottomRight" style="width: 30%; border-right: 0;" >Ghi chú chi tiết</th>
					</tr>
					
					<%
						//LẤY DANH SÁCH SẢN PHẨM
						query = "select b.TEN, sum(a.soluongXUAT) as soluong, c.donvi  "+
								 "from ERP_XACNHANNHANHANG_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ "+
								 "	inner join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ "+
								 "where a.xacnhannhanhang_fk = '" + id + "' "+
								 "group by b.TEN, c.donvi ";
						
						System.out.println("::: LAY SAN PHAM: " + query );
						rs = db.get(query);
						int soSP = 0;
						if( rs != null )
						{
							int stt = 1;
							while(rs.next())
							{
								//th = new String[] { Integer.toString(stt), rs.getString("TEN"), rs.getString("soluong"), rs.getString("donvi"), " ", " " };
							%>	
								<tr>
									<td align="center" class="bottomRightLast" ><%= stt %></td>
									<td align="center" class="bottomRightLast" ><%= rs.getString("TEN") %></td>
									<td align="center" class="bottomRightLast" ><%= rs.getString("soluong") %></td>
									<td align="center" class="bottomRightLast" ><%= rs.getString("donvi") %></td>
									<td align="center" class="bottomRightLast" ></td>
									<td align="center" class="bottomRightLast" style="border-right: 0;" ></td>
								</tr>
								
							<% stt++; soSP++;
							}
							rs.close();
						}
						
						while( soSP <= 12 )
						{
							%>
							
							<tr>
								<td align="center" class="bottomRightLast"  >&nbsp;</td>
								<td align="center" class="bottomRightLast"  >&nbsp;</td>
								<td align="center" class="bottomRightLast"  >&nbsp;</td>
								<td align="center" class="bottomRightLast"  >&nbsp;</td>
								<td align="center" class="bottomRightLast"  >&nbsp;</td>
								<td align="center" class="bottomRightLast"  >&nbsp;</td>
							</tr>
							
						<% 	soSP++;
						}
					%>
				</table>
			</td>
		</tr>
		
		<tr>
			<td colspan="2" style="font-weight: bold; font-size: 12pt; " class="bottom" >CÁC CHỨNG TỪ KÈM THEO NHƯ SAU:</td>
		</tr>
		<tr>
			<td class="bottomRight" >
				<b>Số lượng thùng hàng: </b> 
			</td>
			<td class="bottomRight" >
				<b><i>(Bằng chữ): </i></b>
			</td>
		</tr>
		<tr>
			<td class="bottomRight" style="border-bottom: 0px;" >
				<b style="font-size: 0.8em "  >Số hóa đơn (thuế GTGT): </b> <%= sohoadon %>
			</td>
			<td class="bottomRight" style="border-bottom: 0px;" >
				<table style="width: 100%;" >
					<tr>
						<td style="width: 35%; font-size: 0.8em" >Trong thùng hàng số: </td>
						<td style="width: 30%; font-size: 0.8em" >Gửi chủ xe: </td>
						<td style="width: 35%; border-right: 0; font-size: 0.8em" >Biên bản nhận hàng</td>
					</tr>
				</table>
			</td>
		</tr>
	  	
	</table>

	<table style="width: 100%" >
		<tr>
			<td align="center" style="width: 50%;" >
				<b>Nhân viên bán hàng</b> <br />
				(Ký tên - Họ và tên)
			</td>
			<td align="center" style="width: 50%;" >
				Ngày &nbsp;&nbsp;&nbsp;&nbsp; Tháng &nbsp;&nbsp;&nbsp;&nbsp; Năm 2016 <br/>
				<b>Đã nhận đủ hàng</b> <br />
				<b>Xác nhận khách hàng</b> <br />
				(Ký tên - Họ và tên)
			</td>
		</tr>
	</table>

</div>

<% db.shutDown(); %>
</body>
</html>