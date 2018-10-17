package geso.traphaco.erp.beans.khoanmucchietkhau.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.khoanmucchietkhau.IKhoanmucCK;
import geso.traphaco.erp.beans.khoanmucchietkhau.IKhoanmucchietkhau;;

public class Khoanmucchietkhau implements IKhoanmucchietkhau {
	private String MSG, PK_SEQ, CONGTY_FK, TEN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, MA, TRANGTHAI, LOAI;

	ResultSet taikhoanRs;
	ResultSet kenhBanHangRs;
	String taikhoanId;

	List<IKhoanmucCK> chiTietList;

	private dbutils db;

	public Khoanmucchietkhau() {
		this.LOAI = "0";
		this.PK_SEQ = "";
		this.MSG = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYSUA = "";
		this.NGAYTAO = "";
		this.NGUOISUA = "";
		this.NGUOITAO = "";
		this.TRANGTHAI = "1";

		this.chiTietList = new ArrayList<IKhoanmucCK>();

		this.taikhoanId = "";
		this.db = new dbutils();
	}

	public Khoanmucchietkhau(String pk_seq) {
		this.LOAI = "0";
		this.PK_SEQ = pk_seq;
		this.MA = "";
		this.TEN = "";
		this.NGAYSUA = "";
		this.NGAYTAO = "";
		this.NGUOISUA = "";
		this.NGUOITAO = "";
		this.TRANGTHAI = "1";
		this.MSG = "";
		this.taikhoanId = "";

		this.chiTietList = new ArrayList<IKhoanmucCK>();

		this.db = new dbutils();
	}

	public void setMsg(String msg) {
		this.MSG = msg;
	}

	public String getMsg() {
		return this.MSG;
	}

	public void setLoai(String loai) {
		this.LOAI = loai;
	}

	public String getLoai() {
		return this.LOAI;
	}

	public void setPK_SEQ(String pk_seq) {
		this.PK_SEQ = pk_seq;
	}

	public String getPK_SEQ() {
		return this.PK_SEQ;
	}

	public void setCongty_fk(String congty_fk) {
		this.CONGTY_FK = congty_fk;
	}

	public String getCongty_fk() {
		return this.CONGTY_FK;
	}

	public void setTen(String ten) {
		this.TEN = ten;
	}

	public String getTen() {
		return this.TEN;
	}

	public void setNguoitao(String nguoitao) {
		this.NGUOITAO = nguoitao;
	}

	public String getNguoitao() {
		return this.NGUOITAO;
	}

	public void setNguoisua(String nguoisua) {
		this.NGUOISUA = nguoisua;
	}

	public String getNguoisua() {
		return this.NGUOISUA;
	}

	public void setNgaytao(String ngaytao) {
		this.NGAYTAO = ngaytao;
	}

	public String getNgaytao() {
		return this.NGAYTAO;
	}

	public void setNgaysua(String ngaysua) {
		this.NGAYSUA = ngaysua;
	}

	public String getNgaysua() {
		return this.NGAYSUA;
	}

	public void setMa(String ma) {
		this.MA = ma;
	}

	public String getMa() {
		return this.MA;
	}

	public void setTrangthai(String trangthai) {
		this.TRANGTHAI = trangthai;
	}

	public String getTrangthai() {
		return this.TRANGTHAI;
	}

	public boolean init() {
		String query = "select MAKMCK as MA ,TENKMCK as TEN" + " from ERP_KHOANMUCCHIETKHAU " + " WHERE PK_SEQ='"
				+ this.PK_SEQ + "'";
		ResultSet rs = this.db.get(query);

		try {
			if (rs != null) {
				while (rs.next()) {
					this.MA = rs.getString("MA");
					this.TEN = rs.getString("TEN");

				}
				rs.close();
			}

			query = " SELECT KBH_FK,TAIKHOAN_FK FROM ERP_KHOANMUCCHIETKHAU_KBH WHERE KMCK_FK =" + this.PK_SEQ;

			System.out.println("Câu lấy Chi Tiết " + query);
			ResultSet rsChiTiet = db.get(query);
			List<IKhoanmucCK> chiTietList = new ArrayList<IKhoanmucCK>();

			if (rsChiTiet != null) {
				while (rsChiTiet.next()) {
					IKhoanmucCK ct = new KhoanmucCK();
					ct.setKenhbhId(rsChiTiet.getString("KBH_FK"));
					ct.setTaikhoanId(rsChiTiet.getString("TAIKHOAN_FK"));

					chiTietList.add(ct);
				}
				rsChiTiet.close();
			}

			this.chiTietList = chiTietList;

		} catch (SQLException e) {
			System.out.println("Không thể lấy dữ liệu " + query);
			return false;
		}

		this.CreateRs();

		return true;
	}

