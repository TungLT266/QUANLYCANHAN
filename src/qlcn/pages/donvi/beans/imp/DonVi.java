package qlcn.pages.donvi.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import qlcn.pages.donvi.beans.IDonVi;
import db.Dbutils;

public class DonVi implements IDonVi {
	private String userId;
	private String ID;
	private String ten;
	private String diengiai;
	private String trangthai;
	private String msg;
	
	private Dbutils db;
//	private Utility util;
	
	public DonVi() {
		this.ID = "";
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		try {
			String query = "select TEN, DIENGIAI, TRANGTHAI from DONVI where ID = " + this.ID;
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
			
			rs.next();
			this.ten = rs.getString("TEN");
			this.diengiai = rs.getString("DIENGIAI");
			this.trangthai = rs.getString("TRANGTHAI");
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into DONVI(TEN, diengiai, trangthai, ngaytao, ngaysua, USERID)"
					+ "\n values(N'"+this.ten+"',N'"+this.diengiai+"',"+this.trangthai+",'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+")";
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới DONVI: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean update() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update DONVI set TEN=N'"+this.ten+"',diengiai=N'"+this.diengiai+"',trangthai="+this.trangthai+",ngaysua='"+this.getDateTime()+"' where ID = " + this.ID;
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật DONVI: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return false;
		}
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
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	
	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}
}
