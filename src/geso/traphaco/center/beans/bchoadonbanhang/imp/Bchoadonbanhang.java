package geso.traphaco.center.beans.bchoadonbanhang.imp;

import geso.traphaco.center.beans.bchoadonbanhang.IBchoadonbanhang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Bchoadonbanhang extends Phan_Trang implements IBchoadonbanhang{
	String userId;
	String id;


	String msg;

	dbutils db;

	public Bchoadonbanhang()
	{
		this.userId = "";
		this.id = "";


		this.msg = "";

		this.db = new dbutils();
	}

	public Bchoadonbanhang(String id)
	{
		this.userId = "";
		this.id = id;


		this.msg = "";

		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String Id)
	{
		this.id = Id;
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

	public boolean createBchoadonbanhang()
	{
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "insert bchoadonbanhang(trangthai, ngaytao, nguoitao, ngaysua, nguoisua) " +
							"values('0', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "')";

			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới : " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "select as bchoadonbanhangId";
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					this.id = rs.getString("bchoadonbanhangId");
				}
				rs.close();
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			try
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}

	public boolean updateBchoadonbanhang()
	{
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "Update bchoadonbanhang set ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "' ";

			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới : " + query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}

		catch (Exception e)
		{
			this.msg = "Lỗi: " + e.getMessage();
			try
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}

		return true;
	}

	public void init()
	{
		String query = "";
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try
			{
				while(rs.next())
				{

				}
				rs.close();
			}
			catch (Exception e)
			{
				System.out.println("__Exception Init: " + e.getMessage());
			}
		}

		this.createRs();
	}

	public void createRs()
	{

	}

	public void DbClose()
	{
		try
		{
			this.db.shutDown();
		}
		catch (Exception e) {}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
