package geso.traphaco.erp.beans.erp_tiente.imp;

import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.erp_tiente.IErp_tiente;

public class Erp_tiente implements IErp_tiente
{
	dbutils db;
	String ID;
	String MA;
	String TEN;
	String Msg;
	ResultSet Rstt;
	String userten;
	String userid;
	String doctiensonguyen;
	String doctiensole;

	
	public Erp_tiente()
	{
		db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.Msg = "";
		this.userid = "";
		this.doctiensonguyen="";
		this.doctiensole="";
	}

	public Erp_tiente(String id)
	{
		db = new dbutils();
		this.ID = id;
		this.MA = "";
		this.TEN = "";
		this.Msg = "";
		this.userid = "";
		this.doctiensonguyen="";
		this.doctiensole="";
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

	public String getMsg()
	{

		return Msg;
	}

	public ResultSet getRstt()
	{

		return Rstt;
	}

	public void setRstt(ResultSet Rstt)
	{

		this.Rstt = Rstt;
	}

	public void setMsg(String Msg)
	{

		this.Msg = Msg;
	}
	public boolean ThemMoiMa()
	{
		String ngaytao=getDateTime();
		if (!CheckUnique())
		{
			this.Msg = "Ma tien te nay da duoc su dung,vui long chon ma khac";
			return false;
		}
		String query="";
		try
		{
			this.db.getConnection().setAutoCommit(false);
			 query = "INSERT INTO erp_tiente(ma, ten,doctiensonguyen,doctiensole) VALUES('" + this.MA + "', N'" + this.TEN + "',N'"+this.doctiensonguyen+"',N'"+this.doctiensole+"')";
			if ( ! db.update(query))
			{
				this.Msg = "Khong the tao moi tien te " + query;
				return false;
			}else{
				query = "SELECT SCOPE_IDENTITY() AS ID";
				ResultSet rs = this.db.get(query);
				rs.next();
				String id = rs.getString("ID");
				query = "INSERT INTO ERP_TIGIA (TIENTE_FK, TIGIAQUYDOI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, TUNGAY, DENNGAY, SOLUONGGOC, CONGTY_FK) " +
						"SELECT " + id + ", '1', '" + this.getDateTime() + "', '" + this.getDateTime() + "', '" + this.userid + "', '" + this.userid + "', '0', " +
						"'" + this.getDateTime() + "', '" + this.getDateTime() + "', '1', PK_SEQ " +
						"FROM ERP_CONGTY WHERE TRANGTHAI = '1'";
				
				System.out.println(query);
				if(!this.db.update(query)){
					this.Msg = "Khong the tao moi ti gia " + query;
					return false;					
				}
				
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (SQLException e)
		{
			this.db.update("rollback");
			this.Msg = "Khong the tao moi tien te " + query;
			e.printStackTrace();
			return false;
		}
			
	return true;
	
	}

	public boolean UpdateMa()
	{
		if (!CheckUnique())
		{
			this.Msg = "Ma tien te nay da duoc su dung,vui long chon ma khac";
			return false;
		}
		String query = "UPDATE erp_tiente SET ma = '" + this.getMA() + "', ten = N'" + this.getTEN()
				+ "' , doctiensonguyen=N'"+this.doctiensonguyen+"' , doctiensole=N'"+this.doctiensole+"' "
				+ " WHERE pk_seq = '" + this.getID() + "'";
		System.out.println("update: " + query);
		if (db.update(query))
		{
			return true;
		} else
		{
			this.Msg = "Khong the cap nhat tien te " + query;
			return false;
		}
	}

	public void init()
	{
		String query = " SELECT * FROM erp_tiente WHERE pk_seq = '" + this.ID + "' ";

		ResultSet obj = db.get(query);
		try
		{
			if (obj.next())
			{
				this.ID = obj.getString("pk_seq");
				this.MA = obj.getString("ma");
				this.TEN = obj.getString("ten");
				this.doctiensonguyen=obj.getString("doctiensonguyen")==null?"":obj.getString("doctiensonguyen");
				this.doctiensole=obj.getString("doctiensole")==null?"":obj.getString("doctiensole");
			}

			if (obj != null)
			{
				obj.close();
			}

		} catch (Exception er)
		{
			this.Msg = "Loi" + er.toString();
		}

	}

	public void DBClose()
	{
		try
		{
			if (Rstt != null)
			{
				Rstt.close();
			}
			if (db != null)
			{
				db.shutDown();
			}

		} catch (Exception er)
		{
		}

	}

	public void setUserid(String userid)
	{

		this.userid = userid;
	}

	public String getUserid()
	{

		return userid;
	}

	public void setUserTen(String userten)
	{

		this.userten = userten;
	}

	public String getUserTen()
	{

		return this.userten;
	}

	public boolean CheckUnique()
	{
		String query = "";
		if (this.ID.length() > 0)
			query = "Select count(*) as count From Erp_TienTe Where MA=N'" + this.MA + "' AND PK_SEQ !='"
					+ this.ID + " '";
		else
			query = "Select count(*) as count From Erp_TienTe Where MA=N'" + this.MA + "' ";
		System.out.println("____Kiem tra rang buoc_____ " + query);
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
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getDoctiensonguyen() {
		return doctiensonguyen;
	}

	public void setDoctiensonguyen(String doctiensonguyen) {
		this.doctiensonguyen = doctiensonguyen;
	}

	public String getDoctiensole() {
		return doctiensole;
	}

	public void setDoctiensole(String doctiensole) {
		this.doctiensole = doctiensole;
	}
}
