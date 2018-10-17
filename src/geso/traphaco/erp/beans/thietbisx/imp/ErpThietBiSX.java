package geso.traphaco.erp.beans.thietbisx.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.erp.beans.thietbisx.IErpThietBiSX;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpThietBiSX implements IErpThietBiSX {
	private String userId;
	private String congtyId;
	private String ma;
	private String ten;
	private String tscdFk;
	private String trangthai; 
	private String msg;
	
	ResultSet RsTscd;
	ResultSet RsThietbi;
	dbutils db;
	
	public ErpThietBiSX() {
		this.ma = "";
		this.trangthai = "";
		this.ten = "";
		this.tscdFk = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
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
	public ResultSet getRsThietbi() {
		return RsThietbi;
	}
	public void setRsThietbi(ResultSet rsThietbi) {
		RsThietbi = rsThietbi;
	}
	
	public void init(String search){
		String query = "";
		
		if(search.length() > 0)
			query = search;
		else {
			query = "select tb.pk_seq, tb.ma, tb.ten, tb.trangthai, nvt.TEN as nguoitao, nvs.TEN as nguoisua, tb.ngaytao, tb.ngaysua,"
					+ " case when tb.loai_TSCD=1 and len(tb.tscd_fk)>0 then (tscd.ma+' - '+tscd.diengiai)"
					+ " when tb.loai_TSCD=0 and len(tb.tscd_fk)>0 then (ccdc.ma+' - '+ccdc.diengiai) else '' end as tscd"
					+ " from erp_thietbisx as tb"
					+ " left join ERP_TAISANCODINH tscd on tscd.pk_seq = tb.tscd_fk and tb.loai_TSCD = 1"
					+ " left join ERP_CONGCUDUNGCU ccdc on ccdc.pk_seq = tb.tscd_fk and tb.loai_TSCD = 0"
					+ " inner join NHANVIEN nvt on nvt.PK_SEQ = tb.nguoitao"
					+ " inner join NHANVIEN nvs on nvs.PK_SEQ = tb.nguoisua"
					+ " where 1 = 1";
			if(this.congtyId.length()>0){
				query += " and tb.congty_fk = " + this.congtyId;
			}
			query += " order by tb.pk_seq desc";
		}
		
		System.out.println("__Thiet bi: " + query);
		this.RsThietbi = db.get(query);
	}
	
	public void createRs() {
		String query = "select 'TSCD' + cast(pk_seq as varchar) as pk_seq, 'TSCD - ' + ma + ' - ' + diengiai as tscd"
				+ " from ERP_TAISANCODINH as a where trangthai=1 and congty_fk=" + this.congtyId
				+ " union all"
				+ " select 'CPTT' + cast(pk_seq as varchar) as pk_seq, 'CPTT - ' + ma + ' - ' + diengiai as tscd"
				+ " from ERP_CONGCUDUNGCU where TRANGTHAI=1  and CONGTY_FK="+this.congtyId;
		System.out.println(query);
		this.RsTscd = db.get(query);
	}
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "delete ERP_THIETBISX where pk_seq = '" + id + "'"
					+ " delete ERP_THIETBISX_THONGSO where THIETBI_FK = '" + id + "'";
			if(!db.update(query)) {
				this.msg = "Không thể xóa ERP_THIETBISX: " + query;
				db.getConnection().rollback();
				return;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
		}
	}
	
	public void DbClose() {
		try {
			if(this.RsTscd != null)
				this.RsTscd.close();
			if(this.RsThietbi != null)
				this.RsThietbi.close();
			this.db.shutDown();
		} catch (SQLException e) {}
	}

	public String getTscdFk() {
		return tscdFk;
	}

	public void setTscdFk(String tscdFk) {
		this.tscdFk = tscdFk;
	}

	public ResultSet getRsTscd() {
		return RsTscd;
	}

	public void setRsTscd(ResultSet rsTscd) {
		RsTscd = rsTscd;
	}
}
