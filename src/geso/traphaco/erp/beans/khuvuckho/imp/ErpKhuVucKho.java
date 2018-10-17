package geso.traphaco.erp.beans.khuvuckho.imp;

import geso.traphaco.erp.beans.khuvuckho.IErpKhuVucKho;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpKhuVucKho implements IErpKhuVucKho
{
	String Msg, ctyId, Id, UserId, DienGiai, Ma, TrangThai, KhoId;
	ResultSet KhuVucKhoRs, KhoRs;
	dbutils db;

	public ErpKhuVucKho()
	{
		this.ctyId = "";
		this.Id = "";
		this.UserId = "";
		this.DienGiai = "";
		this.Ma = "";
		this.TrangThai = "";
		this.KhoId = "";
		this.Msg = "";
		this.db = new dbutils();
	}

	public ErpKhuVucKho(String id)
	{
		this.ctyId = "";
		this.Id = id;
		this.UserId = "";
		this.DienGiai = "";
		this.Ma = "";
		this.TrangThai = "";
		this.KhoId = "";
		this.Msg = "";
		this.db = new dbutils();
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getCtyId()
	{
		return this.ctyId;
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

	public void setDienGiai(String DienGiai)
	{
		this.DienGiai = DienGiai;
	}

	public String getDienGiai()
	{
		return this.DienGiai;
	}

	public void setMa(String ma)
	{
		this.Ma = ma;
	}

	public String getMa()
	{
		return this.Ma;
	}

	public String getKhoId()
	{
		return this.KhoId;
	}

	public void setKhoId(String KhoId)
	{
		this.KhoId = KhoId;
	}

	public void setId(String id)
	{
		this.Id = id;
	}

	public String getId()
	{
		return this.Id;
	}

	public boolean Update()
	{
		if (CheckUnique(" And PK_SEQ!='" + this.Id + "'") == false)
		{
			this.Msg = "Mã vị trí này đã có ";
			return false;
		}
		String query = "Update Erp_KhuVucKho Set Ma='" + this.Ma + "',TEN = N'" + this.DienGiai + "', DienGiai=N'" + this.DienGiai + "', KhoTT_FK="
				+ this.KhoId + ",TrangThai='" + this.TrangThai + "', NgaySua='" + getDateTime() + "',NguoiSua='"
				+ this.UserId + "'" + " Where PK_SEQ=" + this.Id + "";
		
		System.out.println("Update: " + query);
		if (!this.db.update(query))
		{
			this.Msg = "Không thể cập nhập " + query;
			return false;
		} else
			return true;
	}

	public boolean CheckUnique(String command)
	{
		String query = "Select count(*) as count From Erp_KhuVucKho Where MA=N'" + this.Ma + "' and khott_fk in (select pk_seq from erp_khott where trangthai = 1 and congty_fk = " + this.ctyId + " )";
		if (command.equals(""))
			command = query;
		else
			query += command;
		int count = 0;
		System.out.print("CheckUnique" + query);
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

	public boolean CheckReferences()
	{
		String query = "select count(*) as count from erp_vitrikho  Where Khu_FK='" + this.Id + "'";
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

	public boolean Create()
	{
		if (CheckUnique("") == false)
		{
			this.Msg = "Mã vị trí này đã có ";
			return false;
		}
		String query = "INSERT INTO ERP_KhuVucKHO(MA, TEN, DIENGIAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, KHOTT_FK) VALUES("
				+ "N'"+ this.Ma+ "', N'"+ this.DienGiai+ "', N'"+ this.DienGiai+ "','"+ getDateTime()+ "','"+ getDateTime()+ "',"
				+ this.UserId + "," + this.UserId + "," + this.TrangThai + "," + this.KhoId + " )";
		System.out.println("Create" + query);
		if (this.db.update(query))
			return true;
		else
		{
			this.Msg = "Không thể tạo mới " + query;
			return false;
		}
	}

	public boolean Delete()
	{

		if (CheckReferences() == false)
		{
			this.Msg = "Vị trí này đã được sử dụng,không thể xóa ";
			return false;
		}
		String query = "DELETE FROM ERP_KHUVUCKHO WHERE PK_SEQ =" + this.Id + "";
		System.out.println("Delete" + query);
		if (this.db.update(query))
			return true;
		else
		{
			this.Msg = "Không thể xóa " + query;
			return false;
		}
	}

	public boolean Search()
	{
		String query = 	"SELECT	KHUVUC.PK_SEQ, KHUVUC.MA, KHUVUC.DIENGIAI, " +
						"ISNULL(KHUVUC.NGAYTAO,'')NGAYTAO, " +
						"ISNULL(KHUVUC.NGAYSUA,'')NGAYSUA, " + 
						"ISNULL(NT.TEN,'') NGUOITAO, " +
						"ISNULL(NS.TEN,'') NGUOISUA, " +
						"KHOTT.TEN AS KHO, " +
						"KHUVUC.TRANGTHAI " +
						"FROM	ERP_KHUVUCKHO KHUVUC " +  
						"INNER  JOIN NHANVIEN NT on KHUVUC.NGUOITAO = NT.PK_SEQ " +  
						"INNER  JOIN NHANVIEN NS on NS.PK_SEQ =  KHUVUC.NGUOISUA " +
						"INNER JOIN ERP_KHOTT KHOTT ON KHOTT.PK_SEQ = KHUVUC.KHOTT_FK " +
						"WHERE KHUVUC.KHOTT_FK IN (SELECT PK_SEQ FROM ERP_KHOTT WHERE CONGTY_FK = " + this.ctyId + " ) ";

		
		if (this.Ma.length() > 0)
			query += " AND KHUVUC.MA LIKE '%" + this.Ma + "%'";

		if (this.DienGiai.length() > 0)
			query += " AND KHUVUC.DIENGIAI LIKE '%" + this.DienGiai + "%' ";


		if (this.KhoId.length() > 0)
			query += " AND KHOTT.PK_SEQ = '" + this.KhoId + "' ";
					
		query = query + "ORDER BY KHUVUC.MA, TRANGTHAI ";
		
		System.out.println("Search" + query);
		this.KhuVucKhoRs = this.db.get(query);

		return true;
	}

	public boolean Init()
	{
		String query = 	"SELECT PK_SEQ, MA, DIENGIAI, KHOTT_FK, ISNULL(TRANGTHAI,0)TRANGTHAI FROM ERP_KHUVUCKHO WHERE PK_SEQ= "	+ this.Id + " ";
		ResultSet rs = this.db.get("Init: " + query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					this.Ma = rs.getString("MA");
					this.DienGiai = rs.getString("DIENGIAI");
					this.TrangThai = rs.getString("TRANGTHAI");
					this.KhoId = rs.getString("KHOTT_FK");
				}
				rs.close();
			} catch (SQLException e)
			{
				this.Msg = "Không thể lấy dữ liệu" + query;
				return false;
			}
		}
		return true;
	}

	public ResultSet getKhoRs()
	{
		return this.KhoRs;
	}

	public void setKhoRs(ResultSet Kho)
	{
		this.KhoRs = Kho;
	}

	public ResultSet getKhuVucKhoRs()
	{
		return this.KhuVucKhoRs;
	}

	public void setKhuVucKhoRs(ResultSet KhuVucKho)
	{
	}

	public void CreateRs()
	{
		String query = "SELECT TEN,PK_SEQ FROM ERP_KHOTT WHERE TRANGTHAI = 1 AND QUANLYBIN = 1 AND CONGTY_FK = " + this.ctyId + " ";
		System.out.println(query);
		this.KhoRs = this.db.get(query);
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
			if (KhoRs != null)
				KhoRs.close();
			if (KhuVucKhoRs != null)
				KhuVucKhoRs.close();
			this.db.shutDown();
		} catch (Exception e)
		{
			System.out.print("Exception  Close" + e.getMessage());
			e.printStackTrace();
		}
	}
}
