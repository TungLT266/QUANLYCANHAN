package qlcn.pages.chuyentien.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import qlcn.center.util.Phan_Trang;
import qlcn.center.util.Utility;
import qlcn.pages.chuyentien.beans.IChuyenTienList;
import db.Dbutils;

public class ChuyenTienList extends Phan_Trang implements IChuyenTienList {
	private String userId;
	private String ID;
	private String tungay;
	private String denngay;
	private String noidung;
	private String taikhoanchuyenId;
	private String sotientu;
	private String sotienden;
	private String taikhoannhanId;
	private String trangthai;
	private String soItems;
	private String msg;
	
	private ResultSet TaikhoanRs;
	private ResultSet ChuyentienRs;
	
	private Dbutils db;
	private Utility util;
	
	public ChuyenTienList() {
		this.ID = "";
		this.tungay = "";
		this.denngay = "";
		this.noidung = "";
		this.taikhoanchuyenId = "";
		this.sotientu = "";
		this.sotienden = "";
		this.taikhoannhanId = "";
		this.trangthai = "";
		this.soItems = "100";
		this.msg = "";
		
		this.db = new Dbutils();
		this.util = new Utility();
	}
	
	public void init() {
		String queryUser = "";
		if(!this.userId.equals("100000")){
			queryUser = " and ct.USERID = " + this.userId;
		}
		
		String query = "select ct.ID, ct.NGAY, ct.NOIDUNG, tkc.ten as taikhoanchuyen, tkn.ten as taikhoannhan, ct.SOTIENCHUYEN, dvc.ten as donvichuyen, ct.TRANGTHAI, ct.NGAYTAO, ct.NGAYSUA"
				+ "\n from CHUYENTIEN ct"
				+ "\n left join TAIKHOAN tkc on tkc.ID = ct.taikhoanchuyen_fk"
				+ "\n left join TAIKHOAN tkn on tkn.ID = ct.taikhoannhan_fk"
				+ "\n left join DONVI dvc on dvc.ID = tkc.donvi_fk"
				+ "\n where ct.ID > 0" + queryUser;
		
		if(this.ID.trim().length() > 0) {
			query += " and ct.ID like '%" + this.ID.trim() + "%'";
		}
		
		if(this.tungay.trim().length() > 0) {
			query += " and ct.ngay >= '" + this.tungay.trim() + "'";
		}
		
		if(this.denngay.trim().length() > 0) {
			query += " and ct.ngay <= '" + this.denngay.trim() + "'";
		}
		
		if(this.noidung.trim().length() > 0) {
			query += " and dbo.ftBoDau(ct.noidung) like '%" + this.util.replaceAEIOU(this.noidung.trim()) + "%'";
		}
		
		if(this.taikhoanchuyenId.length() > 0) {
			query += " and ct.TAIKHOANCHUYEN_FK = " + this.taikhoanchuyenId;
		}
		
		if(this.taikhoannhanId.length() > 0) {
			query += " and ct.TAIKHOANNHAN_FK = " + this.taikhoannhanId;
		}
		
		if(this.sotientu.trim().length() > 0) {
			query += " and ct.sotienchuyen >= " + this.sotientu.trim();
		}
		
		if(this.sotienden.trim().length() > 0) {
			query += " and ct.sotienchuyen <= " + this.sotienden.trim();
		}
		
		if(this.trangthai.length() > 0) {
			query += " and ct.TRANGTHAI = " + this.trangthai;
		}
		
		System.out.println("init: "+query);
		this.ChuyentienRs = createSplittingDataNew(this.db, Integer.parseInt(this.soItems), 10, "ID desc", query);
		
		createRs();
	}
	
	public void createRs() {
		String queryUser = "";
		if(!this.userId.equals("100000")){
			queryUser = " and USERID = " + this.userId;
		}
		
		String query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1" + queryUser;
		this.TaikhoanRs = this.db.getScroll(query);
	}
	
