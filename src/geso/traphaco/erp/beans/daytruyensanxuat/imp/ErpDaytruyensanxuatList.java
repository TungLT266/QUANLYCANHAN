package geso.traphaco.erp.beans.daytruyensanxuat.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.erp.beans.daytruyensanxuat.IErpDaytruyensanxuatList;
import geso.traphaco.center.db.sql.dbutils;

public class ErpDaytruyensanxuatList implements IErpDaytruyensanxuatList 
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	String nmId;
	ResultSet daytruyensanxuatRs;
	ResultSet nhamayRs;
	
	dbutils db;
	
	public ErpDaytruyensanxuatList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		this.nmId = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql = "SELECT DTSX.PK_SEQ, DTSX.MADAYTRUYENSX, DTSX.TENDAYTRUYENSX, NM.MANHAMAY, NM.TENNHAMAY, DTSX.TRANGTHAI,  \n " +
				  "NV1.TEN as NGUOITAO, DTSX.NGAYTAO, NV2.TEN as NGUOISUA, DTSX.NGAYSUA \n " +
				  "FROM ERP_DAYTRUYENSANXUAT DTSX \n " +
				  "INNER JOIN NHANVIEN NV1 on DTSX.NGUOITAO = NV1.PK_SEQ \n " +
				  "INNER JOIN NHANVIEN NV2 on DTSX.NGUOISUA = NV2.PK_SEQ \n " +
				  "INNER JOIN ERP_NHAMAY NM ON NM.PK_SEQ = DTSX.NHAMAY_FK \n " +
				  "WHERE DTSX.CONGTY_FK = " + this.congtyId + " \n " +
				  "ORDER BY DTSX.PK_SEQ DESC ";
		}
		
		System.out.println("__DAY TRUYEN SAN XUAT : " + sql);
		this.daytruyensanxuatRs = db.get(sql);
		
		this.nhamayRs = db.get( "SELECT PK_SEQ AS NMID, MANHAMAY + ' - ' + TENNHAMAY AS TEN \n " +
								"FROM ERP_NHAMAY  \n " +
								"WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.congtyId + "  \n ");
	}

	public void DbClose() 
	{
		try 
		{
			if(this.daytruyensanxuatRs != null)
				this.daytruyensanxuatRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getDaytruyensanxuatRs() 
	{
		return this.daytruyensanxuatRs;
	}

	public void setDaytruyensanxuatRs(ResultSet daytruyensanxuatRs) 
	{
		this.daytruyensanxuatRs = daytruyensanxuatRs;
	}

	public ResultSet getNhamayRs() 
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nhamayRs) 
	{
		this.nhamayRs = nhamayRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public String getNhamayId() 
	{
		return this.nmId;
	}

	public void setNhamayId(String nmId) 
	{
		this.nmId = nmId;
	}


}
