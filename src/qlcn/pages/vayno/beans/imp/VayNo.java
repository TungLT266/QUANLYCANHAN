package qlcn.pages.vayno.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import db.Dbutils;
import qlcn.pages.vayno.beans.IVayNo;

public class VayNo implements IVayNo {
	private String userId;
	private String ID;
	private String ngay;
	private String sotien;
	private String donvi;
	private String taikhoanId;
	private String loai;
	private String nguoivayno;
	private String noidung;
	private String ghichu;
	
	private String taikhoannhantra;
	private String ngaytra;
	private String phi;
	
	private String msg;
	
	private String action;
	
	private ResultSet TaikhoanRs;
	private ResultSet TaikhoanNhantraRs;
	
	private Dbutils db;
//	private Utility util;
	
	public VayNo() {
		this.ID = "";
		this.ngay = this.getDateTime();
		this.sotien = "";
		this.donvi = "";
		this.taikhoanId = "";
		this.loai = "1";
		this.nguoivayno = "";
		this.noidung = "";
		this.ghichu = "";
		this.taikhoannhantra = "";
		this.ngaytra = "";
		this.phi = "";
		this.msg = "";
		this.action = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		NumberFormat formatter = new DecimalFormat("#,###,###,###.##");
		String query = "select ngay,sotien,loai,taikhoan_fk_cho,isnull(taikhoan_fk_nhan,0) as taikhoan_fk_nhan,"
				+ "nguoivayno,noidung,ghichu,isnull(ngaytra,'') as ngaytra,isnull(cast(phi as varchar),'') as phi"
				+ " from VAYNO where ID = " + this.ID;
		System.out.println("init: "+query);
		
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.ngay = rs.getString("ngay");
			this.sotien = formatter.format(Double.parseDouble(rs.getString("sotien")));
			this.taikhoanId = rs.getString("taikhoan_fk_cho");
			this.loai = rs.getString("loai");
			this.nguoivayno = rs.getString("nguoivayno");
			this.noidung = rs.getString("noidung");
			this.ghichu = rs.getString("ghichu");
			this.taikhoannhantra = rs.getString("taikhoan_fk_nhan");
			this.ngaytra = rs.getString("ngaytra");
			this.phi = rs.getString("phi");
			if(this.phi.length() > 0){
				this.phi = formatter.format(Double.parseDouble(this.phi));
			}
			rs.close();
		} catch (Exception e) {}
		
