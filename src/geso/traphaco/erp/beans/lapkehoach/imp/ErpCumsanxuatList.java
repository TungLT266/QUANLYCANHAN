package geso.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.erp.beans.lapkehoach.IErpCumsanxuatList;
import geso.dms.db.sql.dbutils;

public class ErpCumsanxuatList implements IErpCumsanxuatList 
{
	String ctyId;
	String userId;
	String tungay;
	String denngay;
	String trangthai; 
	String ma;
	String diengiai;
	String msg;
	
	ResultSet CumsanxuatRs;
	
	dbutils db;
	
	public ErpCumsanxuatList()
	{
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.diengiai = "";
		this.ma = "";
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

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
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
			sql = "select a.pk_seq, a.ma, a.diengiai, a.sonhancong, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
				  "from Erp_CumSanXuat a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  		"inner join nhanvien c on a.nguoisua = c.pk_seq  " +
				  "where a.nhamay_fk in (select pk_seq from erp_nhamay where congty_fk = " + this.ctyId + ") " +
				  "order by a.pk_seq desc";
		}
		
		System.out.println("__Cum san xuat : " + sql);
		this.CumsanxuatRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.CumsanxuatRs != null)
				this.CumsanxuatRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getCumsanxuatRs() 
	{
		return this.CumsanxuatRs;
	}

	public void setCumsanxuatRs(ResultSet CumsanxuatRs) 
	{
		this.CumsanxuatRs = CumsanxuatRs;
	}

}
