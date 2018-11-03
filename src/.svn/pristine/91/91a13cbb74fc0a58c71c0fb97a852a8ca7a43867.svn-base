package qlcn.pages.taikhoanthanhtoan.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import db.Dbutils;
import qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToan;

public class TaiKhoanThanhToan implements ITaiKhoanThanhToan {
	private String userId;
	private String ID;
	private String loai;
	private String taikhoan;
	private String ten;
	private String loaithe;
	private String sothe;
	private String mapin;
	private String tenchuthe;
	private String thanghieuluc;
	private String namhieuluc;
	private String thanghethan;
	private String namhethan;
	private String chuky;
	private String trangthai;
	private String msg;
	
	private ResultSet TaikhoanRs;
	
	private Dbutils db;
//	private Utility util;
	
	public TaiKhoanThanhToan() {
		this.ID = "";
		this.loai = "1";
		this.taikhoan = "";
		this.ten = "";
		this.loaithe = "";
		this.sothe = "";
		this.mapin = "";
		this.tenchuthe = "";
		this.thanghieuluc = "";
		this.namhieuluc = Calendar.getInstance().get(Calendar.YEAR) + "";
		this.thanghethan = "";
		this.namhethan = Calendar.getInstance().get(Calendar.YEAR) + "";
		this.chuky = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		String query = "select loai, taikhoan_fk, TEN, LOAITHE, SOTHE, MAPIN, TENCHUTHE, THOIGIANHIEULUC, THOIGIANHETHAN, CHUKY, TRANGTHAI from TAIKHOANTHANHTOAN where ID = " + this.ID;
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			
			this.loai = rs.getString("loai");
			this.taikhoan = rs.getString("taikhoan_fk");
			this.ten = rs.getString("TEN");
			this.loaithe = rs.getString("LOAITHE");
			this.sothe = rs.getString("SOTHE");
			this.mapin = rs.getString("MAPIN");
			this.tenchuthe = rs.getString("TENCHUTHE");
			this.thanghieuluc = rs.getString("THOIGIANHIEULUC").split("-")[0];
			this.namhieuluc = rs.getString("THOIGIANHIEULUC").split("-")[1];
			this.thanghethan = rs.getString("THOIGIANHETHAN").split("-")[0];
			this.namhethan = rs.getString("THOIGIANHETHAN").split("-")[1];
			this.chuky = rs.getString("CHUKY");
			this.trangthai = rs.getString("TRANGTHAI");
			rs.close();
		} catch (Exception e) {}
		
		createRS();
	}
	
	public void createRS() {
		String query = "select ID, ten from TAIKHOAN where trangthai = 1";
		this.TaikhoanRs = this.db.get(query);
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "";
			if(this.loai.equals("1")) {
				query = "insert into TAIKHOANTHANHTOAN(loai, taikhoan_fk, TEN, trangthai, ngaytao, ngaysua, USERID, loaithe, sothe, tenchuthe, mapin, thoigianhieuluc, thoigianhethan, chuky)"
						+ "\n values("+this.loai+","+this.taikhoan+",N'"+this.ten+"',"+this.trangthai+",'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+",0,'','','','','','')";
			} else {
				query = "insert into TAIKHOANTHANHTOAN(loai, taikhoan_fk, LOAITHE, SOTHE, TENCHUTHE, MAPIN, THOIGIANHIEULUC, THOIGIANHETHAN, CHUKY, trangthai, USERID, ngaytao, ngaysua, ten)"
						+ "\n values("+this.loai+","+this.taikhoan+","+this.loaithe+",'"+this.sothe+"','"+this.tenchuthe+"','"+this.mapin+"','"+this.thanghieuluc+"-"+this.namhieuluc+"',"
						+ "'"+this.thanghethan+"-"+this.namhethan+"','"+this.chuky+"',"+this.trangthai+","+this.userId+",'"+this.getDateTime()+"','"+this.getDateTime()+"','')";
			}
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới TAIKHOANTHANHTOAN: " + query;
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
			
			String query;
			if(this.loai.equals("1")) {
				query = "update TAIKHOANTHANHTOAN set loai="+this.loai+", taikhoan_fk="+this.taikhoan+", TEN=N'"+this.ten+"', trangthai="+this.trangthai+","
						+ " ngaysua='"+this.getDateTime()+"', LOAITHE=0, SOTHE='', MAPIN='', TENCHUTHE='', THOIGIANHIEULUC='', THOIGIANHETHAN='', CHUKY='' where ID=" + this.ID;
			} else {
				query = "update TAIKHOANTHANHTOAN set loai="+this.loai+", taikhoan="+this.taikhoan+", LOAITHE="+this.loaithe+", SOTHE='"+this.sothe+"', MAPIN='"+this.mapin+"',"
						+ " TENCHUTHE='"+this.tenchuthe+"', THOIGIANHIEULUC='"+this.thanghieuluc+"-"+this.namhieuluc+"',THOIGIANHETHAN='"+this.thanghethan+"-"+this.namhethan+"',"
						+ "CHUKY='"+this.chuky+"',trangthai="+this.trangthai+",ngaysua='"+this.getDateTime()+"',ten='' where ID = " + this.ID;
			}
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật TAIKHOANTHANHTOAN: " + query;
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
			if(this.TaikhoanRs != null)
				this.TaikhoanRs.close();
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

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public String getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(String taikhoan) {
		this.taikhoan = taikhoan;
	}

	public ResultSet getTaikhoanRs() {
		return TaikhoanRs;
	}

	public void setTaikhoanRs(ResultSet taikhoanRs) {
		TaikhoanRs = taikhoanRs;
	}

	public String getLoaithe() {
		return loaithe;
	}

	public void setLoaithe(String loaithe) {
		this.loaithe = loaithe;
	}

	public String getSothe() {
		return sothe;
	}

	public void setSothe(String sothe) {
		this.sothe = sothe;
	}

	public String getMapin() {
		return mapin;
	}

	public void setMapin(String mapin) {
		this.mapin = mapin;
	}

	public String getTenchuthe() {
		return tenchuthe;
	}

	public void setTenchuthe(String tenchuthe) {
		this.tenchuthe = tenchuthe;
	}

	public String getChuky() {
		return chuky;
	}

	public void setChuky(String chuky) {
		this.chuky = chuky;
	}

	public String getThanghieuluc() {
		return thanghieuluc;
	}

	public void setThanghieuluc(String thanghieuluc) {
		this.thanghieuluc = thanghieuluc;
	}

	public String getNamhieuluc() {
		return namhieuluc;
	}

	public void setNamhieuluc(String namhieuluc) {
		this.namhieuluc = namhieuluc;
	}

	public String getThanghethan() {
		return thanghethan;
	}

	public void setThanghethan(String thanghethan) {
		this.thanghethan = thanghethan;
	}

	public String getNamhethan() {
		return namhethan;
	}

	public void setNamhethan(String namhethan) {
		this.namhethan = namhethan;
	}
}
