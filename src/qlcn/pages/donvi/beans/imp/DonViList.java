package qlcn.pages.donvi.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.donvi.beans.IDonViList;
import db.Dbutils;

public class DonViList extends Phan_Trang implements IDonViList {
	private String userId;
	private String ID;
	private String ten;
	private String diengiai;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet DonviRs;
	
	private Dbutils db;
	private Utility util;
	
	public DonViList() {
		this.ID = "";
		this.ten = "";
		this.trangthai = "";
		this.diengiai = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		try {
			String query = "";
			if(this.userId.equals("100000")){
				query = "select ID, TEN, DIENGIAI, TRANGTHAI, NGAYTAO, NGAYSUA from DONVI where ID > 0";
			} else {
				query = "select ID, TEN, DIENGIAI, TRANGTHAI, NGAYTAO, NGAYSUA from DONVI where USERID = " + this.userId;
			}
			
			if(this.ID.trim().length() > 0) {
				query += " and ID like '%" + this.ID.trim() + "%'";
			}
			
			if(this.ten.trim().length() > 0) {
				query += " and dbo.ftBoDau(TEN) like '%" + this.util.replaceAEIOU(this.ten.trim()) + "%'";
			}
			
			if(this.diengiai.trim().length() > 0) {
				query += " and dbo.ftBoDau(DIENGIAI) like '%" + this.util.replaceAEIOU(this.diengiai.trim()) + "%'";
			}
			
			if(this.trangthai.length() > 0) {
				query += " and TRANGTHAI = " + this.trangthai;
			}
			
			System.out.println(query);
			this.DonviRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
		
			String query = "update DONVI set trangthai = 2 where ID = " + id;
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể xóa DONVI: " + query;
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
				rs.close();
				
				query = "delete DONVI where trangthai = 2" + queryUser;
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database DONVI: " + query;
		    		db.getConnection().rollback();
		    		return;
		    	}
			} else {
				this.msg = "Mã PIN không đúng.";
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
	
	public void DBClose() {
		try {
			if (this.DonviRs != null)
				this.DonviRs.close();
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

	public String getSoItems() {
		return soItems;
	}

	public void setSoItems(String soItems) {
		this.soItems = soItems;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public ResultSet getDonviRs() {
		return DonviRs;
	}

	public void setDonviRs(ResultSet donviRs) {
		DonviRs = donviRs;
	}
}
