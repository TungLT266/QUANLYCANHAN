package geso.traphaco.distributor.beans.hopdong.imp;

import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Toll_GuiMail;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpDonhangNppETC implements IErpDonhangNppETC
{
	String userId;
	String id;
	
	String tungay;
	String denngay;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang; 
	String chietkhau;
	String vat;
	
	String khoNhanId;
	ResultSet khoNhanRs;
	ResultSet SPRs;
	String dungchungKENH;
	String khId;
	ResultSet khRs;
	
	String dvkdId;
	ResultSet dvkdRs;
	
	String kbhId;
	ResultSet kbhRs;
	
	String gsbhId;
	ResultSet gsbhRs;
	
	String ddkdId;
	ResultSet ddkdRs;
	ResultSet ddkdSPRs;
	
	ResultSet dvtRs;
	
	String hopdongId;
	ResultSet hopdongRs;
	
	String khKGId;
	String tdv_dangnhap_id;
	ResultSet khKGRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spDonviChuan;
	String[] spSoluongChuan;
	String[] spGianhap;
	String[] spGianhapGOC;
	String[] spChietkhau;
	String[] spVAT;
	String[] spTungay;
	String[] spDenngay;
	String[] spTrongluong;
	String[] spThetich;
	String[] spQuyDoi;
	String[] spSoluongton;
	String[] spSCheme;
	String[] spTDV;
	String[] spCHIETKHAU_BHKM;
	String[] spDagiao;
	String[] spChiavaodongia;
	
	Hashtable<String, String> sanpham_soluong;
	Hashtable<String, String> sanpham_soluongDAXUAT;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String donhangMuon;
	String phanloai;
	String capduyet;
	
	ResultSet congnoRs;
	ResultSet tichluyRs;
	
	dbutils db;
	Utility util;
	
	String doituong;
	String chophepsuagia;
	String isKhuyenmai;
	
	String isMTV;
	String sohopdong;
	String capdogiaohang;
	String npp_duocchon_id;
	
	String maphieuMH;
	String sotiengiam;

	String CtyId;

	String tratichluy;
	String tienTichluy;
	String tienChietkhau;
	
	double TongNo; //1.
	double HanMucNo; //2.
	double SoNgayNo; //3.
	double NoTrongHan; //4.
	double NoQuaHan; //5.
	double NoXau;
	double NoQuaXau;
	
	String capnhatTDV;
	String lydokhongduyet;
	
	public ErpDonhangNppETC()
	{
		this.id = "";
		this.hopdongId = "";
		this.tungay = this.getDateTime();
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.loaidonhang = "2";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.dungchungKENH = "0";
		
		this.dhCk_diengiai = new String[]{"", "", "", ""};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		
		this.donhangMuon = "0";
		this.phanloai = "0";
		this.capduyet = "CS";
		this.doituong = "";
		this.chophepsuagia = "";
		this.khKGId = "";
		this.isKhuyenmai = "0";
		
		this.isMTV = "0";
		this.sohopdong = "";
		this.capdogiaohang = "";
		this.npp_duocchon_id = "";
		
		this.maphieuMH = "";
		this.sotiengiam = "";
		this.tratichluy = "0";
		
		this.CtyId = "";
		this.tienTichluy = "";
		this.tienChietkhau = "0";
		
		// CÔNG NỢ
		this.TongNo = 0; //1.
		this.HanMucNo = 0; //2.
		this.SoNgayNo = 0; //3.
		this.NoTrongHan = 0; //4.
		this.NoQuaHan = 0; //5.
		this.NoXau = 0;
		this.NoQuaXau = 0;
		
		this.capnhatTDV = "0";
		this.lydokhongduyet = "";
		
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
		
		this.db = new dbutils();
		this.util = new Utility();
	}
	
	public ErpDonhangNppETC(String id)
	{
		this.id = id;
		this.hopdongId = "";
		this.tungay = this.getDateTime();
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.khId = "";
		this.msg = "";
		this.loaidonhang = "2";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "10";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";
		this.dungchungKENH = "0";

		this.isMTV = "0";
		this.sohopdong = "";
		
		this.dhCk_diengiai = new String[]{"", "", "", ""};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		
		this.donhangMuon = "0";
		this.phanloai = "0";
		this.capduyet = "CS";
		this.doituong = "";
		this.chophepsuagia = "";
		this.khKGId = "";
		this.isKhuyenmai = "0";
		this.capdogiaohang = "";
		this.npp_duocchon_id = "";
		
		this.maphieuMH = "";
		this.sotiengiam = "";
		this.tratichluy = "0";
		
		this.CtyId = "";
		this.tienTichluy = "";
		this.tienChietkhau = "0";
		
		// CÔNG NỢ
		this.TongNo = 0; //1.
		this.HanMucNo = 0; //2.
		this.SoNgayNo = 0; //3.
		this.NoTrongHan = 0; //4.
		this.NoQuaHan = 0; //5.
		this.NoXau = 0;
		this.NoQuaXau = 0;
		
		this.capnhatTDV = "0";
		this.lydokhongduyet = "";
		
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
				
		this.db = new dbutils();
		this.util = new Utility();
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	double stg = 0;
	public boolean createNK() 
	{	
		if(this.tungay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày bắt đầu hợp đồng";
			return false;
		}
		
		if(this.denngay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày kết thúc hợp đồng";
			return false;
		}

		if( this.gsbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn Giám sát";
			return false;
		}
		
		if( this.ddkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng kiểm tra lại kênh bán hàng";
			return false;
		}
		
		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn khách hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if( this.hopdongId.trim().length() <= 0 && this.donhangMuon.equals("0") && this.loaidonhang.equals("1") )
		{
			this.msg = "Vui lòng chọn hợp đồng đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm hợp đồng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					/*if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}*/
					
					if( ( spGianhap[i].trim().length() <= 0 || spGianhap[i].trim().equals("0") ) && this.isKhuyenmai.equals("0")  )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spDonvi[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đơn vị  của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 && spSCheme[j].trim().length() <= 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}
		
		if( this.khoNhanId.trim().equals("100003") )
		{
			if(this.khKGId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng ký gửi";
				return false;
			}
		}
		
		try
		{
			//CHECK HỢP ĐỒNG CÓ CÒN HIỆU LỰC HAY KHÔNG
			if( this.hopdongId.trim().length() > 0 )
			{
				String queryHD = "select loaidonhang, hieuluc, tungay, case when denngayPL is null then denngay else  denngayPL end as denngay  " +
								"from " +
								"( " +
								"	select loaidonhang, hieuluc, tungay, denngay, " +
								"			( select max(denngay) from ERP_HOPDONGNPP where hopdong_fk = a.pk_seq and trangthai in (1, 2) ) as denngayPL " +
								"	from ERP_HOPDONGNPP a  " +
								"	where pk_seq = '" + this.hopdongId + "'  " +
								") " +
								"HD " +
								"where tungay <= '" + this.tungay + "' and '" + this.getDateTime() + "' <= ( case when denngayPL is null then denngay else  denngayPL end ) ";
				System.out.println("kiem tra hop dong " + queryHD);
				ResultSet rs = db.get(queryHD);
				String hieuluc = "0";
				if(rs.next())
				{
					hieuluc = rs.getString("hieuluc");
					rs.close();
				}
				
				//CHECK CON HIEU LUC KHONG
				if( hieuluc.equals("0") )
				{
					this.msg = "Hợp đồng đã hết hạn. Bạn không thể chuyển thành đơn hàng.";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim().replaceAll(",", "");
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim().replaceAll(",", "");
			String _hopdongId = this.hopdongId.trim().length() <= 0 ? "null" : this.hopdongId.trim();
			String khachhangKG_FK = this.khKGId.trim().length() <= 0 ? "null" : this.khKGId.trim();
			
			String npp_dat_fk = "NULL";
			String khachhang_fk = "NULL";
			String nhanvien_fk = "NULL";
			
			if(this.loaidonhang.equals("0"))
				npp_dat_fk = this.khId;
			else if( this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
				khachhang_fk = this.khId;
			else
				nhanvien_fk = this.khId;
			
			//Quản lý kho theo nhóm kênh
			//String nhomkenhId = " ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select kbh_fk from KHACHHANG where pk_seq = '" + this.khId + "' ) ) ";
			String nhomkenhId = " ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + this.kbhId + "' ) ";
			String query = "select dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1"))
					nhomkenhId = "100000";
				rs.close();
			}
						
			String capdogh = this.capdogiaohang.trim().length() > 0 ? this.capdogiaohang : "NULL";
			
			System.out.println("::: LAY THOI DIEM HIEN TAI: " + this.getThoiDiemHienTai());
			
			//CHECK NGÀY HIỆN TẠI, XEM CÓ LƠN HƠN NGÀY KHÓA SỔ KINH DOANH KHÔNG
			int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, this.getThoiDiemHienTai(), "", "", "");
			String chuyenSALES = "0";
			if(checkKS_KINHDOANH == 1 )
			{
				chuyenSALES = "1";
			}
			
			query = " insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, npp_dachot, ghichu, trangthai, dvkd_fk, kbh_fk, nhomkenh_fk, gsbh_fk, ddkd_fk, npp_dat_fk, khachhang_fk, nhanvien_fk, npp_fk, kho_fk, chietkhau, vat, hopdong_fk, ngaytao, nguoitao, ngaysua, nguoisua, donhangMUON, khachhangKG_FK, isKM, sohopdong, capdogiaohang, chuyenSALES, maphieuMUAHANG, tratichluy ) " +
					" select '" + this.tungay + "', '" + this.denngay + "', " + this.loaidonhang + ", 1, N'" + this.ghichu + "', 0, '" + this.dvkdId + "', '" + this.kbhId + "', " + nhomkenhId + ", '" + this.gsbhId + "', '" + this.ddkdId + "', " + npp_dat_fk + ", " + khachhang_fk + ", " + nhanvien_fk + ", '" + this.nppId + "', " + khonhan_fk + ", " + chietkhau + ", " + vat + ", " + _hopdongId + ", '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.donhangMuon + "', " + khachhangKG_FK + ", '" + this.isKhuyenmai + "', N'"+this.sohopdong+ "', " + capdogh + ", '" + chuyenSALES + "', N'" + this.maphieuMH + "', '" + this.tratichluy + "' ";
			System.out.println("-- INSERT DDH: " + query );
			if(!db.update(query))
			{
				msg = "Lỗi khi tạo mới đơn hàng: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_DondathangNPP') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			System.out.println("DDH ID: " + this.id);
			
			//NẾU THAY ĐỔI CẤP ĐỘ GIAO HÀNG THÌ CẬP NHẬT LẠI TRONG DLN
			if(this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
			{
				query = "Update KHACHHANG set capdogiaohang = " + capdogh + " where pk_seq = " + khachhang_fk + " ";
				if(!db.update(query))
				{
					msg = "Lỗi khi tạo mới đơn hàng: " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 && spGianhap[i].trim().length() > 0  )
				{
					query ="select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
						   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
						   "from SANPHAM sp, DONVIDOLUONG dv " +
						   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
					
					//System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
						{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
					
					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";

					String ddkd_fk = spTDV[i].trim().length() <= 0 ? this.ddkdId : spTDV[i];
					if(this.loaidonhang.equals("1"))
					{
						query = "insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, sanphamTEN, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay, dongiaGOC, ddkd_fk ) " +
								"select '" + this.id + "', pk_seq, N'" + spTen[i] + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + spTungay[i].trim() + "', '" + spDenngay[i].trim() + "',  " +
								" 	  '" + this.spGianhap[i].replaceAll(",", "") + "' as giamua, " + ddkd_fk + " " +
								"from SANPHAM a where MA = '" + spMa[i].trim() + "' ";
					}
					else
					{
						query = "insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, sanphamTEN, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay, dongiaGOC, ddkd_fk ) " +
								"select '" + this.id + "', pk_seq, N'" + spTen[i] + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), ' ', ' ',  " +
								" 	  '" + this.spGianhap[i].replaceAll(",", "") + "' as giamua, " + ddkd_fk + " " +
								"from SANPHAM a where MA = '" + spMa[i].trim() + "' ";
					}
					
					System.out.println("1.Insert HD - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANGNPP_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					
				}
			}
			
			//CHECK XEM CÓ MÃ HÀNG NÀO QUY ĐỔI BỊ LẺ KHÔNG
			query = "  select b.TEN, a.soluong, a.soluong * dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) as soluongChuan  " + 
					"  from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " + 
					"  where a.dondathang_fk = '" + this.id + "'  " + 
					"  	and ( a.soluong * dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) ) - ROUND( ( a.soluong * dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) ), 0 ) != 0 ";
			String quydoiLE = "";
			rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					quydoiLE += rs.getString("TEN") + ", ";
				}
				rs.close();
			}
			
			if( quydoiLE.trim().length() > 0 )
			{
				this.msg = "Các sản phẩm sau ( " + quydoiLE + " ) đang bị quy đổi lẻ, vui lòng kiểm tra lại.";
				db.getConnection().rollback();
				return false;
			}
			
			
			//CHECK XEM SO LUONG CO BI VUOT HOP DONG KHONG
			System.out.println(":::: LOAI DON HANG: " + this.loaidonhang + " -- HOP DONG: " + this.hopdongId );
			if(this.loaidonhang.equals("1") && this.hopdongId.trim().length() >= 6 )
			{
				query = " select pk_seq, loaidonhang, hopdong_fk " +
						" from ERP_HOPDONGNPP where loaidonhang = 0 and pk_seq in ( select hopdong_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.id + "' ) ";
				rs = db.get(query);
				String hoadonId = "";
				if(rs.next())
				{
					hoadonId = rs.getString("pk_seq");
					rs.close();
				}
				
				if (hoadonId.trim().length() > 0  ) // Hóa đơn bình thường, chỉ được phép đặt bằng số còn lại
				{
					query = "select ( select ten from SANPHAM where pk_seq = hd.sanpham_fk ) as spTEN, isnull( hd.soluong, 0) as soluong, isnull( dh.daDAT, 0 ) as daDAT  " +
							"from " +
							"( " +
							"	select sanpham_fk, dvdl_fk, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay " +
							"	from " +
							"	( " +
							"		select sanpham_fk,  " +
							"			case when a.dvdl_fk IS null then a.soluong       " +
							"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
							"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
							"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
							"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
							"		where HOPDONG_FK = '" + hoadonId + "' and HOPDONG_FK in ( select pk_seq from ERP_HOPDONGNPP where pk_seq = '" + hoadonId + "' and loaidonhang = '0' )  " +
							"	union ALL " +
							"		select sanpham_fk,  " +
							"			case when a.dvdl_fk IS null then a.soluong       " +
							"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
							"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
							"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
							"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
							"		where HOPDONG_FK in ( select pk_seq from ERP_HOPDONGNPP where hopdong_fk = '" + hoadonId + "' and trangthai in (1, 2) ) " +
							"	) " +
							"	hopdong group by sanpham_fk, dvdl_fk, tungay, denngay " +
							") " +
							"hd left join " +
							"( " +
							"	select sanpham_fk, sum(soluong) as daDAT " +
							"	from " +
							"	( " +
							"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " +
							"				case when a.dvdl_fk IS null then a.soluong       " +
							"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
							"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
							"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong  " +
							"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
							"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + hoadonId + "'  )     " +
							"	) " +
							"	dathang group by sanpham_fk " +
							") " +
							"dh on hd.sanpham_fk = dh.sanpham_fk " +
							"where hd.soluong < isnull(dh.daDAT, 0) ";
					
					System.out.println("----CHECK VUOT HOPDONG-SANPHAM: " + query );
					rs = db.get(query);
					if(rs != null)
					{
						while(rs.next())
						{
							String spTEN = rs.getString("spTEN");
							double tongHOADON = rs.getDouble("soluong");
							double tongDAXUAT = rs.getDouble("daDAT");
							
							if(tongDAXUAT > tongHOADON )
							{
								this.msg += " Tổng đặt ( " + tongDAXUAT + " ) của sản phẩm ( " + spTEN + " ) đã vượt quá tổng lượng trong hợp đồng ( " + tongHOADON + " ) \n";
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();
					}
				}
			}
			
			msg = util.CapNhat_MaChungTu(db, this.id, "ERP_DONDATHANGNPP", "NgayDonHang");
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return false;
			}
			
			if( checkKS_KINHDOANH == 1 )
			{
				String[] hientai = this.getDateTime().split("-");
				int thangtiep = Integer.parseInt(hientai[1]) + 1;
				if( thangtiep > 12 )
					thangtiep = 1;
				
				this.msg = "Doanh số đơn hàng này được chuyển sales qua tháng " + ( thangtiep );
			}
			
			//Đơn hàng dưới 250k không cho lưu (250k theo đơn giá gốc đã có thuế)
			if( !this.loaidonhang.equals("1") )
			{
				double tonggiatriDH = 0;
				query = "select SUM( round( soluong * round( dongiaGOC * ( 1 + thueVAT / 100.0 ), 0 ), 0 ) ) as tonggiatri " + 
						" from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "'";
				rs = db.get(query);
				if( rs.next() )
				{
					tonggiatriDH = rs.getDouble("tonggiatri");
					rs.close();
				}
				
				if( tonggiatriDH < 250000 )
				{
					this.msg = "Tổng giá trị đơn hàng không được nhỏ hơn 250,000";
					db.getConnection().rollback();
					return false;
				}
			}
			
			//CHECK SAN PHAM VUOT PHAN BO
			query =  " select *, ISNULL( (  select SUM( dh_sp.soluong ) as soluong "+
					 " 					 from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					 " 					 where dh.TRANGTHAI != 3 and month( dh.ngaydonhang ) = pb.thang and YEAR( dh.ngaydonhang ) = pb.nam"+
					 " 								and dh.GSBH_FK = pb.gsbh_fk and dh_sp.sanpham_fk = pb.sanpham_fk"+
					 " 					 group by dh.GSBH_FK, dh_sp.sanpham_fk ) , 0 ) as dagiao"+
					 " from"+
					 " ("+
					 " 	select b.gsbh_fk, c.sanpham_fk, sp.ten, month( a.tungay ) as thang, YEAR( a.tungay ) as nam, sum(c.phanbo) as phanbo"+
					 " 	from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_GSBH b on a.pk_seq = b.phanbo_fk"+
					 " 			inner join ERP_PHANBODONHANG_SANPHAM c on a.pk_seq = c.phanbo_fk"+
					 " 			inner join SANPHAM sp on c.sanpham_fk = sp.PK_SEQ"+
					 " 	where sp.pk_seq in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "' and soluong != 0 )  "+
					 " 			and month( a.tungay ) = month( '" + this.tungay + "' ) and YEAR( a.tungay ) = YEAR( '" + this.tungay + "' )  and b.gsbh_fk = '" + this.gsbhId + "'"+
					 " 	group by b.gsbh_fk, c.sanpham_fk, sp.ten, month( a.tungay ), YEAR( a.tungay )"+
					 " )"+
					 " pb where pb.phanbo - ISNULL( (  select SUM( dh_sp.soluong ) as soluong"+
					 " 					 from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					 " 					 where dh.TRANGTHAI != 3 and month( dh.ngaydonhang ) = pb.thang and YEAR( dh.ngaydonhang ) = pb.nam"+
					 " 								and dh.GSBH_FK = pb.gsbh_fk and dh_sp.sanpham_fk = pb.sanpham_fk"+
					 " 					 group by dh.GSBH_FK, dh_sp.sanpham_fk ) , 0 ) < 0";
			
			System.out.println(":: CHECK VUOT PHAN BO: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String spTEN = rs.getString("ten");
					double phanbo = rs.getDouble("phanbo");
					double dagiao = rs.getDouble("dagiao");
					
					this.msg += " Tổng đặt ( " + dagiao + " ) của sản phẩm ( " + spTEN + " ) đã vượt quá tổng lượng được phân bổ ( " + phanbo + " ) \n";
					db.getConnection().rollback();
					return false;
					
				}
				rs.close();
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//CẬP NHẬT CHIÊT KHẤU
			this.ApChietKhau( this.id, db, "0", "0" );
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateNK(String checkKM) 
	{
		if(this.tungay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đặt hàng";
			return false;
		}
		
		if(this.denngay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao";
			return false;
		}

		if( this.gsbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn Giám sát";
			return false;
		}
		
		if( this.ddkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng kiểm tra lại kênh bán hàng";
			return false;
		}

		if( this.khId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn khách hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if( this.hopdongId.trim().length() <= 0 && this.donhangMuon.equals("0") && this.loaidonhang.equals("1") )
		{
			this.msg = "Vui lòng chọn hợp đồng đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{	
					/*if(spSoluong[i].trim().length() <= 0  )
						spSoluong[i] = "0";
						
					if(spSoluong[i].trim().length() <= 0  )
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}*/
					
					if(spGianhap[i].trim().length() <= 0 && this.isKhuyenmai.equals("0") )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					else
					{
						if( spGianhapGOC[i].trim().length() <= 0 )
							spGianhapGOC[i] = spGianhap[i];
					}
					
					if(spDonvi[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập đơn vị của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 && spSCheme[j].trim().length() <= 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim())  )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}
		}	
		
		if( this.khoNhanId.trim().equals("100003") )
		{
			if(this.khKGId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn khách hàng ký gửi";
				return false;
			}
		}
		
		try
		{
			//CHECK HỢP ĐỒNG CÓ CÒN HIỆU LỰC HAY KHÔNG
			if( this.hopdongId.trim().length() > 0 )
			{
				String queryHD = "select loaidonhang, hieuluc, tungay, case when denngayPL is null then denngay else  denngayPL end as denngay  " +
								"from " +
								"( " +
								"	select loaidonhang, hieuluc, tungay, denngay, " +
								"			( select max(denngay) from ERP_HOPDONGNPP where hopdong_fk = a.pk_seq and trangthai in (1, 2) ) as denngayPL " +
								"	from ERP_HOPDONGNPP a  " +
								"	where pk_seq = '" + this.hopdongId + "'  " +
								") " +
								"HD " +
								"where tungay <= '" + this.tungay + "' and '" + this.getDateTime() + "' <= ( case when denngayPL is null then denngay else  denngayPL end ) ";
				System.out.println("kiem tra hop dong " + queryHD);
				ResultSet rs = db.get(queryHD);
				String hieuluc = "0";
				if(rs.next())
				{
					hieuluc = rs.getString("hieuluc");
					rs.close();
				}
				
				//CHECK CON HIEU LUC KHONG
				if( hieuluc.equals("0") )
				{
					this.msg = "Hợp đồng đã hết hạn. Bạn không thể chuyển thành đơn hàng.";
					return false;
				}
			}
			
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();	
			String khachhangKG_FK = this.khKGId.trim().length() <= 0 ? "null" : this.khKGId.trim();
			
			//Quản lý kho theo nhóm kênh
			String nhomkenhId = " ( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + this.kbhId + "' ) ";
			String query = "select dungchungkenh from NHAPHANPHOI where PK_SEQ = '" + this.nppId + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getString("dungchungkenh").equals("1"))
					nhomkenhId = "100000";
				rs.close();
			}
			
			String npp_dat_fk = "NULL";
			String khachhang_fk = "NULL";
			String nhanvien_fk = "NULL";
			
			if(this.loaidonhang.equals("0"))
				npp_dat_fk = this.khId;
			else if( this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
				khachhang_fk = this.khId;
			else
				nhanvien_fk = this.khId;

			String capdogh = this.capdogiaohang.trim().length() > 0 ? this.capdogiaohang : "NULL";
			
			query =	" Update ERP_DOnDatHangNPP set ngaydonhang = '" + this.tungay + "', ngaydenghi = '" + this.denngay + "', ghichu = N'" + this.ghichu + "', " +
					" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', nhomkenh_fk = " + nhomkenhId + ", gsbh_fk = '" + this.gsbhId + "', ddkd_fk = '" + this.ddkdId + "', npp_dat_fk = " + npp_dat_fk + ", khachhang_fk = " + khachhang_fk + ", nhanvien_fk = " + nhanvien_fk + ", kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "', donhangMUON = '" + this.donhangMuon + "', khachhangKG_FK = " + khachhangKG_FK + ", isKM = " + this.isKhuyenmai + ", " + 
					" sohopdong = N'"+this.sohopdong+"', capdogiaohang = " + capdogh + ", maphieuMUAHANG = N'" + this.maphieuMH + "', tratichluy = '" + this.tratichluy + "' where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DOnDatHangNPP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//NẾU THAY ĐỔI CẤP ĐỘ GIAO HÀNG THÌ CẬP NHẬT LẠI TRONG DLN
			if(this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
			{
				query = "Update KHACHHANG set capdogiaohang = " + capdogh + " where pk_seq = " + khachhang_fk + " ";
				if(!db.update(query))
				{
					msg = "Lỗi khi cập nhật đơn hàng: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			//TANG KHO NGUOC LAI
			/*query = "update kho   " +
					"set kho.available = kho.available + BOOK_KHO.soluong,  " +
					"	kho.booked = kho.booked - BOOK_KHO.soluong  " +
					"from " +
					"( " +
					"	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, sum(soluong) as soluong  " +
					"	from " +
					"	( " +
					"		select c.kho_fk as khoxuat_fk, c.npp_fk, " + nhomkenhId + " as nhomkenh_fk, a.sanpham_fk,       " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " +
					"		where a.dondathang_fk in (  " + this.id + "  ) and a.soluong > 0 " +
					"	) " +
					"	DATHANG  " +
					"	group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " +
					") " +
					"BOOK_KHO inner join NHAPP_KHO kho on BOOK_KHO.khoxuat_fk = kho.kho_fk and BOOK_KHO.npp_fk = kho.npp_fk and BOOK_KHO.nhomkenh_fk = kho.nhomkenh_fk and BOOK_KHO.sanpham_fk = kho.sanpham_fk ";
			
			System.out.println("::::::::::"+query);
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return false;
			}*/
			
			//DO CON LIEN QUAN TOI HANG KM, NEN PHẢI LƯU LẠI CHIẾT KHẤU KM LÚC SAVE
			query = " insert ERP_DONDATHANGNPP_SANPHAM_TEMP select * from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_SANPHAM_TEMP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANGNPP_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 && spGianhap[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
				{					
					query =    "select sp.ten, dv.donvi, case when sp.dvdl_fk != dv.pk_seq   " +
						   "	then ISNULL( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = dv.pk_seq ), -1 ) else 1 end as quycach   "  +
						   "from SANPHAM sp, DONVIDOLUONG dv " +
						   "where sp.MA = '" + spMa[i].trim() + "' and dv.donvi = N'" + spDonvi[i].trim() + "'   ";
					
					//System.out.println("-----CHECK QUY CACH: " + query );
					rs = db.get(query);
					if(rs.next())
					{
						if(rs.getDouble("quycach") <= 0)
						{
							this.msg = "Sản phẩm ( " + rs.getString("ten") + " ) với đơn vị đặt ( " + rs.getString("donvi") + " ) chưa thiết lập quy cách trong DLN ";
							rs.close();
							db.getConnection().rollback();
							return false;
						}
					}
					rs.close();
					
					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";
		
					String ddkd_fk = spTDV[i].trim().length() <= 0 ? this.ddkdId : spTDV[i];
					if(this.loaidonhang.equals("1"))
					{
						query = "insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, sanphamTEN, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay, dongiaGOC, ddkd_fk ) " +
								"select '" + this.id + "', pk_seq, N'" + spTen[i] + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), '" + spTungay[i].trim() + "', '" + spDenngay[i].trim() + "',  " +
								" 	  '" + this.spGianhapGOC[i].replaceAll(",", "") + "' as giamua, " + ddkd_fk + " " +
								"from SANPHAM a where MA = '" + spMa[i].trim() + "' ";
					}
					else
					{
						query = "insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, sanphamTEN, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay, dongiaGOC, ddkd_fk ) " +
								"select '" + this.id + "', pk_seq, N'" + spTen[i] + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), ' ', ' ',  " +
								" 	  '" + this.spGianhapGOC[i].replaceAll(",", "") + "' as giamua, " + ddkd_fk + " " +
								"from SANPHAM a where MA = '" + spMa[i].trim() + "' ";
					}
					
					System.out.println("1.Insert HD - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANGNPP_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//CHECK XEM CÓ MÃ HÀNG NÀO QUY ĐỔI BỊ LẺ KHÔNG
			query = "  select b.TEN, a.soluong, a.soluong * dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) as soluongChuan  " + 
					"  from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ " + 
					"  where a.dondathang_fk = '" + this.id + "'  " + 
					"  	and ( a.soluong * dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) ) - ROUND( ( a.soluong * dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) ), 0 ) != 0 ";
			String quydoiLE = "";
			rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					quydoiLE += rs.getString("TEN") + ", ";
				}
				rs.close();
			}
			
			if( quydoiLE.trim().length() > 0 )
			{
				this.msg = "Các sản phẩm sau ( " + quydoiLE + " ) đang bị quy đổi lẻ, vui lòng kiểm tra lại.";
				db.getConnection().rollback();
				return false;
			}
			
			//XOA DON HANG TEMP
			query = "update dh set dh.schemeCHIETKHAU = dh_temp.schemeCHIETKHAU,  "+
					 "			  dh.chietkhau_KM = dh_temp.chietkhau_KM "+
					 "from ERP_DONDATHANGNPP_SANPHAM dh inner join ERP_DONDATHANGNPP_SANPHAM_TEMP dh_temp on dh.dondathang_fk = dh_temp.dondathang_fk "+
					 "			and dh.sanpham_fk = dh_temp.sanpham_fk "+
					 "where dh_temp.dondathang_fk = '" + this.id + "' and isnull(dh_temp.schemeCHIETKHAU, '') != '' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANGNPP_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = " delete ERP_DONDATHANGNPP_SANPHAM_TEMP where dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_SANPHAM_TEMP " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//INSERT CHIET KHAU BO SUNG
			if(this.dhCk_diengiai != null)
			{
				for(int i = 0; i < this.dhCk_diengiai.length; i++)
				{
					if(this.dhCk_giatri[i].trim().length() > 0)
					{
						query = "insert ERP_DONDATHANGNPP_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI) " +
								"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "' ) ";
						
						//System.out.println("1.Insert HD - CK: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DONDATHANGNPP_CHIETKHAU: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			//CHECK XEM SO LUONG CO BI VUOT HOP DONG KHONG
			System.out.println(":::: LOAI DON HANG: " + this.loaidonhang + " -- HOP DONG: " + this.hopdongId );
			if(this.loaidonhang.equals("1") && this.hopdongId.trim().length() >= 6 )
			{
				query = " select pk_seq, loaidonhang, hopdong_fk " +
						" from ERP_HOPDONGNPP where loaidonhang = 0 and pk_seq in ( select hopdong_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.id + "' ) ";
				rs = db.get(query);
				String hoadonId = "";
				if(rs.next())
				{
					hoadonId = rs.getString("pk_seq");
					rs.close();
				}
				
				if (hoadonId.trim().length() > 0  ) // Hóa đơn bình thường, chỉ được phép đặt bằng số còn lại
				{
					query = "select ( select ten from SANPHAM where pk_seq = hd.sanpham_fk ) as spTEN, isnull( hd.soluong, 0) as soluong, isnull( dh.daDAT, 0 ) as daDAT  " +
							"from " +
							"( " +
							"	select sanpham_fk, dvdl_fk, sum(soluong) as soluong, avg(dongia) as dongia, avg(chietkhau) as chietkhau, avg(thuevat) as thuevat, tungay, denngay " +
							"	from " +
							"	( " +
							"		select sanpham_fk,  " +
							"			case when a.dvdl_fk IS null then a.soluong       " +
							"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
							"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
							"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
							"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq  " +
							"		where HOPDONG_FK = '" + hoadonId + "' and HOPDONG_FK in ( select pk_seq from ERP_HOPDONGNPP where pk_seq = '" + hoadonId + "' and loaidonhang = '0' )  " +
							"	union ALL " +
							"		select sanpham_fk,  " +
							"			case when a.dvdl_fk IS null then a.soluong       " +
							"				 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
							"				 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
							"							/ ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )	 end as soluong, dongia, chietkhau, thueVAT, b.pk_seq as dvdl_fk, tungay, denngay  " +
							"		from ERP_HOPDONGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq   " +
							"		where HOPDONG_FK in ( select pk_seq from ERP_HOPDONGNPP where hopdong_fk = '" + hoadonId + "' and trangthai in (1, 2) ) " +
							"	) " +
							"	hopdong group by sanpham_fk, dvdl_fk, tungay, denngay " +
							") " +
							"hd left join " +
							"( " +
							"	select sanpham_fk, sum(soluong) as daDAT " +
							"	from " +
							"	( " +
							"		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " +
							"				case when a.dvdl_fk IS null then a.soluong       " +
							"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
							"					 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )       " +
							"									 / ( select SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk ) end as soluong  " +
							"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ      " +
							"		where a.dondathang_fk in (   select pk_seq from ERP_DondathangNPP where trangthai != '3' and hopdong_fk = '" + hoadonId + "'  )     " +
							"	) " +
							"	dathang group by sanpham_fk " +
							") " +
							"dh on hd.sanpham_fk = dh.sanpham_fk " +
							"where hd.soluong < isnull(dh.daDAT, 0) ";
					
					System.out.println("----CHECK VUOT HOPDONG-SANPHAM: " + query );
					rs = db.get(query);
					if(rs != null)
					{
						while(rs.next())
						{
							String spTEN = rs.getString("spTEN");
							double tongHOADON = rs.getDouble("soluong");
							double tongDAXUAT = rs.getDouble("daDAT");
							
							if(tongDAXUAT > tongHOADON )
							{
								this.msg += " Tổng đặt ( " + tongDAXUAT + " ) của sản phẩm ( " + spTEN + " ) đã vượt quá tổng lượng trong hợp đồng ( " + tongHOADON + " ) \n";
								db.getConnection().rollback();
								return false;
							}
						}
						rs.close();
					}
				}
			}
			
			msg = util.CapNhat_MaChungTu(db, this.id, "ERP_DONDATHANGNPP", "NgayDonHang");
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return false;
			}
			
			//Đơn hàng dưới 250k không cho lưu (250k theo đơn giá gốc đã có thuế)
			if( !this.loaidonhang.equals("1") )
			{
				double tonggiatriDH = 0;
				query = "select SUM( round( soluong * round( dongiaGOC * ( 1 + thueVAT / 100.0 ), 0 ), 0 ) ) as tonggiatri " + 
						" from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "'";
				rs = db.get(query);
				if( rs.next() )
				{
					tonggiatriDH = rs.getDouble("tonggiatri");
					rs.close();
				}
				
				if( tonggiatriDH < 250000 )
				{
					this.msg = "Tổng giá trị đơn hàng không được nhỏ hơn 250,000";
					db.getConnection().rollback();
					return false;
				}
			}
			
			//CHECK SAN PHAM VUOT PHAN BO
			query =  " select *, ISNULL( (  select SUM( dh_sp.soluong ) as soluong "+
					 " 					 from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					 " 					 where dh.TRANGTHAI != 3 and month( dh.ngaydonhang ) = pb.thang and YEAR( dh.ngaydonhang ) = pb.nam"+
					 " 								and dh.GSBH_FK = pb.gsbh_fk and dh_sp.sanpham_fk = pb.sanpham_fk"+
					 " 					 group by dh.GSBH_FK, dh_sp.sanpham_fk ) , 0 ) as dagiao"+
					 " from"+
					 " ("+
					 " 	select b.gsbh_fk, c.sanpham_fk, sp.ten, month( a.tungay ) as thang, YEAR( a.tungay ) as nam, sum(c.phanbo) as phanbo"+
					 " 	from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_GSBH b on a.pk_seq = b.phanbo_fk"+
					 " 			inner join ERP_PHANBODONHANG_SANPHAM c on a.pk_seq = c.phanbo_fk"+
					 " 			inner join SANPHAM sp on c.sanpham_fk = sp.PK_SEQ"+
					 " 	where sp.pk_seq in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "' and soluong != 0 )  "+
					 " 			and month( a.tungay ) = month( '" + this.tungay + "' ) and YEAR( a.tungay ) = YEAR( '" + this.tungay + "' )  and b.gsbh_fk = '" + this.gsbhId + "'"+
					 " 	group by b.gsbh_fk, c.sanpham_fk, sp.ten, month( a.tungay ), YEAR( a.tungay )"+
					 " )"+
					 " pb where pb.phanbo - ISNULL( (  select SUM( dh_sp.soluong ) as soluong"+
					 " 					 from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					 " 					 where dh.TRANGTHAI != 3 and month( dh.ngaydonhang ) = pb.thang and YEAR( dh.ngaydonhang ) = pb.nam"+
					 " 								and dh.GSBH_FK = pb.gsbh_fk and dh_sp.sanpham_fk = pb.sanpham_fk"+
					 " 					 group by dh.GSBH_FK, dh_sp.sanpham_fk ) , 0 ) < 0";
			
			System.out.println(":: CHECK VUOT PHAN BO: " + query);
			rs = db.get(query);
			if(rs != null)
			{
				while(rs.next())
				{
					String spTEN = rs.getString("ten");
					double phanbo = rs.getDouble("phanbo");
					double dagiao = rs.getDouble("dagiao");
					
					this.msg += " Tổng đặt ( " + dagiao + " ) của sản phẩm ( " + spTEN + " ) đã vượt quá tổng lượng được phân bổ ( " + phanbo + " ) \n";
					db.getConnection().rollback();
					return false;
					
				}
				rs.close();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//CẬP NHẬT CHIÊT KHẤU
			this.ApChietKhau( this.id, db, "0", "0" );
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		return true;
	}

	public void createRs( String tdv_dangnhap_id ) 
	{
		if( this.khId == null )
			this.khId = "";
		
		this.getNppInfo();
		
		System.out.println(":::: TDV: " + this.ddkdId + " -- TDV DANG NHAP: " + tdv_dangnhap_id );
		if( tdv_dangnhap_id.trim().length() > 0 )
			this.ddkdId = tdv_dangnhap_id;
		
		this.khoNhanRs = db.get("select PK_SEQ, TEN from KHO where trangthai = '1' and pk_seq in " + this.util.quyen_kho(this.userId)   );
		if(this.khoNhanId.equals("100003"))
		{
			this.khKGRs = db.get("select pk_seq, MA + ', ' + TEN as ten from KHACHHANG where npp_fk = '" + this.nppId + "' and pk_seq in ( select khachhang_fk from NHAPP_KHO_KYGUI where NPP_FK = '" + this.nppId + "' ) ");
		}
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		
		String query = "";
		if(this.loaidonhang.trim().length() > 0)
		{
			if(this.loaidonhang.equals("0"))
			{
				query = "select PK_SEQ, TEN from KENHBANHANG where TRANGTHAI = '1'   ";
				
				if( this.kbhId.trim().length() <= 0 )
					this.kbhId = "100078";
			}
			else if(this.loaidonhang.equals("1"))
			{
				if( this.khoNhanId.trim().length() <= 0 )
					this.khoNhanId = "100002";
				query = "select PK_SEQ, TEN from KENHBANHANG where TRANGTHAI = '1' and pk_seq in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' )  ";
			}
			else
			{
				if( this.khoNhanId.trim().length() <= 0 )
					this.khoNhanId = "100000";
				query = "select PK_SEQ, TEN from KENHBANHANG where TRANGTHAI = '1'  and pk_seq in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100001' )  ";
			}
			
			query += " and pk_seq in " + util.quyen_kenh(this.userId);
			this.kbhRs = db.get(query);
		}
		
		if(this.loaidonhang.trim().length() > 0)
		{
			if(this.loaidonhang.equals("0"))
			{
				query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and tructhuoc_fk = '" + this.nppId + "' and pk_seq in " + util.quyen_npp( this.userId ) ;
				query += "union ";
				query += "select PK_SEQ, MAFAST + ', ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and loaiNPP = '0' and pk_seq in " + util.quyen_npp( this.userId );
			}
			else if( this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
			{
				query = "select PK_SEQ, MAFAST + ', ' + TEN as TEN from KHACHHANG where TRANGTHAI = '1' and NPP_FK = '" + this.nppId + "' and CS_DUYET = '1'  ";
				if(this.loaidonhang.equals("1"))
					query += " and PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) )  ";
				else if(this.loaidonhang.equals("2"))
					query += " and PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100001' ) )  ";
				
				if(this.kbhId != null)
				{
					if(this.kbhId.trim().length() > 0)
						query += " and PK_SEQ in ( select khachhang_fk from KHACHHANG_KENHBANHANG where kbh_fk = '" + this.kbhId + "' )  ";
				}
				
				if( tdv_dangnhap_id.trim().length() > 0 )
					query += " and pk_seq in ( select KHACHHANG_FK from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '" + tdv_dangnhap_id + "' ) ) ";
			
				if( this.hopdongId.trim().length() > 0 )
					query += " and pk_seq in ( select khachhang_fk from ERP_HOPDONGNPP where pk_seq = '" + this.hopdongId + "' ) ";
				
				//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
				query += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KHACHHANG", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			}
			else
			{
				query = "select PK_SEQ, MA + ', ' + TEN as TEN from ERP_NHANVIEN where TRANGTHAI = '1' and CONGTY_FK = ( select congty_fk from NHAPHANPHOI where pk_seq = '" + this.nppId + "' ) ";
			}
			
			
			//Tam thoi load nhung khach hang luc tao PGH
			if(this.id.trim().length() > 0)
			{
				if(this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
					query += " union ALL select pk_seq, TEN from KHACHHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' and PK_SEQ in ( select khachhang_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.id + "' ) ";
			}
			
			System.out.println(":::: LAY KHACH HANG: " + query );
			this.khRs = db.get(query);
		}

		if(this.khId.trim().length() > 0 && this.loaidonhang.equals("1") )
		{
			query = "select PK_SEQ, MaHopDong + ' [' + TuNgay + ' / ' + DenNgay + ']' as diengiai from ERP_HOPDONGNPP where TRANGTHAI in ( 1, 2 ) and khachhang_fk = '" + this.khId + "'  ";
			query += "order by PK_SEQ desc";
			
			System.out.println("--HOP DONG: " + query);
			this.hopdongRs = db.get(query);
		}
		
		String thuockenhCLC = "";
		if(this.hopdongId.trim().length() > 0 )
		{
			query = " select DDKD_FK, GSBH_FK, khachhang_fk, " + 
					"   (   select top(1) PK_SEQ from KENHBANHANG " +
					"		 where TRANGTHAI = '1' and pk_seq in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' )  " +
					"				and PK_SEQ in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = a.khachhang_fk ) ) as kbh_fk,   " + 
					" 	( select count(KHACHHANG_FK) from KHACHHANG_KENHBANHANG where KBH_FK in ( 100052 ) and khachhang_fk = a.khachhang_fk ) as thuocCLC " + 
					" from ERP_HOPDONGNPP a where pk_seq = '" + this.hopdongId + "'";
			ResultSet rsInfo = db.get(query);
			if(rsInfo != null)
			{
				try 
				{
					if(rsInfo.next())
					{
						this.gsbhId = rsInfo.getString("GSBH_FK")  == null ? "" : rsInfo.getString("GSBH_FK");
						this.ddkdId = rsInfo.getString("DDKD_FK")  == null ? "" : rsInfo.getString("DDKD_FK");
						this.khId = rsInfo.getString("khachhang_fk") == null ? "" : rsInfo.getString("khachhang_fk");
						
						this.kbhId = rsInfo.getString("kbh_fk");
						
						if( rsInfo.getInt("thuocCLC") > 0 )
							thuockenhCLC = "1";
					}
					rsInfo.close();
				} 
				catch (Exception e) {}
			}
			
			if( thuockenhCLC.equals("1") && this.spMa == null )
			{
				this.initSANPHAM_CLC();
			}
			
		}
		
		if(this.khId.trim().length() > 0)
		{
			//query = "select COUNT(*) as sodong from NHAPHANPHOI a inner join ERP_CONGTY b on a.congty_fk = b.PK_SEQ where a.PK_SEQ = " + this.khId;
			query = " select congty_fk " + 
					" from NHAPHANPHOI where pk_seq = '" + this.khId + "'";
			System.out.println("rs kt " + query);
			ResultSet rskt = db.get(query);
			if(rskt != null)
			{
				try 
				{
					if(rskt.next())
					{
						String kt = rskt.getString("congty_fk")==null?"":rskt.getString("congty_fk");
						if( !kt.equals("") )
							this.isMTV = "1";
					}
					rskt.close();
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		}
		
		if(this.khId.trim().length() > 0 && ( this.loaidonhang.equals("1") || this.loaidonhang.equals("2") ) )
		{
			//query = "select COUNT(*) as sodong from NHAPHANPHOI a inner join ERP_CONGTY b on a.congty_fk = b.PK_SEQ where a.PK_SEQ = " + this.khId;
			query = " select ISNULL(capdogiaohang, 0) as capdogiaohang, b.loaiNPP " + 
					" from KHACHHANG a inner join NHAPHANPHOI b on a.NPP_FK = b.PK_SEQ where a.pk_seq = '" + this.khId + "'";
			ResultSet rskt = db.get(query);
			if(rskt != null)
			{
				try 
				{
					if(rskt.next())
					{
						this.capdogiaohang = rskt.getString("capdogiaohang");
					}
					rskt.close();
				}
				catch (Exception e) { e.printStackTrace(); }
			}
		}
		
		query = "select PK_SEQ, TEN from GIAMSATBANHANG a where trangthai = '1' ";
		if( tdv_dangnhap_id != null && tdv_dangnhap_id.trim().length() > 0 )
			query += " AND PK_SEQ in ( select GSBH_FK from DDKD_GSBH where ddkd_fk = '" + tdv_dangnhap_id + "' ) ";
		
		Utility util = new Utility();
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		query += util.getPhanQuyen_TheoNhanVien("GIAMSATBANHANG", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		System.out.println("::: GSBH: " + query );
		this.gsbhRs = db.get(query);
		
		if( tdv_dangnhap_id != null && tdv_dangnhap_id.trim().length() > 0 )
		{
			this.ddkdId = tdv_dangnhap_id;
		}

		if(this.khId.trim().length() > 0 && !loaidonhang.equals("0") && hopdongId.trim().length() <= 0 )
		{
			query = "select b.ddkd_fk, " + 
					" ( select top(1) GSBH_FK from DDKD_GSBH where DDKD_FK = b.ddkd_fk ) as gsbh_fk, " +
					" ( select top(1) kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = a.khachhang_fk ) as kbhId,	" +
					" ( select isnull(PT_CHIETKHAU, 0) as PT_CHIETKHAU from KHACHHANG where pk_seq = a.khachhang_fk ) as PT_CHIETKHAU	" +
					" from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on a.TBH_FK = b.pk_seq " +
					"where a.khachhang_fk = '" + this.khId + "'";
			System.out.println(":::: INIT KHACH HANG: " + query );
			ResultSet rsDDKD = db.get(query);
			if(rsDDKD != null)
			{
				try 
				{
					if(rsDDKD.next())
					{
						if(this.ddkdId.trim().length() <= 0)
							this.ddkdId = rsDDKD.getString("ddkd_fk");
						if(this.kbhId.trim().length() <= 0 )
							this.kbhId = rsDDKD.getString("kbhId");
						if(this.hopdongId.trim().length() <= 0 )
							this.chietkhau = rsDDKD.getString("PT_CHIETKHAU");
						
						this.gsbhId = rsDDKD.getString("gsbh_fk");
					}
					rsDDKD.close();
				}
				catch (Exception e) { e.printStackTrace(); }
			}
			
			query = "select pk_seq, TEN from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
			
			if( this.loaidonhang.equals("2") )  //OTC moi phan tuyen
				query += " and pk_seq in ( select DDKD_FK from TUYENBANHANG where PK_SEQ in ( select TBH_FK from KHACHHANG_TUYENBH where KHACHHANG_FK = '" + this.khId + "' ) ) ";
			
			if( this.id.trim().length() > 0 )
				query += " union select pk_seq, TEN from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.id + "' ) ";
			
			if( tdv_dangnhap_id != null && tdv_dangnhap_id.trim().length() > 0 )
			{
				query += " and pk_seq = '" + tdv_dangnhap_id + "' ";
			}
			
			//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
			query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "DAIDIENKINHDOANH", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			
			System.out.println(":::: LAY TRINH DUOC VIEN:  " + query );
			this.ddkdRs = db.getScrol(query);
			this.ddkdSPRs = db.getScrol(query);
			
		}
		else
		{
			query = "select pk_seq, TEN from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '" + this.nppId + "' ) ";
			if( this.gsbhId != null && this.gsbhId.trim().length() > 0 && this.loaidonhang.equals("2") )
				query += " and pk_seq in ( select ddkd_fk from DDKD_GSBH where GSBH_FK = '" + this.gsbhId + "' ) ";
			if( this.id.trim().length() > 0 )
				query += " union select pk_seq, TEN from DAIDIENKINHDOANH where pk_seq in ( select ddkd_fk from ERP_DONDATHANGNPP where pk_seq = '" + this.id + "' ) ";
			
			if( tdv_dangnhap_id != null && tdv_dangnhap_id.trim().length() > 0 )
			{
				query += " and pk_seq = '" + tdv_dangnhap_id + "' ";
			}
			
			query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "DAIDIENKINHDOANH", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
			this.ddkdRs = db.getScrol(query);
			
			//if( this.hopdongId.trim().length() <= 0 )
				//this.ddkdSPRs = db.getScrol(query);
		}
		
		if( this.hopdongId.trim().length() > 0 )
		{
			query = "select pk_seq, TEN from DAIDIENKINHDOANH where trangthai = '1' ";
			query += " and pk_seq in ( select ddkd_fk from ERP_HOPDONGNPP_SANPHAM where hopdong_fk = '" + this.hopdongId + "' ) ";
		
			System.out.println(":::: TDV HOP DONG: " + query );
			this.ddkdSPRs = db.getScrol(query);
		}
		
		
		//System.out.println(";;;; LAY DUOC KBH: " + this.kbhId );
		// CÔNG NỢ
		if(this.khId.trim().length() > 0)
		{
			// 1.TỔNG NỢ : Lấy giống công thức tính số dư cuối trong báo cáo công nợ chi tiết khách hàng tính đến "Ngày đơn hàng" của đơn hàng.
			// 2.HẠN MỨC NỢ: Lấy từ ô "Hạn mức nợ" trong dữ liệu nền Khách hàng
			
			String khId = "";
			
			if(this.loaidonhang.equals("2") || this.loaidonhang.equals("1")) // BÁN CHO KHÁCH HÀNG KÊNH OTC && ETC
				khId = "KH"+this.khId;
			
			else if(this.loaidonhang.equals("0")) // BÁN CHO NHÀ PHÂN PHỐI
				khId = "NPP"+this.khId;
			
			else // BÁN KHÁCH HÀNG NỘI BỘ
				khId = "NV"+this.khId;				
			
			query = " select ROUND ( CONLAI,0) conLAI, HANMUCNO  from [ufn_CongNoKhachHang] ("+CtyId+",'"+khId+"', '"+this.tungay+"')";
			System.out.println("query ufn_CongNoKhachHang : "+query);
			double TongNoKh = 0;
			double HanMucNoKh = 0;
			
			ResultSet rsCN = db.get(query);
			try{
				if(rsCN.next())
				{
					this.TongNo = rsCN.getDouble("conLAI");
					this.HanMucNo = rsCN.getDouble("HANMUCNO");
					rsCN.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("query TongNo : "+TongNoKh);
			
			//3.SỐ NGÀY NỢ: Lấy hoá đơn cũ nhất còn nợ, lấy ngày đơn hàng trừ đi ngày hoá đơn của hoá đơn cũ đó 
			
			query = " select SONGAYNO from 	[ufn_CongNoSONgayNO] ("+CtyId+",'"+khId+"', '"+this.tungay+"')";
			System.out.println("query ufn_CongNoSONgayNO : "+query);
			double SoNgayNoKh = 0;
			
			rsCN = db.get(query);
			try{
				if(rsCN.next())
				{
					this.SoNgayNo = rsCN.getDouble("SONGAYNO");
					rsCN.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("query TongNo : "+TongNoKh);
			
			//4.NỢ TRONG HẠN: Lấy các hoá đơn còn nợ mà có ngày hoá đơn + thời hạn nợ lớn hơn hoặc bằng ngày đơn hàng, cộng số tiền của các hoá đơn thoả điều kiện này. 
			// Ví dụ hoá đơn xuất ngày 01/10, thời hạn nợ 30 ngày à Ngày cuối cùng trong hạn là ngày 31/10, nếu đơn hàng trước hoặc bằng ngày 31/10 thì hoá đơn này vẫn trong hạn, 
			// nếu đơn hàng ngày 01/11 thì hoá đơn này quá hạn 1 ngày.
			
			query = " select NOTRONGHAN from [ufn_CongNoTrongHan] ("+CtyId+",'"+khId+"', '"+this.tungay+"')";
			System.out.println("query ufn_CongNoTrongHan : "+query);
			double NoTrongHanKh = 0;
			
			rsCN = db.get(query);
			try{
				if(rsCN.next())
				{
					this.NoTrongHan = rsCN.getDouble("NOTRONGHAN");
					rsCN.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("query NoTrongHanKh : "+NoTrongHanKh);
			
			
			//5.Nợ quá hạn: Lấy các hoá đơn còn nợ mà có (ngày hoá đơn + thời hạn nợ) bé hơn ngày đơn hàng, cộng số tiền của các hoá đơn thoả điều kiện này.			
			
			query = " select NOQUAHAN from [ufn_CongNoQuaHan] ("+CtyId+",'"+khId+"', '"+this.tungay+"')";
			System.out.println("query ufn_CongNoQuaHan : "+query);
			double NoQuaHanKh = 0;
			
			rsCN = db.get(query);
			try{
				if(rsCN.next())
				{
					this.NoQuaHan = rsCN.getDouble("NOQUAHAN");
					rsCN.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("query ufn_CongNoQuaHan : "+NoTrongHanKh);
			
			// 6.Nợ xấu: Các hoá đơn còn nợ được xem là nợ xấu khi thoả điều kiện sau:					
			//	-       Nếu thời hạn nợ < 30 ngày: (Ngày hoá đơn + thời hạn nợ + 7 ngày) bé hơn ngày đơn hàng.					
			//	-       Nếu thời hạn nợ >/= 30 ngày: (Ngày hoá đơn + thời hạn nợ + 30 ngày) bé hơn ngày đơn hàng					
			//	Cộng số tiền còn nợ của các hoá đơn này.	
					
			query = " select NOXAU from [ufn_CongNoXau] ("+CtyId+",'"+khId+"', '"+this.tungay+"')";
			System.out.println("query ufn_CongNoQuaHan : "+query);
			double NoXau = 0;
			
			rsCN = db.get(query);
			try{
				if(rsCN.next())
				{
					this.NoXau = rsCN.getDouble("NOXAU");
					rsCN.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("query ufn_CongNoXau : "+NoXau);
			
			
			query = " select NOQUAXAU from [ufn_CongNoQuaXau] ("+CtyId+",'"+khId+"', '"+this.tungay+"')";
			System.out.println("query ufn_CongNoQuaXau : "+query);
			double NoQuaXau = 0;
			
			rsCN = db.get(query);
			try{
				if(rsCN.next())
				{
					this.NoQuaXau = rsCN.getDouble("NOQUAXAU");
					rsCN.close();
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			System.out.println("query ufn_CongNoQuaXau : "+NoQuaXau);
		}
		

		//INIT DOANH SO TICH LUY
		if( this.khId.trim().length() > 0 && ( this.loaidonhang.equals("1") || this.loaidonhang.equals("2") ) )
		{
			query = "select distinct c.scheme, d.tumuc, d.denmuc, isnull(a.doanhso, 0) as doanhso, a.ngaykyhd, a.ngayketthuchd,  "+
					 "			ISNULL( (   select SUM( soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) )  "+
					 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					 "				where isnull( dh.NOTE, '' ) != 'Import ngay 10-11-2015' and dh.TRANGTHAI != 3 and dh.KHACHHANG_FK = a.KHACHHANG_FK and dh.NgayDonHang >= a.ngaykyhd and dh.NgayDonHang <= a.ngayketthuchd and dh_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = c.pk_seq )  ), 0 )  "+
					 "		-	ISNULL ( ( "+
					 "  			select SUM( SOLUONG * round( dongia * ( 1 + ptVat / 100.0 ), 0 ) ) as doanhsoTV  " + 
					 "  			from DONTRAHANG dth inner join DONTRAHANG_TICHLUY dth_tl on dth.PK_SEQ = dth_tl.dontrahang_fk " + 
					 "  				inner join DONTRAHANG_SP dth_sp on dth.PK_SEQ = dth_sp.DONTRAHANG_FK " + 
					 "  			where dth.NPP_FK = '" + this.nppId + "' and dth.KHACHHANG_FK = a.KHACHHANG_FK and dth_tl.tichluy_fk = c.PK_SEQ  " + 
					 "  				and dth_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = dth_tl.tichluy_fk ) " + 
					 "  				and dth.TRANGTHAI in ( 1, 2, 5 ) " +				
					 "				   ), 0 )  " + 
					 " 						as	doanhsoTICHLUY "+
					 "from DANGKYKM_TICHLUY_KHACHHANG a inner join DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ "+
					 "	inner join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ "+
					 "	inner join TIEUCHITHUONGTL_TIEUCHI d on c.PK_SEQ = d.thuongtl_fk "+
					 "where b.daduyet = '1' and a.KHACHHANG_FK = '" + this.khId + "' and b.TRANGTHAI != '3' and a.ngaykyhd <= '" + this.getDateTime() + "' and a.ngayketthuchd >= '" + this.getDateTime() + "' and d.muc = b.muc ";
			
			System.out.println(":::: LAY TICH LUY: " + query);
			this.tichluyRs = db.get(query);
		}
		
		//KIEM TRA XEM DLPP CO TRONG BANG GIA DAC BIET KHONG, NEU CO THI LAY SAN SP LEN
		if( this.loaidonhang.equals("0") && this.khId.trim().length() > 0 )
		{
			boolean dacoSP = false;
			if( this.spMa != null )
			{
				for( int i = 0; i < this.spMa.length; i++  )
				{
					if( this.spMa[i].trim().length() > 0 )
						dacoSP = true;
				}
			}
			
			if( !dacoSP )
			{
				query = "select COUNT(*) as sodong " +
						"	from BANGGIABANKHACHHANG a inner join BANGGIABANKHACHHANG_DLPP b on a.PK_SEQ = b.BGBANKHACHHANG_FK " +
						"	where a.TRANGTHAI = '1' and b.NPP_FK = '" + this.khId + "' ";
				boolean cogiaDB = false;
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					try
					{
						if( rs.next() )
						{
							if( rs.getInt("sodong") > 0 )
								cogiaDB = true;
						}
						rs.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
				
				if( cogiaDB )
				{
					this.initSANPHAM_BG_DACBIET();
				}
			}
		}
	}

	private void initSANPHAM() 
	{
		String query = "";
		
		if( this.hopdongId.trim().length() <= 0 )
		{
			query = "select b.MA, (select kho.available from nhapp_kho kho where kho.sanpham_fk=b.pk_seq and kho.KHO_FK= " + this.getKhoNhapId() + " and NPP_FK in(select NPP_FK from ERP_DONDATHANGNPP where  PK_SEQ=a.dondathang_fk) and kho.NHOMKENH_FK="+ (this.dungchungKENH.equals("1") ? "100000" : this.kbhId) + " )as soluongton,"+
					" isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, a.soluong, a.dongia, a.dongiaGOC, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, isnull(a.tungay, '') as tungay, isnull(a.denngay, '') as denngay, a.thueVAT    "+
						"	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi, a.ddkd_fk, "+ 
					//" round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) + isnull(a.chietkhau_DLN, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH, 0 as dagiao "+
					" round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_DLN, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH, 0 as dagiao "+
					" from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.DONDATHANG_FK = '" + this.id + "' ";
		}
		else
		{
			query = "select b.MA,(select kho.available from nhapp_kho kho where kho.sanpham_fk=b.pk_seq and kho.KHO_FK= " + this.getKhoNhapId() + " and NPP_FK in(select NPP_FK from ERP_DONDATHANGNPP where  PK_SEQ=a.dondathang_fk) and kho.NHOMKENH_FK="+ (this.dungchungKENH.equals("1") ? "100000" : this.kbhId) + " )as soluongton,"+
					" isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, a.soluong, a.dongia, a.dongiaGOC, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, isnull(a.tungay, '') as tungay, isnull(a.denngay, '') as denngay, a.thueVAT    "+
						"	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi, a.ddkd_fk, " + 
					//" round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) + isnull(a.chietkhau_DLN, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH, "+ 
					" round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_DLN, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH, "+ 
					"	ISNULL( ( select SUM( ddh_sp.soluong ) "+
					"				from ERP_DONDATHANGNPP ddh inner join ERP_DONDATHANGNPP_SANPHAM ddh_sp on ddh.pk_seq = ddh_sp.dondathang_fk  "+
					"				where ddh.TRANGTHAI != 3 and ddh.HOPDONG_FK = '" + this.hopdongId + "' and ddh_sp.sanpham_fk = b.PK_SEQ and ddh.pk_seq != '" + this.id + "' ), 0 ) as dagiao  "+
					" from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.DONDATHANG_FK = '" + this.id + "' ";
		}
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		this.SPRs = db.get(query);
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spGIANHAPGOC = "";
				String spCHIETKHAU = "";
				
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				String spSOLUONGTON = "";
				
				String spTHUEVAT = "";
				String spSCHEME = "";
				String spTDV = "";
				String ptChietkhau_KMBH = "";
				
				String spDagiao = "";
				String spChiavaodongia = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spGIANHAPGOC += spRs.getDouble("DONGIAGOC") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spTHUEVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					spSOLUONGTON += formater.format(spRs.getDouble("soluongton")) + "__";
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					if(spRs.getString("ddkd_fk") != null )
						spTDV += spRs.getString("ddkd_fk") + "__";
					else
						spTDV += this.ddkdId + "__";
					
					spSCHEME += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
					
					ptChietkhau_KMBH +=spRs.getString("ptChietkhau_KMBH") + "__";
					
					spDagiao += spRs.getString("dagiao") + "__";
					spChiavaodongia += "1__";
				}
				spRs.close();
				
				
				//INIT SP KHUYEN MAI
				query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, isnull(a.tonggiatri, 0) as tonggiatri,  "+
						 "			ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich  "+
						 "		, ISNULL( (select soluong1 / soluong2 from QUYCACH where sanpham_fk = b.pk_Seq and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018), 0)   as spQuyDoi, 0 as dagiao, 0 as chiavaoDG, 0 as loaikm  "+
						 "	from ERP_DONDATHANGNPP_CTKM_TRAKM a left join SANPHAM b on a.SPMA = b.MA   "+
						 "		left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ   "+
						 "		inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ  "+
						 "	where a.dondathangID = '" + this.id + "' "+
					" union "+
						 "	select ' ' as MA, ' ' as TEN, ' ', a.schemeCHIETKHAU, 0 as soluong, round( SUM( a.soluong * ( a.chietkhau_KM * ( 1 + a.thueVAT / 100.0) ) ), 0 ) as tonggiatri,  "+
						 "			0 as trongluong, 0 as thetich, 1  as spQuyDoi, 0 as dagiao, 1 as chiavaoDG, 1 as loaikm  "+
						 "	from ERP_DONDATHANGNPP_SANPHAM a "+
						 "	where a.dondathang_fk = '" + this.id + "' and ISNULL( a.chietkhau_KM, 0 ) > 0 group by a.schemeCHIETKHAU " + 
					" union ALL "+
						 "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, e.SCHEME, isnull(a.soluong, 0) as soluong, sum( isnull(a.tonggiatri, 0) ) as tonggiatri,   "+
						 "			ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, 1 as spQuyDoi, 0 as dagiao, 1 as chiavaoDG, 2 as loaikm   "+
						 "from ERP_DONDATHANGNPP_TICHLUY_TRATL a  "+
						 "	left join SANPHAM b on a.SPID = b.PK_SEQ    "+
						 "	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ    "+
						 "	inner join DANGKYKM_TICHLUY d on a.dkkmID = d.PK_SEQ  "+
						 "	inner join TIEUCHITHUONGTL e on d.TIEUCHITL_FK = e.PK_SEQ  "+
						 "where a.dondathangID = '" + this.id + "' group by b.MA, b.TEN, c.DONVI, e.SCHEME, a.soluong, b.trongluong, b.thetich ";
				
				System.out.println("___INIT KM: " + query);
				spRs = db.get(query);
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					if(spRs.getString("MA").trim().length() <= 0)
						spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
					else
						spGIANHAP += "0__";
					
					/*if(spRs.getString("MA").trim().length() <= 0)
						spGIANHAPGOC += spRs.getDouble("tonggiatri") + "__";
					else*/
						spGIANHAPGOC += "0__";
					
					spCHIETKHAU += "0__";
					spTHUEVAT += "0__";
					
					spSOLUONGTON += "0__";
					spTUNGAY += " __";
					spDENNGAY += " __";
					spTDV += " __";
					
					spSCHEME += spRs.getString("SCHEME") + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					//spTHETICH += spRs.getString("thetich") + "__";
					spTHETICH += spRs.getString("loaikm") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
					
					ptChietkhau_KMBH += "0__";
					
					spDagiao += spRs.getString("dagiao") + "__";
					spChiavaodongia += spRs.getString("chiavaoDG") + "__";
				}
				spRs.close();
				
				System.out.println(":::: TRINH DUOC VIEN LAY DUOC: " + spTDV );
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spSOLUONGTON = spSOLUONGTON.substring(0, spSOLUONGTON.length() - 2);
					this.spSoluongton = spSOLUONGTON.split("__");

					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spGIANHAPGOC = spGIANHAPGOC.substring(0, spGIANHAPGOC.length() - 2);
					this.spGianhapGOC = spGIANHAPGOC.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
					this.spVAT = spTHUEVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
					
					ptChietkhau_KMBH = ptChietkhau_KMBH.substring(0, ptChietkhau_KMBH.length() - 2);
					this.spCHIETKHAU_BHKM = ptChietkhau_KMBH.split("__");
					
					spDagiao = spDagiao.substring(0, spDagiao.length() - 2);
					this.spDagiao = spDagiao.split("__");
					
					spChiavaodongia = spChiavaodongia.substring(0, spChiavaodongia.length() - 2);
					this.spChiavaodongia = spChiavaodongia.split("__");
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}
	
	private void initSANPHAM_CLC() 
	{
		String query =   "select b.MA, 0 as soluongton,  "+
						 "	b.TEN, DV.donvi, ' ' as soluong, a.dongia, a.dongia as dongiaGOC, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, isnull(a.tungay, '') as tungay, isnull(a.denngay, '') as denngay, a.thueVAT,  "+
						 "	ISNULL( ( select soluong1 / soluong2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK = 100018), 1 ) as spQuyDoi, a.ddkd_fk, 0 as ptChietkhau_KMBH, "+
						 "	ISNULL( ( select SUM( ddh_sp.soluong ) "+
						 "				from ERP_DONDATHANGNPP ddh inner join ERP_DONDATHANGNPP_SANPHAM ddh_sp on ddh.pk_seq = ddh_sp.dondathang_fk  "+
						 "				where ddh.TRANGTHAI != 3 and ddh.HOPDONG_FK = a.hopdong_fk and ddh_sp.sanpham_fk = b.PK_SEQ  ), 0 ) as dagiao  "+
						 "from ERP_HOPDONGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     "+
						 " 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK        "+
						 "where a.hopdong_fk = '" + this.hopdongId + "' ";
		
		System.out.println("---INIT SP CLC: " + query);
		
		//HOP DONG NGUYEN TAC THI MAC DINH CHI HIEN NHUNG SAN PHAM TUNG MUA
		query += " and b.pk_seq in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk in ( select PK_SEQ from ERP_DONDATHANGNPP where TRANGTHAI != 3 and HOPDONG_FK = '" + this.hopdongId + "' ) )  ";
		
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		NumberFormat formater2 = new DecimalFormat("##,###,###.##");
		this.SPRs = db.get(query);
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spGIANHAPGOC = "";
				String spCHIETKHAU = "";
				
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				String spSOLUONGTON = "";
				
				String spTHUEVAT = "";
				String spSCHEME = "";
				String spTDV = "";
				String ptChietkhau_KMBH = "";
				
				String spDagiao = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += spRs.getString("soluong").trim().length() > 0 ? ( formater.format(spRs.getDouble("SOLUONG")) + "__" ) : " __";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spGIANHAPGOC += spRs.getDouble("DONGIAGOC") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spTHUEVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					spSOLUONGTON += formater.format(spRs.getDouble("soluongton")) + "__";
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += this.getDateTime() + "__";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += this.getDateTime() + "__";
					
					if(spRs.getString("ddkd_fk") != null )
						spTDV += spRs.getString("ddkd_fk") + "__";
					else
						spTDV += this.ddkdId + "__";
					
					spSCHEME += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
					
					ptChietkhau_KMBH +=spRs.getString("ptChietkhau_KMBH") + "__";
					
					spDagiao += spRs.getString("dagiao") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spSOLUONGTON = spSOLUONGTON.substring(0, spSOLUONGTON.length() - 2);
					this.spSoluongton = spSOLUONGTON.split("__");

					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spGIANHAPGOC = spGIANHAPGOC.substring(0, spGIANHAPGOC.length() - 2);
					this.spGianhapGOC = spGIANHAPGOC.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
					this.spVAT = spTHUEVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
					
					ptChietkhau_KMBH = ptChietkhau_KMBH.substring(0, ptChietkhau_KMBH.length() - 2);
					this.spCHIETKHAU_BHKM = ptChietkhau_KMBH.split("__");
					
					spDagiao = spDagiao.substring(0, spDagiao.length() - 2);
					this.spDagiao = spDagiao.split("__");
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}

	private void initSANPHAM_BG_DACBIET() 
	{
		String query = "";
		
		query =  " select b.MA, 0 as soluongton, "+
				 " 		b.TEN, DV.donvi, ' ' as soluong, a.dongia, a.DONGIA as dongiaGOC, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, '' as tungay, '' as denngay, b.thuexuat as thueVAT    "+
				 " 		,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi, '' ddkd_fk,   "+
				 " 		0 as ptChietkhau_KMBH, 0 as dagiao  "+
				 " from BANGGIABANKHACHHANG_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     "+
				 "  		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK       "+
				 " where isnull( a.chietkhau, 0 ) != 0 and a.BGBANKHACHHANG_FK in (  select top(1) PK_SEQ "+
				 " 								from BANGGIABANKHACHHANG bg inner join BANGGIABANKHACHHANG_DLPP bg_npp on bg.PK_SEQ = bg_npp.BGBANKHACHHANG_FK"+
				 " 								where bg_npp.NPP_FK = '" + this.khId + "' "+
				 " 								order by bg.PK_SEQ desc )";
		
		System.out.println("---INIT SP DB: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		this.SPRs = db.get(query);
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spGIANHAPGOC = "";
				String spCHIETKHAU = "";
				
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				String spSOLUONGTON = "";
				
				String spTHUEVAT = "";
				String spSCHEME = "";
				String spTDV = "";
				String ptChietkhau_KMBH = "";
				
				String spDagiao = "";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += " __";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spGIANHAPGOC += spRs.getDouble("DONGIAGOC") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spTHUEVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					spSOLUONGTON += formater.format(spRs.getDouble("soluongton")) + "__";
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					if(spRs.getString("ddkd_fk") != null )
						spTDV += spRs.getString("ddkd_fk") + "__";
					else
						spTDV += this.ddkdId + "__";
					
					spSCHEME += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
					
					ptChietkhau_KMBH +=spRs.getString("ptChietkhau_KMBH") + "__";
					
					spDagiao += spRs.getString("dagiao") + "__";
				}
				spRs.close();

				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spSOLUONGTON = spSOLUONGTON.substring(0, spSOLUONGTON.length() - 2);
					this.spSoluongton = spSOLUONGTON.split("__");

					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spGIANHAPGOC = spGIANHAPGOC.substring(0, spGIANHAPGOC.length() - 2);
					this.spGianhapGOC = spGIANHAPGOC.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
					this.spVAT = spTHUEVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spTDV = spTDV.substring(0, spTDV.length() - 2);
					this.spTDV = spTDV.split("__");
					
					ptChietkhau_KMBH = ptChietkhau_KMBH.substring(0, ptChietkhau_KMBH.length() - 2);
					this.spCHIETKHAU_BHKM = ptChietkhau_KMBH.split("__");
					
					spDagiao = spDagiao.substring(0, spDagiao.length() - 2);
					this.spDagiao = spDagiao.split("__");
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}
	
	
	public void init( String tdv_dangnhap_id ) 
	{
		String query =  "   select ISNULL( cast(( select pk_seq from ERP_HOPDONGNPP where pk_seq = a.hopdong_fk  ) as nvarchar), '') as mahopdong, ngaydonhang as tungay, ngaydenghi as denngay, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, npp_fk, khachhang_fk, npp_dat_fk, nhanvien_fk, kho_fk, isnull(chietkhau, 0) as chietkhau, vat, loaidonhang, sohopdong, " +
						" 	Isnull( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh, isnull(chietkhau, 0) as chietkhau,trangthai, donhangMUON, khachhangKG_FK, isKM, capdogiaohang, isnull(maphieuMUAHANG, '') as maphieuMUAHANG, isnull( sotienGIAM, 0 ) as sotienGIAM, a.tratichluy, isnull(lydokhongduyet, '') as lydokhongduyet, " +
						" 	ISNULL( ( select SUM( TONGGIATRI ) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DONDATHANGID = a.pk_seq ), 0 ) as tongtienTICHLUY,	" +
						" 	ISNULL( ( select SUM( TONGGIATRI ) from ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = a.pk_seq and spMA is null ), 0 ) as tongtienCHIETKHAU	" +
						"   from ERP_DONDATHANGNPP a where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO init: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat formater = new DecimalFormat("#,###,###");
				if(rs.next())
				{
					this.hopdongId = rs.getString("mahopdong");
					this.sohopdong = rs.getString("sohopdong")==null?"":rs.getString("sohopdong");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					
					if(rs.getString("khachhang_fk") != null)
						this.khId = rs.getString("khachhang_fk");
					else if(rs.getString("npp_dat_fk") != null)
						this.khId = rs.getString("npp_dat_fk");
					else 
						this.khId = rs.getString("nhanvien_fk");
					
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.gsbhId = rs.getString("gsbh_fk");
					this.ddkdId = rs.getString("ddkd_fk");
					this.trangthai = rs.getString("trangthai");
					// System.out.println("\n trang thai -------------" +
					// this.trangthai);
					if (rs.getString("mahopdong").trim().length() > 0)
						this.chietkhau = "0";
					
					this.dungchungKENH = rs.getString("dungchungkenh");
					this.donhangMuon = rs.getString("donhangMUON");
					
					this.khKGId = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK");
					this.isKhuyenmai = rs.getString("isKM");
					this.capdogiaohang = rs.getString("capdogiaohang") == null ? "" : rs.getString("capdogiaohang");
					
					this.maphieuMH = rs.getString("maphieuMUAHANG");
					this.sotiengiam = formater.format( rs.getDouble("sotienGIAM") );
					this.tratichluy = rs.getString("tratichluy") == null ? "0" : rs.getString("tratichluy");
					this.tienTichluy = formater.format( rs.getDouble("tongtienTICHLUY") );
					this.tienChietkhau = formater.format( rs.getDouble("tongtienCHIETKHAU") );
					this.lydokhongduyet = rs.getString("lydokhongduyet");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}

		this.initSANPHAM();
		
		this.createRs( tdv_dangnhap_id );
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getThoiDiemHienTai() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getSpId() {
		
		return this.spId;
	}

	
	public void setSpId(String[] spId) {
		
		this.spId = spId;
	}

	
	public String[] getSpMa() {
		
		return this.spMa;
	}

	
	public void setSpMa(String[] spMa) {
		
		this.spMa = spMa;
	}

	
	public String[] getSpTen() {
		
		return this.spTen;
	}

	
	public void setSpTen(String[] spTen) {
		
		this.spTen = spTen;
	}

	
	public String[] getSpDonvi() {
		
		return this.spDonvi;
	}

	
	public void setSpDonvi(String[] spDonvi) {
		
		this.spDonvi = spDonvi;
	}

	
	public String[] getSpSoluong() {
		
		return this.spSoluong;
	}

	
	public void setSpSoluong(String[] spSoluong) {
		
		this.spSoluong = spSoluong;
	}

	
	public String[] getSpGianhap() {
		
		return this.spGianhap;
	}

	
	public void setSpGianhap(String[] spGianhap) {
		
		this.spGianhap = spGianhap;
	}

	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getLoaidonhang() {
		
		return this.loaidonhang;
	}

	
	public void setLoaidonhang(String loaidonhang) {
		
		this.loaidonhang = loaidonhang;
	}

	
	public String getChietkhau() {
		
		return this.chietkhau;
	}

	
	public void setChietkhau(String chietkhau) {
		
		this.chietkhau = chietkhau;
	}

	
	public String getVat() {
		
		System.out.println("---VAT LA: " + this.vat);
		return this.vat;
	}

	
	public void setVat(String vat) {
		
		this.vat = vat;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdId) {
		
		this.dvkdId = dvkdId;
	}

	
	public ResultSet getDvkdRs() {
		
		return this.dvkdRs;
	}

	
	public void setDvkdRs(ResultSet dvkdRs) {
		
		this.dvkdRs = dvkdRs;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	public String[] getDhck_diengiai() {
		
		return this.dhCk_diengiai;
	}

	
	public void setDhck_Diengiai(String[] obj) {
		
		this.dhCk_diengiai = obj;
	}

	
	public String[] getDhck_giatri() {
		
		return this.dhCk_giatri;
	}

	
	public void setDhck_giatri(String[] obj) {
		
		this.dhCk_giatri = obj;
	}

	
	public String[] getDhck_loai() {
		
		return this.dhCk_loai;
	}

	
	public void setDhck_loai(String[] obj) {
		
		this.dhCk_loai = obj;
	}

	
	public String[] getSpTrongluong() {
		
		return this.spTrongluong;
	}

	
	public void setSpTrongluong(String[] spTrongluong) {
		
		this.spTrongluong = spTrongluong;
	}

	
	public String[] getSpThetich() {
		
		return this.spThetich;
	}

	
	public void setSpThetich(String[] spThetich) {
		
		this.spThetich = spThetich;
	}

	public String[] getSpQuyDoi()
	{
		return spQuyDoi;
	}
	
	public void setSpQuyDoi(String[] spQuyDoi)
	{
		this.spQuyDoi =spQuyDoi;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String[] getSpChietkhau() {
		
		return this.spChietkhau;
	}

	
	public void setSpChietkhau(String[] spChietkhau) {
		
		this.spChietkhau = spChietkhau;
	}

	public String[] getSpVat() {
		
		return this.spVAT;
	}

	
	public void setSpVat(String[] spVat) {
		
		this.spVAT = spVat;
	}
	
	public String[] getSpTungay() {
		
		return this.spTungay;
	}

	
	public void setSpTungay(String[] spTungay) {
		
		this.spTungay = spTungay;
	}

	
	public String[] getSpDenngay() {
		
		return this.spDenngay;
	}

	
	public void setSpDenngay(String[] spDenngay) {
		
		this.spDenngay = spDenngay;
	}

	
	public String getMahopdong() {
		
		return this.hopdongId;
	}

	
	public void setMahopdong(String ma) {
		
		this.hopdongId = ma;
	}

	
	public String getGsbhId() {
		
		return this.gsbhId;
	}

	
	public void setGsbhId(String gsbhId) {
		
		this.gsbhId = gsbhId;
	}

	
	public ResultSet getGsbhRs() {
		
		return this.gsbhRs;
	}

	
	public void setGsbhRs(ResultSet gsbhRs) {
		
		this.gsbhRs = gsbhRs;
	}

	
	public String getDdkdId() {
		
		return this.ddkdId;
	}

	
	public void setDdkdId(String ddkdId) {
		
		this.ddkdId = ddkdId;
	}

	
	public ResultSet getDdkdRs() {
		
		return this.ddkdRs;
	}

	
	public void setDddkdRs(ResultSet ddkdRs) {
		
		this.ddkdRs = ddkdRs;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	
	public boolean duyetETC(String congtyId, String vitriBAM) 
	{
		try
		{
			if(this.capduyet.trim().length() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại cấp duyệt";
				return false;
			}
			
			String query = "";
			
			//NẾU DUYỆT SS THÌ CHECK HẠN MƯC CÔNG NỢ VÀ GỬI MAIL VÀ KHÔNG CHO SS DUYỆT
			if( this.capduyet.equals("SS") )
			{
				query = "select a.khachhang_fk, ( select NOXAU from [ufn_CongNoXau] (" + congtyId + ", 'KH' + cast( b.pk_seq as varchar(10)), a.NgayDonHang ) ) as NOXAU,  "+
						 "	   ( select NOQUAXAU from [ufn_CongNoQuaXau] (" + congtyId + ", 'KH' + cast( b.pk_seq as varchar(10)), a.NgayDonHang ) ) as NOQUAXAU "+
						 "from ERP_DONDATHANGNPP a inner join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ  "+
						 "where a.PK_SEQ = '" + this.id + "' ";
				
				System.out.println("::: CHECK NO XAU: " + query);
				boolean noxau = false;
				boolean noquaxau = false;
				
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					if( rs.next() )
					{
						if( rs.getString("NOXAU") != null )
							noxau = true;
						if( rs.getString("NOQUAXAU") != null )
							noquaxau = true;
						this.khId = rs.getString("khachhang_fk");
					}
					rs.close();
				}
				
				if( noquaxau ) //GĐ KÊNH ( CAO HƠN ASM )
				{
					Toll_GuiMail mail = new Toll_GuiMail();
					mail.Send_NoXau(this.khId, congtyId, db, "1");
				}
				else if( noxau ) //ASM
				{
					Toll_GuiMail mail = new Toll_GuiMail();
					mail.Send_NoXau_ASM(this.khId, congtyId, db, "1");
				}
				
				//KHÔNG CHO DUYỆT NỮA
				if( noxau || noquaxau )
				{
					this.msg = "Đơn hàng đang bị nợ xấu. Bạn không thể duyệt";
					return false;
				}
			}
			
			
			db.update("SET TRANSACTION ISOLATION LEVEL SNAPSHOT;");
			db.update("SET LOCK_TIMEOUT 60000;");
		   
			db.getConnection().setAutoCommit(false);
			
			//CS duyệt mới cập nhật booked kho chi tiết
			if(this.sanpham_soluong == null && this.capduyet.equals("CS") )
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm xuất chi tiết ";
				db.getConnection().rollback();
				return false;
			}
			
			Utility util = new Utility();
			msg = util.Check_Huy_NghiepVu_KhoaSo("ERP_DondathangNPP", id, "ngaydonhang", db);
			if(msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}
			
			//SS duyệt bán không thầu, CS có thể duyệt phủ cấp
			//Check xem SS đã duyệt chưa
			query = " select SS_DUYET, ASM_DUYET, loaidonhang, nhomkenh_fk, khachhangKG_FK ,NPP_FK  " + 
					" from ERP_DondathangNPP dh where pk_seq = '" + this.id + "'  ";
			
			ResultSet rs = db.get(query);
			String SS_DA_DUYET = "0";
			String ASM_DA_DUYET = "0";
			String nhomkenh_fk = "";
			
			if(rs.next())
			{
				SS_DA_DUYET = rs.getString("SS_DUYET");
				ASM_DA_DUYET = rs.getString("ASM_DUYET");
				nhomkenh_fk = rs.getString("nhomkenh_fk");
				this.loaidonhang = rs.getString("loaidonhang");
				this.khKGId = rs.getString("khachhangKG_FK") == null ? "" : rs.getString("khachhangKG_FK") ;
				this.nppId=rs.getString("NPP_FK");
				rs.close();
			}
			
			//Nếu CS duyệt thì đối với đơn ETC bắt buộc ASM phải duyệt, CS không được duyệt phủ cấp
			//KHÔNG DÙNG duyệt của ASM
			ASM_DA_DUYET = "1";
			if( this.loaidonhang.equals("1") && ASM_DA_DUYET.equals("0") && this.capduyet.equals("CS") )
			{
				this.msg = "Đơn hàng ETC, bạn phải yêu cầu ASM duyệt trước.";
				db.getConnection().rollback();
				return false;
			}
			
			
			if( vitriBAM.equals("benngoai") )
			{
				if(this.capduyet.equals("CS"))
				{
					query = " Update ERP_DondathangNPP set trangthai = '1', CS_DUYET = 1, thoidiem_cs_duyet = getdate(), userId_cs_duyet = '" + userId + "' " +
							"where pk_seq = '" + this.id + "'   ";
				}
				else if(this.capduyet.equals("SS")) //SS đã duyệt thì không được sửa đơn hàng nữa
				{
					query = " Update ERP_DondathangNPP set trangthai = '1', SS_DUYET = 1, thoidiem_ss_duyet = getdate(), userId_ss_duyet = '" + userId + "' " +
							"where pk_seq = '" + this.id + "' ";
				}
				else if(this.capduyet.equals("ASM")) 
				{
					query = " Update ERP_DondathangNPP set trangthai = '1', ASM_DUYET = 1, thoidiem_asm_duyet = getdate(), userId_asm_duyet = '" + userId + "' " +
							"where pk_seq = '" + this.id + "' ";
				}
			}
			else
			{
				if(this.capduyet.equals("CS"))
				{
					query = " Update ERP_DondathangNPP set trangthai = '1', ngaydonhang = '" + this.tungay +  "', ngaydenghi = '" + this.denngay + "', CS_DUYET = 1, thoidiem_cs_duyet = getdate(), userId_cs_duyet = '" + userId + "', ghichu = N'" + this.ghichu + "' " +
							"where pk_seq = '" + this.id + "'   ";
				}
				else if(this.capduyet.equals("SS")) //SS đã duyệt thì không được sửa đơn hàng nữa
				{
					query = " Update ERP_DondathangNPP set trangthai = '1', ngaydonhang = '" + this.tungay +  "', ngaydenghi = '" + this.denngay + "', SS_DUYET = 1, thoidiem_ss_duyet = getdate(), userId_ss_duyet = '" + userId + "', ghichu = N'" + this.ghichu + "' " +
							"where pk_seq = '" + this.id + "' ";
				}
				else if(this.capduyet.equals("ASM")) 
				{
					query = " Update ERP_DondathangNPP set trangthai = '1', ngaydonhang = '" + this.tungay +  "', ngaydenghi = '" + this.denngay + "', ASM_DUYET = 1, thoidiem_asm_duyet = getdate(), userId_asm_duyet = '" + userId + "', ghichu = N'" + this.ghichu + "' " +
							"where pk_seq = '" + this.id + "' ";
				}
			}
			
			System.out.println(":::: DUYET: " + query );
			if(!db.update(query))
			{
				this.msg = "Lỗi khi duyệt: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			boolean chungDVQL = false;
			/*
			if(this.loaidonhang.equals("2") || this.loaidonhang.equals("1") ) //chỉ áp dụng cho quy trình bán hàng không thầu
			{
				query = " select count(*) as sodong from KHACHHANG " + 
						" where PK_SEQ = ( select khachhang_fk from ERP_DondathangNPP where pk_seq = '" + this.id + "' ) AND isnull(NPPXHD_FK, NPP_FK) = isnull(NPPXK_FK, NPP_FK)  ";
				System.out.println(":::: CHECK CHUNG DON VI QUAN LY: " + query );
				rs = db.get(query);
				if(rs != null)
				{
					if(rs.next())
					{
						if(rs.getInt("sodong") > 0 )
							chungDVQL = true;
					}
					rs.close();
				}
			}
			else if( this.loaidonhang.equals("0") || this.loaidonhang.equals("3"))*/
				chungDVQL = true;
			
			
			//chỉ bán không thầu mới có cấp duyệt SS ->> SS duyệt thì chỉ đổi trạng thái, không check tồn kho
			/*System.out.println(":::: SS DA DUYET: " + SS_DA_DUYET + " -- LOAI DON HANG: " + loaidonhang );
			if(SS_DA_DUYET.equals("0") && loaidonhang.equals("2") ) //BOOK KHO KHI DUYET
			{
				if(this.khKGId.trim().length() <= 0 )
				{
				query =  "select khoxuat_fk as kho_fk, dathang.npp_fk, dathang.nhomkenh_fk, sp.PK_SEQ, sp.TEN, dathang.soluong,  kho.AVAILABLE as tonkho   " + 
						 "from      " + 
						 "(      " + 
						 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
						 "	from " + 
						 "	( " + 
						 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
						 "				case when a.dvdl_fk IS null then a.soluong       " + 
						 "					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " + 
						 "					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk ) end as soluong      " + 
						 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
						 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
						 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
						 "	union ALL	 " + 
						 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
						 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
						 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
						 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
						 "	) " + 
						 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
						 ")      " + 
						 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
						 "		inner join NHAPP_KHO kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " + 
						 "where  dathang.soluong > kho.AVAILABLE ";
				}
				else
				{
					query =  "select khoxuat_fk as kho_fk, dathang.npp_fk, dathang.nhomkenh_fk, sp.PK_SEQ, sp.TEN, dathang.soluong,  kho.AVAILABLE as tonkho   " + 
							 "from      " + 
							 "(      " + 
							 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
							 "	from " + 
							 "	( " + 
							 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
							 "				case when a.dvdl_fk IS null then a.soluong       " + 
							 "					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " + 
							 "					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong     " + 
							 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
							 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
							 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
							 "	union ALL	 " + 
							 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
							 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
							 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
							 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
							 "	) " + 
							 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
							 ")      " + 
							 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
							 "		inner join NHAPP_KHO_KYGUI kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " + 
							 "where kho.khachhang_fk = '" + this.khKGId + "' and ko.isNPP = '0' and dathang.soluong > kho.AVAILABLE ";
				}
				
				ResultSet rsCHECK = db.get(query);
				String mahangTHIEUTONKHO = "";
				if(rsCHECK != null)
				{
					while(rsCHECK.next())
					{
						mahangTHIEUTONKHO += "+ Mã hàng " + rsCHECK.getString("TEN") + " tồn kho chỉ còn tối đa ( " + rsCHECK.getString("tonkho") + " ) không đủ số lượng xuất ( " + rsCHECK.getString("soluong") + " ) \n ";
					}
					rsCHECK.close();
				}
				
				if(mahangTHIEUTONKHO.trim().length() > 0)
				{
					this.msg = mahangTHIEUTONKHO;
					db.getConnection().rollback();
					return false;
				}
				
				//NẾU CHUNG ĐƠN VỊ QUẢN LÝ THÌ DUYỆT LỆNH XUẤT HÀNG LUÔN
				if( !chungDVQL )
				{
					if( this.khKGId.trim().length() <= 0 )
					{
						query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
								 "				 kho.BOOKED = kho.BOOKED + dathang.soluong		" +
								 "from      " + 
								 "(      " + 
								 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
								 "	from " + 
								 "	( " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
								 "				case when a.dvdl_fk IS null then a.soluong       " + 
								 "					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " + 
								 "					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk ) end as soluong      " + 
								 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
								 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
								 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
								 "	union ALL	 " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
								 "	) " + 
								 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
								 ")      " + 
								 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
								 "		inner join NHAPP_KHO kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK ";
					}
					else
					{
						query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
								 "				 kho.BOOKED = kho.BOOKED + dathang.soluong		" +
								 "from      " + 
								 "(      " + 
								 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
								 "	from " + 
								 "	( " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
								 "				case when a.dvdl_fk IS null then a.soluong       " + 
								 "					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " + 
								 "					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong     " + 
								 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
								 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
								 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
								 "	union ALL	 " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
								 "	) " + 
								 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
								 ")      " + 
								 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
								 "		inner join NHAPP_KHO_KYGUI kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " + 
								 "where kho.khachhang_fk = '" + this.khKGId + "' and isNPP = '0'	 ";
					}
				}
				else
				{
					if( this.khKGId.trim().length() <= 0 )
					{
						query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
								 "				 kho.SOLUONG = kho.SOLUONG - dathang.soluong		" +
								 "from      " + 
								 "(      " + 
								 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
								 "	from " + 
								 "	( " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
								 "				case when a.dvdl_fk IS null then a.soluong       " + 
								 "					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " + 
								 "					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong     " + 
								 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
								 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
								 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
								 "	union ALL	 " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
								 "	) " + 
								 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
								 ")      " + 
								 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
								 "		inner join NHAPP_KHO kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK ";
					}
					else
					{
						query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
								 "				 kho.SOLUONG = kho.SOLUONG - dathang.soluong		" +
								 "from      " + 
								 "(      " + 
								 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
								 "	from " + 
								 "	( " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
								 "				case when a.dvdl_fk IS null then a.soluong       " + 
								 "					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " + 
								 "					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong     " + 
								 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
								 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
								 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
								 "	union ALL	 " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
								 "	) " + 
								 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
								 ")      " + 
								 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
								 "		inner join NHAPP_KHO_KYGUI kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " + 
								 "where kho.khachhang_fk = '" + this.khKGId + "' and isNPP = '0'	 ";
					}
				}
				
				System.out.print("::: CAP NHAT BOOKED: " + query );
				if(db.updateReturnInt(query) <= 0)
				{
					db.getConnection().rollback(); 
					this.msg = "2.Lỗi cập nhật tồn kho, " + query;
					return false; 
				}
			}*/
			
			
			//Khi CS duyệt đơn hàng nếu khách hàng cùng 1 đơn vị quản lý ( đơn hàng, hóa đơn) thì tự động duyệt luôn lệnh xuất hàng, tạo hóa đơn tự động.
			if( this.capduyet.equals("CS") )
			{
				//2. với đơn hang bán cho NPP + thầu, booked kho khi CS duyệt
				//if(this.loaidonhang.equals("0") || this.loaidonhang.equals("1") || this.loaidonhang.equals("3") )
				if(this.loaidonhang.equals("0") || this.loaidonhang.equals("1") || this.loaidonhang.equals("2") || this.loaidonhang.equals("3") )
				{
					if( this.khKGId.trim().length() <= 0 )
					{
						query =  "select khoxuat_fk as kho_fk, dathang.npp_fk, dathang.nhomkenh_fk, sp.PK_SEQ, sp.TEN, dathang.soluong,  isnull(kho.AVAILABLE, 0) as tonkho   " + 
								 "from      " + 
								 "(      " + 
								 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
								 "	from " + 
								 "	( " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
								 "				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong   " + 
								 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
								 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
								 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
								 "	union ALL	 " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
								 "	) " + 
								 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
								 ")      " + 
								 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
								 "		left join NHAPP_KHO kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " + 
								 "where  dathang.soluong > isnull(kho.AVAILABLE, 0) ";
					}
					else
					{
						query =  "select khoxuat_fk as kho_fk, dathang.npp_fk, dathang.nhomkenh_fk, sp.PK_SEQ, sp.TEN, dathang.soluong,  isnull(kho.AVAILABLE, 0 ) as tonkho   " + 
								 "from      " + 
								 "(      " + 
								 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
								 "	from " + 
								 "	( " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
								 "				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong    " + 
								 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
								 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
								 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
								 "	union ALL	 " + 
								 "		select c.kho_fk as khoxuat_fk, c.npp_fk, c.nhomkenh_fk, b.PK_SEQ as sanpham_fk, b.DVDL_FK as dvCHUAN, a.soluong    " + 
								 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA " + 
								 "				inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq     " + 
								 "		where a.DONDATHANGID in (  " + this.id + "  )   " + 
								 "	) " + 
								 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
								 ")      " + 
								 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
								 "		left join NHAPP_KHO_KYGUI kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " + 
								 "where kho.khachhang_fk = '" + this.khKGId + "' and kho.isNPP = '0' and  dathang.soluong > isnull(kho.AVAILABLE, 0) ";
					}
					
					System.out.println("::: CHECK TON KHO: " + query );
					ResultSet rsCHECK = db.get(query);
					String mahangTHIEUTONKHO = "";
					if(rsCHECK != null)
					{
						while(rsCHECK.next())
						{
							mahangTHIEUTONKHO += "+ Mã hàng " + rsCHECK.getString("TEN") + " tồn kho chỉ còn tối đa ( " + rsCHECK.getString("tonkho") + " ) không đủ số lượng xuất ( " + rsCHECK.getString("soluong") + " ) \n ";
						}
						rsCHECK.close();
					}
					
					if(mahangTHIEUTONKHO.trim().length() > 0)
					{
						this.msg = mahangTHIEUTONKHO;
						db.getConnection().rollback();
						return false;
					}
					
					//Nếu chung đơn vị quản lý thì chốt phiếu giao hàng luôn ( trừ kho luôn, không cần booked )
					if( !chungDVQL )
					{
						if( this.khKGId.trim().length() <= 0 )
						{
							query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
									 "				 kho.BOOKED = kho.BOOKED + dathang.soluong		" +
									 "from      " + 
									 "(      " + 
									 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
									 "	from " + 
									 "	( " + 
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong   " + 
									 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
									 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
									 "	UNION ALL	" +
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, b.pk_seq as sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				a.soluong    " + 
									 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.spMA = b.MA   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathangId = c.pk_seq     " + 
									 "		where a.DONDATHANGID in (  " + this.id + "  ) " + 
									 "	) " + 
									 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
									 ")      " + 
									 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
									 "		inner join NHAPP_KHO kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK ";
						}
						else
						{
							query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
									 "				 kho.BOOKED = kho.BOOKED + dathang.soluong		" +
									 "from      " + 
									 "(      " + 
									 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
									 "	from " + 
									 "	( " + 
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong   " + 
									 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
									 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
									 "	UNION ALL	" +
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, b.pk_seq as sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				a.soluong    " + 
									 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.spMA = b.MA   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathangId = c.pk_seq     " + 
									 "		where a.DONDATHANGID in (  " + this.id + "  ) " + 
									 "	) " + 
									 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
									 ")      " + 
									 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
									 "		inner join NHAPP_KHO_KYGUI kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " +
									 "where kho.khachhang_fk = '" + this.khKGId + "' and kho.isNPP = '0' ";
						}
					}
					else
					{
						if( this.khKGId.trim().length() <= 0 )
						{
							query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
									 "				 kho.SOLUONG = kho.SOLUONG - dathang.soluong		" +
									 "from      " + 
									 "(      " + 
									 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
									 "	from " + 
									 "	( " + 
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong    " + 
									 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
									 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
									 "	UNION ALL	" +
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, b.pk_seq as sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				a.soluong    " + 
									 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.spMA = b.MA   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathangId = c.pk_seq     " + 
									 "		where a.DONDATHANGID in (  " + this.id + "  ) " + 
									 "	) " + 
									 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
									 ")      " + 
									 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
									 "		inner join NHAPP_KHO kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK ";
						}
						else
						{
							query =  "update kho set kho.AVAILABLE = kho.AVAILABLE - dathang.soluong,   " + 
									 "				 kho.SOLUONG = kho.SOLUONG - dathang.soluong		" +
									 "from      " + 
									 "(      " + 
									 "	select khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk, SUM(soluong) as soluong " + 
									 "	from " + 
									 "	( " + 
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, a.sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				( a.soluong * dbo.LayQuyCach ( a.SANPHAM_FK, NULL, a.DVDL_FK ) ) as soluong    " + 
									 "		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq     " + 
									 "		where a.dondathang_fk in (  " + this.id + "  ) " + 
									 "	UNION ALL	" +
									 "		select c.kho_fk as khoxuat_fk, c.npp_fk,  c.nhomkenh_fk, b.pk_seq as sanpham_fk, b.DVDL_FK as dvCHUAN,      " + 
									 "				a.soluong    " + 
									 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.spMA = b.MA   " + 
									 "				inner join ERP_DONDATHANGNPP c on a.dondathangId = c.pk_seq     " + 
									 "		where a.DONDATHANGID in (  " + this.id + "  ) " + 
									 "	) " + 
									 "	DH  group by khoxuat_fk, npp_fk, nhomkenh_fk, sanpham_fk " + 
									 ")      " + 
									 "dathang inner join SANPHAM sp on dathang.sanpham_fk = sp.PK_SEQ   " + 
									 "		inner join NHAPP_KHO_KYGUI kho on dathang.khoxuat_fk = kho.KHO_FK and dathang.NPP_FK = kho.NPP_FK and dathang.nhomkenh_fk = kho.nhomkenh_fk and dathang.sanpham_fk = kho.SANPHAM_FK " +
									 "where kho.khachhang_fk = '" + this.khKGId + "' and kho.isNPP = '0' ";
						}
					}
					
					System.out.print("::: CAP NHAT BOOKED: " + query );
					if(db.updateReturnInt(query) <= 0)
					{
						db.getConnection().rollback(); 
						this.msg = "2.Lỗi cập nhật tồn kho, " + query;
						return false; 
					}
					
					//--> CHI CAP NHAT KHO TONG, KHI XUONG DUOI TAO LENH XUAT HANG SE CAP NHAT KHO CHI TIET
				}
				// PHIẾU XUẤT HÀNG VẪN TRỪ KHO VÀ VẪN TẠO RA BÌNH THƯỜNG.
				
				msg = this.TaoPhieuXuatHangNPP(db, id, userId, nppId, nhomkenh_fk, chungDVQL, vitriBAM );
				if(msg.trim().length() > 0)
				{
					msg = "Khong the chot: " + msg;
					db.getConnection().rollback();
					return false;
				}
				//PHA NAM ĐỔI LẠI, KHI DUYỆT LỆNH XUẤT HÀNG MỚI CHỈ BOOKED KHO TCHI TIẾT,
				////TỚI KHI CHỐT HÓA ĐƠN MỚI TRỪ KHO TỔNG VÀ CHI TIẾT
				
				
				//NẾU ĐƠN HÀNG MƯỢN THÌ KHÔNG TẠO PHIẾU XUẤT HÀNG, CHỈ TẠO RA PHIẾU CHUYỂN KHO KÝ GỬI CHO KHÁCH HÀNG KÝ GỬI
				
				if(this.donhangMuon.equals("1"))
				{
					msg = this.TaoChuyenKhoKyGuiChoKhachHang(db, id );
					if(msg.trim().length() > 0)
					{
						msg = "Khong the chot: " + msg;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					// cập nhật kế toán kho cho đơn hàng này
					String msg1 = this.CapNhatKeToan_Kho(db,this.id);
					if(msg1.trim().length() > 0)
					{
						msg = "Không thể cập nhật kế toán : " + msg1;
						db.getConnection().rollback();
						return false;
					}
				}
			
				//Chung đơn vị quản lý thì tự động chốt lệnh xuất hàng, sinh ra hóa đơn, còn không vẫn booked hàng trong kho chi tiết ở bước trên
				if(chungDVQL)
				{
					query = "select a.khachhang_fk, a.kbh_fk, a.npp_fk, a.npp_dat_fk, a.nhanvien_fk, " +
								"( select priandsecond from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatOTC,  " +
								"( select tuxuatETC from NHAPHANPHOI where pk_seq = a.npp_fk ) as tuxuatETC,  " +
								"( select loaiNPP from NHAPHANPHOI where pk_seq = a.npp_fk ) as loaiNPP, " +
								"( select tructhuoc_fk from NHAPHANPHOI where pk_seq = a.npp_fk ) as tructhuoc_fk,  " +
								" ISNULL( ( select dungchungkenh from NHAPHANPHOI where pk_seq = a.npp_fk ), 0 ) as dungchungkenh, a.kho_fk, a.ngaydonhang, a.donhangMUON, a.LoaiDonHang, isnull(b.tachhoadon, 0) as tachhoadon, isnull(b.tachhoadonTHEOVAT, 0) as tachhoadonTHEOVAT  "+
								"from ERP_DondathangNPP a left join KHACHHANG b on a.khachhang_fk = b.pk_seq " + 
								"where a.pk_seq = '" + this.id + "' order by a.pk_seq desc";
					
					String khachhangID = "";
					String nppId = "";
					String ngaydonhang = "";
					String donhangMUON = "0";
					String loaidonhang = "";
					String tachhoadon = "0";
					String tachhoadonTHEOVAT = "0";
					
					rs = db.get(query);
					if(rs != null)
					{
						if(rs.next())
						{
							ngaydonhang = rs.getString("ngaydonhang");
							if(rs.getString("khachhang_fk") != null)
								khachhangID = rs.getString("khachhang_fk");
							
							nppId = rs.getString("npp_fk");
							donhangMUON = rs.getString("donhangMUON");
							loaidonhang = rs.getString("loaidonhang");
							tachhoadon = rs.getString("tachhoadon");
							tachhoadonTHEOVAT = rs.getString("tachhoadonTHEOVAT");
						}
						rs.close();
					}
					
					query = " update ERP_DondathangNPP set trangthai = '4', NPP_DACHOT = '1', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
					System.out.println(":::: TU DUYET LENH XUAT HANG: " + query );
					if(db.updateReturnInt(query) <= 0 )
					{
						msg = "Khong the chot: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					
					// PHAI HUY DON DUOI Đối tác trực thuộc đặt lên (trường hợp không phải tự tao mới)
					query = "update ERP_Dondathang set trangthai = '4' where pk_seq = ( select from_dondathang from ERP_DondathangNPP where pk_seq = '" + id + "' ) ";
					if(!db.update(query))
					{
						msg = "1.Khong the chot: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					
					//NẾU CÓ TÍCH LŨY THÌ KHÔNG ĐƯỢC TÁCH HÓA ĐƠN
					
					// Tu dong tao Hoa don tai chinh cho NPP	
					////-	Sp thuộc ngành hóa mỹ phẩm phải tự tách hóa đơn 10%
					query = "select COUNT( distinct NGANHHANG_FK ) as soNGANHHANG,  "+
							"	   ( select COUNT( * ) from SANPHAM where NGANHHANG_FK = '100005' and PK_SEQ in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "' and soluong > 0 ) ) as coHOAMYPHAM, "+
							//"	( select count(*) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "' ) as soSP	" +
							"	( select count(*) from ERP_DONDATHANGNPP_TICHLUY_TRATL where dondathangId = '" + id + "' ) as coTL,	" +
							//"	   ( select COUNT( distinct thuexuat ) from SANPHAM where PK_SEQ in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "' ) ) as coTHUE_5_10, "+
							"	   ( select COUNT( distinct thuexuat ) from SANPHAM where nganhhang_fk != '100005' and thuexuat = 5 and PK_SEQ in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "' and soluong > 0 ) ) as coTHUE_5, "+
							"	   ( select COUNT( distinct thuexuat ) from SANPHAM where nganhhang_fk != '100005' and thuexuat = 10 and PK_SEQ in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "' and soluong > 0 ) ) as coTHUE_10, "+
							"	   ( select COUNT( distinct thuexuat ) from SANPHAM where nganhhang_fk != '100005' and thuexuat = 5 and MA in ( select SPMA from ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + id + "' and SPMA is not null ) ) as coTHUE_KM_5, "+
							"	   ( select COUNT( distinct thuexuat ) from SANPHAM where nganhhang_fk != '100005' and thuexuat = 10 and MA in ( select SPMA from ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + id + "' and SPMA is not null ) ) as coTHUE_KM_10 "+
							"from SANPHAM where PK_SEQ in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "' and soluong > 0 ) ";
					System.out.println(":::: CHECK HOA DON: " + query );
					int soNGANHHANG = 0;
					int coHOAMYPHAM = 0;
					int coTHUE_5_10 = 0;
					//int soSANPHAM = 0;
					int coTichluy = 0;
					
					int coTHUE_5 = 0;
					int coTHUE_10 = 0;
					int coTHUE_KM_5 = 0;
					int coTHUE_KM_10 = 0;
					boolean coKM = false;
					
					rs = db.get(query);
					if(rs.next())
					{
						soNGANHHANG = rs.getInt("soNGANHHANG");
						coHOAMYPHAM = rs.getInt("coHOAMYPHAM");
						//coTHUE_5_10 = rs.getInt("coTHUE_5_10");
						//soSANPHAM = rs.getInt("soSANPHAM");
						coTichluy = rs.getInt("coTL");
						
						coTHUE_5 = rs.getInt("coTHUE_5");
						coTHUE_10 = rs.getInt("coTHUE_10");
						coTHUE_KM_5 = rs.getInt("coTHUE_KM_5");
						coTHUE_KM_10 = rs.getInt("coTHUE_KM_10");
						
						coTHUE_5_10 = coTHUE_5 + coTHUE_10 + coTHUE_KM_5 + coTHUE_KM_10;
						if( coTHUE_KM_5 > 0 || coTHUE_KM_10 > 0 )
							coKM = true;
					}
					rs.close();
					
					System.out.println("::: TACH HOA DON: " + tachhoadon + " -- DON HANG MUON: " + donhangMUON );
					if( ( tachhoadon.equals("1") || ( soNGANHHANG >= 2 && coHOAMYPHAM > 0 ) ) && coTichluy >= 1 )
					{
						msg = "Đơn hàng trả tích lũy. Bạn không thể tách hóa đơn này";
						db.getConnection().rollback();
						return false;
					}
					
					if( tachhoadon.equals("1") )
					{
						//Mỗi sản phẩm tách thành 1 hóa đơn
						query = "select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "' and soluong > 0 ";
						System.out.println(query);
						rs = db.get(query);
						
						while( rs.next() )
						{
							msg = this.TaoHoaDonTaiChinhNPP_TuDongTach(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "0", rs.getString("sanpham_fk"));
							if(msg.trim().length() > 0)
							{
								msg = "Khong the tao hoa don tai chinh: " + msg;
								db.getConnection().rollback();
								return false;
							}
						}
					}
					else  //CHỈ QUAN TÂM 2 trường hợp: hóa mỹ phẩm và thuế xuất ( 5, 10 )
					{
						if( coHOAMYPHAM >= 1 )
						{
							//NẾU CÓ HÓA MỸ PHẨM LÀ HÀNG BÁN 
							boolean choHangKM_VaoHMP = false;
							if( soNGANHHANG == 1 ) //Hàng bán chỉ có HÓA MỸ PHẨM, nhưng có KM ngành hàng khác
							{
								query = "select count(*) as sodong from ERP_DONDATHANGNPP_CTKM_TRAKM where dondathangId = '" + this.id + "' ";
								ResultSet rsCHECK = db.get(query);
								if( rsCHECK.next() )
								{
									if( rsCHECK.getInt("sodong") > 0 )
										choHangKM_VaoHMP = true;
								}
								rsCHECK.close();
							}
							
							System.out.println("::::  choHangKM_VaoHMP " + choHangKM_VaoHMP );
							msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "100005", "1", "10", "", choHangKM_VaoHMP );
							if(msg.trim().length() > 0)
							{
								msg = "Khong the tao hoa don tai chinh ( HMP ): " + msg;
								db.getConnection().rollback();
								return false;
							}
						}
						
						//Xét trường hợp có hàng KM, nhưng không có hàng bán với thuế xuất tương ứng
						String tachUUTIEN = "";
						if( coKM )
						{
							if( coTHUE_KM_5 > 0 && coTHUE_5 <= 0  )
							{
								tachUUTIEN = "10"; //ĐƯA HÀNG KM 5% VÀO HÓA ĐƠN BÁN VAT 10%
							}
							else if( coTHUE_KM_10 > 0 && coTHUE_10 <= 0 )
							{
								tachUUTIEN = "5"; //ĐƯA HÀNG KM 10% VÀO HÓA ĐƠN BÁN VAT 5%
							}
						}
						
						System.out.println("::: TACH UU TIEN: " + tachUUTIEN + " - BAN: thue 5%: " + coTHUE_5 + ", thue 10%: " + coTHUE_10 + " - KM: thue 5%: " + coTHUE_KM_5 + ", thue 10%: " + coTHUE_KM_10 );
						if( coTHUE_5 > 0 )
						{
							msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "1", "5", tachUUTIEN, false );
							if(msg.trim().length() > 0)
							{
								msg = "Khong the tao hoa don tai chinh ( 5% ): " + msg;
								db.getConnection().rollback();
								return false;
							}
						}
						
						if( coTHUE_10 > 0 )
						{
							msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "1", "10", tachUUTIEN, false );
							if(msg.trim().length() > 0)
							{
								msg = "Khong the tao hoa don tai chinh ( 10% ): " + msg;
								db.getConnection().rollback();
								return false;
							}
						}
						
						
						/*if( coTHUE_5_10 >= 2 ) //Có tách hóa đơn theo VAT, và có 2 VAT 5, 10 trong đơn hàng
						{
							if( soNGANHHANG <= 1 || coHOAMYPHAM <= 0 )
							{
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "0", "5" );
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh ( 5% ): " + msg;
									db.getConnection().rollback();
									return false;
								}
								
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "0", "10" );
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh ( 10% ): " + msg;
									db.getConnection().rollback();
									return false;
								}
							}
							else if( soNGANHHANG >= 2 && coHOAMYPHAM > 0 )
							{
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "0", "5" );
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh ( 5% ): " + msg;
									db.getConnection().rollback();
									return false;
								}								
								
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "1", "10" );
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh ( 10% ): " + msg;
									db.getConnection().rollback();
									return false;
								}
								
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "100005", "1", "10" );
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh ( 10% ): " + msg;
									db.getConnection().rollback();
									return false;
								}
							}
						}
						else
						{								
							if( soNGANHHANG <= 1 || coHOAMYPHAM <= 0 )
							{
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "0", "");
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh: " + msg;
									db.getConnection().rollback();
									return false;
								}
							}
							else if( soNGANHHANG >= 2 && coHOAMYPHAM > 0 )
							{
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "", "1", "" );
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh: " + msg;
									db.getConnection().rollback();
									return false;
								}
								
								msg = this.TaoHoaDonTaiChinhNPP(db, id, userId, nppId, khachhangID, ngaydonhang, donhangMUON, loaidonhang, congtyId, "100005", "1", "");
								if(msg.trim().length() > 0)
								{
									msg = "Khong the tao hoa don tai chinh HOA MY PHAM: " + msg;
									db.getConnection().rollback();
									return false;
								}
							}
						}*/
					}
					
					msg = util.Check_Kho_Tong_VS_KhoCT(nppId, db);
					if(msg.length() > 0)
					{
						db.getConnection().rollback();
						return false;
					}
					
					msg= this.check_tonkhobi_am(id);
					if(msg.length() > 0)
					{
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}
	  
	private String check_tonkhobi_am(String dhid) {
		 
		Utility_Kho util_kho=new Utility_Kho();
		String msg="";
		try
		{
		// TODO Auto-generated method stub
		String query=" SELECT  A.NGAYDONHANG,B.SANPHAM_FK, A.KHO_FK,A.NPP_FK, 100000 as NHOMKENH_FK, solo ,   SUM(B.soluongCHUAN) AS TONGXUAT "+   
					"		 FROM ERP_DONDATHANGNPP A  "+
					" 		INNER JOIN ERP_DONDATHANGNPP_SANPHAM_CHITIET B ON A.PK_SEQ = B.DONDATHANG_FK "+  
					//" 		WHERE   A.TRANGTHAI=4 and a.pk_seq = " + dhid + " and NPP_DACHOT=1  "+
					" 		WHERE   A.TRANGTHAI in ( 2, 4 ) and a.pk_seq = " + dhid + " and CS_DUYET = 1  "+
					" 		  "+
					" 		GROUP BY A.NGAYDONHANG,B.SANPHAM_FK, A.KHO_FK,A.NPP_FK, solo  ";
		ResultSet rs=db.get(query);
		while(rs.next())
		{
			String sanpham_fk= rs.getString("SANPHAM_FK");
			String ngaydh= rs.getString("NGAYDONHANG");
			//TẠM THỜI CHECK TỚI NGÀY HIỆN TẠI, CUỐI THÁNG KHÓA SỔ XONG MỞ LẠI
			ngaydh = this.getDateTime();
			
			String kho_fk= rs.getString("KHO_FK");
			String NPP_FK= rs.getString("NPP_FK");
			//String NHOMKENH_FK= rs.getString("NHOMKENH_FK");
			String NHOMKENH_FK= "100000";
			String solo= rs.getString("solo");
			msg = util_kho.Check_NPP_Kho_Sp_Chitiet_Nhohon_0(ngaydh, "Đơn đặt hàng", db, kho_fk, sanpham_fk, NPP_FK, NHOMKENH_FK, solo);
			if(msg.length()>0)
			{
				return msg;
			}
		}
		rs.close();
		
		}
		catch(Exception e)
		{ 
			e.printStackTrace();
			return "Exception: " + e.getMessage();
			
		}
		
		return msg;
		
		
	}

	private String CapNhatKeToan_Kho(dbutils db, String id) {
		
		try{
			
			
			Utility_Kho util_kho=new Utility_Kho();
				String query = 	 " SELECT ISNULL(YC.DONHANGMUON,'0') AS DONHANGMUON , YC.NgayDonHang AS NGAYYEUCAU, YC_CT.SANPHAM_FK, "+ 
						 " SUM(isnull(YC_CT.soluongCHUAN,0)) AS TONGXUAT,YC_CT.SOLO ,YC_CT.ngayhethan, "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
						 " 'XK01' "+ 
						 " ELSE "+ 
						 " 'XK02' END AS MAXUATKHO, "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 AND YC.KHO_FK <> 100008  THEN "+ 
						 " ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' "+ 
						 " AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+")) "+ 
						 " ELSE   "+ 
						 " (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000' "+ 
						 " AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" )) "+ 
						 " END AS TAIKHOANNO_FK,   "+ 
						 " "+ 
						 " (  SELECT TK.PK_SEQ FROM ERP_LOAISANPHAM LSP "+ 
						 " INNER JOIN SANPHAM SP ON SP.LOAISANPHAM_FK=LSP.PK_SEQ "+ 
						 " INNER JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN=LSP.TAIKHOANKT_FK "+ 
						 " AND TK.CONGTY_FK =((SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" )) WHERE SP.PK_SEQ=SANPHAM_FK )  AS TAIKHOANCO_FK, "+ 
						 " "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
						 " '100002' "+ 
						 " ELSE "+ 
						 " '100008' END AS NOIDUNGXUAT_FK, "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
						 " N'Xuất bán hàng (theo đơn hàng bán) - Khác kho ký gửi' "+ 
						 " ELSE "+ 
						 " N'Xuất khuyến mại - Khác kho ký gửi' END AS KHOANMUC ,"+ 
						 " (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" ) as congty_fk , SP.MA_FAST MASP, SP.TEN TENSP, DV.DIENGIAI DVT	"+ 
						 " FROM ERP_DONDATHANGNPP YC "+ 
						 " INNER JOIN ERP_DONDATHANGNPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.dondathang_fk "+ 
						 " INNER JOIN SANPHAM SP ON YC_CT.SANPHAM_FK = SP.PK_SEQ "+
						 " INNER JOIN DONVIDOLUONG DV ON YC_CT.DVDL_FK = DV.PK_SEQ "+
						 " WHERE YC.PK_SEQ ="+id +
						 " GROUP BY NgayDonHang, YC_CT.SANPHAM_FK, YC.Kho_FK, YC.NHOMKENH_FK, YC_CT.SCHEME,YC_CT.SOLO,YC_CT.ngayhethan,YC.DONHANGMUON, SP.MA_FAST , SP.TEN , YC_CT.soluong , DV.DIENGIAI  ";
	
	System.out.println("Định khoản: " + query);
	ResultSet rs = db.get(query);
	int dem=0;
	while(rs.next()){
		dem++;
		String ngaychungtu = rs.getString("NGAYYEUCAU");
		String nam = ngaychungtu.substring(0, 4);
		String thang = "" + (Integer.parseInt(ngaychungtu.substring(5, 7)));
		String ngayghinhan = ngaychungtu;
		String loaichungtu = rs.getString("MAXUATKHO");
		String sochungtu = id;
		String taikhoanNO_fk =  rs.getString("TAIKHOANNO_FK");
		String taikhoanCO_fk =  rs.getString("TAIKHOANCO_FK");
		String NOIDUNGXUAT_FK = rs.getString("NOIDUNGXUAT_FK");
		String ngayhethan=rs.getString("ngayhethan");
		
		String congty_fk= rs.getString("congty_fk");
		
		String  SANPHAM_FK= rs.getString("SANPHAM_FK");
		String solo=rs.getString("SOLO");
		String DONHANGMUON=rs.getString("DONHANGMUON");
		
		String masp = rs.getString("MASP");
		String tensp = rs.getString("TENSP");
		String dvt = rs.getString("DVT");
		
		double DONGIA=0; 
		
		String str[] = util_kho.getGiaChayKT(ngaychungtu, db, congty_fk , nppId, SANPHAM_FK, solo);
		if(str[1].length() >0){

			msg = "Không thể cập nhật lỗi :  " + str[1];
			//db.getConnection().rollback();
			return msg;
		}else{
			DONGIA=Double.parseDouble(str[0]);
		}
		
		query=	"	UPDATE  ERP_DONDATHANGNPP_SANPHAM_CHITIET SET GIACHAYKT ="+DONGIA+",taikhoanktNo="+taikhoanNO_fk+",taikhoanktCO="+taikhoanCO_fk+"  WHERE SOLO='"+solo+"' AND SANPHAM_FK= "+SANPHAM_FK+" AND DONDATHANG_FK= "+id;
		if(!db.update(query)){
			return "Không thể cập nhật dòng lệnh ,vui lòng kiểm tra lại"+query;
		}
		
		String NO = "" + (rs.getDouble("TONGXUAT")* DONGIA);
		String CO = NO;
		String DOITUONG_NO="";
		if(NOIDUNGXUAT_FK.equals("100002")){
				DOITUONG_NO = "Giá vốn hàng xuất bán";
		}else{
			   DOITUONG_NO = "Giá vốn hàng xuất khuyến mãi";
		}
		String MADOITUONG_NO = "";
		String DOITUONG_CO = "Sản phẩm";
		String MADOITUONG_CO = rs.getString("SANPHAM_FK");
		String LOAIDOITUONG = "";
		String SOLUONG = "" + rs.getDouble("TONGXUAT");
		
		String TIENTEGOC_FK = "100000";
		String DONGIANT = "0";
		String TIGIA_FK = "1";
		String TONGGIATRI = NO;
		String TONGGIATRINT = TONGGIATRI;
		String khoanmuc = rs.getString("KHOANMUC");
		NumberFormat formater = new DecimalFormat("########.00");
		
		
	 
		// chạy kế toán cho đơn hàng mượn
		String msg1= util.Update_TaiKhoan_SP(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
					 	NOIDUNGXUAT_FK, NO, CO, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
					 	SOLUONG, formater.format(DONGIA), TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, khoanmuc, masp, tensp, dvt);
		/*String msg1 = util.Update_TaiKhoan_DienGiai_SP_TheoLo(db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, taikhoanNO_fk, taikhoanCO_fk, 
				NOIDUNGXUAT_FK, NO, CO, 
				DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO, LOAIDOITUONG, 
				SOLUONG,  formater.format(DONGIA), 
				TIENTEGOC_FK,DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, "",khoanmuc, sochungtu, masp, tensp, dvt,solo,ngayhethan);*/
		 
		if(msg1.length()>0){
			return msg1;
		}
		 
	}
	
	if(dem==0){
		msg = "Không cập nhật được khoản mục kế toán , vui lòng báo admin để được xử lý .";
		//db.getConnection().rollback();
		return msg;
	}
	
			
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
		return "";
	}

	
	
	
	private    String TaoChuyenKhoKyGuiChoKhachHang(dbutils db, String donhangid)
			  {
		try{
			
			String query="  SELECT NgayDonHang,KBH_FK,KHACHHANG_FK,NPP_FK,DH.NGUOISUA,nhomkenh_fk,Kho_FK ,NPP.congty_fk  " +
						 "  FROM ERP_DONDATHANGNPP DH INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK  where DH.PK_SEQ = "+donhangid;
			ResultSet rs=db.get(query);
			Utility_Kho util_kho=new Utility_Kho();
			Utility util =new Utility();
			
			if(rs.next()){
				String ngaydonhang=rs.getString("NgayDonHang");
				String ngaytao= getDateTime();
				String nhomkenh=rs.getString("nhomkenh_fk");
				String Npp_fk=rs.getString("NPP_FK");
				String nguoisua=rs.getString("NGUOISUA");
				String khoxuat_fk=rs.getString("Kho_FK");
				String khonhan_fk="100003";
				String KH_NHAN_FK=rs.getString("KHACHHANG_FK");
				String congtyid=rs.getString("congty_fk");
				
				   String noidungxuat_fk="100009";

				   
				  query=" INSERT INTO ERP_CHUYENKHO (NgayChuyen,KhoXuat_FK,KhoNhan_FK,TRANGTHAI,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,GHICHU" +
				  		" ,NPP_FK,NOIDUNGXUAT_FK,NGAYCHOT,NHOMKENH_FK,KH_NHAN_FK,CONGTY_FK,LYDO ,DONDATHANGNPP_FK,ISHANGDIDUONG) " +
					    " VALUES('"+ngaydonhang+"','"+khoxuat_fk+"',"+khonhan_fk+",'1','"+ngaytao+"',"+nguoisua+",'"+ngaytao+"',"+nguoisua+",N'chuyển kho tự động cho khách hàng ký gửi', "+Npp_fk+" " +
					 		" ,"+noidungxuat_fk+" ,'"+ngaydonhang+"',"+nhomkenh+","+KH_NHAN_FK+","+congtyid+",N'chuyển kho tự động cho khách hàng ký gửi theo đơn hàng:"+donhangid+"',"+donhangid+",'1')";
				  if(!db.update(query)){
						
						return "Không thể tạo chuyển kho kỳ gửi,vui lòng thử lại, hoặc báo Admin để được trợ giúp";
					}
				  String ycnlCurrent = "";
					query = " select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";
					
					ResultSet rsPxk = db.get(query);						
					if(rsPxk.next())
					{
						ycnlCurrent = rsPxk.getString("ckId");
						rsPxk.close();
					}
					
					query="UPDATE ERP_CHUYENKHO SET machungtu= 'PX"+ngaydonhang.substring(5, 6)+ ngaydonhang.substring(0,4) +  ycnlCurrent+"' WHERE PK_SEQ= "+ ycnlCurrent;
					if(!db.update(query)){
						
						return "Không thể tạo chuyển kho kỳ gửi,vui lòng thử lại, hoặc báo Admin để được trợ giúp";
					}
					
				  query=  " INSERT INTO ERP_CHUYENKHO_SANPHAM (chuyenkho_fk,sanpham_fk,SOLUONGNHAN,SOLUONGXUAT)  " +
				  		  " SELECT  "+ycnlCurrent+",  B.SANPHAM_FK,   SUM(B.soluongCHUAN) ,SUM(B.soluongCHUAN) AS TONGXUAT "+   
						  " FROM ERP_DONDATHANGNPP A  "+
						  " INNER JOIN ERP_DONDATHANGNPP_SANPHAM_CHITIET B ON A.PK_SEQ = B.DONDATHANG_FK "+  
						  " WHERE A.PK_SEQ="+donhangid +
						  " GROUP BY B.SANPHAM_FK ";
				  
				    if(!db.update(query)){
						
						return "Không thể tạo chuyển kho kỳ gửi,vui lòng thử lại, hoặc báo Admin để được trợ giúp";
					}
				    
				    query  =" SELECT  A.PK_SEQ,  B.SANPHAM_FK, A.KHO_FK,A.NPP_FK, A.NHOMKENH_FK, solo , SUM(B.soluongCHUAN) AS soluong,B.NGAYHETHAN "+   
						    " FROM ERP_DONDATHANGNPP A  "+
						    " INNER JOIN ERP_DONDATHANGNPP_SANPHAM_CHITIET B ON A.PK_SEQ = B.DONDATHANG_FK "+  
						    " WHERE A.PK_SEQ="+donhangid+ 
						    "  GROUP BY B.SANPHAM_FK, A.KHO_FK,A.NPP_FK, A.NHOMKENH_FK, solo,A.PK_SEQ,NGAYHETHAN ";
				    ResultSet rsct=db.get(query);
				    while(rsct.next()){
				    	String spid=rsct.getString("SANPHAM_FK");
				    	String solo=rsct.getString("solo");
				    	double soluongct=rsct.getDouble("soluong");
				    	String NGAYHETHAN=rsct.getString("NGAYHETHAN");
				    /*	query=  " INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,SOLUONG,NGAYHETHAN) values " +
				    			" ("+ycnlCurrent+","+spid+",'"+solo+"',"+soluongct+",'"+NGAYHETHAN+"')";
				    	if(!db.update(query)){
							
							return "Không thể tạo chuyển kho kỳ gửi,vui lòng thử lại, hoặc báo Admin để được trợ giúp";
						}*/
				    	query=  " INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,SOLUONG,NGAYHETHAN) values " +
		    			" ("+ycnlCurrent+","+spid+",'"+solo+"',"+rsct.getDouble("soluong")+",'"+NGAYHETHAN+"')";
				    	if(!db.update(query)){
							
							return "Không thể tạo chuyển kho kỳ gửi,vui lòng thử lại, hoặc báo Admin để được trợ giúp";
						}
				    	// TĂNG KHO NHẬN LÀ KHO KỲ GỬI KHÁCH HÀNG
				    	 
				   	 /*  query =" select  NGAYSANXUAT,NGAYNHAPKHO,NGAYHETHAN,NGAYCHUNGTU,GIAMUA from NHAPP_KHO_CHITIET where KHO_FK="+khoxuat_fk+" AND SANPHAM_FK="+spid+" AND SOLO='"+solo+"' AND NPP_FK="+Npp_fk+" AND nhomkenh_fk="+nhomkenh;
				   	   ResultSet rslo=db.get(query);
						String ngayhethan="";
						String Ngaysanxuat="";
						String ngaynhapkho="";
						String ngaychungtu="";
						double giatheolo=0;
					if(rslo!=null){
						if(rslo.next()){
							  ngayhethan=rslo.getString("NGAYHETHAN");
							  Ngaysanxuat=rslo.getString("NGAYSANXUAT");
							  ngaynhapkho=rslo.getString("NGAYNHAPKHO");
							  ngaychungtu=rslo.getString("NGAYCHUNGTU");
							  giatheolo=rslo.getDouble("GIAMUA");
						}
						rslo.close();
					}
					
					
				   	query=" select  GIAMUA  from NHAPP_KHO where KHO_FK="+khoxuat_fk+" AND SANPHAM_FK="+spid+" AND NPP_FK="+Npp_fk+" AND nhomkenh_fk="+nhomkenh;
					ResultSet rsgiaton=db.get(query);
					double giaton=0;
					if(rsgiaton!=null){
						if(rsgiaton.next()){
							giaton=rsgiaton.getDouble("GIAMUA");
						}
					}
					
					double	soluong_cn=soluongct;
					double	booked_cn=0;
					double	available_cn=soluongct;
					String	msg1=util_kho.Update_NPP_Kho_Sp(ngaydonhang, "Chuyển kho", db, khonhan_fk, spid, Npp_fk, nhomkenh,soluong_cn, booked_cn , available_cn,giaton);
					if(msg1.length() >0){
						return msg1;
					}
					msg1=util_kho.Update_NPP_Kho_Sp_Kygui( ngaydonhang, "Chuyển kho", db,  khonhan_fk, spid,   Npp_fk, nhomkenh,soluong_cn, booked_cn , available_cn, giaton , KH_NHAN_FK); 
					if(msg1.length() >0){
							return msg1;
					}
					msg1=util_kho.Update_NPP_Kho_Sp_Kygui_Chitiet( ngaydonhang, "Chuyển kho", db,  khonhan_fk,  spid, Npp_fk, nhomkenh,  KH_NHAN_FK ,solo, Ngaysanxuat, ngayhethan,ngaydonhang,  soluong_cn, booked_cn , available_cn, giatheolo) ;
					if(msg1.length() >0){
							return msg1;
					}*/
							 
							
							
				    }
				    rsct.close();
				    
				 String   ngaychot=ngaydonhang;
				 
					String nam = ngaychot.substring(0, 4);
					String thang = ngaychot.substring(5, 7);
								
					//KE TOAN TU DONG PHAT SINH
								 
					int namOLD = Integer.parseInt(nam);
					int thangOLD = Integer.parseInt(thang);
							
					if(thangOLD == 1)
					{
						thangOLD = 12;
						namOLD = namOLD - 1;
					}
					else
					{
						thangOLD = thangOLD - 1;
					}
								
					String taikhoanktCo = "";
					String taikhoanktNo = "";
				/*	if(noidungxuat_fk.equals("100021") || noidungxuat_fk.equals("100028")) {
						
					query=" SELECT count(PK_SEQ) as dem FROM ERP_TAIKHOANKT  "+
					  " WHERE SOHIEUTAIKHOAN IN ( SELECT TAIKHOAN_FK FROM ERP_NHOMCHIPHI "+  
					  " WHERE PK_SEQ = (select NHOMCHIPHI_FK FROM ERP_CHUYENKHO CK WHERE CK.PK_SEQ="+ycnlCurrent+" ) ) AND CONGTY_FK = "+congtyid;
					System.out.println("check tk : "+query);
						ResultSet rscheck1=db.get(query);
						if(rscheck1.next()){
							if(rscheck1.getInt("dem")==0){
								rscheck1.close();
								 
								return " Không xác định được tài khoản của khoản mục chi phí đang chọn ,vui lòng kiểm tra lại ";
							}
							if(rscheck1.getInt("dem")>1){
								rscheck1.close();
								 
								return " Có quá nhiều tài khoản được xác định ở khoản mục chi phí đang chọn ,vui lòng báo admin để kiểm tra lại ";
							}
						}
						rscheck1.close();
					}*/
								 
					/*query = " SELECT CK.NPP_FK,CK_SP.SOLO,CK_SP.ngayhethan , CK_SP.SOLUONG AS SOLUONGXUAT, SP.LOAISANPHAM_FK, CK_SP.SANPHAM_FK, SP.DVKD_FK, \n " +
							"	ISNULL( ( SELECT AVG(GIATON) FROM ERP_TONKHOTHANG  \n " +
							"			  WHERE THANG = " + thangOLD + " AND NAM = " + namOLD + " AND SANPHAM_FK = CK_SP.SANPHAM_FK " +
							" 			  AND CONGTY_FK = " + congtyid + " " + 
							"		  ), 0 ) as GIATON,  \n  " +
								
							" CK.NOIDUNGXUAT_FK, CK.NHOMCHIPHI_FK, \n " +
								 		
							" NDN.MA AS MAXUATKHO,  \n " +
								 		
							" NDN.TEN AS TENXUATKHO,  \n " +
						 
					 		" (  " +
					 		"   SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
							"   WHERE SOHIEUTAIKHOAN IN ( SELECT TAIKHOAN_FK FROM ERP_NHOMCHIPHI " +
							"	WHERE PK_SEQ = CK.NHOMCHIPHI_FK ) AND CONGTY_FK = " + congtyid + " ) AS  TaiKhoanNO,  \n " + 
							
							"	( SELECT PK_SEQ FROM ERP_TAIKHOANKT " +
							"	  WHERE SOHIEUTAIKHOAN IN ( SELECT TAIKHOANKT_FK FROM ERP_LOAISANPHAM " +
							"						WHERE PK_SEQ = SP.LOAISANPHAM_FK) AND CONGTY_FK = " + congtyid + " ) AS TaiKhoanCO, CK.LYDO, CK.MACHUNGTU,  \n " +
							" 	SP.TEN TENSP, SP.MA_FAST MASP, DV.DIENGIAI DONVITINH  \n"+
									
							" FROM ERP_CHUYENKHO_SP_XUATHANG CK_SP  \n " +
							" INNER JOIN  SANPHAM SP on CK_SP.SANPHAM_FK = SP.PK_SEQ  \n " +
							" INNER JOIN DONVIDOLUONG DV ON SP.DVDL_FK = DV.PK_SEQ \n"+
							" INNER JOIN ERP_CHUYENKHO CK ON CK.PK_SEQ = CK_SP.CHUYENKHO_FK  \n " +
							" INNER JOIN ERP_NOIDUNGNHAP NDN ON NDN.PK_SEQ = NOIDUNGXUAT_FK \n " +
							" LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CK.NHOMCHIPHI_FK  \n " +
							" WHERE CK_SP.CHUYENKHO_FK = '" + ycnlCurrent + "' ";
								
					System.out.println("-----LAY TAI KHOAN: " + query);
					ResultSet rsCK = db.get(query);
					 
						while(rsCK.next())
						{
							String sanpham_fk = rsCK.getString("SANPHAM_FK");
							String diengiai = rsCK.getString("LYDO");
							String machungtu = rsCK.getString("MACHUNGTU");
							String tiente_fk = "100000";
							double soluong = rsCK.getDouble("SOLUONGXUAT");
							String solo= rsCK.getString("solo");
							String ngayhethan=rsCK.getString("ngayhethan");
							String NppId=rsCK.getString("NPP_FK");
							String masp = rsCK.getString("MASP");
							String tensp = rsCK.getString("TENSP");
							String dvt = rsCK.getString("DONVITINH");
							
							double giaTonTH=0;
							String[] str=util_kho.getGiaChayKT(ngaychot, db, congtyid, NppId,sanpham_fk,solo);
							
							if(str[1].length()> 0){
								rsCK.close();
								 
								return str[1];
							}else{
								giaTonTH=Double.parseDouble(str[0]);
							}
							
							
							 
							query=" select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN='15700000' and CONGTY_FK= "+congtyid;
							ResultSet rscheck=db.get(query);
							if(rscheck.next()){
								taikhoanktNo = rscheck.getString("pk_seq");
							}
							rscheck.close();
								
							  
							
							taikhoanktCo = rsCK.getString("TaiKhoanCO");
							
							
							query=" update ERP_CHUYENKHO_SP_XUATHANG SET GIACHAYKT="+giaTonTH+" , taikhoanktCO= "+taikhoanktCo+" ,taikhoanktNo ="+taikhoanktNo+" where SANPHAM_FK="+sanpham_fk+" AND CHUYENKHO_FK="+ycnlCurrent+" AND SOLO='"+solo+"'";
							if(!db.update(query)){
								db.getConnection().rollback();
								return "Lỗi dòng lệnh :"+query;
							}
							
						
							//DINH KHOAN KE TOAN
							if(taikhoanktCo.trim().length() > 0 || taikhoanktNo.trim().length() > 0 )
							{
								double tonggiatri = soluong * giaTonTH;
								String msg = util.Update_TaiKhoan_DienGiai_SP_TheoLo(db, thang, nam, ngaychot, ngaychot, rsCK.getString("TENXUATKHO"), ycnlCurrent, taikhoanktNo, taikhoanktCo, 
											 rsCK.getString("NOIDUNGXUAT_FK"), Double.toString(tonggiatri), Double.toString(tonggiatri), 
											 "Chi phí", rsCK.getString("NHOMCHIPHI_FK"), "Sản phẩm", sanpham_fk, "", 
											 Double.toString(rsCK.getDouble("SOLUONGXUAT")), Double.toString(giaTonTH), 
											 tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri), rsCK.getString("TENXUATKHO"), diengiai, machungtu, masp, tensp, dvt,solo,ngayhethan);
											
								if(msg.trim().length() > 0)
								{
									rsCK.close();
									return msg;
								}
		
						 
							}
						}
						rsCK.close();
					 
				*/
				    
				    
				
				  
			}else{
				return "Không xác định được đơn hàng để tạo chuyển kho tự động cho khách hàng ký gửi , vui lòng thử lại hoặc báo Admin để được trợ giúp:";
			}
			rs.close();
			  // Chạy kế toán 
				    
				    
				    
			
			return " ";
		}catch(Exception er){
			er.printStackTrace();
			return er.getMessage();
		}
	}

	private String TaoPhieuXuatHangNPP(dbutils db, String id, String userId, String nppId, String nhomkenh_fk, boolean chungDVQL, String vitriBAM)
	{
		//Nếu chung đơn vị quản lý thì trừ kho luôn, không cần phải booked kho nữa, nếu khôn thì vẫn booked kho, tới khi chốt lệnh xuất hàng sẽ trừ kho
		
		String query = "";
		String msg = "";
		
		try
		{
			// CHECK XEM CO SP NAO CÓ SỐ LƯỢNG TRONG ĐƠN HÀNG MÀ CHƯA THIẾT LẬP QUY CÁCH KHÔNG
			query = "		select a.sanpham_fk, b.DVDL_FK as dvCHUAN,    " +
					"				case when a.dvdl_fk IS null then a.soluong     " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong    " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )   end as soluong, " +
					"			0 as loai, ' ' as scheme, b.ten as spTEN   " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
					"		where a.dondathang_fk in ( '" + id + "' ) and a.soluong > 0   ";
			ResultSet rsCHECK = db.get(query);
			String spCHUACOQC = "";
			if(rsCHECK != null)
			{
				while(rsCHECK.next())
				{
					if (rsCHECK.getString("soluong") == null) 
						spCHUACOQC += rsCHECK.getString("spTEN") + ", ";
				}
				rsCHECK.close();
			}
			
			if(spCHUACOQC.trim().length() > 0)
			{
				msg = "Các sản phẩm sau chưa thiết lập quy cách: " + spCHUACOQC;
				//db.getConnection().rollback();
				return msg;
			}
			
			boolean tudexuatSOLO = false;
			System.out.println(":::: VI TRI BAM: " + vitriBAM);
			if( !vitriBAM.equals("benngoai") ) //Bấm chỗ duyệt ALL thì phải kiểm tra đã có chọn SỐ LÔ chưa, nếu chưa có thì phải tự động đề xuất
			{
				query = "delete ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' ";
				System.out.println(":::: BAM BEN TRONG - XOA DATA CU: " + vitriBAM);
				if(!db.update(query))
				{
					msg = "Lỗi khi duyệt: " + query;
					//db.getConnection().rollback();
					return msg;
				}
				
				//LUU VAO BANG CHI TIET
				for(int i = 0; i < spMa.length; i++)
				{
					if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						
						double totalCT = 0;
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							System.out.println("::::: KEY BEAN 22 ( " + spMa[i] + " ) :  " + key + " -- DANG XET: " + ( spMa[i] + "__" + spSCheme[i] ) );
							
							if(key.startsWith( spMa[i] + "__" + spSCheme[i] ) )
							{
								String[] _sp = key.split("__");
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								totalCT += Double.parseDouble(_soluongCT);
								
								if(spSCheme[i].trim().length() > 0 )
								{
									query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan, loai, scheme )  " +
											"select '" + this.id + "', pk_seq, a.dvdl_fk,  N'" + _sp[2] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '"+_sp[3]+"' as NgayHetHan, '1' as loai, N'" + spSCheme[i] + "'   " +
											"from SANPHAM a where MA = '" + spMa[i] + "'  ";
								}
								else
								{
									query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan, loai, scheme )  " +
											"select '" + this.id + "', pk_seq, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "' and sanpham_fk = a.pk_seq ),  N'" + _sp[2] + "' as solo, '" + _soluongCT.replaceAll(",", "") + "' as soluong, '"+_sp[3]+"' as NgayHetHan, '0' as loai, '' as scheme   " +
											"from SANPHAM a where MA = '" + spMa[i] + "'  ";
								}
								
								//System.out.println("::::: KEY BEAN INSERT 33 ( " + spMa[i] + " ) :  " + key );
								System.out.println("1.2.Insert DDH - SP - CT: " + query);
								if( db.updateReturnInt(query) <= 0 )
								{
									msg = "Khong the tao moi ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
									//db.getConnection().rollback();
									return msg;
								}
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						//System.out.println("+++ TONG SO LUONG CHI TIET: " + totalCT + "  -- SLG TREN WEB: " + spSoluong[i].replaceAll(",", "").trim() + "  -- CONVERT: " + Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) );
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							//db.getConnection().rollback();
							return msg;
						}
					}	
				}
			}
			else //KIỂM TRA XEM ĐÃ CHỌN SỐ LÔ CHƯA, NẾU CHƯA THÌ TỰ ĐỘNG ĐỀ XUẤT
			{
				query = " select count(dondathang_fk) as soDONG " + 
						" from ERP_DONDATHANGNPP_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' ";
				
				System.out.println(":::: CHECK DA DE XUAT LO CT CHUA: " + query );
				ResultSet rs = db.get(query);
				int soDong = 0;
				if(rs != null)
				{
					if(rs.next())
					{
						soDong = rs.getInt("soDONG");
					}
					rs.close();
				}
				
				if(soDong <= 0)
				{
					tudexuatSOLO = true;
					msg = this.tudongDEXUAT_SOLO(db, id, nhomkenh_fk, chungDVQL);
					if( msg.trim().length() > 0 )
					{
						//db.getConnection().rollback();
						return msg;
					}
				}
			}
			
			if( !tudexuatSOLO ) //Nếu không tự đề xuất số lô, thì mới trừ tồn kho ở bước này
			{
				query = " select c.npp_fk, c.NHOMKENH_FK, " +
						" c.kho_fk, a.sanpham_fk, b.ten as TEN, a.soluong as soluongDAT, a.solo, a.ngayhethan,  " +
						" case when a.dvdl_fk IS null then a.soluong      " +
						"			 when a.dvdl_fk = b.DVDL_FK then a.soluong     " +
						"			 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK )   end as soluong,  " +
						"	a.loai, a.scheme    " +
						" from ERP_DONDATHANGNPP_SANPHAM_CHITIET a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
						"		inner join ERP_DONDATHANGNPP c on  a.dondathang_fk = c.pk_seq inner join NHAPHANPHOI d on c.npp_fk = d.pk_seq " +
						"where a.dondathang_fk in ( " + this.id + " )   ";

				System.out.println("--CHECK TON KHO CHI TIET: " + query);

				ResultSet rs = db.get(query);
				if(rs != null)
				{
					while(rs.next())
					{
						String khoID = rs.getString("kho_fk");
						String kbhID = rs.getString("NHOMKENH_FK");
						String nppID = rs.getString("npp_fk");
						String spID = rs.getString("sanpham_fk");

						double soluong = rs.getDouble("soluong");
						String solo = rs.getString("solo");
						String ngayhethan = rs.getString("ngayhethan");

						double tonkho = 0;

						if(this.khKGId.trim().length() <= 0 )
						{
							query = "select AVAILABLE from NHAPP_KHO_CHITIET " +
									"where kho_fk = '" + khoID + "' and sanpham_fk = '" + spID + "' and NHOMKENH_FK = '" + kbhID + "' and npp_fk = '" + nppID + "' and solo = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
						}
						else
						{
							query = "select AVAILABLE from NHAPP_KHO_KYGUI_CHITIET " +
									"where kho_fk = '" + khoID + "' and sanpham_fk = '" + spID + "' and NHOMKENH_FK = '" + kbhID + "' and npp_fk = '" + nppID + "' and solo = '" + solo + "' and NgayHetHan='"+ngayhethan+"' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' ";
						}

						System.out.println("CHECK TON KHO SO LO ( " + solo + " ) : " + query);
						ResultSet rsTONKHO = db.get(query);
						if(rsTONKHO != null)
						{
							if(rsTONKHO.next())
								tonkho = rsTONKHO.getDouble("AVAILABLE");
							rsTONKHO.close();
						}

						if(soluong > tonkho)
						{
							msg = "Sản phẩm ( " + rs.getString("TEN") + " ), số lô ( " + rs.getString("solo") + " ) với số lượng yêu cầu ( " + rs.getString("soluong") + " ) không đủ tồn kho ( " + tonkho + " ). Vui lòng kiểm tra lại.";
							//db.getConnection().rollback();
							rs.close();
							return msg;
						}

						if( !chungDVQL )
						{
							if(this.khKGId.trim().length() <= 0 )
							{
								query = "Update NHAPP_KHO_CHITIET set booked = booked + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
										"where KHO_FK = '" + khoID + "' and NHOMKENH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
							}
							else
							{
								query = "Update NHAPP_KHO_KYGUI_CHITIET set booked = booked + '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
										"where KHO_FK = '" + khoID + "' and NHOMKENH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' ";
							}
						}
						else
						{
							if(this.khKGId.trim().length() <= 0 )
							{
								query = "Update NHAPP_KHO_CHITIET set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
										"where KHO_FK = '" + khoID + "' and NHOMKENH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' ";
							}
							else
							{
								query = "Update NHAPP_KHO_KYGUI_CHITIET set soluong = soluong - '" + soluong + "', AVAILABLE = AVAILABLE - '" + soluong + "' " +
										"where KHO_FK = '" + khoID + "' and NHOMKENH_FK = '" + kbhID + "' and NPP_FK = '" + nppID + "' and SANPHAM_FK = '" + spID + "' and SOLO = '" + solo + "' and NgayHetHan='"+ngayhethan+"' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' ";
							}
						}

						System.out.println(":::: CAP NHAT KHO: " + query );
						if( db.updateReturnInt(query) != 1 )
						{
							msg = "Khong the cap nhat NHAPP_KHO_CHITIET: " + query;
							//db.getConnection().rollback();
							rs.close();
							return msg;
						}				
					}
					rs.close();
				}
			}
			
			//CAP NHAT LAI COT SO LUONG CHUAN
			query =  "update a  "+
					 "set a.soluongCHUAN = a.soluong * ISNULL( dbo.LayQuyCach( a.SANPHAM_FK, null, a.DVDL_FK ), 0 ) "+
					 "from ERP_DONDATHANGNPP_SANPHAM_CHITIET a "+
					 "where a.dondathang_fk = '" + this.id + "' ";
			if( !db.update(query) )
			{
				msg = "Loi cap nhat ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
				return msg;
			}	
			
	
			//CHECK TONG PHAI BANG CHI TIET
			query = "select count(*) as soDONG   " +
					"from ERP_DONDATHANGNPP_SANPHAM tong left join   " +
					"	(  " +
					"		select sanpham_fk, sum(soluong) as soluong   " +
					"		from ERP_DONDATHANGNPP_SANPHAM_CHITIET  " +
					"		where  dondathang_fk = '" + this.id + "' and ltrim(rtrim(scheme)) = ''  " +
					"		group by sanpham_fk " +
					"	)  " +
					"	CT on tong.sanpham_fk = CT.sanpham_fk " +
					"where dondathang_fk = '" + this.id + "' and tong.soluong != isnull(CT.soluong, 0)  ";
			System.out.println("++++ CHECK LECH KHO: " + query);
			rsCHECK = db.get(query);
			int soDONG = 0;
			if(rsCHECK != null )
			{
				if( rsCHECK.next() )
				{
					soDONG = rsCHECK.getInt("soDONG");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				db.getConnection().rollback();
				return "11.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
			}
		} 
		catch (Exception e) {
			
			e.printStackTrace();
			return "Không thể duyệt lệnh xuất hàng " + e.getMessage();
		}
		
		return "";
	}
	
	private String tudongDEXUAT_SOLO(dbutils db, String id, String nhomkenh_fk, boolean chungDVQL)
	{
		String msg = "";
		
		try
		{
			//Tự động đề xuất kho hàng bán
			String query = 	 "select c.npp_fk, c.kho_fk as khoId, c.nhomkenh_fk as nhomkenhId, b.pk_seq as spId, b.ten as spTEN, sum(a.soluong) as soluong   "+
							 "from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.pk_seq inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq    "+
							 "where a.dondathang_fk in ( " + id + " )  "+
							 "group by c.npp_fk, c.kho_fk, c.nhomkenh_fk, b.pk_seq, b.ten "+
							 "having sum(a.soluong) > 0 ";
			System.out.println("---5.0.HANG BAN: " + query);
			ResultSet rsKHO = db.get(query);
			{
				while(rsKHO.next())
				{
					String khoId = rsKHO.getString("khoId");
					String spId = rsKHO.getString("spId");
					//String spTEN = rsKHO.getString("spTEN");
					double soluong = rsKHO.getDouble("soluong");
					String nppID = rsKHO.getString("npp_fk");

					//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
					if(this.khKGId.trim().length() <= 0 )
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN from NHAPP_KHO_CHITIET " +
								"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' order by NGAYHETHAN asc ";
					}
					else
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET " +
								"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' order by NGAYHETHAN asc  ";
					}
					
					System.out.println("---5.1.DE XUAT HANG BAN: " + query);
					ResultSet rs = db.get(query);
					double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG
					if(rs != null)
					{
						double totalLUONG = 0;
						boolean exit = false;
						while(rs.next() && !exit)
						{
							String ngayhethan = rs.getString("NGAYHETHAN");
							String solo = rs.getString("SOLO");
							
							totalLUONG += rs.getDouble("AVAILABLE");
							double soluongXUAT = 0;

							if(totalLUONG <= soluong)
							{
								soluongXUAT = rs.getDouble("AVAILABLE");
							}
							else
							{
								soluongXUAT = soluong - ( totalLUONG - rs.getDouble("AVAILABLE") );
								exit = true;
							}

							if(soluongXUAT > 0)
							{
								//CAP NHAT KHO CHI TIET
								if(this.khKGId.trim().length() <= 0 )
								{
									query = "UPDATE NHAPP_KHO_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
											"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
								}
								else
								{
									query = "UPDATE NHAPP_KHO_KYGUI_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
											"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
								}
								
								int kq = db.updateReturnInt(query);
								//	System.out.println("---UPDATE KHO CHI TIET TOI DAY: " + query + " -- KQ: " + kq);
								if(kq != 1 )
								{
									//db.getConnection().rollback();
									return "5.2.Lỗi khi chốt xuất kho: " + query;
								}

								//INSERT DONHANG - CHI TIET
								query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan, loai, scheme )  " +
										"select '" + this.id + "', pk_seq, ( select dvdl_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + this.id + "' and sanpham_fk = a.pk_seq ),  N'" + solo + "' as solo, '" + soluongXUAT + "' as soluong, '" + ngayhethan + "' as NgayHetHan, '0' as loai, N''   " +
										"from SANPHAM a where PK_SEQ = '" + spId + "'  ";

								System.out.println("---INSERT ERP_DONDATHANGNPP_SANPHAM_CHITIET HANG BAN: " + query);
								if(db.updateReturnInt(query)!=1 )
								{
									//db.getConnection().rollback();
									return "5.3.Lỗi khi chốt xuất kho: " + query;
								}

								tongluongxuatCT += soluongXUAT;
								if(exit)  //DA XUAT DU
								{
									//rs.close();
									break;
								}
							}
						}
						rs.close();
					}
					System.out.println(tongluongxuatCT + "____________" + soluong);
					if(tongluongxuatCT != soluong)
					{
						//db.getConnection().rollback();
						rsKHO.close();
						return "1.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
					}

				}
				rsKHO.close();
			}
			
			//HÀNG KHUYẾN MẠI
			query =  "select c.npp_fk, c.kho_fk as khoId, c.nhomkenh_fk as nhomkenhId, b.pk_seq as spId, b.ten as spTEN, d.SCHEME, sum(a.soluong) as soluong   "+
					 "from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA inner join ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq  "+
					 "		inner join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ   "+
					 "where a.DONDATHANGID in ( " + id + " )  "+
					 "group by c.npp_fk, c.kho_fk, c.nhomkenh_fk, b.pk_seq, b.ten, d.SCHEME "+
					 "having sum(a.soluong) > 0 ";
			System.out.println("---5.4.HANG KHUYEN MAI: " + query);
			rsKHO = db.get(query);
			{
				while(rsKHO.next())
				{
					String khoId = rsKHO.getString("khoId");
					String spId = rsKHO.getString("spId");
					String scheme = rsKHO.getString("SCHEME");
					double soluong = rsKHO.getDouble("soluong");
					String nppID = rsKHO.getString("npp_fk");

					//TU DE XUAT LO --> KHO CHI TIET THI VAN TRU SO LUONG + AVAI
					if(this.khKGId.trim().length() <= 0 )
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN from NHAPP_KHO_CHITIET " +
								"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' order by NGAYHETHAN asc  ";
					}
					else
					{
						query = "select AVAILABLE, SOLO, NGAYHETHAN from NHAPP_KHO_KYGUI_CHITIET " +
								"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' order by NGAYHETHAN asc  ";
					}

					System.out.println("---5.5.DE XUAT HANG KHUYEN MAI: " + query);
					ResultSet rs = db.get(query);
					double tongluongxuatCT = 0;  //PHAI BAT BUOC TONG LUONG XUAT O KHO CHI TIET PHAI BANG TONG LUONG XUAT O KHO TONG
					if(rs != null)
					{
						double totalLUONG = 0;
						boolean exit = false;
						while(rs.next() && !exit)
						{
							String ngayhethan = rs.getString("NGAYHETHAN");
							String solo = rs.getString("SOLO");

							totalLUONG += rs.getDouble("AVAILABLE");
							double soluongXUAT = 0;

							if(totalLUONG <= soluong)
							{
								soluongXUAT = rs.getDouble("AVAILABLE");
							}
							else
							{
								soluongXUAT = soluong - ( totalLUONG - rs.getDouble("AVAILABLE") );
								exit = true;
							}

							if(soluongXUAT > 0)
							{
								//CAP NHAT KHO CHI TIET
								if(this.khKGId.trim().length() <= 0 )
								{
									query = "UPDATE NHAPP_KHO_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
											"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
								}
								else
								{
									query = "UPDATE NHAPP_KHO_KYGUI_CHITIET  set AVAILABLE = AVAILABLE - '" + soluongXUAT + "', SOLUONG = SOLUONG - '" + soluongXUAT + "' " +
											"where AVAILABLE > 0 and kho_fk = '" + khoId + "' and sanpham_fk = '" + spId + "' and NHOMKENH_FK = '" + nhomkenh_fk + "' and npp_fk = '" + nppID + "' and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' and SOLO = '" + solo + "' and ngayhethan = '" + ngayhethan + "' ";
								}

								int kq = db.updateReturnInt(query);
								//	System.out.println("---UPDATE KHO CHI TIET TOI DAY: " + query + " -- KQ: " + kq);
								if(kq != 1 )
								{
									//db.getConnection().rollback();
									return "5.6.Lỗi khi chốt xuất kho: " + query;
								}

								//INSERT DONHANG - CHI TIET
								query = "insert ERP_DONDATHANGNPP_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, dvdl_fk, solo, soluong, ngayhethan, loai, scheme )  " +
										"select '" + this.id + "', pk_seq, a.dvdl_fk,  N'" + solo + "' as solo, '" + soluongXUAT + "' as soluong, '" + ngayhethan + "' as NgayHetHan, '1' as loai, N'" + scheme + "'   " +
										"from SANPHAM a where PK_SEQ = '" + spId + "'  ";
								System.out.println("---INSERT ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query);
								
								if(db.updateReturnInt(query)!=1 )
								{
									//db.getConnection().rollback();
									return "5.7.Lỗi khi chốt xuất kho: " + query;
								}

								tongluongxuatCT += soluongXUAT;
								if(exit)  //DA XUAT DU
								{
									//rs.close();
									break;
								}
							}
						}
						rs.close();
					}
					System.out.println(tongluongxuatCT + "____________" + soluong);
					if(tongluongxuatCT != soluong)
					{
						//db.getConnection().rollback();
						rsKHO.close();
						return "5.8.Lỗi hệ thống ( tổng xuất theo lô đề xuất đang bị sai ). Vui lòng liên hệ trung tâm để được hỗ trợ xử lý.";
					}

				}
				rsKHO.close();
			}
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Lỗi tự đề xuất: " + e.getMessage();
		}
		
		return msg;
	}

	private String TaoHoaDonTaiChinhNPP_TuDongTach(dbutils db, String id, String userId, String nppId, String khachhangID, String ngaydonhang, String donhangMUON, String loaidonhang, String congtyId, String nganhhang_fk, String tachHOAMYPHAM, String spId) 
	{
		String msg1 = "";
		try
		{
		 	String query = "";
		 	query = " update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
		 	if(!db.update(query))
		 	{
			   msg1 = "Không thể cập nhật thông tin NHANVIEN " + query;
			   return msg1;
		 	}
		 	
			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
			
			sohoadon="NA";
			kyhieuhoadon="NA";
			String mau = "1";
			
			String chuoi = "";
			long sohoadontu = 0;
			long sohoadonden = 0;
		
			
			// Lấy mẫu hóa đơn của Khách hàng dùng
			if( loaidonhang.equals("1") || loaidonhang.equals("2") || loaidonhang.equals("0") )
			{
				if( donhangMUON.equals("0") )
				{
					// CN HÀ NỘI && (CN HCM CÓ KH KHAI BÁO MẪU 2) DÙNG MẪU 2 (HO), CÒN LẠI DÙNG MẪU 1
					String query_kyhieu = " NV.KYHIEU ";				
					String query_sohdTU = " NV.SOHOADONTU ";	
					String query_sohdDEN = " NV.SOHOADONDEN ";	
					String query_mauhd = "1";
					String query_ngayhd = " NV.NGAYHOADON  ";
					
					// LAY TT KHAI BAO SO HD TRONG DLN
					query= " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
						   "        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
						   "        (select count(hd.pk_seq) as dem  "+
						   "         from ERP_XUATHOADONKM hd               "+
						   "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_OTC, \n" +
						   "        (select count(hd.pk_seq) as dem  "+
						   "         from ERP_HOADONNPP hd               "+
						   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) isSd_ETC \n" +
						   //" FROM NHANVIEN NV  \n" +
						   //" WHERE NV.pk_seq = '" + userId + "' \n";
						   " FROM NHANVIEN_SOHOADON NV  \n" +
					       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
					System.out.println("Câu check khai báo SHD "+query);
					ResultSet rsLayDL = db.get(query);
					
					int check_OTC = 0;
					int check_ETC = 0;
										
						while(rsLayDL.next())
						{
							kyhieuhoadon= rsLayDL.getString("kyhieu");
							sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
							sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
							ngayhoadon = rsLayDL.getString("ngayhoadon");
							if(ngayhoadon.trim().length() <= 0)  ngayhoadon = ngaydonhang;
							check_OTC = rsLayDL.getInt("isSd_OTC");
							check_ETC = rsLayDL.getInt("isSd_ETC");
						}
						rsLayDL.close();
					
					if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
					{
						msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
						return msg;
					}
								
					if(check_OTC <= 0 && check_ETC <= 0)
					{
						chuoi = ("000000" + sohoadontu);
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					}
					else
					{
						// LAY SOIN MAX TRONG OTC && ETC
						query= " SELECT  \n"+
							   "       (select max(cast(sohoadon as float)) as soin_max  "+
							   "        from ERP_XUATHOADONKM hd               "+
							   "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_OTC, \n" +
							   "       (select max(cast(sohoadon as float)) as soin_max "+
							   "        from ERP_HOADONNPP hd               "+
							   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1 ) soinMAX_ETC  \n" +
							   //" FROM NHANVIEN NV  \n" +
							   //" WHERE NV.pk_seq = '" + userId + "' \n";
							   " FROM NHANVIEN_SOHOADON NV  \n" +
						       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
						
						System.out.println("Câu lấy SHD Max: "+query);
						long soinMAX_OTC = 0;
					    long soinMAX_ETC = 0;
					    
						ResultSet laySOIN = db.get(query);						     
						while(laySOIN.next())
						{
							soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
							soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
						}
						laySOIN.close();
	
						if( soinMAX_OTC > soinMAX_ETC ) 
						{
							chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1) : "1" ));
						}
						else
						{
							chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
						}
						
						 chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
					}
					
					
					if(Integer.parseInt(chuoi) > sohoadonden )
					{ 
						//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
						query= " SELECT  \n"+
							   "      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
							   "       from ERP_HOADONNPP hd                                     \n"+
							   "       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
							   "             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
							   "             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1                               \n"+
							   "       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from ERP_HOADONNPP where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ AND ISNUMERIC(SOHOADON) = 1) \n"+
							   "       ) soinMAX_OTC 										  \n"+								  
							   //" FROM NHANVIEN NV   \n" +
							   //" WHERE NV.pk_seq = '" + userId + "' \n";
							   " FROM NHANVIEN_SOHOADON NV  \n" +
						       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
						
						System.out.println("Câu lấy HD không dùng "+query);
						ResultSet SoMAX_HD = db.get(query);
						String soinmax = "";
						while(SoMAX_HD.next())
						{
							soinmax = SoMAX_HD.getString("soinMAX_OTC")== null ? "" : SoMAX_HD.getString("soinMAX_OTC") ;
							chuoi = ("000000" + (SoMAX_HD.getLong("soinMAX_OTC")));
								
						} SoMAX_HD.close();
						
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
				  
						if(soinmax.trim().length() <= 0 )
						{
							/*msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
							return msg;*/
							
							chuoi = "NA";
						}
					}
					
					 sohoadon = chuoi ;
					 
					 System.out.println("sohoadon:"+sohoadon);
					
				    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
					query = " select ( select count(*) from ERP_XUATHOADONKM where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1 ) as KtraOTC, " +
							"        ( select count(*) from ERP_HOADONNPP where (SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1 ) as KtraETC, " +
							"        ( select count(*) from ERP_HOADONNPP where cast( sohoadon as float ) < cast( ISNULL( "+ sohoadon +", -1 ) as float) and NGAYXUATHD > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1 ) as is_KtraOTC_dk, \n"+
							"        ( select count(*) from ERP_XUATHOADONKM where cast( sohoadon as float ) < cast( ISNULL( "+ sohoadon +", -1 ) as float) and NGAYHOADON > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) = 1 ) as is_KtraETC_dk \n"+
							" from NHANVIEN where pk_seq = '" + userId + "' ";
					
					System.out.println("Câu kiểm tra lại SHD: "+query);
					ResultSet RsRs = db.get(query);
					int KT_OTC = 0;
					int KT_ETC = 0;
					
					int is_KT_OTC = 0;
					int is_KT_ETC = 0;

					
						while(RsRs.next())
						{
							KT_OTC = RsRs.getInt("KtraOTC") ;
							KT_ETC = RsRs.getInt("KtraETC") ;
							
							is_KT_OTC = RsRs.getInt("is_KtraOTC_dk") ;
							is_KT_ETC = RsRs.getInt("is_KtraETC_dk") ;

						}
					
					if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
					{
						//msg = "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
						msg = "Không thể duyệt đơn hàng: Số hoá đơn tiếp theo '"+sohoadon+"' đã có trên hệ thống! ";
						return msg;
					}
					
					if(is_KT_OTC >0 || is_KT_ETC > 0) // NẾU CÓ SỐ HÓA ĐƠN LỚN HƠN MÀ NGÀY XUẤT NHỎ HƠN CỦA HÓA ĐƠN TIẾP THEO THÌ K CHO
					{
						msg = "Không thể duyệt đơn hàng. Yêu cầu check lại ngày thiết lập số hóa đơn!";
						return msg;
					}
				}
				else
				{
					sohoadon = "NA";
					kyhieuhoadon = "";
				}
			}
			
			// LAY TIEN DE LUU
			double tienck= 0;
			double tienbvat= 0;
			double tienavat= 0;
			String nguoimua ="";
				
			query = " select (case when dh.khachhang_fk is not null then " +
					"                               (select isnull(chucuahieu,'') from KHACHHANG where pk_seq = dh.khachhang_fk ) " +
					"             else '' end ) as nguoimua  ," +
					"        dh_sp.chietkhau, dh_sp.bvat , (dh_sp.bvat + dh.Vat) as AVAT "+
					" from ERP_DONDATHANGNPP dh inner join  "+
					"	(select a.dondathang_fk, SUM(a.chietkhau)as chietkhau , sum(a.soluong * a.dongia - a.chietkhau) as bvat "+
					"	from ERP_DONDATHANGNPP_SANPHAM a   "+
					"	group by  a.dondathang_fk ) dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					" where dh.PK_SEQ = "+ id +"  ";
			
			ResultSet rsLayTien = db.get(query);
			{
				while(rsLayTien.next())
				{
					tienck = rsLayTien.getDouble("chietkhau");
					tienbvat = rsLayTien.getDouble("bvat");
					tienavat = rsLayTien.getDouble("avat");
					nguoimua =  rsLayTien.getString("nguoimua");
					
				}rsLayTien.close();
			}
		
			 query =   " insert ERP_HOADONNPP(LOAIHOADON, isKM, DDKD_FK, KBH_fK, KHO_FK, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt , \n" +
				       "        chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, nhanvien_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, nguoimua,  CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN, CHUYENSALES) \n" +
				       " SELECT 0 as LOAIHOADON, DH.isKM, DH.ddkd_Fk, DH.kbh_Fk, DH.kho_fk, '" + ngayhoadon + "', '1','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + kyhieuhoadon + "', \n" +
					   "       '" + sohoadon + "', N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"', \n" +
					   "       '" + this.vat.replaceAll(",", "") + "', N'Phiếu xuất hóa đơn từ động từ đơn hàng " + id + " ', DH.loaidonhang, '" + nppId + "', DH.khachhang_fk, DH.npp_dat_fk, DH.nhanvien_fk, " + mau + " \n" +
					   " 		, KH.TEN as tenkh \n" +
					   " 		, ISNULL( ISNULL( info.diachi, KH.DIACHI ), ISNULL( info2.diachi, NPP.DIACHI ) )  as diachikh \n" +
					   " 		, ISNULL( ISNULL( info.masothue, KH.MASOTHUE ), ISNULL( info2.masothue, NPP.MASOTHUE ) )  as mst " +
					   "		, ISNULL( ISNULL( info.donvi, '' ), ISNULL( info2.donvi, '' ) ) as tenxuatHD " + 
					   " 		, ISNULL( ISNULL( info.tennguoimua, '" + nguoimua + "' ), ISNULL( info2.tennguoimua, '" + nguoimua + "' ) ) as nguoimuahang, " + 
					   " 		'" + congtyId + "', KH.PK_SEQ, isnull(KH.GHICHU, '') GHICHU2, '"+ngayhoadon+"', DH.chuyenSALES \n"+
					   " FROM Erp_DonDatHangNPP DH left join KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ \n" +
					   " 		left join ( select top(1) khachhang_fk, tennguoimua, donvi, diachi, masothue  " + 
					   " 					from KHACHHANG_THONGTINHOADON where KHACHHANG_FK = ( select khachhang_fk from Erp_DonDatHangNPP where pk_seq = '" + id + "' ) order by ACTIVE desc ) info on DH.KHACHHANG_FK = info.khachhang_fk	 " +
					   "		left join NHAPHANPHOI NPP ON DH.NPP_DAT_FK = NPP.PK_SEQ	" +
					   " 		left join ( select top(1) npp_fk, tennguoimua, donvi, diachi, masothue  " + 
					   " 					from NPP_THONGTINHOADON where NPP_FK = ( select npp_dat_fk from Erp_DonDatHangNPP where pk_seq = '" + id + "' ) order by ACTIVE desc ) info2 on DH.NPP_DAT_FK = info2.npp_fk	 " + 
					   " WHERE DH.PK_SEQ = '"+ id +"' ";
								 
			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		
			
			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();

			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk)  values( "+ hdId +",  " + id + "  )";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}
			
			String conditionSP = " and c.pk_seq = '" + spId + "' ";
			
			//Nếu tachHOAMYPHAM = 0 nghĩa là trường hợp đơn hàng chỉ có 1 ngành hóa mỹ phẩm hoạc trong đơn hàng ko có hóa mỹ phẩm
			//if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() > 0 ) //Tách riêng HÓA MỸ PHẨM
				//conditionSP += " AND c.nganhhang_fk = '100005' ";
			//else if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() <= 0 ) //Những SP còn lại không thuộc HÓA MỸ PHẨM
				//conditionSP += " AND c.nganhhang_fk != '100005' ";
			//IN pdf và hiển thị trên giao diện: thứ tự như sau nha: sp, nguồn gốc, lot:.., date:...,Số VISA:..., NNK:..., NSX:…

			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME)    \n" + 
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, "+
					 
					 "   N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.TENXUATHOADON,''), '__') WHERE part!= ''  order by id desc), '') + "+
					 "	' , lot: ' + b.solo + ', Date: ' + " +
					 " 	(	case LEN (CAST(DATEPART(MM, b.NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
					 "		when 1 then '0'+ CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+
					 "		when 2 then CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+ 
					 "		end )   \n"+	
					 " 	  + (case when isnull(kh.insoVISA,0) = 1 then ', '+ N'Số VISA: '+ isnull(c.VISA,'') else '' end)  + "+
					 "	  	(case when isnull(kh.innhaNK,0) = 1 then N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'') +  "+	
					 "  	(case when isnull(kh.innhasx,0) = 1 then N', NSX: ' + isnull(c.NSX,'') else '' end) +''  else '' end)  as TEN, \n"+
					 "  	(select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,      \n" + 
					 "		b.soluong,  b.solo, b.NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat,     \n" + 
					 "		( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdId + "' and sanpham_fk = b.sanpham_fk and ltrim(rtrim(scheme)) = '' ) as dongia,     \n" + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then b.soluong         \n" + 
					 "		else b.soluong *      \n" + 
					 "		( select SOLUONG1 / SOLUONG2     \n" + 
					 "				from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as SoLuong_Chuan,     \n" + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA        \n" + 
					 "		else dhsp.DONGIA /      \n" + 
					 "		( select SOLUONG2 / SOLUONG1    \n" + 
					 "		from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,     \n" + 
					 "		dhsp.soluong as SoLuong_DatHang, b.scheme     \n" + 
					 "	from ERP_DONDATHANGNPP  a left join KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ   \n"+
					 "		 inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     \n" + 
					 "		 left join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = a.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk	     \n" + 
					 "		 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						     \n" + 
					 "		 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     \n" + 
					 "	where a.PK_SEQ = '" + id + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) = '' and a.TRANGTHAI != '3' " + conditionSP +
					 
				"union ALL \n" +
					 
				
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, "+	
					 
					 "   N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.TENXUATHOADON,''), '__') WHERE part!= ''  order by id desc), '') +"+
					 "	' , lot: ' + b.solo + ', Date: ' + " +
					 " 	(	case LEN (CAST(DATEPART(MM, b.NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
					 "		when 1 then '0'+ CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+
					 "		when 2 then CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+ 
					 "		end )   \n"+	
					 " 	  + (case when isnull(kh.insoVISA,0) = 1 then ', '+ N'Số VISA: '+ isnull(c.VISA,'') else '' end)  + "+
					 "	  	(case when isnull(kh.innhaNK,0) = 1 then N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'') +  "+	
					 "  	(case when isnull(kh.innhasx,0) = 1 then N', NSX: ' + isnull(c.NSX,'') else '' end) +''  else '' end)  as TEN, \n"+
					 "  d.DONVI, d.pk_seq as dvCHUAN, d.PK_SEQ  as dvDATHANG,      \n" + 
					 "		soluong, b.solo, b.NGAYHETHAN, 0 chietkhau, 0 thuevat,  0 as dongia,     \n" + 
					 "		soluong as SoLuong_Chuan,     \n" + 
					 "		0 as DonGia_Chuan ,     \n" + 
					 "		soluong SoLuong_DatHang, b.scheme     \n" + 
					 "	from ERP_DONDATHANGNPP a left join KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "		 inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     \n" + 
					 "		 inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ  						     \n" + 
					 "		 inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     \n" + 
					 "	where a.PK_SEQ = '" + id + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) != '' and a.TRANGTHAI != '3' " + conditionSP ;
			
			System.out.println("1.0.Insert ERP_HOADONNPP_SP_CHITIET: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}
			
			conditionSP = " and b.pk_seq = '" + spId + "' ";
			//if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() > 0 ) //Tách riêng HÓA MỸ PHẨM
			//	conditionSP += " AND b.nganhhang_fk = '100005' ";
			//else if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() <= 0 ) //Những SP còn lại không thuộc HÓA MỸ PHẨM
			//	conditionSP += " AND b.nganhhang_fk != '100005' ";
			
			query =  "insert ERP_HOADONNPP_SP ( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat)  \n" + 
					 "select DATA.hoadonId, DATA.PK_SEQ as spId, DATA.sanphamTEN, DATA.donvi, SUM(DATA.soluong) as soluong, SUM(DATA.soluong_chuan) as soluongCHUAN,  \n" + 
					 "	 DATA.dongia, SUM(DATA.soluong) * DATA.dongia as thanhtien, SUM( chietkhau ) as chietkhau, scheme, DATA.vat   \n" + 
					 "from \n" + 
					 "( \n" + 
					 "	select  '" + hdId + "'  as hoadonId, b.PK_SEQ, \n"+
					 " N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= '' order by id desc), '') + \n"+
					 " ' , ' + N'Lot:..., Date:.... ' +"+
					 "	(case when isnull(kh.insoVISA,0) = 1 then (', ' + N'Số VISA: '+  isnull(b.VISA,'')) else '' end)  +  \n"+
					 "	(case when isnull(kh.innhaNK,0) = 1 then (N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'')) +  \n"+	
					 "  (case when isnull(kh.innhasx,0) = 1 then (N', NSX: ' + isnull(b.NSX,'')) else '' end) +''  else '' end)  as sanphamTEN, \n"+					 
					 "		DV.donvi, a.soluong, \n" + 
					 "		case when b.DVDL_FK = a.dvdl_fk then a.soluong           \n" + 
					 " 			else a.soluong *        \n" + 
					 " 			( select SOLUONG1 / SOLUONG2       \n" + 
					 " 					 from QUYCACH where sanpham_fk = b.pk_seq and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) end  as SoLuong_Chuan,  \n" + 
					 "		a.dongia, isnull(a.chietkhau, 0) as chietkhau,  \n" + 
					 "		isnull(scheme, ' ') as scheme, isnull(a.thuevat,0) as vat      \n" + 
					 "	from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   	    \n" + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK     \n" + 
					 "		inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq       \n" + 
					 "		left join KHACHHANG kh on c.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "	where a.dondathang_fk in (  " + id + "  )  and a.soluong > 0   " + conditionSP +
					 ") \n" + 
					 "DATA \n" + 
					 "group by DATA.hoadonId, DATA.PK_SEQ, DATA.sanphamTEN, DATA.donvi, DATA.dongia , scheme, DATA.vat \n" +
					 
					 "union ALL \n" + 
					 
					 " select "+hdId+ ", b.PK_SEQ , \n"+
					 "		  N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= '' order by id desc), '') + \n"+  
					 "		  (case when isnull(kh.insoVISA,0) = 1 then (', ' +N'Số VISA: '+  isnull(b.VISA,'')) else '' end)  + \n"+
					 "		  (case when isnull(kh.innhaNK,0) = 1 then (N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'')) + \n"+ 
					 " 		  (case when isnull(kh.innhasx,0) = 1 then (N', NSX: ' + isnull(b.NSX,'')) else '' end) else '' end)  as TEN, DV.donvi, a.SOLUONG, a.souongCHUAN, a.dongia, a.thanhtien, a.chietkhau, a.scheme, a.vat \n"+						
					 " from  "+
					 " ("+
					 "		select "+hdId+" HOADON_FK, ISNULL( SUM(a.soluong), 0) as soluong, ISNULL( SUM(a.soluong), 0) as souongCHUAN, 0 as dongia, SUM(a.TONGGIATRI) as thanhtien, 0 as chietkhau, d.SCHEME as scheme, 0 as vat, c.KHACHHANG_FK ,a.SPMA  "+
					 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a left Join SanPham b  on a.SPMA = b.MA \n"+
					 "      INNER join  ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq \n"+
					 "		INNER join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ    \n" + 
					 "		where a.DONDATHANGID in ( " + id + " )  and a.soluong > 0 " + conditionSP +
					 "		group by  scheme, a.SPMA, c.KHACHHANG_FK \n"+
					 " ) a  \n"+
					 "	left join KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "  left join SanPham b on a.SPMA = b.MA \n"+
					 "  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK  \n";
					 
			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}
			
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg1 = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				return msg1;
			}
			
			//Cập nhật lại giá cho hóa đơn
			geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			msg1 = util.CapNhat_ThanhTien_HoaDon(db, hdId);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { hdId } );
			
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}
		
		return msg1;
	}
	
	private String TaoHoaDonTaiChinhNPP(dbutils db, String id, String userId, String nppId, String khachhangID, String ngaydonhang, String donhangMUON, String loaidonhang, String congtyId, String nganhhang_fk, String tachHOAMYPHAM, String thueVAT, String tachUutien, boolean choHangKM_VaoHMP) 
	{
		String msg1 = "";
		try
		{
		 	String query = "";
		 	query = " update NHANVIEN SET Active = '1' where pk_seq='"+userId+"'";
		 	if(!db.update(query))
		 	{
			   msg1 = "Không thể cập nhật thông tin NHANVIEN " + query;
			   return msg1;
		 	}
		 	
			String kyhieuhoadon="";
			String sohoadon="";
			String ngayhoadon = "";
			
			sohoadon="NA";
			kyhieuhoadon="NA";
			String mau = "1";
			
			String chuoi = "";
			long sohoadontu = 0;
			long sohoadonden = 0;
		
			
			// Lấy mẫu hóa đơn của Khách hàng dùng
			if( loaidonhang.equals("1") || loaidonhang.equals("2") || loaidonhang.equals("0") )
			{
				/*query =" select isnull( mauhoadon, 1 ) as mauhoadon from KHACHHANG where PK_SEQ ='"+khachhangID+"'";
				System.out.println("AAAAA:"+ query);
				ResultSet mauHDrs = db.get(query);
				
				if(mauHDrs != null)
				{
					while(mauHDrs.next())
					{
						mau = mauHDrs.getString("mauhoadon");
					}
					mauHDrs.close();
				}*/
				
				if( donhangMUON.equals("0") )
				{
					String query_kyhieu = " NV.KYHIEU ";				
					String query_sohdTU = " NV.SOHOADONTU ";	
					String query_sohdDEN = " NV.SOHOADONDEN ";	
					String query_mauhd = "1";
					String query_ngayhd = " NV.NGAYHOADON  ";
					
					// LAY TT KHAI BAO SO HD TRONG DLN
					query= " SELECT ISNULL("+ query_ngayhd +", '') as NGAYHOADON, (CASE WHEN ISNULL("+ query_kyhieu +",'-1') = '' THEN '-1' ELSE ISNULL("+ query_kyhieu +",'-1') END)  as KYHIEU, \n"+
						   "        ISNULL("+ query_sohdTU +", -1) AS SOHOADONTU, ISNULL("+ query_sohdDEN +", -1) AS SOHOADONDEN,  \n"+
						   "        (select count(hd.pk_seq) as dem  "+
						   "         from ERP_XUATHOADONKM hd               "+
						   "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1) isSd_OTC, \n" +
						   "        (select count( hd.pk_seq ) as dem  "+
						   "         from ERP_HOADONNPP hd               "+
						   "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
						   "               and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
						   "               and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1 ) isSd_ETC \n" +
/*						   "  		(SELECT COUNT ( HD.PK_SEQ ) AS DEM " +
						   "		 FROM ERP_HOADONNPP HD " +
						   "		 WHERE HD.TRANGTHAI != 3 AND HD.SOHOADON != 'NA' AND HD.MAUHOADON = "+query_mauhd+" AND HD.KYHIEU = ISNULL("+ query_kyhieu +",'-1') "+
						   "		 and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND HD.NGAYXUATHD < "+query_ngayhd+" ) isSoHdETC_Big, \n"+
						   "  		(SELECT COUNT ( HD.PK_SEQ ) AS DEM " +
						   "		 FROM ERP_XUATHOADONKM HD " +
						   "		 WHERE HD.TRANGTHAI != 2 AND HD.SOHOADON != 'NA' AND HD.MAUHOADON = "+query_mauhd+" AND HD.KYHIEU = ISNULL("+ query_kyhieu +",'-1') "+
						   "		 and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) and hd.NGUOISUA = NV.PK_SEQ AND HD.NgayHoaDon < "+query_ngayhd+" ) isSoHdOTC_Big \n"+*/
						  /* " , (SELECT  SUBSTRING( ( SELECT ','+C1.SOHOADON FROM ERP_HOADONNPP C1 WHERE C1.NGUOISUA = NV.PK_SEQ \n"+
					  	   " AND C1.TRANGTHAI != 3 AND C1.SOHOADON != 'NA' AND C1.MAUHOADON = 1 AND C1.KYHIEU = ISNULL( NV.KYHIEU ,'-1') 		 and cast(C1.sohoadon as int) >= cast(ISNULL( NV.SOHOADONTU , -1) as int) and C1.NGUOISUA = NV.PK_SEQ AND C1.NGAYXUATHD >  NV.NGAYHOADON \n"+ 
						   " FOR XML PATH ('') ), 2, 1000) CustomerList ) \n"+*/
									 
						   //" FROM NHANVIEN NV  \n" +
						   //" WHERE NV.pk_seq = '" + userId + "' \n";
						   " FROM NHANVIEN_SOHOADON NV  \n" +
					       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
					System.out.println("Câu check khai báo SHD 111"+query);
					ResultSet rsLayDL = db.get(query);
					
					int check_OTC = 0;
					int check_ETC = 0;
										
						while(rsLayDL.next())
						{
							kyhieuhoadon= rsLayDL.getString("kyhieu");
							sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadontu") ;
							sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1 : rsLayDL.getLong("sohoadonden") ;;
							ngayhoadon = rsLayDL.getString("ngayhoadon");
							if(ngayhoadon.trim().length() <= 0)  ngayhoadon = ngaydonhang;
							check_OTC = rsLayDL.getInt("isSd_OTC");
							check_ETC = rsLayDL.getInt("isSd_ETC");
						}
						rsLayDL.close();
					
					if(kyhieuhoadon.equals("-1") || sohoadontu == -1 || sohoadonden == -1 )
					{
						msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
						return msg;
					}
										
					if(check_OTC <= 0 && check_ETC <= 0)
					{
						chuoi = ("000000" + sohoadontu);
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
					}
					else
					{
						// LAY SOIN MAX TRONG OTC && ETC
						query= " SELECT  \n"+
							   "       (select max(cast(sohoadon as float)) as soin_max  "+
							   "        from ERP_XUATHOADONKM hd               "+
							   "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1 ) soinMAX_OTC, \n" +
							   "       (select max(cast(sohoadon as float)) as soin_max "+
							   "        from ERP_HOADONNPP hd               "+
							   "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  "+
							   "              and cast(hd.sohoadon as int) >= cast(ISNULL("+ query_sohdTU +", -1) as int) "+
							   "              and cast(hd.sohoadon as int) <= cast(ISNULL("+ query_sohdDEN +", -1) as int) and hd.nguoisua = NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1 ) soinMAX_ETC  \n" +
							   //" FROM NHANVIEN NV  \n" +
							   //" WHERE NV.pk_seq = '" + userId + "' \n";
							   " FROM NHANVIEN_SOHOADON NV  \n" +
						       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
						
						System.out.println("Câu lấy SHD Max: "+query);
						long soinMAX_OTC = 0;
					    long soinMAX_ETC = 0;
					    
						ResultSet laySOIN = db.get(query);						     
						while(laySOIN.next())
						{
							soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
							soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
						}
						laySOIN.close();
	
						if( soinMAX_OTC > soinMAX_ETC ) 
						{
							chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1) : "1" ));
						}
						else
						{
							chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1) : "1"));
						}
						
						 chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length()).trim();
					}
					
					
					if(Integer.parseInt(chuoi) > sohoadonden )
					{ 
						//CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN CON SHD THI TU DONG LAY SO DO
						query= " SELECT  \n"+
							   "      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"+
							   "       from ERP_HOADONNPP hd                                     \n"+
							   "       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "+ query_mauhd +" and hd.kyhieu = ISNULL("+ query_kyhieu +",'-1')  \n"+
							   "             and cast(hd.sohoadon as float) >= cast(ISNULL("+ query_sohdTU +", -1) as float)                                 \n"+
							   "             and cast(hd.sohoadon as float) <= cast(ISNULL("+ query_sohdDEN +", -1) as float)  and hd.nguoisua = NV.PK_SEQ    AND ISNUMERIC(SOHOADON) =1                           \n"+
							   "       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from ERP_HOADONNPP where trangthai!= 3 and  kyhieu = ISNULL("+ query_kyhieu +",'-1')  and nguoisua= NV.PK_SEQ AND ISNUMERIC(SOHOADON) =1) \n"+
							   "       ) soinMAX_OTC 										  \n"+								  
							   //" FROM NHANVIEN NV   \n" +
							   //" WHERE NV.pk_seq = '" + userId + "' \n";
							   " FROM NHANVIEN_SOHOADON NV  \n" +
						       " WHERE NV.nhanvien_fk = '" + userId + "' and nv.npp_fk = '" + this.nppId + "' \n";
						
						System.out.println("Câu lấy HD không dùng "+query);
						ResultSet SoMAX_HD = db.get(query);
						String soinmax = "";
						while(SoMAX_HD.next())
						{
							soinmax = SoMAX_HD.getString("soinMAX_OTC")== null ? "" : SoMAX_HD.getString("soinMAX_OTC") ;
							chuoi = ("000000" + (SoMAX_HD.getLong("soinMAX_OTC")));
								
						} SoMAX_HD.close();
						
						chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());
				  
						if(soinmax.trim().length() <= 0 )
						{
							chuoi = "NA";
							/*msg = "Số hóa đơn tiếp theo đã vượt quá Số hóa đơn đến (" + sohoadonden + ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";
							return msg;*/
						}
					}
					
					 sohoadon = chuoi ;
					
					 System.out.println("sohoadon:"+sohoadon);
				    // KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO HIEN TAI TRONG HD O & E 
					query = " select ( select count(*) from ERP_XUATHOADONKM where ( SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '2' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) =1) as KtraOTC, " +
							"        ( select count(*) from ERP_HOADONNPP where ( SOHOADON = '" + sohoadon + "' ) and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) =1 ) as KtraETC, " +
							"        ( select count(*) from ERP_HOADONNPP where cast( sohoadon as float ) < cast( ISNULL( '"+ sohoadon +"', -1 ) as float) and NGAYXUATHD > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) =1) as is_KtraOTC_dk, \n"+
							"        ( select count(*) from ERP_XUATHOADONKM where cast( sohoadon as float ) < cast( ISNULL( '"+ sohoadon +"', -1 ) as float) and NGAYHOADON > '"+ngayhoadon+"' and kyhieu = '" + kyhieuhoadon + "' and trangthai != '3' and npp_fk = '" + nppId + "' and sohoadon != 'NA' and mauhoadon = "+ query_mauhd +" AND ISNUMERIC(SOHOADON) =1 ) as is_KtraETC_dk \n"+
							" from NHANVIEN where pk_seq = '" + userId + "' ";
					
					System.out.println("Câu kiểm tra lại SHD: "+query);
					ResultSet RsRs = db.get(query);
					int KT_OTC = 0;
					int KT_ETC = 0;
					
					int is_KT_OTC = 0;
					int is_KT_ETC = 0;
					
						while(RsRs.next())
						{
							KT_OTC = RsRs.getInt("KtraOTC") ;
							KT_ETC = RsRs.getInt("KtraETC") ;
							
							is_KT_OTC = RsRs.getInt("is_KtraOTC_dk") ;
							is_KT_ETC = RsRs.getInt("is_KtraETC_dk") ;
						}
					
					if(KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI TẠO
					{
						msg = "Không thể duyệt đơn hàng: Số hoá đơn tiếp theo '"+sohoadon+"' đã có trên hệ thống! ";
						return msg;
					}
					
					if(is_KT_OTC >0 || is_KT_ETC > 0) // NẾU CÓ SỐ HÓA ĐƠN LỚN HƠN MÀ NGÀY XUẤT NHỎ HƠN CỦA HÓA ĐƠN TIẾP THEO THÌ K CHO
					{
						msg = "Không thể duyệt đơn hàng. Yêu cầu check lại ngày thiết lập số hóa đơn!";
						return msg;
					}
				}
				else
				{
					sohoadon = "NA";
					kyhieuhoadon = "";
				}
			}
			
			
			// LAY TIEN DE LUU
			double tienck= 0;
			double tienbvat= 0;
			double tienavat= 0;
			String nguoimua ="";
				
			query = " select (case when dh.khachhang_fk is not null then " +
					"        (select isnull(chucuahieu,'') from KHACHHANG where pk_seq = dh.khachhang_fk ) " +
					"             else '' end ) as nguoimua  ," +
					"        dh_sp.chietkhau, dh_sp.bvat , (dh_sp.bvat + dh.Vat) as AVAT, "+
					"		 DATEDIFF( DD, ngaydonhang, '" + ngayhoadon + "' ) as chenhLECH	" +
					" from ERP_DONDATHANGNPP dh inner join  "+
					"	(select a.dondathang_fk, SUM(a.chietkhau)as chietkhau , sum(a.soluong * a.dongia - a.chietkhau) as bvat "+
					"	from ERP_DONDATHANGNPP_SANPHAM a   "+
					"	group by  a.dondathang_fk ) dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk "+
					" where dh.PK_SEQ = "+ id +"  ";
			
			System.out.println(":::CHECK NGAY: " + query);
			boolean checkNGAY = false;
			ResultSet rsLayTien = db.get(query);
			{
				while(rsLayTien.next())
				{
					tienck = rsLayTien.getDouble("chietkhau");
					tienbvat = rsLayTien.getDouble("bvat");
					tienavat = rsLayTien.getDouble("avat");
					nguoimua =  rsLayTien.getString("nguoimua");
					
					if( rsLayTien.getInt("chenhLECH") < 0 )
						checkNGAY = true;
				}
				rsLayTien.close();
			}
			
			//CHECK NGÀY HÓA ĐƠN KHÔNG ĐƯỢC NHỎ HƠN NGÀY ĐƠN HÀNG
			if( checkNGAY && ngayhoadon.trim().length() > 0 )
			{
				msg1 = "Ngày hóa đơn không được nhỏ hơn ngày đơn hàng";
				return msg1;
			}
		
			 query =   " insert ERP_HOADONNPP(LOAIHOADON, isKM, DDKD_FK, KBH_fK, KHO_FK, ngayxuatHD, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, kyhieu, sohoadon, hinhthuctt , \n" +
				       "        chietkhau, tongtienbvat, tongtienavat, vat, ghichu, loaixuathd, npp_fk, khachhang_fk, npp_dat_fk, nhanvien_fk, mauhoadon, TENKHACHHANG, DIACHI, MASOTHUE, TENXUATHD, nguoimua,  CONGTY_FK, KHGHINO, GHICHU2, NGAYGHINHAN, CHUYENSALES) \n" +
				       " SELECT 0 as LOAIHOADON, DH.isKM, DH.ddkd_Fk, DH.kbh_Fk, DH.kho_fk, '" + ngayhoadon + "', '1','" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + kyhieuhoadon + "', \n" +
					   "       '" + sohoadon + "', N'TM/CK' , '"+ tienck  +"', '"+ tienbvat +"', '"+ tienavat  +"', \n" +
					   "       '" + this.vat.replaceAll(",", "") + "', N'Phiếu xuất hóa đơn từ động từ đơn hàng " + id + " ', DH.loaidonhang, '" + nppId + "', DH.khachhang_fk, DH.npp_dat_fk, DH.nhanvien_fk, " + mau + " \n" +
					   " 		, ISNULL(KH.TEN, NPP.TEN) as tenkh \n" +
					   " 		, CASE WHEN DH.KHACHHANG_FK IS NOT NULL THEN ISNULL( info.diachi, KH.DIACHI ) \n"+
					   "		  WHEN DH.NPP_DAT_FK IS NOT NULL THEN  ISNULL( info2.diachi, NPP.DIACHI ) \n"+
					   "		  ELSE ' ' END  as diachikh \n" +
					   " 		, CASE WHEN DH.KHACHHANG_FK IS NOT NULL THEN ISNULL( info.masothue, KH.MASOTHUE ) \n"+
					   "		  WHEN DH.NPP_DAT_FK IS NOT NULL THEN  ISNULL( info2.masothue, NPP.MASOTHUE) \n"+
					   "		  ELSE ' ' END as mst  \n" +
					   "		, CASE WHEN DH.KHACHHANG_FK IS NOT NULL THEN ISNULL( info.donvi, '' ) \n"+
					   "		  WHEN DH.NPP_DAT_FK IS NOT NULL THEN   ISNULL( info2.donvi, '' ) \n"+
					   "		  ELSE ' ' END as tenxuatHD  \n" + 
					   " 		,  CASE WHEN DH.KHACHHANG_FK IS NOT NULL THEN ISNULL( info.tennguoimua, '' ) \n"+
					   "		  WHEN DH.NPP_DAT_FK IS NOT NULL THEN   ISNULL( info2.tennguoimua, '' ) \n"+
					   "		 ELSE ' ' END as nguoimuahang, " + 
					   " 		'" + congtyId + "', KH.PK_SEQ, isnull(KH.GHICHU, '') GHICHU2, '"+ngayhoadon+"', DH.chuyenSALES \n"+
					   " FROM Erp_DonDatHangNPP DH left join KHACHHANG KH ON DH.KHACHHANG_FK = KH.PK_SEQ \n" +
					   " 		left join ( select top(1) khachhang_fk, tennguoimua, donvi, diachi, masothue  " + 
					   " 					from KHACHHANG_THONGTINHOADON where KHACHHANG_FK = ( select khachhang_fk from Erp_DonDatHangNPP where pk_seq = '" + id + "' ) order by ACTIVE desc ) info on DH.KHACHHANG_FK = info.khachhang_fk	 " +
					   "		left join NHAPHANPHOI NPP ON DH.NPP_DAT_FK = NPP.PK_SEQ	" +
					   " 		left join ( select top(1) npp_fk, tennguoimua, donvi, diachi, masothue  " + 
					   " 					from NPP_THONGTINHOADON where NPP_FK = ( select npp_dat_fk from Erp_DonDatHangNPP where pk_seq = '" + id + "' ) order by PK_SEQ DESC, ACTIVE desc ) info2 on DH.NPP_DAT_FK = info2.npp_fk	 " + 
					   " WHERE DH.PK_SEQ = '"+ id +"' ";
								 
			System.out.println("1.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP " + query;
				return msg1;
			}		
			
			String hdId = "";
			query = "select scope_identity() as hdId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			hdId = rs1.getString("hdId");
			rs1.close();

			query = "Insert ERP_HOADONNPP_DDH(hoadonnpp_fk, ddh_fk)  values( "+ hdId +",  " + id + "  )";
			System.out.println("2.Insert ERP_HOADONNPP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_DDH " + query;
				return msg1;
			}
			
			String conditionSP = "";
			String conditionKM = "";
			
			//Nếu tachHOAMYPHAM = 0 nghĩa là trường hợp đơn hàng chỉ có 1 ngành hóa mỹ phẩm hoạc trong đơn hàng ko có hóa mỹ phẩm
			if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() > 0 ) //Tách riêng HÓA MỸ PHẨM
			{
				conditionSP += " AND c.nganhhang_fk = '100005' ";

				if( !choHangKM_VaoHMP )
					conditionKM += " AND c.nganhhang_fk = '100005' ";
			}
			else if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() <= 0 ) //Những SP còn lại không thuộc HÓA MỸ PHẨM
			{
				conditionSP += " AND c.nganhhang_fk != '100005' ";
				conditionKM += " AND c.nganhhang_fk != '100005' ";
			}
			
			//IN pdf và hiển thị trên giao diện: thứ tự như sau nha: sp, nguồn gốc, lot:.., date:...,Số VISA:..., NNK:..., NSX:…

			//TACH THEO VAT NEU CO TRUYEN VAO
			if( thueVAT.equals("5") )
			{
				conditionSP += " AND c.thuexuat = '5' ";
				
				if( tachUutien.equals("5") ) //đưa hàng KM 10% vào hóa đơn VAT 5% ( trường hợp hàng bán ko có hàng nào VAT 10% )
					conditionKM += " AND (  c.thuexuat = '5'  or ( dbo.trim( b.scheme ) != '' and c.thuexuat = '10' )  ) ";
				else
				{
					if( !choHangKM_VaoHMP )
						conditionKM += " AND c.thuexuat = '5' ";
				}
			}
			else if( thueVAT.equals("10") )
			{
				conditionSP += " AND c.thuexuat = '10' ";
				
				if( tachUutien.equals("10") ) //đưa hàng KM 5% vào hóa đơn VAT 10% ( trường hợp hàng bán ko có hàng nào VAT 5% )
					conditionKM += " AND (  c.thuexuat = '10'  or ( dbo.trim( b.scheme ) != '' and c.thuexuat = '5' )  ) ";
				else
				{
					if( !choHangKM_VaoHMP )
						conditionKM += " AND c.thuexuat = '10' ";
				}
			}
			
			query = "insert ERP_HOADONNPP_SP_CHITIET(hoadon_fk, donhang_fk, KBH_FK, Kho_FK, MA, TEN, DONVI, DVCHUAN, DVDATHANG, SOLUONG, SOLO, NGAYHETHAN, CHIETKHAU, THUEVAT, DONGIA, SoLuong_Chuan, DonGia_Chuan, SoLuong_DatHang, SCHEME)    \n" + 
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, "+
					 "   N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.TENXUATHOADON,''), '__') WHERE part!= ''  order by id desc), '') + "+
					 "	' , lot: ' + b.solo + ', Date: ' + " +
					 " 	(	case LEN (CAST(DATEPART(MM, b.NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
					 "		when 1 then '0'+ CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+
					 "		when 2 then CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+ 
					 "		end )   \n"+	
					 " 	  + (case when isnull(kh.insoVISA,0) = 1 then ', '+ N'Số VISA: '+ isnull(c.VISA,'') else '' end)  + "+
					 "	  (case when isnull(kh.innhaNK,0) = 1 then N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'') +  "+	
					 "  ( case when isnull(kh.innhasx,0) = 1 then N', NSX: ' + isnull(c.NSX,'') else '' end) +''  else '' end)  as TEN, \n"+
					 "  (select donvi from DONVIDOLUONG where pk_seq = dhsp.dvdl_fk ) as donvi, d.pk_seq as dvCHUAN, dhsp.dvdl_fk  as dvDATHANG,      \n" + 
					 "		b.soluong,  b.solo, b.NGAYHETHAN, dhsp.chietkhau, dhsp.thuevat,     \n" + 
					 "		( select dongia from ERP_HOADONNPP_SP where hoadon_fk = '" + hdId + "' and sanpham_fk = b.sanpham_fk and ltrim(rtrim(scheme)) = '' ) as dongia,     \n" + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then b.soluong         \n" + 
					 "		else b.soluong *      \n" + 
					 "		( select SOLUONG1 / SOLUONG2     \n" + 
					 "				from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as SoLuong_Chuan,     \n" + 
					 "		case when d.pk_seq = dhsp.dvdl_fk then dhsp.DONGIA        \n" + 
					 "		else dhsp.DONGIA /      \n" + 
					 "		( select SOLUONG2 / SOLUONG1    \n" + 
					 "		from QUYCACH where sanpham_fk = c.pk_seq and DVDL2_FK = dhsp.dvdl_fk and DVDL1_FK = d.pk_seq ) end as DonGia_Chuan ,     \n" + 
					 "		dhsp.soluong as SoLuong_DatHang, b.scheme     \n" + 
					 "	from ERP_DONDATHANGNPP  a left join KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ   \n"+
					 "		 inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     \n" + 
					 "		 left join ERP_DONDATHANGNPP_SANPHAM dhsp on dhsp.dondathang_fk = a.PK_SEQ and dhsp.sanpham_fk = b.sanpham_fk	     \n" + 
					 "		 inner join SANPHAM c on dhsp.sanpham_fk = c.PK_SEQ  						     \n" + 
					 "		 left join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     \n" + 
					 "	where a.PK_SEQ = '" + id + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) = '' and a.TRANGTHAI != '3' " + conditionSP +
					 
					 "union ALL \n" +  //HÀNG KM không có giá nên phải tách riêng
					 
					 "	select '" + hdId + "' as hoadon_fk, a.pk_seq as donhang_fk, a.KBH_FK, a.KHO_FK, c.MA, "+					 
					 "   N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.TENXUATHOADON,''), '__') WHERE part!= ''  order by id desc), '') +"+
					 "	' , lot: ' + b.solo + ', Date: ' + " +
					 " 	(	case LEN (CAST(DATEPART(MM, b.NGAYHETHAN ) AS NVARCHAR(50))) \n"+ 
					 "		when 1 then '0'+ CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+
					 "		when 2 then CAST(DATEPART(MM, b.NGAYHETHAN) AS NVARCHAR(50))  + '/' + CAST(DATEPART(YYYY, b.NGAYHETHAN) AS NVARCHAR(50)) \n"+ 
					 "		end )   \n"+	
					 " 	  + (case when isnull(kh.insoVISA,0) = 1 then ', '+ N'Số VISA: '+ isnull(c.VISA,'') else '' end)  + "+
					 "	  (case when isnull(kh.innhaNK,0) = 1 then N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(c.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'') +  "+	
					 "  ( case when isnull(kh.innhasx,0) = 1 then N', NSX: ' + isnull(c.NSX,'') else '' end) +''  else '' end)  as TEN, \n"+
					 "  d.DONVI, d.pk_seq as dvCHUAN, d.PK_SEQ  as dvDATHANG,      \n" + 
					 "		soluong, b.solo, b.NGAYHETHAN, 0 chietkhau, 0 thuevat,  0 as dongia,     \n" + 
					 "		soluong as SoLuong_Chuan,     \n" + 
					 "		0 as DonGia_Chuan ,     \n" + 
					 "		soluong SoLuong_DatHang, b.scheme     \n" + 
					 "	from ERP_DONDATHANGNPP a left join KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "		 inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.pk_seq = b.dondathang_fk	  						     \n" + 
					 "		 inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ  						     \n" + 
					 "		 inner join DONVIDOLUONG d on d.PK_SEQ = c.dvdl_fk 	     \n" + 
					 "	where a.PK_SEQ = '" + id + "' and b.soluong > 0 and ltrim(rtrim(b.scheme)) != '' and a.TRANGTHAI != '3' " + conditionKM ;
			
			System.out.println("1.0.Insert ERP_HOADONNPP_SP_CHITIET: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Không thể tạo mới ERP_HOADONNPP_SP_CHITIET " + query;
				return msg1;
			}
			
			conditionSP = "";
			conditionKM = "";
			if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() > 0 ) //Tách riêng HÓA MỸ PHẨM
			{
				conditionSP += " AND b.nganhhang_fk = '100005' ";
				
				if( !choHangKM_VaoHMP )
					conditionKM += " AND b.nganhhang_fk = '100005' ";
			}
			else if( tachHOAMYPHAM.equals("1") && nganhhang_fk.trim().length() <= 0 ) //Những SP còn lại không thuộc HÓA MỸ PHẨM
			{
				conditionSP += " AND b.nganhhang_fk != '100005' ";
				conditionKM += " AND b.nganhhang_fk != '100005' ";
			}
			
			if( thueVAT.equals("5") )
			{
				conditionSP += " AND b.thuexuat = '5' ";
				
				if( tachUutien.equals("5") ) //đưa hàng KM 10% vào hóa đơn VAT 5% ( trường hợp hàng bán ko có hàng nào VAT 10% )
					conditionKM += " AND (  b.thuexuat = '5'  or ( dbo.trim( d.scheme ) != '' and b.thuexuat = '10' )  ) ";
				else
				{
					if( !choHangKM_VaoHMP )
						conditionKM += " AND b.thuexuat = '5' ";
				}
			}
			else if( thueVAT.equals("10") )
			{
				conditionSP += " AND b.thuexuat = '10' ";
				
				if( tachUutien.equals("10") ) //đưa hàng KM 5% vào hóa đơn VAT 10% ( trường hợp hàng bán ko có hàng nào VAT 5% )
					conditionKM += " AND (  b.thuexuat = '10'  or ( dbo.trim( d.scheme ) != '' and b.thuexuat = '5' )  ) ";
				else
				{
					if( !choHangKM_VaoHMP )
						conditionKM += " AND b.thuexuat = '10' ";
				}
			}
			
			query =  "insert ERP_HOADONNPP_SP ( hoadon_fk, sanpham_fk, sanphamTEN, donvitinh, soluong, soluong_chuan, dongia, thanhtien, chietkhau, scheme , vat)  \n" + 
					 "select DATA.hoadonId, DATA.PK_SEQ as spId, DATA.sanphamTEN, DATA.donvi, SUM(DATA.soluong) as soluong, SUM(DATA.soluong_chuan) as soluongCHUAN,  \n" + 
					 "	 DATA.dongia, SUM(DATA.soluong) * DATA.dongia as thanhtien, SUM( chietkhau ) as chietkhau, scheme, DATA.vat   \n" + 
					 "from \n" + 
					 "( \n" + 
					 "	select  '" + hdId + "'  as hoadonId, b.PK_SEQ, \n"+
					 " N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= '' order by id desc), '') + \n"+
					 " ' , ' + N'Lot:..., Date:.... ' +"+
					 "	(case when isnull(kh.insoVISA,0) = 1 then (', ' + N'Số VISA: '+  isnull(b.VISA,'')) else '' end)  +  \n"+
					 "	(case when isnull(kh.innhaNK,0) = 1 then (N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'')) +  \n"+	
					 "  (case when isnull(kh.innhasx,0) = 1 then (N', NSX: ' + isnull(b.NSX,'')) else '' end) +''  else '' end)  as sanphamTEN, \n"+					 
					 "		DV.donvi, a.soluong, \n" + 
					 "		case when b.DVDL_FK = a.dvdl_fk then a.soluong           \n" + 
					 " 			else a.soluong *        \n" + 
					 " 			( select SOLUONG1 / SOLUONG2       \n" + 
					 " 					 from QUYCACH where sanpham_fk = b.pk_seq and DVDL2_FK = a.dvdl_fk and DVDL1_FK = b.DVDL_FK ) end  as SoLuong_Chuan,  \n" + 
					 "		a.dongia, isnull(a.chietkhau, 0) as chietkhau,  \n" + 
					 "		isnull(scheme, ' ') as scheme, isnull(a.thuevat,0) as vat      \n" + 
					 "	from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ   	    \n" + 
					 "		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK     \n" + 
					 "		inner join  ERP_DONDATHANGNPP c on a.dondathang_fk=c.pk_seq       \n" + 
					 "		left join KHACHHANG kh on c.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "	where a.dondathang_fk in (  " + id + "  )  and a.soluong > 0   " + conditionSP +
					 ") \n" + 
					 "DATA \n" + 
					 "group by DATA.hoadonId, DATA.PK_SEQ, DATA.sanphamTEN, DATA.donvi, DATA.dongia , scheme, DATA.vat \n" +
					 
					 "union ALL \n" + 
					 
					 " select "+hdId+ ", b.PK_SEQ , \n"+
					 "		  N''+ isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.TENXUATHOADON,''), '__') WHERE part!= '' order by id desc), '') + \n"+  
					 " 		  ' , ' + N'Lot:..., Date:.... ' +"+
					 "		  (case when isnull(kh.insoVISA,0) = 1 then (', ' +N'Số VISA: '+  isnull(b.VISA,'')) else '' end)  + \n"+
					 "		  (case when isnull(kh.innhaNK,0) = 1 then (N', NNK: ' + isnull((SELECT  top(1)  part  FROM SplitString (isnull(b.nguongocnhapkhau,''), '__') WHERE part!= '' order by id desc),'')) + \n"+ 
					 " 		  (case when isnull(kh.innhasx,0) = 1 then (N', NSX: ' + isnull(b.NSX,'')) else '' end) else '' end)  as TEN, DV.donvi, a.SOLUONG, a.souongCHUAN, a.dongia, a.thanhtien, a.chietkhau, a.scheme, a.vat \n"+						
					 " from  "+
					 " ("+
					 "		select "+hdId+" HOADON_FK, ISNULL( SUM(a.soluong), 0) as soluong, ISNULL( SUM(a.soluong), 0) as souongCHUAN, 0 as dongia, SUM(a.TONGGIATRI) as thanhtien, 0 as chietkhau, d.SCHEME as scheme, 0 as vat, c.KHACHHANG_FK ,a.SPMA  "+
					 "		from ERP_DONDATHANGNPP_CTKM_TRAKM a left Join SanPham b  on a.SPMA = b.MA \n"+
					 "      INNER join  ERP_DONDATHANGNPP c on a.DONDATHANGID = c.pk_seq \n"+
					 "		INNER join CTKHUYENMAI d on a.CTKMID = d.PK_SEQ    \n" + 
					 "		where a.DONDATHANGID in ( " + id + " )  and a.soluong > 0 " + conditionKM +
					 "		group by  scheme, a.SPMA, c.KHACHHANG_FK \n"+
					 " ) a  \n"+
					 "	left join KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+
					 "  left join SanPham b on a.SPMA = b.MA \n"+
					 "  INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK  \n";

			System.out.println("1.1.Insert ERP_HOADONNPP_SP: " + query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}
			
			//CAP NHAT LAI CAC COT TIEN TRONG HOA DON
			/*query = "update hd	 "+
					 "	set hd.DONGIA = dh_sp.dongiaGOC, "+
					 "		hd.chietkhau_chuan = dh_sp.chietkhauCHUAN, "+
					 "		hd.CHIETKHAU = hd.SOLUONG * dh_sp.chietkhauCHUAN "+
					 "from ERP_HOADONNPP_SP hd inner join  "+
					 "	( "+
					 "		select sanpham_fk, dongia, dongiaGOC, isnull(chietkhau_DLN, 0) + isnull(chietkhau_KM, 0) + ISNULL(chietkhau_CSBH, 0) as chietkhauCHUAN "+
					 "		from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + id + "'	 "+
					 "	) "+
					 "	dh_sp on hd.SANPHAM_FK = dh_sp.sanpham_fk "+
					 "where HOADON_FK = '" + hdId + "' and dbo.trim(hd.SCHEME) = '' ";
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_SP: " + query;
				return msg1;
			}*/
			
			//CHEN CHIET KHAU
			conditionKM = "";
			if( thueVAT.equals("5") )
			{
				if( tachUutien.equals("5") ) //đưa hàng KM 10% vào hóa đơn VAT 5% ( trường hợp hàng bán ko có hàng nào VAT 10% )
					conditionKM += " AND (  a.VAT = '5' or a.VAT = '10'  ) ";
				else
				{
					if( !choHangKM_VaoHMP )
						conditionKM += " AND a.VAT = '5' ";
				}
			}
			else if( thueVAT.equals("10") )
			{
				if( tachUutien.equals("10") ) //đưa hàng KM 5% vào hóa đơn VAT 10% ( trường hợp hàng bán ko có hàng nào VAT 5% )
					conditionKM += " AND ( a.VAT = '5' or a.VAT = '10'  ) ";
				else
				{
					if( !choHangKM_VaoHMP )
						conditionKM += " AND a.VAT = '10' ";
				}
			}
			
			query = "insert ERP_HOADONNPP_CHIETKHAU( HOADON_FK, DDH_FK, DIENGIAI, GIATRI, scheme, LOAI, VAT, tienchuaVAT )  "+
					 " 	select '" + hdId + "', a.dondathangID, N'Trả tích lũy', a.tonggiatri, e.SCHEME, 0 as loai, a.VAT, a.tienchuaVAT   "+
					 " 	from ERP_DONDATHANGNPP_TICHLUY_TRATL a    "+
					 " 		inner join DANGKYKM_TICHLUY d on a.dkkmID = d.PK_SEQ "+
					 " 		inner join TIEUCHITHUONGTL e on d.TIEUCHITL_FK = e.PK_SEQ   "+
					 " 	where a.dondathangID = '" + id + "' " + conditionKM;
			
			//TACH THEO VAT NEU CO TRUYEN VAO
			/*if( thueVAT.equals("5") )
				query += " AND a.VAT = '5' ";
			else if( thueVAT.equals("10") )
				query += " AND a.VAT = '10' ";*/
			
			/*query += " union "+
					 " 	select '" + hdId + "', a.dondathangID, N'Trả khuyến mại', a.tonggiatri, e.SCHEME, 0, 0 as VAT, a.TONGGIATRI as tienchuaVAT   "+
					 " 	from ERP_DONDATHANGNPP_CTKM_TRAKM a    "+
					 " 		inner join CTKHUYENMAI e on a.CTKMID = e.PK_SEQ   "+
					 " 	where a.dondathangID = '" + id + "' and a.SPMA is null";*/
			
			query += " union "+
					 " 	select '" + hdId + "', a.dondathangID, N'Trả khuyến mại', a.tonggiatri, e.SCHEME, 1 as loai, a.VAT, a.tienchuaVAT   "+
					 " 	from ERP_DONDATHANGNPP_CTKM_TRAKM_CHIETKHAU_TACHVAT a    "+
					 " 		inner join CTKHUYENMAI e on a.CTKMID = e.PK_SEQ   "+
					 " 	where a.dondathangID = '" + id + "' " + conditionKM;
			
			if(!db.update(query))
			{
				msg1 = "Khong the tao moi ERP_HOADONNPP_CHIETKHAU: " + query;
				return msg1;
			}
			
			//CHECK BANG TONG PHAI BANG BANG CHI TIET
			query = "select count(*) as sodong  " +
					"from " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP a inner join SANPHAM b on a.sanpham_fk = b.pk_seq " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"dh left join " +
					"( " +
					"	select b.pk_seq as sanpham_fk, sum(soluong) as soluong  " +
					"	from ERP_HOADONNPP_SP_CHITIET a inner join SANPHAM b on a.MA = b.MA " +
					"	where a.hoadon_fk = '" + hdId + "' " +
					"	group by b.pk_seq " +
					") " +
					"xk on dh.sanpham_fk = xk.sanpham_fk " +
					"where dh.soluong != isnull(xk.soluong, 0) ";
			System.out.println("---CHECK HOA DON: " + query);
			int soDONG = 0;
			ResultSet rsCHECK = db.get(query);
			if(rsCHECK != null)
			{
				if(rsCHECK.next())
				{
					soDONG = rsCHECK.getInt("sodong");
				}
				rsCHECK.close();
			}
			
			if(soDONG > 0)
			{
				msg1 = "3.Số lượng trong đơn hàng không khớp với hóa đơn. Vui lòng liên hệ Admin để xử lý ";
				return msg1;
			}
			
			//TỰ ĐỘNG CẬP NHẬT LẠI BẢNG ERP_DONDATHANGNPP_SANPHAM_CHITIET
			query = " UPDATE ERP_DONDATHANGNPP_SANPHAM_CHITIET SET HOADON_FK = "+hdId+" WHERE DONDAThang_fk = "+id;
			System.out.println(query);
			if(db.updateReturnInt(query) <= 0 )
			{
				msg1 = "Khong the cập nhật ERP_DONDATHANGNPP_SANPHAM_CHITIET: " + query;
				return msg1;
			}
			
			//Cập nhật lại giá cho hóa đơn
			geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
			msg1 = util.CapNhat_ThanhTien_HoaDon(db, hdId);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { hdId } );
			
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			msg1 = "Exception: " + e.getMessage();
			e.printStackTrace();
			return msg1;
		}
		
		return msg1;
	}
	
	

	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		//System.out.println("---TONG LUONG: " + tongluong );
		
		//String nhomkenh_fk = " select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( select kbh_fk from KHACHHANG where pk_seq = '" + this.khId + "' )  ";
		String nhomkenh_fk = " select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = ( " + this.kbhId + " )  ";
		if(this.dungchungKENH.equals("1")) //DUNG CHUNG KENH THI QUY VE KENH THAU
			nhomkenh_fk = "100000";
		
		//KHÔNG PHÂN BIỆT NHÓM KÊNH
		nhomkenh_fk = "100000";
		
		String query = "";
		
		String soloDACHON = "";
		if( this.sanpham_soluongDAXUAT != null )
		{
			//KEY: MA - SOLO; 
			Enumeration<String> keys = this.sanpham_soluongDAXUAT.keys();
			while( keys.hasMoreElements() )
			{
				String key = keys.nextElement();
				if(key.startsWith(spMa))
				{
					String solo = key.split("__")[1];
					String daxuat =  this.sanpham_soluongDAXUAT.get(key);
					
					if( daxuat.trim().length() > 0 )
						soloDACHON += "select '" + solo + "' as soloCHON, " + daxuat + " as soluongDACHON union ";
				}
			}
		}
		
		if( soloDACHON.trim().length() > 0 )
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
		else
			soloDACHON = " select '1' as soloCHON, 0 as soluongDACHON ";
		
		if(this.khKGId.trim().length() <= 0)
		{
			/*query = "select case when sp.dvdl_fk != '" + donvi + "'  " +
				   "	then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end  as AVAILABLE,  " +
				   "	NGAYHETHAN, SOLO " +
				   "from NHAPP_KHO_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq " +
				   "where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   " +
				   //"	and AVAILABLE - '" + daxuat + "'  > 0 and NPP_FK = '" + this.nppId + "' and NHOMKENH_FK in ( " + nhomkenh_fk + " )  order by NGAYHETHAN asc ";
				   "	and case when sp.dvdl_fk != '" + donvi + "'  " +
				   "		then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end  >= 0 " +
				   " and NPP_FK = '" + this.nppId + "' and NHOMKENH_FK in ( " + nhomkenh_fk + " )  order by NGAYHETHAN asc ";*/
			
			query = "select DT.NGAYHETHAN, DT.SOLO, DT.AVAILABLE - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE  "+
					 "from "+
					 "( "+
					 "	select case when sp.dvdl_fk != '" + donvi + "'  	then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end  as AVAILABLE,  	NGAYHETHAN, SOLO  "+
					 "	from NHAPP_KHO_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
					 "	where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   	 "+
					 "		and case when sp.dvdl_fk != '" + donvi + "'  		then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end  >= 0  and NPP_FK = '" + this.nppId + "' and NHOMKENH_FK in ( " + nhomkenh_fk + " )   "+
					 ") "+
					 "DT left join  "+
					 "( "+
					 soloDACHON +
					 ") "+
					 "DAXUAT on DT.SOLO = DAXUAT.soloCHON  "+
					 "where DT.AVAILABLE - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
					 "order by NGAYHETHAN asc  ";
		}
		else
		{
			/*query = "select case when sp.dvdl_fk != '" + donvi + "'  " +
					   "	then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE  end  as AVAILABLE,  " +
					   "	NGAYHETHAN, SOLO " +
					   "from NHAPP_KHO_KYGUI_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq " +
					   "where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   " +
					   "	and case when sp.dvdl_fk != '" + donvi + "'  " +
					   "		then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end  >= 0 " +
					   "    and NPP_FK = '" + this.nppId + "' and NHOMKENH_FK in ( " + nhomkenh_fk + " ) and khachhang_fk = '" + this.khKGId + "' and isNPP = '0' " + 
					   "  order by NGAYHETHAN asc ";*/
			
			query = "select DT.NGAYHETHAN, DT.SOLO, DT.AVAILABLE - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE  "+
					 "from "+
					 "( "+
					 "	select case when sp.dvdl_fk != '" + donvi + "'  	then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end  as AVAILABLE,  	NGAYHETHAN, SOLO  "+
					 "	from NHAPP_KHO_KYGUI_CHITIET ct inner join SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
					 "	where KHO_FK = '" + this.khoNhanId + "' and SANPHAM_FK = ( select pk_seq from SANPHAM where ma = '" + spMa + "' )   	 "+
					 "		and case when sp.dvdl_fk != '" + donvi + "'  		then ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = sp.PK_SEQ and DVDL1_FK = sp.DVDL_FK and DVDL2_FK = '" + donvi + "' ) * AVAILABLE else AVAILABLE end  >= 0  and NPP_FK = '" + this.nppId + "' and NHOMKENH_FK in ( " + nhomkenh_fk + " )   "+
					 "		and khachhang_fk = '" + this.khKGId + "' and isNPP = '0'	" +
					 ") "+
					 "DT left join  "+
					 "( "+
					 soloDACHON +
					 ") "+
					 "DAXUAT on DT.SOLO = DAXUAT.soloCHON  "+
					 "where DT.AVAILABLE - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
					 "order by NGAYHETHAN asc  ";
		}	
			
		System.out.println("----LAY SO LO: " + query );
		String query2 = "";
		ResultSet rs = db.get(query);
		try 
		{
			double total = 0;
			
			while(rs.next())
			{
				double slg = 0;
				double avai = Math.round(rs.getDouble("AVAILABLE") * 100.0 ) / 100.0;
				
				System.out.println("===================== AVAI: " + avai);
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
					
				if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '" + slg + "' as tuDEXUAT union ALL ";
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("SOLO") + "' as SOLO, '' as tuDEXUAT union ALL ";
				}
				
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			//System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}

	
	public String[] getSpDonviChuan() {
		
		return this.spDonviChuan;
	}

	
	public void setSpDonviChuan(String[] spDonvi) {
		
		this.spDonviChuan = spDonvi;
	}

	
	public String[] getSpSoluongChuan() {
		
		return this.spSoluongChuan;
	}

	
	public void setSpSoluongChuan(String[] spSoluong) {
		
		this.spSoluongChuan = spSoluong;
	}

	
	public String getDungchungKenh() {
		
		return this.dungchungKENH;
	}

	
	public void setDungchungKenh(String dungchungKenh) {
		
		this.dungchungKENH = dungchungKenh;
	}
	
	public String[] getSpSoluongton()
	{
		return spSoluongton;
	}

	public void setSpSoluongton(String[] spSoluongton)
	{
		this.spSoluongton = spSoluongton;
	}

	public ResultSet getHopdongRs()
	{
		return this.hopdongRs;
	}

	public void setHopdongRs(ResultSet hopdongRs) 
	{	
		this.hopdongRs = hopdongRs;
	}

	public String getDonhangmuon() 
	{
		return this.donhangMuon;
	}

	public void setDonhangmuon(String donhangmuon) 
	{
		this.donhangMuon = donhangmuon;
	}

	public String getPhanloai() 
	{
		return this.phanloai;
	}

	public void setPhanloai(String phanloai) 
	{
		this.phanloai = phanloai;
	}
	
	public String getCapduyet() {

		return this.capduyet;
	}


	public void setCapduyet(String capduyet) {

		this.capduyet = capduyet;
	}

	
	public ResultSet getCongnoRs() {
		
		return this.congnoRs;
	}

	
	public void setCongnoRs(ResultSet congnoRs) {
		
		this.congnoRs = congnoRs;
	}
	
	public String[] getSpScheme() {

		return this.spSCheme;
	}


	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	
	public ResultSet getSPRs() {
		
		return this.SPRs;
	}

	
	public void setSPRs(ResultSet SPRs) {
		
		this.SPRs = SPRs;
	}

	public String[] getSpTDV() {

		return this.spTDV;
	}

	public void setSpTDV(String[] spTDV) {
		
		this.spTDV = spTDV;
	}


	public String[] getSpGianhapGOC() {

		return this.spGianhapGOC;
	}


	public void setSpGianhapGOC(String[] spGianhapgoc) {
		
		this.spGianhapGOC = spGianhapgoc;
	}

	
	public String getDoituong() {

		if(this.loaidonhang.equals("0"))
			return "Nhà phân phối";
		if(this.loaidonhang.equals("1"))
			return "Khách hàng";
		if(this.loaidonhang.equals("2"))
			return "Khách hàng";
		if(this.loaidonhang.equals("3"))
			return "Nhân viên";
		
		return "Đối tượng";
	}

	
	public void setDoituong(String doituong) {
		
		this.doituong = doituong;
	}
	
	public String getIsKhuyenmai() {

		return this.isKhuyenmai;
	}


	public void setIsKhuyenmai(String isKhuyenmai) {
		
		this.isKhuyenmai = isKhuyenmai;
	}

	public String getChophepsuagia() {
		
		if( this.loaidonhang.equals("3") || this.donhangMuon.equals("1") || this.isKhuyenmai.equals("1") )
			return "1";
		
		return "0";
	}


	public void setChophepsuagia(String chophepsuagia) {
	
		this.chophepsuagia = chophepsuagia;
	}

	
	public String getKhachhangKGId() {
		
		return this.khKGId;
	}

	
	public void setKhachhangKGId(String khkgId) {
		
		this.khKGId = khkgId;
	}

	
	public ResultSet getKhachhangKGRs() {
		
		return this.khKGRs;
	}

	
	public void setKhachhangKGRs(ResultSet khkgRs) {
		
		this.khKGRs = khkgRs;
	}
	
	public void ApChietKhau(String ddhId, dbutils db, String dung_db_moi, String aplaiKM) 
	{
		//Nếu có check trả tích lũy thì không được CK nào
		if( this.tratichluy.equals("1") || this.donhangMuon.equals("1") )
		{
			String query = " delete ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + ddhId + "' and tichluyGD = 0 ";
			db.update(query);
			
			query = " update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '', chietkhau_DLN = 0, chietkhau = 0 " + 
					   	   " where dondathang_fk = '" + ddhId + "' ";
			db.update(query);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip", new String[] { ddhId } );
			
			return;
		}
		
		//ÁP LẠI CHIẾT KHẤU CSBH VÀ CÁC DẠNG CHIẾT KHẤU TRỪ THẲNG VÀO ĐƠN GIÁ
		String query = "";
		
		//Nếu áp lại KM = 0 thì kiểm tra xem đơn hàng này lần đầu đã được phân bổ KM vào đơn giá chưa
		query = "select count(*) as soDONG from ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + ddhId + "' and tichluyGD = 0 and spMA is null ";
		boolean aplandau = false;
		ResultSet rsCHECK = db.get(query);
		try 
		{
			if( rsCHECK != null && rsCHECK.next() )
			{
				if( rsCHECK.getInt("soDONG") > 0 )
					aplandau = true;
			}
		}
		catch (Exception e) { }
		
		System.out.println("::: AP LAI KM: " + aplaiKM + " -- AP LAN DAU: " + aplandau );
		if( aplaiKM.equals("1") || aplandau )
		{
			query = " update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '', chietkhau_DLN = 0, chietkhau = 0 " + 
						   " where dondathang_fk = '" + ddhId + "' ";
		}
		else
		{
			query = " update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_DLN = 0, chietkhau = 0 " + 
					" where dondathang_fk = '" + ddhId + "' ";
		}
		
		db.update(query);
		
		//XEM KHÁCH HÀNG
		if(this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
			query = "select ISNULL(PT_CHIETKHAU, 0) as PT_CHIETKHAU from KHACHHANG where pk_seq = '" + this.khId + "' ";
		else
			query = "select ISNULL(PT_CHIETKHAU, 0) as PT_CHIETKHAU from NHAPHANPHOI where pk_seq = '" + this.khId + "' ";
		
		float PT_CHIETKHAU = 0;
		ResultSet rsCK = db.get(query);
		if( rsCK != null )
		{
			try 
			{
				if( rsCK.next() )
				{
					PT_CHIETKHAU = rsCK.getFloat("PT_CHIETKHAU");
				}
				rsCK.close();
			} 
			catch (Exception e) { }
		}
		
		//Có chiết khấu trong DLN rồi thì không được chiết khấu KM và chiết khấu CSBH
		System.out.println("::::: PHAN TRAM CHIET KHAU: " + PT_CHIETKHAU + " - LOAI DON HANG: " + loaidonhang );
		if( PT_CHIETKHAU > 0 )
		{
			query = " update ERP_DONDATHANGNPP_SANPHAM set chietkhau_KM = 0, schemeCHIETKHAU = '' " + 
					   " where dondathang_fk = '" + ddhId + "' ";
			db.update(query);
			
			query = "update DH set DH.dongia = DH.dongiaGOC - round( CK.chietkhauGIAM, 0 ), chietkhau = '" + PT_CHIETKHAU + "', chietkhau_DLN = round( CK.chietkhauGIAM, 0 ) " + 
					"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
					"( " + 
					"	select dondathang_fk, sanpham_fk, " + 
					"			dongiaGOC * ( " + ( PT_CHIETKHAU / 100.0 ) + " ) as chietkhauGIAM " +
					"	from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP b on a.dondathang_fk = b.PK_SEQ " + 
					"	where dondathang_fk = '" + ddhId + "'  " + 
					") " + 
					"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
					"where DH.dondathang_fk = '" + ddhId + "'  " ;
			
			System.out.println("::::: CAP NHAT CHIET KHAU THEO DLN: " + query );
			db.update(query);
			
			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip", new String[] { ddhId } );
			
			return;
		}
		
		//NẾU CÓ DÙNG PHIẾU MUA HÀNG, THÌ KHỒNG ĐƯỢC ÁP CHIẾT KHẤU BH, CHỈ ĐƯỢC ÁP CK KHUYẾN MẠI
		String maphieumuahang = "";
		//int dangkyTL = 0;
		String tichluyIds = "";
		
		/*query = " select isnull(maphieuMUAHANG, '') as maphieuMUAHANG, loaidonhang, "+
				"	( select count(*) from DANGKYKM_TICHLUY_KHACHHANG where KHACHHANG_FK = dh.khachhang_fk ) as dangkyTL	"+
				" from ERP_DONDATHANGNPP dh where dh.pk_seq = '" + this.id + "' ";*/
		
		query =  " select isnull(maphieuMUAHANG, '') as maphieuMUAHANG, loaidonhang, "+
				 " 	   isnull( 	"+
				 " 	   (	Select cast(tl.TIEUCHITL_FK as varchar(10) ) + ', ' AS [text()]  "+
				 " 		From DANGKYKM_TICHLUY tl inner join DANGKYKM_TICHLUY_KHACHHANG tl_kh on  tl.PK_SEQ = tl_kh.DKKMTICHLUY_FK and tl_kh.KHACHHANG_FK = dh.KHACHHANG_FK  "+
				 " 		Where tl.TRANGTHAI = '1'"+
				 " 		For XML PATH ('') ) , '' )  as tichluyIds	"+
				 " from ERP_DONDATHANGNPP dh "+
				 " where dh.pk_seq = '" + this.id + "'";
		
		ResultSet rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				if( rs.next() )
				{
					maphieumuahang = rs.getString("maphieuMUAHANG");
					this.loaidonhang = rs.getString("loaidonhang");
					//dangkyTL = rs.getInt("dangkyTL");
					
					if( rs.getString("tichluyIds").trim().length() > 0 )
						tichluyIds = rs.getString("tichluyIds").substring(0, rs.getString("tichluyIds").length() - 2);
				}
				rs.close();
			} 
			catch (Exception e) { }
		}
		
		//TINH LAI CAC KM CO CHIET KHAU
		if(this.loaidonhang.equals("1") || this.loaidonhang.equals("2") )
		{
			if( aplaiKM.equals("1") || aplandau )
			{
				/*query = " select CTKMID, TRAKMID, TONGGIATRI, " + 
						"	ISNULL( ( select CHIETKHAU from TRAKHUYENMAI where PK_SEQ = a.TRAKMID ), 0) as ptChietkhau,	" +
						"	( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = a.CTKMID ) as dkkmId,	" +
						"	( select SCHEME from CTKHUYENMAI where pk_seq = a.CTKMID ) as SCHEME	" +
						" from ERP_DONDATHANGNPP_CTKM_TRAKM a inner join ERP_DONDATHANGNPP b on a.dondathangId = b.pk_seq " +
						" where DONDATHANGID = '" + ddhId + "' and a.spMA is NULL and CTKMID in ( select PK_SEQ from CTKHUYENMAI where chiavaodongia = 1 )";*/
				
				query = "\nselect CTKMID, TRAKMID, TONGGIATRI, 	ISNULL( ( select CHIETKHAU from TRAKHUYENMAI where PK_SEQ = a.TRAKMID ), 0) as ptChietkhau,			 " + 
						 "\n	 ( select SCHEME from CTKHUYENMAI where pk_seq = a.CTKMID ) as SCHEME, " + 
						 "\n	 (	Select cast( DKKHUYENMAI_FK as varchar(10) ) + ', ' AS [text()]   " + 
						 "\n		From CTKM_DKKM  " + 
						 "\n		Where CTKHUYENMAI_FK = a.CTKMID   " + 
						 "\n		For XML PATH ('') )  as dkkmIds      " + 
						 "\nfrom ERP_DONDATHANGNPP_CTKM_TRAKM a inner join ERP_DONDATHANGNPP b on a.dondathangId = b.pk_seq   " + 
						 "\nwhere DONDATHANGID = '" + ddhId + "' and a.spMA is NULL and CTKMID in ( select PK_SEQ from CTKHUYENMAI where chiavaodongia = 1 ) ";
				
				System.out.println("::::LAY CTKM DANG HUONG: " + query );
				rs = db.get(query);
				if(rs != null)
				{
					try 
					{
						while(rs.next())
						{
							//NHỮNG CTKM NÀY PHẢI KHÔNG NẰM TRONG DANH SÁCH ĐĂNG KÝ TÍCH LŨY 
							String ctkmId = rs.getString("CTKMID");
							//String trakmId = rs.getString("TRAKMID");
							//double tongiatri = rs.getDouble("TONGGIATRI");
							double ptChietkhauKM = rs.getDouble("ptChietkhau");
							String dkkmIds = rs.getString("dkkmIds").substring(0, rs.getString("dkkmIds").length() - 2);
							
							System.out.println(":::: CO DK TICH LUY: " + tichluyIds + " -- PT CHIET KHAU KM: " + ptChietkhauKM );
							if( ptChietkhauKM > 0 ) //thỏa điều kiện, chia thẳng vào đơn giá
							{
								//Phân bổ khuyến mại chiết khấu vào đơn giá
								/*query = "update dh set dh.chietkhau_KM = ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptChietkhauKM + " ) / 100.0) / ( 1 + thueVAT / 100.0 ), " + 
										" 	dh.schemeCHIETKHAU = '" + rs.getString("SCHEME") + "' " +
										"from ERP_DONDATHANGNPP_SANPHAM	dh  " +
										"where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = '" + rs.getString("dkkmId") + "' )";*/
								
								//CHỈ CỦA NHỮNG SP CÓ DÙNG KM
								query = " update dh_sp "+
										 " 	set dh_sp.chietkhau_KM = round( ( ( " + rs.getString("tonggiatri") + " * ( DT.tongtienGOC * 100 / tongtien ) / 100.0 ) / DT.soluong ) / ( 1 + dh_sp.thueVAT / 100.0 ), 0 ), "+
										 "      dh_sp.schemeCHIETKHAU = '" + rs.getString("SCHEME") + "'	" +
										 " from ERP_DONDATHANGNPP_SANPHAM dh_sp inner join"+
										 " ("+
										 " 	select dh.sanpham_fk, dh.soluong, dh.soluong * ( dongiaGOC * ( 1 + thueVAT / 100.0 ) / 100.0 ) as tongtienGOC,"+
										 " 			( "+
										 " 				select SUM( dh1.soluong * ( dh1.dongiaGOC * ( 1 + dh1.thueVAT / 100.0 ) / 100.0 ) )"+
										 " 				from ERP_DONDATHANGNPP_SANPHAM	dh1  inner join DIEUKIENKM_SANPHAM dk1 on dh1.sanpham_fk = dk1.SANPHAM_FK"+
										 " 				where dondathang_fk = '" + ddhId + "' and dk1.DIEUKIENKHUYENMAI_FK in ( " + dkkmIds + ") " + 
										 " 					and dh1.sanpham_fk in ( select sanpham_fk from ERP_DONDATHANGNPP_CTKM_SUDUNG where dondathangId = '" + ddhId + "' and ctkmId = '" + ctkmId + "' and dkkmId in (" + dkkmIds + ") and sudung > 0 ) "+
										 " 			  ) as tongtien"+
										 " 	from ERP_DONDATHANGNPP_SANPHAM	dh  inner join DIEUKIENKM_SANPHAM dk on dh.sanpham_fk = dk.SANPHAM_FK"+
										 " 	where dondathang_fk = '" + ddhId + "' and dk.DIEUKIENKHUYENMAI_FK in ( " + dkkmIds + ") "+
										 "			and dh.sanpham_fk in ( select sanpham_fk from ERP_DONDATHANGNPP_CTKM_SUDUNG where dondathangId = '" + ddhId + "' and ctkmId = '" + ctkmId + "' and dkkmId in ( " + dkkmIds + " ) and sudung > 0 )	"+
										 " )"+
										 " DT on dh_sp.sanpham_fk = DT.sanpham_fk"+
										 " where dh_sp.dondathang_fk = '" + ddhId + "' and dh_sp.sanpham_fk in ( select sanpham_fk from ERP_DONDATHANGNPP_CTKM_SUDUNG where dondathangId = '" + ddhId + "' and ctkmId = '" + ctkmId + "' and dkkmId in (" + dkkmIds + ") and sudung > 0 ) ";
								
								System.out.println("::: CAP NHAT CHIET KHAU - KM: " + query );
								db.update(query);
							}
							
							//CHIA THẲNG VÀO GIÁ
							query = "delete ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + ddhId + "' and CTKMID = '" + ctkmId + "' ";
							System.out.println("::: XOA CHIET KHAU - KM CHIA GIA: " + query );
							db.update(query);
						}
						rs.close();
					} 
					catch (Exception e) 
					{
						e.printStackTrace();
					}
				}
			}
			
			//ÁP TIẾP CHÍNH SÁCH BÁN HÀNG NẾU CÓ
			//if( ( loaidonhang.equals("1") || loaidonhang.equals("2") ) && maphieumuahang.trim().length() <= 0  )
			if( ( loaidonhang.equals("1") || loaidonhang.equals("2") )  )
			{
				query = "select khachhang_fk, NgayDonHang from ERP_DONDATHANGNPP where pk_seq = '" + ddhId + "'";
				rs = db.get(query);
				
				String ngaydonhang = "";
				String khId = "";
				try 
				{
					if(rs.next())
					{
						ngaydonhang = rs.getString("NgayDonHang");
						khId = rs.getString("khachhang_fk");
					}
					rs.close();
				} 
				catch (Exception e1) { }
				
				String condition = "";
				if( tichluyIds.trim().length() > 0 )
				{
					//condition += " and sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where TRANGTHAI = '1' and NPP_FK = '" + nppId + "' ) ) ";
					condition += " and sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( " + tichluyIds + " ) ) ";
				}
				
				//Không lấy tích lũy kiểu mới
				condition += " and sanpham_fk not in ( select sanpham_fk from ERP_DONDATHANGNPP_CTKM_SUDUNG where DONDATHANGID = '" + this.id + "' and ctkmId in ( select PK_SEQ from CTKHUYENMAI where loaitrucsbh = '1' ) ) ";
				
				//KHÔNG LẤY NHỨNG SẢN NÀO ĐÃ ĐƯỢC KM
				query = "select a.pk_seq, ( select LCH_FK from KHACHHANG where PK_SEQ = '" + khId + "' ) as lch_fk, a.apdungchodaily, a.apdungchohopdong, " +
						"	( select SUM( soluong * dongiaGOC * ( 1 + thueVAT / 100.0 ) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and ( isnull(schemeCHIETKHAU, '') = '' or schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where loaitrucsbh = 0 )  ) " + condition + " ) as tongtienDONHANG 	" +
						"from CHINHSACHBANHANG a inner join CHINHSACHBANHANG_NPP b on b.chinhsachbanhang_fk = a.pk_seq   " +
						"where a.trangthai = '1' and tungay <= '" + ngaydonhang + "' and '" + ngaydonhang + "' <= denngay and b.npp_fk = '" + nppId + "' " + 
						" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_LOAIKHACHHANG where loaikhachhang_fk in ( select LCH_FK from KHACHHANG where pk_seq = '" + khId + "' ) ) " + 
						" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_KENH where kbh_fk in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) " + 
						//" and ( a.tichluy_fk is NULL or " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "' and TIEUCHITL_FK = a.tichluy_fk ) ) ) " + 
						//" and ( " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "'  ) ) ) " + 
						" and " + khId + " not in ( select khachhang_fk from CHINHSACHBANHANG_KHACHHANG_KHONGAPDUNG where CSBH_FK = 100000 ) ";
						//" and " + ddhId + " not in ( select dondathang_fk from ERP_DONDATHANGNPP_SANPHAM where schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where ( chiavaodongia = 1 or loaitrucsbh = 1 ) ) )	";
				
				System.out.println(":::: LAY CK CSBH DANG AP DUNG: " + query);
				rs = db.get(query);
				double chietkhau = 0;
				double ptchietkhau = 0;
				String chinhsachID = "";
				try 
				{
					if(rs.next())
					{
						chinhsachID = rs.getString("pk_seq");
						String lchId = rs.getString("lch_fk") == null ? "" : rs.getString("lch_fk");
						String apdungchodaily = rs.getString("apdungchodaily");
						String apdungchohopdong = rs.getString("apdungchohopdong");
						double tongtienDONHANG = Math.round( rs.getDouble("tongtienDONHANG") );

						if(lchId.equals("100008") && apdungchodaily.equals("0") )
						{
							rs.close();
							
							//CẬP NHẬT TOOLTIP
							db.execProceduce2("CapNhatTooltip", new String[] { ddhId } );
							
							return;
						}

						//Tìm mức chiết khấu được hưởng
						query = " select top(1) CHIETKHAU from CHINHSACHBANHANG_TIEUCHI " + 
								" where chinhsachbanhang_fk = '" + chinhsachID + "' and tumuc <= '" + tongtienDONHANG + "' and '" + tongtienDONHANG + "' < denmuc";
						System.out.println("::::: LAY CHIET KHAU THEO CHINH SACH: " + query );
						ResultSet rsCHIETKHAU = db.get(query);
						if(rsCHIETKHAU.next())
						{
							ptchietkhau = rsCHIETKHAU.getDouble("CHIETKHAU");
							rsCHIETKHAU.close();
						}

						if(ptchietkhau > 0)
						{
							chietkhau = ptchietkhau * tongtienDONHANG / 100.0;
						}	
					}
					rs.close();
				} 
				catch (Exception e) {

					e.printStackTrace();
				}
				
				if(chinhsachID.trim().length() > 0 && ptchietkhau > 0 )
				{
					//Nếu khách hàng nằm trong danh sách đăng ký tích lũy, thì những sản phẩm nằm trong tích lũy sẽ không được CSBH
					condition = "";
					if( tichluyIds.trim().length() > 0 )
					{
						//condition += " and sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( select TIEUCHITL_FK from DANGKYKM_TICHLUY where TRANGTHAI = '1' and NPP_FK = '" + nppId + "' ) ) ";
						condition += " and dh.sanpham_fk not in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk in ( " + tichluyIds + " ) ) ";
					}
					
					//KHÔNG LẤY NHỨNG SẢN NÀO ĐÃ ĐƯỢC KM, CHỈ LOẠI TRỪ NHỮNG SCHEME TICK LOẠI TRỪ CSBH
					//condition += " and dh.sanpham_fk not in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where ( chiavaodongia = 1 or loaitrucsbh = 1 ) ) ) ";
					condition += " and dh.sanpham_fk not in ( select sanpham_fk from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' and schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where ( loaitrucsbh = 1 ) ) ) ";
					//Không lấy tích lũy kiểu mới
					condition += " and dh.sanpham_fk not in ( select sanpham_fk from ERP_DONDATHANGNPP_CTKM_SUDUNG where DONDATHANGID = '" + this.id + "' and ctkmId in ( select PK_SEQ from CTKHUYENMAI where loaitrucsbh = 1 ) ) ";
					
					//PHAN BO LAI CHIET KHAU NAY
					query =  "update DH set DH.dongia = DH.dongiaGOC - round( CK.chietkhauGIAM, 0 ), " + 
							"			  DH.chietkhau_CSBH = round( CK.chietkhauGIAM, 0 ), DH.chinhsach_fk = '" + chinhsachID + "' " + 
							"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
							"( " + 
							"	select dondathang_fk, sanpham_fk, ( select top(1) htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = b.KBH_FK ) as htbh_fk, " + 
							"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, dh.thueVAT, " + 
							"		    ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptchietkhau + " ) / 100.0) / ( 1 + thueVAT / 100.0 )  as chietkhauGIAM  " + 
							"	from ERP_DONDATHANGNPP_SANPHAM dh inner join ERP_DONDATHANGNPP b on dh.dondathang_fk = b.PK_SEQ " + 
							"	where dondathang_fk = '" + ddhId + "' " + condition +
							" 			and dh.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' ) " + 
							") " + 
							"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
							"where DH.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' )  " + condition ;
					
					System.out.println("::::: CAP NHAT CHIET KHAU: " + query );
					db.update(query);
				}
				
				
				//CHI GIẢM TIỀN KHUYẾN MẠI VÀ CHIẾT KHẤU CHÍNH SÁCH BÁN HÀNG VÀO ĐƠN HÀNG
				query = "update DH set DH.dongia = DH.dongiaGOC - CK.chietkhauGIAM " + 
						"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
						"( " + 
						"	select dondathang_fk, sanpham_fk, " + 
						"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, a.thueVAT, " + 
						"		    a.chietkhau_KM + a.chietkhau_CSBH  as chietkhauGIAM  " + 
						"	from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP b on a.dondathang_fk = b.PK_SEQ " + 
						"	where dondathang_fk = '" + ddhId + "' and isnull( chietkhau_KM, 0 ) > 0 " + 
						") " + 
						"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
						"where DH.dondathang_fk = '" + ddhId + "'  " ;
				
				System.out.println("::::: CAP NHAT CHIET KHAU KM - CSBH: " + query );
				db.update(query);
				
			}
			
			//Tính thêm tiền theo chiết khấu giảm giá --> KHÔNG DÙNG NỮA
			/*double sotienGIAM = 0;
			//System.out.println("::: MA PHIEU MUA HANG: " + maphieumuahang );
			if( maphieumuahang.trim().length() > 0 )
			{
				query = "select giatri, " +
						"	( select SUM( soluong * dongia * ( 1 + thueVAT / 100.0 ) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' ) as tongtienDONHANG  " +
						"from ERP_PHIEUMUAHANGNPP  " +
						"where npp_fk = '" + nppId + "' and trangthai = '1' and maphieu not in ( select maphieuMUAHANG from ERP_DONDATHANGNPP where maphieuMUAHANG is not null and khachhang_fk = '" + khId + "' and PK_SEQ != '" + ddhId + "' )";
				System.out.println(":::: CHECK PHIEU GIAM GIA: " + query );
				rs = db.get(query);
				if( rs != null )
				{
					try 
					{
						if( rs.next() )
						{
							sotienGIAM = Math.min(rs.getDouble("giatri"), rs.getDouble("tongtienDONHANG"));
						}
						rs.close();
					} 
					catch (Exception e) { }
				}
			}
			stg = sotienGIAM;
			query = "Update ERP_DONDATHANGNPP set sotienGIAM = " + sotienGIAM + " where pk_seq = '" + ddhId + "' ";
			db.update(query);*/
			
		}
		
		//CẬP NHẬT TOOLTIP
		System.out.println("::: GOI HAM CAP NHAT TOOLTIP DON HANG... ");
		db.execProceduce2("CapNhatTooltip", new String[] { ddhId } );
		
		if(dung_db_moi.equals("1"))
			db.shutDown();
		
	}
	
	public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		try
		{
			
			//ChayKetToan_Nhanhang(db);
			//chay ket oan donhang
			
			//ChayKeToan_Donhang(db);
			
			 CapnhatKho_HienTai();
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
		
	}
	
	
	private static void CapnhatKho_HienTai() {
		
		 try{
			  
			  dbutils db=new dbutils();
			  String Nppid="106313";
			  //Nppid = "106344";
			  String query = "select pk_seq from nhaphanphoi where loainpp = 1 and trangthai = 1";
			  ResultSet rs = db.get(query);
			  while(rs.next())
			  {
				  Nppid = rs.getString("pk_seq");
				  Capnhathohangban(Nppid,db);
			  }
			  //Capnhatkho_kygui(Nppid,db);
			
			  
			  
			  
		  }catch(Exception er){
			  er.printStackTrace();
		  }
	}

	private static void Capnhatkho_kygui(String Nppid, dbutils db) {
		
		try{
			
			  
			  // đối với kho ký gửi
			String  khoid= "100003";
			String query="select distinct khachhang_fk from NHAPP_KHO_KYGUI";
			
			ResultSet rskh=db.get(query);
			while(rskh.next()){
				String khid=rskh.getString("khachhang_fk"); 
				 String tungay="2015-11-01";
				  String denngay="2015-12-30";
				  
				  String[] param = new String[5];
					param[0] =khoid;
					param[1] = tungay;
					param[2] = denngay;
					param[3] = Nppid;
					param[4] = khid;
					query=" UPDATE NHAPP_KHO_KYGUI set soluong=0,available=0 WHERE khachhang_fk="+khid+" AND npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
					if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
					}
					
					query=" UPDATE NHAPP_KHO_KYGUI_chitiet  set soluong=0,available=0 WHERE  khachhang_fk="+khid+" AND  npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
					if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
					}
					
				  
					
					ResultSet  rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI", param);
					 
					while(rs.next()){
						
						double SOLUONG=rs.getDouble("SL cuối kỳ");
					 
						 String spid=rs.getString("SPID");
						 String nhomkenh=rs.getString("nhomkenh_fk");
						 
						query=" UPDATE NHAPP_KHO_KYGUI set soluong="+SOLUONG+",available="+SOLUONG+"- isnull(booked,0)  WHERE khachhang_fk="+khid+"  and nhomkenh_fk="+nhomkenh+" and  sanpham_fk="+spid+" and  npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
						//System.out.println("khong thanh cong: "+query);
						if(db.updateReturnInt(query)!=1){
							System.out.println("khong thanh cong: "+query);
						}
						 
					}
					rs.close();
					
					   rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI_CHITIET", param);
					 
					while(rs.next()){
						
						double SOLUONG=rs.getDouble("SL cuối kỳ");
					 
						 String spid=rs.getString("SPID");
						 String nhomkenh=rs.getString("nhomkenh_fk");
						 String solo=rs.getString("số lô");
						 
						 
						query=" UPDATE NHAPP_KHO_KYGUI_chitiet set soluong="+SOLUONG+",available="+SOLUONG+"- isnull(booked,0)  WHERE solo='"+solo+"' and khachhang_fk="+khid+"  and nhomkenh_fk="+nhomkenh+" and  sanpham_fk="+spid+" and  npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
						//System.out.println("khong thanh cong: "+query);
						if(db.updateReturnInt(query)!=1){
							System.out.println("khong thanh cong: "+query);
						}
						 
					}
					rs.close();
					
					
					
			}
			rskh.close();
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private static void Capnhathohangban(String Nppid, dbutils db) {
		
		try{
			String query="SELECT PK_SEQ FROM KHO WHERE PK_SEQ IN (100000)";
			  ResultSet rskho=db.get(query);
			  while(rskho.next()){
				  String khoid=rskho.getString("PK_SEQ");
				  
				  query="delete kho_tmp ";
				  if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
					}
				  
				  query="insert kho_tmp (kho_fk)values ("+khoid+") ";
				  if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
				  }
				  
				  query="delete npp_tmp ";
				  if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
					}
				  
				  query="insert npp_tmp (npp_fk)values ("+Nppid+") ";
				  if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
				  }
				  
				  query="delete sanpham_tmp ";
				  if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
					}
				  
				  query="insert sanpham_tmp (sanpham_fk) select pk_seq from sanpham where trangthai = 1 ";
				  if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
				  }
				  
				  String tungay="2016-03-01";
				  String denngay="2016-03-25";
				  
				  String[] param = new String[4];
					param[0] = khoid;
					param[1] = tungay;
					param[2] = denngay;
					param[3] = Nppid;
					 
					query=" UPDATE NHAPP_KHO_CHITIET set soluong=0,available=0 WHERE npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
					if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
					}
					
					query=" UPDATE NHAPP_KHO  set soluong=0,available=0 WHERE npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
					if(!db.update(query)){
						System.out.println("khong thanh cong: "+query);
					}
					
					String proc = "REPORT_XUATNHAPTON_CHITIET";
					//proc = "REPORT_XUATNHAPTON_DLPP_CHITIET"; // thủ tục lấy xnt dlpp
					ResultSet  rs = db.getRsByPro(proc, param);
					 
					while(rs.next()){
						
						double SOLUONG=rs.getDouble("SL cuối kỳ");
						String solo=rs.getString("Số lô");
						String spid=rs.getString("Spid");
						//String nhomkenh=rs.getString("nhomkenh_fk");
						String nhomkenh = "100000"; //chạy xnt dlpp xài nhóm kênh này
						 
						query=" UPDATE NHAPP_KHO_CHITIET set soluong="+SOLUONG+", available="+SOLUONG+"- isnull(booked,0)  WHERE nhomkenh_fk="+nhomkenh+" and sanpham_fk="+spid+" and  solo='"+solo+"' and  npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
						//System.out.println("[nhapp_kho_chitiet] "+query); 
						if(db.updateReturnInt(query)!=1){
							System.out.println("khong thanh cong: "+query);
						}
						 
					}
					rs.close();
					
					proc = "REPORT_XUATNHAPTON";
					//proc = "REPORT_XUATNHAPTON_DLPP"; // thủ tục lấy xnt dlpp
					rs = db.getRsByPro(proc, param);
					
					while(rs.next()){
						
						double SOLUONG=rs.getDouble("SL cuối kỳ");
						 
						String spid=rs.getString("Spid");
						//String nhomkenh=rs.getString("nhomkenh_fk");
						String nhomkenh = "100000"; //chạy xnt dlpp xài nhóm kênh này
						query=" UPDATE NHAPP_KHO  set soluong="+SOLUONG+", available="+SOLUONG+"- isnull(booked,0) WHERE nhomkenh_fk="+nhomkenh+" and sanpham_fk="+spid+"  and  npp_fk= "+Nppid+" and  KHO_FK= "+khoid;
					 
						if(db.updateReturnInt(query)!=1){
							System.out.println("khong thanh cong: "+query);
					}
						 
				}
				rs.close();
		  }
		  rskho.close();
			  
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private static void ChayKeToan_Donhang(dbutils db) {
		
		try{
			
			

			String id="224964";
			
			String nppId="106313";
			
			Utility_Kho util_kho=new Utility_Kho();
				String query = 	 " SELECT ISNULL(YC.DONHANGMUON,'0') AS DONHANGMUON , YC.NgayDonHang AS NGAYYEUCAU, YC_CT.SANPHAM_FK, "+ 
						 " SUM(isnull(YC_CT.soluongCHUAN,0)) AS TONGXUAT,YC_CT.SOLO , "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
						 " 'XK01' "+ 
						 " ELSE "+ 
						 " 'XK02' END AS MAXUATKHO, "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 AND YC.KHO_FK <> 100008  THEN "+ 
						 " ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' "+ 
						 " AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+")) "+ 
						 " ELSE   "+ 
						 " (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63220000' "+ 
						 " AND CONGTY_FK = (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" )) "+ 
						 " END AS TAIKHOANNO_FK,   "+ 
						 " "+ 
						 " (  SELECT TK.PK_SEQ FROM ERP_LOAISANPHAM LSP "+ 
						 " INNER JOIN SANPHAM SP ON SP.LOAISANPHAM_FK=LSP.PK_SEQ "+ 
						 " INNER JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN=LSP.TAIKHOANKT_FK "+ 
						 " AND TK.CONGTY_FK =((SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" )) WHERE SP.PK_SEQ=SANPHAM_FK )  AS TAIKHOANCO_FK, "+ 
						 " "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
						 " '100002' "+ 
						 " ELSE "+ 
						 " '100008' END AS NOIDUNGXUAT_FK, "+ 
						 " CASE WHEN LEN(ISNULL(YC_CT.SCHEME, '')) <= 0 THEN "+ 
						 " N'Xuất bán hàng (theo đơn hàng bán) - Khác kho ký gửi' "+ 
						 " ELSE "+ 
						 " N'Xuất khuyến mại - Khác kho ký gửi' END AS KHOANMUC ,"+ 
						 " (SELECT CONGTY_FK from NHAPHANPHOI where PK_SEQ = "+nppId+" ) as congty_fk 	"+ 
						 " FROM ERP_DONDATHANGNPP YC "+ 
						 " INNER JOIN ERP_DONDATHANGNPP_SANPHAM_CHITIET YC_CT ON YC.PK_SEQ = YC_CT.dondathang_fk"+ 
						 " WHERE YC.PK_SEQ ="+id +
						 " GROUP BY NgayDonHang, YC_CT.SANPHAM_FK, YC.Kho_FK, YC.NHOMKENH_FK, YC_CT.SCHEME,YC_CT.SOLO,YC.DONHANGMUON  ";
	
	System.out.println("Định khoản: " + query);
	ResultSet rs = db.get(query);
	int dem=0;
	while(rs.next()){
		dem++;
		String ngaychungtu = rs.getString("NGAYYEUCAU");
	 
		String taikhoanNO_fk =  rs.getString("TAIKHOANNO_FK");
		String taikhoanCO_fk =  rs.getString("TAIKHOANCO_FK");
 
		
		String congty_fk= rs.getString("congty_fk");
		
		String  SANPHAM_FK= rs.getString("SANPHAM_FK");
		String solo=rs.getString("SOLO");
	 
		double DONGIA=0; 
		
		String str[] = util_kho.getGiaChayKT(ngaychungtu, db, congty_fk , nppId, SANPHAM_FK, solo);
		if(str[1].length() >0){
 
		}else{
			DONGIA=Double.parseDouble(str[0]);
		}
		
		query="UPDATE  ERP_DONDATHANGNPP_SANPHAM_CHITIET SET GIACHAYKT ="+DONGIA+",taikhoanktNo="+taikhoanNO_fk+",taikhoanktCO="+taikhoanCO_fk+"  WHERE SOLO='"+solo+"' AND SANPHAM_FK= "+SANPHAM_FK+" AND DONDATHANG_FK= "+id;
		if(!db.update(query)){
			System.out.println(query);
		}
		
	 
		
		
	  
		 
	}
 
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	private static void ChayKetToan_Nhanhang(dbutils db) {
		
		try{

			
			String query="select PK_SEQ,NPP_FK from ERP_NHANHANG  dh where DH.TRANGTHAI=1 and DH.MUAHANG_FK IS NOT NULL  ";
			 
			String ctyId="100001";
			 ResultSet rsdh=db.get(query);
			 
			 while(rsdh.next()){
				 String nhId=rsdh.getString("PK_SEQ");
				 
			//Lay tai khoan No trong bang config
			 query = " select SANPHAM_FK, SOLUONGNHAN, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, isnull(donvi, '') as donvi, " +
					 " ( select isnull(ma, '') from SANPHAM where pk_seq = nh.SANPHAM_FK ) as masp " +
					 " from ERP_NHANHANG_SANPHAM nh where NHANHANG_FK = '" + nhId + "'";
			
			//System.out.println("_____LAY TK: " + query);
			 ResultSet rsSp = db.get(query);
			
		 
				while(rsSp.next())
				{
					String taisan_fk = rsSp.getString("SANPHAM_FK");
					 
					
					query = " select ( select pk_seq from ERP_TaiKhoanKT where sohieutaikhoan = b.taikhoankt_fk and CONGTY_FK="+ctyId+") as  taikhoankt_fk  ,  " +
							" ( select pk_seq from ERP_TaiKhoanKT where sohieutaikhoan = '15100000' AND CONGTY_FK = "+ctyId+" ) as taikhoanCO_FK " +
					        " from SANPHAM a inner join ERP_LOAISANPHAM b on a.LOAISANPHAM_FK = b.pk_seq " +
					        " where a.pk_seq = '" + taisan_fk + "'";
					
					
					System.out.println("__Lay TK Ke toan: " + query);
					
					ResultSet rsTKNo = db.get(query);
					String taikhoanNo = "";
					String taikhoanco_fk="";
					if(rsTKNo.next())
					{
						taikhoanNo = rsTKNo.getString("taikhoankt_fk");
						taikhoanco_fk = rsTKNo.getString("taikhoanCO_FK");
					}
					rsTKNo.close();
					
					query=  " update ERP_NHANHANG_SP_CHITIET SET GIACHAYKT="+ rsSp.getDouble("DONGIA")+" , taikhoanktCO= "+taikhoanco_fk+" ,taikhoanktNo ="+taikhoanNo+" " +
							" where SANPHAM_FK="+ taisan_fk+" AND NHANHANG_FK ="+nhId+"  ";
							if(!db.update(query)){
								 
								System.out.println("Lỗi dòng lệnh :"+query);
							}
			 
					
				}
				rsSp.close();
			 }
		
			

				Utility_Kho util_kho = new Utility_Kho();
				
				 query=" select PK_SEQ,NGAYNHAN  from ERP_NHANHANG  dh where DH.TRANGTHAI=1 and DH.MUAHANG_FK IS   NULL  ";
				 
				  ctyId="100001";
				   rsdh=db.get(query);
				 
				 while(rsdh.next()){
					 String nhId=rsdh.getString("PK_SEQ");
					 
					 String NGAYNHAN=rsdh.getString("NGAYNHAN");
					 query = " SELECT DISTINCT a.CONGTY_FK, a.NHAPHANPHOI_FK, a.NCC_KH_FK, b.SANPHAM_FK, b.SOLUONG, c.DONGIA, \n"+
				        "        d.TAIKHOAN_FK as taikhoanKH, ( select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = f.TAIKHOANKT_FK and CONGTY_FK = a.CONGTY_FK) as taikhoanLoaiSP,  "+
				        "        (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52130000' AND CONGTY_FK = a.CONGTY_FK) taikhoanNo_TienHang, "+
				        "        (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63211000' AND CONGTY_FK = a.CONGTY_FK) taikhoanCo_TienVon, b.SOLO "+			       
						" FROM ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM c on a.PK_SEQ = c.NHANHANG_FK \n"+
						"      inner join ERP_NHANHANG_SP_CHITIET b on a.PK_SEQ = b.NHANHANG_FK  \n"+
						"      inner join KHACHHANG d on a.NCC_KH_FK = d.PK_SEQ  \n"+
						"      inner join SANPHAM e on b.SANPHAM_FK = e.PK_SEQ  \n"+
						"      inner join ERP_LOAISANPHAM f on e.LOAISANPHAM_FK = f.PK_SEQ  \n"+
						" WHERE a.PK_SEQ = '" + nhId + "'";
				
				System.out.println(query);
				ResultSet rsKT = db.get(query);
				
				while(rsKT.next())
				{
					String taikhoanktNo = "";
					String taikhoanktCo = "";
					
					String doituongno = "";
					String madoituongno = "";
					String doituongco = "";
					String madoituongco = "";
					
					double giaTonTH = 0;
					String[] str=util_kho.getGiaChayKT(NGAYNHAN, db, rsKT.getString("CONGTY_FK"), rsKT.getString("NHAPHANPHOI_FK"), rsKT.getString("SANPHAM_FK"),rsKT.getString("SOLO"));
					
					if(str[1].length()> 0){
						rsKT.close();
						db.getConnection().rollback();
						 
					}else{
						giaTonTH=Double.parseDouble(str[0]);
					}
					
					taikhoanktNo = rsKT.getString("taikhoanLoaiSP")== null ?"": rsKT.getString("taikhoanLoaiSP");
					taikhoanktCo = rsKT.getString("taikhoanCo_TienVon")== null ?"": rsKT.getString("taikhoanCo_TienVon");
					
					 
					
					query=" update ERP_NHANHANG_SP_CHITIET SET GIACHAYKT="+giaTonTH+" , taikhoanktCO= "+taikhoanktCo+" ,taikhoanktNo ="+taikhoanktNo+" " +
					" where SANPHAM_FK="+ rsKT.getString("SANPHAM_FK")+" AND NHANHANG_FK ="+nhId+" AND SOLO='"+rsKT.getString("SOLO")+"'";
					if(db.updateReturnInt(query)!=1){
						 System.out.println("An hai  : "+ query);
					}
				}
				 
				 }
			
				
			
				 
		
				 
		}catch(Exception er){
			
			
		}
	}

	private static void ApChietKhau_CHAYLAI( dbutils db, String ddhId, String loaidonhang, String ngaydonhang, String khId, String nppId ) 
	{
		String query = "update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chietkhau_KM = 0 where dondathang_fk = '" + ddhId + "' ";
		db.update(query);
		
		//TINH CHIET KHAU
		if( loaidonhang.equals("1") || loaidonhang.equals("2") )
		{
			query = "select a.pk_seq, ( select LCH_FK from KHACHHANG where PK_SEQ = '" + khId + "' ) as lch_fk, a.apdungchodaily, a.apdungchohopdong, " +
					"	( select SUM( soluong * dongiaGOC * ( 1 + thueVAT / 100.0 ) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' ) as tongtienDONHANG 	" +
					"from CHINHSACHBANHANG a inner join CHINHSACHBANHANG_NPP b on b.chinhsachbanhang_fk = a.pk_seq   " +
					"where a.trangthai = '1' and tungay <= '" + ngaydonhang + "' and '" + ngaydonhang + "' <= denngay and b.npp_fk = '" + nppId + "' " + 
					" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_LOAIKHACHHANG where loaikhachhang_fk in ( select LCH_FK from KHACHHANG where pk_seq = '" + khId + "' ) ) " + 
					" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_KENH where kbh_fk in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) " + 
					" and ( a.tichluy_fk is NULL or " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "' and TIEUCHITL_FK = a.tichluy_fk ) ) )	" ;
			
			System.out.println(":::: LAY CK DANG AP DUNG: " + query);
			ResultSet rs = db.get(query);
			double chietkhau = 0;
			double ptchietkhau = 0;
			String chinhsachID = "";
			try 
			{
				if(rs.next())
				{
					chinhsachID = rs.getString("pk_seq");
					String lchId = rs.getString("lch_fk") == null ? "" : rs.getString("lch_fk");
					String apdungchodaily = rs.getString("apdungchodaily");
					String apdungchohopdong = rs.getString("apdungchohopdong");
					double tongtienDONHANG = rs.getDouble("tongtienDONHANG");

					if(lchId.equals("100008") && apdungchodaily.equals("0") )
					{
						rs.close();
						return;
					}

					//Tìm mức chiết khấu được hưởng
					query = " select top(1) CHIETKHAU from CHINHSACHBANHANG_TIEUCHI " + 
							" where chinhsachbanhang_fk = '" + chinhsachID + "' and tumuc <= '" + tongtienDONHANG + "' and '" + tongtienDONHANG + "' < denmuc";
					System.out.println("::::: LAY CHIET KHAU THEO CHINH SACH: " + query );
					ResultSet rsCHIETKHAU = db.get(query);
					if(rsCHIETKHAU.next())
					{
						ptchietkhau = rsCHIETKHAU.getDouble("CHIETKHAU");
						rsCHIETKHAU.close();
					}

					if(ptchietkhau > 0)
					{
						chietkhau = ptchietkhau * tongtienDONHANG / 100.0;
					}	
				}
				rs.close();
			} 
			catch (Exception e) {

				e.printStackTrace();
			}

			if(chinhsachID.trim().length() > 0 && ptchietkhau > 0 )
			{
				//PHAN BO LAI CHIET KHAU NAY
				
				query =  "update DH set DH.dongia = DH.dongiaGOC - CK.chietkhauGIAM, " + 
						"			  DH.chietkhau_CSBH = CK.chietkhauGIAM, DH.chinhsach_fk = '" + chinhsachID + "' " + 
						"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
						"( " + 
						"	select dondathang_fk, sanpham_fk, ( select top(1) htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = b.KBH_FK ) as htbh_fk, " + 
						"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, a.thueVAT, " + 
						"		    ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptchietkhau + " ) / 100.0) / ( 1 + thueVAT / 100.0 )  as chietkhauGIAM  " + 
						"	from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP b on a.dondathang_fk = b.PK_SEQ " + 
						"	where dondathang_fk = '" + ddhId + "' and a.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' ) " + 
						") " + 
						"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
						"where DH.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' )  " ;
				
				System.out.println("::::: CAP NHAT CHIET KHAU: " + query );
				db.update(query);
			}
		}
		
	}
	
	
	private static void ApKHUYENMAI_ChietKhau_CHAYLAI( dbutils db, String ddhId, String loaidonhang, String ngaydonhang, String khId, String nppId ) 
	{
		String query = "update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '' where dondathang_fk = '" + ddhId + "' ";
		db.update(query);
		
		
		//ÁP KHUYẾN MẠI TRƯỚC, CÓ 1 SỐ SCHEME THỎA KM RỒI THÌ KO ĐƯỢC HƯỞNG CSBH NỮA
		//SCHEME: 100120 --> AP DUNG CHO TAT CA KHACH HANG   CK 10% ÐH 1T Mua KS
		query =  "select SCHEME, ctkmId, dkkmId, ptCHietkhau  " + 
				 "from " + 
				 "( " + 
				 "	select ct.SCHEME, ct.PK_SEQ as ctkmId, dk.TONGTIEN, " + 
				 "			(  " + 
				 "			  select SUM( soluong * dongiaGOC * ( 1 + thueVAT / 100.0 ) )  " + 
				 "			  from ERP_DONDATHANGNPP_SANPHAM  " + 
				 "			  where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = dk.PK_SEQ ) ) as tongtienDH, " + 
				 "			 ( select CHIETKHAU from TRAKHUYENMAI where PK_SEQ in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = ct.PK_SEQ ) ) as ptCHietkhau, " + 
				 "			 ( select PK_SEQ from TRAKHUYENMAI where PK_SEQ in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = ct.PK_SEQ ) ) as trakmId, dk.PK_SEQ as dkkmId " + 
				 "	from CTKHUYENMAI ct inner join CTKM_DKKM ct_dk on ct.PK_SEQ = ct_dk.CTKHUYENMAI_FK " + 
				 "			inner join	DIEUKIENKHUYENMAI dk on ct_dk.DKKHUYENMAI_FK = dk.PK_SEQ  " + 
				 "	where ct.PK_SEQ in ( 100120 ) and " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG  ) " + 
				 ") " + 
				 "KM where KM.tongtienDH >= KM.TONGTIEN ";
		
		ResultSet rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				if(rs.next())
				{
					double ptChietkhauKM = rs.getDouble("ptChietkhau");
					if(ptChietkhauKM > 0 )
					{
						//Phân bổ khuyến mại chiết khấu vào đơn giá
						query = "update dh set dh.chietkhau_KM = ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptChietkhauKM + " ) / 100.0) / ( 1 + thueVAT / 100.0 ), " + 
								" 	dh.schemeCHIETKHAU = '" + rs.getString("SCHEME") + "' " +
								"from ERP_DONDATHANGNPP_SANPHAM	dh  " +
								"where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = '" + rs.getString("dkkmId") + "' )";
						
						System.out.println("::: CAP NHAT CHIET KHAU - KM: " + query );
						db.update(query);
					}
				}
				rs.close();
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		//SCHEME: 100119 --> AP DUNG CHO KHACH HANG DUOC PHAN BO
		query =  "select SCHEME, ctkmId, dkkmId, ptCHietkhau  " + 
				 "from " + 
				 "( " + 
				 "	select ct.SCHEME, ct.PK_SEQ as ctkmId, dk.TONGTIEN, " + 
				 "			(  " + 
				 "			  select SUM( soluong * dongiaGOC * ( 1 + thueVAT / 100.0 ) )  " + 
				 "			  from ERP_DONDATHANGNPP_SANPHAM  " + 
				 "			  where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = dk.PK_SEQ ) ) as tongtienDH, " + 
				 "			 ( select CHIETKHAU from TRAKHUYENMAI where PK_SEQ in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = ct.PK_SEQ ) ) as ptCHietkhau, " + 
				 "			 ( select PK_SEQ from TRAKHUYENMAI where PK_SEQ in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = ct.PK_SEQ ) ) as trakmId, dk.PK_SEQ as dkkmId " + 
				 "	from CTKHUYENMAI ct inner join CTKM_DKKM ct_dk on ct.PK_SEQ = ct_dk.CTKHUYENMAI_FK " + 
				 "			inner join	DIEUKIENKHUYENMAI dk on ct_dk.DKKHUYENMAI_FK = dk.PK_SEQ  " + 
				 "	where ct.PK_SEQ in ( 100119 ) and " + khId + " in ( select khachhang_fk from PHANBOKHUYENMAI where khachhang_fk is not null and CTKM_FK in ( 100119 )  ) and " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG  ) " + 
				 ") " + 
				 "KM where KM.tongtienDH >= KM.TONGTIEN ";
		
		rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				if(rs.next())
				{
					double ptChietkhauKM = rs.getDouble("ptChietkhau");
					if(ptChietkhauKM > 0 )
					{
						//Phân bổ khuyến mại chiết khấu vào đơn giá
						query = "update dh set dh.chietkhau_KM = ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptChietkhauKM + " ) / 100.0) / ( 1 + thueVAT / 100.0 ), " + 
								" 	dh.schemeCHIETKHAU = '" + rs.getString("SCHEME") + "' " +
								"from ERP_DONDATHANGNPP_SANPHAM	dh  " +
								"where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = '" + rs.getString("dkkmId") + "' )";
						
						System.out.println("::: CAP NHAT CHIET KHAU - KM: " + query );
						db.update(query);
					}
				}
				rs.close();
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		//SCHEME: 100108 --> AP DUNG CHO TAT CA KHACH HANG TRU NHUNG KHACH HANG CO DANG KY TICH LUY  MRMN2015CSPK 
		query =  "select SCHEME, ctkmId, dkkmId, ptCHietkhau  " + 
				 "from " + 
				 "( " + 
				 "	select ct.SCHEME, ct.PK_SEQ as ctkmId, dk.TONGTIEN, " + 
				 "			(  " + 
				 "			  select SUM( soluong * dongiaGOC * ( 1 + thueVAT / 100.0 ) )  " + 
				 "			  from ERP_DONDATHANGNPP_SANPHAM  " + 
				 "			  where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = dk.PK_SEQ ) ) as tongtienDH, " + 
				 "			 ( select CHIETKHAU from TRAKHUYENMAI where PK_SEQ in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = ct.PK_SEQ ) ) as ptCHietkhau, " + 
				 "			 ( select PK_SEQ from TRAKHUYENMAI where PK_SEQ in ( select TRAKHUYENMAI_FK from CTKM_TRAKM where CTKHUYENMAI_FK = ct.PK_SEQ ) ) as trakmId, dk.PK_SEQ as dkkmId " + 
				 "	from CTKHUYENMAI ct inner join CTKM_DKKM ct_dk on ct.PK_SEQ = ct_dk.CTKHUYENMAI_FK " + 
				 "			inner join	DIEUKIENKHUYENMAI dk on ct_dk.DKKHUYENMAI_FK = dk.PK_SEQ  " + 
				 "	where ct.PK_SEQ in ( 100119 ) and " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG  ) " + 
				 ") " + 
				 "KM where KM.tongtienDH >= KM.TONGTIEN ";
		
		rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				if(rs.next())
				{
					double ptChietkhauKM = rs.getDouble("ptChietkhau");
					if(ptChietkhauKM > 0 )
					{
						//Phân bổ khuyến mại chiết khấu vào đơn giá
						query = "update dh set dh.chietkhau_KM = ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptChietkhauKM + " ) / 100.0) / ( 1 + thueVAT / 100.0 ), " + 
								" 	dh.schemeCHIETKHAU = '" + rs.getString("SCHEME") + "' " +
								"from ERP_DONDATHANGNPP_SANPHAM	dh  " +
								"where dondathang_fk = '" + ddhId + "' and sanpham_fk in ( select sanpham_fk from DIEUKIENKM_SANPHAM where DIEUKIENKHUYENMAI_FK = '" + rs.getString("dkkmId") + "' )";
						
						System.out.println("::: CAP NHAT CHIET KHAU - KM: " + query );
						db.update(query);
					}
				}
				rs.close();
			} 
			catch (Exception e) {
				
				e.printStackTrace();
			}
		}
		
		
		//TINH CHIET KHAU
		if( loaidonhang.equals("1") || loaidonhang.equals("2") )
		{
			query = "select a.pk_seq, ( select LCH_FK from KHACHHANG where PK_SEQ = '" + khId + "' ) as lch_fk, a.apdungchodaily, a.apdungchohopdong, " +
					"	( select SUM( soluong * dongiaGOC * ( 1 + thueVAT / 100.0 ) ) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + ddhId + "' ) as tongtienDONHANG 	" +
					"from CHINHSACHBANHANG a inner join CHINHSACHBANHANG_NPP b on b.chinhsachbanhang_fk = a.pk_seq   " +
					"where a.trangthai = '1' and tungay <= '" + ngaydonhang + "' and '" + ngaydonhang + "' <= denngay and b.npp_fk = '" + nppId + "' " + 
					" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_LOAIKHACHHANG where loaikhachhang_fk in ( select LCH_FK from KHACHHANG where pk_seq = '" + khId + "' ) ) " + 
					" and a.pk_seq in ( select chinhsachbanhang_fk from CHINHSACHBANHANG_KENH where kbh_fk in ( select kbh_fk from KHACHHANG_KENHBANHANG where khachhang_fk = '" + khId + "' ) ) " + 
					" and ( a.tichluy_fk is NULL or " + khId + " not in ( select KHACHHANG_FK from DANGKYKM_TICHLUY_KHACHHANG where DKKMTICHLUY_FK in ( select pk_seq from DANGKYKM_TICHLUY where NPP_FK = '" + nppId + "' and TIEUCHITL_FK = a.tichluy_fk ) ) ) " + 
					" and " + ddhId + " not in ( select dondathang_fk from ERP_DONDATHANGNPP_SANPHAM where schemeCHIETKHAU in ( select scheme from CTKHUYENMAI where PK_SEQ in ( 100108, 100119, 100120 ) ) )	";
			
			System.out.println(":::: LAY CK DANG AP DUNG: " + query);
			rs = db.get(query);
			double chietkhau = 0;
			double ptchietkhau = 0;
			String chinhsachID = "";
			try 
			{
				if(rs.next())
				{
					chinhsachID = rs.getString("pk_seq");
					String lchId = rs.getString("lch_fk") == null ? "" : rs.getString("lch_fk");
					String apdungchodaily = rs.getString("apdungchodaily");
					String apdungchohopdong = rs.getString("apdungchohopdong");
					double tongtienDONHANG = rs.getDouble("tongtienDONHANG");

					if(lchId.equals("100008") && apdungchodaily.equals("0") )
					{
						rs.close();
						return;
					}

					//Tìm mức chiết khấu được hưởng
					query = " select top(1) CHIETKHAU from CHINHSACHBANHANG_TIEUCHI " + 
							" where chinhsachbanhang_fk = '" + chinhsachID + "' and tumuc <= '" + tongtienDONHANG + "' and '" + tongtienDONHANG + "' < denmuc";
					System.out.println("::::: LAY CHIET KHAU THEO CHINH SACH: " + query );
					ResultSet rsCHIETKHAU = db.get(query);
					if(rsCHIETKHAU.next())
					{
						ptchietkhau = rsCHIETKHAU.getDouble("CHIETKHAU");
						rsCHIETKHAU.close();
					}

					if(ptchietkhau > 0)
					{
						chietkhau = ptchietkhau * tongtienDONHANG / 100.0;
					}	
				}
				rs.close();
			} 
			catch (Exception e) {

				e.printStackTrace();
			}
			
			if(chinhsachID.trim().length() > 0 && ptchietkhau > 0 )
			{
				//PHAN BO LAI CHIET KHAU NAY
				query =  "update DH set DH.dongia = DH.dongiaGOC - CK.chietkhauGIAM, " + 
						"			  DH.chietkhau_CSBH = CK.chietkhauGIAM, DH.chinhsach_fk = '" + chinhsachID + "' " + 
						"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
						"( " + 
						"	select dondathang_fk, sanpham_fk, ( select top(1) htbh_fk from hethongbanhang_kenhbanhang where kbh_fk = b.KBH_FK ) as htbh_fk, " + 
						"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, a.thueVAT, " + 
						"		    ( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) * " + ptchietkhau + " ) / 100.0) / ( 1 + thueVAT / 100.0 )  as chietkhauGIAM  " + 
						"	from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP b on a.dondathang_fk = b.PK_SEQ " + 
						"	where dondathang_fk = '" + ddhId + "' and a.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' ) " + 
						") " + 
						"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
						"where DH.sanpham_fk in ( select sanpham_fk from CHINHSACHBANHANG_SANPHAM where chinhsachbanhang_fk = '" + chinhsachID + "' )  " ;
				
				System.out.println("::::: CAP NHAT CHIET KHAU: " + query );
				db.update(query);
			}
			
			
			//CHI GIẢM TIỀN KHUYẾN MẠI VÀ CHIẾT KHẤU CHÍNH SÁCH BÁN HÀNG VÀO ĐƠN HÀNG
			query = "update DH set DH.dongia = DH.dongiaGOC - CK.chietkhauGIAM " + 
					"from ERP_DONDATHANGNPP_SANPHAM DH inner join " + 
					"( " + 
					"	select dondathang_fk, sanpham_fk, " + 
					"			soluong, dongiaGOC, soluong * dongiaGOC as thanhtien, a.thueVAT, " + 
					"		    a.chietkhau_KM + a.chietkhau_CSBH  as chietkhauGIAM  " + 
					"	from ERP_DONDATHANGNPP_SANPHAM a inner join ERP_DONDATHANGNPP b on a.dondathang_fk = b.PK_SEQ " + 
					"	where dondathang_fk = '" + ddhId + "' and isnull( chietkhau_KM, 0 ) > 0 " + 
					") " + 
					"CK on DH.dondathang_fk = CK.dondathang_fk and DH.sanpham_fk = CK.sanpham_fk " + 
					"where DH.dondathang_fk = '" + ddhId + "'  " ;
			
			System.out.println("::::: CAP NHAT CHIET KHAU KM - CSBH: " + query );
			db.update(query);
			
		}
		
	}

	public String[] getSpChietkhauBHKM() {

		return this.spCHIETKHAU_BHKM;
	}

	public void setSpChietkhauBHKM(String[] SpChietkhauBHKM) {
		
		this.spCHIETKHAU_BHKM = SpChietkhauBHKM;
	}

	
	public String getIsMTV() {
		return this.isMTV;
	}

	
	public void setIsMTV(String isMTV) {
		this.isMTV = isMTV;
	}

	
	public String getSohopdong() {
		return this.sohopdong;
	}

	
	public void setSohopdong(String sohopdong) {
		this.sohopdong = sohopdong;
	}
	
	public String getCapdogiaohang() {
		return this.capdogiaohang;
	}

	
	public void setCapdogiaohang(String value) {
		this.capdogiaohang = value;
	}
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN() 
	{
		return this.sanpham_soluongDAXUAT;
	}


	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong) 
	{
		this.sanpham_soluongDAXUAT = sp_soluong;
	}


	public String[] getSpDagiao() {

		return this.spDagiao;
	}


	public void setSpDagiao(String[] spDagiao) {
		
		this.spDagiao = spDagiao;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}

	
	public String getMaphieuMH() {
		
		return this.maphieuMH;
	}

	
	public void setMaphieuMH(String mapmh) {
		
		this.maphieuMH = mapmh;
	}

	
	public String getSotiengiam() {
		
		return this.sotiengiam;
	}

	
	public void setSotiengiam(String sotiengiam) {
		
		this.sotiengiam = sotiengiam;
	}

	
	public void checkKSKD() {
		
		//CHECK DA KHOA SO KINH DOANH CHUA
		int checkKS_KINHDOANH = util.CheckKhoaSoKinhDoanh(db, nppId, this.getDateTime(), "", "", "");
		if(checkKS_KINHDOANH == 1 )
		{
			String[] hientai = this.getDateTime().split("-");
			int thangtiep = Integer.parseInt(hientai[1]) + 1;
			if( thangtiep > 12 )
				thangtiep = 1;
			
			this.msg = "Doanh số đơn hàng này được chuyển sales qua tháng " + ( thangtiep );
		}
		
		
	}


	public ResultSet getTichluyRs() {

		return this.tichluyRs;
	}


	public void setTichluyRs(ResultSet tichluyRs) {
		
		this.tichluyRs = tichluyRs;
	}

	public String getCtyId() {
		
		return this.CtyId;
	}

	
	public void setCtyId(String ctyId) {
		
		this.CtyId = ctyId;
	}

	
	public Double TongNo() {
	
		return this.TongNo;
	}

	
	public Double HanMucNo() {
	
		return this.HanMucNo;
	}

	
	public Double SoNgayNo() {
	
		return this.SoNgayNo;
	}

	
	public Double NoTrongHan() {
	
		return this.NoTrongHan;
	}

	
	public Double NoQuaHan() {
	
		return this.NoQuaHan;
	}

	
	public Double NoXau() {
	
		return this.NoXau;
	}

	
	public String getTratichluy() {

		return this.tratichluy;
	}


	public void setTratichluy(String value) {
		
		this.tratichluy = value;
	}


	public void ApTichLuy() {
		
		//Nếu có tích lũy thì không được CSBH + KM nào nữa
		String query = "delete ERP_DONDATHANGNPP_TICHLUY_TRATL where dondathangId = '" + this.id + "' ";
		db.update(query);
		
		query = "update ERP_DONDATHANGNPP set tratichluy = '" + this.tratichluy + "' where pk_seq = '" + this.id + "'";
		db.update(query);
		
		if( this.tratichluy.equals("0") || this.id.trim().length() <= 0 )
		{
			return;
		}
			
		query = " select thueVAT, SUM( soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as tonggiatri  "+
		 	    " from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk " + 
		 	    " where dh.pk_seq = '" + this.id + "' " + 
		 	    " group by thueVAT ";
		System.out.println("::: LAY TONG TIEN DON HANG: " + query );
		
		double tongtienDH = 0;
		double tongtienDH_thue5 = 0;
		double tongtienDH_thue10 = 0;
		ResultSet rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					if( rs.getDouble("thueVAT") == 5 )
						tongtienDH_thue5 = rs.getDouble("tonggiatri");
					else
						tongtienDH_thue10 = rs.getDouble("tonggiatri");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			tongtienDH = tongtienDH_thue5 + tongtienDH_thue10;
		}
		System.out.println(":::TIEN THUE 5: " + tongtienDH_thue5 + " -- TIEN THUE 10: " + tongtienDH_thue10 );
		
		query = "select DKKMTICHLUY_FK, doanhsoDAT, donvitra, sanphamtra,  "+
				 "		case donvitra when 2 then NULL else (giatritra - tonggiatri - ISNULL(DATA.dachi,0 )) end as giatriTRA, "+
				 "		case donvitra when 2 then soluongTRA - tongsoluong else NULL end as soluongTRA "+
				 "from "+
				 "( "+
				 "	select DKKMTICHLUY_FK, doanhsoDAT, donvitra, sanphamtra, soluongtra, giatritra,  "+
				 "		ISNULL( ( select SUM(TONGGIATRI) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = a.DKKMTICHLUY_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tonggiatri, "+
				 "		ISNULL( ( select SUM(SOLUONG) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = a.DKKMTICHLUY_FK and SPID is not null and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tongsoluong, "+
				 "  	ISNULL( ( SELECT SUM(ct.SOTIENTT) AS DATT FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ "+   
				 "    			  WHERE tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khId+"' and ct.HOADON_FK = a.DKKMTICHLUY_FK and ct.LOAIHD = '9' ),0) as dachi "+
				 "	from DANGKYKM_TICHLUY_KHACHHANG a "+
				 "	where KHACHHANG_FK = '" + this.khId + "' and DUYET = '1' and MucDat is not null "+
				 "		and DKKMTICHLUY_FK in ( select PK_SEQ from DANGKYKM_TICHLUY where TIEUCHITL_FK in ( select PK_SEQ from TIEUCHITHUONGTL where TRANGTHAI = '1' and khongcanduyettra = '0' ) ) 	" +
				 ") "+
				 "DATA "+
				 "where ( case donvitra when 2 then soluongTRA - tongsoluong else giatritra - tonggiatri end > 0 ) " + 
				 "	and DATA.tonggiatri <= 0	" +  //Chỉ được trả 1 lần cho 1 đăng ký thôi
				 "order by donvitra desc, DKKMTICHLUY_FK asc ";
		System.out.println("::: LAY TICH LUY AP KM: " + query );
		
		boolean coTL = false;
		rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				boolean exit = false;
				while( rs.next() )
				{
					String DKKMTICHLUY_FK = rs.getString("DKKMTICHLUY_FK");
					String donvitra = rs.getString("donvitra");
					
					if(donvitra.equals("2")) //TRA SAN PHAM THI CON BAO NHIEU TRA HET
					{
						query = "insert ERP_DONDATHANGNPP_TICHLUY_TRATL( dondathangID, DKKMID, SOXUAT, TONGGIATRI, SPID, SOLUONG ) " + 
								" values( '" + this.id + "', '" + DKKMTICHLUY_FK + "', '1', '0', '" + rs.getString("sanphamtra") + "', '" + rs.getString("soluongTRA") + "' )";
						System.out.println("::: CHEN TRA KM: " + query );
						db.update(query);
						
						coTL = true;
					}
					else
					{
						double tonggiatri = rs.getDouble("giatriTRA");
						
						double giatritra = tonggiatri;
						if( tongtienDH <= 0 )
						{
							giatritra = 0;
							exit = true;
						}
						else
						{
							if( giatritra > tongtienDH  )
							{
								giatritra = tongtienDH;
								tongtienDH = 0;
								exit = true;
							}
							else
								tongtienDH -= giatritra;
						}
					
						if( giatritra > 0 )
						{
							//Xác định phần trăm thuế, giá trị trả bước này có thể phải chia thành 2 dòng 5 và 10 riêng ( trogn trường hợp 5 hoặc 10 không đủ dùng hết )
							double giatritra_thue5 = 0;
							double giatritra_thue10 = 0;
							
							if( giatritra >= ( tongtienDH_thue5 + tongtienDH_thue10 ) )
							{
								if( tongtienDH_thue5 > 0 )
								{
									giatritra_thue5 = tongtienDH_thue5;
									tongtienDH_thue5 = 0;
									
									giatritra -= giatritra_thue5;
								}
								
								if( tongtienDH_thue10 > 0 )
								{
									giatritra_thue10 = tongtienDH_thue10;
									tongtienDH_thue10 = 0;
									
									giatritra -= giatritra_thue10;
								}
							}
							else if( giatritra < ( tongtienDH_thue5 + tongtienDH_thue10 ) )
							{
								if( tongtienDH_thue5 > 0 )
								{
									if( tongtienDH_thue5 < giatritra )
									{
										giatritra_thue5 = tongtienDH_thue5;
										tongtienDH_thue5 = 0;
									}
									else
									{
										giatritra_thue5 = giatritra;
										tongtienDH_thue5 -= giatritra;
									}
									
									giatritra -= giatritra_thue5;
								}
								
								if( giatritra > 0 && tongtienDH_thue10 > 0 )
								{
									if( tongtienDH_thue10 < giatritra )
									{
										giatritra_thue10 = tongtienDH_thue10;
										tongtienDH_thue10 = 0;
									}
									else
									{
										giatritra_thue10 = giatritra;
										tongtienDH_thue10 -= giatritra;
									}
									
									giatritra -= giatritra_thue10;
								}
							}
							
							if( giatritra_thue5 > 0 )
							{
								query = "insert ERP_DONDATHANGNPP_TICHLUY_TRATL( dondathangID, DKKMID, SOXUAT, TONGGIATRI, SPID, SOLUONG, VAT, tienchuaVAT ) " + 
										" values( '" + this.id + "', '" + DKKMTICHLUY_FK + "', '1', '" + giatritra_thue5 + "', NULL, NULL, 5, " + giatritra_thue5 + " / 1.05 )";
								System.out.println("::: CHEN TRA KM VAT 5%: " + query );
								db.update(query);
								
								coTL = true;
							}
							
							if( giatritra_thue10 > 0 )
							{
								query = "insert ERP_DONDATHANGNPP_TICHLUY_TRATL( dondathangID, DKKMID, SOXUAT, TONGGIATRI, SPID, SOLUONG, VAT, tienchuaVAT ) " + 
										" values( '" + this.id + "', '" + DKKMTICHLUY_FK + "', '1', '" + giatritra_thue10 + "', NULL, NULL, 10,  " + giatritra_thue10 + " / 1.1 )";
								System.out.println("::: CHEN TRA KM VAT 10%: " + query );
								db.update(query);
								
								coTL = true;
							}
						}
					}
					
					if(exit)
						break;
					
				}
				rs.close();
				
				if( coTL ) //Nếu có tích lũy này thì không được KM nào hết --> TICH LUY GIAI DOAN DUOC HUONG HET
				{
					query = " update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '', chietkhau_DLN = 0, chietkhau = 0 " + 
							" where dondathang_fk = '" + this.id + "' ";
					db.update(query);
					
					query = "delete  ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + this.id + "' and tichluyGD = 0 ";
					db.update(query);
				}
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//ÁP TÍCH LŨY LOẠI KHÔNG CẦN DUYỆT TRẢ
		this.ApTichLuy_KhongCanDuyet();
		
		//ÁP TÍCH LŨY GIAI ĐOẠN --> CHỈ ÁP KHI LÀM ĐƠN HÀNG
		//this.ApTichLuy_TheoGiaiDoan();
		
	}
	
	private void ApTichLuy_KhongCanDuyet()
	{
		String query = "";
		
		query = "select thueVAT, SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) - sum( ISNULL( tra.TONGGIATRI, 0 ) ) as tonggiatri  "+
				 "from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  "+
				 "		left join ERP_DONDATHANGNPP_TICHLUY_TRATL tra on dh.PK_SEQ = tra.DONDATHANGID and dh_sp.thueVAT = tra.VAT "+
				 "where dh.pk_seq = '" + this.id + "'  "+
				 "group by thueVAT ";
		System.out.println("::: LAY TONG TIEN DON HANG: " + query );
		
		double tongtienDH = 0;
		double tongtienDH_thue5 = 0;
		double tongtienDH_thue10 = 0;
		ResultSet rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				while( rs.next() )
				{
					if( rs.getDouble("thueVAT") == 5 )
						tongtienDH_thue5 = rs.getDouble("tonggiatri");
					else
						tongtienDH_thue10 = rs.getDouble("tonggiatri");
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			
			tongtienDH = tongtienDH_thue5 + tongtienDH_thue10;
		}
		
		query = "select DKKMTICHLUY_FK, doanhsoDAT, donvitra, sanphamtra,   "+
				 "		case donvitra when 2 then NULL else (giatritra - tonggiatri - dachi) end as giatriTRA,  "+
				 "		case donvitra when 2 then soluongTRA - tongsoluong else NULL end as soluongTRA  "+
				 "from  "+
				 "(  "+
				 "	select DT.DKKMTICHLUY_FK, DT.doanhsoDAT, tc.donvi as donvitra,  "+
				 "		( case tc.donvi when 2 then ( select top(1) sanpham_fk from TIEUCHITHUONGTL_SPTRA where thuongtl_fk = DT.PK_SEQ and muctra = tc.muc ) else NULL end ) as sanphamtra,  "+
				 "		( case tc.donvi when 2 then ( select top(1) soluong from TIEUCHITHUONGTL_SPTRA where thuongtl_fk = DT.PK_SEQ and muctra = tc.muc ) else NULL end ) as soluongtra,  "+
				 "		( case tc.donvi when 0 then round( tc.chietkhau * DT.doanhsoDAT / 100.0, 0 ) when 1 then tc.chietkhau else 0 end ) as giatritra, "+
				 "		ISNULL( ( select SUM(TONGGIATRI) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = DT.DKKMTICHLUY_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tonggiatri,  "+
				 "		ISNULL( ( select SUM(SOLUONG) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = DT.DKKMTICHLUY_FK and SPID is not null and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tongsoluong," +
				 "  	ISNULL( ( SELECT SUM(ct.SOTIENTT) AS DATT FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ "+   
				 "    			  WHERE tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '" + this.khId + "' and ct.HOADON_FK = DT.DKKMTICHLUY_FK and ct.LOAIHD = '9' ),0) as dachi "+
				 "	from "+
				 "	( "+
				 "		select DKKMTICHLUY_FK, c.PK_SEQ,  "+
				 "			ISNULL ( ( "+
				 "				select SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as doanhso "+
				 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  "+
				 "				where dh.npp_fk = '" + this.nppId + "' and dh.KHACHHANG_FK = a.KHACHHANG_FK and ( ( dh.CS_DUYET = '1' and dh.TRANGTHAI not in ( 0, 3 ) ) or dh.pk_seq = '" + this.id + "' )  and dh.NgayDonHang >= c.ngayds_tungay and dh.NgayDonHang <= c.ngayds_denngay "+
				 "		 				and dh_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = c.PK_SEQ ) "+
				 "				   ), 0 ) - " + 
				 "			ISNULL ( ( "+
				 "  			select SUM( SOLUONG * round( dongia * ( 1 + ptVat / 100.0 ), 0 ) ) as doanhsoTV  " + 
				 "  			from DONTRAHANG dth inner join DONTRAHANG_TICHLUY dth_tl on dth.PK_SEQ = dth_tl.dontrahang_fk " + 
				 "  				inner join DONTRAHANG_SP dth_sp on dth.PK_SEQ = dth_sp.DONTRAHANG_FK " + 
				 "  			where dth.NPP_FK = '" + this.nppId + "' and dth.KHACHHANG_FK = a.KHACHHANG_FK and dth_tl.tichluy_fk = c.PK_SEQ  " + 
				 "  				and dth_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = dth_tl.tichluy_fk ) " + 
				 "  				and dth.TRANGTHAI in ( 1, 2, 5 ) " +				
				 "				   ), 0 )  " + 
				 " 						as	doanhsoDAT "+
				 "		from DANGKYKM_TICHLUY_KHACHHANG a inner join DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ "+
				 "				inner join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ "+
				 "		where KHACHHANG_FK = '" + this.khId + "' and c.TRANGTHAI = '1' and c.khongcanduyettra = '1'   and b.daduyet = '1'	  "+
				 "	) "+
				 "	DT inner join TIEUCHITHUONGTL_TIEUCHI tc on DT.PK_SEQ = tc.thuongtl_fk "+	
				 "	where tc.tumuc <= DT.doanhsoDAT and DT.doanhsoDAT <= tc.denmuc "+
				 ")  "+
				 "DATA  "+
				 "where ( case donvitra when 2 then soluongTRA - tongsoluong else (giatritra - tonggiatri - dachi) end > 0 )  "+
				 "	and DATA.tonggiatri <= 0	" +
				 "order by donvitra desc, DKKMTICHLUY_FK asc  ";
		
		System.out.println(":::TICH LUY KHONG CAN DUYET: " + query );
		boolean coTL = false;
		rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				boolean exit = false;
				while( rs.next() )
				{
					String DKKMTICHLUY_FK = rs.getString("DKKMTICHLUY_FK");
					String donvitra = rs.getString("donvitra");
					
					if(donvitra.equals("2")) //TRA SAN PHAM THI CON BAO NHIEU TRA HET
					{
						query = "insert ERP_DONDATHANGNPP_TICHLUY_TRATL( dondathangID, DKKMID, SOXUAT, TONGGIATRI, SPID, SOLUONG ) " + 
								" values( '" + this.id + "', '" + DKKMTICHLUY_FK + "', '1', '0', '" + rs.getString("sanphamtra") + "', '" + rs.getString("soluongTRA") + "' )";
						System.out.println("::: CHEN TRA KM: " + query );
						db.update(query);
						
						coTL = true;
					}
					else
					{
						double tonggiatri = rs.getDouble("giatriTRA");
						
						double giatritra = tonggiatri;
						if( tongtienDH <= 0 )
						{
							giatritra = 0;
							exit = true;
						}
						else
						{
							if( giatritra > tongtienDH  )
							{
								giatritra = tongtienDH;
								tongtienDH = 0;
								exit = true;
							}
							else
								tongtienDH -= giatritra;
						}
					
						if( giatritra > 0 )
						{
							//Xác định phần trăm thuế, giá trị trả bước này có thể phải chia thành 2 dòng 5 và 10 riêng ( trogn trường hợp 5 hoặc 10 không đủ dùng hết )
							double giatritra_thue5 = 0;
							double giatritra_thue10 = 0;
							
							if( giatritra >= ( tongtienDH_thue5 + tongtienDH_thue10 ) )
							{
								if( tongtienDH_thue5 > 0 )
								{
									giatritra_thue5 = tongtienDH_thue5;
									tongtienDH_thue5 = 0;
									
									giatritra -= giatritra_thue5;
								}
								
								if( tongtienDH_thue10 > 0 )
								{
									giatritra_thue10 = tongtienDH_thue10;
									tongtienDH_thue10 = 0;
									
									giatritra -= giatritra_thue10;
								}
							}
							else if( giatritra < ( tongtienDH_thue5 + tongtienDH_thue10 ) )
							{
								if( tongtienDH_thue5 > 0 )
								{
									if( tongtienDH_thue5 < giatritra )
									{
										giatritra_thue5 = tongtienDH_thue5;
										tongtienDH_thue5 = 0;
									}
									else
									{
										giatritra_thue5 = giatritra;
										tongtienDH_thue5 -= giatritra;
									}
									
									giatritra -= giatritra_thue5;
								}
								
								if( giatritra > 0 && tongtienDH_thue10 > 0 )
								{
									if( tongtienDH_thue10 < giatritra )
									{
										giatritra_thue10 = tongtienDH_thue10;
										tongtienDH_thue10 = 0;
									}
									else
									{
										giatritra_thue10 = giatritra;
										tongtienDH_thue10 -= giatritra;
									}
									
									giatritra -= giatritra_thue10;
								}
							}
							
							if( giatritra_thue5 > 0 )
							{
								query = "insert ERP_DONDATHANGNPP_TICHLUY_TRATL( dondathangID, DKKMID, SOXUAT, TONGGIATRI, SPID, SOLUONG, VAT, tienchuaVAT ) " + 
										" values( '" + this.id + "', '" + DKKMTICHLUY_FK + "', '1', '" + giatritra_thue5 + "', NULL, NULL, 5, " + giatritra_thue5 + " / 1.05 )";
								System.out.println("::: CHEN TRA KM VAT 5%: " + query );
								db.update(query);
								
								coTL = true;
							}
							
							if( giatritra_thue10 > 0 )
							{
								query = "insert ERP_DONDATHANGNPP_TICHLUY_TRATL( dondathangID, DKKMID, SOXUAT, TONGGIATRI, SPID, SOLUONG, VAT, tienchuaVAT ) " + 
										" values( '" + this.id + "', '" + DKKMTICHLUY_FK + "', '1', '" + giatritra_thue10 + "', NULL, NULL, 10,  " + giatritra_thue10 + " / 1.1 )";
								System.out.println("::: CHEN TRA KM VAT 10%: " + query );
								db.update(query);
								
								coTL = true;
							}
						}
					}
					
					if(exit)
						break;
					
				}
				rs.close();
				
				if( coTL ) //Nếu có tích lũy này thì không được KM nào hết
				{
					query = " update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '', chietkhau_DLN = 0, chietkhau = 0 " + 
							" where dondathang_fk = '" + this.id + "' ";
					db.update(query);
					
					query = "delete  ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + this.id + "' and tichluyGD = 0 ";
					db.update(query);
				}
				
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public void ApTichLuy_TheoGiaiDoan(String ddhId, dbutils db, String dung_db_moi) 
	{
		String query = "";
		this.id = ddhId;
		
		query = "delete ERP_DONDATHANGNPP_CTKM_TRAKM where tichluyGD = 1 and dondathangId = '" + this.id + "' ";
		db.update(query);
		
		//Trả tích lũy giai đoạn, hiện tại chỉ có trả SP thôi
		query = "select a.DKKMTICHLUY_FK, ( select NgayDonHang from ERP_DONDATHANGNPP where PK_SEQ = '" + this.id + "' ) as ngaydonhang  "+
				 "from DANGKYKM_TICHLUY_KHACHHANG a inner join DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ  "+
				 "		inner join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ "+
				 "where KHACHHANG_FK = '" + this.khId + "' and c.phanloai = '3' and b.TRANGTHAI = '1' ";
		ResultSet rsDangky = db.get(query);
		boolean coTL = false;
		if( rsDangky != null )
		{
			try 
			{
				while( rsDangky.next() )
				{
					String ngaydonhang = rsDangky.getString("ngaydonhang");
					
					query = "select *,  "+
							 "	( select MAX( muc ) from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk = DT.thuongtl_fk ) as giaidoanMAX, "+
							 "	( case DT.donvi when 2 then ( select top(1) sanpham_fk from TIEUCHITHUONGTL_SPTRA where thuongtl_fk = DT.THUONGTL_FK and muctra = DT.giaidoan ) else NULL end ) as sanphamtra,  "+
							 "	( case DT.donvi when 2 then ( select top(1) soluong from TIEUCHITHUONGTL_SPTRA where thuongtl_fk = DT.THUONGTL_FK and muctra = DT.giaidoan ) else NULL end ) as soluongtra,  "+
							 "	( case DT.donvi when 0 then round( DT.chietkhau * DT.doanhsoDAT / 100.0, 0 ) when 1 then DT.chietkhau when 2 then DT.chietkhau else 0 end ) as giatritra, "+
							 //"	ISNULL( ( select SUM(TONGGIATRI) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = DT.DKKMTICHLUY_FK and muc = DT.giaidoan and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tonggiatriDaTra,  "+
							 //"	ISNULL( ( select SUM(SOLUONG) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = DT.DKKMTICHLUY_FK and muc = DT.giaidoan and SPID is not null and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tongsoluongDaTra "+
							 "	ISNULL( ( select SUM(TONGGIATRI) from ERP_DONDATHANGNPP_CTKM_TRAKM where DKKMID = DT.DKKMTICHLUY_FK and muc = DT.giaidoan and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tonggiatriDaTra,  "+
							 "	ISNULL( ( select SUM(SOLUONG) from ERP_DONDATHANGNPP_CTKM_TRAKM where DKKMID = DT.DKKMTICHLUY_FK and muc = DT.giaidoan and SPID is not null and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '" + this.khId + "' and trangthai != 3 ) ) , 0) as tongsoluongDaTra, "+
							 "	( select top(1) muc from TIEUCHITHUONGTL_TIEUCHI where thuongtl_fk = DT.thuongtl_fk and tungay <= '" + ngaydonhang + "' and denngay >= '" + ngaydonhang + "'  ) as mucseHUONG	" +
							 "from "+
							 "( "+
							 "	select DKKMTICHLUY_FK, c.PK_SEQ as THUONGTL_FK, tc.muc as giaidoan, tc.tumuc, tc.denmuc, tc.soxuatToiDa, tc.soxuatToiDa * tc.denmuc as doanhsoToiDa, "+
							 "		ISNULL ( ( "+
							 "		select SUM( TONGGIATRI ) AS tonggiatri "+
							 "		from "+
							 "		( "+
							 "				select SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as tonggiatri "+
							 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  "+
							 "				where dh.pk_seq != '" + this.id + "' and dh.KHACHHANG_FK = a.KHACHHANG_FK and dh.CS_DUYET = '1' and dh.TRANGTHAI not in ( 0, 3 ) and dh.NgayDonHang >= tc.tungay and dh.NgayDonHang <= tc.denngay "+
							 " 						and dh_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = c.PK_SEQ ) "+
							 " 			union "+
							 " 				select SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as tonggiatri "+
							 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  "+
							 "				where dh.pk_seq = '" + this.id + "' and dh.KHACHHANG_FK = a.KHACHHANG_FK and dh.TRANGTHAI not in ( 3 ) and dh.NgayDonHang >= tc.tungay and dh.NgayDonHang <= tc.denngay "+
							 " 						and dh_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = c.PK_SEQ ) "+
							 " 			union "+
							 "				select SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as tonggiatri "+
							 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  "+
							 "				where dh.pk_seq != '" + this.id + "' and dh.KHACHHANG_FK = a.KHACHHANG_FK and dh.CS_DUYET = '0' and dh.TRANGTHAI != 3 and dh.NgayDonHang >= tc.tungay and dh.NgayDonHang <= tc.denngay "+
							 " 						and dh.pk_seq in ( select DONDATHANGID from ERP_DONDATHANGNPP_CTKM_TRAKM where SPMA is not null and MUC is not null ) "+
							 " 		 ) "+
							 "		 DH	 ), 0 ) doanhsoDAT, "+
							 "		 tc.donvi, tc.chietkhau "+
							 "	from DANGKYKM_TICHLUY_KHACHHANG a inner join DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ "+
							 "			inner join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ "+
							 "			inner join TIEUCHITHUONGTL_TIEUCHI tc on c.PK_SEQ = tc.thuongtl_fk "+
							 "	where KHACHHANG_FK = '" + this.khId + "' and c.TRANGTHAI = '1' and c.phanloai = '3' and DKKMTICHLUY_FK = '" + rsDangky.getString("DKKMTICHLUY_FK") + "' "+
							 ") "+
							 "DT ";
					
					System.out.println(":::TICH LUY THEO GIAI DOAN: " + query );
					ResultSet rs = db.get(query);
					if( rs != null )
					{
						boolean exit = false;
						double doanhsoTL_CongDon = 0;
						try 
						{
							while( rs.next() )
							{
								String DKKMTICHLUY_FK = rs.getString("DKKMTICHLUY_FK");
								int mucseHUONG = rs.getInt("mucseHUONG");
								int giaidoan = rs.getInt("giaidoan");
								double tumuc = rs.getDouble("tumuc");
								double denmuc = rs.getDouble("denmuc");
								int soxuatToiDa = rs.getInt("soxuatToiDa");
								double giatriTRA = rs.getDouble("giatriTRA");
								
								double soluongToiDa = soxuatToiDa * giatriTRA;
								
								double doanhsoDAT = rs.getDouble("doanhsoDAT");
								//int giaidoanMAX = rs.getInt("giaidoanMAX");
								
								//double tonggiatriDaTra = rs.getDouble("tonggiatriDaTra");
								double tongsoluongDaTra = rs.getDouble("tongsoluongDaTra");
								
								//XET XEM GIAI DOAN 1, 2, 3 NAY THOA BAO NHIEU XUAT
								if( ( doanhsoDAT + doanhsoTL_CongDon ) >= tumuc )
								{
									double soXuatDAT = Math.round( ( ( doanhsoDAT + doanhsoTL_CongDon ) / denmuc ) + 0.5 ) ; //Làm tròn lên
									boolean quaXuat_ToiDa = false;
									if( soXuatDAT > soxuatToiDa )
									{
										quaXuat_ToiDa = true;
										soXuatDAT = soxuatToiDa;
									}
									
									double soluongTRA = soXuatDAT * giatriTRA;
									double soluongCONLAI = soluongTRA - tongsoluongDaTra;
									if( soluongCONLAI > soluongToiDa )
										soluongCONLAI = soluongToiDa;
									
									if( soluongCONLAI > 0.0 && ( mucseHUONG == giaidoan ) ) //Đơn hàng thuộc giai đoạn nào thì chỉ được hưởng giai đoạn đó
									{
										/*query = "insert ERP_DONDATHANGNPP_TICHLUY_TRATL( dondathangID, DKKMID, SOXUAT, TONGGIATRI, SPID, SOLUONG, MUC ) " + 
												" values( '" + this.id + "', '" + DKKMTICHLUY_FK + "', '" + soXuatDAT + "', '0', '" + rs.getString("sanphamtra") + "', " + soluongCONLAI + ", '" + giaidoan + "' )";*/
										
										query = "insert ERP_DONDATHANGNPP_CTKM_TRAKM( dondathangID, CTKMID, DKKMID, SOXUAT, TONGGIATRI, SPID, SPMA, SOLUONG, MUC, tichluyGD ) " + 
												"select '" + this.id + "', ( select PK_SEQ from CTKHUYENMAI where thuongtl_fk in (	select TIEUCHITL_FK from DANGKYKM_TICHLUY where PK_SEQ = '" + DKKMTICHLUY_FK + "' ) ) as ctkmId, '" + DKKMTICHLUY_FK + "', '" + soXuatDAT + "', '0', pk_seq, ma, " + soluongCONLAI + ", '" + giaidoan + "', '1' " + 
												" from SANPHAM where pk_seq = '" + rs.getString("sanphamtra") + "' ";
										
										System.out.println("::: CHEN TRA KM TL GD - SP: " + query );
										db.update(query);
										
										coTL = true;
										exit = true;
									}
									
									//Tính doanh số còn dư lại sau khi đã hưởng số xuất tối đa
									if( quaXuat_ToiDa && soluongCONLAI > 0 )
										doanhsoTL_CongDon = ( doanhsoTL_CongDon + doanhsoDAT ) - ( denmuc * soXuatDAT );
									else if( soluongCONLAI <= 0 ) //Tính lượng doanh số còn dư nếu có cho giai đoạn sau
									{
										double doanhsoToiDa = denmuc * soxuatToiDa;
										double conDu = ( doanhsoTL_CongDon + doanhsoDAT ) - doanhsoToiDa;
										
										if( conDu > 0 )  //BANG LUON LUONG CON DU O GIAI DOAN SAU, CHU KO CO CONG DON SE BI SAI
										{
											doanhsoTL_CongDon = conDu;
											//doanhsoTL_CongDon += conDu;
										}
									}
									
									//1 đơn hàng chỉ được trả 1 giai đoạn ==> xác định giai đoạn thỏa tích lũy
									if( exit )
										break;
								}
								else  //Nếu không thỏa, thì tích lũy này sẽ được cộng dồn sang giai đoạn sau
									doanhsoTL_CongDon += doanhsoDAT;
							}
						} 
						catch (Exception e) 
						{
							e.printStackTrace();
						}
					}
					
				}
				rsDangky.close();
				
				/*if( coTL ) //Nếu có tích lũy này thì không được KM nào hết
				{
					query = " update ERP_DONDATHANGNPP_SANPHAM set dongia = dongiaGOC, chietkhau_CSBH = 0, chinhsach_fk = null, chietkhau_KM = 0, schemeCHIETKHAU = '', chietkhau_DLN = 0, chietkhau = 0 " + 
							" where dondathang_fk = '" + this.id + "' ";
					db.update(query);
					
					query = "delete  ERP_DONDATHANGNPP_CTKM_TRAKM where DONDATHANGID = '" + this.id + "' ";
					db.update(query);
				}*/
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
	}
	
	
	public String getTientichluy() {

		if( this.tienTichluy.trim().length() <= 0 )
			return "0";
		
		return this.tienTichluy;
	}


	public void setTientichluy(String tientichluy) {
		
		this.tienTichluy = tientichluy;
	}
	
	public String getTienchietkhau() {

		if( this.tienChietkhau.trim().length() <= 0 )
			return "0";
		
		return this.tienChietkhau;
	}


	public void setTienchietkhau(String tienchietkhau) {
		
		this.tienChietkhau = tienchietkhau;
	}

	
	public Double NoQuaXau() {
		
		return this.NoQuaXau;
	}
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}


	public ResultSet getDdkdSPRs() {

		return this.ddkdSPRs;
	}


	public void setDddkdSPRs(ResultSet ddkdSPRs) {
	
		this.ddkdSPRs = ddkdSPRs;
	}

	
	public String getCapnhatTDV() {
		
		return this.capnhatTDV;
	}

	
	public void setCapnhatTDV(String capnhatTDV) {
		
		this.capnhatTDV = capnhatTDV;
	}

	
	public String[] getSpChiavaodongia() {

		return this.spChiavaodongia;
	}


	public void setSpChiavaodongia(String[] spChiavaodongia) {
		
		this.spChiavaodongia = spChiavaodongia;
	}

	
	public String getLydokhongduyet() {

		return this.lydokhongduyet;
	}


	public void setLydokhongduyet(String lydokhongduyet) {

		this.lydokhongduyet = lydokhongduyet;
	}


	public void XoaKhuyenMai(String schemeXOA)
	{
		//XOA TICH LUY
		String query = "delete ERP_DONDATHANGNPP_TICHLUY_TRATL " +
					   "where DONDATHANGID = '" + this.id + "' and DKKMID in ( select PK_SEQ from DANGKYKM_TICHLUY where TIEUCHITL_FK in ( select PK_SEQ from TIEUCHITHUONGTL where SCHEME = N'" + schemeXOA + "' ) )";
		db.update(query);
		
		query = "delete ERP_DONDATHANGNPP_CTKM_TRAKM " +
				   "where DONDATHANGID = '" + this.id + "' and CTKMID in ( select PK_SEQ from CTKHUYENMAI where SCHEME = N'" + schemeXOA + "' )";
		db.update(query);
	}
	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
}
