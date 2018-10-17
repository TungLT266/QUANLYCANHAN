package geso.traphaco.erp.beans.daytruyensanxuat.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.daytruyensanxuat.IErpDaytruyensanxuat;

public class ErpDaytruyensanxuat implements IErpDaytruyensanxuat
{
	String userId;
	String congtyId;
	String id;
	
	String ma;
	String diengiai;
	
	String trangthai;
	String nmId;
	String msg;
	ResultSet nhamayRs;
	ResultSet gioRs;
	String tugio;

	String dengio;
	
	String nghitruatu;
	String nghitruaden;
	dbutils db;
	
	public ErpDaytruyensanxuat()
	{
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.tugio = "";
		this.dengio = "";
		this.nghitruatu = "";
		this.nghitruaden = "";
		this.nmId = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public ErpDaytruyensanxuat(String id)
	{
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.tugio = "";
		this.dengio = "";
		this.nghitruatu = "";
		this.nghitruaden = "";
		this.msg = "";
		this.db = new dbutils();
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getNhamayId() 
	{
		return this.nmId;
	}

	public void setNhamayId(String nmId) 
	{
		this.nmId = nmId;
	}

	public String getTugio() 
	{
		return this.tugio;
	}

	public void setTugio(String tugio) 
	{
		this.tugio = tugio;
	}

	public String getDengio() 
	{
		return this.dengio;
	}

	public void setDengio(String dengio) 
	{
		this.dengio = dengio;
	}

	public String getNghitruaTu() 
	{
		return this.nghitruatu;
	}

	public void setNghitruaTu(String nghitruatu) 
	{
		this.nghitruatu = nghitruatu;
	}

	public String getNghitruaDen() 
	{
		return this.nghitruaden;
	}

	public void setNghitruaDen(String nghitruaden) 
	{
		this.nghitruaden = nghitruaden;
	}

	public ResultSet getNhamayRs() 
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nhamayRs) 
	{
		this.nhamayRs = nhamayRs;
	}

	public ResultSet getGioRs() 
	{
		return this.gioRs;
	}

	public void setGioRs(ResultSet gioRs) 
	{
		this.gioRs = gioRs;
	}

	public void init() 
	{
		String query = 	"SELECT MADAYTRUYENSX, TENDAYTRUYENSX, TRANGTHAI, NHAMAY_FK AS NMID, ISNULL(TUGIO, 0) AS TUGIO, ISNULL(DENGIO, 0) AS DENGIO, \n " +
						"ISNULL(BDNGHITRUA, 0) AS NGHITRUATU, ISNULL(KTNGHITRUA, 0) AS NGHTRUADEN \n " +
						"FROM ERP_DAYTRUYENSANXUAT \n " +
						"WHERE PK_SEQ = '" + this.id + "'";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ma =  rs.getString("MADAYTRUYENSX");
					this.diengiai = rs.getString("TENDAYTRUYENSX");
					this.trangthai = rs.getString("TRANGTHAI");
					this.nmId = rs.getString("NMID");
					this.tugio =  rs.getString("TUGIO");
					this.dengio =  rs.getString("DENGIO");
					this.nghitruatu = rs.getString("NGHITRUATU");
					this.nghitruaden = rs.getString("NGHTRUADEN");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		this.createRs();
	}
	
	public void createRs(){
		
		this.nhamayRs = db.get( "SELECT PK_SEQ AS NMID, MANHAMAY + ' - ' + TENNHAMAY AS TEN \n " +
								"FROM ERP_NHAMAY  \n " +
								"WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + "  \n ");	
		
		
		String query = "SELECT PK_SEQ AS ID, GIO FROM ERP_GIOCHUAN ORDER BY PK_SEQ ";
		this.gioRs = this.db.getScrol(query);
	}
	
	public boolean createDaytruyensanxuat()
	{	
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã dây truyền sản xuất";
				return false;
			}
			
			if(this.nmId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà máy";
				return false;
			}

			db.getConnection().setAutoCommit(false);
			
			String query = "INSERT INTO ERP_DAYTRUYENSANXUAT(BDNGHITRUA, KTNGHITRUA, TUGIO, DENGIO, MADAYTRUYENSX, TENDAYTRUYENSX, NHAMAY_FK, CONGTY_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA) " +
							"VALUES(" + this.nghitruatu + ", " + this.nghitruaden + ", " + this.tugio + ", " + this.dengio + ", '" + this.ma + "', N'" + this.diengiai + "', N'" + this.nmId + "', '" + this.congtyId + "', '" + this.trangthai + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_DAYTRUYENSANXUAT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean updateDaytruyensanxuat() 
	{
		try 
		{
			if(this.ma.trim().length() <= 0)
			{
				this.msg = "Vui lòng nhập mã dây truyền sản xuất";
				return false;
			}
			
			
			if(this.nmId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà máy";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query =  "UPDATE ERP_DAYTRUYENSANXUAT SET BDNGHITRUA = " + this.nghitruatu + ", KTNGHITRUA = " + this.nghitruaden + ", " +
							"TUGIO = " + this.tugio + ", DENGIO = " + this.dengio + ", MADAYTRUYENSX = '" + this.ma + "', " +
							"TENDAYTRUYENSX = N'" + this.diengiai + "', \n " +
							"NHAMAY_FK = N'" + this.nmId + "', CONGTY_FK = '" + this.congtyId + "', trangthai = '" + this.trangthai + "',  \n " +
							"NGAYSUA = '" + this.getDateTime() + "', NGUOISUA = '" + this.userId + "' WHERE PK_SEQ = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DAYTRUYENSANXUAT " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	
	public void DbClose() 
	{
		try 
		{
			if(this.gioRs != null) this.gioRs.close();
			if(this.nhamayRs != null) this.nhamayRs.close();
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}

	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


}