	public void chot(String id) {
		try {
			String query = "select TAIKHOANCHUYEN_FK, SOTIENCHUYEN, TAIKHOANNHAN_FK, SOTIENNHAN, TKPHI, PHI,"
					+ " (select sotien from TAIKHOAN where ID=ct.TAIKHOANCHUYEN_FK) as tientkchuyen,"
					+ " (select sotien from TAIKHOAN where ID=ct.TAIKHOANNHAN_FK) as tientknhan"
					+ "\n from CHUYENTIEN ct where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String taikhoanchuyen = rs.getString("TAIKHOANCHUYEN_FK");
			double sotienchuyen = rs.getDouble("SOTIENCHUYEN");
			String taikhoannhan = rs.getString("TAIKHOANNHAN_FK");
			double sotiennhan = rs.getDouble("SOTIENNHAN");
			String tkphi = rs.getString("TKPHI");
			double phi = rs.getDouble("PHI");
			double tientkchuyen = rs.getDouble("tientkchuyen");
			double tientknhan = rs.getDouble("tientknhan");
			rs.close();
			
			if(tkphi.equals("1")){
				if(tientkchuyen < sotienchuyen + phi){
					this.msg = "Số tiền trong tài khoản chuyển còn "+tientkchuyen+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = sotien - "+sotienchuyen+" - "+phi+" where ID = " + taikhoanchuyen
						+ "\n update TAIKHOAN set sotien = sotien + "+sotiennhan+" where ID = " + taikhoannhan;
			} else if(tkphi.equals("2")){
				if(tientkchuyen < sotienchuyen){
					this.msg = "Số tiền trong tài khoản chuyển còn "+tientkchuyen+", không đủ để thực hiện.";
					return;
				}
				
				if(tientknhan + sotiennhan < phi){
					this.msg = "Số tiền trong tài khoản nhận còn "+tientknhan+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = sotien - "+sotienchuyen+" where ID = " + taikhoanchuyen
						+ "\n update TAIKHOAN set sotien = sotien + "+sotiennhan+" - "+phi+" where ID = " + taikhoannhan;
			} else {
				if(tientkchuyen < sotienchuyen){
					this.msg = "Số tiền trong tài khoản chuyển còn "+tientkchuyen+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = sotien - "+sotienchuyen+" where ID = " + taikhoanchuyen
						+ "\n update TAIKHOAN set sotien = sotien + "+sotiennhan+" where ID = " + taikhoannhan;
			}
			
			db.getConnection().setAutoCommit(false);
			
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể cập nhật TAIKHOAN: " + query;
	    		db.getConnection().rollback();
	    		return;
	    	}
			
			query = "update CHUYENTIEN set trangthai = 1, ngaychot = '"+this.getDateTime()+"' where trangthai=0 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể cập nhật CHUYENTIEN: " + query;
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
			String query = "select TAIKHOANCHUYEN_FK, SOTIENCHUYEN, TAIKHOANNHAN_FK, SOTIENNHAN, TKPHI, PHI, (select sotien from TAIKHOAN where ID=ct.TAIKHOANNHAN_FK) as tientknhan"
					+ " from CHUYENTIEN ct where ID = " + id;
			ResultSet rs = this.db.get(query);
			rs.next();
			String taikhoanchuyen = rs.getString("TAIKHOANCHUYEN_FK");
			double sotienchuyen = rs.getDouble("SOTIENCHUYEN");
			String taikhoannhan = rs.getString("TAIKHOANNHAN_FK");
			double sotiennhan = rs.getDouble("SOTIENNHAN");
			String tkphi = rs.getString("TKPHI");
			double phi = rs.getDouble("PHI");
			double tientknhan = rs.getDouble("tientknhan");
			rs.close();
			
			if(tkphi.equals("1")){
				if(tientknhan < sotiennhan){
					this.msg = "Số tiền trong tài khoản nhận còn "+tientknhan+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = sotien + "+sotienchuyen+" + "+phi+" where ID = " + taikhoanchuyen
						+ "\n update TAIKHOAN set sotien = sotien - "+sotiennhan+" where ID = " + taikhoannhan;
			} else if(tkphi.equals("2")){
				if(tientknhan + phi < sotiennhan){
					this.msg = "Số tiền trong tài khoản nhận còn "+tientknhan+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = sotien + "+sotienchuyen+" where ID = " + taikhoanchuyen
						+ "\n update TAIKHOAN set sotien = sotien - "+sotiennhan+" + "+phi+" where ID = " + taikhoannhan;
			} else {
				if(tientknhan < sotiennhan){
					this.msg = "Số tiền trong tài khoản nhận còn "+tientknhan+", không đủ để thực hiện.";
					return;
				}
				
				query = "update TAIKHOAN set sotien = sotien + "+sotienchuyen+" where ID = " + taikhoanchuyen
						+ "\n update TAIKHOAN set sotien = sotien - "+sotiennhan+" where ID = " + taikhoannhan;
			}
			
			db.getConnection().setAutoCommit(false);
			
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể cập nhật TAIKHOAN: " + query;
	    		db.getConnection().rollback();
	    		return;
	    	}
			
			query = "update CHUYENTIEN set trangthai = 0 where trangthai=1 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể cập nhật CHUYENTIEN: " + query;
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
		
			String query = "update CHUYENTIEN set trangthai = 2 where trangthai = 0 and ID = " + id;
			if(db.updateReturnInt(query) != 1) {
	    		this.msg = "Không thể xóa CHUYENTIEN: " + query;
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
				
				query = "delete CHUYENTIEN where trangthai = 2" + queryUser;
				if(!this.db.update(query)){
		    		this.msg = "Không thể xóa Database CHUYENTIEN: " + query;
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
			if (this.TaikhoanRs != null)
				this.TaikhoanRs.close();
			if (this.ChuyentienRs != null)
				this.ChuyentienRs.close();
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

	public ResultSet getChuyentienRs() {
		return ChuyentienRs;
	}

	public void setChuyentienRs(ResultSet chuyentienRs) {
		ChuyentienRs = chuyentienRs;
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

	public String getTaikhoanchuyenId() {
		return taikhoanchuyenId;
	}

	public void setTaikhoanchuyenId(String taikhoanchuyenId) {
		this.taikhoanchuyenId = taikhoanchuyenId;
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

	public String getTaikhoannhanId() {
		return taikhoannhanId;
	}

	public void setTaikhoannhanId(String taikhoannhanId) {
		this.taikhoannhanId = taikhoannhanId;
	}

	public ResultSet getTaikhoanRs() {
		return TaikhoanRs;
	}

	public void setTaikhoanRs(ResultSet taikhoanRs) {
		TaikhoanRs = taikhoanRs;
	}

	public String getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
}
