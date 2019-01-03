package qlcn.pages.chuyentien.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import qlcn.pages.chuyentien.beans.IChuyenTien;
import db.Dbutils;

public class ChuyenTien implements IChuyenTien {
	private String userId;
	private String ID;
	private String ngay;
	private String noidung;
	private String taikhoanchuyenId;
	private String sotienchuyen;
	private String donvichuyen;
	private String taikhoannhanId;
	private String sotiennhan;
	private String donvinhan;
	private String tkphi;
	private String phi;
	private String msg;
	
	private ResultSet TaikhoanRs;
	private ResultSet TaikhoannhanRs;
	
	private Dbutils db;
//	private Utility util;
	
	public ChuyenTien() {
		this.ID = "";
		this.ngay = this.getDateTime();
		this.noidung = "";
		this.taikhoanchuyenId = "";
		this.sotienchuyen = "";
		this.donvichuyen = "";
		this.taikhoannhanId = "";
		this.sotiennhan = "";
		this.donvinhan = "";
		this.tkphi = "0";
		this.phi = "";
		this.msg = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		NumberFormat formatter = new DecimalFormat("#,###,###,###.##");
		String query = "select ngay,noidung,taikhoanchuyen_fk,sotienchuyen,taikhoannhan_fk,sotiennhan,tkphi,phi from CHUYENTIEN where ID = " + this.ID;
		System.out.println("init: "+query);
		
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.ngay = rs.getString("ngay");
			this.noidung = rs.getString("noidung");
			this.taikhoanchuyenId = rs.getString("taikhoanchuyen_fk");
			this.sotienchuyen = formatter.format(Double.parseDouble(rs.getString("sotienchuyen")));
			this.taikhoannhanId = rs.getString("taikhoannhan_fk");
			this.sotiennhan = formatter.format(Double.parseDouble(rs.getString("sotiennhan")));
			this.tkphi = rs.getString("tkphi");
			this.phi = formatter.format(Double.parseDouble(rs.getString("phi")));
			rs.close();
		} catch (Exception e) {}
		
		createRs();
	}
	
	public void createRs() {
		String queryUser = "";
		if(!this.userId.equals("100000")){
			queryUser = " and USERID = " + this.userId;
		}
		
		String query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1" + queryUser;
		this.TaikhoanRs = this.db.get(query);
		
		if(this.taikhoanchuyenId.length() > 5){
			try {
				// Lấy đơn vị cho tài khoản chuyển
				query = "select dv.id,dv.ten from TAIKHOAN tk inner join DONVI dv on dv.ID=tk.donvi_fk where tk.ID = " + this.taikhoanchuyenId;
				ResultSet rs = this.db.get(query);
				rs.next();
				this.donvichuyen = rs.getString("ten");
				String donvichuyenId = rs.getString("id");
				rs.close();
				
				// Lấy Resultset tài khoản chuyển, không lấy tài khoản đã được chọn làm tài khoản chuyển
				query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where ID != "+this.taikhoanchuyenId+" and TRANGTHAI = 1" + queryUser;
				this.TaikhoannhanRs = this.db.get(query);
				
				if(this.taikhoannhanId.length() > 5){
					// Nếu đơn vị tài khoản nhận khác đơn vị tài khoản chuyển, thì lấy đơn vị cho tài khoản nhận
					query = "select dv.ten from TAIKHOAN tk inner join DONVI dv on dv.ID=tk.donvi_fk where dv.ID != "+donvichuyenId+" and tk.ID = " + this.taikhoannhanId;
					rs = this.db.get(query);
					if(rs.next()){
						this.donvinhan = rs.getString("ten");
						if(this.sotiennhan.trim().length() == 0){
							this.sotiennhan = this.sotienchuyen;
						}
					}
					rs.close();
				}
			} catch (Exception e) {}
		}
	}
	
	public boolean create() {
		try {
			if(this.sotiennhan.trim().length() == 0){
				this.sotiennhan = this.sotienchuyen;
			}
			
			if(this.phi.trim().length() == 0){
				this.phi = "0";
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into CHUYENTIEN(ngay,noidung,taikhoanchuyen_fk,sotienchuyen,taikhoannhan_fk,sotiennhan,tkphi,phi,trangthai,ngaytao,ngaysua,USERID)"
					+ "\n values('"+this.ngay+"',N'"+this.noidung+"',"+this.taikhoanchuyenId+","+this.sotienchuyen.replaceAll(",", "")+","+this.taikhoannhanId+","
					+ this.sotiennhan.replaceAll(",", "")+","+this.tkphi+","+this.phi.replaceAll(",", "")+",0,'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+")";
			System.out.println("create: "+query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới CHUYENTIEN: " + query;
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
			if(this.sotiennhan.trim().length() == 0){
				this.sotiennhan = this.sotienchuyen;
			}
			
			if(this.phi.trim().length() == 0){
				this.phi = "0";
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update CHUYENTIEN set ngay='"+this.ngay+"',noidung=N'"+this.noidung+"',taikhoanchuyen_fk="+this.taikhoanchuyenId+",sotienchuyen="+this.sotienchuyen.replaceAll(",", "")+","
					+ "taikhoannhan_fk="+this.taikhoannhanId+",sotiennhan="+this.sotiennhan.replaceAll(",", "")+",tkphi="+this.tkphi+",phi="+this.phi.replaceAll(",", "")+",ngaysua='"+this.getDateTime()+"'"
					+ " where trangthai in (0) and ID = " + this.ID;
			System.out.println("update: "+query);
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật CHUYENTIEN: " + query;
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
	
	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose() {
		try {
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

	public String getSotienchuyen() {
		return sotienchuyen;
	}

	public void setSotienchuyen(String sotienchuyen) {
		this.sotienchuyen = sotienchuyen;
	}

	public String getDonvichuyen() {
		return donvichuyen;
	}

	public void setDonvichuyen(String donvichuyen) {
		this.donvichuyen = donvichuyen;
	}

	public String getTaikhoannhanId() {
		return taikhoannhanId;
	}

	public void setTaikhoannhanId(String taikhoannhanId) {
		this.taikhoannhanId = taikhoannhanId;
	}

	public String getSotiennhan() {
		return sotiennhan;
	}

	public void setSotiennhan(String sotiennhan) {
		this.sotiennhan = sotiennhan;
	}

	public String getDonvinhan() {
		return donvinhan;
	}

	public void setDonvinhan(String donvinhan) {
		this.donvinhan = donvinhan;
	}

	public String getTkphi() {
		return tkphi;
	}

	public void setTkphi(String tkphi) {
		this.tkphi = tkphi;
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

	public ResultSet getTaikhoannhanRs() {
		return TaikhoannhanRs;
	}

	public void setTaikhoannhanRs(ResultSet taikhoannhanRs) {
		TaikhoannhanRs = taikhoannhanRs;
	}
}
