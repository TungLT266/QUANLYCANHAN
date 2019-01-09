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
	private String ghichu2;
	
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
		this.ghichu2 = "";
		
		this.msg = "";
		this.action = "";
		
		this.db = new Dbutils();
//		this.util = new Utility();
	}
	
	public void init() {
		try {
			NumberFormat formatter = new DecimalFormat("#,###,###,###.##");
			String query = "select ngay,sotien,loai,taikhoan_fk_cho,taikhoan_fk_nhan,nguoivayno,noidung,ghichu,ngaytra,phi,ghichu2 from VAYNO where ID = " + this.ID;
			System.out.println("init: "+query);
			
			ResultSet rs = this.db.get(query);
		
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
			if(this.action.equals("nhantra") && this.ngaytra.length() == 0){
				this.ngaytra = this.getDateTime();
			}
			
			this.phi = rs.getString("phi") == null ? "" : rs.getString("phi");
			if(this.phi.length() > 0){
				this.phi = formatter.format(Double.parseDouble(this.phi));
			}
			
			this.ghichu2 = rs.getString("ghichu2");
			rs.close();
			
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
			
			//Lấy list tài khoản
			String query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN where 1=1" + queryUser;
			if(this.action.equals("display") && this.taikhoanId.length() > 5){ // Lấy cả tài khoản có trạng thái ngưng hoạt động
				query += " and (TRANGTHAI=1 or ID=" + this.taikhoanId + ")";
			} else {
				query += " and TRANGTHAI=1";
			}
			this.TaikhoanRs = this.db.get(query);
			
			if(this.taikhoanId.length() > 5){
				// Lấy đơn vị của tài khoản
				query = "select dv.ten from TAIKHOAN tk left join DONVI dv on dv.ID = tk.donvi_fk where tk.ID = " + this.taikhoanId;
				ResultSet rs = this.db.get(query);
				rs.next();
				this.donvi = rs.getString("ten");
				rs.close();
			}
			
			// Lấy list tài khoản thực hiện, chỉ lấy những tài khoản có cùng đơn vị với ô tài khoản
			if(this.taikhoanId.length() > 0){
				query = "select ID, '['+cast(ID as varchar)+'] '+TEN as ten from TAIKHOAN"
						+ " where donvi_fk=(select donvi_fk from TAIKHOAN where ID = "+this.taikhoanId+")" + queryUser;
				if(this.action.equals("display") && this.taikhoannhantra.length() > 5){ // Lấy cả tài khoản có trạng thái ngưng hoạt động
					query += " and (TRANGTHAI=1 or ID=" + this.taikhoannhantra + ")";
				} else {
					query += " and TRANGTHAI=1";
				}
				this.TaikhoanNhantraRs = this.db.get(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean create() {
		try {
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into VAYNO(ngay,sotien,loai,taikhoan_fk_cho,nguoivayno,noidung,ghichu,ngaytra,taikhoan_fk_nhan,phi,ghichu2,trangthai,ngaytao,ngaysua,USERID)"
					+ "\n values('"+this.ngay+"',"+this.sotien.replaceAll(",", "")+","+this.loai+","+this.taikhoanId+",N'"+this.nguoivayno+"',"
					+ " N'"+this.noidung+"',N'"+this.ghichu+"','"+this.ngaytra+"',"+this.taikhoannhantra+","+(this.phi.length() > 0 ? this.phi : "null")+","
					+ " N'"+this.ghichu2+"',0,'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+")";
			System.out.println("create: "+query);
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới VAYNO: " + query;
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
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean update() {
		try {
			db.getConnection().setAutoCommit(false);

			String query = "update VAYNO set ngay='"+this.ngay+"',sotien="+this.sotien.replaceAll(",", "")+",loai="+this.loai+","
					+ " taikhoan_fk_cho="+this.taikhoanId+",nguoivayno=N'"+this.nguoivayno+"',noidung=N'"+this.noidung+"',"
					+ " ghichu=N'"+this.ghichu+"',ngaytra='"+this.ngaytra+"',taikhoan_fk_nhan="+this.taikhoannhantra+","
					+ " phi="+(this.phi.length() > 0 ? this.phi : "null")+",ghichu2=N'"+this.ghichu2+"',ngaysua='"+this.getDateTime()+"'"
					+ " where trangthai in (0) and ID = " + this.ID;
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
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean nhantra() {
		try {
			db.getConnection().setAutoCommit(false);
			
			// Cập nhật tiền tài khoản nhận
			//begin{
			String query = "select loai, sotien from VAYNO where ID = " + this.ID;
			ResultSet rs = this.db.get(query);
			rs.next();
			String loai = rs.getString("loai");
			double sotien = rs.getDouble("sotien");
			rs.close();
			
			query = "select sotien from TAIKHOAN where ID = " + this.taikhoannhantra;
			rs = this.db.get(query);
			rs.next();
			double stientk = rs.getDouble("sotien");
			rs.close();
			
			double phint = 0;
			if(this.phi.trim().length() > 0){
				phint = Double.parseDouble(this.phi.replaceAll(",", ""));
			}
			
			if(loai.equals("1")) {
				if(stientk < sotien + phint){
					this.msg = "Số tiền trong tài khoản còn "+stientk+", không đủ để thực hiện.";
					return false;
				}
				
				query = "update TAIKHOAN set sotien = (sotien - "+sotien+" - "+phint+") where ID = " + this.taikhoannhantra;
			} else {
				if(stientk + sotien < phint){
					this.msg = "Số tiền trong tài khoản còn "+stientk+", không đủ để thực hiện.";
					return false;
				}
				
				query = "update TAIKHOAN set sotien = (sotien + "+sotien+" - "+phint+") where ID = " + this.taikhoannhantra;
			}
			
			if(db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật TAIKHOAN: " + query;
				db.getConnection().rollback();
				return false;
			}
			//}end
			
			// Lưu log cho tài khoản
			//begin{
			query = "insert into TAIKHOAN_LOG(ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,NGAY_LOG,CHUCNANG)"
					+ " select ID,TEN,SOTIEN,DONVI_FK,TRANGTHAI,NGAYTAO,NGAYSUA,USERID,NGANHANG,ISTKNGANHANG,ISTKTINDUNG,HANMUC,NOTINDUNG,GETDATE(),N'Vay/Nợ'"
					+ " from TAIKHOAN where ID = " + this.taikhoannhantra;
			if(this.db.updateReturnInt(query) != 1) {
				this.msg = "Không thể tạo mới TAIKHOAN_LOG: " + query;
				db.getConnection().rollback();
				return false;
			}
			//}end
			
			// Lưu thông tin trả
			query = "update VAYNO set ngaytra='"+this.ngaytra+"',phi="+phint+",taikhoan_fk_nhan="+this.taikhoannhantra+",ghichu2=N'"+this.ghichu2+"',trangthai=2 where trangthai = 1 and ID = " + this.ID;
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
			e.printStackTrace();
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

	public String getGhichu2() {
		return ghichu2;
	}

	public void setGhichu2(String ghichu2) {
		this.ghichu2 = ghichu2;
	}
}
