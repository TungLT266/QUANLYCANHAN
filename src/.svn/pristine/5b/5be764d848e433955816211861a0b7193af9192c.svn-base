package geso.traphaco.center.util;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.util.List;

public class UtilityKeToan
{
	private String loaiChungTu;
	private String soChungTu;
	private String thang;
	private String nam;
	private String ngayChungTu;
	private String ngayGhiNhan;
	private String taiKhoanNo_FK;
	private String taiKhoanCo_FK;
	private String soHieuTaiKhoanNo;
	private String soHieuTaiKhoanCo;
	private String noiDungNhapXuatNo_FK;
	private String noiDungNhapXuatCo_FK;
	private String no;
	private String co;
	private String doiTuongNo;
	private String doiTuongCo;
	private String maDoiTuongNo;
	private String maDoiTuongCo;
	private String loaiDoiTuongNo;
	private String loaiDoiTuongCo;
	private String tiGia_FK;
	private String tienTeGoc_FK;
	private String soLuongNo;
	private String soLuongCo;
	private String donGiaNo;
	private String donGiaCo;
	private String donGiaNoNT;
	private String donGiaCoNT;
	private String tongGiaTri;
	private String tongGiaTriNT;
	private String khoanMucNo;
	private String khoanMucCo;
	private String dienGiaiNo;
	private String dienGiaiCo;
	private String maChungTuNo;
	private String maChungTuCo;
	private String maSanPhamNo;
	private String maSanPhamCo;
	private String tenSanPhamNo;
	private String tenSanPhamCo;
	private String donViTinhNo;
	private String donViTinhCo;
	private String soLoNo;
	private String soLoCo;
	private String ngayHetHanNo;
	private String ngayHetHanCo;
	private String kenhBanHangNo_FK;
	private String kenhBanHangCo_FK;
	private String khoXuatNo_FK;
	private String khoXuatCo_FK;
	private String khoNhanNo_FK;
	private String khoNhanCo_FK;
	private String khoanMucChiPhiNo_FK;
	private String khoanMucChiPhiCo_FK;
	private String vatNo;
	private String vatCo;
	private String isNPPNo;
	private String isNPPCo;
	//Chưa get set
	private String maHangNo;
	private String maHangCo;
	private String tenHangNo;
	private String tenHangCo;
	
	private String tienHangNo;
	private String tienHangCo;
	private String maHoaDonNo;
	private String maHoaDonCo;
	private String mauHoaDonNo;
	private String mauHoaDonCo;
	
	private String kyHieuNo;
	private String kyHieuCo;
	private String ngayHoaDonNo;
	private String ngayHoaDonCo;
	private String tenNhaCungCapNo;
	private String tenNhaCungCapCo;
	private String maSoThueNo;
	private String maSoThueCo;
	private String thueSuatNo;
	private String thueSuatCo;
	private String tienThueNo;
	private String tienThueCo;
	private String maFast_DTNo;
	private String maFast_DTCo;
	private String ten_DTNo;
	private String ten_DTCo;
	private String ten_PBNo;
	private String ten_PBCo;
	private String ten_KBHNo;
	private String ten_KBHCo;
	private String ten_VVNo;
	private String ten_VVCo;
	private String ten_diaBanNo;
	private String ten_diaBanCo;
	private String ten_tinhThanhNo;
	private String ten_tinhThanhCo;
	private String ten_benhVienNo;
	private String ten_benhVienCo;
	private String ten_sanPhamNo;
	private String ten_sanPhamCo;
	private String dienGiai_CTNo;
	private String dienGiai_CTCo;
	private String bangDoiTuongNo;
	private String bangDoiTuongCo;
	private String sanPham_FKNo;
	private String sanPham_FKCo;
	private String soHoaDonNo;
	private String soHoaDonCo;

	public UtilityKeToan()
	{
		this.loaiChungTu = "";
		this.soChungTu = "";
		this.thang = "";
		this.nam = "";
		this.ngayChungTu = "";
		this.ngayGhiNhan = "";
		this.taiKhoanNo_FK = "";
		this.taiKhoanCo_FK = "";
		this.soHieuTaiKhoanNo = "";
		this.soHieuTaiKhoanCo = "";
		this.noiDungNhapXuatNo_FK = "";
		this.noiDungNhapXuatCo_FK = "";
		this.no = "0";
		this.co = "0";
		this.doiTuongNo = "";
		this.doiTuongCo = "";
		this.maDoiTuongNo = "";
		this.maDoiTuongCo = "";
		this.loaiDoiTuongNo = "";
		this.loaiDoiTuongCo = "";
		this.tiGia_FK = "1";
		this.tienTeGoc_FK = "100000";
		this.soLuongNo = "";
		this.soLuongCo = "";
		this.donGiaNo = "";
		this.donGiaCo = "";
		this.donGiaNoNT = "";
		this.donGiaCoNT = "";
		this.tongGiaTri = "";
		this.tongGiaTriNT = "";
		this.khoanMucNo = "";
		this.khoanMucCo = "";
		this.dienGiaiNo = "";
		this.dienGiaiCo = "";
		this.maChungTuNo = "";
		this.maChungTuCo = "";
		this.maSanPhamNo = "";
		this.maSanPhamCo = "";
		this.tenSanPhamNo = "";
		this.tenSanPhamCo = "";
		this.donViTinhNo = "";
		this.donViTinhCo = "";
		this.soLoNo = "";
		this.soLoCo = "";
		this.ngayHetHanNo = "";
		this.ngayHetHanCo = "";
		this.kenhBanHangNo_FK = "";
		this.kenhBanHangCo_FK = "";
		this.khoXuatNo_FK = "";
		this.khoXuatCo_FK = "";
		this.khoNhanNo_FK = "";
		this.khoNhanCo_FK = "";
		this.khoanMucChiPhiNo_FK = "";
		this.khoanMucChiPhiCo_FK = "";
		this.vatNo = "";
		this.vatCo = "";
		this.isNPPNo = "0";
		this.isNPPCo = "0";
		this.maHangNo = "";
		this.maHangCo = "";
		this.tenHangNo = "";
		this.tenHangCo = "";
		this.maHoaDonNo = "";
		this.maHoaDonCo = "";
		this.mauHoaDonNo = "";
		this.mauHoaDonCo = "";
		
		this.kyHieuNo = "";
		this.kyHieuCo = "";
		this.ngayHoaDonNo = "";
		this.ngayHoaDonCo = "";
		this.tenNhaCungCapNo = "";
		this.tenNhaCungCapCo = "";
		this.maSoThueNo = "";
		this.maSoThueCo = "";
		this.thueSuatNo = "";
		this.thueSuatCo = "";
		this.tienThueNo = "";
		this.tienThueCo = "";
		
		this.maFast_DTNo = "";
		this.maFast_DTCo = "";
		this.ten_DTNo = "";
		this.ten_DTCo = "";
		this.ten_PBNo = "";
		this.ten_PBCo = "";
		this.ten_KBHNo = "";
		this.ten_KBHCo = "";
		this.ten_VVNo = "";
		this.ten_VVCo = "";
		this.ten_diaBanNo = "";
		this.ten_diaBanCo = "";
		this.ten_tinhThanhNo = "";
		this.ten_tinhThanhCo = "";
		this.ten_benhVienNo = "";
		this.ten_benhVienCo = "";
		this.ten_sanPhamNo = "";
		this.ten_sanPhamCo = "";
		this.dienGiai_CTNo = "";
		this.dienGiai_CTCo = "";
		this.bangDoiTuongNo = "";
		this.bangDoiTuongCo = "";
		this.sanPham_FKNo = "";
		this.sanPham_FKCo = "";
		this.soHoaDonNo = "";
		this.soHoaDonCo = "";
	}
	
	public UtilityKeToan(String loaiChungTu, String soChungTu, String ngayChungTu, String ngayGhiNhan
			, String taiKhoanNo_FK, String taiKhoanCo_FK, String no, String co
			, String tiGia_FK, String tienTeGoc_FK
			)
	{
		this();
		this.loaiChungTu = loaiChungTu;
		this.soChungTu = soChungTu;
		this.ngayChungTu = ngayChungTu;
		this.ngayGhiNhan = ngayGhiNhan;
		this.taiKhoanNo_FK = taiKhoanNo_FK;
		this.taiKhoanCo_FK = taiKhoanCo_FK;
		this.no = no;
		this.co = co;
		this.tiGia_FK = tiGia_FK;
		this.tienTeGoc_FK = tienTeGoc_FK;
		this.tongGiaTri=no;
		this.tongGiaTriNT=no;
	}
	
	public UtilityKeToan(String loaiChungTu, String soChungTu, String ngayChungTu, String ngayGhiNhan
			, String taiKhoanNo_FK, String taiKhoanCo_FK, String no, String co
			, String tiGia_FK, String tienTeGoc_FK
			, String doiTuongNo, String maDoiTuongNo, String doiTuongCo, String maDoiTuongCo
			)
	{
		this(loaiChungTu, soChungTu, ngayChungTu, ngayGhiNhan
				, taiKhoanNo_FK, taiKhoanCo_FK, no, co
				, tiGia_FK, tienTeGoc_FK
				);
		this.doiTuongNo = doiTuongNo;
		this.doiTuongCo = doiTuongCo;
		this.maDoiTuongNo = maDoiTuongNo;
		this.maDoiTuongCo = maDoiTuongCo;
	}
	
