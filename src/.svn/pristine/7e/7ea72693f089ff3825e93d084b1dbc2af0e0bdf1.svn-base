package qlcn.pages.thuchi.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.thuchi.beans.IThuChiList;
import db.Dbutils;

public class ThuChiList extends Phan_Trang implements IThuChiList {
	private String userId;
	private String ID;
	private String tungay;
	private String denngay;
	private String sotientu;
	private String sotienden;
	private String loai;
	private String noidungthuchiId;
	private String taikhoaId;
	private String soItems;
	private String msg;
	
	private ResultSet ThuchiRs;
	private ResultSet NoidungthuchiRs;
	private ResultSet TaikhoanRs;
	
	private Dbutils db;
	private Utility util;
	
	public ThuChiList() {
		this.ID = "";
		this.tungay = "";
		this.denngay = "";
		this.sotientu = "";
		this.sotienden = "";
		this.loai = "";
		this.noidungthuchiId = "";
		this.taikhoaId = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		String query = "";
		if(this.userId.equals("100000")){
			query = "select tc.ID, tc.ngay, tc.sotien, dv.ten as donvi, tc.loai, isnull('['+cast(ndtc.id as varchar)+'] '+ndtc.ten,'') as tenndtc,"
					+ "\n	'['+cast(tk.id as varchar)+'] '+tk.ten as tentaikhoan, tc.TRANGTHAI, tc.NGAYTAO, tc.NGAYSUA"
					+ "\n from THUCHI tc"
					+ "\n left join NOIDUNGTHUCHI ndtc on ndtc.ID = tc.noidungthuchi_fk"
					+ "\n left join TAIKHOAN tk on tk.ID = tc.taikhoan_fk"
					+ "\n left join DONVI dv on dv.ID = tk.donvi_fk"
					+ "\n where tc.ID > 0";
		} else {
			query = "select tc.ID, tc.ngay, tc.sotien, dv.ten as donvi, tc.loai, isnull('['+cast(ndtc.id as varchar)+'] '+ndtc.ten,'') as tenndtc,"
					+ "\n	'['+cast(tk.id as varchar)+'] '+tk.ten as tentaikhoan, tc.TRANGTHAI, tc.NGAYTAO, tc.NGAYSUA from THUCHI tc"
					+ "\n left join NOIDUNGTHUCHI ndtc on ndtc.ID = tc.noidungthuchi_fk"
					+ "\n left join TAIKHOAN tk on tk.ID = tc.taikhoan_fk"
					+ "\n left join DONVI dv on dv.ID = tk.donvi_fk"
					+ " where tc.USERID = " + this.userId;
		}
		
		if(this.ID.trim().length() > 0) {
			query += " and tc.ID like '%" + this.ID.trim() + "%'";
		}
		
		if(this.tungay.trim().length() > 0) {
			query += " and tc.ngay >= '" + this.tungay.trim() + "'";
		}
		
		if(this.denngay.trim().length() > 0) {
			query += " and tc.ngay <= '" + this.denngay.trim() + "'";
		}
		
		if(this.sotientu.trim().length() > 0) {
			query += " and tc.sotien >= " + this.sotientu.trim();
		}
		
		if(this.sotienden.trim().length() > 0) {
			query += " and tc.sotien <= " + this.sotienden.trim();
		}
		
		if(this.loai.length() > 0) {
			query += " and tc.loai = " + this.loai;
		}
		
		if(this.noidungthuchiId.length() > 0) {
			query += " and tc.noidungthuchi_fk = " + this.noidungthuchiId;
		}
		
		if(this.taikhoaId.length() > 0) {
			query += " and tc.taikhoan_fk = " + this.taikhoaId;
		}
		
//		if(this.trangthai.length() > 0) {
//			query += " and tc.TRANGTHAI = " + this.trangthai;
//		}
		
		System.out.println(query);
		this.ThuchiRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
		
		createRs();
	}
	
	public void createRs() {
		String query = "";
		if(this.userId.equals("100000")){
			query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from NOIDUNGTHUCHI where TRANGTHAI = 1";
			this.NoidungthuchiRs = this.db.get(query);
			
			query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1";
			this.TaikhoanRs = this.db.get(query);
		} else {
			if(this.loai.length() > 0){
				query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from NOIDUNGTHUCHI where TRANGTHAI = 1 and USERID = " + this.userId + " and loai = " + this.loai;
				this.NoidungthuchiRs = this.db.get(query);
			} else {
				query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from NOIDUNGTHUCHI where TRANGTHAI = 1 and USERID = " + this.userId;
				this.NoidungthuchiRs = this.db.get(query);
			}
			
			query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1 and USERID = " + this.userId;
			this.TaikhoanRs = this.db.get(query);
		}
	}
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query;
			
			query = "select loai, sotien, taikhoan_fk from THUCHI where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String loai = rs.getString("loai");
			String sotien = rs.getString("sotien");
			String taikhoanId = rs.getString("taikhoan_fk");
			rs.close();
			
			if(loai.equals("1")){
				query = "update TAIKHOAN set sotien = (sotien - "+sotien+") where ID = " + taikhoanId;
				if(db.updateReturnInt(query) != 1) {
					this.msg = "Không cập nhật TAIKHOAN: " + query;
					db.getConnection().rollback();
					return;
				}
			} else {
				query = "update TAIKHOAN set sotien = (sotien + "+sotien+") where ID = " + taikhoanId;
				if(db.updateReturnInt(query) != 1) {
					this.msg = "Không cập nhật TAIKHOAN: " + query;
					db.getConnection().rollback();
					return;
				}
			}
			
			query = "update THUCHI set trangthai = 2 where ID = " + id;
			if(!this.db.update(query)){
	    		this.msg = "Không thể xóa THUCHI: " + query;
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
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				query = "delete THUCHI where trangthai = 2";
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database THUCHI: " + query;
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
			if (this.ThuchiRs != null)
				this.ThuchiRs.close();
			if (this.NoidungthuchiRs != null)
				this.NoidungthuchiRs.close();
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
	
	public String getMsg() {
		return msg;
	}
	
	public void setMsg(String msg) {
		this.msg = msg;
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

	public String getNoidungthuchiId() {
		return noidungthuchiId;
	}

	public void setNoidungthuchiId(String noidungthuchiId) {
		this.noidungthuchiId = noidungthuchiId;
	}

	public String getTaikhoaId() {
		return taikhoaId;
	}

	public void setTaikhoaId(String taikhoaId) {
		this.taikhoaId = taikhoaId;
	}

	public ResultSet getThuchiRs() {
		return ThuchiRs;
	}

	public void setThuchiRs(ResultSet thuchiRs) {
		ThuchiRs = thuchiRs;
	}

	public ResultSet getNoidungthuchiRs() {
		return NoidungthuchiRs;
	}

	public void setNoidungthuchiRs(ResultSet noidungthuchiRs) {
		NoidungthuchiRs = noidungthuchiRs;
	}

	public ResultSet getTaikhoanRs() {
		return TaikhoanRs;
	}

	public void setTaikhoanRs(ResultSet taikhoanRs) {
		TaikhoanRs = taikhoanRs;
	}

	public String getSotientu() {
		return sotientu;
	}

	public void setSotientu(String sotientu) {
		this.sotientu = sotientu;
	}

	public String getSotienden() {
		return sotienden;
	}

	public void setSotienden(String sotienden) {
		this.sotienden = sotienden;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}
}
