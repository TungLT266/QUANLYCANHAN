package geso.traphaco.erp.beans.congty.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.congty.IErpNganHang;
public class ErpNganHang implements IErpNganHang
{
	String id;
	String ma;
	String ten;
	public ErpNganHang()
	{
		this.id = "";
		this.ma = "";
		this.ten = "";
	}
	public ErpNganHang(String id)
	{
		String query = "Select PK_SEQ,Ma,Ten From Erp_NganHang Where TrangThai=1";
		dbutils db = new dbutils();
		ResultSet rsNganHang = db.get(query);
		if (rsNganHang != null)
		{
			try
			{
				while (rsNganHang.next())
				{
					this.id = rsNganHang.getString("PK_SEQ");
					this.ma = rsNganHang.getString("Ma");
					this.ten = rsNganHang.getString("Ten");
				}
				rsNganHang.close();
				db.shutDown();
			}
			catch (SQLException e)
			{
				System.out.println("Loi lay ngan hang ErpNganHang");
			}
		}
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public String getId()
	{
		return this.id;
	}
	public void setTen(String ten)
	{
		this.ten = ten;
	}
	public String getTen()
	{
		return this.ten;
	}
	public String getMa()
	{
		return this.ma;
	}
	public void setMa(String ma)
	{
		this.ma = ma;
	}
}
