package geso.traphaco.erp.beans.uynhiemchi.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Hoadon;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchi;
import geso.traphaco.erp.util.Library;

public class ErpUynhiemchi implements IErpUynhiemchi{
	int myvar1 = 100;
	double myvar2 = 200;
	String userId;
	String id;
	String ngayghinhan;
	String ctyId, dungchos;
	String nccId;
	String diachi;
	String loaiThue;
	ResultSet nccRs;
	String htttId;
	ResultSet htttRs;
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;

	
	String nganhang_tpId;
	String chinhanh_tpId;

	ResultSet sotkRs;
	ResultSet sotkRs_tp;

	String sotaikhoan;
	String sotaikhoan_tp;
	String noidungtt;

	String tthdCurrent = "";
	String tigia;
	String dinhkhoanno = "";
	String dinhkhoannoId = "";
	String trichphi;

	String hoadonId;
	List<IHoadon> hoadonList;

	String tienteId;
	ResultSet tienteRs;

	String msg;
	private dbutils db;

	String DoiTuongTamUng;
	String LoaiThanhToan;

	String TenHienThi = "";

	String NhomNCCCN;

	String nhomNCCCNId;
	ResultSet nhomNCCRs;

	String DoiTuongChiPhiKhac;

	String prefix;

	String DoiTuongDinhKhoan = "";

	String MaTenDoiTuongDinhKhoan = "";

	String doituongdinhkhoanId;
	String sotientt;
	String sotienHD;
	String pnganhang;
	String thueVAT;
	String chenhlech;

	String sotienttNT;
	String sotienHDNT;
	String pnganhangNT;
	String thueVATNT;
	String chenhlechVND;

	String mahoadon;
	String mauhoadon;
	String kyhieu;
	String sohoadon;
	String ngayhoadon;
	String tenNCC;
	String mst;
	String tienhang;
	String thuesuat;
	String tienthue;
	String nhId_VAT;
	String cnId_VAT;
	String tenNCC_VAT;

	String checkThanhtoantuTV;

	String khachhangId;
	ResultSet khachhangRs;

	String NhanvienId;
	ResultSet NhanvienRs;

	String bophanId;
	String bophanTen;

	String nppdangnhap;

	String isNpp;

	// CHI PHÍ KHÁC

	String[] Mahd;
	String[] Mauhd;
	String[] Kyhieuhd;
	String[] Sohd;
	String[] Ngayhd;
	String[] TenNCChd;
	String[] MSThd;
	String[] Tienhanghd;
	String[] Thuesuathd;
	String[] Tienthuehd;
	String[] Diengiaihd;
	
	//Phòng ban
	String[] PhongBanIds;
	
	ResultSet PhongBanRs;
	
	//Kênh bán hàng
	String[] KenhbhIds;
	
	ResultSet KenhBhRs;
	
	//Tỉnh thành
	String[] TinhThanhIds;
	
	ResultSet TinhThanhRs;
	
	//Sản phẩm
	String[] SanPhamIds;
	
	ResultSet SanphamRs;
	
	//Mã vụ việc
	String[] MavvIds;
	
	ResultSet MavvRs;
	
	//Địa bàn
	String[] DiabanIds;
	
	ResultSet DiabanRs;
	
	//Bệnh viện
	String[] BenhvienIds;
	
	String duyetchi;
	
	ResultSet BenhvienRs;
	
	ResultSet TaiKhoanKTRs; 
	
	String[] TaiKhoanIds, dcIds, loais, ttcpIds, diaChiHd;
	
	private String DNTT_FK;
	private String DNTU_FK;
	
	int count;
	private int daDuyet;
	private int trangThai;
	private String tenKhachHang;
	private String tenHinhThucThanhToan;
	private String tenSoTaiKhoan;
	private String tenTienTe;
	private String tenSoTaiKhoanTrichPhi;
	private String soChungTu;
	private String soChungTu_Chu;
	private String soChungTu_So;
	private String isDNTT;
	private String khachHang_NPP_FK;
	private String doiTuongKhacId;
	private List<Erp_Item> doiTuongKhacList;
	private List<Erp_Item> loaiThanhToanList;
	private List<Erp_Item> nppList;
	
	public ErpUynhiemchi() {
		this.id = "";
		this.ctyId = "";
		this.ngayghinhan = this.getDateTime();
		this.nccId = "";
		this.diachi = "";
		this.htttId = "100001";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.nganhang_tpId = "";
		this.chinhanh_tpId = "";
		this.sotaikhoan_tp = "";
		this.noidungtt = "";
		this.hoadonId = "";
		this.NhanvienId = "";
		this.TenHienThi = "";
		this.duyetchi="";
		this.msg = "";
		this.DNTT_FK = "";
		this.DNTU_FK = "";
		this.loaiThue = "";
		this.trichphi = "1";
		this.hoadonList = new ArrayList<IHoadon>();

		this.nhomNCCCNId = "";
		this.NhomNCCCN = "";
		DoiTuongTamUng = "1";
		DoiTuongChiPhiKhac = "1";
		LoaiThanhToan = "1";
		this.db = new dbutils();

		this.dinhkhoanno = "";
		this.dinhkhoannoId = "";

		this.sotientt = "0";
		this.sotienHD = "0";
		this.thueVAT = "0";
		this.pnganhang = "0";

		this.sotienttNT = "0";
		this.sotienHDNT = "0";
		this.thueVATNT = "0";
		this.pnganhangNT = "0";

		this.tienteId = "100000";
		this.tigia = "1";
		this.chenhlechVND = "0";

		this.mahoadon = "";
		this.mauhoadon = "";
		this.kyhieu = "";
		this.sohoadon = "";
		this.ngayhoadon = "";
		this.tenNCC = "";
		this.mst = "";
		this.tienhang = "0";
		this.thuesuat = "0";
		this.tienthue = "0";
		this.nhId_VAT = "";
		this.cnId_VAT = "";
		this.tenNCC_VAT = "";
		this.checkThanhtoantuTV = "0";
		this.khachhangId = "";
		this.loaiThue = "";

		this.bophanId = "";
		this.bophanTen = "";
		
		this.count=0;
		this.dungchos = "";
		
		this.nppdangnhap = "";
		this.isNpp = "";
		this.daDuyet = 0;
		this.trangThai = 0;
		this.soChungTu_Chu = "UNC"+this.ngayghinhan.substring(5,7)+ this.ngayghinhan.substring(0, 4);
		this.soChungTu_So = geso.traphaco.center.util.Utility.getSoChungTuMax(db, "ERP_THANHTOANHOADON", "100001");
		this.soChungTu = soChungTu_Chu + soChungTu_So;
		this.isDNTT = "0";
		this.doiTuongKhacId = "";
		this.DNTT_FK = "";
		this.DNTU_FK = "";
		this.doiTuongKhacList = new ArrayList<Erp_Item>();
		this.loaiThanhToanList = new ArrayList<Erp_Item>();
		
		this.khachHang_NPP_FK =  "";
		this.nppList = new ArrayList<Erp_Item>();
	}

	public ErpUynhiemchi(String id) {
		this();
		this.id = id;
	}
	
	public String getTenNCC_VAT() {
		/*
		 * String tenNCC = ""; String query = ""; ResultSet rs; try{
		 * if(this.sotaikhoan== null) this.sotaikhoan = "";
		 * 
		 * if(!this.tienteId.equals("100000")){ // NGOAI TE
		 * 
		 * if(this.trichphi.equals("0")){ // NGOAI TE, TRICH PHI BANG NGOAI TE,
		 * NGAN HANG TRICH PHI SE BANG NGAN HANG CHUYEN KHOAN
		 * if(this.sotaikhoan.length() > 0){ rs = this.db.get(
		 * "SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"
		 * + this.sotaikhoan + "'");
		 * 
		 * if(rs != null){ rs.next(); this.nganhangId =
		 * rs.getString("NGANHANG_FK"); this.chinhanhId =
		 * rs.getString("CHINHANH_FK"); rs.close(); } }else{ this.nganhangId =
		 * ""; this.chinhanhId = "";
		 * 
		 * }
		 * 
		 * if(this.nganhangId.length() > 0){ query=
		 * "SELECT NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, NH.TEN + ' - ' + CN.TEN AS NHTEN "
		 * + "FROM ERP_NGANHANG_CONGTY NHCTY " +
		 * "INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK " +
		 * "INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NHCTY.CHINHANH_FK " +
		 * "WHERE NHCTY.TIENTE_FK = " + this.tienteId +
		 * " AND NH.TRANGTHAI = 1 AND NH.PK_SEQ = " + this.nganhangId + " " +
		 * "AND CN.PK_SEQ = " + this.chinhanhId + " "; rs = this.db.get(query);
		 * if(rs != null){ try{ rs.next(); tenNCC = rs.getString("NHTEN");
		 * this.nhId_VAT = this.nganhangId; this.cnId_VAT = this.chinhanhId;
		 * rs.close(); }catch(java.sql.SQLException e){} } }else{ tenNCC = "";
		 * this.nhId_VAT = ""; this.cnId_VAT = ""; }
		 * 
		 * }else{ // NGOAI TE, TRICH PHI BANG VND, NGAN HANG TRICH PHI SE BANG
		 * NGAN HANG VND if(this.sotaikhoan_tp.length() > 0){ rs = this.db.get(
		 * "SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"
		 * + this.sotaikhoan_tp + "'"); System.out.println(
		 * "SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"
		 * + this.sotaikhoan_tp + "'"); if(rs != null){ rs.next();
		 * this.nganhang_tpId = rs.getString("NGANHANG_FK"); this.chinhanh_tpId
		 * = rs.getString("CHINHANH_FK"); rs.close(); } }else{
		 * this.nganhang_tpId = ""; this.chinhanh_tpId = "";
		 * 
		 * }
		 * 
		 * 
		 * if(this.nganhang_tpId.length() > 0){ query=
		 * "SELECT NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, NH.TEN + ' - ' + CN.TEN AS NHTEN "
		 * + "FROM ERP_NGANHANG_CONGTY NHCTY " +
		 * "INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK " +
		 * "INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NHCTY.CHINHANH_FK " +
		 * "WHERE NHCTY.TIENTE_FK = " + this.tienteId +
		 * " AND NH.TRANGTHAI = 1 AND NH.PK_SEQ = " + this.nganhang_tpId + " " +
		 * "AND CN.PK_SEQ = " + this.chinhanh_tpId + " "; rs =
		 * this.db.get(query); if(rs != null){ try{ rs.next(); tenNCC =
		 * rs.getString("NHTEN"); this.nhId_VAT = this.nganhang_tpId;
		 * this.cnId_VAT = this.chinhanh_tpId;
		 * 
		 * rs.close(); }catch(java.sql.SQLException e){} } }else{ tenNCC = "";
		 * this.nhId_VAT = ""; this.cnId_VAT = "";
		 * 
		 * }
		 * 
		 * } }else{ // TIEN VND if(this.sotaikhoan.length() > 0){ rs =
		 * this.db.get(
		 * "SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"
		 * + this.sotaikhoan + "'"); System.out.println(
		 * "SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"
		 * + this.sotaikhoan + "'"); if(rs != null){ rs.next(); this.nganhangId
		 * = rs.getString("NGANHANG_FK"); this.chinhanhId =
		 * rs.getString("CHINHANH_FK"); rs.close(); } }else{ this.nganhangId =
		 * ""; this.chinhanhId = "";
		 * 
		 * }
		 * 
		 * if(this.nganhangId.length() > 0){ query =
		 * "SELECT NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, NH.TEN + ' - ' + CN.TEN AS NHTEN "
		 * + "FROM ERP_NGANHANG_CONGTY NHCTY " +
		 * "INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK " +
		 * "INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NHCTY.CHINHANH_FK " +
		 * "WHERE NHCTY.TIENTE_FK = " + this.tienteId +
		 * " AND NH.TRANGTHAI = 1 AND NH.PK_SEQ = " + this.nganhangId + " " +
		 * "AND CN.PK_SEQ = " + this.chinhanhId + " "; rs = this.db.get(query);
		 * if(rs != null){ try{ rs.next(); tenNCC = rs.getString("NHTEN");
		 * this.nhId_VAT = this.nganhangId; this.cnId_VAT = this.chinhanhId;
		 * rs.close(); }catch(java.sql.SQLException e){} } }else{ tenNCC = "";
		 * this.nhId_VAT = ""; this.cnId_VAT = ""; } }
		 * 
		 * if(this.DoiTuongChiPhiKhac.equals("3")) // NẾU ĐỐI TƯỢNG CHI PHÍ KHÁC
		 * THÌ {
		 * 
		 * }
		 * 
		 * 
		 * }catch(java.sql.SQLException e){}
		 * 
		 * System.out.println("TEN NCC:" + query); System.out.println(tenNCC);
		 * return tenNCC;
		 */

		return this.tenNCC;
	}
	public void taoMoiSoChungTu(){
		if(this.ngayghinhan.length() > 0 ){
			String prefix ="UNC";
			if(this.DNTT_FK != null && this.DNTT_FK.trim().length() > 0)
				prefix = "DNBN";
			if(this.DNTU_FK != null && this.DNTU_FK.trim().length() > 0)
				prefix = "DNBN";
			this.soChungTu_Chu = prefix +this.ngayghinhan.substring(5,7)+ this.ngayghinhan.substring(0, 4);
			this.soChungTu_So = geso.traphaco.center.util.Utility.generataSoChungTu(db, this.soChungTu_Chu,
					geso.traphaco.center.util.Utility.tblPC_UNC ,
					geso.traphaco.center.util.Utility.httt_CK, this.id, this.ngayghinhan);
			this.soChungTu = soChungTu_Chu + soChungTu_So;
		}
	}

	public void setTenNCC_VAT(String tenNCC_VAT) {
		this.tenNCC_VAT = tenNCC_VAT;
	}

	public boolean createTTHD() {
		if (this.doiTuongKhacId != null && this.doiTuongKhacId.trim().length() == 0)
			this.doiTuongKhacId = null;
		
		this.soChungTu = this.soChungTu_Chu + this.soChungTu_So;
		if (!this.DoiTuongChiPhiKhac.equals("3")) {
			if (this.hoadonList.size() <= 0) {
				this.msg = "Không có hóa đơn nào để thanh toán";
				return false;
			}
		}

		if (this.htttId.length() <= 0) {
			this.msg = "Vui lòng chọn hình thức thanh toán";
			return false;
		}

		if (this.htttId.equals("100001") || this.htttId.equals("100003")) {

			if (this.sotaikhoan.trim().length() <= 0) {
				if (this.checkThanhtoantuTV.equals("1"))
					this.msg = "Vui lòng nhập Số tài khoản để trích phí ngân hàng !";
				else
					this.msg = "Vui lòng nhập Số tài khoản  !";
				return false;
			}
		}

		
		try {
			String ngaytao = getDateTime();

			db.getConnection().setAutoCommit(false);
			
			if(geso.traphaco.center.util.Utility.checkSoChungTu(db, this.soChungTu,
					geso.traphaco.center.util.Utility.tblPC_UNC,
					geso.traphaco.center.util.Utility.httt_CK, this.id))
			{
				this.msg = "Số chứng từ " + this.soChungTu + " đã tồn tại trong hệ thống. Vui lòng kiểm tra lại !";
				db.getConnection().rollback();
				return false;
			}
			

			if (this.sotaikhoan.trim().length() <= 0)
				this.sotaikhoan = "";

			if (this.DoiTuongDinhKhoan.trim().length() <= 0) {
				this.DoiTuongDinhKhoan = "NULL";
				if (this.doituongdinhkhoanId.trim().length() <= 0) {
					this.doituongdinhkhoanId = "NULL";
				}
			}

			double tongthanhtoan = 0;
			// Tinh lai tong tien

			if (this.sotaikhoan.length() > 0) {
				ResultSet rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan + "'");
				System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"
						+ this.sotaikhoan + "'");
				if (rs != null) {
					rs.next();
					this.nganhangId = rs.getString("NGANHANG_FK");
					this.chinhanhId = rs.getString("CHINHANH_FK");
					rs.close();
				}
			} else {
				this.nganhangId = "";
				this.chinhanhId = "";

			}

			if (this.sotaikhoan_tp.length() > 0) {
				ResultSet rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan_tp + "'");
				System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan_tp + "'");
				if (rs != null) {
					rs.next();
					this.nganhang_tpId = rs.getString("NGANHANG_FK");
					this.chinhanh_tpId = rs.getString("CHINHANH_FK");
					rs.close();
				}
			} else {
				this.nganhang_tpId = "";
				this.chinhanh_tpId = "";

			}
			if(this.khachHang_NPP_FK==null || this.khachHang_NPP_FK.length()==0)
			{
				this.khachHang_NPP_FK="NULL";
			}

			System.out.println("Hoa don lis	t size: " + this.hoadonList.size());
			for (int i = 0; i < this.hoadonList.size(); i++) {
				IHoadon hd = this.hoadonList.get(i);
				if (hd.getThanhtoan().length() > 0)
					tongthanhtoan += Double.parseDouble(hd.getThanhtoan().replaceAll(",", ""));
			}

			if (this.DoiTuongChiPhiKhac.equals("3")) {
				tongthanhtoan = Double.parseDouble(this.sotientt.replaceAll(",", ""));
			}

			System.out.println("[ErpThanhtoanhoadon.createTTHD] tongthanhtoan = " + tongthanhtoan);

			// String tientt = this.sotientt;
			if (nhomNCCCNId.length() > 0) {
				String query = "Insert ERP_THANHTOANHOADON"
						+ "(DVTH_FK, NGAYGHINHAN, NHOMNCCCN ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, "
						+ " SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, "
						+ " PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, "
						+ " TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, "
						+ " NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA, THANHTOANTUTIENVAY, CONGTY_FK, NPP_FK,PREFIX  "
						+ ", soChungTu, soChungTu_So,soChungTu_Chu,NGUOINHANTIEN, doiTuongKhac_FK, khachHang_NPP_FK) \n" 
						+ "values(" + (this.bophanId.length() == 0 ? null : this.bophanId) + ", '"
						+ this.ngayghinhan + "', " + (this.nhomNCCCNId.length() == 0 ? null : this.nhomNCCCNId) + ","
						+ "" + (this.NhanvienId.length() == 0 ? null : this.NhanvienId) + ", '" + this.htttId + "', "
						+ "" + (this.nganhangId.length() == 0 ? null : this.nganhangId) + ", "
						+ (this.chinhanhId.length() == 0 ? null : this.chinhanhId) + ", '" + this.sotaikhoan + "', N'"
						+ this.noidungtt + "', " + "" + ("" + this.sotientt).replaceAll(",", "") + ", "
						+ this.sotienttNT.replaceAll(",", "") + ", " + this.sotienHD.replaceAll(",", "") + ", "
						+ this.sotienHDNT.replaceAll(",", "") + " , " + "" + this.pnganhang.replaceAll(",", "") + ", "
						+ this.pnganhangNT.replaceAll(",", "") + ", " + this.thueVAT.replaceAll(",", "") + ", "
						+ this.thueVATNT.replaceAll(",", "") + ", " + this.chenhlechVND.replaceAll(",", "") + ", " + ""
						+ this.trichphi + ", " + this.sotaikhoan_tp + ", "
						+ (this.nganhang_tpId.length() == 0 ? null : this.nganhang_tpId) + ", " + ""
						+ (this.chinhanh_tpId.length() == 0 ? null : this.chinhanh_tpId) + ", " + "'" + ngaytao + "', '"
						+ this.userId + "', '" + ngaytao + "', '" + this.userId + "','" + this.LoaiThanhToan + "', "
						+ this.tienteId + ", " + this.tigia.replaceAll(",", "") + " , '" + this.checkThanhtoantuTV
						+ "', " + this.ctyId + ", " + this.nppdangnhap + ", 'DNBN'\n" +
						", '" + this.soChungTu + "', '" + this.soChungTu_So + "','"+this.soChungTu_Chu+"', " + this.doiTuongKhacId + ", " + this.khachHang_NPP_FK + " "
						+ "\n)";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			} else {

				String isTICHLUY = "0";				
				if(this.DoiTuongChiPhiKhac.equals("5")) isTICHLUY = "1";
				
				String query = "Insert ERP_THANHTOANHOADON "
						+ "(DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, "
						+ " SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, "
						+ " PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, "
						+ " TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, "
						+ " NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, CONGTY_FK, NPP_FK, PREFIX, isNPP, isTICHLUY "
						+ " , soChungTu, soChungTu_So,soChungTu_Chu, doiTuongKhac_FK, khachHang_NPP_FK) " + "values(" + (this.bophanId.length() == 0 ? null : this.bophanId) + ", '"
						+ this.ngayghinhan + "', " + (this.nccId.length() == 0 ? null : this.nccId) + "," + ""
						+ (this.NhanvienId.length() == 0 ? null : this.NhanvienId) + ", '" + this.htttId + "', " + ""
						+ (this.nganhangId.length() == 0 ? null : this.nganhangId) + ", "
						+ (this.chinhanhId.length() == 0 ? null : this.chinhanhId) + ", '" + this.sotaikhoan + "', N'"
						+ this.noidungtt + "', " + "" + ("" + this.sotientt).replaceAll(",", "") + ", "
						+ this.sotienttNT.replaceAll(",", "") + ", " + this.sotienHD.replaceAll(",", "") + ", "
						+ this.sotienHDNT.replaceAll(",", "") + " , " + "" + this.pnganhang.replaceAll(",", "") + ", "
						+ this.pnganhangNT.replaceAll(",", "") + ", " + this.thueVAT.replaceAll(",", "") + ", "
						+ this.thueVATNT.replaceAll(",", "") + ", " + this.chenhlechVND.replaceAll(",", "") + ", " + ""
						+ this.trichphi + ", '" + this.sotaikhoan_tp + "', "
						+ (this.nganhang_tpId.length() == 0 ? null : this.nganhang_tpId) + ", "
						+ (this.chinhanh_tpId.length() == 0 ? null : this.chinhanh_tpId) + ", " + "'" + ngaytao + "', '"
						+ this.userId + "', '" + ngaytao + "', '" + this.userId + "','" + this.LoaiThanhToan + "', "
						+ this.tienteId + ", " + this.tigia.replaceAll(",", "") + ", '" + this.checkThanhtoantuTV
						+ "', " + (this.khachhangId.length() == 0 ? null : this.khachhangId) + ", " + this.ctyId + ", "
						+ this.nppdangnhap + ", 'DNBN', '" + this.isNpp + "', "+isTICHLUY+" \n" +
						", '" + this.soChungTu + "', '" + this.soChungTu_So + "','"+this.soChungTu_Chu+"'," + this.doiTuongKhacId + ", " + this.khachHang_NPP_FK + " "
						+ "\n)";

				if (this.DoiTuongChiPhiKhac.equals("3")) {
					query = "Insert ERP_THANHTOANHOADON(NGAYGHINHAN, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT,  SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT,"
							+ " TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, "
							+ " PHINGANHANG, PHINGANHANGNT, VAT, VATNT, "
							+ " NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA,LOAITHANHTOAN ,"
							+ " TIENTE_FK, TIGIA, THANHTOANTUTIENVAY, CONGTY_FK, NPP_FK, PREFIX , soChungTu, soChungTu_So,soChungTu_Chu, doiTuongKhac_FK, khachHang_NPP_FK) " + "values('"
							+ this.ngayghinhan + "', '" + this.htttId + "', "
							+ (this.nganhangId.length() == 0 ? null : this.nganhangId) + ", "
							+ (this.chinhanhId.length() == 0 ? null : this.chinhanhId) + ", '" + this.sotaikhoan
							+ "', N'" + this.noidungtt + "', " + "" + ("" + this.sotientt).replaceAll(",", "") + ","
							+ this.sotienttNT.replaceAll(",", "") + "," + this.sotienHD.replaceAll(",", "") + ","
							+ this.sotienHDNT.replaceAll(",", "") + " ," + "" + this.trichphi + ", '"
							+ this.sotaikhoan_tp + "', "
							+ (this.nganhang_tpId.length() == 0 ? null : this.nganhang_tpId) + ", "
							+ (this.chinhanh_tpId.length() == 0 ? null : this.chinhanh_tpId) + ", " + ""
							+ this.pnganhang.replaceAll(",", "") + ", " + this.pnganhangNT.replaceAll(",", "") + ", "
							+ this.thueVAT.replaceAll(",", "") + ", " + this.thueVATNT.replaceAll(",", "") + ", " + "'"
							+ ngaytao + "', '" + this.userId + "', '" + ngaytao + "', '" + this.userId + "','"
							+ this.LoaiThanhToan + "'," + this.tienteId + ", "
							+ this.tigia.replaceAll(",", "") + " ,'" + this.checkThanhtoantuTV + "', " + this.ctyId
							+ ", " + this.nppdangnhap + ", 'DNBN'\n" +
							", '" + this.soChungTu + "', '" + this.soChungTu_So + "','"+this.soChungTu_Chu+"'," + this.doiTuongKhacId + ", " + this.khachHang_NPP_FK + " "
							+ "\n)";

				}

				System.out.println(query);
				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}

			String query = "select IDENT_CURRENT('ERP_THANHTOANHOADON') as tthdId";

			ResultSet rsTthd = db.get(query);
			if (rsTthd.next()) {
				tthdCurrent = rsTthd.getString("tthdId");
				rsTthd.close();
			}

			if (this.bophanId.trim().length() > 0) // THANH TOÁN THEO BỘ PHẬN
			{
				for (int i = 0; i < this.hoadonList.size(); i++) {
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = (hoadon.getThanhtoan().replaceAll(",", ""));
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					String loaihd = hoadon.getLoaihd1();
					String sohoadon = hoadon.getSo();

					String doituong = hoadon.getDoituong();
					String nhanvienId = "NULL";
					String nccId = "NULL";
					if (doituong.equals("0"))
						nhanvienId = hoadon.getDoituongId();
					else
						nccId = hoadon.getDoituongId();

					if (thanhtoan.length() > 0) {
						if (Float.parseFloat(thanhtoan) != 0) {
							if (this.tienteId.equals("100000")) {
								query = "Insert ERP_THANHTOANHOADON_HOADONBOPHAN(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, NHANVIEN_FK, NCC_FK, SOHOADON) "
										+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', "
										+ nhanvienId + ", " + nccId + ", '" + sohoadon + "')";

							} else {
								query = "Insert ERP_THANHTOANHOADON_HOADONBOPHAN(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, NHANVIEN_FK, NCC_FK, SOHOADON) "
										+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
										+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', " + nhanvienId
										+ ", " + nccId + ", '" + sohoadon + "')";

							}
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADONBOPHAN: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			} else {
				for (int i = 0; i < this.hoadonList.size(); i++) {
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = (hoadon.getThanhtoan().replaceAll(",", ""));
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					String loaihd = hoadon.getLoaihd1();
					String loaiThue = hoadon.getLoaiThue();
					String sohoadon = hoadon.getSo();
					String tigiahd = "1";
					String tientehd = "100000";
					String diaChiHd = hoadon.getDiaChi();
					if(hoadon.getTigia()!=null) tigiahd = hoadon.getTigia().replaceAll(",", "");
					if(hoadon.getTienteId()!=null) tientehd = hoadon.getTienteId().replaceAll(",", "");
					
					if (thanhtoan.length() > 0) {
						if (Float.parseFloat(thanhtoan) != 0) {
							if (this.tienteId.equals("100000")) {
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON, TIENTE_FK , TIGIA, DIACHI) "
										+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', '"
										+ sohoadon + "', "+tientehd+", "+tigiahd+", N'"+diaChiHd+"')";
								if (loaihd.equals("4")) {
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON, TIENTE_FK, TIGIA,LOAITHUENK, DIACHI) "
											+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '"
											+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
											+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd
											+ "', N'" + hoadon.getKyhieu() + "', '" + sohoadon + "', "+tientehd+", "+tigiahd+",'"+loaiThue+"',  N'"+diaChiHd+"')";
								}

							} else {
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON, TIENTE_FK, TIGIA, DIACHI) "
										+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
										+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', '" + sohoadon+ "', "+tientehd+", "+tigiahd+",  N'"+diaChiHd+"')";
								if (loaihd.equals("4")) {
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON, TIENTE_FK, TIGIA,LOAITHUENK, DIACHI) "
											+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '"
											+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
											+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
											+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', N'"
											+ hoadon.getKyhieu() + "', '" + sohoadon + "', "+tientehd+", "+tigiahd+",'"+loaiThue+"',  N'"+diaChiHd+"')";
								}
							}
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			}

			if (!this.DoiTuongChiPhiKhac.equals("3")) {
				query = "INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, "
						+ "NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) "
						+ "VALUES(" + tthdCurrent + ", N'" + this.mahoadon + "', N'" + this.mauhoadon + "', N'"
						+ this.kyhieu + "', '" + this.sohoadon + "'," + "'" + this.ngayghinhan + "', N'" + this.tenNCC
						+ "', '" + this.mst + "', " + this.tienhang.replaceAll(",", "") + ", "
						+ this.thuesuat.replaceAll(",", "") + ", " + this.tienthue.replaceAll(",", "") + ", " + ""
						+ (this.nhId_VAT.length() == 0 ? null : this.nhId_VAT) + ", "
						+ (this.cnId_VAT.length() == 0 ? null : this.cnId_VAT) + ")";
				System.out.println(query);
				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON_PHINGANHANG: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			} 
			else {
				int i=0; 
				if(Tienhanghd !=null)
				{
					while(Tienhanghd.length>i)
					{						
						System.out.println("Tienhanghd[i].trim():"+Tienhanghd[i].trim());
						if(Tienhanghd[i].trim().length() <=0)
							Tienhanghd[i] = "0";
						
						if(Double.parseDouble(Tienhanghd[i].trim().replaceAll(",", "")) > 0)
						{
							if(Thuesuathd[i].replaceAll(",", "").trim().length()<=0)
								Thuesuathd[i]  = "0";
							if(Tienthuehd[i].replaceAll(",", "").trim().length()<=0)
								Tienthuehd[i]  = "0";
							if(PhongBanIds[i].trim().length()<=0)
								PhongBanIds[i]  = "null";							
							if(KenhbhIds[i].trim().length()<=0)
								KenhbhIds[i]  = "null";
							if(TinhThanhIds[i].trim().length()<=0)
								TinhThanhIds[i]  = "null";							
							if(SanPhamIds[i].trim().length()<=0)
								SanPhamIds[i]  = "null";
							if(DiabanIds[i].trim().length()<=0)
								DiabanIds[i]  = "null";
							if(MavvIds[i].trim().length()<=0)
								MavvIds[i]  = "null";
							if(BenhvienIds[i].trim().length()<=0)
								BenhvienIds[i]  = "null";
							if(Diengiaihd[i].trim().length()<=0)
								Diengiaihd[i]  = "";
							if(diaChiHd[i] == null || diaChiHd[i].trim().length()<=0)
								diaChiHd[i] = "";
							
							if(TaiKhoanIds[i]!="")
							{

								String []TkId=TaiKhoanIds[i].split("_");
																
								query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
										"DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN " +
										"FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + TkId[0] + "";
								System.out.println(query);
								ResultSet rs  = this.db.get(query);
								rs.next();
								
								System.out.println("dcIds[i]:"+dcIds[i]);
								// KIẾM TRA XEM CÓ CHỌN ĐỐI TƯỢNG CHƯA
								if(rs.getString("DUNGCHONHANVIEN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHONCC").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHONGANHANG").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}

								if(rs.getString("DUNGCHOTAISAN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHOKHACHHANG").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
																
								if(rs.getString("DUNGCHONHANVIEN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}

								if(rs.getString("COTTCHIPHI").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn trung tâm chi phí.";
									return false;
								}
								if(rs.getString("COTTCHIPHI").equals("1")&& ttcpIds[i].equals("") ){
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn khoản mục chi phí";
									return false;
								}
								if(rs.getString("SOHIEUTAIKHOAN").startsWith("6")){
									if(ttcpIds[i].trim().length()==0)
									{
										this.db.getConnection().rollback();
										this.msg = "Vui lòng chọn khoản mục chi phí.";
										return false;
									}
									
								}

								if(dcIds[i].trim().length()<=0)
									dcIds[i]  = "null";
								if(ttcpIds[i] == null)
										ttcpIds[i] = "null";
								if(ttcpIds[i] != null)
									if(ttcpIds[i].trim().length() <=0)
										ttcpIds[i] = "null";
								
								if(loais[i].equals("1") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, SANPHAM_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI   ) VALUES " +
										" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
										"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
										dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
									
								}else						
								if(loais[i].equals("2") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NCC_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI   ) VALUES " +
											" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
									
								}else						
								if(loais[i].equals("3") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI   ) VALUES " +
											" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";									
								}else
								if(loais[i].equals("4") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE,TAISAN_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI   ) VALUES " +
											" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";							
									
								}else
								if(loais[i].equals("5") ){ // KHÁCH HÀNG
									//CHỖ NÀY ĐỂ PHÂN BIỆT LÀ NPP HAY KHÁCH HÀNG HAY NHÂN VIÊN
									String isNPP = "NULL";
									String kh_noId = "null";		
									
									kh_noId = dcIds[i];
									
									if( kh_noId.contains("-") )
									{
										String kh[] = kh_noId.split("-");
										kh_noId = kh[0];
										isNPP = kh[1];
									}
									
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, KHACHHANG_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI,ISNPP, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											kh_noId+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+isNPP+", "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";	
									
								}else 
								if(loais[i].equals("7") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NHANVIEN_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK , SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";	
									
								}else
								if(loais[i].equals("6") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, TTCP_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";								
								}else
									if(loais[i].equals("8") ){
										query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, DOITUONGKHAC_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
												" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
												"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
												dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";								
									}								
								else {
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+tthdCurrent+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";			
								}
														
								System.out.println(query);
								if(!this.db.update(query))
								{
									this.db.getConnection().rollback();
									this.msg="Loi dong lenh sau "+query;
									return false;
								}
							
							}
							else
							{
								this.msg="Vui lòng chọn tài khoản !";
								return false;
							}
							
					}
						
						i++;
				}
					
			}	
			
			}
			
			// cập nhật mã chứng từ
			query = " update ERP_THANHTOANHOADON set machungtu = PREFIX + SUBSTRING(NGAYGHINHAN, 6, 2) + SUBSTRING(NGAYGHINHAN, 0, 5) + '-' + dbo.LaySoChungTu( " + tthdCurrent + " ) " + 
					" where pk_seq = '" + tthdCurrent + "' ";
			
			System.out.println("[ERP_THANHTOANHOADON] error message:" + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
				System.out.println("[ERP_THANHTOANHOADON] error message:" + this.msg);
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("Loi trong qua trinh update : " + e.toString());
			this.msg = e.toString();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}

		return true;
	}

	public boolean updateTTHD() {
		if (this.doiTuongKhacId != null && this.doiTuongKhacId.trim().length() == 0)
			this.doiTuongKhacId = null;
		
		this.soChungTu = this.soChungTu_Chu + this.soChungTu_So;
		if (!this.DoiTuongChiPhiKhac.equals("3")) {
			if (this.hoadonList.size() < 0) {
				this.msg = "Không có hóa đơn nào để thanh toán";
				return false;
			} else {
				// neu hoa dơn nao bị bỏ chọn thì xóa trong bảng
				// thanhtoanhoadonn_hoadon
				List<String> hdMois = new ArrayList<String>();
				for (IHoadon hd : hoadonList) {
					hdMois.add(hd.getId());
				}
				List<String> listHDHienTai = GetlistHoaDonHienTai();
				for (String hdHienTai : listHDHienTai) {
					if (!hdMois.contains(hdHienTai)) {
						XoaThanhToanHoaDon_HoaDon(hdHienTai);
					}
				}

			}
		}

		if (this.htttId.length() <= 0) {
			this.msg = "Vui lòng chọn hình thức thanh toán";
			return false;
		}

		if (this.htttId.equals("100001") || this.htttId.equals("100003")) {
			System.out.println("SOTAIKHOAN:" + this.sotaikhoan);
			if (this.sotaikhoan.trim().length() <= 0) {
				if (this.checkThanhtoantuTV.equals("1"))
					this.msg = "Vui lòng nhập Số tài khoản để trích phí ngân hàng !";
				else
					this.msg = "Vui lòng nhập Số tài khoản  !";
				return false;
			}
		}

		// Tinh lai tong tien
//		double tongthanhtoan = 0;
//		for (int i = 0; i < this.hoadonList.size(); i++) {
//			IHoadon hd = this.hoadonList.get(i);
//			if (hd.getThanhtoan().length() > 0)
//				tongthanhtoan += Double.parseDouble(hd.getThanhtoan().replaceAll(",", ""));
//		}

//		if (this.DoiTuongChiPhiKhac.equals("3")) {
//			tongthanhtoan = Double.parseDouble(this.sotientt.replaceAll(",", ""));
//		}

		try {
			String ngaysua = getDateTime();

			db.getConnection().setAutoCommit(false);
			
			if(geso.traphaco.center.util.Utility.checkSoChungTu(db, this.soChungTu,
					geso.traphaco.center.util.Utility.tblPC_UNC,
					geso.traphaco.center.util.Utility.httt_CK, this.id))
			{
				this.msg = "Số chứng từ " + this.soChungTu + " đã tồn tại trong hệ thống. Vui lòng kiểm tra lại !";
				db.getConnection().rollback();
				return false;
			}
			

			if (this.DoiTuongDinhKhoan.trim().length() <= 0) {
				this.DoiTuongDinhKhoan = "NULL";

				if (this.doituongdinhkhoanId == null || this.doituongdinhkhoanId.trim().length() <= 0)
					this.doituongdinhkhoanId = "NULL";
			}

			if (this.sotaikhoan.length() > 0) {
				ResultSet rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan + "'");
				System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan + "'");
				if (rs != null) {
					rs.next();
					this.nganhangId = rs.getString("NGANHANG_FK");
					this.chinhanhId = rs.getString("CHINHANH_FK");
					rs.close();
				}
			} else {

				this.sotaikhoan = "NULL";
			}

			if (this.sotaikhoan_tp.length() > 0) {
				ResultSet rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan_tp + "'");
				System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan_tp + "'");
				if (rs != null) {
					rs.next();
					this.nganhang_tpId = rs.getString("NGANHANG_FK");
					this.chinhanh_tpId = rs.getString("CHINHANH_FK");
					rs.close();
				}
			} else {
				this.sotaikhoan_tp = "NULL";

			}
			if(this.khachHang_NPP_FK==null || this.khachHang_NPP_FK.length()==0)
			{
				this.khachHang_NPP_FK="NULL";
			}
			
			String query = "";

			String isTICHLUY = "0";
			
			if(this.DoiTuongChiPhiKhac.equals("5")) isTICHLUY = "1";
			
			query = "update ERP_THANHTOANHOADON " + "set NGAYGHINHAN = '" + this.ngayghinhan + "', " + "DVTH_FK="
					+ (this.bophanId.length() > 0 ? this.bophanId : null) + " , " + "NCC_FK = "
					+ (this.nccId.length() > 0 ? this.nccId : null) + ", " + "nhanvien_fk="
					+ (this.NhanvienId.length() > 0 ? this.NhanvienId : null) + " , " + "HTTT_FK = '" + this.htttId
					+ "', NGANHANG_FK = " + (this.nganhangId.length() == 0 ? null : this.nganhangId) + ", "
					+ "CHINHANH_FK = " + (this.chinhanhId.length() == 0 ? null : this.chinhanhId) + ", "
					+ "SOTAIKHOAN = '" + this.sotaikhoan + "'," + " NOIDUNGTT = N'" + this.noidungtt.replace("'", "&apos;") + "', "
					+ "SOTIENTT = " + ("" + this.sotientt).replaceAll(",", "") + ", SOTIENTTNT = "
					+ this.sotienttNT.replaceAll(",", "") + ", " + "SOTIENHD = " + this.sotienHD.replaceAll(",", "")
					+ ", " + "SOTIENHDNT = " + this.sotienHDNT.replaceAll(",", "") + ", PHINGANHANG = "
					+ this.pnganhang.replaceAll(",", "") + ", " + "PHINGANHANGNT = "
					+ this.pnganhangNT.replaceAll(",", "") + ", " + "VAT = " + this.thueVAT.replaceAll(",", "")
					+ ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " + "CHENHLECHVND = "
					+ this.chenhlechVND.replaceAll(",", "") + ", " + "TRICHPHI = " + this.trichphi
					+ ", SOTAIKHOAN_TP = '" + this.sotaikhoan_tp + "', " + "NGANHANG_TP_FK = " + this.nganhang_tpId
					+ ", CHINHANH_TP_FK = " + this.chinhanh_tpId + ", " + "NGAYSUA = '" + ngaysua + "', NGUOISUA = '"
					+ this.userId + "', TIENTE_FK = " + this.tienteId + "," + " TIGIA = "
					+ this.tigia.replaceAll(",", "") + ", THANHTOANTUTIENVAY = '" + this.checkThanhtoantuTV
					+ "', khachhang_fk=" + (this.khachhangId.length() > 0 ? this.khachhangId : null)
					+ ", PREFIX ='DNBN', isNPP = '" + this.isNpp + "'  " + ", isTICHLUY = " +isTICHLUY+ " \n" +
					", soChungTu_Chu = '" + this.soChungTu_Chu + "'" +
					", soChungTu = '" + this.soChungTu + "'\n" +
					", soChungTu_So = '" + this.soChungTu_So + "', doiTuongKhac_FK = " + this.doiTuongKhacId + ", khachHang_NPP_FK = " + this.khachHang_NPP_FK + " -- \n" +
					" where PK_SEQ = '"  + this.id + "' and trangthai=0\n";

			System.out.println("Update: " + query);

			if (this.DoiTuongDinhKhoan.equals("3")) {
				query = "update ERP_THANHTOANHOADON " + "set  NGAYGHINHAN = '" + this.ngayghinhan + "', "
						+ "HTTT_FK = '" + this.htttId + "', NGANHANG_FK = "
						+ (this.nganhangId.length() == 0 ? null : this.nganhangId) + ", " + "CHINHANH_FK = "
						+ (this.chinhanhId.length() == 0 ? null : this.chinhanhId) + ", " + "SOTAIKHOAN = '"
						+ this.sotaikhoan + "'," + " NOIDUNGTT = N'" +  this.noidungtt.replace("'", "&apos;") + "', " + "SOTIENTT = "
						+ ("" + this.sotientt).replaceAll(",", "") + ", SOTIENTTNT = "
						+ this.sotienttNT.replaceAll(",", "") + ", " + "SOTIENHD = " + this.sotienHD.replaceAll(",", "")
						+ ", " + "SOTIENHDNT = " + this.sotienHDNT.replaceAll(",", "") + ", PHINGANHANG = "
						+ this.pnganhang.replaceAll(",", "") + ", " + "PHINGANHANGNT = "
						+ this.pnganhangNT.replaceAll(",", "") + ", " + "VAT = " + this.thueVAT.replaceAll(",", "")
						+ ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " + "TRICHPHI = " + this.trichphi
						+ ", SOTAIKHOAN_TP = '" + this.sotaikhoan_tp + "', " + "NGANHANG_TP_FK = " + this.nganhang_tpId
						+ ", CHINHANH_TP_FK = " + this.chinhanh_tpId + ", " + "NGAYSUA = '" + ngaysua
						+ "', NGUOISUA = '" + this.userId + "', TIENTE_FK = " + this.tienteId + ", TIGIA = "
						+ this.tigia.replaceAll(",", "") + ", THANHTOANTUTIENVAY = '" + this.checkThanhtoantuTV
						+ "', PREFIX ='DNBN'  " + " \n" +
						", soChungTu_Chu = '" + this.soChungTu_Chu + "'" +
						", soChungTu = '" + this.soChungTu + "'\n" +
						", soChungTu_So = '" + this.soChungTu_So + "', doiTuongKhac_FK = " + this.doiTuongKhacId + ", khachHang_NPP_FK = " + this.khachHang_NPP_FK + " -- \n" +
						" where PK_SEQ = '"  + this.id + "' and trangthai=0\n";
			}

			// NẾU CÓ CHECK THANH TOÁN TỪ TIỀN VAY : KHI CẬP NHẬT CHỈ ĐƯỢC SỬA
			// PHÍ NGÂN HÀNG + THUE CÒN LẠI KHÔNG CHO SỬA
			if (Double.parseDouble(this.checkThanhtoantuTV) == 1) {
				query = "update ERP_THANHTOANHOADON " + "set SOTIENTT = " + ("" + this.sotientt).replaceAll(",", "")
						+ ", SOTIENTTNT = " + this.sotienttNT.replaceAll(",", "") + ", " + "PHINGANHANG = "
						+ this.pnganhang.replaceAll(",", "") + ", PHINGANHANGNT = "
						+ this.pnganhangNT.replaceAll(",", "") + ", " + "VAT = " + this.thueVAT.replaceAll(",", "")
						+ ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " + "CHENHLECHVND = "
						+ this.chenhlechVND.replaceAll(",", "") + ", " + "NGAYSUA = '" + ngaysua + "', NGUOISUA = '"
						+ this.userId + "', PREFIX ='DNBN'  " + " \n" +
						", soChungTu_Chu = '" + this.soChungTu_Chu + "'" +
						", soChungTu = '" + this.soChungTu + "'\n" +
						", soChungTu_So = '" + this.soChungTu_So + "', doiTuongKhac_FK = " + this.doiTuongKhacId + ", khachHang_NPP_FK = " + this.khachHang_NPP_FK + " \n" +
						" where PK_SEQ = '"  + this.id + "' and trangthai=0\n";
			}

			query = UtilityKeToan.refactorQuery(query);
			if (db.updateReturnInt(query)!=1) {
				this.msg = "Khong the cap nhat ERP_THANHTOANHOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			if (Double.parseDouble(this.checkThanhtoantuTV) == 0) {
				query = "delete ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = '" + this.id + "'";
				db.update(query);
			}

			query = "delete ERP_THANHTOANHOADON_TAMUNG where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			query = "delete ERP_THANHTOANHOADON_CHIPHIKHAC where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			query = "delete ERP_THANHTOANHOADON_PHINGANHANG where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			query = "delete ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			System.out.println("KICH thuoc hoa don " + this.hoadonList.size());

			if (this.bophanId.trim().length() > 0) {
				for (int i = 0; i < this.hoadonList.size(); i++) {
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = (hoadon.getThanhtoan().replaceAll(",", ""));
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					String loaihd = hoadon.getLoaihd1();
					String sohoadon = hoadon.getSo();

					String doituong = hoadon.getDoituong();
					String nhanvienId = "NULL";
					String nccId = "NULL";
					if (doituong.equals("0"))
						nhanvienId = hoadon.getDoituongId();
					else
						nccId = hoadon.getDoituongId();

					if (thanhtoan.length() > 0) {
						if (Float.parseFloat(thanhtoan) != 0) {
							if (this.tienteId.equals("100000")) {
								query = "Insert ERP_THANHTOANHOADON_HOADONBOPHAN(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, NHANVIEN_FK, NCC_FK, SOHOADON) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', "
										+ nhanvienId + ", " + nccId + ", '" + sohoadon + "')";

							} else {
								query = "Insert ERP_THANHTOANHOADON_HOADONBOPHAN(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, NHANVIEN_FK, NCC_FK, SOHOADON) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
										+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', " + nhanvienId
										+ ", " + nccId + ", '" + sohoadon + "')";

							}
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADONBOPHAN: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			} else {

				for (int i = 0; i < this.hoadonList.size(); i++) {
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					String loaihd = hoadon.getLoaihd1();
					String sohoadon = hoadon.getSo();
					String loaiThue = hoadon.getLoaiThue();
					String tigiahd = "1";
					String tientehd = "100000";
					String diaChiHd = hoadon.getDiaChi();
					if(hoadon.getTigia()!=null) tigiahd = hoadon.getTigia().replaceAll(",", "");
					if(hoadon.getTienteId()!=null) tientehd = hoadon.getTienteId().replaceAll(",", "");
					
					if (thanhtoan.length() > 0) {
						if (Float.parseFloat(thanhtoan) != 0) {
							if (this.tienteId.equals("100000")) {
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON, TIENTE_FK, TIGIA, DIACHI) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', '"
										+ sohoadon + "', "+tientehd+", "+tigiahd+",  N'"+diaChiHd+"')";
								if (loaihd.equals("4")) {
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON, TIENTE_FK, TIGIA,LOAITHUENK, DIACHI ) "
											+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
											+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
											+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd
											+ "', N'" + hoadon.getKyhieu() + "', '" + sohoadon + "', "+tientehd+", "+tigiahd+",'"+loaiThue+"',  N'"+diaChiHd+"')";
								}

							} else {
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON, TIENTE_FK, TIGIA, DIACHI ) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
										+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', '" + sohoadon+ "', "+tientehd+", "+tigiahd+",  N'"+diaChiHd+"')";
								if (loaihd.equals("4")) {
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON, TIENTE_FK, TIGIA,LOAITHUENK, DIACHI ) "
											+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
											+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
											+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
											+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', N'"
											+ hoadon.getKyhieu() + "', '" + sohoadon + "', "+tientehd+", "+tigiahd+",'"+loaiThue+"',  N'"+diaChiHd+"')";

								}
							}
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
							
						}
					}
				}

			}

			if (!this.DoiTuongChiPhiKhac.equals("3")) {
				query = "INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, "
						+ "NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) "
						+ "VALUES(" + this.id + ", N'" + this.mahoadon + "', N'" + this.mauhoadon + "', N'"
						+ this.kyhieu + "', '" + this.sohoadon + "'," + "'" + this.ngayghinhan + "', N'" + this.tenNCC
						+ "', '" + this.mst + "', " + this.tienhang.replaceAll(",", "") + ", "
						+ this.thuesuat.replaceAll(",", "") + ", " + this.tienthue.replaceAll(",", "") + ", " + ""
						+ (this.nhId_VAT.length() == 0 ? null : this.nhId_VAT) + ", "
						+ (this.cnId_VAT.length() == 0 ? null : this.cnId_VAT) + ")";
				System.out.println(query);
				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON_PHINGANHANG: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			} else {
				int i=0; 
				if(Tienhanghd !=null)
				{
					while(Tienhanghd.length>i)
					{						
						if(Tienhanghd[i].trim().length() <=0)
							Tienhanghd[i] = "0";
						
						if(Double.parseDouble(Tienhanghd[i].trim().replaceAll(",", "")) > 0)
						{
							if(Thuesuathd[i].replaceAll(",", "").trim().length()<=0)
								Thuesuathd[i]  = "0";
							if(Tienthuehd[i].replaceAll(",", "").trim().length()<=0)
								Tienthuehd[i]  = "0";							
							if(PhongBanIds[i].trim().length()<=0)
								PhongBanIds[i]  = "null";							
							if(KenhbhIds[i].trim().length()<=0)
								KenhbhIds[i]  = "null";
							if(TinhThanhIds[i].trim().length()<=0)
								TinhThanhIds[i]  = "null";							
							if(SanPhamIds[i].trim().length()<=0)
								SanPhamIds[i]  = "null";
							if(DiabanIds[i].trim().length()<=0)
								DiabanIds[i]  = "null";
							if(MavvIds[i].trim().length()<=0)
								MavvIds[i]  = "null";
							if(BenhvienIds[i].trim().length()<=0)
								BenhvienIds[i]  = "null";
							if(Diengiaihd[i].trim().length()<=0)
								Diengiaihd[i]  = "";
							if(diaChiHd[i].trim().length()<=0 || diaChiHd[i] == null)
								diaChiHd[i]  = "";
							
							if(TaiKhoanIds[i]!="")
							{

								String []TkId=TaiKhoanIds[i].split("_");
								
								query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, " +
										"DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + TkId[0] + "";
								System.out.println(query);
								ResultSet rs  = this.db.get(query);
								rs.next();
																
								// KIẾM TRA XEM CÓ CHỌN ĐỐI TƯỢNG CHƯA
								if(rs.getString("DUNGCHONHANVIEN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHONCC").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHONGANHANG").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}

								if(rs.getString("DUNGCHOTAISAN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHOKHACHHANG").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
																
								if(rs.getString("DUNGCHONHANVIEN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}

								if(rs.getString("COTTCHIPHI").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn trung tâm chi phí.";
									return false;
								}
								if(rs.getString("SOHIEUTAIKHOAN").startsWith("6")){
									if(ttcpIds[i].trim().length()==0)
									{
										this.db.getConnection().rollback();
										this.msg = "Vui lòng chọn khoản mục chi phí.";
										return false;
									}
									
								}
								// Dùng cho : 1 Kho, 2 NCC, 3 Ngân hàng, 4 Tài Sản, 5 Khách hàng, 6 TTCP, 7 Nhân viên																
								
								if(dcIds[i].trim().length()<=0)
									dcIds[i]  = "null";
								if(ttcpIds[i].trim().length()<=0)
									ttcpIds[i]  = "null";
								 if(ttcpIds[i] != null)
							         if(ttcpIds[i].trim().length() <=0)
							          ttcpIds[i] = "null";
								 
								if(loais[i].equals("1") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, SANPHAM_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK , DIENGIAI, KHOANMUC_FK, DIACHI  ) VALUES " +
										" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
										"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
										dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";
									
								}else						
								if(loais[i].equals("2") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NCC_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI  ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";
									
								}else						
								if(loais[i].equals("3") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI  ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";									
								}else
								if(loais[i].equals("4") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE,TAISAN_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK , DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";
									
								}else
								if(loais[i].equals("5") ){
									
									//CHỖ NÀY ĐỂ PHÂN BIỆT LÀ NPP HAY KHÁCH HÀNG HAY NHÂN VIÊN
									String isNPP = "NULL";
									String kh_noId = "null";		
									
									kh_noId = dcIds[i];
									
									if( kh_noId.contains("-") )
									{
										String kh[] = kh_noId.split("-");
										kh_noId = kh[0];
										isNPP = kh[1];
									}
									
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, KHACHHANG_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI , ISNPP, KHOANMUC_FK, DIACHI) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											kh_noId+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+isNPP+", "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";
									
								}else 
								if(loais[i].equals("7") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NHANVIEN_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK , SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";
									
								}else
								if(loais[i].equals("6") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, TTCP_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", " +DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";									
								}else
									if(loais[i].equals("8") ){
										query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, DOITUONGKHAC_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
												" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
												"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
												dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", " +DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";									
									}								
								else {
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";
								}
														
								System.out.println(query);
								if(!this.db.update(query))
								{
									this.db.getConnection().rollback();
									this.msg="Loi dong lenh sau "+query;
									return false;
								}
							
							}
							else
							{
								this.msg="Vui lòng chọn tài khoản !";
								return false;
							}
							
					}
						
						i++;
				}
					
			}	
			
			}
			
//			// cập nhật mã chứng từ
			query = " update ERP_THANHTOANHOADON set machungtu = PREFIX + SUBSTRING(NGAYGHINHAN, 6, 2) + SUBSTRING(NGAYGHINHAN, 0, 5) + '-' + dbo.LaySoChungTu( " + this.id + " ) " + 
					" where pk_seq = '" + this.id + "' ";
			
			System.out.println("[ERP_THANHTOANHOADON] error message:" + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THANHTOANHOADON: " + query;
				System.out.println("[ERP_THANHTOANHOADON] error message:" + this.msg);
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}

		return true;
	}

	public boolean updateTTHD_TiGia() {
		// Tinh lai tong tien
//		double tongthanhtoan = 0;
//		for (int i = 0; i < this.hoadonList.size(); i++) {
//			IHoadon hd = this.hoadonList.get(i);
//			if (hd.getThanhtoan().length() > 0)
//				tongthanhtoan += Double.parseDouble(hd.getThanhtoan().replaceAll(",", ""));
//		}
//
//		if (this.DoiTuongChiPhiKhac.equals("3")) {
//			tongthanhtoan = Double.parseDouble(this.sotientt.replaceAll(",", ""));
//		}

		try {
			String ngaysua = getDateTime();

			db.getConnection().setAutoCommit(false);

			if (this.DoiTuongDinhKhoan.trim().length() <= 0) {
				this.DoiTuongDinhKhoan = "NULL";

				if (this.doituongdinhkhoanId.trim().length() <= 0)
					this.doituongdinhkhoanId = "NULL";
			}

			if (this.sotaikhoan.length() > 0) {
				ResultSet rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan + "'");
				System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan + "'");
				if (rs != null) {
					rs.next();
					this.nganhangId = rs.getString("NGANHANG_FK");
					this.chinhanhId = rs.getString("CHINHANH_FK");
					rs.close();
				}
			} else {

				this.sotaikhoan = "NULL";
			}

			if (this.sotaikhoan_tp.length() > 0) {
				ResultSet rs = this.db.get("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan_tp + "'");
				System.out.println("SELECT NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE SOTAIKHOAN = '"+ this.sotaikhoan_tp + "'");
				if (rs != null) {
					rs.next();
					this.nganhang_tpId = rs.getString("NGANHANG_FK");
					this.chinhanh_tpId = rs.getString("CHINHANH_FK");
					rs.close();
				}
			} else {
				this.sotaikhoan_tp = "NULL";

			}
			String query = "";

			query = "update ERP_THANHTOANHOADON " + "set SOTIENTT = " + ("" + this.sotientt).replaceAll(",", "") + ", SOTIENTTNT = "
					+ this.sotienttNT.replaceAll(",", "") + ", " + "SOTIENHD = " + this.sotienHD.replaceAll(",", "")
					+ ", " + "SOTIENHDNT = " + this.sotienHDNT.replaceAll(",", "") + ", PHINGANHANG = "
					+ this.pnganhang.replaceAll(",", "") + ", " + "PHINGANHANGNT = "
					+ this.pnganhangNT.replaceAll(",", "") + ", " + "VAT = " + this.thueVAT.replaceAll(",", "")
					+ ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " + "CHENHLECHVND = "
					+ this.chenhlechVND.replaceAll(",", "") + ", " + "TRICHPHI = " + this.trichphi
					+ ", NGAYSUA = '" + ngaysua + "', NGUOISUA = '"
					+ this.userId + "'," + " TIGIA = "
					+ this.tigia.replaceAll(",", "") + "\n" + 
					", soChungTu = '" + this.soChungTu + "'\n" +
					", soChungTu_So = '" + this.soChungTu_So + "'\n" + 
					" where PK_SEQ = '" + this.id + "'";

			System.out.println("Update: " + query);

			if (this.DoiTuongDinhKhoan.equals("3")) {
				query = "update ERP_THANHTOANHOADON " + "set SOTIENTT = "
						+ ("" + this.sotientt).replaceAll(",", "") + ", SOTIENTTNT = "
						+ this.sotienttNT.replaceAll(",", "") + ", " + "SOTIENHD = " + this.sotienHD.replaceAll(",", "")
						+ ", " + "SOTIENHDNT = " + this.sotienHDNT.replaceAll(",", "") + ", PHINGANHANG = "
						+ this.pnganhang.replaceAll(",", "") + ", " + "PHINGANHANGNT = "
						+ this.pnganhangNT.replaceAll(",", "") + ", " + "VAT = " + this.thueVAT.replaceAll(",", "")
						+ ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " + "TRICHPHI = " + this.trichphi
						+ ", NGAYSUA = '" + ngaysua
						+ "', NGUOISUA = '" + this.userId + "', TIENTE_FK = " + this.tienteId + ", TIGIA = "
						+ this.tigia.replaceAll(",", "") + " \n" +
						", soChungTu = '" + this.soChungTu + "'\n" +
						", soChungTu_So = '" + this.soChungTu_So + "'\n" + 
						"where PK_SEQ = '" + this.id + "'";
			}

			// NẾU CÓ CHECK THANH TOÁN TỪ TIỀN VAY : KHI CẬP NHẬT CHỈ ĐƯỢC SỬA
			// PHÍ NGÂN HÀNG + THUE CÒN LẠI KHÔNG CHO SỬA
			if (Double.parseDouble(this.checkThanhtoantuTV) == 1) {
				query = "update ERP_THANHTOANHOADON " + "set SOTIENTT = " + ("" + this.sotientt).replaceAll(",", "")
						+ ", SOTIENTTNT = " + this.sotienttNT.replaceAll(",", "") + ", " + "PHINGANHANG = "
						+ this.pnganhang.replaceAll(",", "") + ", PHINGANHANGNT = "
						+ this.pnganhangNT.replaceAll(",", "") + ", " + "VAT = " + this.thueVAT.replaceAll(",", "")
						+ ", VATNT = " + this.thueVATNT.replaceAll(",", "") + ", " + "CHENHLECHVND = "
						+ this.chenhlechVND.replaceAll(",", "") + ", " + "NGAYSUA = '" + ngaysua + "', NGUOISUA = '"
						+ this.userId + "', PREFIX ='DNBN'  " + " \n" +
						", soChungTu = '" + this.soChungTu + "'\n" +
						", soChungTu_So = '" + this.soChungTu_So + "'\n" + 
						"where PK_SEQ = '" + this.id + "'";
			}

			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_THANHTOANHOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			if (Double.parseDouble(this.checkThanhtoantuTV) == 0) {
				query = "delete ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = '" + this.id + "'";
				db.update(query);
			}

			query = "delete ERP_THANHTOANHOADON_TAMUNG where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			query = "delete ERP_THANHTOANHOADON_CHIPHIKHAC where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			query = "delete ERP_THANHTOANHOADON_PHINGANHANG where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			query = "delete ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = '" + this.id + "'";
			db.update(query);

			System.out.println("KICH thuoc hoa don " + this.hoadonList.size());

			if (this.bophanId.trim().length() > 0) {
				for (int i = 0; i < this.hoadonList.size(); i++) {
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = (hoadon.getThanhtoan().replaceAll(",", ""));
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					String loaihd = hoadon.getLoaihd1();
					String sohoadon = hoadon.getSo();

					String doituong = hoadon.getDoituong();
					String nhanvienId = "NULL";
					String nccId = "NULL";
					if (doituong.equals("0"))
						nhanvienId = hoadon.getDoituongId();
					else
						nccId = hoadon.getDoituongId();

					if (thanhtoan.length() > 0) {
						if (Float.parseFloat(thanhtoan) != 0) {
							if (this.tienteId.equals("100000")) {
								query = "Insert ERP_THANHTOANHOADON_HOADONBOPHAN(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, NHANVIEN_FK, NCC_FK, SOHOADON) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', "
										+ nhanvienId + ", " + nccId + ", '" + sohoadon + "')";

							} else {
								query = "Insert ERP_THANHTOANHOADON_HOADONBOPHAN(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, NHANVIEN_FK, NCC_FK, SOHOADON) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
										+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', " + nhanvienId
										+ ", " + nccId + ", '" + sohoadon + "')";

							}
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADONBOPHAN: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}
					}
				}
			} else {

				for (int i = 0; i < this.hoadonList.size(); i++) {
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					String loaihd = hoadon.getLoaihd1();
					String sohoadon = hoadon.getSo();
					String tigiahd = "1";
					String tientehd = "100000";
					String diaChiHd = hoadon.getDiaChi();
					if(hoadon.getTigia()!=null) tigiahd = hoadon.getTigia().replaceAll(",", "");
					if(hoadon.getTienteId()!=null) tientehd = hoadon.getTienteId().replaceAll(",", "");
					
					if (thanhtoan.length() > 0) {
						if (Float.parseFloat(thanhtoan) != 0) {
							if (this.tienteId.equals("100000")) {
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON, TIENTE_FK, TIGIA, DIACHI) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', '"
										+ sohoadon + "', "+tientehd+", "+tigiahd+",  N'"+diaChiHd+"')";
								if (loaihd.equals("4")) {
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON, TIENTE_FK, TIGIA, DIACHI ) "
											+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
											+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
											+ "'," + " 0, '" + conlai.trim().replaceAll(",", "") + "', '" + loaihd
											+ "', N'" + hoadon.getKyhieu() + "', '" + sohoadon + "', "+tientehd+", "+tigiahd+",  N'"+diaChiHd+"')";
								}

							} else {
								query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON, TIENTE_FK, TIGIA, DIACHI ) "
										+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
										+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
										+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
										+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', '" + sohoadon+ "', "+tientehd+", "+tigiahd+",  N'"+diaChiHd+"')";
								if (loaihd.equals("4")) {
									query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, LOAITHUE, SOHOADON, TIENTE_FK, TIGIA, DIACHI ) "
											+ "values('" + this.id + "', '" + hoadon.getId() + "', '"
											+ thanhtoan.trim().replaceAll(",", "") + "', '" + avat.replaceAll(",", "")
											+ "', " + sotienNT.replaceAll(",", "") + "," + " '"
											+ conlai.trim().replaceAll(",", "") + "', '" + loaihd + "', N'"
											+ hoadon.getKyhieu() + "', '" + sohoadon + "', "+tientehd+", "+tigiahd+",  N'"+diaChiHd+"')";

								}
							}
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
							
						}
					}
				}

			}

			if (!this.DoiTuongChiPhiKhac.equals("3")) {
				query = "INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, "
						+ "NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) "
						+ "VALUES(" + this.id + ", N'" + this.mahoadon + "', N'" + this.mauhoadon + "', N'"
						+ this.kyhieu + "', '" + this.sohoadon + "'," + "'" + this.ngayghinhan + "', N'" + this.tenNCC
						+ "', '" + this.mst + "', " + this.tienhang.replaceAll(",", "") + ", "
						+ this.thuesuat.replaceAll(",", "") + ", " + this.tienthue.replaceAll(",", "") + ", " + ""
						+ (this.nhId_VAT.length() == 0 ? null : this.nhId_VAT) + ", "
						+ (this.cnId_VAT.length() == 0 ? null : this.cnId_VAT) + ")";
				System.out.println(query);
				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON_PHINGANHANG: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			} else {
				int i=0; 
				if(Tienhanghd !=null)
				{
					while(Tienhanghd.length>i)
					{						
						if(Tienhanghd[i].trim().length() <=0)
							Tienhanghd[i] = "0";
						
						if(Double.parseDouble(Tienhanghd[i].trim().replaceAll(",", "")) > 0)
						{
							if(Thuesuathd[i].replaceAll(",", "").trim().length()<=0)
								Thuesuathd[i]  = "0";
							if(Tienthuehd[i].replaceAll(",", "").trim().length()<=0)
								Tienthuehd[i]  = "0";							
							if(PhongBanIds[i].trim().length()<=0)
								PhongBanIds[i]  = "null";							
							if(KenhbhIds[i].trim().length()<=0)
								KenhbhIds[i]  = "null";
							if(TinhThanhIds[i].trim().length()<=0)
								TinhThanhIds[i]  = "null";							
							if(SanPhamIds[i].trim().length()<=0)
								SanPhamIds[i]  = "null";
							if(DiabanIds[i].trim().length()<=0)
								DiabanIds[i]  = "null";
							if(MavvIds[i].trim().length()<=0)
								MavvIds[i]  = "null";
							if(BenhvienIds[i].trim().length()<=0)
								BenhvienIds[i]  = "null";
							if(Diengiaihd[i].trim().length()<=0)
								Diengiaihd[i]  = "";
							if(diaChiHd[i].trim().length()<=0)
								diaChiHd[i]  = "";
							
							if(TaiKhoanIds[i]!="")
							{

								String []TkId=TaiKhoanIds[i].split("_");
								
								query = "SELECT DUNGCHOKHO, DUNGCHONCC, DUNGCHONGANHANG, DUNGCHOTAISAN, DUNGCHOKHACHHANG, COTTCHIPHI, DUNGCHONHANVIEN, " +
										"ISNULL(DUNGCHODOITUONGKHAC, 0) AS DUNGCHODOITUONGKHAC, SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + TkId[0] + "";
								System.out.println(query);
								ResultSet rs  = this.db.get(query);
								rs.next();
																
								// KIẾM TRA XEM CÓ CHỌN ĐỐI TƯỢNG CHƯA
								if(rs.getString("DUNGCHONHANVIEN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHONCC").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHONGANHANG").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}

								if(rs.getString("DUNGCHOTAISAN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
								
								if(rs.getString("DUNGCHOKHACHHANG").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}
																
								if(rs.getString("DUNGCHONHANVIEN").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}

								if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn mã đối tượng.";
									return false;
								}

								if(rs.getString("COTTCHIPHI").equals("1")&&dcIds[i].equals("")){
									
									this.db.getConnection().rollback();
									this.msg = "Vui lòng chọn trung tâm chi phí.";
									return false;
								}
								if(rs.getString("SOHIEUTAIKHOAN").startsWith("6")){
									if(ttcpIds[i].trim().length()==0)
									{
										this.db.getConnection().rollback();
										this.msg = "Vui lòng chọn khoản mục chi phí.";
										return false;
									}
									
								}
								// Dùng cho : 1 Kho, 2 NCC, 3 Ngân hàng, 4 Tài Sản, 5 Khách hàng, 6 TTCP, 7 Nhân viên																
								
								if(dcIds[i].trim().length()<=0)
									dcIds[i]  = "null";
								if(this.ttcpIds[i].trim().length()<=0)
									this.ttcpIds[i]  = "null";

								 if(ttcpIds[i] != null)
							         if(ttcpIds[i].trim().length() <=0)
							          ttcpIds[i] = "null";
								 
								if(loais[i].equals("1") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, SANPHAM_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK , DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
										" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
										"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
										dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
									
								}else						
								if(loais[i].equals("2") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NCC_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
									
								}else						
								if(loais[i].equals("3") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";									
								}else
								if(loais[i].equals("4") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE,TAISAN_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK , DIENGIAI, KHOANMUC_FK, DIACHI) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
									
								}else
								if(loais[i].equals("5") ){
									
									//CHỖ NÀY ĐỂ PHÂN BIỆT LÀ NPP HAY KHÁCH HÀNG HAY NHÂN VIÊN
									String isNPP = "NULL";
									String kh_noId = "null";		
									
									kh_noId = dcIds[i];
									
									if( kh_noId.contains("-") )
									{
										String kh[] = kh_noId.split("-");
										kh_noId = kh[0];
										isNPP = kh[1];
									}
									
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, KHACHHANG_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI , ISNPP, KHOANMUC_FK, DIACHI ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											kh_noId+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+isNPP+", "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
									
								}else 
								if(loais[i].equals("7") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NHANVIEN_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK , SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI  ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
									
								}else
								if(loais[i].equals("6") ){
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, TTCP_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI  ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", " +DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";									
								}else
									if(loais[i].equals("8") ){
										query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, DOITUONGKHAC_FK, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI ) VALUES " +
												" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
												"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
												dcIds[i]+", "+TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", " +DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+",  N'"+diaChiHd[i]+"')";									
								}								
								else {
									query="INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAUHOADON, KYHIEU, SOHOADON,NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, TAIKHOAN_FK, PHONGBAN_FK, KBH_FK, TINHTHANH_FK, DIABAN_FK, VUVIEC_FK, BENHVIEN_FK, SP_FK, DIENGIAI, KHOANMUC_FK, DIACHI  ) VALUES " +
											" ('"+this.id+"', UPPER(N'" + Mauhd[i] +"'), UPPER(N'" + Kyhieuhd[i] + "'), '" + Sohd[i] + "'," +
											"'" + Ngayhd[i] + "', N'" + TenNCChd[i] + "', '" + MSThd[i] + "', " + Tienhanghd[i].replaceAll(",", "") + ", " + Thuesuathd[i].replaceAll(",", "") + ", " + Tienthuehd[i].replaceAll(",", "") + ", " +
											TkId[0]+","+PhongBanIds[i]+","+KenhbhIds[i]+", "+TinhThanhIds[i]+", "+DiabanIds[i]+", "+MavvIds[i]+", "+ BenhvienIds[i] + ", " + SanPhamIds[i]+", N'"+Diengiaihd[i]+"', "+ttcpIds[i]+", N'"+diaChiHd[i]+"')";
								}
														
								System.out.println(query);
								if(!this.db.update(query))
								{
									this.db.getConnection().rollback();
									this.msg="Loi dong lenh sau "+query;
									return false;
								}
							
							}
							else
							{
								this.msg="Vui lòng chọn tài khoản !";
								return false;
							}
							
					}
						
						i++;
				}
					
			}	
			
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}

		return true;
	}


	private void XoaThanhToanHoaDon_HoaDon(String hdHienTai) {

		String sql = "DELETE FROM ERP_THANHTOANHOADON_HOADON \n" + 
					 "WHERE ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK = '"+ this.id + "' AND ERP_THANHTOANHOADON_HOADON.HOADON_FK = '" + hdHienTai + "'";
		db.update(sql);

	}

	private List<String> GetlistHoaDonHienTai() {

		String sql =  " SELECT ERP_THANHTOANHOADON_HOADON.HOADON_FK FROM ERP_THANHTOANHOADON_HOADON\n"
					+ " WHERE ERP_THANHTOANHOADON_HOADON.THANHTOANHD_FK = '" + this.id + "'";
		ResultSet resultSet = db.get(sql);
		List<String> list = new ArrayList<String>();
//		String[] rerult = null;
		try {
			// rerult = (String[])resultSet.getArray("HOADON_FK").getArray();

			while (resultSet.next()) {
				list.add(resultSet.getString("HOADON_FK"));
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;

	}

	public boolean checkNhomNCC() {
		String check = "0";
		String query = "select case when NHOMNCCCN IS null then 0 else 1 end as checkNhom from ERP_THANHTOANHOADON "
					+  "where PK_SEQ = '" + this.id + "'";
		ResultSet checkNhomRs = db.get(query);
		try {
			while (checkNhomRs.next()) {
				check = checkNhomRs.getString("checkNhom");
			}
			checkNhomRs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (check.equalsIgnoreCase("1"))
			return true;
		else
			return false;
	}

	public boolean chotTTHD(String userId) {
		try {

			String query = "select * from ERP_CANTRUCONGNO where THANHTOAN_FK = " + this.id;

			System.out.println(" check can tru11 :" + query);

			ResultSet rs = db.get(query);
			if (rs.next()) {
				this.msg = "CTTHD1.1 Thanh toán này đã có trong phần Cấn trừ công nợ, vui lòng chốt phần cấn trừ !";
				return false;
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "CTTHD1.2 Lỗi chốt UNC";
			return false;
		}

		try {
			
			Utility util = new Utility();
			String ngaysua = getDateTime();

			db.getConnection().setAutoCommit(false);
			
			String query = "";
//			ResultSet rs = null;
			
			
			query="SELECT TRANGTHAI FROM ERP_THANHTOANHOADON WHERE PK_SEQ= '"  + this.id + "' ";
			ResultSet ttRs = db.get(query);
			if(ttRs.next())
			{
				 int trangthai = ttRs.getInt("TRANGTHAI");
				 if(trangthai==1)
				 {
					 this.msg = " Trạng thái không hợp lệ hoặc phiếu này đã được chốt!";
					db.getConnection().rollback();
					return false;
				 }
			}
			
			util.HuyUpdate_TaiKhoan(db, this.id, "Trả khác");
			util.HuyUpdate_TaiKhoan(db, this.id, "Thanh toán hóa đơn");
			util.HuyUpdate_TaiKhoan(db, this.id, "Chi phí khác");

			query = "update ERP_THANHTOANHOADON set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '"+ ngaysua + "' where PK_SEQ = '" + this.id + "' and trangthai=0";
			System.out.println("1.Cap nhat hoadon: " + query);

			if (db.updateReturnInt(query)!=1) {
				this.msg = "CTTHD1.2 Khong the chot ERP_THANHTOANHOADON: " + query;
				db.getConnection().rollback();
				return false;
			}

		//  CẬP NHẬT ĐÃ THANH TOÁN CỦA ĐỀ NGHỊ TẠM ỨNG
			if (capNhatHoaDon("ERP_TAMUNG", "1") == false)
			{
				this.msg = "CTTHD1.3 Không thể chốt UNC: lỗi cập nhật đề nghị tạm ứng";
				db.getConnection().rollback();
				return false;
			}	
			
			//  CẬP NHẬT ĐÃ THANH TOÁN CỦA ĐỀ NGHỊ THANH TOÁN
			if (capNhatHoaDon("ERP_MUAHANG", "6") == false)
			{
				this.msg = "CTTHD1.4 Không thể chốt UNC: lỗi cập nhật đề nghị thanh toán";
				db.getConnection().rollback();
				return false;
			}
 		
			if (getIsCanTru() == 1)//Định khoản cho trường hợp PC tạo ra từ DNTT, DNTT có cấn trừ
			{
				if(dinhKhoanCanTru() == false)
				{
					this.msg = "CTTHD1.5 Không thể chốt phiếu chi" + this.msg;
					db.getConnection().rollback();
					return false;	
				}
			}
			else
			{
	
				// GHI NHAN KE TOAN
	
	//			String hinhthuctt = "";
	//			String tigia = "";
	
//				String nam = "";
//				String thang = "";
	//			String tiente_fk = "";
	//			String ncc_fk = "";
	//			String nhanvien_fk = "";
	//			String khachhang_fk = "";
	//			String nganhang_fk = "";
				boolean result; 
				
				if (this.DoiTuongChiPhiKhac.equals("3")) // KHAC
				{
					result = dinhKhoanThanhToanKhac(util);
					if (result == false)
					{
						this.msg = "CTTHD1.6 Không thể chốt phiếu chi " + this.msg;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{ 
					result = dinhKhoanVAT_PhiNganHang(util);
					if (result == false)
					{
						this.msg = "CTTHD1.7 Không thể chốt phiếu chi " + this.msg;
						db.getConnection().rollback();
						return false;
					}
		
					if (Double.parseDouble(this.checkThanhtoantuTV) <= 0) 
					{
						if (this.bophanId.trim().length() > 0) // Bộ phận
						{
							result = dinhKhoanBoPhan(util);
							if (result == false)
							{
								this.msg = "CTTHD1.14 " + this.msg;
								db.getConnection().rollback();
								return false;
							}
						}else
						{
							// ĐỀ NGHỊ THANH TOÁN - CÀI RIÊNG
							result = dinhKhoanDNTT();
							if (result == false)
							{
								this.msg = "CTTHD1.15 " + this.msg;
								db.getConnection().rollback();
								return false;
							}
							
							if (this.DoiTuongChiPhiKhac.equals("1")) // NCC
							{
								// NHỮNG LOẠI HĐ LIÊN QUAN ĐẾN NCC TRỪ ĐỀ NGHỊ THANH TOÁN THÌ CÀI RIÊNG									
								result = dinhKhoanNCC( util);
								if (result == false)
								{
									this.msg = "CTTHD1.19 " + this.msg;
									db.getConnection().rollback();
									return false;
								}
							} else if (this.DoiTuongChiPhiKhac.equals("0")) // NHÂN VIÊN// (TẠM// ỨNG/ĐỀ// NGHỊ	// THANH	// TOÁN/CHI	// PHÍ KHÁC)
							{
								// NHỮNG LOẠI HĐ CÓ NCC > 0
								result = dinhKhoanNhanVien(util);
								if (result == false)
								{
									this.msg = "CTTHD1.11 " + this.msg;
									db.getConnection().rollback();
									return false;
								}
							} else if((this.khachhangId != null && this.khachhangId.trim().length() > 0) 
									|| (this.khachHang_NPP_FK != null && this.khachHang_NPP_FK.trim().length() > 0)) // Khách hàng, chi nhánh
							{
								//Định khoản cho những hóa đơn không là DNTT
								result = dinhKhoanKhachHang(util);
								if (result == false)
								{
									this.msg = "CTTHD1.13 " + this.msg;
									db.getConnection().rollback();
									return false;
								}
							}
							
							
							query= "select DNTT_fk from erp_thanhtoanhoadon where pk_seq ="+this.id;
							System.out.println("lay id cua DNTT");
							ResultSet  rs1 = db.get(query);

							try
							{
								if(rs1!=null)
								{
									while(rs1.next())
									{
										String DNTT_fk = rs1.getString("DNTT_fk");

										System.out.println("dntt_fk"+DNTT_fk);
										query="SELECT KHOANMUCCHI FROM ERP_MUAHANG WHERE KHOANMUCCHI IS NOT NULL AND ISNULL(QUANLYCONGNO,0)=0 AND PK_SEQ ="+DNTT_fk;
										Library lib = new Library();

										System.out.println("khoan muc chi phi "+query);
										rs1=db.get(query);
										if (rs1.next()) {
											String resultUpdate = lib.updateNguyenGia(DNTT_fk,db);
											if (resultUpdate.length()> 0) {
												db.getConnection().rollback();
												if (this.msg.trim().length() == 0)
													this.msg = "Lỗi khi duyệt kế toán trưởng"+resultUpdate;
												return false;
											}
										}
									}
									rs1.close();
								}
							}catch(Exception e)
							{

								this.msg ="Không thể cập nhật nguyên giá cho tài sản ";
								db.getConnection().rollback();
							}
							
							
						}
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}

			this.msg = "CTTHD1.33 Không thể chốt phiếu UNC";
			return false;
		}

		return true;
	}
	
	public boolean unchotTTHD(String userId)
	{

		
		try 
		{
			String ngaysua = getDateTime();
			UtilityKeToan util = new UtilityKeToan();
			db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE ERP_THANHTOANHOADON set TRANGTHAI = '0', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "' and trangthai=1";
			System.out.println("1.Cap nhat hoadon: " + query);
			
			if(db.updateReturnInt(query)!=1)
			{
				this.msg = "CTTHD1.2 Khong the mo chot ERP_THANHTOANHOADON: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			String query1="select TRANGTHAI FROM ERP_HUYCHUNGTUKETOAN where sochungtugoc='"+this.id+"' ";
			System.out.println("Cau query"+query1);
			ResultSet rsCheck = db.get(query1);
			try {
				if(rsCheck.next())
				{
					if(rsCheck.getInt("TRANGTHAI")==1)
					{
						db.getConnection().rollback();
						this.msg="Không thể mở chốt bút toán này : Phiếu này đã được hủy! ";
						return false ;
						
					}
					
				}rsCheck.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			query="select count(*) as dem \n"
					+ " from ERP_DENGHITT_THANHTOANHOADON dnhd "
					+ " left join erp_muahang mh on dnhd.denghitt_fk=mh.pk_seq"
					+ " where thanhtoanhoadon_fk='"+this.id+"' and mh.trangthai!=2 and mh.trangthai!=3";
			
			System.out.println("thamchieu:23 "+query);
			ResultSet rs=db.get(query);
			int dem=0;
			String dntt_fk="";
			while(rs.next()){
				dem=rs.getInt("dem");
			}rs.close();
			if(dem>0){
				query="select denghitt_fk \n"
						+ " from ERP_DENGHITT_THANHTOANHOADON dnhd "
						+ " left join erp_muahang mh on dnhd.denghitt_fk=mh.pk_seq"
						+ " where thanhtoanhoadon_fk='"+this.id+"' and mh.trangthai!=2 and mh.trangthai!=3 \n"
								+ " group by denghitt_fk";
				System.out.println("thamchieu:2 "+query);
				rs=db.get(query);
				while(rs.next()){
					dntt_fk+=rs.getString("denghitt_fk")+" ";
				}
				rs.close();
				
				this.msg = "Số chứng từ nay đã được tham chiếu vào DNTT "+dntt_fk+". Bạn không được mở chốt!!";
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			//kiem tra da duoc xoano
			query=" select count(*) as dem  \n"+ 
					"    from ERP_XOANONCC_TAMUNG xntu left join ERP_XOANONCC xn on xntu.xoanoncc_fk=xn.PK_SEQ \n"+ 
					"    where thanhtoanhoadon_fk='"+this.id+"' and xn.trangthai!=2\n";
			System.out.println("xoano "+query);
			int demxoano=0;
			String xoano_fk="";
			rs=db.get(query);
			while(rs.next()){
				demxoano=rs.getInt("dem");
			}rs.close();
			if(demxoano>0){
				query=" select xoanoncc_fk  \n"+ 
						"    from ERP_XOANONCC_TAMUNG xntu left join ERP_XOANONCC xn on xntu.xoanoncc_fk=xn.PK_SEQ \n"+ 
						"    where thanhtoanhoadon_fk='"+this.id+"' and xn.trangthai!=2\n"
								+ " group by xoanoncc_fk ";
				System.out.println("xoano22 "+query);
				rs=db.get(query);
				while(rs.next()){
					xoano_fk+=rs.getString("xoanoncc_fk")+" ";
				}rs.close();
				this.msg = "Số chứng từ nay đã được xóa nợ "+xoano_fk+". Bạn không được mở chốt!!";
				
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			//  CẬP NHẬT ĐÃ THANH TOÁN CỦA ĐỀ NGHỊ TẠM ỨNG
			if (revert_capNhatHoaDon("ERP_TAMUNG", "1") == false)
			{
				db.getConnection().rollback();
				return false;
			}	
			
			//  CẬP NHẬT ĐÃ THANH TOÁN CỦA ĐỀ NGHỊ THANH TOÁN
			if (revert_capNhatHoaDon("ERP_MUAHANG", "6") == false)
			{
				db.getConnection().rollback();
				return false;
			}
 		
			query = "select month(NGAYGHINHAN) thang, year(NGAYGHINHAN) nam from ERP_THANHTOANHOADON WHERE PK_SEQ = " + this.id + "";
			System.out.println("ngay thang " +query);
			rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
				{
					util.setThang(rs.getString("thang"));
					util.setNam(rs.getString("nam"));
				}
				rs.close();
			}
			
			
			String loaiChungTu=" N'Thanh toán hóa đơn' , N'Chi phí khác' , N'Trả khác'";
			this.msg=util.HuyUpdate_TaiKhoan(db, this.id, loaiChungTu);
			if(this.msg.length()>0)
			{
				db.getConnection().rollback();
				return false;
			}

			Revert_KeToan_PC_UNC_CCDC_MaSCLon(db,this.id);

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			this.msg = "Khong the cap nhat ERP_HOADONNCC ABC: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	
	private String  Revert_KeToan_PC_UNC_CCDC_MaSCLon(Idbutils db,String sochungtu) {
		String msg = "";
		try{
		String CCDC_FK="";
		String query="";
		
		query="SELECT CCDC_FK FROM ERP_CONGCUDUNGCU_DIEUCHINH WHERE SOCHUNGTU IN ( SELECT DNTT_FK FROM ERP_THANHTOANHOADON WHERE PK_SEQ= "+sochungtu+" ) AND BANGTHAMCHIEU='ERP_MUAHANG'" ;
		ResultSet rs= db.get(query);
		while (rs.next())
		{
			CCDC_FK = rs.getString("CCDC_FK");
			int kh=0;
			query="SELECT COUNT (KHAUHAO)  AS NUM FROM  ERP_KHAUHAOCCDC WHERE CCDC_FK ="+CCDC_FK+" ";
			ResultSet rsKH= db.get(query);
			if(rsKH.next())
			{
				kh= rsKH.getInt("NUM");
			}rsKH.close();
			if(kh>0 )
			{
				msg =  "2.Chi phí trả trước đã được phân bổ, không thể xóa: " + query;
				return msg;
			}else
			{
				
				query="UPDATE ERP_CONGCUDUNGCU SET TRANGTHAI=0 WHERE PK_SEQ ="+CCDC_FK+" ";
				if(!db.update(query))
				{
					msg =  "2.Lỗi REVERT: " + query;
					return msg;
				}	
			}
		}
		rs.close();
		
		
		
		
		
		query="DELETE ERP_CONGCUDUNGCU_DIEUCHINH WHERE SOCHUNGTU IN ( SELECT DNTT_FK FROM ERP_THANHTOANHOADON WHERE PK_SEQ= "+sochungtu+" ) AND BANGTHAMCHIEU='ERP_MUAHANG' ";
		
		if(!db.update(query))
		{
			msg =  "2.Lỗi REVERT: " + query;
			return msg;
		}	
		
		String MASCLON="";
		query="SELECT MASCLON_FK FROM ERP_MASCLON_DIEUCHINH WHERE SOCHUNGTU IN ( SELECT DNTT_FK FROM ERP_THANHTOANHOADON WHERE PK_SEQ= "+sochungtu+" ) AND BANGTHAMCHIEU='ERP_MUAHANG'" ;
		rs= db.get(query);
		while (rs.next())
		{
			MASCLON = rs.getString("MASCLON_FK");
			int trangthai=0;
			query="SELECT TRANGTHAI  FROM  ERP_MASCLON WHERE PK_SEQ ="+MASCLON+" ";
			ResultSet rsKH= db.get(query);
			if(rsKH.next())
			{
				trangthai= rsKH.getInt("TRANGTHAI");
			}rsKH.close();
			if(trangthai==3 )
			{
				msg =  "2.Mã XDCB/ SC Lớn đã được kết chuyển, không thể xóa " + query;
				return msg;
			}
		}
		rs.close();
			
		query="DELETE ERP_MASCLON_DIEUCHINH WHERE CAST(SOCHUNGTU AS NVARCHAR) in (SELECT CAST(DNTT_FK AS NVARCHAR) FROM ERP_THANHTOANHOADON WHERE PK_SEQ= "+sochungtu+" ) AND BANGTHAMCHIEU='ERP_MUAHANG' ";
		
		if(!db.update(query))
		{
			msg =  "2.Lỗi REVERT: " + query;
			return msg;
		}	
		  }				
		catch(Exception e )
		{
			e.printStackTrace();
		}

		return msg;
	}
	

	private boolean dinhKhoanVAT_PhiNganHang(Utility util) 
	{
		// 1. TIỀN VAT, PHÍ NGÂN HÀNG GIỐNG NHAU CHO MỌI TRƯỜNG HỢP
		String query =
		   " select  TTHD.CHENHLECHVND, ISNULL(TTHD.PHINGANHANG, 0) AS PHINGANHANG , TTHD.TRICHPHI, \n" +
		   "         ISNULL(TTHD.VAT,0) AS VAT,  TTHD.HTTT_FK as HINHTHUCTT, TTHD.TIENTE_FK, TTHD.KHACHHANG_FK,  \n"+
		   "	     TTHD.NGAYGHINHAN ,TTHD.NCC_FK , TTHD.NGANHANG_FK , TTHD.NGANHANG_TP_FK , isnull(TTHD.TIGIA,1) as TIGIA, \n" +
		   "         ISNULL(TTHD.CHENHLECHVND,0) AS CHENHLECHVND , TTHD.NHANVIEN_FK, NCC.TAIKHOAN_FK as taikhoanNO_NCC, NV.TAIKHOAN_FK as taikhoanNO_NV, \n"+
	       "        ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where  SoTaiKhoan = TTHD.SOTAIKHOAN AND CONGTY_FK = "+this.ctyId+") as taikhoanCO_NH , \n"+
	       "        ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where  SoTaiKhoan = TTHD.SOTAIKHOAN_TP AND CONGTY_FK = "+this.ctyId+") as taikhoanCO_NH_TP, \n"+
	       "        (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+this.ctyId+") as taikhoan_TIENVND, \n"+
	       "        (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '64253000' AND CONGTY_FK = "+this.ctyId+") as taikhoanNO_PHINH, \n"+
	       "        (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '13311000' AND CONGTY_FK = "+this.ctyId+") as taikhoan_THUE, \n" +
	       "        (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '63580000' AND CONGTY_FK = "+this.ctyId+") as taikhoanNO_CHENHLECH, \n" +
	       "        (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '51580000' AND CONGTY_FK = "+this.ctyId+") as taikhoanCO_CHENHLECH, \n"+
	       "   		TTHD.NOIDUNGTT, TTHD.sochungtu machungtu, ISNULL( TTHD.ISNPP, '') ISNPP " +
	       " from   ERP_THANHTOANHOADON TTHD  left join ERP_NHACUNGCAP NCC on TTHD.NCC_FK= NCC.PK_SEQ \n" +
	       "                                  left join ERP_NHANVIEN NV on TTHD.NHANVIEN_FK = NV.PK_SEQ \n"+
	       " where TTHD.PK_SEQ = "+ this.id +"  ";
		
		System.out.println("Câu lấy tiền vat/phinganhang2: "+query);
		
		try {
			ResultSet RsRs = db.get(query);
			if(RsRs == null)
			{
				this.msg = "DKVATPNH1.1";
				return false;
			}
			
			if(RsRs != null)
			{
				while(RsRs.next())
				{
					String diengiai = RsRs.getString("NOIDUNGTT");
					String machungtu = RsRs.getString("machungtu");
					String isNPP = RsRs.getString("ISNPP");
					
					String hinhthuctt = RsRs.getString("HINHTHUCTT");
					tigia = RsRs.getString("tigia");
	
//					String ncc_fk = RsRs.getString("NCC_FK") == null ? "":RsRs.getString("NCC_FK");
//					String nhanvien_fk = RsRs.getString("NHANVIEN_FK") == null ? "":RsRs.getString("NHANVIEN_FK");
//					String khachhang_fk = RsRs.getString("KHACHHANG_FK") == null ? "":RsRs.getString("KHACHHANG_FK");
					
					String nam = RsRs.getString("ngayghinhan").substring(0, 4);
					String thang = RsRs.getString("ngayghinhan").substring(5, 7);
					String tiente_fk = RsRs.getString("tiente_fk");
					
					String nganhang_fk= RsRs.getString("NGANHANG_FK")== null ?"":RsRs.getString("NGANHANG_FK") ;
					String nganhangTP_fk= RsRs.getString("NGANHANG_TP_FK")== null ?"":RsRs.getString("NGANHANG_TP_FK") ;
					String trichphi_fk = RsRs.getString("TRICHPHI")== null ?"":RsRs.getString("TRICHPHI") ;
					
					String taikhoanCO = "";
					String taikhoanNO = "";
					
					double phinganhang= RsRs.getDouble("PHINGANHANG");
					
					double vat = RsRs.getDouble("VAT");
					
	//					double chenhlech = RsRs.getDouble("CHENHLECHVND");
					
			//1.a PHÍ NGÂN HÀNG + VAT
			   // THANHTOAN: NGOAI TE, TRICH PHI BANG VND						     
					  if(!tiente_fk.equals("100000")&& trichphi_fk.equals("1") )
					  {							 	
						 //GHI NHAN PHINGANHANG
	
							if(phinganhang > 0)
							{
								taikhoanNO = RsRs.getString("taikhoanNO_PHINH") == null ? "":RsRs.getString("taikhoanNO_PHINH") ;
								taikhoanCO = RsRs.getString("taikhoanCO_NH_TP") == null ? "":RsRs.getString("taikhoanCO_NH_TP");
								
								if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
								{
									this.msg = "CTTHD1.11 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									return false;
								}
								
								this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, RsRs.getString("NGAYGHINHAN"), RsRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
								Double.toString(phinganhang), Double.toString(phinganhang), "BANK CHARGES", "", 
								"Ngân hàng", "Ngân hàng", "0", "", "", tiente_fk, "", tigia, Double.toString(phinganhang), Double.toString(phinganhang), "PHÍ NGÂN HÀNG", "0" , diengiai , machungtu, isNPP);
																			
								if(this.msg.trim().length() > 0)
								{
									this.msg = "CTTHD1.12 Không thể chốt phiếu chi" + msg;
									RsRs.close();
									return false;
								}
							}
						//GHI NHAN VAT		
							if(vat > 0)
							{
								taikhoanNO = RsRs.getString("taikhoan_THUE")== null?"":RsRs.getString("taikhoan_THUE");
								String doituong = "";
								String madoituong = "";
								if(hinhthuctt.equals("100001")) //THANH TOAN: NGANHANG
								{
									taikhoanCO = RsRs.getString("taikhoanCO_NH_TP")== null?"":RsRs.getString("taikhoanCO_NH_TP");
									doituong= "Ngân hàng";
									madoituong= nganhangTP_fk;
								}
								else
								{
									taikhoanCO = RsRs.getString("taikhoan_TIENVND");
								}
								
								if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
								{
									this.msg = "CTTHD1.13 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									return false;
								}
								
								this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, RsRs.getString("NGAYGHINHAN"), RsRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
								Double.toString(vat), Double.toString(vat), "", "", doituong, madoituong, "0", "", "", tiente_fk, "", tigia, Double.toString(vat), Double.toString(vat), "VAT", "0" , diengiai , machungtu, isNPP);
																
								if(this.msg.trim().length() > 0)
								{
									this.msg = "CTTHD1.14 Không thể chốt phiếu chi" + msg;
									RsRs.close();
									return false;
								}
							}
						
					  }							
					else// TRUONG HOP CON LAI THANH TOÁN BẰNG VND
					{
						 // GHI NHAN PHINGANHANG
	
							if(phinganhang > 0)
							{
								taikhoanNO = RsRs.getString("taikhoanNO_PHINH") == null ? "": RsRs.getString("taikhoanNO_PHINH") ;
								taikhoanCO = RsRs.getString("taikhoanCO_NH") == null ? "": RsRs.getString("taikhoanCO_NH") ;
								
								if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
								{
									this.msg = "CTTHD1.15 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									return false;
								}
								
								this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, RsRs.getString("NGAYGHINHAN"), RsRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
								Double.toString(phinganhang), Double.toString(phinganhang), "BANK CHARGES", "", "Ngân hàng", nganhang_fk, "0", "", "", tiente_fk, "", tigia, Double.toString(phinganhang), Double.toString(phinganhang), "PHÍ NGÂN HÀNG", "0" , diengiai , machungtu, isNPP);
								
								if(this.msg.trim().length() > 0)
								{
									this.msg = "CTTHD1.16 Không thể chốt phiếu chi" + msg;
									RsRs.close();
									return false;
								}
							}
						//  GHI NHAN VAT		
							if(vat > 0)
							{
								taikhoanNO = RsRs.getString("taikhoan_THUE") == null? "":RsRs.getString("taikhoan_THUE")  ;
								String doituong = "";
								String madoituong = "";
								if(hinhthuctt.equals("100001")) //THANH TOAN: NGANHANG
								{
									taikhoanCO = RsRs.getString("taikhoanCO_NH") == null ? "":RsRs.getString("taikhoanCO_NH");
									doituong= "Ngân hàng";
									madoituong= nganhang_fk;
								}
								else
								{
									taikhoanCO = RsRs.getString("taikhoan_TIENVND") == null ?"": RsRs.getString("taikhoan_TIENVND")  ;
								}
								
								if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
								{
									this.msg = "CTTHD1.17 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									return false;
								}
								
								this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, RsRs.getString("NGAYGHINHAN"), RsRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
								Double.toString(vat), Double.toString(vat), "", "", doituong, madoituong, "0", "", "", tiente_fk, "", tigia, Double.toString(vat), Double.toString(vat), "VAT", "0" , diengiai , machungtu, isNPP);
							
								if(this.msg.trim().length() > 0)
								{
									this.msg = "CTTHD1.18 Không thể chốt phiếu chi" + msg;
									RsRs.close();
									return false;
								}
							}
						
					}
					  
		/*	 // 1.b.CHÊNH LỆCH	
						if(chenhlech!= 0)
						{
							String loaidoituong = "Nhà cung cấp";
							String madoituong = ncc_fk;
							
							if(chenhlech > 0) 
							{
								taikhoanNO = RsRs.getString("taikhoanNO_CHENHLECH")== null ?"": RsRs.getString("taikhoanNO_CHENHLECH");
								taikhoanCO = RsRs.getString("taikhoanNO_NCC")== null ?"": RsRs.getString("taikhoanNO_NCC");
								
								if(nhanvien_fk.trim().length() > 0)
								{
									loaidoituong = "Nhân viên";
									madoituong = nhanvien_fk;
									taikhoanCO = RsRs.getString("taikhoanNO_NV")== null ?"": RsRs.getString("taikhoanNO_NV");
								}
								
								if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
								{
									this.msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									db.getConnection().rollback();
									return false;
								}
								
								this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, RsRs.getString("NGAYGHINHAN"), RsRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
								Double.toString(chenhlech), Double.toString(chenhlech), "", "", loaidoituong, madoituong, "0", "", "", tiente_fk, "", tigia, Double.toString(chenhlech), Double.toString(chenhlech), "", "0" , diengiai , machungtu, isNPP);
									
								
								if(this.msg.trim().length() > 0)
								{
									RsRs.close();
									db.getConnection().rollback();
									return false;
								}
							}
							if(chenhlech < 0)
							{
								taikhoanNO = RsRs.getString("taikhoanNO_NCC")== null ?"":  RsRs.getString("taikhoanNO_NCC");
								taikhoanCO = RsRs.getString("taikhoanCO_CHENHLECH")== null ?"": RsRs.getString("taikhoanCO_CHENHLECH");
								
								if(nhanvien_fk.trim().length() > 0)
								{
									loaidoituong = "Nhân viên";
									madoituong = nhanvien_fk;
									taikhoanNO = RsRs.getString("taikhoanNO_NV") == null ?"":  RsRs.getString("taikhoanNO_NV");
								}
								
								if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
								{
									this.msg = "Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
									db.getConnection().rollback();
									return false;
								}
								
								this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, RsRs.getString("NGAYGHINHAN"), RsRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
								Double.toString(chenhlech*(-1)), Double.toString(chenhlech*(-1)), loaidoituong, madoituong, "", "", "0", "", "", tiente_fk, "", tigia, Double.toString(chenhlech*(-1)), Double.toString(chenhlech*(-1)), "", "0" , diengiai , machungtu, isNPP);
								
								if(this.msg.trim().length() > 0)
								{
									RsRs.close();
									db.getConnection().rollback();
									return false;
								}
							}
						}  */
					
				}
				RsRs.close();
			}
		} catch (Exception e) {
			this.msg = "CTTHD1.19";
			e.printStackTrace();
			return false;
		}
		return true;
	}

	private boolean dinhKhoanThanhToanKhac(Utility util) 
	{
		String query = "\n select  a.httt_fk, a.SOTIENHD, a.NGAYGHINHAN , "+
		"\n 		( select TaiKhoan_fk  from ERP_NGANHANG_CONGTY where  SoTaiKhoan = a.SOTAIKHOAN AND CONGTY_FK = "+ this.ctyId + "  )  as taikhoanCO_NH, "+
    	"\n 		( select pk_seq from erp_taikhoankt where sohieutaikhoan= '13311000' AND CONGTY_FK = "+ this.ctyId + " ) as taikhoanNO_tienthue, "+
		"\n 		isnull(b.TIENHANG, 0) tienhang, isnull(b.TIENTHUE,0) tienthue, b.TAIKHOAN_FK  as taikhoanNO,  " +				    	
	  	"\n 		case when b.KHACHHANG_FK is not null then N'Khách hàng' "+
		"\n   		when b.KHO_FK is not null then N'Sản phẩm' "+
		"\n   		when b.NCC_FK is not null then N'Nhà cung cấp' "+
		"\n   		when b.NGANHANG_FK is not null then N'Ngân hàng' "+
		"\n		    when b.NHANVIEN_FK is not null then N'Nhân viên' "+
		"\n		    when b.TTCP_FK is not null then N'Trung tâm chi phí' "+
		"\n   		when b.TAISAN_FK   is not null then N'Tài sản' " +
		"\n   		when b.DOITUONGKHAC_FK   is not null then N'Đối tượng khác' " +
		"\n			else '' end as doituong,  "+
		
	  	"\n			case when b.KHACHHANG_FK is not null then CAST(b.KHACHHANG_FK as nvarchar(50)) "+
		"\n   		when b.SANPHAM_FK is not null then CAST( b.SANPHAM_FK as nvarchar(50)) "+
		"\n   		when b.NCC_FK is not null then CAST (b.NCC_FK as nvarchar(50)) "+
		"\n   		when b.NGANHANG_FK is not null then CAST( b.NGANHANG_FK as nvarchar(50)) "+
		"\n		    when b.NHANVIEN_FK is not null then CAST( b.NHANVIEN_FK as nvarchar(50)) "+
		"\n   		when b.TAISAN_FK is not null then CAST( b.TAISAN_FK as nvarchar(50)) " +
		"\n			when b.TTCP_FK is not null then CAST( b.TTCP_FK as nvarchar(50)) "+
		"\n			when b.DOITUONGKHAC_FK is not null then CAST( b.DOITUONGKHAC_FK as nvarchar(50)) "+
		"\n   		else '' end as madoituong, " +
		
		"\n			a.NGAYGHINHAN , a.TIENTE_FK , isnull(a.TIGIA,1) as TIGIA, a.NGANHANG_FK, a.NOIDUNGTT, A.SOCHUNGTU MACHUNGTU, ISNULL(B.ISNPP,'') ISNPP , "+
		"\n			b.MAHOADON, b.MAUHOADON, b.KYHIEU, b.SOHOADON, b.NGAYHOADON,b.TENNCC,b.MST, b.TIENHANG, b.THUESUAT, "+
	  	"\n			case when b.SANPHAM_FK IS NOT NULL then sp_dt.TEN "+
	  	"\n			when b.NCC_FK IS NOT NULL THEN ncc_dt.TEN "+
	  	"\n			when b.NGANHANG_FK IS NOT NULL THEN nh_dt.TEN "+
	  	"\n 		when b.TAISAN_FK IS NOT NULL then ts_dt.TEN "+
	  	"\n 		when b.KHACHHANG_FK IS NOT NULL then kh_dt.TEN "+
	  	"\n			when b.NHANVIEN_FK IS NOT NULL then nv_dt.TEN "+
	  	"\n			when b.DOITUONGKHAC_FK IS NOT NULL then dtk_dt.TENDOITUONG "+
	  	"\n			when b.TTCP_FK IS NOT NULL then ttcp_dt.TEN "+
	  	"\n			else '' END TEN_DT, " +
	  	
	  	"\n 		pb.TEN TEN_PB,kbh.TEN TEN_KBH,vv.TEN TEN_VV, db.TEN TEN_DIABAN, "+
	  	"\n			tt.TEN TEN_TINHTHANH,bv.TEN TEN_BENHVIEN,sp.TEN TEN_SANPHAM, "+
	   	"\n			case when b.SANPHAM_FK IS NOT NULL then sp_dt.MA_FAST "+
	   	"\n			when b.NCC_FK IS NOT NULL THEN ncc_dt.MA "+
	   	"\n			when b.NGANHANG_FK IS NOT NULL THEN nh_dt.MA "+
	   	"\n			when b.TAISAN_FK IS NOT NULL then ts_dt.MAFAST "+
	   	"\n  	    when b.KHACHHANG_FK IS NOT NULL then kh_dt.maFAST "+
		"\n	  		when b.NHANVIEN_FK IS NOT NULL then nv_dt.MA "+
		"\n	  		when b.DOITUONGKHAC_FK IS NOT NULL then dtk_dt.MADOITUONG "+
		"\n	  		when b.TTCP_FK IS NOT NULL then ttcp_dt.MA "+
		"\n	  		else '' END MAFAST_DT, b.DIENGIAI DIENGIAICT,  "+
		"\n	  		KHOANMUC_FK, b.DIACHI AS DIACHIHD  "+								  
		"\n	from ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_PHINGANHANG b on a.PK_SEQ = b.THANHTOANHD_FK "+
		"\n	LEFT JOIN ERP_NGANHANG nh_dt on b.NGANHANG_FK = nh_dt.PK_SEQ "+
		"\n	LEFT JOIN ERP_DONVITHUCHIEN pb on b.PHONGBAN_FK = pb.PK_SEQ "+
		"\n	LEFT JOIN KENHBANHANG kbh on b.KBH_FK = kbh.PK_SEQ "+
		"\n	LEFT JOIN VUVIEC vv on b.VUVIEC_FK = vv.PK_SEQ "+
		"\n	LEFT JOIN DIABAN db on b.DIABAN_FK = db.PK_SEQ "+
		"\n	LEFT JOIN TINHTHANH tt on b.TINHTHANH_FK = tt.PK_SEQ "+
		"\n	LEFT JOIN KHACHHANG bv on b.BENHVIEN_FK = bv.PK_SEQ "+
		"\n	LEFT JOIN SANPHAM sp on b.SP_FK = sp.PK_SEQ "+
		"\n	LEFT JOIN SANPHAM sp_dt on b.SANPHAM_FK = sp_dt.PK_SEQ "+
		"\n	LEFT JOIN ERP_NHACUNGCAP ncc_dt on b.NCC_FK = ncc_dt.PK_SEQ "+
		"\n	LEFT JOIN KHACHHANG kh_dt on b.KHACHHANG_FK = kh_dt.PK_SEQ "+
		"\n	LEFT JOIN ERP_TAISANCODINH ts_dt on b.TAISAN_FK = ts_dt.pk_seq "+
		"\n	LEFT JOIN ERP_NHANVIEN nv_dt on b.NHANVIEN_FK = nv_dt.PK_SEQ "+
		"\n	LEFT JOIN ERP_DOITUONGKHAC dtk_dt on b.DOITUONGKHAC_FK = dtk_dt.PK_SEQ "+
		"\n	LEFT JOIN ERP_TRUNGTAMCHIPHI ttcp_dt on ttcp_dt.PK_SEQ = b.TTCP_FK "+
		"\n	where a.PK_SEQ = '" + this.id + "'";
	
		System.out.println("Câu query TT Khác " + query);
		
		ResultSet ktRs = db.get(query);
		try {
			if(ktRs == null)
			{
				this.msg = "DKTTK1.1";
				return false;	
			}
			
			if (ktRs != null) 
			{
				while (ktRs.next()) 
				{
					String taikhoanCO = "";
					String taikhoanNO = "";
					
					String loaidoituongco = "";
					String madoiduongco = "";
					
					String loaidoituongno = "";
					String madoiduongno = "";
					
					String nam = ktRs.getString("ngayghinhan").substring(0, 4);
					String thang = ktRs.getString("ngayghinhan").substring(5, 7);
					String tiente_fk = ktRs.getString("tiente_fk");
					double tigia = ktRs.getDouble("tigia");
					
					double tienhang = ktRs.getDouble("TIENHANG");
					double tienthue = ktRs.getDouble("tienthue");
					
					String diengiai = ktRs.getString("NOIDUNGTT");
					String machungtu = ktRs.getString("MACHUNGTU");
					String isNPP = ktRs.getString("ISNPP");
					
					String MAHOADON = ktRs.getString("MAHOADON")== null ? "": ktRs.getString("MAHOADON");
					String MAUHOADON = ktRs.getString("MAUHOADON")== null ? "": ktRs.getString("MAUHOADON"); 
					String KYHIEU = ktRs.getString("KYHIEU")== null ? "": ktRs.getString("KYHIEU"); 
					String SOHOADON = ktRs.getString("SOHOADON")== null ? "": ktRs.getString("SOHOADON"); 
					String NGAYHOADON = ktRs.getString("NGAYHOADON")== null ? "": ktRs.getString("NGAYHOADON"); 
					String TENNCC = ktRs.getString("TENNCC")== null ? "": ktRs.getString("TENNCC"); 
					String MST = ktRs.getString("MST")== null ? "": ktRs.getString("MST"); 
					String TIENHANG = ktRs.getString("TIENHANG")== null ? "": ktRs.getString("TIENHANG"); 
					String THUESUAT = ktRs.getString("THUESUAT")== null ? "": ktRs.getString("THUESUAT"); 
			//		String TIENTHUE = ktRs.getString("TIENTHUE")== null ? "": ktRs.getString("TIENTHUE"); 
					String MAFAST_DT = ktRs.getString("MAFAST_DT")== null ? "": ktRs.getString("MAFAST_DT"); 
					String TEN_DT = ktRs.getString("TEN_DT")== null ? "": ktRs.getString("TEN_DT"); 
					String TEN_PB = ktRs.getString("TEN_PB")== null ? "": ktRs.getString("TEN_PB"); 
					String TEN_KBH = ktRs.getString("TEN_KBH")== null ? "": ktRs.getString("TEN_KBH"); 
					String TEN_VV = ktRs.getString("TEN_VV")== null ? "": ktRs.getString("TEN_VV"); 
					String TEN_DIABAN = ktRs.getString("TEN_DIABAN")== null ? "": ktRs.getString("TEN_DIABAN"); 
					String TEN_TINHTHANH = ktRs.getString("TEN_TINHTHANH")== null ? "": ktRs.getString("TEN_TINHTHANH"); 
					String TEN_BENHVIEN = ktRs.getString("TEN_BENHVIEN")== null ? "": ktRs.getString("TEN_BENHVIEN"); 
					String TEN_SANPHAM = ktRs.getString("TEN_SANPHAM")== null ? "": ktRs.getString("TEN_SANPHAM"); 
					String DIENGIAI_CT = ktRs.getString("DIENGIAICT")== null ? "": ktRs.getString("DIENGIAICT"); 
					String KHOANMUC_FK = ktRs.getString("KHOANMUC_FK")== null ? "NULL": ktRs.getString("KHOANMUC_FK"); 
			
					if (Double.parseDouble(this.checkThanhtoantuTV) <= 0) {
						// GHI NHAN SO TIEN TT
						if(tienhang > 0)
						{
							taikhoanCO = ktRs.getString("taikhoanCO_NH") == null ? "": ktRs.getString("taikhoanCO_NH");
							
							String nganhang_fk = ktRs.getString("nganhang_fk") == null ? "": ktRs.getString("nganhang_fk");
							
							loaidoituongco = "Ngân hàng";
							madoiduongco = nganhang_fk;
							
							taikhoanNO = ktRs.getString("taikhoanNO") == null ? "" : ktRs.getString("taikhoanNO");
							
							madoiduongno = ktRs.getString("madoituong");
							loaidoituongno = ktRs.getString("doituong");
			
							if (taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0) {
								this.msg = "DKTTK1.3 .Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								return false;
							}
			
							this.msg = util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC( db, thang, nam, ktRs.getString("ngayghinhan")
									, ktRs.getString("ngayghinhan"), "Trả khác", this.id, taikhoanNO, taikhoanCO, "", 
									Double.toString(Math.round(tienhang*tigia)),Double.toString(Math.round(tienhang*tigia)) , loaidoituongno, madoiduongno
									, loaidoituongco, madoiduongco, "0", "", "", tiente_fk, "", Double.toString(tigia)
									, Double.toString(Math.round(tienhang*tigia)), Double.toString(tienhang), "Tiền hàng"
									, Double.toString(tienthue) , diengiai , machungtu, isNPP,
									MAHOADON, MAUHOADON, KYHIEU, SOHOADON,  NGAYHOADON,  TENNCC, MST,  TIENHANG,  THUESUAT,MAFAST_DT,  TEN_DT,  TEN_PB,
									TEN_KBH,  TEN_VV,  TEN_DIABAN,  TEN_TINHTHANH,  TEN_BENHVIEN,  TEN_SANPHAM, DIENGIAI_CT, KHOANMUC_FK, "");
							
			//				public String Update_TaiKhoan_Vat_DienGiai_CHIKHAC (dbutils db, String thang, String nam, String ngaychungtu, String ngayghinhan
			//						, String loaichungtu, String sochungtu, String taikhoanNO_fk, String taikhoanCO_fk
			//						, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
			//						String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO
			//						, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK, String DONGIANT, String TIGIA_FK
			//						, String TONGGIATRI, String TONGGIATRINT, String khoanmuc
			//						, String VAT, String DienGiai, String MaChungTu, String isNPP,
			//						String MAHOADON, String MAUHOADON, String KYHIEU, String SOHOADON, String NGAYHOADON, String TENNCC, String MST, String TIENHANG, String THUESUAT, String MAFAST_DT, String TEN_DT, String TEN_PB, String TEN_KBH, String TEN_VV, String TEN_DIABAN, String TEN_TINHTHANH, String TEN_BENHVIEN, String TEN_SANPHAM, String DIENGIAI_CT, String chiPhiNo, String chiPhiCo)
			
							if(this.msg.trim().length() > 0)
							{
								this.msg = "DKTTK1.4 " + this.msg;
								ktRs.close();
								return false;
							}
						}
						
						if(tienthue > 0)
						{
							taikhoanCO =ktRs.getString("taikhoanCO_NH")== null?"": ktRs.getString("taikhoanCO_NH");
							taikhoanNO = ktRs.getString("taikhoanNO_tienthue")== null?"": ktRs.getString("taikhoanNO_tienthue");
								
							madoiduongno = ktRs.getString("madoituong");
							loaidoituongno = ktRs.getString("doituong");
							
							loaidoituongco = "";
							madoiduongco = "";
							
							if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
							{
								this.msg = "DKTTK1.5 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								return false;
							}
							
							this.msg = util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC( db, thang, nam, ktRs.getString("ngayghinhan"), ktRs.getString("ngayghinhan"), "Trả khác", this.id, taikhoanNO, taikhoanCO, "", 
							Double.toString(Math.round(tienthue*tigia)), Double.toString(Math.round(tienthue*tigia)), loaidoituongno, madoiduongno, loaidoituongco, madoiduongco, "0", "", "", tiente_fk, "", Double.toString(tigia), Double.toString(Math.round(tienthue*tigia)), Double.toString(tienthue), "Tiền thuế", Double.toString(tienthue), diengiai , machungtu, isNPP,
							MAHOADON, MAUHOADON, KYHIEU, SOHOADON,  NGAYHOADON,  TENNCC, MST,  TIENHANG,  THUESUAT, MAFAST_DT,  TEN_DT,  TEN_PB,
							TEN_KBH,  TEN_VV,  TEN_DIABAN,  TEN_TINHTHANH,  TEN_BENHVIEN,  TEN_SANPHAM, DIENGIAI_CT	, KHOANMUC_FK, "");
						
							
							if(this.msg.trim().length() > 0)
							{
								this.msg = "DKTTK1.6 " + this.msg;
								ktRs.close();
								return false;
							}
						}
					}
				}
				ktRs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "DKTTK1.6";
			return false;
		}
		
		return true;
	}

	private boolean dinhKhoanBoPhan(Utility util) 
	{
		String query =	   
				   " select distinct TTHD.NGANHANG_FK , NV.PK_SEQ as NVID, NV.TAIKHOAN_FK as taikhoanNO_NV,  TTHD.NGAYGHINHAN , \n"+
			       "        ( select TAIKHOAN_FK from ERP_NGANHANG_CONGTY where  SoTaiKhoan = TTHD.SOTAIKHOAN AND CONGTY_FK = "+this.ctyId+" ) as taikhoanCO_NH , \n"+
			       "        ( select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+this.ctyId+") as taikhoan_TIENVND, " +
			       "         case when TTHD.TIENTE_FK = 100000 then (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = "+ this.id +" and NHANVIEN_FK = NV.PK_SEQ and LOAIHD in (6)  ) " +
			       "              else (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = "+ this.id +" and NHANVIEN_FK = NV.PK_SEQ  and LOAIHD in (6) ) * TTHD.TIGIA " +
			       "              end as tonggiatri, ISNULL( TTHD.NOIDUNGTT, '' ) NOIDUNGTT, ISNULL ( TTHD.sochungtu, '' ) MACHUNGTU, ISNULL ( TTHD.ISNPP, '') ISNPP  \n" +
			       ", TTHD.httt_fk, TTHD.nganHang_FK, TTHD.tiente_fk\n"+
			       " from   ERP_THANHTOANHOADON TTHD  \n" +
			       "        inner join ERP_THANHTOANHOADON_HOADONBOPHAN TTHD_HD on TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK \n"+
			       "        INNER JOIN ERP_NHANVIEN NV ON TTHD_HD.NHANVIEN_FK = NV.PK_SEQ \n"+
			       " where TTHD.PK_SEQ = "+ this.id +" ";
		System.out.println("Câu query BP "+query);
		
		try {
			ResultSet psktRs = db.get(query);
			if(psktRs == null)
			{
				this.msg = "DKBP1.1";
				return false;
			}
			if(psktRs != null)
			{							
				while(psktRs.next())
				{		
					String hinhthuctt = psktRs.getString("httt_fk");
					String nganhang_fk = psktRs.getString("nganhang_fk");
					String taikhoanNO = "";
					String taikhoanCO = "";
					
					String loaidoituongco = "";
					String madoiduongco = "";
					
					String nhanvienId = psktRs.getString("NVID");
					String diengiai = psktRs.getString("NOIDUNGTT");
					String machungtu = psktRs.getString("MACHUNGTU"); 
					String isNPP = psktRs.getString("ISNPP"); 
					String nam = psktRs.getString("ngayghinhan").substring(0, 4);
					String thang = psktRs.getString("ngayghinhan").substring(5, 7);
					String tiente_fk = psktRs.getString("tiente_fk"); 
					double tonggiatri = Math.round(psktRs.getDouble("tonggiatri")) ;
					
					if(tonggiatri > 0)
					{
						taikhoanNO = psktRs.getString("taikhoanNO_NV") == null ? "": psktRs.getString("taikhoanNO_NV");
						
						if(hinhthuctt.equals("100001")) //THANH TOAN: NGANHANG
						{
							taikhoanCO = psktRs.getString("taikhoanCO_NH")== null?"": psktRs.getString("taikhoanCO_NH") ;
							loaidoituongco = "Ngân hàng";
							madoiduongco = nganhang_fk;
						}
						else //TIEN MAT
						{
							taikhoanCO = psktRs.getString("taikhoan_TIENVND");
						}																																
					
						if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
						{
							this.msg = "DKBP1.2 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							return false;
						}
						
						this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, psktRs.getString("NGAYGHINHAN"), psktRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
						Double.toString(tonggiatri), Double.toString(tonggiatri), "Nhân viên", nhanvienId, loaidoituongco, madoiduongco, "0", "", "", tiente_fk, "", tigia, Double.toString(tonggiatri), Double.toString(tonggiatri),"", "0" , diengiai , machungtu, isNPP);
																		
						if(this.msg.trim().length() > 0)
						{
							this.msg = "DKBP1.2 " + this.msg;
							psktRs.close();
							return false;
						}
					}		
				}
				psktRs.close();
			}										
		}catch (Exception e) {
			e.printStackTrace();
			this.msg = "DKBP1.3";
			return false;
		}
		
		return true;
	}

	private boolean dinhKhoanKhachHang(Utility util) 
	{
		String query =	   
	       " select isnull(TTHD.NOIDUNGTT,'') as diengiai,  TTHD.NGANHANG_FK , tk.PK_SEQ as taikhoanNO_KH,  TTHD.NGAYGHINHAN , \n"+
	       "        ( SELECT TaiKhoan_fk from ERP_NGANHANG_CONGTY where  SoTaiKhoan = TTHD.SOTAIKHOAN AND CONGTY_FK = "+ this.ctyId + " ) as taikhoanCO_NH , \n"+
	       "        ( SELECT pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+ this.ctyId + ")  as taikhoan_TIENVND, " +
	       "        isnull( case when TTHD.TIENTE_FK = 100000 then (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ this.id +" and LOAIHD in (7, 8, 10)  ) " +
	       "              else (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ this.id +"  and LOAIHD in (7, 8, 10) ) * TTHD.TIGIA " +
	       "              end , 0 ) as tonggiatri, TTHD.NOIDUNGTT, TTHD.SOCHUNGTU MACHUNGTU, isnull(TTHD.ISNPP,'') ISNPP \n" +
	       ", TTHD.TIENTE_FK, TTHD.HTTT_FK, TTHD.nganHang_FK, isNull(TTHD.khachHang_NPP_FK, TTHD.KHACHHANG_FK)KHACHHANG_FK\n"+
	       " from   ERP_THANHTOANHOADON TTHD \n" +
	       " inner join  nhaPhanPhoi KH on isNull(TTHD.khachHang_NPP_FK, TTHD.KHACHHANG_FK) = KH.PK_SEQ \n"+
	       " left join erp_taiKhoanKT tk on tk.soHieutaiKhoan = kh.taiKhoan_fk and tk.npp_fk = 1\n" +
	       " where TTHD.PK_SEQ = "+ this.id +" ";

		System.out.println("Câu query NV :\n" + query);
		try 
		{
			ResultSet psktRs = db.get(query);
			if (psktRs == null)
			{
				this.msg = "DKKH1.2";
				return false;
			}
			
			while(psktRs.next())
			{				
				String hinhthuctt = psktRs.getString("HTTT_FK");
				String nganhang_fk = psktRs.getString("nganHang_FK"); 
				String khachhang_fk = psktRs.getString("khachhang_fk");
				String nam = psktRs.getString("ngayghinhan").substring(0, 4);
				String thang = psktRs.getString("ngayghinhan").substring(5, 7);
				String tiente_fk = psktRs.getString("tiente_fk");
				
				String diengiai = psktRs.getString("NOIDUNGTT");
				String machungtu = psktRs.getString("MACHUNGTU");
				String isNPP = psktRs.getString("ISNPP");
				isNPP = "1";
				String taikhoanCO = "";
				String taikhoanNO = "";
				
				String loaidoituongco = "";
				String madoiduongco = "";
				
				double tonggiatri = Math.round(psktRs.getDouble("tonggiatri")) ;
				
				if(tonggiatri > 0)
				{
					taikhoanNO = psktRs.getString("taikhoanNO_KH") == null ? "": psktRs.getString("taikhoanNO_KH");
					
					if(hinhthuctt.equals("100001")) //THANH TOAN: NGANHANG
					{
						taikhoanCO = psktRs.getString("taikhoanCO_NH")== null?"": psktRs.getString("taikhoanCO_NH") ;
						loaidoituongco = "Ngân hàng";
						madoiduongco = nganhang_fk;
					}
					else //TIEN MAT
					{
						taikhoanCO = psktRs.getString("taikhoan_TIENVND");
					}																																
				
					if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
					{
						this.msg = "DKKH1.3 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						return false;
					}
					
					this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, psktRs.getString("NGAYGHINHAN"), psktRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
							Double.toString(tonggiatri), Double.toString(tonggiatri), "Khách hàng", khachhang_fk, loaidoituongco, madoiduongco, "0", "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatri),"Trả tạm ứng khách hàng", "0" , diengiai , machungtu, isNPP, "0");
					
					if(this.msg.trim().length() > 0)
					{
						this.msg = "DKKH1.4 " + this.msg;
						psktRs.close();
						return false;
					}
				}		
			}
			psktRs.close();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "DKKH1.1";
			return false;
		}
		
		return true;
	}

	private boolean dinhKhoanNhanVien(Utility util) 
	{
		String query =
	       " select  TTHD.NGANHANG_FK , (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN= (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ= NV.TAIKHOAN_FK) AND NPP_FK=1) as taikhoanNO_NV,  TTHD.NGAYGHINHAN , \n"+
	       "        ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where  SoTaiKhoan = TTHD.SOTAIKHOAN ) as taikhoanCO_NH , \n"+
	       "        (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+this.ctyId+") as taikhoan_TIENVND, " +
	       "         case when TTHD.TIENTE_FK = 100000 then (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ this.id +" and LOAIHD in (1,5,6) ) " +
	       "              else (select SUM(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ this.id +" and LOAIHD in (1,5,6,8) ) * TTHD.TIGIA " +
	       "              end as tonggiatri, TTHD.NOIDUNGTT, TTHD.sochungtu MACHUNGTU, isnull(TTHD.ISNPP,'') ISNPP \n" +
	       ", TTHD.tiente_fk, TTHD.HTTT_FK, TTHD.nhanvien_fk\n"+
	       " from   ERP_THANHTOANHOADON TTHD  " +
	       " inner join   \n" +
	       "  (  \n" +
	       " SELECT HD.THANHTOANHD_FK, ISNULL(mh.QUANLYCONGNO,0) AS QUANLYCONGNO,HD.LOAIHD FROM ERP_THANHTOANHOADON_HOADON HD  \n" +
	       " left join ERP_MUAHANG mh on HD.HOADON_FK=mh.PK_SEQ   \n"  +
	       " where HD.THANHTOANHD_FK =" +this.id +"  \n"  +
	       " )DNTT on TTHD.PK_SEQ=DNTT.THANHTOANHD_FK   \n"  +
	       "inner join ERP_NHANVIEN NV on TTHD.NHANVIEN_FK = NV.PK_SEQ \n"+
	       " where  TTHD.PK_SEQ = "+ this.id + " AND ( DNTT.LOAIHD<>6 ) ";
			System.out.println("Câu query NV " + query);
		try {
			ResultSet psktRs = db.get(query);
			if (psktRs == null)
			{
				this.msg = "DKNV1.2";
				return false;
			}
			
			if(psktRs != null)
			{			
				while(psktRs.next())
				{								 	
					String nam = psktRs.getString("ngayghinhan").substring(0, 4);
					String thang = psktRs.getString("ngayghinhan").substring(5, 7);
					String tiente_fk = psktRs.getString("tiente_fk");
					
					String taikhoanCO = "";
					String taikhoanNO = "";
					
					String loaidoituongco = "";
					String madoiduongco = "";
					
					String diengiai =  psktRs.getString("NOIDUNGTT");
					String machungtu = psktRs.getString("MACHUNGTU");
					String isNPP =  psktRs.getString("ISNPP");
					
					String hinhthuctt = psktRs.getString("HTTT_FK");
					String nganhang_fk = psktRs.getString("nganhang_fk");
					String nhanvien_fk = psktRs.getString("nhanvien_fk");
					
					double tonggiatri = Math.round(psktRs.getDouble("tonggiatri")) ;
					
					if(tonggiatri > 0)
					{
						taikhoanNO = psktRs.getString("taikhoanNO_NV") == null ? "": psktRs.getString("taikhoanNO_NV");
						
						if(hinhthuctt.equals("100001")) //THANH TOAN: NGANHANG
						{
							taikhoanCO = psktRs.getString("taikhoanCO_NH")== null?"": psktRs.getString("taikhoanCO_NH") ;
							loaidoituongco = "Ngân hàng";
							madoiduongco = nganhang_fk;
						}
						else //TIEN MAT
						{
							taikhoanCO = psktRs.getString("taikhoan_TIENVND");
						}																																
					
						if( taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0 )
						{
							this.msg = "DKNV1.3 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							return false;
						}
						
						this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, psktRs.getString("NGAYGHINHAN"), psktRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO, taikhoanCO, "", 
						Double.toString(tonggiatri), Double.toString(tonggiatri), "Nhân viên", nhanvien_fk, loaidoituongco, madoiduongco, "0", "", "", tiente_fk, "", tigia, Double.toString(tonggiatri), Double.toString(tonggiatri), "", "0" , diengiai , machungtu, isNPP);
																				
						if(this.msg.trim().length() > 0)
						{
							this.msg = "DKNV1.4 " + this.msg;
							psktRs.close();
							return false;
						}
					}		

				}
				psktRs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "DKNV1.1";
			return false;
		}
		
		return true;
	}

	private boolean dinhKhoanNCC(Utility util) 
	{
		String nam="";
		String thang="";
		String query = " SELECT  TTHD.NGANHANG_FK , NCC.TAIKHOAN_FK as taikhoan_NCC,  TTHD.NGAYGHINHAN , \n"+
			"        ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where  SoTaiKhoan = TTHD.SOTAIKHOAN AND CONGTY_FK = "+ this.ctyId + "  ) as taikhoan_NH , \n"+
			"        ( select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+ this.ctyId + " ) as taikhoan_TIENVND, \n"+
			"        TTHD.TIENTE_FK , case when TTHD.TIENTE_FK = 100000 then TTHD_HD.SOTIENTT else isnull(TTHD_HD.SOTIENTT,0)* ISNULL(TTHD.TIGIA,1) end AS TIENTHANHTOAN_HD_VND, \n"+
			"        TTHD.TIENTE_FK , case when TTHD.TIENTE_FK = 100000 then TTHD_HD.SOTIENTT else isnull(TTHD_HD.SOTIENTT,0) end AS TIENTHANHTOAN_HD_NT, \n"+
			"        ( case when TTHD.TIENTE_FK = 100000 then TTHD_HD.SOTIENTT else isnull(TTHD_HD.SOTIENTT,0)* ISNULL(TTHD_HD.TIGIA,1) end ) \n"+
			"        - ( case when TTHD.TIENTE_FK = 100000 then TTHD_HD.SOTIENTT else isnull(TTHD_HD.SOTIENTT,0)* ISNULL(TTHD.TIGIA,1) end ) as TIENCHENHLECH , \n"+
			"        TTHD.NOIDUNGTT, ISNULL(TTHD.SOCHUNGTU,'') AS MACHUNGTU, ISNULL(TTHD.ISNPP,0) ISNPP, TTHD_HD.TIGIA , \n"+
			"        (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51580000' and CONGTY_FK = "+ this.ctyId + " ) TAIKHOAN_51580000, \n"+
			"		  (select pk_seq from ERP_TAIKHOANKT where SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = "+ this.ctyId + ") as taikhoan_11110000, \n"+
			"		  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63580000' and CONGTY_FK = "+ this.ctyId + " ) TAIKHOAN_63580000 \n" +
			"		, tthd.httt_fk, tthd.ncc_fk\n"+
			" FROM   ERP_THANHTOANHOADON TTHD \n"+
			" inner join ERP_NHACUNGCAP NCC on TTHD.NCC_FK= NCC.PK_SEQ \n"+
			" inner join ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD.pk_seq = TTHD_HD.THANHTOANHD_FK and TTHD_HD.loaiHD <> 6\n"+
			" WHERE TTHD.PK_SEQ =  " + this.id + " \n";
		
		System.out.println("Câu query NCC \n" + query);
		ResultSet psktRs = db.get(query);
		if (psktRs == null)
		{
			this.msg = "DKHDNCC1.1 Lỗi chốt UNC\n";
			return false;
		}
		
		try{
			if (psktRs != null) {
				while (psktRs.next()) {
			
					String taikhoanNO = "";
					String loaidoituongco = "";
					String madoiduongco = "";
					String loaidoituongno = "";
					String madoituongno = "";
					String taikhoanCO = "";
					
					
					String ngayghinhan =  psktRs.getString("NGAYGHINHAN");
					nam =ngayghinhan.substring(0,4);
					thang = ngayghinhan.substring(5,7);
					String diengiai = psktRs.getString("NOIDUNGTT");
					String machungtu = psktRs.getString("MACHUNGTU");
					String isNPP = psktRs.getString("ISNPP");
					String nganhang_fk = psktRs.getString("NGANHANG_FK");
					tigia = psktRs.getString("TIGIA");
					String tiente_fk = psktRs.getString("tiente_fk");
					String hinhthuctt = psktRs.getString("httt_fk");
					String ncc_fk = psktRs.getString("ncc_fk");
					double TIENTHANHTOAN_HD_VND = psktRs.getDouble("TIENTHANHTOAN_HD_VND"); 
					double TIENTHANHTOAN_HD_NT = psktRs.getDouble("TIENTHANHTOAN_HD_NT"); 
					
					double TIENCHENHLECH = psktRs.getDouble("TIENCHENHLECH"); 
					
					//double tonggiatri = Math.round(psktRs.getDouble("tonggiatri"));
			
					if (Double.parseDouble(this.checkThanhtoantuTV) <= 0) {
						if (TIENTHANHTOAN_HD_VND > 0) {
			
							taikhoanNO = psktRs.getString("taikhoan_NCC") == null ? "": psktRs.getString("taikhoan_NCC");
							loaidoituongno = "Nhà cung cấp"; 
							madoituongno = ncc_fk;												
							
							if (hinhthuctt.equals("100001") ||hinhthuctt.equals("100003")  ) // THANH// TOAN:// NGANHANG
							{
								taikhoanCO = psktRs.getString("taikhoan_NH") == null ? "": psktRs.getString("taikhoan_NH");
								loaidoituongco = "Ngân hàng";
								madoiduongco = nganhang_fk;
			
							} else // TIEN MAT
							{
								taikhoanCO = psktRs.getString("taikhoan_TIENVND");
							}
			
							if (taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0) {
								this.msg = "DKHDNCC1.2 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								return false;
							}
			
//							public String Update_TaiKhoan_Vat_DienGiai(dbutils db, String thang, String nam, String ngaychungtu
//									, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk
//									, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
//									String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO
//									, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK
//									, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT
//									, String khoanmuc, String VAT, String DienGiai, String MaChungTu
//									, String isNPP)
							this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, psktRs.getString("NGAYGHINHAN")
									, psktRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO
									, taikhoanCO, "", Double.toString(TIENTHANHTOAN_HD_VND), Double.toString(TIENTHANHTOAN_HD_VND)
									, "Nhà cung cấp", ncc_fk, loaidoituongco, madoiduongco
									, "0", "", "", tiente_fk
									, "", tigia, Double.toString(TIENTHANHTOAN_HD_VND), Double.toString(TIENTHANHTOAN_HD_NT)
									, "", "0" , diengiai , machungtu
									, isNPP);
			
						
							if (this.msg.trim().length() > 0) {
								this.msg = "DKHDNCC1.3 " + this.msg;
								psktRs.close();
								return false;
							}
						}
						// chênh lệch này là HÓA ĐƠN - THANH TOÁN 
						
						/*Khi thanh toán hóa đơn nhà cung cấp nước ngoài
						Định khoản:
						Nợ TK 331
						Có TK 112
						Số tiền lấy theo số tiền thanh toán
						Chênh lệch tỷ giá:
						+) Số tiền thanh toán > số tiền hóa đơn
						Nợ TK 635
						Có TK 331
						Số tiền nguyên tệ *(Tỷ giá thanh toán-Tỷ giá hóa đơn)
						+) Số tiền thanh toán < số tiền Hóa đơn
						Nợ TK 331
						Có TK 515
						Số tiền nguyên tệ *(Tỷ giá hóa đơn-Tỷ giá Thanh toán)*/
						if(TIENCHENHLECH > 0)
						{
							//Nợ TK 331
							//Có TK 515
							taikhoanNO = psktRs.getString("taikhoan_NCC") == null ? "": psktRs.getString("taikhoan_NCC");
							loaidoituongno = "Nhà cung cấp"; 
							madoituongno = ncc_fk;	
							
							taikhoanCO = psktRs.getString("TAIKHOAN_51580000") == null ? "": psktRs.getString("TAIKHOAN_51580000");
							loaidoituongco = ""; 
							madoiduongco = "";	
							
							tiente_fk = "100000";
							tigia = "1";
							
							if (taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0) {
								this.msg = "DKHDNCC1.4 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								return false;
							}
			
//							public String Update_TaiKhoan_Vat_DienGiai(dbutils db, String thang, String nam, String ngaychungtu
//							, String ngayghinhan, String loaichungtu, String sochungtu, String taikhoanNO_fk
//							, String taikhoanCO_fk, String NOIDUNGNHAPXUAT_FK, String NO, String CO, 
//							String DOITUONG_NO, String MADOITUONG_NO, String DOITUONG_CO, String MADOITUONG_CO
//							, String LOAIDOITUONG, String SOLUONG, String DONGIA, String TIENTEGOC_FK
//							, String DONGIANT, String TIGIA_FK, String TONGGIATRI, String TONGGIATRINT
//							, String khoanmuc, String VAT, String DienGiai, String MaChungTu
//							, String isNPP)
							this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, psktRs.getString("NGAYGHINHAN")
									, psktRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO
									, taikhoanCO, "", Double.toString(TIENCHENHLECH), Double.toString(TIENCHENHLECH)
									, loaidoituongno, madoituongno, loaidoituongco, madoiduongco
									, "0", "", "", tiente_fk
									, "", tigia, Double.toString(TIENCHENHLECH), Double.toString(TIENCHENHLECH), "", "0" , diengiai , machungtu, isNPP);
			
								
							if (this.msg.trim().length() > 0) {
								this.msg = "DKHDNCC1.5 " + this.msg;
								psktRs.close();
								return false;
							}
						}
						
						if(TIENCHENHLECH < 0)
						{
							//Nợ TK 635
							//Có TK 331
							TIENCHENHLECH = Math.abs(TIENCHENHLECH);
			
							taikhoanNO = psktRs.getString("TAIKHOAN_63580000") == null ? "": psktRs.getString("TAIKHOAN_63580000");
							loaidoituongno = ""; 
							madoituongno = "";	
							
							taikhoanCO = psktRs.getString("taikhoan_NCC") == null ? "": psktRs.getString("taikhoan_NCC");
							loaidoituongco = "Nhà cung cấp"; 
							madoiduongco = ncc_fk;	
							
							tiente_fk = "100000";
							tigia = "1";
							
							if (taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0) {
								this.msg = "DKHDNCC1.6 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								return false;
							}
			
							
							this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, psktRs.getString("NGAYGHINHAN")
									, psktRs.getString("NGAYGHINHAN"), "Thanh toán hóa đơn", this.id, taikhoanNO
									, taikhoanCO, "", Double.toString(TIENCHENHLECH), Double.toString(TIENCHENHLECH)
									, loaidoituongno, madoituongno, loaidoituongco, madoiduongco
									, "0", "", "", tiente_fk
									, "", tigia, Double.toString(TIENCHENHLECH), Double.toString(TIENCHENHLECH), "", "0" , diengiai , machungtu, isNPP);
			
								
							if (this.msg.trim().length() > 0) {
								this.msg = "DKHDNCC1.7 " + this.msg;
								psktRs.close();
								return false;
							}
						}
					}
				}
				psktRs.close();
			}
			
			// THUẾ NHẬP KHẨU
			query = "select (select SOCHUNGTU from ERP_THUENHAPKHAU where PK_SEQ = (select top 1 THUENHAPKHAU_FK from ERP_THANHTOANHOADON_CHIPHIKHAC  where THANHTOANHD_FK= "+ this.id + " )) as sotokhai, \n"+
					" 	    (select sum(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK= "+ this.id + " and LOAIHD = 4 and LOAITHUE= N'Thuế nhập khẩu' and LOAITHUENK = '1') as sotienThueNK, \n"+
					" 	    (select sum(SOTIENTT) from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK= "+ this.id + " and LOAIHD = 4 and LOAITHUE= N'VAT nhập khẩu' and LOAITHUENK = '2') as sotienVATNK, \n"+
					"	    (select TaiKhoan_fk from ERP_NGANHANG_CONGTY  where  SoTaiKhoan = a.SOTAIKHOAN  AND CONGTY_FK = "+ this.ctyId + ")  as taikhoanCO_NH, \n"+
					"       (select pk_seq from erp_taikhoankt where sohieutaikhoan= '33330000' AND CONGTY_FK = "	+ this.ctyId + ") as taikhoanNO_ThueNK, \n"+
					"       (select pk_seq from erp_taikhoankt where sohieutaikhoan= '33312000' AND CONGTY_FK = "	+ this.ctyId + ") as taikhoanNO_VATNK, \n"+
					"       (select TaiKhoan_fk from ERP_NGANHANG_CONGTY  where  SoTaiKhoan = a.SOTAIKHOAN  AND CONGTY_FK = "+ this.ctyId + ") as taikhoanCO_Tienmat, \n"+ 
					"       a.ngayghinhan, a.noidungtt, a.sochungtu machungtu, isnull(a.isnpp, '') ISNPP \n" +
					"		, a.httt_fk, a.nganhang_fk, a.tiente_fk\n"+
					"from ERP_THANHTOANHOADON a  left join ERP_NHACUNGCAP d on a.ncc_fk = d.pk_seq \n"+
					"where a.PK_SEQ = '" + this.id + "'";
			
			System.out.println("KE TOAN_ THANH TOAN THUE NHAP KHAU: " + query);
			ResultSet ktRs = db.get(query);
			if (ktRs == null)
			{
				this.msg = "DKHDNCC1.7.1 Lỗi chốt UNC";
				return false;
			}
			if (ktRs != null) {
				while (ktRs.next()) {
					String loaidoituongco = "";
					String madoiduongco = "";
			
					String taikhoanCO = "";
					String taikhoanNO = "";
					
					String diengiai = ktRs.getString("noidungtt");
					String machungtu = ktRs.getString("machungtu");
					String hinhthuctt = ktRs.getString("httt_fk");
					String nganhang_fk = ktRs.getString("nganhang_fk");
					String tiente_fk = ktRs.getString("tiente_fk");
					String isNPP = ktRs.getString("ISNPP");
			
					if (Double.parseDouble(this.checkThanhtoantuTV) <= 0) {
						// GHI NHAN : THUE NHAP KHAU
						double stThueNK = ktRs.getDouble("sotienThueNK");
						if (stThueNK > 0) {
							taikhoanNO = ktRs.getString("taikhoanNO_ThueNK") == null ? "": ktRs.getString("taikhoanNO_ThueNK");
							if (hinhthuctt.equals("100000")) {
								taikhoanCO = ktRs.getString("taikhoanCO_Tienmat");
							} else {
								taikhoanCO = ktRs.getString("taikhoanCO_NH") == null ? "": ktRs.getString("taikhoanCO_NH");
								loaidoituongco = "Ngân hàng";
								madoiduongco = nganhang_fk;
							}
			
							if (taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0) {
								this.msg = "DKHDNCC1.8 Lỗi xác định tài khoản kế toán (Thuế nhập khẩu). Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								return false;
							}
			
							this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, ktRs.getString("ngayghinhan"), ktRs.getString("ngayghinhan"), "Thanh toán Thuế nhập khẩu", this.id, taikhoanNO, taikhoanCO, "", 
							Double.toString(stThueNK), Double.toString(stThueNK), "", ktRs.getString("sotokhai"), loaidoituongco, madoiduongco, "0", "", "", tiente_fk, "", tigia, Double.toString(stThueNK), Double.toString(stThueNK), ktRs.getString("sotokhai"), "0" , diengiai , machungtu, isNPP);
			
							
							if (this.msg.trim().length() > 0) {
								this.msg = "DKHDNCC1.9 " + this.msg;
								ktRs.close();
								return false;
							}
						}
			
						// GHI NHAN : VAT NHAP KHAU
						double stVATNK = ktRs.getDouble("sotienVATNK");
						if (stVATNK > 0) {
							taikhoanNO = ktRs.getString("taikhoanNO_VATNK") == null ? "": ktRs.getString("taikhoanNO_VATNK");
							if (hinhthuctt.equals("100000")) {
								taikhoanCO = ktRs.getString("taikhoanCO_Tienmat");
							} else {
								taikhoanCO = ktRs.getString("taikhoanCO_NH") == null ? "": ktRs.getString("taikhoanCO_NH");
								loaidoituongco = "Ngân hàng";
								madoiduongco = nganhang_fk;
							}
			
							if (taikhoanCO.trim().length() <= 0 || taikhoanNO.trim().length() <= 0) {
								this.msg = "DKHDNCC1.20 Lỗi xác định tài khoản kế toán (VAT nhập khẩu). Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
								return false;
							}
			
							this.msg = util.Update_TaiKhoan_Vat_DienGiai( db, thang, nam, ktRs.getString("ngayghinhan"), ktRs.getString("ngayghinhan"), "Thanh toán Thuế nhập khẩu", this.id, taikhoanNO, taikhoanCO, "", 
									Double.toString(stVATNK), Double.toString(stVATNK), "", ktRs.getString("sotokhai"), loaidoituongco, madoiduongco, "0", "", "", tiente_fk, "", tigia, Double.toString(stVATNK), Double.toString(stVATNK), ktRs.getString("sotokhai"), "0" , diengiai , machungtu, isNPP);
				
							if (this.msg.trim().length() > 0) {
								this.msg = "DKHDNCC1.21 " + this.msg;
								ktRs.close();
								return false;
							}
						}
			
					}
			
				}
				ktRs.close();
			}
		}catch (Exception e) {
			e.printStackTrace();
			this.msg = "DKHDNCC1.22 Lỗi chốt UNC";
			return false;
		}
		return true;
	}

	private boolean dinhKhoanDNTT() 
	{
		String loaiDoiTuongNO = "";
		String maDoiTuongNO = "";
//		String taiKhoanNO = "";
		String taiKhoanCO = "";
		String loaiDoiTuongCO = "";
		String maDoiTuongCO = "";
		String isCongNo="";
		String query =  " SELECT  MH.QUANLYCONGNO,TTHD_HD.HOADON_FK, \n" +
						" CASE  WHEN MH.NHACUNGCAP_FK  IS NOT NULL THEN N'Nhà cung cấp'  \n" +
						"		WHEN MH.NHANVIEN_FK  IS NOT NULL THEN N'Nhân viên' \n" +
						"		WHEN MH.doiTuongKhac_fk IS NOT NULL THEN N'Đối tượng khác' \n" +
						"		WHEN (MH.KHACHHANG_FK IS NOT NULL or MH.khachHang_NPP_Fk IS NOT NULL) THEN N'Khách hàng' END \n" +
						"AS loaiDoiTuong  \n" +
						", CASE  WHEN  MH.NHACUNGCAP_FK  IS NOT NULL THEN NCC.PK_SEQ \n" +
						"		WHEN MH.NHANVIEN_FK  IS NOT NULL THEN NV.PK_SEQ \n" +
						"		WHEN MH.doiTuongKhac_fk IS NOT NULL THEN MH.doiTuongKhac_fk \n" +
						"		WHEN (MH.KHACHHANG_FK IS NOT NULL or MH.khachHang_NPP_Fk IS NOT NULL) THEN KH.PK_SEQ END \n" +
						" AS maDoiTuong, TTHD_HD.SOTIENTT, \n" +
						" CASE  WHEN MH.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK \n" +
						"		WHEN MH.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK \n" +
						"		ELSE (select pk_seq from erp_taiKhoanKT tk where tk.trangThai = 1 and tk.congTy_FK = MH.congTy_FK and tk.soHieuTaiKhoan = kh.taiKhoan_FK) END \n" +
						" AS taiKhoanDuoiDoiTuong \n" +
						"	, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13311000' AND CONGTY_FK = MH.CONGTY_FK  ) \n" +
						"	AS taiKhoanVAT_133   \n" +
						"	, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = MH.CONGTY_FK  ) \n" +
						"	as taiKhoanTienMat_111\n" +
						"	, ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN =tknh.SOHIEUTAIKHOAN  AND CONGTY_FK = MH.CONGTY_FK  ) \n" +
						"	as taiKhoanNganHang, TTHD.tienTe_FK, TTHD.NOIDUNGTT, \n" +
						"  TTHD.NGAYGHINHAN, TTHD.NGANHANG_FK \n" +
						", TTHD.httt_fk,TTHD.SOCHUNGTU MACHUNGTU \n" +
						" FROM ERP_THANHTOANHOADON TTHD INNER JOIN ERP_THANHTOANHOADON_HOADON TTHD_HD ON TTHD_HD.THANHTOANHD_FK= TTHD.PK_SEQ  \n" +
						" INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = TTHD_HD.HOADON_FK AND LOAIHD=6  " +
						"  LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = MH.NHANVIEN_FK      \n" +
						"  LEFT JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK = NCC.PK_SEQ    \n" +
						"  LEFT JOIN nhaPhanPhoi KH ON isNull(MH.KHACHHANG_FK, MH.khachHang_NPP_Fk) = KH.PK_SEQ \n" +
						"  left join  erp_doiTuongKhac dtk on dtk.pk_seq = MH.doiTuongKhac_fk \n" + 
						"  left join ERP_NGANHANG_CONGTY nhct on nhct.SoTaiKhoan =  TTHD.SOTAIKHOAN \n" +
						"  left join ERP_TAIKHOANKT tknh on tknh.PK_SEQ = nhct.TaiKhoan_FK \n" +
						" WHERE TTHD.PK_SEQ= "+this.id+"  ";
		ResultSet rs= db.get(query);
			try 
			{
				while (rs.next())
				{
					isCongNo=rs.getString("QUANLYCONGNO");
					String hoaDon_fk= rs.getString("HOADON_FK");
					if (isCongNo.trim().equals("1"))
					{
						String httt_fk = rs.getString("httt_fk");
						String machungtu = rs.getString("MACHUNGTU");
		
						String nam = rs.getString("ngayghinhan").substring(0, 4);
						String thang = rs.getString("ngayghinhan").substring(5, 7);
						
						double soTienTT= rs.getDouble("SOTIENTT");
						
						String ngayghinhan = rs.getString("NGAYGHINHAN");			
						String tiente_fk = rs.getString("tiente_fk");
						String diengiai = rs.getString("NOIDUNGTT");
						String loaiDoiTuong = rs.getString("loaiDoiTuong");
						String maDoiTuong = rs.getString("maDoiTuong");
						String nganHang_fk = rs.getString("nganHang_fk");
						
						String taiKhoanTienMat_111 = rs.getString("taiKhoanTienMat_111");
						if (httt_fk.trim().equals("100000") &&(taiKhoanTienMat_111 == null || taiKhoanTienMat_111.trim().length() <= 0))
						{
							msg = "DKDNTT1.5 Vui lòng kiểm tra lại thông tin tài khoản tiền mặt trước khi chốt.";
							return false;
						}
						
						String taiKhoanNganHang = rs.getString("taiKhoanNganHang");
						if (httt_fk.trim().equals("100001") &&(taiKhoanNganHang == null || taiKhoanNganHang.trim().length() <= 0))
						{
							msg = "DKDNTT1.5.1 Vui lòng kiểm tra lại thông tin tài khoản kế toán dưới ngân hàng trước khi chốt.";
							return false;
						}
						
						String taiKhoanDuoiDoiTuong = rs.getString("taiKhoanDuoiDoiTuong");
						
						if (httt_fk.equals("100000"))
						{
							loaiDoiTuongCO = "";
							maDoiTuongCO = "";
							taiKhoanCO = taiKhoanTienMat_111;
						}
						else if (httt_fk.equals("100001"))
						{
							loaiDoiTuongCO = "Ngân hàng";
							maDoiTuongCO = nganHang_fk;
							taiKhoanCO = taiKhoanNganHang;
						}
						
						loaiDoiTuongNO = loaiDoiTuong; //KH, NCC,NV
						maDoiTuongNO = maDoiTuong == null ? "" : maDoiTuong; // KH, NCC,NV
						
						UtilityKeToan ukt = new UtilityKeToan("Thanh toán hóa đơn", this.id, ngayghinhan, ngayghinhan
								, taiKhoanDuoiDoiTuong, taiKhoanCO, Double.toString(soTienTT), Double.toString(soTienTT)
								, tigia, tiente_fk, thang, nam
								, loaiDoiTuongNO, maDoiTuongNO, loaiDoiTuongCO, maDoiTuongCO
								);
						
						ukt.setMaChungTuNoCo(machungtu);
						ukt.setDienGiaiNoCo(diengiai);

						ukt.setKhoanMucNoCo("Tiền thanh toán");
						ukt.setTongGiaTri(Double.toString(soTienTT));
						ukt.setTongGiaTriNT(Double.toString(soTienTT));
		
						
						this.msg = ukt.Update_TaiKhoanByTaiKhoan_FK(db);
						
						if(msg.trim().length() > 0)
						{
							this.msg = "DKDNTT1.7 " + this.msg;
							return false;
						}
						
						
					}else
					{
						query= "SELECT \n" +
						"case when d.SCLON_FK is not null then N'Mã sửa chữa lớn'\n" +
						"	 when d.cpTraTruoc_fk is not null then N'Chi phí trả trước' \n" +
						"	 else N'Khoản mục chi phí' end \n" +
						"AS loaiSanPham\n" +
						", case when d.SCLON_FK is not null then convert(nvarchar, d.SCLON_FK)\n" +
						"	   when d.cpTraTruoc_fk is not null then convert(nvarchar, d.cpTraTruoc_fk)\n" +
						"	   else convert(nvarchar, isNull(a.KHOANMUCCHI, d.chiPhi_FK)) end \n" +
						"AS maSanPham  \n" +
						", CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN N'Nhà cung cấp'  \n" +
						"		WHEN A.NHANVIEN_FK  IS NOT NULL THEN N'Nhân viên' \n" +
						"		WHEN a.doiTuongKhac_fk IS NOT NULL THEN N'Đối tượng khác' \n" +
						"		WHEN (A.KHACHHANG_FK IS NOT NULL or a.khachHang_NPP_Fk IS NOT NULL) THEN N'Khách hàng' END \n" +
						"AS loaiDoiTuong  \n" +
						", CASE  WHEN  A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.PK_SEQ \n" +
						"		WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.PK_SEQ \n" +
						"		WHEN a.doiTuongKhac_fk IS NOT NULL THEN a.doiTuongKhac_fk \n" +
						"		WHEN (A.KHACHHANG_FK IS NOT NULL or a.khachHang_NPP_Fk IS NOT NULL) THEN KH.PK_SEQ END \n" +
						"AS maDoiTuong  \n" +
						", CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK \n" +
						"		WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK \n" +
						"		ELSE (select pk_seq from erp_taiKhoanKT tk where tk.trangThai = 1 and tk.congTy_FK = a.congTy_FK and tk.soHieuTaiKhoan = kh.taiKhoan_FK) END \n" +
						"AS taiKhoanDuoiDoiTuong\n" +
						", CASE WHEN A.KHOANMUCCHI IS NULL THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = E.TAIKHOAN_FK AND CONGTY_FK = A.CONGTY_FK)  \n" +
					    "   WHEN A.KHOANMUCCHI IS NOT NULL THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN =  F.TAIKHOAN_FK AND CONGTY_FK = A.CONGTY_FK)  \n" +
					    "   ELSE '' END AS taiKhoanChiPhi \n" +
					    
						", CASE WHEN A.KHOANMUCCHI IS NULL THEN E.TAIKHOAN_FK \n" +
						"   WHEN A.KHOANMUCCHI IS NOT NULL THEN F.TAIKHOAN_FK \n" +
						"   ELSE '' END AS soHieuTaiKhoanChiPhi \n" +
		
		
						", ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13311000' AND CONGTY_FK = A.CONGTY_FK  ) \n" +
						"AS taiKhoanVAT_133   \n" +
						", ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = A.CONGTY_FK  ) \n" +
						"as taiKhoanTienMat_111\n" +
						", ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN =tknh.SOHIEUTAIKHOAN  AND CONGTY_FK = A.CONGTY_FK  ) \n" +
						"as taiKhoanNganHang \n" +
						", CASE WHEN A.KHOANMUCCHI IS NULL THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = E.TAIKHOAN_FK AND CONGTY_FK = A.CONGTY_FK)  \n" +
					    "   WHEN A.KHOANMUCCHI IS NOT NULL THEN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN =  F.TAIKHOAN_FK AND CONGTY_FK = A.CONGTY_FK)  \n" +
					    "   ELSE '' END AS taiKhoanChiPhi \n" +
						", ISNULL(a.KHOANMUCCHI,'') as cpLon, tt.SOTIENTT, A.NGAYMUA AS NGAYHOADON, ROUND((D.SOLUONG * D.DONGIA),0) AS DOANHSO\n" +
						", tt.tienTe_FK, tt.NOIDUNGTT, a.tongTienAVAT, ROUND(ROUND((D.SOLUONG * D.DONGIA),0) * D.PHANTRAMTHUE/100,0) AS THUE  \n" +
						", case when a.httt_fk = 100004 then 1 else 0 end as isCongNo\n" +
						", tt.SOCHUNGTU MACHUNGTU\n" + 
						", case when (a.khachHang_fk is not null or a.khachHang_NPP_fk is not null) then 1 else 0 end ISNPP\n" + 
						", tt.NGAYGHINHAN, tt.NGANHANG_FK\n" +
						", tt.httt_fk, D.dienGiai dienGiaiChiPhi\n" +
						"  FROM ERP_MUAHANG A    \n" +
						"  left join ERP_THANHTOANHOADON_HOADON tthd on tthd.HOADON_FK = a.pk_seq\n" +
						"  inner join erp_thanhToanHoaDon tt on tt.PK_SEQ = tthd.THANHTOANHD_FK and tthd.loaiHD = 6\n" +
						"  LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK      \n" +
						"  LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ    \n" +
						"  LEFT JOIN nhaPhanPhoi KH ON isNull(A.KHACHHANG_FK, a.khachHang_NPP_Fk) = KH.PK_SEQ \n" +
						"  left join  erp_doiTuongKhac dtk on dtk.pk_seq = a.doiTuongKhac_fk \n" + 
						"  left join ERP_NGANHANG_CONGTY nhct on nhct.SoTaiKhoan = tt.SOTAIKHOAN\n" +
						"  left join ERP_TAIKHOANKT tknh on tknh.PK_SEQ = nhct.TaiKhoan_FK\n" +
						"  LEFT JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK     \n" +
						"  LEFT JOIN ERP_NHOMCHIPHI E ON D.CHIPHI_FK = E.PK_SEQ     \n" +
						"  LEFT JOIN ERP_NHOMCHIPHI F ON A.khoanMucChi = convert(nvarchar,F.PK_SEQ   )   \n" +
						"											   \n" +
						"  WHERE tt.PK_SEQ = " + this.id + " AND TTHD.HOADON_FK="+hoaDon_fk+" " ;
					System.out.println("Cau lenh dinh khoan cho TH DNTT:\n" + query + "\n------------------------------------------------------------");
					ResultSet rsTk = db.get(query);
					if (rsTk == null)
					{
						this.msg = "DKDNTT1.1";
						return false;
					}
					
					
						if(rsTk!= null)
						{
							while(rsTk.next())
							{
								String httt_fk = rsTk.getString("httt_fk");
								String machungtu = rsTk.getString("MACHUNGTU");
								String isNPP = rsTk.getString("ISNPP");
								String nam = rsTk.getString("ngayghinhan").substring(0, 4);
								String thang = rsTk.getString("ngayghinhan").substring(5, 7);
								
								double totalDS = Math.round(rsTk.getDouble("DOANHSO"));
								double totalVAT = Math.round(rsTk.getDouble("THUE"));
								double soTienTT = Math.round(rsTk.getDouble("tongTienAVAT"));
								
								String ngayghinhan = rsTk.getString("NGAYGHINHAN");			
								String tiente_fk = rsTk.getString("tiente_fk");
								String diengiai = rsTk.getString("NOIDUNGTT");
								String dienGiaiChiPhi = rsTk.getString("dienGiaiChiPhi");
								
												
								String loaiSanPham = rsTk.getString("loaiSanPham");
								String maSanPham = rsTk.getString("maSanPham");
								String loaiDoiTuong = rsTk.getString("loaiDoiTuong");
								String maDoiTuong = rsTk.getString("maDoiTuong");
								String nganHang_fk = rsTk.getString("nganHang_fk");
								
								String taiKhoanDuoiDoiTuong = rsTk.getString("taiKhoanDuoiDoiTuong");
								
								String taiKhoanVAT_133 = rsTk.getString("taiKhoanVAT_133");
								if (taiKhoanVAT_133 == null || taiKhoanVAT_133.trim().length() <= 0)
								{
									msg = "DKDNTT1.3 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin tài khoản VAT trước khi chốt.";
									return false;
								}
								
								String taiKhoanChiPhi = rsTk.getString("taiKhoanChiPhi");
								if (taiKhoanChiPhi == null || taiKhoanChiPhi.trim().length() <= 0)
								{
									msg = "DKDNTT1.4 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin mã chi phí trước khi chốt.";
									return false;
								}
								
								String soHieuTaiKhoanChiPhi = rsTk.getString("soHieuTaiKhoanChiPhi");
								
								String taiKhoanTienMat_111 = rsTk.getString("taiKhoanTienMat_111");
								if (httt_fk.trim().equals("100000") &&(taiKhoanTienMat_111 == null || taiKhoanTienMat_111.trim().length() <= 0))
								{
									msg = "DKDNTT1.5 Vui lòng kiểm tra lại thông tin tài khoản tiền mặt trước khi chốt.";
									return false;
								}
								
								String taiKhoanNganHang = rsTk.getString("taiKhoanNganHang");
								if (httt_fk.trim().equals("100001") &&(taiKhoanNganHang == null || taiKhoanNganHang.trim().length() <= 0))
								{
									msg = "DKDNTT1.5.1 Vui lòng kiểm tra lại thông tin tài khoản kế toán dưới ngân hàng trước khi chốt.";
									return false;
								}
								
								//Trường hợp DNTT có tick chọn công nợ (PC, UNC người dùng tạo tay)
								
								if(totalDS > 0)
								{
									if (loaiDoiTuong.trim().contains("Đối tượng khác") 
											&& (!soHieuTaiKhoanChiPhi.trim().startsWith("2") || !soHieuTaiKhoanChiPhi.trim().startsWith("6")))
									{
										loaiDoiTuongNO = loaiDoiTuong;
										maDoiTuongNO = maDoiTuong;
									}
									else
									{
										loaiDoiTuongNO = loaiSanPham; // CHI PHÍ
										maDoiTuongNO = maSanPham == null ? "" : maSanPham; // MÃ CHI PHÍ
									}
									
									if (httt_fk.equals("100000"))
									{
										loaiDoiTuongCO = "";
										maDoiTuongCO = "";
										taiKhoanCO = taiKhoanTienMat_111;
									}
									else if (httt_fk.equals("100001"))
									{
										loaiDoiTuongCO = "Ngân hàng";
										maDoiTuongCO = nganHang_fk;
										taiKhoanCO = taiKhoanNganHang;
									}
									
									UtilityKeToan ukt = new UtilityKeToan("Chi phí khác", this.id, ngayghinhan, ngayghinhan
											, taiKhoanChiPhi, taiKhoanCO, Double.toString(totalDS), Double.toString(totalDS)
											, tigia, tiente_fk, thang, nam
											, loaiDoiTuongNO, maDoiTuongNO, loaiDoiTuongCO, maDoiTuongCO
											);
									
									ukt.setVatNoCo(Double.toString(totalVAT));
									ukt.setMaChungTuNoCo(machungtu);
									ukt.setDienGiaiNoCo(diengiai);
									ukt.setDienGiai_CTNoCo(dienGiaiChiPhi);
									ukt.setKhoanMucNoCo("Doanh số");
									ukt.setTongGiaTri(Double.toString(totalDS));
									ukt.setTongGiaTriNT(Double.toString(totalDS));
									ukt.setIsNPPNo(isNPP);
									if (loaiSanPham.trim().equals("Khoản mục chi phí"))
										ukt.setKhoanMucChiPhiNo_FK(maSanPham);
									else 
										ukt.setKhoanMucChiPhiNo_FK(rsTk.getString("cpLon"));
									
									this.msg = ukt.Update_TaiKhoanByTaiKhoan_FK(db);
									
									if(msg.trim().length() > 0)
									{
										this.msg = "DKDNTT1.7 " + this.msg;
										return false;
									}
								}
								
								if(totalVAT > 0)
								{
									if (httt_fk.equals("100000"))
									{
										loaiDoiTuongCO = "";
										maDoiTuongCO = "";
										taiKhoanCO = taiKhoanTienMat_111;
									}
									else if (httt_fk.equals("100001"))
									{
										loaiDoiTuongCO = "Ngân hàng";
										maDoiTuongCO = nganHang_fk;
										taiKhoanCO = taiKhoanNganHang;
									}
									
									UtilityKeToan ukt = new UtilityKeToan("Chi phí khác", this.id, ngayghinhan, ngayghinhan
											, taiKhoanVAT_133, taiKhoanCO, Double.toString(totalVAT), Double.toString(totalVAT)
											, tigia, tiente_fk, thang, nam
											, "", "", loaiDoiTuongCO, maDoiTuongCO
											);
									ukt.setVatNoCo(Double.toString(totalVAT));
									ukt.setMaChungTuNoCo(machungtu);
									ukt.setDienGiaiNoCo(diengiai);
									ukt.setDienGiai_CTNoCo(dienGiaiChiPhi);
									ukt.setKhoanMucNoCo("VAT");
									ukt.setTongGiaTri(Double.toString(totalVAT));
									ukt.setTongGiaTriNT(Double.toString(totalVAT));
									ukt.setIsNPPNo(isNPP);
									
									this.msg = ukt.Update_TaiKhoanByTaiKhoan_FK(db);
									
									if(msg.trim().length() > 0)
									{
										this.msg = "DKDNTT1.8 " + this.msg;
										return false;
									}
								}
								
							}
							rsTk.close();
						}
		
				
				
				
				
					}
				}
			} catch (Exception e) {
				this.msg = "DKDNTT1.11 ";
				e.printStackTrace();
				return false;
		}
	return true;
	}

	private boolean dinhKhoanCanTru() 
	{
		String query = 
			"select tt.SOTIENTTNT, tt.TIENTE_FK tienTeGoc_FK, tt.TIGIA tiGia_FK, tt.SOTIENTT\n" +
			", tt.NOIDUNGTT dienGiai, tt.NGAYGHINHAN ngayChungTu, isNull(tt.soChungTu, tt.maChungTu) maChungTu\n" +
			", case when tt.NHANVIEN_FK is not null then \n" +
			"	(select tk.PK_SEQ \n" +
			"	from ERP_TAIKHOANKT tk \n" +
			"	where tk.TRANGTHAI = 1 and tk.npp_fk = 1 and tk.SOHIEUTAIKHOAN = (select tkt.SOHIEUTAIKHOAN from ERP_TAIKHOANKT tkt where tkt.PK_SEQ = nv.TAIKHOAN_FK)\n" +
			"	)\n" +
			"when tt.NCC_FK is not null then\n" +
			"	(select tk.PK_SEQ \n" +
			"	from ERP_TAIKHOANKT tk \n" +
			"	where tk.TRANGTHAI = 1 and tk.npp_fk = 1 and tk.SOHIEUTAIKHOAN = (select tkt.SOHIEUTAIKHOAN from ERP_TAIKHOANKT tkt where tkt.PK_SEQ = ncc.TAIKHOAN_FK)\n" +
			"	)\n" +
			"when tt.KHACHHANG_FK is not null then\n" +
			"	(select tk.PK_SEQ \n" +
			"	from ERP_TAIKHOANKT tk \n" +
			"	where tk.TRANGTHAI = 1 and tk.npp_fk = 1 and tk.SOHIEUTAIKHOAN = (npp.TaiKhoan_FK)\n" +
			"	)\n" +
			"end as taiKhoanNo_FK \n" +
			"\n" +
			", case when tt.HTTT_FK = 100001 then --Chuyển khoản\n" +
			"				(select tk.PK_SEQ \n" +
			"				from ERP_TAIKHOANKT tk \n" +
			"				where tk.TRANGTHAI = 1 and tk.npp_fk = 1 and tk.SOHIEUTAIKHOAN = (select tkt.SOHIEUTAIKHOAN from ERP_TAIKHOANKT tkt where tkt.PK_SEQ = nh.TAIKHOAN_FK))\n" +
			"end as taiKhoanCo_FK \n" +

			", case when tt.NHANVIEN_FK is not null then N'Nhân viên'\n" +
			"when tt.NCC_FK is not null then N'Nhà cung cấp'\n" +
			"when tt.KHACHHANG_FK is not null then N'Khách hàng'\n" +
			"end as doiTuongNo \n" +
			", case when tt.NHANVIEN_FK is not null then tt.NHANVIEN_FK\n" +
			"when tt.NCC_FK is not null then tt.NCC_FK\n" +
			"when tt.KHACHHANG_FK is not null then tt.KHACHHANG_FK\n" +
			"end as maDoituongNo \n" +
			
			", case when tt.HTTT_FK = 100001 then N'Ngân hàng'\n" +
			"end as doiTuongCo \n" +
			", case when tt.HTTT_FK = 100000 then ''\n" +
			"when tt.HTTT_FK = 100001 then convert(nvarchar, tt.NGANHANG_FK)\n" +
			"end as maDoituongCo \n" +
			
			"from ERP_THANHTOANHOADON tt \n" +
			"left join ERP_NHANVIEN nv on nv.PK_SEQ = tt.NHANVIEN_FK\n" +
			"left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = tt.NCC_FK\n" +
			"left join NHAPHANPHOI npp on npp.PK_SEQ = tt.KHACHHANG_FK\n" +
			"left join ERP_NGANHANG_CONGTY nh on nh.SoTaiKhoan = tt.SOTAIKHOAN\n" +
			"where tt.PK_SEQ = " +  this.id; 
		System.out.println("dinhKhoanCanTru: \n" + query + "\n-------------------------------------------------------");
		try {
			ResultSet rs = this.db.get(query);
			
			if (rs == null)
			{
				this.msg = "DKCT1.2";
				return false;
			}
			if (rs.next())
			{
				String taiKhoanNo_FK = rs.getString("taiKhoanNo_FK");
				if (taiKhoanNo_FK == null || taiKhoanNo_FK.trim().length() == 0)
				{
					this.msg = "DKCT1.3 Vui lòng kiểm tra lại tài khoản bên dưới Nhân viên / Nhà cung cấp / Khách hàng";
					return false;
				}
				
				String taiKhoanCo_FK = rs.getString("taiKhoanCo_FK");
				if (taiKhoanCo_FK == null || taiKhoanCo_FK.trim().length() == 0)
				{
					this.msg = "DKCT14 Vui lòng kiểm tra lại tài khoản bên dưới Ngân hàng / tài khoản tiền mặt";
					return false;
				}
				//////
		        
				String doiTuongNo = rs.getString("doiTuongNo");
				if (doiTuongNo == null || doiTuongNo.trim().length() == 0)
				{
					this.msg = "DKCT1.5 Vui lòng kiểm tra lại mã Nhân viên / Nhà cung cấp / Khách hàng";
					return false;
				}
				
				String ngayChungTu = rs.getString("ngayChungTu");
				if (ngayChungTu == null || ngayChungTu.trim().length() == 0)
				{
					this.msg = "DKCT1.6 Vui lòng kiểm tra lại ngày ghi nhận";
					return false;
				}
				
				UtilityKeToan ukt = new UtilityKeToan("Thanh toán hóa đơn", this.id, ngayChungTu, ngayChungTu
						, taiKhoanNo_FK, taiKhoanCo_FK, rs.getString("soTienTT"), rs.getString("soTienTT")
						, rs.getString("tiGia_FK"), rs.getString("tienTeGoc_FK")
						, rs.getString("doiTuongNo"), rs.getString("maDoiTuongNo"), rs.getString("doiTuongCo"), rs.getString("maDoiTuongCo")
						);
				ukt.setThangNam(ngayChungTu);
				ukt.setTongGiaTri(rs.getString("soTienTT"));
				if (rs.getString("tienTeGoc_FK") != null && !rs.getString("tienTeGoc_FK").equals("100000"))
					ukt.setTongGiaTriNT(rs.getString("soTienTTNT"));
				ukt.setMaChungTuNoCo(rs.getString("maChungTu"));
				ukt.setDienGiaiNoCo(rs.getString("dienGiai"));
				ukt.setDienGiai_CTNoCo(rs.getString("dienGiai"));
				
				
				this.msg = ukt.Update_TaiKhoanByTaiKhoan_FK(db);
				if (this.msg.trim().length() > 0)
				{
					this.msg = "DKCT1.7 Lỗi hạch toán";
					return false;
				}	
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "DKCT1.1";
			return false;
		}
		
		return true;
	}

	private int getIsCanTru() {
		int result = 0;
		String query = 
			"select case when tt.DNTT_FK is null then 0 \n" + 
			"	else (case when \n" + 
			"			(select isNull(TONGTIENCONLAI, 0)\n" +  
			"			from ERP_MUAHANG dntt \n" + 
			"			where dntt.PK_SEQ = tt.DNTT_FK\n" + 
			"			and 0 < (select COUNT(ct.DENGHITT_FK) \n" + 
			"					from ERP_DENGHITT_THANHTOANHOADON ct\n" +  
			"					where ct.DENGHITT_FK = tt.DNTT_FK)\n" + 
			"			) \n" + 
			"			<> 0 then 1 else 0 end)\n" +
			"	end\n" +
			"from ERP_THANHTOANHOADON tt\n" + 
			"where tt.PK_SEQ = " + this.id + "\n";
		
		try {
			ResultSet rs = this.db.get(query);
			
			if (rs != null)
			{
				if (rs.next())
				{
					result = rs.getInt(1);
				}
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	private boolean capNhatHoaDon(String tableName, String loaiHoaDon) {
		String query = 
			"UPDATE " + tableName + " \n" +
			"SET ISTHANHTOAN = 1 \n" +
			"WHERE PK_SEQ IN (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE THANHTOANHD_FK = " + this.id + " AND LOAIHD = " + loaiHoaDon + " ) ";
		
		if(!db.update(query))
		{
			this.msg = "CTTHD1.3 Khong the chot ERP_TAMUNG: " + query;
			return false;
		}
		
		return true;
	}

	
	private boolean revert_capNhatHoaDon(String tableName, String loaiHoaDon) {
		String query = 
			"UPDATE " + tableName + " \n" +
			"SET ISTHANHTOAN = 0 \n" +
			"WHERE PK_SEQ IN (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE THANHTOANHD_FK = " + this.id + " AND LOAIHD = " + loaiHoaDon + " ) ";
		
		if(!db.update(query))
		{
			this.msg = "CTTHD1.3 Khong the chot ERP_THANHTOANHOADON_HOADON: " + query;
			return false;
		}
		
		return true;
	}


	public void init() {
		NumberFormat formatter = new DecimalFormat("#,###,###");
		String query = " select hd.prefix,  isnull(hd.LoaiThanhToan,'1') as LoaiThanhToan,hd.nhanvien_fk,hd.pk_seq \n"+
					   ", hd.ngayghinhan, hd.trangthai, hd.ncc_fk , hd.httt_fk, hd.nganhang_fk, hd.chinhanh_fk, hd.sotaikhoan,isnull(hd.soChungTu_Chu,'') as soChungTu_Chu,hd.soChungTu_So, \n"+
					   " hd.noidungtt, hd.sotientt ,ncc.ma as mancc,ncc.ten as tenncc, nv.ma as manv,nv.ten as tennv, \n"+
					   " hd.khachhang_fk, kh.ma as makh, kh.ten as tenkh, \n"+
					   " isnull(hd.NHOMNCCCN,'0') nhomncccn , nc.DIENGIAI as tennhomncc , tk.SOHIEUTAIKHOAN +' -- '+ tk.TENTAIKHOAN as dinhkhoanno , \n"+
					   " hd.doituongdinhkhoan , hd.MADOITUONGDINHKHOAN,isnull(hd.THANHTOANTUTIENVAY,0) as THANHTOANTUTIENVAY , \n"+
					   "	hd.ghichu, hd.loaithanhtoan, hd.sotienHD, hd.sotienHDNT, hd.sotientt, hd.sotienttNT, hd.phinganhang, hd.phinganhangNT, \n"+
					   "   hd.vat, hd.vatNT, hd.sotienttNT, isnull(hd.chenhlechVND,0) as chenhlechVND , trichphi, sotaikhoan_tp, nganhang_tp_fk, chinhanh_tp_fk, \n"+
					   "   PNH.mahoadon , PNH.mauhoadon, PNH.KYHIEU, PNH.SOHOADON, PNH.NGAYHOADON, PNH.TENNCC, PNH.MST, isnull(PNH.TIENHANG,0) as TIENHANG, isnull(PNH.THUESUAT,0) as THUESUAT, \n"+
					   "   isnull(PNH.TIENTHUE,0) as TIENTHUE, PNH.NGANHANG_FK AS NGANHANG_PNH_FK, PNH.CHINHANH_FK AS CHINHANH_PNH_FK, hd.TIENTE_FK, hd.TIGIA ,  \n"+
					   "   hd.dvth_fk, dvth.pk_seq as dvthId, dvth.ma as dvthMa, dvth.Ten as dvthTen, isnull(hd.isNPP,'0') isNPP, hd.NPP_FK , isnull(hd.isTICHLUY,0) tratichluy \n" +
					   "	, case when hd.ISKTTDUYET = 1 and hd.trangThai = 0 then 1 else 0 end as daDuyet \n" +
					   "	, isNull(kh.ten, '') as tenKhachHang, isNull(httt.ten, '') as tenHinhThucThanhToan\n" +
					   "	, hd.SOTAIKHOAN_TP + ' - ' + (select ten from ERP_NGANHANG where pk_seq = hd.nganhang_tp_fk) + ' - ' + (select ten from ERP_CHINHANH where pk_seq = hd.chinhanh_tp_fk) as tenSoTaiKhoanTrichPhi\n"+
					   "	, hd.SOTAIKHOAN + ' - ' + (select ten from ERP_NGANHANG where pk_seq = hd.nganhang_fk) + ' - ' + (select ten from ERP_CHINHANH where pk_seq = hd.chinhanh_fk) as tenSoTaiKhoan\n" +
					   "	, (select ma from ERP_TIENTE where PK_SEQ = hd.tienTe_FK) as tenTienTe\n" +
					   "	, hd.trangThai \n" +
					   "	, isNull(hd.soChungTu, '') as soChungTu, KHOANMUC_FK, ISNULL(PNH.DIACHI, '') DIACHI \n"+
					   "		, case when hd.DNTT_FK > 0 then '1' else '0' end isDNTT, hd.doiTuongKhac_FK, hd.khachHang_NPP_FK \n"+
					   "    , ISNULL(CONVERT(VARCHAR,HD.DNTT_FK),'') AS DNTT_FK, ISNULL(CONVERT(VARCHAR,HD.DNTU_FK),'') AS DNTU_FK \n"+
					   
					   " from ERP_THANHTOANHOADON hd  \n"+
					   " left join ERP_THANHTOANHOADON_PHINGANHANG PNH on PNH.THANHTOANHD_FK = hd.PK_SEQ \n" +
					   " left join ERP_HINHTHUCTHANHTOAN httt on httt.pk_seq = hd.HTTT_FK\n"+ 
					   " left join erp_nhacungcap ncc on ncc.pk_Seq=hd.ncc_fk \n"+
					   " left join erp_nhanvien nv on nv.pk_Seq=hd.nhanvien_fk  \n"+
					   " left join khachhang kh on kh.pk_seq = hd.khachhang_fk \n"+
					   " left join erp_donvithuchien dvth on dvth.pk_seq = hd.dvth_fk \n"+
					   "   left join NHOMNHACUNGCAPCN nc on nc.PK_SEQ = hd.Nhomncccn \n"+
					   " left join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN = hd.dinhkhoanno \n" + 
					   "where hd.pk_seq = '"+ this.id + "'";
		System.out.println("GET SQL : \n" + query + "\n-------------------------------------------------------------");
		String isTICHLUY = "";
		
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.khachHang_NPP_FK = rs.getString("khachHang_NPP_FK")==null?"": rs.getString("khachHang_NPP_FK");
					this.doiTuongKhacId = rs.getString("doiTuongKhac_FK")==null?"":rs.getString("doiTuongKhac_FK");
					this.isDNTT = rs.getString("isDNTT");
					this.soChungTu = rs.getString("soChungTu");
					this.DNTT_FK = rs.getString("DNTT_FK");
					this.DNTU_FK = rs.getString("DNTU_FK");
					this.soChungTu = rs.getString("soChungTu").toUpperCase();
					this.soChungTu_Chu =  rs.getString("soChungTu_chu").toUpperCase();
					this.soChungTu_So =  rs.getString("soChungTu_so");
					
					this.trangThai = rs.getInt("trangThai");
					this.tenKhachHang = rs.getString("tenKhachHang");
					this.tenHinhThucThanhToan = rs.getString("tenHinhThucThanhToan");
					this.tenSoTaiKhoan = rs.getString("tenSoTaiKhoan");
					this.tenTienTe = rs.getString("tenTienTe");
					this.tenSoTaiKhoanTrichPhi = rs.getString("tenSoTaiKhoanTrichPhi");
					
					this.daDuyet = rs.getInt("daDuyet");
					this.isNpp = rs.getString("isNPP");
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("ncc_fk") == null ? "" : rs.getString("ncc_fk");
					this.htttId = rs.getString("httt_fk");
					this.nhomNCCCNId = rs.getString("NHOMNCCCN");
					this.prefix = rs.getString("prefix");
					this.checkThanhtoantuTV = rs.getString("THANHTOANTUTIENVAY");
					
					isTICHLUY = rs.getString("tratichluy");

					if (this.htttId.equals("100001")||this.htttId.equals("100003")) {
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}

					this.NhanvienId = rs.getString("nhanvien_fk");
					if (this.NhanvienId == null) {
						this.NhanvienId = "";
						if (this.nccId.length() > 1) {
							this.DoiTuongChiPhiKhac = "1";
							this.TenHienThi = rs.getString("ncc_fk") + " -- " + rs.getString("mancc") + " -- "+ rs.getString("tenncc");
						} else {
							this.TenHienThi = rs.getString("NHOMNCCCN") + " -- " + rs.getString("tennhomncc");
						}
					}

					if (this.nccId.length() <= 1 && this.NhanvienId.length() > 1) {
						this.nccId = "";
						this.DoiTuongChiPhiKhac = "0";
						this.TenHienThi = rs.getString("nhanvien_fk") + " -- " + rs.getString("manv") + " -- "+ rs.getString("tennv");
					}

					this.khachhangId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");

					if (this.khachhangId.length() > 0) {
						
						if(isTICHLUY.equals("1"))
							this.DoiTuongChiPhiKhac = "5";
						else
							this.DoiTuongChiPhiKhac = "2";
						
						this.TenHienThi = rs.getString("khachhang_fk") + " -- " + rs.getString("makh") + ","+ rs.getString("tenkh");
					}

					this.bophanId = rs.getString("dvth_fk") == null ? "" : rs.getString("dvth_fk");
					if (this.bophanId.length() > 0) {
						this.DoiTuongChiPhiKhac = "4";
						this.bophanTen = rs.getString("dvthId") + " - " + rs.getString("dvthMa") + ","+ rs.getString("dvthTen");
					}

					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));

					if(this.bophanId.trim().length() <= 0 && this.nccId.trim().length() <= 0 && this.NhanvienId.trim().length() <= 0 && this.khachhangId.trim().length() <= 0 
							&& (this.doiTuongKhacId == null || this.doiTuongKhacId.trim().length() == 0)
							&& (this.khachHang_NPP_FK == null || this.khachHang_NPP_FK.trim().length() == 0)) // tra Khac
					{
						this.DoiTuongChiPhiKhac = "3";
						this.dinhkhoanno = rs.getString("dinhkhoanno");
						this.DoiTuongDinhKhoan = rs.getString("doituongdinhkhoan") == null ? ""	: rs.getString("doituongdinhkhoan");
						this.doituongdinhkhoanId = rs.getString("MADOITUONGDINHKHOAN") == null ? ""	: rs.getString("MADOITUONGDINHKHOAN");
					}

					if (this.doiTuongKhacId != null && this.doiTuongKhacId.trim().length() > 0)
						this.DoiTuongChiPhiKhac = "6";
					
					if(this.khachHang_NPP_FK != null && this.khachHang_NPP_FK.length() > 0){
						this.DoiTuongChiPhiKhac = "7";
					}
					
					this.sotientt = rs.getString("sotientt").replaceAll(",", "");
					this.sotienttNT = rs.getString("sotienttNT").replaceAll(",", "");
					this.sotienHD = rs.getString("sotienHD").replaceAll(",", "");
					this.sotienHDNT = rs.getString("sotienHDNT").replaceAll(",", "");
					this.pnganhang = rs.getString("phinganhang").replaceAll(",", "");
					this.pnganhangNT = rs.getString("phinganhangNT").replaceAll(",", "");
					this.thueVAT = rs.getString("vat").replaceAll(",", "");
					this.thueVATNT = rs.getString("vatNT").replaceAll(",", "");
					this.chenhlechVND = rs.getString("chenhlechVND").replaceAll(",", "");
					this.trichphi = rs.getString("trichphi");
					this.sotaikhoan_tp = rs.getString("sotaikhoan_tp") == null ? "" : rs.getString("sotaikhoan_tp");
					this.nganhang_tpId = rs.getString("nganhang_tp_fk") == null ? "" : rs.getString("nganhang_tp_fk");
					this.chinhanh_tpId = rs.getString("chinhanh_tp_fk");
					this.mahoadon = rs.getString("mahoadon") == null ? "" : rs.getString("mahoadon");
					this.mauhoadon = rs.getString("mauhoadon") == null ? "" : rs.getString("mauhoadon");
					this.kyhieu = rs.getString("KYHIEU") == null ? "" : rs.getString("KYHIEU");
					this.sohoadon = rs.getString("SOHOADON") == null ? "" : rs.getString("SOHOADON");
					this.ngayhoadon = rs.getString("NGAYHOADON");

					this.tenNCC = rs.getString("TENNCC") == null ? "" : rs.getString("TENNCC");
					this.mst = rs.getString("MST") == null ? "" : rs.getString("MST");
					this.tienhang = rs.getString("TIENHANG").replaceAll(",", "");

					this.thuesuat = rs.getString("THUESUAT").replaceAll(",", "");
					this.tienthue = rs.getString("TIENTHUE").replaceAll(",", "");

					this.nhId_VAT = rs.getString("NGANHANG_PNH_FK") == null ? "" : rs.getString("NGANHANG_PNH_FK");
					this.cnId_VAT = rs.getString("CHINHANH_PNH_FK") == null ? "" : rs.getString("CHINHANH_PNH_FK");
					this.tienteId = rs.getString("TIENTE_FK");

					this.tigia = rs.getString("TIGIA").replaceAll(",", "");

				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// khoi tao them loai KHAC
		if (this.DoiTuongDinhKhoan != null && !(this.DoiTuongDinhKhoan.equals(""))) {
			createDoiTuongDinhKhoan();
		}

		if (this.DoiTuongChiPhiKhac.equals("3")) {
			this.count=0;
			if(this.id.length() >0){
				query=	" SELECT COUNT(*) as count"+
						" FROM  ERP_THANHTOANHOADON_PHINGANHANG BT "+
						" WHERE THANHTOANHD_FK = "+this.id;
				System.out.println(query);
				rs =this.db.get(query);
				if(rs!=null)
				{
					try
					{
						while(rs.next())
						{
							this.count=rs.getInt("count");
						}rs.close();
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
			}
			
			TaiKhoanIds=new String[count];
			dcIds = new String[count];
			ttcpIds = new String[count];
			diaChiHd = new String[count];
			KenhbhIds = new String[count];
			PhongBanIds = new String[count];
			TinhThanhIds = new String[count];
			SanPhamIds = new String[count];
			DiabanIds = new String[count];
			BenhvienIds = new String[count];
			Mauhd = new String[count];
			Kyhieuhd = new String[count];
			MavvIds = new String[count];
			Sohd = new String[count];
			Ngayhd = new String[count];
			TenNCChd =  new String[count];
			MSThd = new String[count];
			Thuesuathd = new String[count];
			Tienthuehd = new String[count];
			Tienhanghd = new String[count];
			Diengiaihd = new String[count];
			loais = new String[count];
					
			for (int j = 0; j < count; j++){
				TaiKhoanIds[j] = "";
				dcIds[j] = "";
				ttcpIds[j] = "";
				diaChiHd[j] = "";
				KenhbhIds[j] = "";
				PhongBanIds[j] = "";
				TinhThanhIds[j] = "";
				DiabanIds[j] = "";
				BenhvienIds[j] = "";
				SanPhamIds[j] = "";
				MavvIds[j]= "";
				Mauhd[j] = "";
				Kyhieuhd[j] = "";
				Sohd[j] = "";
				Ngayhd[j] = "";
				TenNCChd[j] = "";
				MSThd[j] = "";
				Thuesuathd[j] = "";
				Tienthuehd[j] = "";
				Tienhanghd[j] = "";
				Diengiaihd[j] = "";
				loais[j] = "";
			}
			
			query=	" SELECT BT.*, " +
					" CASE WHEN BT.SANPHAM_FK IS NOT NULL THEN 1 "+
					"      WHEN BT.NCC_FK IS NOT NULL THEN 2 "+
					"      WHEN BT.NGANHANG_FK IS NOT NULL THEN 3 "+
					"      WHEN BT.TAISAN_FK IS NOT NULL THEN 4 "+
					"      WHEN BT.KHACHHANG_FK IS NOT NULL THEN 5 "+
					"      WHEN BT.TTCP_FK IS NOT NULL THEN 6 "+
					"      WHEN BT.NHANVIEN_FK IS NOT NULL THEN 7 "+
					"      WHEN BT.DOITUONGKHAC_FK IS NOT NULL THEN 8 "+

					" 	   ELSE 0 END LOAI "+
					" FROM ERP_THANHTOANHOADON_PHINGANHANG BT "+
					" WHERE THANHTOANHD_FK = "+this.id;
			
			System.out.println(query);
			rs = db.get(query);
			
			if(rs != null)
			{
				try 
				{
					int i = 0 ;
										
					while(rs.next())
					{				
						loais[i] = rs.getString("LOAI");
						
						TaiKhoanIds[i]=rs.getString("TAIKHOAN_FK") == null?"":rs.getString("taikhoan_fk");
						
						if(rs.getString("SANPHAM_FK") != null){
							
							dcIds[i]=rs.getString("SANPHAM_FK");
						
						}else if(rs.getString("NGANHANG_FK") != null){
						
							dcIds[i]=rs.getString("NGANHANG_FK");
						
						}else if(rs.getString("NCC_FK") != null){ 
						
							dcIds[i]=rs.getString("NCC_FK");
						
						}else if(rs.getString("TAISAN_FK") != null){ 
						
							dcIds[i]=rs.getString("TAISAN_FK");
						
						}else if(rs.getString("KHACHHANG_FK") != null){
						
							dcIds[i]=rs.getString("KHACHHANG_FK")+ "-"+rs.getString("ISNPP");
							
						}else if(rs.getString("NHANVIEN_FK") != null){
							
							dcIds[i]=rs.getString("NHANVIEN_FK");
							
						}else if(rs.getString("DOITUONGKHAC_FK") != null){
							
							dcIds[i]=rs.getString("DOITUONGKHAC_FK");
							
						}else if(rs.getString("TTCP_FK") != null){
							
							dcIds[i]=rs.getString("TTCP_FK");
						}

						this.ttcpIds[i] =  rs.getString("KHOANMUC_FK") == null?"":rs.getString("KHOANMUC_FK");	
						this.diaChiHd[i] =  rs.getString("DIACHI") == null?"":rs.getString("DIACHI");			
						PhongBanIds[i] =  rs.getString("phongban_fk") == null?"":rs.getString("phongban_fk");						
						
						KenhbhIds[i] =  rs.getString("kbh_fk") == null?"":rs.getString("kbh_fk");
						
						TinhThanhIds[i] =  rs.getString("tinhthanh_fk") == null?"":rs.getString("tinhthanh_fk");
						
						SanPhamIds[i] =  rs.getString("sp_fk") == null?"":rs.getString("sp_fk");
						
						DiabanIds[i] = rs.getString("diaban_fk") == null?"":rs.getString("diaban_fk");
						
						BenhvienIds[i] = rs.getString("benhvien_fk") == null?"":rs.getString("benhvien_fk");
						
						MavvIds[i] = rs.getString("vuviec_fk") == null?"":rs.getString("vuviec_fk");
						
						Mauhd[i] = rs.getString("MAUHOADON")== null?"":rs.getString("MAUHOADON");
						
						Kyhieuhd[i] = rs.getString("kyhieu")== null?"":rs.getString("kyhieu");
						
						Sohd[i] = rs.getString("Sohoadon");
						
						Ngayhd[i] = rs.getString("Ngayhoadon");
												
						TenNCChd[i] = rs.getString("tenncc");
												
						MSThd[i] = rs.getString("mst");
						
						Thuesuathd[i] = rs.getString("thuesuat");
						
						Tienthuehd[i] = rs.getString("tienthue");
						
						Tienhanghd[i] = rs.getString("tienhang");	
						
						Diengiaihd[i] = rs.getString("diengiai")== null?"":rs.getString("diengiai");
					
						i++;
					
					}
					rs.close();
				}
					
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		this.createRs();
	}

	public void createDoiTuongDinhKhoan() {
		String query = "";
		if (this.DoiTuongDinhKhoan.equals("1")) {
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from SANPHAM where PK_SEQ = "
					+ this.doituongdinhkhoanId;
		} else if (this.DoiTuongDinhKhoan.equals("2")) {
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_NGANHANG where PK_SEQ = "
					+ this.doituongdinhkhoanId;
		} else if (this.DoiTuongDinhKhoan.equals("3")) {
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_NHACUNGCAP where PK_SEQ = "
					+ this.doituongdinhkhoanId+ " AND CONGTY_FK ="+this.ctyId;
		} else if (this.DoiTuongDinhKhoan.equals("4")) {
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_TAISANCODINH where PK_SEQ = "
					+ this.doituongdinhkhoanId;
		} else if (this.DoiTuongDinhKhoan.equals("5")) {
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from NHAPHANPHOI where PK_SEQ = "
					+ this.doituongdinhkhoanId;
		} else if (this.DoiTuongDinhKhoan.equals("6")) {
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_NHANVIEN where PK_SEQ = "
					+ this.doituongdinhkhoanId;
		} else if (this.DoiTuongDinhKhoan.equals("8")) {
			query = "select cast(PK_SEQ as varchar) + ' -- ' + MA+ ',' + TEN  as maten from ERP_DOITUONGKHAC where PK_SEQ = "
				+ this.doituongdinhkhoanId;
		}

		System.out.println("GET doi tuong : " + query);
		ResultSet dtrs = db.get(query);
		if (dtrs != null)
			try {
				while (dtrs.next()) {
					this.MaTenDoiTuongDinhKhoan = dtrs.getString("maten");
				}
				dtrs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}

	public void initDisplay() {
		NumberFormat formatter = new DecimalFormat("#,###,###");
		String query = " select hd.prefix,  isnull(hd.LoaiThanhToan,'1') as LoaiThanhToan,hd.nhanvien_fk,hd.pk_seq \n"+
					   ", hd.ngayghinhan, hd.trangthai, hd.ncc_fk , hd.httt_fk, hd.nganhang_fk, hd.chinhanh_fk, hd.sotaikhoan, \n"+
					   " hd.noidungtt, hd.sotientt ,ncc.ma as mancc,ncc.ten as tenncc, nv.ma as manv,nv.ten as tennv, \n"+
					   " hd.khachhang_fk, kh.ma as makh, kh.ten as tenkh, \n"+
					   " isnull(hd.NHOMNCCCN,'0') nhomncccn , nc.DIENGIAI as tennhomncc , tk.SOHIEUTAIKHOAN +' -- '+ tk.TENTAIKHOAN as dinhkhoanno , \n"+
					   " hd.doituongdinhkhoan , hd.MADOITUONGDINHKHOAN,isnull(hd.THANHTOANTUTIENVAY,0) as THANHTOANTUTIENVAY , \n"+
					   "	hd.ghichu, hd.loaithanhtoan, hd.sotienHD, hd.sotienHDNT, hd.sotientt, hd.sotienttNT, hd.phinganhang, hd.phinganhangNT, \n"+
					   "   hd.vat, hd.vatNT, hd.sotienttNT, isnull(hd.chenhlechVND,0) as chenhlechVND , trichphi, sotaikhoan_tp, nganhang_tp_fk, chinhanh_tp_fk, \n"+
					   "   PNH.mahoadon , PNH.mauhoadon, PNH.KYHIEU, PNH.SOHOADON, PNH.NGAYHOADON, PNH.TENNCC, PNH.MST, isnull(PNH.TIENHANG,0) as TIENHANG, isnull(PNH.THUESUAT,0) as THUESUAT, \n"+
					   "   isnull(PNH.TIENTHUE,0) as TIENTHUE, PNH.NGANHANG_FK AS NGANHANG_PNH_FK, PNH.CHINHANH_FK AS CHINHANH_PNH_FK, hd.TIENTE_FK, hd.TIGIA ,  \n"+
					   "   hd.dvth_fk, dvth.pk_seq as dvthId, dvth.ma as dvthMa, dvth.Ten as dvthTen, isnull(hd.isNPP,'0') isNPP, hd.NPP_FK , isnull(hd.isTICHLUY,0) tratichluy \n"+
					   "   , case when hd.DNTT_FK > 0 then '1' else '0' end as isDNTT, hd.khachHang_NPP_FK\n"+
					   
					   " from ERP_THANHTOANHOADON hd  \n"+
					   " left join ERP_THANHTOANHOADON_PHINGANHANG PNH on PNH.THANHTOANHD_FK = hd.PK_SEQ \n"+ 
					   " left join erp_nhacungcap ncc on ncc.pk_Seq=hd.ncc_fk \n"+
					   " left join erp_nhanvien nv on nv.pk_Seq=hd.nhanvien_fk  \n"+
					   " left join khachhang kh on kh.pk_seq = hd.khachhang_fk \n"+
					   " left join erp_donvithuchien dvth on dvth.pk_seq = hd.dvth_fk \n"+
					   "   left join NHOMNHACUNGCAPCN nc on nc.PK_SEQ = hd.Nhomncccn \n"+
					   " left join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN = hd.dinhkhoanno \n" + 
					   "where hd.pk_seq = '"+ this.id + "'";
					System.out.println("GET SQL : " + query);
					String isTICHLUY = "";
					
					ResultSet rs = db.get(query);
					if (rs != null) {
					try {
						while (rs.next()) {
							this.khachHang_NPP_FK = rs.getString("khachHang_NPP_FK");
							this.isDNTT = rs.getString("isDNTT");
							this.isNpp = rs.getString("isNPP");
							this.ngayghinhan = rs.getString("ngayghinhan");
							this.nccId = rs.getString("ncc_fk") == null ? "" : rs.getString("ncc_fk");
							this.htttId = rs.getString("httt_fk");
							this.nhomNCCCNId = rs.getString("NHOMNCCCN");
							this.prefix = rs.getString("prefix");
							this.checkThanhtoantuTV = rs.getString("THANHTOANTUTIENVAY");
							
							isTICHLUY = rs.getString("tratichluy");
					
							if (this.htttId.equals("100001")||this.htttId.equals("100003")) {
								this.nganhangId = rs.getString("nganhang_fk");
								this.chinhanhId = rs.getString("chinhanh_fk");
								this.sotaikhoan = rs.getString("sotaikhoan");
							}
					
							this.NhanvienId = rs.getString("nhanvien_fk");
							if (this.NhanvienId == null) {
								this.NhanvienId = "";
								if (this.nccId.length() > 1) {
									this.DoiTuongChiPhiKhac = "1";
									this.TenHienThi = rs.getString("ncc_fk") + " -- " + rs.getString("mancc") + " -- "+ rs.getString("tenncc");
								} else {
									this.TenHienThi = rs.getString("NHOMNCCCN") + " -- " + rs.getString("tennhomncc");
								}
							}
					
							if (this.nccId.length() <= 1 && this.NhanvienId.length() > 1) {
								this.nccId = "";
								this.DoiTuongChiPhiKhac = "0";
								this.TenHienThi = rs.getString("nhanvien_fk") + " -- " + rs.getString("manv") + " -- "+ rs.getString("tennv");
							}
					
							this.khachhangId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
					
							if (this.khachhangId.length() > 0) {
								
								if(isTICHLUY.equals("1"))
									this.DoiTuongChiPhiKhac = "5";
								else
									this.DoiTuongChiPhiKhac = "2";
								
								this.TenHienThi = rs.getString("khachhang_fk") + " -- " + rs.getString("makh") + ","+ rs.getString("tenkh");
							}
					
							this.bophanId = rs.getString("dvth_fk") == null ? "" : rs.getString("dvth_fk");
							if (this.bophanId.length() > 0) {
								this.DoiTuongChiPhiKhac = "4";
								this.bophanTen = rs.getString("dvthId") + " - " + rs.getString("dvthMa") + ","+ rs.getString("dvthTen");
							}
					
							this.noidungtt = rs.getString("noidungtt");
							this.sotientt = formatter.format(rs.getDouble("sotientt"));
					
							if(this.bophanId.trim().length() <= 0 && this.nccId.trim().length() <= 0 && this.NhanvienId.trim().length() <= 0 && this.khachhangId.trim().length() <= 0 
									&& (this.doiTuongKhacId == null || this.doiTuongKhacId.trim().length() == 0)
									&& (this.khachHang_NPP_FK == null || this.khachHang_NPP_FK.trim().length() == 0)) // tra Khac
							{
								this.DoiTuongChiPhiKhac = "3";
								this.dinhkhoanno = rs.getString("dinhkhoanno");
								this.DoiTuongDinhKhoan = rs.getString("doituongdinhkhoan") == null ? ""	: rs.getString("doituongdinhkhoan");
								this.doituongdinhkhoanId = rs.getString("MADOITUONGDINHKHOAN") == null ? ""	: rs.getString("MADOITUONGDINHKHOAN");
					
							}
							
							if(this.khachHang_NPP_FK != null && this.khachHang_NPP_FK.length() > 0){
								this.DoiTuongChiPhiKhac = "7";
							}
					
							this.sotientt = rs.getString("sotientt").replaceAll(",", "");
							this.sotienttNT = rs.getString("sotienttNT").replaceAll(",", "");
							this.sotienHD = rs.getString("sotienHD").replaceAll(",", "");
							this.sotienHDNT = rs.getString("sotienHDNT").replaceAll(",", "");
							this.pnganhang = rs.getString("phinganhang").replaceAll(",", "");
							this.pnganhangNT = rs.getString("phinganhangNT").replaceAll(",", "");
							this.thueVAT = rs.getString("vat").replaceAll(",", "");
							this.thueVATNT = rs.getString("vatNT").replaceAll(",", "");
							this.chenhlechVND = rs.getString("chenhlechVND").replaceAll(",", "");
							this.trichphi = rs.getString("trichphi");
							this.sotaikhoan_tp = rs.getString("sotaikhoan_tp") == null ? "" : rs.getString("sotaikhoan_tp");
							this.nganhang_tpId = rs.getString("nganhang_tp_fk") == null ? "" : rs.getString("nganhang_tp_fk");
							this.chinhanh_tpId = rs.getString("chinhanh_tp_fk");
							this.mahoadon = rs.getString("mahoadon") == null ? "" : rs.getString("mahoadon");
							this.mauhoadon = rs.getString("mauhoadon") == null ? "" : rs.getString("mauhoadon");
							this.kyhieu = rs.getString("KYHIEU") == null ? "" : rs.getString("KYHIEU");
							this.sohoadon = rs.getString("SOHOADON") == null ? "" : rs.getString("SOHOADON");
							this.ngayhoadon = rs.getString("NGAYHOADON");
					
							this.tenNCC = rs.getString("TENNCC") == null ? "" : rs.getString("TENNCC");
							this.mst = rs.getString("MST") == null ? "" : rs.getString("MST");
							this.tienhang = rs.getString("TIENHANG").replaceAll(",", "");
					
							this.thuesuat = rs.getString("THUESUAT").replaceAll(",", "");
							this.tienthue = rs.getString("TIENTHUE").replaceAll(",", "");
					
							this.nhId_VAT = rs.getString("NGANHANG_PNH_FK") == null ? "" : rs.getString("NGANHANG_PNH_FK");
							this.cnId_VAT = rs.getString("CHINHANH_PNH_FK") == null ? "" : rs.getString("CHINHANH_PNH_FK");
							this.tienteId = rs.getString("TIENTE_FK");
					
							this.tigia = rs.getString("TIGIA").replaceAll(",", "");
					
						}
						rs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					}

		// khoi tao them loai KHAC
		if (this.DoiTuongDinhKhoan != null && !(this.DoiTuongDinhKhoan.equals(""))) {
			createDoiTuongDinhKhoan();
		}

		if (this.DoiTuongChiPhiKhac.equals("3")) {
			this.count=0;
			if(this.id.length() >0){
				query=	" SELECT COUNT(*) as count"+
						" FROM  ERP_THANHTOANHOADON_PHINGANHANG BT "+
						" WHERE THANHTOANHD_FK = "+this.id;
				rs =this.db.get(query);
				if(rs!=null)
				{
					try
					{
						while(rs.next())
						{
							this.count=rs.getInt("count");
						}rs.close();
					} catch (SQLException e)
					{
						e.printStackTrace();
					}
				}
			}
			TaiKhoanIds=new String[count];
			dcIds = new String[count];
			ttcpIds = new String[count];
			KenhbhIds = new String[count];
			PhongBanIds = new String[count];
			TinhThanhIds = new String[count];
			SanPhamIds = new String[count];
			DiabanIds = new String[count];
			BenhvienIds = new String[count];
			Kyhieuhd = new String[count];
			MavvIds = new String[count];
			Sohd = new String[count];
			Ngayhd = new String[count];
			TenNCChd =  new String[count];
			MSThd = new String[count];
			Thuesuathd = new String[count];
			Tienthuehd = new String[count];
			Tienhanghd = new String[count];
			Diengiaihd = new String[count];
			loais = new String[count];
					
			for (int j = 0; j < count; j++){
				TaiKhoanIds[j] = "";
				dcIds[j] = "";
				ttcpIds[j] = "";
				KenhbhIds[j] = "";
				PhongBanIds[j] = "";
				TinhThanhIds[j] = "";
				DiabanIds[j] = "";
				BenhvienIds[j] = "";
				SanPhamIds[j] = "";
				MavvIds[j]= "";
				Kyhieuhd[j] = "";
				Sohd[j] = "";
				Ngayhd[j] = "";
				TenNCChd[j] = "";
				MSThd[j] = "";
				Thuesuathd[j] = "";
				Tienthuehd[j] = "";
				Tienhanghd[j] = "";
				Diengiaihd[j] = "";
				loais[j] = "";
			}
			
			query=	" SELECT BT.*, " +
					" CASE WHEN BT.SANPHAM_FK IS NOT NULL THEN 1 "+
					"      WHEN BT.NCC_FK IS NOT NULL THEN 2 "+
					"      WHEN BT.NGANHANG_FK IS NOT NULL THEN 3 "+
					"      WHEN BT.TAISAN_FK IS NOT NULL THEN 4 "+
					"      WHEN BT.KHACHHANG_FK IS NOT NULL THEN 5 "+
					"      WHEN BT.NHANVIEN_FK IS NOT NULL THEN 7 "+
					"      WHEN BT.TTCP_FK IS NOT NULL THEN 6 "+
					"      WHEN BT.DOITUONGKHAC_FK IS NOT NULL THEN 8 "+
					" 	   ELSE 0 END LOAI "+
					" FROM ERP_THANHTOANHOADON_PHINGANHANG BT "+
					" WHERE THANHTOANHD_FK = "+this.id;
			
			System.out.println(query);
			rs = db.get(query);
			
			if(rs != null)
			{
				try 
				{
					int i = 0 ;
										
					while(rs.next())
					{				
						loais[i] = rs.getString("LOAI");
						
						TaiKhoanIds[i]=rs.getString("TAIKHOAN_FK") == null?"":rs.getString("taikhoan_fk");
						
						if(rs.getString("SANPHAM_FK") != null){
							
							dcIds[i]=rs.getString("SANPHAM_FK");
						
						}else if(rs.getString("NGANHANG_FK") != null){
						
							dcIds[i]=rs.getString("NGANHANG_FK");
						
						}else if(rs.getString("NCC_FK") != null){ 
						
							dcIds[i]=rs.getString("NCC_FK");
						
						}else if(rs.getString("TAISAN_FK") != null){ 
						
							dcIds[i]=rs.getString("TAISAN_FK");
						
						}else if(rs.getString("KHACHHANG_FK") != null){
						
							dcIds[i]=rs.getString("KHACHHANG_FK")+"-"+rs.getString("ISNPP");
							
						}else if(rs.getString("NHANVIEN_FK") != null){
							
							dcIds[i]=rs.getString("NHANVIEN_FK");
							
						}else if(rs.getString("TTCP_FK") != null){
							
							dcIds[i]=rs.getString("TTCP_FK");
							
						}else if(rs.getString("DOITUONGKHAC_FK") != null){
							
							dcIds[i]=rs.getString("DOITUONGKHAC_FK");
						}

						this.ttcpIds[i] =  rs.getString("KHOANMUC_FK") == null?"":rs.getString("KHOANMUC_FK");							
						PhongBanIds[i] =  rs.getString("phongban_fk") == null?"":rs.getString("phongban_fk");						
						
						KenhbhIds[i] =  rs.getString("kbh_fk") == null?"":rs.getString("kbh_fk");
						
						TinhThanhIds[i] =  rs.getString("tinhthanh_fk") == null?"":rs.getString("tinhthanh_fk");
						
						SanPhamIds[i] =  rs.getString("sp_fk") == null?"":rs.getString("sp_fk");
						
						DiabanIds[i] = rs.getString("diaban_fk") == null?"":rs.getString("diaban_fk");
						
						BenhvienIds[i] = rs.getString("benhvien_fk") == null?"":rs.getString("benhvien_fk");
						
						MavvIds[i] = rs.getString("vuviec_fk") == null?"":rs.getString("vuviec_fk");
						
						Kyhieuhd[i] = rs.getString("kyhieu")== null?"":rs.getString("kyhieu");
						
						Sohd[i] = rs.getString("Sohoadon");
						
						Ngayhd[i] = rs.getString("Ngayhoadon");
												
						TenNCChd[i] = rs.getString("tenncc");
												
						MSThd[i] = rs.getString("mst");
						
						Thuesuathd[i] = rs.getString("thuesuat");
						
						Tienthuehd[i] = rs.getString("tienthue");
						
						Tienhanghd[i] = rs.getString("tienhang");	
						
						Diengiaihd[i] = rs.getString("diengiai")== null?"":rs.getString("diengiai");
					
						i++;
					
					}
					rs.close();
				}
					
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}

		String ktra = "	SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN \n"+
					  "	FROM ERP_NGANHANG_CONGTY NH_CTY \n"+
//					  " INNER JOIN NhanVien nt on nt.PK_SEQ=NH_CTY.NguoiTao  \n"+
//					  "	INNER JOIN NhanVien ns on ns.PK_SEQ=NH_CTY.NguoiSua  \n"+
					  "	INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK \n"+
					  "	INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK \n"+
					  "	INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK \n"+
					  "	WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "'  \n" +
			  			"and TaiKhoan_FK in (select PK_SEQ from ERP_TAIKHOANKT where npp_fk =1)";

		System.out.println("caus lenh lay tai khoan ngan hnag:\n" + ktra + "\n---------------------------------------------------");

		this.sotkRs = db.get(ktra);

		this.sotkRs_tp = db.get("SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN "+
								"FROM ERP_NGANHANG_CONGTY NH_CTY "+
								"INNER JOIN NhanVien nt on nt.PK_SEQ=NH_CTY.NguoiTao  "+
								"INNER JOIN NhanVien ns on ns.PK_SEQ=NH_CTY.NguoiSua  "+
								"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK "+
								"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK "+
								"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK "+
								"WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId +"' AND TT.PK_SEQ = 100000 ");

		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");

		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as TEN from erp_nhacungcap where trangthai = '1' and congty_fk ="+this.ctyId);
		query = "select pk_seq, ma + ', ' + ten as TEN from erp_nhacungcap where trangthai = '1' and congty_fk ="+this.ctyId;
		System.out.println("cau lenh lay ncc:\n" + query + "\n-------------------------------------------------------");
		this.NhanvienRs = db.get("SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHANVIEN WHERE TRANGTHAI=1 ");
		this.nhomNCCRs = db.get("SELECT PK_SEQ, DIENGIAI AS TEN FROM NHOMNHACUNGCAPCN where TRANGTHAI = 1");
		this.khachhangRs = db.get("select pk_seq, ma + ', ' + ten as TEN, '0' isNPP from KhachHang where trangthai = '1' AND CONGTY_FK ="+ this.ctyId + " "
						+ "union all select pk_seq, ma + ', ' + ten as TEN, '1' isNPP from NHAPHANPHOI where trangthai = '1' AND CONGTY_FK ="+ this.ctyId + "");

		
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' and pk_seq IN ( '100001' )");
		
		
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang where PK_SEQ in ( select NganHang_FK from ERP_NGANHANG_CONGTY where congty_fk = '"	+ this.ctyId + "' ) ");
		
		this.PhongBanRs = db.getScrol("select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1' ");
		
		this.KenhBhRs = db.getScrol("select pk_seq, diengiai ten from KENHBANHANG where trangthai = '1' ");
		
		
		String query1 = " SELECT PK_SEQ,SOHIEUTAIKHOAN as MA,TENTAIKHOAN AS TEN, ISNULL(COTTCHIPHI,0)COTTCHIPHI, " +
						" ISNULL(DUNGCHOKHO, 0) DUNGCHOKHO, ISNULL(DUNGCHONGANHANG, 0) DUNGCHONGANHANG, ISNULL(DUNGCHONCC, 0) DUNGCHONCC, ISNULL(DUNGCHOTAISAN, 0) DUNGCHOTAISAN, " +
						" ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG, ISNULL(DUNGCHONHANVIEN, 0) DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) DUNGCHODOITUONGKHAC " +
						" FROM ERP_TAIKHOANKT WHERE CONGTY_FK = "+this.ctyId;
		
		this.TaiKhoanKTRs=this.db.getScrol(query1);
		
		if(this.dungchos.trim().length() > 0) this.dungchos = this.dungchos.substring(0, this.dungchos.length()-1);
		

		if (!this.DoiTuongChiPhiKhac.equals("3")) // neu khac loai (KHAC)
		{
			if (this.htttId.length() > 0 && this.hoadonList.size() <= 0) {
				// LOAD NHỮNG HD CỦA NHÀ CUNG CẤP
				if (this.nccId.trim().length() > 0) {
					query = " select 0 as LOAIHD, b.pk_seq, isnull(b.sohoadon, '') as sohoadon,  isnull(b.kyhieu, '') as kyhieu, b.ngayhoadon,  b.sotienAVAT as sotiengoc, \n" +
					"         b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, SOTIENTT, \n" +
					 " ISNULL( (  \n" +
					"			Select distinct  \n" +
					"               ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  \n" +
					"				  From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
					"			      Where PO1.hoadonncc_fk = b.pk_seq \n" +
					"			      For XML PATH ('')) [phongbanChon_fk]  \n" +
					"			From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
					"			where PO.hoadonncc_fk = b.pk_seq \n" +
					"            ), '' )   as POID, c.TIENTE_FK AS TTID, c.TIGIA, b.NGAYDENHANTT, \n" +
					" 	 isnull((case when b.LOAIHD = 0 then (SELECT top(1) isnull(mh.SOPO,'') SOHOPDONG FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_HOADONNCC_DONMUAHANG hd_mh on nk.PK_SEQ = hd_mh.MUAHANG_FK inner join ERP_MUAHANG mh on nk.MUAHANG_FK = mh.PK_SEQ where hd_mh.HOADONNCC_FK = b.PK_SEQ and nk.TRANGTHAI = 1 ) " +
					"			 else ( SELECT mh.SOPO FROM ERP_MUAHANG mh inner join ERP_HOADONNCC_DONMUAHANG hd_mh on mh.PK_SEQ = hd_mh.MUAHANG_FK  where hd_mh.HOADONNCC_FK = b.PK_SEQ ) end),'') SOHOPDONG, \n"+
					"    isnull((case when b.LOAIHD = 0 then (SELECT top(1) isnull(mh.SOHOPDONG,'') SOHOPDONG FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_HOADONNCC_DONMUAHANG hd_mh on nk.PK_SEQ = hd_mh.MUAHANG_FK inner join ERP_MUAHANG mh on nk.MUAHANG_FK = mh.PK_SEQ where hd_mh.HOADONNCC_FK = b.PK_SEQ and nk.TRANGTHAI = 1 ) else '' end),'') SOHOPDONGNGOAI \n"+
					"from ERP_THANHTOANHOADON_HOADON a \n" +
					"inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq  and a.loaihd = 0  \n" +
					"inner join ERP_PARK c on b.park_fk = c.pk_seq \n" +
					"left join	\n" +
					"   ( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
					"     from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
					"     where TTHD.LOAIHD = 0 AND TT.trangthai !=2 and TTHD.thanhtoanhd_fk != '" + this.id + "' \n" +  //sua trang thai !=2 thanh =1
					"     group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
					"where a.thanhtoanhd_fk = '" + this.id + "' and c.TIENTE_FK = " + this.tienteId + " \n" +
					" UNION ALL \n";
			
							   
			// LOAIHD: 1 - TAMUNG(NCC)		 											
					
					query= query + "  \n" +
					" select 1 as LOAIHD, HOADON_FK AS PK_SEQ, CAST(HOADON_FK as nvarchar(50)) AS SOHOADON , N'TẠM ỨNG ' as   KYHIEU ," +
					" a.ngaytamung as ngayhoadon, " +
					" case when a.TIENTE_FK = 100000 then  SOTIENTAMUNG else a.SOTIENTAMUNGNT end as sotiengoc, \n " +
					" case when TU.TIENTE_FK = 100000 then SOTIENTAMUNG    \n " +
			        "   -( SELECT ISNULL(SUM(b.SOTIENTT),0)    \n " +
			        "      FROM ERP_THANHTOANHOADON_HOADON B  \n " +
			        "      INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ = B.THANHTOANHD_FK \n " +  
			        "      WHERE  A.NCC_FK = 100001 AND b.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK= TU.PK_SEQ \n " +            
			        "      ) -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN   \n " +
			     	"      FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n " + 
			     	"      WHERE TT.NCC_FK = 100001 AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0    \n " +
			    	"     )  \n " +
			    	     
			    	" ELSE  TU.SOTIENTAMUNGNT \n " +
			        "    -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n " +   
			        "      FROM ERP_THANHTOANHOADON_HOADON B  \n " +
			        "      INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK \n " +  
			        "      WHERE  A.NCC_FK = 100001 AND b.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK= 100120 --TU.PK_SEQ \n " +            
			        "      ) -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN   \n " +
			     	"      FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n " + 
			     	"      WHERE TT.NCC_FK = 100001 AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0    \n " +
			    	"     )  \n " +
			    	     
			    	" END AS sotienAVAT , \n " + 
		 
					" sotientt, '' as POID, a.TIENTE_FK as TTID ,"+this.tigia.replace(",", "")+" AS TIGIA , isnull(a.ngaydenhantt,'') as ngaydenhantt, " +
					" '' SOHOPDONG, '' SOHOPDONGNGOAI \n" +
					" from ERP_THANHTOANHOADON_HOADON b " +
					" inner join ERP_TAMUNG a on a.pk_seq=b.HOADON_FK  AND b.LOAIHD = 1 \n" +
					" where thanhtoanhd_fk="+this.id+" and b.LOAIHD = 1 \n" ;
					
		  // LOAIHD: 2 - CHIPHINOIBO						 

			query += " UNION ALL \n"; 
			query += " select 2 as LOAIHD, mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon ,  mhsp.SOTIENPO as sotiengoc, \n" +
					"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
					"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA , mh.ngaydenhantt, mh.SOPO SOHOPDONG, isnull(mh.SOHOPDONG, '') SOHOPDONGNGOAI  \n"+
					" from ERP_MUAHANG mh \n" +
					"       inner join \n" +
					"       (select a.PK_SEQ, SUM(a.TONGTIENAVAT)as SOTIENPO \n" +
					"		from  ERP_MUAHANG a \n" +
					"			  inner join ERP_NHACUNGCAP c on c.PK_SEQ = a.NHACUNGCAP_FK  \n" +
					"		where c.NOIBO = 1 and a.TIENTE_FK= "+ this.tienteId +"  and a.TRANGTHAI= 2 \n" +
					"       group by a.PK_SEQ )as mhsp on mh.PK_SEQ= mhsp.PK_SEQ  \n"+
					" 	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 2  \n"+
					" 	left join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ \n"+
					" 	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
					" where ncc.NOIBO = 1 and  mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "' \n";
									
			
		// LOAIHD: 3 - CHIPHINHANHANG

			query += " UNION ALL \n"; 
			query += "select distinct 3 as LOAIHD, cpct.pk_seq , cpct.SOCHUNGTU as sohoadon, cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon,  (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as sotiengoc \n" +
					" ,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA, cp.NGAYDENHANTT, isnull(mh.SOPO, '') SOHOPDONG, isnull(mh.SOHOPDONG, '') SOHOPDONGNGOAI \n" +
					" from ERP_CHIPHINHAPKHAU_CHITIET cpct \n" +
					" left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n" +
					" left join ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n" +
					" left join ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n" +
					" left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n" +
					" left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
					" where cp.NCCID_CN = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"'  \n";
									
			
		   if(this.tienteId.equals("100000"))
		   {
			// LOAIHD: 4 - THUENHAPKHAU						
		
				query += "UNION ALL \n"; 
				query +="select distinct 4 as LOAIHD, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'Thuế nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon, tnk.THUENK as sotiengoc  \n" +
					" 		,tt.sotienavat, tt.SOTIENTT as sotientt  ,'' as POID  \n" +
					" 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, tt.DIACHI \n" +
					"from ERP_THUENHAPKHAU tnk \n" +
					"		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
					"		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
//					" where tnk.NCC_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'Thuế nhập khẩu' \n" +
					" where tnk.TIENTE_FK = " + this.tienteId + " and tnk.COQUANTHUE_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'Thuế nhập khẩu' \n" +
					
					" union all \n" +
					
					"select distinct  4 as LOAIHD, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'VAT nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon, tnk.VAT as sotiengoc  \n" +
					" 		,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , '' as POID \n" +
					" 		,'100000' as ttId, '1' as TIGIA,  tnk.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, tt.DIACHI \n" +
					"from ERP_THUENHAPKHAU tnk \n" +
					"		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n" +
					"		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
//					" where tnk.NCC_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'VAT nhập khẩu' \n";
					" where tnk.TIENTE_FK = " + this.tienteId + " and tnk.COQUANTHUE_FK= '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and tt.LOAITHUE = N'VAT nhập khẩu' \n";
			 				
			
								
		   }
			// LOAIHD: 5 - CHIPHIKHAC

			 query +=" UNION ALL \n"; 
			 query +="select distinct 5 as LOAIHD, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as sotiengoc \n" +
					"      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n" +
					"      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, tt.DIACHI \n" +
					"from ERP_CHIPHIKHAC_CHITIET cpct \n" +
					"     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n" +
					"     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n" +
					"     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n" +
					"where cp.DOITUONG = '"+this.nccId+"' and tt.THANHTOANHD_FK = '"+this.id+"' and cp.LOAI= '0' \n";
		
				
		// LOAIHD: 6 - DENGHITHANHTOAN							 
			query += " UNION ALL \n"; 
			query += " select 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon ,  mh.TONGTIENAVAT as sotiengoc, \n" +
					"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
					"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, '' SOHOPDONG , '' SOHOPDONGNGOAI \n"+
					" from ERP_MUAHANG mh \n" +
					" 	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"+
					" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
					" 	inner join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
					" where   mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" +this.id+ "'  \n";

			// LOAIHD 	10 - BUTTOANTONGHOP
			
			query += " UNION ALL \n"; 
			query += "SELECT 10 AS LOAIHD, BTTH.PK_SEQ, TT.SOHOADON AS SOHOADON,  N'Bút toán tổng hợp'  AS KYHIEU , BTTH.NGAYBUTTOAN AS NGAYHOADON ,  \r\n" + 
					"BTTHCT.CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) AS SOTIENGOC,       \r\n" + 
					"CASE WHEN TTHD.TIENTE_FK= '100000' THEN TT.SOTIENAVAT ELSE TT.SOTIENNT END AS SOTIENAVAT,      \r\n" + 
					"TT.SOTIENTT , '' AS POID ,BTTH.TIENTE_FK AS TTID, BTTH.TIGIA AS TIGIA, NGAYBUTTOAN, '' SOHOPDONG , '' SOHOPDONGNGOAI      \r\n" + 
					"FROM ERP_BUTTOANTONGHOP BTTH         \r\n" + 
					"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK      \r\n" + 
					"INNER JOIN ERP_THANHTOANHOADON_HOADON TT ON TT.HOADON_FK = BTTH.PK_SEQ  AND TT.LOAIHD = 10      \r\n" + 
					"INNER JOIN ERP_THANHTOANHOADON TTHD ON TT.THANHTOANHD_FK = TTHD.PK_SEQ       \r\n" + 
					"WHERE ((BTTHCT.KHACHHANG_FK = " + this.nccId + " AND BTTHCT.DOITUONGDAUKY =N'Nhà cung cấp') OR BTTHCT.NCC_FK = " + this.nccId + ")\r\n" + 
					"AND TT.THANHTOANHD_FK = " +this.id;
		// END OF LOAIHD 	10 - BUTTOANTONGHOP	
		// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TT ĐÃ CHỐT)
			/*	query += " UNION ALL \n" ;
				query += " select 8 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , \n"+
						"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
						"       ISNULL((select SUM(a.SOTIENTT) as SOTIENTT  from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ where a.LOAIHD = 6 AND b.NCC_FK = '" + this.nccId + "' ),0)*(-1) as sotiengoc, "+
						"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
						"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
						" from  ERP_THANHTOANHOADON_HOADON tt  \n"+
						" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 8 \n"+
						" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
						" where   tthd.NCC_FK = '" + this.nccId + "' and tthd.PK_SEQ = "+ this.id +" \n";*/
			
			
		// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TẠM ỨNG ĐÃ CHỐT)

			/*query += " UNION ALL \n" ;
			query += " select 9 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị tạm ứng'  AS KYHIEU , \n"+
					"       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n" +
					"      ISNULL( (select SUM(a.SOTIENTT) as SOTIENTT  from ERP_THANHTOANHOADON_HOADON  a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ where a.LOAIHD = 1 and b.NCC_FK = '" + this.nccId + "' ),0)*(-1) as sotiengoc,  \n"+
					"       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"+
					"		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"+
					" from  ERP_THANHTOANHOADON_HOADON tt  \n"+
					" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 9 \n"+
					" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"+
					" where   tthd.NCC_FK = '" + this.nccId + "' and tthd.PK_SEQ = "+ this.id +" \n";*/
			
		}

				// LOAD NHỮNG HD CỦA NHÂN VIÊN
				if (this.NhanvienId.trim().length() > 0) {
					// LOAIHD: 1 - TAMUNG
					query = " select 1 AS LOAIHD, B.HOADON_FK AS PK_SEQ, cast(B.HOADON_FK as nvarchar(50)) AS SOHOADON , N'TẠM ỨNG' as KYHIEU, " +
							" a.ngaytamung as ngayhoadon, case when a.TIENTE_FK = 100000 then  a.SOTIENTAMUNG else a.SOTIENTAMUNGNT end as sotiengoc,   \n " +
							
							" case when a.TIENTE_FK = 100000 then   \n " +
							" a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n"+
							"                          FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n"+
							"                          WHERE  CT.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+ this.id + " )  " +
							" ELSE " +
							" a.SOTIENTAMUNGNT - ( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n"+
							"                      FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n"+
							"                      WHERE  CT.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <>"+ this.id + " )  " +
							" END AS sotienAVAT , \n" +
							
							" sotientt, '' as POID,  a.TIENTE_FK as TTID ,"+ this.tigia.replace(",", "")+ " AS TIGIA ,isnull(a.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI \n"+
							" from ERP_THANHTOANHOADON_HOADON b \n"+
							" inner join ERP_TAMUNG a on a.pk_seq = b.HOADON_fk  and b.loaihd = 1  \n"+
							" where   thanhtoanhd_fk=" + this.id + "  \n" ;


					// LOAIHD: 5 - CHIPHIKHAC

					query += " UNION ALL \n";
					query += "select distinct 5 as LOAIHD, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE) as sotiengoc \n"+
							 "      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n"+
							 "      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI \n"+
							 "from ERP_CHIPHIKHAC_CHITIET cpct \n"+
							 "     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n"+
							 "     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"+
							 "     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
							 "where cp.DOITUONG = '" + this.NhanvienId + "' and tt.THANHTOANHD_FK = '" + this.id+ "' and cp.LOAI= '1'  \n";

					// LOAIHD: 6 - DENGHITHANHTOAN
					query += " UNION ALL \n";
					query += " select 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENAVAT as sotiengoc, \n"+
							 "        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
							 "		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt,  '' SOHOPDONG, '' SOHOPDONGNGOAI  \n"+
							 " from ERP_MUAHANG mh \n"+
							 " 	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ and TT.LOAIHD = 6 \n"+
							 " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
							 " 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"+
							 " where   mh.NHANVIEN_FK = '" + this.NhanvienId + "' and tt.THANHTOANHD_FK = '" + this.id+ "' \n";

				}

				// LOAD NHỮNG HD CỦA KHÁCH HÀNG
				if (this.khachhangId.trim().length() > 0) {
					// LOAIHD: 7 - KHACHHANGTRATRUOC
					if(this.id.trim().length() > 0)
					{
						// LOAIHD: 7 - KHACHHANGTRATRUOC
					    query = "select distinct 7 as LOAIHD, tt.PK_SEQ, cast(tt.PK_SEQ as nvarchar(50)) as SOHOADON, N'Khách hàng trả trước' as KYHIEU, tt.NGAYCHUNGTU AS NGAYHOADON  , \n" +
					    		"       (select  CASE WHEN tt.TIENTE_FK <> '100000'  THEN ISNULL(tt.THUDUOCNT,0) ELSE ISNULL(tt.THUDUOC,0) END   from ERP_THUTIEN a where TRANGTHAI = '1' and NOIDUNGTT_FK = '100001' and KHACHHANG_FK = '"+ this.khachhangId +"' and a.pk_seq = tt.pk_seq ) AS SOTIENGOC,  \n"+
					    		"       CASE WHEN tt.TIENTE_FK <> '100000' THEN ttct.SOTIENNT ELSE ttct.SOTIENAVAT END AS SOTIENAVAT , \n" +
					    		"       ttct.SOTIENTT, '' AS POID, tt.TIENTE_FK AS TTID, ISNULL(tt.TIGIA,1) as TIGIA, '' as NGAYDENHANTT, '' SOHOPDONG, '' SOHOPDONGNGOAI  \n" +
					    		"from ERP_THUTIEN tt  inner join ERP_THANHTOANHOADON_HOADON ttct on ttct.HOADON_FK = tt.PK_SEQ AND ttct.LOAIHD = 7 \n"+
					    		"where tt.KHACHHANG_FK = '"+ this.khachhangId +"' and ttct.THANHTOANHD_FK = "+ this.id +" \n";

						// LOAIHD: 6 - DENGHITHANHTOAN	
						  
						query += " UNION ALL \n"; 
						
						query += " select distinct 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENAVAT as sotiengoc, \n" +
								 "        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
								 "		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI \n"+
								 " from ERP_MUAHANG mh \n" +
								 " 	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"+
								 " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
								 " 	inner join KHACHHANG kh on kh.PK_SEQ = mh.KHACHHANG_FK \n"+
								 " where   mh.KHACHHANG_FK = '" + this.khachhangId + "' and tt.THANHTOANHD_FK = '" +this.id+ "'  \n";		
						query += " UNION ALL \n"; 
						
						query += 
							"	select distinct 8 as LOAIHD, hd.PK_SEQ, hd.SOHOADON as sohoadon,  N'Hóa đơn hàng trả về'  AS KYHIEU , hd.NGAYXUATHD as ngayhoadon , \n"+
							"	 HD.TONGTIENAVAT as sotiengoc,  tt.SOTIENAVAT SOTIENAVAT, \n"+
							"	 tt.SOTIENTT , '' as POID , isnull(hd.TIENTE_FK, 100000) as ttId, isnull(hd.TYGIA,1) as TIGIA, '' ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI \n"+
							" from ERP_HOADON hd \n"+
							"	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = hd.PK_SEQ  AND TT.LOAIHD = 8 \n"+
							"	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
							"	inner join KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
							
							"  LEFT JOIN ( \n"+
							"  SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n"+
							"  FROM  \n"+
							"  (  \n"+ 
							" 		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+  
							" 		FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
							" 		INNER JOIN ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n"+
							" 		WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and TTHD.KHACHHANG_FK = "+this.khachhangId+" and isnull(TT.isNPP,0) = 0 \n"+ 
							" 		group by HOADON_FK  \n"+
			
							" 		UNION ALL    \n"+
			
							" 		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n"+
							" 		FROM ERP_THUTIEN_HOADON TTHD \n"+
							" 		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n"+
							" 		WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = 0 AND TT.BANGKE_FK IS NULL  \n"+
							" 		group by HOADON_FK  \n"+
			
							" 		UNION ALL  \n"+
			
							" 		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n"+
							" 		FROM ERP_THUTIEN_HOADON TTHD \n"+
							" 		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ \n"+ 
							" 		WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = 0 AND TT.BANGKE_FK IS NOT NULL \n"+
							" 		group by HOADON_FK  \n"+
			
							" 		UNION ALL  \n"+
							 
							" 		select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN \n"+   
							" 		from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ  \n"+ 
							" 		where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and isnull(TT.isNPP,0) = 0 \n"+
							" 		group by HOADON_FK \n"+
									
							" 		UNION ALL  \n"+
									
							" 		select ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
							" 	    from ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ  \n"+  
							" 	    where   tthd.TRANGTHAI != '2' \n"+            
							" 	    and  ct.LOAIHD = '8' AND TTHD.PK_SEQ != "+ (this.id.length() <= 0 ? 0 : this.id) +
							" 	    group by ct.HOADON_FK \n"+		       
			
							" 	)HOADONDATT  group by HOADON_FK  \n"+								
							"  )dathanhtoan on hd.pk_seq = dathanhtoan.hoadon_fk    \n"+
													 
							" where   hd.KHACHHANG_FK = '"+this.khachhangId+"' and tt.THANHTOANHD_FK = '"+this.id+"' \n";
						
					}
					
					if(this.DoiTuongChiPhiKhac.equals("5"))
					{
						query = "";
						if(this.khachhangId.trim().length() >0 )
						{
							if(this.id.trim().length()>0)
							{
								query = // TÍCH LŨY CẦN DUYỆT
									" SELECT distinct 8 as LOAIHD,PK_SEQ , SCHEME sohoadon, SCHEME AS KYHIEU, ngaykyhd ngayhoadon, giatritra as sotiengoc, \n"+
									" 		(giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) as SOTIENAVAT , isnull(TICHLUY.DATT,0) as sotientt, '' as POID, \n"+
									"        100000 as ttId, 1 as TIGIA, ngayketthuchd ngaydenhantt, sohopdong SOHOPDONG, '' SOHOPDONGNGOAI \n"+
									" FROM \n"+ 
									" ( \n"+
									"	SELECT DKKMTICHLUY_FK PK_SEQ, c.SCHEME , doanhsoDAT, donvitra, sanphamtra, soluongtra, giatritra, a.ngaykyhd, a.ngayketthuchd, \n"+
									"		   a.sohopdong, \n"+
									"		   ISNULL( ( SELECT SUM(TONGGIATRI)  FROM ERP_DONDATHANGNPP_TICHLUY_TRATL \n"+
									"				  	 WHERE DKKMID = a.DKKMTICHLUY_FK \n"+
									"				  	 AND DONDATHANGID in ( SELECT PK_SEQ FROM ERP_DONDATHANGNPP  \n"+
									"										   WHERE KHACHHANG_FK = '"+this.khachhangId+"' and trangthai != 3 ) ) , 0) as tonggiatri \n"+
									"	FROM DANGKYKM_TICHLUY_KHACHHANG a \n"+
									"		INNER JOIN DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ \n"+
									"		INNER join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ \n"+		
									"	WHERE KHACHHANG_FK = '"+this.khachhangId+"' and DUYET = '1' and MucDat is not null \n"+
									"		and c.TRANGTHAI = '1' and c.khongcanduyettra = '0'  \n"+
									"		and donvitra != 2 \n"+
									" ) \n"+
									" DATA \n"+
									" LEFT JOIN \n"+
									" ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									"   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									"   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									"   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '8' and tthd.PK_SEQ != "+this.id +" \n"+
									"   GROUP BY ct.HOADON_FK \n"+
									" )DATHANHTOAN on  DATA.PK_SEQ = DATHANHTOAN.HOADON_FK \n"+	
									" INNER JOIN \n"+
									" ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									"   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									"   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									"   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '8' and tthd.PK_SEQ = "+this.id +" \n"+
									"   GROUP BY ct.HOADON_FK \n"+
									" )TICHLUY on  DATA.PK_SEQ = TICHLUY.HOADON_FK \n";							
									//" where ( giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) > 0  \n";
								
							// TÍCH LŨY KHÔNG CẦN DUYỆT
							query += " UNION ALL \n"+
							 		 " SELECT distinct 9 as LOAIHD, PK_SEQ,  SCHEME SOHOADON, DATA.SCHEME AS KYHIEU , DATA.ngaykyhd ngayhoadon, giatritra as sotiengoc, \n"+
							 		 "		  (giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) as SOTIENAVAT, TICHLUY.DATT as sotientt, '' as POID, \n"+
							 		 " 		  100000 as ttId, 1 as TIGIA, ngayketthuchd ngaydenhantt, sohopdong SOHOPDONG, '' SOHOPDONGNGOAI \n"+
							 		 " FROM \n"+  
							 		 "	(  \n"+
									 "	SELECT 	DT.DKKMTICHLUY_FK PK_SEQ, DT.doanhsoDAT, tc.donvi as donvitra, DT.sohopdong, DT.ngaykyhd, DT.ngayketthuchd, DT.SCHEME, \n"+
									 "			( case tc.donvi when 0 then round( tc.chietkhau * DT.doanhsoDAT / 100.0, 0 ) when 1 then tc.chietkhau else 0 end ) as giatritra, \n"+
									 "			ISNULL( ( select SUM(TONGGIATRI) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = DT.DKKMTICHLUY_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '"+this.khachhangId+"' and trangthai != 3 ) ) , 0) as tonggiatri \n"+ 
									 "	FROM \n"+
									 "	( \n"+
									 "		SELECT	DKKMTICHLUY_FK, c.PK_SEQ,  \n"+
									 "				ISNULL ( ( \n"+
									 "				select SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as tonggiatri \n"+
									 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  \n"+
									 "				where dh.KHACHHANG_FK = a.KHACHHANG_FK and dh.CS_DUYET = '1' and dh.TRANGTHAI not in ( 0, 3 ) and dh.NgayDonHang >= c.ngayds_tungay and dh.NgayDonHang <= c.ngayds_denngay \n"+
									 "		 				and dh_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = c.PK_SEQ ) \n"+
									 "				), 0 ) doanhsoDAT , a.sohopdong,a.ngaykyhd, a.ngayketthuchd , c.SCHEME \n"+
									 "		from DANGKYKM_TICHLUY_KHACHHANG a inner join DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ \n"+
									 "		inner join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ \n"+
									 "		where KHACHHANG_FK = '"+this.khachhangId+"' and c.TRANGTHAI = '1' and c.khongcanduyettra = '1' and b.daduyet = '1' and b.TRANGTHAI = '1'  \n"+ 
									 "	) \n"+
									 "	DT inner join TIEUCHITHUONGTL_TIEUCHI tc on DT.PK_SEQ = tc.thuongtl_fk \n"+
									 "	where tc.tumuc <= DT.doanhsoDAT and DT.doanhsoDAT <= tc.denmuc and tc.donvi != 2 \n"+
									 " )  \n"+
									 " DATA  \n"+
									 " LEFT JOIN \n"+
									 " ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									 "   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									 "   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									 "   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '9' and tthd.PK_SEQ != "+this.id +" \n"+
									 "   GROUP BY ct.HOADON_FK \n"+
									 " )DATHANHTOAN on  DATA.PK_SEQ = DATHANHTOAN.HOADON_FK \n"+	
									 " INNER JOIN \n"+
									 " ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									 "   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									 "   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									 "   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '9' and tthd.PK_SEQ = "+this.id +" \n"+
									 "   GROUP BY ct.HOADON_FK \n"+
									 " )TICHLUY on  DATA.PK_SEQ = TICHLUY.HOADON_FK \n";							
									 //" where ( giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) > 0  \n";
									
								
							}
						}
					}

				}

				if (this.bophanId.trim().length() > 0) {
					// LOAIHD: 6 - DENGHITHANHTOAN (NV)

					query = " select  '0' as DOITUONG ,nv.PK_SEQ as DOITUONG_FK, nv.MA as MADOITUONG , 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENAVAT as sotiengoc,  \n"+
							"        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
							"		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI   \n"+
							" from ERP_MUAHANG mh \n"+
							" 	inner join ERP_THANHTOANHOADON_HOADONBOPHAN tt on tt.HOADON_FK = mh.PK_SEQ and TT.LOAIHD = 6 \n"+
							" 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
							" 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"+
							" where   tt.NHANVIEN_FK is not null and tt.THANHTOANHD_FK = '" + this.id + "' \n";

				}

				System.out.println("Query khoi tao hoa don 111112: " + query);

				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if (rsHoadon != null) {
					try {
						IHoadon hd = null;
						while (rsHoadon.next()) {
							String doituong = "";
							String doituongId = "";
							String madoituong = "";

							String id = rsHoadon.getString("pk_seq");
							String kyhieu = rsHoadon.getString("kyhieu");
							String sohoadon = rsHoadon.getString("sohoadon");
							String ngayhd = rsHoadon.getString("ngayhoadon");
							String ngaydenhantt = rsHoadon.getString("ngaydenhantt");
							String tienteId = rsHoadon.getString("TTID");
							String sotiengoc = "";
							String avat = "";
							String loaihd = rsHoadon.getString("LOAIHD");
							String sohopdong = rsHoadon.getString("sohopdong");
							String sohopdongngoai = rsHoadon.getString("sohopdongngoai");
							String diaChi = rsHoadon.getString("DIACHI"); 

							String sotienNT = "0";
							String dathanhtoan = "0";
							String tigia = "1";
							if (!this.DoiTuongChiPhiKhac.equals("3")) // neu khac loai (KHAC)
							{
								avat = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");

								sotiengoc = ("" + rsHoadon.getDouble("sotiengoc")).replaceAll(",", "");
	
								sotienNT = ("" + rsHoadon.getDouble("sotiengoc")).replaceAll(",", "");
								dathanhtoan = ("" + rsHoadon.getDouble("SOTIENTT")).replaceAll(",", "");
								tigia = rsHoadon.getString("TIGIA").replaceAll(",", "");
							}else{
								if (tienteId.equals("100000")) {
									avat = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");
								} else {
									avat = ("" + rsHoadon.getDouble("sotienAVAT") * rsHoadon.getDouble("TIGIA")).replaceAll(",", "");
								}
	
								if (tienteId.equals("100000")) {
									sotiengoc = ("" + rsHoadon.getDouble("sotiengoc")).replaceAll(",", "");
								} else {
									sotiengoc = ("" + rsHoadon.getDouble("sotiengoc") * rsHoadon.getDouble("TIGIA")).replaceAll(",", "");
								}
	
								sotienNT = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");
								dathanhtoan = ("" + rsHoadon.getDouble("SOTIENTT")).replaceAll(",", "");
								tigia = rsHoadon.getString("TIGIA").replaceAll(",", "");
								
							}
							if (this.bophanId.trim().length() > 0) {
								doituong = rsHoadon.getString("DOITUONG");
								doituongId = rsHoadon.getString("DOITUONG_FK");
								madoituong = rsHoadon.getString("MADOITUONG");
							}

							hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, sotienNT, dathanhtoan, tienteId, diaChi);
							hd.setSoTienGoc2(sotiengoc);
							hd.setSoPO(rsHoadon.getString("POID"));
							hd.setTigia(tigia);
							hd.setNgaydenhanTT(ngaydenhantt);
							hd.setLoaihd1(loaihd);
							hd.setSohopdong(sohopdong);
							hd.setSohopdongNGOAI(sohopdongngoai);

							if (this.bophanId.trim().length() > 0) {
								hd.setDoituong(doituong);
								hd.setDoituongId(doituongId);
								hd.setMadoituong(madoituong);
							}

							hdList.add(hd);
						}
						rsHoadon.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				this.hoadonList = hdList;
				System.out.println("So hoa don: " + this.hoadonList.size());
			}

		}

	}

	public void initPdf() {
		String query = " select a.pk_seq, a.ngayghinhan, \n"+
					   "   case when a.NCC_FK IS not null then ncc.TEN when a.NHANVIEN_FK IS not null then nv.TEN	else '' end AS nguoinhan, \n"+
					   " 	case when a.NCC_FK IS not null then ncc.diachi else '' end AS diachi,  \n"+
					   "   a.httt_fk, a.nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt, \n"+
					   "	a.ghichu, a.loaithanhtoan, a.sotienHD, a.sotienHDNT, a.phinganhang, a.phinganhangNT, \n"+
					   "   a.vat, a.vatNT, a.sotienttNT, a.chenhlechVND, trichphi, sotaikhoan_tp, nganhang_tp_fk, chinhanh_tp_fk, \n"+
					   "   PNH.mahoadon, PNH.mauhoadon, PNH.KYHIEU, PNH.SOHOADON, PNH.NGAYHOADON, PNH.TENNCC, PNH.MST, PNH.TIENHANG, PNH.THUESUAT, \n"+
					   "   PNH.TIENTHUE, PNH.NGANHANG_FK AS NGANHANG_PNH_FK, PNH.CHINHANH_FK AS CHINHANH_PNH_FK \n"+
					   " from ERP_THANHTOANHOADON a \n"+
					   " left join ERP_THANHTOANHOADON_PHINGANHANG PNH on PNH.THANHTOANHD_FK = a.PK_SEQ \n"+
					   " left join ERP_NHACUNGCAP ncc on a.NCC_FK = ncc.PK_SEQ \n"+
					   " left join ERP_NHANVIEN nv on a.NHANVIEN_FK = nv.PK_SEQ \n" + 
					   " where a.pk_seq = '" + this.id + "'";
		
		System.out.println("[ErpThanhtoanhoadon.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("nguoinhan");
					this.diachi = rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");
					if (this.htttId.equals("100001") ||this.htttId.equals("100003") ) {
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.noidungtt = rs.getString("noidungtt");
					this.sotienttNT = rs.getString("sotienttNT").replaceAll(",", "");
					this.sotientt = rs.getString("sotientt").replaceAll(",", "");
					this.LoaiThanhToan = rs.getString("loaithanhtoan");
					this.sotienHD = rs.getString("sotienHD").replaceAll(",", "");
					this.sotienHDNT = rs.getString("sotienHDNT").replaceAll(",", "");
					this.pnganhang = rs.getString("phinganhang").replaceAll(",", "");
					this.pnganhangNT = rs.getString("phinganhangNT").replaceAll(",", "");
					this.thueVAT = rs.getString("vat").replaceAll(",", "");
					this.thueVATNT = rs.getString("vatNT").replaceAll(",", "");
					this.chenhlechVND = rs.getString("chenhlechVND").replaceAll(",", "");
					this.trichphi = rs.getString("trichphi");
					this.sotaikhoan_tp = rs.getString("sotaikhoan_tp");
					this.nganhang_tpId = rs.getString("nganhang_tp_fk");
					this.chinhanh_tpId = rs.getString("chinhanh_tp_fk");
					this.mahoadon = rs.getString("mahoadon");
					this.mauhoadon = rs.getString("mauhoadon");
					this.kyhieu = rs.getString("KYHIEU");
					this.sohoadon = rs.getString("SOHOADON");
					this.ngayhoadon = rs.getString("NGAYHOADON");
					this.tenNCC = rs.getString("TENNCC");
					this.mst = rs.getString("MST");
					this.tienhang = rs.getString("TIENHANG").replaceAll(",", "");
					this.thuesuat = rs.getString("THUESUAT").replaceAll(",", "");
					this.tienthue = rs.getString("TIENTHUE").replaceAll(",", "");
					this.nhId_VAT = rs.getString("NGANHANG_PNH_FK");
					this.cnId_VAT = rs.getString("CHINHANH_PNH_FK");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		// khoi tao hoa don
		query = "select sohoadon from erp_thanhtoanhoadon_hoadon a inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq where thanhtoanhd_fk = '"
				+ this.id + "'";
		String sohoadon = "";
		ResultSet hoadonRs = db.get(query);
		if (hoadonRs != null) {
			try {
				while (hoadonRs.next()) {
					sohoadon += hoadonRs.getString("sohoadon") + ", ";
				}
				hoadonRs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (sohoadon.length() > 0)
			this.noidungtt += " --- " + sohoadon.substring(0, sohoadon.length() - 2);

	}

	public void initUnc() {
		NumberFormat formatter = new DecimalFormat("#,###,###");
		String query = "select a.pk_seq, a.ngayghinhan, b.ten as nccTen, b.diachi, a.httt_fk, c.ten as nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt \n"+
					   "from ERP_THANHTOANHOADON a inner join ERP_NHACUNGCAP b on a.ncc_fk = b.pk_seq left join erp_nganhang c on a.nganhang_fk = c.pk_seq \n"+
					   " where a.pk_seq = '" + this.id + "'";
		System.out.println("Khoi tao Unc: " + query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.ngayghinhan = rs.getString("ngayghinhan");
					this.nccId = rs.getString("nccTen") + " --- " + rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");
					if (this.htttId.equals("100001")||this.htttId.equals("100003")) {
						this.nganhangId = rs.getString("nganhang_fk");
						this.chinhanhId = rs.getString("chinhanh_fk");
						this.sotaikhoan = rs.getString("sotaikhoan");
					}
					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	public void createRs() {
		
		initDoiTuongKhacList();
		initLoaiThanhToanList();
		initNppList();
		
		this.sotkRs = db.get("SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN "+
							 "FROM ERP_NGANHANG_CONGTY NH_CTY "+
//							 "INNER JOIN NhanVien nt on nt.PK_SEQ=NH_CTY.NguoiTao  "+
//							 "INNER JOIN NhanVien ns on ns.PK_SEQ=NH_CTY.NguoiSua "+
							 "INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK "+
							 "INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK "+
							 "INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK "+
							 "WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "'  and NH_CTY.NPP_FK = 1");
		System.out.println("caus lenh lay tai khoan ngan hnag1:\n" + "SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN "+
				 "FROM ERP_NGANHANG_CONGTY NH_CTY "+
//				 "INNER JOIN NhanVien nt on nt.PK_SEQ=NH_CTY.NguoiTao  "+
//				 "INNER JOIN NhanVien ns on ns.PK_SEQ=NH_CTY.NguoiSua "+
				 "INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK "+
				 "INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK "+
				 "INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK "+
				 "WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "'  and NH_CTY.NPP_FK = 1" + "\n---------------------------------------------------");

		this.sotkRs_tp = db.get("SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN "+
								"FROM ERP_NGANHANG_CONGTY NH_CTY "+
								"INNER JOIN NhanVien nt on nt.PK_SEQ=NH_CTY.NguoiTao  "+
								"INNER JOIN NhanVien ns on ns.PK_SEQ=NH_CTY.NguoiSua "+
								"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK "+
								"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK "+
								"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK "+
								"WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "' AND TT.PK_SEQ = 100000 ");

		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");

		this.nccRs = db.get("select pk_seq, ma + ', ' + ten as TEN from erp_nhacungcap where trangthai = '1' and CONGTY_FK = "+ this.ctyId + " ");

		this.NhanvienRs = db.get("SELECT PK_SEQ ,MA+','+TEN AS TEN   FROM ERP_NHANVIEN WHERE TRANGTHAI=1 and CONGTY_FK  = "+ this.ctyId + " ");
		this.nhomNCCRs = db.get("SELECT PK_SEQ, DIENGIAI AS TEN FROM NHOMNHACUNGCAPCN where TRANGTHAI = 1 and CONGTY_FK  = "+ this.ctyId + "");

		this.khachhangRs = db.get("select pk_seq, ma + ', ' + ten as TEN, '0' isNPP from KhachHang where trangthai = '1' AND CONGTY_FK ="+ this.ctyId + " "
								+ "union all select pk_seq, ma + ', ' + ten as TEN, '1' isNPP from NHAPHANPHOI where trangthai = '1' AND CONGTY_FK ="+ this.ctyId + "");

		
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' and pk_seq IN ( '100001' )");
		
		this.nganhangRs = db.get("select pk_seq, ma + ', ' + ten as nhTen from erp_nganhang ");

		
		this.PhongBanRs = db.getScrol("select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1' ");
		
		this.KenhBhRs = db.getScrol("select pk_seq, diengiai ten from KENHBANHANG where trangthai = '1' ");
		
		
		String query1 = " SELECT PK_SEQ,SOHIEUTAIKHOAN as MA,TENTAIKHOAN AS TEN, ISNULL(COTTCHIPHI,0)COTTCHIPHI, " +
						" ISNULL(DUNGCHOKHO, 0) DUNGCHOKHO, ISNULL(DUNGCHONGANHANG, 0) DUNGCHONGANHANG, ISNULL(DUNGCHONCC, 0) DUNGCHONCC, ISNULL(DUNGCHOTAISAN, 0) DUNGCHOTAISAN, " +
						" ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG, ISNULL(DUNGCHONHANVIEN, 0) DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) DUNGCHODOITUONGKHAC " +
						" FROM ERP_TAIKHOANKT WHERE CONGTY_FK ="+this.ctyId;
		
		this.TaiKhoanKTRs=this.db.getScrol(query1);
		
		if(this.dungchos.trim().length() > 0) this.dungchos = this.dungchos.substring(0, this.dungchos.length()-1);
		
		System.out.println("count2414:"+this.count);
		
		if (!this.DoiTuongChiPhiKhac.equals("3")) // neu khac loai (KHAC)
		{
			if (this.htttId.length() > 0 && this.hoadonList.size() <= 0) {
//				NumberFormat formatter = new DecimalFormat("#,###,###");

				String query = "";
				// LOAD NHỮNG HD CỦA NHÀ CUNG CẤP
				if (this.nccId.trim().length() > 0) {
					String diaChiGoc = "";
					try {
						query = "SELECT ISNULL(DIACHI, '') DIACHI FROM ERP_NHACUNGCAP WHERE PK_SEQ = "+this.nccId+"\n";
						ResultSet rs = db.get(query);
						if (rs != null)
						{
							diaChiGoc = rs.next() ? rs.getString("DIACHI") : "";
							rs.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					query = "";
					//Kiểm tra có phải nhà cung cấp quản lý thuế - Nếu là nhà cung cấp quản lý thuế thì load đúng thuế nhập khẩu
					String sqlThueNK = "SELECT ISNULL(LOAINCC,0) AS LOAINCC FROM ERP_NHACUNGCAP WHERE PK_SEQ = " +this.nccId;
					String loaiNCC = "" ;
					ResultSet rsThueNK = db.get(sqlThueNK);
					if(rsThueNK != null){
						try {
							while(rsThueNK.next()){
								loaiNCC = rsThueNK.getString("LOAINCC");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if (loaiNCC.equals("100011")){
						//CHIA LÀM 2 LOẠI THUẾ 
						//1 - THUẾ NHẬP KHẨU 
						//2 - THUẾ VAT
						
						//THUẾ NHẬP KHẨU
						query += "	SELECT LOAIHD,LOAITHUE,PK_SEQ ,SOHOADON,KYHIEU,NGAYHOADON, SOTIENGOC, SOTIENAVAT,  \n " + 
								"   SOTIENTT,POID, 100000 AS TTID,1 AS TIGIA,NGAYDENHANTT,SOHOPDONG,SOHOPDONGNGOAI, N'' as DIACHI  \n " + 
								"   FROM (  \n " ; 
//								"   --Có Id \n " ;
						if(id.length() > 0 ){
							//Loại 1 :Thuế nhập khẩu 
							query+= "   SELECT DISTINCT 4 AS LOAIHD,1 AS LOAITHUE, TNK.PK_SEQ,TNK.SOCHUNGTU AS SOHOADON,N'Thuế nhập khẩu' AS KYHIEU,   \n " + 
								"   NGAYCHUNGTU AS NGAYHOADON,TNK.THUENK AS SOTIENGOC,TNK.THUENK - ISNULL(DATT.THANHTOAN,0) AS SOTIENAVAT,   \n " + 
								"   HD.SOTIENTT AS SOTIENTT,'' AS POID,   \n " + 
								"   TNK.TIENTE_FK AS TTID ,TNK.TIGIA,   \n " + 
								"   '' AS NGAYDENHANTT , '' AS SOHOPDONG, '' AS SOHOPDONGNGOAI, N'' as DIACHI  \n " + 
								"   FROM ERP_THUENHAPKHAU TNK  \n " + 
//								"   INNER JOIN ERP_THANHTOANHOADON_HOADON HD ON TNK.PK_SEQ = HD.HOADON_FK AND HD.LOAIHD = 4 AND HD.LOAITHUENK = 1 \n " + 
								"   LEFT JOIN   \n " + 
								"   (   \n " + 
								"   SELECT HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK,SUM(HD.SOTIENTT) AS THANHTOAN   \n " + 
								"   FROM ERP_THANHTOANHOADON_HOADON HD   \n " + 
								"   INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = HD.THANHTOANHD_FK   \n " + 
								"   WHERE LOAIHD = 4 AND LOAITHUENK = '1' AND TTHD.TRANGTHAI != 2 AND HD.THANHTOANHD_FK < '"+this.id+"' \n " + 
								"   GROUP BY HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK   \n " + 
								"   )DATT ON DATT.HOADON_FK = TNK.PK_SEQ  \n " + 
								"   WHERE COQUANTHUE_FK = '"+this.nccId+"' AND HD.THANHTOANHD_FK = '"+this.id+"' \n " + 
								//Loại 2 Thuế VAT
								"   UNION ALL \n " + 
								"     SELECT DISTINCT 4 AS LOAIHD,2 AS LOAITHUE, TNK.PK_SEQ,TNK.SOCHUNGTU AS SOHOADON,N'VAT nhập khẩu' AS KYHIEU,   \n " + 
								"   NGAYCHUNGTU AS NGAYHOADON,TNK.VAT AS SOTIENGOC,TNK.VAT - ISNULL(DATT.THANHTOAN,0) AS SOTIENAVAT,   \n " + 
								"   HD.SOTIENTT AS SOTIENTT,'' AS POID,   \n " + 
								"   TNK.TIENTE_FK AS TTID ,TNK.TIGIA,   \n " + 
								"   '' AS NGAYDENHANTT , '' AS SOHOPDONG, '' AS SOHOPDONGNGOAI, N'' as DIACHI  \n " + 
								"   FROM ERP_THUENHAPKHAU TNK  \n " + 
//								"   INNER JOIN ERP_THANHTOANHOADON_HOADON HD ON TNK.PK_SEQ = HD.HOADON_FK AND HD.LOAIHD = 4 AND HD.LOAITHUENK = 2 \n " + 
								"   LEFT JOIN   \n " + 
								"   (   \n " + 
								"   SELECT HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK,SUM(HD.SOTIENTT) AS THANHTOAN   \n " + 
								"   FROM ERP_THANHTOANHOADON_HOADON HD   \n " + 
								"   INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = HD.THANHTOANHD_FK   \n " + 
								"   WHERE LOAIHD = 4 AND LOAITHUENK = '2' AND TTHD.TRANGTHAI != 2 AND HD.THANHTOANHD_FK < '"+this.id+"' \n " + 
								"   GROUP BY HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK   \n " + 
								"   )DATT ON DATT.HOADON_FK = TNK.PK_SEQ  \n " + 
								"   WHERE COQUANTHUE_FK = '"+this.nccId+"' AND HD.THANHTOANHD_FK = '"+this.id+"' \n " +
								"   UNION ALL  \n " ;
						}
							query+=	"   --Không có ID \n " + 
								"   SELECT DISTINCT 4 AS LOAIHD,1 AS LOAITHUE, TNK.PK_SEQ, TNK.SOCHUNGTU AS SOHOADON, N'Thuế nhập khẩu' AS KYHIEU,  \n " + 
								"   NGAYCHUNGTU AS NGAYHOADON, TNK.THUENK AS SOTIENGOC,TNK.THUENK - ISNULL(DATT.THANHTOAN,0) AS SOTIENAVAT ,  \n " + 
								"   0 AS SOTIENTT, '' AS POID,  \n " + 
								"   TNK.TIENTE_FK AS TTID, TNK.TIGIA,  \n " + 
								"   '' AS NGAYDENHANTT, '' AS SOHOPDONG , '' AS SOHOPDONGNGOAI, N'' as DIACHI   \n " + 
								"   FROM ERP_THUENHAPKHAU TNK  \n " + 
//								"   INNER JOIN ERP_THANHTOANHOADON_HOADON HD ON TNK.PK_SEQ = HD.HOADON_FK AND HD.LOAIHD = 4 AND HD.LOAITHUENK = 1 \n " + 
								"   LEFT JOIN   \n " + 
								"   (  \n " + 
								"   SELECT HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK,SUM(HD.SOTIENTT) AS THANHTOAN   \n " + 
								"   FROM ERP_THANHTOANHOADON_HOADON HD   \n " + 
								"   INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = HD.THANHTOANHD_FK   \n " + 
								"   WHERE LOAIHD = 4 AND LOAITHUENK = '1' AND TTHD.TRANGTHAI != 2 \n " + 
								"   GROUP BY HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK   \n " + 
								"   )DATT ON DATT.HOADON_FK = TNK.PK_SEQ   \n " + 
								"   WHERE TNK.THUENK - ISNULL(DATT.THANHTOAN,0) >0 \n " + 
								"   AND COQUANTHUE_FK = '"+this.nccId+"'  and ngaychungtu >='2017-01-01' \n";
							if(id.length() >0)
								query += " AND TNK.PK_SEQ NOT IN"+
										"   (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD =4 AND HD.LOAITHUENK =1) \n"+
										" AND HD.THANHTOANHD_FK != '"+this.id+"' \n " ; 
							query+="    \n " + 
								"   UNION ALL \n " + 
								"   SELECT DISTINCT 4 AS LOAIHD,2 AS LOAITHUE, TNK.PK_SEQ, TNK.SOCHUNGTU AS SOHOADON, N'VAT nhập khẩu' AS KYHIEU,  \n " + 
								"   NGAYCHUNGTU AS NGAYHOADON, TNK.VAT AS SOTIENGOC,TNK.VAT - ISNULL(DATT.THANHTOAN,0) AS SOTIENAVAT ,  \n " + 
								"   0 AS SOTIENTT, '' AS POID,  \n " + 
								"   TNK.TIENTE_FK AS TTID, TNK.TIGIA,  \n " + 
								"   '' AS NGAYDENHANTT, '' AS SOHOPDONG , '' AS SOHOPDONGNGOAI, N'' as DIACHI   \n " + 
								"   FROM ERP_THUENHAPKHAU TNK  \n " + 
//								"   INNER JOIN ERP_THANHTOANHOADON_HOADON HD ON TNK.PK_SEQ = HD.HOADON_FK AND HD.LOAIHD = 4 AND HD.LOAITHUENK = 2 \n " + 
								"   LEFT JOIN   \n " + 
								"   (  \n " + 
								"   SELECT HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK,SUM(HD.SOTIENTT) AS THANHTOAN   \n " + 
								"   FROM ERP_THANHTOANHOADON_HOADON HD   \n " + 
								"   INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = HD.THANHTOANHD_FK   \n " + 
								"   WHERE LOAIHD = 4 AND LOAITHUENK = '2' AND TTHD.TRANGTHAI != 2  \n " + 
								"   GROUP BY HD.HOADON_FK,HD.LOAIHD,HD.TIENTE_FK   \n " + 
								"   )DATT ON DATT.HOADON_FK = TNK.PK_SEQ   \n " + 
								"   WHERE TNK.TRANGTHAI != '3' AND TNK.VAT - ISNULL(DATT.THANHTOAN,0) >0 \n " + 
								"   AND COQUANTHUE_FK = '"+this.nccId+"'\n";
							if(id.length() >0)
								query += " AND TNK.PK_SEQ NOT IN"+
										"   (SELECT HOADON_FK FROM ERP_THANHTOANHOADON_HOADON WHERE LOAIHD =4 AND HD.LOAITHUENK =2) \n"+
										" AND HD.THANHTOANHD_FK != '"+this.id+"' \n " ;
							query+="     \n " + 
								"   )TNK WHERE SOTIENAVAT > 0 OR SOTIENTT > 0 \n " + 
								"   ORDER BY LOAIHD,PK_SEQ,LOAITHUE ";
					
					}else{
						
					// LOAIHD: 0 - HOADONNCC
					if (this.id.length() > 0) {
						query +=" SELECT distinct 0 as LOAIHD,'' AS LOAITHUE, b.pk_seq, isnull(b.sohoadon, '') as sohoadon,  isnull(b.kyhieu, '') as kyhieu, b.ngayhoadon, \n" +
								"         b.sotienAVAT as sotiengoc, b.sotienAVAT - ISNULL(thanh_toan.tongthanhtoan,0) as sotienAVAT, SOTIENTT, \n" +
								" ISNULL( (  \n" +
								"			Select distinct  \n" +
								"               ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  \n" +
								"				  From ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
								"			      Where PO1.hoadonncc_fk = b.pk_seq \n" +
								"			      For XML PATH ('')) [phongbanChon_fk]  \n" +
								"			From ERP_HOADONNCC_PHIEUNHAP PO  \n" +
								"			where PO.hoadonncc_fk = b.pk_seq \n" +
								"            ), '' )   as POID, c.TIENTE_FK AS TTID, c.TIGIA, b.NGAYDENHANTT, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(DIACHI, '') AS DIACHI  \n"+
								"FROM ERP_THANHTOANHOADON_HOADON a \n" +
								"     inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq  and a.loaihd = 0  \n" +
								"	  inner join ERP_PARK c on b.park_fk = c.pk_seq \n" +
								"     left join	\n" +
								"   ( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan \n" +
								"     from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
								"     where TT.NCC_FK is not null AND TT.CONGTY_FK = "+this.ctyId+"  and TTHD.LOAIHD = 0 AND TT.trangthai !=2 and TTHD.thanhtoanhd_fk != '" + this.id + "' \n" +  //sua trang thai !=2 thanh =1
								"           and TTHD.hoadon_fk in ( select hoadon_fk \n" +
						        "									from ERP_THANHTOANHOADON_HOADON \n" +
								"									where thanhtoanhd_fk = '" + this.id + "' and loaihd = 0 ) \n" +
								"     group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk \n" +
								"WHERE a.thanhtoanhd_fk = '" + this.id + "' and c.TIENTE_FK = " + this.tienteId + " and b.CONGTY_FK = "+this.ctyId+" \n" +
								
								" UNION ALL \n";
				}

					query +="(SELECT distinct 0 as LOAIHD,'' AS LOAITHUE, hoadon.pk_seq, isnull(hoadon.sohoadon, '') as sohoadon, isnull(hoadon.kyhieu, '') as kyhieu, hoadon.ngayhoadon, \n" +
							"         hoadon.sotienAVAT as sotiengoc, hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0')  as sotienAVAT, 0 as sotientt ,\n " +
							" ISNULL( (  " +
							"		SELECT distinct ( SELECT cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()] \n" +
							"						  FROM ERP_HOADONNCC_PHIEUNHAP PO1   \n" +
							"	                      WHERE PO1.hoadonncc_fk = hoadon.pk_seq \n" +
							"                         FOR XML PATH ('')) [phongbanChon_fk]  \n" +
							"       FROM ERP_HOADONNCC_PHIEUNHAP PO  \n" +
							"       WHERE PO.hoadonncc_fk = hoadon.pk_seq \n" +
							"          ), '' )   as POID, hoadon.ttId, hoadon.tigia,  hoadon.ngaydenhantt, isnull(hoadon.SOHOPDONG,'') SOHOPDONG, isnull(hoadon.SOHOPDONGNGOAI, '') SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI   \n" +
							" FROM ( \n" +
							"       SELECT a.pk_seq, a.kyhieu, a.sohoadon, a.ngayhoadon, b.ngayGhiNhan, a.sotienAVAT, b.TIENTE_FK as ttId, b.TIGIA, a.sotienavat_vnd, isnull(a.ngaydenhantt,'') as  ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI \n"+

							"       FROM ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq \n" +
							"                            left join ERP_HOADONNCC_PHIEUNHAP hd_pn on a.pk_seq = hd_pn.hoadonncc_fk \n"+
							"							 left join ERP_YEUCAUKIEMDINH yckd on hd_pn.phieunhan_fk = yckd.NHANHANG_FK \n"+
							"       WHERE  a.CONGTY_FK = "+this.ctyId+" and b.ncc_fk = '" + this.nccId + "' and b.trangthai = '2' and a.trangthai = '0' \n"+
							
							"			  and ( yckd.PK_SEQ is null OR ( yckd.PK_SEQ is not null and ISNULL(yckd.THIEUHOSO,0) <> 1 ) )  \n";
							if(this.id.length() > 0)
							{
								query += " and a.pk_seq not in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '" + this.id + "') \n";
							}
						query += " ) hoadon \n" +
							"      left join " +
							"   ( \n" +
							"     SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
							"     from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
							"     where TT.NCC_FK is not null AND TTHD.LOAIHD = 0 AND TT.TRANGTHAI !=2 and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0)  \n" + //sua trang thai !=2 thanh =1
							"     group by HOADON_FK \n" +
							
						    "     UNION ALL \n" +
						    
						    "	  SELECT TTHD.HOADONNCC_FK, sum(TTHD.TienThanhToan) as tongthanhtoan \n " +
						    "     FROM ERP_XOANONCC_HOADONNCC TTHD inner join ERP_XOANONCC TT on TTHD.XOANONCC_FK = TT.PK_SEQ \n " +
						    "     WHERE TT.trangthai != '2' and TT.NCC_FK = '" + this.nccId + "' \n " +
						   " 	  and TTHD.LOAICT = 0 \n" + "     GROUP BY TTHD.HOADONNCC_FK \n " +
						    
							"    ) dathanhtoan  on hoadon.pk_seq = dathanhtoan.hoadon_fk \n" +
							"      left join " +
							"    ( \n" +
							"     select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n" +
							"     from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n" +
							"     where TTHD.LOAIHD = 0 AND TT.TRANGTHAI !=2 and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0)  \n" + //sua trang thai !=2 thanh =1
							"     group by HOADON_FK \n" +
							"    ) dathanhtoanNCC  on hoadon.pk_seq = dathanhtoanNCC.hoadon_fk \n" +
							"where hoadon.ngayghinhan >'2017-01-01' and hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0')  > 0 and hoadon.ttId = " + this.tienteId + " ) \n" ;

					// LOAIHD: 1 - TAMUNG(NCC)
					if (this.id.length() > 0) {
						query += "UNION ALL \n";

						query += "SELECT distinct 1 as LOAIHD,'' AS LOAITHUE, TU.PK_SEQ , CAST(TU.PK_SEQ as nvarchar(50)) as sohoadon ,N'TẠM ỨNG'  AS KYHIEU, " +
								 " TU.NGAYTAMUNG AS ngayhoadon, " +
								 " case when TU.TIENTE_FK = 100000 then  TU.SOTIENTAMUNG else TU.SOTIENTAMUNGNT end as sotiengoc, " +
								
								 " CASE WHEN TU.TIENTE_FK = 100000 then \n " +
								 " SOTIENTAMUNG \n"+
								 "             -(SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "               FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "               WHERE A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 				 AND B.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id +" \n"+
								 "  			 AND B.HOADON_FK=TU.PK_SEQ \n" + 
								 "			     AND A.CONGTY_FK = "+ this.ctyId + ") " +
								 "			  -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	        FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	        WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 				and TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0   \n"+
								 "				AND TT.CONGTY_FK = " + this.ctyId +" \n"+
								 "    	       )  " +
								 "ELSE " +
								 " TU.SOTIENTAMUNGNT \n"+
								 "             -(SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "               FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "               WHERE A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 				 AND B.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id +" \n"+
								 "  			 AND B.HOADON_FK=TU.PK_SEQ \n" + 
								 "			     AND A.CONGTY_FK = "+ this.ctyId + ") " +
								 "			  -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	        FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	        WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 				and TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0   \n " +
								 "				AND TT.CONGTY_FK = " + this.ctyId + " \n " +
								 "    	       )  " +

								 "END AS sotienAVAT , \n"+
								 "             0 AS SOTIENTT, '' as POID, TU.TIENTE_FK as TTID, "+ this.tigia.replace(",", "") + " AS TIGIA, \n"+
								 "             isnull(TU.ngaydenhantt,'') as ngaydenhantt , '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL((SELECT ISNULL(DIACHI, '') DIACHI FROM ERP_THANHTOANHOADON_HOADON B WHERE thanhtoanhd_fk =" + this.id + " AND B.HOADON_FK=TU.PK_SEQ ), '') DIACHI \n"+
								 "      FROM ERP_TAMUNG TU \n"+
								 "      WHERE  TU.CONGTY_FK = " + this.ctyId + " AND TU.TRANGTHAI !=2 \n"+
								 "  	AND  ISNULL(TU.HOANTAT,'0')='0' and TU.TIENTE_FK = (select tiente_fk from ERP_THANHTOANHOADON where pk_seq="+ this.id + " ) \n"+
								 "            and TU.pk_seq not in  \n"+
								 "           (select HOADON_FK  from ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_HOADON b on a.pk_seq=b.thanhtoanhd_fk \n"+
								 "     		  where b.LOAIHD = 1 AND  b.thanhtoanhd_fk =" + this.id + "  )  \n"+
								 "      AND ( " +
								 "			(TU.TIENTE_FK = 100000 AND TU.SOTIENTAMUNG \n"+
								 "              -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "                 FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "                 WHERE A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					AND b.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id +" \n"+
								 "  				AND b.HOADON_FK = TU.PK_SEQ \n"+
								 "				  	AND A.CONGTY_FK = "+ this.ctyId + ") " +
								 "				- (" +
								 "					SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	        	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	        	WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0 and TT.CONGTY_FK = " + this.ctyId + " \n"+
								 "    	        	)  \n"+
								 "              >0 ) \n"+
								 " 			OR " +
								 "			(TU.TIENTE_FK <> 100000 AND TU.SOTIENTAMUNGNT \n"+
								 "              -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "                 FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "                 WHERE A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					AND b.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id +" \n"+
								 "  				AND b.HOADON_FK = TU.PK_SEQ \n"+
								 "				  	AND A.CONGTY_FK = "+ this.ctyId + ") " +
								 "				- (" +
								 "					SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	        	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	        	WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0 and TT.CONGTY_FK = " + this.ctyId + " \n"+
								 "    	        	)  \n"+
								 "           >0 ) \n"+
								 "		) " +
								 "     and tu.NCC_FK="+ (this.nccId == "" ? "0" : this.nccId);

						query += " UNION ALL \n"+
								 " select distinct 1 as LOAIHD,'' AS LOAITHUE, HOADON_FK AS PK_SEQ, CAST(HOADON_FK as nvarchar(50)) AS SOHOADON , N'TẠM ỨNG' as KYHIEU," +
								 " a.ngaytamung as ngayhoadon, " +
								 " case when a.TIENTE_FK = 100000 then  a.SOTIENTAMUNG else a.SOTIENTAMUNGNT end as sotiengoc, \n"+
								 " case when a.TIENTE_FK = 100000 then " +
								 " a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n"+
								 "                   FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n"+
								 "                   WHERE  A.CONGTY_FK = " + this.ctyId + " AND  A.NCC_FK = "+ (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					 AND ct.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK = B.HOADON_FK and a.pk_seq <>"	+ this.id + " )  \n"+
								 "                -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	      		FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	     		WHERE TT.CONGTY_FK = " + this.ctyId +" \n"+
								 " 					AND  TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					AND TTHD.LOAIHD = 1   AND TT.TRANGTHAI = 0 )  \n"+

								 " ELSE \n " +
								 " a.SOTIENTAMUNGNT -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n"+
								 "                     FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n"+
								 "                     WHERE  A.CONGTY_FK = " + this.ctyId + " AND  A.NCC_FK = "+ (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					   AND ct.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK = B.HOADON_FK and a.pk_seq <>"	+ this.id + " )  \n"+
								 
								 "                -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	      		FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	     		WHERE TT.CONGTY_FK = " + this.ctyId +" \n"+
								 " 					AND  TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) + " \n"+
								 " 					AND TTHD.LOAIHD = 1   AND TT.TRANGTHAI = 0 )  \n"+
								 
								 " END AS sotienAVAT, \n"+
								 
								 " sotientt, '' as POID, a.TIENTE_FK as TTID ,b.tigia \n"+
								 " AS TIGIA , isnull(a.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, b.DIACHI DIACHI \n"+
								 " from ERP_THANHTOANHOADON_HOADON b \n " +
								 " inner join ERP_TAMUNG a on a.pk_seq=b.HOADON_FK  AND b.LOAIHD = 1 \n"+
								 " where thanhtoanhd_fk = " + this.id + " and b.LOAIHD = 1 \n";

					} else {
						query += " UNION ALL \n";
						query += " SELECT distinct 1 as LOAIHD,'' AS LOAITHUE, TU.PK_SEQ , CAST(TU.PK_SEQ as nvarchar(50)) as sohoadon ,N'TẠM ỨNG'  AS KYHIEU, " +
								 " TU.NGAYTAMUNG AS ngayhoadon, " +
								 " case when TU.TIENTE_FK = 100000 then  TU.SOTIENTAMUNG else TU.SOTIENTAMUNGNT end as sotiengoc, " +
								 " case when TU.TIENTE_FK = 100000 then " +
								 " TU.SOTIENTAMUNG   \n"+
								 "           -( SELECT ISNULL(SUM(b.SOTIENTT),0)   \n"+
								 "              FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "              WHERE  A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 				AND b.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ  "+
								 "            ) " +
								 "			-(	SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	      	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	      	WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 				AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0   \n" + 
								 "    	     )  " +
								 " ELSE " +
								 " TU.SOTIENTAMUNGNT   \n"+
								 "           -( SELECT ISNULL(SUM(b.SOTIENTT),0)   \n"+
								 "              FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "              WHERE  A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 				AND b.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ  "+
								 "            ) " +
								 "			-(	SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	      	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	      	WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 				AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0   \n" + 
								 "    	     )  " +

								 " END AS sotienAVAT , \n"+
								 " 0 AS SOTIENTT, '' as POID, TU.TIENTE_FK as TTID, "+this.tigia+" \n"+
								 " AS TIGIA, isnull(TU.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI  \n"+
								 " FROM ERP_TAMUNG TU \n" + " WHERE TU.CONGTY_FK= " + this.ctyId +
								 " AND TU.TIENTE_FK = '" + this.tienteId + "'  and ISNULL(TU.HOANTAT,'0')='0' " +
								 " AND ( " +
								 "		(TU.TIENTE_FK = 100000 AND " +
								 "		 TU.SOTIENTAMUNG   \n" +
								 "     -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "        FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "        WHERE A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 		  AND b.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ " +
								 "		) \n"+
								 "     -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 		AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0  \n" + "    	) \n"+
								 "   	>0 ) " +
								 
								 "   	OR " +
								 "		(TU.TIENTE_FK <> 100000 AND " +
								 "		 TU.SOTIENTAMUNGNT   \n" +
								 "     -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "        FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "        WHERE A.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 		  AND b.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ " +
								 "		) \n"+
								 "     -(SELECT ISNULL(sum(TTHD.SOTIENTT),0) as DATHANHTOAN  \n"+
								 "     	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "     	WHERE TT.NCC_FK = " + (this.nccId == "" ? "0" : this.nccId) +
								 " 		AND TTHD.LOAIHD = 1 AND TT.TRANGTHAI = 0  \n" + "    	) \n"+
								 "   	>0 ) " +

								 " ) " +
								 "	and TU.TRANGTHAI = 1 \n" + " and   NCC_FK="+ (this.nccId == "" ? "0" : this.nccId) +" and TU.NGAYTAMUNG>='2017-01-01' ";

					}

					// LOAIHD: 2 - CHIPHINOIBO
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += " select distinct 2 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , \n"+
								 " mhsp.SOTIENPO as sotiengoc, " +
								 " CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
								 " tt.SOTIENTT , '' as POID, mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA , mh.ngaydenhantt, mh.SOPO SOHOPDONG, isnull(mh.SOHOPDONG,'') SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI  \n"+
								 " from ERP_MUAHANG mh \n" + 
								 "       inner join \n"+
								 "       (select a.PK_SEQ, SUM(a.TONGTIENAVAT)as SOTIENPO \n"+
								 "		from  ERP_MUAHANG a \n"+
								 "			  inner join ERP_NHACUNGCAP c on c.PK_SEQ = a.NHACUNGCAP_FK  \n"+
								 "		where c.NOIBO = 1 and a.TIENTE_FK= " + this.tienteId + "  and a.TRANGTHAI= 2 \n"+
								 "       group by a.PK_SEQ )as mhsp on mh.PK_SEQ= mhsp.PK_SEQ  \n"+
								 " 	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 2  \n"+
								 " 	left join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ \n"+
								 " 	left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
								 " where ncc.NOIBO = 1 and  mh.NHACUNGCAP_FK = '" + this.nccId +
								 "' and tt.THANHTOANHD_FK = '" + this.id + "' \n";

					}
					query += " UNION ALL \n";
					query += " select distinct 2 as LOAIHD,'' AS LOAITHUE,  mh.PK_SEQ, cast(mh.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi phí nội bộ'  AS KYHIEU , mh.NGAYMUA as ngayhoadon \n"+
							 " 		,mhsp.SOTIENPO as sotiengoc , mhsp.SOTIENPO - isnull(t.SOTIENTT,0) - isnull(dathanhtoanNCC.DATHANHTOAN,0) as SOTIENAVAT ,0 as sotientt, '' as POID  \n"+
							 " 		,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, mh.SOPO SOHOPDONG, isnull(mh.SOHOPDONG, '' ) SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI  \n"+
							 " from ERP_MUAHANG mh \n" + "       inner join \n"+
							 "       (select a.PK_SEQ, SUM(a.TONGTIENAVAT)as SOTIENPO \n"+
							 "		from  ERP_MUAHANG a \n"+
							 "			  inner join ERP_NHACUNGCAP c on c.PK_SEQ = a.NHACUNGCAP_FK  \n"+
							 "		where c.NOIBO = 1 and a.TIENTE_FK= " + this.tienteId +
							 "  and a.TRANGTHAI= 2 and a.CONGTY_FK = " + this.ctyId +
							 "       group by a.PK_SEQ )as mhsp on mh.PK_SEQ = mhsp.PK_SEQ  \n"+
							 " 		left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 2 \n"+
							 " 		left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"+
							 "      	left join " + 
							 "   	( \n"+
							 "       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+
							 "       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
							 "       where TTHD.LOAIHD = 2 AND TT.TRANGTHAI = 0 and TT.CONGTY_FK = " + this.ctyId +
							 "       group by HOADON_FK \n"+
							 "       ) dathanhtoanNCC  on mh.PK_SEQ = dathanhtoanNCC.hoadon_fk \n"+
							 " 		left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							 " where  mh.CONGTY_FK = " + this.ctyId + 
							 " and ncc.NOIBO = 1 and mh.NHACUNGCAP_FK = '"+ this.nccId + "' \n" + 
							 " 		and ( tt.CONLAI is null or (tt.CONLAI > 0 \n"+
							 "			and mh.TIENTE_FK = " + this.tienteId + " \n"+
							 " 			and tt.HOADON_FK not in \n"+
							 "				(select distinct tt.HOADON_FK \n" + 
							 "				from ERP_MUAHANG mh \n"+
							 "				left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 2 \n"+
							 "				left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							 "				where mh.CONGTY_FK = " + this.ctyId + " AND mh.NHACUNGCAP_FK = '"+ this.nccId + "' and tt.CONLAI = 0 and mh.TIENTE_FK = " + this.tienteId + ") \n"+
							 "  			and tt.THANHTOANHD_FK in \n"+
							 "				(select MAX(tt.THANHTOANHD_FK) \n" + 
							 "				from Erp_MuaHang mh  \n"+
							 "				left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ   AND TT.LOAIHD = 2 \n"+
							 " 				left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
							 "				left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							 "				where ncc.NOIBO = 1 and mh.TRANGTHAI =2 and mh.TIENTE_FK = " + this.tienteId +
							 "  and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	\n"+
							 "	and mh.CONGTY_FK =" + this.ctyId + " and  mh.NGAYMUA>='2017-01-01' "+
							 "	group by tt.HOADON_FK ) ) ) \n" + 
							 " 		and mh.TRANGTHAI = 2 \n";
					
					if (this.id.length() > 0) {
						query += " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + ") \n";
					}
					
					query += "       and  mhsp.SOTIENPO - isnull(t.SOTIENTT,0) - isnull(dathanhtoanNCC.DATHANHTOAN,0) > 0 \n";

					// LOAIHD: 3 - CHIPHINHANHANG
					if (this.id.length() > 0) {
						query += " UNION ALL \n";

						query += "select distinct 3 as LOAIHD,'' AS LOAITHUE, cpct.pk_seq , cpct.SOCHUNGTU as sohoadon, cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon, (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as sotiengoc \n"+
								 " ,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA, cp.NGAYDENHANTT, isnull(mh.SOPO, '') SOHOPDONG, isnull(mh.SOHOPDONG, '') SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI \n"+
								 " from ERP_CHIPHINHAPKHAU_CHITIET cpct \n"+
								 " left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n"+
								 " left join ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n"+
								 " left join ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n"+
								 " left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n"+
								 " left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
								 " where cp.NCCID_CN = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id + "'  \n";

					}

					query += " UNION ALL \n";

					query += "select distinct 3 as LOAIHD,'' AS LOAITHUE, cpct.pk_seq ,cpct.SOCHUNGTU as sohoadon,  cpct.KYHIEUCHUNGTU as kyhieu , cpct.NGAYCHUNGTU as ngayhoadon, (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100))) as sotiengoc  \n"+
							 ",case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then (cpct.TIENHANG + (cpct.TIENHANG*(cpct.THUESUAT/100)) - isnull(dathanhtoanNCC.DATHANHTOAN,0) ) else tt.CONLAI end as sotienavat \n"+
							 " ,'0' as sotientt, cast(mh.PK_SEQ as nvarchar(50)) as POID, isnull(cp.TIENTE_FK,'100000') as ttId,  isnull(cp.TIGIA,1) as TIGIA , cp.NGAYDENHANTT, isnull(mh.SOPO, '') SOHOPDONG, isnull(mh.SOHOPDONG, '') SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI  \n"+
							 "from ERP_CHIPHINHAPKHAU_CHITIET cpct \n"+
							 "left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n"+
							 "left join ERP_NHANHANG nh on nh.PK_SEQ = cp.nhanhang_fk \n"+
							 "left join ERP_MUAHANG mh on mh.PK_SEQ = nh.MUAHANG_FK  \n"+
							 "left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n"+
							 "left join " + 
							 "   	( \n"+
							 "       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+
							 "       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
							 "       where TTHD.LOAIHD = 3 AND TT.TRANGTHAI != 2 and TT.CONGTY_FK = " + this.ctyId +
							 "       group by HOADON_FK \n"+
							 "       ) dathanhtoanNCC  on cpct.PK_SEQ = dathanhtoanNCC.hoadon_fk \n"+
							 "left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
							 "where cpct.ngaychungtu >'2017-01-01' and isnull(cp.TIENTE_FK,'100000') = " + this.tienteId + " and cp.NCCID_CN = '"+ this.nccId + "' and ( tt.CONLAI is null or (tt.CONLAI > 0  \n"+
							 "					 and tt.HOADON_FK not in  \n"+
							 "						(select distinct tt.HOADON_FK  \n"+
							 "						from ERP_CHIPHINHAPKHAU_CHITIET cpct  \n"+
							 "							left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n"+
							 "							left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ  AND TT.LOAIHD = 3 \n"+
							 "						where CP.CONGTY_FK =" + this.ctyId + "  AND cp.NCCID_CN = '"+ this.nccId + "' and tt.CONLAI = 0 and cp.TIENTE_FK= " + this.tienteId + ") \n"+
							 "					  and tt.THANHTOANHD_FK in \n"+
							 "						(select MAX(tt.THANHTOANHD_FK)  \n"+
							 "						from ERP_CHIPHINHAPKHAU_CHITIET cpct    \n"+
							 "							left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.PK_SEQ AND TT.LOAIHD = 3 \n"+
							 "					 		left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"+
							 "							left join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = cpct.CHIPHINHAPKHAU_FK \n"+
							 "						 where cp.CONGTY_FK = " + this.ctyId + " AND cp.trangthai = 1 and cp.TIENTE_FK= " + this.tienteId + "  and  cp.NCCID_CN =  '"+ this.nccId + "' and t.TRANGTHAI<>2 	 \n"+
							 "						 group by tt.HOADON_FK ) ) ) \n"+
							 "					 and cp.trangthai = 1  \n";
					if (this.id.length() > 0) {
						query += " and cpct.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "+ this.id + " ) \n";
					}

					if (this.tienteId.equals("100000")) {
						// LOAIHD: 4 - THUENHAPKHAU
						if (this.id.length() > 0) {
							query += "UNION ALL \n";
							query += "select distinct 4 as LOAIHD,'' AS LOAITHUE, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'Thuế nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon, tnk.THUENK as sotiengoc \n"+
									 " 		,tt.sotienavat, tt.SOTIENTT as sotientt  ,'' as POID  \n"+
									 " 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI  \n"+
									 "from ERP_THUENHAPKHAU tnk \n"+
									 "		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n"+
									 "		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
//									 " where tnk.NCC_FK= '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id + "' and tt.LOAITHUE = N'Thuế nhập khẩu' \n" +
									 " where tnk.TIENTE_FK = " + this.tienteId + " and tnk.COQUANTHUE_FK= '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id + "' and tt.LOAITHUE = N'Thuế nhập khẩu' \n" +
									 " union all \n" +

									 "select distinct  4 as LOAIHD,'' AS LOAITHUE, tnk.pk_seq , tnk.SOCHUNGTU as sohoadon, N'VAT nhập khẩu' as kyhieu , tnk.NGAYCHUNGTU as ngayhoadon, tnk.VAT as sotiengoc \n"+
									 " 		,tt.sotienavat as sotienavat, tt.SOTIENTT as sotientt  , '' as POID \n"+
									 " 		,'100000' as ttId, '1' as TIGIA,  tnk.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI , tt.DIACHI \n"+
									 "from ERP_THUENHAPKHAU tnk \n"+
									 "		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n"+
									 "		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
//									 " where tnk.NCC_FK= '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id +"' and tt.LOAITHUE = N'VAT nhập khẩu' \n";
									 " where tnk.TIENTE_FK = " + this.tienteId + " and tnk.COQUANTHUE_FK= '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id +"' and tt.LOAITHUE = N'VAT nhập khẩu' \n";
						}
						query += " UNION ALL \n";
						query += " select distinct  4 as LOAIHD,'' AS LOAITHUE, tnk.pk_seq, tnk.SOCHUNGTU as sohoadon, N'Thuế nhập khẩu' as kyhieu ,  tnk.NGAYCHUNGTU as ngayhoadon, tnk.THUENK as sotiengoc \n"+
								 "		,tnk.THUENK - isnull(tt.sotientt,0)- isnull(dathanhtoanNCC.DATHANHTOAN,0) as sotienavat, '0' as sotientt, '' as POID \n"+
								 " 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' \n"+
								 " from ERP_THUENHAPKHAU tnk \n"+
								 "		left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ and tt.LOAITHUE =  N'Thuế nhập khẩu' AND TT.LOAIHD = 4 \n"+
								 "		left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
								 "       left join " + 
								 "   	( \n"+
								 "       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+
								 "       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "       where TTHD.LOAIHD = 4 AND tthd.LOAITHUE =  N'Thuế nhập khẩu' AND TT.TRANGTHAI = 0   \n"+
								 "			  and TT.CONGTY_FK =" + this.ctyId + 
								 "       group by HOADON_FK \n"+
								 "       ) dathanhtoanNCC  on tnk.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n"+
//								 " where tnk.NCC_FK = '" + this.nccId+ "' and ( tt.CONLAI is null or (tt.CONLAI > 0 \n"+
								 " where tnk.TIENTE_FK = " + this.tienteId + " and tnk.COQUANTHUE_FK = '" + this.nccId+ "' and tnk.NGAYCHUNGTU>='2017-01-01' and ( tt.CONLAI is null or (tt.CONLAI > 0 \n"+								 
								 "					 and tt.HOADON_FK not in \n"+
								 "						(select distinct tt.HOADON_FK \n"+
								 "						from ERP_THUENHAPKHAU tnk \n"+
								 "						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n"+
//								 "						where tnk.NCC_FK = '" + this.nccId+ "' and tt.CONLAI = 0 and tnk.CONGTY_FK = " + this.ctyId +
								 "						where tnk.COQUANTHUE_FK = '" + this.nccId+ "' and tt.CONLAI = 0 and tnk.CONGTY_FK = " + this.ctyId +								 
								 "					  and tt.THANHTOANHD_FK in \n"+
								 "						(select MAX(tt.THANHTOANHD_FK) \n"+
								 "						from ERP_THUENHAPKHAU tnk  \n"+
								 "						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n"+
								 "						left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
//								 "						 where tnk.trangthai = 2    and tnk.NCC_FK = '" + this.nccId + "' and t.TRANGTHAI<>2 and tnk.CONGTY_FK = " + this.ctyId +
								 "						 where tnk.trangthai = 2    and tnk.COQUANTHUE_FK = '" + this.nccId + "' and t.TRANGTHAI<>2 and tnk.CONGTY_FK = " + this.ctyId +								 
								 "						 group by tt.HOADON_FK ) ) )\n"+
								 "					 and tnk.trangthai in (1,2)  and tnk.THUENK > 0 ) \n";
						
						if (this.id.length() > 0) {
							query += " and tnk.PK_SEQ not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK= "
									+ this.id + " AND LOAIHD = 4 and LOAITHUE= N'Thuế nhập khẩu') \n";
						}

						query += " UNION ALL \n"+
								 "select distinct 4 AS LOAIHD,'' AS LOAITHUE, tnk.pk_seq ,tnk.SOCHUNGTU as sohoadon, N'VAT nhập khẩu' as kyhieu ,  tnk.NGAYCHUNGTU as ngayhoadon, tnk.VAT as sotiengoc \n"+
								 "		,tnk.VAT - isnull(tt.sotientt,0)- isnull(dathanhtoanNCC.DATHANHTOAN,0) as sotienavat, '0' as sotientt, '' as POID \n"+
								 " 		,'100000' as ttId, '1' as TIGIA, tnk.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI \n"+
								 "from ERP_THUENHAPKHAU tnk \n"+
								 "left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ and tt.LOAITHUE =  N'VAT nhập khẩu' AND TT.LOAIHD = 4 \n"+
								 "left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
								 "       left join " + 
								 "   	( \n"+
								 "       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+
								 "       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
								 "       where TTHD.LOAIHD = 4 AND tthd.LOAITHUE =  N'VAT nhập khẩu' AND TT.TRANGTHAI = 0  and TT.CONGTY_FK = "	+ this.ctyId + 
								 "       group by HOADON_FK \n"+
								 "       ) dathanhtoanNCC  on tnk.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n"+
//								 "where tnk.CONGTY_FK = " + this.ctyId + " AND tnk.NCC_FK = '" + this.nccId +
								 "where tnk.TIENTE_FK = " + this.tienteId + " and tnk.CONGTY_FK = " + this.ctyId + " AND tnk.COQUANTHUE_FK = '" + this.nccId +
								 "' and ( tt.CONLAI is null or (tt.CONLAI > 0 \n"+
								 "					 and tt.HOADON_FK not in \n"+
								 "						(select distinct tt.HOADON_FK \n"+
								 "						from ERP_THUENHAPKHAU tnk \n"+
								 "						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND TT.LOAIHD = 4 \n"+
//								 "						where tnk.NCC_FK = '" + this.nccId+
								 "						where tnk.COQUANTHUE_FK = '" + this.nccId+
								 "' and tt.CONLAI = 0 and tnk.CONGTY_FK = " + this.ctyId + " ) \n"+
								 "					  and tt.THANHTOANHD_FK in \n"+
								 "						(select MAX(tt.THANHTOANHD_FK) \n"+
								 "						from ERP_THUENHAPKHAU tnk  \n"+
								 "						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = tnk.PK_SEQ AND LOAIHD = 4 \n"+
								 "						left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
//								 "						 where tnk.trangthai = 2    and tnk.NCC_FK = '" + this.nccId+
								 "						 where tnk.trangthai = 2    and tnk.COQUANTHUE_FK = '" + this.nccId+
								 "' and t.TRANGTHAI<>2 and tnk.CONGTY_FK = " + this.ctyId + " \n"+
								 "						 group by tt.HOADON_FK ) ) )\n"+
								 "					 and tnk.trangthai = 2  and tnk.VAT > 0 \n ";
						if (this.id.length() > 0) {
							query += " and tnk.PK_SEQ not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK= "
									+ this.id + " AND LOAIHD = 4  and LOAITHUE= N'VAT nhập khẩu' ) \n";
						}

					}
					// LOAIHD: 5 - CHIPHIKHAC

					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += "select distinct 5 as LOAIHD,'' AS LOAITHUE, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as sotiengoc \n"+
								 "      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n"+
								 "      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI  \n"+
								 "from ERP_CHIPHIKHAC_CHITIET cpct \n"+
								 "     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n"+
								 "     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"+
								 "     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
								 "where cp.DOITUONG = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id +
								 "' and cp.LOAI= '0' \n";

					}
					query += " UNION ALL \n";
					query += "select distinct 5 as LOAIHD,'' AS LOAITHUE, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as sotiengoc \n"+
							 "	   ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then (cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0) - isnull(dathanhtoanNCC.DATHANHTOAN,0) ) else tt.CONLAI end as sotienavat \n"+
							 "	   ,'0' as sotientt, '' as POID , cp.TIENTE_FK as ttId, isnull(cp.TIGIA,1) as TIGIA, \n"+
							 "      isnull(cp.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI  \n"+
							 "from ERP_CHIPHIKHAC_CHITIET cpct \n"+
							 "     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n"+
							 "     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"+
							 "     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
							 "     left join (select a.cttt_fk,SUM(a.tienthanhtoan) as dathanhtoan \n"+
							 "	         from ERP_XOAKHTRATRUOC_CTTT a inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk=b.PK_SEQ \n"+
							 "	         where b.CONGTY_FK = " + this.ctyId + " AND b.TRANGTHAI=1 and b.TIENTE_FK= "+ this.tienteId + " and b.LOAIXOATRATRUOC ='1' and isnull(b.NCC_FK, b.NHANVIEN_FK)= "+ this.nccId + " \n"+
							 "	         group by a.cttt_fk) xoatamung  on xoatamung.cttt_fk = cp.PK_SEQ \n"+
							 "     left join " + 
							 "   	( \n"+
							 "       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+
							 "       from ERP_THANHTOANHOADONNCC_HOADON TTHD inner join ERP_THANHTOANHOADONNCC TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
							 "       where TTHD.LOAIHD = 5 AND TT.TRANGTHAI = 0 and TT.CONGTY_FK = " + this.ctyId + "  \n" + 
							 "       group by HOADON_FK \n"+
							 "       ) dathanhtoanNCC  on cp.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n"+
							 "where cp.ngay >'2017-01-01' and cp.CONGTY_FK = " + this.ctyId + "  AND cp.DOITUONG = '" + this.nccId +
							 "' and cp.TIENTE_FK= " + this.tienteId + " and ( tt.CONLAI is null or (tt.CONLAI > 0 \n"+
							 "					 and tt.HOADON_FK not in \n"+
							 "						(select distinct tt.HOADON_FK \n"+
							 "						from ERP_CHIPHIKHAC_CHITIET cpct \n"+
							 "						left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n"+
							 "						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"+
							 "						where cp.DOITUONG = '" + this.nccId +
							 "' and tt.CONLAI = 0 and cp.Congty_fk = " + this.ctyId + " ) \n"+
							 "					  and tt.THANHTOANHD_FK in \n"+
							 "						(select MAX(tt.THANHTOANHD_FK) \n"+
							 "						from ERP_CHIPHIKHAC_CHITIET cpct   \n"+
							 "							left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"+
							 "					 		left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
							 "							left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n"+
							 "						 where cp.trangthai = 1    and  cp.DOITUONG =  '" + this.nccId +
							 "' and t.TRANGTHAI<>2 and cp.CONGTY_FK = " + this.ctyId + " \n" +
							 "						 group by tt.HOADON_FK ) ) )\n" +
							 "					 and cp.trangthai = 1 \n ";

					if (this.DoiTuongChiPhiKhac.equals("1")) { // Neu la NCC
						query += " and cp.LOAI= '0' \n";
					}
					if (this.id.length() > 0) {
						query += " and cp.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + "  ) \n";
					}
					query += " and ((cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0) - isnull(dathanhtoanNCC.dathanhtoan,0) ) > 0 or tt.CONLAI > 0) \n";

					//LOAI HD: 10 - BUTTOANTONGHOP
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += "SELECT 10 AS LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, TT.SOHOADON AS SOHOADON,  N'Bút toán tổng hợp'  AS KYHIEU , BTTH.NGAYBUTTOAN AS NGAYHOADON ,  \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN BTTHCT.CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd where hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0)  ELSE BTTHCT.GIATRINT END AS SOTIENGOC,       \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN TT.SOTIENAVAT ELSE TT.SOTIENNT END AS SOTIENAVAT,      \r\n" + 
								"TT.SOTIENTT , '' AS POID ,BTTH.TIENTE_FK AS TTID, BTTH.TIGIA AS TIGIA, NGAYBUTTOAN, '' SOHOPDONG , '' SOHOPDONGNGOAI, ISNULL(TT.DIACHI, '') AS DIACHI       \r\n" + 
								"FROM ERP_BUTTOANTONGHOP BTTH         \r\n" + 
								"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON_HOADON TT ON TT.HOADON_FK = BTTH.PK_SEQ  AND TT.LOAIHD = 10      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON TTHD ON TT.THANHTOANHD_FK = TTHD.PK_SEQ       \r\n" + 
								"WHERE ((BTTHCT.KHACHHANG_FK = "+this.nccId+" AND BTTHCT.DOITUONGDAUKY =N'Nhà cung cấp') OR BTTHCT.NCC_FK = "+this.nccId+")\r\n" + 
								"AND TT.THANHTOANHD_FK = "+this.id;

					}
					query += " UNION ALL \n";
					query += 
							"SELECT 10 LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, \n " +
							"CASE WHEN BTTH.MACHUNGTU IS NOT NULL THEN BTTH.MACHUNGTU ELSE CONVERT(NVARCHAR, BTTH.PK_SEQ) END AS SOHOADON,  \r\n" + 
							"N'Bút toán tổng hợp' KYHIEU,  \r\n" + 
							"NGAYBUTTOAN AS NGAYHOADON,  \r\n" + 
							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN BTTHCT.CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0)  ELSE BTTHCT.GIATRINT END AS SOTIENGOC,  \r\n" + 

							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN \n " +
							"CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) - isnull(DATT.SOTIENTT, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0)  \r\n" +
							"ELSE " +
							" BTTHCT.GIATRINT - isnull(DATT.SOTIENTT, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) \n " +
							"END AS SOTIENAVAT, \n " +
							
							"0 as sotientt,  \r\n" + 
							"'' AS POID,  \r\n" + 
							"BTTH.TIENTE_FK AS ttId,  \r\n" + 
							"BTTH.TIGIA AS TIGIA,  \r\n" + 
							"NGAYBUTTOAN AS NGAYDENHANTT,  \r\n" + 
							"'' SOHOPDONG,  \r\n" + 
							"'' SOHOPDONGNGOAI,  \r\n" +
							"N'"+diaChiGoc+"' AS DIACHI  \r\n" +  
							
							"FROM  ERP_BUTTOANTONGHOP BTTH  \r\n" + 
							"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK  \r\n" + 
							//"LEFT JOIN ERP_THANHTOANHOADON_HOADON TT ON TT.HOADON_FK = BTTH.PK_SEQ  AND TT.LOAIHD = 10  \r\n" + 
							"LEFT JOIN (  \r\n" + 
							"	SELECT  \r\n" + 
							"	tt.HOADON_FK,  \r\n" + 
							"	SUM (tt.SOTIENTT) AS SOTIENTT  \r\n" + 
							"	FROM ERP_THANHTOANHOADON_HOADONBOPHAN tt  \r\n" + 
							"	INNER JOIN ERP_THANHTOANHOADON t ON tt.THANHTOANHD_FK = t.PK_SEQ  \r\n" + 
							"	WHERE  tt.LOAIHD = 10  \r\n" + 
							"	AND t.TRANGTHAI != 2  AND tt.NCC_FK =  "+this.nccId+"   \r\n" + 
							"	AND t.CONGTY_FK =  " + this.ctyId + " AND T.TIENTE_FK = " + this.tienteId + "  \r\n" + 
							"	GROUP BY  tt.HOADON_FK  \r\n" + 
						
							")DATT ON DATT.HOADON_FK = BTTH.PK_SEQ  \r\n" + 
							"LEFT JOIN (  \r\n" + 
							"	SELECT  TTHD.HOADON_FK, SUM (TTHD.SOTIENTT) AS DATHANHTOAN  \r\n" + 
							"	FROM ERP_THANHTOANHOADONNCC_HOADON TTHD  \r\n" + 
							"	INNER JOIN ERP_THANHTOANHOADONNCC TT ON TTHD.THANHTOANHD_FK = TT.PK_SEQ  \r\n" + 
							"	WHERE TTHD.LOAIHD = 10 AND TT.TRANGTHAI <> 2 AND tt.CONGTY_FK =  " + this.ctyId + " AND TT.TIENTE_FK = " + this.tienteId + "   \r\n" + 
							"	GROUP BY  HOADON_FK  \r\n" + 
							")dathanhtoanNCC ON BTTH.PK_SEQ = dathanhtoanNCC.hoadon_fk  \r\n" + 
							"LEFT JOIN (\r\n" + 
							"	SELECT HOADON_FK, TT.NCC_FK, SUM(TTHD.SOTIENTT) AS TIENTHANHTOAN \r\n" + 
							"	FROM ERP_THANHTOANHOADON_HOADON TTHD \r\n" + 
							"	INNER JOIN ERP_THANHTOANHOADON TT ON TTHD.THANHTOANHD_FK = TT.PK_SEQ \r\n" + 
							"	WHERE LOAIHD = 10 AND TT.TRANGTHAI <> 2 AND TT.TIENTE_FK = " + this.tienteId + " \r\n" + 
							"	GROUP BY HOADON_FK, TT.NCC_FK  \r\n" + 
							")DATHANHTOAN ON DATHANHTOAN.HOADON_FK = BTTH.PK_SEQ AND DATHANHTOAN.NCC_FK = "+this.nccId+" \r\n" +
							"WHERE BTTH.TIENTE_FK =  " + this.tienteId + " AND CONGTY_FK =  " + this.ctyId + "   \r\n" + 
							"AND BTTH.TRANGTHAI = 1  \r\n" + 
							"AND (  \r\n" + 
							"(  \r\n" + 
							"	KHACHHANG_FK = "+this.nccId+"   \r\n" + 
							"	AND BTTHCT.DOITUONGDAUKY = N'Nhà cung cấp'  \r\n" + 
							")  \r\n" + 
							"OR BTTHCT.NCC_FK =  "+this.nccId+"   \r\n" + 
							")  \r\n" + 
							"AND CO > 0 \r\n" ;

					if (this.id.length() > 0) {
						query += " and BTTH.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + "  ) \n";
					}
					query += 
							"AND (" +
							"		(BTTH.TIENTE_FK= '100000' AND BTTHCT.CO - isnull(DATT.SOTIENTT, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) " +
							" 		OR \n " +
							"		(BTTH.TIENTE_FK<> '100000' AND BTTHCT.GIATRINT - isnull(DATT.SOTIENTT, '0') - isnull(dathanhtoanNCC.DATHANHTOAN, '0') - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) \n " +
							") ";
					
					//END LOAI HD: 10 - BUTTOANTONGHOP
					
					//LOAI 11 HÓA ĐƠN ĐIỀU CHỈNH NCC 
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += "select distinct 11 as LOAIHD,'' AS LOAITHUE, HDK.pk_seq , hdk.sohoadon as sohoadon, hdk.mauhoadon as kyhieu ,  hdk.ngayhoadon as ngayhoadon, hdk.TONGTIENAVAT_VND as sotiengoc \n"+
								 "      ,case when HDK.TIENTE_FK= '100000'  then tt.sotienavat else   tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, HDK.TIENTE_FK as ttId, \n"+
								 "      isnull(HDK.tygia,1) as tigia , isnull(hdk.ngayhoadon,'') as ngayhoadon, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(HDK.DIACHI, '') DIACHI  \n"+
								 "from ERP_HOADONKHACNCC HDK \n"+
								 "     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = HDK.PK_SEQ AND TT.LOAIHD = 11 \n"+
								 "     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"+
								 "where HDK.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id +
								 "' and HDK.TIENTE_FK= "+this.tienteId+" \n";

					}
					query += " UNION ALL \n";
					query += "select distinct 11 as LOAIHD,'' AS LOAITHUE, hdk.pk_seq , hdk.sohoadon as sohoadon, hdk.mauhoadon as kyhieu,  HDK.ngayhoadon as ngayhoadon, hdk.TONGTIENAVAT_VND as sotiengoc \n"+
							 "	   ,case when HDK.TIENTE_FK= '100000' then (hdk.TONGTIENAVAT_VND - isnull(DATHANHTOAN.TIENTHANHTOAN,0)  ) end as sotienavat \n"+
							 "	   ,'0' as sotientt, '' as POID , HDK.TIENTE_FK as ttId, isnull(HDK.tygia,1) as TIGIA, \n"+
							 "      isnull(HDK.ngayhoadon,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI  \n"+
							 "from ERP_HOADONKHACNCC HDK \n"+
							 "LEFT JOIN (\r\n" + 
							 "	SELECT HOADON_FK, TT.NCC_FK, SUM(TTHD.SOTIENTT) AS TIENTHANHTOAN \r\n" + 
							 "	FROM ERP_THANHTOANHOADON_HOADON TTHD \r\n" + 
							 "	INNER JOIN ERP_THANHTOANHOADON TT ON TTHD.THANHTOANHD_FK = TT.PK_SEQ \r\n" + 
							 "	WHERE LOAIHD = 11 AND TT.TRANGTHAI <> 2 AND TT.TIENTE_FK = " + this.tienteId + " \r\n" + 
							 "	GROUP BY HOADON_FK, TT.NCC_FK  \r\n" + 
							 ")DATHANHTOAN ON DATHANHTOAN.HOADON_FK = HDK.PK_SEQ AND DATHANHTOAN.NCC_FK = "+this.nccId+" \r\n" + 
							 "where HDK.ngayhoadon >'2017-01-01' and  HDK.CONGTY_FK = " + this.ctyId + "  AND HDK.nhacungcap_fk = '" + this.nccId +
							 "' and hdk.trangthai = 1  and HDK.TIENTE_FK= " + this.tienteId +
							 " and  (hdk.TONGTIENAVAT_VND - isnull(DATHANHTOAN.TIENTHANHTOAN,0)  ) > 0  ";
					if (this.id.length() > 0) {
						query += " and hdk.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + " AND LOAIHD = 11 ) \n";
					}
					
					
					// LOAIHD: 6 - DENGHITHANHTOAN
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon, " +
								 " mh.TONGTIENAVAT as sotiengoc, \n"+
								 " CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"+
								 " tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') AS DIACHI  \n"+
								 " from ERP_MUAHANG mh \n"+
								 " inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"+
								 " inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
								 " inner join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
								 " where   mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.THANHTOANHD_FK = '" + this.id	+ "'  \n";

					}
					query += " UNION ALL \n";
					query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon, " +
							 " mh.TONGTIENAVAT as sotiengoc, \n"+
							 " mh.TONGTIENAVAT - isnull(DATT.SOTIENTT,0) - isnull(DATHANHTOANNCC.DATHANHTOAN,0) as SOTIENAVAT, " +
							 " 0 as sotientt, '' as POID,  \n"+
							 " mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' AS DIACHI   \n"+
							 " from ERP_MUAHANG mh \n"+
							 " LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
							 " INNER JOIN ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							 " LEFT JOIN " + 
							 "       ( \n"+
							 "         SELECT tt.HOADON_FK , SUM(tt.SOTIENTT) as SOTIENTT \n"+
							 "         FROM ERP_THANHTOANHOADON_HOADONBOPHAN tt \n"+
							 "         INNER JOIN ERP_THANHTOANHOADON t on tt.THANHTOANHD_FK = t.PK_SEQ \n"+
							 "         WHERE tt.LOAIHD = 6 AND t.TRANGTHAI != 2  AND tt.NCC_FK = " + this.nccId+
							 " 		   and t.CONGTY_FK = " + this.ctyId + " AND t.TIENTE_FK = " + this.tienteId + " \n " +
							 "         GROUP BY tt.HOADON_FK \n"+
							 "        ) DATT ON DATT.HOADON_FK = mh.PK_SEQ \n" + 
							 "       LEFT JOIN  " + 
							 "   	( \n"+
							 "           SELECt HOADON_FK , SUM(DATHANHTOAN)DATHANHTOAN FROM ( \n"+
							 "       select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+
							 "       from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
							 "       where TTHD.LOAIHD = 6 AND TT.TRANGTHAI !=2 AND tt.CONGTY_FK = " + this.ctyId + " " +
							 "		 AND TT.TIENTE_FK = " + this.tienteId + " \n " +
							 "       group by HOADON_FK \n"+
							 
 							 " 		 union all \n"+		
 
							 "	     SELECT TTHD.HOADONNCC_FK, sum(TTHD.TienThanhToan) as tongthanhtoan \n " +
							 "       FROM ERP_XOANONCC_HOADONNCC TTHD inner join ERP_XOANONCC TT on TTHD.XOANONCC_FK = TT.PK_SEQ \n " +
							 "       WHERE TT.trangthai != '2' and TT.NCC_FK = '" + this.nccId + "' \n " +
							 " 	     and TTHD.LOAICT = 6 \n" + "     GROUP BY TTHD.HOADONNCC_FK \n " +
							 "         ) DTT GROUP BY HOADON_FK  \n"+
							 
							 "       ) dathanhtoanNCC  on mh.PK_SEQ  = dathanhtoanNCC.hoadon_fk \n"+

							 "where mh.ngaymua >'2017-01-01' and mh.CONGTY_FK =" + this.ctyId + "  AND MH.TIENTE_FK = " + this.tienteId + " AND mh.TRANGTHAI = 1 and mh.NHACUNGCAP_FK = '"+ this.nccId + "' \n"+ 
/*							 "and ( " +
							 "		tt.CONLAI is null or  \n"+
							 "      (tt.CONLAI > 0 \n" + 
							 "		 and mh.TIENTE_FK = " + this.tienteId + " \n"+
							 " 		 and tt.HOADON_FK not in \n"+
							 "				(select distinct tt.HOADON_FK \n" + 
							 "				 from ERP_MUAHANG mh \n"+
							 "			     left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
							 "				 left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							 "				 where mh.CONGTY_FK = " + this.ctyId + " and mh.TRANGTHAI = 1 and mh.NHACUNGCAP_FK = '" + this.nccId + "' and tt.CONLAI = 0 " +
							 "				 and mh.TIENTE_FK = " + this.tienteId + "" +
							 "				) \n"+
							 "  			and tt.THANHTOANHD_FK in \n"+
							 "				(select MAX(tt.THANHTOANHD_FK) \n" + 
							 "				 from Erp_MuaHang mh  \n"+
							 "				 left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"+
							 " 				 left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"+
							 "				 left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = mh.NHACUNGCAP_FK \n"+
							 "				 where mh.CONGTY_FK = " + this.ctyId +
							 " 				 and  mh.TRANGTHAI = 1 and mh.LOAIHANGHOA_FK = '2' and TYPE = '1' and mh.TIENTE_FK = "+ this.tienteId + 
							 "  			 and  mh.NHACUNGCAP_FK =  '" + this.nccId + "' and t.TRANGTHAI<>2	\n"+
							 "	 			 group by tt.HOADON_FK " +
							 "				) " +
							 "		) " +
							 "	) \n"+
*/							 " and  mh.quanlycongno='1' and mh.TYPE ='1' and mh.trangthai not in (2,3)\n";
					if (this.id.length() > 0) {
						query += " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + ") \n";
					}
					query += "       and  (mh.TONGTIENAVAT- isnull(DATHANHTOANNCC.DATHANHTOAN,0) ) > 0 \n";
				}
				}

				// LOAD NHỮNG HD CỦA NHÂN VIÊN

				System.out.println("NhanvieN:" + NhanvienId);
				if (this.NhanvienId.trim().length() > 0) {
					String diaChiGoc = "";
					try {
						query = "SELECT ISNULL(DIACHI, '') DIACHI FROM ERP_NHANVIEN WHERE PK_sEQ = "+this.NhanvienId+"";
						ResultSet rs = db.get(query);
						diaChiGoc = rs.next() ? rs.getString("DIACHI") : "";
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					query = "";
					// LOAIHD: 1 - TAMUNG

					if (this.id.length() > 0) {
						query = "SELECT distinct 1 AS LOAIHD,'' AS LOAITHUE, TU.PK_SEQ , cast(TU.PK_SEQ as nvarchar(50)) as sohoadon ,N'TẠM ỨNG'  AS KYHIEU, " +
								"TU.NGAYTAMUNG AS ngayhoadon, " +
								"case when TU.TIENTE_FK = 100000 then  TU.SOTIENTAMUNG else TU.SOTIENTAMUNGNT end as sotiengoc, " +
								
								"case when TU.TIENTE_FK = 100000 then \n " +
								"	TU.SOTIENTAMUNG \n"+
								"             -(SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								"               FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								"               WHERE B.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id+
								"  				AND B.HOADON_FK=TU.PK_SEQ) " +
								"ELSE " +
								"	TU.SOTIENTAMUNGNT \n"+
								"             -(SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								"               FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								"               WHERE B.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id+
								"  				AND B.HOADON_FK=TU.PK_SEQ) " +
								
								"END AS sotienAVAT , \n"+
								
								"0 AS SOTIENTT, '' as POID, \n" + 
								"TU.TIENTE_FK as TTID, \n"+ this.tigia.replace(",", "") + " AS TIGIA, isnull(TU.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL((SELECT ISNULL(DIACHI, '') DIACHI FROM ERP_THANHTOANHOADON_HOADON B WHERE THANHTOANHD_FK = "+this.id+" AND B.HOADON_FK=TU.PK_SEQ ), '') DIACHI  \n"+
								"FROM ERP_TAMUNG TU \n"+
								"WHERE TU.TRANGTHAI = 1 AND  TU.CONGTY_FK = " + this.ctyId +
								"and	" +
								"ISNULL(TU.HOANTAT,'0')='0' and TU.TIENTE_FK = (select tiente_fk from ERP_THANHTOANHOADON where pk_seq = "	+ this.id + " ) \n" +
								" and TU.pk_seq not in  \n"+
								" (	" +
								"	select hoadon_fk \n"+
								"  	from ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_HOADON b on a.pk_seq=b.thanhtoanhd_fk \n"+
								"  	where   b.thanhtoanhd_fk=" + this.id+
								"	and b.LOAIHD = 1 and a.CONGTY_FK = " + this.ctyId + " " +
								")  \n" + 
								" and (" +
								" (	TU.TIENTE_FK = 100000 AND " +
								"	TU.SOTIENTAMUNG \n"+
								"              -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								"                 FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								"                 WHERE A.CONGTY_FK = " + this.ctyId +
								"  				  AND B.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id +
								"  				 AND HOADON_FK = TU.PK_SEQ) >0 " +
								" ) \n" +
								" OR " +
								" ( TU.TIENTE_FK <> 100000 AND " +
								"	TU.SOTIENTAMUNGNT \n"+
								"              -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								"                 FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								"                 WHERE A.CONGTY_FK = " + this.ctyId +
								"  				  AND B.LOAIHD = 1 AND A.TRANGTHAI <>2 and a.pk_seq <> " + this.id +
								"  				 AND HOADON_FK = TU.PK_SEQ) >0 " +
								" ) \n" +
								") \n " +
								"  and   tu.NHANVIEN_FK = " + (this.NhanvienId == "" ? "0" : this.NhanvienId);

						query +=" UNION ALL \n"+
								" select distinct 1 AS LOAIHD,'' AS LOAITHUE, B.HOADON_FK AS PK_SEQ, cast(B.HOADON_FK as nvarchar(50)) AS SOHOADON, N'TẠM ỨNG' as KYHIEU," +
								" a.ngaytamung as ngayhoadon," +
								" case when a.TIENTE_FK = 100000 then  a.SOTIENTAMUNG else a.SOTIENTAMUNGNT end as sotiengoc,  \n"+
								" case when a.TIENTE_FK = 100000 then \n " +
								" 	a.sotientamung -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n"+
								"                     FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n"+
								"                     WHERE  CT.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <> "+this.id + " )  " +
								" ELSE " +
								" 	a.SOTIENTAMUNGNT -( SELECT ISNULL(SUM(CT.SOTIENTT),0) \n"+
								"                     FROM ERP_THANHTOANHOADON_HOADON CT INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=CT.THANHTOANHD_FK \n"+
								"                     WHERE  CT.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND CT.HOADON_FK=B.HOADON_FK and a.pk_seq <> "+this.id + " )  " +
								
								" END AS sotienAVAT , \n" + 
								"  		sotientt, '' as POID,  a.TIENTE_FK as TTID , \n"
								+ this.tigia.replace(",", "") +
								" AS TIGIA ,isnull(a.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(b.DIACHI, '') DIACHI \n"+
								" from ERP_THANHTOANHOADON_HOADON b \n"+
								" inner join ERP_TAMUNG a on a.pk_seq=b.HOADON_fk  and b.loaihd = 1  \n"+

								" where   thanhtoanhd_fk=" + this.id + "  \n";

					} else {

						query += " SELECT distinct 1 AS LOAIHD,'' AS LOAITHUE, TU.PK_SEQ , cast(TU.PK_SEQ as nvarchar(50)) as sohoadon ,N'TẠM ỨNG'  AS KYHIEU," +
								 " TU.NGAYTAMUNG AS ngayhoadon, " +
								 " case when TU.TIENTE_FK = 100000 then  TU.SOTIENTAMUNG else TU.SOTIENTAMUNGNT end as sotiengoc, " +
								 " case when TU.TIENTE_FK = 100000 then \n " +
								 " SOTIENTAMUNG   \n"+
								 "        -( SELECT ISNULL(SUM(b.SOTIENTT),0)   \n"+
								 " 			FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "		    WHERE B.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ ) " +
								 " ELSE \n " +
								 " TU.SOTIENTAMUNGNT   \n"+
								 "        -( SELECT ISNULL(SUM(b.SOTIENTT),0)   \n"+
								 " 			FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "		    WHERE B.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ ) " +

								 "END AS sotienAVAT , \n"+
								 "        0 AS SOTIENTT, '' as POID, TU.TIENTE_FK as TTID, \n"
								+ this.tigia.replace(",", "") +
								 " AS TIGIA, isnull(TU.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"'  DIACHI  \n"+
								 " FROM ERP_TAMUNG TU \n" + " WHERE TU.CONGTY_FK = " + this.ctyId +" and TU.NGAYTAMUNG>='2017-01-01'"+
								 " AND TU.TIENTE_FK = '" + this.tienteId + "'  and ISNULL(TU.HOANTAT,'0')='0'  " +
								 " AND (" +
								 " ( TU.TIENTE_FK = 100000 AND " +
								 "	SOTIENTAMUNG   \n"+
								 "   -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "      FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "      WHERE  B.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ) >0 " +
								 "	 ) \n"+
								 "  OR \n " +
								 " ( TU.TIENTE_FK <> 100000 AND " +
								 "	TU.SOTIENTAMUNGNT   \n"+
								 "   -( SELECT ISNULL(SUM(b.SOTIENTT),0) \n"+
								 "      FROM ERP_THANHTOANHOADON_HOADON B INNER JOIN ERP_THANHTOANHOADON A ON A.PK_SEQ=B.THANHTOANHD_FK  \n"+
								 "      WHERE  B.LOAIHD = 1 AND A.TRANGTHAI<>'2'  AND B.HOADON_FK=TU.PK_SEQ) >0 " +
								 "	 ) \n"+
								 
								 " ) " +
								 "   and TU.TRANGTHAI = 1 \n" + 
								 "   and   NHANVIEN_FK = "	+ (this.NhanvienId == "" ? "0" : this.NhanvienId);

					}

					// LOAIHD: 5 - CHIPHIKHAC

					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += "select distinct 5 as LOAIHD,'' AS LOAITHUE, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu ,  cp.NGAY as ngayhoadon, (cpct.TONGTIENCHUATHUE + cpct.THUE ) as sotiengoc \n"
								+ "      ,case when cp.TIENTE_FK= '100000' then tt.sotienavat else tt.SOTIENNT end as sotienavat, tt.SOTIENTT as sotientt  , '' as POID, cp.TIENTE_FK as ttId, \n"
								+ "      isnull(cp.tigia,1) as tigia , isnull(cp.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI  \n"
								+ "from ERP_CHIPHIKHAC_CHITIET cpct \n"
								+ "     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n"
								+ "     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"
								+ "     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"
								+ "where cp.DOITUONG = '" + this.NhanvienId + "' and tt.THANHTOANHD_FK = '" + this.id
								+ "' and cp.LOAI= '1'  \n";

					}
					query += " UNION ALL \n";
					query += "select distinct 5 as LOAIHD,'' AS LOAITHUE, cp.pk_seq , cast(cp.PK_SEQ as nvarchar(50)) as sohoadon, cp.DIENGIAI as kyhieu,  cp.NGAY as ngayhoadon , (cpct.TONGTIENCHUATHUE + cpct.THUE ) as sotiengoc \n"
							+ "	   ,case when (tt.SOTIENAVAT is null OR t.TRANGTHAI=2) then (cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0)) else tt.CONLAI end as sotienavat \n"
							+ "	   ,'0' as sotientt, '' as POID , cp.TIENTE_FK as ttId, isnull(cp.TIGIA,1) as TIGIA, \n"
							+ "      isnull(cp.ngaydenhantt,'') as ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"'  \n"
							+ "from ERP_CHIPHIKHAC_CHITIET cpct \n"
							+ "     left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK\n"
							+ "     left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"
							+ "     left join ERP_THANHTOANHOADON t on t.PK_SEQ = tt.THANHTOANHD_FK  \n"
							+ "     left join (select a.cttt_fk,SUM(a.tienthanhtoan) as dathanhtoan \n"
							+ "	         from ERP_XOAKHTRATRUOC_CTTT a inner join ERP_XOAKHTRATRUOC b on a.xoakhtratruoc_fk=b.PK_SEQ \n"
							+ "	         where b.CONGTY_FK = " + this.ctyId + "  AND b.TRANGTHAI=1 and b.TIENTE_FK= "
							+ this.tienteId + " and b.LOAIXOATRATRUOC ='1' and isnull(b.NCC_FK, b.NHANVIEN_FK)= "
							+ this.NhanvienId + " \n"
							+ "	         group by a.cttt_fk) xoatamung  on xoatamung.cttt_fk = cp.PK_SEQ \n"
							+ "where cp.CONGTY_FK = " + this.ctyId + " and  cp.DOITUONG = '" + this.NhanvienId
							+ "' and cp.NGAY>='2017-01-01' and cp.TIENTE_FK= " + this.tienteId + " and ( tt.CONLAI is null or (tt.CONLAI > 0 \n"
							+ "					 and tt.HOADON_FK not in \n"
							+ "						(select distinct tt.HOADON_FK \n"
							+ "						from ERP_CHIPHIKHAC_CHITIET cpct \n"
							+ "						left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK \n"
							+ "						left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"
							+ "						where cp.DOITUONG = '" + this.NhanvienId + "' and tt.CONLAI = 0) \n"
							+ "					  and tt.THANHTOANHD_FK in \n"
							+ "						(select MAX(tt.THANHTOANHD_FK) \n"
							+ "						from ERP_CHIPHIKHAC_CHITIET cpct   \n"
							+ "							left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = cpct.CHIPHIKHAC_FK AND TT.LOAIHD = 5 \n"
							+ "					 		left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"
							+ "							left join ERP_CHIPHIKHAC cp on cp.pk_seq = cpct.CHIPHIKHAC_FK\n"
							+ "						 where cp.trangthai = 1    and  cp.DOITUONG =  '" + this.NhanvienId
							+ "' and t.TRANGTHAI<>2	\n" + "						 group by tt.HOADON_FK ) ) )\n"
							+ "					 and cp.trangthai = 1 \n ";

					if (this.DoiTuongChiPhiKhac.equals("0")) { // Neu la NV
						query += " and cp.LOAI= '1' \n";
					}
					if (this.id.length() > 0) {
						query += " and cp.pk_seq not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "
								+ this.id + "  ) \n";
					}
					query += " and ((cpct.TONGTIENCHUATHUE + cpct.THUE - isnull(xoatamung.dathanhtoan,0) ) > 0 or tt.CONLAI > 0) \n";
						
					//LOAI HD: 10 - BUTTOANTONGHOP
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += "SELECT 10 AS LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, TT.SOHOADON AS SOHOADON,  N'Bút toán tổng hợp'  AS KYHIEU , BTTH.NGAYBUTTOAN AS NGAYHOADON ,  \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN BTTHCT.CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) ELSE BTTHCT.GIATRINT END AS SOTIENGOC,       \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN TT.SOTIENAVAT ELSE TT.SOTIENNT END AS SOTIENAVAT,      \r\n" + 
								"TT.SOTIENTT , '' AS POID ,BTTH.TIENTE_FK AS TTID, BTTH.TIGIA AS TIGIA, NGAYBUTTOAN, '' SOHOPDONG , '' SOHOPDONGNGOAI, ISNULL(TT.DIACHI, '') AS DIACHI       \r\n" + 
								"FROM ERP_BUTTOANTONGHOP BTTH         \r\n" + 
								"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON_HOADON TT ON TT.HOADON_FK = BTTH.PK_SEQ  AND TT.LOAIHD = 10      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON TTHD ON TT.THANHTOANHD_FK = TTHD.PK_SEQ       \r\n" + 
								"WHERE ((BTTHCT.KHACHHANG_FK = "+this.NhanvienId+" AND BTTHCT.DOITUONGDAUKY =N'Nhân viên') OR BTTHCT.NHANVIEN_FK = "+this.NhanvienId+")\r\n" + 
								"AND TT.THANHTOANHD_FK = "+this.id;

					}
					query += " UNION ALL \n";
					query += 
							"SELECT 10 LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, \n " +
							"CASE WHEN BTTH.MACHUNGTU IS NOT NULL THEN BTTH.MACHUNGTU ELSE CONVERT(NVARCHAR, BTTH.PK_SEQ) END AS SOHOADON,  \r\n" + 
							"N'Bút toán tổng hợp' KYHIEU,  \r\n" + 
							"NGAYBUTTOAN AS NGAYHOADON,  \r\n" + 
							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN BTTHCT.CO ELSE BTTHCT.GIATRINT END AS SOTIENGOC,  \r\n" + 

							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN \n " +
							"CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0)  \r\n" +
							"ELSE " +
							" BTTHCT.GIATRINT - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) \n " +
							"END AS SOTIENAVAT, \n " +
							
							"0 as sotientt,  \r\n" + 
							"'' AS POID,  \r\n" + 
							"BTTH.TIENTE_FK AS ttId,  \r\n" + 
							"BTTH.TIGIA AS TIGIA,  \r\n" + 
							"NGAYBUTTOAN AS NGAYDENHANTT,  \r\n" + 
							"'' SOHOPDONG,  \r\n" + 
							"'' SOHOPDONGNGOAI,  \r\n" +
							"N'"+diaChiGoc+"' AS DIACHI  \r\n" +  
							
							"FROM  ERP_BUTTOANTONGHOP BTTH  \r\n" + 
							"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK  \r\n" + 
							//"LEFT JOIN ERP_THANHTOANHOADON_HOADON TT ON TT.HOADON_FK = BTTH.PK_SEQ  AND TT.LOAIHD = 10  \r\n" + 
							"LEFT JOIN (\r\n" + 
							"	SELECT HOADON_FK, TT.NHANVIEN_FK, SUM(TTHD.SOTIENTT) AS TIENTHANHTOAN \r\n" + 
							"	FROM ERP_THANHTOANHOADON_HOADON TTHD \r\n" + 
							"	INNER JOIN ERP_THANHTOANHOADON TT ON TTHD.THANHTOANHD_FK = TT.PK_SEQ \r\n" + 
							"	WHERE LOAIHD = 10 AND TT.TRANGTHAI <> 2 AND TT.TIENTE_FK = " + this.tienteId + " \r\n" + 
							"	GROUP BY HOADON_FK, TT.NHANVIEN_FK  \r\n" + 
							")DATHANHTOAN ON DATHANHTOAN.HOADON_FK = BTTH.PK_SEQ AND DATHANHTOAN.NHANVIEN_FK = "+this.NhanvienId+" \r\n" + 
							"WHERE (NGAYBUTTOAN>='2017-01-01' or btth.diengiai like '%DK%')  and BTTH.TIENTE_FK =  " + this.tienteId + " AND CONGTY_FK =  " + this.ctyId + "   \r\n" + 
							"AND BTTH.TRANGTHAI = 1  \r\n" + 
							"AND (  \r\n" + 
							"(  \r\n" + 
							"	KHACHHANG_FK = "+this.NhanvienId+"   \r\n" + 
							"	AND BTTHCT.DOITUONGDAUKY = N'Nhân viên'  \r\n" + 
							")  \r\n" + 
							"OR BTTHCT.NHANVIEN_FK =  "+this.NhanvienId+"   \r\n" + 
							")  \r\n" + 
							"AND CO > 0 \r\n" ;

					if (this.id.length() > 0) {
						query += " and BTTH.pk_seq not in " +
								 " (" +
								 "	select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + " AND LOAIHD = 10 " +
								 "  AND (SELECT NHANVIEN_FK FROM ERP_THANHTOANHOADON WHERE PK_SEQ = "	+ this.id + ") = " + this.NhanvienId + " " +
								") \n";
					}
					query += 
							"AND (" +
							"		(BTTH.TIENTE_FK= '100000' AND BTTHCT.CO  - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) " +
							" 		OR \n " +
							"		(BTTH.TIENTE_FK<> '100000' AND BTTHCT.GIATRINT - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) \n " +
							") ";
					
					//END LOAI HD: 10 - BUTTOANTONGHOP
					
					// LOAIHD: 6 - DENGHITHANHTOAN
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon ,  mh.TONGTIENAVAT as sotiengoc, \n"
								+ "        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENTT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"
								+ "		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(TT.DIACHI, '') DIACHI  \n"
								+ " from ERP_MUAHANG mh \n"
								+ " 	left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ and TT.LOAIHD = 6 \n"
								+ " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"
								+ " 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"
								+ " where   mh.NHANVIEN_FK = '" + this.NhanvienId + "' and tt.THANHTOANHD_FK = '"
								+ this.id + "' \n";

					}
					query += " UNION ALL \n";
					query += " select distinct 6 as LOAIHD,'' AS LOAITHUE,  mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon,  mh.TONGTIENAVAT as sotiengoc \n"
							+ " 		, mh.TONGTIENAVAT  - isnull(DATT.SOTIENTT,0) as SOTIENAVAT ,0 as sotientt, '' as POID  \n"
							+ " 		,mh.TIENTE_FK as ttId,  mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"'  \n"
							+ " from ERP_MUAHANG mh "
							+ " 		left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND tt.LOAIHD = 6  \n"
							+ " 		left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"
							+ " 		inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"
							+ " 		left join \n" 
							+ "       ( \n"
							+ "         SELECT tt.HOADON_FK , SUM(tt.SOTIENTT) as SOTIENTT "
							+ "         FROM ERP_THANHTOANHOADON_HOADON tt  \n"
							+ "              INNER JOIN ERP_THANHTOANHOADON t on tt.THANHTOANHD_FK = t.PK_SEQ "
							+ "         WHERE tt.LOAIHD = 6 AND t.TRANGTHAI != 2 and t.CONGTY_FK  = " + this.ctyId 
							+ "         GROUP BY tt.HOADON_FK "
							+ "        ) DATT ON DATT.HOADON_FK = mh.PK_SEQ \n"
							+ " where mh.TRANGTHAI = 1 and  mh.NHANVIEN_FK = '" + this.NhanvienId + "' \n"
							+ " 		and ( tt.CONLAI is null or  \n" + "           (tt.CONLAI > 0 \n"
							+ "			and mh.TIENTE_FK = " + this.tienteId + " \n"
							+ " 			and tt.HOADON_FK not in \n"
							+ "				(select distinct tt.HOADON_FK \n" + "				 from ERP_MUAHANG mh \n"
							+ "				      left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ  AND tt.LOAIHD = 6 \n"
							+ "				      left join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"
							+ "				where mh.CONGTY_FK = " + this.ctyId
							+ " and  mh.TRANGTHAI = 1 and  mh.NHANVIEN_FK = '" + this.NhanvienId
							+ "' and tt.CONLAI = 0 and mh.TIENTE_FK = " + this.tienteId + ") \n"
							+ "  			and tt.THANHTOANHD_FK in \n"
							+ "				(select MAX(tt.THANHTOANHD_FK) \n" + "				from Erp_MuaHang mh  \n"
							+ "				    left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND tt.LOAIHD = 6 \n"
							+ " 				    left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"
							+ "				    left join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"
							+ "				where mh.CONGTY_FK = " + this.ctyId
							+ " and  mh.TRANGTHAI = 1 and mh.LOAIHANGHOA_FK = '2' and TYPE = '1'  and mh.TIENTE_FK = "
							+ this.tienteId + "  and  mh.NHANVIEN_FK =  '" + this.NhanvienId
							+ "' and t.TRANGTHAI<>2	\n" + // trang thai mh=2 da
															// hoan tat
							"	 			group by tt.HOADON_FK ) ) ) \n"
							+ " 		 and  mh.LOAIHANGHOA_FK = '2' and mh.TYPE ='1' \n";
					if (this.id.length() > 0) {
						query += " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "
								+ this.id + " ) \n";
					}
					query += "       and  mh.TONGTIENAVAT  - isnull(DATT.SOTIENTT,0) > 0 \n";

					// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TT ĐÃ CHỐT)
					/*
					 * if(this.id.length() > 0 ) { query += " UNION ALL \n" ;
					 * query +=
					 * " select distinct 8 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , \n"
					 * +
					 * "       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n"
					 * +
					 * "       ISNULL((select SUM(a.SOTIENTT) as SOTIENTT  from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ where  b.NHANVIEN_FK = '"
					 * + this.NhanvienId +
					 * "' and a.LOAIHD = 6),0)*(-1) as sotiengoc, \n"+
					 * "       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"
					 * +
					 * "		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"
					 * + " from  ERP_THANHTOANHOADON_HOADON tt  \n"+
					 * " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 8 \n"
					 * +
					 * " 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"
					 * + " where   tthd.NHANVIEN_FK = '" + this.NhanvienId +
					 * "' and tthd.PK_SEQ = "+ this.id +" \n";
					 * 
					 * }
					 */

					/*
					 * query += " UNION ALL \n" ; query +=
					 * " select distinct 8 as LOAIHD, tthd.PK_SEQ, cast(tthd.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , tthd.NGAYGHINHAN as ngayhoadon , ISNULL(GOC.SOTIENTT,0)*(-1) as sotiengoc, \n"
					 * +
					 * "       ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0) as SOTIENAVAT, \n"
					 * +
					 * "		 0 as SOTIENTT , '' as POID ,tthd.TIENTE_FK as ttId, tthd.TIGIA,  '' ngaydenhantt  \n"
					 * + " from  ERP_THANHTOANHOADON tthd  \n"+
					 * "   inner join   "+
					 * "   (select THANHTOANHD_FK, SUM(SOTIENTT) as SOTIENTT "+
					 * "    from ERP_THANHTOANHOADON_HOADON "+
					 * "    where LOAIHD = 6 "+ "    group by THANHTOANHD_FK "+
					 * ") GOC on tthd.PK_SEQ = GOC.THANHTOANHD_FK "+
					 * " 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"
					 * + "   left join "+
					 * "   ( select a.HOADON_FK , SUM(a.SOTIENTT) as DATHANHTOAN "
					 * +
					 * "     from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ  "
					 * + "     where a.LOAIHD = 8 and b.TRANGTHAI != '2' "+
					 * "     group by a.HOADON_FK "+
					 * ") DATT on DATT.HOADON_FK = tthd.PK_SEQ  "+
					 * " where   tthd.NHANVIEN_FK = '" + this.NhanvienId +
					 * "' and tthd.TRANGTHAI = 1 \n";
					 * 
					 * if(this.id.length() > 0) { query+=
					 * " and tthd.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "
					 * + this.id +" ) \n" ; }
					 */

					// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TẠM ỨNG ĐÃ CHỐT)
					/*
					 * if(this.id.length() > 0 ) { query += " UNION ALL \n" ;
					 * query +=
					 * " select distinct 9 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị tạm ứng'  AS KYHIEU , \n"
					 * +
					 * "       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n"
					 * +
					 * "       ISNULL((select SUM(a.SOTIENTT) as SOTIENTT  from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ where a.LOAIHD = 1 and  b.NHANVIEN_FK = '"
					 * + this.NhanvienId + "'),0)*(-1) as sotiengoc, \n"+
					 * "       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"
					 * +
					 * "		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"
					 * + " from  ERP_THANHTOANHOADON_HOADON tt  \n"+
					 * " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 9 \n"
					 * +
					 * " 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"
					 * + " where   tthd.NHANVIEN_FK = '" + this.NhanvienId +
					 * "' and tthd.PK_SEQ = "+ this.id +" \n";
					 * 
					 * }
					 */

					/*
					 * query += " UNION ALL \n" ; query +=
					 * " select distinct 9 as LOAIHD, tthd.PK_SEQ, cast(tthd.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi đề nghị tạm ứng'  AS KYHIEU , tthd.NGAYGHINHAN as ngayhoadon , ISNULL(GOC.SOTIENTT,0)*(-1) as sotiengoc, \n"
					 * +
					 * "       ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0) as SOTIENAVAT, \n"
					 * +
					 * "		 0 as SOTIENTT , '' as POID ,tthd.TIENTE_FK as ttId, tthd.TIGIA,  '' ngaydenhantt  \n"
					 * + " from  ERP_THANHTOANHOADON tthd  \n"+
					 * "   inner join   "+
					 * "   (select THANHTOANHD_FK, SUM(SOTIENTT) as SOTIENTT "+
					 * "    from ERP_THANHTOANHOADON_HOADON "+
					 * "    where LOAIHD = 1 "+ "    group by THANHTOANHD_FK "+
					 * ") GOC on tthd.PK_SEQ = GOC.THANHTOANHD_FK "+
					 * " 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = tthd.NHANVIEN_FK \n"
					 * + "   left join "+
					 * "   ( select a.HOADON_FK , SUM(a.SOTIENTT) as DATHANHTOAN "
					 * +
					 * "     from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ  "
					 * + "     where a.LOAIHD = 9 and b.TRANGTHAI != '2' "+
					 * "     group by a.HOADON_FK "+
					 * ") DATT on DATT.HOADON_FK = tthd.PK_SEQ  "+
					 * " where   tthd.NHANVIEN_FK = '" + this.NhanvienId +
					 * "' and tthd.TRANGTHAI = 1 \n";
					 * 
					 * if(this.id.length() > 0) { query+=
					 * " and tthd.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "
					 * + this.id +" ) \n" ; }
					 */

				}

				// LOAD NHỮNG HD CỦA KHÁCH HÀNG
				if (this.khachhangId.trim().length() > 0) {
					String diaChiGoc = "";
					try {
						query = "SELECT ISNULL(DIACHI, '') DIACHI FROM KhachHang WHERE PK_sEQ = "+this.khachhangId+"";
						ResultSet rs = db.get(query);
						diaChiGoc = rs.next() ? rs.getString("DIACHI") : "";
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					query = "";
					// LOAIHD: 7 - KHACHHANGTRATRUOC
					if (this.id.trim().length() > 0) {
						String loaiphieuthu = "";
						if (this.isNpp.equals("0"))
							loaiphieuthu = " AND TT.LOAIPHIEUTHU IN (1,2) ";
						if (this.isNpp.equals("1"))
							loaiphieuthu = " AND TT.LOAIPHIEUTHU IN (0) ";

						query = "select distinct 7 as LOAIHD,'' AS LOAITHUE, tt.PK_SEQ, cast(tt.PK_SEQ as nvarchar(50)) as SOHOADON, N'Khách hàng trả trước' as KYHIEU, tt.NGAYCHUNGTU AS NGAYHOADON  , \n"
								+ "       (select  CASE WHEN tt.TIENTE_FK <> '100000'  "
								+ "		THEN ISNULL(tt.THUDUOCNT,0) ELSE ISNULL(tt.THUDUOC,0) END   "
								+ "		from ERP_THUTIEN a where TRANGTHAI = '1' and NOIDUNGTT_FK = '100001' and KHACHHANG_FK = '"+ this.khachhangId + "' and a.PK_SEQ = tt.PK_SEQ ) AS SOTIENGOC,  \n"
								+ "       CASE WHEN tt.TIENTE_FK <> '100000' THEN ttct.SOTIENNT ELSE ttct.SOTIENAVAT END AS SOTIENAVAT , \n"
								+ "       ttct.SOTIENTT, '' AS POID, tt.TIENTE_FK AS TTID, ISNULL(tt.TIGIA,1) as TIGIA, '' as NGAYDENHANTT, '' SOHOPDONG, '' SOHOPDONGNGOAI, ttct.DIACHI  \n"
								+ "from ERP_THUTIEN tt  inner join ERP_THANHTOANHOADON_HOADON ttct on ttct.HOADON_FK = tt.PK_SEQ AND ttct.LOAIHD = 7 \n"
								+ "where tt.KHACHHANG_FK = '" + this.khachhangId + "' and ttct.THANHTOANHD_FK = "
								+ this.id + "  \n" + loaiphieuthu;
						query += " UNION ALL \n";
					}

					query += "select distinct 7 as LOAIHD,'' AS LOAITHUE, tt.PK_SEQ, cast(tt.PK_SEQ as nvarchar(50)) as SOHOADON, N'Khách hàng trả trước' as KYHIEU, tt.NGAYCHUNGTU AS NGAYHOADON , \n"
							+ "       CASE WHEN tt.TIENTE_FK <> '100000'  THEN ISNULL(tt.THUDUOCNT,0)   \n"
							+ "            ELSE  ISNULL(tt.THUDUOC,0)  END AS SOTIENGOC, \n"
							+ "       CASE WHEN tt.TIENTE_FK <> '100000'  THEN (ISNULL(tt.THUDUOCNT,0)  - ISNULL(DAXOAKH.DAXOA,0) - ISNULL(DATHANHTOAN.DATT,0) ) \n"
							+ "            ELSE  (ISNULL(tt.THUDUOC,0)  - ISNULL(DAXOAKH.DAXOAVND,0)- ISNULL(DATHANHTOAN.DATT,0) ) END AS SOTIENAVAT, \n"
							+ "       0 as SOTIENTT, '' AS POID, tt.TIENTE_FK as TTID, ISNULL(tt.TIGIA,1) as TIGIA, \n"
							+ "       '' as NGAYDENHANTT, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' DIACHI \n"
							+ "from ERP_THUTIEN tt left join \n"
							+ "     ( select ct.cttt_fk , sum(tienthanhtoan) as DAXOA, sum(tienthanhtoan*ISNULL(ct.TIGIA,1)) as DAXOAVND \n"
							+ "       from ERP_XOAKHTRATRUOC xkh inner join ERP_XOAKHTRATRUOC_CTTT ct on xkh.PK_SEQ = ct.xoakhtratruoc_fk \n"
							+ "       where xkh.trangthai = '1' and loaixoatratruoc = 0 "
							+ "       group by ct.cttt_fk \n" + "      ) DAXOAKH on tt.PK_SEQ = DAXOAKH.cttt_fk \n"
							+ "     left join \n" + "     ( select ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"
							+ "       from ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ "
							+ "       where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '" + this.khachhangId + "' "
							+ "             and tthd.TIENTE_FK = '" + this.tienteId + "' and ct.LOAIHD = '7' \n"
							+ "       group by ct.HOADON_FK  "
							+ "     )DATHANHTOAN on  tt.PK_SEQ = DATHANHTOAN.HOADON_FK \n"
							+ "where  tt.TRANGTHAI = '1' and tt.NOIDUNGTT_FK = '100001' and tt.KHACHHANG_FK = '"
							+ this.khachhangId + "' and tt.TIENTE_FK = '" + this.tienteId + "' \n"
							+ "       and ISNULL(tt.THUDUOC,0)  - ISNULL(DAXOAKH.DAXOAVND,0) - (ISNULL(DATHANHTOAN.DATT,0)*ISNULL(tt.TIGIA,1) ) > 0 \n";
					// if(this.isNpp.equals("0")) query+= " AND tt.LOAIPHIEUTHU
					// IN (1,2) ";
					// if(this.isNpp.equals("1")) query+= " AND tt.LOAIPHIEUTHU
					// IN (0) ";

					if (this.id.trim().length() > 0) {
						query += "       and tt.PK_SEQ not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where  THANHTOANHD_FK = '"
								+ this.id + "') ";
					}

					// LOAIHD: 6 - DENGHITHANHTOAN
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENAVAT as sotiengoc, \n"
								+ "        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"
								+ "		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, isnull(mh.SOPO, '') SOHOPDONG, isnull(mh.SOHOPDONG, '') SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI   \n"
								+ " from ERP_MUAHANG mh \n"
								+ " 	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"
								+ " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"
								+ " 	inner join  nhaPhanPhoi kh on kh.PK_SEQ = mh.KHACHHANG_FK \n"
								+ " where   mh.KHACHHANG_FK = '" + this.khachhangId + "' and tt.THANHTOANHD_FK = '"
								+ this.id + "'  \n";

					}
					query += " UNION ALL \n";
					query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon, mh.TONGTIENAVAT as sotiengoc \n"
							+ " 		, mh.TONGTIENAVAT - isnull(DATT.SOTIENTT,0)   as SOTIENAVAT ,0 as sotientt, '' as POID  \n"
							+ " 		,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"'   \n"
							+ " from ERP_MUAHANG mh \n"	
							+ " 	INNER JOIN KHACHHANG kh on kh.PK_SEQ = mh.KHACHHANG_FK \n"
							+ " 	LEFT JOIN \n" 
							+ "       ( \n"
							+ "         SELECT tt.HOADON_FK , SUM(tt.SOTIENTT) as SOTIENTT "
							+ "         FROM ERP_THANHTOANHOADON_HOADON tt  \n"
							+ "              INNER JOIN ERP_THANHTOANHOADON t on tt.THANHTOANHD_FK = t.PK_SEQ "
							+ "         WHERE tt.LOAIHD = 6 AND t.TRANGTHAI != 2 and t.CONGTY_FK  = " + this.ctyId 
							+ "         GROUP BY tt.HOADON_FK "
							+ "        ) DATT ON DATT.HOADON_FK = mh.PK_SEQ \n"
							+ " 		LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ " 		LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"							
							+ " where mh.CONGTY_FK = " + this.ctyId 
							+ " AND mh.TRANGTHAI = 1 and mh.KHACHHANG_FK = '"+ this.khachhangId + "' \n" 
							+ " 		and ( tt.CONLAI is null or  \n"
							+ "           	(tt.CONLAI > 0 \n" 
							+ "				and mh.TIENTE_FK = " + this.tienteId + " \n"
							+ " 			and tt.HOADON_FK not in \n"
							+ "				(select distinct tt.HOADON_FK \n" 
							+ "				 from ERP_MUAHANG mh \n"
							+ "				      left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ "				      left join KHACHHANG kh on kh.PK_SEQ = mh.KHACHHANG_FK \n"
							+ "				where mh.TRANGTHAI = 1 and mh.KHACHHANG_FK = '" + this.khachhangId
							+ "' and tt.CONLAI = 0 and mh.TIENTE_FK = " + this.tienteId + " and mh.CONGTY_FK = "+ this.ctyId + " ) \n" 
							+ "  			and tt.THANHTOANHD_FK in \n"
							+ "				(select MAX(tt.THANHTOANHD_FK) \n" 
							+ "				 from Erp_MuaHang mh  \n"
							+ "				    left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ " 				    left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"
							+ "				    left join KHACHHANG kh on kh.PK_SEQ = mh.KHACHHANG_FK \n"
							+ "				where mh.TRANGTHAI = 1 and mh.LOAIHANGHOA_FK = '2' and TYPE = '1' and mh.TIENTE_FK = "
							+ this.tienteId + "  and  mh.KHACHHANG_FK =  '" + this.khachhangId
							+ "' and t.TRANGTHAI<>2 and mh.CONGTY_FK = " + this.ctyId + " 	\n" 
							+ "	 			group by tt.HOADON_FK ) ) ) \n"
							+ " 		and  mh.LOAIHANGHOA_FK = '2' and mh.TYPE ='1' and isnull(mh.ISDNTT,0) = '1' \n";
					if (this.id.length() > 0) {
						query += " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + ") \n";
					}
					query += "       and  (mh.TONGTIENAVAT - isnull(t.SOTIENTT,0)   ) > 0 \n";
										
					System.out.println("Doi tuong Chi Phi Khac:"+this.DoiTuongChiPhiKhac);
					
					
					// HÓA ĐƠN HÀNG TRẢ VỀ
					if(this.id.length() > 0)
					{
						query += " UNION ALL \n"; 
						query += 
						"	select distinct 8 as LOAIHD,'' AS LOAITHUE, hd.PK_SEQ, hd.SOHOADON as sohoadon,  N'Hóa đơn hàng trả về'  AS KYHIEU , hd.NGAYXUATHD as ngayhoadon , \n"+
						"	 HD.TONGTIENAVAT as sotiengoc,  tt.SOTIENAVAT SOTIENAVAT, \n"+
						"	 tt.SOTIENTT , '' as POID , isnull(hd.TIENTE_FK, 100000) as ttId, isnull(hd.TYGIA,1) as TIGIA, '' ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI \n"+
						" from ERP_HOADON hd \n"+
						"	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = hd.PK_SEQ  AND TT.LOAIHD = 8 \n"+
						"	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"+
						"	inner join KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
						
						"  LEFT JOIN ( \n"+
						"  SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n"+
						"  FROM  \n"+
						"  (  \n"+ 
						" 		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"+  
						" 		FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
						" 		INNER JOIN ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n"+
						" 		WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and TTHD.KHACHHANG_FK = "+this.khachhangId+" and isnull(TT.isNPP,0) = 0 \n"+ 
						" 		group by HOADON_FK  \n"+
		
						" 		UNION ALL    \n"+
		
						" 		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n"+
						" 		FROM ERP_THUTIEN_HOADON TTHD \n"+
						" 		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n"+
						" 		WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = 0 AND TT.BANGKE_FK IS NULL  \n"+
						" 		group by HOADON_FK  \n"+
		
						" 		UNION ALL  \n"+
		
						" 		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n"+
						" 		FROM ERP_THUTIEN_HOADON TTHD \n"+
						" 		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ \n"+ 
						" 		WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = 0 AND TT.BANGKE_FK IS NOT NULL \n"+
						" 		group by HOADON_FK  \n"+
		
						" 		UNION ALL  \n"+
						 
						" 		select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN \n"+   
						" 		from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ  \n"+ 
						" 		where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and isnull(TT.isNPP,0) = 0 \n"+
						" 		group by HOADON_FK \n"+
								
						" 		UNION ALL  \n"+
								
						" 		select ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
						" 	    from ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ  \n"+  
						" 	    where   tthd.TRANGTHAI != '2' \n"+            
						" 	    and  ct.LOAIHD = '8' AND TTHD.PK_SEQ != "+ (this.id.length() <= 0 ? 0 : this.id) +
						" 	    group by ct.HOADON_FK \n"+		       
		
						" 	)HOADONDATT  group by HOADON_FK  \n"+								
						"  )dathanhtoan on hd.pk_seq = dathanhtoan.hoadon_fk    \n"+
												 
						" where   hd.KHACHHANG_FK = '"+this.khachhangId+"' and tt.THANHTOANHD_FK = '"+this.id+"' \n";
						
					}
					
					query += " UNION ALL \n"+
					
					 " SELECT 	distinct 8 as LOAIHD,'' AS LOAITHUE, hd.PK_SEQ, hd.SOHOADON as SOHOADON, N'Hóa đơn hàng trả về' as KYHIEU, hd.NGAYXUATHD AS NGAYHOADON , \n"+
				     "  		hd.TONGTIENAVAT SOTIENGOC, \n"+ 
				     " 			(ISNULL(hd.TONGTIENAVAT,0)  - ISNULL(dathanhtoan.DATHANHTOAN,0) ) SOTIENAVAT, \n"+
				     "  		0 as SOTIENTT, '' AS POID, isnull(hd.TIENTE_FK,10000) as TTID, ISNULL(hd.TYGIA,1) as TIGIA, \n"+
				     "  		'' as NGAYDENHANTT, '' SOHOPDONG, '' SOHOPDONGNGOAI, N'"+diaChiGoc+"' DIACHI \n"+
				     " FROM ERP_HOADON hd \n"+
				     " LEFT JOIN ( \n"+
				     " SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n"+
				     " FROM  \n"+
					 " ( \n"+  
					 "		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN    \n"+
					 "		FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
					 "		INNER JOIN ERP_XOAKHTRATRUOC TT on TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ  \n"+
					 "		WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and TTHD.KHACHHANG_FK = "+this.khachhangId+" and isnull(TT.isNPP,0) = 0 \n"+
					 "		group by HOADON_FK  \n"+
					
					 "		UNION ALL \n"+   

					 "		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n"+
					 "		FROM ERP_THUTIEN_HOADON TTHD \n"+
					 "		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n"+
					 "		WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = 0 AND TT.BANGKE_FK IS NULL \n"+ 
					 "		group by HOADON_FK  \n"+
				
					 "		UNION ALL \n"+ 
				
					 "		SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n"+
					 "		FROM ERP_THUTIEN_HOADON TTHD \n"+
					 "		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n"+
					 "		WHERE  TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = '7' and isnull(TTHD.isNPP,0) = 0 AND TT.BANGKE_FK IS NOT NULL \n"+
					 "		group by HOADON_FK  \n"+
				
					 "		UNION ALL \n"+ 
					 
					 "		select TTHD.HOADON_FK , sum(TTHD.XOANO) as DATHANHTOAN    \n"+
					 "		from ERP_BUTRUKHACHHANG_CHITIET TTHD inner join ERP_BUTRUKHACHHANG TT on TTHD.BTKH_FK = TT.PK_SEQ \n"+  
					 "		where  TT.TRANGTHAI != 2 AND TTHD.LOAIHD = '7' and isnull(TT.isNPP,0) = 0 \n"+
					 "		group by HOADON_FK \n"+
							
					 "		UNION ALL \n"+ 
							
					 "		select ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
					 "	    from ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ    \n"+    
					 "	    where   tthd.TRANGTHAI != '2'     \n"+        
					 "	    and tthd.TIENTE_FK = '"+this.tienteId+"' and ct.LOAIHD = '8' AND TTHD.PK_SEQ != "+ (this.id.length() <= 0 ? 0 : this.id) +
					 "	    group by ct.HOADON_FK  \n"+				       
				
					 "	)HOADONDATT  group by HOADON_FK  	\n"+							
					 " )dathanhtoan on hd.pk_seq = dathanhtoan.hoadon_fk     \n"+
					 " where  hd.TRANGTHAI = '1' and hd.KHACHHANG_FK = '"+this.khachhangId+"' AND HD.LOAIHOADON = 2  AND (ISNULL(hd.TONGTIENAVAT,0)  - ISNULL(dathanhtoan.DATHANHTOAN,0) ) !=0 ";
					 if (this.id.trim().length() > 0) {
							query += "       and hd.PK_SEQ not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADON where  THANHTOANHD_FK = '"+ this.id + "') ";
					  }  
					 
					
					if(this.DoiTuongChiPhiKhac.equals("5"))
					{
						query = "";
						if(this.khachhangId.trim().length() >0 )
						{
							if(this.id.trim().length()>0)
							{
								query = // TÍCH LŨY CẦN DUYỆT
									" SELECT distinct 8 as LOAIHD,'' AS LOAITHUE,PK_SEQ , SCHEME sohoadon, SCHEME AS KYHIEU, ngaykyhd ngayhoadon, tonggiatri as sotiengoc, \n"+
									" 		(giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) as SOTIENAVAT , isnull(TICHLUY.DATT,0) as sotientt, '' as POID, \n"+
									"        100000 as ttId, 1 as TIGIA, ngayketthuchd ngaydenhantt, sohopdong SOHOPDONG, '' SOHOPDONGNGOAI \n"+
									" FROM \n"+ 
									" ( \n"+
									"	SELECT DKKMTICHLUY_FK PK_SEQ, c.SCHEME , doanhsoDAT, donvitra, sanphamtra, soluongtra, giatritra, a.ngaykyhd, a.ngayketthuchd, \n"+
									"		   a.sohopdong, \n"+
									"		   ISNULL( ( SELECT SUM(TONGGIATRI)  FROM ERP_DONDATHANGNPP_TICHLUY_TRATL \n"+
									"				  	 WHERE DKKMID = a.DKKMTICHLUY_FK \n"+
									"				  	 AND DONDATHANGID in ( SELECT PK_SEQ FROM ERP_DONDATHANGNPP  \n"+
									"										   WHERE KHACHHANG_FK = '"+this.khachhangId+"' and trangthai != 3 ) ) , 0) as tonggiatri \n"+
									"	FROM DANGKYKM_TICHLUY_KHACHHANG a \n"+
									"		INNER JOIN DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ \n"+
									"		INNER join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ \n"+		
									"	WHERE KHACHHANG_FK = '"+this.khachhangId+"' and DUYET = '1' and MucDat is not null \n"+
									"		and c.TRANGTHAI = '1' and c.khongcanduyettra = '0'  \n"+
									"		and donvitra != 2 \n"+
									" ) \n"+
									" DATA \n"+
									" LEFT JOIN \n"+
									" ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									"   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									"   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									"   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '8' and tthd.PK_SEQ != "+this.id +" \n"+
									"   GROUP BY ct.HOADON_FK \n"+
									" )DATHANHTOAN on  DATA.PK_SEQ = DATHANHTOAN.HOADON_FK \n"+	
									" INNER JOIN \n"+
									" ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									"   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									"   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									"   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '8' and tthd.PK_SEQ = "+this.id +" \n"+
									"   GROUP BY ct.HOADON_FK \n"+
									" )TICHLUY on  DATA.PK_SEQ = TICHLUY.HOADON_FK \n"+								
									" where ( giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) > 0  \n";
								
							// TÍCH LŨY KHÔNG CẦN DUYỆT
							query += " UNION ALL \n"+
							 		 " SELECT distinct 9 as LOAIHD,'' AS LOAITHUE, PK_SEQ, DATA.SCHEME SOHOADON, DATA.SCHEME AS KYHIEU , DATA.ngaykyhd ngayhoadon, giatritra as sotiengoc, \n"+
							 		 "		  (giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) as SOTIENAVAT, TICHLUY.DATT as sotientt, '' as POID, \n"+
							 		 " 		  100000 as ttId, 1 as TIGIA, ngayketthuchd ngaydenhantt, sohopdong SOHOPDONG, '' SOHOPDONGNGOAI \n"+
							 		 " FROM \n"+  
							 		 "	(  \n"+
									 "	SELECT 	DT.DKKMTICHLUY_FK PK_SEQ, DT.doanhsoDAT, tc.donvi as donvitra, DT.sohopdong, DT.ngaykyhd, DT.ngayketthuchd, DT.SCHEME, \n"+
									 "			( case tc.donvi when 0 then round( tc.chietkhau * DT.doanhsoDAT / 100.0, 0 ) when 1 then tc.chietkhau else 0 end ) as giatritra, \n"+
									 "			ISNULL( ( select SUM(TONGGIATRI) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = DT.DKKMTICHLUY_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '"+this.khachhangId+"' and trangthai != 3 ) ) , 0) as tonggiatri \n"+ 
									 "	FROM \n"+
									 "	( \n"+
									 "		SELECT	DKKMTICHLUY_FK, c.PK_SEQ,  \n"+
									 "				ISNULL ( ( \n"+
									 "				select SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as tonggiatri \n"+
									 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  \n"+
									 "				where dh.KHACHHANG_FK = a.KHACHHANG_FK and dh.CS_DUYET = '1' and dh.TRANGTHAI not in ( 0, 3 ) and dh.NgayDonHang >= c.ngayds_tungay and dh.NgayDonHang <= c.ngayds_denngay \n"+
									 "		 				and dh_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = c.PK_SEQ ) \n"+
									 "				), 0 ) doanhsoDAT , a.sohopdong,a.ngaykyhd, a.ngayketthuchd , c.SCHEME \n"+
									 "		from DANGKYKM_TICHLUY_KHACHHANG a inner join DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ \n"+
									 "		inner join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ \n"+
									 "		where KHACHHANG_FK = '"+this.khachhangId+"' and c.TRANGTHAI = '1' and c.khongcanduyettra = '1'  \n"+ 
									 "	) \n"+
									 "	DT inner join TIEUCHITHUONGTL_TIEUCHI tc on DT.PK_SEQ = tc.thuongtl_fk \n"+
									 "	where tc.tumuc <= DT.doanhsoDAT and DT.doanhsoDAT <= tc.denmuc and tc.donvi != 2 \n"+
									 " )  \n"+
									 " DATA  \n"+
									 " LEFT JOIN \n"+
									 " ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									 "   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									 "   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									 "   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '9' and tthd.PK_SEQ != "+this.id +" \n"+
									 "   GROUP BY ct.HOADON_FK \n"+
									 " )DATHANHTOAN on  DATA.PK_SEQ = DATHANHTOAN.HOADON_FK \n"+	
									 " INNER JOIN \n"+
									 " ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									 "   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									 "   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									 "   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '9' and tthd.PK_SEQ = "+this.id +" \n"+
									 "   GROUP BY ct.HOADON_FK \n"+
									 " )TICHLUY on  DATA.PK_SEQ = TICHLUY.HOADON_FK \n"+								
									 " where ( giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) > 0  \n";
									
								
							query += " UNION ALL \n";
							}
							
							query += // TÍCH LŨY CẦN DUYỆT
									" SELECT distinct 8 as LOAIHD,'' AS LOAITHUE,PK_SEQ , SCHEME sohoadon, SCHEME AS KYHIEU, ngaykyhd ngayhoadon, tonggiatri as sotiengoc, \n"+
									" 		(giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) as SOTIENAVAT , 0 as sotientt, '' as POID, \n"+
									"        100000 as ttId, 1 as TIGIA, ngayketthuchd ngaydenhantt, sohopdong SOHOPDONG, '' SOHOPDONGNGOAI \n"+
									" FROM \n"+ 
									" ( \n"+
									"	SELECT DKKMTICHLUY_FK PK_SEQ, c.SCHEME , doanhsoDAT, donvitra, sanphamtra, soluongtra, giatritra, a.ngaykyhd, a.ngayketthuchd, \n"+
									"		   a.sohopdong, \n"+
									"		   ISNULL( ( SELECT SUM(TONGGIATRI)  FROM ERP_DONDATHANGNPP_TICHLUY_TRATL \n"+
									"				  	 WHERE DKKMID = a.DKKMTICHLUY_FK \n"+
									"				  	 AND DONDATHANGID in ( SELECT PK_SEQ FROM ERP_DONDATHANGNPP  \n"+
									"										   WHERE KHACHHANG_FK = '"+this.khachhangId+"' and trangthai != 3 ) ) , 0) as tonggiatri \n"+
									"	FROM DANGKYKM_TICHLUY_KHACHHANG a \n"+
									"		INNER JOIN DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ \n"+
									"		INNER join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ \n"+		
									"	WHERE KHACHHANG_FK = '"+this.khachhangId+"' and DUYET = '1' and MucDat is not null \n"+
									"		and c.TRANGTHAI = '1' and c.khongcanduyettra = '0'  \n"+
									"		and donvitra != 2 \n"+
									" ) \n"+
									" DATA \n"+
									" LEFT JOIN \n"+
									" ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									"   FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+   
									"   where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+
									"   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '8' and tthd.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" \n"+
									"   GROUP BY ct.HOADON_FK \n"+
									" )DATHANHTOAN on  DATA.PK_SEQ = DATHANHTOAN.HOADON_FK \n"+
									" where ( giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) > 0 \n ";
							
									if(this.id.trim().length()>0)
									{
										query += " and DATA.PK_SEQ NOT IN (SELECT ct.HOADON_FK FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ " +
												 "						   WHERE tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+ " \n" +
												 "						   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '8' and tthd.PK_SEQ = "+ this.id +" ) \n";
									}
								
							// TÍCH LŨY KHÔNG CẦN DUYỆT
							query += " UNION ALL \n"+
							 		 " SELECT distinct 9 as LOAIHD,'' AS LOAITHUE, PK_SEQ, DATA.SCHEME SOHOADON, DATA.SCHEME AS KYHIEU , DATA.ngaykyhd ngayhoadon, giatritra as sotiengoc, \n"+
							 		 "		  (giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) as SOTIENAVAT, 0 as sotientt, '' as POID, \n"+
							 		 " 		  100000 as ttId, 1 as TIGIA, ngayketthuchd ngaydenhantt, sohopdong SOHOPDONG, '' SOHOPDONGNGOAI \n"+
							 		 " FROM \n"+  
							 		 "	(  \n"+
									 "	SELECT 	DT.DKKMTICHLUY_FK PK_SEQ, DT.doanhsoDAT, tc.donvi as donvitra, DT.sohopdong, DT.ngaykyhd, DT.ngayketthuchd, DT.SCHEME, \n"+
									 "			( case tc.donvi when 0 then round( tc.chietkhau * DT.doanhsoDAT / 100.0, 0 ) when 1 then tc.chietkhau else 0 end ) as giatritra, \n"+
									 "			ISNULL( ( select SUM(TONGGIATRI) from ERP_DONDATHANGNPP_TICHLUY_TRATL where DKKMID = DT.DKKMTICHLUY_FK and DONDATHANGID in ( select PK_SEQ from ERP_DONDATHANGNPP where KHACHHANG_FK = '"+this.khachhangId+"' and trangthai != 3 ) ) , 0) as tonggiatri \n"+ 
									 "	FROM \n"+
									 "	( \n"+
									 "		SELECT	DKKMTICHLUY_FK, c.PK_SEQ,  \n"+
									 "				ISNULL ( ( \n"+
									 "				select SUM( dh_sp.soluong * round( ( dongiaGOC * ( 1 + thueVAT / 100.0 ) ), 0 ) ) as tonggiatri \n"+
									 "				from ERP_DONDATHANGNPP dh inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on dh.PK_SEQ = dh_sp.dondathang_fk  \n"+
									 "				where dh.KHACHHANG_FK = a.KHACHHANG_FK and dh.CS_DUYET = '1' and dh.TRANGTHAI not in ( 0, 3 ) and dh.NgayDonHang >= c.ngayds_tungay and dh.NgayDonHang <= c.ngayds_denngay \n"+
									 "		 				and dh_sp.sanpham_fk in ( select sanpham_fk from TIEUCHITHUONGTL_SANPHAM where thuongtl_fk = c.PK_SEQ ) \n"+
									 "				), 0 ) doanhsoDAT , a.sohopdong,a.ngaykyhd, a.ngayketthuchd , c.SCHEME \n"+
									 "		from DANGKYKM_TICHLUY_KHACHHANG a inner join DANGKYKM_TICHLUY b on a.DKKMTICHLUY_FK = b.PK_SEQ \n"+
									 "		inner join TIEUCHITHUONGTL c on b.TIEUCHITL_FK = c.PK_SEQ \n"+
									 "		where KHACHHANG_FK = '"+this.khachhangId+"' and c.TRANGTHAI = '1' and c.khongcanduyettra = '1' and b.daduyet = '1' and b.TRANGTHAI = '1'  \n"+ 
									 "	) \n"+
									 "	DT inner join TIEUCHITHUONGTL_TIEUCHI tc on DT.PK_SEQ = tc.thuongtl_fk \n"+
									 "	where tc.tumuc <= DT.doanhsoDAT and DT.doanhsoDAT <= tc.denmuc and tc.donvi != 2 \n"+
									 " )  \n"+
									 " DATA  \n"+
									 " LEFT JOIN \n"+
									 " ( SELECT ct.HOADON_FK, SUM(ct.SOTIENTT) AS DATT \n"+
									 "  FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ \n"+
									 "  where   tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = "+this.ctyId+"  \n" +
									 " 	and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '9' and tthd.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" \n"+
									 "  GROUP BY ct.HOADON_FK \n"+
									 " )DATHANHTOAN on  DATA.PK_SEQ = DATHANHTOAN.HOADON_FK  \n"+
									 " WHERE  (giatritra - tonggiatri - isnull(DATHANHTOAN.DATT,0)) >0  ";
									 if(this.id.trim().length()>0)
									{
										query += " and DATA.PK_SEQ NOT IN (SELECT ct.HOADON_FK FROM ERP_THANHTOANHOADON_HOADON ct inner join ERP_THANHTOANHOADON tthd on ct.THANHTOANHD_FK = tthd.PK_SEQ " +
												 "						   WHERE tthd.TRANGTHAI != '2' AND tthd.KHACHHANG_FK = '"+this.khachhangId+"' and tthd.CONGTY_FK = " +this.ctyId+ " \n" +
												 "						   and tthd.TIENTE_FK = '100000' and ct.LOAIHD = '9' and tthd.PK_SEQ = "+ this.id +" ) \n";
									}
							
						}
					}
					

					// LOAD NHỮNG PHIẾU CHI (ĐỀ NGHỊ TT ĐÃ CHỐT)
					/*
					 * if(this.id.length() > 0 ) { query += " UNION ALL \n" ;
					 * query +=
					 * " select distinct  8 as LOAIHD, tt.HOADON_FK as PK_SEQ, cast(tt.HOADON_FK as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , \n"
					 * +
					 * "       (select a.NGAYGHINHAN from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as ngayhoadon , \n"
					 * +
					 * "       ISNULL((select SUM(a.SOTIENTT) as SOTIENTT  from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ where a.LOAIHD = 6 AND b.KHACHHANG_FK = '"
					 * + this.khachhangId + "' ),0)*(-1) as sotiengoc, "+
					 * "       ( CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END) as SOTIENAVAT, \n"
					 * +
					 * "		 tt.SOTIENTT as SOTIENTT , '' as POID , tthd.TIENTE_FK as ttId,  (select a.TIGIA from ERP_THANHTOANHOADON a where a.PK_SEQ = tt.HOADON_FK ) as TIGIA,  '' ngaydenhantt  \n"
					 * + " from  ERP_THANHTOANHOADON_HOADON tt  \n"+
					 * " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  and tt.LOAIHD = 8 \n"
					 * +
					 * " 	inner join KHACHHANG kh on kh.PK_SEQ = tthd.KHACHHANG_FK \n"
					 * + " where   tthd.KHACHHANG_FK = '" + this.khachhangId +
					 * "' and tthd.PK_SEQ = "+ this.id +" \n";
					 * 
					 * }
					 */

					/*
					 * query += " UNION ALL \n" ; query +=
					 * " select distinct 8 as LOAIHD, tthd.PK_SEQ, cast(tthd.PK_SEQ as nvarchar(50)) as sohoadon,  N'Chi đề nghị thanh toán'  AS KYHIEU , tthd.NGAYGHINHAN as ngayhoadon ,ISNULL(GOC.SOTIENTT,0)*(-1) as sotiengoc, \n"
					 * +
					 * "       ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0)  as SOTIENAVAT, \n"
					 * +
					 * "		 0 as SOTIENTT , '' as POID ,tthd.TIENTE_FK as ttId, tthd.TIGIA,  '' ngaydenhantt  \n"
					 * + " from  ERP_THANHTOANHOADON tthd  \n"+
					 * "   inner join   "+
					 * "   (select THANHTOANHD_FK, SUM(SOTIENTT) as SOTIENTT "+
					 * "    from ERP_THANHTOANHOADON_HOADON "+
					 * "    where LOAIHD = 6 "+ "    group by THANHTOANHD_FK "+
					 * ") GOC on tthd.PK_SEQ = GOC.THANHTOANHD_FK "+
					 * " 	inner join KHACHHANG kh on kh.PK_SEQ = tthd.KHACHHANG_FK \n"
					 * + "   left join "+
					 * "   ( select a.HOADON_FK , SUM(a.SOTIENTT) as DATHANHTOAN "
					 * +
					 * "     from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ  "
					 * +
					 * "     where a.LOAIHD = 8 and b.TRANGTHAI != '2' and b.CONGTY_FK = "
					 * +this.ctyId+ "     group by a.HOADON_FK "+
					 * ") DATT on DATT.HOADON_FK = tthd.PK_SEQ  "+
					 * " where  tthd.CONGTY_FK = "+this.ctyId+
					 * " AND ABS(ISNULL(GOC.SOTIENTT,0)*(-1) - ISNULL(DATT.DATHANHTOAN,0)  ) > 0 AND  tthd.KHACHHANG_FK = '"
					 * + this.khachhangId + "' and tthd.TRANGTHAI = 1 \n";
					 * 
					 * if(this.id.length() > 0) { query+=
					 * " and tthd.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "
					 * + this.id +" ) \n" ; }
					 */
					
					
				}

				if (this.bophanId.trim().length() > 0) {

					// LOAIHD: 6 - DENGHITHANHTOAN (NV)

					if (this.id.length() > 0) {
						query += " select distinct '0' as DOITUONG ,nv.PK_SEQ as DOITUONG_FK, (nv.MA + ' - '+ nv.TEN)  as MADOITUONG , 6 as LOAIHD, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENAVAT as sotiengoc, \n"
								+ "        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"
								+ "		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI  \n"
								+ " from ERP_MUAHANG mh \n"
								+ " 	inner join ERP_THANHTOANHOADON_HOADONBOPHAN tt on tt.HOADON_FK = mh.PK_SEQ and TT.LOAIHD = 6 \n"
								+ " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"
								+ " 	inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"
								+ " where   tt.NHANVIEN_FK is not null and tt.THANHTOANHD_FK = '" + this.id + "' \n";

						query += " UNION ALL \n";

					}

					query += " select distinct '0' as DOITUONG ,nv.PK_SEQ as DOITUONG_FK, (nv.MA + ' - ' + nv.TEN) as MADOITUONG , 6 as LOAIHD,  mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon, mh.TONGTIENAVAT as sotiengoc \n"
							+ " 		, mh.TONGTIENAVAT  - isnull(DATHANHTOAN.DATHANHTOAN,0) - isnull(DATHANHTOANBP.DATHANHTOAN,0) as SOTIENAVAT ,0 as sotientt, '' as POID  \n"
							+ " 		,mh.TIENTE_FK as ttId,  mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI  \n"
							+ " from ERP_MUAHANG mh "
							+ " 		inner join ERP_NHANVIEN nv on nv.PK_SEQ = mh.NHANVIEN_FK \n"
							+ " 		left join \n" 
							+ "       ( \n"
							+ "        SELECT t.NHANVIEN_FK, tt.HOADON_FK, SUM(tt.SOTIENTT) as DATHANHTOAN \n"
							+ "        FROM ERP_THANHTOANHOADON_HOADON tt  \n"
							+ " 		      inner join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"
							+ "        WHERE t.NHANVIEN_FK is not null  and t.TRANGTHAI != 2 AND tt.LOAIHD = 6  "
							+ "        GROUP BY t.NHANVIEN_FK, tt.HOADON_FK \n"
							+ "        ) DATHANHTOAN ON mh.PK_SEQ = DATHANHTOAN.HOADON_FK AND mh.NHANVIEN_FK = DATHANHTOAN.NHANVIEN_FK  \n"
							+ " 		left join \n" 
							+ "       ( \n"
							+ "        SELECT tt.NHANVIEN_FK, tt.HOADON_FK, SUM(tt.SOTIENTT) as DATHANHTOAN \n"
							+ "        FROM ERP_THANHTOANHOADON_HOADONBOPHAN tt   \n"
							+ " 		      inner join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"
							+ "        WHERE tt.NHANVIEN_FK is not null  and t.TRANGTHAI != 2 ";
					if (this.id.trim().length() > 0) {
						query += " and tt.THANHTOANHD_FK  != " + this.id + "  ";
					}
					query += "        GROUP BY tt.NHANVIEN_FK, tt.HOADON_FK \n"
							+ "        ) DATHANHTOANBP ON mh.PK_SEQ = DATHANHTOANBP.HOADON_FK AND mh.NHANVIEN_FK = DATHANHTOANBP.NHANVIEN_FK  \n"
							+ " where mh.NGAYMUA >='2017-01-01' AND  mh.TONGTIENAVAT  - isnull(DATHANHTOAN.DATHANHTOAN,0) - isnull(DATHANHTOANBP.DATHANHTOAN,0) >0 and mh.TRANGTHAI = 1 and  mh.NHANVIEN_FK is not null and mh.DONVITHUCHIEN_FK = "
							+ this.bophanId + " \n";
					if (this.id.trim().length() > 0) {
						query += " and mh.PK_SEQ not in (select HOADON_FK from ERP_THANHTOANHOADON_HOADONBOPHAN where THANHTOANHD_FK = "
								+ this.id + " ) \n";
					}
					query += " order by nv.PK_SEQ ";

				}

				if (this.doiTuongKhacId != null && this.doiTuongKhacId.trim().length() > 0)
				{
					
					//LOAI HD: 10 - BUTTOANTONGHOP
					if (this.id.length() > 0) {
						query += "SELECT 10 AS LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, TT.SOHOADON AS SOHOADON,  N'Bút toán tổng hợp'  AS KYHIEU , BTTH.NGAYBUTTOAN AS NGAYHOADON ,  \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN BTTHCT.CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) ELSE BTTHCT.GIATRINT END AS SOTIENGOC,       \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN TT.SOTIENAVAT ELSE TT.SOTIENNT END AS SOTIENAVAT,      \r\n" + 
								"TT.SOTIENTT , '' AS POID ,BTTH.TIENTE_FK AS TTID, BTTH.TIGIA AS TIGIA, NGAYBUTTOAN AS NGAYDENHANTT, '' SOHOPDONG , '' SOHOPDONGNGOAI, ISNULL(TT.DIACHI, '') AS DIACHI       \r\n" + 
								"FROM ERP_BUTTOANTONGHOP BTTH         \r\n" + 
								"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON_HOADON TT ON TT.HOADON_FK = BTTH.PK_SEQ  AND TT.LOAIHD = 10      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON TTHD ON TT.THANHTOANHD_FK = TTHD.PK_SEQ       \r\n" + 
								"WHERE ((BTTHCT.doiTuongKhac_FK = "+this.doiTuongKhacId+") OR BTTHCT.doiTuongKhac_FK = "+this.doiTuongKhacId+")\r\n" + 
								"AND TT.THANHTOANHD_FK = "+this.id;
						query += " UNION ALL \n";
					}
					query += 
							"SELECT 10 LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, \n " +
							"CASE WHEN BTTH.MACHUNGTU IS NOT NULL THEN BTTH.MACHUNGTU ELSE CONVERT(NVARCHAR, BTTH.PK_SEQ) END AS SOHOADON,  \r\n" + 
							"N'Bút toán tổng hợp' KYHIEU,  \r\n" + 
							"NGAYBUTTOAN AS NGAYHOADON,  \r\n" + 
							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN BTTHCT.CO ELSE BTTHCT.GIATRINT END AS SOTIENGOC,  \r\n" + 

							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN \n " +
							"CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0)  \r\n" +
							"ELSE " +
							" BTTHCT.GIATRINT - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) \n " +
							"END AS SOTIENAVAT, \n " +
							
							"0 as sotientt,  \r\n" + 
							"'' AS POID,  \r\n" + 
							"BTTH.TIENTE_FK AS ttId,  \r\n" + 
							"BTTH.TIGIA AS TIGIA,  \r\n" + 
							"NGAYBUTTOAN AS NGAYDENHANTT,  \r\n" + 
							"'' SOHOPDONG,  \r\n" + 
							"'' SOHOPDONGNGOAI,  \r\n" +
							"N'' AS DIACHI  \r\n" +  
							
							"FROM  ERP_BUTTOANTONGHOP BTTH  \r\n" + 
							"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK  \r\n" + 
							"LEFT JOIN (\r\n" + 
							"	SELECT HOADON_FK, TT.doiTuongKhac_FK, SUM(TTHD.SOTIENTT) AS TIENTHANHTOAN \r\n" + 
							"	FROM ERP_THANHTOANHOADON_HOADON TTHD \r\n" + 
							"	INNER JOIN ERP_THANHTOANHOADON TT ON TTHD.THANHTOANHD_FK = TT.PK_SEQ \r\n" + 
							"	WHERE LOAIHD = 10 AND TT.TRANGTHAI <> 2 AND TT.TIENTE_FK = " + this.tienteId + " \r\n" + 
							"	GROUP BY HOADON_FK, TT.doiTuongKhac_FK  \r\n" + 
							")DATHANHTOAN ON DATHANHTOAN.HOADON_FK = BTTH.PK_SEQ AND DATHANHTOAN.doiTuongKhac_FK = " + this.doiTuongKhacId + " \r\n" + 
							"WHERE BTTH.TIENTE_FK =  " + this.tienteId + " AND CONGTY_FK =  " + this.ctyId + "   \r\n" + 
							"AND BTTH.TRANGTHAI = 1  \r\n" + 
							"AND (  \r\n" + 
							"BTTHCT.doiTuongKhac_FK =  "+this.doiTuongKhacId+"   \r\n" + 
							")  \r\n" + 
							"AND CO > 0 \r\n" ;

					if (this.id.length() > 0) {
						query += " and BTTH.pk_seq not in " +
								 " (" +
								 "	select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + " AND LOAIHD = 10 " +
								 "  AND (SELECT NCC_FK FROM ERP_THANHTOANHOADON WHERE PK_SEQ = "	+ this.id + ") = " + this.doiTuongKhacId + " " +
								") \n";
					}
					query += 
							"AND (" +
							"		(BTTH.TIENTE_FK= '100000' AND BTTHCT.CO - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) " +
							" 		OR \n " +
							"		(BTTH.TIENTE_FK<> '100000' AND BTTHCT.GIATRINT - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) \n " +
							") ";
					
					//END LOAI HD: 10 - BUTTOANTONGHOP
					
					// LOAIHD: 6 - DENGHITHANHTOAN
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENAVAT as sotiengoc, \n"
								+ "        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"
								+ "		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, isnull(mh.SOPO, '') SOHOPDONG, isnull(mh.SOHOPDONG, '') SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI   \n"
								+ " from ERP_MUAHANG mh \n"
								+ " 	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"
								+ " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"
								+ " 	inner join erp_doiTuongKhac kh on kh.PK_SEQ = mh.doiTuongKhac_FK \n"
								+ " where   mh.doiTuongKhac_FK = '" + this.doiTuongKhacId + "' and tt.THANHTOANHD_FK = '"
								+ this.id + "'  \n";

					}
					query += " UNION ALL \n";
					query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon, mh.TONGTIENAVAT as sotiengoc \n"
							+ " 		, mh.TONGTIENAVAT - isnull(DATT.SOTIENTT,0)   as SOTIENAVAT ,0 as sotientt, '' as POID  \n"
							+ " 		,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N''   \n"
							+ " from ERP_MUAHANG mh \n"	
							+ " 	INNER JOIN erp_doiTuongKhac kh on kh.PK_SEQ = mh.doiTuongKhac_FK \n"
							+ " 	LEFT JOIN \n" 
							+ "       ( \n"
							+ "         SELECT tt.HOADON_FK , SUM(tt.SOTIENTT) as SOTIENTT "
							+ "         FROM ERP_THANHTOANHOADON_HOADON tt  \n"
							+ "              INNER JOIN ERP_THANHTOANHOADON t on tt.THANHTOANHD_FK = t.PK_SEQ "
							+ "         WHERE tt.LOAIHD = 6 AND t.TRANGTHAI != 2 and t.CONGTY_FK  = " + this.ctyId 
							+ "         GROUP BY tt.HOADON_FK "
							+ "        ) DATT ON DATT.HOADON_FK = mh.PK_SEQ \n"
							+ " 		LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ " 		LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"							
							+ " where mh.NGAYMUA >='2017-01-01' AND mh.CONGTY_FK = " + this.ctyId 
							+ " AND mh.TRANGTHAI = 1 and mh.doiTuongKhac_FK = '"+ this.doiTuongKhacId + "' \n" 
							+ " 		and ( tt.CONLAI is null or  \n"
							+ "           	(tt.CONLAI > 0 \n" 
							+ "				and mh.TIENTE_FK = " + this.tienteId + " \n"
							+ " 			and tt.HOADON_FK not in \n"
							+ "				(select distinct tt.HOADON_FK \n" 
							+ "				 from ERP_MUAHANG mh \n"
							+ "				      left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ "				      left join erp_doiTuongKhac kh on kh.PK_SEQ = mh.doiTuongKhac_FK \n"
							+ "				where mh.TRANGTHAI = 1 and mh.doiTuongKhac_FK = '" + this.doiTuongKhacId
							+ "' and tt.CONLAI = 0 and mh.TIENTE_FK = " + this.tienteId + " and mh.CONGTY_FK = "+ this.ctyId + " ) \n" 
							+ "  			and tt.THANHTOANHD_FK in \n"
							+ "				(select MAX(tt.THANHTOANHD_FK) \n" 
							+ "				 from Erp_MuaHang mh  \n"
							+ "				    left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ " 				    left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"
							+ "				    left join erp_doiTuongKhac kh on kh.PK_SEQ = mh.doiTuongKhac_FK \n"
							+ "				where mh.TRANGTHAI = 1 and mh.LOAIHANGHOA_FK = '2' and TYPE = '1' and mh.TIENTE_FK = "
							+ this.tienteId + "  and  mh.doiTuongKhac_FK =  '" + this.doiTuongKhacId
							+ "' and t.TRANGTHAI<>2 and mh.CONGTY_FK = " + this.ctyId + " 	\n" 
							+ "	 			group by tt.HOADON_FK ) ) ) \n"
							+ " 		and  mh.LOAIHANGHOA_FK = '2' and mh.TYPE ='1' and isnull(mh.ISDNTT,0) = '1' \n";
					if (this.id.length() > 0) {
						query += " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + " AND LOAIHD = 6 ) \n";
					}
					query += "       and  (mh.TONGTIENAVAT - isnull(t.SOTIENTT,0)   ) > 0 \n";
				}
				
				if (this.khachHang_NPP_FK != null && this.khachHang_NPP_FK.trim().length() > 0)
				{
					
					//LOAI HD: 10 - BUTTOANTONGHOP
					if (this.id.length() > 0) {
						query += "SELECT 10 AS LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, TT.SOHOADON AS SOHOADON,  N'Bút toán tổng hợp'  AS KYHIEU , BTTH.NGAYBUTTOAN AS NGAYHOADON ,  \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN BTTHCT.CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) ELSE BTTHCT.GIATRINT END AS SOTIENGOC,       \r\n" + 
								"CASE WHEN TTHD.TIENTE_FK= '100000' THEN TT.SOTIENAVAT ELSE TT.SOTIENNT END AS SOTIENAVAT,      \r\n" + 
								"TT.SOTIENTT , '' AS POID ,BTTH.TIENTE_FK AS TTID, BTTH.TIGIA AS TIGIA, NGAYBUTTOAN AS NGAYDENHANTT, '' SOHOPDONG , '' SOHOPDONGNGOAI, ISNULL(TT.DIACHI, '') AS DIACHI       \r\n" + 
								"FROM ERP_BUTTOANTONGHOP BTTH         \r\n" + 
								"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON_HOADON TT ON TT.HOADON_FK = BTTH.PK_SEQ  AND TT.LOAIHD = 10      \r\n" + 
								"INNER JOIN ERP_THANHTOANHOADON TTHD ON TT.THANHTOANHD_FK = TTHD.PK_SEQ       \r\n" + 
								"WHERE (BTTHCT.khachHang_FK = " + this.khachHang_NPP_FK + " and BTTHCT.isNPP = 1)\r\n" + 
								"AND TT.THANHTOANHD_FK = "+this.id;
						query += " UNION ALL \n";
					}
					query += 
							"SELECT 10 LOAIHD,'' AS LOAITHUE, BTTH.PK_SEQ, \n " +
							"CASE WHEN BTTH.MACHUNGTU IS NOT NULL THEN BTTH.MACHUNGTU ELSE CONVERT(NVARCHAR, BTTH.PK_SEQ) END AS SOHOADON,  \r\n" + 
							"N'Bút toán tổng hợp' KYHIEU,  \r\n" + 
							"NGAYBUTTOAN AS NGAYHOADON,  \r\n" + 
							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN BTTHCT.CO ELSE BTTHCT.GIATRINT END AS SOTIENGOC,  \r\n" + 

							"CASE WHEN BTTH.TIENTE_FK= '100000' THEN \n " +
							"CO + ISNULL((SELECT SUM(ISNULL(TIENTHUE,0)) FROM ERP_BUTTOANTONGHOP_CHITIET_HOADON hd WHERE hd.BTTH_FK =BTTH.PK_SEQ AND hd.PKSEQ = BTTHCT.PKSEQ),0) - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0)  \r\n" +
							"ELSE " +
							" BTTHCT.GIATRINT - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) \n " +
							"END AS SOTIENAVAT, \n " +
							
							"0 as sotientt,  \r\n" + 
							"'' AS POID,  \r\n" + 
							"BTTH.TIENTE_FK AS ttId,  \r\n" + 
							"BTTH.TIGIA AS TIGIA,  \r\n" + 
							"NGAYBUTTOAN AS NGAYDENHANTT,  \r\n" + 
							"'' SOHOPDONG,  \r\n" + 
							"'' SOHOPDONGNGOAI,  \r\n" +
							"N'' AS DIACHI  \r\n" +  
							
							"FROM  ERP_BUTTOANTONGHOP BTTH  \r\n" + 
							"INNER JOIN ERP_BUTTOANTONGHOP_CHITIET BTTHCT ON BTTH.PK_SEQ = BTTHCT.BUTTOANTONGHOP_FK  \r\n" + 
							"LEFT JOIN (\r\n" + 
							"	SELECT HOADON_FK, TT.khachHang_NPP_FK, SUM(TTHD.SOTIENTT) AS TIENTHANHTOAN \r\n" + 
							"	FROM ERP_THANHTOANHOADON_HOADON TTHD \r\n" + 
							"	INNER JOIN ERP_THANHTOANHOADON TT ON TTHD.THANHTOANHD_FK = TT.PK_SEQ \r\n" + 
							"	WHERE LOAIHD = 10 AND TT.TRANGTHAI <> 2 AND TT.TIENTE_FK = " + this.tienteId + " \r\n" + 
							"	GROUP BY HOADON_FK, TT.khachHang_NPP_FK \r\n" + 
							")DATHANHTOAN ON DATHANHTOAN.HOADON_FK = BTTH.PK_SEQ AND DATHANHTOAN.khachHang_NPP_FK = " + this.khachHang_NPP_FK + " \r\n" + 
							"WHERE BTTH.TIENTE_FK =  " + this.tienteId + " AND CONGTY_FK =  " + this.ctyId + "   \r\n" + 
							"AND BTTH.TRANGTHAI = 1  \r\n" + 
							"AND (  \r\n" + 
							" BTTHCT.isNPP = 1 and BTTHCT.khachHang_fk =  "+this.khachHang_NPP_FK+"   \r\n" + 
							")  \r\n" + 
							"AND CO > 0 \r\n" ;

					if (this.id.length() > 0) {
						query += " and BTTH.pk_seq not in " +
								 " (" +
								 "	select HOADON_FK from ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + " AND LOAIHD = 10 " +
								 "  AND (SELECT khachHang_NPP_FK FROM ERP_THANHTOANHOADON WHERE PK_SEQ = "	+ this.id + ") = " + this.khachHang_NPP_FK + " " +
								") \n";
					}
					query += 
							"AND (" +
							"		(BTTH.TIENTE_FK= '100000' AND BTTHCT.CO - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) " +
							" 		OR \n " +
							"		(BTTH.TIENTE_FK<> '100000' AND BTTHCT.GIATRINT - ISNULL(DATHANHTOAN.TIENTHANHTOAN, 0) > 0) \n " +
							") ";
					
					//END LOAI HD: 10 - BUTTOANTONGHOP
					
					// LOAIHD: 6 - DENGHITHANHTOAN
					if (this.id.length() > 0) {
						query += " UNION ALL \n";
						query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon , mh.TONGTIENAVAT as sotiengoc, \n"
								+ "        CASE WHEN tthd.TIENTE_FK= '100000' THEN tt.SOTIENAVAT ELSE tt.SOTIENNT END as SOTIENAVAT, \n"
								+ "		 tt.SOTIENTT , '' as POID ,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA, mh.ngaydenhantt, isnull(mh.SOPO, '') SOHOPDONG, isnull(mh.SOHOPDONG, '') SOHOPDONGNGOAI, ISNULL(tt.DIACHI, '') DIACHI   \n"
								+ " from ERP_MUAHANG mh \n"
								+ " 	inner join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ  AND TT.LOAIHD = 6 \n"
								+ " 	inner join ERP_THANHTOANHOADON tthd on tt.THANHTOANHD_FK = tthd.PK_SEQ  \n"
								+ " 	inner join nhaPhanPhoi kh on kh.PK_SEQ = mh.khachHang_NPP_FK \n"
								+ " where   mh.khachHang_NPP_FK = '" + this.khachHang_NPP_FK + "' and tt.THANHTOANHD_FK = '"
								+ this.id + "'  \n";

					}
					query += " UNION ALL \n";
					query += " select distinct 6 as LOAIHD,'' AS LOAITHUE, mh.PK_SEQ, cast(mh.SOPO as nvarchar(50)) as sohoadon,  N'Đề nghị thanh toán'  AS KYHIEU , mh.NGAYMUA as ngayhoadon, mh.TONGTIENAVAT as sotiengoc \n"
							+ " 		, mh.TONGTIENAVAT - isnull(DATT.SOTIENTT,0)   as SOTIENAVAT ,0 as sotientt, '' as POID  \n"
							+ " 		,mh.TIENTE_FK as ttId, mh.tygiaquydoi as TIGIA,  mh.ngaydenhantt, '' SOHOPDONG, '' SOHOPDONGNGOAI, N''   \n"
							+ " from ERP_MUAHANG mh \n"	
							+ " 	INNER JOIN nhaPhanPhoi kh on kh.PK_SEQ = mh.khachHang_NPP_FK \n"
							+ " 	LEFT JOIN \n" 
							+ "       ( \n"
							+ "         SELECT tt.HOADON_FK , SUM(tt.SOTIENTT) as SOTIENTT "
							+ "         FROM ERP_THANHTOANHOADON_HOADON tt  \n"
							+ "              INNER JOIN ERP_THANHTOANHOADON t on tt.THANHTOANHD_FK = t.PK_SEQ "
							+ "         WHERE tt.LOAIHD = 6 AND t.TRANGTHAI != 2 and t.CONGTY_FK  = " + this.ctyId 
							+ "         GROUP BY tt.HOADON_FK "
							+ "        ) DATT ON DATT.HOADON_FK = mh.PK_SEQ \n"
							+ " 		LEFT JOIN ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ " 		LEFT JOIN ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk  \n"							
							+ " where mh.NGAYMUA >='2017-01-01' AND mh.CONGTY_FK = " + this.ctyId 
							+ " AND mh.TRANGTHAI = 1 and mh.khachHang_NPP_FK = '"+ this.khachHang_NPP_FK + "' \n" 
							+ " 		and ( tt.CONLAI is null or  \n"
							+ "           	(tt.CONLAI > 0 \n" 
							+ "				and mh.TIENTE_FK = " + this.tienteId + " \n"
							+ " 			and tt.HOADON_FK not in \n"
							+ "				(select distinct tt.HOADON_FK \n" 
							+ "				 from ERP_MUAHANG mh \n"
							+ "				      left join ERP_THANHTOANHOADON_HOADON tt on  tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ "				      left join nhaPhanPhoi kh on kh.PK_SEQ = mh.khachHang_NPP_FK \n"
							+ "				where mh.TRANGTHAI = 1 and mh.khachHang_NPP_FK = '" + this.khachHang_NPP_FK
							+ "' and tt.CONLAI = 0 and mh.TIENTE_FK = " + this.tienteId + " and mh.CONGTY_FK = "+ this.ctyId + " ) \n" 
							+ "  			and tt.THANHTOANHD_FK in \n"
							+ "				(select MAX(tt.THANHTOANHD_FK) \n" 
							+ "				 from Erp_MuaHang mh  \n"
							+ "				    left join ERP_THANHTOANHOADON_HOADON tt on tt.HOADON_FK = mh.PK_SEQ AND TT.LOAIHD = 6 \n"
							+ " 				    left join ERP_THANHTOANHOADON t on  t.pk_seq = tt.thanhtoanhd_fk \n"
							+ "				    left join nhaPhanPhoi kh on kh.PK_SEQ = mh.khachHang_NPP_FK \n"
							+ "				where mh.TRANGTHAI = 1 and mh.LOAIHANGHOA_FK = '2' and TYPE = '1' and mh.TIENTE_FK = "
							+ this.tienteId + "  and  mh.khachHang_NPP_FK =  '" + this.khachHang_NPP_FK
							+ "' and t.TRANGTHAI<>2 and mh.CONGTY_FK = " + this.ctyId + " 	\n" 
							+ "	 			group by tt.HOADON_FK ) ) ) \n"
							+ " 		and  mh.LOAIHANGHOA_FK = '2' and mh.TYPE ='1' and isnull(mh.ISDNTT,0) = '1' \n";
					if (this.id.length() > 0) {
						query += " and mh.PK_SEQ not in (select HOADON_FK from  ERP_THANHTOANHOADON_HOADON where THANHTOANHD_FK = "	+ this.id + " AND LOAIHD = 6 ) \n";
					}
					query += "       and  (mh.TONGTIENAVAT - isnull(t.SOTIENTT,0)   ) > 0 \n";
				}
				
				System.out.println("---------------------Query khoi tao hoa don------------  :\n " + query + "\n------------------------------------");
				query=this.changeQuery(query, " ngayhoadon asc, sohoadon asc ");
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if (rsHoadon != null) {
					try {
						IHoadon hd = null;
						while (rsHoadon.next()) {
							String doituong = "";
							String doituongId = "";
							String madoituong = "";
							String loaiThue = rsHoadon.getString("LOAITHUE");
							String id = rsHoadon.getString("pk_seq");
							String kyhieu = rsHoadon.getString("kyhieu");
							String sohoadon = rsHoadon.getString("sohoadon");
							String ngayhd = rsHoadon.getString("ngayhoadon");
							String ngaydenhantt = rsHoadon.getString("ngaydenhantt");
							String tienteId = rsHoadon.getString("TTID");
							String sotiengoc = "";
							String avat = "";
							String loaihd = rsHoadon.getString("LOAIHD");
							String sohopdong = rsHoadon.getString("sohopdong");
							String sohopdongngoai = rsHoadon.getString("sohopdongngoai");
							String diaChi = rsHoadon.getString("DIACHI");
							

							if (tienteId.equals("100000")) {
							avat = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");
							} else {
								avat = ("" + rsHoadon.getDouble("sotienAVAT") * rsHoadon.getDouble("TIGIA")).replaceAll(",", "");
							}

//							if (tienteId.equals("100000")) {
								sotiengoc = ("" + rsHoadon.getDouble("sotiengoc")).replaceAll(",", "");
//							} else {
//								sotiengoc = ("" + rsHoadon.getDouble("sotiengoc") * rsHoadon.getDouble("TIGIA")).replaceAll(",", "");
//							}

							//String sotienNT = ("" + rsHoadon.getDouble("sotiengoc")).replaceAll(",", "");
							String sotienNT = ("" + rsHoadon.getDouble("sotienAVAT")).replaceAll(",", "");
							
							String dathanhtoan = ("" + rsHoadon.getDouble("SOTIENTT")).replaceAll(",", "");
							String tigia = rsHoadon.getString("TIGIA").replaceAll(",", "");

							if (this.bophanId.trim().length() > 0) {
								doituong = rsHoadon.getString("DOITUONG");
								doituongId = rsHoadon.getString("DOITUONG_FK");
								madoituong = rsHoadon.getString("MADOITUONG");
							}

							hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, sotienNT, dathanhtoan, tienteId, diaChi);
							hd.setSoTienGoc2(sotiengoc);
							hd.setSoPO(rsHoadon.getString("POID"));
							hd.setTigia(tigia);
							hd.setNgaydenhanTT(ngaydenhantt);
							hd.setLoaihd1(loaihd);
							hd.setSohopdong(sohopdong);
							hd.setLoaiThue(loaiThue);
							hd.setSohopdongNGOAI(sohopdongngoai);

							if (this.bophanId.trim().length() > 0) {
								hd.setDoituong(doituong);
								hd.setDoituongId(doituongId);
								hd.setMadoituong(madoituong);
							}

							hdList.add(hd);
						}
						rsHoadon.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				this.hoadonList = hdList;
				System.out.println("So hoa don: " + this.hoadonList.size());
			}

			if ((this.nhomNCCCNId.length() > 1) && this.htttId.length() > 0 && this.hoadonList.size() <= 0) {
				NumberFormat formatter = new DecimalFormat("#,###,###");

				String query = "";
				if (this.LoaiThanhToan.equals("1")) // HÓA ĐƠN
				{
					if (this.id.length() > 0) {
						query += " select p.ncc_fk , b.pk_seq, b.kyhieu, b.sohoadon, b.ngayhoadon, b.sotienAVAT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, SOTIENTT, "
								+ " ISNULL( (  "
								+ "Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  "
								+ "From ERP_HOADONNCC_PHIEUNHAP PO1   " + "Where PO1.hoadonncc_fk = b.pk_seq "
								+ "For XML PATH ('')) [phongbanChon_fk]  " + "From ERP_HOADONNCC_PHIEUNHAP PO  "
								+ "where PO.hoadonncc_fk = b.pk_seq " + "), '' )   as POID, ISNULL(DIACHI, '') AS DIACHI  \n"
								+ "from ERP_THANHTOANHOADON_HOADON a "
								+ "inner join ERP_HOADONNCC b on a.hoadon_fk = b.pk_seq "
								+ "    inner join ERP_PARK p on b.park_fk = p.pk_seq  " + // them join voi ERP_PARK de lay ma ncc
								"left join	" + "( select TTHD.hoadon_fk, sum(TTHD.SOTIENTT) as tongthanhtoan "
								+ "from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ "
								+ "where TT.trangthai !=2 and TTHD.thanhtoanhd_fk != '" + this.id + "' " + // sua trang thai !=2 thanh =1
								" and TTHD.hoadon_fk in (select hoadon_fk " + "from ERP_THANHTOANHOADON_HOADON "
								+ "where thanhtoanhd_fk = '" + this.id + "') "
								+ "group by hoadon_fk) thanh_toan on a.hoadon_fk = thanh_toan.hoadon_fk "
								+ "where a.thanhtoanhd_fk = '" + this.id + "' " + " union all ";
					}

					query += "(select hoadon.ncc_fk , hoadon.pk_seq, hoadon.kyhieu, hoadon.sohoadon, hoadon.ngayhoadon, "
							+ " hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, 0 as sotientt ,"
							+ " ISNULL( (  "
							+ "Select distinct ( Select cast(PO1.muahang_fk as varchar(10)) + ',' AS [text()]  "
							+ "From ERP_HOADONNCC_PHIEUNHAP PO1   " + "Where PO1.hoadonncc_fk = hoadon.pk_seq "
							+ "For XML PATH ('')) [phongbanChon_fk]  " + "From ERP_HOADONNCC_PHIEUNHAP PO  "
							+ "where PO.hoadonncc_fk = hoadon.pk_seq " + "), '' )   as POID " + "from ( "
							+ "select b.ncc_fk , a.pk_seq, a.kyhieu, a.sohoadon, a.ngayhoadon, a.sotienAVAT "
							+ "from ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq "
							+ "where b.ncc_fk in ( select NCC_FK from NHOMNHACUNGCAPCN_NCC where NHOMNHACUNGCAPCN_FK = '"
							+ this.nhomNCCCNId + "')" + " and b.trangthai = '2' and a.trangthai = '0' ";
					if (this.id.length() > 0) {
						query += "and a.pk_seq not in (select hoadon_fk from ERP_THANHTOANHOADON_HOADON where thanhtoanhd_fk = '"
								+ this.id + "') ";
					}
					query += ") hoadon " + "left join ( "
							+ "select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  "
							+ "from ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ "
							+ "where TT.TRANGTHAI !=2 and TTHD.HOADON_FK in (select pk_seq from ERP_HOADONNCC where trangthai = 0)  "
							+ // sua trang thai !=2 thanh =1
							"group by HOADON_FK " + ") dathanhtoan " + "on hoadon.pk_seq = dathanhtoan.hoadon_fk "
							+ "where hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') > 0 "
							+ " and hoadon.pk_seq not in ( "
							+ "	select distinct td.HOADON_FK from ERP_THANHTOANHOADON_HOADON td "
							+ "	inner join ERP_THANHTOANHOADON t on t.PK_SEQ = td.THANHTOANHD_FK "
							+ "	where t.TRANGTHAI =0) )	";
				}
				System.out.println("Query khoi tao hoa don: " + query);
				query=this.changeQuery(query, " ngayhoadon asc, sohoadon asc ");
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if (rsHoadon != null) {
					try {
						IHoadon hd = null;
						while (rsHoadon.next()) {
							String ncc_fk = rsHoadon.getString("ncc_fk");
							String id = rsHoadon.getString("pk_seq");
							String kyhieu = rsHoadon.getString("kyhieu");
							String sohoadon = rsHoadon.getString("sohoadon");
							String ngayhd = rsHoadon.getString("ngayhoadon");
							String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
//							String diaChi = rsHoadon.getString("DIACHI");
							String dathanhtoan = "";
							if (this.id.length() > 0) {
								dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
							}

							hd = new Hoadon(ncc_fk, id, kyhieu, sohoadon, ngayhd, avat, "0", dathanhtoan, "", "");
							hd.setSoPO(rsHoadon.getString("POID"));
							hdList.add(hd);
						}
						rsHoadon.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				this.hoadonList = hdList;
			}
		}
	}

	private void initNppList() {
		String query = "";
		query = 
			"select PK_SEQ, isNull(npp.ma, '') + ' - ' + isNull(npp.maFast, '') + ' - ' + isNull(npp.TEN, '') ten\n" +
			"from nhaPhanPhoi npp\n" +
			"where npp.TRANGTHAI = 1 and isKhachHang = 0 and npp.pk_seq != 1\n";
		
		System.out.println(query);
		Erp_Item.getListFromQuery(db, query, this.nppList);
	}
	
	private void initLoaiThanhToanList() 
	{
		this.loaiThanhToanList.clear();
		this.loaiThanhToanList.add(new Erp_Item("0", "Nhân viên"));
		this.loaiThanhToanList.add(new Erp_Item("1", "Nhà cung cấp"));
		this.loaiThanhToanList.add(new Erp_Item("2", "Khách hàng"));
		this.loaiThanhToanList.add(new Erp_Item("3", "Khác"));
		this.loaiThanhToanList.add(new Erp_Item("5", "Chi trả hợp đồng tài trợ"));
		if (this.doiTuongKhacId != null && this.doiTuongKhacId.trim().length() > 0)
			this.loaiThanhToanList.add(new Erp_Item("6", "Đối tượng khác"));
		this.loaiThanhToanList.add(new Erp_Item("7", "Chi nhánh"));
	}

	private void initDoiTuongKhacList() {
		String query = "";
		query = 
			"select PK_SEQ, dt.MADOITUONG + ' - ' + dt.TENDOITUONG ten\n" +
			"from ERP_DoiTuongKhac dt\n" +
			"where dt.TRANGTHAI = 1\n";
		
		System.out.println(query);
		Erp_Item.getListFromQuery(db, query, this.doiTuongKhacList);
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBclose() {
		this.db.shutDown();
	}

	public String getLoaiThanhToan() {
		return this.LoaiThanhToan;
	}

	public void setLoaiThanhToan(String loaithanhtoan) {
		this.LoaiThanhToan = loaithanhtoan;
	}

	public String getDoiTuongTamUng() {
		return this.DoiTuongTamUng;
	}

	public void setDoiTuongTamUng(String DoiTuongTamUng) {
		this.DoiTuongTamUng = DoiTuongTamUng;
	}

	public String getNhanVienId() {
		return this.NhanvienId;
	}

	public void setNhanVienId(String nvId) {
		this.NhanvienId = nvId;
	}

	public String Gettenhienthi() {
		return this.TenHienThi;
	}

	public void settenhienthi(String tenhienthi) {
		this.TenHienThi = tenhienthi;
	}

	public String getDiachi() {
		return this.diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}

	public String delete() {
		try {
			db.getConnection().setAutoCommit(false);

			db.update("update ERP_ThanhToanHoaDon set trangthai = '2' where pk_seq = '" + this.id + "'");

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();

			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			db.shutDown();
			return "Khong the xoa ThanhToanHoaDon";
		}
	}

	public String delete(String IdThanhToan) {
		try {
			db.getConnection().setAutoCommit(false);

			db.update("update ERP_ThanhToanHoaDon set trangthai = '2' where pk_seq = '" + IdThanhToan + "'");

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			db.shutDown();

			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			db.shutDown();
			return "Khong the xoa ThanhToanHoaDon";
		}
	}

	

	public void setCheckThanhtoantuTV(String checkThanhtoantuTV) {
		this.checkThanhtoantuTV = checkThanhtoantuTV;
	}

	public String getCheckThanhtoantuTV() {
		return this.checkThanhtoantuTV;
	}

	public String getKhachhangId() {

		return this.khachhangId;
	}

	public void setKhachhangId(String khachhangId) {

		this.khachhangId = khachhangId;
	}

	public ResultSet getNhanvienRs() {

		return this.NhanvienRs;
	}

	public void setNhanvienRs(ResultSet NhanvienRs) {

		this.NhanvienRs = NhanvienRs;
	}

	public ResultSet getKhachhangRs() {
		return this.khachhangRs;
	}

	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;

	}

	public ResultSet getNhomNCCRs() {
		return this.nhomNCCRs;
	}

	public void setNhomNCCRs(ResultSet nhomNCCRs) {

		this.nhomNCCRs = nhomNCCRs;
	}

	public String getBophanTen() {
		return this.bophanTen;
	}

	public void setBophanTen(String bophanTen) {
		this.bophanTen = bophanTen;
	}

	public String getBophanId() {
		return this.bophanId;
	}

	public void setBophanId(String bophanId) {
		this.bophanId = bophanId;
	}

	public String getnppdangnhap() {

		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) {

		this.nppdangnhap = nppdangnhap;
	}

	public String getisNPP() {

		return this.isNpp;
	}

	public void setisNPP(String isNPP) {

		this.isNpp = isNPP;
	}

	public String[] getMahd() {

		return this.Mahd;
	}

	public void setMahd(String[] Mahd) {

		this.Mahd = Mahd;
	}

	public String[] getMauhd() {

		return this.Mauhd;
	}

	public void setMauhd(String[] Mauhd) {

		this.Mauhd = Mauhd;
	}

	public String[] getKyhieuhd() {

		return this.Kyhieuhd;
	}

	public void setKyhieuhd(String[] Kyhieuhd) {

		this.Kyhieuhd = Kyhieuhd;
	}

	public String[] getSohd() {

		return this.Sohd;
	}

	public void setSohd(String[] Sohd) {

		this.Sohd = Sohd;
	}

	public String[] getNgayhd() {

		return this.Ngayhd;
	}

	public void setNgayhd(String[] Ngayhd) {

		this.Ngayhd = Ngayhd;
	}

	public String[] getTenNCChd() {

		return this.TenNCChd;
	}

	public void setTenNCChd(String[] TenNCChd) {

		this.TenNCChd = TenNCChd;
	}

	public String[] getMSThd() {

		return this.MSThd;
	}

	public void setMSThd(String[] MSThd) {

		this.MSThd = MSThd;
	}

	public String[] getTienhanghd() {

		return this.Tienhanghd;
	}

	public void setTienhanghd(String[] Tienhanghd) {

		this.Tienhanghd = Tienhanghd;
	}

	public String[] getThuesuathd() {

		return this.Thuesuathd;
	}

	public void setThuesuathd(String[] Thuesuathd) {

		this.Thuesuathd = Thuesuathd;
	}

	public String[] getTienthuehd() {

		return this.Tienthuehd;
	}

	public void setTienthuehd(String[] Tienthuehd) {

		this.Tienthuehd = Tienthuehd;
	}

public void setPhongBanIds(String[] PhongBanIds) {
		
		this.PhongBanIds = PhongBanIds;
	}

	
	public ResultSet getPhongBanRs() {
		
		return this.PhongBanRs;
	}

	
	public void setPhongBanRs(ResultSet PhongBanRs) {
		
		this.PhongBanRs = PhongBanRs;
	}

	
	public int getCount()
	{
		return this.count;
	}
	
	public void setCount(int count)
	{
	this.count=count;	
	}
	

	public ResultSet getTaiKhoanKTRs() {
		return TaiKhoanKTRs;
	}

	
	public String[] getTaiKhoanIds()
	{

		return this.TaiKhoanIds;
	}

	public void setTaiKhoanIds(String[] TaiKhoanIds)
	{
		this.TaiKhoanIds = TaiKhoanIds;
	}

	public String[] getDcIds()
	{

		return this.dcIds;
	}

	public void setDcIds(String[] dcIds)
	{
		this.dcIds = dcIds;
	}
	

	public String[] getLoais() 
	{
		return this.loais;
	}

	public void setLoais(String[] loais) 
	{
		this.loais = loais;
	}

	public ResultSet getDoituongRs(String taikhoanId) 
	{
		this.dungchos = "";
		
		if(taikhoanId.indexOf("_") >= 0){
			taikhoanId = taikhoanId.substring(0, taikhoanId.indexOf("_"));
		}
		String query = 
				" SELECT PK_SEQ,SOHIEUTAIKHOAN as MA,TENTAIKHOAN AS TEN, ISNULL(COTTCHIPHI,0)COTTCHIPHI, " +
				" ISNULL(DUNGCHOKHO, 0) DUNGCHOKHO, ISNULL(DUNGCHONGANHANG, 0) DUNGCHONGANHANG, ISNULL(DUNGCHONCC, 0) DUNGCHONCC, ISNULL(DUNGCHOTAISAN, 0) DUNGCHOTAISAN, " +
				" ISNULL(DUNGCHOKHACHHANG, 0) DUNGCHOKHACHHANG, ISNULL(DUNGCHONHANVIEN, 0) DUNGCHONHANVIEN, ISNULL(DUNGCHODOITUONGKHAC, 0) DUNGCHODOITUONGKHAC " +
				" FROM ERP_TAIKHOANKT "+
				" WHERE PK_SEQ = '"+ taikhoanId +"' ";
		System.out.println(" Câu query "+query);
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					// Dùng cho : 1 Kho, 2 NCC, 3 Ngân hàng, 4 Tài Sản, 5 Khách hàng, 6 TTCP, 7 Nhân viên
					
					if(rs.getString("DUNGCHOKHO").equals("1")){
						dungchos += "1,";
					}
					if(rs.getString("DUNGCHONCC").equals("1")){
						dungchos += "2,";
					}
					if(rs.getString("DUNGCHONGANHANG").equals("1")){
						dungchos += "3,";
					}
					if(rs.getString("DUNGCHOTAISAN").equals("1")){
						dungchos += "4,";
					}
					if(rs.getString("DUNGCHOKHACHHANG").equals("1")){
						dungchos += "5,";
					}
					if(rs.getString("COTTCHIPHI").equals("1")){
						dungchos += "6,";
					}
					if(rs.getString("DUNGCHONHANVIEN").equals("1")){
						dungchos += "7,";
					}

					if(rs.getString("DUNGCHODOITUONGKHAC").equals("1")){
						dungchos += "8,";
					}

				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		
		if(this.dungchos.trim().length() > 0) this.dungchos = this.dungchos.substring(0, this.dungchos.length()-1);
		
		String command = "";
		ResultSet rsDT = null;
		
		if(this.dungchos.trim().length() > 0)
		{
			String[] mang = this.dungchos.split(",");
			for(int i= 0; i < mang.length; i++)
			{
				if(mang[i].equals("1")){
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN, 1 AS LOAI FROM SANPHAM WHERE  TRANGTHAI=1";
				}
				if(mang[i].equals("2")){
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN, 2 AS LOAI FROM ERP_NHACUNGCAP WHERE  TRANGTHAI=1 and congty_fk = "+this.ctyId;
				}
				if(mang[i].equals("3")){
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN, 3 AS LOAI FROM ERP_NGANHANG WHERE  TRANGTHAI=1";
				}
				if(mang[i].equals("4")){
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, DIENGIAI AS TEN, 4 AS LOAI FROM ERP_TAISANCODINH  WHERE  TRANGTHAI=1";
				}
				if(mang[i].equals("5")){ // KHÁCH HÀNG
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += " SELECT CONVERT(VARCHAR, PK_SEQ) + '-0' AS PK_SEQ, ( '[KH] ' + MAFAST ) AS MA, TEN, 5 AS LOAI FROM KHACHHANG \n" +
					 			" WHERE  TRANGTHAI=1 AND CONGTY_FK IN ( "+this.ctyId+ " ) \n"+
					 			" UNION ALL \n"+
					 			" SELECT CONVERT(VARCHAR, PK_SEQ) + '-1' AS PK_SEQ, ( '[NPP] ' + MA ) AS MA, TEN, 5 AS LOAI FROM NHAPHANPHOI \n" +
					 			" WHERE  TRANGTHAI=1 AND TRUCTHUOC_FK IN (SELECT PK_SEQ FROM NHAPHANPHOI WHERE CONGTY_FK = " + this.ctyId + ") \n" +
					 			" UNION ALL \n"+
					 			" SELECT CONVERT(VARCHAR, PK_SEQ) + '-2' AS PK_SEQ, ( '[NV] ' + MA ) AS MA, TEN , 5 AS LOAI FROM ERP_NHANVIEN \n" +
								" WHERE  TRANGTHAI = 1  AND CONGTY_FK IN ( " + this.ctyId + " ) \n" +
								" ORDER BY TEN ";				 
				}
				if(mang[i].equals("6")){
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN, 6 AS LOAI FROM ERP_TRUNGTAMCHIPHI WHERE  TRANGTHAI=1";
				}
				if(mang[i].equals("7")){
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN, 7 AS LOAI FROM ERP_NHANVIEN WHERE  TRANGTHAI=1";
				}
				
				if(mang[i].equals("8")){
					 if(command.trim().length() > 0)  command += " UNION ALL ";
					 command += "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MADOITUONG AS MA, TENDOITUONG AS TEN, 8 AS LOAI FROM ERP_DOITUONGKHAC WHERE  TRANGTHAI=1";
				}

			}
		}
		
		System.out.println("Câu DT "+command);
		if(command.length() > 0) rsDT = this.db.get(command);
		
		return rsDT;
	}

	
	public String[] getKenhIds() {
		
		return this.KenhbhIds;
	}

	
	public void setKenhIds(String[] KenhIds) {
		
		this.KenhbhIds = KenhIds;
	}

	
	public ResultSet getKenhBhRs() {
		
		return this.KenhBhRs;
	}

	
	public void setKenhBhRs(ResultSet KenhBhRs) {
		
		this.KenhBhRs = KenhBhRs;
	}

	public String[] getPhongBanIds() {
			
			return this.PhongBanIds;
		}

	public ResultSet getTinhThanhRs() {
		
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, TEN AS TEN FROM TINHTHANH WHERE  TRANGTHAI = 1  ";
		System.out.println(query);
		
		return this.db.getScrol(query);
	}

	
	public void setTinhThanhRs(ResultSet TinhThanhRs) {
		
		this.TinhThanhRs = TinhThanhRs;
	}

	
	public String[] getSanphamIds() {
		
		return this.SanPhamIds;
	}

	
	public void setSanPhamIds(String[] SanPhamIds) {
		
		this.SanPhamIds = SanPhamIds;
	}

	
	public ResultSet getSanPhamRs() {
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA_FAST + ' - ' + TEN AS TEN FROM SANPHAM WHERE  TRANGTHAI = 1";
		
		System.out.println(query);
		return this.db.getScrol(query);
	}

	
	public void setSanPhamRs(ResultSet SanPhamRs) {
		
		this.SanphamRs = SanPhamRs;
	}

	public String[] getTinhThanhIds() {
		
		return this.TinhThanhIds;
	}

	
	public void setTinhThanhIds(String[] TinhThanhIds) {
		
		this.TinhThanhIds = TinhThanhIds;
	}

	public ResultSet getDiaBanRs() {
		
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, TEN AS TEN FROM diaban WHERE  TRANGTHAI = 1 ";
		System.out.println(query);
		
		return this.db.getScrol(query);
	}

	
	public void setDiaBanRs(ResultSet DiaBanRs) {
	
		this.DiabanRs = DiaBanRs;
	}

	
	public String[] getMavvIds() {
	
		return this.MavvIds;
	}

	
	public void setMavvIds(String[] MavvIds) {
	
		this.MavvIds = MavvIds;
	}

	
	public ResultSet getMavvRs() {
	
		String query =  "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, TEN AS TEN " +
						"FROM vuviec WHERE  TRANGTHAI = 1  AND CONGTY_FK = "+this.ctyId ;
		System.out.println(query);
		
		return this.db.getScrol(query);
	}

	
	public void setMavvRs(ResultSet MavvRs) {
	
		this.MavvRs = MavvRs;
	}

	
	public String[] getBenhVienIds() {
	
		return this.BenhvienIds;
	}

	
	public void setBenhVienIds(String[] BenhVienIds) {
	
		this.BenhvienIds = BenhVienIds;
	}

	
	public ResultSet getBenhVienRs() {
	
		// LẤY KHÁCH HÀNG LÀ LOẠI BỆNH VIỆN
		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MAFAST + ' - ' + TEN AS TEN FROM KHACHHANG WHERE  TRANGTHAI = 1 AND LCH_FK IN (100011) AND CONGTY_FK = "+this.ctyId;
				
		System.out.println(query);
		
		return this.db.getScrol(query);
	}

	
	public void setBenhVienRs(ResultSet BenhVienRs) {
	
		this.BenhvienRs = BenhVienRs;
	}

	public String[] getDiaBanIds() {
		
		return this.DiabanIds;
	}

	
	public void setDiaBanIds(String[] DiaBanIds) {
	
		this.DiabanIds = DiaBanIds;
	}


	public String[] getDiengiaihd() {
		
		return this.Diengiaihd;
	}


	public void setDiengiaihd(String[] Diengiaihd) {
		
		this.Diengiaihd = Diengiaihd;
	}

	public void setDaDuyet(int daDuyet) {
		this.daDuyet = daDuyet;
	}

	public int getDaDuyet() {
		return daDuyet;
	}
	
	public String getTenKhachHang() {
		return tenKhachHang;
	}

	public void setTenKhachHang(String tenKhachHang) {
		this.tenKhachHang = tenKhachHang;
	}

	public String getTenHinhThucThanhToan() {
		return tenHinhThucThanhToan;
	}

	public void setTenHinhThucThanhToan(String tenHinhThucThanhToan) {
		this.tenHinhThucThanhToan = tenHinhThucThanhToan;
	}

	public String getTenSoTaiKhoan() {
		return tenSoTaiKhoan;
	}

	public void setTenSoTaiKhoan(String tenSoTaiKhoan) {
		this.tenSoTaiKhoan = tenSoTaiKhoan;
	}

	public String getTenTienTe() {
		return tenTienTe;
	}

	public void setTenTienTe(String tenTienTe) {
		this.tenTienTe = tenTienTe;
	}

	public String getTenSoTaiKhoanTrichPhi() {
		return tenSoTaiKhoanTrichPhi;
	}

	public void setTenSoTaiKhoanTrichPhi(String tenSoTaiKhoanTrichPhi) {
		this.tenSoTaiKhoanTrichPhi = tenSoTaiKhoanTrichPhi;
	}

	public void setTrangThai(int trangThai) {
		this.trangThai = trangThai;
	}

	public int getTrangThai() {
		return trangThai;
	}
	
	public void setSoChungTu(String soChungTu) {
		this.soChungTu = soChungTu;
	}

	public String getSoChungTu() {
		return soChungTu;
	}
	
	public void setSoChungTu_Chu(String soChungTu_Chu) {
		this.soChungTu_Chu = soChungTu_Chu;
	}

	public String getSoChungTu_Chu() {
		return soChungTu_Chu;
	}

	public void setSoChungTu_So(String soChungTu_So) {
		this.soChungTu_So = soChungTu_So;
	}

	public String getSoChungTu_So() {
		return soChungTu_So;
	}
	@Override
	public String[] getTtcpIds() {
		return ttcpIds;
	}

	@Override
	public void setTtcpIds(String[] ttcpIds) {
		this.ttcpIds = ttcpIds;
	}

	@Override
	public String[] getDiaChiHd() {
		return diaChiHd;
	}

	@Override
	public void setDiaChiHd(String[] diaChiHd) {
		this.diaChiHd = diaChiHd;
	}
	@Override
	public ResultSet getTrungTamChiPhiRs(String taikhoanId) {
		
		/*String query = 	"SELECT PK_SEQ,MA,TEN FROM ERP_TRUNGTAMCHIPHI " +
		"WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + " " +
		"AND 1 = (SELECT ISNULL(COTTCHIPHI, 0) FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + tknoId + " AND CONGTY_FK = " + this.congtyId + ") ";*/

		//TRUNG TAM CHI PHI KHONG PHAN BIET CONG TY
		if(taikhoanId.indexOf("_") >= 0){
		taikhoanId = taikhoanId.substring(0, taikhoanId.indexOf("_"));
		}
		taikhoanId = taikhoanId.length() >= 0 ?  taikhoanId : "0";
		String query = 	
		"SELECT n.PK_SEQ, n.TEN as MA, n.DIENGIAI as TEN \n" +
		"FROM ERP_TRUNGTAMCHIPHI tt\n" +
		"inner join ERP_NHOMCHIPHI n on n.TTCHIPHI_FK = tt.PK_SEQ\n" +
		"WHERE tt.TRANGTHAI = 1  \n" +
		" AND n.TAIKHOAN_FK IN ( SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ IN ("+taikhoanId+") )\n " +
		"	AND 1 = (SELECT ISNULL(COTTCHIPHI, 0) FROM ERP_TAIKHOANKT WHERE PK_SEQ = " + taikhoanId + " AND CONGTY_FK = " + this.ctyId + ") \n";
		
		System.out.println("TTCP NO:\n" + query + "\n---------------------------------------------------------------");
		System.out.println("MA TAI KHOAN: " + taikhoanId );
		
		return this.db.getScrol(query);
	}
	
	public void setIsDNTT(String isDNTT) {
		this.isDNTT = isDNTT;
	}

	public String getIsDNTT() {
		return isDNTT;
	}
	
	public void reGetSoChungTu()
	{
		this.soChungTu_Chu = "UNC"+this.ngayghinhan.substring(5,7)+ this.ngayghinhan.substring(0, 4);
		this.soChungTu_So = geso.traphaco.center.util.Utility.getSoChungTuMax(db, "ERP_THANHTOANHOADON", "100001", this.ngayghinhan);
		this.soChungTu = soChungTu_Chu + soChungTu_So;
	}
	
	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}
	
	public void setDoiTuongKhacId(String doiTuongKhacId) {
		this.doiTuongKhacId = doiTuongKhacId;
	}

	public String getDoiTuongKhacId() {
		return doiTuongKhacId;
	}

	public void setDoiTuongKhacList(List<Erp_Item> doiTuongKhacList) {
		this.doiTuongKhacList = doiTuongKhacList;
	}

	public List<Erp_Item> getDoiTuongKhacList() {
		return doiTuongKhacList;
	}
	
	public void setLoaiThanhToanList(List<Erp_Item> loaiThanhToanList) {
		this.loaiThanhToanList = loaiThanhToanList;
	}

	public List<Erp_Item> getLoaiThanhToanList() {
		return loaiThanhToanList;
	}
	
	public static void main(String[] args) {
		dbutils db = new dbutils();
		UtilityKeToan util = new UtilityKeToan();
		ErpUynhiemchi ucn = new ErpUynhiemchi();
		ucn.db = db;
		try {
		
			String query = " select distinct ngayghinhan,nguoisua,PK_SEQ from ERP_THANHTOANHOADON \n"+
						   " where trangThai = 1 and httt_fk=100001 and nhanvien_fk is not null and  DNTT_FK IS NOT NULL " ;
//						   "in  \n"+
//						   
//						   
//						   " (select ps.SOCHUNGTU from ERP_PHATSINHKETOAN ps where ps.LOAICHUNGTU = N'Chi phí khác')  \n"+
//						   " and pk_seq in  \n"+
//						   " (select ps.SOCHUNGTU from ERP_PHATSINHKETOAN ps where ps.LOAICHUNGTU = N'Thanh toán hóa đơn')    \n";
			String msg = "";
			ResultSet rs = db.get(query);
			while (rs.next()) {
				ucn.setId(rs.getString("pk_seq"));
				ucn.setCtyId("100000");
				ucn.setUserId(rs.getString("nguoisua"));
				String ngayghinhan = rs.getString("ngayghinhan");
				String nam = ngayghinhan.substring(0, 4);
				String thang = ngayghinhan.substring(5, 7);
				util.setNam(nam);
				util.setThang(thang);
				msg = util.HuyUpdate_TaiKhoan(db, rs.getString("pk_seq"),
						"N'Thanh toán hóa đơn',N'Chi phí khác'");
				if (msg.length() > 0) {
					db.getConnection().rollback();
					System.out.println("Tạch :" + msg);
				}
				ucn.init();
				boolean check = ucn.chotTTHD(rs.getString("nguoisua"));
				if (!check) {
					System.out.println("Tạch" + ucn.getMsg());
				}
				System.out.println("Đã xong");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getKhachHang_NPP_FK() {
		return khachHang_NPP_FK;
	}

	public void setKhachHang_NPP_FK(String khachHang_NPP_FK) {
		this.khachHang_NPP_FK = khachHang_NPP_FK;
	}
	
	public List<Erp_Item> getNppList() {
		return nppList;
	}

	public void setNppList(List<Erp_Item> nppList) {
		this.nppList = nppList;
	}

	public String getDuyetchi() {
		return duyetchi;
	}

	public void setDuyetchi(String duyetchi) {
		this.duyetchi = duyetchi;
	}
	
	//DNTT_FK
	public String getDNTT_FK(){
		return this.DNTT_FK;
	}
		
	public void setDNTT_FK(String DNTT_FK){
		this.DNTT_FK =DNTT_FK;
	}
		
	//DNTU_FK
	public String getDNTU_FK(){
		return this.DNTU_FK;
	}
		
	public void setDNTU_FK(String DNTU_FK){
		this.DNTU_FK = DNTU_FK;
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getTienteId() {
		return this.tienteId;
	}

	public void setTienteId(String ttId) {
		this.tienteId = ttId;
	}

	public String getTrichphi() {
		return this.trichphi;
	}

	public void setTrichphi(String trichphi) {
		this.trichphi = trichphi;
	}

	public ResultSet getTienteRs() {

		return this.tienteRs;
	}

	public void setTienteRs(ResultSet tienteRs) {
		this.tienteRs = tienteRs;
	}

	public String getTigia() {
		return this.tigia;
	}

	public void setTigia(String tigia) {
		if (tigia.length() == 0) {
			String query = "SELECT TIGIAQUYDOI FROM ERP_TIGIA WHERE TUNGAY <= '" + this.ngayghinhan+ "' AND DENNGAY >= '" + this.ngayghinhan + "' AND TIENTE_FK = " + this.tienteId + "";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			try {
				if (rs != null) {
					if(rs.next()){
						this.tigia = rs.getString("TIGIAQUYDOI");
						rs.close();
					}
				}
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
			}
		} else {
			this.tigia = tigia;
		}
	}

	public String getChenhlech() {
		return this.chenhlechVND;
	}

	public void setChenhlech(String chenhlechVND) {
		this.chenhlechVND = chenhlechVND;
	}

	public String getDoituongdinhkhoanId() {
		return doituongdinhkhoanId;
	}

	public void setDoituongdinhkhoanId(String doituongdinhkhoanId) {
		this.doituongdinhkhoanId = doituongdinhkhoanId;
	}

	public String getMaTenDoiTuongDinhKhoan() {
		return MaTenDoiTuongDinhKhoan;
	}

	public void setMaTenDoiTuongDinhKhoan(String maTenDoiTuongDinhKhoan) {
		MaTenDoiTuongDinhKhoan = maTenDoiTuongDinhKhoan;
	}

	public String getDoiTuongDinhKhoan() {
		return DoiTuongDinhKhoan;
	}

	public void setDoiTuongDinhKhoan(String doiTuongDinhKhoan) {
		DoiTuongDinhKhoan = doiTuongDinhKhoan;
	}

	public String getDinhkhoannoId() {
		return dinhkhoannoId;
	}

	public void setDinhkhoannoId(String dinhkhoannoId) {
		this.dinhkhoannoId = dinhkhoannoId;
	}

	public String getDinhkhoanno() {
		return dinhkhoanno;
	}

	public void setDinhkhoanno(String dinhkhoanno) {
		this.dinhkhoanno = dinhkhoanno;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getDoiTuongChiPhiKhac() {
		return DoiTuongChiPhiKhac;
	}

	public void setDoiTuongChiPhiKhac(String doiTuongChiPhiKhac) {
		DoiTuongChiPhiKhac = doiTuongChiPhiKhac;
	}

	public String getNhomNCCCNId() {
		return nhomNCCCNId;
	}

	public void setNhomNCCCNId(String nhomNCCCNId) {
		this.nhomNCCCNId = nhomNCCCNId;
	}

	public String getNhomNCCCN() {
		return NhomNCCCN;
	}

	public void setNhomNCCCN(String nhomNCCCN) {
		NhomNCCCN = nhomNCCCN;
	}

	public ResultSet getSotkRs() {
		return this.sotkRs;
	}

	public void setSotkRs(ResultSet sotkRs) {
		this.sotkRs = sotkRs;
	}

	public ResultSet getSotkRs_tp() {
		return this.sotkRs_tp;
	}

	public void setSotkRs_tp(ResultSet sotkRs_tp) {
		this.sotkRs_tp = sotkRs_tp;
	}

	public String getTthdCurrent() {
		return tthdCurrent;
	}

	public void setTthdCurrent(String tthdCurrent) {
		this.tthdCurrent = tthdCurrent;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNgayghinhan() {
		return this.ngayghinhan;
	}

	public void setNgayghinhan(String ngayghinhan) {
		this.ngayghinhan = ngayghinhan;
	}

	public String getNccId() {
		return this.nccId;
	}

	public void setNccId(String nccId) {
		this.nccId = nccId;
	}

	public ResultSet getNccRs() {
		return this.nccRs;
	}

	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}

	public String getHtttId() {
		return this.htttId;
	}

	public void setHtttId(String htttId) {
		this.htttId = htttId;
	}

	public ResultSet getHtttRs() {
		return this.htttRs;
	}

	public void setHtttRs(ResultSet htttRs) {
		this.htttRs = htttRs;
	}

	public String getNganhangId() {
		return this.nganhangId;
	}

	public void setNganhangId(String nganhangId) {
		this.nganhangId = nganhangId;
	}

	public ResultSet getNganhangRs() {
		return this.nganhangRs;
	}

	public void setNganhangRs(ResultSet nganhangRs) {
		this.nganhangRs = nganhangRs;
	}

	public String getChinhanhId() {
		return this.chinhanhId;
	}

	public void setChinhanhId(String cnId) {
		this.chinhanhId = cnId;
	}

	public ResultSet getChinhanhRs() {
		return this.chinhanhRs;
	}

	public void setChinhanhRs(ResultSet chinhanhRs) {
		this.chinhanhRs = chinhanhRs;
	}

	public String getSotaikhoan() {
		return this.sotaikhoan;
	}

	public void setSotaikhoan(String sotk) {
		this.sotaikhoan = sotk;
	}

	public String getSotaikhoan_tp() {
		return this.sotaikhoan_tp;
	}

	public void setSotaikhoan_tp(String sotk_tp) {
		this.sotaikhoan_tp = sotk_tp;
	}

	public String getNoidungtt() {
		return this.noidungtt;
	}

	public void setNoidungtt(String ndtt) {
		this.noidungtt = ndtt;
	}

	public String getHoadonIds() {
		return this.hoadonId;
	}

	public void setHoadonIds(String hdIds) {
		this.hoadonId = hdIds;
	}

	public List<IHoadon> getHoadonRs() {
		return this.hoadonList;
	}

	public void setHoadonRs(List<IHoadon> hoadonRs) {
		this.hoadonList = hoadonRs;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getSotientt() {
		return this.sotientt;
	}

	public void setSotientt(String sotientt) {
		this.sotientt = sotientt;
	}

	public String getSotienHD() {
		return this.sotienHD;
	}

	public void setSotienHD(String sotienHD) {
		this.sotienHD = sotienHD;
	}

	public String getPhinganhang() {
		return this.pnganhang;
	}

	public void setPhinganhang(String pnganhang) {
		this.pnganhang = pnganhang;
	}

	public String getThueVAT() {
		return this.thueVAT;
	}

	public void setThueVAT(String thueVAT) {
		this.thueVAT = thueVAT;
	}

	public String getSotienttNT() {
		return this.sotienttNT;
	}

	public void setSotienttNT(String sotienttNT) {
		this.sotienttNT = sotienttNT;
	}

	public String getSotienHDNT() {
		return this.sotienHDNT;
	}

	public void setSotienHDNT(String sotienHDNT) {
		this.sotienHDNT = sotienHDNT;
	}

	public String getPhinganhangNT() {
		return this.pnganhangNT;
	}

	public void setPhinganhangNT(String pnganhangNT) {
		this.pnganhangNT = pnganhangNT;
	}

	public String getThueVATNT() {
		return this.thueVATNT;
	}

	public void setThueVATNT(String thueVATNT) {
		this.thueVATNT = thueVATNT;
	}

	public String getMahoadon() {
		return this.mahoadon;
	}

	public void setMahoadon(String mahoadon) {
		this.mahoadon = mahoadon;
	}

	public String getMauhoadon() {
		return this.mauhoadon;
	}

	public void setMauhoadon(String mauhoadon) {
		this.mauhoadon = mauhoadon;
	}

	public String getKyhieu() {
		return this.kyhieu;
	}

	public void setKyhieu(String kyhieu) {
		this.kyhieu = kyhieu;
	}

	public String getSohoadon() {
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
	}

	public String getNgayhoadon() {
		return this.ngayhoadon;
	}

	public void setNgayhoadon(String ngayhoadon) {
		this.ngayhoadon = ngayhoadon;
	}

	public String getTenNCC() {
		return this.tenNCC;
	}

	public void setTenNCC(String tenNCC) {
		this.tenNCC = tenNCC;
	}

	public String getMST() {
		return this.mst;
	}

	public void setMST(String mst) {
		this.mst = mst;
	}

	public String getTienhang() {
		return this.tienhang;
	}

	public void setTienhang(String tienhang) {
		this.tienhang = tienhang;
	}

	public String getThuesuat() {
		return this.thuesuat;
	}

	public void setThuesuat(String thuesuat) {
		this.thuesuat = thuesuat;
	}

	public String getTienthue() {
		return this.tienthue;
	}

	public void setTienthue(String tienthue) {
		this.tienthue = tienthue;
	}

	public String getNhId_VAT() {
		return this.nhId_VAT;
	}

	public void setNhId_VAT(String nhId_VAT) {
		this.nhId_VAT = nhId_VAT;
	}

	public String getCnId_VAT() {
		return this.cnId_VAT;
	}

	public void setCnId_VAT(String cnId_VAT) {
		this.cnId_VAT = cnId_VAT;
	}
	public String getLoaiThue(){
		return this.loaiThue;
	}
	public void setLoaiThue(String loaiThue){
		this.loaiThue = loaiThue;
	}
	
	private String changeQuery(String query,String orderByColumn)
	{
		//luonghv
		String order = "";
		order = orderByColumn;
		
				query = "select * from(select row_number() over(order by " + order + ") as _no, addNo.* "
						+ "\n from (" + query + " ) addNo )list  order by "+order;
		
		System.out.println("____"+query);
		
		return query;
	}
	
}