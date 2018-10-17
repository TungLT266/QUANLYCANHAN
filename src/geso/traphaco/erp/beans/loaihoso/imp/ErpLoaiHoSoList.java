package geso.traphaco.erp.beans.loaihoso.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSoList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpLoaiHoSoList implements IErpLoaiHoSoList {
	private String userId;
	private String congtyId;
	private String maLoaihoso;
	private String maBieumau;
	private String diengiai;
	private String loaimauin;
	private String trangthai;
	private String msg;
	
	ResultSet LoaihosoRs;
	dbutils db;
	
	public ErpLoaiHoSoList() {
		this.maLoaihoso = "";
		this.maBieumau = "";
		this.diengiai = "";
		this.loaimauin = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select lhs.PK_SEQ,lhs.MA,lhs.MABIEUMAU,lhs.TEN,lhs.loaimauin,lhs.TRANGTHAI,nvt.TEN as nguoitao,lhs.NGAYTAO,nvs.TEN as nguoisua,lhs.NGAYSUA"
				+ " from ERP_LOAIHOSO lhs inner join NHANVIEN nvt on nvt.PK_SEQ=lhs.NGUOITAO"
				+ " inner join NHANVIEN nvs on nvs.PK_SEQ=lhs.NGUOISUA where lhs.CONGTY_FK = " + this.congtyId;
		
		if(this.maLoaihoso.length() > 0){
			query += " and lhs.MA like '%" + this.maLoaihoso + "%'";
		}
		
		if(this.maBieumau.length() > 0){
			query += " and lhs.MABIEUMAU like '%" + this.maBieumau + "%'";
		}
		
		if(this.diengiai.length() > 0){
			Utility util = new Utility();
			query += " and dbo.ftBoDau(lhs.TEN) like N'%" + util.replaceAEIOU(this.diengiai) + "%'";
		}
		
		if(this.loaimauin.length() > 0){
			query += " and lhs.loaimauin = '" + this.loaimauin + "'";
		}
		
		if(this.trangthai.length() > 0){
			query += " and lhs.trangthai = '" + this.trangthai + "'";
		}
		
		query += " order by lhs.PK_SEQ desc";
		
		this.LoaihosoRs = this.db.get(query);
	}
	
	public void delete(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_LOAIHOSO set trangthai = '2' where pk_seq = " + id;
			
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {}
	}
	
	public void DBClose(){
		try {
			if(this.LoaihosoRs != null)
				this.LoaihosoRs.close();
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

	public ResultSet getLoaihosoRs() {
		return LoaihosoRs;
	}
	public void setLoaihosoRs(ResultSet LoaihosoRs) {
		this.LoaihosoRs = LoaihosoRs;
	}

	public String getMaLoaihoso() {
		return maLoaihoso;
	}

	public void setMaLoaihoso(String maLoaihoso) {
		this.maLoaihoso = maLoaihoso;
	}

	public String getMaBieumau() {
		return maBieumau;
	}

	public void setMaBieumau(String maBieumau) {
		this.maBieumau = maBieumau;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getLoaimauin() {
		return loaimauin;
	}
	public void setLoaimauin(String loaimauin) {
		this.loaimauin = loaimauin;
	}
}
