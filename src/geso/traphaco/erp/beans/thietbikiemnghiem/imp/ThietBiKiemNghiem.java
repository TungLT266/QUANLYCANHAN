package geso.traphaco.erp.beans.thietbikiemnghiem.imp;

import geso.traphaco.erp.beans.thietbikiemnghiem.IThietBiKiemNghiem;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThietBiKiemNghiem implements IThietBiKiemNghiem {
	private String userId;
	private String congtyId;
	private String id;
	private String ma;
	private String ten;
	private String ghichu;
	private String trangthai;
	private String msg;
	
	dbutils db;
	
	public ThietBiKiemNghiem() {
		this.id = "";
		this.ma = "";
		this.ten = "";
		this.ghichu = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select MA,TEN,GHICHU,trangthai from ERP_THIETBIKIEMNGHIEM where pk_seq = " + this.id;
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try {
				rs.next();
				this.ma = rs.getString("MA");
				this.ten = rs.getString("TEN");
				this.ghichu = rs.getString("GHICHU");
				this.trangthai = rs.getString("trangthai");
				
				rs.close();
			} catch (SQLException e) {}
		}
	}
	
	public boolean CheckUnique() {
		try{
			String query = "select count(*) as count from ERP_THIETBIKIEMNGHIEM where MA='" + this.ma + "'";
			if (this.id.length() > 0)
				query += " and PK_SEQ != '" + this.id + "'";
			
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
			
			String query = "insert into ERP_THIETBIKIEMNGHIEM(ma,ten,ghichu,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
					+ " values('"+this.ma+"',N'"+this.ten+"',N'"+this.ghichu+"','"+this.trangthai+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_THIETBIKIEMNGHIEM: " + query;
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
			
			String query = "update ERP_THIETBIKIEMNGHIEM set ma='"+this.ma+"',ten=N'"+this.ten+"',ghichu=N'"+this.ghichu+"',"
					+ "trangthai='"+this.trangthai+"',nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"' where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_THIETBIKIEMNGHIEM: " + query;
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
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try {
			this.db.shutDown();
		} catch (Exception e) {}
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
}