	public boolean Update() {
		if (this.MA.trim().length() <= 0) {
			this.MSG = "Vui lòng nhập mã khoản mục chiết khấu";
			return false;
		}

		if (this.TEN.trim().length() <= 0) {
			this.MSG = "Vui lòng nhập tên khoản mục chiết khấu";
			return false;
		}
		System.out.println("Đã vào update");
		try {
			String query = "";
			this.db.getConnection().setAutoCommit(false);

			query = " update ERP_KHOANMUCCHIETKHAU set MAKMCK = N'" + this.MA + "', TENKMCK=N'" + this.TEN
					+ "', NGAYSUA = '" + this.getDateTime() + "',NGUOISUA = '" + this.NGUOISUA + "' "
					+ " WHERE PK_SEQ = '" + this.PK_SEQ + "'";

			if (!db.update(query)) {
				this.MSG = "1.Không thể cập nhật ERP_KHOANMUCCHIETKHAU " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete from ERP_KHOANMUCCHIETKHAU_KBH where KMCK_FK = '" + this.PK_SEQ + "'";

			if (!db.update(query)) {
				this.MSG = "2.Không thể xóa ERP_KHOANMUCCHIETKHAU_KBH " + query;
				db.getConnection().rollback();
				return false;
			}

			for (int i = 0; i < chiTietList.size(); i++) {
				IKhoanmucCK ct = chiTietList.get(i);
				if (ct.getKenhbhId() != null && ct.getKenhbhId() != "") {
					query = " insert into ERP_KHOANMUCCHIETKHAU_KBH values( '" + this.PK_SEQ + "', '" + ct.getKenhbhId()
							+ "', '" + ct.getTaikhoanId() + "' ) ";
					System.out.println("query" + query);
					if (!db.update(query)) {
						this.MSG = "3.Không thể cập nhật ERP_KHOANMUCCHIETKHAU_KBH " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.db.update("rollback");
			this.MSG = "Exception: " + e.getMessage();
			System.out.println(this.MSG);
			return false;
		}

		return true;

	}

	public boolean New() {
		if (this.MA.trim().length() <= 0) {
			this.MSG = "Vui lòng nhập mã khoản mục chiết khấu";
			return false;
		}

		if (this.TEN.trim().length() <= 0) {
			this.MSG = "Vui lòng nhập tên khoản mục chiết khấu";
			return false;
		}

		try {
			String query = "";
			this.db.getConnection().setAutoCommit(false);
			query = "insert into erp_khoanmucchietkhau(makmck,tenkmck,ngaytao,nguoitao,trangthai) values ( N'" + this.MA
					+ "' ,N'" + this.TEN + "','" + this.getDateTime() + "'," + this.NGUOITAO + "," + this.TRANGTHAI
					+ ")";
			if (!db.update(query)) {
				this.MSG = "1.Không thể tạo mới ERP_KHOANMUCCHIETKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			String kmckCurrent = "";
			query = "select SCOPE_IDENTITY() as kmckID";
			ResultSet rsKMCK = db.get(query);
			if (rsKMCK.next()) {
				kmckCurrent = rsKMCK.getString("kmckID");
				this.PK_SEQ = kmckCurrent;
				rsKMCK.close();
			}

			for (int i = 0; i < chiTietList.size(); i++) {
				IKhoanmucCK ct = chiTietList.get(i);
				if (ct.getKenhbhId() != null && ct.getKenhbhId() != "") {
					query = " insert into ERP_KHOANMUCCHIETKHAU_KBH values( '" + this.PK_SEQ + "', '" + ct.getKenhbhId()
							+ "', '" + ct.getTaikhoanId() + "' ) ";

					if (!db.update(query)) {
						this.MSG = "3.Không thể cập nhật ERP_KHOANMUCCHIETKHAU_KBH " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.db.update("rollback");
			this.MSG = "Exception: " + e.getMessage();
			System.out.println(this.MSG);
			return false;
		}

		return true;

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBClose() {
		try {
			if (this.taikhoanRs != null)
				this.taikhoanRs.close();
			if (this.db != null)
				this.db.shutDown();
		} catch (java.sql.SQLException e) {
		}
	}

	public void setTaikhoanRs(ResultSet TaikhoanRs) {
		this.taikhoanRs = TaikhoanRs;
	}

	public ResultSet getTaikhoanRs() {
		return this.taikhoanRs;
	}

	public void setTaikhoanId(String TaikhoanId) {
		this.taikhoanId = TaikhoanId;
	}

	public String getTaikhoanId() {
		return this.taikhoanId;
	}

	public void CreateRs() {
		String sqlTaiKhoan = "SELECT PK_SEQ, SOHIEUTAIKHOAN,TENTAIKHOAN FROM ERP_TAIKHOANKT WHERE TRANGTHAI = 1 AND CONGTY_FK="
				+ this.CONGTY_FK + " ORDER BY SOHIEUTAIKHOAN";
		this.taikhoanRs = db.getScrol(sqlTaiKhoan);
		String sqlKenhBanHang = "select pk_seq,ten from kenhbanhang where trangthai = 1";
		this.kenhBanHangRs = db.getScrol(sqlKenhBanHang);

	}

	public List<IKhoanmucCK> getChiTietList() {
		return this.chiTietList;
	}

	public void setChiTietList(List<IKhoanmucCK> chiTietList) {
		this.chiTietList = chiTietList;

	}

	public void setKenhBanHangRs(ResultSet kenhBanHangRs) {
		this.kenhBanHangRs = kenhBanHangRs;
	}

	public ResultSet getKenhBanHangRs() {
		return this.kenhBanHangRs;
	}
}