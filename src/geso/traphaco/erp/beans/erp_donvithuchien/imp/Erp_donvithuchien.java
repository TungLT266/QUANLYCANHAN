package geso.traphaco.erp.beans.erp_donvithuchien.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_donvithuchien.IErp_donvithuchien;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Erp_donvithuchien   implements IErp_donvithuchien
{
	dbutils db = new dbutils();
	String ID,TrangThai;
	String CTYID;
//	String DVKDID;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String PREFIX;
	String userId;
	String userTen;
	String Msg;
	ResultSet Rsdvth;
	ResultSet ctylist;
	ResultSet dvkd;
	public Erp_donvithuchien(String id)
	{
		this.db = new dbutils();
		this.ID = id;
//		this.DVKDID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.PREFIX = "";
		this.userId = "";
		this.Msg = "";
		this.TrangThai="";
	}

	public Erp_donvithuchien()
	{
		this.db = new dbutils();
		this.ID = "";
//		this.DVKDID = "";		
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.PREFIX = "";
		this.userId = "";
		this.Msg = "";
		this.TrangThai="";
	}

	public String getID()
	{
		return ID;
	}

	public String getCtyId()
	{
		return this.CTYID;
	}

	public void setCtyId(String CTYID)
	{
		this.CTYID = CTYID;
		this.dvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DonViKinhDoanh WHERE CONGTY_FK = " + this.CTYID + " AND TRANGTHAI = '1' ");
	}
	
/*	public String getDvkdId()
	{

		return this.DVKDID;
	}

	public void setDvkdId(String dvkdId)
	{

		this.DVKDID = dvkdId;
	}

	public ResultSet getDvkdRs()
	{

		return this.dvkd;
	}

	public void setDvkdRs(ResultSet dvkd)
	{

		this.dvkd = dvkd;
	}*/
	
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

	public String getPrefix()
	{

		return this.PREFIX;
	}

	public void setPrefix(String PREFIX)
	{

		this.PREFIX = PREFIX;
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

	public boolean UpdateMa()
	{
		if (!CheckUnique())
		{
			this.Msg = "Mã này đã có,vui lòng chọn mã khác";
			return false;
		}
		
		String query = 	"UPDATE erp_donvithuchien " + "SET ma = '" + this.getMA() + "'," + "ten = N'" + this.getTEN() + "' ," +
						"NGUOISUA='" + this.getNGUOISUA() + "',NGAYSUA='" + this.getDateTime() + "', TRANGTHAI = '" + this.TrangThai + "', " +
						"CONGTY_FK = '" + this.CTYID + "', PREFIX = '" + this.PREFIX + "' " +
						
				 		"WHERE pk_seq = '" + this.getID() + "' ";

		System.out.println("Query update is: " + query);
		if (db.update(query))
		{
			return true;
		} else
		{
			return false;
		}
	}

	public void setCtyList(ResultSet ctylist)
	{
		this.ctylist = ctylist;
	}

	public ResultSet getCtyList()
	{ 		
		return this.ctylist;
		
	}


	public boolean themMoiMa()
	{
		if (!CheckUnique())
		{
			this.Msg = "Mã này đã có,vui lòng chọn mã khác";
			return false;
		}
		String query = "insert erp_donvithuchien(ma, ten, ngaytao, ngaysua, nguoitao, nguoisua, trangthai, congty_fk,  prefix) " + "values('"
				+ this.MA + "',N'" + this.TEN + "','" + this.getDateTime() + "','" + this.getDateTime() + "'," + "'"
				+ this.NGUOITAO + "','" + this.NGUOITAO + "', '1', '" + this.CTYID + "', '" + this.PREFIX + "')";
		System.out.println("Query them moi : " + query);
		if (db.update(query))
		{
			return true;
		} else
		{
			// System.out.println("Insert Data: " + query);
			return false;
		}
	}

	public void init()
	{
		String query = " select * from erp_donvithuchien where pk_seq = '" + ID + "' ";

		ResultSet obj = db.get(query);
		try
		{
			if (obj.next())
			{
				this.ID = obj.getString("pk_seq");
				this.MA = obj.getString("ma");
				this.TEN = obj.getString("ten");
				this.NGAYTAO = obj.getString("ngaytao");
				this.NGAYSUA = obj.getString("ngaysua");
				this.NGUOITAO = obj.getString("nguoitao");
				this.NGUOISUA = obj.getString("nguoisua");
				this.TrangThai= obj.getString("TrangThai");
				this.CTYID = obj.getString("CONGTY_FK");
//				this.DVKDID = obj.getString("DVKD_FK");
				this.PREFIX = obj.getString("PREFIX");
			}

			if (obj != null)
			{
				obj.close();
			}

		} catch (Exception er)
		{
			this.Msg = "Loi" + er.toString();
		}

//		this.dvkd = this.db.get("SELECT PK_SEQ AS DVKDID, DONVIKINHDOANH AS DVKD FROM DonViKinhDoanh WHERE CONGTY_FK = " + this.CTYID + " AND TRANGTHAI = '1' ");
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

	public boolean CheckUnique()
	{
		String query = "";
		if (this.ID.length() > 0)
			query = "Select count(*) as count From Erp_DonViThucHien Where MA='" + this.MA + "' AND PK_SEQ !='" + this.ID + "' AND CONGTY_FK = '" + this.CTYID + "' ";
		else
			query = "Select count(*) as count From Erp_DonViThucHien Where MA='" + this.MA + "' AND CONGTY_FK = '" + this.CTYID + "' ";
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				return false;
			}
		return true;
	}
	public void setTrangThai(String TrangThai)
	{
		this.TrangThai=TrangThai;
	}

	public String getTrangThai()
	{
		return this.TrangThai;
	}
	
	private void createCtyList()
	{		
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY where pk_seq = '" + this.CTYID + "' ");
	}

	public void DBClose()
	{

		try
		{
			if(this.ctylist != null) this.ctylist.close();
			
//			if(this.dvkd != null) this.dvkd.close();
			
			if (Rsdvth != null)
			{
				Rsdvth.close();
			}
			if (db != null)
			{
				db.shutDown();
			}

		} catch (Exception er)
		{
		}
	}
	
}
