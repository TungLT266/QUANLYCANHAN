package geso.traphaco.erp.beans.yeucaukythuat.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucaukythuat.IErpYeuCauKyThuatList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpYeuCauKyThuatList implements IErpYeuCauKyThuatList {
	private String userId;
	private String congtyId;
	private String ma;
	private String ten;
	private String trangthai;
	private String msg;
	
	ResultSet YeucauKTRs;
	dbutils db;
	
	public ErpYeuCauKyThuatList() {
		this.ma = "";
		this.ten = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select yckt.pk_seq, yckt.ma, yckt.ten, yckt.trangthai, nvt.TEN as nguoitao, nvs.TEN as nguoisua, yckt.ngaytao, yckt.ngaysua"
				+ " from ERP_YEUCAUKYTHUAT yckt inner join nhanvien nvt on nvt.PK_SEQ = yckt.nguoitao"
				+ " inner join nhanvien nvs on nvs.PK_SEQ = yckt.nguoisua where yckt.congty_fk = " + this.congtyId;
		
		if(this.ma.length() > 0){
			query += " and yckt.ma like '%" + this.ma + "%'";
		}
		
		if(this.ten.length() > 0){
			Utility util = new Utility();
			query += " and dbo.ftBoDau(yckt.ten) like N'%" + util.replaceAEIOU(this.ten) + "%'";
		}
		
		if(this.trangthai.length() > 0){
			query += " and yckt.trangthai = '" + this.trangthai + "'";
		}
		
		System.out.println("tiofjiodj"+query);
		
		this.YeucauKTRs = this.db.get(query);
	}
	
	public void delete(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_YEUCAUKYTHUAT set trangthai = '2' where pk_seq = " + id;
			
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {}
	}
	
	public void DBClose(){
		try {
			if(this.YeucauKTRs != null)
				this.YeucauKTRs.close();
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

	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
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

	public ResultSet getYeucauKTRs() {
		return YeucauKTRs;
	}
	public void setYeucauKTRs(ResultSet YeucauKTRs) {
		this.YeucauKTRs = YeucauKTRs;
	}
}
