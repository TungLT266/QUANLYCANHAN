package geso.traphaco.erp.beans.khuvuckho.imp;

import geso.traphaco.erp.beans.khuvuckho.IErpViTriKho;
import geso.traphaco.erp.db.sql.dbutils;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpViTriKho implements IErpViTriKho
{
	String Msg, ctyId, Id, UserId, DienGiai, Ma, TrangThai, KhoId, kvId;
	ResultSet KhuVucKhoRs, KhoRs, vitriRs;
	dbutils db;

	public ErpViTriKho()
	{
		this.ctyId = "";
		this.Id = "";
		this.UserId = "";
		this.DienGiai = "";
		this.Ma = "";
		this.TrangThai = "";
		this.KhoId = "";
		this.kvId = "";
		this.Msg = "";
		this.db = new dbutils();
	}

	public ErpViTriKho(String id)
	{
		this.ctyId = "";
		this.Id = id;
		this.UserId = "";
		this.DienGiai = "";
		this.Ma = "";
		this.TrangThai = "";
		this.KhoId = "";
		this.kvId = "";
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
		if( this.Ma.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng nhập mã";
			return false;
		}	
		
		if( this.DienGiai.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng nhập tên";
			return false;
		}	
		
		if( this.KhoId.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng chọn kho";
			return false;
		}	
		
		if( this.kvId.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng chọn khu vực";
			return false;
		}	
		
		if (CheckUnique(" And PK_SEQ != '" + this.Id + "'") == false)
		{
			this.Msg = "Mã vị trí này đã có ";
			return false;
		}
		
		String query =  "Update ERP_BIN Set Ma = '" + this.Ma + "', TEN = N'" + this.DienGiai + "', KhoTT_FK = "+ this.KhoId + ", KHUVUC_FK = '" + this.kvId + "', TrangThai='" + this.TrangThai + "', " + 
						" NgaySua = '" + getDateTime() + "', NguoiSua = '"+ this.UserId + "'" + 
						" Where PK_SEQ=" + this.Id + "";
		
		System.out.println("Update: " + query);
		if (!this.db.update(query))
		{
			this.Msg = "Không thể cập nhập " + query;
			return false;
		} 
		else
			return true;
	}

	public boolean CheckUnique(String command)
	{
		String query = " Select count(*) as count From ERP_BIN " + 
					   " Where MA = N'" + this.Ma + "' and khott_fk = '" + this.KhoId + "' and khuvuc_fk = '" + this.kvId + "' ";
		if (command.equals(""))
			command = query;
		else
			query += command;
		
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} 
			catch (Exception e)
			{
				return false;
			}
		}
		
		return true;
	}

	public boolean CheckReferences()
	{
		String query = "select count(*) as count from ERP_KHOTT_SP_CHITIET  Where BIN_FK = '" + this.Id + "'";
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
		} 
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	public boolean Create()
	{
		if( this.Ma.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng nhập mã";
			return false;
		}	
		
		if( this.DienGiai.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng nhập tên";
			return false;
		}	
		
		if( this.KhoId.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng chọn kho";
			return false;
		}	
		
		if( this.kvId.trim().length() <= 0 )
		{
			this.Msg = "Vui lòng chọn khu vực";
			return false;
		}	
			
		if (CheckUnique("") == false)
		{
			this.Msg = "Mã vị trí này đã có ";
			return false;
		}
		
		String query = "INSERT INTO ERP_BIN(MA, TEN,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, KHOTT_FK, KHUVUC_FK ) " + 
					   " VALUES( N'" + this.Ma + "', N'"+ this.DienGiai+ "', '"+ getDateTime()+ "', '"+ getDateTime()+ "', "+ this.UserId + "," + this.UserId + "," + this.TrangThai + ", " + this.KhoId + ", '" + this.kvId + "' )";
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
		String query = "DELETE FROM ERP_BIN WHERE PK_SEQ =" + this.Id + "";
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
		String query = 	"SELECT	BIN.PK_SEQ, BIN.MA, BIN.TEN, BIN.NGAYTAO, BIN.NGAYSUA, ISNULL(NT.TEN,'') NGUOITAO, ISNULL(NS.TEN,'') NGUOISUA, " +
						"	KHOTT.TEN AS KHO, KV.TEN as KHUVUC, BIN.TRANGTHAI " +
						"FROM	ERP_BIN BIN " +  
						"INNER  JOIN NHANVIEN NT on BIN.NGUOITAO = NT.PK_SEQ " +  
						"INNER  JOIN NHANVIEN NS on NS.PK_SEQ =  BIN.NGUOISUA " +
						"INNER JOIN ERP_KHOTT KHOTT ON KHOTT.PK_SEQ = BIN.KHOTT_FK " +
						"INNER JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ = BIN.KHUVUC_FK " +
						"WHERE BIN.KHOTT_FK IN (SELECT PK_SEQ FROM ERP_KHOTT WHERE CONGTY_FK = " + this.ctyId + " ) ";

		if (this.Ma.length() > 0)
			query += " AND BIN.MA LIKE '%" + this.Ma + "%'";

		if (this.DienGiai.length() > 0)
			query += " AND BIN.DIENGIAI LIKE '%" + this.DienGiai + "%' ";


		if (this.KhoId.length() > 0)
			query += " AND KHOTT.PK_SEQ = '" + this.KhoId + "' ";
		
		if (this.kvId.length() > 0)
			query += " AND KV.PK_SEQ = '" + this.kvId + "' ";
					
		query = query + "ORDER BY BIN.MA, BIN.TRANGTHAI ";
		
		System.out.println("Search: " + query);
		this.vitriRs = this.db.get(query);

		return true;
	}

	public boolean Init()
	{
		String query = 	"SELECT PK_SEQ, MA, TEN, KHUVUC_FK, KHOTT_FK, TRANGTHAI FROM ERP_BIN WHERE PK_SEQ= "	+ this.Id + " ";
		ResultSet rs = this.db.get("Init: " + query);
		if (rs != null)
		{
			try
			{
				if (rs.next())
				{
					this.Ma = rs.getString("MA");
					this.DienGiai = rs.getString("TEN");
					this.TrangThai = rs.getString("TRANGTHAI");
					this.KhoId = rs.getString("KHOTT_FK");
					this.kvId = rs.getString("KHUVUC_FK");
				}
				rs.close();
			} 
			catch (Exception e)
			{
				e.printStackTrace();
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
		String query = "SELECT TEN, PK_SEQ FROM ERP_KHOTT WHERE TRANGTHAI = 1 AND QUANLYBIN = 1 AND CONGTY_FK = " + this.ctyId + " ";
		System.out.println("::: INIT KHO: " + query);
		this.KhoRs = this.db.get(query);
		
		if( this.KhoId.trim().length() > 0 )
		{
			query = "select PK_SEQ, MA, TEN from ERP_KHUVUCKHO where KHOTT_FK = '" + this.KhoId + "' order by TEN asc";
			System.out.println("::: INIT KHU VUC: " + query);
			this.KhuVucKhoRs = this.db.get(query);
		}
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

	
	public void setKhuvucId(String khuvucId) 
	{
		this.kvId = khuvucId;
	}

	public String getKhuvucId() 
	{
		return this.kvId;
	}

	public ResultSet getVitriRs() 
	{
		return this.vitriRs;
	}

	public void setVitriRs(ResultSet vitriRs) 
	{
		this.vitriRs = vitriRs;
	}
}
