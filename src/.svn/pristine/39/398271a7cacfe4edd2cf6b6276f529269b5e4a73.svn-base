package qlcn.pages.noidungthuchi.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.Dbutils;
import qlcn.pages.noidungthuchi.beans.INoiDungThuChi;

public class NoiDungThuChi implements INoiDungThuChi {
	private String userId;
	private String ID;
	private String loai;
	private String ten;
	private String diengiai;
	private String trangthai;
	private String msg;
	
	private Dbutils db;
//	private Utility util;
	
	public NoiDungThuChi() {
		this.ID = "";
		this.loai = "1";
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		String query = "select loai, TEN, diengiai, TRANGTHAI from NOIDUNGTHUCHI where ID = " + this.ID;
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.loai = rs.getString("loai");
			this.ten = rs.getString("TEN");
			this.diengiai = rs.getString("diengiai");
			this.trangthai = rs.getString("TRANGTHAI");
			rs.close();
		} catch (Exception e) {}
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into NOIDUNGTHUCHI(loai, TEN, DIENGIAI, trangthai, USERID, ngaytao, ngaysua)"
					+ "\n values("+this.loai+",N'"+this.ten+"',N'"+this.diengiai+"',"+this.trangthai+","+this.userId+",'"+this.getDateTime()+"','"+this.getDateTime()+"')";
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới NOIDUNGTHUCHI: " + query;
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
			
			String query = "update NOIDUNGTHUCHI set loai="+this.loai+", TEN=N'"+this.ten+"', diengiai=N'"+this.diengiai+"',"
					+ "\n trangthai="+this.trangthai+", ngaysua='"+this.getDateTime()+"' where ID = " + this.ID;
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật NOIDUNGTHUCHI: " + query;
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

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}
}
