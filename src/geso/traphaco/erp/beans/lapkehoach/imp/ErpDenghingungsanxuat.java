package geso.traphaco.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.lapkehoach.IErpDenghingungsanxuat;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpDenghingungsanxuat implements IErpDenghingungsanxuat 
{
	String ctyId;
	String dvkdId;
	String userId;
	String nam;
	String thang;
	String msg;
	
	ResultSet huyRs;
	ResultSet rsDvkd;
	
	dbutils db;
	
	public ErpDenghingungsanxuat()
	{
		this.ctyId = "";
		this.userId = "";
		this.dvkdId = "";
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
	
	public String getDvkdId()
	{

		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{

		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkdRs()
	{

		return this.rsDvkd;
	}

	public void setDvkdRs(ResultSet rsDvkd)
	{

		this.rsDvkd = rsDvkd;
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
		

			sql = " SELECT	SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4), CONVERT(VARCHAR, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)), \n  " +
					"'PRO-' + CONVERT(VARCHAR, LSXDK.SANPHAM_FK) AS ID, LSXDK.SANPHAM_FK AS SPID,  \n " +
					"SP.TEN AS SP, SP.MA,  \n " +
					
					"LSXDK.SOLUONG AS QTY, ISNULL(SANXUAT.soluongSX, 0) AS SANXUAT,  \n   " +
					
					"CASE WHEN ( LSXDK.SOLUONG -  ISNULL(SANXUAT.SOLUONGSX, 0) ) < 0  \n " +
					"THEN ( ISNULL(SANXUAT.SOLUONGSX, 0) - LSXDK.SOLUONG) ELSE 0 END AS DENGHINGUNG    \n  " +
					
					"FROM ERP_LENHSANXUATDUKIEN LSXDK    \n " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXDK.SANPHAM_FK    \n  " +
					
					"LEFT JOIN  \n " +
					"(  \n " +
						"SELECT LSX_SP.SANPHAM_FK, SUM(LSX_SP.SOLUONG) AS SOLUONGSX   \n " +
						"FROM ERP_LENHSANXUAT_GIAY LSX   \n " +
						"INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP ON LSX.PK_SEQ = LSX_SP.LENHSANXUAT_FK   \n " +
						"WHERE LSX.NGAYDUKIENHT LIKE '" + this.nam + "-" + ( this.thang.trim().length() <= 1 ? ( "0" + this.thang ) : this.thang ) + "%'  \n   " +
						"GROUP BY LSX_SP.SANPHAM_FK  \n " +
					") " +
					"SANXUAT ON SP.PK_SEQ = SANXUAT.SANPHAM_FK   \n " +
					"WHERE LSXDK.SANPHAM_FK IS NOT NULL AND ( ISNULL(SANXUAT.SOLUONGSX, 0) - LSXDK.SOLUONG) > 0 AND LSXDK.CONGTY_FK = '" + this.ctyId + "'  \n  " +
					"AND SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4) = '" + this.nam + "' AND CONVERT(VARCHAR, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)) =  '" + this.thang + "'  \n " +
					"ORDER BY SUBSTRING(LSXDK.NGAYKETTHUC, 1, 4), CONVERT(VARCHAR, SUBSTRING(LSXDK.NGAYKETTHUC, 6, 2)), SP.TEN  \n ";
					
		
		System.out.println("De nghi ngưng sản xuất: " + sql);
		this.huyRs = db.get(sql);
	}
	
	public ResultSet getChitiet(String spId, String nam, String thang)
	{
		String sql =	"SELECT	LSXSP.LENHSANXUAT_FK AS PROID, LSXSP.SANPHAM_FK AS SPID, " +
						"SP.MA + ' - ' + SP.TEN  AS TEN, " +
						"LSXSP.SOLUONG AS SANXUAT, LSX.NGAYDUKIENHT " +
						"FROM ERP_LENHSANXUAT_SANPHAM LSXSP " +
						"INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ = LSXSP.LENHSANXUAT_FK " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSXSP.SANPHAM_FK " +
						
						"WHERE LSXSP.SANPHAM_FK IS NOT NULL AND LSX.CONGTY_FK = " + this.ctyId + " AND " +
						"	CONVERT(INT, SUBSTRING(LSX.NGAYDUKIENHT, 6, 2)) = " + thang + " AND CONVERT(INT, SUBSTRING(LSX.NGAYDUKIENHT, 1, 4)) = " + nam + " " +
						"	AND LSXSP.SANPHAM_FK = '" + spId + "' " +
						"ORDER BY LSX.NGAYDUKIENHT" ;
						
		
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

	public ResultSet getDenghingungsanxuatRs() 
	{
		return this.huyRs;
	}

	public void setDenghingungsanxuatRs(ResultSet huyRs) 
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
