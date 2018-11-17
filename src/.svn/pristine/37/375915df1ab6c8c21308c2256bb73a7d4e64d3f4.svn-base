package qlcn.pages.loaitaikhoan.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.Dbutils;
import qlcn.center.util.Utility;
import qlcn.pages.loaitaikhoan.beans.ILoaiTaiKhoan;

public class LoaiTaiKhoan implements ILoaiTaiKhoan {
	private String userId;
	private String ID;
	private String ten;
	private String trangthai;
	private String msg;
	
	private Dbutils db;
	private Utility util;
	
	public LoaiTaiKhoan() {
		this.ID = "";
		this.ten = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		String query = "select TEN, TRANGTHAI from LOAITAIKHOAN where ID = " + this.ID;
		System.out.println("init: "+query);
		
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.ten = rs.getString("TEN");
			this.trangthai = rs.getString("TRANGTHAI");
			rs.close();
		} catch (Exception e) {}
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into LOAITAIKHOAN(TEN, trangthai, ngaytao, ngaysua, USERID)"
					+ "\n values(N'"+this.ten+"',"+this.trangthai+",'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+")";
			System.out.println("create: "+query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới LOAITAIKHOAN: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
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
			db.getConnection().setAutoCommit(false);
			
			String query = "update LOAITAIKHOAN set TEN=N'"+this.ten+"',trangthai="+this.trangthai+",ngaysua='"+this.getDateTime()+"' where ID = " + this.ID;
			System.out.println("update: "+query);
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật LOAITAIKHOAN: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
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
	
	public void DBClose() {
		try {
			if (this.db != null)
				this.db.shutDown();
		} catch (Exception e) {}
	}
	
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getID() {
		return ID;
	}
	
	public void setID(String iD) {
		ID = iD;
	}
	
	public String getTen() {
		return ten;
	}
	
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
}
