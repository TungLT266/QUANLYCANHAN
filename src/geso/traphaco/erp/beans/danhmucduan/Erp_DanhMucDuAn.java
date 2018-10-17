package geso.traphaco.erp.beans.danhmucduan;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Erp_DanhMucDuAn
{
	private String id;
	private String ma;
	private String ten;
	private String trangThai;
	private String ngayTao;
	private String ngaySua;
	private String nguoiTao;
	private String nguoiSua;
	private String congTyId;
	private String msg;
	
	private dbutils db;

	public Erp_DanhMucDuAn()
	{
		id = "";
		this.ma = "";
		this.ten = "";
		this.trangThai = "";
		this.ngayTao = "";
		this.ngaySua = "";
		this.nguoiTao = "";
		this.nguoiSua = "";
		this.congTyId = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init()
	{
		if (this.id.trim().length() > 0)
		{
			String query = "select TEN, MA, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA from ERP_DANHMUCDUAN where PK_SEQ = " + this.id;
			
			ResultSet rs = null;
			
			try {
				rs = this.db.get(query);
				
				if (rs != null)
					if (rs.next())
					{
						this.ten = rs.getString("TEN");
						this.ma = rs.getString("MA");
						this.ngayTao = rs.getString("NGAYTAO");
						this.ngaySua = rs.getString("NGAYSUA");
						this.nguoiTao = rs.getString("NGUOITAO");
						this.nguoiSua = rs.getString("NGUOISUA");
					}
			} catch (Exception e) {
				e.printStackTrace();
			}
			finally{
				if (rs != null)
					try {
						rs.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			
		}
	}
	
	private boolean kiemTraDuLieu()
	{
		if (this.ma == null || this.ma.trim().length() == 0)
		{
			this.msg = "KTDL1.1 Vui lòng nhập 'Mã'\n";
			return false;
		}
		
		if (this.ten == null || this.ten.trim().length() == 0)
		{
			this.msg = "KTDL1.2 Vui lòng nhập 'Tên'\n";
			return false;
		}
		
		return true;
	}
	
	public boolean create() {
		try {
			this.db.getConnection().setAutoCommit(false);
			
			if (!kiemTraDuLieu())
			{
				this.db.getConnection().rollback();
				return false;
			}
			
			this.ngayTao = Utility.getCurrentDay();
			
			String query = "insert into ERP_DANHMUCDUAN(TRANGTHAI, MA, TEN, NGUOITAO" +
					", NGAYTAO, CONGTY_FK) \n" +
					"values('0', N'" + this.ma + "', N'" + this.ten + "', " + this.nguoiTao + "" +
					", '" + this.ngayTao + "', " + this.congTyId + ")";
			if (!this.db.update(query))
			{
				this.msg = "N1.1 Không thể tạo mới danh mục dự án";
				this.db.getConnection().rollback();
				return false;
			}
			
			
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean edit() {
		try {
			this.db.getConnection().setAutoCommit(false);
			this.ngaySua = Utility.getCurrentDay();
			
			if (!kiemTraDuLieu())
			{
				this.db.getConnection().rollback();
				return false;
			}
			
			String query = 
				"update ERP_DANHMUCDUAN set MA = N'" + this.ma + "', TEN = N'" + this.ten + "'\n" +
				", NGAYSUA = '" + this.ngaySua + "'\n" +
				", NGUOISUA = " + this.nguoiSua + " \n" +
				"where PK_SEQ = " + this.id;
			
			if (!this.db.update(query))
			{
				this.msg = "E1.1 Không thể sửa danh mục dự án";
				this.db.getConnection().rollback();
				return false;
			}
			
			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "E1 Không thể sửa danh mục dự án";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean delete()
	{
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DANHMUCDUAN set trangThai = 2 where trangThai = 0 and PK_SEQ = " + this.id;
			int num = this.db.updateReturnInt(query);
			if (num < 1)
			{
				this.msg = "D1.1 Không thể xóa danh mục dự án";
				this.db.getConnection().rollback();
				return false;
			}

			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "D1 Không thể xóa danh mục dự án";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public boolean chot() {
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DANHMUCDUAN set trangThai = 1 where trangThai = 0 and PK_SEQ = " + this.id;
			int num = this.db.updateReturnInt(query);
			if (num < 1)
			{
				this.msg = "U1.1 Không thể chốt danh mục dự án";
				this.db.getConnection().rollback();
				return false;
			}

			this.db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "U1 Không thể chốt danh mục dự án";
			return false;
		}
		finally
		{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	public void DbClose()
	{
		if (this.db != null)
			this.db.shutDown();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public String getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(String ngayTao) {
		this.ngayTao = ngayTao;
	}

	public String getNgaySua() {
		return ngaySua;
	}

	public void setNgaySua(String ngaySua) {
		this.ngaySua = ngaySua;
	}

	public String getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(String nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	public String getNguoiSua() {
		return nguoiSua;
	}

	public void setNguoiSua(String nguoiSua) {
		this.nguoiSua = nguoiSua;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getMa() {
		return ma;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTen() {
		return ten;
	}
}