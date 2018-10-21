package qlcn.pages.taikhoan.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.Dbutils;
import qlcn.pages.taikhoan.beans.ITaiKhoan;

public class TaiKhoan implements ITaiKhoan {
	private String userId;
	private String ID;
	private String ten;
	private String sotien;
	private String donvi;
//	private String loai;
	private String trangthai;
	private String msg;
	
	private ResultSet DonviRs;
	
	private Dbutils db;
	
	public TaiKhoan() {
		this.ID = "";
		this.ten = "";
		this.sotien = "0";
		this.donvi = "";
//		this.loai = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new Dbutils();
	}
	
	public void init() {
		NumberFormat formatter = new DecimalFormat("#,###,###.######");
		
		String query = "select TEN, SOTIEN, DONVI_FK, TRANGTHAI from TAIKHOAN where ID = " + this.ID;
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.ten = rs.getString("TEN");
			this.sotien = formatter.format(rs.getDouble("SOTIEN"));
			this.donvi = rs.getString("DONVI_FK");
//			this.loai = rs.getString("LOAI");
			this.trangthai = rs.getString("TRANGTHAI");
			rs.close();
		} catch (Exception e) {}
		
		createRS();
	}
	
	public void createRS() {
		String query = "select ID, ten from DONVI where trangthai = 1";
		this.DonviRs = this.db.get(query);
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String sotien = this.sotien.replaceAll("[, ]", "");
			String query = "insert into TAIKHOAN(TEN, SOTIEN, DONVI_FK, trangthai, nguoitao, ngaytao, nguoisua, ngaysua)"
					+ "\n values(N'"+this.ten+"',"+sotien+","+this.donvi+","+this.trangthai+","+this.userId+",'"+this.getDateTime()+"',"+this.userId+",'"+this.getDateTime()+"')";
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới TAIKHOAN: " + query;
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
			
			String sotien = this.sotien.replaceAll("[, ]", "");
			String query = "update TAIKHOAN set TEN=N'"+this.ten+"', sotien="+sotien+", donvi_fk="+this.donvi+", trangthai="+this.trangthai+","
					+ "\n nguoisua="+this.userId+", ngaysua='"+this.getDateTime()+"' where ID = " + this.ID;
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
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
			if(this.DonviRs != null)
				this.DonviRs.close();
			if(this.db != null)
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

	public String getSotien() {
		return sotien;
	}

	public void setSotien(String sotien) {
		this.sotien = sotien;
	}

	public String getDonvi() {
		return donvi;
	}

	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}

	public ResultSet getDonviRs() {
		return DonviRs;
	}

	public void setDonviRs(ResultSet donviRs) {
		DonviRs = donviRs;
	}

//	public String getLoai() {
//		return loai;
//	}
//
//	public void setLoai(String loai) {
//		this.loai = loai;
//	}
}
