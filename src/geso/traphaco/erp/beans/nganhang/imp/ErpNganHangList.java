package geso.traphaco.erp.beans.nganhang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.nganhang.IErpNganHangList;
import geso.traphaco.erp.util.UtilitySyn;

public class ErpNganHangList extends Phan_Trang implements IErpNganHangList
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
	ResultSet NhList;
	
	String chixem;

	public ErpNganHangList()
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
		this.Msg = "";
		this.chixem = "0";
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

	public ResultSet getNhList()
	{
		return NhList;
	}

	public void setNhList(ResultSet NhList)
	{
		this.NhList = NhList;
	}

	public void init()
	{
		Utility util = new Utility();
		
		String query =    " SELECT NH.PK_SEQ,NH.MA ,NH.TEN ,isnull(NH.TRANGTHAI,0) AS TRANGTHAI ,NH.NGAYTAO ,NH.NGAYSUA ,NT.TEN AS "
						+ " NGUOITAO,NS.TEN AS NGUOISUA "
						+ " FROM ERP_NGANHANG NH "
						+ " INNER JOIN NHANVIEN NT  ON NT.PK_SEQ= NH.NGUOITAO "
						+ " INNER JOIN NHANVIEN NS ON NS.PK_SEQ=NH.NGUOISUA " +
						" WHERE 1=1 ";
		if (this.MA.length() > 0)
			query += " and nh.ma like N'%" + this.MA + "%'";
		if (this.TEN.length() > 0)
			query += " and dbo.ftBoDau(nh.ten) like N'%" + util.replaceAEIOU(this.TEN) + "%'";
		
		this.NhList = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC,TRANGTHAI  ", query);
	}

	public void DBClose()
	{
		if (db != null)
		{
			db.shutDown();
		}
		try
		{
			if (NhList != null)
				NhList.close();
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

	public boolean DeleteNganHang()
	{

		if (!CheckReferences("NGANHANG_FK", "NHACUNGCAP"))
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
		}
		
		String query = "DELETE Erp_NganHang WHERE PK_SEQ='" + this.ID + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa ngân hàng " + query;
			return false;
		}
		else
		{
			UtilitySyn.SynData(db, "ERP_NGANHANG", "ERP_NGANHANG", "PK_SEQ", this.ID, "2", true);
		}
		return true;
		
	}

	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
