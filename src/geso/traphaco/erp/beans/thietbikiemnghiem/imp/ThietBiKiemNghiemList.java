package geso.traphaco.erp.beans.thietbikiemnghiem.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thietbikiemnghiem.IThietBiKiemNghiemList;
import geso.traphaco.erp.db.sql.dbutils;

public class ThietBiKiemNghiemList implements IThietBiKiemNghiemList {
	private String userId;
	private String congtyId;
	private String ma;
	private String ten;
	private String ghichu;
	private String trangthai;
	private String msg;
	
	private ResultSet ThietbiKNRs;
	dbutils db;
	
	public ThietBiKiemNghiemList() {
		this.ma = "";
		this.ten = "";
		this.ghichu = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		Utility util = new Utility();
		String query = "select tbkn.PK_SEQ, tbkn.MA,tbkn.TEN, tbkn.GHICHU,tbkn.TRANGTHAI,nvt.TEN as nguoitao,tbkn.NGAYTAO,nvs.TEN as nguoisua,tbkn.NGAYSUA"
				+ " from ERP_THIETBIKIEMNGHIEM tbkn inner join NHANVIEN nvt on nvt.PK_SEQ=tbkn.NGUOITAO"
				+ " inner join NHANVIEN nvs on nvs.PK_SEQ=tbkn.NGUOISUA where tbkn.CONGTY_FK = " + this.congtyId;
		
		if(this.ma.length() > 0){
			query += " and tbkn.MA like '%" + this.ma + "%'";
		}
		
		if(this.ten.length() > 0){
			query += " and dbo.ftBoDau(tbkn.ten) like '%" + util.replaceAEIOU(this.ten) + "%'";
		}
		
		if(this.ghichu.length() > 0){
			query += " and dbo.ftBoDau(tbkn.GHICHU) like N'%" + util.replaceAEIOU(this.ghichu) + "%'";
		}
		
		if(this.trangthai.length() > 0){
			query += " and tbkn.trangthai = '" + this.trangthai + "'";
		}
		
		query += " order by tbkn.PK_SEQ desc";
		
		this.ThietbiKNRs = this.db.get(query);
	}
	
	public void delete(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_THIETBIKIEMNGHIEM set trangthai = '2' where pk_seq = " + id;
			
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {}
	}
	
	public void DBClose(){
		try {
			if(this.ThietbiKNRs != null)
				this.ThietbiKNRs.close();
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

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public ResultSet getThietbiKNRs() {
		return ThietbiKNRs;
	}

	public void setThietbiKNRs(ResultSet thietbiKNRs) {
		ThietbiKNRs = thietbiKNRs;
	}
}
