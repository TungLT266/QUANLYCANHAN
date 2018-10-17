package geso.traphaco.erp.beans.erp_capquanly.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.erp_capquanly.IErp_capquanly;

public class Erp_capquanly extends Phan_Trang implements IErp_capquanly {
	/**
	 * 
	 */
	private static final long serialVersionUID = 668328923969461422L;
	dbutils db;
	String ID;
	String userId;
	String userTen;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String TRANGTHAI;
	String Msg;
	ResultSet Rscn;
	String ctyId;
	ResultSet rsCty;

	// MỚI

	ResultSet loaicapRs;
	String loaicapId;
	String macap;
	String tencap;
	String quanlycapId;
	String email;
	ResultSet quanlycapRs;
	ResultSet nhanvienRs;
	ResultSet nhanvienchonRs;

	public Erp_capquanly() {
		db = new dbutils();
		this.userId = "";
		this.userTen = "";
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.TRANGTHAI = "1";
		this.Msg = "";
		this.loaicapId = "10000";
		this.macap = "";
		this.tencap = "";
		this.quanlycapId = "";
		this.ctyId = "";
		this.email = "";
	}

	public Erp_capquanly(String id) {
		db = new dbutils();
		this.userId = "";
		this.userTen = "";
		this.ID = id;
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.TRANGTHAI = "1";
		this.Msg = "";
		this.loaicapId = "10000";
		this.macap = "";
		this.tencap = "";
		this.quanlycapId = "";
		this.ctyId = "";
		this.email = "";
	}

	public String getID() {

		return ID;
	}

	public String getMA() {

		return MA;
	}

	public String getTEN() {

		return TEN;
	}

	public String getNGAYTAO() {

		return NGAYTAO;
	}

	public String getNGAYSUA() {

		return NGAYSUA;
	}

	public String getNGUOITAO() {

		return NGUOITAO;
	}

	public String getNGUOISUA() {

		return NGUOISUA;
	}

	public String getTRANGTHAI() {

		return TRANGTHAI;
	}

	public String getMsg() {

		return Msg;
	}

	public ResultSet getRscn() {

		return Rscn;
	}

	public void setID(String ID) {

		this.ID = ID;
	}

	public void setMA(String MA) {

		this.MA = MA;
	}

	public void setTEN(String TEN) {

		this.TEN = TEN;
	}

	public void setNGAYTAO(String NGAYTAO) {

		this.NGAYTAO = NGAYTAO;
	}

	public void setNGAYSUA(String NGAYSUA) {

		this.NGAYSUA = NGAYSUA;
	}

	public void setNGUOITAO(String NGUOITAO) {

		this.NGUOITAO = NGUOITAO;
	}

	public void setNGUOISUA(String NGUOISUA) {

		this.NGUOISUA = NGUOISUA;
	}

	public void setTRANGTHAI(String TRANGTHAI) {

		this.TRANGTHAI = TRANGTHAI;
	}

	public void setMsg(String Msg) {

		this.Msg = Msg;
	}

	public void init(String sql) {
		String query = " SELECT CN.PK_SEQ AS ID_CN,CN.MA,CN.TEN,isnull(CN.TRANGTHAI,0) AS TT ,CN.NGAYTAO ,CN.NGAYSUA ,NT.TEN AS "
				+ " NGUOITAO,NS.TEN AS NGUOISUA" + " FROM ERP_CHINHANH CN "
				+ " INNER JOIN NHANVIEN NT  ON NT.PK_SEQ = CN.NGUOITAO "
				+ " INNER JOIN NHANVIEN NS ON NS.PK_SEQ = CN.NGUOISUA";
		if (this.MA.trim().length() > 0)
			query += " and CN.ma like N'%" + this.MA + "%'";
		if (this.TEN.trim().length() > 0)
			query += " and CN.ten like N'%" + this.TEN + "%'";
		if (this.TRANGTHAI.length() > 0)
			query += " and CN.TRANGTHAI = '" + TRANGTHAI + "' ";
		if (this.NGAYTAO.length() > 0)
			query += " and cn.ngaytao >= '%" + NGAYTAO + "%'";
		this.Rscn = createSplittingData(50, 10, " ID_CN DESC,TT  ", query);
		;

		System.out.println("query list " + query);

	}

	public void DBClose() {
		try {
			if (nhanvienchonRs != null)
				nhanvienchonRs.close();
			if (nhanvienRs != null)
				nhanvienRs.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.shutDown();
		}
	}

