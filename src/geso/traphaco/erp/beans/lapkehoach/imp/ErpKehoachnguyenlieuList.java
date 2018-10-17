package geso.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.erp.beans.lapkehoach.IErpKehoachnguyenlieuList;
import geso.dms.db.sql.dbutils;

public class ErpKehoachnguyenlieuList implements IErpKehoachnguyenlieuList 
{
	String ctyId;
	String userId;
	String tungay;
	String denngay;
	String diengiai;

	String msg;
	
	ResultSet khnlRs;
	
	dbutils db;
	
	public ErpKehoachnguyenlieuList()
	{
		this.ctyId = "";
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.diengiai = "";
		
		this.msg = "";
		
		this.db = new dbutils();
	}

	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
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
			sql = 	"SELECT  KHNL.PK_SEQ, KHNL.NGAYLAP, NV.TEN AS NGUOITAO, ISNULL(LOAISP.TEN, N'Không chọn') AS LOAISP, " +
					"SOSP.NUM " +
					"FROM ERP_KEHOACHNGUYENLIEU KHNL " +
					"LEFT JOIN ERP_LOAISANPHAM LOAISP ON LOAISP.PK_SEQ = KHNL.LOAISANPHAM_FK " +
					"LEFT JOIN ( " +
					"	SELECT COUNT(DISTINCT SANPHAM_FK) AS NUM, KEHOACH_FK AS KHID " + 
					"	FROM ERP_KEHOACHNGUYENLIEU_SANPHAM GROUP BY KEHOACH_FK " +  
					")SOSP ON SOSP.KHID = KHNL.PK_SEQ " +
					"INNER JOIN NHANVIEN NV ON KHNL.NGUOITAO = NV.PK_SEQ " + 
					"WHERE KHNL.CONGTY_FK = " + this.ctyId + " " +
					"ORDER BY KHNL.PK_SEQ DESC" ;
		}
		
		System.out.println("__Ke hoach tong the: " + sql);
		this.khnlRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.khnlRs != null)
				this.khnlRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getKehoachnguyenlieuRs() 
	{
		return this.khnlRs;
	}

	public void setKehoachnguyenlieuRs(ResultSet khnlRs) 
	{
		this.khnlRs = khnlRs;
	}

}
