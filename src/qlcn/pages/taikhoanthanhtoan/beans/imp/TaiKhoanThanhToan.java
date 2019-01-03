package qlcn.pages.taikhoanthanhtoan.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.Dbutils;
import qlcn.center.util.Utility;
import qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToan;

public class TaiKhoanThanhToan implements ITaiKhoanThanhToan {
	private String userId;
	private String action;
	private String ID;
	private String taikhoan;
	private String loaithe;
	private String sothe;
	private String mapin;
	private String isChangeMapin;
	private String tenchuthe;
	private String thanghieuluc;
	private String namhieuluc;
	private String thanghethan;
	private String namhethan;
	private String chuky;
	private String isChangeChuky;
	private String trangthai;
	private String msg;
	
	private ResultSet TaikhoanRs;
	
	private Dbutils db;
	private Utility util;
	
	public TaiKhoanThanhToan() {
		this.ID = "";
		this.action = "";
		this.taikhoan = "";
		this.loaithe = "";
		this.sothe = "";
		this.mapin = "";
		this.isChangeMapin = "";
		this.tenchuthe = "";
		this.thanghieuluc = Integer.parseInt(this.getDateTime().split("-")[1]) + "";
		this.namhieuluc = this.getDateTime().split("-")[0];
		this.thanghethan = Integer.parseInt(this.getDateTime().split("-")[1]) + "";
		this.namhethan = this.getDateTime().split("-")[0];
		this.chuky = "";
		this.isChangeChuky = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		try {
			String query = "select taikhoan_fk, LOAITHE, SOTHE, MAPIN, TENCHUTHE, THOIGIANHIEULUC, THOIGIANHETHAN, CHUKY, TRANGTHAI from TAIKHOANTHANHTOAN where ID = " + this.ID;
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
		
			rs.next();
			
			this.taikhoan = rs.getString("taikhoan_fk");
			this.loaithe = rs.getString("LOAITHE");
			this.sothe = rs.getString("SOTHE");
			this.tenchuthe = rs.getString("TENCHUTHE");
			this.thanghieuluc = rs.getString("THOIGIANHIEULUC").split("-")[0];
			this.namhieuluc = rs.getString("THOIGIANHIEULUC").split("-")[1];
			this.thanghethan = rs.getString("THOIGIANHETHAN").split("-")[0];
			this.namhethan = rs.getString("THOIGIANHETHAN").split("-")[1];
			this.trangthai = rs.getString("TRANGTHAI");
			
			if(this.action.equals("display")){
				this.mapin = rs.getString("MAPIN");
				this.chuky = rs.getString("CHUKY");
			}
			
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
			
			String query = "select ID, '['+cast(ID as varchar)+'] ' + ten as ten from TAIKHOAN where trangthai = 1 and istknganhang = 1" + queryUser;
			this.TaikhoanRs = this.db.get(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into TAIKHOANTHANHTOAN(taikhoan_fk, LOAITHE, SOTHE, TENCHUTHE, MAPIN, THOIGIANHIEULUC, THOIGIANHETHAN, CHUKY, trangthai, USERID, ngaytao, ngaysua)"
						+ "\n values("+this.taikhoan+","+this.loaithe+",'"+this.sothe+"','"+this.tenchuthe+"','"+this.mapin+"','"+this.thanghieuluc+"-"+this.namhieuluc+"',"
						+ "'"+this.thanghethan+"-"+this.namhethan+"','"+this.chuky+"',"+this.trangthai+","+this.userId+",'"+this.getDateTime()+"','"+this.getDateTime()+"')";
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới TAIKHOANTHANHTOAN: " + query;
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
			
			String query = "update TAIKHOANTHANHTOAN set"
						;
			if(this.isChangeMapin.equals("1")){
				query += " MAPIN='"+this.mapin+"',";
			}
			
			if(this.isChangeChuky.equals("1")){
				query += " CHUKY='"+this.chuky+"',";
			}
			
			query += " LOAITHE="+this.loaithe+", SOTHE='"+this.sothe+"', TENCHUTHE='"+this.tenchuthe+"', THOIGIANHIEULUC='"+this.thanghieuluc+"-"+this.namhieuluc+"',"
					+ "THOIGIANHETHAN='"+this.thanghethan+"-"+this.namhethan+"',trangthai="+this.trangthai+",ngaysua='"+this.getDateTime()+"' where ID = " + this.ID;
			
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật TAIKHOANTHANHTOAN: " + query;
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
	
	public String checkPinUser(String pinUser) {
		try {
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				rs.close();
				return "";
			} else {
				rs.close();
				return "Mã PIN không đúng.";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}
	}
	
	public void DBClose() {
		try {
			if(this.TaikhoanRs != null)
				this.TaikhoanRs.close();
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

	public String getIsChangeMapin() {
		return isChangeMapin;
	}

	public void setIsChangeMapin(String isChangeMapin) {
		this.isChangeMapin = isChangeMapin;
	}

	public String getIsChangeChuky() {
		return isChangeChuky;
	}

	public void setIsChangeChuky(String isChangeChuky) {
		this.isChangeChuky = isChangeChuky;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}
