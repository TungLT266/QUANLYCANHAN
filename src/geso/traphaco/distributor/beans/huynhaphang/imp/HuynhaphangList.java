package geso.traphaco.distributor.beans.huynhaphang.imp;

import geso.traphaco.distributor.beans.huynhaphang.IHuynhaphangList;
import geso.traphaco.distributor.beans.huynhaphang.IHuynhaphang;
import geso.traphaco.distributor.db.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HuynhaphangList implements IHuynhaphangList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String tungay;
	String denngay;
	String trangthai;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet daidienkd;
	String ddkdId;
	
	ResultSet dhtvlist;
	
	dbutils db;
		
	//Constructor
	public HuynhaphangList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		db = new dbutils();
	}
	
	public HuynhaphangList()
	{
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.ddkdId = "";
		//init("");
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
	
	public ResultSet getDaidienkd() 
	{	
		return this.daidienkd;
	}
	
	public void setDaidienkd(ResultSet daidienkd) 
	{
		this.daidienkd = daidienkd;		
	}
	
	public String getDdkdId()
	{	
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}

	public String getTrangthai() 
	{	
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;	
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
	
	public ResultSet getDhtvList() 
	{	
		return this.dhtvlist;
	}

	public void setDhtvList(ResultSet dhtvlist) 
	{
		this.dhtvlist = dhtvlist;		
	}
	
	public void createDdkd()
	{
		this.daidienkd = db.get("select ten as ddkdTen, pk_seq as ddkdId from daidienkinhdoanh where npp_fk = '" + this.nppId +"'");
	}
	
	private void getNppInfo(){
 
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public void createDhtvBeanList(String query)
	{
		this.dhtvlist =  db.get(query);
	}
	
	public void init(String search) 
	{
		db = new dbutils();
		this.getNppInfo();
		String query;	
		if (search.length() == 0)
		{
			query = " select a.sochungtu, a.pk_seq  , a.ngaychot , a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " + 
			 " from huynhaphang a inner join nhanvien b on a.nguoitao = b.pk_seq " +
			 "  inner join nhanvien c on a.nguoisua = c.pk_seq " +
			 " inner join nhaphanphoi f on a.npp_fk = f.pk_seq "+
			 " where a.npp_fk = '" + this.nppId + "' order by a.pk_seq" ;
		}
		else
		{
			query = search;
		}
		
		this.createDhtvBeanList(query);  
		this.createDdkd();
	}

	@Override
	public void DBclose()
	{
		try 
		{
			if(!(this.daidienkd == null))
				this.daidienkd.close();
			if(this.dhtvlist==null){
				this.dhtvlist.close();
			}
			if(this.db != null)
				this.db.shutDown();
		} 
		catch(Exception e) {}
	}
	
			
}

