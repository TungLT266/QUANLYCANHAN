package geso.traphaco.erp.beans.xoanoncc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.xoanoncc.IErpXoaNoNCC;
import geso.traphaco.erp.beans.xoanoncc.IHoadon;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpXoaNoNCC implements IErpXoaNoNCC {

	String userId;
	String id;
	String ngaychungtu;
	String ngayghiso;

	String nppId;
	ResultSet nppRs;

	String htttId;
	ResultSet htttRs;

	String ndId;
	ResultSet ndRs;

	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;

	String sotaikhoan;
	String noidungtt;
	String sotientt;

	String ctttId;
	ResultSet ctttRs;

	String hoadonId;
	List<IHoadon> hoadonList;

	String ctttIds;
	List<IHoadon> ctttList;

	String loaixnId; // loại xóa nợ: Xóa nợ KH trả trước/ Xóa tạm ứng
	String DoiTuongTamUng = "";
	String nccId = "";
	ResultSet nccRs;

	String nvId = "";
	ResultSet nvRs;

	ResultSet tienteRs;
	String tienteId;

	String congtyId;
	String nppdangnhap;

	String msg;
	dbutils db;

	public ErpXoaNoNCC() {
		this.id = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.noidungtt = "";
		this.sotientt = "";
		this.hoadonId = "";
		this.ctttId = "";
		this.ctttIds = "";

		this.loaixnId = "0";
		this.DoiTuongTamUng = "0";
		this.nccId = "";
		this.nvId = "";
		this.tienteId = "100000";
		this.msg = "";
		this.congtyId = "";
		this.nppdangnhap = "";
		this.hoadonList = new ArrayList<IHoadon>();
		this.ctttList = new ArrayList<IHoadon>();

		this.db = new dbutils();
	}

	public ErpXoaNoNCC(String id) {
		this.id = id;
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.sotaikhoan = "";
		this.noidungtt = "";
		this.sotientt = "";
		this.hoadonId = "";
		this.ctttId = "";
		this.ctttIds = "";

		this.loaixnId = "";
		this.DoiTuongTamUng = "";
		this.congtyId = "";
		this.nppdangnhap = "";

		this.msg = "";
		this.hoadonList = new ArrayList<IHoadon>();
		this.ctttList = new ArrayList<IHoadon>();

		this.db = new dbutils();
	}

	public void setDoiTuongTamUng(String doiTuongTamUng) {
		DoiTuongTamUng = doiTuongTamUng;
	}

	public String getDoiTuongTamUng() {
		return DoiTuongTamUng;
	}

	public void setNccId(String nccId) {
		this.nccId = nccId;
	}

	public String getNccId() {
		return nccId;
	}

	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}

	public ResultSet getNccRs() {
		return nccRs;
	}

	public String getNvId() {
		return nvId;
	}

	public void setNvId(String nvId) {
		this.nvId = nvId;
	}

	public ResultSet getNvRs() {
		return nvRs;
	}

	public void setNvRs(ResultSet nvRs) {
		this.nvRs = nvRs;
	}

	public String getLoaixnId() {
		return loaixnId;
	}

	public void setLoaixnId(String loaixnId) {
		this.loaixnId = loaixnId;
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

	public String getNgaychungtu() {
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getNppId() {
		return this.nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public ResultSet getNppRs() {
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) {
		this.nppRs = nppRs;
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

	public String getNoidungtt() {
		return this.noidungtt;
	}

	public void setNoidungtt(String ndtt) {
		this.noidungtt = ndtt;
	}

	public String getSotientt() {
		return this.sotientt;
	}

	public void setSotientt(String sotientt) {
		this.sotientt = sotientt;
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

	public ResultSet getTienteRs() {

		return this.tienteRs;
	}

	public void setTienteRs(ResultSet tienteRs) {

		this.tienteRs = tienteRs;
	}

	public String getTienteId() {

		return this.tienteId;
	}

	public void setTienteId(String ttId) {

		this.tienteId = ttId;
	}

	public boolean createTTHD() {
		if (this.ctttList.size() <= 0) {
			this.msg = "Vui lòng chọn chứng từ trả trước";
			return false;
		}

		if (this.sotientt.trim().length() <= 0) {
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}

		// Tinh lai tong tien
		long tongthanhtoan = 0;
		for (int i = 0; i < this.hoadonList.size(); i++) {
			IHoadon hd = this.hoadonList.get(i);
			if (hd.getThanhtoan().length() > 0)
				tongthanhtoan += Long.parseLong(hd.getThanhtoan().replaceAll(",", ""));
		}

		if (tongthanhtoan <= 0) {
			this.msg = "Vui lòng chọn hóa đơn để thanh toán";
			return false;
		}

		try {
			String ngaytao = getDateTime();

			db.getConnection().setAutoCommit(false);
			String query = "";

			if (this.nccId.trim().length() > 0 || this.nvId.trim().length() > 0) {
				if (this.loaixnId.equals("0"))
					this.nvId = "NULL";
				else
					this.nccId = "NULL";

				query = "Insert ERP_XOANONCC(TIENTE_FK, NGAYCHUNGTU, NGAYGHISO, TRANGTHAI, NCC_FK, NHANVIEN_FK , TONGTIENTRATRUOC, TONGTIENHOADON, GHICHU, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAIXOANO, CONGTY_FK ) "
						+ "values(" + this.tienteId + ", '" + this.ngaychungtu + "', '" + this.ngayghiso + "', '0', "
						+ this.nccId + ", " + this.nvId + " , '" + this.sotientt.replaceAll(",", "") + "', '"
						+ tongthanhtoan + "', N'" + this.noidungtt + "', " + "'" + ngaytao + "', '" + this.userId
						+ "', '" + ngaytao + "', '" + this.userId + "'," + this.loaixnId + ", " + this.congtyId + " )";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_XOANONCC: " + query;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			String tthdCurrent = "";
			query = "select IDENT_CURRENT('ERP_XOANONCC') as tthdId";

			ResultSet rsTthd = db.get(query);
			if (rsTthd.next()) {
				tthdCurrent = rsTthd.getString("tthdId");
				rsTthd.close();
			}

			for (int i = 0; i < this.ctttList.size(); i++) {
				IHoadon hoadon = this.ctttList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");

				if (thanhtoan.length() > 0) {
					if (Float.parseFloat(thanhtoan) > 0) {
						if (hoadon.getLoaict().equals("0")) // CHI TẠM ỨNG
						{
							query = "Insert ERP_XOANONCC_TAMUNG(xoanoncc_fk, THANHTOANHOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI) "
									+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat + "', '" + conlai.trim() + "')";
						} else if (hoadon.getLoaict().equals("1")) // BÚT TOÁN
																	// TỔNG HỢP
						{
							query = "Insert ERP_XOANONCC_BTTH(xoanoncc_fk, BTTH_FK, SOTIENTT, SOTIENAVAT, CONLAI) "
									+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat + "', '" + conlai.trim() + "')";
						}

						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_XOANONCC_TAMUNG: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}

					}
				}
			}

			for (int i = 0; i < this.hoadonList.size(); i++) {

				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");

				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");

				if (thanhtoan.length() > 0) {
					if (Float.parseFloat(thanhtoan) > 0) {
						if (this.loaixnId.equals("0")) // Xóa nợ NCC
						{
							query = "Insert ERP_XOANONCC_HOADONNCC(xoanoncc_fk, hoadonncc_FK, TIENTHANHTOAN, TIENCHUNGTU, CONLAI, LOAICT) "
									+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat.trim() + "', '" + conlai.trim() + "', " + hoadon.getLoaict() + ")";
							System.out.println(query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_XOANONCC_HOADONNCC: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						} else // Xóa nợ nhân viên
						{
							query = "Insert ERP_XOANONCC_DNTT(xoanoncc_fk, dntt_FK, TIENTHANHTOAN, TIENCHUNGTU, CONLAI, LOAICT) "
									+ "values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat.trim() + "', '" + conlai.trim() + "', " + hoadon.getLoaict() + ")";
							System.out.println(query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_XOANONCC_DNTT: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}

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
			}
			return false;
		}

		return true;
	}

	public boolean updateTTHD() {
		if (this.ctttList.size() <= 0) {
			this.msg = "Vui lòng chọn chứng từ trả trước";
			return false;
		}

		if (this.sotientt.trim().length() <= 0) {
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}

		// Tinh lai tong tien
		long tongthanhtoan = 0;
		for (int i = 0; i < this.hoadonList.size(); i++) {
			IHoadon hd = this.hoadonList.get(i);
			if (hd.getThanhtoan().length() > 0)
				tongthanhtoan += Long.parseLong(hd.getThanhtoan().replaceAll(",", ""));
		}

		if (tongthanhtoan <= 0) {
			this.msg = "Vui lòng chọn hóa đơn để thanh toán";
			return false;
		}

		try {
			String ngaysua = getDateTime();

			db.getConnection().setAutoCommit(false);
			String query = "";

			if (this.loaixnId.equals("0"))
				this.nvId = "NULL";
			else
				this.nccId = "NULL";

			query = "update ERP_XOANONCC set TIENTE_FK = " + this.tienteId + ", NGAYCHUNGTU = '" + this.ngaychungtu
					+ "', NGAYGHISO = '" + this.ngayghiso + "', " + " NCC_FK = " + this.nccId + ", NHANVIEN_FK = "
					+ this.nvId + ", GHICHU = N'" + this.noidungtt + "', " + " TONGTIENTRATRUOC = '"
					+ this.sotientt.replaceAll(",", "") + "', TONGTIENHOADON = '" + tongthanhtoan + "',  "
					+ " NGAYSUA = '" + ngaysua + "', NGUOISUA = '" + this.userId + "', LOAIXOANO = " + this.loaixnId
					+ " where PK_SEQ = '" + this.id + "'";

			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_XOANCCTHANHTOAN: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			if (this.loaixnId.equals("0")) {
				query = "delete ERP_XOANONCC_HOADONNCC where XOANONCC_FK = '" + this.id + "'";
				if (!db.update(query)) {
					this.msg = "Khong the cap nhat ERP_XOANONCC_HOADONNCC: " + query;
					db.getConnection().rollback();
					return false;
				}
			} else {
				query = "delete ERP_XOANONCC_DNTT where XOANONCC_FK = '" + this.id + "'";
				if (!db.update(query)) {
					this.msg = "Khong the cap nhat ERP_XOANONCC_DNTT: " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			query = "delete ERP_XOANONCC_TAMUNG where XOANONCC_FK = '" + this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_XOANONCC_TAMUNG: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_XOANONCC_BTTH where XOANONCC_FK = '" + this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_XOANONCC_BTTH: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			for (int i = 0; i < this.ctttList.size(); i++) {
				IHoadon hoadon = this.ctttList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");

				if (thanhtoan.length() > 0) {
					if (Float.parseFloat(thanhtoan) > 0) {
						if (hoadon.getLoaict().equals("0")) // CHI TẠM ỨNG
						{
							query = "Insert ERP_XOANONCC_TAMUNG(xoanoncc_fk, THANHTOANHOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI) "
									+ "values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat + "', '" + conlai.trim() + "')";
						} else if (hoadon.getLoaict().equals("1")) {
							query = "Insert ERP_XOANONCC_BTTH(xoanoncc_fk, BTTH_FK, SOTIENTT, SOTIENAVAT, CONLAI) "
									+ "values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat + "', '" + conlai.trim() + "')";
						}

						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_XOANONCC_TAMUNG: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			for (int i = 0; i < this.hoadonList.size(); i++) {

				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");

				if (thanhtoan.length() > 0) {
					if (Float.parseFloat(thanhtoan) > 0) {
						if (this.loaixnId.equals("0")) // Xóa nợ NCC
						{
							query = "Insert ERP_XOANONCC_HOADONNCC(xoanoncc_fk, hoadonncc_FK, TIENTHANHTOAN, TIENCHUNGTU, CONLAI, LOAICT) "
									+ "values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat.trim() + "', '" + conlai.trim() + "', " + hoadon.getLoaict() + ")";
							System.out.println(query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_XOANONCC_HOADONNCC: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						} else // Xóa nợ nhân viên
						{
							query = "Insert ERP_XOANONCC_DNTT(xoanoncc_fk, dntt_FK, TIENTHANHTOAN, TIENCHUNGTU, CONLAI, LOAICT) "
									+ "values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()
									+ "', '" + avat.trim() + "', '" + conlai.trim() + "', " + hoadon.getLoaict() + ")";
							System.out.println(query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_XOANONCC_DNTT: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}

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
			}
			return false;
		}

		return true;
	}

	public boolean chotTTHD(String userId) {
		try {
			String ngaysua = getDateTime();

			db.getConnection().setAutoCommit(false);

			String query = "update ERP_XOANONCC set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua
					+ "' where PK_SEQ = '" + this.id + "'";
			System.out.println("1.Cap nhat ERP_XOANONCC: " + query);

			if (!db.update(query)) {
				this.msg = "Khong the chot ERP_XOANONCC: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			/*Tại chức năng Xóa nợ NCC, khi chốt phiếu xóa nợ: Tính và So sánh các số M1 vs. M2.
			Đồng thời tự động sinh ra 1 chứng từ đã được chốt trên Chức năng Bút toán tổng hợp có định khoản như trường hợp 1 hoặc trường hợp 2 bên dưới:
			 
			M1 (VND) = Tổng (Số lượng ngoại tệ trên từng chứng từ trả trước được ghi nhận ở cột Thanh toán của POPup Chứng từ trả trước  * Tỷ giá thanh toán trên chứng từ trả trước đó)
			Note: Chứng từ trả trước có thể là Phiếu chi/UNC/Bút toán tổng hợp
			 
			M2 (VND) = Tổng (Số lượng ngoại tệ trên từng Chứng từ tăng nợ được được ghi nhận ở cột Thanh toán * Tỷ giá trên chứng từ đó)
			Note: Chứng từ tăng nợ có thể là Hóa đơn nhập khẩu/Bút toán tổng hợp/Đề nghị thanh toán
			 
			1. Nếu M1>M2: Tiền định khoản = M1-M2
			Nợ < 63580000>
			Có <TK bên dưới NCC>  à Lưu đối tượng CÓ : Mã NCC
			 
			2. Nếu M1<M2: Tiền định khoản = M2 – M1
			Nợ < TK bên dưới NCC > à Lưu đối tượng NỢ: Mã NCC
			Có < 51530000>*/
			
			this.thucHienTaoBTTH();

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}

		return true;
	}

	public void thucHienTaoBTTH() {
		String query1 = "select pk_seq, ncc_fk, TIENTE_FK \n" + "from ERP_XOANONCC \n" + "where pk_seq = '" + this.id
				+ "'";
		ResultSet rs = db.get(query1);
		System.out.println(query1);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.nccId = rs.getString("ncc_fk") == null ? "" : rs.getString("ncc_fk");
					this.tienteId = rs.getString("TIENTE_FK");
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		// load chung tu tra truoc
		double tongTienTraTruoc = 0;
		double tongTienHoaDon = 0;
		String query = "";
		if (this.id.length() > 0) {
			// Cách tính ra số tiền còn lại để xóa nợ NCC như sau:
			// b.sotienTT: số tiền đã tạm ứng cho NCC
			// thanh_toan.tongthanhtoan: số tiền đã xóa tạm ứng NCC khác với
			// chứng từ được chọn
			// a.sotientt: số tiền xóa tạm ứng của chứng từ Xóa tạm ứng NCC được
			// chọn

			query += "SELECT 0 as loaict, b.pk_seq, N'Chi Tạm Ứng' as kyhieu, b.pk_seq as sohoadon, b.NGAYGHINHAN as ngayhoadon, \n"
					+ "CASE WHEN B.TIENTE_FK = 100000 THEN	b.sotienTT ELSE B.SOTIENTTNT END"
					+ " - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, a.sotientt as DaThanhToan,B.TIENTE_FK,B.TIGIA  \n"
					+ "FROM 	ERP_XOANONCC_TAMUNG a "
					+ "INNER JOIN ERP_THANHTOANHOADON b on a.thanhtoanhoadon_fk = b.pk_seq \n" + "LEFT JOIN	\n"
					+ "		(" + "		select XTU.thanhtoanhoadon_fk, sum(XTU.SOTIENTT) as tongthanhtoan \n"
					+ "   	from ERP_XOANONCC_TAMUNG XTU inner join ERP_XOANONCC XN on XTU.XOANONCC_FK = XN.PK_SEQ \n"
					+ "  	 	where XN.trangthai != '2' and XTU.XOANONCC_FK != '" + this.id + "' \n"
					+ "   	and XTU.thanhtoanhoadon_fk in (select thanhtoanhoadon_fk from ERP_XOANONCC_TAMUNG where XOANONCC_FK = '"
					+ this.id + "') \n" + "   	group by thanhtoanhoadon_fk"
					+ "		)thanh_toan on a.thanhtoanhoadon_fk = thanh_toan.thanhtoanhoadon_fk \n"
					+ " WHERE a.XOANONCC_FK = '" + this.id + "' AND B.TIENTE_FK = '"+this.tienteId+"' \n";

			// LẤY RA NHỮNG XÓA NỢ NCC BÚT TOÁN TỔNG HỢP

			query += " UNION ALL \n" +

					" SELECT 1 as loaict, b.BUTTOANTONGHOP_FK pk_seq, N'Bút toán tổng hợp' as kyhieu, c.pk_seq as sohoadon, c.ngaybuttoan ngayhoadon,  \n"
					+ "case when c.tiente_fk ='100000' then    isnull(b.NO,0) else isnull(b.giatrint,0) end"
					+ " - isnull(thanh_toan.tienthanhtoan, '0') as tienAvat, \n"
					+ "        a.sotientt as DaThanhToan  ,C.TIENTE_FK,C.TIGIA \n"
					+ " FROM   ERP_XOANONCC_BTTH a INNER JOIN \n"
					+ " ERP_BUTTOANTONGHOP_CHITIET b ON a.btth_fk = b.buttoantonghop_fk \n"
					+ " INNER JOIN ERP_BUTTOANTONGHOP c ON b.buttoantonghop_fk = c.pk_seq \n" + " LEFT JOIN \n"
					+ " 		( \n" + "			select 	a.btth_fk, sum(a.sotientt) as tienthanhtoan \n"
					+ "			from 	ERP_XOANONCC_BTTH a \n"
					+ "					inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq \n"
					+ "			where 	b.trangthai != 2 and a.XOANONCC_FK != '" + this.id + "' \n"
					+ "					and a.btth_fk IN (	select btth_fk from ERP_XOANONCC_BTTH \n"
					+ "   									where XOANONCC_FK = '" + this.id + "' ) \n"
					+ "			group by a.btth_fk \n" + " 		)thanh_toan on a.btth_fk = thanh_toan.btth_fk \n"
					+ " WHERE a.XOANONCC_FK = '" + this.id + "' and b.NO > 0 and b.NCC_FK = " + this.nccId + " AND C.TIENTE_FK ='"+this.tienteId+"' \n" +

					"UNION ALL \n";

		}

		// CHI TẠM ỨNG NCC
		query += "(SELECT 0 as loaict, hoadon.pk_seq, N'Chi Tạm Ứng' as kyhieu, hoadon.sohoadon, hoadon.ngayhoadon,  "
				+ "		 hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, \n"
				+ "		 isnull(dathanhtoan.DATHANHTOAN, '') as DATHANHTOAN ,HOADON.TIENTE_FK,HOADON.TIGIA   \n"
				+ " FROM \n" + " (	\n" +
				// TRƯỜNG HỢP UNC - TẠM ỨNG NCC
				"	SELECT tt.PK_SEQ, tt.PK_SEQ as sohoadon, tt.NGAYGHINHAN as ngayhoadon ,isnull(TTHD_HD.SOTIENTT,0)  as sotienAVAT, \n"
				+ " TTHD_HD.TIENTE_FK, TTHD_HD.TIGIA \n" + "	FROM ERP_THANHTOANHOADON tt  \n" + "   INNER JOIN "
				+ "       ( \n"
				+ "         SELECT a.THANHTOANHD_FK , SUM(a.SOTIENTT) as SOTIENTT ,A.TIENTE_FK ,A.TIGIA  \n"
				+ "         FROM ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ  \n"
				+ "         WHERE b.TRANGTHAI = 1 and a.LOAIHD = 1 AND b.congty_fk = " + this.congtyId
				+ "         GROUP BY a.THANHTOANHD_FK,A.TIENTE_FK,A.TIGIA   "
				+ "        ) TTHD_HD ON tt.PK_SEQ = TTHD_HD.THANHTOANHD_FK " + "	WHERE TT.CONGTY_FK = "
				+ this.congtyId + " AND TT.TRANGTHAI=1 and tt.NCC_FK = '" + this.nccId + "'  AND tt.TIENTE_FK = "
				+ this.tienteId + " "
				+ " 	and tt.pk_seq not in (SELECT thanhtoanhoadon_fk FROM ERP_XOANONCC_TAMUNG WHERE xoanoncc_fk = '"
				+ (this.id == "" ? "0" : this.id) + "')  ";

		query += " ) HOADON \n" + " LEFT JOIN  \n" + " ( \n"
				+ "   SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" + "	FROM  " + "		( \n" +

				// TRƯỜNG HỢP XÓA TẠM ỨNG KHÁC VỚI XÓA TẠM ỨNG ĐANG ĐƯỢC CHỌN
				"	   	SELECT XNTU.THANHTOANHOADON_FK as HOADON_FK, sum(XNTU.SOTIENTT) as DATHANHTOAN   \n"
				+ "	   	FROM ERP_XOANONCC_TAMUNG XNTU "
				+ "		INNER JOIN ERP_XOANONCC XN on XNTU.XOANONCC_FK = XN.PK_SEQ  \n"
				+ "	   	WHERE XN.TRANGTHAI != 2 and XN.CONGTY_FK = " + this.congtyId + " \n ";

		if (this.id.trim().length() > 0)
			query += " 		and XN.pk_seq != '" + this.id + "' \n";

		query += " 		GROUP BY THANHTOANHOADON_FK  \n" +

				"      	UNION ALL   \n" +

				// TRƯỜNG HỢP THU HỒI TẠM ỨNG NCC
				"       SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"
				+ "	   	FROM ERP_THUTIEN_HOADON TTHD "
				+ "		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ \n"
				+ "       WHERE TT.NOIDUNGTT_FK = 100001 AND  TT.TRANGTHAI != 2 and TT.NCC_FK= '" + this.nccId
				+ "' and TT.CONGTY_FK = " + this.congtyId + " \n" + "       GROUP BY HOADON_FK  \n" +

				"      	UNION ALL  \n" +

				// TRƯỜNG HỢP XÓA TẠM ỨNG CHO NCC
				"       SELECT XHD.HOADON_FK , sum(XHD.SOTIENTT) as DATHANHTOAN  \n"
				+ "       FROM ERP_XOAKHTRATRUOC_HOADON XHD "
				+ "		INNER JOIN ERP_XOAKHTRATRUOC XTT on XHD.xoakhtratruoc_fk = XTT.PK_SEQ \n"
				+ "       WHERE  XTT.TRANGTHAI != 2 AND XTT.NCC_FK = '" + this.nccId + "' and XTT.CONGTY_FK = "
				+ this.congtyId + " \n" + "       GROUP BY HOADON_FK  \n" + "	) HOADONDATT  \n"
				+ "	group by HOADON_FK \n" + ") dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n"
				+ " WHERE round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 ) ";

		// LẤY RA NHỮNG XÓA NỢ NCC BÚT TOÁN TỔNG HỢP

		query += " UNION ALL \n" +

				" select 1 as loaict, chungtu.pk_seq pk_seq, N'Bút toán tổng hợp' as kyhieu, chungtu.pk_seq as sohoadon,chungtu.ngaychungtu ngayhoadon,  \n"
				+ "       isnull(chungtu.sotientt,0) - (ISNULL(dathanhtoan.tienthanhtoan, 0)) as tienAvat, \n"
				+ "       0 as DaThanhToan ,chungtu.TIENTE_FK , CHUNGTU.TIGIA \n" + " "
				+ "from \n" + " ( \n"
				+ "	select	a.pk_seq, CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYBUTTOAN ngaychungtu, sum(isnull(NO,0)) as SOTIENTTNT, sum(NO) sotientt,  A.TIENTE_FK , a.TIGIA as tigia  \n"
				+ "	from	ERP_BUTTOANTONGHOP a INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK \n"
				+ "	where	trangthai = '1' and a.CONGTY_FK = " + this.congtyId + " \n" + "			and b.ncc_fk = '"
				+ this.nccId
				+ "' and a.tiente_fk ='"+this.tienteId+"' and b.NO > 0 and TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33111000' and CONGTY_FK = "
				+ this.congtyId + ")";

		query += "	and pk_seq not in (select btth_fk from ERP_XOANONCC_BTTH where xoanoncc_fk = '"
				+ (this.id == "" ? "0" : this.id) + "' ) \n";

		query += "	group by a.pk_seq, khachhang_fk, TAIKHOANKT_FK, ngaybuttoan,A.TIENTE_FK,A.TIGIA \n" + " )chungtu \n"
				+ " left join \n" + " ( \n" + "	select 	a.btth_fk, sum(a.sotientt) as tienthanhtoan \n"
				+ "	from 	ERP_XOANONCC_BTTH a \n"
				+ "			inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq \n"
				+ "	where 	b.trangthai != 2 and b.ncc_fk = " + this.nccId + " and b.CONGTY_FK = " + this.congtyId
				+ " \n";

		query += " 	and b.pk_seq != '" + (this.id == "" ? "0" : this.id) + "' \n";

		query += "	group by a.btth_fk  \n" + " )dathanhtoan on chungtu.pk_seq = dathanhtoan.btth_fk  \n"
				+ "where chungtu.sotientt - ISNULL(dathanhtoan.tienthanhtoan, 0) > 0 \n";
		System.out.println("----------------query chung tu tra truoc --------------");
		System.out.println(query);
		System.out.println("----------------------------------------------");
		ResultSet cttt = db.get(query);

		if (cttt != null) {
			try {
				while (cttt.next()) {
					if (!cttt.getString("TIENTE_FK").equals("100000")) {
						tongTienTraTruoc = tongTienTraTruoc + cttt.getDouble("DATHANHTOAN") * cttt.getDouble("TIGIA");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				this.msg ="Không thể lấy các chứng từ trả trước "+ e.toString();
				return;
			}
		}
		
		System.out.println("Tổng tiền trả trước" + tongTienTraTruoc);
		//////////////////////////////////////////////////////
		// load các hóa đơn

		query = "";
		if (this.id.length() > 0) {
			// LOAICT = 0 : HÓA ĐƠN NHÀ CUNG CẤP
			query += " SELECT c.pk_seq, c.kyhieu, c.sohoadon , c.ngayhoadon , \n"
					+ " (c.sotienAVAT) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat, a. tienthanhtoan as SOTIENTT,  \n"
					+ " isnull((SELECT distinct D.SOPO + ',' \n"
					+ " 		  FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_MUAHANG D ON A.MUAHANG_FK = D.PK_SEQ AND HOADONNCC_FK = c.PK_SEQ \n"
					+ " 		  for xml path('') ),'') SOPO, 0 as loaict ,b.tiente_fk , b.tigia \n"
					+ " FROM  ERP_XOANONCC_HOADONNCC a \n"
					+ " INNER JOIN ERP_HOADONNCC c on a.hoadonncc_fk = c.pk_seq and a.LOAICT = 0 \n"
					+ " INNER JOIN ERP_PARK b on c.PARK_FK = b.pk_seq \n" + " LEFT JOIN \n " + " ( \n"
					+ "	SELECT a.HOADON_FK , sum(a.SOTIENTT) as sotientt   \n"
					+ "   FROM ERP_THANHTOANHOADON_HOADON a \n"
					+ "	INNER JOIN ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.pk_seq    \n"
					+ "   WHERE b.trangthai = 1 and b.ncc_fk ='" + this.nccId + "' and a.LOAIHD = 0   \n"
					+ "   GROUP BY a.HOADON_FK  \n" + " ) HDTT on HDTT.HOADON_FK = a.hoadonncc_fk  \n"
					+ " LEFT JOIN	\n" + "   ("
					+ "		SELECT TTHD.HOADONNCC_FK, sum(TTHD.TienThanhToan) as tongthanhtoan \n"
					+ "       FROM ERP_XOANONCC_HOADONNCC TTHD inner join ERP_XOANONCC TT on TTHD.XOANONCC_FK = TT.PK_SEQ \n"
					+ "       WHERE TT.trangthai != '2' and TTHD.XOANONCC_FK != '" + this.id + "' \n"
					+ "       and TTHD.hoadonncc_fk in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where XOANONCC_FK = '"
					+ this.id + "') and TTHD.LOAICT = 0 \n" + "       GROUP BY TTHD.HOADONNCC_FK"
					+ "	)thanh_toan on a.HOADONNCC_FK = thanh_toan.HOADONNCC_FK \n" + " WHERE a.XOANONCC_FK = '"
					+ this.id + "' AND a.LOAICT = 0 and b.tiente_fk ='"+this.tienteId+"' \n";

			// LOAICT = 1: BÚT TOÁN TỔNG HỢP

			query += " UNION ALL \n";

			query += " SELECT b.pk_seq, N'Bút toán tổng hợp' Kyhieu, cast(b.pk_seq as nvarchar(50)) sohoadon , b.NGAYBUTTOAN, \n"
					+ " (PLSP.SOTIENVND) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat, a.tienthanhtoan as SOTIENTT, ' ' SOPO, 1 as loaict \n"
					+ " ,b.tiente_fk,b.tigia \n" + " FROM  ERP_XOANONCC_HOADONNCC a \n"
					+ " INNER JOIN ERP_BUTTOANTONGHOP b on a.hoadonncc_fk = b.PK_SEQ \n"
					+ " INNER JOIN (	SELECT BUTTOANTONGHOP_FK, NCC_FK,"
					+ " case when BT.TIENTE_FK = '100000' THEN SUM(CO) ELSE SUM(GIATRINT) END AS SOTIENVND \n"
					+ "               FROM   ERP_BUTTOANTONGHOP_CHITIET CT\n"
					+ "				INNER JOIN ERP_BUTTOANTONGHOP BT ON BT.PK_SEQ = CT.BUTTOANTONGHOP_FK "
					+ "               WHERE  TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33111000' and CONGTY_FK = "
					+ this.congtyId + ") \n" 
					+ "	and BT.tiente_fk = '"+this.tienteId+"'   AND NCC_FK IS NOT NULL AND CO > 0 AND NCC_FK = '"
					+ this.nccId + "'  \n"
					+ "				GROUP BY BUTTOANTONGHOP_FK, NCC_FK,BT.TIENTE_FK ) AS PLSP  ON b.PK_SEQ= PLSP.BUTTOANTONGHOP_FK \n"
					+ "  LEFT JOIN	\n" + "  ( \n"
					+ "	  SELECT TTHD.HOADONNCC_FK, sum(TTHD.TienThanhToan) as tongthanhtoan \n"
					+ "     FROM ERP_XOANONCC_HOADONNCC TTHD inner join ERP_XOANONCC TT on TTHD.XOANONCC_FK = TT.PK_SEQ \n"
					+ "     WHERE TT.trangthai != '2' and TTHD.XOANONCC_FK != '" + this.id + "' \n"
					+ "     and TTHD.hoadonncc_fk in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where XOANONCC_FK = '"
					+ this.id + "') and TTHD.LOAICT = 1 \n" + "     GROUP BY TTHD.HOADONNCC_FK \n"
					+ "	) thanh_toan on a.HOADONNCC_FK = thanh_toan.HOADONNCC_FK \n" + " WHERE a.XOANONCC_FK = '"
					+ this.id + "' AND a.LOAICT = 1 \n";

			query += " UNION ALL \n";
		}

		// HÓA ĐƠN NCC
		query += " SELECT chungtu.pk_seq, chungtu.kyhieu, chungtu.sohoadon, chungtu.ngayhoadon, chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0) as tienAvat, \n "
				+ "		0 as SOTIENTT, isnull(chungtu.SOPO,'') SOPO, 0 as loaict ,chungtu.tiente_fk,chungtu.tigia \n "
				+ " FROM \n" + " 	( \n"
				+ "	SELECT b.PK_SEQ, b.kyhieu, b.sohoadon, b.PK_SEQ as sochungtu, b.ngayhoadon, b.sotienAVAT - isnull(HDTT.sotientt,0) as tienAvat,   \n"
				+ "			(SELECT distinct C.SOPO + ',' \n"
				+ "			FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_MUAHANG C ON A.MUAHANG_FK = C.PK_SEQ AND HOADONNCC_FK = b.PK_SEQ \n"
				+ "			for xml path('') ) SOPO ,a.tiente_fk,a.tigia \n" + "	FROM ERP_PARK a \n"
				+ "	INNER JOIN ERP_HOADONNCC b on a.PK_SEQ=b.park_fk  \n" + "   LEFT JOIN \n" + "   ("
				+ "		SELECT a.HOADON_FK , sum(a.SOTIENTT) as sotientt  \n"
				+ "       FROM ERP_THANHTOANHOADON_HOADON a "
				+ "		INNER JOIN ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.pk_seq   \n"
				+ "       WHERE b.trangthai = 1 and b.ncc_fk ='" + this.nccId + "' and a.LOAIHD = 0   \n"
				+ "       GROUP by a.HOADON_FK  \n" + "	) HDTT on HDTT.HOADON_FK = a.pk_seq \n"
				+ " WHERE a.TRANGTHAI=2 and a.ncc_fk ='" + this.nccId + "'  AND a.TIENTE_FK = " + this.tienteId
				+ "  and b.CONGTY_FK = " + this.congtyId + "  \n ";

		if (this.id.length() > 0) {
			query += " and b.pk_seq not in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where xoanoncc_fk = '"
					+ this.id + "' and loaict = 0 ) \n";
		}
		query += "  )chungtu \n" + "LEFT JOIN  \n" + " (  \n"
				+ " 	SELECT a.hoadonncc_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"
				+ " 	FROM ERP_XOANONCC_HOADONNCC a inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq  \n"
				+ " 	WHERE b.ncc_fk ='" + this.nccId + "' and b.CONGTY_FK = " + this.congtyId
				+ " AND b.TRANGTHAI NOT IN (2) and a.loaict = 0  \n";
		if (this.id.trim().length() > 0)
			query += " and b.pk_seq != '" + this.id + "' \n";

		query += "	GROUP BY a.hoadonncc_fk \n" + " )dathanhtoan on chungtu.pk_seq = dathanhtoan.hoadonncc_fk \n"
				+ "WHERE round(chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0), 0) > 0 ";

		// ERP_BUTTOANTONGHOP

		query += " UNION ALL \n" +

				" SELECT chungtu.pk_seq, chungtu.kyhieu, chungtu.sohoadon, chungtu.ngayhoadon, chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0) as tienAvat,     \n "
				+ "		0 as SOTIENTT, isnull(chungtu.SOPO,'') SOPO, 1 as loaict,chungtu.tiente_fk,CHUNGTU.tigia \n "
				+ " FROM \n" + " ( \n"
				+ "	SELECT a.PK_SEQ, N'Bút toán tổng hợp' KYHIEU,  cast(a.PK_SEQ as nvarchar(50) ) sohoadon, a.PK_SEQ as sochungtu, a.NGAYBUTTOAN ngayhoadon, isnull(b.CO,0) - isnull(HDTT.sotientt,0) as tienAvat, ' ' SOPO   \n"
				+ " 	,a.tiente_fk,a.tigia \n" + "	FROM ERP_BUTTOANTONGHOP a \n"
				+ "	INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK  \n" + "   LEFT JOIN \n"
				+ "   (" + "		SELECT a.HOADONNCC_FK HOADON_FK , sum(a.TIENTHANHTOAN) as sotientt  \n"
				+ "       FROM ERP_XOANONCC_HOADONNCC a \n"
				+ "		INNER JOIN ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq   \n"
				+ "       WHERE b.trangthai = 1 and b.ncc_fk ='" + this.nccId + "' and a.LOAICT = 1    \n"
				+ "       GROUP BY a.HOADONNCC_FK  \n" + "	) HDTT on HDTT.HOADON_FK = a.pk_seq \n"
				+ "   WHERE  a.TIENTE_FK ='"+this.tienteId+"' AND a.TRANGTHAI = 1 and b.ncc_fk ='" + this.nccId + "' and a.CONGTY_FK = " + this.congtyId
				+ " AND isnull(b.CO,0) > 0  AND \n "
				+ "	b.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33111000' and CONGTY_FK = "
				+ this.congtyId + ")  ";

		if (this.id.length() > 0) {
			query += " and a.PK_SEQ not in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where xoanoncc_fk = '"
					+ this.id + "' and loaict = 1 ) \n";
		}

		query += " )chungtu \n" + " LEFT JOIN  \n" + " (  \n"
				+ " 	SELECT a.hoadonncc_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"
				+ " 	FROM ERP_XOANONCC_HOADONNCC a inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq  \n"
				+ " 	WHERE b.ncc_fk ='" + this.nccId + "' and b.CONGTY_FK = " + this.congtyId
				+ " AND b.TRANGTHAI NOT IN (2) and a.loaict = 1  \n";
		if (this.id.trim().length() > 0)
			query += " and b.pk_seq != '" + this.id + "' \n";

		query += "	GROUP BY a.hoadonncc_fk \n" + " )dathanhtoan on chungtu.pk_seq = dathanhtoan.hoadonncc_fk \n"
				+ "  WHERE round(chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0), 0) > 0 ";
		System.out.println("----------------query hoa don --------------");
		System.out.println(query);
		System.out.println("----------------------------------------------");

		ResultSet hdRs = db.get(query);
		if (hdRs != null) {
			try {
				while (hdRs.next()) {
					if (!hdRs.getString("TIENTE_FK").equals("100000")) {
						tongTienHoaDon = tongTienHoaDon + hdRs.getDouble("SOTIENTT") * hdRs.getDouble("TIGIA");
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				this.msg ="Không thể lấy các chứng từ hóa đơn "+ e.toString();
				return;
			}
		}
		System.out.println("Tổng tiền hóa đơn : " + tongTienHoaDon);
		if (tongTienTraTruoc != tongTienHoaDon) {
			// Chèn dữ liệu vào bút toán tổng hợp
			// tạo biến dữ liệu
			String dienGiai = "Bút toán tổng hợp xóa nợ NCC " + this.id;
			String ngayButToan = this.getDateTime();
			String ngayTao = this.getDateTime();
			String nguoiTao = this.userId;
			String ngaySua = this.getDateTime();
			String nguoiSua = this.userId;
			String congTy = this.congtyId;
			String tienTe = "100000";
			String tiGia = "1";
			String sqlPKmax = "select IDENT_CURRENT('ERP_BUTTOANTONGHOP') as BTTHID";
			int pk_max = 0;
			ResultSet rsPKmax = this.db.get(sqlPKmax);
			if (rsPKmax != null) {
				try {
					while (rsPKmax.next()) {
						pk_max = rsPKmax.getInt("BTTHID");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			String maChungTu = "BTTH" + this.getDateTime().substring(5, 7)+
					this.getDateTime().substring(0, 4)+ "-" + (pk_max + 1);
			String taiKhoanNo = "";
			String taiKhoanCo = "";
			String doiTuongNo = "";
			String doiTuongCo = "";
			String tienDinhKhoan = "";
			String taiKhoanNCCSQL = "SELECT TAIKHOAN_FK FROM ERP_NHACUNGCAP WHERE PK_SEQ = " + this.nccId;
			ResultSet taiKhoanNCCrs = this.db.get(taiKhoanNCCSQL);
			String taiKhoanNCC = "";
			if (taiKhoanNCCrs != null) {
				try {
					while (taiKhoanNCCrs.next()) {
						taiKhoanNCC = taiKhoanNCCrs.getString("TAIKHOAN_FK");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (tongTienTraTruoc > tongTienHoaDon) {
				double tien = tongTienTraTruoc - tongTienHoaDon;
				tienDinhKhoan = Double.toString(tien);
				String taiKhoanNoSQL = "SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '63580000%' AND CONGTY_FK = '"
						+ this.congtyId + "'";
				ResultSet tkNoRs = this.db.get(taiKhoanNoSQL);
				if (tkNoRs != null) {
					try {
						while (tkNoRs.next()) {
							taiKhoanNo = tkNoRs.getString("PK_SEQ");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				taiKhoanCo = taiKhoanNCC;
				doiTuongNo = "null";
				doiTuongCo = this.nccId;
			}
			if (tongTienTraTruoc < tongTienHoaDon) {
				double tien = tongTienHoaDon - tongTienTraTruoc;
				tienDinhKhoan = Double.toString(tien);
				String taiKhoanCoSQL = "SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN LIKE '51530000%' AND CONGTY_FK = '"
						+ this.congtyId + "'";
				ResultSet tkCoRs = this.db.get(taiKhoanCoSQL);
				if (tkCoRs != null) {
					try {
						while (tkCoRs.next()) {
							taiKhoanCo = tkCoRs.getString("PK_SEQ");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				taiKhoanNo = taiKhoanNCC;
				doiTuongCo = "null";
				doiTuongNo = this.nccId;
			}

			try {
				

				String insBUTH = "INSERT ERP_BUTTOANTONGHOP(NGAYBUTTOAN,DIENGIAI,NGAYTAO,NGAYSUA,"
						+ "NGUOITAO,NGUOISUA,TRANGTHAI,CONGTY_FK,NPP_FK,MACHUNGTU,TIENTE_FK,TIGIA)VALUES " + "('"
						+ ngayButToan + "',N'" + dienGiai + "','" + ngayTao + "','" + ngaySua + "','" + nguoiTao + "','"
						+ nguoiSua + "','0','" + congTy + "','0','" + maChungTu + "','" + tienTe + "','" + tiGia + "')";
				System.out.println("1.Thêm mới ERP_BUTTOANTONGHOP: " + insBUTH);

				if (!db.update(insBUTH)) {
					this.msg = "Không thể thêm mới ERP_BUTTOANTONGHOP: " + insBUTH;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return;
				}
				String curIdSQL = "select IDENT_CURRENT('ERP_BUTTOANTONGHOP') as BTTHID";
				String BTTHID = "";
				ResultSet curIdRs = this.db.get(curIdSQL);
				if (curIdRs != null) {
					while (curIdRs.next()) {
						BTTHID = curIdRs.getString("BTTHID");
					}
				}
				String insBTTHCT = "INSERT ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK,TAIKHOANKT_FK,NO,CO,NCC_FK,STT,DIENGIAI)VALUES('"
						+ BTTHID + "','" + taiKhoanNo + "'," + tienDinhKhoan + ",'0'," + doiTuongNo + ",'1',N'"
						+ dienGiai + "')";
				System.out.println("Thêm mới ERP_BUTTOANTONGHOP_CHITIET NỢ " + insBTTHCT);
				if (!db.update(insBTTHCT)) {
					this.msg = "Không thể thêm mới ERP_BUTTOANTONGHOP_CHITIET NỢ " + insBTTHCT;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return;
				}
				String insBTTHCT1 = "INSERT ERP_BUTTOANTONGHOP_CHITIET(BUTTOANTONGHOP_FK,TAIKHOANKT_FK,NO,CO,NCC_FK,STT,DIENGIAI)VALUES('"
						+ BTTHID + "','" + taiKhoanCo + "','0'," + tienDinhKhoan + "," + doiTuongCo + ",'2',N'"
						+ dienGiai + "')";
				System.out.println("Thêm mới ERP_BUTTOANTONGHOP_CHITIET CÓ " + insBTTHCT1);
				if (!db.update(insBTTHCT1)) {
					this.msg = "Không thể thêm mới ERP_BUTTOANTONGHOP_CHITIET CÓ " + insBTTHCT1;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return;
				}
				String insBTTHCT_TH = "INSERT ERP_BUTTOANTONGHOP_CHITIET_TH(BUTTOANTONGHOP_FK,TAIKHOANNOKT_FK,TAIKHOANCOKT_FK"
						+ ",NCC_FK_NO,NCC_FK_CO,DIENGIAI,PKSEQ)VALUES ('" + BTTHID + "','" + taiKhoanNo + "','"
						+ taiKhoanCo + "'," + doiTuongNo + "," + doiTuongCo + ",N'" + dienGiai + "','0')";
				System.out.println("Thêm mới ERP_BUTTOANTONGHOP_CHITIET_TH " + insBTTHCT_TH);
				if (!db.update(insBTTHCT_TH)) {
					this.msg = "Không thể thêm mới ERP_BUTTOANTONGHOP_CHITIET_TH " + insBTTHCT_TH;
					System.out.println(this.msg);
					db.getConnection().rollback();
					return;
				}
				//thực hiện chốt và định khoản luôn bút toán tổng hợp đó
				this.msg = this.chotBTTH(BTTHID);
				System.out.println("------------CHỐT BÚT TOÁN TỔNG HỢP------------");
				System.out.println(this.msg);
				System.out.println("----------------------------------------------");
			
			}catch (Exception ex){
				ex.printStackTrace();
			}
		}
	}

	public String chotBTTH(String id) {
		// Hàm chốt sử dụng khi chốt bút toán tổng hợp khi tạo mới bút toán tổng
		// hợp
		if (this.userId == null || this.userId.equals("") || this.userId.equals("null"))
			return "Vui lòng đăng nhập lại";

		try {
			// db.getConnection().setAutoCommit(false);

			String query = "UPDATE ERP_BUTTOANTONGHOP SET TRANGTHAI =1,NGUOISUA='" + this.userId + "' WHERE PK_SEQ='"
					+ id + "'";
			if (!this.db.update(query)) {
				db.getConnection().rollback();
				return "Không thể chốt bút toán này " + query;
			}

			String thang = "";
			String nam = "";
			String ngaybuttoan = "";

			query = "select NGAYBUTTOAN from ERP_BUTTOANTONGHOP where PK_SEQ = '" + id + "'";
			ResultSet rsNGAYBT = db.get(query);
			if (rsNGAYBT.next()) {
				ngaybuttoan = rsNGAYBT.getString("NGAYBUTTOAN");
				nam = ngaybuttoan.substring(0, 4);
				thang = ngaybuttoan.substring(5, 7);
			}
			rsNGAYBT.close();

			query = "SELECT COUNT(*) AS NUM FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = '" + id + "'";
			ResultSet rs = this.db.get(query);
			rs.next();

			int n = rs.getInt("NUM");
			rs.close();

			query = "";
			int dem = 0;
			for (int i = 1; i <= n; i++) {
				dem++;
				// GHI NHAN TAI KHOAN KE TOAN
				if (query.length() > 0)
					query += "UNION ALL ";

				query += "SELECT BT.NGAYBUTTOAN AS NGAYCHUNGTU, BT.NGAYBUTTOAN AS NGAYGHINHAN, N'Bút toán tổng hợp' AS LOAICHUNGTU, BT.PK_SEQ AS SOCHUNGTU, BT.DIENGIAI DIENGIAI_CT, BT.TIGIA, BT.TIENTE_FK, \n"
						+ " (SELECT TAIKHOANKT_FK FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = "
						+ i + ") AS TK_NO, \n" + " ( \n" + "	SELECT  \n"
						+ "	CASE WHEN NHOMCHIPHI_FK is not null then N'Khoản mục chi phí'   \n"
						+ "		 WHEN KHACHHANG_FK is not null then N'Khách hàng'  \n"
						+ "		 WHEN KHO_FK is not null then N'Sản phẩm'   \n"
						+ "		 WHEN NCC_FK is not null then N'Nhà cung cấp'   \n"
						+ "		 WHEN NGANHANG_FK is not null then N'Ngân hàng' \n"
						+ "		 WHEN NHANVIEN_FK is not null then N'Nhân viên' \n"
						+ "		 WHEN TAISAN_FK   is not null then N'Tài sản'   \n" + "	ELSE ''  \n"
						+ "	END AS DOITUONG_NO \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + i + " \n"
						+ " )AS DOITUONGNO, \n" + " ( \n" + "	SELECT \n"
						+ "	CASE WHEN NHOMCHIPHI_FK is not null then CAST(NHOMCHIPHI_FK as nvarchar(50))  \n"
						+ "		 WHEN KHACHHANG_FK is not null then CAST(KHACHHANG_FK as nvarchar(50))  \n"
						+ "		 WHEN KHO_FK is not null then CAST( KHO_FK as nvarchar(50))  \n"
						+ "		 WHEN NCC_FK is not null then CAST (NCC_FK as nvarchar(50))  \n"
						+ "		 WHEN NGANHANG_FK is not null then CAST(NGANHANG_FK as nvarchar(50))  \n"
						+ "		 WHEN NHANVIEN_FK is not null then CAST(NHANVIEN_FK as nvarchar(50))  \n"
						+ "		 WHEN TAISAN_FK is not null then CAST(TAISAN_FK as nvarchar(50))  \n" + "	ELSE ''  \n"
						+ "	END AS MADOITUONG_NO \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + i + " \n"
						+ " )AS MADOITUONGNO, \n"
						+ " (SELECT TAIKHOANKT_FK FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = "
						+ (i + 1) + ") AS TK_CO, \n" + " ( \n" + "	SELECT  \n"
						+ "	CASE WHEN NHOMCHIPHI_FK is not null then N'Khoản mục chi phí'   \n"
						+ "		 WHEN KHACHHANG_FK is not null then N'Khách hàng'  \n"
						+ "		 WHEN KHO_FK is not null then N'Sản phẩm'   \n"
						+ "		 WHEN NCC_FK is not null then N'Nhà cung cấp'   \n"
						+ "		 WHEN NGANHANG_FK is not null then N'Ngân hàng' \n"
						+ "		 WHEN NHANVIEN_FK is not null then N'Nhân viên' \n"
						+ "		 WHEN TAISAN_FK   is not null then N'Tài sản'   \n" + "	ELSE ''  \n"
						+ "	END AS DOITUONG_NO \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1)
						+ "  \n" + " )AS DOITUONGCO, \n" + " ( \n" + "	SELECT \n"
						+ "	CASE WHEN NHOMCHIPHI_FK is not null then CAST(NHOMCHIPHI_FK as nvarchar(50))  \n"
						+ "		 WHEN KHACHHANG_FK is not null then CAST(KHACHHANG_FK as nvarchar(50))  \n"
						+ "		 WHEN KHO_FK is not null then CAST( KHO_FK as nvarchar(50))  \n"
						+ "		 WHEN NCC_FK is not null then CAST (NCC_FK as nvarchar(50))  \n"
						+ "		 WHEN NGANHANG_FK is not null then CAST(NGANHANG_FK as nvarchar(50))  \n"
						+ "		 WHEN NHANVIEN_FK is not null then CAST(NHANVIEN_FK as nvarchar(50))  \n"
						+ "		 WHEN TAISAN_FK is not null then CAST(TAISAN_FK as nvarchar(50))  \n" + "	ELSE ''  \n"
						+ "	END AS MADOITUONG_NO \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1)
						+ " \n" + " )AS MADOITUONGCO, \n"
						+ " (SELECT ISNULL(GIATRINT,0) FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = "
						+ i + " AND NO > 0) AS SOTIENNT, \n"
						+ " (SELECT ISNULL(NO,0) FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = "
						+ i + ") AS SOTIEN, \n"
						+ " (select ISNULL(sum(TIENTHUE),0) from erp_buttoantonghop_chitiet_hoadon WHERE BTTH_FK = BT.PK_SEQ AND PKSEQ = "
						+ (dem - 1) + ") AS TIENTHUE, \n" +

						" (SELECT DIENGIAI FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = "
						+ i + ") AS DIENGIAI, \n" +

						" BT.MACHUNGTU, BT.DIENGIAI DGIAI, " + " ( \n" + "	SELECT isnull( isNPP , 0) isNPP \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1)
						+ " \n" + " )AS ISNPP, \n" + " ( \n" + "	SELECT isnull( kbh.TEN ,'') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN KENHBANHANG kbh on A.KBH_FK = kbh.PK_SEQ "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" + " )AS KBH_FK, \n"
						+ " ( \n" + "	SELECT isnull( vv.TEN ,'') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN VUVIEC vv on A.VUVIEC_FK = vv.PK_SEQ "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" + " )AS VV_FK, \n"
						+ " ( \n" + "	SELECT isnull( db.TEN ,'') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN DIABAN db on A.DIABAN_FK = db.PK_SEQ "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n" + " )AS DIABAN_FK, \n"
						+ " ( \n" + "	SELECT isnull( tt.TEN ,'') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN TINHTHANH tt on A.TINHTHANH_FK = tt.PK_SEQ "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n"
						+ " )AS TINHTHANH_FK, \n" + " ( \n" + "	SELECT isnull( bv.TEN ,'') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN KHACHHANG bv on A.BENHVIEN_FK = bv.PK_SEQ "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND STT = " + (i + 1) + " \n"
						+ " )AS BENHVIEN_FK, \n" + " ( \n" + "	SELECT isnull( NHOMCHIPHI_FK_NO ,'0') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET_TH A  "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND A.PKSEQ = " + (dem - 1) + " \n"
						+ " )AS CHIPHINO, \n" + " ( \n" + "	SELECT isnull( NHOMCHIPHI_FK_CO ,'0') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET_TH A  "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND A.PKSEQ = " + (dem - 1) + " \n"
						+ " )AS CHIPHICO, \n" + " ( \n" + "	SELECT isnull( sp.TEN ,'') TEN \n"
						+ "	FROM ERP_BUTTOANTONGHOP_CHITIET A LEFT JOIN SANPHAM sp on A.SP_FK = sp.PK_SEQ "
						+ "   WHERE A.BUTTOANTONGHOP_FK = BT.PK_SEQ AND A.STT = " + (i + 1) + " \n" + " )AS SP_FK \n" +

						" FROM ERP_BUTTOANTONGHOP BT \n" +

						" WHERE BT.PK_SEQ = '" + id + "' \n";
				i++;
			}

			System.out.println("___INIT TAI KHOAN: " + query);
			rs = db.get(query);

			Utility util = new Utility();

			while (rs.next()) {

				if (rs.getString("TK_NO").trim().length() > 0 & rs.getString("TK_CO").trim().length() > 0) {
					String isNPP = rs.getString("isNPP");
					String MAHOADON = "";
					String MAUHOADON = "";
					String KYHIEU = "";
					String SOHOADON = "";
					String NGAYHOADON = "";
					String TENNCC = "";
					String MST = "";
					String TIENHANG = "";
					String THUESUAT = "";
					String TEN_KBH = rs.getString("KBH_FK");
					String TEN_VV = rs.getString("VV_FK");
					String TEN_DIABAN = rs.getString("DIABAN_FK");
					String TEN_TINHTHANH = rs.getString("TINHTHANH_FK");
					String TEN_BENHVIEN = rs.getString("BENHVIEN_FK");
					String TEN_SANPHAM = rs.getString("SP_FK");
					String TEN_DT = "";
					String TEN_PB = "";
					String chiphico = rs.getString("CHIPHICO") == "0" ? "NULL" : rs.getString("CHIPHICO");
					String chiphino = rs.getString("CHIPHINO") == "0" ? "NULL" : rs.getString("CHIPHINO");
					chiphico = rs.getString("CHIPHICO") == "" ? "NULL" : rs.getString("CHIPHICO");
					chiphino = rs.getString("CHIPHINO") == "" ? "NULL" : rs.getString("CHIPHINO");
					String DIENGIAI_CT = rs.getString("DIENGIAI_CT");
					String DIENGIAI = rs.getString("DIENGIAI_CT");
					float TIENTHUE = rs.getFloat("TIENTHUE");

					String msg = util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC(db, thang, nam, rs.getString("NGAYCHUNGTU"),
							rs.getString("NGAYGHINHAN"), "Bút toán tổng hợp", id, rs.getString("TK_NO"),
							rs.getString("TK_CO"), "", rs.getString("SOTIEN"), rs.getString("SOTIEN"),
							rs.getString("DOITUONGNO"), rs.getString("MADOITUONGNO"), rs.getString("DOITUONGCO"),
							rs.getString("MADOITUONGCO"), "0", "", "", rs.getString("TIENTE_FK"), "",
							rs.getString("TIGIA"), rs.getString("SOTIEN"), rs.getString("SOTIENNT"), "", "0",
							rs.getString("DIENGIAI"), rs.getString("MACHUNGTU"), isNPP, MAHOADON, MAUHOADON, KYHIEU,
							SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, DIENGIAI, TEN_DT, TEN_PB, TEN_KBH,
							TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT, chiphino,
							chiphico);

					if (TIENTHUE != 0.0) {
						String tienthue1 = "" + Math.round(TIENTHUE);
						System.out.println("tienThue:" + TIENTHUE);

						String q = "select pk_seq from erp_taikhoankt where sohieutaikhoan like '13311000' and npp_fk =1";
						ResultSet rtk = db.get(q);
						String tkno = "";
						try {
							if (rtk.next()) {
								tkno = rtk.getString("pk_seq");
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						msg = util.Update_TaiKhoan_Vat_DienGiai_CHIKHAC(db, thang, nam, rs.getString("NGAYCHUNGTU"),
								rs.getString("NGAYGHINHAN"), "Bút toán tổng hợp", id, tkno, rs.getString("TK_CO"), "",
								tienthue1, tienthue1, rs.getString("DOITUONGNO"), rs.getString("MADOITUONGNO"),
								rs.getString("DOITUONGCO"), rs.getString("MADOITUONGCO"), "0", "", "",
								rs.getString("TIENTE_FK"), "", rs.getString("TIGIA"), tienthue1, "0", "Tiền thuế", "0",
								rs.getString("DIENGIAI"), rs.getString("MACHUNGTU"), isNPP, MAHOADON, MAUHOADON, KYHIEU,
								SOHOADON, NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, DIENGIAI, TEN_DT, TEN_PB,
								TEN_KBH, TEN_VV, TEN_DIABAN, TEN_TINHTHANH, TEN_BENHVIEN, TEN_SANPHAM, DIENGIAI_CT,
								"NULL", "NULL");
					}

					if (msg.trim().length() > 0) {
						db.getConnection().rollback();
						return msg;
					}

				}

			}

			// db.getConnection().commit();
			//db.shutDown();
		} catch (Exception er) {
			db.update("rollback");
			er.printStackTrace();
			return "Lỗi chốt bút toán: " + er.getMessage();

		}

		return "";

	}

	public void init() {
		NumberFormat formatter = new DecimalFormat("#,###,###");
		String query = "select pk_seq, ngaychungtu, ngayghiso, trangthai, ncc_fk, nhanvien_fk, ghichu, TONGTIENTRATRUOC, TONGTIENHOADON, TIENTE_FK, LOAIXOANO \n"
				+ "from ERP_XOANONCC \n" + "where pk_seq = '" + this.id + "'";
		ResultSet rs = db.get(query);
		System.out.println(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.loaixnId = rs.getString("loaixoano");
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.nccId = rs.getString("ncc_fk") == null ? "" : rs.getString("ncc_fk");
					this.nvId = rs.getString("nhanvien_fk") == null ? "" : rs.getString("nhanvien_fk");
					this.tienteId = rs.getString("TIENTE_FK");

					this.noidungtt = rs.getString("ghichu");
					this.sotientt = formatter.format(rs.getDouble("TONGTIENTRATRUOC"));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/*try {
			db.getConnection().setAutoCommit(false);
			this.thucHienTaoBTTH();
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				System.out.println("[Exception] : " + e1.toString());
			}
			return;
		}*/
		this.createRs();
	}

	public void initDisplay() {
		NumberFormat formatter = new DecimalFormat("#,###,###");
		String query = " select pk_seq, ngaychungtu, ngayghiso, ncc_fk,  ghichu, TONGTIENTRATRUOC, TONGTIENHOADON, TIENTE_FK  "
				+ " from ERP_XOANONCC where pk_seq = '" + this.id + "'";
		System.out.println(query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.tienteId = rs.getString("TIENTE_FK");
					this.nccId = rs.getString("ncc_fk");
					this.noidungtt = rs.getString("ghichu");
					this.sotientt = formatter.format(rs.getDouble("TONGTIENTRATRUOC"));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (this.nccId == null)
			this.nccId = "";

		this.nccRs = db
				.get(" select pk_seq, ma, ten from erp_nhacungcap where trangthai=1 and duyet = '1' and congty_fk = " + this.congtyId);

		query = "select b.pk_seq, 'Tam Ung' as kyhieu, b.pk_seq as sohoadon, b.ngayghinhan as ngayhoadon, a.sotienAVAT, a.SOTIENTT, a.ConLai "
				+ "from ERP_XOANONCC_TAMUNG a inner join ERP_THANHTOANHOADON b on a.thanhtoanhoadon_fk = b.pk_seq where a.xoanoncc_fk = '"
				+ this.id + "'";

		System.out.println("1.Khoi tao hoadon display: " + query);

		ResultSet rsHoadon = db.get(query);
		List<IHoadon> hdList = new ArrayList<IHoadon>();
		if (rsHoadon != null) {
			try {
				IHoadon hd = null;
				while (rsHoadon.next()) {
					String id = rsHoadon.getString("pk_seq");
					String kyhieu = rsHoadon.getString("kyhieu");
					String sohoadon = rsHoadon.getString("sohoadon");
					String ngayhd = rsHoadon.getString("ngayhoadon");
					String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));

					String dathanhtoan = "";
					if (rsHoadon.getDouble("SOTIENTT") > 0)
						dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));

					hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
					hdList.add(hd);
				}
				rsHoadon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.hoadonList = hdList;

		query = "select b.pk_seq, b.pk_seq as sochungtu, b.ngayghinhan as ngayhoadon, a.tienchungtu as sotienAVAT, a.tienthanhtoan as SOTIENTT, a.ConLai "
				+ " from ERP_XOANONCC_HOADONNCC a inner join ERP_PARK b on a.hoadonncc_fk = b.pk_seq where a.Xoanoncc_fk = '"
				+ this.id + "'";

		System.out.println("1.Khoi tao cttt display: " + query);

		rsHoadon = db.get(query);
		List<IHoadon> ctttList = new ArrayList<IHoadon>();
		if (ctttList != null) {
			try {
				IHoadon hd = null;
				while (rsHoadon.next()) {
					String id = rsHoadon.getString("pk_seq");
					String kyhieu = rsHoadon.getString("sochungtu");
					String sohoadon = rsHoadon.getString("sochungtu");
					String ngayhd = rsHoadon.getString("ngayhoadon");
					String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));

					String dathanhtoan = "";
					if (rsHoadon.getDouble("SOTIENTT") > 0)
						dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));

					hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
					ctttList.add(hd);
				}
				rsHoadon.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("__So cttt: " + ctttList.size());
		this.ctttList = ctttList;
	}

	private void getNppInfo() {
		// Phien ban moi
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap = util.getIdNhapp(this.userId);
	}

	public void createRs() {
		this.getNppInfo();

		NumberFormat formatter = new DecimalFormat("#,###,###");
		System.out.println(
				" select pk_seq, ma, ten from erp_nhacungcap where trangthai=1 and duyet = '1' and congty_fk = " + this.congtyId);
		this.nccRs = db
				.get(" select pk_seq, ma, ten from erp_nhacungcap where trangthai=1 and duyet = '1' and congty_fk = " + this.congtyId);

		this.nvRs = db
				.get(" select pk_seq, ma, ten from erp_nhanvien where trangthai=1 and CONGTY_FK = " + this.congtyId);

		this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");

		// Load chứng từ trả trước : Chi tạm ứng

		if (this.ctttList.size() <= 0 && (this.nccId.trim().length() > 0 || this.nvId.trim().length() > 0)) // Load chứng từ trả trước chi phí khác
		{
			String query = "";
			if (this.nccId.trim().length() > 0) {
				/*
				if (this.id.length() > 0) {
					// Cách tính ra số tiền còn lại để xóa nợ NCC như sau:
					// b.sotienTT: số tiền đã tạm ứng cho NCC
					// thanh_toan.tongthanhtoan: số tiền đã xóa tạm ứng NCC khác
					// với chứng từ được chọn
					// a.sotientt: số tiền xóa tạm ứng của chứng từ Xóa tạm ứng
					// NCC được chọn
					
					query += "SELECT 0 as loaict, b.pk_seq, N'Chi Tạm Ứng' as kyhieu, b.pk_seq as sohoadon, b.NGAYGHINHAN as ngayhoadon, \n"
							+ "	CASE WHEN B.TIENTE_FK = 100000 THEN	b.sotienTT ELSE B.SOTIENTTNT END - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, a.sotientt as DaThanhToan \n"
							+ "FROM 	ERP_XOANONCC_TAMUNG a "
							+ "INNER JOIN ERP_THANHTOANHOADON b on a.thanhtoanhoadon_fk = b.pk_seq \n" + "LEFT JOIN	\n"
							+ "		(" + "		select XTU.thanhtoanhoadon_fk, sum(XTU.SOTIENTT) as tongthanhtoan \n"
							+ "   	from ERP_XOANONCC_TAMUNG XTU inner join ERP_XOANONCC XN on XTU.XOANONCC_FK = XN.PK_SEQ \n"
							+ "  	 	where XN.trangthai != '2' and XTU.XOANONCC_FK != '" + this.id + "' \n"
							+ "   	and XTU.thanhtoanhoadon_fk in (select thanhtoanhoadon_fk from ERP_XOANONCC_TAMUNG where XOANONCC_FK = '"
							+ this.id + "') \n" + "   	group by thanhtoanhoadon_fk"
							+ "		)thanh_toan on a.thanhtoanhoadon_fk = thanh_toan.thanhtoanhoadon_fk \n"
							+ " WHERE a.XOANONCC_FK = '" + this.id + "' AND B.TIENTE_FK = '"+this.tienteId+"' \n";

					// LẤY RA NHỮNG XÓA NỢ NCC BÚT TOÁN TỔNG HỢP

					query += " UNION ALL \n" +

							" SELECT 1 as loaict, b.BUTTOANTONGHOP_FK pk_seq, N'Bút toán tổng hợp' as kyhieu, c.pk_seq as sohoadon, c.ngaybuttoan ngayhoadon,  \n"
							+ "case when c.tiente_fk ='100000' then    isnull(b.NO,0) else isnull(b.giatrint,0) end "
							+ " - isnull(thanh_toan.tienthanhtoan, '0') as tienAvat, \n"
							+ "        a.sotientt as DaThanhToan \n" + " FROM   ERP_XOANONCC_BTTH a INNER JOIN \n"
							+ " ERP_BUTTOANTONGHOP_CHITIET b ON a.btth_fk = b.buttoantonghop_fk \n"
							+ " INNER JOIN ERP_BUTTOANTONGHOP c ON b.buttoantonghop_fk = c.pk_seq \n" + " LEFT JOIN \n"
							+ " 		( \n" + "			select 	a.btth_fk, sum(a.sotientt) as tienthanhtoan \n"
							+ "			from 	ERP_XOANONCC_BTTH a \n"
							+ "					inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq \n"
							+ "			where 	b.trangthai != 2 and a.XOANONCC_FK != '" + this.id + "' \n"
							+ "					and a.btth_fk IN (	select btth_fk from ERP_XOANONCC_BTTH \n"
							+ "   									where XOANONCC_FK = '" + this.id + "' ) \n"
							+ "			group by a.btth_fk \n"
							+ " 		)thanh_toan on a.btth_fk = thanh_toan.btth_fk \n" + " WHERE a.XOANONCC_FK = '"
							+ this.id + "' and b.NO > 0 and b.NCC_FK = " + this.nccId + " and c.tiente_fk = '"+this.tienteId+"' \n" +

							"UNION ALL \n";

				}

				// CHI TẠM ỨNG NCC
				query += "(SELECT 0 as loaict, hoadon.pk_seq, N'Chi Tạm Ứng' as kyhieu, hoadon.sohoadon, hoadon.ngayhoadon,  "
						+ "		 hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0') as sotienAVAT, \n"
						+ "		 isnull(dathanhtoan.DATHANHTOAN, '') as DATHANHTOAN  \n" + " FROM \n" + " (	\n" +
						// TRƯỜNG HỢP UNC - TẠM ỨNG NCC
						"	SELECT tt.PK_SEQ, tt.PK_SEQ as sohoadon, tt.NGAYGHINHAN as ngayhoadon ,isnull(TTHD_HD.SOTIENTT,0)  as sotienAVAT \n"
						+ "	FROM ERP_THANHTOANHOADON tt  \n" + "   INNER JOIN " + "       ( \n"
						+ "         SELECT a.THANHTOANHD_FK ,"
						+ "	CASE WHEN A.TIENTE_FK='100000' THEN SUM(a.SOTIENTT) ELSE SUM(SOTIENNT) end as SOTIENTT \n"
						+ "         FROM ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ  \n"
						+ "         WHERE b.TRANGTHAI = 1 and a.LOAIHD = 1 and A.TIENTE_FK ='"+this.tienteId+"' AND b.congty_fk = " + this.congtyId
						+ "         GROUP BY a.THANHTOANHD_FK,A.TIENTE_FK "
						+ "        ) TTHD_HD ON tt.PK_SEQ = TTHD_HD.THANHTOANHD_FK " + "	WHERE TT.CONGTY_FK = "
						+ this.congtyId + " AND TT.TRANGTHAI=1 and tt.NCC_FK = '" + this.nccId
						+ "'  AND tt.TIENTE_FK = " + this.tienteId + " "
						+ " 	and tt.pk_seq not in (SELECT thanhtoanhoadon_fk FROM ERP_XOANONCC_TAMUNG WHERE xoanoncc_fk = '"
						+ (this.id == "" ? "0" : this.id) + "')  ";

				query += " ) HOADON \n" + " LEFT JOIN  \n" + " ( \n"
						+ "   SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) as DATHANHTOAN  \n" + "	FROM  "
						+ "		( \n" +

						// TRƯỜNG HỢP XÓA TẠM ỨNG KHÁC VỚI XÓA TẠM ỨNG ĐANG ĐƯỢC
						// CHỌN
						"	   	SELECT XNTU.THANHTOANHOADON_FK as HOADON_FK, sum(XNTU.SOTIENTT) as DATHANHTOAN   \n"
						+ "	   	FROM ERP_XOANONCC_TAMUNG XNTU "
						+ "		INNER JOIN ERP_XOANONCC XN on XNTU.XOANONCC_FK = XN.PK_SEQ  \n"
						+ "	   	WHERE XN.TRANGTHAI != 2 and XN.CONGTY_FK = " + this.congtyId + " \n ";

				if (this.id.trim().length() > 0)
					query += " 		and XN.pk_seq != '" + this.id + "' \n";

				query += " 		GROUP BY THANHTOANHOADON_FK  \n" +

						"      	UNION ALL   \n" +

						// TRƯỜNG HỢP THU HỒI TẠM ỨNG NCC
						"       SELECT TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN  \n"
						+ "	   	FROM ERP_THUTIEN_HOADON TTHD "
						+ "		INNER JOIN ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ \n"
						+ "       WHERE TT.NOIDUNGTT_FK = 100001 AND  TT.TRANGTHAI != 2 and TT.NCC_FK= '" + this.nccId
						+ "' and TT.CONGTY_FK = " + this.congtyId + " \n" + "       GROUP BY HOADON_FK  \n" +

						"      	UNION ALL  \n" +

						// TRƯỜNG HỢP XÓA TẠM ỨNG CHO NCC
						"       SELECT XHD.HOADON_FK , sum(XHD.SOTIENTT) as DATHANHTOAN  \n"
						+ "       FROM ERP_XOAKHTRATRUOC_HOADON XHD "
						+ "		INNER JOIN ERP_XOAKHTRATRUOC XTT on XHD.xoakhtratruoc_fk = XTT.PK_SEQ \n"
						+ "       WHERE  XTT.TRANGTHAI != 2 AND XTT.NCC_FK = '" + this.nccId + "' and XTT.CONGTY_FK = "
						+ this.congtyId + " \n" + "       GROUP BY HOADON_FK  \n" + "	) HOADONDATT  \n"
						+ "	group by HOADON_FK \n" + ") dathanhtoan on hoadon.pk_seq = dathanhtoan.hoadon_fk \n"
						+ " WHERE round(hoadon.sotienAVAT - isnull(dathanhtoan.DATHANHTOAN, '0'), 0) > 0 ) ";

				// LẤY RA NHỮNG XÓA NỢ NCC BÚT TOÁN TỔNG HỢP

				query += " UNION ALL \n" +

						" select 1 as loaict, chungtu.pk_seq pk_seq, N'Bút toán tổng hợp' as kyhieu, chungtu.pk_seq as sohoadon,chungtu.ngaychungtu ngayhoadon,  \n"
						+ "       isnull(chungtu.sotientt,0) - (ISNULL(dathanhtoan.tienthanhtoan, 0)) as tienAvat, \n"
						+ "       0 as DaThanhToan \n" + " from \n" + " ( \n"
						+ "	select	a.pk_seq, CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYBUTTOAN ngaychungtu,"
						+ "CASE WHEN A.TIENTE_FK = '100000' THEN sum(isnull(NO,0)) ELSE SUM(ISNULL(GIATRINT,0)) END as SOTIENTTNT,"
						+ "CASE WHEN A.TIENTE_FK = '100000' THEN sum(isnull(NO,0)) ELSE SUM(ISNULL(GIATRINT,0)) END AS sotientt,"
						+ " 1 as tigia \n"
						+ "	from	ERP_BUTTOANTONGHOP a INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK \n"
						+ "	where	trangthai = '1' and a.CONGTY_FK = " + this.congtyId + " \n"
						+ "			and b.ncc_fk = '" + this.nccId
						+ "' and a.tiente_fk ='"+this.tienteId+"' and b.NO > 0 and TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33111000' and CONGTY_FK = "
						+ this.congtyId + ")";

				query += "	and pk_seq not in (select btth_fk from ERP_XOANONCC_BTTH where xoanoncc_fk = '"
						+ (this.id == "" ? "0" : this.id) + "' ) \n";

				query += "	group by a.pk_seq, khachhang_fk, TAIKHOANKT_FK, ngaybuttoan,A.TIENTE_FK \n" + " )chungtu \n"
						+ " left join \n" + " ( \n" + "	select 	a.btth_fk, sum(a.sotientt) as tienthanhtoan \n"
						+ "	from 	ERP_XOANONCC_BTTH a \n"
						+ "			inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq \n"
						+ "	where 	b.trangthai != 2 and b.ncc_fk = " + this.nccId + " and b.CONGTY_FK = "
						+ this.congtyId + " \n";

				query += " and b.pk_seq != '" + (this.id == "" ? "0" : this.id) + "' \n"+
				         " group by a.btth_fk  \n" + 
						 " union all  \n"+
						 " SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN  \n"+
						 " FROM ERP_THUTIEN_HOADON TTHD  \n"+
						 " INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ  \n"+
						 " WHERE TTHD.LOAIHOADON= '11' AND TT.TRANGTHAI NOT IN (2) AND TT.TIENTE_FK = " + this.tienteId + "  \n"+
						 " GROUP BY TTHD.HOADON_FK  \n";
						 
				query += " )dathanhtoan on chungtu.pk_seq = dathanhtoan.btth_fk  \n"
						+ "where chungtu.sotientt - ISNULL(dathanhtoan.tienthanhtoan, 0) > 0 \n";
				*/
					query = "EXEC GET_XOANONCC_CTTT "
							+ this.nccId + ","
							+ (this.id!=null && this.id.trim().length()==0? "0" : this.id) + " ,"
							+ this.tienteId + " ,"
							+ " 0, 0, " + this.congtyId + ", '"+this.ngaychungtu+"'";
					
			} else if (this.nvId.trim().length() > 0) {
				if (this.id.trim().length() > 0) {
					query += " SELECT 0 as loaict, b.pk_seq, N'Chi Tạm Ứng' as kyhieu, '' as sohoadon, b.NGAYGHINHAN as ngayhoadon, \n"
							+ "        b.sotienTT - isnull(thanh_toan.tongthanhtoan, '0') as sotienAVAT, a.sotientt as DaThanhToan \n"
							+ " FROM ERP_XOANONCC_TAMUNG a inner join ERP_THANHTOANHOADON b on a.thanhtoanhoadon_fk = b.pk_seq \n"
							+ "      left join	\n" + "      ("
							+ "		select XTU.thanhtoanhoadon_fk, sum(XTU.SOTIENTT) as tongthanhtoan \n"
							+ "   	from ERP_XOANONCC_TAMUNG XTU inner join ERP_XOANONCC XN on XTU.XOANONCC_FK = XN.PK_SEQ \n"
							+ "   	where XN.trangthai != '2' and XTU.XOANONCC_FK != '" + this.id
							+ "' and XN.CONGTY_FK = " + this.congtyId + " \n"
							+ "   			and XTU.thanhtoanhoadon_fk in (select thanhtoanhoadon_fk from ERP_XOANONCC_TAMUNG where XOANONCC_FK = '"
							+ this.id + "') \n" + "   	group by thanhtoanhoadon_fk"
							+ "		)thanh_toan on a.thanhtoanhoadon_fk = thanh_toan.thanhtoanhoadon_fk \n"
							+ " WHERE a.XOANONCC_FK = '" + this.id + "' \n" +

							" UNION ALL ";

					// LẤY RA NHỮNG XÓA NỢ NCC BÚT TOÁN TỔNG HỢP

					query += "select 1 as loaict, b.BUTTOANTONGHOP_FK pk_seq, N'Bút toán tổng hợp' as kyhieu, cast(c.pk_seq as nvarchar(50)) as sohoadon, c.ngaybuttoan ngayhoadon,  \n"
							+ "       isnull(b.NO,0) - isnull(thanh_toan.tienthanhtoan, '0') as tienAvat, \n"
							+ "       a.sotientt as DaThanhToan \n" + "from  ERP_XOANONCC_BTTH a INNER JOIN \n"
							+ "		ERP_BUTTOANTONGHOP_CHITIET b ON a.btth_fk = b.buttoantonghop_fk \n"
							+ "		INNER JOIN ERP_BUTTOANTONGHOP c ON b.buttoantonghop_fk = c.pk_seq \n"
							+ " 		left join \n" + " 		( \n"
							+ "			select 	a.btth_fk, sum(a.sotientt) as tienthanhtoan \n"
							+ "			from 	ERP_XOANONCC_BTTH a \n"
							+ "					inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq \n"
							+ "			where 	b.trangthai != 2 and a.XOANONCC_FK != '" + this.id + "' \n"
							+ "					and a.btth_fk IN (	select btth_fk from ERP_XOANONCC_BTTH \n"
							+ "   									where XOANONCC_FK = '" + this.id + "' ) \n"
							+ "			group by a.btth_fk \n"
							+ " 		)thanh_toan on a.btth_fk = thanh_toan.btth_fk \n" + "where a.XOANONCC_FK = '"
							+ this.id + "' and b.NO > 0 and b.NHANVIEN_FK = " + this.nvId + " \n" +

							"UNION ALL \n";

				}

				query += " SELECT 0 as loaict, TTHD.PK_SEQ, N'Chi tạm ứng' as kyhieu, '' AS SOHOADON, TTHD.NGAYGHINHAN as NGAYHOADON, \n"
						+ "		  isnull(TTHD_HD.SOTIENTT,0) - ISNULL(DACANTRU.CANTRU,0) - ISNULL(DAXOANO.XOANO,0) - ISNULL(THUHOITU.DATHANHTOAN,0) SOTIENAVAT, 0 AS DATHANHTOAN \n"
						+ " FROM ERP_THANHTOANHOADON TTHD INNER JOIN ERP_NHANVIEN NV ON TTHD.NHANVIEN_FK = NV.PK_SEQ \n"
						+ " LEFT JOIN  \n" + "     ( \n"
						+ "      select a.THANHTOANHD_FK, SUM(a.SOTIENTT)as SOTIENTT \n"
						+ "      from ERP_THANHTOANHOADON_HOADON a inner join ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.PK_SEQ \n"
						+ "      where a.LOAIHD = 1 and b.TRANGTHAI = 1 and b.NHANVIEN_FK = " + this.nvId
						+ " and b.CONGTY_FK = " + this.congtyId + "  \n" + "      group by a.THANHTOANHD_FK \n"
						+ "     )TTHD_HD ON TTHD.PK_SEQ = TTHD_HD.THANHTOANHD_FK \n" + " LEFT JOIN  \n" + "     ( \n"
						+ "      select a.THANHTOANHOADON_FK, SUM(a.SOTIENCANTRU) as CANTRU \n"
						+ "      from ERP_DENGHITT_THANHTOANHOADON a inner join ERP_MUAHANG b on a.DENGHITT_FK = b.PK_SEQ \n"
						+ "      where b.TRANGTHAI not in (3,4) and b.NHANVIEN_FK = " + this.nvId
						+ " and b.CONGTY_FK = " + this.congtyId + "  \n" + "      group by a.THANHTOANHOADON_FK \n"
						+ "     )DACANTRU ON TTHD.PK_SEQ = DACANTRU.THANHTOANHOADON_FK \n" + " LEFT JOIN \n"
						+ "     ( \n" + "      select a.THANHTOANHOADON_FK, SUM(a.sotientt) as XOANO \n"
						+ "      from ERP_XOANONCC_TAMUNG a inner join ERP_XOANONCC b on a.xoanoncc_fk = b.PK_SEQ \n"
						+ "      where b.TRANGTHAI != 2 and b.NHANVIEN_FK = " + this.nvId + " and b.CONGTY_FK = "
						+ this.congtyId + "  \n";

				if (this.id.trim().length() > 0) {
					query += " and b.PK_SEQ != " + this.id + " ";
				}

				query += "      group by a.THANHTOANHOADON_FK \n"
						+ "     )DAXOANO ON TTHD.PK_SEQ = DAXOANO.THANHTOANHOADON_FK \n" + " LEFT JOIN  \n"
						+ "     ( \n" + "      select TTHD.HOADON_FK , sum(TTHD.SOTIENTT) as DATHANHTOAN   \n"
						+ "	     from ERP_THUTIEN_HOADON TTHD  inner join ERP_THUTIEN TT on TTHD.THUTIEN_FK = TT.PK_SEQ  \n"
						+ "      where TT.NOIDUNGTT_FK = 100001 AND  TT.TRANGTHAI != 2 and TT.NHANVIEN_FK = "
						+ this.nvId + " and TT.CONGTY_FK = " + this.congtyId + " \n"
						+ "      group by TTHD.HOADON_FK   \n"
						+ "      ) THUHOITU ON TTHD.PK_SEQ = THUHOITU.HOADON_FK \n" + " WHERE TTHD.CONGTY_FK = "
						+ this.congtyId + "  and TTHD.TRANGTHAI = 1  and  \n"
						+ "       ( isnull(TTHD_HD.SOTIENTT,0) - ISNULL(DACANTRU.CANTRU,0) - ISNULL(DAXOANO.XOANO,0) - ISNULL(THUHOITU.DATHANHTOAN,0) >0 )  \n";
				if (this.id.trim().length() > 0) {
					query += " AND TTHD.PK_SEQ != ( select THANHTOANHOADON_FK from ERP_XOANONCC_TAMUNG where XOANONCC_FK =  "
							+ this.id + " ) ";
				}

				// LẤY RA NHỮNG XÓA NỢ NHÂN VIÊN BÚT TOÁN TỔNG HỢP

				query += " UNION ALL \n" +

						" SELECT 1 as loaict, chungtu.pk_seq pk_seq, N'Bút toán tổng hợp' as kyhieu, cast(chungtu.pk_seq as nvarchar(50)) as sohoadon,chungtu.ngaychungtu ngayhoadon,  \n"
						+ "       isnull(chungtu.sotientt,0) - (ISNULL(dathanhtoan.tienthanhtoan, 0)) as tienAvat, \n"
						+ "       0 as DaThanhToan \n" + " FROM \n" + " ( \n"
						+ "	SELECT	a.pk_seq, CAST(a.pk_seq as varchar(10)) as sochungtu, NGAYBUTTOAN ngaychungtu, sum(isnull(NO,0)) as SOTIENTTNT, sum(NO) sotientt, 1 as tigia \n"
						+ "	FROM	ERP_BUTTOANTONGHOP a INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK \n"
						+ "	WHERE	trangthai = '1' and a.CONGTY_FK = " + this.congtyId + " \n"
						+ "			and b.nhanvien_fk = '" + this.nvId
						+ "' and b.NO > 0 and TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' and CONGTY_FK = "
						+ this.congtyId + ")";

				query += "	and pk_seq not in (select btth_fk from ERP_XOANONCC_BTTH where xoanoncc_fk = '"
						+ (this.id == "" ? "0" : this.id) + "' ) \n";

				query += "	group by a.pk_seq, khachhang_fk, TAIKHOANKT_FK, ngaybuttoan \n" + " )chungtu \n"
						+ " LEFT JOIN \n" + " ( \n" + "	select 	a.btth_fk, sum(a.sotientt) as tienthanhtoan \n"
						+ "	from 	ERP_XOANONCC_BTTH a \n"
						+ "			inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq \n"
						+ "	where 	b.trangthai != 2 and b.nhanvien_fk = " + this.nvId + " and b.CONGTY_FK = "
						+ this.congtyId + " \n";

				query += " and b.pk_seq != '" + (this.id == "" ? "0" : this.id) + "' \n";

				query += "	group by a.btth_fk  \n" + " )dathanhtoan on chungtu.pk_seq = dathanhtoan.btth_fk  \n"
						+ " WHERE chungtu.sotientt - ISNULL(dathanhtoan.tienthanhtoan, 0) > 0 \n";
			}

			System.out.println("1.Khoi tao chứng từ trả trước: " + query);

			ResultSet rsHoadon = db.get(query);
			List<IHoadon> cttList = new ArrayList<IHoadon>();
			if (rsHoadon != null) {
				try {
					IHoadon hd = null;
					while (rsHoadon.next()) {
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
						String avat = formatter.format(rsHoadon.getDouble("sotienAVAT"));
						String loaict = rsHoadon.getString("loaict");

						String dathanhtoan = "";
						if (this.id.length() > 0) {
							if (rsHoadon.getDouble("DaThanhToan") > 0)
								dathanhtoan = formatter.format(rsHoadon.getDouble("DaThanhToan"));
						}

						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setLoaict(loaict);

						cttList.add(hd);
					}
					rsHoadon.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			this.ctttList = cttList;
		}

		// Load Hóa đơn : NCC(hóa đơn ncc) , NV(Đề nghị TT)

		if ((this.nccId.trim().length() > 0 || this.nvId.trim().length() > 0) && this.hoadonList.size() <= 0) // Khởi tạo hóa đơn
		{
			String query = "";

			if (this.nccId.trim().length() > 0) {
				/*
				if (this.id.length() > 0) {
					// LOAICT = 0 : HÓA ĐƠN NHÀ CUNG CẤP
					query += " SELECT c.pk_seq, c.kyhieu, c.sohoadon , c.ngayhoadon , \n"
							+ " (c.sotienAVAT) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat, a. tienthanhtoan as SOTIENTT,  \n"
							+ " isnull((SELECT distinct D.SOPO + ',' \n"
							+ " 		  FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_MUAHANG D ON A.MUAHANG_FK = D.PK_SEQ AND HOADONNCC_FK = c.PK_SEQ \n"
							+ " 		  for xml path('') ),'') SOPO, 0 as loaict \n"
							+ " FROM  ERP_XOANONCC_HOADONNCC a \n"
							+ " INNER JOIN ERP_HOADONNCC c on a.hoadonncc_fk = c.pk_seq and a.LOAICT = 0 \n"
							+ " INNER JOIN ERP_PARK b on c.PARK_FK = b.pk_seq \n" + " LEFT JOIN \n " + " ( \n"
							+ "	SELECT a.HOADON_FK , sum(a.SOTIENTT) as sotientt   \n"
							+ "   FROM ERP_THANHTOANHOADON_HOADON a \n"
							+ "	INNER JOIN ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.pk_seq    \n"
							+ "   WHERE b.trangthai = 1 and b.ncc_fk ='" + this.nccId + "' and a.LOAIHD = 0   \n"
							+ "   GROUP BY a.HOADON_FK  \n" + " ) HDTT on HDTT.HOADON_FK = a.hoadonncc_fk  \n"
							+ " LEFT JOIN	\n" + "   ("
							+ "		SELECT TTHD.HOADONNCC_FK, sum(TTHD.TienThanhToan) as tongthanhtoan \n"
							+ "       FROM ERP_XOANONCC_HOADONNCC TTHD inner join ERP_XOANONCC TT on TTHD.XOANONCC_FK = TT.PK_SEQ \n"
							+ "       WHERE TT.trangthai != '2' and TTHD.XOANONCC_FK != '" + this.id + "' \n"
							+ "       and TTHD.hoadonncc_fk in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where XOANONCC_FK = '"
							+ this.id + "') and TTHD.LOAICT = 0 \n" + "       GROUP BY TTHD.HOADONNCC_FK"
							+ "	)thanh_toan on a.HOADONNCC_FK = thanh_toan.HOADONNCC_FK \n" + " WHERE a.XOANONCC_FK = '"
							+ this.id + "' AND a.LOAICT = 0 and b.tiente_fk ='"+this.tienteId+"' \n";

					// LOAICT = 1: BÚT TOÁN TỔNG HỢP

					query += " UNION ALL \n";

					query += " SELECT b.pk_seq, N'Bút toán tổng hợp' Kyhieu, cast(b.pk_seq as nvarchar(50)) sohoadon , b.NGAYBUTTOAN, \n"
							+ " (PLSP.SOTIENVND) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat, a.tienthanhtoan as SOTIENTT, ' ' SOPO, 1 as loaict \n"
							+ " FROM  ERP_XOANONCC_HOADONNCC a \n"
							+ " INNER JOIN ERP_BUTTOANTONGHOP b on a.hoadonncc_fk = b.PK_SEQ \n"
							+ " INNER JOIN (	SELECT BUTTOANTONGHOP_FK, NCC_FK,"
							+ " case when BT.TIENTE_FK = '100000' THEN SUM(CO) ELSE SUM(GIATRINT) END AS SOTIENVND \n"
							+ "               FROM   ERP_BUTTOANTONGHOP_CHITIET CT \n"
							+ " 	INNER JOIN ERP_BUTTOANTONGHOP BT ON BT.PK_SEQ = CT.BUTTOANTONGHOP_FK "
							+ "               WHERE  TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33111000' and CONGTY_FK = "
							+ this.congtyId + ") \n"
							+ "			AND TIENTE_FK = '"+this.tienteId+"'		   AND NCC_FK IS NOT NULL AND CO > 0 AND NCC_FK = '" + this.nccId
							+ "'  \n"
							+ "				GROUP BY BUTTOANTONGHOP_FK, NCC_FK,TIENTE_FK ) AS PLSP  ON b.PK_SEQ= PLSP.BUTTOANTONGHOP_FK \n"
							+ "  LEFT JOIN	\n" + "  ( \n"
							+ "	  SELECT TTHD.HOADONNCC_FK, sum(TTHD.TienThanhToan) as tongthanhtoan \n"
							+ "     FROM ERP_XOANONCC_HOADONNCC TTHD inner join ERP_XOANONCC TT on TTHD.XOANONCC_FK = TT.PK_SEQ \n"
							+ "     WHERE TT.trangthai != '2' and TTHD.XOANONCC_FK != '" + this.id + "' \n"
							+ "     and TTHD.hoadonncc_fk in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where XOANONCC_FK = '"
							+ this.id + "') and TTHD.LOAICT = 1 \n" + "     GROUP BY TTHD.HOADONNCC_FK \n"
							+ "	) thanh_toan on a.HOADONNCC_FK = thanh_toan.HOADONNCC_FK \n"
							+ " WHERE a.XOANONCC_FK = '" + this.id + "' AND a.LOAICT = 1 \n";

					query += " UNION ALL \n";
				}

				// HÓA ĐƠN NCC
				query += " SELECT chungtu.pk_seq, chungtu.kyhieu, chungtu.sohoadon, chungtu.ngayhoadon, chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0) as tienAvat, \n "
						+ "		0 as SOTIENTT, isnull(chungtu.SOPO,'') SOPO, 0 as loaict \n " + " FROM \n" + " 	( \n"
						+ "	SELECT b.PK_SEQ, b.kyhieu, b.sohoadon, b.PK_SEQ as sochungtu, b.ngayhoadon, b.sotienAVAT - isnull(HDTT.sotientt,0) as tienAvat,   \n"
						+ "			(SELECT distinct C.SOPO + ',' \n"
						+ "			FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_MUAHANG C ON A.MUAHANG_FK = C.PK_SEQ AND HOADONNCC_FK = b.PK_SEQ \n"
						+ "			for xml path('') ) SOPO \n" + "	FROM ERP_PARK a \n"
						+ "	INNER JOIN ERP_HOADONNCC b on a.PK_SEQ=b.park_fk  \n" + "   LEFT JOIN \n" + "   ("
						+ "		SELECT a.HOADON_FK , sum(a.SOTIENTT) as sotientt  \n"
						+ "       FROM ERP_THANHTOANHOADON_HOADON a "
						+ "		INNER JOIN ERP_THANHTOANHOADON b on a.THANHTOANHD_FK = b.pk_seq   \n"
						+ "       WHERE b.trangthai = 1 and b.ncc_fk ='" + this.nccId + "' and a.LOAIHD = 0   \n"
						+ "       GROUP by a.HOADON_FK  \n" + "	) HDTT on HDTT.HOADON_FK = a.pk_seq \n"
						+ " WHERE a.TRANGTHAI=2 and a.ncc_fk ='" + this.nccId + "'  AND a.TIENTE_FK = " + this.tienteId
						+ "  and b.CONGTY_FK = " + this.congtyId + "  \n ";

				if (this.id.length() > 0) {
					query += " and b.pk_seq not in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where xoanoncc_fk = '"
							+ this.id + "' and loaict = 0 ) \n";
				}
				query += "  )chungtu \n" + "LEFT JOIN  \n" + " (  \n"
						+ " 	SELECT a.hoadonncc_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"
						+ " 	FROM ERP_XOANONCC_HOADONNCC a inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq  \n"
						+ " 	WHERE b.ncc_fk ='" + this.nccId + "' and b.CONGTY_FK = " + this.congtyId
						+ " AND b.TRANGTHAI NOT IN (2) and a.loaict = 0  \n";
				if (this.id.trim().length() > 0)
					query += " and b.pk_seq != '" + this.id + "' \n";

				query += "	GROUP BY a.hoadonncc_fk \n"
						+ " )dathanhtoan on chungtu.pk_seq = dathanhtoan.hoadonncc_fk \n"
						+ "WHERE round(chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0), 0) > 0 ";

				// ERP_BUTTOANTONGHOP

				query += " UNION ALL \n" +

						" SELECT chungtu.pk_seq, chungtu.kyhieu, chungtu.sohoadon, chungtu.ngayhoadon, chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0) as tienAvat,     \n "
						+ "		0 as SOTIENTT, isnull(chungtu.SOPO,'') SOPO, 1 as loaict \n " + " FROM \n" + " ( \n"
						+ "	SELECT a.PK_SEQ, N'Bút toán tổng hợp' KYHIEU,  cast(a.PK_SEQ as nvarchar(50) ) sohoadon, a.PK_SEQ as sochungtu, a.NGAYBUTTOAN ngayhoadon,\n"
						+ "case when a.tiente_Fk = '100000' then isnull(b.CO,0) else isnull(b.giatrint,0) end - isnull(HDTT.sotientt,0) as tienAvat, ' ' SOPO   \n"
						+ "	FROM ERP_BUTTOANTONGHOP a \n"
						+ "	INNER JOIN ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK  \n"
						+ "   LEFT JOIN \n" + "   ("
						+ "		SELECT a.HOADONNCC_FK HOADON_FK , sum(a.TIENTHANHTOAN) as sotientt  \n"
						+ "       FROM ERP_XOANONCC_HOADONNCC a \n"
						+ "		INNER JOIN ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq   \n"
						+ "       WHERE b.trangthai = 1 and b.ncc_fk ='" + this.nccId + "' and a.LOAICT = 1    \n"
						+ "       GROUP BY a.HOADONNCC_FK  \n" + "	) HDTT on HDTT.HOADON_FK = a.pk_seq \n"
						+ "   WHERE a.TIENTE_FK ='"+this.tienteId+"'and a.TRANGTHAI = 1 and b.ncc_fk ='" + this.nccId + "' and a.CONGTY_FK = "
						+ this.congtyId + " AND isnull(b.CO,0) > 0  AND \n "
						+ "	b.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33111000' and CONGTY_FK = "
						+ this.congtyId + ")  ";

				if (this.id.length() > 0) {
					query += " and a.PK_SEQ not in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where xoanoncc_fk = '"
							+ this.id + "' and loaict = 1 ) \n";
				}

				query += " )chungtu \n" + " LEFT JOIN  \n" + " (  \n"
						+ " 	SELECT a.hoadonncc_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"
						+ " 	FROM ERP_XOANONCC_HOADONNCC a inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq  \n"
						+ " 	WHERE b.ncc_fk ='" + this.nccId + "' and b.CONGTY_FK = " + this.congtyId
						+ " AND b.TRANGTHAI NOT IN (2) and a.loaict = 1  \n";
				if (this.id.trim().length() > 0)
					query += " and b.pk_seq != '" + this.id + "' \n";

				query += "	GROUP BY a.hoadonncc_fk \n"
						+ " )dathanhtoan on chungtu.pk_seq = dathanhtoan.hoadonncc_fk \n"
						+ "  WHERE round(chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0), 0) > 0 ";
				*/
				
				query = "EXEC GET_XOANONCC_HOADON "
						+ this.nccId + ","
						+ (this.id!=null && this.id.trim().length()==0? "0" : this.id) + " ,"
						+ this.tienteId + " ,"
						+ " 0, 0, " + this.congtyId + ", '"+this.ngaychungtu+"'";
				
			} else if (this.nvId.trim().length() > 0) {
				if (this.id.trim().length() > 0) {
					// ĐỀ NGHỊ THANH TOÁN
					query = "SELECT DNTT.PK_SEQ, N'Đề nghị thanh toán' as KYHIEU, CAST(DNTT.SOPO as nvarchar(50)) as SOHOADON, DNTT.NGAYMUA as NGAYHOADON, \n"
							+ "       XN.TIENCHUNGTU AS TIENAVAT, XN.TIENTHANHTOAN AS SOTIENTT, '' SOPO, 0 as loaict \n"
							+ "FROM ERP_MUAHANG DNTT \n"
							+ "     INNER JOIN ERP_XOANONCC_DNTT XN ON DNTT.PK_SEQ = XN.DNTT_FK \n"
							+ "WHERE XN.xoanoncc_fk = " + this.id + " AND DNTT.NHANVIEN_FK = " + this.nvId
							+ " AND XN.LOAICT = 0 \n" +

							"UNION ALL \n";

					// LOAICT = 1: BÚT TOÁN TỔNG HỢP

					query += " SELECT b.pk_seq, N'Bút toán tổng hợp' Kyhieu, cast(b.pk_seq as nvarchar(50)) sohoadon , b.NGAYBUTTOAN, \n"
							+ " (PLSP.SOTIENVND) - isnull(thanh_toan.tongthanhtoan, '0') as tienAvat, a.tienthanhtoan as SOTIENTT, ' ' SOPO, 1 as loaict \n"
							+ " FROM  ERP_XOANONCC_DNTT a \n"
							+ "      INNER JOIN ERP_BUTTOANTONGHOP b on a.dntt_fk = b.PK_SEQ \n"
							+ "	   INNER JOIN (	SELECT BUTTOANTONGHOP_FK, NHANVIEN_FK,SUM(CO) AS SOTIENVND \n"
							+ "                   FROM   ERP_BUTTOANTONGHOP_CHITIET \n"
							+ "                   WHERE  TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' and CONGTY_FK = "
							+ this.congtyId + ") \n"
							+ "						   AND NHANVIEN_FK IS NOT NULL AND CO > 0 AND NHANVIEN_FK = '"
							+ this.nvId + "'  \n"
							+ "					GROUP BY BUTTOANTONGHOP_FK, NHANVIEN_FK ) AS PLSP  ON b.PK_SEQ= PLSP.BUTTOANTONGHOP_FK "
							+ "   	LEFT JOIN	\n" + "   	("
							+ "			SELECT TTHD.DNTT_FK HOADONNCC_FK, sum(TTHD.TienThanhToan) as tongthanhtoan \n"
							+ "       	FROM ERP_XOANONCC_DNTT TTHD inner join ERP_XOANONCC TT on TTHD.XOANONCC_FK = TT.PK_SEQ \n"
							+ "      	 	WHERE TT.trangthai != '2' and TTHD.XOANONCC_FK != '" + this.id + "' \n"
							+ "       	and TTHD.DNTT_FK in (select hoadonncc_fk from ERP_XOANONCC_HOADONNCC where XOANONCC_FK = '"
							+ this.id + "') and TTHD.LOAICT = 1 \n" + "       	GROUP BY TTHD.DNTT_FK"
							+ "		) thanh_toan on a.DNTT_FK = thanh_toan.HOADONNCC_FK \n" + " WHERE a.XOANONCC_FK = '"
							+ this.id + "' AND a.LOAICT = 1 \n";

					query += " UNION ALL \n";

				}

				// ĐỀ NGHỊ THANH TOÁN
				query += "SELECT DNTT.PK_SEQ, N'Đề nghị thanh toán' as KYHIEU, CAST(DNTT.SOPO as nvarchar(50)) as SOHOADON, DNTT.NGAYMUA as NGAYHOADON, \n"
						+ "       DNTT.TONGTIENCONLAI - ISNULL(DATHANHTOAN.DATHANHTOAN,0) - ISNULL(DAXOANO.TIENTHANHTOAN,0) AS TIENAVAT, 0 AS SOTIENTT, '' SOPO, 0 as loaict \n"
						+ "FROM ERP_MUAHANG DNTT \n" + "     LEFT JOIN  \n"
						+ "     (SELECT b.HOADON_FK, SUM(b.SOTIENTT) as DATHANHTOAN \n"
						+ "      FROM  ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_HOADON b on a.PK_SEQ = b.THANHTOANHD_FK \n"
						+ "      WHERE a.TRANGTHAI != 2 and b.LOAIHD = 6 and a.CONGTY_FK = " + this.congtyId + "  \n"
						+ "      GROUP BY b.HOADON_FK \n"
						+ "     )DATHANHTOAN ON DNTT.PK_SEQ = DATHANHTOAN.HOADON_FK \n" + "    LEFT JOIN  " + "     (  "
						+ " 	SELECT a.dntt_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"
						+ " 	FROM ERP_XOANONCC_DNTT a inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq  \n"
						+ " 	WHERE b.nhanvien_fk ='" + this.nvId + "' and b.CONGTY_FK = " + this.congtyId + " \n";

				if (this.id.trim().length() > 0)
					query += "         and b.pk_seq != '" + this.id + "' \n";

				query += "      GROUP BY a.dntt_fk \n" + "     )daxoano on DNTT.pk_seq = daxoano.dntt_fk \n"
						+ "WHERE DNTT.TRANGTHAI = 1 AND DNTT.LOAIHANGHOA_FK = 2 and TYPE = '1' and DNTT.NHANVIEN_FK = "
						+ this.nvId + " and DNTT.CONGTY_FK = " + this.congtyId + "  \n"
						+ " AND  DNTT.TONGTIENCONLAI - ISNULL(DATHANHTOAN.DATHANHTOAN,0) - ISNULL(DAXOANO.TIENTHANHTOAN,0) >0 ";

				if (this.id.trim().length() > 0)
					query += " and DNTT.pk_seq != ( select DNTT_FK from ERP_XOANONCC_DNTT where XOANONCC_FK = '"
							+ this.id + "')  \n";

				// ERP_BUTTOANTONGHOP

				query += " UNION ALL \n" +

						" select chungtu.pk_seq, chungtu.kyhieu, chungtu.sohoadon, chungtu.ngayhoadon, chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0) as tienAvat,     \n "
						+ "		0 as SOTIENTT, isnull(chungtu.SOPO,'') SOPO, 1 as loaict \n " + " from \n" + " ( \n"
						+ "	select a.PK_SEQ, N'Bút toán tổng hợp' KYHIEU,  cast(a.PK_SEQ as nvarchar(50) ) sohoadon, a.PK_SEQ as sochungtu, a.NGAYBUTTOAN ngayhoadon, isnull(b.CO,0) - isnull(HDTT.sotientt,0) as tienAvat, ' ' SOPO   \n"
						+ "	from ERP_BUTTOANTONGHOP a \n"
						+ "	inner join ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK  \n"
						+ "   left join \n" + "   ("
						+ "		select a.DNTT_FK HOADON_FK , sum(a.TIENTHANHTOAN) as sotientt  \n"
						+ "       from ERP_XOANONCC_DNTT a \n"
						+ "		inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq   \n"
						+ "       where b.trangthai = 1 and b.nhanvien_fk ='" + this.nvId + "' and a.LOAICT = 1    \n"
						+ "       group by a.DNTT_FK  \n" + "	) HDTT on HDTT.HOADON_FK = a.pk_seq \n"
						+ "   where a.TRANGTHAI = 1 and b.nhanvien_fk ='" + this.nvId + "' and a.CONGTY_FK = "
						+ this.congtyId + " AND isnull(b.CO,0) > 0  AND \n "
						+ "	b.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '14100000' and CONGTY_FK = "
						+ this.congtyId + ")  ";

				if (this.id.length() > 0) {
					query += " and a.PK_SEQ not in (select dntt_fk from ERP_XOANONCC_DNTT where xoanoncc_fk = '"
							+ this.id + "' and loaict = 1 ) \n";
				}

				query += " )chungtu \n" + " left join  \n" + " (  \n"
						+ " 	select a.dntt_fk hoadonncc_fk, sum(a.tienthanhtoan) as tienthanhtoan \n"
						+ " 	from ERP_XOANONCC_DNTT a inner join ERP_XOANONCC b on a.xoanoncc_fk = b.pk_seq  \n"
						+ " 	where b.nhanvien_fk ='" + this.nvId + "' and b.CONGTY_FK = " + this.congtyId
						+ " AND b.TRANGTHAI NOT IN (2) and a.loaict = 1  \n";
				if (this.id.trim().length() > 0)
					query += " and b.pk_seq != '" + this.id + "' \n";

				query += "  group by a.dntt_fk \n" + " )dathanhtoan on chungtu.pk_seq = dathanhtoan.hoadonncc_fk \n"
						+ " where round(chungtu.tienAvat - ISNULL(dathanhtoan.tienthanhtoan, 0), 0) > 0 ";
			}

			System.out.println("1.Query khoi tao hoa don : " + query);

			ResultSet rsHoadon = db.get(query);
			List<IHoadon> hdList = new ArrayList<IHoadon>();
			if (rsHoadon != null) {
				try {
					IHoadon hd = null;
					while (rsHoadon.next()) {
						String id = rsHoadon.getString("pk_seq");
						String kyhieu = rsHoadon.getString("kyhieu");
						String sohoadon = rsHoadon.getString("sohoadon");
						String ngayhd = rsHoadon.getString("ngayhoadon");
						String avat = formatter.format(rsHoadon.getDouble("tienAvat"));
						String sopo = rsHoadon.getString("sopo");
						String loaict = rsHoadon.getString("loaict");

						String dathanhtoan = "";
						if (this.id.length() > 0) {
							if (rsHoadon.getDouble("SOTIENTT") > 0)
								dathanhtoan = formatter.format(rsHoadon.getDouble("SOTIENTT"));
						}

						hd = new Hoadon(id, kyhieu, sohoadon, ngayhd, avat, dathanhtoan, "");
						hd.setHopdong(sopo);
						hd.setLoaict(loaict);
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

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBclose() {

	}

	public String getNgayghiso() {
		return this.ngayghiso;
	}

	public void setNgayghiso(String ngayghiso) {
		this.ngayghiso = ngayghiso;
	}

	public String getNoidungId() {
		return this.ndId;
	}

	public void setNoidungId(String ndId) {
		this.ndId = ndId;
	}

	public ResultSet getNoidungRs() {
		return this.ndRs;
	}

	public void setNoidungRs(ResultSet ndRs) {
		this.ndRs = ndRs;
	}

	public String getCttratruocId() {
		return this.ctttId;
	}

	public void setCttratruocId(String ctttId) {
		this.ctttId = ctttId;
	}

	public ResultSet getCttratruocRs() {
		return this.ctttRs;
	}

	public void setCttratruocRs(ResultSet ctttRs) {
		this.ctttRs = ctttRs;
	}

	public String getCtttIds() {
		return this.ctttIds;
	}

	public void setCtttIds(String ctttIds) {
		this.ctttIds = ctttIds;
	}

	public List<IHoadon> getCtttList() {
		return this.ctttList;
	}

	public void setCtttList(List<IHoadon> hoadonRs) {
		this.ctttList = hoadonRs;
	}

	public String getCongtyId() {

		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {

		this.congtyId = congtyId;
	}

	public String getnppdangnhap() {

		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) {

		this.nppdangnhap = nppdangnhap;
	}

}
