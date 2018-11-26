package qlcn.pages.thuchi.beans.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import qlcn.pages.thuchi.beans.IThuChi;
import db.Dbutils;

public class ThuChi implements IThuChi {
	private String userId;
	private String ID;
	private String ngay;
	private String sotien;
	private String donvi;
	private String loai;
	private String noidungthuchiId;
	private String taikhoanId;
	private String taikhoanthanhtoanId;
	private String diengiai;
	private String phi;
	private String ghichuphi;
	private String msg;
	
	private ResultSet NoidungthuchiRs;
	private ResultSet TaikhoanRs;
	private ResultSet TaikhoanthanhtoanRs;
	
	private Dbutils db;
//	private Utility util;
	
	public ThuChi() {
		this.ID = "";
		this.ngay = this.getDateTime();
		this.sotien = "";
		this.donvi = "";
		this.loai = "2";
		this.noidungthuchiId = "0";
		this.taikhoanId = "0";
		this.taikhoanthanhtoanId = "0";
		this.diengiai = "";
		this.phi = "";
		this.ghichuphi = "";
		this.msg = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		NumberFormat formatter = new DecimalFormat("#,###,###,###.##");
		String query = "select ngay, sotien, loai, noidungthuchi_fk, taikhoan_fk, taikhoanthanhtoan_fk, diengiai, phi, ghichuphi from THUCHI where ID = " + this.ID;
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try {
			rs.next();
			this.ngay = rs.getString("ngay");
			this.sotien = formatter.format(Double.parseDouble(rs.getString("sotien")));
			this.loai = rs.getString("loai");
			this.noidungthuchiId = rs.getString("noidungthuchi_fk");
			this.taikhoanId = rs.getString("taikhoan_fk");
			this.taikhoanthanhtoanId = rs.getString("taikhoanthanhtoan_fk");
			this.diengiai = rs.getString("diengiai");
			this.phi = formatter.format(Double.parseDouble(rs.getString("phi")));
			this.ghichuphi = rs.getString("ghichuphi");
			rs.close();
		} catch (Exception e) {}
		
		createRs();
	}
	
	public void createRs() {
		String query = "";
		String queryUser = "";
		if(!this.userId.equals("100000")){
			queryUser = " and USERID = " + this.userId;
		}
		
		if(this.loai.length() > 0){
			// Lấy nội dung thu chi
			query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from NOIDUNGTHUCHI where TRANGTHAI = 1 and loai in (0,"+this.loai+")" + queryUser;
			this.NoidungthuchiRs = this.db.get(query);
		}
		
		// Lấy danh sách tài khoản có trạng thái hoạt động
		query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where TRANGTHAI = 1" + queryUser;
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
			
			// Lấy tài khoản thanh toán thuộc tài khoản này
			query = "select ID, '['+cast(ID as varchar)+'] '+(case when LOAITHE = 1 then 'ATM: ' when LOAITHE = 2 then 'VISA: ' when LOAITHE = 3 then 'MASTERCARD: ' else '' end)+SOTHE as ten"
					+ " from TAIKHOANTHANHTOAN where TRANGTHAI = 1 and taikhoan_fk = " + this.taikhoanId + queryUser;
			this.TaikhoanthanhtoanRs = this.db.get(query);
		}
	}
	
	public boolean create() {
		try {
			if(this.phi.trim().length() == 0){
				this.phi = "0";
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into THUCHI(ngay, sotien, loai, noidungthuchi_fk, taikhoan_fk, taikhoanthanhtoan_fk, diengiai, phi, ghichuphi, trangthai, ngaytao, ngaysua, USERID)"
					+ "\n values('"+this.ngay+"',"+this.sotien.replaceAll(",", "")+","+this.loai+","+this.noidungthuchiId+","+this.taikhoanId+","+this.taikhoanthanhtoanId+","
					+ " N'"+this.diengiai+"',"+this.phi.replaceAll(",", "")+",N'"+this.ghichuphi+"',0,'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+")";
			System.out.println(query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới THUCHI: " + query;
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
			if(this.phi.trim().length() == 0){
				this.phi = "0";
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update THUCHI set ngay='"+this.ngay+"',sotien="+this.sotien.replaceAll(",", "")+",loai="+this.loai+",noidungthuchi_fk="+this.noidungthuchiId+","
					+ "taikhoan_fk="+this.taikhoanId+",taikhoanthanhtoan_fk="+this.taikhoanthanhtoanId+",diengiai=N'"+this.diengiai+"',phi="+this.phi.replaceAll(",", "")+","
					+ "ghichuphi=N'"+this.ghichuphi+"',ngaysua='"+this.getDateTime()+"' where trangthai=0 and ID = " + this.ID;
			System.out.println(query);
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật THUCHI: " + query;
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

	public String getNoidungthuchiId() {
		return noidungthuchiId;
	}

	public void setNoidungthuchiId(String noidungthuchiId) {
		this.noidungthuchiId = noidungthuchiId;
	}

	public String getTaikhoanId() {
		return taikhoanId;
	}

	public void setTaikhoanId(String taikhoanId) {
		this.taikhoanId = taikhoanId;
	}

	public String getTaikhoanthanhtoanId() {
		return taikhoanthanhtoanId;
	}

	public void setTaikhoanthanhtoanId(String taikhoanthanhtoanId) {
		this.taikhoanthanhtoanId = taikhoanthanhtoanId;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
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

	public ResultSet getTaikhoanthanhtoanRs() {
		return TaikhoanthanhtoanRs;
	}

	public void setTaikhoanthanhtoanRs(ResultSet taikhoanthanhtoanRs) {
		TaikhoanthanhtoanRs = taikhoanthanhtoanRs;
	}

	public String getDonvi() {
		return donvi;
	}

	public void setDonvi(String donvi) {
		this.donvi = donvi;
	}

	public String getPhi() {
		return phi;
	}

	public void setPhi(String phi) {
		this.phi = phi;
	}

	public String getGhichuphi() {
		return ghichuphi;
	}

	public void setGhichuphi(String ghichuphi) {
		this.ghichuphi = ghichuphi;
	}
}
