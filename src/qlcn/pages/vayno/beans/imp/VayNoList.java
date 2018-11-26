package qlcn.pages.vayno.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import db.Dbutils;
import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.vayno.beans.IVayNoList;

public class VayNoList extends Phan_Trang implements IVayNoList {
	private String userId;
	private String ID;
	private String tungay;
	private String denngay;
	private String sotientu;
	private String sotienden;
	private String loai;
	private String nguoivayno;
	private String noidung;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet VaynoRs;
	
	private Dbutils db;
	private Utility util;
	
	public VayNoList() {
		this.ID = "";
		this.tungay = "";
		this.denngay = "";
		this.sotientu = "";
		this.sotienden = "";
		this.loai = "";
		this.nguoivayno = "";
		this.noidung = "";
		this.trangthai = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		String queryUser = "";
		if(!this.userId.equals("100000")){
			queryUser = " and vn.USERID = " + this.userId;
		}
		
		String query = "select vn.ID, ngay, vn.sotien, dv.ten as donvi, case when vn.loai=1 then 'Vay' else 'Cho vay' end as loai,"
				+ " vn.nguoivayno, vn.noidung, vn.TRANGTHAI, vn.NGAYTAO, vn.NGAYSUA"
				+ "\n from VAYNO vn"
				+ "\n left join TAIKHOAN tk on tk.ID = vn.taikhoan_fk_cho"
				+ "\n left join DONVI dv on dv.ID = tk.donvi_fk"
				+ "\n where vn.ID > 0" + queryUser;
		
		if(this.ID.trim().length() > 0) {
			query += " and vn.ID like '%" + this.ID.trim() + "%'";
		}
		
		if(this.tungay.trim().length() > 0) {
			query += " and vn.ngay >= '" + this.tungay.trim() + "'";
		}
		
		if(this.denngay.trim().length() > 0) {
			query += " and vn.ngay <= '" + this.denngay.trim() + "'";
		}
		
		if(this.sotientu.length() > 0) {
			query += " and vn.sotien >= " + this.sotientu;
		}
		
		if(this.sotienden.length() > 0) {
			query += " and vn.sotien <= " + this.sotienden;
		}
		
		if(this.loai.length() > 0) {
			query += " and vn.loai = " + this.loai;
		}
		
		if(this.nguoivayno.trim().length() > 0) {
			query += " and dbo.ftBoDau(vn.nguoivayno) like '%" + this.util.replaceAEIOU(this.nguoivayno.trim()) + "%'";
		}
		
		if(this.noidung.trim().length() > 0) {
			query += " and dbo.ftBoDau(vn.noidung) like '%" + this.util.replaceAEIOU(this.noidung.trim()) + "%'";
		}
		
		if(this.trangthai.length() > 0) {
			query += " and vn.TRANGTHAI = " + this.trangthai;
		}
		
		System.out.println(query);
		this.VaynoRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
	}
	
	public void unNhantra(String id) {
		try {
			String query = "select loai, sotien, phi, taikhoan_fk_nhan from VAYNO where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String loai = rs.getString("loai");
			double sotien = rs.getDouble("sotien");
			double phi = rs.getDouble("phi");
			String taikhoannhantra = rs.getString("taikhoan_fk_nhan");
			rs.close();
			
			query = "select sotien from TAIKHOAN where ID = " + taikhoannhantra;
			rs = this.db.get(query);
			rs.next();
			double stientk = rs.getDouble("sotien");
			rs.close();
			
			if(loai.equals("1")) {
				query = "update TAIKHOAN set sotien = (sotien + "+sotien+" + "+phi+") where ID = " + taikhoannhantra;
			} else {
				if(stientk + phi < sotien){
					this.msg = "Số tiền trong tài khoản còn "+stientk+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = (sotien - "+sotien+" + "+phi+") where ID = " + taikhoannhantra;
			}
			
			db.getConnection().setAutoCommit(false);
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
				db.getConnection().rollback();
				return;
			}
			
			query = "update VAYNO set trangthai=0 where trangthai in (1) and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật VAYNO: " + query;
				db.getConnection().rollback();
				return;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return;
		}
	}
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "select loai, sotien, taikhoan_fk_cho from VAYNO where trangthai in (0) and ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String loai = rs.getString("loai");
			String sotien = rs.getString("sotien");
			String taikhoanId = rs.getString("taikhoan_fk_cho");
			rs.close();
			
			// Cộng trừ lại tiển trong tài khoản
			if(loai.equals("1")){
				// Kiểm tra có đủ tiền trong tài khoản để trừ
				query = "select sotien from TAIKHOAN where ID = " + taikhoanId;
				rs = this.db.get(query);
				rs.next();
				double stien = rs.getDouble("sotien");
				rs.close();
				if(stien < Double.parseDouble(sotien)){
					this.msg = "Số tiền trong tài khoản không đủ.";
					return;
				}
				
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
		
			query = "update VAYNO set trangthai = 2 where trangthai = 0 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể xóa VAYNO: " + query;
	    		db.getConnection().rollback();
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
				
				query = "delete VAYNO where trangthai = 2";
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database VAYNO: " + query;
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
			if (this.VaynoRs != null)
				this.VaynoRs.close();
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

	public String getNguoivayno() {
		return nguoivayno;
	}

	public void setNguoivayno(String nguoivayno) {
		this.nguoivayno = nguoivayno;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
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

	public ResultSet getVaynoRs() {
		return VaynoRs;
	}

	public void setVaynoRs(ResultSet vaynoRs) {
		VaynoRs = vaynoRs;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
}
