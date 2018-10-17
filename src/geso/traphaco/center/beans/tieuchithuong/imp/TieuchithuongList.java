package geso.traphaco.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.tieuchithuong.*;
import geso.traphaco.center.db.sql.dbutils;

public class TieuchithuongList implements ITieuchithuongList {

	public String dvkdId;
	public ResultSet dvkd;

	public String kbhId;
	public ResultSet kbh;

	public String msg;

	public String thang;
	public String nam;

	public String userId;

	public String loai;
	public String htbh;

	ResultSet tct;

	public dbutils db;

	public TieuchithuongList() {
		this.dvkdId = "";
		this.kbhId = "";
		this.msg = "";
		this.thang = "";
		this.nam = "";
		this.loai = "1";
		this.htbh = "100000";
		this.db = new dbutils();
	}

	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}

	public String getdvkdId() {
		return this.dvkdId;
	}

	public void setDvkd(ResultSet dvkd) {
		this.dvkd = dvkd;
	}

	public ResultSet getdvkd() {
		return this.dvkd;
	}

	public void setKbhId(String kbhId) {
		this.kbhId = kbhId;
	}

	public String getkbhId() {
		return this.kbhId;
	}

	public void setKbh(ResultSet kbh) {
		this.kbh = kbh;
	}

	public ResultSet getKbh() {
		return this.kbh;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMonth(String month) {
		this.thang = month;
	}

	public String getMonth() {
		return this.thang;
	}

	public void setYear(String year) {
		this.nam = year;
	}

	public String getYear() {
		return this.nam;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getLoai() {
		return this.loai;
	}

	public void setTct(ResultSet tct) {
		this.tct = tct;
	}

	public ResultSet getTct() {
		return this.tct;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd: hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void init() {
		String query = "SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DONVIKINHDOANH WHERE PK_SEQ IN (100024)";
		this.dvkd = this.db.get(query);

		query = "SELECT PK_SEQ AS KBHID, TEN AS KBH FROM KENHBANHANG WHERE PK_SEQ IN (100021, 100025)";
		this.kbh = this.db.get(query);

		query = "SELECT TCT.PK_SEQ, TCT.DIENGIAI, KBH.TEN AS KBH, "
				+ "DVKD.DONVIKINHDOANH AS DVKD, TCT.THANG, TCT.NAM, "
				+ "NV1.TEN AS NGUOITAO, "
				+ "NV2.TEN AS NGUOISUA,"
				+ "TCT.NGAYTAO,"
				+ "TCT.NGAYSUA,"
				+ "TCT.TRANGTHAI "
				+ "FROM TIEUCHITINHTHUONG TCT "
				+ "INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = TCT.KBH_FK "
				+ "INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = TCT.DVKD_FK "
				+ "INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TCT.NGUOITAO "
				+ "INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TCT.NGUOISUA ";

		String condition = " WHERE TCT.LOAI ='" + this.loai + "' and tct.hethongbh_fk = '"+ this.htbh +"' ";

		if (this.kbhId.length() > 0) {
			condition = condition + " AND TCT.KBH_FK='" + this.kbhId + "' ";
		}

		if (this.dvkdId.length() > 0) {
			condition = condition + " AND TCT.DVKD_FK='" + this.dvkdId + "' ";
		}

		if (this.thang.length() > 0) {
			condition = condition + " AND TCT.THANG ='" + this.thang + "' ";
		}

		if (this.nam.length() > 0) {
			condition = condition + " AND TCT.NAM ='" + this.nam + "' ";
		}

		query = query + condition;

		System.out.println(query);
		this.tct = this.db.get(query);
	}

	public void closeDB() {
		if (this.db != null)
			this.db.shutDown();
	}

	@Override
	public void sethethongBH(String htbh) {
		// TODO Auto-generated method stub
		this.htbh = htbh;
	}

	@Override
	public String gethethongBH() {
		// TODO Auto-generated method stub
		return this.htbh;
	}

}
