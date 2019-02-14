package qlcn.pages.thuchi.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.thuchi.beans.IThuChiList;
import db.Dbutils;

public class ThuChiList extends Phan_Trang implements IThuChiList {
	private String userId;
	private String ID;
	private String tungay;
	private String denngay;
	private String taikhoanId;
	private String sotientu;
	private String sotienden;
	private String loai;
	private String noidungthuchiId;
	private String noidung;
	private String trangthai;
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
		this.taikhoanId = "";
		this.sotientu = "";
		this.sotienden = "";
		this.loai = "";
		this.noidungthuchiId = "";
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
				queryUser = " and tc.USERID = " + this.userId;
			}
			
			String query = "select tk.ten as tentk, tc.ID, tc.ngay, tc.sotien, dv.ten as donvi, case when tc.loai=1 then 'Thu' else 'Chi' end as loai, isnull(ndtc.ten,'') as tenndtc, tc.diengiai, tc.TRANGTHAI, tc.NGAYTAO, tc.NGAYSUA"
						+ "\n from THUCHI tc"
						+ "\n left join NOIDUNGTHUCHI ndtc on ndtc.ID = tc.noidungthuchi_fk"
						+ "\n left join TAIKHOAN tk on tk.ID = tc.taikhoan_fk"
						+ "\n left join DONVI dv on dv.ID = tk.donvi_fk"
						+ "\n where tc.ID > 0" + queryUser;
			
			if(this.ID.trim().length() > 0) {
				query += " and tc.ID like '%" + this.ID.trim() + "%'";
			}
			
			if(this.tungay.trim().length() > 0) {
				query += " and tc.ngay >= '" + this.tungay.trim() + "'";
			}
			
			if(this.denngay.trim().length() > 0) {
				query += " and tc.ngay <= '" + this.denngay.trim() + "'";
			}
			
			if(this.taikhoanId.length() > 0) {
				query += " and tc.taikhoan_fk = " + this.taikhoanId;
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
			
			if(this.noidung.trim().length() > 0) {
				query += " and dbo.ftBoDau(tc.diengiai) like '%" + this.util.replaceAEIOU(this.noidung.trim()) + "%'";
			}
			
			if(this.trangthai.length() > 0) {
				query += " and tc.TRANGTHAI = " + this.trangthai;
			}
			
			System.out.println(query);
			this.ThuchiRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ngay desc, id desc", query);
			
			createRs();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void createRs() {
		try {
			String queryUser = "";
			if(!this.userId.equals("100000")){
				queryUser = " and USERID = " + this.userId;
			}
			
			String query = "";
			if(this.loai.length() > 0){
				query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from NOIDUNGTHUCHI where TRANGTHAI = 1 and loai in (0,"+this.loai+")" + queryUser;
			} else {
				query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from NOIDUNGTHUCHI where TRANGTHAI = 1" + queryUser;
			}
			this.NoidungthuchiRs = this.db.get(query);
			
			query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1" + queryUser;
			this.TaikhoanRs = this.db.get(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void chot(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			// Định khoản
			//begin{
			String query = "select tc.ngay, SOTIEN, LOAI, TAIKHOAN_FK, phi,"
					+ " (select isnull(sum(no),0) - isnull(sum(co),0) from PHATSINHKETOAN where tkkt_fk = tc.TAIKHOAN_FK) as tientk,"
					+ " (select count(*) from TAIKHOAN where ID=tc.TAIKHOAN_FK and trangthai=1) as isTkkt"
					+ " from THUCHI tc where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String ngaychungtu = rs.getString("ngay");
			double sotien = rs.getDouble("SOTIEN");
			String loai = rs.getString("LOAI");
			String taikhoan = rs.getString("TAIKHOAN_FK");
			double phi = rs.getDouble("phi");
			double tientk = rs.getDouble("tientk");
			int isTkkt = rs.getInt("isTkkt");
			rs.close();
			
			// Kiểm tra tài khoản có hoạt động
			if(isTkkt != 1){
				this.msg = "Tài khoản đã xóa hoặc ngưng hoạt động.";
				db.getConnection().rollback();
				return;
			}
			
			if(loai.equals("1")){ // Thu
				if(tientk + sotien < phi){
					this.msg = "Số tiền trong tài khoản còn "+tientk+", không đủ để thực hiện.";
					db.getConnection().rollback();
					return;
				}
				
				query = "insert into PHATSINHKETOAN(NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TKKT_FK, NO, CO, USERID, GHICHU)"
						+ " values('"+ngaychungtu+"',GETDATE(),N'Thu/Chi',"+id+","+taikhoan+","+sotien+",0,"+this.userId+",N'Số tiền')";
				query += "\n insert into PHATSINHKETOAN(NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TKKT_FK, NO, CO, USERID, GHICHU)"
						+ " values('"+ngaychungtu+"',GETDATE(),N'Thu/Chi',"+id+","+taikhoan+",0,"+phi+","+this.userId+",N'Phí chuyển tiền')";
			} else{ // Chi
				if(tientk < sotien + phi){
					this.msg = "Số tiền trong tài khoản còn "+tientk+", không đủ để thực hiện.";
					db.getConnection().rollback();
					return;
				}
				
				query = "insert into PHATSINHKETOAN(NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TKKT_FK, NO, CO, USERID, GHICHU)"
						+ " values('"+ngaychungtu+"',GETDATE(),N'Thu/Chi',"+id+","+taikhoan+",0,"+sotien+","+this.userId+",N'Số tiền')";
				query += "\n insert into PHATSINHKETOAN(NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TKKT_FK, NO, CO, USERID, GHICHU)"
						+ " values('"+ngaychungtu+"',GETDATE(),N'Thu/Chi',"+id+","+taikhoan+",0,"+phi+","+this.userId+",N'Phí chuyển tiền')";
			}
			
			if(!this.db.update(query)) {
				this.msg = "Không thể định khoản: " + query;
	    		db.getConnection().rollback();
	    		return;
	    	}
			//}end
			
			// Cập nhât thu/chi sang trạng thái đã chốt
			query = "update THUCHI set trangthai = 1, ngaychot = '"+this.getDateTime()+"' where trangthai=0 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể cập nhật THUCHI: " + query;
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
	
	public void unchot(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			// Định khoản
			//begin{
			String query = "select SOTIEN, LOAI, phi,"
					+ " (select isnull(sum(no),0) - isnull(sum(co),0) from PHATSINHKETOAN where tkkt_fk = tc.TAIKHOAN_FK) as tientk,"
					+ " (select count(*) from TAIKHOAN where ID=tc.TAIKHOAN_FK and trangthai=1) as isTkkt"
					+ " from THUCHI tc where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			double sotien = rs.getDouble("SOTIEN");
			String loai = rs.getString("LOAI");
			double phi = rs.getDouble("phi");
			double tientk = rs.getDouble("tientk");
			int isTkkt = rs.getInt("isTkkt");
			rs.close();
			
			// Kiểm tra tài khoản có hoạt động
			if(isTkkt != 1){
				this.msg = "Tài khoản đã xóa hoặc ngưng hoạt động.";
				db.getConnection().rollback();
				return;
			}
			
			if(loai.equals("1")){ // Thu
				if(tientk + phi < sotien){
					this.msg = "Số tiền trong tài khoản còn "+tientk+", không đủ để thực hiện.";
					return;
				}
			}
			
			query = "delete PHATSINHKETOAN where loaichungtu=N'Thu/Chi' and sochungtu="+id;
			if(!db.update(query)) {
				this.msg = "Không thể xóa định khoản: " + query;
	    		db.getConnection().rollback();
	    		return;
	    	}
			//}end
			
			// Cập nhât thu/chi sang trạng thái chưa chốt
			query = "update THUCHI set trangthai = 0 where trangthai=1 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể cập nhật THUCHI: " + query;
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
	
	public void delete(String id) {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update THUCHI set trangthai = 2 where trangthai=0 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
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
			String queryUser = "";
			if(!this.userId.equals("100000")){
				queryUser = " and USERID = " + this.userId;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "select pin from NGUOIDUNG where pin = '"+this.util.encrypt(pinUser)+"' and ID = " + this.userId;
			ResultSet rs = this.db.get(query);
			if(rs.next()){
				rs.close();
				
				query = "delete THUCHI where trangthai = 2" + queryUser;
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database THUCHI: " + query;
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
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
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

	public String getNoidungthuchiId() {
		return noidungthuchiId;
	}

	public void setNoidungthuchiId(String noidungthuchiId) {
		this.noidungthuchiId = noidungthuchiId;
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

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public ResultSet getTaikhoanRs() {
		return TaikhoanRs;
	}

	public void setTaikhoanRs(ResultSet taikhoanRs) {
		TaikhoanRs = taikhoanRs;
	}

	public String getTaikhoanId() {
		return taikhoanId;
	}

	public void setTaikhoanId(String taikhoanId) {
		this.taikhoanId = taikhoanId;
	}
}
