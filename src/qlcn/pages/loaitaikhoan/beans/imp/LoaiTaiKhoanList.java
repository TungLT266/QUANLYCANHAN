package qlcn.pages.loaitaikhoan.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Dbutils;
import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.loaitaikhoan.beans.ILoaiTaiKhoanList;

public class LoaiTaiKhoanList extends Phan_Trang implements ILoaiTaiKhoanList {
	private String userId;
	private String ID;
	private String ten;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet loaitaikhoanRs;
	
	private Dbutils db;
	private Utility util;
	
	public LoaiTaiKhoanList() {
		this.ID = "";
		this.ten = "";
		this.trangthai = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		try {
			String queryUser = "";
			if(!this.userId.equals("100000")){
				queryUser = " and ltk.USERID = " + this.userId;
			}
			
			String query = "select ltk.ID, ltk.TEN, ltk.TRANGTHAI, ltk.NGAYTAO, ltk.NGAYSUA from LOAITAIKHOAN ltk where ltk.ID > 0" + queryUser;
			
			if(this.ID.trim().length() > 0) {
				query += " and ltk.ID like '%" + this.ID.trim() + "%'";
			}
			
			if(this.ten.trim().length() > 0) {
				query += " and dbo.ftBoDau(ltk.TEN) like '%" + this.util.replaceAEIOU(this.ten.trim()) + "%'";
			}
			
			if(this.trangthai.length() > 0) {
				query += " and ltk.TRANGTHAI = " + this.trangthai;
			}
			
			System.out.println("init: "+query);
			this.loaitaikhoanRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
		
			String query = "update LOAITAIKHOAN set trangthai = 2 where ID = " + id;
			if(this.db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể xóa LOAITAIKHOAN: " + query;
	    		db.getConnection().rollback();
	    		return;
	    	}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
		}
	}
	
	public void deleteDB(String pinUser) {
		try {
			String queryUser = "";
			if(!this.userId.equals("100000")){
				queryUser = " and USERID = " + this.userId;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				query = "delete LOAITAIKHOAN where trangthai = 2" + queryUser;
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database LOAITAIKHOAN: " + query;
		    		db.getConnection().rollback();
		    		return;
		    	}
			} else {
				this.msg = "Mã PIN không đúng.";
			}
			rs.close();
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
		}
	}
	
	public void DBClose() {
		try {
			if (this.loaitaikhoanRs != null)
				this.loaitaikhoanRs.close();
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

	public ResultSet getLoaitaikhoanRs() {
		return loaitaikhoanRs;
	}

	public void setLoaitaikhoanRs(ResultSet loaitaikhoanRs) {
		this.loaitaikhoanRs = loaitaikhoanRs;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getSoItems() {
		return soItems;
	}

	public void setSoItems(String soItems) {
		this.soItems = soItems;
	}
	
}
