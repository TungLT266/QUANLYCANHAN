package qlcn.pages.taikhoanthanhtoan.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.Dbutils;
import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.taikhoanthanhtoan.beans.ITaiKhoanThanhToanList;

public class TaiKhoanThanhToanList extends Phan_Trang implements ITaiKhoanThanhToanList {
	private String userId;
	private String ID;
	private String loaithe;
	private String taikhoan;
	private String ten;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet TaikhoanRs;
	private ResultSet TaikhoanthanhtoanRs;
	
	private Dbutils db;
	private Utility util;
	
	public TaiKhoanThanhToanList() {
		this.ID = "";
		this.loaithe = "";
		this.taikhoan = "";
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
				queryUser = " and tktt.USERID = " + this.userId;
			}
			
			String query = "select tktt.ID, (case when tktt.loaithe=1 then 'ATM' when tktt.loaithe=2 then 'VISA' when tktt.loaithe=3 then 'MASTERCARD' when tktt.loaithe=4 then N'Tín dụng' else '' end) as loaithe,"
					+ " tk.ten as taikhoan,tktt.sothe as ten,tktt.TRANGTHAI, tktt.NGAYTAO, tktt.NGAYSUA"
					+ "\n from TAIKHOANTHANHTOAN tktt left join TAIKHOAN tk on tk.ID = tktt.taikhoan_fk where tktt.ID > 0" + queryUser;
			
			if(this.ID.trim().length() > 0) {
				query += " and tktt.ID like '%" + this.ID.trim() + "%'";
			}
			
			if(this.loaithe.length() > 0) {
				query += " and tktt.loaithe = " + this.loaithe;
			}
			
			if(this.ten.trim().length() > 0) {
				query += " and tktt.sothe like '%" + this.util.replaceAEIOU(this.ten.trim()) + "%')";
			}
			
			if(this.taikhoan.trim().length() > 0) {
				query += " and tk.id = " + this.taikhoan;
			}
			
			if(this.trangthai.length() > 0) {
				query += " and tktt.TRANGTHAI = " + this.trangthai;
			}
			
			System.out.println(query);
			this.TaikhoanthanhtoanRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
			
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
			
			String query = "select ID, ten from TAIKHOAN where trangthai = 1 and istknganhang = 1" + queryUser;
			this.TaikhoanRs = this.db.get(query);
			
			query = "select ID from TAIKHOANTHANHTOAN"
					+ " where (case when thoigianhethan = '' then '' else left(thoigianhethan,CHARINDEX('-',thoigianhethan)-1) end) = '"+Integer.parseInt(this.getDateTime().split("-")[1])+"'"
					+ " and right(thoigianhethan,4) = '"+this.getDateTime().split("-")[0]+"'" + queryUser;
			ResultSet rs = this.db.get(query);
			String idhethan = "";
			while(rs.next()){
				idhethan += " : " + rs.getString("id");
			}
			rs.close();
			if(idhethan.length() > 0){
				this.msg = "Tài khoản thanh toán sắp hết hạn" + idhethan;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
		
			String query = "update TAIKHOANTHANHTOAN set trangthai = 2 where ID = " + id;
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể xóa TAIKHOANTHANHTOAN: " + query;
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
				
				query = "delete TAIKHOANTHANHTOAN where trangthai = 2" + queryUser;
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database TAIKHOANTHANHTOAN: " + query;
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
			if (this.TaikhoanthanhtoanRs != null)
				this.TaikhoanthanhtoanRs.close();
			if (this.db != null)
				this.db.shutDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
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
}