	public UtilityKeToan(String loaiChungTu, String soChungTu, String ngayChungTu, String ngayGhiNhan
			, String taiKhoanNo_FK, String taiKhoanCo_FK, String no, String co
			, String tiGia_FK, String tienTeGoc_FK, String thang, String nam
			, String doiTuongNo, String maDoiTuongNo, String doiTuongCo, String maDoiTuongCo
			)
	{
		this(loaiChungTu, soChungTu, ngayChungTu, ngayGhiNhan
				, taiKhoanNo_FK, taiKhoanCo_FK, no, co
				, tiGia_FK, tienTeGoc_FK
				, doiTuongNo, maDoiTuongNo, doiTuongCo, maDoiTuongCo
				);
		
		this.thang = thang;
		this.nam = nam;
	}
	
	public UtilityKeToan(String loaiChungTu, String soChungTu, String ngayChungTu, String ngayGhiNhan
			, String taiKhoanNo_FK, String taiKhoanCo_FK, String no, String co
			, String tiGia_FK, String tienTeGoc_FK, String thang, String nam
			, String doiTuongNo, String maDoiTuongNo, String doiTuongCo, String maDoiTuongCo
			, String maChungTu, String dienGiai
			)
	{
		this(loaiChungTu, soChungTu, ngayChungTu, ngayGhiNhan
				, taiKhoanNo_FK, taiKhoanCo_FK, no, co
				, tiGia_FK, tienTeGoc_FK, thang, nam
				, doiTuongNo, maDoiTuongNo, doiTuongCo, maDoiTuongCo
				);
		
		this.maChungTuNo = this.maChungTuCo = maChungTu;
		this.dienGiaiNo = this.dienGiaiCo = dienGiai;
	}
	

	public String HuyUpdate_TaiKhoan(Idbutils db, String soChungTu, String loaiChungTu)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String msg = checkNgayKhoaSoThang(db);
		
	if (msg.trim().length() > 0)
			return msg;
		
		//Tạm thời chỉ xóa phát sinh kế toán
		String query = 
			"delete erp_PhatSinhKeToan\n" +
			"where soChungTu = " + soChungTu + " and loaiChungTu in ( "+loaiChungTu+" )\n";
		System.out.println("xoa hach toan: \n" + query + "\n----------------------------------------");
		try {
			if (!db.update(query))
				return "HUTK1.1 Không hủy được định khoản";
		} catch (Exception e) {
			e.printStackTrace();
		}
			
