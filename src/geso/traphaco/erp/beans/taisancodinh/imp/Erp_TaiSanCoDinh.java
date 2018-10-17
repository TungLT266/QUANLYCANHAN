package geso.traphaco.erp.beans.taisancodinh.imp;

import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taisancodinh.IErp_TaiSanCoDinh;
import geso.traphaco.erp.beans.taisancodinh.IKhauHaoDuKien;
import geso.traphaco.erp.beans.taisancodinh.IPhanBo;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Erp_TaiSanCoDinh extends Phan_Trang implements IErp_TaiSanCoDinh {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	dbutils db;
	String Id, Diengiai, Ma, LtsId, NtsId, ctyId, SothangKH, Dvt,
			ThangbatdauKH, PpKH, Msg, Nguoitao, Nguoisua, Ngaytao, Ngaysua,
			Trangthai, userTen, userId, SoLuong, DonGia, ThanhTien;
	String ngayghitang;
	String[] cdtsIds;
	String[] ttcpIds;

	String ttcp;
	String loaitaisan;
	ResultSet RsTtcp;
	ResultSet dieuChinhNguyenGiaRs;
	ResultSet dieuChuyenTaiSanRs;
	ResultSet NtsList;
	ResultSet CdList;
	ResultSet dvthList;
	ResultSet kmcpList;
	String dvthId;

	List<IPhanBo> phanBoList = new ArrayList<IPhanBo>();

	ResultSet TsList;
	ResultSet DvtList, PPKHrS;
	ResultSet NsList;
	ResultSet LtsList;
	ResultSet ttcpRs, cdtsRs;
	List<IKhauHaoDuKien> khauhaodukienList = new ArrayList<IKhauHaoDuKien>();
	ResultSet lnsRs;
	String lnsId;
	String ttcpId;
	boolean enable;
	String phanloai;
	String maTSDD = "";
	private int num;
	private int[] listPages;
	private int currentPages;

	public Erp_TaiSanCoDinh() {
		this.Id = "";
		this.Ma = "";
		this.Diengiai = "";
		this.LtsId = "";
		this.NtsId = "";
		this.ctyId = "";
		this.Dvt = "";
		this.SothangKH = "";
		this.ThangbatdauKH = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "";
		this.userId = "";
		this.userTen = "";
		this.SoLuong = "";
		this.DonGia = "0";
		this.ThanhTien = "0";
		this.ttcp = "Hệ thống tự động tạo";
		this.loaitaisan = "Được kế thừa từ Nhóm Tài Sản";
		this.lnsId = "";
		this.dvthId = "";
		this.ngayghitang = "";
		currentPages = 1;
		num = 1;
		this.phanloai = "1";
		this.db = new dbutils();

	}

	public Erp_TaiSanCoDinh(String id) {
		this.Id = id;
		this.Ma = "";
		this.Diengiai = "";
		this.LtsId = "";
		this.NtsId = "";
		this.ctyId = "";
		this.Dvt = "";
		this.SothangKH = "";
		this.ThangbatdauKH = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		this.SoLuong = "";
		this.DonGia = "0";
		this.ThanhTien = "0";
		this.ngayghitang = "";
		this.ttcp = "Hệ thống tự động tạo";
		this.loaitaisan = "Được kế thừa từ Nhóm Tài Sản";
		this.dvthId = "";
		currentPages = 1;
		num = 1;

		this.phanloai = "1";
		this.lnsId = "";
		this.db = new dbutils();
		this.Msg = "";

		/*
		 * String query =
		 * "SELECT TSCD.MA, TSCD.DIENGIAI, ISNULL(TSCD.DONVI, '') AS DONVI, " +
		 * "TSCD.SOTHANGKH, TSCD.THANGBATDAUKH, TSCD.TRANGTHAI, TSCD.NGUOITAO, TSCD.NGUOISUA, "
		 * +
		 * "TSCD.NGAYTAO, TSCD.NGAYSUA, TSCD.THANHTIEN, TSCD.SOLUONG, TSCD.DONGIA, TSCD.NHOMTAISAN_FK, "
		 * + "TTCP.MA AS TTCP, LTS.DIENGIAI AS LTS FROM ERP_TAISANCODINH TSCD "
		 * + "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK "
		 * + "INNER JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK "
		 * + "WHERE TSCD.PK_SEQ = '" + id + "' ";
		 * System.out.println("1.Khoi tao tai san: " + query);
		 * 
		 * ResultSet obj = db.get(query); try { if (obj.next()) { this.Id = id;
		 * this.Ma = obj.getString("MA"); this.Diengiai =
		 * obj.getString("DIENGIAI"); this.loaitaisan = obj.getString("LTS");
		 * this.Dvt = obj.getString("DONVI"); this.SothangKH =
		 * obj.getString("sothangKH"); this.ThangbatdauKH =
		 * obj.getString("thangbatdauKH"); this.Trangthai =
		 * obj.getString("trangthai"); this.Nguoitao =
		 * obj.getString("nguoitao"); this.Nguoisua = obj.getString("nguoisua");
		 * this.Ngaytao = obj.getString("ngaytao"); this.Ngaysua =
		 * obj.getString("ngaysua"); this.ThanhTien =
		 * obj.getString("ThanhTien"); this.SoLuong = obj.getString("SoLuong");
		 * this.DonGia = obj.getString("DonGia"); this.NtsId =
		 * obj.getString("NHOMTAISAN_FK"); this.ttcp = obj.getString("TTCP"); }
		 * if (obj != null) { obj.close(); } } catch (Exception er) { this.Msg =
		 * "Loi" + er.toString(); }
		 */

	}

	public String getId() {
		return Id;
	}

	public String getMa() {
		return Ma;
	}

	public String getDiengiai() {
		return this.Diengiai;
	}

	public String getTtcp() {
		return this.ttcp;
	}

	public String getMsg() {
		return Msg;
	}

	public void setId(String id) {
		this.Id = id;
	}

	public void setMa(String ma) {
		this.Ma = ma;
	}

	public void setDiengiai(String diengiai) {
		this.Diengiai = diengiai;
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
	}

	public ResultSet getRsts() {
		return TsList;
	}

	public void setRsts(ResultSet Rsts) {
		this.TsList = Rsts;
	}

	public ResultSet getKmcpList() {
		return kmcpList;
	}

	public void setKmcpList(ResultSet kmcpList) {
		this.kmcpList = kmcpList;
	}

	public ResultSet getRsLoaitaisan() {
		return this.LtsList;
	}

	public void setRsLoaitaisan(ResultSet LtsList) {
		this.LtsList = LtsList;
	}

	public String getLoaitaisan() {
		return this.loaitaisan;
	}

	public void setUserid(String userId) {
		this.userId = userId;
	}

	public String getUserid() {
		return userId;
	}

	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}

	public String getUserTen() {
		return userTen;
	}

	public void setLtsId(String ltsId) {
		this.LtsId = ltsId;
	}

	public String getLtsId() {
		return this.LtsId;
	}

	public ResultSet getRsCdts() {

		return this.cdtsRs;
	}

	public void setRsCdts(ResultSet cdtsRs) {

		this.cdtsRs = cdtsRs;
	}

	public void setCdtsIds(String[] cdtsIds) {

		this.cdtsIds = cdtsIds;
	}

	public String[] getCdtsIds() {

		return cdtsIds;
	}

	public ResultSet getRsTtcp() {

		return this.ttcpRs;
	}

	public void setRsTtcp(ResultSet ttcpRs) {

		this.ttcpRs = ttcpRs;
	}

	public void setTtcpIds(String[] ttcpIds) {

		this.ttcpIds = ttcpIds;
	}

	public String[] getTtcpIds() {

		return ttcpIds;
	}

	public String getNgaytao() {
		return Ngaytao;
	}

	public String getNguoitao() {
		return Nguoitao;
	}

	public String getNgaysua() {
		return Ngaysua;
	}

	public String getNguoisua() {
		return Nguoisua;
	}

	public void setNgaytao(String ngaytao) {
		this.Ngaytao = ngaytao;
	}

	public void setNguoitao(String nguoitao) {
		this.Nguoitao = nguoitao;
	}

	public void setNgaysua(String ngaysua) {
		this.Ngaysua = ngaysua;
	}

	public void setNguoisua(String nguoisua) {
		this.Nguoisua = nguoisua;
	}

	public String getTrangthai() {
		return Trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.Trangthai = trangthai;
	}

	public String getNtsId() {
		return NtsId;
	}

	public String getDvt() {
		return Dvt;
	}

	public String getSothangKH() {
		return SothangKH;
	}

	public String getThangbatdauKH() {
		return ThangbatdauKH;
	}

	public String getPpKH() {
		return PpKH;
	}

	public void setNtsId(String ntsId) {
		this.NtsId = ntsId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public void setDvt(String dvt) {
		this.Dvt = dvt;
	}

	public void setSothangKH(String sothangKh) {
		this.SothangKH = sothangKh;
	}

	public void setThangbatdauKH(String thangbatdauKh) {
		this.ThangbatdauKH = thangbatdauKh;
	}

	public void setPpKH(String ppKh) {
		this.PpKH = ppKh;
	}

	public ResultSet getRsNts() {
		return NtsList;
	}

	public void setRsNts(ResultSet rsNts) {
		this.NtsList = rsNts;
	}

	public void setPhanloai(String phanloai) {
		this.phanloai = phanloai;
	}

	public String getPhanloai() {
		return this.phanloai;
	}

	public ResultSet getRscd() {
		return CdList;
	}

	public void setRscd(ResultSet rsCd) {
		this.CdList = rsCd;
	}

	public ResultSet getRsdvt() {
		return DvtList;
	}

	public void setRsdvt(ResultSet rsDvt) {
		this.DvtList = rsDvt;
	}

	public ResultSet getRsTrungtamchiphi() {
		return this.RsTtcp;
	}

	public void setRsTrungtamchiphi(ResultSet rsTtcp) {
		this.RsTtcp = rsTtcp;
	}

	public String getSoLuong() {
		return this.SoLuong;
	}

	public void setSoLuong(String soluong) {
		this.SoLuong = soluong;
	}

	public String getDonGia() {
		return this.DonGia;
	}

	public void setDonGia(String dongia) {
		this.DonGia = dongia;
	}

	public void setThanhTien(String thanhtien) {
		this.ThanhTien = thanhtien;
	}

	public String getThanhTien() {
		return this.ThanhTien;
	}

	public void setPPKHrS(ResultSet ppkh) {
		this.PPKHrS = ppkh;
	}

	public ResultSet getPPKHrS() {
		return this.PPKHrS;
	}

	public ResultSet getNgansachRs() {

		return this.NsList;
	}

	public void setNgansachRs(ResultSet nsRs) {

		this.NsList = nsRs;
	}

	public boolean getEnable() {
		String query = " SELECT ISNULL(SUM(KHAUHAOTHUCTE), 0) AS KH "
				+ " FROM ERP_TAISANCODINH_CHITIET " + " WHERE TAISAN_FK = '"
				+ this.Id + "'";
		System.out.println(query);

		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			float kh = rs.getFloat("KH");
			rs.close();

			if (kh == 0) {
				return true;
			} else {
				return false;
			}
		} catch (java.sql.SQLException e) {
			return false;
		}
	}

	public boolean isKhauhao(String tsId) {
		boolean result = false;
		String query = " SELECT isnull(khauhao.num,0) + ISNULL(SOTHANGDAKHAUHAO,0) AS NUM \n"
				+ "	FROM ERP_TAISANCODINH ts  \n"
				+ "	left join  \n"
				+ "	( \n"
				+ "	select taisan_fk, COUNT(*) as num from ERP_KHAUHAOTAISAN \n"
				+ "	where TAISAN_FK="
				+ tsId
				+ " \n"
				+ "	group by taisan_fk \n"
				+ "	) khauhao on ts.pk_seq=khauhao.TAISAN_FK \n"
				+ "	WHERE ts.pk_seq = " + tsId + " \n";
		System.out.println(query);

		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			String tmp = rs.getString("NUM");
			rs.close();

			if (tmp.equals("0")) {
				return true;
			} else {
				return false;
			}

		} catch (java.sql.SQLException e) {
		}

		return result;
	}

	public void init_convert() {
		String query = "";
		if (this.Id.length() > 0) {
			System.out.println("_____Khoi tao: " + query);
			query = "SELECT  TSCD.MA, TSCD.DIENGIAI, ISNULL(TSCD.DONVI, '') AS DONVI, "
					+ "'0' AS SOTHANGKH, '' AS THANGBATDAUKH, TSCD.TRANGTHAI, TSCD.NGUOITAO, TSCD.NGUOISUA, "
					+ "TSCD.NGAYTAO, TSCD.NGAYSUA, TSCD.THANHTIEN, TSCD.SOLUONG, TSCD.DONGIA, '0' AS NHOMTAISAN_FK, "
					+ "'' AS TTCP, '' AS LTS, LAPNGANSACH_TAISAN_FK as lnsId, TSCD.PHANLOAI "
					+ "FROM ERP_TAISANCODINH TSCD " +

					"WHERE TSCD.PK_SEQ = '" + this.Id + "' ";
			System.out.println(query);
			ResultSet rs = db.get(query);
			try {
				if (rs.next()) {
					this.Ma = rs.getString("ma") + " - TSCD";
					this.Diengiai = rs.getString("diengiai");
					this.NtsId = rs.getString("nhomtaisan_fk");
					this.loaitaisan = rs.getString("LTS");
					this.ttcp = "Hệ thống tự động tạo";
					this.Dvt = rs.getString("donvi");
					this.SothangKH = rs.getString("sothangKH");
					this.ThangbatdauKH = rs.getString("thangbatdauKH");
					this.Trangthai = rs.getString("trangthai");
					this.Nguoitao = rs.getString("nguoitao");
					this.Nguoisua = rs.getString("nguoisua");
					this.Ngaytao = rs.getString("ngaytao");
					this.Ngaysua = rs.getString("ngaysua");
					this.ThanhTien = "" + rs.getLong("ThanhTien");
					this.SoLuong = rs.getString("SoLuong");

					this.DonGia = "" + rs.getLong("DonGia");
					this.lnsId = rs.getString("lnsId") != null ? rs
							.getString("lnsId") : "";
					this.phanloai = "1";
					this.maTSDD = rs.getString("ma");
					this.Id = "";
				}
				if (rs != null) {
					rs.close();
				}
			} catch (Exception er) {
				this.Msg = "Loi " + er.toString();
			}
		}

		// System.out.println("______Init::::::: " + query);
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public ResultSet getKMCPList() {
		String query = "";

		query = " SELECT NCP.PK_SEQ AS NCPID,TK.SOHIEUTAIKHOAN+'-'+ NCP.TEN +'-'+ NCP.DIENGIAI + isnull( ' - ' + b.DIENGIAI,'') AS DIENGIAI,0 AS PHANTRAM  "
				+ " FROM ERP_NHOMCHIPHI NCP "
				+ "   left join  erp_trungtamchiphi b on NCP.ttchiphi_fk = b.pk_seq  "
				+ " INNER JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN = NCP.TAIKHOAN_FK AND TK.NPP_FK=1"
				+ " WHERE NCP.TRANGTHAI = 1 AND NCP.CONGTY_FK = "
				+ this.ctyId
				+ " ORDER BY NCP.PK_SEQ ";

		System.out.println("Get KMCP List : " + query);
		return this.db.getScrol(query);
	}

	public ResultSet getChoncongdung() {
		String query = "";
		if (this.Id.length() > 0) {
			try {
				query = "SELECT COUNT(CONGDUNG_FK) AS NUM FROM ERP_TAISANCODINH_CONGDUNG WHERE TAISAN_FK = "
						+ this.Id + " ";

				ResultSet rs = this.db.get(query);
				rs.next();

				// if(rs.getString("NUM").equals("0")){ // nghia la cong dung
				// chua duoc luu trong ERP_TAISAN_CONGDUNG
				//
				// query =
				// "SELECT CD.PK_SEQ AS CDTSID, CD.TEN AS DIENGIAI, 0 AS PHANTRAM \ns"
				// +
				// "FROM ERP_NHOMTAISAN_CONGDUNG NTS_CD \n" +
				// "INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK \n"
				// +
				// "WHERE NHOMTAISAN_FK = " + this.NtsId +
				// " ORDER BY CD.PK_SEQ \n";
				//
				// }else
				{

					query = "SELECT CD.PK_SEQ AS CDTSID, CD.TEN AS DIENGIAI, TS_CD.PHANTRAM  \n"
							+ "FROM ERP_TAISANCODINH_CONGDUNG TS_CD \n"
							+ "INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = TS_CD.CONGDUNG_FK \n"
							+ "WHERE TS_CD.TAISAN_FK = "
							+ this.Id
							+ " ORDER BY CD.PK_SEQ \n";

				}

				System.out.println("getChoncongdung: \n" + query
						+ "\n----------------------------------------");
				rs.close();
				return this.db.get(query);
			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				return null;
			}

		} else {
			if (this.NtsId.length() > 0) {
				query = "SELECT CD.PK_SEQ AS CDTSID, CD.TEN AS DIENGIAI, 0 AS PHANTRAM "
						+ "FROM ERP_NHOMTAISAN_CONGDUNG NTS_CD "
						+ "INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK "
						+ "WHERE NHOMTAISAN_FK = "
						+ this.NtsId
						+ " ORDER BY CD.PK_SEQ ";

				System.out.println("getChoncongdung " + query);
				return this.db.get(query);
			} else {
				return null;
			}
		}

	}

	public ResultSet getChoncongdungthem() {
		String query = "";
		if (this.Id.length() > 0) {

			query = "SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI "
					+ "FROM ERP_CONGDUNG WHERE PK_SEQ NOT IN ("
					+ "SELECT CD.PK_SEQ "
					+ "FROM ERP_NHOMTAISAN_CONGDUNG NTS_CD "
					+ "INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK "
					+ "WHERE NHOMTAISAN_FK = " + this.NtsId
					+ ") ORDER BY PK_SEQ ";

			return this.db.get(query);

		} else {
			query = "SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI "
					+ "FROM ERP_CONGDUNG ORDER BY PK_SEQ ";

			return this.db.get(query);
		}

	}

	public ResultSet getChonTTCP() {
		// String query = "";
		// if(this.Id.length() > 0){
		// try{
		// query = "SELECT (A.NUM1 - B.NUM2) AS NUM, A.NUM1, B.NUM2 \n" +
		// "FROM( \n" +
		// "		SELECT TSCD.PK_SEQ AS TSID, COUNT(NTS_PB.TTCP_FK) AS NUM1 \n" +
		// "		FROM ERP_TAISANCODINH TSCD \n" +
		// "		LEFT JOIN ERP_NHOMTAISAN_PHANBOKHAUHAO NTS_PB ON NTS_PB.NHOMTAISAN_FK = TSCD.NHOMTAISAN_FK \n"
		// +
		// "		WHERE TSCD.PK_SEQ = " + this.Id + " \n" +
		// "		GROUP BY TSCD.PK_SEQ \n" +
		// ")A \n" +
		// "LEFT JOIN \n" +
		// "( \n" +
		// "		SELECT TSCD.PK_SEQ AS TSID, COUNT(PB.TTCPNHAN_FK) AS NUM2 \n" +
		// "		FROM ERP_TAISANCODINH TSCD \n" +
		// "		LEFT JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TSCD.TTCP_FK = PB.TTCPCHO_FK AND TSCD.PK_SEQ = PB.TAISAN_FK \n"
		// +
		// "		WHERE TSCD.PK_SEQ = " + this.Id + " \n" +
		// "		GROUP BY TSCD.PK_SEQ \n" +
		// ")B ON A.TSID = B.TSID \n" +
		// "WHERE A.TSID = " + this.Id + " \n" ;
		//
		// ResultSet rs = this.db.get(query);
		// System.out.println("adsadsa11111"+query);
		// rs.next();
		//
		// // if(!rs.getString("NUM1").equals("0") &
		// !rs.getString("NUM2").equals("0")){ // nghia la da luu trong
		// ERP_TRUNGTAMCHIPHI_PHANBO
		// query =
		// "SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, PB.PHANTRAM \n"
		// +
		// "FROM ERP_TAISANCODINH TSCD \n" +
		// "LEFT JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TSCD.TTCP_FK = PB.TTCPCHO_FK AND TSCD.PK_SEQ = PB.TAISAN_FK \n"
		// +
		// "LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCPNHAN_FK \n"
		// +
		// "WHERE TSCD.PK_SEQ =  " + this.Id;
		// // }
		// // else
		// // { //nghia la chua duoc luu trong ERP_TRUNGTAMCHIPHI_PHANBO
		//
		// // query =
		// "SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, 0 AS PHANTRAM \n"
		// +
		// // "FROM ERP_NHOMTAISAN_PHANBOKHAUHAO PB \n" +
		// //
		// "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK \n" +
		// // "WHERE NHOMTAISAN_FK = " + this.NtsId +
		// " ORDER BY TTCP.PK_SEQ \n";
		// // }
		//
		// System.out.println("getChonTTCP \n" + query +
		// "\n----------------------------------------------");
		// rs.close();
		// return this.db.get(query);
		//
		// }catch(java.sql.SQLException e){
		// e.printStackTrace();
		// return null;
		// }
		String query = "";
		if (this.Id.length() > 0) {
			try {
				query = "SELECT COUNT(TTCPNHAN_FK) AS NUM FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE TAISAN_FK = "
						+ this.Id + " ";

				System.out.println(query);
				ResultSet rs = this.db.get(query);
				rs.next();

				if (rs.getString("NUM").equals("0")) { // nghia la cong dung
														// chua duoc luu trong
														// ERP_TAISAN_CONGDUNG

					query = "SELECT TTCP.PK_SEQ AS TTCPID,TTCP.MA, TTCP.TEN AS DIENGIAI, 0 AS PHANTRAM \n"
							+ "FROM ERP_NHOMTAISAN_PHANBOKHAUHAO NTS_TTCP \n"
							+ "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NTS_TTCP.TTCP_FK \n"
							+ "WHERE NHOMTAISAN_FK = "
							+ this.NtsId
							+ " ORDER BY TTCP.PK_SEQ \n";

				} else {

					query = "SELECT TTCP.PK_SEQ AS TTCPID,TTCP.MA, TTCP.TEN AS DIENGIAI, TTCP_PB.PHANTRAM  \n"
							+ "FROM ERP_TRUNGTAMCHIPHI TTCP LEFT JOIN ERP_TRUNGTAMCHIPHI_PHANBO TTCP_PB ON TTCP.PK_SEQ = TTCP_PB.TTCPNHAN_FK \n"
							+ "WHERE TTCP_PB.TAISAN_FK = "
							+ this.Id
							+ "  and ttcp.congty_fk=" + this.ctyId + " ";

					query += " UNION ALL \n"
							+ " SELECT TTCP.PK_SEQ AS TTCPID,TTCP.MA, TTCP.TEN AS DIENGIAI, 0 AS PHANTRAM \n"
							+ " FROM ERP_NHOMTAISAN_PHANBOKHAUHAO NTS_TTCP \n"
							+ " INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = NTS_TTCP.TTCP_FK \n"
							+ " WHERE NHOMTAISAN_FK = "
							+ this.NtsId
							+ " AND TTCP.PK_SEQ NOT IN ( SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE TAISAN_FK = "
							+ this.Id + " )";

				}

				System.out.println("getChoncongdung " + query);
				rs.close();
				return this.db.get(query);
			} catch (java.sql.SQLException e) {
				return null;
			}

		} else {
			if (this.NtsId.length() > 0) {
				query = "SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, 0 AS PHANTRAM \n"
						+ "FROM ERP_NHOMTAISAN_PHANBOKHAUHAO PB \n"
						+ "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK \n"
						+ "WHERE NHOMTAISAN_FK = "
						+ this.NtsId
						+ " and ttcp.congty_fk="
						+ this.ctyId
						+ " ORDER BY TTCP.PK_SEQ \n";
				return this.db.get(query);
			}
		}
		System.out.println(query);

		return null;
	}

	public ResultSet getChonTTCPThem() {
		String query = "";
		if (this.Id.length() > 0) {

			query = "SELECT PK_SEQ AS TTCPID, MA, DIENGIAI \n"
					+ "FROM ERP_TRUNGTAMCHIPHI WHERE PK_SEQ NOT IN (\n"
					+ "SELECT TTCP.PK_SEQ  \n"
					+ "FROM ERP_NHOMTAISAN_PHANBOKHAUHAO PB \n"
					+ "INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK \n"
					+ "WHERE NHOMTAISAN_FK = " + this.NtsId
					+ " )and ttcp.congty_fk=" + this.ctyId
					+ " ORDER BY PK_SEQ \n";

			return this.db.get(query);

		} else {
			query = "SELECT PK_SEQ AS TTCPID, MA, DIENGIAI \n"
					+ "FROM ERP_TRUNGTAMCHIPHI where congty_fk=" + this.ctyId
					+ " ORDER BY PK_SEQ\n";
			System.out.println("query" + query);
			return this.db.get(query);
		}

	}

	public String getCdIdsList() {
		String tmp = "";
		if (this.cdtsIds != null) {
			for (int i = 0; i < this.cdtsIds.length; i++) {
				tmp = tmp + this.cdtsIds[i] + ";";
			}
		}
		return tmp;
	}

	public String getTtcpIdsList() {
		String tmp = "";
		if (this.ttcpIds != null) {
			for (int i = 0; i < this.ttcpIds.length; i++) {
				tmp = tmp + this.ttcpIds[i] + ";";
			}
		}
		return tmp;
	}

	public boolean themmoiMa(HttpServletRequest request) {
		String query = "";
		int thang = 0;
		int nam = 0;
		int sodong = 0;
		ResultSet rs = null;
		boolean error = false;
		Utility util = new Utility();
		float totalPt = 0;
		for (int i = 0; i < phanBoList.size(); i++) {
			IPhanBo phanBo = phanBoList.get(i);
			if(phanBo.getPhanTram() ==null || phanBo.getPhanTram().trim().length()==0)
				phanBo.setPhanTram("0");
			totalPt += Double.parseDouble(phanBo.getPhanTram());
		}
		if (totalPt != 100) {
			this.Msg = "Tổng phần trăm phân bổ phải bằng 100%";
			return false;
		}

		// if(this.phanloai.equals("1")){
		if (this.ThangbatdauKH.length() == 10) {
			thang = Integer.valueOf(this.ThangbatdauKH.substring(5, 7));
			nam = Integer.valueOf(this.ThangbatdauKH.substring(0, 4));

			query = "Select count(PK_SEQ) as sodong From ERP_KHOASOKETOAN "
					+ "Where " + " " + thang + " "
					+ "IN ( Select ThangKS From ERP_KHOASOKETOAN Where Nam="
					+ nam + ")";
			rs = this.db.get(query);
		}

		try {

			if (rs != null) {
				while (rs.next()) {
					sodong = rs.getInt("sodong");
				}
				rs.close();
				if (sodong > 0) {
					// System.out.println("So dong " + sodong);
					this.setMsg("Vui lòng chọn lại tháng bắt đầu khấu hao vì tháng "
							+ thang + " năm " + nam + " đã khóa sổ rồi!");
					error = true;
				}
			}
			query = " SELECT MA FROM Erp_TaiSanCoDinh WHERE MA = '"+this.Ma+"'  ";
			ResultSet rscheckma = db.get(query);
			if(rscheckma.next())
			{
				rscheckma.close();
				this.Msg = "Đã có mã tài sản, vui òng chọn mã khác";
				return false;
			}
			rscheckma.close();
			if (!error) {
				this.db.getConnection().setAutoCommit(false);

				String LAPNGANSACH_TAISAN_FK = this.lnsId.trim().length() <= 0 ? "null"
						: this.lnsId.trim();
				query = "INSERT INTO ERP_TAISANCODINH(ma, diengiai, loaitaisan_fk, congty_fk,donvi, dongia, thanhtien, sothangKH, thangbatdauKH, trangthai, "
						+ "nguoitao, nguoisua, ngaytao, ngaysua, LAPNGANSACH_TAISAN_FK,PHANLOAI,DVTH_FK) "
						+ "VALUES ('"
						+ this.Ma
						+ "', N'"
						+ this.Diengiai
						+ "', "
						+ this.LtsId
						+ ", '"
						+ this.ctyId
						+ "',N'"
						+ this.Dvt
						+ "', '0', '0', '"
						+ this.SothangKH
						+ "', "
						+ "'"
						+ this.ThangbatdauKH
						+ "', '0', "
						+ this.Nguoitao
						+ ", "
						+ this.Nguoisua
						+ ", '"
						+ this.getDateTime()
						+ "', "
						+ "'"
						+ this.getDateTime()
						+ "', "
						+ LAPNGANSACH_TAISAN_FK + ",1, " + this.dvthId + " )";

				System.out.println("them moi tai san: " + query);

				if (this.db.update(query)) {

					query = "SELECT SCOPE_IDENTITY() AS ID";
					rs = this.db.get(query);
					rs.next();

					this.Id = rs.getString("ID");

					for (int i = 0; i < phanBoList.size(); i++) {
						IPhanBo phanBo = phanBoList.get(i);
						query = "INSERT INTO ERP_TAISANCODINH_KHOANMUCCHIPHI(TAISANCODINH_FK, KHOANMUCCHIPHI_FK, PHANTRAM) VALUES ("
								+ this.Id
								+ ", "
								+ phanBo.getKhoanMucId()
								+ ", " + phanBo.getPhanTram() + " )";

						System.out.println(query);
						if (!this.db.update(query)) {
							this.Msg = "4.Error: " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}

					// this.CapnhatChovaNhan();

					if (this.Khauhao()) {
						this.Msg = "Dữ liệu đã được lưu thành công";
					}

					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);

					return true;
				} else {
					this.setMsg("Không thể tạo mới tài sản cố định " + query);
					this.db.getConnection().rollback();
					return false;
				}

			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			this.setMsg("Không thể tạo mới tài sản cố định " + query);
			return false;
		}
		// }else{
		// String LAPNGANSACH_TAISAN_FK = this.lnsId.trim().length() <= 0 ?
		// "null" : this.lnsId.trim();
		// String loaitaisan_fk = "100012"; // Loại tài sản: TSCD xây dựng cơ
		// bản dở dang
		//
		// query =
		// "INSERT INTO ERP_TAISANCODINH(ma, diengiai, loaitaisan_fk, congty_fk, ttcp_fk, soluong, dongia, thanhtien, sothangKH, thangbatdauKH, trangthai, "
		// +
		// "nguoitao, nguoisua, ngaytao, ngaysua, nhomtaisan_fk, LAPNGANSACH_TAISAN_FK, PHANLOAI) "
		// +
		// "SELECT N'" +this.Ma +"', N'" +this.Diengiai +"', " + loaitaisan_fk +
		// ", '" +this.ctyId +"', null, '1', '0', '0', '" + this.SothangKH +
		// "', " +
		// "'" + this.ThangbatdauKH + "', '0', " + this.Nguoitao + ", " +
		// this.Nguoisua + ", '" + this.getDateTime() + "', " +
		// "'" + this.getDateTime() + "', null, " + LAPNGANSACH_TAISAN_FK + ", "
		// + this.phanloai + " " ;
		//
		//
		// System.out.println("them moi tai san do dang: " + query);
		// if (this.db.update(query)){
		// return true;
		// }else{
		// return false;
		// }
		//
		//
		// }
	}

	public boolean UpdateMa(HttpServletRequest request) {
		String query = "";
		int thang = 0;
		int nam = 0;
		int sodong = 0;
		ResultSet rs = null;
		boolean error = false;
		Utility util = new Utility();
		try {
			
			float totalPt = 0;
			for (int i = 0; i < phanBoList.size(); i++) {
				IPhanBo phanBo = phanBoList.get(i);
				if(phanBo.getPhanTram() ==null || phanBo.getPhanTram().trim().length()==0)
					phanBo.setPhanTram("0");
				totalPt += Double.parseDouble(phanBo.getPhanTram());
			}
			if (totalPt != 100) {
				this.Msg = "Tổng phần trăm phân bổ phải bằng 100%";
				return false;
			}

			query = "SELECT COUNT(THANG) AS NUM FROM ERP_TAISANCODINH_CHITIET WHERE TAISAN_FK = "
					+ this.Id + " AND KHAUHAOTHUCTE > 0";

			rs = this.db.get(query);
			rs.next();

			if (Integer.parseInt(this.SothangKH) < rs.getInt("NUM")) {
				this.Msg = "Số tháng khấu hao dự kiến phải lớn hơn số tháng khấu hao đã thực hiện trên tài sản";
				error = true;
			}

			if (this.ThangbatdauKH.length() == 10) {
				thang = Integer.valueOf(this.ThangbatdauKH.substring(5, 7));
				nam = Integer.valueOf(this.ThangbatdauKH.substring(0, 4));

				query = " Select count(PK_SEQ) as sodong From ERP_KHOASOKETOAN Where "
						+ " "
						+ thang
						+ " IN ( Select ThangKS From ERP_KHOASOKETOAN Where Nam="
						+ nam + ")";
				rs = this.db.get(query);
			}

			if (rs != null) {
				while (rs.next()) {
					sodong = rs.getInt("sodong");
				}
				rs.close();
				if (sodong > 0) {
					System.out.println("So dong " + sodong);
					this.setMsg("Vui lòng chọn lại tháng bắt đầu khấu hao vì tháng "
							+ thang + " năm " + nam + " đã khóa sổ rồi!");
					error = true;
				}
			}
			query = " SELECT MA FROM Erp_TaiSanCoDinh WHERE MA = '"+this.Ma+"' AND PK_SEQ != "+this.Id;
			ResultSet rscheckma = db.get(query);
			if(rscheckma.next())
			{
				rscheckma.close();
				this.Msg = "Đã có mã tài sản, vui òng chọn mã khác";
				return false;
			}
			rscheckma.close();
			if (!error) {
				this.db.getConnection().setAutoCommit(false);
				String LAPNGANSACH_TAISAN_FK = this.lnsId.trim().length() <= 0 ? "null"
						: this.lnsId.trim();
				query = "UPDATE Erp_TaiSanCoDinh SET ma = '" + this.Ma
						+ "', diengiai = N'" + this.Diengiai
						+ "',  sothangKH = " + this.SothangKH + ", "
						+ "thangbatdauKH = '" + this.ThangbatdauKH
						+ "', LAPNGANSACH_TAISAN_FK = " + LAPNGANSACH_TAISAN_FK
						+ ",donvi = N'" + this.Dvt + "', " + "nguoitao = '"
						+ this.Nguoitao + "'," + " nguoisua = '"
						+ this.Nguoisua + "', ngaytao = '" + this.Ngaytao
						+ "', " + "ngaysua = '" + this.Ngaysua + "' ,"
						+ "loaitaisan_fk = '" + this.LtsId + "'" + " ,DVTH_FK="
						+ this.dvthId + " WHERE pk_seq = '" + this.Id + "' ";

				System.out.println("1.Update: " + query);

				if (db.update(query)) {
					query = "DELETE ERP_TAISANCODINH_KHOANMUCCHIPHI WHERE TAISANCODINH_FK = "
							+ this.Id;
					if (!this.db.update(query)) {
						this.Msg = "4.Error: " + query;
						this.db.getConnection().rollback();
						return false;
					}
					System.out.println("query" + query);
					for (int i = 0; i < phanBoList.size(); i++) {
						IPhanBo phanBo = phanBoList.get(i);
						query = "INSERT INTO ERP_TAISANCODINH_KHOANMUCCHIPHI(TAISANCODINH_FK, KHOANMUCCHIPHI_FK, PHANTRAM) VALUES ("
								+ this.Id
								+ ", "
								+ phanBo.getKhoanMucId()
								+ ", " + phanBo.getPhanTram() + " )";

						System.out.println(query);
						if (!this.db.update(query)) {
							this.Msg = "4.Error: " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}

					// this.CapnhatChovaNhan();

					this.Khauhao();
					// }
					
					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);
					return true;

				} else {
					this.db.getConnection().rollback();
					this.setMsg("Không thể cập nhật tài sản cố định " + query);
					return false;
				}
			} else {
				return false;
			}
			
		} catch (Exception e) {
			db.update("rollback");
			this.setMsg("Không thể tạo cập nhật tài sản cố định " + query);
			return false;
		}

	}

	private void CapnhatChovaNhan() {
		String query;
		// Cap nhat lai NHANPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 0 WHERE PK_SEQ IN ( "
				+ "SELECT PK_SEQ "
				+ "FROM ERP_TRUNGTAMCHIPHI TTCP "
				+ "WHERE NHANPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE TAISAN_FK IS NOT NULL) )";
		this.db.update(query);

		// Cap nhat lai NHANPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 1 "
				+ "WHERE PK_SEQ IN (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0 AND TAISAN_FK IS NOT NULL) ";
		this.db.update(query);

		// Cap nhat lai CHOPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 0 WHERE PK_SEQ IN ( "
				+ "SELECT PK_SEQ "
				+ "FROM ERP_TRUNGTAMCHIPHI TTCP "
				+ "WHERE CHOPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE TAISAN_FK IS NOT NULL) )";
		this.db.update(query);

		// Cap nhat lai CHOPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 1 "
				+ "WHERE PK_SEQ IN  (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0  AND TAISAN_FK IS NOT NULL) ";
		this.db.update(query);

	}

	public void Delete(String id) {

		String query = "SELECT TRANGTHAI FROM ERP_TAISANCODINH WHERE PK_SEQ = "
				+ id + " ";

		System.out.println(query);
		ResultSet rs = this.db.get(query);

		try {
			if (rs != null) {
				rs.next();
				// Chua thuc hien khau hao thuc te
				if (rs.getString("TRANGTHAI").equals("0")) {
					rs.close();

					query = "DELETE FROM ERP_TAISANCODINH_CHITIET WHERE TAISAN_FK = "
							+ id + "";
					System.out.println(query);

					this.db.update(query);

					query = "DELETE FROM ERP_TAISANCODINH_CONGDUNG WHERE TAISAN_FK = "
							+ id + "";
					System.out.println(query);

					this.db.update(query);

					query = "DELETE FROM ERP_TRUNGTAMCHIPHI_PHANBO "
							+ "WHERE TTCPCHO_FK IN (SELECT TTCP_FK FROM ERP_TAISANCODINH WHERE PK_SEQ = "
							+ id + ") AND TAISAN_FK = " + id + " ";
					System.out.println(query);
					this.db.update(query);

					query = "DELETE FROM ERP_TRUNGTAMCHIPHI WHERE PK_SEQ IN (SELECT TTCP_FK FROM ERP_TAISANCODINH WHERE PK_SEQ = "
							+ id + ")";
					System.out.println(query);

					this.db.update(query);

					query = "DELETE FROM ERP_TAISANCODINH WHERE PK_SEQ = " + id
							+ "";
					System.out.println(query);

					this.db.update(query);

					this.CapnhatChovaNhan();
				}
			}
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public void init(String sql) {
		String query = "";
		if (this.Id.length() == 0) {
			if (sql.length() > 0) {
				query = sql;
				this.TsList = createSplittingDataNew(this.db, 50, 10,
						"MA asc ", query);
				/* this.TsList = this.db.get(query); */
			} else {
				query = " Select * from ( "
						+ "SELECT  TSCD.PK_SEQ, TSCD.MA, TSCD.DIENGIAI as DIENGIAI,TSCD.DVTH_FK, \n"
						+ "TSCD.TRANGTHAI, \n"
						+ "ISNULL(LTS.DIENGIAI, '') AS LOAITAISAN, \n"
						+ "ISNULL(NTS.MA, '') AS NHOMTAISAN, ISNULL(TTCP.MA, '') AS TTCP, \n"
						+ "TSCD.SOTHANGKH, TSCD.THANGBATDAUKH,  \n "
						+ "N'TSCD' AS PHANLOAI \n "
						+ "FROM ERP_TAISANCODINH TSCD \n"
						+ "INNER JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n"
						+ "LEFT JOIN ERP_NHOMTAISAN NTS ON NTS.PK_SEQ = TSCD.NHOMTAISAN_FK \n"
						+ "LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK \n"
						+ "LEFT JOIN( \n"
						+ "		SELECT isnull(TAISAN_FK,0) as TAISAN_FK, COUNT(*) AS NUM \n"
						+ "		FROM ERP_THANHLYTAISAN_TAISAN TLTS_TS \n"
						+ "		GROUP BY TAISAN_FK \n"
						+ "	)THANHLY ON THANHLY.TAISAN_FK = TSCD.PK_SEQ \n"
						+

						"WHERE TSCD.CONGTY_FK = "
						+ this.ctyId
						+ "  AND ISNULL(PHANLOAI,'1') = 1 \n"
						+

						"UNION ALL \n"
						+ "SELECT  TSCD.PK_SEQ, TSCD.MA, TSCD.DIENGIAI as DIENGIAI,TSCD.DVTH_FK, \n"
						+ "TSCD.TRANGTHAI, \n"
						+ "ISNULL(LTS.DIENGIAI, '') AS LOAITAISAN, \n"
						+ "ISNULL(NTS.MA, '') AS NHOMTAISAN, ISNULL(TTCP.MA, '') AS TTCP, \n"
						+ "TSCD.SOTHANGKH, TSCD.THANGBATDAUKH,  \n "
						+ "N'TSDD' AS PHANLOAI \n "
						+ "FROM ERP_TAISANCODINH TSCD \n"
						+ "INNER JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK \n"
						+ "LEFT JOIN ERP_NHOMTAISAN NTS ON NTS.PK_SEQ = TSCD.NHOMTAISAN_FK \n"
						+ "LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK \n"
						+ "LEFT JOIN( \n"
						+ "		SELECT isnull(TAISAN_FK,0) as TAISAN_FK, COUNT(*) AS NUM \n"
						+ "		FROM ERP_THANHLYTAISAN_TAISAN TLTS_TS \n"
						+ "		GROUP BY TAISAN_FK \n"
						+ "	)THANHLY ON THANHLY.TAISAN_FK = TSCD.PK_SEQ \n" +

						"WHERE TSCD.CONGTY_FK = " + this.ctyId
						+ "  AND ISNULL(PHANLOAI,'1') = 2) A where 1=1 \n ";
				if (this.Ma.length() > 0) {
					query = query + " and A.MA LIKE '%" + this.Ma + "%'";
				}

				if (this.LtsId.length() > 0) {
					query = query + " and A.LTSID = " + this.LtsId + " ";
				}

				if (this.NtsId.length() > 0) {
					query = query + " and A.NHOMTAISAN = " + this.NtsId + " ";
				}

				if (this.Trangthai.length() > 0) {
					query = query + " and A.TRANGTHAI = " + this.Trangthai
							+ " ";
				}
				System.out.println("sadsadad" + query);

				this.TsList = createSplittingData(50, 10, "MA asc ", query);
				/* this.TsList = this.db.get(query); */
			}
		} else {
			// this.Khauhao(); // Cap nhat lai ke hoach khau hao du kien
			this.Msg = "";
			if (this.phanloai.equals("1")) {
				query = "SELECT TSCD.MA, TSCD.DIENGIAI, ISNULL(TSCD.DONVI, '') AS DONVI,TSCD.DVTH_Fk, "
						+ "TSCD.SOTHANGKH, TSCD.THANGBATDAUKH, TSCD.TRANGTHAI, TSCD.NGUOITAO, TSCD.NGUOISUA, "
						+ "TSCD.NGAYTAO, TSCD.NGAYSUA, TSCD.THANHTIEN, TSCD.SOLUONG, TSCD.DONGIA, TSCD.NHOMTAISAN_FK, "
						+ "ISNULL(TTCP.MA,'') AS TTCP, LTS.DIENGIAI AS LTS, LAPNGANSACH_TAISAN_FK as lnsId, TSCD.TTCP_FK AS TTCP_FK, TSCD.PHANLOAI,ISNULL(TSCD.NGAYGHITANG,'') AS NGAYGHITANG,TSCD.LOAITAISAN_FK  "
						+ "FROM ERP_TAISANCODINH TSCD "
						+ "LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = TSCD.TTCP_FK "
						+ "LEFT JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK "
						+ "WHERE TSCD.PK_SEQ = '" + this.Id + "' ";
			} else {
				query = "SELECT  TSCD.MA, TSCD.DIENGIAI, ISNULL(TSCD.DONVI, '') AS DONVI,TSCD.DVTH_FK ,"
						+ "'0' AS SOTHANGKH, '' AS THANGBATDAUKH, TSCD.TRANGTHAI, TSCD.NGUOITAO, TSCD.NGUOISUA, "
						+ "TSCD.NGAYTAO, TSCD.NGAYSUA, TSCD.THANHTIEN, "
						+ "0 AS TONGTIENBT, "
						+ "TSCD.SOLUONG, TSCD.DONGIA, '0' AS NHOMTAISAN_FK, "
						+ "'' AS TTCP, LTS.DIENGIAI AS LTS, LAPNGANSACH_TAISAN_FK as lnsId, TSCD.PHANLOAI,TSCD.LOAITAISAN_FK "
						+ "FROM ERP_TAISANCODINH TSCD "
						+ "INNER JOIN ERP_LOAITAISAN LTS ON LTS.PK_SEQ = TSCD.LOAITAISAN_FK "
						+ "WHERE TSCD.PK_SEQ = '" + this.Id + "' ";

			}
			System.out.println("_____Khoi tao: " + query);

			ResultSet rs = db.get(query);
			try {
				if (rs.next()) {
					this.Ma = rs.getString("ma");
					this.Diengiai = rs.getString("diengiai");
					this.NtsId = rs.getString("nhomtaisan_fk") != null ? rs
							.getString("nhomtaisan_fk") : "";
					this.loaitaisan = rs.getString("LTS");
					this.ttcp = rs.getString("TTCP_FK") != null ? rs
							.getString("TTCP_FK") : "";
					this.Dvt = rs.getString("donvi");
					this.SothangKH = rs.getString("sothangKH");
					this.ThangbatdauKH = rs.getString("thangbatdauKH");
					this.Trangthai = rs.getString("trangthai");
					this.Nguoitao = rs.getString("nguoitao");
					this.Nguoisua = rs.getString("nguoisua");
					this.Ngaytao = rs.getString("ngaytao");
					this.Ngaysua = rs.getString("ngaysua");
					this.LtsId = rs.getString("LOAITAISAN_FK");
					this.ThanhTien = rs.getString("ThanhTien");
					this.SoLuong = rs.getString("SoLuong");
					this.ngayghitang = rs.getString("ngayghitang");
					this.dvthId = rs.getString("DVTH_FK") != null ? rs
							.getString("DVTH_FK") : "";

					if (Double.parseDouble(this.SoLuong) > 0) {
						this.Khauhao();
					}

					this.DonGia = rs.getString("DonGia");
					this.lnsId = rs.getString("lnsId") != null ? rs
							.getString("lnsId") : "";
				}
				if (rs != null) {
					rs.close();
				}

				query = "SELECT KHOANMUCCHIPHI_FK, PHANTRAM FROM ERP_TAISANCODINH_KHOANMUCCHIPHI WHERE TAISANCODINH_FK = "
						+ this.Id + "";
				ResultSet rsKMPB = db.get(query);
				while (rsKMPB.next()) {
					IPhanBo phanBo = new PhanBo();
					phanBo.setKhoanMucId(rsKMPB.getString("KHOANMUCCHIPHI_FK"));
					phanBo.setPhanTram(rsKMPB.getString("PHANTRAM"));
					this.phanBoList.add(phanBo);
				}
				rsKMPB.close();
			} catch (Exception er) {
				this.Msg = "Loi " + er.toString();
			}
			query = " SELECT LOAICHUNGTU,SOCHUNGTU,NGAYDIEUCHINH,GIATRI,SOTHANG FROM erp_taisancodinh_dieuchinh where TSCD_FK ="
					+ this.Id;
			dieuChinhNguyenGiaRs = db.get(query);

			query = " select DC.PK_SEQ,DC.NGAYCHUNGTU,DVTH.TEN AS PBTEN from ERP_DIEUCHUYENTAISAN DC INNER JOIN ERP_DONVITHUCHIEN DVTH "
					+ " ON DVTH.PK_SEQ= DC.DVTH_FK WHERE TAISAN_FK=" + this.Id;
			this.dieuChuyenTaiSanRs = db.get(query);

		}

	}

	public void createRs() {

		String query = "";
		/*
		 * String query = "SELECT * FROM ERP_CONGDUNG WHERE TRANGTHAI = '1'";
		 * this.CdList = this.db.get(query);
		 * 
		 * query =
		 * "SELECT * FROM ERP_NHOMTAISAN WHERE  TRANGTHAI = '1' ORDER BY MA";
		 * this.NtsList = this.db.get(query);
		 * 
		 * query =
		 * "select PK_SEQ,MA +'-' +TEN  as TEN from ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1 and congty_fk="
		 * +this.ctyId+" ORDER BY MA "; this.ttcpRs = db.get(query);
		 */

		query = "SELECT PK_SEQ, TEN FROM ERP_PHUONGPHAPKHAUHAO ";
		this.PPKHrS = this.db.get(query);

		query = "SELECT * FROM ERP_LOAITAISAN  WHERE TRANGTHAI = '1' ORDER BY DIENGIAI";
		this.LtsList = this.db.get(query);

		query = "SELECT PK_SEQ ,MA, TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI=1 AND CONGTY_FK = "
				+ this.ctyId + " ORDER BY MA";
		this.dvthList = db.get(query);

		query = "select PK_SEQ, DIENGIAI from ERP_LAPNGANSACH_TAISAN  "
				+ "where LAPNGANSACH_FK in ( select PK_SEQ from ERP_LAPNGANSACH where HIEULUC = '1' and NAM >= '"
				+ this.getDateTime().substring(0, 4) + "' )";

		this.lnsRs = db.get(query);

		/*
		 * if(this.lnsId.trim().length() > 0) { query =
		 * "select DONVITHUCHIEN_FK, NHOMTAISAN_FK, SOTHANGKH from ERP_LAPNGANSACH_TAISAN where PK_SEQ = '"
		 * + this.lnsId + "'"; ResultSet rsLNS = db.get(query); try {
		 * if(rsLNS.next()) { this.NtsId = rsLNS.getString("NHOMTAISAN_FK");
		 * this.SothangKH = rsLNS.getString("SOTHANGKH"); } rsLNS.close(); }
		 * catch (Exception e) { }
		 * 
		 * if(this.NtsId.trim().length() <= 0) { this.Msg =
		 * "Vui lòng chọn Nhóm tài sản "; } query =
		 * "select DONVITHUCHIEN_FK, NHOMTAISAN_FK, SOTHANGKH from ERP_LAPNGANSACH_TAISAN where PK_SEQ = '"
		 * + this.lnsId + "'";
		 * 
		 * }
		 */

		query = "select PK_SEQ,MA +'-' +TEN  as TEN from ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1 and congty_fk="
				+ this.ctyId + " ORDER BY MA ";
		this.ttcpRs = db.get(query);
	}

	public ResultSet Laykhauhao() {
		System.out
				.println("SELECT * FROM ERP_TAISANCODINH_CHITIET WHERE TAISAN_FK = '"
						+ this.Id + "'");
		return this.db
				.get("SELECT * FROM ERP_TAISANCODINH_CHITIET WHERE TAISAN_FK = '"
						+ this.Id + "'");
	}

	public boolean Khauhao() {
		if (this.Id.length() == 0) {
			return false;
		} else {
			// UpdateMa();
			String query = " SELECT TS.THANHTIEN + ISNULL(DIEUCHINH.GIATRI,0) AS THANHTIEN , ISNULL(TS.SOTHANGKH,0) - ISNULL(TS.SOTHANGDAKHAUHAO,0) + ISNULL(DIEUCHINH.THANG,0) AS  SOTHANGKH , ISNULL(THANGDAKHAUHAO.THANGTHU, 0)  AS THANGDAKHAUHAO, \n"
					+ " (ISNULL(TS.SOTHANGKH,0) - ISNULL(TS.SOTHANGDAKHAUHAO,0) - CONVERT(INT, ISNULL(THANGDAKHAUHAO.THANGTHU, 0)) ) AS THANGCONKH, \n"
					+ " ISNULL(THANGDAKHAUHAO.THANGTHU, 0) + ISNULL(TS.SOTHANGDAKHAUHAO,0) AS SOTHANGDAKHAUHAO, \n"
					+ " ISNULL(TIENDAKHAUHAO.TIENDAKHAUHAO,0) as TIENDAKHAUHAO \n"
					+ " FROM ERP_TAISANCODINH TS \n"
					+ " LEFT JOIN \n"
					+ "( \n"
					+ "	SELECT TOP 1 THANGTHU, TAISAN_FK \n"
					+ "	FROM ERP_KHAUHAOTAISAN \n"
					+ "	WHERE KHAUHAO > 0 \n"
					+ "	AND TAISAN_FK = '"
					+ this.Id
					+ "'  \n"
					+ "   ORDER BY THANG DESC, NAM DESC \n"
					+ ")THANGDAKHAUHAO ON THANGDAKHAUHAO.TAISAN_FK = TS.PK_SEQ \n"
					+ "LEFT JOIN \n"
					+ "( \n"
					+ "	SELECT TAISAN_FK, COUNT(KHAUHAO) AS SOTHANGDAKHAUHAO \n"
					+ "	FROM ERP_KHAUHAOTAISAN \n"
					+ "	WHERE TAISAN_FK = '"
					+ this.Id
					+ "' AND KHAUHAO > 0 \n"
					+ "	GROUP BY TAISAN_FK \n"
					+ ")SOTHANGDAKHAUHAO ON SOTHANGDAKHAUHAO.TAISAN_FK =  TS.PK_SEQ  \n"
					+

					"LEFT JOIN \n"
					+ "( \n"
					+ "	SELECT TOP 1 TAISAN_FK, KHAUHAOLUYKE AS TIENDAKHAUHAO \n"
					+ "	FROM ERP_KHAUHAOTAISAN \n"
					+ "	WHERE TAISAN_FK = '"
					+ this.Id
					+ "' AND KHAUHAO > 0 \n"
					+ "	ORDER BY THANG DESC,NAM DESC \n"
					+ ")TIENDAKHAUHAO ON TIENDAKHAUHAO.TAISAN_FK =  TS.PK_SEQ  \n"
					+ "LEFT JOIN \n"
					+ "( \n"
					+ "	SELECT TSCD_FK,SUM(GIATRI) AS GIATRI,SUM(SOTHANG) AS THANG \n"
					+ "	FROM ERP_TAISANCODINH_DIEUCHINH \n"
					+ "	WHERE TSCD_FK = '"
					+ this.Id
					+ "' \n"
					+ "	GROUP BY  TSCD_FK \n"
					+ ")DIEUCHINH ON DIEUCHINH.TSCD_FK =  TS.PK_SEQ  \n" +

					"WHERE TS.PK_SEQ = '" + this.Id + "' ";
			System.out.println(query);

			ResultSet rs = this.db.get(query);
			try {
				if (rs != null) {

					rs.next();
					double thanhtien = Double.parseDouble(rs
							.getString("THANHTIEN"));
					double tiendaKH = Double.parseDouble(rs
							.getString("TIENDAKHAUHAO"));
					int thangdaKH = rs.getInt("THANGDAKHAUHAO");
					int sothangKH = rs.getInt("SOTHANGKH");
					int sothangdaKH = Integer.parseInt(rs
							.getString("SOTHANGDAKHAUHAO"));

					if (thanhtien > 0 & sothangKH > 0) {
						if (thangdaKH == 0) { // nghia la chua co ke hoach khau
												// hao
							System.out
									.println("Thuc hien tao moi khau hao du kien");
							this.Tinhkhauhao_New(sothangKH, thanhtien);
						} else { // da co ke hoach khau hao roi, neu cap nhat
									// lai
							System.out
									.println("Thuc hien update khau hao du kien");
							this.Tinhkhauhao_Update(sothangKH, thangdaKH,
									sothangdaKH, thanhtien, tiendaKH);
						}
					}
					rs.close();
				}
			} catch (java.sql.SQLException e) {
				System.out.println("Error : " + e.toString());
			}

			return true;
		}
	}

	private void Tinhkhauhao_New(int n, double thanhtien) {
		int month = 1;
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		double[] khdkVal = new double[n];
		double[] lkdkVal = new double[n];
		double[] gtdkVal = new double[n];

		lkdkVal[0] = 0;

		for (int i = 0; i < n; i++) {

			// String m, y;
			String thang = "";

			if (i == (n - 1)) {
				if (i > 1)
					khdkVal[i] = thanhtien - lkdkVal[i - 1];
				else
					khdkVal[i] = thanhtien;

				if (i > 1)
					lkdkVal[i] = khdkVal[i] + lkdkVal[i - 1];
				else
					lkdkVal[i] = khdkVal[i];

				gtdkVal[i] = thanhtien - lkdkVal[i];
			} else {
				khdkVal[i] = Math.round(thanhtien / n);
				if (i > 0)
					lkdkVal[i] = khdkVal[i] + lkdkVal[i - 1];
				else
					lkdkVal[i] = khdkVal[i];

				gtdkVal[i] = thanhtien - lkdkVal[i];
			}

			IKhauHaoDuKien khauhao = new KhauHaoDuKien();
			khauhao.setTaisanid(this.Id);
			khauhao.setThang(formatter.format(month));
			khauhao.setKhauhaodukien(formatter.format(khdkVal[i]));
			khauhao.setKhauhaothucte("0");
			khauhao.setKhauhaoluykedukien(formatter.format(lkdkVal[i]));
			khauhao.setKhauhaoluykethucte(formatter.format(0));
			khauhao.setGiatriconlaidukien(formatter.format(gtdkVal[i]));
			khauhao.setGiatriconlaithucte(formatter.format(0));
			khauhaodukienList.add(khauhao);

			month++;
		}

	}

	private boolean Tinhkhauhao_Update(int sothangKH, int thangdaKH,
			int sothangdaKH, double thanhtien, double tiendaKH) {
		NumberFormat formatter = new DecimalFormat("#,###,###.##");
		double sotienconlai = thanhtien;
		double luykebandau = tiendaKH;
		double conlaibandau = thanhtien - tiendaKH;
		int n = sothangKH;
		double[] khdkVal = new double[n];
		double[] lkdkVal = new double[n];
		double[] gtdkVal = new double[n];
		String query = "";
		khauhaodukienList.clear();

		try {
			// LẤY RA THÁNG KHẤU HAO THỰC THẾ TRÊN HỆ THỐNG ĐỂ KIỂM TRA SỐ THÁNG
			// CÒN CHƯA KHẤU HAO CHO TỚI LÚC THAY ĐỔI NGUYÊN GIÁ/ SỐ THÁNG KH
			query = "Select thangthu + isnull(ts.sothangdakhauhao,0) as thang,kh.KHAUHAO, kh.KHAUHAOLUYKE,kh.GIATRICONLAI FROM ERP_KHAUHAOTAISAN kh  inner join erp_taisancodinh ts on "
					+ " ts.pk_seq = kh.taisan_fk where TAISAN_FK =" + this.Id;
			ResultSet rs = db.get(query);
			while (rs.next()) {
				IKhauHaoDuKien khauhao = new KhauHaoDuKien();
				khauhao.setTaisanid(this.Id);
				khauhao.setThang(formatter.format(rs.getDouble("thang")));
				khauhao.setKhauhaodukien(formatter.format(rs
						.getDouble("KHAUHAO")));
				khauhao.setKhauhaothucte(formatter.format(rs
						.getDouble("KHAUHAO")));
				khauhao.setKhauhaoluykedukien(formatter.format(rs
						.getDouble("KHAUHAOLUYKE")));
				khauhao.setKhauhaoluykethucte(formatter.format(rs
						.getDouble("KHAUHAOLUYKE")));
				khauhao.setGiatriconlaidukien(formatter.format(rs
						.getDouble("GIATRICONLAI")));
				khauhao.setGiatriconlaithucte(formatter.format(rs
						.getDouble("GIATRICONLAI")));
				khauhaodukienList.add(khauhao);
			}
			rs.close();
			int m = n - thangdaKH;
			int month = sothangdaKH + 1;
			int sothangconlai = n - sothangdaKH;
			// System.out.println("m: " + m);

			for (int i = 0; i < m; i++) {

				if (i == (m - 1)) // thang cuoi cung
				{
					if (i > 0)
						khdkVal[i] = thanhtien - lkdkVal[i - 1];
					else
						khdkVal[i] = sotienconlai;

					if (i > 0)
						lkdkVal[i] = khdkVal[i] + lkdkVal[i - 1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i];

					gtdkVal[i] = thanhtien - lkdkVal[i];
				} else {
					khdkVal[i] = Math.round(conlaibandau / (n - thangdaKH));

					if (i > 0)
						lkdkVal[i] = khdkVal[i] + lkdkVal[i - 1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i];

					gtdkVal[i] = thanhtien - lkdkVal[i];
				}

				if (i != (m - 1)) {
					IKhauHaoDuKien khauhao = new KhauHaoDuKien();
					khauhao.setTaisanid(this.Id);
					khauhao.setThang(formatter.format(month));
					khauhao.setKhauhaodukien(formatter.format(khdkVal[i]));
					khauhao.setKhauhaothucte("0");
					khauhao.setKhauhaoluykedukien(formatter.format(lkdkVal[i]));
					khauhao.setKhauhaoluykethucte(formatter.format(luykebandau));
					khauhao.setGiatriconlaidukien(formatter.format(gtdkVal[i]));
					khauhao.setGiatriconlaithucte(formatter
							.format(conlaibandau));
					khauhaodukienList.add(khauhao);
				} else {
					IKhauHaoDuKien khauhao = new KhauHaoDuKien();
					khauhao.setTaisanid(this.Id);
					khauhao.setThang(formatter.format(month));
					khauhao.setKhauhaodukien(formatter.format(khdkVal[i]));
					khauhao.setKhauhaothucte("0");
					khauhao.setKhauhaoluykedukien(formatter.format(lkdkVal[i]));
					khauhao.setKhauhaoluykethucte(formatter.format(luykebandau));
					khauhao.setGiatriconlaidukien(formatter.format(gtdkVal[i]));
					khauhao.setGiatriconlaithucte(formatter
							.format(conlaibandau));
					khauhaodukienList.add(khauhao);
				}
				// System.out.println("khauhao_update " + month + ": " + query);
				month++;
			}

		} catch (Exception e) {
			System.out.println("khauhao_update: " + e.toString());
			return false;
		}
		return true;
	}

	public void DBClose() {
		try {
			if (TsList != null) {
				TsList.close();
			}
			if (CdList != null) {
				CdList.close();
			}
			if (NtsList != null) {
				NtsList.close();
			}
			if (this.RsTtcp != null) {
				this.RsTtcp.close();
			}
			if (this.DvtList != null) {
				this.DvtList.close();
			}
			if (this.PPKHrS != null) {
				this.PPKHrS.close();
			}
			if (this.NsList != null) {
				this.NsList.close();
			}
			if (this.LtsList != null) {
				this.LtsList.close();
			}
			if (this.ttcpRs != null) {
				this.ttcpRs.close();
			}
			if (this.cdtsRs != null) {
				this.cdtsRs.close();
			}
			if (this.lnsRs != null) {
				this.lnsRs.close();
			}

		} catch (Exception er) {
			er.printStackTrace();
		} finally {
			if (db != null) {
				db.shutDown();
			}
		}
	}

	public ResultSet getRsLapNganSach() {

		return this.lnsRs;
	}

	public void setRsLapNganSach(ResultSet rsLns) {

		this.lnsRs = rsLns;
	}

	public String getLapngansachId() {

		return this.lnsId;
	}

	public void setLapngansachId(String lnsId) {

		this.lnsId = lnsId;
	}

	public int getCurrentPage() {

		return this.currentPages;
	}

	public void setCurrentPage(int current) {
		this.currentPages = current;

	}

	public int[] getListPages() {

		return this.listPages;
	}

	public void setListPages(int[] listPages) {
		this.listPages = listPages;

	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public void setTtcp(String Ttcp) {
		this.ttcp = Ttcp;
	}

	public ResultSet getThanhphan_TSDD() {
		String query = "SELECT * FROM ( \n "
				+ "	SELECT  2 AS STT, SP.MA, SP.TEN, SUM(CK_SP.SOLUONGXUAT) AS SOLUONG, \n "
				+ "	("
				+ "		SELECT SUM(CO) "
				+ "   	FROM ERP_PHATSINHKETOAN WHERE SOCHUNGTU = CK.PK_SEQ AND LOAICHUNGTU = N'Chuyển kho' AND MADOITUONG = SP.PK_SEQ"
				+ " 	) AS TONGTIEN, \n "
				+ "	N'Xuất vật tư cho tài sản xây dựng dở dang' AS GHICHU \n "
				+ "	FROM ERP_YEUCAUCHUYENKHO YCCK \n "
				+ "	INNER JOIN ERP_TAISANCODINH TSCD ON TSCD.PK_SEQ = YCCK.TSDD_FK \n "
				+ "	INNER JOIN ERP_CHUYENKHO CK ON CK.YEUCAUCHUYENKHO_FK = YCCK.PK_SEQ \n "
				+ "	INNER JOIN ERP_CHUYENKHO_SANPHAM CK_SP ON CK_SP.CHUYENKHO_FK = CK.PK_SEQ \n "
				+ "	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = CK_SP.SANPHAM_FK \n "
				+ "	WHERE CK.TRANGTHAI = 3 AND TSCD.PK_SEQ = "
				+ this.Id
				+ " \n "
				+ "	GROUP BY SP.MA, SP.TEN, SP.PK_SEQ, CK.PK_SEQ \n "
				+

				"	UNION ALL \n "
				+ "	SELECT 1 AS STT, TSCD.MA, TSCD.DIENGIAI, SUM(NH_SP.SOLUONGNHAN) AS SOLUONG, "
				+ "	SUM(NH_SP.SOLUONGNHAN*NH_SP.DONGIAVIET) AS TONGTIEN,  \n "
				+ "	N'Mua tài sản xây dựng dở dang' AS GHICHU \n "
				+ "	FROM ERP_NHANHANG_SANPHAM NH_SP \n "
				+ "	INNER JOIN ERP_TAISANCODINH TSCD ON TSCD.PK_SEQ = NH_SP.TAISAN_FK \n "
				+ "	WHERE TAISAN_FK =  " + this.Id + " \n "
				+ "	GROUP BY TSCD.MA, TSCD.DIENGIAI \n "
				+ ")DATA ORDER BY DATA.STT, DATA.MA \n ";
		return this.db.get(query);

	}

	public ResultSet getDieuChinhNguyenGiaRs() {
		return dieuChinhNguyenGiaRs;
	}

	public void setDieuChinhNguyenGiaRs(ResultSet dieuChinhNguyenGiaRs) {
		this.dieuChinhNguyenGiaRs = dieuChinhNguyenGiaRs;
	}

	public String getNgayghitang() {
		return ngayghitang;
	}

	public void setNgayghitang(String ngayghitang) {
		this.ngayghitang = ngayghitang;
	}

	public List<IKhauHaoDuKien> getKhauhaodukienList() {
		return khauhaodukienList;
	}

	public void setKhauhaodukienList(List<IKhauHaoDuKien> khauhaodukienList) {
		this.khauhaodukienList = khauhaodukienList;
	}

	public ResultSet getDvthList() {
		return dvthList;
	}

	public void setDvthList(ResultSet dvthList) {
		this.dvthList = dvthList;
	}

	public String getDvthId() {
		return dvthId;
	}

	public void setDvthId(String dvthId) {
		this.dvthId = dvthId;
	}

	public ResultSet getDieuChuyenTaiSanRs() {
		return dieuChuyenTaiSanRs;
	}

	public void setDieuChuyenTaiSanRs(ResultSet dieuChuyenTaiSanRs) {
		this.dieuChuyenTaiSanRs = dieuChuyenTaiSanRs;
	}

	public List<IPhanBo> getPhanBoList() {
		return phanBoList;
	}

	public void setPhanBoList(List<IPhanBo> phanBoList) {
		this.phanBoList = phanBoList;
	}

}