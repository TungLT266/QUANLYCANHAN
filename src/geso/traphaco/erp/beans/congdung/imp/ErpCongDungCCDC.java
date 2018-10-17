package geso.traphaco.erp.beans.congdung.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.congdung.IErpCongDungCCDC;
public class ErpCongDungCCDC extends Phan_Trang implements IErpCongDungCCDC
{

	private static final long serialVersionUID = -4958137103080538665L;
	String Msg , Id , UserId , Ten , Ma , TrangThai , TaiKhoan, ctyId;
	ResultSet CongDungRs , TaiKhoanRs;
	dbutils db;
	
	public ErpCongDungCCDC( )
	{
		this.Id = "";
		this.UserId = "";
		this.Ten = "";
		this.Ma = "";
		this.TrangThai = "1";
		this.TaiKhoan = "";
		this.ctyId = "";
		this.Msg = "";
		this.db = new dbutils();
	}
	
	public ErpCongDungCCDC( String id )
	{
		this.Id = id;
		this.UserId = "";
		this.Ten = "";
		this.Ma = "";
		this.TrangThai = "1";
		this.TaiKhoan = "";
		this.Msg = "";
		this.ctyId = "";
		this.db = new dbutils();
		System.out.println("I am here, Sir");
	}
	
	public void setMsg(String msg)
	{
		this.Msg = msg;
	}
	
	public String getMsg()
	{
		return this.Msg;
	}
	
	public void setTrangThai(String trangthai)
	{
		this.TrangThai = trangthai;
	}
	
	public String getTrangThai()
	{
		return this.TrangThai;
	}
	
	public void setUserId(String userId)
	{
		this.UserId = userId;
	}
	
	public String getUserId()
	{
		return this.UserId;
	}
	
	public void setTen(String ten)
	{
		this.Ten = ten;
	}
	
	public String getTen()
	{
		return this.Ten;
	}
	
	public void setMa(String ma)
	{
		this.Ma = ma;
	}
	
	public String getMa()
	{
		return this.Ma;
	}
	
	public String getTaiKhoan()
	{
		return this.TaiKhoan;
	}
	
	public void setTaiKhoan(String taikhoan)
	{
		this.TaiKhoan = taikhoan;
	}
	
	public void setId(String id)
	{
		this.Id = id;
	}
	
	public String getId()
	{
		return this.Id;
	}
	
	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getCtyId()
	{
		return this.ctyId;
	}
	
	public boolean Update()
	{
		if (!CheckUnique())
		{
			this.Msg = "Mã  này đã có,vui lòng chọn mã khác";
			return false;
		}
		String query =
		"Update Erp_CongDungCCDC Set Ma='" + this.Ma + "',Ten=N'" + this.Ten + "',TaiKhoan_FK=" + this.TaiKhoan +
		",TrangThai='" + this.TrangThai + "',NguoiSua='"+this.UserId+"',NgaySua='"+getDateTime()+"' Where PK_SEQ=" + this.Id + "";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể cập nhập " + query;
			return false;
		}
		else
			return true;
	}
	
	public boolean Create()
	{
		if (!CheckUnique())
		{
			this.Msg = "Mã  này đã có,vui lòng chọn mã khác";
			return false;
		}
		String query =
		"Insert into Erp_CongDungCCDC(Ma,Ten,NgayTao,NgaySua,NguoiTao,NguoiSua,TrangThai,TaiKhoan_FK,CongTy_fk) values(" + "N'" +
		this.Ma + "',N'" + this.Ten + "','" + getDateTime() + "','" + getDateTime() + "'," + this.UserId + "," +
		this.UserId + "," + this.TrangThai + "," + this.TaiKhoan + " , "+ this.ctyId +")";
		System.out.println("Create" + query);
		if (this.db.update(query)) return true;
		else
		{
			this.Msg = "Không thể tạo mới " + query;
			return false;
		}
	}
	
	public boolean Search()
	{
		Utility util = new Utility();
		
		String query =
		" Select c.PK_SEQ,c.Ma,c.Ten,ISNULL(c.NgayTao,'')NgayTao,ISNULL(c.NgaySua,'')NgaySua,c.TaiKhoan_FK , \n"
		+ " ISNULL(nt.TEN,'') NguoiTao,ISNULL(ns.TEN,'') NguoiSua,ISNULL(t.TenTaiKhoan,'') TaiKhoan ,c.TrangThai \n"
		+ " From Erp_CongDungCCDC c " + " LEFT JOIN NhanVien nt on c.NguoiTao=nt.PK_SEQ \n"
		+ " LEFT JOIN NhanVien ns on ns.PK_SEQ=c.NguoiSua \n"
		+ " LEFT JOIN Erp_TaiKhoanKT t on t.PK_SEQ=c.TaiKhoan_FK \n" +
		  " WHERE c.congty_fk = "+ this.ctyId +" \n";
		if (this.Ma.length() > 0) query += " And c.Ma LIKE '%" + this.Ma + "%'\n";
		if (this.Ten.length() > 0) query += " And dbo.ftBoDau(c.Ten) LIKE N'%" + util.replaceAEIOU(this.Ten) + "%' \n";
		if (this.TaiKhoan.length() > 0) query += " And c.TaiKhoan_FK =" + this.TaiKhoan + "\n";
		
		System.out.println("Search:\n" + query + "\n===================================");
		
		this.CongDungRs = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query);
		return true;
	}
	
	public boolean Init()
	{
		String query = "Select PK_SEQ,Ma,Ten,TaiKhoan_FK,ISNULL(TrangThai,0)TrangThai From Erp_CongDungCCDC Where PK_SEQ= " + this.Id + "";
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.Ma = rs.getString("Ma");
					this.Ten = rs.getString("Ten");
					this.TaiKhoan = rs.getString("TaiKhoan_FK")==null?"": rs.getString("TaiKhoan_FK");
					this.TrangThai = rs.getString("TrangThai");
				}
			}
			catch (SQLException e)
			{
				this.Msg = "Không thể lấy dữ liệu" + query;
				return false;
			}
		}
		return true;
	}
	
	public ResultSet getTaiKhoanRs()
	{
		return this.TaiKhoanRs;
	}
	
	public void setTaiKhoanRs(ResultSet taikhoan)
	{
		this.TaiKhoanRs = taikhoan;
	}
	
	public ResultSet getCongDungRs()
	{
		return this.CongDungRs;
	}
	
	public void setCongDungRs(ResultSet congdung)
	{
		this.CongDungRs=congdung;
	}
	
	public void CreateRs()
	{
		String query = 	"Select PK_SEQ, SoHieuTaiKhoan As Ma, TenTaiKhoan as Ten \n" +
						"From Erp_TaiKhoanKT \n" +
						"Where TrangThai = 1 AND CONGTY_FK = " + this.ctyId + " and npp_fk = 1  \n" +
						"order by SoHieuTaiKhoan\n";
		
		this.TaiKhoanRs = this.db.get(query);
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void Close()
	{
		try
		{
			if (TaiKhoanRs != null) TaiKhoanRs.close();
			if (CongDungRs != null) CongDungRs.close();
		}
		catch (Exception e)
		{
			System.out.print("Exception  Close" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	public boolean CheckUnique()
	{
		String query = "";
		if (this.Id.length() > 0)
			query = "Select count(*) as count From Erp_CongDungCCDC Where MA = '" + this.Ma + "' AND PK_SEQ !='" + this.Id
					+ " '";
		else
			query = "Select count(*) as count From Erp_CongDungCCDC Where MA = '" + this.Ma + "' AND CONGTY_FK = " + this.ctyId + " ";
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				return false;
			}
		return true;
	}
}