		createRS();
	}
	
	public void createRS() {
		String queryUser = "";
		if(!this.userId.equals("100000")){
			queryUser = " and USERID = " + this.userId;
		}
		
		String query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1" + queryUser;
		this.TaikhoanRs = this.db.get(query);
		
		if(this.taikhoanId.length() > 5){
			// Lấy đơn vị của tài khoản
			try {
				query = "select dv.ten from TAIKHOAN tk left join DONVI dv on dv.ID = tk.donvi_fk where tk.ID = " + this.taikhoanId;
				ResultSet rs = this.db.get(query);
				rs.next();
				this.donvi = rs.getString("ten");
				rs.close();
			} catch (SQLException e) {}
		}
		
		if(this.action.equals("nhantra") || this.action.equals("display")){
			if(this.action.equals("nhantra") && this.ngaytra.length() <= 0){
				this.ngaytra = this.getDateTime();
			}
			
			query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1 and donvi_fk=(select donvi_fk from TAIKHOAN where ID = "+this.taikhoanId+")" + queryUser;
			this.TaikhoanNhantraRs = this.db.get(query);
		}
	}
	
	private boolean check() {
		try {
			if(this.loai.equals("2")) {
				String query = "select sotien from TAIKHOAN where ID = " + this.taikhoanId;
				ResultSet rs = this.db.get(query);
				rs.next();
				double stien = rs.getDouble("sotien");
				rs.close();
				if(stien < Double.parseDouble(this.sotien.replaceAll(",", ""))){
					this.msg = "Số tiền trong tài khoản không đủ.";
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean create() {
		try {
			if(!check()){
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into VAYNO(ngay,sotien,loai,taikhoan_fk_cho,nguoivayno,noidung,ghichu,trangthai,ngaytao,ngaysua,USERID)"
					+ "\n values('"+this.ngay+"',"+this.sotien.replaceAll(",", "")+","+this.loai+","+this.taikhoanId+",N'"+this.nguoivayno+"',"
					+ "N'"+this.noidung+"',N'"+this.ghichu+"',0,'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+")";
			System.out.println("create: "+query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới VAYNO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.loai.equals("1")){
				query = "update TAIKHOAN set sotien = (sotien + "+this.sotien.replaceAll(",", "")+") where ID = " + this.taikhoanId;
			} else {
				query = "update TAIKHOAN set sotien = (sotien - "+this.sotien.replaceAll(",", "")+") where ID = " + this.taikhoanId;
			}
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không cập nhật TAIKHOAN: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean update() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "update VAYNO set nguoivayno=N'"+this.nguoivayno+"',noidung=N'"+this.noidung+"',ghichu=N'"+this.ghichu+"',ngaysua='"+this.getDateTime()+"' where trangthai in (0) and ID = " + this.ID;
			System.out.println("update: "+query);
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật VAYNO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} catch (SQLException e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
	}
	
	public boolean nhantra() {
		try {
			String query = "select sotien from TAIKHOAN where ID = " + this.taikhoannhantra;
			ResultSet rs = this.db.get(query);
			rs.next();
			double stien = rs.getDouble("sotien");
			rs.close();
			
			double phint = 0;
			if(this.phi.trim().length() > 0){
				phint = Double.parseDouble(this.phi.replaceAll(",", ""));
			}
			
			if(this.loai.equals("1")) {
				if(stien < Double.parseDouble(this.sotien.replaceAll(",", "")) + phint){
					this.msg = "Số tiền trong tài khoản còn "+stien+", không đủ để thực hiện.";
					return false;
				}
				
				query = "update TAIKHOAN set sotien = (sotien - "+this.sotien.replaceAll(",", "")+" - "+phint+") where ID = " + this.taikhoannhantra;
			} else {
				if(stien + Double.parseDouble(this.sotien.replaceAll(",", "")) < phint){
					this.msg = "Số tiền trong tài khoản còn "+stien+", không đủ để thực hiện.";
					return false;
				}
				
				query = "update TAIKHOAN set sotien = (sotien + "+this.sotien.replaceAll(",", "")+" - "+phint+") where ID = " + this.taikhoannhantra;
			}
			
			db.getConnection().setAutoCommit(false);
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "update VAYNO set ghichu=N'"+this.ghichu+"',ngaytra='"+this.ngaytra+"',phi="+phint+",taikhoan_fk_nhan="+this.taikhoannhantra+",trangthai=1 where trangthai in (0) and ID = " + this.ID;
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật VAYNO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
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

	public String getNgay() {
		return ngay;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
	}

	public String getSotien() {
		return sotien;
	}

	public void setSotien(String sotien) {
		this.sotien = sotien;
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

	public String getGhichu() {
		return ghichu;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
	}

	public String getNgaytra() {
		return ngaytra;
	}

	public void setNgaytra(String ngaytra) {
		this.ngaytra = ngaytra;
	}

	public String getPhi() {
		return phi;
	}

	public void setPhi(String phi) {
		this.phi = phi;
	}

	public ResultSet getTaikhoanRs() {
		return TaikhoanRs;
	}

	public void setTaikhoanRs(ResultSet taikhoanRs) {
		TaikhoanRs = taikhoanRs;
	}

	public String getDonvi() {
		return donvi;
	}

	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTaikhoanId() {
		return taikhoanId;
	}

	public void setTaikhoanId(String taikhoanId) {
		this.taikhoanId = taikhoanId;
	}

	public String getTaikhoannhantra() {
		return taikhoannhantra;
	}

	public void setTaikhoannhantra(String taikhoannhantra) {
		this.taikhoannhantra = taikhoannhantra;
	}

	public ResultSet getTaikhoanNhantraRs() {
		return TaikhoanNhantraRs;
	}

	public void setTaikhoanNhantraRs(ResultSet taikhoanNhantraRs) {
		TaikhoanNhantraRs = taikhoanNhantraRs;
	}
}
