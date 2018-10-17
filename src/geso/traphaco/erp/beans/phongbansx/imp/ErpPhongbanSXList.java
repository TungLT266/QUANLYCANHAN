package geso.traphaco.erp.beans.phongbansx.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.phongbansx.IErpPhongbanSXList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpPhongbanSXList implements IErpPhongbanSXList {
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai;
	String msg;
	ResultSet phongbanRs;

	dbutils db;

	public ErpPhongbanSXList() {
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";

		this.msg = "";

		this.db = new dbutils();
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMa() {
		return this.ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getDiengiai() {
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void init(String query) {
		String sql = "";

		if (query.length() > 0)
			sql = query;
		else {
			sql = "select a.pk_seq, a.ma, a.ten, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    "
					+ "from Erp_phongbansx a inner join NhanVien b on a.nguoitao = b.pk_seq    "
					+ "inner join nhanvien c on a.nguoisua = c.pk_seq  "
					+ " where 1 = 1";
			/*
			 * if(this.nppId.length()>0){ sql += " and a.npp_fk ="+this.nppId; }
			 */
			if (this.congtyId.length() > 0) {
				sql += " and a.congty_fk = " + this.congtyId;
			}
			sql += " order by a.pk_seq desc";
		}

		System.out.println("__Nha may : " + sql);
		this.phongbanRs = db.get(sql);
	}

	public void DbClose() {
		try {
			if (this.phongbanRs != null)
				this.phongbanRs.close();
			this.db.shutDown();
		} catch (SQLException e) {
		}
	}

	public ResultSet getPhongbanRs() {
		return this.phongbanRs;
	}

	public void setPhongbanRs(ResultSet phongbanRs) {
		this.phongbanRs = phongbanRs;
	}

	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
}
