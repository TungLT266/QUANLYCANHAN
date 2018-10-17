package geso.traphaco.distributor.beans.cantrucongno.imp;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.bienbanbutru.IBienBanBuTruCongNoList;
import geso.traphaco.distributor.beans.cantrucongno.ICantrucongnoList;
import geso.traphaco.distributor.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import B.FI;

public class CantrucongnoList extends Phan_Trang implements ICantrucongnoList
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String tungay;
	String denngay;
	String trangthai;	
	ResultSet ddkd;
	String ddkdId;
	String sohoadon;
		
	ResultSet btcnlist;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	dbutils db;
	
	public CantrucongnoList(String[] param)
	{
		this.tungay = param[0];
		this.denngay = param[1];
		this.trangthai = param[2];
		this.ddkdId = param[3];
		this.sohoadon = param[4];
		db = new dbutils();
	}
	
	public CantrucongnoList()
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
	
	public ResultSet getBtcnList() 
	{	
		return this.btcnlist;
	}
	
	public void setBtcnList(ResultSet btcnlist)
	{
		this.btcnlist = btcnlist;		
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
			query = " select a.pk_seq as id, a.ngaychungtu , a.trangthai , b.ten as nguoitao, c.ten as nguoisua, a.ngaytao, a.ngaysua \n"+
					" from CANTRUCONGNO a inner join NHANVIEN b on a.nguoitao = b.pk_seq \n"+ 
					" inner join NHANVIEN c on a.nguoisua = c.pk_seq where a.npp_fk='" + this.nppId + "' \n";
			
			System.out.println("INIT_______ : "+query);
		}
		else
		{
			query = search;
		}		
		
		this.btcnlist = createSplittingData(50, 10, " ngaychungtu desc,  trangthai asc, ngaytao desc ", query);
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
