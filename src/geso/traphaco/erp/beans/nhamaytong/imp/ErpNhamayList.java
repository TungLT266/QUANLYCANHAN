package geso.traphaco.erp.beans.nhamaytong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import geso.traphaco.erp.beans.nhamaytong.IErpNhamayList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpNhamayList implements IErpNhamayList 
{
	String userId;
	String congtyId;
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	
	ResultSet nhamayRs;
	
	dbutils db;
	
	public ErpNhamayList()
	{
		this.userId = "";

		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		
		this.msg = "";
		
		this.db = new dbutils();
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
			sql = "select a.pk_seq, a.manhamay, a.tennhamay, a.diachi, a.trangthai, b.ten as nguoitao, a.ngaytao, c.ten as nguoisua, a.ngaysua    " +
				  "from ERP_NHAMAYTONG a inner join NhanVien b on a.nguoitao = b.pk_seq    " +
				  		"inner join nhanvien c on a.nguoisua = c.pk_seq  " +
				  "order by a.pk_seq desc";
		}
		
		System.out.println("__Nha may : " + sql);
		this.nhamayRs = db.get(sql);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.nhamayRs != null)
				this.nhamayRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getNhamayRs() 
	{
		return this.nhamayRs;
	}

	public void setNhamayRs(ResultSet nhamayRs) 
	{
		this.nhamayRs = nhamayRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	

}
