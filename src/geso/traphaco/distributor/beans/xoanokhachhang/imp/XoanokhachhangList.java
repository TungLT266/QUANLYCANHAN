package geso.traphaco.distributor.beans.xoanokhachhang.imp;

import geso.traphaco.distributor.beans.xoanokhachhang.*;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import B.FI;

public class XoanokhachhangList implements IXoanokhachhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String tungay;
	String denngay;
	String trangthai;	
	ResultSet ddkd;
	String ddkdId;
	String sohoadon;
		
	ResultSet xnkhlist;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public XoanokhachhangList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		this.sohoadon = param[4];
		db = new dbutils();
	}
	
	public XoanokhachhangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
		this.sohoadon= "";
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
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
	
	public ResultSet getDdkd() 
	{		
		return this.ddkd;
	}
	
	public void setDdkd(ResultSet ddkd) 
	{		
		this.ddkd = ddkd;
	}
	
	public String getDdkdId() 
	{	
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}
	
	public ResultSet getXnkhList() 
	{	
		return this.xnkhlist;
	}
	
	public void setXnkhList(ResultSet xnkhlist)
	{
		this.xnkhlist = xnkhlist;		
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public void init(String search) 
	{		
	
		this.getNppInfo();
		
		String query = "";	
		if (search.length() == 0)
		{
			query = " select a.pk_seq as id, a.ngaychungtu , a.trangthai , b.ten as nguoitao, c.ten as nguoisua, a.ngaytao, a.ngaysua  "+
	                " from xoanokhachhang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
			        "      inner join nhanvien c on a.nguoisua = c.pk_seq where a.npp_fk='" + this.nppId + "' " +
			        " order by a.pk_seq ";
			System.out.println("Search : "+query);
		}
		else
		{
			query = search;
		}		
		
		this.xnkhlist = db.get(query);
	}



	public void DBclose() 
	{
			try 
			{
				if(this.db != null)
					this.db.shutDown();
				
				if(!(ddkd == null))
					ddkd.close();
			} 
			catch(Exception e) {}
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) 
	{
		this.sohoadon = sohoadon;
	}
	
}
