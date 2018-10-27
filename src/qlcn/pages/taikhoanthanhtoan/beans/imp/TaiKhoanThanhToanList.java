package qlcn.pages.taikhoanthanhtoan.beans.imp;

import java.sql.ResultSet;

import db.Dbutils;
import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToanList;

public class TaiKhoanThanhToanList extends Phan_Trang implements ITaiKhoanThanhToanList {
	private String userId;
	private String ID;
	private String taikhoan;
	private String ten;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet TaikhoanthanhtoanRs;
	
	private Dbutils db;
	private Utility util;
	
	public TaiKhoanThanhToanList() {
		this.ID = "";
		this.taikhoan = "";
		this.ten = "";
		this.trangthai = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		String query = "";
		if(this.userId.equals("100000")){
			query = "select tktt.ID, '[' + cast(tk.id as varchar) + '] ' + tk.ten as taikhoan,"
					+ "\n	case when tktt.loai = 1 then tktt.TEN when tktt.loai = 2 then tktt.sothe else '' end as ten,"
					+ "\n	tktt.TRANGTHAI, tktt.NGAYTAO, tktt.NGAYSUA"
					+ "\n from TAIKHOANTHANHTOAN tktt left join TAIKHOAN tk on tk.ID = tktt.taikhoan_fk where tktt.ID > 0";
		} else {
			query = "select tktt.ID, '[' + cast(tk.id as varchar) + '] ' + tk.ten as taikhoan,"
					+ "\n	case when tktt.loai = 1 then tktt.TEN when tktt.loai = 2 then tktt.sothe else '' end as ten,"
					+ "\n	tktt.TRANGTHAI, tktt.NGAYTAO, tktt.NGAYSUA"
					+ "\n from TAIKHOANTHANHTOAN tktt left join TAIKHOAN tk on tk.ID = tktt.taikhoan_fk where tktt.USERID = " + this.userId;
		}
		
		if(this.ID.trim().length() > 0) {
			query += " and tktt.ID like '%" + this.ID.trim() + "%'";
		}
		
		if(this.ten.trim().length() > 0) {
			query += " and (dbo.ftBoDau(tktt.TEN) like '%" + this.util.replaceAEIOU(this.ten.trim()) + "%' or tktt.sothe like '%" + this.util.replaceAEIOU(this.ten.trim()) + "%')";
		}
		
		if(this.taikhoan.trim().length() > 0) {
			query += " and (tk.id like '%" + this.util.replaceAEIOU(this.taikhoan.trim()) + "%' or (dbo.ftBoDau(tk.TEN)) like '%" + this.util.replaceAEIOU(this.taikhoan.trim()) + "%')";
		}
		
		if(this.trangthai.length() > 0) {
			query += " and tktt.TRANGTHAI = '" + this.trangthai + "'";
		}
		
		System.out.println(query);
		this.TaikhoanthanhtoanRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
	}
	
	public void delete(String id) {
		String query = "update TAIKHOANTHANHTOAN set trangthai = 2 where ID = " + id;
		if(!this.db.update(query)){
    		this.msg = "Không thể xóa TAIKHOANTHANHTOAN: " + query;
    	}
	}
	
	public void deleteDB(String pinUser) {
		try {
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				query = "delete TAIKHOANTHANHTOAN where trangthai = 2";
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database TAIKHOANTHANHTOAN: " + query;
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
			if (this.TaikhoanthanhtoanRs != null)
				this.TaikhoanthanhtoanRs.close();
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

	public String getTaikhoan() {
		return taikhoan;
	}

	public void setTaikhoan(String taikhoan) {
		this.taikhoan = taikhoan;
	}

	public ResultSet getTaikhoanthanhtoanRs() {
		return TaikhoanthanhtoanRs;
	}

	public void setTaikhoanthanhtoanRs(ResultSet taikhoanthanhtoanRs) {
		TaikhoanthanhtoanRs = taikhoanthanhtoanRs;
	}
}
