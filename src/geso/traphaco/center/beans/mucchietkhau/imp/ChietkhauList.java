package geso.traphaco.center.beans.mucchietkhau.imp;

import java.sql.ResultSet;
import geso.traphaco.center.beans.mucchietkhau.IChietkhauList;
import geso.traphaco.center.db.sql.dbutils;

public class ChietkhauList implements IChietkhauList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String sotien;
	String mucchietkhau;
	String Msg;
	ResultSet congnoRs;
	
	dbutils db;
	
	public ChietkhauList()
	{
		this.db = new dbutils();
		this.sotien = "";
		this.mucchietkhau = "";
		this.Msg = "";
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getSotien() 
	{
		return this.sotien;
	}

	public void setSotien(String sotien) 
	{
		this.sotien = sotien;
	}

	public String getChietkhau() 
	{
		return this.mucchietkhau;
	}

	public void setChietkhau(String chietkhau) 
	{
		this.mucchietkhau = chietkhau;
	}

	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq, isnull(a.diengiai, '') as diengiai, a.loai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua " +
					"from CONGNO a inner join nhanvien b on a.nguoitao = b.pk_seq " +
					"	inner join nhanvien c on a.nguoisua = c.pk_seq ";
		}
		else
		{
			query = search;
		}
		
		System.out.println("Query la: " + query + "\n");
		
		this.congnoRs = db.get(query);
	}

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}


	public void setMsg(String Msg) {
	     this.Msg = Msg;
		
	}
	public String getMsg() {
          return this.Msg;
	}


	public ResultSet getCongnoList() {
		
		return this.congnoRs;
	}


	public void setCongnoList(ResultSet congnoList) {
		
		this.congnoRs = congnoList;
	}

	
}
