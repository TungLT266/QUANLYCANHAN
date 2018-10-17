package geso.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.erp.beans.lapkehoach.IErpKehoachtongtheList;
import geso.dms.db.sql.dbutils;

public class ErpKehoachtongtheList implements IErpKehoachtongtheList 
{
	String ctyId;
	String userId;
	String tungay;
	String denngay;
	String diengiai;
	String msg;
	
	ResultSet khttRs;
	
	dbutils db;
	
	public ErpKehoachtongtheList()
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
			sql = "select a.pk_seq, a.ngaylap, b.ten as nguoitao, ISNULL(nh.ten, N'Không chọn') as nhanhang, " +
				  "ISNULL(cl.ten, N'Không chọn') as chungloai, SOSP.NUM " +
			 	  "from ERP_KeHoachTongThe a " +
			 	  "left join nhanhang nh on nh.pk_seq = a.nhanhang_fk " +
			 	  "left join chungloai cl on cl.pk_seq = a.chungloai_fk " +
			 	  "left join (" +
			 	  "	SELECT COUNT(DISTINCT SANPHAM_FK) AS NUM, KEHOACH_FK AS KHID " + 
			 	  "	FROM ERP_KEHOACHTONGTHE_SANPHAM GROUP BY KEHOACH_FK " +			 	  
			 	  ")SOSP ON SOSP.KHID = a.PK_SEQ " +		 	  
			 	  "inner join NhanVien b on a.nguoitao = b.pk_seq  " +
			 	  "where a.CONGTY_FK = " + this.ctyId + " " +
			 	  "order by a.pk_seq desc";
		}
		
		System.out.println("__Ke hoach tong the: " + sql);
		this.khttRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.khttRs != null)
				this.khttRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getKehoachtongtheRs() 
	{
		return this.khttRs;
	}

	public void setKehoachtongtheRs(ResultSet khlRs) 
	{
		this.khttRs = khlRs;
	}

}