		return "";
	}
	
	public String Update_TaiKhoanByTaiKhoan_FK(Idbutils db)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		msg = kiemTraDuLieu(db);
		if (msg.trim().length() > 0)
			return msg;
		
		setParamNull();
		
		boolean isNo = false;
		//GHI CO
		/*if(Float.parseFloat(_CO) != 0) */
		{
			msg = dinhKhoan(db, isNo);
			if (msg.trim().length() > 0)
			{
				msg = "UDTK1.1Không thể định khoản\n" + msg;
				return msg;
			}
		}
		
		//GHI NO
		/*if(Float.parseFloat(_NO) != 0) */
		{
			isNo = true;
			msg = dinhKhoan(db, isNo);
			if (msg.trim().length() > 0)
			{
				msg = "UDTK1.2Không thể định khoản\n" + msg;
				return msg;
			}
		}
		
		return msg;
	}
	
	//Không ghi nợ / hoặc có với tài khoản 411
	public String Update_TaiKhoanByTaiKhoan_FK_DauKy(Idbutils db)
	{
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String msg = Check_NgayNghiepVu_KeToan(db, thang, nam);
		if (msg.trim().length() > 0)
		{
			msg = "1.0 Không thể cập nhật tài khoản kế toán " + msg;
			return msg;
		}
		

		
		msg = kiemTraDuLieu(db);
		if (msg.trim().length() > 0)
			return msg;
		
		setParamNull();
		
		boolean isNo = false;
		//GHI CO
		if (!this.soHieuTaiKhoanCo.startsWith("411"))
		{
			msg = dinhKhoan(db, isNo);
			if (msg.trim().length() > 0)
			{
				msg = "UDTK1.1Không thể định khoản\n" + msg;
				return msg;
			}
		}
		
		//GHI NO
		if (!this.soHieuTaiKhoanNo.startsWith("411"))
		{
			msg = dinhKhoan(db, isNo);
			if (msg.trim().length() > 0)
			{
				msg = "UDTK1.1Không thể định khoản\n" + msg;
				return msg;
			}
		}
		
		return msg;
	}
	

	public String Update_TaiKhoanBySoHieu(Idbutils db, String npp_fk)
	{
		String msg = getTaiKhoanFK_FromSoHieu(db, npp_fk);
		if (msg.trim().length() > 0)
			return "UDTKBSH1.0 " + msg;

		msg = Update_TaiKhoanByTaiKhoan_FK(db);
		
		return msg;
	}
	
	private String getTaiKhoanFK_FromSoHieu(Idbutils db, String npp_fk)
	{
		String msg = "";
		
		String query = 
			"select n.pk_seq, c.pk_seq \n" +
			"from erp_taiKhoanKT n, erp_taiKhoanKT c \n" +
			"where n.trangThai = 1 and n.npp_fk = " + npp_fk + " and n.soHieuTaiKhoan = '" + this.taiKhoanNo_FK + "'\n" +
			"and c.trangThai = 1 and c.npp_fk = " + npp_fk + " and c.soHieuTaiKhoan = '" + this.taiKhoanCo_FK + "'";
		try {
			ResultSet rs = db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
				{
					this.taiKhoanNo_FK = rs.getString(1);
					this.taiKhoanCo_FK = rs.getString(2);
				}
				rs.close();
			}
			else
				msg = "GTKFKFSH1.0";
		} catch (Exception e) {
			e.printStackTrace();
			msg = "GTKFKFSH1.1";
		}
		
		return msg;
	}
	
	private String kiemTraDuLieu(Idbutils db) {
		String msg = kiemTraDoiTuong(db);
		
		if (this.taiKhoanNo_FK == null || this.taiKhoanNo_FK.trim().length() == 0
				|| this.taiKhoanCo_FK == null || this.taiKhoanCo_FK.trim().length() == 0)
			msg += "Tài khoản nợ / có không hợp lệ"; 
			
		return msg;
	}

	private String kiemTraDoiTuongCo(Idbutils db)
	{
		String msg = "";
		
		//Kiểm tra đối tượng, mã đối tượng với tài khoản có chi tiết
		String query = 
			"select isNull(taiKhoanCoChiTiet, '0') taiKhoanCoChiTiet, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG\n" + 
			", DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTDOANHTHU\n" + 
			", DUNGCHOCOPHIEU, DUNGCHOCONGTYCON, DUNGCHODOITUONGKYQUY \n" + 
			"from erp_taiKhoanKT\n" + 
			"where PK_SEQ = " + this.taiKhoanCo_FK;
		
		ResultSet rs = db.get(query);
		try 
		{
			if(rs.next())
			{
				String taiKhoanCoChiTiet = rs.getString("taiKhoanCoChiTiet");
				if (taiKhoanCoChiTiet.trim().length() != 0 && taiKhoanCoChiTiet.trim().equals("1"))
				{
					String coTTCP = rs.getString("COTTCHIPHI");
					if (coTTCP.trim().equals("1"))
					{
						if (this.khoanMucChiPhiCo_FK == null || this.khoanMucChiPhiCo_FK.trim().length() == 0)
							msg = "Thiếu khoản mục chi phí cho tài khoản nợ";
					}
					else
					{
						if (this.maDoiTuongCo == null || this.maDoiTuongCo.trim().length() == 0)
							msg = "Thiếu đối tượng nợ";
					}
				}
			}
			rs.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		
		return msg;
	}
	
	private String kiemTraDoiTuongNo(Idbutils db)
	{
		String msg = "";
		
		//Kiểm tra đối tượng, mã đối tượng với tài khoản có chi tiết
		String query = 
			"select isNull(taiKhoanCoChiTiet, '0') taiKhoanCoChiTiet, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG\n" + 
			", DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTDOANHTHU\n" + 
			", DUNGCHOCOPHIEU, DUNGCHOCONGTYCON, DUNGCHODOITUONGKYQUY \n" + 
			"from erp_taiKhoanKT\n" + 
			"where PK_SEQ = " + this.taiKhoanNo_FK;
		
		ResultSet rs = db.get(query);
		try 
		{
			if(rs.next())
			{
				String taiKhoanCoChiTiet = rs.getString("taiKhoanCoChiTiet");
				if (taiKhoanCoChiTiet.trim().length() != 0 && taiKhoanCoChiTiet.trim().equals("1"))
				{
					String coTTCP = rs.getString("COTTCHIPHI");
					if (coTTCP.trim().equals("1"))
					{
						if (this.khoanMucChiPhiNo_FK == null || this.khoanMucChiPhiNo_FK.trim().length() == 0)
							msg = "Thiếu khoản mục chi phí cho tài khoản nợ";
					}
					else
					{
						if (this.maDoiTuongNo == null || this.maDoiTuongNo.trim().length() == 0)
							msg = "Thiếu đối tượn nợ";
					}
				}
			}
			rs.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		
		return msg;
	}
	
	private String kiemTraDoiTuong(Idbutils db)
	{
		String msg = kiemTraDoiTuongNo(db) + kiemTraDoiTuongCo(db);
		
		return msg;
	}
	
	private String dinhKhoan(Idbutils db, boolean isNo) {
		
		String msg = ghiNoCo(db, isNo);
		if (msg.trim().length() > 0)
		{
			msg = "DK1.1";
			return msg;
		}
		
		msg = ghiPhatSinhNoCo(db, isNo);
		if (msg.trim().length() > 0)
		{
			msg = "DK1.2";
			return msg;
		}
	return "";
}

	private String ghiPhatSinhNoCo(Idbutils db, boolean isNo) {
		String query = 
			"insert ERP_PHATSINHKETOAN (ngaychungtu, ngayghinhan, loaichungtu, sochungtu\n" +
			", taikhoan_fk, taikhoandoiung_fk, NOIDUNGNHAPXUAT_FK, NO\n" +
			", CO, DOITUONG,  MADOITUONG, LOAIDOITUONG\n" +
			", SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT\n" +
			", TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC\n" +
			", khoanMucChiPhi_FK, VAT, DIENGIAI , MACHUNGTU\n" +
			", ISNPP, mahang, tenhang, donvi\n" +
			", KBH_FK, maHoaDon, mauHoaDon, kyHieu\n" +
			", soHoaDon, ngayHoaDon, tenNCC, mst\n" +
			", thueSuat, tienThue, MAFAST_DT, TEN_DT\n" +
			", TEN_PB, TEN_KBH, TEN_VV, TEN_DIABAN\n" +
			", TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT\n" +
			", BANGDOITUONG, SANPHAM_FK, tienHang) \n";

		String msg = "";
		if (isNo == false)//Ghi co'
			query += 
				"values ( '" + this.ngayChungTu + "', '" + this.ngayGhiNhan + "', N'" + this.loaiChungTu + "', " + this.soChungTu + "\n" +
				", '" + this.taiKhoanCo_FK + "', '" + this.taiKhoanNo_FK + "', " + this.noiDungNhapXuatCo_FK + ", '0'\n" +
				", " + this.co + ", N'" + this.doiTuongCo + "', N'" + this.maDoiTuongCo + "', '" + this.loaiDoiTuongCo + "'\n" +
				", '" + this.soLuongCo + "', " + this.donGiaCo + ", '" + this.tienTeGoc_FK + "', " + this.donGiaCoNT + "\n" +
				", '" + this.tiGia_FK + "', " + this.tongGiaTri + ", " + this.tongGiaTriNT + ", N'" + this.khoanMucCo + "'\n" +
				", " + this.khoanMucChiPhiCo_FK + ", '" + this.vatCo+ "', N'" + this.dienGiaiCo + "', '" + this.maChungTuCo + "'\n" +
				", " + this.isNPPCo+ ", N'" + this.maHangCo + "', N'" + this.tenHangCo + "', '" + this.donViTinhCo + "'\n" +
				", " + this.kenhBanHangCo_FK + ", '" + this.maHoaDonCo + "', '" + this.mauHoaDonCo + "', '" + this.kyHieuCo + "'\n" +
				", '" + this.soHoaDonCo + "', '" + this.ngayHoaDonCo + "', '" + this.tenNhaCungCapCo + "', '" + this.maSoThueCo + "'\n" +
				", '" + this.thueSuatCo + "', '" + this.tienThueCo + "', '" + this.maFast_DTCo + "', N'" + this.ten_DTCo + "'\n" +
				", '" + this.ten_PBCo + "', '" + this.ten_KBHCo + "', '" + this.ten_VVCo + "', '" + this.ten_diaBanCo + "'\n" +
				", '" + this.ten_tinhThanhCo + "', '" + this.ten_benhVienCo + "', '" + this.ten_sanPhamCo + "', N'" + this.dienGiai_CTCo + "'\n" +
				", '" + this.bangDoiTuongCo + "', '" + this.sanPham_FKCo + "', '" + this.tienHangCo + "') ";
		else
			query += 
				"values ( '" + this.ngayChungTu + "', '" + this.ngayGhiNhan + "', N'" + this.loaiChungTu + "', " + this.soChungTu + "\n" +
				", '" + this.taiKhoanNo_FK + "', '" + this.taiKhoanCo_FK + "', " + this.noiDungNhapXuatNo_FK + ", " + this.no + "\n" +
				", '0', N'" + this.doiTuongNo + "', N'" + this.maDoiTuongNo + "', '" + this.loaiDoiTuongNo + "'\n" +
				", '" + this.soLuongNo + "', " + this.donGiaNo + ", '" + this.tienTeGoc_FK + "', " + this.donGiaNoNT + "\n" +
				", '" + this.tiGia_FK + "', " + this.tongGiaTri + ", " + this.tongGiaTriNT + ", N'" + this.khoanMucNo + "'\n" +
				", " + this.khoanMucChiPhiNo_FK + ", '" + this.vatNo+ "', N'" + this.dienGiaiNo + "', '" + this.maChungTuNo + "'\n" +
				", " + this.isNPPNo+ ", N'" + this.maHangNo + "', N'" + this.tenHangNo + "', '" + this.donViTinhNo + "'\n" +
				", " + this.kenhBanHangNo_FK + ", '" + this.maHoaDonNo + "', '" + this.mauHoaDonNo + "', '" + this.kyHieuNo + "'\n" +
				", '" + this.soHoaDonNo + "', '" + this.ngayHoaDonNo + "', '" + this.tenNhaCungCapNo + "', '" + this.maSoThueNo + "'\n" +
				", '" + this.thueSuatNo + "', '" + this.tienThueNo + "', '" + this.maFast_DTNo + "', N'" + this.ten_DTNo + "'\n" +
				", '" + this.ten_PBNo + "', '" + this.ten_KBHNo + "', '" + this.ten_VVNo + "', '" + this.ten_diaBanNo + "'\n" +
				", '" + this.ten_tinhThanhNo + "', '" + this.ten_benhVienNo + "', '" + this.ten_sanPhamNo + "', N'" + this.dienGiai_CTNo + "'\n" +
				", '" + this.bangDoiTuongNo + "', '" + this.sanPham_FKNo + "', '" + this.tienHangNo + "') ";
		
		query = query.replaceAll("N'null'", "null");
		query = query.replaceAll("N'NULL'", "null");	
		query = query.replaceAll("'null'", "null");
		query = query.replaceAll("'NULL'", "null");
		query = query.replaceAll(",,", ", null,");
		query = query.replaceAll(", ,", ", null,");
		
		System.out.println("3.Cap nhat ERP_PHATSINHKETOAN: " + query );
		if(!db.update(query))
		{
			msg = "3.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		return "";
	}

	private void setParamNull() {
		try{
			if (this.loaiChungTu != null && this.loaiChungTu.trim().length() == 0) this.loaiChungTu = null;
			if (this.soChungTu != null && this.soChungTu.trim().length() == 0) this.soChungTu = null;
			if (this.thang != null && this.thang.trim().length() == 0) this.thang = null;
			if (this.nam != null && this.nam.trim().length() == 0) this.nam = null;
			if (this.ngayChungTu != null && this.ngayChungTu.trim().length() == 0) this.ngayChungTu = null;
			if (this.ngayGhiNhan != null && this.ngayGhiNhan.trim().length() == 0) this.ngayGhiNhan = null;
			if (this.taiKhoanNo_FK != null && this.taiKhoanNo_FK.trim().length() == 0) this.taiKhoanNo_FK = null;
			if (this.taiKhoanCo_FK != null && this.taiKhoanCo_FK.trim().length() == 0) this.taiKhoanCo_FK = null;
			if (this.soHieuTaiKhoanNo != null && this.soHieuTaiKhoanNo.trim().length() == 0) this.soHieuTaiKhoanNo = null;
			if (this.soHieuTaiKhoanCo != null && this.soHieuTaiKhoanCo.trim().length() == 0) this.soHieuTaiKhoanCo = null;
			if (this.noiDungNhapXuatNo_FK != null && this.noiDungNhapXuatNo_FK.trim().length() == 0) this.noiDungNhapXuatNo_FK = null;
			if (this.noiDungNhapXuatCo_FK != null && this.noiDungNhapXuatCo_FK.trim().length() == 0) this.noiDungNhapXuatCo_FK = null;
			if (this.no != null && this.no.trim().length() == 0) this.no = "0";
			if (this.co != null && this.co.trim().length() == 0) this.co = "0";
			if (this.doiTuongNo != null && this.doiTuongNo.trim().length() == 0) this.doiTuongNo = null;
			if (this.doiTuongCo != null && this.doiTuongCo.trim().length() == 0) this.doiTuongCo = null;
			if (this.maDoiTuongNo != null && this.maDoiTuongNo.trim().length() == 0) this.maDoiTuongNo = null;
			if (this.maDoiTuongCo != null && this.maDoiTuongCo.trim().length() == 0) this.maDoiTuongCo = null;
			if (this.loaiDoiTuongNo != null && this.loaiDoiTuongNo.trim().length() == 0) this.loaiDoiTuongNo = null;
			if (this.loaiDoiTuongCo != null && this.loaiDoiTuongCo.trim().length() == 0) this.loaiDoiTuongCo = null;
			if (this.tiGia_FK != null && this.tiGia_FK.trim().length() == 0) this.tiGia_FK = null;
			if (this.tienTeGoc_FK != null && this.tienTeGoc_FK.trim().length() == 0) this.tienTeGoc_FK = null;
			if (this.soLuongNo != null && this.soLuongNo.trim().length() == 0) this.soLuongNo = null;
			if (this.soLuongCo != null && this.soLuongCo.trim().length() == 0) this.soLuongCo = null;
			if (this.donGiaNo != null && this.donGiaNo.trim().length() == 0) this.donGiaNo = null;
			if (this.donGiaCo != null && this.donGiaCo.trim().length() == 0) this.donGiaCo = null;
			if (this.donGiaNoNT != null && this.donGiaNoNT.trim().length() == 0) this.donGiaNoNT = null;
			if (this.donGiaCoNT != null && this.donGiaCoNT.trim().length() == 0) this.donGiaCoNT = null;
			if (this.tongGiaTri != null && this.tongGiaTri.trim().length() == 0) this.tongGiaTri = null;
			if (this.tongGiaTriNT != null && this.tongGiaTriNT.trim().length() == 0) this.tongGiaTriNT = null;
			if (this.khoanMucNo != null && this.khoanMucNo.trim().length() == 0) this.khoanMucNo = null;
			if (this.khoanMucCo != null && this.khoanMucCo.trim().length() == 0) this.khoanMucCo = null;
			if (this.dienGiaiNo != null && this.dienGiaiNo.trim().length() == 0) this.dienGiaiNo = null;
			if (this.dienGiaiCo != null && this.dienGiaiCo.trim().length() == 0) this.dienGiaiCo = null;
			if (this.maChungTuNo != null && this.maChungTuNo.trim().length() == 0) this.maChungTuNo = null;
			if (this.maChungTuCo.trim().length() == 0) this.maChungTuCo = null;
			if (this.maChungTuNo != null && this.maSanPhamNo.trim().length() == 0) this.maSanPhamNo = null;
			if (this.maSanPhamCo != null && this.maSanPhamCo.trim().length() == 0) this.maSanPhamCo = null;
			if (this.tenSanPhamNo != null && this.tenSanPhamNo.trim().length() == 0) this.tenSanPhamNo = null;
			if (this.tenSanPhamCo != null && this.tenSanPhamCo.trim().length() == 0) this.tenSanPhamCo = null;
			if (this.donViTinhNo != null && this.donViTinhNo.trim().length() == 0) this.donViTinhNo = null;
			if (this.donViTinhCo != null && this.donViTinhCo.trim().length() == 0) this.donViTinhCo = null;
			if (this.soLoNo != null && this.soLoNo.trim().length() == 0) this.soLoNo = null;
			if (this.soLoCo != null && this.soLoCo.trim().length() == 0) this.soLuongCo = null;
			if (this.ngayHetHanNo != null && this.ngayHetHanNo.trim().length() == 0) this.ngayHetHanNo = null;
			if (this.ngayHetHanCo != null && this.ngayHetHanCo.trim().length() == 0) this.ngayHetHanCo = null;
			if (this.kenhBanHangNo_FK != null && this.kenhBanHangNo_FK.trim().length() == 0) this.kenhBanHangNo_FK = null;
			if (this.kenhBanHangCo_FK != null && this.kenhBanHangCo_FK.trim().length() == 0) this.kenhBanHangCo_FK = null;
			if (this.khoXuatNo_FK != null && this.khoXuatNo_FK.trim().length() == 0) this.khoXuatNo_FK = null;
			if (this.khoXuatCo_FK != null && this.khoXuatCo_FK.trim().length() == 0) this.khoXuatCo_FK = null;
			if (this.khoNhanNo_FK != null && this.khoNhanNo_FK.trim().length() == 0) this.khoNhanNo_FK = null;
			if (this.khoNhanCo_FK != null && this.khoNhanCo_FK.trim().length() == 0) this.khoNhanNo_FK = null;
			if (this.khoanMucChiPhiNo_FK != null && this.khoanMucChiPhiNo_FK != null && this.khoanMucChiPhiNo_FK.trim().length() == 0) this.khoanMucChiPhiNo_FK = null;
			if (this.khoanMucChiPhiCo_FK != null && this.khoanMucChiPhiCo_FK != null && this.khoanMucChiPhiCo_FK.trim().length() == 0) this.khoanMucChiPhiCo_FK = null;
			if (this.vatNo != null && this.vatNo.trim() != null && this.vatNo.trim().length() == 0) this.vatNo = null;
			if (this.vatCo != null && this.vatCo.trim().length() == 0) this.vatCo = null;
			if (this.isNPPNo != null && this.isNPPNo.trim().length() == 0) this.isNPPNo = null;
			if (this.isNPPCo != null && this.isNPPCo.trim().length() == 0) this.isNPPCo = null;
			if (this.maHangNo != null && this.maHangNo.trim().length() == 0) this.maHangNo = null;
			if (this.maHangCo != null && this.maHangCo.trim().length() == 0) this.maHangCo = null;
			if (this.tenHangNo != null && this.tenHangNo.trim().length() == 0) this.tenHangNo = null;
			if (this.tenHangCo != null && this.tenHangCo.trim().length() == 0) this.tenHangCo = null;
			if (this.maHoaDonNo != null && this.maHoaDonNo.trim().length() == 0) this.maHoaDonNo = null;
			if (this.maHoaDonCo != null && this.maHoaDonCo.trim().length() == 0) this.maHoaDonCo = null;
			if (this.mauHoaDonNo != null && this.mauHoaDonNo.trim().length() == 0) this.mauHoaDonNo = null;
			if (this.mauHoaDonCo != null && this.mauHoaDonCo.trim().length() == 0) this.mauHoaDonCo = null;
			
			if (this.kyHieuNo != null && this.kyHieuNo.trim().length() == 0) this.kyHieuNo = null;
			if (this.kyHieuCo != null && this.kyHieuCo.trim().length() == 0) this.kyHieuCo = null;
			if (this.ngayHoaDonNo != null && this.ngayHoaDonNo.trim().length() == 0) this.ngayHoaDonNo = null;
			if (this.ngayHoaDonCo != null && this.ngayHoaDonCo.trim().length() == 0) this.ngayHoaDonCo = null;
			if (this.tenNhaCungCapNo != null && this.tenNhaCungCapNo.trim().length() == 0) this.ngayHoaDonCo = null;
			if (this.tenNhaCungCapCo != null && this.tenNhaCungCapCo.trim().length() == 0) this.ngayHoaDonCo = null;
			if (this.maSoThueNo != null && this.maSoThueNo.trim().length() == 0) this.maSoThueNo = null;
			if (this.maSoThueCo != null && this.maSoThueCo.trim().length() == 0) this.maSoThueCo = null;
			if (this.thueSuatNo != null && this.thueSuatNo.trim().length() == 0) this.thueSuatNo = null;
			if (this.thueSuatCo != null && this.thueSuatCo.trim().length() == 0) this.thueSuatCo = null;
			if (this.tienThueNo != null && this.tienThueNo.trim().length() == 0) this.tienThueNo = null;
			if (this.tienThueCo != null && this.tienThueCo.trim().length() == 0) this.tienThueCo = null;
			
			if (this.maFast_DTNo != null && this.maFast_DTNo.trim().length() == 0) this.maFast_DTNo = null;
			if (this.maFast_DTCo != null && this.maFast_DTCo.trim().length() == 0) this.maFast_DTCo = null;
			if (this.ten_DTNo != null && this.ten_DTNo.trim().length() == 0) this.ten_DTNo = null;
			if (this.ten_DTCo != null && this.ten_DTCo.trim().length() == 0) this.ten_DTCo = null;
			if (this.ten_PBNo != null && this.ten_PBNo.trim().length() == 0) this.ten_PBNo = null;
			if (this.ten_PBCo != null && this.ten_PBCo.trim().length() == 0) this.ten_PBCo = null;
			if (this.ten_KBHNo != null && this.ten_KBHNo.trim().length() == 0) this.ten_KBHNo = null;
			if (this.ten_KBHCo != null && this.ten_KBHCo.trim().length() == 0) this.ten_KBHCo = null;
			if (this.ten_VVNo != null && this.ten_VVNo.trim().length() == 0) this.ten_VVNo = null;
			if (this.ten_VVCo != null && this.ten_VVCo.trim().length() == 0) this.ten_VVCo = null;
			if (this.ten_diaBanNo != null && this.ten_diaBanNo.trim().length() == 0) this.ten_diaBanNo = null;
			if (this.ten_diaBanCo != null && this.ten_diaBanCo.trim().length() == 0) this.ten_diaBanCo = null;
			if (this.ten_tinhThanhNo != null && this.ten_tinhThanhNo.trim().length() == 0) this.ten_tinhThanhNo = null;
			if (this.ten_tinhThanhCo != null && this.ten_tinhThanhCo.trim().length() == 0) this.ten_tinhThanhCo = null;
			if (this.ten_benhVienNo != null && this.ten_benhVienNo.trim().length() == 0) this.ten_benhVienNo = null;
			if (this.ten_benhVienCo != null && this.ten_benhVienCo.trim().length() == 0) this.ten_benhVienCo = null;
			if (this.ten_sanPhamNo != null && this.ten_sanPhamNo.trim().length() == 0) this.ten_sanPhamNo = null;
			if (this.ten_sanPhamCo != null && this.ten_sanPhamCo.trim().length() == 0) this.ten_sanPhamCo = null;
			if (this.dienGiai_CTNo != null && this.dienGiai_CTNo.trim().length() == 0) this.dienGiai_CTNo = null;
			if (this.dienGiai_CTCo != null && this.dienGiai_CTCo.trim().length() == 0) this.dienGiai_CTCo = null;
			if (this.bangDoiTuongNo != null && this.bangDoiTuongNo.trim().length() == 0) this.bangDoiTuongNo = null;
			if (this.bangDoiTuongCo != null && this.bangDoiTuongCo.trim().length() == 0) this.bangDoiTuongCo = null;
			if (this.sanPham_FKNo != null && this.sanPham_FKNo.trim().length() == 0) this.sanPham_FKNo = null;
			if (this.sanPham_FKCo != null && this.sanPham_FKCo.trim().length() == 0) this.sanPham_FKCo = null;
			if (this.soHoaDonNo != null && this.soHoaDonNo.trim().length() == 0) this.soHoaDonNo = null;
			if (this.soHoaDonCo != null && this.soHoaDonCo.trim().length() == 0) this.soHoaDonCo = null;
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String ghiNoCo(Idbutils db, boolean isNo) {
		String msg = "";
		String query = "";
		if (isNo == true)
			query = getQueryNoCo_No(db);
		else
			query = getQueryNoCo_Co(db);
		
		if(!db.update(query))
		{
			msg = "1.Không thể cập nhật tài khoản kế toán " + query;
			return msg;
		}
		
		return "";
	}

	private String getQueryNoCo_Co(Idbutils db) {
		int sodong = getSoDongTaiKhoanNoCo(db, this.taiKhoanCo_FK, this.tienTeGoc_FK, this.thang, this.nam);
		String query = "";
		if(sodong > 0) //daco
		{
			query = getUpdateTaiKhoanNoCoQuery(this.taiKhoanCo_FK, "0", this.tongGiaTri, this.tongGiaTriNT
					, "0", this.tongGiaTriNT, this.tienTeGoc_FK, this.thang, this.nam);
		}
		else
		{
			query = getInsertNoCoQuery(this.taiKhoanCo_FK, "0", this.tongGiaTri, this.tongGiaTriNT
					, "0", this.tongGiaTriNT, this.tienTeGoc_FK, this.thang, this.nam);
		}
		return query;
	}

	private String getQueryNoCo_No(Idbutils db) {
		int sodong = getSoDongTaiKhoanNoCo(db, this.taiKhoanNo_FK, this.tienTeGoc_FK, this.thang, this.nam);
		String query = "";
		if(sodong > 0) //daco
		{
			query = getUpdateTaiKhoanNoCoQuery(this.taiKhoanNo_FK, this.tongGiaTri, "0", this.tongGiaTriNT
					, this.tongGiaTriNT, "0", this.tienTeGoc_FK, this.thang, this.nam);
		}
		else
		{
			query = getInsertNoCoQuery(this.taiKhoanNo_FK, this.tongGiaTri, "0", this.tongGiaTriNT
					, this.tongGiaTriNT, "0", this.tienTeGoc_FK, this.thang, this.nam);
		}
		return query;
	}
	
	private String getInsertNoCoQuery(String taiKhoan_FK, String giaTriNoVND, String giaTriCoVND, String giaTriNguyenTe
			, String giaTriNoNguyenTe, String giaTriCoNguyenTe, String tienTeGoc_FK, String thang
			, String nam) {
		String query = 
			"insert ERP_TAIKHOAN_NOCO(TAIKHOANKT_FK, GIATRINOVND, GIATRICOVND,  NGUYENTE_FK\n" +
			", GIATRINGUYENTE, GIATRINONGUYENTE, GIATRICONGUYENTE, THANG\n" +
			", NAM) " +
			"select '" + taiKhoan_FK + "', " + giaTriNoVND + ", " + giaTriCoVND + ", '" + tienTeGoc_FK + "'\n" +
			", " + giaTriNguyenTe + ", " + giaTriNoNguyenTe + ", " + giaTriCoNguyenTe + ",'" + thang + "'\n" +
			", '" + nam + "' ";

		return query;
	}

	private String getUpdateTaiKhoanNoCoQuery(String taiKhoan_FK, String giaTriNoVND, String giaTriCoVND, String giaTriNguyenTe
			, String giaTriNoNguyenTe, String giaTriCoNguyenTe, String tienTeGoc_FK, String thang
			, String nam) {
		String query = 
			"update ERP_TAIKHOAN_NOCO \n" +
			"set GIATRINOVND = GIATRINOVND + " + giaTriCoVND + ", GIATRICOVND = GIATRICOVND +" + giaTriCoVND + "\n" +
			", GIATRINGUYENTE = GIATRINGUYENTE + "  + giaTriNguyenTe + "\n" +
			", GIATRINONGUYENTE = GIATRINONGUYENTE + "  + giaTriNoNguyenTe + ", GIATRICONGUYENTE = GIATRICONGUYENTE + " + giaTriCoNguyenTe + "\n" +
			"where taiKhoanKT_FK = '" + taiKhoan_FK + "' and nguyenTe_FK = '" + tienTeGoc_FK + "'\n" +
			" and thang = '" + thang + "' and nam = '" + nam + "'";
		return query;
	}

	private int getSoDongTaiKhoanNoCo(Idbutils db, String taiKhoan_FK, String tienTeGoc_FK, String thang, String nam) {
		String query = 
			"select count(*) as sodong from ERP_TAIKHOAN_NOCO \n" +
			"where taikhoankt_fk = '" + taiKhoan_FK + "' and nguyente_fk = '" + tienTeGoc_FK + "' \n" +
			"and thang = '" + thang + "' and nam = '" + nam + "'";
		
		ResultSet rsTKNo = db.get(query);
		int soDong = 0;
		try 
		{
			if(rsTKNo.next())
			{
				soDong = rsTKNo.getInt("sodong");
			}
			rsTKNo.close();
		} 
		catch (Exception e) { 
			e.printStackTrace();
		}
		return soDong;
	}

	private String checkNgayKhoaSoThang(Idbutils db) {
		String query = "select top 1 THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		String thangKS = "6";
		String namKS = "2016";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKS = rsCheck.getString("THANGKS");
					namKS = rsCheck.getString("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		String thangHopLe = "";
		String namHopLe = "";
		if(Integer.parseInt(thangKS) == 12 )
		{
			thangHopLe =  "1";
			namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
		}
		else
		{
			thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
			namHopLe = namKS;
		}
		
		System.out.println("aaaaaaaaaaaaaaa"+thangHopLe);
		System.out.println("bbbbbbbbbbbbbbbb"+namHopLe);
		System.out.println("cccccccccccccccc"+this.thang);
		System.out.println("dddddddddddddddd"+this.nam);
		if( (Integer.parseInt(namKS) > Integer.parseInt(nam)) 
				|| (Integer.parseInt(namKS) == Integer.parseInt(nam) && Integer.parseInt(thangKS) >= Integer.parseInt(thang)) 
			)
		{
			//TAM THOI CHUA CHECK
			return "Bạn chỉ có thể đóng / mở nghiệp vụ sau tháng khóa sổ gần nhất ( " + thangKS + "-" + namKS + " ) 1 tháng";
		}
		
//	
		
		return "";
	}

	public String CheckKhoaSoKT (dbutils db, String ngayGhiNhan, String nppId)
	{
		String msg = "";
		
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = 
			"select count(*) dem from ERP_KHOASOKETOAN \n" +
			"where THANGKS= DATEPART(MONTH,'" + ngayGhiNhan + "') \n" +
			"and NAM=DATEPART(YEAR,'" + ngayGhiNhan + "') and NPP_FK = " + nppId;
			
		int count = 0 ;
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					count = rsCheck.getInt("dem");
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(count > 0)
			return "Bạn không được chỉnh sửa nghiệp vụ trong tháng đã khóa sổ";
				
		return msg;
	}
	
	public static void GetThangKhoaSoMax(Idbutils db, List<Integer> thangNam)
	{
		 int thangKSMax = 0;
		 int namKSMax = 0;
		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = "select top 1 THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKSMax = rsCheck.getInt("THANGKS");
					namKSMax = rsCheck.getInt("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		thangNam.add(thangKSMax);
		thangNam.add(namKSMax);
	}
	
	public static String GetNgayKhoaSoMax(Idbutils db)
	{
		String ngayKhoaSoMax = "";
		String query = "select top 1 THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					int thang = rsCheck.getInt("THANGKS");
					ngayKhoaSoMax = rsCheck.getString("NAM") + "-" + (thang > 9 ? thang : ("0" + thang)) + "-30";
				}
				rsCheck.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ngayKhoaSoMax;
	}
	
	public static String refactorQuery(String query)
	{
		query = query.replaceAll("N'NULL'", "null");
		query = query.replaceAll("N'null'", "null");
		query = query.replaceAll("'NULL'", "null");
		query = query.replaceAll("'null'", "null");
		query = query.replaceAll("= ,", "= null,");
		query = query.replaceAll("=  ,", "= null,");
		query = query.replaceAll("=,", "= null,");
		query = query.replaceAll(", ,", ", null,");
//		query = query.replaceAll(",)", ", null)");
//		query = query.replaceAll(", )", ", null)");
		query = query.replaceAll(", --", ", null");
		query = query.replaceAll("= --", "= null");
		query = query.replaceAll("--", "");
		return query;
	}
	/////////////////////////////////
	
	public void setThangNam(String ngayThang)//yyyy-mm-dd
	{
		String[] arr = ngayThang.split("-");
		if (arr != null && arr.length > 0)
		{
			this.nam = arr[0];
			if (arr.length > 1)
				this.thang = arr[1];
		}
	}
	
	public void setTaiKhoanNoCo_FK(String taiKhoan_FK) {
		this.taiKhoanCo_FK = this.taiKhoanNo_FK = taiKhoan_FK;
	}

	public void setSoHieuTaiKhoanNoCo(String soHieuTaiKhoan) {
		this.soHieuTaiKhoanNo = this.soHieuTaiKhoanCo = soHieuTaiKhoan;
	}

	public void setNoiDungNhapXuatNoCo_FK(String noiDungNhapXuat_FK) {
		this.noiDungNhapXuatNo_FK = this.noiDungNhapXuatCo_FK = noiDungNhapXuat_FK;
	}

	public void setNoCo(String value) {
		this.no = this.co = value;
	}

	public void setDoiTuongNoCo(String doiTuong) {
		this.doiTuongNo = this.doiTuongCo = doiTuong;
	}

	public void setMaDoiTuongNoCo(String maDoiTuong) {
		this.maDoiTuongNo = this.maDoiTuongCo = maDoiTuong;
	}

	public void setLoaiDoiTuongNoCo(String loaiDoiTuong) {
		this.loaiDoiTuongNo = this.loaiDoiTuongCo = loaiDoiTuong;
	}

	public void setSoLuongNoCo(String soLuong) {
		this.soLuongNo = this.soLuongCo = soLuong;
	}

	public void setDonGiaNoCo(String donGia) {
		this.donGiaNo = this.donGiaCo = donGia;
	}

	public void setDonGiaNoCoNT(String donGiaNT) {
		this.donGiaNoNT = this.donGiaCoNT = donGiaNT;
	}

	public void setKhoanMucNoCo(String khoanMuc) {
		this.khoanMucNo = this.khoanMucCo = khoanMuc;
	}

	public void setDienGiaiNoCo(String dienGiai) {
		this.dienGiaiNo = this.dienGiaiCo = dienGiai;
	}

	public void setMaChungTuNoCo(String maChungTu) {
		this.maChungTuNo = this.maChungTuCo = maChungTu;
	}

	public void setMaSanPhamNoCo(String maSanPham) {
		this.maSanPhamNo = this.maSanPhamCo = maSanPham;
	}

	public void setTenSanPhamNoCo(String tenSanPham) {
		this.tenSanPhamNo = this.tenSanPhamCo = tenSanPham;
	}

	public void setDonViTinhNoCo(String donViTinh) {
		this.donViTinhNo = this.donViTinhCo = donViTinh;
	}

	public void setSoLoNoCo(String soLo) {
		this.soLoNo = this.soLoCo = soLo;
	}

	public void setNgayHetHanNoCo(String ngayHetHan) {
		this.ngayHetHanNo = this.ngayHetHanCo = ngayHetHan;
	}

	public void setKenhBanHangNoCo_FK(String kenhBanHang_FK) {
		this.kenhBanHangNo_FK = this.kenhBanHangCo_FK = kenhBanHang_FK;
	}

	public void setKhoXuatNoCo_FK(String khoXuat_FK) {
		this.khoXuatNo_FK = this.khoXuatCo_FK = khoXuat_FK;
	}

	public void setKhoNhanNoCo_FK(String khoNhan_FK) {
		this.khoNhanNo_FK = this.khoNhanCo_FK = khoNhan_FK;
	}

	public void setKhoanMucChiPhiNoCo_FK(String khoanMucChiPhi_FK) {
		this.khoanMucChiPhiNo_FK = this.khoanMucChiPhiCo_FK = khoanMucChiPhi_FK;
	}

	public void setVatNoCo(String vat) {
		this.vatNo = this.vatCo = vat;
	}

	public void setIsNPPNoCo(String isNPP) {
		this.isNPPNo = this.isNPPCo = isNPP;
	}

	public void setMaHangNoCo(String maHang) {
		this.maHangNo = this.maHangCo = maHang;
	}

	public void setTenHangNoCo(String tenHang) {
		this.tenHangNo = this.tenHangCo = tenHang;
	}

	public void setMaHoaDonNoCo(String maHoaDon) {
		this.maHoaDonNo = this.maHoaDonCo = maHoaDon;
	}

	public void setMauHoaDonNoCo(String mauHoaDon) {
		this.mauHoaDonNo = this.mauHoaDonCo = mauHoaDon;
	}

	public void setKyHieuNoCo(String kyHieu) {
		this.kyHieuNo = this.kyHieuCo = kyHieu;
	}

	public void setNgayHoaDonNoCo(String ngayHoaDon) {
		this.ngayHoaDonNo = this.ngayHoaDonCo = ngayHoaDon;
	}

	public void setTenNhaCungCapNoCo(String tenNhaCungCap) {
		this.tenNhaCungCapNo = this.tenNhaCungCapCo = tenNhaCungCap;
	}

	public void setMaSoThueNoCo(String maSoThue) {
		this.maSoThueNo = this.maSoThueCo = maSoThue;
	}

	public void setThueSuatNoCo(String thueSuat) {
		this.thueSuatNo = this.thueSuatCo = thueSuat;
	}

	public void setTienThueNoCo(String tienThue) {
		this.tienThueNo = this.tienThueCo = tienThue;
	}

	public void setMaFast_DTNoCo(String maFast_DT) {
		this.maFast_DTNo = this.maFast_DTCo = maFast_DT;
	}

	public void setTen_DTNoCo(String ten_DT) {
		this.ten_DTNo = this.ten_DTCo = ten_DT;
	}

	public void setTen_PBNoCo(String ten_PB) {
		this.ten_PBNo = this.ten_PBCo = ten_PB;
	}

	public void setTen_KBHNoCo(String ten_KBH) {
		this.ten_KBHNo = this.ten_KBHCo = ten_KBH;
	}

	public void setTen_VVNoCo(String ten_VV) {
		this.ten_VVNo = this.ten_VVCo = ten_VV;
	}

	public void setTen_diaBanNoCo(String ten_diaBan) {
		this.ten_diaBanNo = this.ten_diaBanCo = ten_diaBan;
	}

	public void setTen_tinhThanhNoCo(String ten_tinhThanh) {
		this.ten_tinhThanhNo = this.ten_tinhThanhCo = ten_tinhThanh;
	}

	public void setTen_benhVienNoCo(String ten_benhVien) {
		this.ten_benhVienNo = this.ten_benhVienCo = ten_benhVien;
	}

	public void setTen_sanPhamNoCo(String ten_sanPham) {
		this.ten_sanPhamNo = this.ten_sanPhamCo = ten_sanPham;
	}

	public void setDienGiai_CTNoCo(String dienGiai_CT) {
		this.dienGiai_CTNo = this.dienGiai_CTCo = dienGiai_CT;
	}

	public void setBangDoiTuongNoCo(String bangDoiTuong) {
		this.bangDoiTuongNo = this.bangDoiTuongCo = bangDoiTuong;
	}

	public void setSanPham_FKNoCo(String sanPham_FK) {
		this.sanPham_FKNo = this.sanPham_FKCo = sanPham_FK;
	}

	public void setSoHoaDonNoCo(String soHoaDon) {
		this.soHoaDonNo = this.soHoaDonCo = soHoaDon;
	}

	public void setTienHangNoCo(String tienHang) {
		this.tienHangNo = this.tienHangCo = tienHang;
	}
	/////////////////////////////////
	public String getLoaiChungTu() {
		return loaiChungTu;
	}

	public void setLoaiChungTu(String loaiChungTu) {
		this.loaiChungTu = loaiChungTu;
	}

	public String getSoChungTu() {
		return soChungTu;
	}

	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getThang() {
		return thang;
	}

	public void setThang(String thang) {
		this.thang = thang;
	}

	public String getNam() {
		return nam;
	}

	public void setNam(String nam) {
		this.nam = nam;
	}

	public String getNgayChungTu() {
		return ngayChungTu;
	}

	public void setNgayChungTu(String ngayChungTu) {
		this.ngayChungTu = ngayChungTu;
	}

	public String getNgayGhiNhan() {
		return ngayGhiNhan;
	}

	public void setNgayGhiNhan(String ngayGhiNhan) {
		this.ngayGhiNhan = ngayGhiNhan;
	}

	public String getTaiKhoanNo_FK() {
		return taiKhoanNo_FK;
	}

	public void setTaiKhoanNo_FK(String taiKhoanNo_FK) {
		this.taiKhoanNo_FK = taiKhoanNo_FK;
	}

	public String getTaiKhoanCo_FK() {
		return taiKhoanCo_FK;
	}

	public void setTaiKhoanCo_FK(String taiKhoanCo_FK) {
		this.taiKhoanCo_FK = taiKhoanCo_FK;
	}

	public String getSoHieuTaiKhoanNo() {
		return soHieuTaiKhoanNo;
	}

	public void setSoHieuTaiKhoanNo(String soHieuTaiKhoanNo) {
		this.soHieuTaiKhoanNo = soHieuTaiKhoanNo;
	}

	public String getSoHieuTaiKhoanCo() {
		return soHieuTaiKhoanCo;
	}

	public void setSoHieuTaiKhoanCo(String soHieuTaiKhoanCo) {
		this.soHieuTaiKhoanCo = soHieuTaiKhoanCo;
	}

	public String getNoiDungNhapXuatNo_FK() {
		return noiDungNhapXuatNo_FK;
	}

	public void setNoiDungNhapXuatNo_FK(String noiDungNhapXuatNo_FK) {
		this.noiDungNhapXuatNo_FK = noiDungNhapXuatNo_FK;
	}

	public String getNoiDungNhapXuatCo_FK() {
		return noiDungNhapXuatCo_FK;
	}

	public void setNoiDungNhapXuatCo_FK(String noiDungNhapXuatCo_FK) {
		this.noiDungNhapXuatCo_FK = noiDungNhapXuatCo_FK;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getDoiTuongNo() {
		return doiTuongNo;
	}

	public void setDoiTuongNo(String doiTuongNo) {
		this.doiTuongNo = doiTuongNo;
	}

	public String getDoiTuongCo() {
		return doiTuongCo;
	}

	public void setDoiTuongCo(String doiTuongCo) {
		this.doiTuongCo = doiTuongCo;
	}

	public String getMaDoiTuongNo() {
		return maDoiTuongNo;
	}

	public void setMaDoiTuongNo(String maDoiTuongNo) {
		this.maDoiTuongNo = maDoiTuongNo;
	}

	public String getMaDoiTuongCo() {
		return maDoiTuongCo;
	}

	public void setMaDoiTuongCo(String maDoiTuongCo) {
		this.maDoiTuongCo = maDoiTuongCo;
	}

	public String getLoaiDoiTuongNo() {
		return loaiDoiTuongNo;
	}

	public void setLoaiDoiTuongNo(String loaiDoiTuongNo) {
		this.loaiDoiTuongNo = loaiDoiTuongNo;
	}

	public String getLoaiDoiTuongCo() {
		return loaiDoiTuongCo;
	}

	public void setLoaiDoiTuongCo(String loaiDoiTuongCo) {
		this.loaiDoiTuongCo = loaiDoiTuongCo;
	}

	public String getTiGia_FK() {
		return tiGia_FK;
	}

	public void setTiGia_FK(String tiGia_FK) {
		this.tiGia_FK = tiGia_FK;
	}

	public String getTienTeGoc_FK() {
		return tienTeGoc_FK;
	}

	public void setTienTeGoc_FK(String tienTeGoc_FK) {
		this.tienTeGoc_FK = tienTeGoc_FK;
	}

	public String getSoLuongNo() {
		return soLuongNo;
	}

	public void setSoLuongNo(String soLuongNo) {
		this.soLuongNo = soLuongNo;
	}

	public String getSoLuongCo() {
		return soLuongCo;
	}

	public void setSoLuongCo(String soLuongCo) {
		this.soLuongCo = soLuongCo;
	}

	public String getDonGiaNo() {
		return donGiaNo;
	}

	public void setDonGiaNo(String donGiaNo) {
		this.donGiaNo = donGiaNo;
	}

	public String getDonGiaCo() {
		return donGiaCo;
	}

	public void setDonGiaCo(String donGiaCo) {
		this.donGiaCo = donGiaCo;
	}

	public String getDonGiaNoNT() {
		return donGiaNoNT;
	}

	public void setDonGiaNoNT(String donGiaNoNT) {
		this.donGiaNoNT = donGiaNoNT;
	}

	public String getDonGiaCoNT() {
		return donGiaCoNT;
	}

	public void setDonGiaCoNT(String donGiaCoNT) {
		this.donGiaCoNT = donGiaCoNT;
	}

	public String getTongGiaTri() {
		return tongGiaTri;
	}

	public void setTongGiaTri(String tongGiaTri) {
		this.tongGiaTri = tongGiaTri;
	}

	public String getTongGiaTriNT() {
		return tongGiaTriNT;
	}

	public void setTongGiaTriNT(String tongGiaTriNT) {
		this.tongGiaTriNT = tongGiaTriNT;
	}

	public String getKhoanMucNo() {
		return khoanMucNo;
	}

	public void setKhoanMucNo(String khoanMucNo) {
		this.khoanMucNo = khoanMucNo;
	}

	public String getKhoanMucCo() {
		return khoanMucCo;
	}

	public void setKhoanMucCo(String khoanMucCo) {
		this.khoanMucCo = khoanMucCo;
	}

	public String getDienGiaiNo() {
		return dienGiaiNo;
	}

	public void setDienGiaiNo(String dienGiaiNo) {
		this.dienGiaiNo = dienGiaiNo;
	}

	public String getDienGiaiCo() {
		return dienGiaiCo;
	}

	public void setDienGiaiCo(String dienGiaiCo) {
		this.dienGiaiCo = dienGiaiCo;
	}

	public String getMaChungTuNo() {
		return maChungTuNo;
	}

	public void setMaChungTuNo(String maChungTuNo) {
		this.maChungTuNo = maChungTuNo;
	}

	public String getMaChungTuCo() {
		return maChungTuCo;
	}

	public void setMaChungTuCo(String maChungTuCo) {
		this.maChungTuCo = maChungTuCo;
	}

	public String getMaSanPhamNo() {
		return maSanPhamNo;
	}

	public void setMaSanPhamNo(String maSanPhamNo) {
		this.maSanPhamNo = maSanPhamNo;
	}

	public String getMaSanPhamCo() {
		return maSanPhamCo;
	}

	public void setMaSanPhamCo(String maSanPhamCo) {
		this.maSanPhamCo = maSanPhamCo;
	}

	public String getTenSanPhamNo() {
		return tenSanPhamNo;
	}

	public void setTenSanPhamNo(String tenSanPhamNo) {
		this.tenSanPhamNo = tenSanPhamNo;
	}

	public String getTenSanPhamCo() {
		return tenSanPhamCo;
	}

	public void setTenSanPhamCo(String tenSanPhamCo) {
		this.tenSanPhamCo = tenSanPhamCo;
	}

	public String getDonViTinhNo() {
		return donViTinhNo;
	}

	public void setDonViTinhNo(String donViTinhNo) {
		this.donViTinhNo = donViTinhNo;
	}

	public String getDonViTinhCo() {
		return donViTinhCo;
	}

	public void setDonViTinhCo(String donViTinhCo) {
		this.donViTinhCo = donViTinhCo;
	}

	public String getSoLoNo() {
		return soLoNo;
	}

	public void setSoLoNo(String soLoNo) {
		this.soLoNo = soLoNo;
	}

	public String getSoLoCo() {
		return soLoCo;
	}

	public void setSoLoCo(String soLoCo) {
		this.soLoCo = soLoCo;
	}

	public String getNgayHetHanNo() {
		return ngayHetHanNo;
	}

	public void setNgayHetHanNo(String ngayHetHanNo) {
		this.ngayHetHanNo = ngayHetHanNo;
	}

	public String getNgayHetHanCo() {
		return ngayHetHanCo;
	}

	public void setNgayHetHanCo(String ngayHetHanCo) {
		this.ngayHetHanCo = ngayHetHanCo;
	}

	public String getKenhBanHangNo_FK() {
		return kenhBanHangNo_FK;
	}

	public void setKenhBanHangNo_FK(String kenhBanHangNo_FK) {
		this.kenhBanHangNo_FK = kenhBanHangNo_FK;
	}

	public String getKenhBanHangCo_FK() {
		return kenhBanHangCo_FK;
	}

	public void setKenhBanHangCo_FK(String kenhBanHangCo_FK) {
		this.kenhBanHangCo_FK = kenhBanHangCo_FK;
	}

	public String getKhoXuatNo_FK() {
		return khoXuatNo_FK;
	}

	public void setKhoXuatNo_FK(String khoXuatNo_FK) {
		this.khoXuatNo_FK = khoXuatNo_FK;
	}

	public String getKhoXuatCo_FK() {
		return khoXuatCo_FK;
	}

	public void setKhoXuatCo_FK(String khoXuatCo_FK) {
		this.khoXuatCo_FK = khoXuatCo_FK;
	}

	public String getKhoNhanNo_FK() {
		return khoNhanNo_FK;
	}

	public void setKhoNhanNo_FK(String khoNhanNo_FK) {
		this.khoNhanNo_FK = khoNhanNo_FK;
	}

	public String getKhoNhanCo_FK() {
		return khoNhanCo_FK;
	}

	public void setKhoNhanCo_FK(String khoNhanCo_FK) {
		this.khoNhanCo_FK = khoNhanCo_FK;
	}

	public String getKhoanMucChiPhiNo_FK() {
		return khoanMucChiPhiNo_FK;
	}

	public void setKhoanMucChiPhiNo_FK(String khoanMucChiPhiNo_FK) {
		this.khoanMucChiPhiNo_FK = khoanMucChiPhiNo_FK;
	}

	public String getKhoanMucChiPhiCo_FK() {
		return khoanMucChiPhiCo_FK;
	}

	public void setKhoanMucChiPhiCo_FK(String khoanMucChiPhiCo_FK) {
		this.khoanMucChiPhiCo_FK = khoanMucChiPhiCo_FK;
	}

	public void setVatNo(String vatNo) {
		this.vatNo = vatNo;
	}

	public String getVatNo() {
		return vatNo;
	}

	public void setVatCo(String vatCo) {
		this.vatCo = vatCo;
	}

	public String getVatCo() {
		return vatCo;
	}
	
	public String getIsNPPNo() {
		return isNPPNo;
	}

	public void setIsNPPNo(String isNPPNo) {
		this.isNPPNo = isNPPNo;
	}

	public String getIsNPPCo() {
		return isNPPCo;
	}

	public void setIsNPPCo(String isNPPCo) {
		this.isNPPCo = isNPPCo;
	}

	public String getMaHangNo() {
		return maHangNo;
	}

	public void setMaHangNo(String maHangNo) {
		this.maHangNo = maHangNo;
	}

	public String getMaHangCo() {
		return maHangCo;
	}

	public void setMaHangCo(String maHangCo) {
		this.maHangCo = maHangCo;
	}

	public String getTenHangNo() {
		return tenHangNo;
	}

	public void setTenHangNo(String tenHangNo) {
		this.tenHangNo = tenHangNo;
	}

	public String getTenHangCo() {
		return tenHangCo;
	}

	public void setTenHangCo(String tenHangCo) {
		this.tenHangCo = tenHangCo;
	}
	
	public String getMaHoaDonNo() {
		return maHoaDonNo;
	}

	public void setMaHoaDonNo(String maHoaDonNo) {
		this.maHoaDonNo = maHoaDonNo;
	}

	public String getMaHoaDonCo() {
		return maHoaDonCo;
	}

	public void setMaHoaDonCo(String maHoaDonCo) {
		this.maHoaDonCo = maHoaDonCo;
	}

	public String getMauHoaDonNo() {
		return mauHoaDonNo;
	}

	public void setMauHoaDonNo(String mauHoaDonNo) {
		this.mauHoaDonNo = mauHoaDonNo;
	}

	public String getMauHoaDonCo() {
		return mauHoaDonCo;
	}

	public void setMauHoaDonCo(String mauHoaDonCo) {
		this.mauHoaDonCo = mauHoaDonCo;
	}
	
	public String getKyHieuNo() {
		return kyHieuNo;
	}

	public void setKyHieuNo(String kyHieuNo) {
		this.kyHieuNo = kyHieuNo;
	}

	public String getKyHieuCo() {
		return kyHieuCo;
	}

	public void setKyHieuCo(String kyHieuCo) {
		this.kyHieuCo = kyHieuCo;
	}

	public String getNgayHoaDonNo() {
		return ngayHoaDonNo;
	}

	public void setNgayHoaDonNo(String ngayHoaDonNo) {
		this.ngayHoaDonNo = ngayHoaDonNo;
	}

	public String getNgayHoaDonCo() {
		return ngayHoaDonCo;
	}

	public void setNgayHoaDonCo(String ngayHoaDonCo) {
		this.ngayHoaDonCo = ngayHoaDonCo;
	}

	public String getTenNhaCungCapNo() {
		return tenNhaCungCapNo;
	}

	public void setTenNhaCungCapNo(String tenNhaCungCapNo) {
		this.tenNhaCungCapNo = tenNhaCungCapNo;
	}

	public String getTenNhaCungCapCo() {
		return tenNhaCungCapCo;
	}

	public void setTenNhaCungCapCo(String tenNhaCungCapCo) {
		this.tenNhaCungCapCo = tenNhaCungCapCo;
	}

	public String getMaSoThueNo() {
		return maSoThueNo;
	}

	public void setMaSoThueNo(String maSoThueNo) {
		this.maSoThueNo = maSoThueNo;
	}

	public String getMaSoThueCo() {
		return maSoThueCo;
	}

	public void setMaSoThueCo(String maSoThueCo) {
		this.maSoThueCo = maSoThueCo;
	}

	public String getThueSuatNo() {
		return thueSuatNo;
	}

	public void setThueSuatNo(String thueSuatNo) {
		this.thueSuatNo = thueSuatNo;
	}

	public String getThueSuatCo() {
		return thueSuatCo;
	}

	public void setThueSuatCo(String thueSuatCo) {
		this.thueSuatCo = thueSuatCo;
	}

	public String getTienThueNo() {
		return tienThueNo;
	}

	public void setTienThueNo(String tienThueNo) {
		this.tienThueNo = tienThueNo;
	}

	public String getTienThueCo() {
		return tienThueCo;
	}

	public void setTienThueCo(String tienThueCo) {
		this.tienThueCo = tienThueCo;
	}
	
	public String getMaFast_DTNo() {
		return maFast_DTNo;
	}

	public void setMaFast_DTNo(String maFast_DTNo) {
		this.maFast_DTNo = maFast_DTNo;
	}

	public String getMaFast_DTCo() {
		return maFast_DTCo;
	}

	public void setMaFast_DTCo(String maFast_DTCo) {
		this.maFast_DTCo = maFast_DTCo;
	}

	public String getTen_DTNo() {
		return ten_DTNo;
	}

	public void setTen_DTNo(String ten_DTNo) {
		this.ten_DTNo = ten_DTNo;
	}

	public String getTen_DTCo() {
		return ten_DTCo;
	}

	public void setTen_DTCo(String ten_DTCo) {
		this.ten_DTCo = ten_DTCo;
	}

	public String getTen_PBNo() {
		return ten_PBNo;
	}

	public void setTen_PBNo(String ten_PBNo) {
		this.ten_PBNo = ten_PBNo;
	}

	public String getTen_PBCo() {
		return ten_PBCo;
	}

	public void setTen_PBCo(String ten_PBCo) {
		this.ten_PBCo = ten_PBCo;
	}

	public String getTen_KBHNo() {
		return ten_KBHNo;
	}

	public void setTen_KBHNo(String ten_KBHNo) {
		this.ten_KBHNo = ten_KBHNo;
	}

	public String getTen_KBHCo() {
		return ten_KBHCo;
	}

	public void setTen_KBHCo(String ten_KBHCo) {
		this.ten_KBHCo = ten_KBHCo;
	}

	public String getTen_VVNo() {
		return ten_VVNo;
	}

	public void setTen_VVNo(String ten_VVNo) {
		this.ten_VVNo = ten_VVNo;
	}

	public String getTen_VVCo() {
		return ten_VVCo;
	}

	public void setTen_VVCo(String ten_VVCo) {
		this.ten_VVCo = ten_VVCo;
	}

	public String getTen_diaBanNo() {
		return ten_diaBanNo;
	}

	public void setTen_diaBanNo(String ten_diaBanNo) {
		this.ten_diaBanNo = ten_diaBanNo;
	}

	public String getTen_diaBanCo() {
		return ten_diaBanCo;
	}

	public void setTen_diaBanCo(String ten_diaBanCo) {
		this.ten_diaBanCo = ten_diaBanCo;
	}

	public String getTen_tinhThanhNo() {
		return ten_tinhThanhNo;
	}

	public void setTen_tinhThanhNo(String ten_tinhThanhNo) {
		this.ten_tinhThanhNo = ten_tinhThanhNo;
	}

	public String getTen_tinhThanhCo() {
		return ten_tinhThanhCo;
	}

	public void setTen_tinhThanhCo(String ten_tinhThanhCo) {
		this.ten_tinhThanhCo = ten_tinhThanhCo;
	}

	public String getTen_benhVienNo() {
		return ten_benhVienNo;
	}

	public void setTen_benhVienNo(String ten_benhVienNo) {
		this.ten_benhVienNo = ten_benhVienNo;
	}

	public String getTen_benhVienCo() {
		return ten_benhVienCo;
	}

	public void setTen_benhVienCo(String ten_benhVienCo) {
		this.ten_benhVienCo = ten_benhVienCo;
	}

	public String getTen_sanPhamNo() {
		return ten_sanPhamNo;
	}

	public void setTen_sanPhamNo(String ten_sanPhamNo) {
		this.ten_sanPhamNo = ten_sanPhamNo;
	}

	public String getTen_sanPhamCo() {
		return ten_sanPhamCo;
	}

	public void setTen_sanPhamCo(String ten_sanPhamCo) {
		this.ten_sanPhamCo = ten_sanPhamCo;
	}

	public String getDienGiai_CTNo() {
		return dienGiai_CTNo;
	}

	public void setDienGiai_CTNo(String dienGiai_CTNo) {
		this.dienGiai_CTNo = dienGiai_CTNo;
	}

	public String getDienGiai_CTCo() {
		return dienGiai_CTCo;
	}

	public void setDienGiai_CTCo(String dienGiai_CTCo) {
		this.dienGiai_CTCo = dienGiai_CTCo;
	}

	public String getBangDoiTuongNo() {
		return bangDoiTuongNo;
	}

	public void setBangDoiTuongNo(String bangDoiTuongNo) {
		this.bangDoiTuongNo = bangDoiTuongNo;
	}

	public String getBangDoiTuongCo() {
		return bangDoiTuongCo;
	}

	public void setBangDoiTuongCo(String bangDoiTuongCo) {
		this.bangDoiTuongCo = bangDoiTuongCo;
	}

	public String getSanPham_FKNo() {
		return sanPham_FKNo;
	}

	public void setSanPham_FKNo(String sanPham_FKNo) {
		this.sanPham_FKNo = sanPham_FKNo;
	}

	public String getSanPham_FKCo() {
		return sanPham_FKCo;
	}

	public void setSanPham_FKCo(String sanPham_FKCo) {
		this.sanPham_FKCo = sanPham_FKCo;
	}

	public void setSoHoaDonNo(String soHoaDonNo) {
		this.soHoaDonNo = soHoaDonNo;
	}

	public String getSoHoaDonNo() {
		return soHoaDonNo;
	}

	public void setSoHoaDonCo(String soHoaDonCo) {
		this.soHoaDonCo = soHoaDonCo;
	}

	public String getSoHoaDonCo() {
		return soHoaDonCo;
	}

	public void setTienHangNo(String tienHangNo) {
		this.tienHangNo = tienHangNo;
	}

	public String getTienHangNo() {
		return tienHangNo;
	}

	public void setTienHangCo(String tienHangCo) {
		this.tienHangCo = tienHangCo;
	}

	public String getTienHangCo() {
		return tienHangCo;
	}
	
	public String Check_NgayNghiepVu_KeToan(Idbutils db,String thang,String nam)
	{

		//CHECK THANG KHOA SO CO HOP LE HAY KHONG ( CHI DUOC CHOT SAU THANG KHOA SO + 1 )
		String query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		String thangKS = "12";
		String namKS = "2016";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKS = rsCheck.getString("THANGKS");
					namKS = rsCheck.getString("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {}
		}
		System.out.println("nam :"+nam);
		System.out.println("namKS :"+namKS);
		System.out.println("thang :"+thang);
		System.out.println("thangKS :"+thangKS);
		
		if( (Integer.parseInt(nam)<Integer.parseInt(namKS))  || (( Integer.parseInt(nam) == Integer.parseInt(namKS)) && (Integer.parseInt(thang) <= Integer.parseInt(thangKS) )))
		{
			return "Không được thực hiện nghiệp vụ kế toán trong thời gian đã khóa sổ";
		}
	
		return "";
		
	}
	

}