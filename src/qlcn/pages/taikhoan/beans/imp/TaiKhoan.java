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
//	private String sotien;
	private String donvi;
	private String nganhang;
	private String isTknganhang;
	private String isTktindung;
	private String hanmuc;
	private String noTindung;
	private String trangthai;
	private String msg;
	
	private ResultSet DonviRs;
	
	private Dbutils db;
//	private Utility util;
	
	public TaiKhoan() {
		this.ID = "";
		this.ten = "";
//		this.sotien = "";
		this.donvi = "";
		this.nganhang = "";
		this.isTknganhang = "1";
		this.isTktindung = "0";
		this.hanmuc = "";
		this.noTindung = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###.##");
			
			String query = "select TEN, DONVI_FK, nganhang, istknganhang, istktindung, hanmuc, notindung, TRANGTHAI from TAIKHOAN where ID = " + this.ID;
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
		
			rs.next();
			this.ten = rs.getString("TEN");
//			this.sotien = formatter.format(rs.getDouble("SOTIEN"));
			this.donvi = rs.getString("DONVI_FK");
			this.nganhang = rs.getString("nganhang");
			this.isTknganhang = rs.getString("istknganhang");
			this.isTktindung = rs.getString("istktindung");
			this.hanmuc = formatter.format(rs.getDouble("hanmuc"));
			this.noTindung = formatter.format(rs.getDouble("notindung"));
			this.trangthai = rs.getString("TRANGTHAI");
			rs.close();
			
			createRS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createRS() {
		try {
			String queryUser = "";
			if(!this.userId.equals("100000")){
				queryUser = " and USERID = " + this.userId;
			}
			
			String query = "select ID, ten from DONVI where trangthai = 1" + queryUser;
			this.DonviRs = this.db.get(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			if(!this.isTknganhang.equals("1") && !this.isTktindung.equals("1")){
				this.nganhang = "";
			} else if(!this.isTktindung.equals("1")){
				this.hanmuc = "0";
				this.noTindung = "0";
			}
			
			String query = "insert into TAIKHOAN(TEN, DONVI_FK, nganhang, istknganhang, istktindung, hanmuc, notindung, trangthai, ngaytao, ngaysua, USERID)"
					+ "\n values(N'"+this.ten.trim()+"',"+this.donvi+",N'"+this.nganhang.trim()+"',"+this.isTknganhang+","+this.isTktindung+","
					+ (this.hanmuc.trim().length()==0 ? "0" : this.hanmuc.trim().replaceAll(",", ""))+","+(this.noTindung.trim().length()==0 ? "0" : this.noTindung.trim().replaceAll(",", ""))+","
					+ this.trangthai+",'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+")";
			System.out.println(query);
			
			if(!this.db.update(query)) {
				this.msg = "Không thể tạo mới TAIKHOAN: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "SELECT SCOPE_IDENTITY() AS ID";
			ResultSet rs = this.db.get(query);
			rs.next();
			String idCreate = rs.getString("ID");
			rs.close();
			
			// Lưu log tài khoản
			query = "insert into TAIKHOAN_LOG(ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,NGAY_LOG,CHUCNANG)"
					+ " select ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,GETDATE(),N'Tài khoản'"
					+ " from TAIKHOAN where ID = " + idCreate;
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể tạo mới TAIKHOAN_LOG: " + query;
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
			
			String query = "update TAIKHOAN set TEN=N'"+this.ten.trim()+"',nganhang=N'"+this.nganhang.trim()+"',"
					+ "hanmuc="+(this.hanmuc.trim().length()==0 ? "0" : this.hanmuc.trim().replaceAll("[, ]", ""))+","
					+ "trangthai="+this.trangthai+", ngaysua='"+this.getDateTime()+"' where ID = " + this.ID;
			System.out.println(query);
			
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "insert into TAIKHOAN_LOG(ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,NGAY_LOG,CHUCNANG)"
					+ " select ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,GETDATE(),N'Tài khoản'"
					+ " from TAIKHOAN where ID = " + this.ID;
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể tạo mới TAIKHOAN_LOG: " + query;
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
			if(this.DonviRs != null)
				this.DonviRs.close();
			if(this.db != null)
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

	public String getIsTknganhang() {
		return isTknganhang;
	}

	public void setIsTknganhang(String isTknganhang) {
		this.isTknganhang = isTknganhang;
	}

	public String getNganhang() {
		return nganhang;
	}

	public void setNganhang(String nganhang) {
		this.nganhang = nganhang;
	}

	public String getIsTktindung() {
		return isTktindung;
	}

	public void setIsTktindung(String isTktindung) {
		this.isTktindung = isTktindung;
	}

	public String getHanmuc() {
		return hanmuc;
	}

	public void setHanmuc(String hanmuc) {
		this.hanmuc = hanmuc;
	}

	public String getNoTindung() {
		return noTindung;
	}

	public void setNoTindung(String noTindung) {
		this.noTindung = noTindung;
	}
}
