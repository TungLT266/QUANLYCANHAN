package geso.traphaco.erp.beans.tailieuphachethuoc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tailieuphachethuoc.IErpTaiLieuPhaCheThuocList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpTaiLieuPhaCheThuocList implements IErpTaiLieuPhaCheThuocList {
	private String userId;
	private String congtyId;
	private String ma;
	private String noidung;
	private String trangthai;
	private String msg;
	
	ResultSet TlpctRs;
	dbutils db;
	
	public ErpTaiLieuPhaCheThuocList() {
		this.ma = "";
		this.noidung = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select tlpct.pk_seq, tlpct.ma, tlpct.noidung, tlpct.trangthai, nvt.TEN as nguoitao, nvs.TEN as nguoisua, tlpct.ngaytao, tlpct.ngaysua"
				+ " from ERP_TAILIEUPHACHETHUOC tlpct inner join nhanvien nvt on nvt.PK_SEQ = tlpct.nguoitao"
				+ " inner join nhanvien nvs on nvs.PK_SEQ = tlpct.nguoisua where tlpct.congty_fk = " + this.congtyId;
		
		if(this.ma.length() > 0){
			query += " and tlpct.ma like '%" + this.ma + "%'";
		}
		
		if(this.noidung.length() > 0){
			Utility util = new Utility();
			query += " and dbo.ftBoDau(tlpct.noidung) like N'%" + util.replaceAEIOU(this.noidung) + "%'";
		}
		
		if(this.trangthai.length() > 0){
			query += " and tlpct.trangthai = '" + this.trangthai + "'";
		}
		
		this.TlpctRs = this.db.get(query);
	}
	
	public void delete(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_TAILIEUPHACHETHUOC set trangthai = '2' where pk_seq = " + id;
			
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {}
	}
	
	public void DBClose(){
		try {
			if(this.TlpctRs != null)
				this.TlpctRs.close();
			this.db.shutDown();
		} catch (SQLException e) {}
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getMa() {
		return ma;
	}
	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResultSet getTlpctRs() {
		return TlpctRs;
	}
	public void setTlpctRs(ResultSet TlpctRs) {
		this.TlpctRs = TlpctRs;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}
}
