package geso.traphaco.erp.beans.loaihoso.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSo;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpLoaiHoSo implements IErpLoaiHoSo {
	private String userId;
	private String congtyId;
	private String id;
	private String maLoaihoso;
	private String maBieumau;
	private String diengiai;
	private String ngaybanhanh;
	private String loaimauin;
	private String bieumauPath;
	private String bieumauName;
	private String trangthai;
	private String msg;
	
	dbutils db;
	
	public ErpLoaiHoSo() {
		this.id = "";
		this.maLoaihoso = "";
		this.maBieumau = "";
		this.diengiai = "";
		this.ngaybanhanh = "";
		this.loaimauin = "";
		this.bieumauPath = "";
		this.bieumauName = "";
		this.trangthai = "1";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select ma,MABIEUMAU,ten,ngaybanhanh,loaimauin,bieumau,trangthai from ERP_LOAIHOSO where pk_seq = " + this.id;
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try {
				rs.next();
				this.maLoaihoso = rs.getString("ma");
				this.maBieumau = rs.getString("MABIEUMAU");
				this.diengiai = rs.getString("ten");
				this.ngaybanhanh = rs.getString("ngaybanhanh");
				this.loaimauin = rs.getString("loaimauin");
				this.bieumauPath = rs.getString("bieumau");
				this.bieumauName = this.bieumauPath.substring(this.bieumauPath.lastIndexOf("\\")+1,this.bieumauPath.length());
				this.trangthai = rs.getString("trangthai");
				
				rs.close();
			} catch (SQLException e) {}
		}
	}
	
	public boolean CheckUnique() {
		try{
			String query = "select count(*) as count from ERP_LOAIHOSO where MA='" + this.maLoaihoso + "'";
			if (this.id.length() > 0)
				query += " and PK_SEQ != '" + this.id + "'";
			
			ResultSet rs = this.db.get(query);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			if (count > 0)
				return false;
			
			return true;
		} catch(SQLException e){
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean create() {
		try {
			if (!CheckUnique()) {
				this.msg = "Mã loại hồ sơ này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into ERP_LOAIHOSO(ma,mabieumau,ten,ngaybanhanh,loaimauin,bieumau,trangthai,nguoitao,ngaytao,nguoisua,ngaysua,congty_fk)"
					+ " values('"+this.maLoaihoso+"','"+this.maBieumau+"',N'"+this.diengiai+"','"+this.ngaybanhanh+"','"+this.loaimauin+"',N'"+this.bieumauPath+"',"
					+ "'"+this.trangthai+"','"+this.userId+"','"+this.getDateTime()+"','"+this.userId+"','"+this.getDateTime()+"','"+this.congtyId+"')";
			
			if(!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_LOAIHOSO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
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
			if (!CheckUnique()) {
				this.msg = "Mã loại hồ sơ này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_LOAIHOSO set ma='"+this.maLoaihoso+"',mabieumau='"+this.maBieumau+"',ten=N'"+this.diengiai+"',ngaybanhanh='"+this.ngaybanhanh+"',"
					+ "loaimauin='"+this.loaimauin+"',bieumau=N'"+this.bieumauPath+"',trangthai='"+this.trangthai+"',nguoisua='"+this.userId+"',ngaysua='"+this.getDateTime()+"' where pk_seq = " + this.id;
			
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_LOAIHOSO: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
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
	
	public void DBClose(){
		try {
			this.db.shutDown();
		} catch (Exception e) {}
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMaLoaihoso() {
		return maLoaihoso;
	}

	public void setMaLoaihoso(String maLoaihoso) {
		this.maLoaihoso = maLoaihoso;
	}

	public String getMaBieumau() {
		return maBieumau;
	}

	public void setMaBieumau(String maBieumau) {
		this.maBieumau = maBieumau;
	}

	public String getDiengiai() {
		return diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getNgaybanhanh() {
		return ngaybanhanh;
	}

	public void setNgaybanhanh(String ngaybanhanh) {
		this.ngaybanhanh = ngaybanhanh;
	}

	public String getLoaimauin() {
		return loaimauin;
	}

	public void setLoaimauin(String loaimauin) {
		this.loaimauin = loaimauin;
	}

	public String getBieumauPath() {
		return bieumauPath;
	}

	public void setBieumauPath(String bieumauPath) {
		this.bieumauPath = bieumauPath;
	}

	public String getBieumauName() {
		return bieumauName;
	}

	public void setBieumauName(String bieumauName) {
		this.bieumauName = bieumauName;
	}
}
