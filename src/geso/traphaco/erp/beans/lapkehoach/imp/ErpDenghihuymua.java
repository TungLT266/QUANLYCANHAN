package geso.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.erp.beans.lapkehoach.IErpDenghihuymua;
import geso.dms.db.sql.dbutils;

public class ErpDenghihuymua implements IErpDenghihuymua 
{
	String ctyId;
	String userId;
	String nam;
	String thang;
	String msg;
	
	ResultSet huyRs;
	
	dbutils db;
	
	public ErpDenghihuymua()
	{
		this.ctyId = "";
		this.userId = "";
		this.msg = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + (Integer.parseInt(this.getDateTime().substring(5, 7)) + 1);
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
	
	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;	
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;	
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

	public void init() 
	{
		String sql = "";
		
		//System.out.println("__THANG: " + this.thang + " -- NAM: " + this.nam);
		
		sql =	"SELECT	MNLDK.NAM, MNLDK.THANG,  'PR-' + CONVERT(VARCHAR, MNLDK.SANPHAM_FK) AS ID, MNLDK.SANPHAM_FK AS SPID, " +
				"SP.TEN  AS SP, " + 
				"MNLDK.SOLUONG AS QTY, MNLDK.DADATHANG AS DADAT, " +
				"CASE WHEN (MNLDK.SOLUONG - MNLDK.DADATHANG) < 0 THEN (MNLDK.DADATHANG - MNLDK.SOLUONG) ELSE 0 END AS DENGHIHUY " + 
				"FROM ERP_MUANGUYENLIEUDUKIEN MNLDK " +
				"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MNLDK.SANPHAM_FK " +
				"WHERE MNLDK.SANPHAM_FK IS NOT NULL AND (MNLDK.DADATHANG - MNLDK.SOLUONG) > 0 AND MNLDK.CONGTY_FK = " + this.ctyId + " " +
				"ORDER BY MNLDK.NAM, MNLDK.THANG, SP.TEN ";
			
		
		System.out.println("De nghi huy mua nguyen lieu: " + sql);
		this.huyRs = db.get(sql);
	}
	
	public ResultSet getChitiet(String spId, String nam, String thang)
	{
		String sql =	"SELECT	MH.PK_SEQ AS POID, MHSP.SANPHAM_FK AS SPID, NCC.TEN, " +
						"MHSP.SOLUONG AS DADAT, MHSP.NGAYNHAN " +
						"FROM ERP_MUAHANG MH " +
						"INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ " + 
						"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK " +
						"INNER JOIN ERP_SANPHAM SP on MHSP.SANPHAM_FK = SP.PK_SEQ " +
						"WHERE SP.MA = '" + spId + "' and MHSP.SANPHAM_FK IS NOT NULL AND MH.CONGTY_FK = " + this.ctyId + " AND " +
						"CONVERT(INT, SUBSTRING(MHSP.NGAYNHAN, 6, 2)) = " + thang + " AND CONVERT(INT, SUBSTRING(MHSP.NGAYNHAN, 1, 4)) = " + nam + " " +
						"ORDER BY MHSP.NGAYNHAN " ;
		
		System.out.println("Chitiet: " + sql);
		return this.db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.huyRs != null)
				this.huyRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getDenghihuymuaRs() 
	{
		return this.huyRs;
	}

	public void setDenghihuymuaRs(ResultSet huyRs) 
	{
		this.huyRs = huyRs;
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


}
