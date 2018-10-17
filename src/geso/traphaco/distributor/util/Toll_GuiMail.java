package geso.traphaco.distributor.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.util.SendMail;
import geso.traphaco.distributor.db.sql.dbutils;

public class Toll_GuiMail 
{
	public static void main(String[] arg) 
	{
		Toll_GuiMail tool = new Toll_GuiMail();
		
		//tool.Send_DonDatHang("135751", new dbutils(), "0");
		
		tool.Send_DonDatHang_SMS("3", new dbutils(), "0");
		
		System.out.println(":::::::::::::::: GUI MAIL XONG.............");
		
	}

	
	public String Send_DonDatHang(String id, dbutils db, String dungdb_cosan)
	{
		if( dungdb_cosan.equals("0") )
			db = new dbutils();
		
		String msg = "";
		
		String query = "select _to, _cc, _subject from MAIL_CONFIG where chucnang = N'DONDATHANG'";
		System.out.println(":::: THOGN TIN GUI MAIL: " + query );
		
		ResultSet rsInfo = db.get(query);
		String _to = "";
		String _cc = "";
		String _subject = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					_to = rsInfo.getString("_to");
					_cc = rsInfo.getString("_cc");
					_subject = rsInfo.getString("_subject");
				}
				rsInfo.close();
			} 
			catch (Exception e) { }
			
			if( _to.trim().length() > 0 )
			{
				//_to += ",truonggiang@merapgroup.com,quocvinh@merapgroup.com";
				
				query = "select b.TEN as nppTEN, e.TEN as khoTEN, b.DIACHI as nppDIACHI,  "+
						 "		a.machungtu, a.NGAYTAO as ngaylap, ISNULL(c.maFAST, d.maFAST) as khMA, ISNULL(c.TEN, d.TEN) as khTEN, ISNULL(c.diachi, d.diachi) as diachi, "+
						 "		ISNULL(c.DIACHI, d.DIACHI) as diachiGIAOHANG, ISNULL(c.DIENTHOAI, d.DIENTHOAI) as dienthoai, a.GHICHU "+
						 "from ERP_DONDATHANGNPP a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ "+
						 "	left join KHACHHANG c on a.KHACHHANG_FK = c.PK_SEQ "+
						 "	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.PK_SEQ "+
						 "	inner join KHO e on a.Kho_FK = e.pk_seq  "+
						 "where a.PK_SEQ = '" + id + "' ";
				
				System.out.println(":::: THONG TIN DON HANG: " + query);
				rsInfo = db.get(query);
				if( rsInfo != null )
				{
					try 
					{
						if( rsInfo.next() )
						{
							query = "select b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, a.soluong, round( a.dongia * a.thueVAT, 0) as dongia,  "+
									 "	round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) + isnull(a.chietkhau_DLN, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH	" +
									 "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     "+
									 "	INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK  "+
									 "where a.DONDATHANG_FK = '" + id + "'  ";
							ResultSet rsSP = db.get(query);
							
							String mailMSG = "<table style='width: 100%;' cellpadding='0' cellspacing='0' >  "+
									 "	<tr> "+
									 "		<td>" + rsInfo.getString("nppTEN") + "</td> "+
									 "		<td valign='middle' rowspan='3' >BM-20-1</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td>" + rsInfo.getString("khoTEN") + "</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td>" + rsInfo.getString("nppDIACHI") + "</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='background-color: #DDDDDD; padding: 2; font-size: 1.2em; text-align: center; ' >ĐƠN ĐẶT HÀNG</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='background-color: #DDDDDD;' >Thông tin chứng từ</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' > "+
									 "			<table style='width: 100%;' > "+
									 "				<tr> "+
									 "					<td style='width: 50%' ><b>Mã chứng từ: </b>" + rsInfo.getString("machungtu") + "</td> "+
									 "					<td><b>Ngày lập: </b>" + rsInfo.getString("ngaylap") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Mã khách hàng: </b>" + rsInfo.getString("khMA") + "</td> "+
									 "					<td><b>Khách hàng: </b>" + rsInfo.getString("khTEN") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Địa chỉ: </b>" + rsInfo.getString("diachi") + "</td> "+
									 "					<td><b>Địa chỉ giao hàng: </b>" + rsInfo.getString("diachiGIAOHANG") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Người lập: </b>" + rsInfo.getString("khoTEN") + "</td> "+
									 "					<td><b>Nhân viên bán hàng: </b>" + rsInfo.getString("khoTEN") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Điện thoại: </b>" + rsInfo.getString("dienthoai") + "</td> "+
									 "					<td><b>Ghi chú: </b>" + rsInfo.getString("GHICHU") + "</td> "+
									 "				</tr> "+
									 "			</table> "+
									 "		</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='font-size: 1.1em;' ><b>Danh mục sản phẩm</b></td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' > "+
									 "			<table style='width: 100%;' > "+
									 "				<tr style='background-color: #DDDDDD;' > "+
									 "					<th>STT</th> "+
									 "					<th>Mã sản phẩm</th> "+
									 "					<th>Tên sản phẩm</th> "+
									 "					<th>ĐVT</th> "+
									 "					<th>Số lượng</th> "+
									 "					<th>Giá sản phẩm</th> "+
									 "					<th>Thành tiền</th> "+
									 "				</tr> ";
						
						int stt = 0;
						double tongtien = 0;
						double ptchietkhau = 0;
						
						NumberFormat formater = new DecimalFormat("#,###,###");
						
						// Tien bang chu
						doctienrachu doctien = new doctienrachu();
						
						if( rsSP != null  )
						{
							while( rsSP.next() )
							{
								double soluong = rsSP.getDouble("soluong");
								double dongia = rsSP.getDouble("dongia");
								double thanhtien = soluong * dongia;
								ptchietkhau = rsSP.getDouble("ptChietkhau_KMBH");
								tongtien += thanhtien;
								
								mailMSG +=   "				<tr > "+
											 "					<td>" + (++stt) + "</td> "+
											 "					<td>" + rsSP.getString("ma") + "</td> "+
											 "					<td>" + rsSP.getString("ten") + "</td> "+
											 "					<td>" + rsSP.getString("donvi") + "</td> "+
											 "					<td style='text-align: right;' >" + formater.format(soluong) + "</td> "+
											 "					<td style='text-align: right;' >" + formater.format(dongia) + "</td> "+
											 "					<td style='text-align: right;' >" + formater.format(thanhtien) + "</td> "+
											 "				</tr> ";
							}
							rsSP.close();
						}
		 
					mailMSG +=		 "				<tr> "+
									 "					<td colspan='5'>Tổng đơn vị sản phẩm " + stt + " (Bằng chữ): " + doctien.docSo((long)stt) + " Sản phẩm </td> "+
									 "					<td style='text-align: right;' ><b>Thành tiền: </b></td> "+
									 "					<td style='text-align: right;' >" + formater.format(tongtien) + "</td> "+
									 "				</tr>	 "+
									 "				<tr> "+
									 "					<td colspan='5'> </td> "+
									 "					<td style='text-align: right;' ><b>Chiết khấu(%): </b>" + ptchietkhau + "</td> "+
									 "					<td style='text-align: right;' >0</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td colspan='5'>Tổng tiền (Bằng chữ): " + doctien.docTien((long)tongtien) + "</td> "+
									 "					<td style='text-align: right;' ><b>Tổng tiền: </b></td> "+
									 "					<td style='text-align: right;' >" + formater.format(tongtien) + "</td> "+
									 "				</tr>			 "+
									 "			</table> "+
									 "		</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='text-align: center; padding: 5px; ' ><br />Bản quyền thuộc về MEGAPGROUP - Giải pháp tổng thể SalesUp</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='text-align: center;' >Thiết kế & Lập trình hệ thống <a href='http://geso.us'>GESOGROUP</a> </td> "+
									 "	</tr> "+
									 "</table> ";
							
							SendMail sendmail = new SendMail();
							sendmail.postMailHTML(_to, _cc, _subject, mailMSG);
							
						}
						rsInfo.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			
		}
		
		if( dungdb_cosan.equals("0") )
			db.shutDown();
		return msg;
	}
	
	
	public String Send_DonDatHang_Cap1(String id, dbutils db, String dungdb_cosan)
	{
		if( dungdb_cosan.equals("0") )
			db = new dbutils();
		
		String msg = "";
		
		String query = "select _to, _cc, _subject from MAIL_CONFIG where chucnang = N'DONDATHANG_CAP1'";
		System.out.println(":::: THOGN TIN GUI MAIL: " + query );
		
		ResultSet rsInfo = db.get(query);
		String _to = "";
		String _cc = "";
		String _subject = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					_to = rsInfo.getString("_to");
					_cc = rsInfo.getString("_cc");
					_subject = rsInfo.getString("_subject");
				}
				rsInfo.close();
			} 
			catch (Exception e) { }
			
			if( _to.trim().length() > 0 )
			{
				//_to += ",truonggiang@merapgroup.com,quocvinh@merapgroup.com";
				
				query = "select b.TEN as nppTEN, e.TEN as khoTEN, b.DIACHI as nppDIACHI,  "+
						 "		a.machungtu, a.NGAYTAO as ngaylap, ISNULL(c.maFAST, d.maFAST) as khMA, ISNULL(c.TEN, d.TEN) as khTEN, ISNULL(c.diachi, d.diachi) as diachi, "+
						 "		ISNULL(c.DIACHI, d.DIACHI) as diachiGIAOHANG, ISNULL(c.DIENTHOAI, d.DIENTHOAI) as dienthoai, a.GHICHU "+
						 "from ERP_DONDATHANGNPP a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ "+
						 "	left join KHACHHANG c on a.KHACHHANG_FK = c.PK_SEQ "+
						 "	left join NHAPHANPHOI d on a.NPP_DAT_FK = d.PK_SEQ "+
						 "	inner join KHO e on a.Kho_FK = e.pk_seq  "+
						 "where a.PK_SEQ = '" + id + "' ";
				
				System.out.println(":::: THONG TIN DON HANG: " + query);
				rsInfo = db.get(query);
				if( rsInfo != null )
				{
					try 
					{
						if( rsInfo.next() )
						{
							query = "select b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, a.soluong, round( a.dongia * a.thueVAT, 0) as dongia,  "+
									 "	round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) + isnull(a.chietkhau_DLN, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH	" +
									 "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     "+
									 "	INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK  "+
									 "where a.DONDATHANG_FK = '" + id + "'  ";
							ResultSet rsSP = db.get(query);
							
							String mailMSG = "<table style='width: 100%;' cellpadding='0' cellspacing='0' >  "+
									 "	<tr> "+
									 "		<td>" + rsInfo.getString("nppTEN") + "</td> "+
									 "		<td valign='middle' rowspan='3' >BM-20-1</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td>" + rsInfo.getString("khoTEN") + "</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td>" + rsInfo.getString("nppDIACHI") + "</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='background-color: #DDDDDD; padding: 2; font-size: 1.2em; text-align: center; ' >ĐƠN ĐẶT HÀNG</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='background-color: #DDDDDD;' >Thông tin chứng từ</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' > "+
									 "			<table style='width: 100%;' > "+
									 "				<tr> "+
									 "					<td style='width: 50%' ><b>Mã chứng từ: </b>" + rsInfo.getString("machungtu") + "</td> "+
									 "					<td><b>Ngày lập: </b>" + rsInfo.getString("ngaylap") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Mã khách hàng: </b>" + rsInfo.getString("khMA") + "</td> "+
									 "					<td><b>Mã khách hàng: </b>" + rsInfo.getString("khMA") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Tên đại lý: </b>" + rsInfo.getString("khTEN") + "</td> "+
									 "					<td><b>Tên khách hàng: </b>" + rsInfo.getString("khTEN") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Địa chỉ: </b>" + rsInfo.getString("diachi") + "</td> "+
									 "					<td><b>Địa chỉ: </b>" + rsInfo.getString("diachiGIAOHANG") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Nhân viên bán hàng: </b>" + rsInfo.getString("khoTEN") + "</td> "+
									 "					<td><b>Địa chỉ giao hàng: </b>" + rsInfo.getString("khoTEN") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Điện thoại: </b>" + rsInfo.getString("dienthoai") + "</td> "+
									 "					<td><b>Ghi chú: </b>" + rsInfo.getString("GHICHU") + "</td> "+
									 "				</tr> "+
									 "			</table> "+
									 "		</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='font-size: 1.1em;' ><b>Danh mục sản phẩm</b></td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' > "+
									 "			<table style='width: 100%;' > "+
									 "				<tr style='background-color: #DDDDDD;' > "+
									 "					<th>STT</th> "+
									 "					<th>Mã sản phẩm</th> "+
									 "					<th>Tên sản phẩm</th> "+
									 "					<th>ĐVT</th> "+
									 "					<th>Số lượng</th> "+
									 "					<th>Giá sản phẩm</th> "+
									 "					<th>Thành tiền</th> "+
									 "				</tr> ";
						
						int stt = 0;
						double tongtien = 0;
						double ptchietkhau = 0;
						
						NumberFormat formater = new DecimalFormat("#,###,###");
						
						// Tien bang chu
						doctienrachu doctien = new doctienrachu();
						
						if( rsSP != null  )
						{
							while( rsSP.next() )
							{
								double soluong = rsSP.getDouble("soluong");
								double dongia = rsSP.getDouble("dongia");
								double thanhtien = soluong * dongia;
								ptchietkhau = rsSP.getDouble("ptChietkhau_KMBH");
								tongtien += thanhtien;
								
								mailMSG +=   "				<tr > "+
											 "					<td>" + (++stt) + "</td> "+
											 "					<td>" + rsSP.getString("ma") + "</td> "+
											 "					<td>" + rsSP.getString("ten") + "</td> "+
											 "					<td>" + rsSP.getString("donvi") + "</td> "+
											 "					<td style='text-align: right;' >" + formater.format(soluong) + "</td> "+
											 "					<td style='text-align: right;' >" + formater.format(dongia) + "</td> "+
											 "					<td style='text-align: right;' >" + formater.format(thanhtien) + "</td> "+
											 "				</tr> ";
							}
							rsSP.close();
						}
		 
					mailMSG +=		 "				<tr> "+
									 "					<td colspan='5'>Tổng đơn vị sản phẩm " + stt + " (Bằng chữ): " + doctien.docSo((long)stt) + " Sản phẩm </td> "+
									 "					<td style='text-align: right;' ><b>Thành tiền: </b></td> "+
									 "					<td style='text-align: right;' >" + formater.format(tongtien) + "</td> "+
									 "				</tr>	 "+
									 "				<tr> "+
									 "					<td colspan='5'> </td> "+
									 "					<td style='text-align: right;' ><b>Chiết khấu(%): </b>" + ptchietkhau + "</td> "+
									 "					<td style='text-align: right;' >0</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td colspan='5'>Tổng tiền (Bằng chữ): " + doctien.docTien((long)tongtien) + "</td> "+
									 "					<td style='text-align: right;' ><b>Tổng tiền: </b></td> "+
									 "					<td style='text-align: right;' >" + formater.format(tongtien) + "</td> "+
									 "				</tr>			 "+
									 "			</table> "+
									 "		</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='text-align: center; padding: 5px; ' ><br />Bản quyền thuộc về MEGAPGROUP - Giải pháp tổng thể SalesUp</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='text-align: center;' >Thiết kế & Lập trình hệ thống <a href='http://geso.us'>GESOGROUP</a> </td> "+
									 "	</tr> "+
									 "</table> ";
							
							SendMail sendmail = new SendMail();
							sendmail.postMailHTML(_to, _cc, _subject, mailMSG);
							
						}
						rsInfo.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			
		}
		
		if( dungdb_cosan.equals("0") )
			db.shutDown();
		return msg;
	}
	
	
	public String Send_DonDatHang_SMS(String id, dbutils db, String dungdb_cosan)
	{
		if( dungdb_cosan.equals("0") )
			db = new dbutils();
		
		String msg = "";
		
		String query = " select _to, _cc, _subject " + 
					   " from MAIL_CONFIG where chucnang = N'DONDATHANG_SMS'";
		System.out.println(":::: THOGN TIN GUI MAIL: " + query );
		
		ResultSet rsInfo = db.get(query);
		String _to = "";
		String _cc = "";
		String _subject = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					_to = rsInfo.getString("_to");
					_cc = rsInfo.getString("_cc");
					_subject = rsInfo.getString("_subject");
				}
				rsInfo.close();
			} 
			catch (Exception e) { }
			
			//Lấy danh sách TO và CC trong đơn hàng
			query = "select b.EMAIL as _to " + 
					" from ERP_GUISMSTDV a inner join NHANVIEN b on a.nhanvien_denId = b.PK_SEQ " +
					"where a.pk_seq = '" + id + "'";
			rsInfo = db.get(query);
			_to = "";
			_cc = "";
			
			try 
			{
				if ( rsInfo.next() )
				{
					if( rsInfo.getString("_to").contains("@") )
						_to = rsInfo.getString("_to");
				}
				rsInfo.close();
				
				//Lấy danh sách CC
				query = "select b.EMAIL as _cc " +
						"from ERP_GUISMSTDV_CC a inner join NHANVIEN b on a.nhanvien_fk = b.PK_SEQ  " +
						"where a.guisms_fk = '" + id + "' ";
				rsInfo = db.get(query);
				while ( rsInfo.next() )
				{
					if( rsInfo.getString("_cc").contains("@") )
						_cc += rsInfo.getString("_cc") + ",";
				}
				rsInfo.close();
				
				if( _cc.trim().length() > 0 )
					_cc = _cc.substring(0, _cc.length() - 1);
			} 
			catch (Exception e1) { }
			
			
			if( _to.trim().length() > 0 )
			{
				if( _cc.trim().length() > 0 )
					_cc += ",quocvinh@merapgroup.com,hienttd@geso.us";
				else
					_cc = "quocvinh@merapgroup.com,hienttd@geso.us";
				
				query =  "select machungtu, ngaygiaohang, ngaydukienHANGDEN, ngayvanchuyen, chanhxe, a.dienthoai, soluong, donvitinh, a.ghichu,  "+
						 "	 b.TEN as nguoiguiTEN, b.EMAIL as nguoiguiEMAIL, b.DIENTHOAI as nguoiguiDT, c.TEN as nguoinhanTEN, c.EMAIL as nguoinhanEMAIL, c.DIENTHOAI as nguoinhanDT  "+
						 "from ERP_GUISMSTDV a left join NHANVIEN b on a.nhanvien_tuId = b.PK_SEQ "+
						 "		left join NHANVIEN c on a.nhanvien_denId = c.PK_SEQ "+
						 "where a.pk_seq = '" + id + "' ";
				
				System.out.println(":::: THONG TIN DON HANG: " + query);
				rsInfo = db.get(query);
				if( rsInfo != null )
				{
					try 
					{
						if( rsInfo.next() )
						{
							query = "select c.machungtu, a.NGAYTAO as ngaylap, ISNULL(d.maFAST, e.maFAST) as maKH, ISNULL(d.TEN, e.TEN) as tenKH, ISNULL(f.ten, '') as tinhthanh  "+
									 "from ERP_GUISMSTDV a inner join ERP_GUISMSTDV_DDH b on a.PK_SEQ = b.guisms_fk "+
									 "	inner join ERP_DONDATHANGNPP c on b.ddh_fk = c.PK_SEQ "+
									 "	left join KHACHHANG d on c.KHACHHANG_FK = d.PK_SEQ "+
									 "	left join NHAPHANPHOI e on c.NPP_DAT_FK = e.PK_SEQ "+
									 "	left join TINHTHANH f on d.TINHTHANH_FK = f.PK_SEQ "+
									 "where a.PK_SEQ = '" + id + "' ";
							ResultSet rsKH = db.get(query);
							
							query =  "select MA, TEN, DONVI, SUM(soluong) as soluong  "+
									 "from "+
									 "( "+
									 "	select d.MA, d.TEN, DV.DONVI, sum(c.soluong) as soluong  "+
									 "	from ERP_GUISMSTDV a inner join ERP_GUISMSTDV_DDH b on a.PK_SEQ = b.guisms_fk  "+
									 "		inner join ERP_DONDATHANGNPP_SANPHAM c on b.ddh_fk = c.dondathang_fk  "+
									 "		inner join SANPHAM d on c.sanpham_fk = d.PK_SEQ  "+
									 "		inner join DONVIDOLUONG dv on c.dvdl_fk = dv.PK_SEQ "+
									 "	where a.PK_SEQ = '" + id + "' "+
									 "	group by d.MA, d.TEN, DV.DONVI "+
									 "union ALL "+
									 "	select d.MA, d.TEN, DV.DONVI, sum(c.soluong) as soluong  "+
									 "	from ERP_GUISMSTDV a inner join ERP_GUISMSTDV_DDH b on a.PK_SEQ = b.guisms_fk  "+
									 "		inner join ERP_DONDATHANGNPP_CTKM_TRAKM c on b.ddh_fk = c.DONDATHANGID  "+
									 "		inner join SANPHAM d on c.SPMA = d.MA  "+
									 "		inner join DONVIDOLUONG dv on d.dvdl_fk = dv.PK_SEQ "+
									 "	where a.PK_SEQ = '" + id + "' "+
									 "	group by d.MA, d.TEN, DV.DONVI "+
									 ") "+
									 "DH group by MA, TEN, DONVI ";
							ResultSet rsSP = db.get(query);
							
							query = "select b.TEN, b.EMAIL, b.DIENTHOAI " +
									"from ERP_GUISMSTDV_CC a inner join NHANVIEN b on a.nhanvien_fk = b.PK_SEQ  " +
									"where a.guisms_fk = '" + id + "'";
							ResultSet rsCC = db.get(query);
							
							String mailMSG = "<table style='width: 100%;' cellpadding='0' cellspacing='0' > "+
									 "	<tr> "+
									 "		<td colspan='2' style='background-color: #339933; padding: 4px; font-size: 1.1em; font-weight: bold; text-align: center; color: #FFF ' >Gửi SMS cho TDV tỉnh </td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' > "+
									 "			<table style='width: 100%;' > "+
									 "				<tr> "+
									 "					<td style='width: 15%' ><b>Mã số SMS: </b> </td> "+
									 "					<td style='width: 25%' >" + rsInfo.getString("machungtu") + "</td> "+
									 "					<td style='width: 15%' ><b>Ngày giao hàng: </b> </td> "+
									 "					<td>" + rsInfo.getString("ngaygiaohang") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Chành xe: </b> </td> "+
									 "					<td>" + rsInfo.getString("chanhxe") + "</td> "+
									 "					<td><b>Ngày dự kiến hàng đến: </b> </td> "+
									 "					<td>" + rsInfo.getString("ngaydukienHANGDEN") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Điện thoại: </b> </td> "+
									 "					<td>" + rsInfo.getString("dienthoai") + "</td> "+
									 "					<td><b>Số lượng: </b> </td> "+
									 "					<td>" + rsInfo.getString("soluong") + "</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Ghi chú: </b> </td> "+
									 "					<td>" + rsInfo.getString("GHICHU") + "</td> "+
									 "					<td><b>ĐVT: </b> </td> "+
									 "					<td>Kiện</td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td><b>Từ: </b> </td> "+
									 "					<td><b>" + rsInfo.getString("nguoiguiTEN") + "</b>     " + rsInfo.getString("nguoiguiEMAIL") + "     " + rsInfo.getString("nguoiguiDT") + " </td> "+
									 "					<td><b>Đến: </b> </td> "+
									 "					<td><b>" + rsInfo.getString("nguoinhanTEN") + "</b>     " + rsInfo.getString("nguoinhanEMAIL") + "     " + rsInfo.getString("nguoinhanDT") + " </td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td ><b>CC Quản lý: </b> </td> "+
									 "					<td colspan='3' > ";
							
								if( rsCC != null )
								{
									while( rsCC.next() )
									{
										mailMSG += "  <b>" + rsCC.getString("ten") + "</b> | " + rsCC.getString("email") + " | " + rsCC.getString("dienthoai") + " <br />";
									}
									rsCC.close();
								}
							
						mailMSG +=	 " </td> "+
									 "				</tr> "+
									 "				<tr> "+
									 "					<td ><b>Xác nhận Đơn đặt hàng: </b> </td> "+
									 "					<td colspan='3' > "+
									 "						Vui lòng Xác nhận Đơn đặt hàng, Soạn tin nhắn với cú pháp: <br /> "+
									 "						<b>SMS ACTIVEDMS " + rsInfo.getString("machungtu") + "</b> gửi đến <b>8185</b> (Ghi chú: Bạn phải trả <b>1.000</b> Đồng từ thẻ sim của bạn - Áp dụng ở Việt Nam)  "+
									 "					</td> "+
									 "				</tr> "+
									 "			</table> "+
									 "		</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' > "+
									 "			<table style='width: 100%;' > "+
									 "				<tr style='background-color: #339933; padding: 3px;color: #FFF;' > "+
									 "					<th>STT</th> "+
									 "					<th>Mã chứng từ</th> "+
									 "					<th>Mã khách hàng</th> "+
									 "					<th>Khách hàng</th> "+
									 "					<th>Ngày lập</th> "+
									 "					<th>Tỉnh thành</th> "+
									 "				</tr> ";
							
							int stt = 0;
							while( rsKH.next() )
							{
								mailMSG +=	"				<tr > "+
											 "					<td>" + (++stt) + "</td> "+
											 "					<td>" + rsKH.getString("machungtu") + "</td> "+
											 "					<td>" + rsKH.getString("maKH") + "</td> "+
											 "					<td>" + rsKH.getString("tenKH") + "</td> "+
											 "					<td>" + rsKH.getString("ngaylap") + "</td> "+
											 "					<td>" + rsKH.getString("tinhthanh") + "</td> "+
											 "				</tr> ";
							}
							rsKH.close();
									 
							mailMSG +=	 "		   <tr  > "+
									 "					<th> </th>"+
									 "					<th> </th>"+
									 "					<th >Mã sản phẩm</th> "+
									 "					<th >Tên sản phẩm</th> "+
									 "					<th >Đơn vị</th> "+
									 "					<th >Số lượng</th> "+
									 "				</tr> ";
							
							while( rsSP.next() )
							{
								mailMSG +=	"				<tr > "+
											 "					<td></td> "+
											 "					<td></td> "+
											 "					<td>" + rsSP.getString("ma") + "</td> "+
											 "					<td>" + rsSP.getString("ten") + "</td> "+
											 "					<td>" + rsSP.getString("donvi") + "</td> "+
											 "					<td>" + rsSP.getString("soluong") + "</td> "+
											 "				</tr> ";
							}
							rsSP.close();
							
							mailMSG +=	"			</table> "+
									 "		</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='text-align: center; padding: 5px; ' ><br />Bản quyền thuộc về GESOGROUP - Giải pháp tổng thể SalesUp</td> "+
									 "	</tr> "+
									 "	<tr> "+
									 "		<td colspan='2' style='text-align: center;' >Thiết kế & Lập trình hệ thống <a href='http://geso.us'>GESOGROUP</a> </td> "+
									 "	</tr> "+
									 "</table> ";
									
							SendMail sendmail = new SendMail();
							sendmail.postMailHTML(_to, _cc, _subject, mailMSG);
							
						}
						rsInfo.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			
		}
		
		if( dungdb_cosan.equals("0") )
			db.shutDown();
		return msg;
	}

	
	public String Send_NoXau(String khId, String congtyId, dbutils db, String dungdb_cosan)
	{
		if( dungdb_cosan.equals("0") )
			db = new dbutils();
		
		String msg = "";
		
		String query = " select _to, _cc, _subject " + 
					   " from MAIL_CONFIG where chucnang = 'DUYETSS' and capduyet = N'RSM' ";
		System.out.println(":::: THOGN TIN GUI MAIL: " + query );
		
		ResultSet rsInfo = db.get(query);
		String _to = "";
		String _cc = "";
		String _subject = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					_to = rsInfo.getString("_to");
					_cc = rsInfo.getString("_cc");
					_subject = rsInfo.getString("_subject");
				}
				rsInfo.close();
			} 
			catch (Exception e) { }
		}
		
		//_to = "haind@geso.us,hienttd@geso.us,huongnth@geso.us";
		//_cc = "luonghv@geso.us";
		
		String noidung = "";
		
		noidung = //"<h1>CẢNH BÁO NỢ XẤU</h1>  "+
				 "<table style='width: 100%;' cellpadding='0' cellspacing='1'  > "+
				 "	<tr> "+
				 "		<td colspan='2' style='padding: 4px; font-size: 1.1em; font-weight: bold; text-align: left; margin-left: 15px;' > "+
				 "			** Đơn hàng nợ xấu GĐ Kênh cần duyệt "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' > "+
				 "			<table style='width: 100%;' > "+
				 "				<tr style='background-color: #339933; padding: 2px; color: #FFF;' > "+
				 "					<th>Mã ĐH</th> "+
				 "					<th>Ngày ĐH</th> "+
				/* "					<th>Mã khách hàng</th> "+
				 "					<th>Tên khách hàng</th> "+
				 "					<th>Tỉnh thành</th> "+*/
				 "					<th>Tên sản phẩm</th> "+
				 "					<th>Số lượng</th> "+
				 "					<th>Đơn giá</th> "+
				 "					<th>Thành tiền</th> "+
				 "				</tr> ";
		
		//Lấy danh sách đơn hàng chưa duyệt
		query = "select a.machungtu, a.NgayDonHang, c.TEN, b.soluong, ROUND( b.dongia *  ( 1 + thueVAT / 100.0 ), 0) as dongia,  "+
				 "		b.soluong *  ROUND( b.dongia *  ( 1 + thueVAT / 100.0 ), 0) as thanhtien "+
				 "from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on a.PK_SEQ = b.dondathang_fk "+
				 "	inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ "+
				 "where a.KHACHHANG_FK = '" + khId + "' and a.CS_DUYET = '0' and a.trangthai != 3 "+
				 "order by NgayDonHang asc ";
		ResultSet rs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("#,###,###");
		if( rs != null )
		{
			try
			{
				while( rs.next() )
				{
					noidung +=   "				<tr > "+
								 "					<td>" + rs.getString("machungtu") + "</td> "+
								 "					<td>" + rs.getString("NgayDonHang") + "</td> "+
								 "					<td>" + rs.getString("TEN") + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("soluong") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("dongia") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("thanhtien") ) + "</td> "+
								 "				</tr> ";
				}
				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		noidung += "			</table> "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' style='padding: 4px; font-size: 1.1em; font-weight: bold; text-align: left; margin-left: 15px;' > "+
				 "			** Công nợ của Khách  hàng: "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' > "+
				 "			<table style='width: 100%;' > "+
				 "				<tr style='background-color: #339933; padding: 2px; color: #FFF;' > "+
				 "					<th>Mã ĐH</th> "+
				 "					<th>Ngày ĐH</th> "+
				 /*"					<th>Mã khách hàng</th> "+
				 "					<th>Tên khách hàng</th> "+*/
				 "					<th>Tổng nợ</th> "+
				 "					<th>Số ngày nợ QH</th> "+
				 "					<th style='color: red;' >Nợ Trong hạn (<30 ngày)</th> "+
				 "					<th style='color: red;' >Nợ Quá hạn (HĐ +30 ngày)</th> "+
				 "					<th style='color: red;' >Nợ Xấu (HĐ +90 ngày)</th> "+
				 "					<th style='color: red;' >Nợ quá xấu (HĐ +91 ngày)</th> "+
				 "				</tr> ";
		
		query = "select a.machungtu, a.NgayDonHang,  "+
				"	( select SUM( soluong * ROUND( dongia *  ( 1 + thueVAT / 100.0 ), 0) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = a.pk_seq ) as tongno,	" +
				 "	ISNULL( ( select SONGAYNO from  [ufn_CongNoSONgayNO] ( " + congtyId + ",  'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as SONGAYNO, "+
				 "	ISNULL( ( select NOTRONGHAN from [ufn_CongNoTrongHan] ( " + congtyId + ", 'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as NOTRONGHAN, "+
				 "	ISNULL( ( select NOQUAHAN from [ufn_CongNoQuaHan] ( " + congtyId + ", 'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as NOQUAHAN,	" +
				 "	ISNULL( ( select NOQUAXAU from [ufn_CongNoQuaXau] ( " + congtyId + ", 'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as NOQUAXAU, "+
				 "	ISNULL( ( select NOXAU from [ufn_CongNoXau] ( " + congtyId + ", b.maFAST, a.NgayDonHang ) ), 0) as NOXAU "+
				 "from ERP_DONDATHANGNPP a inner join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ "+
				 "where a.KHACHHANG_FK = '" + khId + "' and a.CS_DUYET = '0' and a.trangthai != 3 "+
				 "order by NgayDonHang asc ";
		rs = db.get(query);
		
		if( rs != null )
		{
			try
			{
				while( rs.next() )
				{
					noidung +=   "				<tr > "+
								 "					<td>" + rs.getString("machungtu") + "</td> "+
								 "					<td>" + rs.getString("NgayDonHang") + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("tongno") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("SONGAYNO") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOTRONGHAN") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOQUAHAN") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOXAU") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOQUAXAU") ) + "</td> "+
								 "				</tr> ";
				}
				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			 
		noidung +=	 "			</table> "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' style='text-align: center; padding: 5px; ' ><br />Bản quyền thuộc về GESOGROUP - Giải pháp tổng thể SalesUp</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' style='text-align: center;' >Thiết kế & Lập trình hệ thống <a href='http://geso.us'>GESOGROUP</a> </td> "+
				 "	</tr> "+
				 "</table> ";
		
		try
		{
			SendMail sendmail = new SendMail();
			sendmail.postMailHTML(_to, _cc, _subject, noidung);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if( dungdb_cosan.equals("0") )
			db.shutDown();
		return msg;
	}
	
	public String Send_NoXau_ASM(String khId, String congtyId, dbutils db, String dungdb_cosan)
	{
		if( dungdb_cosan.equals("0") )
			db = new dbutils();
		
		String msg = "";
		
		String query = " select _to, _cc, _subject " + 
					   " from MAIL_CONFIG where chucnang = 'DUYETSS' and capduyet = N'ASM' ";
		System.out.println(":::: THOGN TIN GUI MAIL: " + query );
		
		ResultSet rsInfo = db.get(query);
		String _to = "";
		String _cc = "";
		String _subject = "";
		if( rsInfo != null )
		{
			try 
			{
				if( rsInfo.next() )
				{
					_to = rsInfo.getString("_to");
					_cc = rsInfo.getString("_cc");
					_subject = rsInfo.getString("_subject");
				}
				rsInfo.close();
			} 
			catch (Exception e) { }
		}
		
		String noidung = "";
		
		noidung = //"<h1>CẢNH BÁO NỢ XẤU</h1>  "+
				 "<table style='width: 100%;' cellpadding='0' cellspacing='1'  > "+
				 "	<tr> "+
				 "		<td colspan='2' style='padding: 4px; font-size: 1.1em; font-weight: bold; text-align: left; margin-left: 15px;' > "+
				 "			** Đơn hàng nợ xấu ASM cần duyệt "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' > "+
				 "			<table style='width: 100%;' > "+
				 "				<tr style='background-color: #339933; padding: 2px; color: #FFF;' > "+
				 "					<th>Mã ĐH</th> "+
				 "					<th>Ngày ĐH</th> "+
				/* "					<th>Mã khách hàng</th> "+
				 "					<th>Tên khách hàng</th> "+
				 "					<th>Tỉnh thành</th> "+*/
				 "					<th>Tên sản phẩm</th> "+
				 "					<th>Số lượng</th> "+
				 "					<th>Đơn giá</th> "+
				 "					<th>Thành tiền</th> "+
				 "				</tr> ";
		
		//Lấy danh sách đơn hàng chưa duyệt
		query = "select a.machungtu, a.NgayDonHang, c.TEN, b.soluong, ROUND( b.dongia *  ( 1 + thueVAT / 100.0 ), 0) as dongia,  "+
				 "		b.soluong *  ROUND( b.dongia *  ( 1 + thueVAT / 100.0 ), 0) as thanhtien "+
				 "from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on a.PK_SEQ = b.dondathang_fk "+
				 "	inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ "+
				 "where a.KHACHHANG_FK = '" + khId + "' and a.CS_DUYET = '0' "+
				 "order by NgayDonHang asc ";
		ResultSet rs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("#,###,###");
		if( rs != null )
		{
			try
			{
				while( rs.next() )
				{
					noidung +=   "				<tr > "+
								 "					<td>" + rs.getString("machungtu") + "</td> "+
								 "					<td>" + rs.getString("NgayDonHang") + "</td> "+
								 "					<td>" + rs.getString("TEN") + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("soluong") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("dongia") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("thanhtien") ) + "</td> "+
								 "				</tr> ";
				}
				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		noidung += "			</table> "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' style='padding: 4px; font-size: 1.1em; font-weight: bold; text-align: left; margin-left: 15px;' > "+
				 "			** Công nợ của Khách  hàng: "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' > "+
				 "			<table style='width: 100%;' > "+
				 "				<tr style='background-color: #339933; padding: 2px; color: #FFF;' > "+
				 "					<th>Mã ĐH</th> "+
				 "					<th>Ngày ĐH</th> "+
				 /*"					<th>Mã khách hàng</th> "+
				 "					<th>Tên khách hàng</th> "+*/
				 "					<th>Tổng nợ</th> "+
				 "					<th>Số ngày nợ QH</th> "+
				 "					<th style='color: red;' >Nợ Trong hạn (<30 ngày)</th> "+
				 "					<th style='color: red;' >Nợ Quá hạn (HĐ +30 ngày)</th> "+
				 "					<th style='color: red;' >Nợ Xấu (HĐ +90 ngày)</th> "+
				 "					<th style='color: red;' >Nợ quá xấu (HĐ +91 ngày)</th> "+
				 "				</tr> ";
		
		query = "select a.machungtu, a.NgayDonHang,  "+
				"	( select SUM( soluong * ROUND( dongia *  ( 1 + thueVAT / 100.0 ), 0) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = a.pk_seq ) as tongno,	" +
				 "	ISNULL( ( select SONGAYNO from  [ufn_CongNoSONgayNO] ( " + congtyId + ",  'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as SONGAYNO, "+
				 "	ISNULL( ( select NOTRONGHAN from [ufn_CongNoTrongHan] ( " + congtyId + ", 'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as NOTRONGHAN, "+
				 "	ISNULL( ( select NOQUAHAN from [ufn_CongNoQuaHan] ( " + congtyId + ", 'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as NOQUAHAN,	" +
				 "	ISNULL( ( select NOQUAXAU from [ufn_CongNoQuaXau] ( " + congtyId + ", 'KH' + cast( b.PK_SEQ as varchar(10) ), a.NgayDonHang ) ), 0) as NOQUAXAU, "+
				 "	ISNULL( ( select NOXAU from [ufn_CongNoXau] ( " + congtyId + ", b.maFAST, a.NgayDonHang ) ), 0) as NOXAU "+
				 "from ERP_DONDATHANGNPP a inner join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ "+
				 "where a.KHACHHANG_FK = '" + khId + "' and a.CS_DUYET = '0' "+
				 "order by NgayDonHang asc ";
		rs = db.get(query);
		
		if( rs != null )
		{
			try
			{
				while( rs.next() )
				{
					noidung +=   "				<tr > "+
								 "					<td>" + rs.getString("machungtu") + "</td> "+
								 "					<td>" + rs.getString("NgayDonHang") + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("tongno") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("SONGAYNO") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOTRONGHAN") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOQUAHAN") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOXAU") ) + "</td> "+
								 "					<td style='text-align:right;' >" + formater.format( rs.getDouble("NOQUAXAU") ) + "</td> "+
								 "				</tr> ";
				}
				rs.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
			 
		noidung +=	 "			</table> "+
				 "		</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' style='text-align: center; padding: 5px; ' ><br />Bản quyền thuộc về GESOGROUP - Giải pháp tổng thể SalesUp</td> "+
				 "	</tr> "+
				 "	<tr> "+
				 "		<td colspan='2' style='text-align: center;' >Thiết kế & Lập trình hệ thống <a href='http://geso.us'>GESOGROUP</a> </td> "+
				 "	</tr> "+
				 "</table> ";
		
		try
		{
			SendMail sendmail = new SendMail();
			sendmail.postMailHTML(_to, _cc, _subject, noidung);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		if( dungdb_cosan.equals("0") )
			db.shutDown();
		return msg;
	}
	
	
}
