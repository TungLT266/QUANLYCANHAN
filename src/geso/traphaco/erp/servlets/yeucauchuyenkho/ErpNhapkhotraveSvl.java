package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.kho.KhoNewSvl;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpNhapkhotrave;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpNhapkhotraveList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpNhapkhotrave;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpNhapkhotraveList;
import geso.traphaco.erp.util.Library;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpNhapkhotraveSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
    public ErpNhapkhotraveSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpNhapkhotraveList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpNhapkhotraveList();
	    
	    if (action.equals("delete"))
	    {	
	    	String msg = this.DeleteChuyenKho(lsxId, userId);
			obj.setMsg(msg);
	    }
	    else if(action.equals("chot"))
    	{
    		String msg = this.Chot(lsxId, userId);
			obj.setMsg(msg);
    	}
	    else if(action.equals("unChot"))
    	{
    		String msg = this.UnChot(lsxId, userId);
			obj.setMsg(msg);
    	}
	    
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVe.jsp";
		response.sendRedirect(nextJSP);
	}

	private String UnChot(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";

			//Check Khoa so thang
			Utility util = new Utility();
			msg = util.CheckKhoaSoKho(db, "", "ERP_DONTRAHANG", "ngaynhapkho", lsxId);
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_DONTRAHANG set trangthai = '1', nguoisua = '" + userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai in (2,3,4)";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the chốt: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TĂNG KHO NHẬN
			query =  " select b.khott_fk, b.NGAYNHAPKHO, a.SanPham_fk, a.SoLo, a.NgayHetHan,  "+ 
					 "  		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong   "+ 
					 " from ERP_DONTRAHANG_SANPHAM a inner join ERP_DONTRAHANG b on a.dontrahang_FK = b.PK_SEQ  "+ 
					 " where b.PK_SEQ = '" + lsxId + "' and a.soluong > 0  "+ 
					 " group by b.khott_fk,b.NGAYNHAPKHO, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
		    ResultSet rs = db.get(query);
			 
			while( rs.next() )
			{
				String khoId = rs.getString("khott_fk");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String NGAYNHAPKHO = rs.getString("NGAYNHAPKHO");
				
				String loaidoituong = "";
				String doituongId = "";
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");
				
				//Giảm kho
				double soluong = -1 * rs.getDouble("soluong");
				
				msg = util.Update_KhoTT_NEW(rs.getString("NGAYNHAPKHO"), "Mở Chốt nhận hàng trả lại", db, khoId, spId, solo, ngayhethan, NGAYNHAPKHO, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, 0, "", "");
				if( msg.trim().length() > 0 )
				{
					msg = "2.Khong the mở chốt: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			rs.close();
			
			query = "delete ERP_PHATSINHKETOAN where loaichungtu = N'Hóa đơn trả hàng' and sochungtu = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "3.Khong the mở chốt: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	public  String Chot(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "select ngaynhapkho, khott_fk from ERP_DONTRAHANG where pk_seq = '" + lsxId + "' ";
			ResultSet rs = db.get(query);
			String ngaynhapkho = "";
			String khott_fk = "";
			if( rs.next() )
			{
				ngaynhapkho = rs.getString("ngaynhapkho") == null ? "" : rs.getString("ngaynhapkho");
				khott_fk = rs.getString("khott_fk") == null ? "" : rs.getString("khott_fk");
				rs.close();
			}
			
			if( ngaynhapkho.trim().length() <= 0 )
			{
				msg = "Bạn phải chọn ngày nhập kho";
				db.getConnection().rollback();
				return msg;
			}
			
			if( khott_fk.trim().length() <= 0 )
			{
				msg = "Bạn phải chọn kho nhận";
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_DONTRAHANG set trangthai = '2', nguoisua = '" + userId + "', ngaysua = '" + getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai=1";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the chốt: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//TĂNG KHO NHẬN
			Utility util = new Utility();

			query =  " select b.NGAYNHAPKHO, a.SanPham_fk, a.SoLo, a.NgayHetHan,  "+ 
					 "  		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong   "+ 
					 " from ERP_DONTRAHANG_SANPHAM a inner join ERP_DONTRAHANG b on a.dontrahang_FK = b.PK_SEQ  "+ 
					 " where b.PK_SEQ = '" + lsxId + "' and a.soluong > 0  "+ 
					 " group by b.NGAYNHAPKHO, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
		    rs = db.get(query);
			 
			while( rs.next() )
			{
				String khoId = khott_fk;
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				
				String loaidoituong = "";
				String doituongId = "";
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");
				
				double soluong = rs.getDouble("soluong");
				
				msg = util.Update_KhoTT_NEW(rs.getString("NGAYNHAPKHO"), "Chốt nhận hàng trả lại", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, 0, "", "");
				if( msg.trim().length() > 0 )
				{
					msg = "2.Khong the chốt: " + query;
					db.getConnection().rollback();
					return msg;
				}
			}
			rs.close();
			
			query = "update ERP_DONTRAHANG_SANPHAM set ngaynhapkho = '" + ngaynhapkho + "' where dontrahang_FK = '" + lsxId + "' ";
			if( db.updateReturnInt(query) < 1 )
			{
				msg = "3.Khong the chốt: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			Library lb = new Library();
			msg=lb.capnhatketoan_Nhap_trahang(userId, db,lsxId , false);
			if(msg.length()>0)
			{
				msg += "Không thể chạy kế toán cho phiếu trả hàng :"+msg;
				db.getConnection().rollback();
				return msg;
			}
				
			query = " select ISNULL(TT.SOHOADON,'') AS SOHOADON,SUBSTRING(TT.NGAYNHAPKHO, 1, 4) AS NAM, SUBSTRING(TT.NGAYNHAPKHO, 6, 2) AS THANG, TT.NGAYNHAPKHO as NGAYCHUNGTU, N'Hóa đơn trả hàng' AS LOAICHUNGTU, TT.PK_SEQ AS SOCHUNGTU, TK.isNPP,   "+
			"      TK.tienhang, TK.tienthue, 100000 AS TIENTEGOC,  "+
			"    	TK.DOITUONGNO_TIENHANG, TK.MADOITUONGNO_TIENHANG, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUNO_TIENHANG AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANNO_TIENHANG,   "+
			"    	TK.DOITUONGCO_TIENHANG, TK.MADOITUONGCO_TIENHANG, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUCO_TIENHANG AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANCO_TIENHANG,   "+
			"    	TK.DOITUONGNO_TIENTHUE, TK.MADOITUONGNO_TIENTHUE, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUNO_TIENTHUE AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANNO_TIENTHUE,   "+
			"    	TK.DOITUONGCO_TIENTHUE, TK.MADOITUONGCO_TIENTHUE, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUCO_TIENTHUE AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANCO_TIENTHUE   "+
			"  from    "+
			"  (   "+
			// NẾU CÔNG NỢ HO THÌ NPP_FK=1
			"    	select 1 NPP_FK, round( b.soluong * b.dongia, 0) as tienhang,   "+
			"  			N'Sản phẩm' as DOITUONGNO_TIENHANG, c.PK_SEQ AS MADOITUONGNO_TIENHANG, d.TaikhoanHangbantralai_fk as SOHIEUNO_TIENHANG,   "+
			"    			case a.xuatcho when 0 then N'Chi nhánh/Đối tác' else N'Khách hàng' end as DOITUONGCO_TIENHANG,  "+
			" 			case a.xuatcho when 0 then e.pk_seq else f.pk_seq end AS MADOITUONGCO_TIENHANG, '13111000' as SOHIEUCO_TIENHANG,  "+
			//"  			round ( round( b.soluong * b.dongia, 0) * b.VAT / 100.0, 0 ) as tienthue,   "+
			"  			round ( isnull(b.tienVAT, 0) , 0 ) as tienthue,   "+
			"  			N'' as DOITUONGNO_TIENTHUE, '' AS MADOITUONGNO_TIENTHUE, '33314000' as SOHIEUNO_TIENTHUE,   "+
			"    			case a.xuatcho when 0 then N'Chi nhánh/Đối tác' else N'Khách hàng' end as DOITUONGCO_TIENTHUE,  "+
			" 			case a.xuatcho when 0 then e.pk_seq else f.pk_seq end AS MADOITUONGCO_TIENTHUE, '13111000' as SOHIEUCO_TIENTHUE,    "+
			"			case a.xuatcho when 0 then 1 else 0 end as isNPP	" +
			"    	from ERP_DONTRAHANG a inner join ERP_DONTRAHANG_SANPHAM b on a.PK_SEQ = b.dontrahang_fk   "+
			"  			left join ERP_SANPHAM c on b.sanpham_fk = c.pk_seq  "+
			"  			left join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq  "+
			"    		left join NHAPHANPHOI e on a.doituongid = e.pk_seq  and a.xuatcho=0 "+
			" 			left join KHACHHANG f on a.doituongid = f.pk_seq  and a.xuatcho=1 "+
			"    	where  a.PK_SEQ = '" + lsxId + "'    "+
			"  )   "+
			"  TK, ERP_DONTRAHANG TT  where TT.pk_seq = '" + lsxId + "' ";
			
			System.out.println("::: LAY DINH KHOAN: " + query );
			util.HuyUpdate_TaiKhoan(db, lsxId, "Hóa đơn trả hàng");
		    rs = db.get(query);
			while(rs.next())
			{
				//TIEN HÀNG
				msg = util.Update_TaiKhoan_FULL(db, rs.getString("THANG"), rs.getString("NAM"), rs.getString("NGAYCHUNGTU"), rs.getString("NGAYCHUNGTU"), 
						rs.getString("LOAICHUNGTU"), rs.getString("SOCHUNGTU"), rs.getString("TAIKHOANNO_TIENHANG"), rs.getString("TAIKHOANCO_TIENHANG"), 
						"NULL", rs.getString("tienhang"), rs.getString("tienhang"), rs.getString("DOITUONGNO_TIENHANG"), rs.getString("MADOITUONGNO_TIENHANG"), 
						rs.getString("DOITUONGCO_TIENHANG"), rs.getString("MADOITUONGCO_TIENHANG"), "0", "NULL", "NULL", 
						rs.getString("TIENTEGOC"), "0", "1", rs.getString("tienhang"), rs.getString("tienhang"), "", "0", "Hóa đơn trả hàng - tiền hàng", rs.getString("SOCHUNGTU"), "1", "", "", 
						"", "", "", "", "", "0");
				
				if( msg.trim().length() > 0 )
				{
					msg = "1.Lỗi định khoản tiền hàng: " + msg;
					db.getConnection().rollback();
					return msg;
				}
				
				if(rs.getString("SOHOADON").length()>0)
				{
					//TIEN THUE
					msg = util.Update_TaiKhoan_FULL(db, rs.getString("THANG"), rs.getString("NAM"), rs.getString("NGAYCHUNGTU"), rs.getString("NGAYCHUNGTU"), 
							rs.getString("LOAICHUNGTU"), rs.getString("SOCHUNGTU"), rs.getString("TAIKHOANNO_TIENTHUE"), rs.getString("TAIKHOANCO_TIENTHUE"), 
							"NULL", rs.getString("tienthue"), rs.getString("tienthue"), rs.getString("DOITUONGNO_TIENTHUE"), rs.getString("MADOITUONGNO_TIENTHUE"), 
							rs.getString("DOITUONGCO_TIENTHUE"), rs.getString("MADOITUONGCO_TIENTHUE"), "0", "NULL", "NULL", 
							rs.getString("TIENTEGOC"), "0", "1", rs.getString("tienthue"), rs.getString("tienthue"), "", "0", "Hóa đơn trả hàng - tiền thuế", rs.getString("SOCHUNGTU"), "1", "", "", 
							"", "", "", "", "", "0");
					
					if( msg.trim().length() > 0 )
					{
						msg = "2.Lỗi định khoản tiền thuế: " + msg;
						db.getConnection().rollback();
						return msg;
					}
				}
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	public  String ChayLaiDinhKhoan(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";

			Utility util = new Utility();
				
			Library lb = new Library();
			msg=lb.capnhatketoan_Nhap_trahang(userId, db,lsxId , false);
			if(msg.length()>0)
			{
				msg += "Không thể chạy kế toán cho phiếu trả hàng :"+msg;
				db.getConnection().rollback();
				return msg;
			}
				
			query = " select ISNULL(TT.SOHOADON,'') AS SOHOADON,SUBSTRING(TT.NGAYNHAPKHO, 1, 4) AS NAM, SUBSTRING(TT.NGAYNHAPKHO, 6, 2) AS THANG, TT.NGAYNHAPKHO as NGAYCHUNGTU, N'Hóa đơn trả hàng' AS LOAICHUNGTU, TT.PK_SEQ AS SOCHUNGTU, TK.isNPP,   "+
			"      TK.tienhang, TK.tienthue, 100000 AS TIENTEGOC,  "+
			"    	TK.DOITUONGNO_TIENHANG, TK.MADOITUONGNO_TIENHANG, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUNO_TIENHANG AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANNO_TIENHANG,   "+
			"    	TK.DOITUONGCO_TIENHANG, TK.MADOITUONGCO_TIENHANG, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUCO_TIENHANG AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANCO_TIENHANG,   "+
			"    	TK.DOITUONGNO_TIENTHUE, TK.MADOITUONGNO_TIENTHUE, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUNO_TIENTHUE AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANNO_TIENTHUE,   "+
			"    	TK.DOITUONGCO_TIENTHUE, TK.MADOITUONGCO_TIENTHUE, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TKKT WHERE SOHIEUTAIKHOAN = TK.SOHIEUCO_TIENTHUE AND TKKT.NPP_FK = TK.npp_fk ) as TAIKHOANCO_TIENTHUE   "+
			"  from    "+
			"  (   "+
			// NẾU CÔNG NỢ HO THÌ NPP_FK=1
			"    	select 1 NPP_FK, round( b.soluong * b.dongia, 0) as tienhang,   "+
			"  			N'Sản phẩm' as DOITUONGNO_TIENHANG, c.PK_SEQ AS MADOITUONGNO_TIENHANG, d.TaikhoanHangbantralai_fk as SOHIEUNO_TIENHANG,   "+
			"    			case a.xuatcho when 0 then N'Chi nhánh/Đối tác' else N'Khách hàng' end as DOITUONGCO_TIENHANG,  "+
			" 			case a.xuatcho when 0 then e.pk_seq else f.pk_seq end AS MADOITUONGCO_TIENHANG, '13111000' as SOHIEUCO_TIENHANG,  "+
			//"  			round ( round( b.soluong * b.dongia, 0) * b.VAT / 100.0, 0 ) as tienthue,   "+
			"  			round ( b.tienVAT , 0 ) as tienthue,   "+
			"  			N'' as DOITUONGNO_TIENTHUE, '' AS MADOITUONGNO_TIENTHUE, '33314000' as SOHIEUNO_TIENTHUE,   "+
			"    			case a.xuatcho when 0 then N'Chi nhánh/Đối tác' else N'Khách hàng' end as DOITUONGCO_TIENTHUE,  "+
			" 			case a.xuatcho when 0 then e.pk_seq else f.pk_seq end AS MADOITUONGCO_TIENTHUE, '13111000' as SOHIEUCO_TIENTHUE,    "+
			"			case a.xuatcho when 0 then 1 else 0 end as isNPP	" +
			"    	from ERP_DONTRAHANG a inner join ERP_DONTRAHANG_SANPHAM b on a.PK_SEQ = b.dontrahang_fk   "+
			"  			left join ERP_SANPHAM c on b.sanpham_fk = c.pk_seq  "+
			"  			left join ERP_LOAISANPHAM d on c.loaisanpham_fk = d.pk_seq  "+
			"    		left join NHAPHANPHOI e on a.doituongid = e.pk_seq  and a.xuatcho=0 "+
			" 			left join KHACHHANG f on a.doituongid = f.pk_seq  and a.xuatcho=1 "+
			"    	where  a.PK_SEQ = '" + lsxId + "'    "+
			"  )   "+
			"  TK, ERP_DONTRAHANG TT  where TT.pk_seq = '" + lsxId + "' ";
			
			System.out.println("::: LAY DINH KHOAN: " + query );
			util.HuyUpdate_TaiKhoan(db, lsxId, "Hóa đơn trả hàng");
		    ResultSet	rs = db.get(query);
			while(rs.next())
			{
				//TIEN HÀNG
				msg = util.Update_TaiKhoan_FULL(db, rs.getString("THANG"), rs.getString("NAM"), rs.getString("NGAYCHUNGTU"), rs.getString("NGAYCHUNGTU"), 
						rs.getString("LOAICHUNGTU"), rs.getString("SOCHUNGTU"), rs.getString("TAIKHOANNO_TIENHANG"), rs.getString("TAIKHOANCO_TIENHANG"), 
						"NULL", rs.getString("tienhang"), rs.getString("tienhang"), rs.getString("DOITUONGNO_TIENHANG"), rs.getString("MADOITUONGNO_TIENHANG"), 
						rs.getString("DOITUONGCO_TIENHANG"), rs.getString("MADOITUONGCO_TIENHANG"), "0", "NULL", "NULL", 
						rs.getString("TIENTEGOC"), "0", "1", rs.getString("tienhang"), rs.getString("tienhang"), "", "0", "Hóa đơn trả hàng - tiền hàng", rs.getString("SOCHUNGTU"), "1", "", "", 
						"", "", "", "", "", "0");
				
				if( msg.trim().length() > 0 )
				{
					msg = "1.Lỗi định khoản tiền hàng: " + msg;
					db.getConnection().rollback();
					return msg;
				}
				
				if(rs.getString("SOHOADON").length()>0)
				{
					//TIEN THUE
					msg = util.Update_TaiKhoan_FULL(db, rs.getString("THANG"), rs.getString("NAM"), rs.getString("NGAYCHUNGTU"), rs.getString("NGAYCHUNGTU"), 
							rs.getString("LOAICHUNGTU"), rs.getString("SOCHUNGTU"), rs.getString("TAIKHOANNO_TIENTHUE"), rs.getString("TAIKHOANCO_TIENTHUE"), 
							"NULL", rs.getString("tienthue"), rs.getString("tienthue"), rs.getString("DOITUONGNO_TIENTHUE"), rs.getString("MADOITUONGNO_TIENTHUE"), 
							rs.getString("DOITUONGCO_TIENTHUE"), rs.getString("MADOITUONGCO_TIENTHUE"), "0", "NULL", "NULL", 
							rs.getString("TIENTEGOC"), "0", "1", rs.getString("tienthue"), rs.getString("tienthue"), "", "0", "Hóa đơn trả hàng - tiền thuế", rs.getString("SOCHUNGTU"), "1", "", "", 
							"", "", "", "", "", "0");
					
					if( msg.trim().length() > 0 )
					{
						msg = "2.Lỗi định khoản tiền thuế: " + msg;
						db.getConnection().rollback();
						return msg;
					}
				}
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}
	
	private String DeleteChuyenKho(String lsxId, String userId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			String query = "";
			
			query = "update ERP_DONTRAHANG set trangthai = '0', nguoisua = '" + userId + "', ngaysua = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai=1";
			if(db.updateReturnInt(query)!=1)
			{
				msg = "2.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
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
	    
		IErpNhapkhotraveList obj = new ErpNhapkhotraveList();
	 
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    obj.setUserId(userId);
	    
	    if(action.equals("Tao moi"))
	    {
	    	IErpNhapkhotrave lsxBean = new ErpNhapkhotrave();
	    	lsxBean.setUserId(userId);
	    	lsxBean.createRs();
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVeNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVe.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.init(search);
				
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhapKhoTraVe.jsp";
	    		response.sendRedirect(nextJSP);
	    		
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpNhapkhotraveList obj)
	{
		String query =  "  select a.PK_SEQ, a.trangthai, a.ngaytra, a.lydo, NV.TEN as nguoitao, a.NGAYSUA, a.NGAYTAO, isnull(NV2.TEN, '') as nguoisua, a.ghichu,  " + 
						"  			b.Ten as doituong    " + 
						"  from ERP_DONTRAHANG a left join NHAPHANPHOI b on a.doituongId = b.pk_seq    " + 
						"  	left join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ    " + 
						"  	left join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 ";
		
		String tungaySX = request.getParameter("tungay");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngay");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);

		String soPhieu = request.getParameter("soPhieu");
		if(soPhieu == null)
			soPhieu = "";
		obj.setSophieu(soPhieu);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String masanpham = request.getParameter("masanpham");
		if(masanpham == null)
			masanpham = "";
		obj.setMasanpham(masanpham);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setSohoadon(sohoadon);
		
		String khonhanId = request.getParameter("khonhanId");
		if(khonhanId == null)
			khonhanId = "";
		obj.setKhonhanId(khonhanId);
		
		if(tungaySX.length() > 0)
			query += " and a.ngaytra >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaytra <= '" + denngaySX + "'";

		if(soPhieu.length() > 0)
			query += " and a.PK_SEQ LIKE '%" + soPhieu + "%'";
		
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(masanpham.length() > 0)
			query += " and a.pk_seq in (select a.dontrahang_fk from ERP_DONTRAHANG_SANPHAM a inner join ERP_SANPHAM b on a.sanpham_fk=b.pk_seq where b.ma='"+masanpham+"')";
		
		if(sohoadon.length() > 0)
			query += " and a.sohoadon = '"+sohoadon+"'";
		
		if(khonhanId.length() > 0)
			query += " and khott_fk = '"+khonhanId+"'";
		
		System.out.print(query);
		return query;
	}
		
	public static String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] arg)
	{
		ErpNhapkhotraveSvl nk = new ErpNhapkhotraveSvl();
	
		dbutils db = new dbutils();
		String query = "select pk_seq from ERP_DONTRAHANG where trangthai = 2 and pk_seq in ( 100356,100349,100348,100341,100239,100231,100212,100211,100210 ) ";
		ResultSet rs = db.get(query);
		try 
		{
			while( rs.next() )
			{
				String nkId = rs.getString("pk_seq");
				
				String msg = nk.ChayLaiDinhKhoan(nkId, "100002");
				
				System.out.println("::: KQ CHAY... " + msg);
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("::: CHAY XONG... ");
		
	}
	
	
	
}
