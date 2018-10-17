package geso.traphaco.erp.beans.erp_donvithuchien.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchienList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Erp_donvithuchienList  extends Phan_Trang implements IErp_donvithuchienList
{
	private static final long serialVersionUID = 99589141007304167L;
	dbutils db = new dbutils();
	String congtyId;
	
	String ID;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String userId;
	String userTen;
	String Msg;
	ResultSet ctylist;
	ResultSet Rsdvth;

	public Erp_donvithuchienList()
	{
		this.db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
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

	public String getMsg()
	{

		return Msg;
	}

	public ResultSet getRsdvth()
	{

		return Rsdvth;
	}

	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}

	public void setRsdvth(ResultSet Rsdvth)
	{
		this.Rsdvth = Rsdvth;
	}

	
	public void init()
	{
		this.createCtyList();
		
		Utility util = new Utility();
		
		String query = " SELECT DVTH.PK_SEQ,DVTH.MA, DVTH.TEN, DVTH.NGAYTAO ,DVTH.NGAYSUA ,NT.TEN AS "
				+ " NGUOITAO,NS.TEN AS NGUOISUA," +
				" CASE WHEN DVTH.TRANGTHAI =1 THEN N'Hoạt động' " +
				" ELSE N'Ngưng hoạt động' END TrangThai " + ", CTY.TEN AS CONGTY, DVTH.PREFIX" 
				+ " FROM ERP_DONVITHUCHIEN DVTH "
				
				+ " INNER JOIN NHANVIEN NT  ON NT.PK_SEQ = DVTH.NGUOITAO "
				+ " INNER JOIN NHANVIEN NS ON NS.PK_SEQ = DVTH.NGUOISUA" 
				+ " INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = DVTH.CONGTY_FK where dvth.congty_fk = '" + this.congtyId + "'  ";
		
		if (this.TEN.length() > 0)
			query += " and dbo.ftBoDau(DVTH.ten) like N'%" + util.replaceAEIOU(this.TEN) + "%'";
		
		System.out.println("Init: Erp_donvithuchienList:" + query);
		this.Rsdvth = createSplittingDataNew(this.db, 50, 10, " PK_SEQ DESC ,TRANGTHAI ", query);

	}


	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
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
		System.out.println("___CheckReferences___ " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					if (rs.getString("num").equals("0"))
						return true;
				}
			} catch (SQLException e)
			{
				e.printStackTrace();
			}finally{
				try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
			}
		}
		return false;
	}
	
	public boolean DeleteDvth()
	{

		if (!CheckReferences("DonViThucHIen_FK", "ERP_CHINHSACHDUYET"))
		{
			this.Msg = " Đơn vị thực hiện này đã thiết lập chính sách duyệt chi phí ,bạn phải xóa chính sách duyệt chi phí của DVTH này trước khi xóa ";
			return false;
		}

		if (!CheckReferences("DonViThucHIen_FK", "Erp_MuaHang"))
		{
			this.Msg = " Đơn vị thực hiện này đã có mua hàng,bạn phải xóa mua hàng của DVTH này trước khi xóa ";
			return false;
		}
		if (!CheckReferences("DonViThucHien_FK", "Erp_NhanHang"))
		{
			this.Msg = " Đơn vị thực hiện này đã có nhận hàng,bạn phải xóa nhận hàng của DVTH này trước khi xóa ";
			return false;
		}
		if (!CheckReferences("DonViThucHien_FK", "ERP_KHOANGCHIPHI_DONVITHUCHIEN"))
		{
			this.Msg = " Đơn vị thực hiện này đã có mức chi phí,bạn phải xóa mức chi phí của DVTH này trước khi xóa ";
			return false;
		}
		
		String query = "DELETE Erp_DonViThucHien WHERE PK_SEQ='" + this.ID + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa Đơn vị thực hiện " + query;
			return false;
		}
		return true;
		
	}
	
	public void setCtyList(ResultSet ctylist)
	{
		this.ctylist = ctylist;
	}

	public ResultSet getCtyList()
	{ 		
		return this.ctylist;
		
	}
	private void createCtyList()
	{		
		System.out.println("___Khoi tao: SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY where pk_seq = '" + this.congtyId + "'");
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY where pk_seq = '" + this.congtyId + "'");
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public void DBClose()
	{

		try
		{
			if(this.ctylist != null) this.ctylist.close();
			
			
			if (Rsdvth != null)
			{
				Rsdvth.close();
			}
		} catch (Exception er)
		{
			er.printStackTrace();
		}finally{
			if (db != null)
			{
				db.shutDown();
			}
		}
	}

}
