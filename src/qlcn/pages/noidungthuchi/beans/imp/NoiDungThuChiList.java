package qlcn.pages.noidungthuchi.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Dbutils;
import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.noidungthuchi.beans.INoiDungThuChiList;

public class NoiDungThuChiList extends Phan_Trang implements INoiDungThuChiList {
	private String userId;
	private String ID;
	private String loai;
	private String ten;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet NoidungthuchiRs;
	
	private Dbutils db;
	private Utility util;
	
	public NoiDungThuChiList() {
		this.ID = "";
		this.loai = "";
		this.ten = "";
		this.trangthai = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		String queryUser = "";
		if(!this.userId.equals("100000")){
			queryUser = " and ndtc.USERID = " + this.userId;
		}
		
		String query = "select ndtc.ID, case when ndtc.loai=0 then 'Thu - Chi' when ndtc.loai=1 then 'Thu' else 'Chi' end as loai, ndtc.TEN, ndtc.TRANGTHAI, ndtc.NGAYTAO, ndtc.NGAYSUA from NOIDUNGTHUCHI ndtc where ndtc.ID > 0" + queryUser;
		
		if(this.ID.trim().length() > 0) {
			query += " and ndtc.ID like '%" + this.ID.trim() + "%'";
		}
		
		if(this.loai.length() > 0) {
			query += " and ndtc.loai = " + this.loai;
		}
		
		if(this.ten.trim().length() > 0) {
			query += " and dbo.ftBoDau(ndtc.TEN) like '%" + this.util.replaceAEIOU(this.ten.trim()) + "%'";
		}
		
		if(this.trangthai.length() > 0) {
			query += " and ndtc.TRANGTHAI = " + this.trangthai;
		}
		
		System.out.println(query);
		this.NoidungthuchiRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
	}
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
		
			String query = "update NOIDUNGTHUCHI set trangthai = 2 where ID = " + id;
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể xóa NOIDUNGTHUCHI: " + query;
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
			db.getConnection().setAutoCommit(false);
			
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				rs.close();
				
				query = "delete NOIDUNGTHUCHI where trangthai = 2";
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database NOIDUNGTHUCHI: " + query;
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
			if (this.NoidungthuchiRs != null)
				this.NoidungthuchiRs.close();
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

	public String getSoItems() {
		return soItems;
	}

	public void setSoItems(String soItems) {
		this.soItems = soItems;
	}

	public String getLoai() {
		return loai;
	}

	public void setLoai(String loai) {
		this.loai = loai;
	}

	public ResultSet getNoidungthuchiRs() {
		return NoidungthuchiRs;
	}

	public void setNoidungthuchiRs(ResultSet noidungthuchiRs) {
		NoidungthuchiRs = noidungthuchiRs;
	}
}
