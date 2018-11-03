package qlcn.pages.taikhoan.beans.imp;

import java.sql.ResultSet;

import db.Dbutils;
import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.taikhoan.beans.ITaiKhoanList;

public class TaiKhoanList extends Phan_Trang implements ITaiKhoanList {
	private String userId;
	private String ID;
	private String ten;
//	private String sotien;
//	private String loai;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet TaikhoanRs;
	
	private Dbutils db;
	private Utility util;
	
	public TaiKhoanList() {
		this.ID = "";
		this.ten = "";
//		this.loai = "";
		this.trangthai = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		String query = "";
		if(this.userId.equals("100000")){
			query = "select tk.ID, tk.TEN, tk.SOTIEN, dv.TEN as donvi, tk.TRANGTHAI, tk.NGAYTAO, tk.NGAYSUA"
					+ "\n from TAIKHOAN tk left join DONVI dv on dv.ID = tk.DONVI_FK where tk.ID > 0";
		} else {
			query = "select tk.ID, tk.TEN, tk.SOTIEN, dv.TEN as donvi, tk.TRANGTHAI, tk.NGAYTAO, tk.NGAYSUA"
					+ "\n from TAIKHOAN tk left join DONVI dv on dv.ID = tk.DONVI_FK where tk.USERID = " + this.userId;
		}
		
		if(this.ID.trim().length() > 0) {
			query += " and tk.ID like '%" + this.ID.trim() + "%'";
		}
		
		if(this.ten.trim().length() > 0) {
			query += " and dbo.ftBoDau(tk.TEN) like '%" + this.util.replaceAEIOU(this.ten.trim()) + "%'";
		}
		
//		if(this.loai.length() > 0) {
//			query += " and tk.loai = '" + this.loai + "'";
//		}
		
		if(this.trangthai.length() > 0) {
			query += " and tk.TRANGTHAI = '" + this.trangthai + "'";
		}
		
		System.out.println(query);
		
		this.TaikhoanRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
	}
	
	public void delete(String id) {
		String query = "update TAIKHOAN set trangthai = 2 where ID = " + id;
		if(!this.db.update(query)){
    		this.msg = "Không thể xóa TAIKHOAN: " + query;
    	}
	}
	
	public void deleteDB(String pinUser) {
		try {
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				query = "delete TAIKHOAN where trangthai = 2";
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database TAIKHOAN: " + query;
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
			if (this.TaikhoanRs != null)
				this.TaikhoanRs.close();
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

//	public String getLoai() {
//		return loai;
//	}
//
//	public void setLoai(String loai) {
//		this.loai = loai;
//	}

	public ResultSet getTaikhoanRs() {
		return TaikhoanRs;
	}

	public void setTaikhoanRs(ResultSet taikhoanRs) {
		TaikhoanRs = taikhoanRs;
	}

}