	public boolean ThemMoiMa() {
		try {
			if (this.nhanvienchonRs == null) {
				this.Msg = "Vui lòng chọn nhân viên";
				return false;
			}

			String query = "";

			this.db.getConnection().setAutoCommit(false);

			query = "INSERT ERP_CAPQUANLY ( MACAP, TENCAP, NVQL_FK , LOAICAP_FK , EMAIL ,NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, TRANGTHAI, CONGTY_FK ) "
					+ "VALUES(N'" + this.macap + "',N'" + this.tencap + "', " + this.quanlycapId + ", " + this.loaicapId
					+ ", N'" + this.email + "' , '" + this.getDateTime() + "', '" + this.NGUOITAO + "'," + "'"
					+ this.getDateTime() + "', '" + this.NGUOITAO + "', 0 , " + this.ctyId + ")";

			if (!db.update(query)) {
				db.update("rollback");
				this.Msg = query;
				return false;
			}

			String cqlCurrent = "";
			query = "select IDENT_CURRENT('ERP_CAPQUANLY') as cqlId";

			ResultSet rsTthd = db.get(query);
			if (rsTthd.next()) {
				cqlCurrent = rsTthd.getString("cqlId");
				rsTthd.close();
			}

			if (nhanvienchonRs != null) {
				while (nhanvienchonRs.next()) {
					int lc_fk = Integer.parseInt(this.loaicapId) - 1;

					query = "INSERT INTO ERP_CAPQUANLY_CT (nhanvien_fk,capquanly_fk, loaicap_fk , nvql_fk , congty_fk ) values('"
							+ nhanvienchonRs.getString("pk_seq") + "','" + cqlCurrent + "', " + lc_fk + ", "
							+ this.quanlycapId + ", " + this.ctyId + ")";
					if (!db.update(query)) {
						db.update("rollback");
						this.Msg = query;
						return false;
					}

				}
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);

		} catch (Exception ex) {
			db.update("rollback");
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean UpdateMa() {
		try {
			String query = " UPDATE ERP_CAPQUANLY SET MACAP = N'" + this.macap + "', TENCAP = N'" + this.tencap
					+ "', NVQL_FK =" + this.quanlycapId + " , LOAICAP_FK = " + this.loaicapId + ", EMAIL = N'"
					+ this.email + "' , NGAYSUA = '" + this.getDateTime() + "' , CONGTY_FK = " + this.ctyId
					+ " WHERE PK_SEQ = " + this.ID;
			System.out.println("Query INSERT: " + query);

			if (!db.update(query)) {
				db.update("rollback");
				this.Msg = query;
				return false;
			}

			query = "DELETE ERP_CAPQUANLY_CT WHERE CAPQUANLY_FK = " + this.ID;
			System.out.println(query);
			if (!db.update(query)) {
				db.update("rollback");
				this.Msg = query;
				return false;
			}

			if (nhanvienchonRs != null) {
				while (nhanvienchonRs.next()) {
					int lc_fk = Integer.parseInt(this.loaicapId) - 1;

					query = "INSERT INTO ERP_CAPQUANLY_CT (nhanvien_fk,capquanly_fk, LOAICAP_FK, NVQL_FK, CONGTY_FK )values('"
							+ nhanvienchonRs.getString("pk_seq") + "','" + this.ID + "', " + lc_fk + ", "
							+ this.loaicapId + ", " + this.ctyId + ")";

					System.out.println(query);
					if (!db.update(query)) {
						db.update("rollback");
						this.Msg = query;
						return false;
					}

				}
			}

		} catch (Exception ex) {
			db.update("rollback");
			ex.printStackTrace();
			return false;
		}

		return true;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void setUserTen(String userten) {
		this.userTen = userten;

	}

	public String getUserTen() {

		return this.userTen;
	}

	public void setUserid(String userid) {

		this.userId = userid;
	}

	public String getUserid() {

		return userId;
	}

	public boolean CheckUnique() {
		String query = "";
		if (this.ID.length() > 0)
			query = "Select count(*) as count From ERP_CAPQUANLY Where MACAP = N'" + this.macap + "' AND PK_SEQ !='"
					+ this.ID + "'";
		else
			query = "Select count(*) as count From ERP_CAPQUANLY Where MACAP = N'" + this.macap + "' ";
		System.out.println("____Kiem tra rang buoc_____ " + query);

		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try {
				while (rs.next()) {
					count = rs.getInt("count");
				}
				rs.close();

				if (count > 0) {
					return false;
				}
			} catch (SQLException e) {
				return false;
			}
		return true;
	}

	public boolean CheckReferences(String column, String table) {
		String query = "SELECT count(" + column + ") AS NUM  FROM " + table + " WHERE " + column + " =" + this.ID + "";
		System.out.println("CheckReferences " + query);
		ResultSet rs = db.get(query);
		System.out.println("____Kiem tra rang buoc_____ " + query);
		try {// kiem tra ben san pham
			while (rs.next()) {
				if (rs.getString("num").equals("0"))
					return false;
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return true;
	}

	public boolean Delete() {
		if (CheckReferences("ChiNhanh_FK", "NhaCungCap")) {
			this.Msg = "Chi nhánh này đã được sử dụng,không thể xóa ";
			return false;
		}
		if (CheckReferences("ChiNhanh_FK", "Erp_NganHang_CongTy")) {
			this.Msg = "Chi nhánh này đã được sử dụng,không thể xóa ";
			return false;
		}
		String query = "Delete Erp_ChiNhanh Where PK_SEQ =" + this.ID + "";
		if (!this.db.update(query)) {
			this.Msg = "Không thể xoá chi nhánh này ";
			return false;
		}
		return true;
	}

	public String getLoaicapId() {

		return this.loaicapId;
	}

	public void setLoaicapId(String loaicapId) {

		this.loaicapId = loaicapId;
	}

	public ResultSet getLoaicapRs() {

		return loaicapRs;
	}

	public void setLoaicapRs(ResultSet loaicapRs) {

		this.loaicapRs = loaicapRs;
	}

	public void init() {

		String query =  " SELECT MACAP, TENCAP, NVQL_FK, EMAIL, Nguoitao, Nguoisua, Ngaytao, Ngaysua, LoaiCap_FK "+
						" FROM ERP_CAPQUANLY WHERE PK_SEQ = " + this.ID;

		System.out.println(query);
		ResultSet rs = db.get(query);

		try {

			if (rs != null) {
				while (rs.next()) {
					this.macap = rs.getString("MACAP");
					this.tencap = rs.getString("TENCAP");
					this.loaicapId = rs.getString("LoaiCap_FK");
					this.quanlycapId = rs.getString("NVQL_FK");
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		query = "SELECT PK_SEQ, TEN FROM ERP_LOAICAPQUANLY WHERE TRANGTHAI = 1";

		this.loaicapRs = db.get(query);

		query = " SELECT distinct C.PK_SEQ,( C.DANGNHAP + ' - ' + C.TEN ) TEN  \n"+
				" FROM NHANVIEN C WHERE C.TRANGTHAI = 1 AND C.PK_SEQ = " + this.quanlycapId;
		
		System.out.println(query);
		this.quanlycapRs = db.get(query);

		this.rsCty = this.db.get("SELECT PK_SEQ , MA + ' - '+ TEN AS TEN FROM ERP_CONGTY WHERE TRANGTHAI = '1' ");

	}

	public void createRs() {

		String query = "SELECT PK_SEQ, TEN FROM ERP_LOAICAPQUANLY WHERE TRANGTHAI = 1";

		this.loaicapRs = db.get(query);

		if (this.ctyId.trim().length() > 0) {
			query = " SELECT distinct C.PK_SEQ,( C.DANGNHAP + ' - ' + C.TEN ) TEN "+
					" FROM NHANVIEN C \n" + 
					" WHERE C.TRANGTHAI = 1 \n" + 
					" AND C.PK_SEQ NOT IN (SELECT NVQL_FK FROM ERP_CAPQUANLY \n"+
					" WHERE TRANGTHAI != 2 AND PK_SEQ != "+ (this.ID.trim().length() <= 0 ? "0" : this.ID) + 
					
					" UNION ALL \n"+
					
					" SELECT B.NHANVIEN_FK FROM ERP_CAPQUANLY A INNER JOIN ERP_CAPQUANLY_CT B ON A.PK_SEQ = B.CAPQUANLY_FK \n"+
					" WHERE A.TRANGTHAI != 2 AND PK_SEQ != "+ (this.ID.trim().length() <= 0 ? "0" : this.ID) + " \n"+
					" AND B.NHANVIEN_FK NOT IN (SELECT NVQL_FK FROM ERP_CAPQUANLY WHERE TRANGTHAI != 2 ) "+
					" )";

			System.out.println("Quan ly cap:" + query);

			this.quanlycapRs = db.get(query);
		}

		this.rsCty = this.db.get("SELECT PK_SEQ , MA + ' - '+ TEN AS TEN FROM ERP_CONGTY WHERE TRANGTHAI = '1' ");
	}

	public String getMacap() {

		return this.macap;
	}

	public void setMacap(String macap) {

		this.macap = macap;
	}

	public String getTencap() {

		return this.tencap;
	}

	public void setTencap(String tencap) {

		this.tencap = tencap;
	}

	public String getQuanlycapId() {

		return this.quanlycapId;
	}

	public void setQuanlycapId(String quanlycapId) {

		this.quanlycapId = quanlycapId;
	}

	public ResultSet getQuanlycapRs() {

		return this.quanlycapRs;
	}

	public void setQuanlycapRs(ResultSet quanlycapRs) {

		this.quanlycapRs = quanlycapRs;
	}

	public String getEmail() {

		return this.email;
	}

	public void setEmail(String email) {

		this.email = email;
	}

	public ResultSet getNhanvienRs() {

		return this.nhanvienRs;
	}

	public void setNhanvienRs(ResultSet nhanvienRs) {

		this.nhanvienRs = nhanvienRs;
	}

	public ResultSet getNhanvienChonRs() {

		return this.nhanvienchonRs;
	}

	public void setNhanvienChonRs(ResultSet nhanvienchonRs) {

		this.nhanvienchonRs = nhanvienchonRs;
	}

	public void CreateNhanVien(String[] chuoi) {

		String st = "(";
		if (chuoi != null) {
			for (int i = 0; i < chuoi.length; i++)
				st = st + chuoi[i] + ",";
			st = st.substring(0, st.length() - 1);

			st = st + ")";

		}

		String sql = "";
		String qlc = "";
		String loaicap = "";

		if (this.quanlycapId.trim().length() > 0)
			qlc = " AND C.PK_SEQ != " + this.quanlycapId;

		if (loaicapId.equals("10000"))
			loaicap = "10000"; // QUẢN LÝ TRỰC TIẾP - QUẢN LÝ NHÂN VIÊN K THUỘC  SỞ HỮU CỦA CẤP NÀO
		else if (loaicapId.equals("10001"))
			loaicap = "10000"; // QUẢN LÝ KÊNH - QUẢN LÝ USER LÀ QUẢN LÝ TRỰC TIẾP
		else if (loaicapId.equals("10002"))
			loaicap = "10001"; // QUẢN LÝ DUYỆT CHI - QUẢN LÝ CẤP USER LÀ QUẢN LÝ KÊNH
		else if (loaicapId.equals("10003"))
			loaicap = "10002"; // QUẢN LÝ CS - QUẢN LÝ CẤP USER LÀ QUẢN LÝ DUYỆT CHI
		else if (loaicapId.equals("10004"))
			loaicap = "10003"; // QUẢN LÝ GẮN MÃ CHI PHÍ - QUẢN LÝ CẤP USER LÀ QUẢN LÝ CS

		// 1. LOẠI QUẢN LÝ TRỰC TIẾP
		if (this.loaicapId.equals("10000") && this.ctyId != "") {
			sql =   " SELECT distinct C.PK_SEQ, ( C.DANGNHAP + ' - ' + C.TEN ) TEN \n"+
					" FROM NHANVIEN C WHERE C.TRANGTHAI = 1 AND C.PK_SEQ NOT IN " +
					" (SELECT A.NHANVIEN_FK FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ \n"+
					"  WHERE B.TRANGTHAI != 2 \n"+
					"  UNION ALL \n"+
					"  SELECT NVQL_FK FROM ERP_CAPQUANLY WHERE TRANGTHAI != 2 ) " + qlc;

			if (this.ID.trim().length() > 0)
				sql += " AND C.PK_SEQ NOT IN (SELECT NHANVIEN_FK FROM ERP_CAPQUANLY_CT WHERE CAPQUANLY_FK = " + this.ID +" )";

		} else // CÁC LOẠI QUẢN LÝ CÒN LẠI
		{
			sql = " SELECT distinct C.PK_SEQ, ( C.DANGNHAP + ' - ' + C.TEN ) TEN \n"+
				  " FROM NHANVIEN C WHERE  C.TRANGTHAI = 1 AND C.PK_SEQ IN (SELECT NVQL_FK FROM ERP_CAPQUANLY WHERE TRANGTHAI = 1 AND LOAICAP_FK = "+ loaicap + " ) \n" + qlc +
				  " AND C.PK_SEQ NOT IN (SELECT A.NHANVIEN_FK FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ \n"+
				  "				       WHERE B.TRANGTHAI != 2 ) \n";
			if (this.ID.trim().length() > 0)
				sql += " AND C.PK_SEQ NOT IN (SELECT NHANVIEN_FK FROM ERP_CAPQUANLY_CT WHERE CAPQUANLY_FK = " + this.ID + " )";
		}

		System.out.println("NHAN VIEN LOAD_ " + sql);

		// 1. KHÔNG LẤY NHÂN VIÊN ĐÃ ĐƯỢC CHỌN
		if (chuoi != null) {
			// 1. LOẠI QUẢN LÝ TRỰC TIẾP
			if (this.loaicapId.equals("10000")) {
				sql = " SELECT distinct C.PK_SEQ, ( C.DANGNHAP + ' - ' + C.TEN ) TEN \n"+
					  " FROM NHANVIEN C WHERE C.TRANGTHAI = 1 AND C.PK_SEQ NOT IN (	SELECT A.NHANVIEN_FK FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ \n"+
					  "	WHERE B.TRANGTHAI != 2 "+
					  " UNION ALL \n"+
					  "	SELECT NVQL_FK FROM ERP_CAPQUANLY WHERE TRANGTHAI != 2 ) \n" + 
					  "	AND C.PK_SEQ NOT IN " + st + qlc;

				if (this.ID.trim().length() > 0)
					sql += " AND C.PK_SEQ NOT IN (SELECT NHANVIEN_FK FROM ERP_CAPQUANLY_CT WHERE CAPQUANLY_FK = "+ this.ID + ")";
			} else {
				sql = " SELECT distinct C.PK_SEQ, ( C.DANGNHAP + ' - ' + C.TEN ) TEN \n"+
					  " FROM NHANVIEN C WHERE C.TRANGTHAI = 1 AND C.PK_SEQ IN (SELECT NVQL_FK FROM ERP_CAPQUANLY WHERE TRANGTHAI = 1 AND LOAICAP_FK = " + loaicap + " ) \n" + 
					  " AND C.PK_SEQ NOT IN " + st + qlc + 
					  " AND C.PK_SEQ NOT IN ( SELECT A.NHANVIEN_FK FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ \n"
						+ "				       WHERE B.TRANGTHAI != 2 ) \n";
				if (this.ID.trim().length() > 0)
					sql += " AND C.PK_SEQ NOT IN ( SELECT NHANVIEN_FK FROM ERP_CAPQUANLY_CT WHERE CAPQUANLY_FK = "+ this.ID + " )";

			}
		}

		System.out.println("NHAN VIEN LOAD_2 " + sql);

		nhanvienRs = db.get(sql);

		// 1. LOẠI QUẢN LÝ TRỰC TIẾP - LẤY NHÂN VIÊN ĐC TICK CHỌN
		if (this.ID.trim().length() > 0) {
			sql = " SELECT distinct C.PK_SEQ, ( C.DANGNHAP + ' - ' + C.TEN ) TEN  \n"+
				  " FROM PHAMVIHOATDONG A INNER JOIN NHAPHANPHOI B ON A.Npp_fk = B.PK_SEQ \n"+
				  " INNER JOIN NHANVIEN C ON A.Nhanvien_fk = C.PK_SEQ \n" + 
				  " WHERE C.TRANGTHAI = 1 AND C.PK_SEQ IN (SELECT NHANVIEN_FK FROM ERP_CAPQUANLY_CT WHERE CAPQUANLY_FK ='"+ (this.ID.trim().length() <= 0 ? "0" : this.ID) + "' ) \n "
					+ qlc;
			System.out.println("nv dc chon:" + sql);
			nhanvienchonRs = db.get(sql);
			System.out.println(sql);
		}

		if (chuoi != null) {
			if (qlc.trim().length() > 0)
				qlc = " AND C.PK_SEQ != " + this.quanlycapId;

			sql = " SELECT distinct C.PK_SEQ, ( C.DANGNHAP + ' - ' + C.TEN ) TEN \n"+
				  " FROM NHANVIEN C \n"+
				  " WHERE C.TRANGTHAI = 1 AND C.PK_SEQ IN " + st + " " + qlc;

			System.out.println("NV_CHON_ABD:" + sql);
			nhanvienchonRs = db.get(sql);
			System.out.println(sql);
		}

		System.out.println("NHAN VIEN LOAD_ " + sql);
	}

	public void setRsCty(ResultSet rsCty) {
		this.rsCty = rsCty;
	}

	public ResultSet getRsCty() {
		return this.rsCty;
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}
}
