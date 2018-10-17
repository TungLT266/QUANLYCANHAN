package geso.traphaco.distributor.servlets.xuatkho;

import geso.traphaco.distributor.beans.nhaphang.imp.Nhaphang;
import geso.traphaco.distributor.beans.xuatkho.IErpSoxuathangNpp;
import geso.traphaco.distributor.beans.xuatkho.IErpSoxuathangNppList;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpSoxuathangNpp;
import geso.traphaco.distributor.beans.xuatkho.imp.ErpSoxuathangNppList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class ErpSoxuathangNppSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpSoxuathangNppSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpSoxuathangNppList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	 
	    
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
    	String lsxId = util.getId(querystring);
	    obj = new ErpSoxuathangNppList();
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    /*String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    obj.setLoaidonhang(loaidonhang);
	    
	    String phanloai = request.getParameter("loai");
		if(phanloai == null)
			phanloai = "";
		obj.setPhanloai(phanloai);*/
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);
	    
    	if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, userId, nppId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("delete"))
    	{
    		String msg = this.Delete(lsxId, nppId);
			obj.setMsg(msg);
    	}
    	else if(action.equals("mochot"))
    	{
    		String msg = this.MoChot(lsxId, userId, nppId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    
	    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNpp.jsp";
		response.sendRedirect(nextJSP);
	    
	}

	private String MoChot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			//CHECK XEM CÓ HÓA ĐƠN NÀO ĐÃ CÓ NHẬN HÀNG CHƯA
			query = " select hoadon_fk, ( select pk_seq from NHAPHANG where trangthai in ( 0, 1 ) and hoadonnpp_fk = a.hoadon_fk ) as nhanhangId " + 
					" from ERP_SOXUATHANGNPP_DDH a " + 
					" where soxuathang_fk = '" + lsxId + "' and hoadon_fk in ( select PK_SEQ from ERP_HOADONNPP where NPP_DAT_FK is not null )";
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String hdId = rs.getString("hoadon_fk");
					String nhanhangId = rs.getString("nhanhangId");
					msg = this.MoChotNhapHang( db, nhanhangId, hdId, userId );
					
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			}
			
			
			query = "update ERP_SOXUATHANGNPP set trangthai = '0'  where pk_seq = '" + lsxId + "' ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String MoChotNhapHang(dbutils db, String nhanhangId, String hdId, String userId) 
	{
		String msg = "";
		try
		{
			//TANG KHO CỦA ĐỐI TÁC LUÔN
			Nhaphang nhaphang = new Nhaphang();
			if( !nhaphang.MTVMoChot(db, nhanhangId, hdId))
			{
				msg = nhaphang.getMsg();
				return msg;
			}
		}
		catch (Exception e) 
		{
			return "Lỗi khi mở nhận hàng: " + e.getMessage();
		}
		
		return "";
	}

	private String Delete(String lsxId, String nppId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			//XOA GHI NHAN
			query = "update e set e.thoigianxuly = NULL  "+
					 "from ERP_SOXUATHANGNPP a inner join ERP_SOXUATHANGNPP_DDH b on a.PK_SEQ = b.soxuathang_fk  "+
					 "		inner join ERP_HOADONNPP c on b.hoadon_fk = c.PK_SEQ "+
					 "		inner join ERP_HOADONNPP_DDH d on c.PK_SEQ = d.HOADONNPP_FK "+
					 "		inner join ERP_DONDATHANGNPP e on d.DDH_FK = e.PK_SEQ "+
					 "where a.PK_SEQ = '" + lsxId + "' and c.TRANGTHAI not in ( 3, 5 )  ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_SOXUATHANGNPP_DDH where soxuathang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_SOXUATHANGNPP_SANPHAM where soxuathang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_SOXUATHANGNPP_SANPHAM_CHITIET where soxuathang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_YCXUATKHO_SANPHAM " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			query = "delete ERP_SOXUATHANGNPP  where pk_seq = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}
	
	private String Chot(String lsxId, String userId, String nppId) 
	{
		dbutils db = new dbutils();
		//SMSClient smsClient = new SMSClient();
		
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			
			//GOP CHUNG BUOC YC VA XUAT THANH 1
			query = "update ERP_SOXUATHANGNPP set trangthai = '2'  where pk_seq = '" + lsxId + "' ";
			System.out.println("---CAP NHAT TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//NHẮN TIN SMS -> chi khach hang ngoai tinh HCM
			/*query = "select a.PK_SEQ, a.machungtu, SUBSTRING(NgayDonHang, 0, 5) + SUBSTRING(NgayDonHang, 6, 2) + CAST( a.PK_SEQ as varchar(10) ) as orderId, c.DIENTHOAI  " +
					"from ERP_DONDATHANGNPP a inner join ERP_SOXUATHANGNPP_DDH b on a.PK_SEQ = b.ddh_fk  " +
					"	left join GIAMSATBANHANG c on a.GSBH_FK = c.PK_SEQ	" +
					"where b.soxuathang_fk = '" + lsxId + "' and a.khachhang_fk not in ( select PK_SEQ from KHACHHANG where TINHTHANH_FK = '100049' ) ";
			ResultSet rs = db.get(query);
			String ddhIds = "";
			if( rs != null )
			{
				while( rs.next() )
				{
					ddhIds += rs.getString("PK_SEQ") + ",";
					String dienthoai = rs.getString("DIENTHOAI").replaceAll(" ", "").replaceAll(",", "").replaceAll(".", "");
					//String sms = rs.getString("orderId");
					String sms = rs.getString("machungtu");
					
					if( dienthoai.trim().length() < 10 || dienthoai.trim().length() >= 14 )
						msg = "Số điện thoại không hợp lệ";
					else
						msg = smsClient.sendSMS(dienthoai, sms);
					
					query = "insert SMS_LOG( phoneNumber, sms, ghichu ) values ( '" + dienthoai + "', N'" + sms + "', N'Lỗi: " + msg + "' )";
					if(!db.update(query))
					{
						msg = "Lỗi khi gủi SMS " + query;
						db.getConnection().rollback();
						return msg;
					}		
				}
				rs.close();
			}*/
			
			/*if( ddhIds.trim().length() > 0 )
			{
				ddhIds = ddhIds.substring(0, ddhIds.length() - 1);
				
				query = "Update ERP_DONDATHANGNPP set ngayguiSMS = getdate(), trangthaiSMS = 0 where pk_seq in ( " + ddhIds + " ) ";
				if(!db.update(query))
				{
					msg = "Lỗi khi gủi SMS " + query;
					db.getConnection().rollback();
					return msg;
				}	
			}*/
				
			//Tìm những hoa đơn của DLPP để tạo nhập hàng tự động
			/*query = "select hoadon_fk from ERP_SOXUATHANGNPP_DDH " + 
					" where soxuathang_fk = '" + lsxId + "' and hoadon_fk in ( select PK_SEQ from ERP_HOADONNPP where NPP_DAT_FK is not null )";*/
			
			query = "  select hoadon_fk, b.NPP_DAT_FK, c.TEN, c.loaiNPP  " + 
					"  from ERP_SOXUATHANGNPP_DDH a inner join ERP_HOADONNPP b on a.hoadon_fk = b.PK_SEQ " + 
					"  		inner join NHAPHANPHOI c on b.NPP_DAT_FK = c.PK_SEQ " + 
					"  where soxuathang_fk = '" + lsxId + "' and NPP_DAT_FK is not null ";
			
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String hdId = rs.getString("hoadon_fk");
					String loaiNPP = rs.getString("loaiNPP");
					
					//NẾU DLPP ĐẶT HÀNG THÌ TẠO NHẬP HÀNG TỰ ĐỘNG, MTV ĐẶT HÀNG THÌ TẠO HĐNCC NẰM CHỜ
					if( !loaiNPP.equals("0") )
						msg = this.TaoNhapHang( db, hdId, userId );
					else
						msg = this.TaoHDNCC( db, hdId, userId );
					
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			}
			
			//CẬP NHẬT HẠN DÙNG
			query = "  update a  " + 
					"  	set a.ngayhethan = e.NGAYHETHAN " + 
					"  from ERP_SOXUATHANGNPP_SANPHAM_CHITIET a inner join ERP_SOXUATHANGNPP_DDH b on a.soxuathang_fk = b.soxuathang_fk  " + 
					"  		inner join ERP_HOADONNPP c on b.hoadon_fk = c.PK_SEQ " + 
					"  		inner join SANPHAM d on a.sanpham_fk = d.PK_SEQ " + 
					"  		inner join ERP_HOADONNPP_SP_CHITIET e on c.PK_SEQ = e.hoadon_fk and d.MA = e.MA and a.solo = e.SOLO " + 
					"  where a.soxuathang_fk = '" + lsxId + "' ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//CẬP NHẬT LÀ ĐƠN HÀNG ĐÃ RA SỔ XUẤT HÀNG
			query = "  update a  " + 
					"  	set a.daraSXH = '1' " + 
					"  from ERP_DONDATHANGNPP a inner join ERP_HOADONNPP_DDH b on a.PK_SEQ = b.DDH_FK " + 
					"  	inner join ERP_HOADONNPP c on b.HOADONNPP_FK = c.PK_SEQ " + 
					"  	inner join ERP_SOXUATHANGNPP_DDH d on c.PK_SEQ = d.hoadon_fk " + 
					"  	inner join ERP_SOXUATHANGNPP e on d.soxuathang_fk = e.PK_SEQ " + 
					"  where e.PK_SEQ = '" + lsxId + "' and c.TRANGTHAI not in ( 3, 5 ) ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_DONDATHANGNPP " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//CẬP NHẬT LẠI CỘT ĐƠN HÀNG ĐỂ TÍNH LƯỢNG HÀNG CÒN ĐANG BOOKED
			query = "update a set a.ddh_fk = ( select ddh_fk from ERP_HOADONNPP_DDH where HOADONNPP_FK = a.hoadon_fk ) " +
					"from ERP_SOXUATHANGNPP_DDH a where a.soxuathang_fk = '" + lsxId + "' ";
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "Không thể cập nhật ERP_SOXUATHANGNPP_DDH " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String TaoHDNCC(dbutils db, String hdId, String userId)
	{
		String msg = "";
		String query = "";
		
		try
		{
			//QUY ĐỊNH PHẢI CÓ NHÀ CUNG CẤP VÀ CÔNG TY TRÊN HỆ THỐNG
			query = "  select b.MaFAST, ( select top(1) PK_SEQ from ERP_NHACUNGCAP where MA = b.MaFAST ) as ncc_fk,  " + 
					"  		( select top(1) PK_SEQ from ERP_CONGTY where MA = c.MaFAST ) as congty_fk " + 
					"  from ERP_HOADONNPP a  " + 
					"  			inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ " + 
					"  			inner join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ " + 
					"  where a.PK_SEQ = '" + hdId + "' ";
			System.out.println("---CHECK INFO: " + query );
			ResultSet rs = db.get(query);
			String maFAST = "";
			String ncc_fk = "";
			String congty_fk = "";
			if( rs.next() )
			{
				maFAST = rs.getString("MaFAST");
				ncc_fk = rs.getString("ncc_fk") == null ? "" : rs.getString("ncc_fk");
				congty_fk = rs.getString("congty_fk") == null ? "" : rs.getString("congty_fk");
			}
			rs.close();
			
			if( ncc_fk.trim().length() <= 0 )
			{
				msg = "Không tìm thấy nhà cung cấp có mã " + maFAST + " trên hệ thống";
				return msg;
			}
			
			if( congty_fk.trim().length() <= 0 )
			{
				msg = "Không tìm thấy công ty có mã " + maFAST + " trên hệ thống";
				return msg;
			}
			
			query = "  insert ERP_PARK( NCCTHAYTHE_FK, NGAYGHINHAN, NCC_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, PO_NHAPKHAU, TIENTE_FK, TIGIA, LOAIHD, NPP_FK, from_hoadonnpp_fk) " + 
					"  select null, '" + this.getDateTime() + "', '" + ncc_fk + "' as ncc_fk, '" + this.getDateTime() + "', '" + this.getDateTime() + "', '" + userId + "', '" + userId + "', 0 as trangthai, " + 
					"  		'" + congty_fk + "' as congty_fk, null, 100000 as tiente_fk, 1 as tigia, 1 as loaiHD, a.NPP_DAT_FK, a.PK_SEQ " + 
					"  from ERP_HOADONNPP a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ  " + 
					"  			inner join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ " + 
					"  where a.PK_SEQ = '" + hdId + "' ";
			
			System.out.println("---INSERT HĐ NCC ( PARK ): " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới HĐ NCC PARK " + query;
				return msg;
			}
			
			query = "select IDENT_CURRENT('ERP_PARK') as nhId";
			rs = db.get(query);
			String hdnccId = "";
			rs.next();
			hdnccId = rs.getString("nhId");
			rs.close();
			
			query = "  insert ERP_HOADONNCC(mauhoadon, mausohoadon, kyhieu, sohoadon, ngayhoadon, sotienAVAT, sotienchietkhau, vat, sotienBVAT, thuesuat, park_fk, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, NGAYDENHANTT, TINHTHUENHAPKHAU, CONGTY_FK, NPP_FK, LOAIHD ) " + 
					"  select 'NA' as mauhoadon, 'NA' as mausohoadon, 'NA' as kyhieu, 'NA' as sohoadon, '' as ngayhoadon, sum(d.TIENAVAT) as tienAVAT, sum(a.chietkhau) as chietkhau, " + 
					"  		 sum(d.TIENBVAT * ( 1 + d.VAT / 100.0 ) ) as vat, sum(d.TIENBVAT) as tienBVAT, d.VAT as thuesuat, '" + hdnccId + "' as park_fk, " + 
					"  		'', '', "+userId+" , "+userId+", 0 as TRANGTHAI, '' NGAYDENHANTT, 0 as TINHTHUENHAPKHAU,  " + 
					"  		'" + congty_fk + "' as congty_fk, a.NPP_DAT_FK, 1 as LOAIHD " + 
					"  from ERP_HOADONNPP a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ " + 
					"  			inner join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ  " + 
					"  			inner join ERP_HOADONNPP_SP d on a.PK_SEQ = d.HOADON_FK " + 
					"  where a.PK_SEQ = '" + hdId + "' " + 
					"  group by d.VAT, c.MaFAST, a.NPP_DAT_FK ";
			
			System.out.println("---INSERT HĐ NCC: " + query );
			if( db.updateReturnInt(query) <= 0 )
			{
				msg = "Không tạo mới HĐ NCC " + query;
				return msg;
			}
			
			query = "  insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk, sanpham_fk, dvt, soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, SOLO)  " + 
					"  select ( select top(1) pk_seq from ERP_HOADONNCC where park_fk = '" + hdnccId + "' and thuesuat = b.THUEVAT ) as hoadonncc_fk, null, " + 
					"  		c.PK_SEQ, c.DVDL_FK, b.SoLuong_Chuan, b.DonGia_Chuan, b.SoLuong_Chuan * b.DonGia_Chuan as thanhtien, b.SoLuong_Chuan, '' as ngaynhandk, " + 
					"  			b.THUEVAT, b.SoLuong_Chuan * b.DonGia_Chuan * b.THUEVAT / 100.0 as TIENVAT, b.SOLO " + 
					"  from ERP_HOADONNPP a  " + 
					"  			inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.HOADON_FK " + 
					"  			inner join SANPHAM c on b.MA = c.MA " + 
					"  where a.PK_SEQ = '" + hdId + "' ";
			
			System.out.println("---INSERT HĐ NCC SP: " + query );
			if( db.updateReturnInt(query) <= 0 )
			{
				msg = "Không tạo mới HĐ NCC SP" + query;
				return msg;
			}
		}
		catch(Exception ex)
		{
			return "Lỗi khi chốt sổ xuất hàng: " + ex.getMessage();
		}
		
		return "";
	}

	private String TaoNhapHang(dbutils db, String hdId, String userId)
	{
		String msg = "";
		String query = "";
		
		try
		{
			query = " insert NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, SOCHUNGTU, hoadonnpp_fk, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, NHOMKENH_FK, YCXKNPP_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
					" select distinct ngayxuathd, ngayxuathd, NPP_DAT_FK, a.sohoadon, a.pk_seq, " +
					" 			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.NPP_DAT_FK ) ), " +
					"			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.NPP_DAT_FK ), " +
					"			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK )), " +
					"			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.NPP_DAT_FK ) ) ), " +
					" 	   '100001' as DVKD_FK, " + 
					" 		( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select kbh_fk from ERP_DONDATHANGNPP where PK_SEQ = ( select DDH_FK from ERP_HOADONNPP_DDH where HOADONNPP_FK = '" + hdId + "' ) ) ) NHOMKENH_FK, " + 
					" 	   '" + hdId + "', '1' as trangthai, '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "' " +
					" from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on a.PK_SEQ = b.hoadon_fk " +
					" where a.pk_seq = '" + hdId + "' ";
			
			System.out.println("---INSERT NHAN HANG: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG " + query;
				return msg;
			}
			
			query = "select IDENT_CURRENT('NHAPHANG') as nhId";
			ResultSet rs = db.get(query);
			String nhaphangId = "";
			rs.next();
			nhaphangId = rs.getString("nhId");
			rs.close();
			
			/*query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, thueVAT, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
					"  select '" + nhaphangId + "',   c.PK_SEQ as sanpham_fk, b.soluong, NULL, b.dongia, b.thueVAT, 0 as chietkhau, c.DVDL_FK, "+
					 " 	case when ISNULL( b.scheme, '' ) != '' then 1 else 0 end as LOAI, b.SCHEME, b.solo, b.ngayhethan "+
					 "  from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk "+
					 "  		inner join SANPHAM c on b.MA = c.MA   "+
					 " where a.PK_SEQ = '" + hdId + "' and b.soluong > 0";*/
			
			//DLPP không quản lý SOLO, dùng cột giá đã làm tròn làm SOLO
			query = " insert NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, thueVAT, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
					"  select '" + nhaphangId + "',   c.PK_SEQ as sanpham_fk, b.soluong, NULL, b.dongia, b.thueVAT, 0 as chietkhau, c.DVDL_FK, "+
					 " 	case when ISNULL( b.scheme, '' ) != '' then 1 else 0 end as LOAI, b.SCHEME, round( b.dongia, 0) as solo, a.ngayxuathd as ngayhethan "+
					 "  from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk "+
					 "  		inner join SANPHAM c on b.MA = c.MA   "+
					 " where a.PK_SEQ = '" + hdId + "' and b.soluong > 0";
			
			System.out.println("---INSERT NHAN HANG - SP: " + query );
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Không tạo mới NHAPHANG_SP " + query;
				return msg;
			}
			
			query = "insert NHAPHANG_DDH(nhaphang_fk, ddh_fk)  " +
					"select '" + nhaphangId + "' as nhID, ddh_fk  " +
					"from ERP_HOADONNPP_DDH where hoadonnpp_fk = '" + hdId + "'";
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG_DDH " + query;
				return msg;
			}
			
			//TANG KHO CỦA ĐỐI TÁC LUÔN
			Nhaphang nhaphang = new Nhaphang();
			if( !nhaphang.MTVChot(db, nhaphangId) )
			{
				msg = "Lỗi khi chốt nhập hàng " + nhaphang.getMsg();
				return msg;
			}
		}
		catch(Exception ex)
		{
			return "Lỗi khi chốt sổ xuất hàng: " + ex.getMessage();
		}
		
		return "";
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
		IErpSoxuathangNppList obj = new ErpSoxuathangNppList();
		
		String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "";
		obj.setLoaidonhang(loaidonhang);
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
	    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
	    
	    obj.setUserId(userId);
	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    
	    String nppId = request.getParameter("nppId");
	    if(nppId == null)
	    	nppId = "";
	    obj.setNppId(nppId);

	    String khId = request.getParameter("khId");
	    if(khId == null)
	    	khId = "";
	    obj.setNppTen(khId);
	    
	    System.out.println("---NPP ID: " + nppId);
	    if(action.equals("Tao moi"))
	    {
	    	IErpSoxuathangNpp lsxBean = new ErpSoxuathangNpp();
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
		    lsxBean.setDoituongId(session.getAttribute("doituongId"));
		    lsxBean.setTdv_dangnhap_id(tdv_dangnhap_id);
		   lsxBean.setNpp_duocchon_id(npp_duocchon_id);
	    	
	    	//lsxBean.setXuatcho(phanloai);
	    	lsxBean.createRs();
	    	
	    	session.setAttribute("dvkdId", "");
			session.setAttribute("kbhId", "");
			session.setAttribute("nppId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNppNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
	    		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	    obj.setDoituongId(session.getAttribute("doituongId"));
	    	    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	   obj.setNpp_duocchon_id(npp_duocchon_id);
	    	    
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNpp.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    		if (action.equals("toExcel")) 
				{	
	    			OutputStream out = response.getOutputStream();
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=SoXuatHang.xlsm");
					
					obj.setLoainhanvien(session.getAttribute("loainhanvien"));
				    obj.setDoituongId(session.getAttribute("doituongId"));
				   obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	    obj.setNpp_duocchon_id(npp_duocchon_id);
					
				    String query = getExportExcel(request, obj);
					System.out.println("Query Excell "+query);
					try {
						ExportToExcel(out,obj,query);
					} catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
	    		else
		    	{
	    			obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    		    obj.setDoituongId(session.getAttribute("doituongId"));
	    		    obj.setTdv_dangnhap_id(tdv_dangnhap_id);
		    	    obj.setNpp_duocchon_id(npp_duocchon_id);
	    		    
			    	String search = getSearchQuery(request, obj);
			    	System.out.println();
			    	System.out.println("câu truy vấn search:"+ search);
			    	obj.init(search);
					obj.setUserId(userId);
					
			    	session.setAttribute("obj", obj);  	
		    		session.setAttribute("userId", userId);
			
		    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpSoXuatHangNpp.jsp";
		    		response.sendRedirect(nextJSP);
		    	}
	    }
	}
	
	private void ExportToExcel(OutputStream out,IErpSoxuathangNppList obj,String query )throws Exception
	 {
		try{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\SoXuatHang.xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook,obj,query);		
			workbook.save(out);	

		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		
	}
	
	private void TaoBaoCao(Workbook workbook,IErpSoxuathangNppList obj,String query )throws Exception
	{
		try{
			
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Soxuathang");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 80.0f);
		
//			Cell cell = cells.getCell("A1");
//			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Danh Sách Sổ Xuất Hàng");
			
			Cell cell = cells.getCell("A2");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+getDateTime());

			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 8;

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );cell.setValue(rsmd.getColumnName(i));
				ReportAPI.setCellBackground(cell, Color.YELLOW, BorderLineType.THIN, true, 0);	
				
			
			}
			countRow ++;
			
			
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE )
					{
						cell.setValue(rs.getDouble(i));
						
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}

	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	private String getSearchQuery(HttpServletRequest request, IErpSoxuathangNppList obj)
	{
		Utility util = new Utility();
		/*String query =
				"select  distinct a.PK_SEQ, a.machungtu, a.trangthai, a.created_date as ngayyeucau, isnull(c.ten, d.ten) as nppTEN, N'Hàng bán' as khoTEN, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
						"	 (	Select hd.sohoadon + ', ' AS [text()]  " +
						"		From ERP_SOXUATHANGNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq  " +
						"		Where YCXK1.soxuathang_fk = a.pk_seq  " +
						"		For XML PATH ('') )   as ddhIds    " +
						"from ERP_SOXUATHANGNPP a " + 
						//" inner join KHO b on a.kho_fk = b.pk_seq " +
						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						util.getPhanQuyen_TheoNhanVien("KHACHHANG", "hd", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() ) +						
						"   left join NVGN_KH nvgn on a.KHACHHANG_FK=nvgn.KHACHHANG_FK   "+
						"   left join ddkd_khachhang ddkd on a.PK_SEQ=ddkd.khachhang_fk "+
						"   left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" + obj.getNppId() + "' "; */
		
		String query = "  select distinct a.PK_SEQ, a.machungtu, a.trangthai, a.created_date as ngayyeucau, isnull( isnull(c.ten, d.ten), '')" +
				" as nppTEN, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, " +
		"	 (	Select hd.sohoadon + ', ' AS [text()]  " +
		"		From ERP_SOXUATHANGNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq  " +
		"		Where YCXK1.soxuathang_fk = a.pk_seq  " +
		"		For XML PATH ('') )  as ddhIds,    " +
		"	 N'Hàng bán' as khoTEN, isnull( nvgn.ten, '' ) as nvgn    " +
		"from ERP_SOXUATHANGNPP a " + 
		//" inner join KHO b on a.kho_fk = b.pk_seq " +
		"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
		"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
		"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
		"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ " + 
		"	inner join ERP_SOXUATHANGNPP_DDH sxh on a.pk_seq = sxh.soxuathang_fk	" +
		"	inner join ERP_HOADONNPP hd on sxh.hoadon_fk = hd.pk_seq	" +
		"	left join NHANVIENGIAONHAN nvgn on a.nvgn_fk = nvgn.pk_seq	" +
		" where a.npp_fk = '" +  obj.getNppId() + "' "; 
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
	
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setNppTen(khId);
		
		String khohh = request.getParameter("khohhid");
		if(khohh == null)
			khohh = "";
		obj.setKhohh(khohh);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoigiao = request.getParameter("nguoigiao");
		if(nguoigiao == null)
			nguoigiao = "";
		obj.setNguoigiao(nguoigiao);
		
		// mã chứng từ
		String machungtu = request.getParameter("machungtu");
		if(machungtu == null){
			machungtu = "";
		}
		obj.setMaChungTu(machungtu);
		
		if(machungtu.length() >0){
			query += " and a.machungtu like '%" + machungtu + "%' ";
		}
		
		if(tungay.length() > 0)
			query += " and a.ngayyeucau >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayyeucau <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai >= '" + trangthai + "'";
		}
		
		if(khId.length() > 0)
		{
			//query += " and isnull(a.npp_Dat_fk, a.khachhang_Fk) = '" + khId + "'";
			query += " and isnull(d.timkiem, c.timkiem) like N'%" + khId + "%'";
		}
		
		if(khohh.length()>0)
		{
			query+=" and a.kho_fk like '%" + khohh + "%' ";
			//query+=" and a.kho_fk="+khohh;
		}
		
		if(nguoitao.length()>0)
		{
			query+=" and dbo.ftBoDau( nv.ten ) like N'%" + util.replaceAEIOU( nguoitao ) + "%'";
		}
		if(nguoigiao.length()>0)
		{
			//query+=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiao ) + "%'";
			query+=" and a.nvgn_fk=" + nguoigiao ;
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and ddkd.ddkd_fk="+nvbanhang;
		}
		System.out.print("::: LAY SO XUAT HANG: " + query);
		return query;
	}
	
	private String getExportExcel(HttpServletRequest request, IErpSoxuathangNppList obj)
	{
		Utility util = new Utility();
		String query =
				"select  a.machungtu as N'Mã chứng từ', a.created_date as N'Ngày yêu cầu', isnull(c.ten, d.ten) as N'Khách hàng', N'Hàng bán' as N'Kho xuất', " +
						"	 (	Select hd.sohoadon + ', ' AS [text()]  " +
						"		From ERP_SOXUATHANGNPP_DDH YCXK1 inner join ERP_HOADONNPP hd on  YCXK1.hoadon_fk = hd.pk_seq  " +
						"		Where YCXK1.soxuathang_fk = a.pk_seq  " +
						"		For XML PATH ('') )   as N'Số hóa đơn' ,case when a.trangthai  = 0 then N'Chưa chốt' else N'Đã chốt' end as N'Trạng thái' , NV.TEN as N'Người tạo', a.NGAYTAO as N'Ngày tạo', a.NGAYSUA as N'Ngày sửa' , NV2.TEN as N'Người sửa'   " +
						"from ERP_SOXUATHANGNPP a " + 
						//" inner join KHO b on a.kho_fk = b.pk_seq " +
						"	left join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq " +
						"	left join KHACHHANG d on a.khachhang_fk = d.pk_seq  " +
						"   left join NVGN_KH nvgn on a.KHACHHANG_FK=nvgn.KHACHHANG_FK   "+
						"   left join ddkd_khachhang ddkd on a.PK_SEQ=ddkd.khachhang_fk "+
						"   left join NHANVIENGIAONHAN nvgnn on nvgnn.PK_SEQ=nvgn.NVGN_FK  "+	
						"	inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"	inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.npp_fk = '" 
						+ obj.getNppId() + "' and " +
		                " exists ( select cast(KHO_fK as varchar(10))  from NHANVIEN_KHO " +
		                " kk where nhanvien_fk = '" + obj.getUserId() +"' and CHARINDEX(cast (kk.kho_fk " +
		                " as varchar(50)),cast (a.kho_fk as varchar(50)) )<>0)"; 
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String nvbanhang=request.getParameter("nvbanhang");
		if(nvbanhang==null)
			nvbanhang="";
		obj.setNvbanhang(nvbanhang);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
	
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setNppTen(khId);
		
		String khohh = request.getParameter("khohhid");
		if(khohh == null)
			khohh = "";
		obj.setKhohh(khohh);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoigiao = request.getParameter("nguoigiao");
		if(nguoigiao == null)
			nguoigiao = "";
		obj.setNguoigiao(nguoigiao);
		
		// mã chứng từ
		String machungtu = request.getParameter("machungtu");
		if(machungtu == null){
			machungtu = "";
		}
		obj.setMaChungTu(machungtu);
		
		if(machungtu.length() >0){
			query += " and a.pk_seq= "+ machungtu;
		}
		
		if(tungay.length() > 0)
			query += " and a.ngayyeucau >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngayyeucau <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
		{
			if(trangthai.equals("0"))
				query += " and a.TrangThai = '" + trangthai + "'";
			else
				query += " and a.TrangThai >= '" + trangthai + "'";
		}

		if(khId.length() > 0)
		{
			//query += " and isnull(a.npp_Dat_fk, a.khachhang_Fk) = '" + khId + "'";
			query += " and isnull(d.timkiem, c.timkiem) like N'%" + khId + "%'";
		}
		
		if(khohh.length()>0)
		{
			query+=" and a.kho_fk like '%" + khohh + "%' ";
			//query+=" and a.kho_fk="+khohh;
		}
		
		if(nguoitao.length()>0)
		{
			query+=" and dbo.ftBoDau( nv.ten ) like N'%" + util.replaceAEIOU( nguoitao ) + "%'";
		}
		if(nguoigiao.length()>0)
		{
			query+=" and dbo.ftBoDau( nvgnn.ten ) like N'%" + util.replaceAEIOU( nguoigiao ) + "%'";
		}
		
		if(nvbanhang.length()>0)
		{
			query+=" and ddkd.ddkd_fk="+nvbanhang;
		}
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
