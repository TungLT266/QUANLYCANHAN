package geso.traphaco.erp.beans.tieuchikiemnghiem.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tieuchikiemnghiem.IErpTieuChiKiemNghiem;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpTieuChiKiemNghiem implements IErpTieuChiKiemNghiem {
	private String userId;
	private String congtyId;
	private String id;
	private String ma;
	private String ten;
	private String trangthai;
	private String msg;
	
	ResultSet tieuchiKNRs;
	dbutils db;
	
	public ErpTieuChiKiemNghiem() {
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select tckn.pk_seq, tckn.ma, tckn.ten, tckn.trangthai, nvt.TEN as nguoitao, nvs.TEN as nguoisua, tckn.ngaytao, tckn.ngaysua"
				+ " from ERP_TIEUCHIKIEMNGHIEM  tckn inner join nhanvien nvt on nvt.PK_SEQ = tckn.nguoitao"
				+ " inner join nhanvien nvs on nvs.PK_SEQ = tckn.nguoisua where tckn.congty_fk = " + this.congtyId;
		
		if(this.ma.length() > 0){
			query += " and tckn.ma like '%" + this.ma + "%'";
		}
		
		if(this.ten.length() > 0){
			Utility util = new Utility();
			query += " and dbo.ftBoDau(tckn.ten) like N'%" + util.replaceAEIOU(this.ten) + "%'";
		}
		
		if(this.trangthai.length() > 0){
			query += " and tckn.trangthai = '" + this.trangthai + "'";
		}
		
		System.out.println("tiofjiodj"+query);
		
		this.tieuchiKNRs = this.db.get(query);
	}
	
	public void initCapnhat(){
		String query = "select ma,ten,trangthai from ERP_TIEUCHIKIEMNGHIEM where pk_seq = " + this.id;
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try {
				rs.next();
				this.ma = rs.getString("ma");
				this.ten = rs.getString("ten");
				this.trangthai = rs.getString("trangthai");
				
				rs.close();
			} catch (SQLException e) {}
		}
		
	}
	
	public boolean CheckUnique() {
		try{
			String query = "select count(*) as count from ERP_TIEUCHIKIEMNGHIEM where MA='" + this.ma + "'";
			if (this.id.length() > 0)
				query += " and PK_SEQ !='" + this.id + "'";
			
			ResultSet rs = this.db.get(query);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			if (count > 0)
				return false;
			
			return true;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean create() {
		try {
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into ERP_TIEUCHIKIEMNGHIEM(ma,ten,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
					+ " values('"+this.ma+"',N'"+this.ten+"','"+this.trangthai+"','"+this.userId+"','"+this.getDateTime()+"',"
					+ "'"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_TIEUCHIKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean update() {
		try {
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_TIEUCHIKIEMNGHIEM set ma='"+this.ma+"',ten=N'"+this.ten+"',trangthai='"+this.trangthai+"',"
					+ "nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"' where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_TIEUCHIKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public void delete(){
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_TIEUCHIKIEMNGHIEM set trangthai = '2' where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try {
			if(this.tieuchiKNRs != null)
				this.tieuchiKNRs.close();
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

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public ResultSet getTieuchiKNRs() {
		return tieuchiKNRs;
	}
	public void setTieuchiKNRs(ResultSet tieuchiKNRs) {
		this.tieuchiKNRs = tieuchiKNRs;
	}
}
