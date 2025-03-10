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
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void chot(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			// Cập nhật tiền trong tài khoản
			//begin{
			String query = "select loai, sotien, taikhoan_fk_cho from VAYNO where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String loai = rs.getString("loai");
			double sotien = rs.getDouble("sotien");
			String taikhoan = rs.getString("taikhoan_fk_cho");
			rs.close();
			
			query = "select sotien from TAIKHOAN where ID = " + taikhoan;
			rs = this.db.get(query);
			rs.next();
			double stientk = rs.getDouble("sotien");
			rs.close();
			
			if(loai.equals("1")) { // Nếu và vay
				query = "update TAIKHOAN set sotien = (sotien + "+sotien+") where ID = " + taikhoan;
			} else { // Nếu là cho vay
				if(stientk < sotien){
					this.msg = "Số tiền trong tài khoản còn "+stientk+", không đủ để thực hiện.";
					db.getConnection().rollback();
					return;
				}
				
				query = "update TAIKHOAN set sotien = (sotien - "+sotien+") where ID = " + taikhoan;
			}
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
				db.getConnection().rollback();
				return;
			}
			//}end
			
			// Lưu log cho tài khoản
			//begin{
			query = "insert into TAIKHOAN_LOG(ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,NGAY_LOG,CHUCNANG)"
					+ " select ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,GETDATE(),N'Vay/Nợ'"
					+ " from TAIKHOAN where ID = " + taikhoan;
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể tạo mới TAIKHOAN_LOG: " + query;
				db.getConnection().rollback();
				return;
			}
			//}end
			
			// Cập nhật vay nợ sang trạng thái đã chốt
			query = "update VAYNO set trangthai = 1 where trangthai = 0 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể chốt VAYNO: " + query;
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
			e.printStackTrace();
			return;
		}
	}
	
	public void unChot(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			// Cập nhật tiền trong tài khoản
			//begin{
			String query = "select loai, sotien, taikhoan_fk_cho from VAYNO where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String loai = rs.getString("loai");
			double sotien = rs.getDouble("sotien");
			String taikhoan = rs.getString("taikhoan_fk_cho");
			rs.close();
			
			query = "select sotien from TAIKHOAN where ID = " + taikhoan;
			rs = this.db.get(query);
			rs.next();
			double stientk = rs.getDouble("sotien");
			rs.close();
			
			if(loai.equals("1")) { // Nếu là vay
				if(stientk < sotien){
					this.msg = "Số tiền trong tài khoản còn "+stientk+", không đủ để thực hiện.";
					db.getConnection().rollback();
					return;
				}
				
				query = "update TAIKHOAN set sotien = (sotien - "+sotien+") where ID = " + taikhoan;
			} else { // Nếu là cho vay
				query = "update TAIKHOAN set sotien = (sotien + "+sotien+") where ID = " + taikhoan;
			}
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
				db.getConnection().rollback();
				return;
			}
			//}end
			
			// Lưu log cho tài khoản
			//begin{
			query = "insert into TAIKHOAN_LOG(ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,NGAY_LOG,CHUCNANG)"
					+ " select ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,GETDATE(),N'Vay/Nợ'"
					+ " from TAIKHOAN where ID = " + taikhoan;
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể tạo mới TAIKHOAN_LOG: " + query;
				db.getConnection().rollback();
				return;
			}
			//}end
			
			// Cập nhật sang trạng thái chưa chốt
			query = "update VAYNO set trangthai = 0 where trangthai = 1 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể bỏ chốt VAYNO: " + query;
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
			e.printStackTrace();
			return;
		}
	}
	
	public void unNhantra(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			// Cập nhật tiền trong tài khoản nhận
			//begin{
			String query = "select loai, sotien, taikhoan_fk_nhan, phi from VAYNO where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String loai = rs.getString("loai");
			double sotien = rs.getDouble("sotien");
			String taikhoannhantra = rs.getString("taikhoan_fk_nhan");
			double phi = rs.getDouble("phi");
			rs.close();
			
			query = "select sotien from TAIKHOAN where ID = " + taikhoannhantra;
			rs = this.db.get(query);
			rs.next();
			double stientk = rs.getDouble("sotien");
			rs.close();
			
			if(loai.equals("1")) { // Vay
				query = "update TAIKHOAN set sotien = (sotien + "+sotien+" + "+phi+") where ID = " + taikhoannhantra;
			} else { // Cho vay
				if(stientk + phi < sotien){
					this.msg = "Số tiền trong tài khoản còn "+stientk+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = (sotien - "+sotien+" + "+phi+") where ID = " + taikhoannhantra;
			}
			
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
				this.db.getConnection().rollback();
				return;
			}
			//}end
			
			// Lưu log cho tài khoản
			//begin{
			query = "insert into TAIKHOAN_LOG(ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,NGAY_LOG,CHUCNANG)"
					+ " select ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,GETDATE(),N'Vay/Nợ'"
					+ " from TAIKHOAN where ID = " + taikhoannhantra;
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể tạo mới TAIKHOAN_LOG: " + query;
				this.db.getConnection().rollback();
				return;
			}
			//}end
			
			// Cập nhật sang trạng thái đã chốt
			query = "update VAYNO set trangthai=1 where trangthai = 2 and ID = " + id;
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật VAYNO: " + query;
				db.getConnection().rollback();
				return;
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {}
			e.printStackTrace();
			return;
		}
	}
	
	public void delete(String id) {
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update VAYNO set trangthai = 3 where trangthai = 0 and ID = " + id;
			if(this.db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể xóa VAYNO: " + query;
	    		this.db.getConnection().rollback();
	    		return;
	    	}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				this.db.getConnection().rollback();
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
				
				query = "delete VAYNO where trangthai = 3" + queryUser;
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
