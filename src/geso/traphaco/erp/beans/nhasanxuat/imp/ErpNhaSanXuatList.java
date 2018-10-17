package geso.traphaco.erp.beans.nhasanxuat.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhasanxuat.IErpNhaSanXuatList;

public class ErpNhaSanXuatList extends Phan_Trang implements IErpNhaSanXuatList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2713619371468830952L;
	dbutils db;
	String ID;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String userTen;
	String userId;
	String Msg;
	String TRANGTHAI;
	ResultSet NsxList;
	
	public ErpNhaSanXuatList()
	{
		db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.TRANGTHAI = "";
		this.userId = "";
		this.Msg = "";
	}

	public String getID()
	{
		return ID;
	}

	public String getMA()
	{
		return MA;
	}

	public String getTEN()
	{
		return TEN;
	}

	public String getNGAYTAO()
	{
		return NGAYTAO;
	}

	public String getNGAYSUA()
	{
		return NGAYSUA;
	}

	public String getNGUOITAO()
	{
		return NGUOITAO;
	}

	public String getNGUOISUA()
	{
		return NGUOISUA;
	}

	public String getMsg()
	{
		return Msg;
	}

	public String getTRANGTHAI()
	{
		return TRANGTHAI;
	}

	public void setID(String ID)
	{
		this.ID = ID;
	}

	public void setMA(String MA)
	{
		this.MA = MA;
	}

	public void setTEN(String TEN)
	{
		this.TEN = TEN;
	}

	public void setNGAYTAO(String NGAYTAO)
	{
		this.NGAYTAO = NGAYTAO;
	}

	public void setNGAYSUA(String NGAYSUA)
	{
		this.NGAYSUA = NGAYSUA;
	}

	public void setNGUOITAO(String NGUOITAO)
	{
		this.NGUOITAO = NGUOITAO;
	}

	public void setNGUOISUA(String NGUOISUA)
	{
		this.NGUOISUA = NGUOISUA;
	}

	public void setTRANGTHAI(String TRANGTHAI)
	{
		this.TRANGTHAI = TRANGTHAI;
	}

	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}

	public ResultSet getNsxList()
	{
		return NsxList;
	}

	public void setNsxList(ResultSet NsxList)
	{
		this.NsxList = NsxList;
	}

	public void init()
	{
		Utility util = new Utility();
		
		String query =    " SELECT NSX.PK_SEQ,ISNULL(NSX.MA,'') AS MA ,NSX.TEN ,isnull(NSX.TRANGTHAI,0) AS TRANGTHAI ,NSX.NGAYTAO ,NSX.NGAYSUA ,NT.TEN AS "
						+ " NGUOITAO,NS.TEN AS NGUOISUA "
						+ " FROM ERP_NHASANXUAT NSX "
						+ " INNER JOIN NHANVIEN NT  ON NT.PK_SEQ= NSX.NGUOITAO "
						+ " INNER JOIN NHANVIEN NS ON NS.PK_SEQ=NSX.NGUOISUA " +
						" WHERE 1=1 ";
		if (this.MA.length() > 0)
			query += " and NSX.ma like N'%" + this.MA + "%'";
		if (this.TEN.length() > 0)
			query += " and dbo.ftBoDau(NSX.ten) like N'%" + util.replaceAEIOU(this.TEN) + "%'";
		if (this.TRANGTHAI.length() > 0)
			query += " and NSX.trangthai = '" + this.TRANGTHAI + "'";
		
		System.out.println("List Nha san xuat: " + query);
		
		this.NsxList = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query);
	}

	public void DBClose()
	{
		if (db != null)
		{
			db.shutDown();
		}
		try
		{
			if (NsxList != null)
				NsxList.close();
		} catch (SQLException e)
		{
		}
	}

	public void setUserid(String user)
	{

		this.userId = user;
	}

	public String getUserid()
	{

		return userId;
	}

	public void setUserTen(String userten)
	{

		this.userTen = userten;
	}

	public String getUserTen()
	{

		return userTen;
	}
	
	public boolean deleteNhaSanXuat()
	{
		String query = "update ERP_NHASANXUAT set TRANGTHAI=0  WHERE PK_SEQ='" + this.ID + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa nhà sản xuất " + query;
			return false;
		}
		return true;
	}
	
}
