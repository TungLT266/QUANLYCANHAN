package geso.traphaco.erp.beans.congty.imp;

import geso.traphaco.erp.beans.congty.IErpCongTyList;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpCongTyList implements IErpCongTyList
{
	String ID, MA, TEN, DIACHI, MASOTHUE, NGANHNGHEKINHDOANH, DIENTHOAI, FAX, GIAMDOC, KETOANTRUONG, USERID, TRANGTHAI, congtyId;
	dbutils db;
	String msg;
	ResultSet rsCongtyList;
	
	public ErpCongTyList()
	{
		this.db = new dbutils();
		this.ID = "";
		this.DIACHI = "";
		this.DIENTHOAI = "";
		this.FAX = "";
		this.GIAMDOC = "";
		this.KETOANTRUONG = "";
		this.MA = "";
		this.MASOTHUE = "";
		this.NGANHNGHEKINHDOANH = "";
		this.USERID = "";
		this.TEN = "";
		this.msg = "";
		this.TRANGTHAI = "";
		this.db = new dbutils();
	}
	
	public String getID()
	{
		return this.ID;
	}
	
	public void setID(String pk_seo)
	{
		this.ID = pk_seo;
	}
	
	public String getMA()
	{
		return this.MA;
	}
	
	public void setMA(String ma)
	{
		this.MA = ma;
	}
	
	public String getTEN()
	{
		return this.TEN;
	}
	
	public void setTEN(String ten)
	{
		this.TEN = ten;
	}
	
	public String getDIACHI()
	{
		return this.DIACHI;
	}
	
	public void setDIACHI(String diachi)
	{
		this.DIACHI = diachi;
	}
	
	public String getMASOTHUE()
	{
		return this.MASOTHUE;
	}
	
	public void setMASOTHUE(String masothue)
	{
		this.MASOTHUE = masothue;
	}
	
	public String getNGANHNGHEKINHDOANH()
	{
		return this.NGANHNGHEKINHDOANH;
	}
	
	public void setNGANHNGHEKINHDOANH(String nganhnghekinhdoanh)
	{
		this.NGANHNGHEKINHDOANH = nganhnghekinhdoanh;
	}
	
	public String getDIENTHOAI()
	{
		return this.DIENTHOAI;
	}
	
	public void setDIENTHOAI(String dienthoai)
	{
		this.DIENTHOAI = dienthoai;
	}
	
	public String getFAX()
	{
		return this.FAX;
	}
	
	public void setFAX(String fax)
	{
		this.FAX = fax;
	}
	
	public String getGIAMDOC()
	{
		return this.GIAMDOC;
	}
	
	public void setGIAMDOC(String giamdoc)
	{
		this.GIAMDOC = giamdoc;
	}
	
	public String getKETOANTRUONG()
	{
		return this.KETOANTRUONG;
	}
	
	public void setKETOANTRUONG(String ketoantruong)
	{
		this.KETOANTRUONG = ketoantruong;
	}
	
	public String getTRANGTHAI()
	{
		return this.TRANGTHAI;
	}
	
	public void setTRANGTHAI(String trangthai)
	{
		this.TRANGTHAI = trangthai;
	}
	
	public void init(String query)
	{
		String sql = "";
		if (query.length() == 0)
			sql =
			"select  *,NV.TEN as TENNV,NV.PK_SEQ as MANV,NV2.TEN as TENNVS,NV2.PK_SEQ as MANVS " +
			"from ERP_CONGTY ct " +
			"inner join NHANVIEN nv on ct.nguoitao = nv.PK_SEQ " +
			"inner join NHANVIEN nv2 on ct.NGUOISUA = nv2.PK_SEQ where ct.trangthai=1";
		else
			sql = query;
		System.out.println("sql = " + sql);
		this.rsCongtyList = this.db.get(sql);
	}
	
	public ResultSet getCongtyList()
	{
		return this.rsCongtyList;
	}
	
	public void closeDB()
	{
			try
			{
				if (this.rsCongtyList != null) this.rsCongtyList.close();
				if (this.db != null) this.db.shutDown();
			}
			catch (SQLException e)
			{
				this.db.shutDown(); 
				e.printStackTrace();
			}
	
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public String getUserId()
	{
		return this.USERID;
	}
	
	public void setUserId(String userId)
	{
		this.USERID = userId;
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
