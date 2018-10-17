package geso.traphaco.erp.beans.cophieu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.*;
import geso.traphaco.center.util.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.cophieu.IErpCoPhieuList;

public class ErpCoPhieuList extends Phan_Trang implements IErpCoPhieuList
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
	String Trangthai;
	String congtyphathanh;
	ResultSet CpList;

	public ErpCoPhieuList()
	{
		db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.Trangthai = "";
		this.userId = "";
		this.congtyphathanh="";
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

	public String gettrangthai()
	{
		return Trangthai;
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

	public void setTrangthai(String trangthai)
	{
		this.Trangthai = trangthai;
	}

	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}

	public ResultSet getCpList()
	{
		return CpList;
	}

	public void setCpList(ResultSet CpList)
	{
		this.CpList = CpList;
	}

	public void init()
	{
		Utility util = new Utility();
		
		String query = " SELECT CP.PK_SEQ, CP.MA ,CP.TEN ,isnull(CP.TRANGTHAI,0) AS TRANGTHAI ,CP.NGAYTAO ,CP.NGAYSUA ,NT.TEN AS " + " NGUOITAO,NS.TEN AS NGUOISUA, CP.Congtyphathanh "
						+ " FROM ERP_COPHIEU CP "
						+ " LEFT JOIN NHANVIEN NT  ON NT.PK_SEQ= CP.NGUOITAO "
						+ " LEFT JOIN NHANVIEN NS ON NS.PK_SEQ=CP.NGUOISUA " +
						" WHERE 1=1 ";
		System.out.println(query);
		if (this.MA.length() > 0)
			query += " and cp.ma like N'%" + this.MA + "%'";
		if (this.TEN.length() > 0)
			query += " and dbo.ftBoDau(cp.ten) like N'%" + util.replaceAEIOU(this.TEN) + "%'";
		if (this.congtyphathanh.length() > 0)
			query += " and dbo.ftBoDau(cp.congtyphathanh) like N'%" + util.replaceAEIOU(this.congtyphathanh) + "%'";
		
		this.CpList = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query);
	}

	public void DBClose()
	{
		if (db != null)
		{
			db.shutDown();
		}
		try
		{
			if (CpList != null)
				CpList.close();
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
	
	public boolean CheckReferences(String column, String table)
	{
		String query = "SELECT count(" + column + ") AS NUM  FROM " + table + " WHERE " + column + " =" + this.ID + "";
		System.out.println("____CheckReferences____ " + query);
		ResultSet rs = db.get(query);
		try
		{// kiem tra ben san pham
			while (rs.next())
			{
				if (rs.getString("num").equals("0"))
					return true;
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		return false;
	}

	public boolean DeleteCoPhieu()
	{

/*		if (!CheckReferences("NGANHANG_FK", "NHACUNGCAP"))
		{
			this.Msg = "Ngân hàng này đã có trong công ty bạn phải xóa công ty này trước khi xóa Ngân hàng";
			return false;
		}
		if (!CheckReferences("NganHang_FK", "ERP_THANHTOANHOADON"))
		{
			this.Msg = "Ngân hàng này đã có trong thanh toán hóa đơn bạn phải xóa thanh toán hóa đơn  này trước khi xóa Ngân hàng";
			return false;
		}
		if (!CheckReferences("NganHang_FK", "Erp_NganHang_CongTy"))
		{
			this.Msg = "Ngân hàng này đã có trong ngân hàng công ty bạn phải xóa ngân hàng công ty này trước khi xóa Ngân hàng";
			return false;
		}*/
		
		String query = "DELETE Erp_COPHIEU WHERE PK_SEQ='" + this.ID + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa cổ phiếu " + query;
			return false;
		}
		return true;
	}

	
	public void setCongtyphathanh(String congtyphathanh) {
	
		this.congtyphathanh = congtyphathanh;
	}

	
	public String getCongtyphathanh() {
	
		return this.congtyphathanh;
	}

	
}
