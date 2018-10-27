package qlcn.pages.noidungthuchi.beans.imp;

import java.sql.ResultSet;

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
		String query = "select ndtc.ID, ndtc.loai, ndtc.TEN, ndtc.TRANGTHAI, ndtc.NGAYTAO, ndtc.NGAYSUA"
				+ "\n from NOIDUNGTHUCHI ndtc where ndtc.USERID = " + this.userId;
		
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
		String query = "update NOIDUNGTHUCHI set trangthai = 2 where ID = " + id;
		if(!this.db.update(query)){
    		this.msg = "Không thể xóa NOIDUNGTHUCHI: " + query;
    	}
	}
	
	public void deleteDB(String pinUser) {
		try {
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				query = "delete NOIDUNGTHUCHI where trangthai = 2";
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database NOIDUNGTHUCHI: " + query;
		    	}
			} else {
				this.msg = "Mã PIN không đúng.";
			}
			rs.close();
		} catch (Exception e) {
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
