package geso.traphaco.erp.beans.loaitaikhoan.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaitaikhoan.IErpLoaiTaiKhoan;
import geso.traphaco.erp.util.UtilitySyn;

public class ErpLoaiTaiKhoan  extends Phan_Trang implements IErpLoaiTaiKhoan
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7098075456466668852L;
	String id;
	String Ten;
	String trangthai;
	String Ma;
	String ngaytao;
	String ngaysua;
	String userid;
	String msg;
	String nppId;
	String congtyId;
	ResultSet rsLoaiTaiKhoan;
	dbutils db;
	
	String chixem;

	public ErpLoaiTaiKhoan()
	{
		this.id = "";
		this.Ten = "";
		this.trangthai = "";
		this.Ma = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.userid = "";
		this.msg = "";
		this.nppId = "";
		this.congtyId = "";
		this.chixem = "0";
		this.db = new dbutils();
	}

	public ErpLoaiTaiKhoan(String id)
	{
		this.id = id;
		this.Ten = "";
		this.trangthai = "";
		this.Ma = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.userid = "";
		this.msg = "";
		this.chixem = "0";
		this.nppId = "";
		this.congtyId = "";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTen()
	{
		return this.Ten;
	}

	public void setTen(String Ten)
	{
		this.Ten = Ten;
	}

	public void setMa(String Ma)
	{
		this.Ma = Ma;
	}

	public String getMa()
	{
		return this.Ma;
	}

	public String getNgayTao()
	{
		return this.ngaytao;
	}

	public String getNgaySua()
	{
		return this.ngaysua;
	}

	public void setNgayTao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}

	public void setNgaySua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}

	public String getUserId()
	{
		return this.userid;
	}

	public void setUserId(String userid)
	{
		this.userid = userid;
	}

	public String getTrangThai()
	{
		return trangthai;
	}

	public void setTrangThai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public void Init()
	{
		this.rsLoaiTaiKhoan = null;
		PreparedStatement prep = null;
		String query = "Select PK_SEQ,Ma,isnull(Ten,'')Ten,NgaySua,isnull(TrangThai,0)TrangThai From Erp_LoaiTaiKhoan Where PK_SEQ=?";
		try
		{
			prep = this.db.getConnection().prepareStatement(query);
			prep.setString(1, this.id);
			this.rsLoaiTaiKhoan = prep.executeQuery();
			while (rsLoaiTaiKhoan.next())
			{
				this.id = rsLoaiTaiKhoan.getString("PK_SEQ");
				this.Ma = rsLoaiTaiKhoan.getString("Ma");
				this.Ten = rsLoaiTaiKhoan.getString("Ten");
				this.ngaysua = rsLoaiTaiKhoan.getString("NgaySua");
				this.trangthai = rsLoaiTaiKhoan.getString("TrangThai");
			}
			rsLoaiTaiKhoan.close();
			prep.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void search()
	{
		Utility util = new Utility();
		
		String query = " Select l.PK_SEQ ,l.Ma,l.Ten,ISNULL(nt.NguoiTao,'')NguoiTao ,ISNULL(ns.NguoiSua,'')NguoiSua,ISNULL(l.NgayTao,'')NgayTao,"
			+ " ISNULL(l.NgaySua,'')NgaySua,ISNULL(l.TrangThai,0)TrangThai "
			+ " From Erp_LoaiTaiKhoan l "
			+ " INNER JOIN "
			+ " ( Select PK_SEQ,Ten as NguoiTao "
			+ " From NHANVIEN)nt on nt.PK_SEQ=l.NGUOITAO "
			+ " INNER Join"
			+ " ( Select PK_SEQ ,Ten as NguoiSua " + " From NhanVien )ns on ns.PK_SEQ=l.NGUOISUA Where 1=1 ";
		System.out.println("Search" + query);
		if (this.Ma.length() > 0)
		{
			query += " and  l.Ma like N'%" + this.Ma + "%' ";
		}
		if (this.Ten.length() > 0)
		{
			query += " and dbo.ftBoDau(l.Ten) like N'%" + util.replaceAEIOU(this.Ten) + "%'";
		}
		this.rsLoaiTaiKhoan =createSplittingDataNew(this.db, 50, 10, " MA  ", query); 
		System.out.println("Search LoaiTaiKhoan : " + query);
	}

	public boolean Create()
	{
		if(!CheckValid())
		{
			return false;
		}
		String query = "";
		try
		{
			query = "Insert into Erp_LoaiTaiKhoan(Ma,Ten,NgayTao,NgaySua,NguoiTao,NguoiSua,TrangThai) values(" + "N'" +
				this.Ma + "',N'" + this.Ten + "','" + getDateTime() + "','" + getDateTime() + "','" + this.userid + "','" +
				this.userid + "' ,'" + this.trangthai + "')";
		

			String currentId = "";
			System.out.println("Create Query " + query);
			this.db.getConnection().setAutoCommit(false);
			if (this.db.update(query))
			{
				System.out.println("Tao duoc Erp_LoaiTaiKhoan ");

				query = "Select IDENT_CURRENT('Erp_LoaiTaiKhoan') as currentId";
				ResultSet rsId = this.db.get(query);
				if (rsId != null)
				{
					while (rsId.next())
					{
						currentId = rsId.getString("currentId");
						System.out.println("ID" + currentId);
					}
					rsId.close();
				}
			} else
			{
				this.msg = "Không thể tạo mới nhóm tài khoản kế toán :" + query;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "Erp_LoaiTaiKhoan", "Erp_LoaiTaiKhoan", "PK_SEQ", currentId, "0", false);
			
			return true;
		} catch (SQLException e)
		{
			this.db.update("rollback");
			this.msg = "Không thể tạo mới nhóm tài khoản kế toán :" + query;
			this.db.shutDown();
			System.out.println("Exception");
			return false;
		}
	}

	public boolean Update()
	{
		
		if(!CheckValid())
		{
			return false;
		}
		String query = "Update Erp_LoaiTaiKhoan set Ma=N'" + this.Ma + "',Ten=N'" + this.Ten + "',NgaySua='" +
			getDateTime() + "',NguoiSua='" + this.userid + "',TrangThai='" + this.trangthai + "" + "' Where PK_SEQ='" +this.id + "'";
		
		System.out.println("update Loai tai khoan: " + query);
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if(!this.db.update(query))
			{
				this.msg = "Không thể cập nhật:" + query;
				this.db.update("rollback");
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "Erp_LoaiTaiKhoan", "Erp_LoaiTaiKhoan", "PK_SEQ", this.id, "1", false);
			
			return true;
		} catch (SQLException e)
		{
			this.msg = "Không thể cập nhật:" + query;
			this.db.update("rollback");
			System.out.println(e.getMessage());
			this.db.shutDown();
		}
		return false;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public ResultSet getRsLoaiTaiKhoan()
	{
		return this.rsLoaiTaiKhoan;
	}

	public void setRsLoaiTaiKhoan(ResultSet rsloaitaikhoan)
	{
		this.rsLoaiTaiKhoan = rsloaitaikhoan;
	}

	
	public boolean DeleteLtk()
	{		
		String query="DELETE FROM ERP_LOAITAIKHOAN WHERE PK_SEQ='"+this.id+"'";
			if(!this.db.update(query))
			{
				this.msg="Không thể xóa loại tài khoản này "+query;
				return false;
			}
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "Erp_LoaiTaiKhoan", "Erp_LoaiTaiKhoan", "PK_SEQ", this.id, "2", true); // XOA THANG
			
		return false;
	}
	
	public boolean CheckValid()
	{
		String query="";
		
		if(this.id.length() > 0)
			query="Select count(*) sodong FROM Erp_LoaiTaiKhoan WHERE Ma=N'"+this.Ma+"' and pk_seq <> '" + this.id + "'";
		else
			query="Select count(*) sodong FROM Erp_LoaiTaiKhoan WHERE Ma=N'"+this.Ma+"'";
		
		try
		{
			System.out.println("CheckValid Erp_LoaiTaiKhoan : " + query);
			int sodong = 0;
			ResultSet rs = this.db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
				System.out.println("So dong la: " + sodong + "\n");
				if(sodong > 0)
				{
					this.msg="Mã loại tài khoản này đã có vui lòng chọn mã khác";
					return false;
				}
			}else
				return false;
		} catch (SQLException e)
		{
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public void closeDB(){
		try{
			if(this.rsLoaiTaiKhoan != null) this.rsLoaiTaiKhoan.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
		
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getcongtyId() {
		
		return this.congtyId;
	}

	
	public void setcongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}
	
}
